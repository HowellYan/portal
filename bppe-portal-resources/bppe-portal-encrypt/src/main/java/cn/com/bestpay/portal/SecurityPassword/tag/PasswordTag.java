package cn.com.bestpay.portal.SecurityPassword.tag;


import cn.com.bestpay.portal.SecurityPassword.impl.Password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PasswordTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	/**
	 * ����ؼ���ID
	 */
	private String id;
	/**
	 * ����ؼ�������������class,��������������ܺ��ֵ,�������ؼ�,���ֵ������ͬ
	 */
	private String clazz;
	/**
	 * ��������������,������̨ȡ������ֵ
	 */
	private String name;
	/**
	 * �����������������,������̨ȡֵ
	 */
	private String rdName;
	/**
	 * session�洢������ӵ�KEY
	 */
	private String sessionKey;
	/**
	 * �Ƿ��sessionȡ���������ֵ(����sessionKey)
	 * true ��ʾ��sessionȡ false ���������
	 */
	private boolean rdFromSession = false;
	/**
	 * ��չ����,����Զ��Ÿ���
	 */
	private String params;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try{
			rdName = rdFromSession ? null : rdName;
			//String[] _params = Charset.isEmpty(params)? new String[0] : params.split(",");			
			out.print(Password.writePWDInput(id, clazz, name, rdName, sessionKey,rdFromSession, ((HttpServletRequest)pageContext.getRequest()),params));
			out.flush();
		}catch(Exception ex){
			try {
				ex.printStackTrace();
				out.print("����ؼ�����ʧ��");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SKIP_BODY;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRdName(String rdName) {
		this.rdName = rdName;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public void setRdFromSession(boolean rdFromSession) {
		this.rdFromSession = rdFromSession;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
}
