package cn.com.bestpay.portal.utils;

import java.util.Random;

/**
 * Created by susie on 2016/11/9.
 */
public class RandomString {
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
}
