package cn.com.bestpay.portal.config.filter.tool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by yfzx_gd_yanghh on 2016/9/28.
 */
public class SetVersion {
    private static Logger logger = LoggerFactory.getLogger(SetVersion.class);

    private static String resourceVersion;

    @Value("#{systemProperties['debug']}")
    private static String debug;
    /**
     * Response 注入静态资源版本号
     * @param content
     * @return
     */
    public static String setFileVersion(String content){

        if(resourceVersion == null){
            resourceVersion=RandomString(9);
        }
        logger.debug("isDebug:"+debug);
        return content.replaceAll("v=&version&","v="+resourceVersion);
    }


    /** 产生一个随机的字符串*/
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {
                //汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

}
