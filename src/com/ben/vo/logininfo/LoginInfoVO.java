/**
 * 
 */
package com.ben.vo.logininfo;

/**
 * @author Administrator
 * 
 */
public class LoginInfoVO {
	/* ������Ա��ϢID */
	private int uPk;
	/** ����Ա��¼�� */
	private String uName;
	/** ����Ա��¼���� */
	private String uPaw;
	/** ��½״̬ 1Ϊ��½ 0 Ϊδ��½ */
	private int loginState;
	/** ����ʱ�� */
	private String createTime;
	/**������*/
	private String super_qudao;
	/**������*/
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
