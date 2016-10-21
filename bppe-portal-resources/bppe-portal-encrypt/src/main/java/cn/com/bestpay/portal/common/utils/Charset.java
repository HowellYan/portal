/**
 * File                 : Charset.java
 * Copy Right           : 天讯瑞达通信技术有限公司
 * Project              : 通用统一平台
 * JDK version used     : JDK 1.5
 * Comments             : 字符编码转换类
 * Version              : 1.01
 * Modification history : 2009-01-13
 * Author               : 
 **/

package cn.com.bestpay.portal.common.utils;


public class Charset {

	/**
	 * 判断字符串是否为空
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
