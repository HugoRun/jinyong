package com.pm.vo.pkelimi;
/**
 * �������ֵ��
 * @author zhangjj
 *
 */
public class PKValueElimiVO
{

	/** �������ֵid */
	private int pkvalue_elimi;
	/** ���˽�ɫid */
	private int pPK;
	/** ���ֵ */
	private int pkvalue;
	/** �Ƿ��ڼ������� */
	private int isPerion;
	/** �������ʱ�� */
	private String lastTime;
	
	
	public int getPkvalue_elimi()
	{
		return pkvalue_elimi;
	}
	public void setPkvalue_elimi(int pkvalue_elimi)
	{
		this.pkvalue_elimi = pkvalue_elimi;
	}
	public int getPPK()
	{
		return pPK;
	}
	public void setPPK(int ppk)
	{
		pPK = ppk;
	}
	public int getPkvalue()
	{
		return pkvalue;
	}
	public void setPkvalue(int pkvalue)
	{
		this.pkvalue = pkvalue;
	}
	public int getIsPerion()
	{
		return isPerion;
	}
	public void setIsPerion(int isPerion)
	{
		this.isPerion = isPerion;
	}
	public String getLastTime()
	{
		return lastTime;
	}
	public void setLastTime(String lastTime)
	{
		this.lastTime = lastTime;
	}
	
}
