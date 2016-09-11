package cn.com.bestpay.template.engine.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * Created by Howell on 16/1/30.
 */
@Repository
public class RedisComponentCategoryDao extends RedisBaseDao<ComponentCategory> {
    @Autowired
    public RedisComponentCategoryDao(JedisTemplate template) {
        super(template);
    }

    public ComponentCategory findById(Long id) {
        ComponentCategory componentCategory = (ComponentCategory)this.findByKey(id);
        return componentCategory.getId() != null?componentCategory:null;
    }

    public List<ComponentCategory> findByParentId(final Long parentId) {
        Set ids = (Set)this.template.execute(new JedisTemplate.JedisAction() {
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(KeyUtils.componentCategoryChildrenOf(parentId.longValue()));
            }
        });
        return super.findByIds(ids);
    }

    public void create(final ComponentCategory componentCategory) {
        final Long id = this.newId();
        componentCategory.setId(id);
        this.template.execute(new JedisTemplate.JedisActionNoResult() {
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(KeyUtils.entityId(ComponentCategory.class, id.longValue()), RedisComponentCategoryDao.this.stringHashMapper.toHash(componentCategory));
                if(componentCategory.getParentId() != null) {
                    t.sadd(KeyUtils.componentCategoryChildrenOf(componentCategory.getParentId().longValue()), new String[]{id.toString()});
                }

                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final ComponentCategory componentCategory = this.findById(id);
        if(componentCategory != null) {
            this.template.execute(new JedisTemplate.JedisActionNoResult() {
                public void action(Jedis jedis) {
                    Transaction t = jedis.multi();
                    t.del(KeyUtils.entityId(ComponentCategory.class, id.longValue()));
                    if(componentCategory.getParentId() != null) {
                        t.srem(KeyUtils.componentCategoryChildrenOf(componentCategory.getParentId().longValue()), id.toString());
                    }

                    t.exec();
                }
            });
        }
    }

    public void update(final ComponentCategory componentCategory) {
        final Long id = componentCategory.getId();
        final ComponentCategory original = this.findById(id);
        if(original != null) {
            this.template.execute(new JedisTemplate.JedisActionNoResult() {
                public void action(Jedis jedis) {
                    Transaction t = jedis.multi();
                    t.hmset(KeyUtils.entityId(ComponentCategory.class, id.longValue()), RedisComponentCategoryDao.this.stringHashMapper.toHash(componentCategory));
                    if(componentCategory.getParentId() != null) {
                        t.srem(KeyUtils.componentCategoryChildrenOf(original.getParentId().longValue()), id.toString());
                        t.sadd(KeyUtils.componentCategoryChildrenOf(componentCategory.getParentId().longValue()), new String[]{id.toString()});
                    }

                    t.exec();
                }
            });
        }
    }
}

