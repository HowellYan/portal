package cn.com.bestpay.portal.SecurityPassword;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 密码控件 File : PasswordInf.java Copy Right : www.tisson.cn Project : bppf JDK
 * version used : JDK 1.5 Comments : Version : 1.00 Modification history :
 * 2013-1-9 上午11:28:05 [created] Author : jiarong Tan Email : tanjr@tisson.cn
 * 
 */
public interface PasswordInf {
    /**
     * 输出密码框
     * 
     * @version: 1.00
     * @history: 2013-1-15 上午11:52:05 [created]
     * @author jiarong Tan
     * @param id
     *            密码控件的ID
     * @param clazz
     *            密码隐藏域class,用来设置密码加密后的值,多个密码控件,这个值必须相同
     * @param name
     *            密码隐藏域名字,用来后台取得密码值
     * @param rdName
     *            随机因子数隐藏域名,用来后台取值
     * @param sessionKey
     *            session存储随机因子的KEY
     * @param params
     *            扩展参数
     * @return
     * @throws Exception
     */
    public String writePWDInput(String id, String clazz, String name, String rdName, String sessionKey,
                                Map<String, String> params, HttpServletRequest request) throws Exception;

    /**
     * 加密密码
     * 
     * @version: 1.00
     * @history: 2013-1-18 上午11:34:11 [created]
     * @author jiarong Tan
     * @param pwd
     * @param params
     *            扩展参数
     * @return
     * @throws Exception
     * @see
     */
    public String encode(String pwd, Map<String, String> params) throws Exception;

    /**
     * 将加密后的字符串解密
     * 
     * @version: 1.00
     * @history: 2013-1-9 下午04:03:20 [created]
     * @author jiarong Tan
     * @param random
     *            传入随机因子
     * @param pwd
     *            加密后的字符串
     * @param params
     *            扩展参数
     * @throws Exception
     * @see
     */
    public String decode(String random, String pwd, Map<String, String> params) throws Exception;

    /**
     * 获取密码控件随机因子数
     * 
     * @version: 1.00
     * @history: 2013-1-9 上午11:27:02 [created]
     * @author jiarong Tan
     * @param len
     *            生成随机因子长度
     * @param params
     *            扩展参数
     * @return
     * @throws Exception
     * @see
     */
    public String getRandom(int len, Map<String, String> params) throws Exception;
}
