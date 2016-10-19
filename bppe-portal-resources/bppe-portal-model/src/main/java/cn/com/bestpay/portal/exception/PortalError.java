/**
 * 
 */
package cn.com.bestpay.portal.exception;

/**
 * @author aaronMing
 * 
 */
public enum PortalError {
	Logout_msg("999999", "请重新登陆！");

	private String code;
	private String reason;

	PortalError(String code, String reason) {
		this.code = code;
		this.reason = reason;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
