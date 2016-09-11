package cn.com.bestpay.template.engine.model.redis;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Howell on 16/1/28.
 */
public class SiteInstance implements Serializable {

    private static final long serialVersionUID = 7735697080733511796L;
    private Long id;
    private Long siteId;
    private String name;
    private Integer status;
    private Long defaultPageId;
    private String style;
    private Date createdAt;
    private Date updatedAt;

    public SiteInstance() {
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof SiteInstance) {
            SiteInstance that = (SiteInstance)o;
            return Objects.equal(this.siteId, that.siteId) && Objects.equal(this.name, that.name) && Objects.equal(this.defaultPageId, that.defaultPageId) && Objects.equal(this.style, that.style) && Objects.equal(this.status, that.status);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.siteId, this.name, this.defaultPageId, this.style, this.status});
    }

    public String toString() {
        return Objects.toStringHelper(this).add("id", this.id).add("siteId", this.siteId).add("defaultPageId", this.defaultPageId).add("name", this.name).add("status", this.status).omitNullValues().toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDefaultPageId() {
        return this.defaultPageId;
    }

    public void setDefaultPageId(Long defaultPageId) {
        this.defaultPageId = defaultPageId;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
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

    public static enum Status {
        DELETED(Integer.valueOf(1), "删除"),
        NORMAL(Integer.valueOf(2), "正常");

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
                case 1:
                    return DELETED;
                case 2:
                    return NORMAL;
                default:
                    return null;
            }
        }
    }
}
