package cn.com.bestpay.portal.config.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by susie on 2016/9/28.
 */

public class SystemProperty {
    private static Logger logger = LoggerFactory.getLogger(SystemProperty.class);

    public SystemProperty() {
        logger.info("-------初始化配置开始------");
        initSystemConf();
        logger.info("-------初始化配置完成-----");
    }

    //Property 文件 转成 Map
    private static Map<String,String> param = null ;

    /**
     *
     * @param param
     */
    public static void setParam(Map<String, String> param) {
        if(param==null) {
            param = new HashMap<String,String>(0);
        }
        SystemProperty.param = param;
    }

    /**
     *
     * @param key
     * @return
     */
    public static String getValueParam(String key) {
       return SystemProperty.param.get(key);
    }
    /**
     * 初始化用户配置.
     *
     */
    private static void initSystemConf() {
        Map<String, String> param = new HashMap<String, String>();
        Properties properties = SystemProperty.readConfig(GlobalProperty.systemConf);
        param.put("system.debug", properties.getProperty("debug"));

        SystemProperty.setParam(param);
    }

    /**
     * @param propFile
     *            路径.
     * @return proerties.
     */
    public static Properties readConfig(String propFile) {
        Properties p = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(propFile);
            p.load(input);
        } catch (IOException e) {
            logger.error("加载配置异常:", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.error("关闭流异常:", e);
            }
        }
        return p;
    }


}
