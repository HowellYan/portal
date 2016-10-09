package cn.com.bestpay.portal.config.property;

/**
 * Created by yfzx_gd_yanghh on 2016/9/29.
 */
public class GlobalProperty {
    // 配置文件所在包，以"/"开头，将包名的"."替换成"/"
    public static final String CONFIG_FILE_PACKAGE = GlobalProperty.class.getClassLoader().getResource("").getPath();

    public static final String systemConf = CONFIG_FILE_PACKAGE +"properties/system.properties";
}
