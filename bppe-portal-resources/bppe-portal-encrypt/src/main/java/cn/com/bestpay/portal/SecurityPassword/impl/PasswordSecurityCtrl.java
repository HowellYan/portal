package cn.com.bestpay.portal.SecurityPassword.impl;


import cn.com.bestpay.portal.SecurityPassword.PasswordInf;
import cn.com.bestpay.portal.common.service.PwdManager;
import cn.com.bestpay.portal.common.utils.Charset;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 安全密码控件
 *
 */
public class PasswordSecurityCtrl implements PasswordInf {

	private static final Logger log = Logger.getLogger(PasswordSecurityCtrl.class);

	public String decode(String random, String pwd, Map<String, String> params)
			throws Exception {
		return pwd;
	}

	public String getRandom(int len, Map<String, String> params) throws Exception {
		return new PwdManager().createSCRand();
	}
	/**
	 * @param id 控件ID
	 * @param clazz 控件样式
	 * @param name
	 * @param rdName
	 * @param sessionKey
	 * @param params 扩展属性
	 *
	 * 扩展属性 (密码标示符(取值[0,1]),staffId(取值${sessionScope.staffId}]))
	 * 密码标示符 key为pwdmark
	 * 密码标示符取0表示使用控件getStaffRandPwd方法来加密
	 * 密码标示符取1表示使用控件getStaffPwd方法来加密
	 * staffId的key为staffId
	 */
	public String writePWDInput(String id, String clazz, String name,
								String rdName, String sessionKey, Map<String, String> params, HttpServletRequest request) throws Exception {
		String ctrStr="";
		if(checkparams(params)){
			log.error("扩展属性为空,密码控件生成失败");
			throw new Exception("扩展属性为空,密码控件生成失败");

		}
		String pwdmark=params.get("pwdmark");
		String staffId=params.get("staffId");
		if(Charset.isEmpty(pwdmark)){
			log.error("密码控件密码标示符为空,密码控件生成失败");
			throw new Exception("密码控件密码标示符为空,密码控件生成失败");
		}
		else if(!checkPwd(pwdmark)){
			log.error("密码控件密码标示符不正确,密码控件生成失败");
			throw new Exception("密码控件密码标示符不正确,密码控件生成失败");
		}
		ctrStr=" <object id=\""+id+"\" class=\""+clazz+"\"  classid=\"clsid:9FF161C1-6BE7-43B4-8E81-B7550212FA79\"" +
				" codebase=\"./bppf/activeX/bppf_sc.CAB#version=1,0,0,1\"></object>"+
				"<input type=\"hidden\" name=\""+name+"\" id=\"hiden-"+id+"\" class=\""+clazz+"-hidpwd\" objId=\""+id+"\"/>"+
				"<input type=\"hidden\" id=\"staff_"+id+"\" value=\""+staffId+"\" />"+
				"<input type=\"hidden\" id=\"pwd_"+id+"\"  value=\""+pwdmark+"\"/>";
		log.info("密码控件:"+ctrStr);
		return ctrStr;
	}

	public String encode(String pwd, Map<String, String> params) throws Exception {
		return pwd;
	}

	private boolean checkPwd(String pwd){
		boolean flag=false;
		String[]  pwdmark={"0","1"};
		for(int i=0;i<pwdmark.length;i++){
			if(pwdmark[i].equals(pwd))
				flag=true;
		}
		return flag;
	}

	private boolean checkparams(Map<String, String> params){
		boolean flag=false;
		if(params==null) flag=true;
		else if(params.size()==0) flag=true;

		return flag;
	}
}
