package cn.com.bestpay.template.engine.model.redis;

import cn.pojo.common.utils.Node;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by Howell on 16/1/28.
 */
public class Widget implements Node, Serializable {
    private static final long serialVersionUID = 6222424880839098697L;
    private Long id;
    private Long compId;
    private String jsonData;
    private Long parentId;
    private Long belongId;
    private Integer belongType;
    private String style;
    private String mode;
    private String behavior;

    public Widget() {
    }

    public boolean equals(Object o) {
        if(o != null && o instanceof Widget) {
            Widget that = (Widget)o;
            return Objects.equal(this.compId, that.compId) && Objects.equal(this.parentId, that.parentId) && Objects.equal(this.belongType, that.belongType) && Objects.equal(this.belongId, that.belongId) && Objects.equal(this.style, that.style) && Objects.equal(this.behavior, that.behavior) && Objects.equal(this.jsonData, that.jsonData);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.compId, this.parentId, this.belongType, this.belongId, this.style, this.behavior, this.jsonData});
    }

    public String toString() {
        return Objects.toStringHelper(this).add("id", this.id).add("compId", this.compId).add("parentId", this.parentId).add("belongType", this.belongType).add("belongId", this.belongId).add("style", this.style).add("behavior", this.behavior).add("jsonData", this.jsonData).omitNullValues().toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompId() {
        return this.compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getBelongId() {
        return this.belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

    public Integer getBelongType() {
        return this.belongType;
    }

    public void setBelongType(Integer belongType) {
        this.belongType = belongType;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBehavior() {
        return this.behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public static enum BelongType {
        HEADER(Integer.valueOf(1), "头部"),
        FOOTER(Integer.valueOf(2), "尾部"),
        PAGE(Integer.valueOf(3), "页面"),
        SITE(Integer.valueOf(4), "站点"),
        ITEM(Integer.valueOf(5), "商品");

        private final Integer value;
        private final String display;

        private BelongType(Integer value, String display) {
            this.value = value;
            this.display = display;
        }

        public Integer toNumber() {
            return this.value;
        }

        public String toString() {
            return this.display;
        }

        public static BelongType fromNumber(Integer value) {
            switch(value.intValue()) {
                case 1:
                    return HEADER;
                case 2:
                    return FOOTER;
                case 3:
                    return PAGE;
                case 4:
                    return SITE;
                case 5:
                    return ITEM;
                default:
                    return null;
            }
        }
    }
}

