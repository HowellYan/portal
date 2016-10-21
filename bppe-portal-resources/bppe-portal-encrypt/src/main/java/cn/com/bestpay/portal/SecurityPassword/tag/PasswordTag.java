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
	 * 密码控件的ID
	 */
	private String id;
	/**
	 * 密码控件及密码隐藏域class,用来设置密码加密后的值,多个密码控件,这个值必须相同
	 */
	private String clazz;
	/**
	 * 密码隐藏域名字,用来后台取得密码值
	 */
	private String name;
	/**
	 * 随机因子数隐藏域名,用来后台取值
	 */
	private String rdName;
	/**
	 * session存储随机因子的KEY
	 */
	private String sessionKey;
	/**
	 * 是否从session取的随机数的值(根据sessionKey)
	 * true 表示从session取 false 则从新生成
	 */
	private boolean rdFromSession = false;
	/**
	 * 扩展属性,多个以逗号隔开
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
				out.print("密码控件生成失败");
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
