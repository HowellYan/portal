package cn.com.bestpay.template.engine.dao.redis;


import com.google.common.base.Strings;
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
public class RedisComponentDao extends RedisBaseDao<Component> {
    @Autowired
    public RedisComponentDao(JedisTemplate template) {
        super(template);
    }

    public Component findById(Long id) {
        Component component = (Component)this.findByKey(id);
        return component.getId() != null?component:null;
    }

    public List<Component> findByCategoryId(final Long categoryId) {
        Set ids = (Set)this.template.execute(new JedisAction() {
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(KeyUtils.components(categoryId.longValue()));
            }
        });
        return super.findByIds(ids);
    }

    public Component findByPath(final String path) {
        String id = (String)this.template.execute(new JedisAction() {
            public String action(Jedis jedis) {
                return jedis.get(KeyUtils.component(path));
            }
        });
        return Strings.isNullOrEmpty(id)?null:this.findById(Long.valueOf(Long.parseLong(id)));
    }

    public void create(final Component component) {
        final Long id = this.newId();
        component.setId(id);
        this.template.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.hmset(KeyUtils.entityId(Component.class, id.longValue()), RedisComponentDao.this.stringHashMapper.toHash(component));
                t.sadd(KeyUtils.components(component.getCompCategoryId().longValue()), new String[]{id.toString()});
                if(!Strings.isNullOrEmpty(component.getPath())) {
                    t.setnx(KeyUtils.component(component.getPath()), id.toString());
                }

                t.exec();
            }
        });
    }

    public void delete(final Long id) {
        final Component component = (Component)this.findByKey(id);
        if(id != null) {
            this.template.execute(new JedisActionNoResult() {
                public void action(Jedis jedis) {
                    Transaction t = jedis.multi();
                    t.del(KeyUtils.entityId(Component.class, id.longValue()));
                    t.srem(KeyUtils.components(component.getCompCategoryId().longValue()), id.toString());
                    if(!Strings.isNullOrEmpty(component.getPath())) {
                        t.del(KeyUtils.component(component.getPath()));
                    }

                    t.exec();
                }
            });
        }
    }

    public void update(final Component component) {
        final Long id = component.getId();
        final Component original = (Component)this.findByKey(id);
        if(original == null) {
            throw new IllegalStateException("component not exist");
        } else {
            this.template.execute(new JedisActionNoResult() {
                public void action(Jedis jedis) {
                    Transaction t = jedis.multi();
                    t.hmset(KeyUtils.entityId(Component.class, id.longValue()), RedisComponentDao.this.stringHashMapper.toHash(component));
                    if(component.getCompCategoryId() != null) {
                        t.srem(KeyUtils.components(original.getCompCategoryId().longValue()), id.toString());
                        t.sadd(KeyUtils.components(component.getCompCategoryId().longValue()), new String[]{id.toString()});
                    }

                    if(!Strings.isNullOrEmpty(component.getPath())) {
                        t.del(KeyUtils.component(original.getPath()));
                        t.setnx(KeyUtils.component(component.getPath()), id.toString());
                    }

                    t.exec();
                }
            });
        }
    }
}

