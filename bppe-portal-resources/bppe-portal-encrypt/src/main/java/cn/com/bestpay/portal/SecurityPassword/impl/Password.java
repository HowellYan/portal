package cn.com.bestpay.portal.SecurityPassword.impl;

import cn.com.bestpay.portal.SecurityPassword.PasswordInf;
import cn.com.bestpay.portal.common.utils.BASE64;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class Password {
    private static final Logger log = Logger.getLogger(Password.class);

    /**
     * 随机因子在session里的KEY
     */
    public static final String PWD_RANDOM_KEY = "TISSON_BPPF_PWD_RANDOM_KEY";
    /**
     * 密码实现类全路径KEY
     */
    public static final String PWD_PWDIMPL_KEY = "TISSON_BPPF_PWD_PASSWORDINF_KEY";
    /**
     * 密码控件名称
     */
    public static final String PWD_NAME_KEY = "TISSON_BPPF_PWD_NAME_KEY";
    /**
     * request范围
     */
    public static final int REQUEST_SCOPE = 1;
    /**
     * session范围
     */
    public static final int SESSION_SCOPE = 0;
    /**
     * 默认随机因子数长度
     */
    private static final int DEFAULT_RANDOM_LEN = 32;

    /**
     * 加密的密码
     *
     * @version: 1.00
     * @history: 2013-1-18 上午11:34:11 [created]
     * @author jiarong Tan
     * @param pwd
     * @param request
     * @param params
     *            扩展参数
     * @return
     * @throws Exception
     * @see
     */
    public String encode(String pwd, HttpServletRequest request, String params) throws Exception {
        return getPasswordInf(request).encode(pwd, params2Map(params));
    }

    /**
     * 输出密码框
     *
     * @version: 1.00
     * @history: 2013-1-15 上午11:52:05 [created]
     * @author jiarong Tan
     * @param id
     *            密码控件的ID
     * @param clazz
     *            密码控件及密码隐藏域class,用来设置密码加密后的值,多个密码控件,这个值必须相同
     * @param name
     *            密码隐藏域名字,用来后台取得密码值
     * @param rdName
     *            随机因子数隐藏域名,用来后台取值,如果为空则不生成随机因子隐藏域
     * @param sessionKey
     *            session存储随机因子的KEY
     * @param rdFromSession
     *            随机因子数是否从session取,true 则在session取,false则从新生成
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String writePWDInput(String id, String clazz, String name, String rdName, String sessionKey,
                                       boolean rdFromSession, HttpServletRequest request, String params) throws Exception {
        HttpSession session = request.getSession();
        String rd = session.getAttribute(sessionKey) + "".intern();
        if (!rdFromSession)
            rd = getRandom(sessionKey, DEFAULT_RANDOM_LEN, request, params);
        return getPasswordInf(request).writePWDInput(id, clazz, name, rdName, sessionKey, params2Map(params), request);
    }

    /**
     * 将加密后的字符串解密
     *
     * @version: 1.00
     * @history: 2013-1-9 下午04:03:20 [created]
     * @author jiarong Tan
     * @param str
     *            传入随机因子或范围的键
     * @param pwdStr
     *            加密后的字符串
     * @param scope
     *            取随机因子范围,如果该值设置为-1,则直接取str作为随机因子
     * @return 如果传入的密码为空或空字符串或设置密码实现类不正确，则返回传入值
     * @param request
     * @throws Exception
     * @see
     */
    public static String decode(String str, String pwdStr, int scope, HttpServletRequest request, String params)
            throws Exception {
        if (null == pwdStr || "".intern().equals(pwdStr.trim()) || null == str || "".intern().equals(str.trim()))
            return pwdStr;
        HttpSession session = request.getSession();
        String random = (String) session.getAttribute(str);// FIXME
        // 2014.10.21紧急版后改为直接从session获取
        PasswordInf pi = getPasswordInf(request);
        if (null == pi)
            return pwdStr;
        pwdStr = new String(BASE64.decode(pwdStr.trim()));
        String password = pi.decode(random, pwdStr.trim(), params2Map(params));
        return password;
    }

    /**
     * 将加密后的字符串解密
     *
     * @version: 1.00
     * @history: 2013-1-15 下午12:28:48 [created]
     * @author jiarong Tan
     * @param sesssionKey
     * @param pwdStr
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String decode(String sesssionKey, String pwdStr, HttpServletRequest request, String params)
            throws Exception {
        return decode(sesssionKey, pwdStr, SESSION_SCOPE, request, params);
    }

    /**
     * 将加密后的字符串解密,(取默认的KEY)
     *
     * @version: 1.00
     * @history: 2013-1-15 上午11:55:20 [created]
     * @author jiarong Tan
     * @param pwdStr
     *            加密字符串
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String decode(String pwdStr, HttpServletRequest request) throws Exception {
        return decode(PWD_RANDOM_KEY, pwdStr, SESSION_SCOPE, request, null);
    }

    /**
     * 获取密码控件随机因子数
     *
     * @version: 1.00
     * @history: 2013-1-15 上午11:56:01 [created]
     * @author jiarong Tan
     * @param sessionKey
     *            存储随机因子数的session键
     * @param len
     *            生成多少位的随机因子数
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String getRandom(String sessionKey, int len, HttpServletRequest request, String params)
            throws Exception {
        String str = getPasswordInf(request).getRandom(len, params2Map(params));
        request.getSession().setAttribute(sessionKey, str);
        return str;
    }

    /**
     * 获取密码控件随机因子数
     *
     * @version: 1.00
     * @history: 2013-1-15 上午11:55:53 [created]
     * @author jiarong Tan
     * @param len
     *            生成多少位的随机因子数
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String getRandom(int len, HttpServletRequest request, String params) throws Exception {
        return getRandom(PWD_RANDOM_KEY, len, request, params);
    }

    /**
     * 设置密码实现类,并把实现类类名存入session
     *
     * @version: 1.00
     * @history: 2013-1-10 下午01:01:45 [created]
     * @author jiarong Tan
     * @param clazz
     *            实现类全路径
     * @param request
     * @throws Exception
     * @see
     */
    public static void initPwdImpl(String clazz, HttpServletRequest request) throws Exception {
        Object _PWD_PWDIMPL_KEY = request.getSession().getAttribute("PWD_PWDIMPL_KEY");
        if (null == _PWD_PWDIMPL_KEY) {
            request.getSession().setAttribute(PWD_PWDIMPL_KEY, clazz);
            request.getSession()
                    .setAttribute(PWD_NAME_KEY, clazz.substring(clazz.lastIndexOf(".") + 1, clazz.length()));
            // request.getSession().getServletContext().setAttribute(PWD_PWDIMPL_KEY,
            // clazz);
        }
    }

    // public static void initPwdImpl(PwdEnum pwdEnum,HttpServletRequest
    // request)throws Exception{
    // initPwdImpl(pwdEnum.toString(),request);
    // }

    /**
     * 设置密码实现类
     *
     * @version: 1.00
     * @history: 2013-1-10 下午01:02:11 [created]
     * @author jiarong Tan
     * @param clazz
     *            实现类全路径
     * @param request
     * @throws Exception
     * @see
     */
    public static void initPwdImpl(Class clazz, HttpServletRequest request) throws Exception {
        initPwdImpl(clazz.getCanonicalName(), request);
    }

    /**
     * 获取密码具体实现类,在request里面获取具体实现类全路径,键值:[TISSON_BPPF_PWD_PASSWORDINF_KEY]
     *
     * @version: 1.00
     * @history: 2013-1-10 上午11:47:52 [created]
     * @author jiarong Tan
     * @param request
     * @return 返回null表示实现该类失败或传入的路径不正确
     * @throws Exception
     * @see
     */
    private static PasswordInf getPasswordInf(HttpServletRequest request) throws Exception {
        String classStr = request.getSession().getAttribute(PWD_PWDIMPL_KEY) + "".intern();
        // String classStr =
        // request.getSession().getServletContext().getAttribute(PWD_PWDIMPL_KEY)+"".intern();
        if (null == classStr || "".intern().equals(classStr.trim()) || "null".equals(classStr.trim())) {
            // throw new BusinessException("密码实现类路径不能为空");
            log.error("密码实现类路径不能为空");
            return null;
        }
        log.info("classStr:"+classStr);
        return (PasswordInf) getInstanceClazz(classStr);
    }

    /**
     * 根据传入的类全路径初始化类
     *
     * @version: 1.00
     * @history: 2013-1-10 上午11:34:33 [created]
     * @author jiarong Tan
     * @param clazz
     *            类全路径
     * @return 返回初始化后的类
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private static Object getInstanceClazz(String clazz) throws Exception {
        Class c = Class.forName(clazz);
        return (c.getConstructor().newInstance());
    }

    /**
     * 把附加属性转换成MAP
     *
     * @version: 1.00
     * @history: 2013-1-30 上午09:21:00 [created]
     * @author jiarong Tan
     * @param params
     *            属性格式:key1:value1,key2:value2....
     * @return 如果传入的字符为空 则返回null
     * @throws Exception
     * @see
     */
    private static Map<String, String> params2Map(String params) throws Exception {
        if (null == params || "".intern().equals(params.trim()))
            return null;
        String[] strs = params.split(",".intern());
        String[] tmpStrs = null;
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            tmpStrs = string.split(":".intern());
            map.put(tmpStrs[0], tmpStrs[1]);
        }
        return map;
    }

    // public enum PwdEnum{
    // OLDPWD("old"),
    // PASSGUARDCTRL("cn.tisson.cmpf.util.pwd.impl.PassGuardCtrl");
    //
    // private String classStr;
    //
    // private PwdEnum(String impClass){
    // this.classStr = impClass;
    // }
    // //覆盖原方法
    // public String toString(){
    // return classStr;
    // }
    // }

    public static void main(String[] args) throws Exception {
        // Password pwdUtil = new Password(request);
        // pwdUtil.initPwdImpl(PassGuardCtrl.class);
        // System.out.println();
        // PasswordInf pi =
        // (PasswordInf)Password.getInstanceClazz(PassGuardCtrl.class.getCanonicalName());
        // System.out.println(pi.decode("74823202975218382327363479990089",
        // "rfbqqvPYO0Y349VBKbnaqA=="));

        // String abc = "cnbppfabc";
        // System.out.println(abc.substring(abc.lastIndexOf(".")+1,abc.length()));

        // Password pwdUtil = new Password();

        // System.out.println(PwdEnum.PASSGUARDCTRL);
    }
}
