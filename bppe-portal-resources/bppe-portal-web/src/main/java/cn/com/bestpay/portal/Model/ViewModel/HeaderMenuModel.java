package cn.com.bestpay.portal.Model.ViewModel;

import java.io.Serializable;

/**
 * Created by susie on 2016/10/12.
 *
 */
public class HeaderMenuModel implements Serializable {
    /**
     * 菜单ID
     * @ValueType [int]
     */
    private String menuID = "";
    /**
     * 菜单名称
     */
    private String menuName = "";
    private String menuUrl = "";
    private String template = "";
    private String menuTip = "";
    /**
     * 跳新窗口
     * @value ['true' or 'false']
     */
    private String skipping = "";

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMenuTip() {
        return menuTip;
    }

    public void setMenuTip(String menuTip) {
        this.menuTip = menuTip;
    }

    public String getSkipping() {
        return skipping;
    }

    public void setSkipping(String skipping) {
        this.skipping = skipping;
    }
}
