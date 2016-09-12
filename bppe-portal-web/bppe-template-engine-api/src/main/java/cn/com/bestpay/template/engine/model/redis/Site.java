package cn.com.bestpay.template.engine.model.redis;


import cn.com.bestpay.common.model.Indexable;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * Created by Howell on 16/1/28.
 */
public class Site implements Indexable {
    private static final long serialVersionUID = -7166930192352652007L;
    private Long id;

    private String name;
    private String image;
    private Long userId;
    private String domain;
    private String subDomain;
    private String hrefBase;
    private String rootPath;
    private Integer price;
    private Integer status;
    private Integer siteCategory;
    private Integer type;
    private Long releaseSiteInstanceId;
    private Long defaultSiteInstanceId;
    private Long forkFrom;
    private Long forkRoot;
    private Integer forkable;
    private Integer color;
    private Integer stars;
    private Integer forks;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public Site() {
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof Site) {
            Site that = (Site)o;
            return Objects.equal(this.name, that.name) && Objects.equal(this.userId, that.userId) && Objects.equal(this.domain, that.domain) && Objects.equal(this.subDomain, that.subDomain) && Objects.equal(this.defaultSiteInstanceId, that.defaultSiteInstanceId) && Objects.equal(this.siteCategory, that.siteCategory) && Objects.equal(this.type, that.type);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.name, this.userId, this.domain, this.subDomain, this.defaultSiteInstanceId, this.siteCategory, this.type});
    }

    public String toString() {
        return Objects.toStringHelper(this).add("id", this.id).add("name", this.name).add("userId", this.userId).add("domain", this.domain).add("subDomain", this.subDomain).add("defaultSiteInstanceId", this.defaultSiteInstanceId).add("siteCategory", this.siteCategory).add("type", this.type).add("forkFrom", this.forkFrom).add("stars", this.stars).add("forks", this.forks).omitNullValues().toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSubDomain() {
        return this.subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getHrefBase() {
        return this.hrefBase;
    }

    public void setHrefBase(String hrefBase) {
        this.hrefBase = hrefBase;
    }

    public String getRootPath() {
        return this.rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSiteCategory() {
        return this.siteCategory;
    }

    public void setSiteCategory(Integer siteCategory) {
        this.siteCategory = siteCategory;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getDefaultSiteInstanceId() {
        return this.defaultSiteInstanceId;
    }

    public void setDefaultSiteInstanceId(Long defaultSiteInstanceId) {
        this.defaultSiteInstanceId = defaultSiteInstanceId;
    }

    public Long getReleaseSiteInstanceId() {
        return this.releaseSiteInstanceId;
    }

    public void setReleaseSiteInstanceId(Long releaseSiteInstanceId) {
        this.releaseSiteInstanceId = releaseSiteInstanceId;
    }

    public Long getForkFrom() {
        return this.forkFrom;
    }

    public void setForkFrom(Long forkFrom) {
        this.forkFrom = forkFrom;
    }

    public Long getForkRoot() {
        return this.forkRoot;
    }

    public void setForkRoot(Long forkRoot) {
        this.forkRoot = forkRoot;
    }

    public Integer getForkable() {
        return this.forkable;
    }

    public void setForkable(Integer forkable) {
        this.forkable = forkable;
    }

    public Integer getColor() {
        return this.color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getStars() {
        return this.stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Integer getForks() {
        return this.forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static enum Type {
        OFFICIAL(Integer.valueOf(111), "官方"),
        NORMAL(Integer.valueOf(1), "一般"),
        SHOP(Integer.valueOf(2), "店铺");

        private final Integer value;
        private final String display;

        private Type(Integer value, String display) {
            this.value = value;
            this.display = display;
        }

        public Integer toNumber() {
            return this.value;
        }

        public String toString() {
            return this.display;
        }

        public static Type fromNumber(Integer value) {
            switch(value.intValue()) {
                case 1:
                    return NORMAL;
                case 2:
                    return SHOP;
                case 111:
                    return OFFICIAL;
                default:
                    return null;
            }
        }
    }

    public static enum Status {
        DELETED(Integer.valueOf(-1), "删除"),
        NORMAL(Integer.valueOf(1), "正常"),
        DESIGN(Integer.valueOf(0), "设计");

        private final Integer value;
        private final String display;

        private Status(Integer value, String display) {
            this.value = value;
            this.display = display;
        }

        public Integer toNumber() {
            return this.value;
        }

        public String toString() {
            return this.display;
        }

        public static Status fromNumber(Integer value) {
            switch(value.intValue()) {
                case -1:
                    return DELETED;
                case 0:
                    return DESIGN;
                case 1:
                    return NORMAL;
                default:
                    return null;
            }
        }
    }
}

