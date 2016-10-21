package cn.com.bestpay.portal.SecurityPassword;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ����ؼ� File : PasswordInf.java Copy Right : www.tisson.cn Project : bppf JDK
 * version used : JDK 1.5 Comments : Version : 1.00 Modification history :
 * 2013-1-9 ����11:28:05 [created] Author : jiarong Tan Email : tanjr@tisson.cn
 * 
 */
public interface PasswordInf {
    /**
     * ��������
     * 
     * @version: 1.00
     * @history: 2013-1-15 ����11:52:05 [created]
     * @author jiarong Tan
     * @param id
     *            ����ؼ���ID
     * @param clazz
     *            ����������class,��������������ܺ��ֵ,�������ؼ�,���ֵ������ͬ
     * @param name
     *            ��������������,������̨ȡ������ֵ
     * @param rdName
     *            �����������������,������̨ȡֵ
     * @param sessionKey
     *            session�洢������ӵ�KEY
     * @param params
     *            ��չ����
     * @return
     * @throws Exception
     */
    public String writePWDInput(String id, String clazz, String name, String rdName, String sessionKey,
                                Map<String, String> params, HttpServletRequest request) throws Exception;

    /**
     * ��������
     * 
     * @version: 1.00
     * @history: 2013-1-18 ����11:34:11 [created]
     * @author jiarong Tan
     * @param pwd
     * @param params
     *            ��չ����
     * @return
     * @throws Exception
     * @see
     */
    public String encode(String pwd, Map<String, String> params) throws Exception;

    /**
     * �����ܺ���ַ�������
     * 
     * @version: 1.00
     * @history: 2013-1-9 ����04:03:20 [created]
     * @author jiarong Tan
     * @param random
     *            �����������
     * @param pwd
     *            ���ܺ���ַ���
     * @param params
     *            ��չ����
     * @throws Exception
     * @see
     */
    public String decode(String random, String pwd, Map<String, String> params) throws Exception;

    /**
     * ��ȡ����ؼ����������
     * 
     * @version: 1.00
     * @history: 2013-1-9 ����11:27:02 [created]
     * @author jiarong Tan
     * @param len
     *            ����������ӳ���
     * @param params
     *            ��չ����
     * @return
     * @throws Exception
     * @see
     */
    public String getRandom(int len, Map<String, String> params) throws Exception;
}
