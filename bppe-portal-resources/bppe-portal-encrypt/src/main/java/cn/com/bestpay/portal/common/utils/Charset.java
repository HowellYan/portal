/**
 * File                 : Charset.java
 * Copy Right           : ��Ѷ���ͨ�ż������޹�˾
 * Project              : ͨ��ͳһƽ̨
 * JDK version used     : JDK 1.5
 * Comments             : �ַ�����ת����
 * Version              : 1.01
 * Modification history : 2009-01-13
 * Author               : 
 **/

package cn.com.bestpay.portal.common.utils;


public class Charset {

	/**
	 * �ж��ַ����Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

}
