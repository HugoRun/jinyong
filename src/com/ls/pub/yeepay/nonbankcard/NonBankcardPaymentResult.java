package com.ls.pub.yeepay.nonbankcard;
/**
 * �����п�֧�����
 * @author lu.li
 *
 */
public class NonBankcardPaymentResult {
	private String p0_Cmd;			// ҵ������
	private String r1_Code;			// ����������(�ý�����������Ƿ�ɹ���������ʵ�ʿۿ���)
	private String r2_TrxId;		// �ױ�֧��������ˮ��
	private String r6_Order;		// �̻�������
	private String rq_ReturnMsg;	// ������Ϣ
	private String hmac;			// ǩ������
	
	public String getP0_Cmd() {
		return p0_Cmd;
	}
	public void setP0_Cmd(String cmd) {
		p0_Cmd = cmd;
	}
	public String getR1_Code() {
		return r1_Code;
	}
	public void setR1_Code(String code) {
		r1_Code = code;
	}
	public String getR2_TrxId() {
		return r2_TrxId;
	}
	public void setR2_TrxId(String trxId) {
		r2_TrxId = trxId;
	}
	public String getR6_Order() {
		return r6_Order;
	}
	public void setR6_Order(String order) {
		r6_Order = order;
	}
	public String getRq_ReturnMsg() {
		return rq_ReturnMsg;
	}
	public void setRq_ReturnMsg(String rq_ReturnMsg) {
		this.rq_ReturnMsg = rq_ReturnMsg;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
}
