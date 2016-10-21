package cn.com.bestpay.portal.SecurityPassword.impl;


import cn.com.bestpay.portal.SecurityPassword.PasswordInf;
import cn.com.bestpay.portal.common.service.PwdManager;
import cn.com.bestpay.portal.common.utils.Charset;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ��ȫ����ؼ�
 * File                 : PasswordManger.java
 * Copy Right           : www.tisson.cn
 * Project              : bppf
 * JDK version used     : JDK 1.5
 * Comments             : 
 * Version              : 1.00
 * Modification history : 2013-1-16 ����11:59:17 [created]
 * Author               : XiuWang Lan
 * Email                :
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
    * @param id �ؼ�ID
    * @param clazz �ؼ���ʽ
    * @param name
    * @param rdName
    * @param rd
    * @param params ��չ���� 
    * 
    * ��չ���� (�����ʾ��(ȡֵ[0,1]),staffId(ȡֵ${sessionScope.staffId}]))
    * �����ʾ�� keyΪpwdmark
    * �����ʾ��ȡ0��ʾʹ�ÿؼ�getStaffRandPwd����������
    * �����ʾ��ȡ1��ʾʹ�ÿؼ�getStaffPwd����������
    * staffId��keyΪstaffId
    */
	public String writePWDInput(String id, String clazz, String name,
                                String rdName, String sessionKey, Map<String, String> params, HttpServletRequest request) throws Exception {
		String ctrStr="";
		if(checkparams(params)){
			log.error("��չ����Ϊ��,����ؼ�����ʧ��");
			throw new Exception("��չ����Ϊ��,����ؼ�����ʧ��");
			
		}
		String pwdmark=params.get("pwdmark");
		String staffId=params.get("staffId");
		if(Charset.isEmpty(pwdmark)){
			log.error("����ؼ������ʾ��Ϊ��,����ؼ�����ʧ��");
			throw new Exception("����ؼ������ʾ��Ϊ��,����ؼ�����ʧ��");
		}
		else if(!checkPwd(pwdmark)){
			log.error("����ؼ������ʾ������ȷ,����ؼ�����ʧ��");
			throw new Exception("����ؼ������ʾ������ȷ,����ؼ�����ʧ��");
		}
		 ctrStr=" <object id=\""+id+"\" class=\""+clazz+"\"  classid=\"clsid:9FF161C1-6BE7-43B4-8E81-B7550212FA79\"" +
		               " codebase=\"./bppf/activeX/bppf_sc.CAB#version=1,0,0,1\"></object>"+
		               "<input type=\"hidden\" name=\""+name+"\" id=\"hiden-"+id+"\" class=\""+clazz+"-hidpwd\" objId=\""+id+"\"/>"+
		               "<input type=\"hidden\" id=\"staff_"+id+"\" value=\""+staffId+"\" />"+
		               "<input type=\"hidden\" id=\"pwd_"+id+"\"  value=\""+pwdmark+"\"/>";
		log.info("����ؼ�:"+ctrStr);
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
