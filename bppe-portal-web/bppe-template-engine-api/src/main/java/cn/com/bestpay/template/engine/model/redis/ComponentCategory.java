package cn.com.bestpay.template.engine.model.redis;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by Howell on 16/1/30.
 */
public class ComponentCategory implements Serializable {
    private static final long serialVersionUID = -94031290224430163L;
    private Long id;
    private Long parentId;
    private String name;
    private String icon;
    private Integer rank;

    public ComponentCategory() {
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof ComponentCategory) {
            ComponentCategory that = (ComponentCategory)o;
            return Objects.equal(this.parentId, that.parentId) && Objects.equal(this.name, that.name) && Objects.equal(this.rank, that.rank);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.parentId, this.name, this.rank});
    }

    public String toString() {
        return Objects.toStringHelper(this).add("id", this.id).add("name", this.name).add("icon", this.icon).add("rank", this.rank).toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
}
