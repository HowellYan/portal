package cn.com.bestpay.portal.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * BASE64�ı���ͽ���
 * File                 : BASE64.java
 * Copy Right           : ��Ѷ���ͨ�ż������޹�˾ www.tisson.cn
 * Project              : bppf
 * JDK version used     : JDK 1.6
 * Comments             : 
 * Version              : 1.00
 * Modification history : 2012-3-29 ����02:26:51 [created]
 * Author               : Zhilong Luo ��־��
 * Email                : luozhilong@tisson.cn
 **/
public class BASE64 {
	
	
    /**
     * BASE64�ı���
     * @version: 1.00
     * @history: 2012-3-29 ����02:28:04 [created]
     * @author Zhilong Luo ��־��
     * @param str
     * @return
     * @see
     */
    public static String encode(String str){
    	// У�������Ƿ�Ϊ��
    	if(str == null) {
    		return null;
    	}
    	// ����BASE64����
    	return new BASE64Encoder().encode(str.getBytes());
    }  
  

    /**
     * BASE64�Ľ���
     * @version: 1.00
     * @history: 2012-3-29 ����02:32:39 [created]
     * @author Zhilong Luo ��־��
     * @param str
     * @return
     * @see
     */
    public static String decode(String str){
    	// У�������Ƿ�Ϊ��
    	if(str == null) {
    		return null;
    	} 
    	// ����BASE64����
    	BASE64Decoder decoder = new BASE64Decoder();
	    try {  
	        byte[] b = decoder.decodeBuffer(str); 
	        return new String(b);
	    } catch (Exception e) {
	        return null; 
	    }  
    }  

	/**
	 * 
	 * @version: 1.00
	 * @history: 2012-3-29 ����02:24:42 [created]
	 * @author Zhilong Luo ��־��
	 * @param args
	 * @see
	 */
	public static void main(String[] args) {
		String s = BASE64.encode("CUSTCODE=a1@a1.com&DETAILS=1^20120404104949377180^120404006890809^20120404051824^00000000&ENCODETYPE=1&ORDERAMOUNT=10000&TRANDATE=20120404051824");
		String s1 = BASE64.decode(s);
		System.out.println(BASE64.encode("CUSTCODE=a1@a1.com&DETAILS=1^20120404104949377180^120404006890809^20120404051824^00000000&ENCODETYPE=1&ORDERAMOUNT=10000&TRANDATE=20120404051824"));
		System.out.println(s.equals("Q1VTVENPREU9YTFAYTEuY29tJkRFVEFJTFM9MV4yMDEyMDQwNDEwNDk0OTM3NzE4MF4xMjA0MDQwMDY4OTA4MDleMjAxMjA0MDQwNTE4MjReMDAwMDAwMDAmRU5DT0RFVFlQRT0xJk9SREVSQU1PVU5UPTEwMDAwJlRSQU5EQVRFPTIwMTIwNDA0MDUxODI0"));
		System.out.println(BASE64.decode(s));
		System.out.println(s1.equals("CUSTCODE=a1@a1.com&DETAILS=1^20120404104949377180^120404006890809^20120404051824^00000000&ENCODETYPE=1&ORDERAMOUNT=10000&TRANDATE=20120404051824"));
	}

}
