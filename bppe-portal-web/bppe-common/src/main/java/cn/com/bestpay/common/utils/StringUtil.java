package cn.com.bestpay.common.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

/**
 * User: 董培基
 * Date: 13-7-30
 * Time: 下午6:02
 */
public class StringUtil {

    private static final String FOLDER_SEPARATOR = "/";
    private static char[] codeSequence1 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] codeSequence2 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'};

    public static String generatePassWordRandomString(int i) {
        StringBuilder stringBuffer = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < i; j++) {
            stringBuffer.append(codeSequence2[random.nextInt(codeSequence2.length)]);
        }
        return stringBuffer.toString();
    }

    public static String generateRandomString(int i) {
        StringBuilder stringbuffer = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < i; j++)
            stringbuffer.append(codeSequence1[random.nextInt(codeSequence1.length)]);
        return stringbuffer.toString();
    }

    public static String getStringByNumber(int num) {
        Random random = new Random();
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < num; i++) {
            str.append(String.valueOf(codeSequence1[random.nextInt(10)]));
        }
        return str.toString();
    }

    public static String bytesToHexString(byte abyte0[]) {
        StringBuilder stringbuilder = new StringBuilder("");
        if (abyte0 == null || abyte0.length <= 0)
            return null;
        for (byte anAbyte0 : abyte0) {
            int j = anAbyte0 & 0xff;
            String s = Integer.toHexString(j);
            if (s.length() < 2)
                stringbuilder.append(0);
            stringbuilder.append(s);
        }
        return stringbuilder.toString();
    }

    public static byte[] hexStringToBytes(String s) {
        if (s == null || s.equals(""))
            return null;
        s = s.toUpperCase();
        int i = s.length() / 2;
        char ac[] = s.toCharArray();
        byte abyte0[] = new byte[i];
        for (int j = 0; j < i; j++) {
            int k = j * 2;
            abyte0[j] = (byte) (charToByte(ac[k]) << 4 | charToByte(ac[k + 1]));
        }
        return abyte0;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String fillLeft(String s, char c, int i) {
        return fillStr(s, c, i, true);
    }

    public static String fillRight(String s, char c, int i) {
        return fillStr(s, c, i, false);
    }

    private static String fillStr(String s, char c, int i, boolean flag) {
        int j = i - s.length();
        if (j <= 0)
            return s;
        StringBuilder stringbuilder = new StringBuilder(s);
        for (; j > 0; j--)
            if (flag)
                stringbuilder.insert(0, c);
            else
                stringbuilder.append(c);
        return stringbuilder.toString();
    }

    /**
     * 获取指定文件路径中对应的文件名
     *
     * @param path 文件路径
     * @return 文件名
     * @see {@link org.springframework.util.StringUtils#getFilename(String)}
     */
    public static String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
    }

    public static int length(String s) {
        if (Strings.isNullOrEmpty(s)) {
            return 0;
        } else {
            return s.length();
        }
    }

    public static String generateParams(Map<String, Object> paramMap) {
        StringBuilder params = new StringBuilder();
        for(Map.Entry<String, Object> m : paramMap.entrySet()){
            if (m.getValue() != null) {
                params.append("<")
                        .append(m.getKey())
                        .append(">")
                        .append(m.getValue())
                        .append("</")
                        .append(m.getKey())
                        .append(">");
            } else {
                params.append("<")
                        .append(m.getKey())
                        .append(">")
                        .append("</")
                        .append(m.getKey())
                        .append(">");
            }
        }
        return params.toString();
    }

    public static boolean isNumber(String s) {
        if (s == null || s.equals(""))
            return false;
        String s1 = "0123456789";
        for (int i = 0; i < s.length(); i++)
            if (s1.indexOf(s.charAt(i)) < 0)
                return false;
        return true;
    }

    /*
     *生成随机x位数字
     */
    public static String random(int x) {
        Double d = Math.random();
        String s = d.toString();
        int i = x > s.length() - 2 ? s.length() - 2 : x;
        return s.substring(2, 2 + i);
    }

    public static Map<String, String> getMap(String s) {
        Map<String, String> hashMap = Maps.newHashMap();
        if (s == null)
            return hashMap;
        String as[] = s.split("&");
        for (String s1 : as) {
            String as1[] = s1.split("=");
            if (as1.length == 2)
                hashMap.put(as1[0], as1[1]);
        }
        return hashMap;
    }

    /**
     * 得到城市拼音全拼
     *
     * @param src 传入 汉字
     * @return author qj
     */

    public static String getPingYin(String src) {
        char[] t1;
        t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        StringBuilder t4 = new StringBuilder("");
        try {
            for (char aT1 : t1) {
                //判断是否为汉字字符
                if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(aT1, t3);
                    t4.append(t2[0]);
                } else {
                    t4.append(Character.toString(aT1));
                }
            }
            return t4.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            return "";
        }
    }

    public static String toTrim(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        } else {
            return str.trim();
        }
    }
    /**
     * 格式化日期字符串，返回格式“yyyy年MM月dd日”
     *
     * @param str 日期字符串(yyyyMMdd格式,8位以上)
     * @return yyyy年MM月dd日
     * @author 韩纳威
     */
    public static String formatDateString(String str) {
        if (Strings.isNullOrEmpty(str)) return "";
        else {
            if (str.length() < 8) return str;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str.substring(0, 4)).append("年")
                    .append(str.substring(4, 6)).append("月")
                    .append(str.substring(6, 8)).append("日");
            return stringBuilder.toString();
        }
    }
    /**
     * 格式化日期字符串，返回格式“yyyy年MM月dd日”
     *
     * @param str 日期字符串(yyyyMMdd格式,8位以上)
     * @return yyyy年MM月dd日
     * @author 韩纳威
     */
    public static String formatDates(String str) {
        if (Strings.isNullOrEmpty(str)) return "";
        else {
            if (str.length() < 8) return str;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str.substring(0, 4)).append(".")
                    .append(str.substring(4, 6)).append(".")
                    .append(str.substring(6, 8));
            return stringBuilder.toString();
        }
    }
    /**
     * 格式化日期时间字符串，返回格式“yyyy年MM月dd日HH:mm”
     *
     * @param str 日期时间字符串(yyyyMMddHHmm格式，12位以上)
     * @return yyyy年MM月dd日HH:mm
     * @author 韩纳威
     */
    public static String formatDateTimeString(String str) {
        if (Strings.isNullOrEmpty(str)) return "";
        else {
            if (str.length() < 12) return str;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str.substring(0, 4)).append("年")
                    .append(str.substring(4, 6)).append("月")
                    .append(str.substring(6, 8)).append("日")
                    .append(str.substring(8, 10)).append(":")
                    .append(str.substring(10, 12));
            return stringBuilder.toString();
        }
    }

    /**
     * 格式化金额，转换为万（大于10000时）或千（大于1000且小于10000时）
     *
     * @param money 金额数字
     * @return #.####万/千
     */
    public static String formatMoney(double money) {
        DecimalFormat df = new DecimalFormat("0.####");
        String explain;
        if (money >= 10000) {
            explain = df.format(money / 10000) + "万";
        } else if (money >= 1000) {
            explain = df.format(money / 1000) + "千";
        } else {
            explain = df.format(money);
        }
        return explain;
    }
}

