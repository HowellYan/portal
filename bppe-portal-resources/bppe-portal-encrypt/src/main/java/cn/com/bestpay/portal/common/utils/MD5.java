package cn.com.bestpay.portal.common.utils;


import org.apache.log4j.Logger;

import java.security.MessageDigest;

/**
 * @author lyz
 * 
 */
public class MD5 {
	private static Logger logger = Logger.getLogger(MD5.class);
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * ���������һ���ֽ����� �����������ֽ������MD5����ַ���
	 * @author lyz
	 * @param bytesSrc
	 * @return
	 */
	public static String getMD5(byte[] bytesSrc) {
		String result = "";
		// �������ֽ�ת����16���Ʊ�ʾ���ַ�
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(bytesSrc);
			// MD5�ļ�������һ��128 λ�ĳ��������ֽڱ�ʾ��16���ֽ�
			byte tmp[] = md.digest(); 
			// ÿ���ֽ���16���Ʊ�ʾ��ʹ�������ַ�����ʾ��16������Ҫ32���ַ�
			char str[] = new char[16 * 2];
			// ��ʾת������ж�Ӧ���ַ�λ��
			int k = 0; 
			// �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�
			for (int i = 0; i < 16; i++) { 
				// ת���� 16 �����ַ���ת��
				byte byte0 = tmp[i]; // ȡ��i���ֽ�
				// ȡ�ֽ��и� 4 λ������ת����>>> Ϊ�߼����ƣ�������λһ������
				str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
				// ȡ�ֽ��е� 4 λ������ת��
				str[k++] = HEX_DIGITS[byte0 & 0xf];
			}
			// ����Ľ��ת��Ϊ�ַ���
			result = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return result;
	}
	public static String MD5Encode(String origin){
		String resultString = null;
			try {
				resultString=new String(origin);
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString=byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
			}catch (Exception ex){
				logger.error(ex);
			}
			return resultString;
		}
	public static String byteArrayToHexString(byte[] b){
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++){
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	private static String byteToHexString(byte b){
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	private final static String[] hexDigits = {
	      "0", "1", "2", "3", "4", "5", "6", "7", 
	      "8", "9", "a", "b", "c", "d", "e", "f"}; 

	/**
	 * @see
	 * @author lyz
	 * @param args
	 */
	public static void main(String args[]) {
		// ����"a"��MD5���룬Ӧ��Ϊ��0cc175b9c0f1b6a831c399e269772661
		String md5 = MD5.getMD5("".getBytes());
		System.out.println(md5);
	}

}
