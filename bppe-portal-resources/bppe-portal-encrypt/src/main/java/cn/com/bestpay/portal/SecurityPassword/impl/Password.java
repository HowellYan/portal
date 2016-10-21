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
     * ���������session���KEY
     */
    public static final String PWD_RANDOM_KEY = "TISSON_BPPF_PWD_RANDOM_KEY";
    /**
     * ����ʵ����ȫ·��KEY
     */
    public static final String PWD_PWDIMPL_KEY = "TISSON_BPPF_PWD_PASSWORDINF_KEY";
    /**
     * ����ؼ�����
     */
    public static final String PWD_NAME_KEY = "TISSON_BPPF_PWD_NAME_KEY";
    /**
     * request��Χ
     */
    public static final int REQUEST_SCOPE = 1;
    /**
     * session��Χ
     */
    public static final int SESSION_SCOPE = 0;
    /**
     * Ĭ���������������
     */
    private static final int DEFAULT_RANDOM_LEN = 32;

    /**
     * ���ܵ�����
     * 
     * @version: 1.00
     * @history: 2013-1-18 ����11:34:11 [created]
     * @author jiarong Tan
     * @param pwd
     * @param request
     * @param params
     *            ��չ����
     * @return
     * @throws Exception
     * @see
     */
    public String encode(String pwd, HttpServletRequest request, String params) throws Exception {
        return getPasswordInf(request).encode(pwd, params2Map(params));
    }

    /**
     * ��������
     * 
     * @version: 1.00
     * @history: 2013-1-15 ����11:52:05 [created]
     * @author jiarong Tan
     * @param id
     *            ����ؼ���ID
     * @param clazz
     *            ����ؼ�������������class,��������������ܺ��ֵ,�������ؼ�,���ֵ������ͬ
     * @param name
     *            ��������������,������̨ȡ������ֵ
     * @param rdName
     *            �����������������,������̨ȡֵ,���Ϊ���������������������
     * @param sessionKey
     *            session�洢������ӵ�KEY
     * @param rdFromSession
     *            ����������Ƿ��sessionȡ,true ����sessionȡ,false���������
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
     * �����ܺ���ַ�������
     * 
     * @version: 1.00
     * @history: 2013-1-9 ����04:03:20 [created]
     * @author jiarong Tan
     * @param str
     *            ����������ӻ�Χ�ļ�
     * @param pwdStr
     *            ���ܺ���ַ���
     * @param scope
     *            ȡ������ӷ�Χ,�����ֵ����Ϊ-1,��ֱ��ȡstr��Ϊ�������
     * @return ������������Ϊ�ջ���ַ�������������ʵ���಻��ȷ���򷵻ش���ֵ
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
                                                           // 2014.10.21��������Ϊֱ�Ӵ�session��ȡ
        PasswordInf pi = getPasswordInf(request);
        if (null == pi)
            return pwdStr;
        pwdStr = new String(BASE64.decode(pwdStr.trim()));
        String password = pi.decode(random, pwdStr.trim(), params2Map(params));
        return password;
    }

    /**
     * �����ܺ���ַ�������
     * 
     * @version: 1.00
     * @history: 2013-1-15 ����12:28:48 [created]
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
     * �����ܺ���ַ�������,(ȡĬ�ϵ�KEY)
     * 
     * @version: 1.00
     * @history: 2013-1-15 ����11:55:20 [created]
     * @author jiarong Tan
     * @param pwdStr
     *            �����ַ���
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String decode(String pwdStr, HttpServletRequest request) throws Exception {
        return decode(PWD_RANDOM_KEY, pwdStr, SESSION_SCOPE, request, null);
    }

    /**
     * ��ȡ����ؼ����������
     * 
     * @version: 1.00
     * @history: 2013-1-15 ����11:56:01 [created]
     * @author jiarong Tan
     * @param sessionKey
     *            �洢�����������session��
     * @param len
     *            ���ɶ���λ�����������
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
     * ��ȡ����ؼ����������
     * 
     * @version: 1.00
     * @history: 2013-1-15 ����11:55:53 [created]
     * @author jiarong Tan
     * @param len
     *            ���ɶ���λ�����������
     * @param request
     * @return
     * @throws Exception
     * @see
     */
    public static String getRandom(int len, HttpServletRequest request, String params) throws Exception {
        return getRandom(PWD_RANDOM_KEY, len, request, params);
    }

    /**
     * ��������ʵ����,����ʵ������������session
     * 
     * @version: 1.00
     * @history: 2013-1-10 ����01:01:45 [created]
     * @author jiarong Tan
     * @param clazz
     *            ʵ����ȫ·��
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
     * ��������ʵ����
     * 
     * @version: 1.00
     * @history: 2013-1-10 ����01:02:11 [created]
     * @author jiarong Tan
     * @param clazz
     *            ʵ����ȫ·��
     * @param request
     * @throws Exception
     * @see
     */
    public static void initPwdImpl(Class clazz, HttpServletRequest request) throws Exception {
        initPwdImpl(clazz.getCanonicalName(), request);
    }

    /**
     * ��ȡ�������ʵ����,��request�����ȡ����ʵ����ȫ·��,��ֵ:[TISSON_BPPF_PWD_PASSWORDINF_KEY]
     * 
     * @version: 1.00
     * @history: 2013-1-10 ����11:47:52 [created]
     * @author jiarong Tan
     * @param request
     * @return ����null��ʾʵ�ָ���ʧ�ܻ����·������ȷ
     * @throws Exception
     * @see
     */
    private static PasswordInf getPasswordInf(HttpServletRequest request) throws Exception {
        String classStr = request.getSession().getAttribute(PWD_PWDIMPL_KEY) + "".intern();
        // String classStr =
        // request.getSession().getServletContext().getAttribute(PWD_PWDIMPL_KEY)+"".intern();
        if (null == classStr || "".intern().equals(classStr.trim()) || "null".equals(classStr.trim())) {
            // throw new BusinessException("����ʵ����·������Ϊ��");
            log.error("����ʵ����·������Ϊ��");
            return null;
        }
        return (PasswordInf) getInstanceClazz(classStr);
    }

    /**
     * ���ݴ������ȫ·����ʼ����
     * 
     * @version: 1.00
     * @history: 2013-1-10 ����11:34:33 [created]
     * @author jiarong Tan
     * @param clazz
     *            ��ȫ·��
     * @return ���س�ʼ�������
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private static Object getInstanceClazz(String clazz) throws Exception {
        Class c = Class.forName(clazz);
        return (c.getConstructor().newInstance());
    }

    /**
     * �Ѹ�������ת����MAP
     * 
     * @version: 1.00
     * @history: 2013-1-30 ����09:21:00 [created]
     * @author jiarong Tan
     * @param params
     *            ���Ը�ʽ:key1:value1,key2:value2....
     * @return ���������ַ�Ϊ�� �򷵻�null
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
    // //����ԭ����
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
