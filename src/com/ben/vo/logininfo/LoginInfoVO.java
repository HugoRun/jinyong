/**
 * 
 */
package com.ben.vo.logininfo;

/**
 * @author Administrator
 * 
 */
public class LoginInfoVO {
	/* 创建人员信息ID */
	private int uPk;
	/** 管理员登录名 */
	private String uName;
	/** 管理员登录密码 */
	private String uPaw;
	/** 登陆状态 1为登陆 0 为未登陆 */
	private int loginState;
	/** 创建时间 */
	private String createTime;
	/**父渠道*/
	private String super_qudao;
	/**子渠道*/
	private String qudao;
	
	public String getSuper_qudao()
	{
		return super_qudao;
	}

	public void setSuper_qudao(String super_qudao)
	{
		this.super_qudao = super_qudao;
	}

	public String getQudao()
	{
		return qudao;
	}

	public void setQudao(String qudao)
	{
		this.qudao = qudao;
	}

	public int getLoginState() {
		return loginState;
	}

	public void setLoginState(int loginState) {
		this.loginState = loginState;
	}

	public int getUPk() {
		return uPk;
	}

	public void setUPk(int pk) {
		uPk = pk;
	}

	public String getUName() {
		return uName;
	}

	public void setUName(String name) {
		uName = name;
	}

	public String getUPaw() {
		return uPaw;
	}

	public void setUPaw(String paw) {
		uPaw = paw;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
