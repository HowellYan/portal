package cn.com.bestpay.template.engine.dao.redis;


import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Howell on 16/1/28.
 */

@Repository
public class RedisSiteDao extends RedisBaseDao<Site> {
    @Autowired
    public RedisSiteDao(JedisTemplate template) {
        super(template);
    }

    public Site findById(Long id) {
        Site site = (Site)this.findByKey(id);
        return site.getId() != null?site:null;
    }

    public Site findByDomain(String domain, boolean isSubDomain) {
        final String key = isSubDomain?KeyUtils.subDomain(domain):KeyUtils.domain(domain);
        String siteId = (String)this.template.execute(new JedisTemplate.JedisAction() {
            public String action(Jedis jedis) {
                return jedis.get(key);
            }
        });
        return !Strings.isNullOrEmpty(siteId)?this.findById(Long.valueOf(Long.parseLong(siteId))):null;
    }

    public void create(final Site site) {
        final Long id = site.getId();
        Preconditions.checkArgument(id != null, "Site\'s id can not be null.");
        Date now = new Date();
        site.setCreatedAt(now);
        site.setUpdatedAt(now);
        if(!Strings.isNullOrEmpty(site.getDomain()) && this.domainExists(site.getDomain()).booleanValue()) {
            throw new IllegalArgumentException("domain exists");
        } else if(!Strings.isNullOrEmpty(site.getSubDomain()) && this.subDomainExists(site.getSubDomain()).booleanValue()) {
            throw new IllegalArgumentException("subDomain exists");
        } else {
            this.template.execute(new JedisActionNoResult() {
                public void action(Jedis jedis) {
                    Transaction t = jedis.multi();
                    t.hmset(KeyUtils.entityId(Site.class, id.longValue()), RedisSiteDao.this.stringHashMapper.toHash(site));
                    t.sadd(KeyUtils.sites(site.getUserId().longValue()), new String[]{id.toString()});
                    if(!Strings.isNullOrEmpty(site.getDomain())) {
                        t.setnx(KeyUtils.domain(site.getDomain()), id.toString());
                    }

                    if(!Strings.isNullOrEmpty(site.getSubDomain())) {
                        t.setnx(KeyUtils.subDomain(site.getSubDomain()), id.toString());
                    }

                    if(Objects.equal(site.getForkable(), Integer.valueOf(1))) {
                        t.zadd(KeyUtils.templatesOf(site.getUserId().longValue()), (double)(-id.longValue()), id.toString());
                    }

                    t.lpush(KeyUtils.allSites(), new String[]{id.toString()});
                    t.exec();
                }
            });
        }
    }

    public void delete(final Long id) {
        final Site site = this.findById(id);
        if(site != null) {
            this.template.execute(new JedisActionNoResult() {
                public void action(Jedis jedis) {
                    String subDomain = site.getSubDomain();
                    String domain = site.getDomain();
                    Transaction t = jedis.multi();
                    t.del(KeyUtils.entityId(Site.class, id.longValue()));
                    t.srem(KeyUtils.sites(site.getUserId().longValue()), id.toString());
                    if(!Strings.isNullOrEmpty(subDomain)) {
                        t.del(KeyUtils.subDomain(subDomain));
                    }

                    if(!Strings.isNullOrEmpty(domain)) {
                        t.del(KeyUtils.domain(domain));
                    }

                    t.lrem(KeyUtils.allSites(), 1L, id.toString());
                    t.exec();
                }
            });
        }
    }

    public void update(final Site site) {
        Preconditions.checkArgument(site.getDomain() == null, "can not update domain use this method");
        Preconditions.checkArgument(site.getSubDomain() == null, "can not update subDomain use this method");
        site.setUpdatedAt(new Date());
        this.template.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                Long id = site.getId();
                t.hmset(KeyUtils.entityId(Site.class, id.longValue()), RedisSiteDao.this.stringHashMapper.toHash(site));
                t.lrem(KeyUtils.allSites(), 1L, id.toString());
                t.lpush(KeyUtils.allSites(), new String[]{id.toString()});
                t.exec();
            }
        });
    }

    public void updateSubDomain(final Long id, final String subDomain) {
        Site site = this.findById(id);
        if(site != null) {
            if(this.subDomainExists(subDomain).booleanValue()) {
                throw new IllegalArgumentException("subDomain exists");
            } else {
                final String originSubDomain = site.getSubDomain();
                this.template.execute(new JedisTemplate.JedisActionNoResult() {
                    public void action(Jedis jedis) {
                        Transaction t = jedis.multi();
                        if(!Strings.isNullOrEmpty(originSubDomain)) {
                            t.del(KeyUtils.subDomain(originSubDomain));
                        }

                        t.hset(KeyUtils.entityId(Site.class, id.longValue()), "subDomain", subDomain);
                        t.setnx(KeyUtils.subDomain(subDomain), id.toString());
                        t.exec();
                    }
                });
            }
        }
    }

    public Boolean domainExists(final String domain) {
        return (Boolean)this.template.execute(new JedisTemplate.JedisAction() {
            public Boolean action(Jedis jedis) {
                return jedis.exists(KeyUtils.domain(domain));
            }
        });
    }

    public Boolean subDomainExists(final String subDomain) {
        return (Boolean)this.template.execute(new JedisAction() {
            public Boolean action(Jedis jedis) {
                return jedis.exists(KeyUtils.subDomain(subDomain));
            }
        });
    }

    public void updateDomain(final Long id, final String domain) {
        Site site = this.findById(id);
        if(site != null) {
            if(this.domainExists(domain).booleanValue()) {
                throw new IllegalArgumentException("domain exists");
            } else {
                final String originalDomain = site.getDomain();
                this.template.execute(new JedisActionNoResult() {
                    public void action(Jedis jedis) {
                        Transaction t = jedis.multi();
                        if(!Strings.isNullOrEmpty(originalDomain)) {
                            t.del(KeyUtils.domain(originalDomain));
                        }

                        t.hset(KeyUtils.entityId(Site.class, id.longValue()), "domain", domain);
                        t.set(KeyUtils.domain(domain), id.toString());
                        t.exec();
                    }
                });
            }
        }
    }

    public List<Site> findByUserId(final Long userId) {
        Set ids = (Set)this.template.execute(new JedisAction() {
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(KeyUtils.sites(userId.longValue()));
            }
        });
        return super.findByIds(ids);
    }

    public Long maxId() {
        return (Long)this.template.execute(new JedisAction() {
            public Long action(Jedis jedis) {
                return Long.valueOf(Long.parseLong(jedis.get(KeyUtils.entityCount(Site.class))));
            }
        });
    }

    public void changeOwner(final Long siteId, final Long oldUerId, final Long newUserId) {
        this.template.execute(new JedisActionNoResult() {
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                t.srem(KeyUtils.sites(oldUerId.longValue()), siteId.toString());
                t.sadd(KeyUtils.sites(newUserId.longValue()), new String[]{siteId.toString()});
                t.exec();
            }
        });
    }

    public Paging<Site> pagination(final int offset, final Integer size) {
        Long total = (Long)this.template.execute(new JedisAction() {
            public Long action(Jedis jedis) {
                return jedis.llen(KeyUtils.allSites());
            }
        });
        if((long)offset >= total.longValue()) {
            return new Paging(total, Collections.emptyList());
        } else {
            List ids = (List)this.template.execute(new JedisAction() {
                public List<String> action(Jedis jedis) {
                    return jedis.lrange(KeyUtils.allSites(), (long)offset, (long)(offset + size.intValue() - 1));
                }
            });
            List sites = super.findByIds(ids);
            return new Paging(total, sites);
        }
    }
}

