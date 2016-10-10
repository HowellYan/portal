package cn.com.bestpay.portal.config.property;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by susie on 2016/9/28.
 */

public class SystemProperty {

    //Property 文件 转成 Map
    private static Map<String,String> param = null ;


    public SystemProperty(String systemConf){
        initSystemConf(systemConf);
    }
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
     * 初始化config.property文件配置.
     *
     */
    public static void initSystemConf(String systemConf) {
        Map<String, String> param = new HashMap<String, String>();
        Properties properties = SystemProperty.readConfig(systemConf);
        param.put("system.debug", properties.getProperty("debug"));
        param.put("system.CDN_Url", properties.getProperty("CDN_Url"));
        param.put("system.setVersionStr", properties.getProperty("setVersionStr"));
        param.put("system.setCDNUrlStr", properties.getProperty("setCDNUrlStr"));
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

        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {

            }
        }
        return p;
    }


}
