package cn.com.bestpay.portal.SecurityPassword.impl;

import cn.com.bestpay.portal.SecurityPassword.PasswordInf;
import cn.com.bestpay.portal.common.utils.Charset;

import ocx.AESWithJCE;
import ocx.GetRandom;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 密码卫士控件实现
 * File                 : PassGuardCtrl.java
 * Copy Right           : www.tisson.cn
 * Project              : bppf
 * JDK version used     : JDK 1.5
 * Comments             :
 * Version              : 1.00
 * Modification history : 2013-1-9 上午11:31:27 [created]
 * Author               : jiarong Tan
 * Email                : tanjr@tisson.cn
 *
 */
public class PassGuardCtrl implements PasswordInf {
	private static final Logger log = Logger.getLogger(PassGuardCtrl.class);
	/*
	 * (non-Javadoc)
	 * @see cn.tisson.cmpf.util.pwd.PasswordInf#getRandom(int, java.util.Map)
	 */
	public String getRandom(int len, Map<String, String> params) throws Exception {
		if(0>=len){
			log.error("生成密码随机因子长度为空");
		}
		String mcrypt_key=GetRandom.generateString(len);
		return mcrypt_key;
	}
	/*
	 * (non-Javadoc)
	 * @see cn.tisson.cmpf.util.pwd.PasswordInf#decode(java.lang.String, java.lang.String, java.util.Map)
	 */
	public String decode(String random, String pwdStr, Map<String, String> params) throws Exception {
		String password = null;
		try{
			password = AESWithJCE.getResult(random, pwdStr);
		}catch (Exception e) {
			log.error("密码解密出错",e);
		}
		return password;
	}
	/*
	 * (non-Javadoc)
	 * @see cn.tisson.cmpf.util.pwd.PasswordInf#writePWDInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public String writePWDInput(String id, String clazz, String name, String rdName, String sessionKey, Map<String, String> params, HttpServletRequest request) throws Exception {
	    HttpSession session = request.getSession();
	    String rd = (String) session.getAttribute(sessionKey);
		String target = "PassGuardCtrl"+id.toLowerCase();
		String str = "<script type=\"text/javascript\">var "+target+" = new $.pge({pgeClass:\""+clazz+"\",pgeId: \""+id+"-self\"});" +
				"jQuery(function(){"+target+".pwdSetSk(\""+rd+"\");"+target+".pgInitialize();});"+target+".generate();</script>" +
				"<input type=\"hidden\" name=\""+name+"\" id=\""+id+"\" class=\""+clazz+"\" objId=\""+id+"-self\"/>";
		if(!Charset.isEmpty(rdName)) {
		    session.setAttribute(rdName, rd);
		}

		return str;
	}
	/*
	 * (non-Javadoc)
	 * @see cn.tisson.cmpf.util.pwd.PasswordInf#encode(java.lang.String, java.util.Map)
	 */
	public String encode(String pwd, Map<String, String> params) throws Exception {
		return pwd;
	}
}