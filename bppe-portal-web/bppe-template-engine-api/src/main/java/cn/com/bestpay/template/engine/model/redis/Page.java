package cn.com.bestpay.template.engine.model.redis;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by Howell on 16/1/30.
 */
public class Page implements Serializable {
    private static final long serialVersionUID = -8050328865364181624L;
    private Long id;
    private String path;
    private String name;
    private Long siteInstanceId;
    private String title;
    private String description;
    private String keyword;
    private String onNav;
    private Integer rank;

    public Page() {
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof Page) {
            Page that = (Page)o;
            return Objects.equal(this.path, that.path);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.path});
    }

    public String toString() {
        return Objects.toStringHelper(this).add("id", this.id).add("siteInstanceId", this.siteInstanceId).add("path", this.path).add("title", this.title).add("description", this.description).add("keyword", this.keyword).add("name", this.name).add("rank", this.rank).omitNullValues().toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSiteInstanceId() {
        return this.siteInstanceId;
    }

    public void setSiteInstanceId(Long siteInstanceId) {
        this.siteInstanceId = siteInstanceId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOnNav() {
        return this.onNav;
    }

    public void setOnNav(String onNav) {
        this.onNav = onNav;
    }

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
