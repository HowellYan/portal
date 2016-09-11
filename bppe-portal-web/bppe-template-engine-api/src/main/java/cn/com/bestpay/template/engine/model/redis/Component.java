package cn.com.bestpay.template.engine.model.redis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by Howell on 16/1/30.
 */
public class Component implements Serializable {

    private static final long serialVersionUID = -8376120801944888172L;
    private Long id;
    private Long compCategoryId;
    private String name;
    private String path;
    private String icon;
    private Integer rank;
    private Integer cachedBy = Integer.valueOf(0);
    private Long cachedTime;
    private String api;
    private Date createdAt;
    private Date updatedAt;
    @JsonIgnore
    private Map<String, String> apis;

    public Component() {
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof Component) {
            Component that = (Component)o;
            return Objects.equal(this.compCategoryId, that.compCategoryId) && Objects.equal(this.name, that.name) && Objects.equal(this.path, that.path);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.compCategoryId, this.name, this.path});
    }

    public String toString() {
        return Objects.toStringHelper(this).add("id", this.id).add("name", this.name).add("path", this.path).add("icon", this.icon).add("icon", this.icon).add("rank", this.rank).add("service", this.api).omitNullValues().toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompCategoryId() {
        return this.compCategoryId;
    }

    public void setCompCategoryId(Long compCategoryId) {
        this.compCategoryId = compCategoryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getCachedBy() {
        return this.cachedBy;
    }

    public void setCachedBy(Integer cachedBy) {
        this.cachedBy = cachedBy;
    }

    public Long getCachedTime() {
        return this.cachedTime;
    }

    public void setCachedTime(Long cachedTime) {
        this.cachedTime = cachedTime;
    }

    public String getApi() {
        return this.api;
    }

    public void setApi(String api) {
        this.api = api;
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

    public Map<String, String> getApis() {
        return this.apis;
    }

    public void setApis(Map<String, String> apis) {
        this.apis = apis;
    }

    public static enum CodeType {
        HANDLEBARS(Integer.valueOf(1), "Handlebars");

        private final Integer value;
        private final String display;

        private CodeType(Integer value, String display) {
            this.value = value;
            this.display = display;
        }

        public Integer toNumber() {
            return this.value;
        }

        public String toString() {
            return this.display;
        }

        public static CodeType fromNumber(int value) {
            CodeType[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                CodeType type = arr$[i$];
                if(type.value.intValue() == value) {
                    return type;
                }
            }

            return null;
        }
    }

    public static enum CacheBy {
        None(0, "None"),
        Widget(1, "Widget"),
        Component(2, "Component");

        private final int value;
        private final String display;

        private CacheBy(int value, String display) {
            this.value = value;
            this.display = display;
        }

        public static CacheBy fromNumber(int value) {
            CacheBy[] arr$ = values();
            int len$ = arr$.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                CacheBy type = arr$[i$];
                if(type.value == value) {
                    return type;
                }
            }

            return null;
        }

        public int value() {
            return this.value;
        }

        public String toString() {
            return this.display;
        }
    }
}
