/**
 * 
 */
package com.ben.vo.checkpcrequest;

/**
 * @author ��ƾ� ����PC�����û�����
 * 
 */
public class CheckPcRequestVO
{
	/** ������ID */
	private int ipPk;
	/** IP��ʼ */
	private String ipBegin;
	/** IP���� */
	private String ipEnd;

	public int getIpPk()
	{
		return ipPk;
	}

	public void setIpPk(int ipPk)
	{
		this.ipPk = ipPk;
	}

	public String getIpBegin()
	{
		return ipBegin;
	}

	public void setIpBegin(String ipBegin)
	{
		this.ipBegin = ipBegin;
	}

	public String getIpEnd()
	{
		return ipEnd;
	}

	public void setIpEnd(String ipEnd)
	{
		this.ipEnd = ipEnd;
	}

}
