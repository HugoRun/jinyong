package com.pm.vo.passsecond;

public class SecondPassVO
{
	/**���������:����*/
	public int passId; 
	/**  ��ɫid */
	public int uPk;				
	/**  ��ɫ��������  */	
	public String secondPass;
	/**  ��ɫ�״��޸Ķ����������ʱ��   */
	public String passFirstTime;				
	/**   ��ɫ�ڶ����޸Ķ����������ʱ�� **/
	public String passSecondTime;		
	/**   ��ɫ�������޸Ķ����������ʱ�� **/
	public String passThirdTime;		
	/**  �����������δ����־ 1Ϊ��ֹʹ�ö�������, 0Ϊ����ʹ��  */	
	public int passWrongFlag;									
	/**  �������������ʼ��Ƿ��Ѿ����͹� */
	public int passMailSend;
	
	
	public int getPassId()
	{
		return passId;
	}
	public void setPassId(int passId)
	{
		this.passId = passId;
	}
	public int getUPk()
	{
		return uPk;
	}
	public void setUPk(int pk)
	{
		uPk = pk;
	}
	public String getSecondPass()
	{
		return secondPass;
	}
	public void setSecondPass(String secondPass)
	{
		this.secondPass = secondPass;
	}
	public String getPassFirstTime()
	{
		return passFirstTime;
	}
	public void setPassFirstTime(String passFirstTime)
	{
		this.passFirstTime = passFirstTime;
	}
	public String getPassSecondTime()
	{
		return passSecondTime;
	}
	public void setPassSecondTime(String passSecondTime)
	{
		this.passSecondTime = passSecondTime;
	}
	public int getPassWrongFlag()
	{
		return passWrongFlag;
	}
	public void setPassWrongFlag(int passWrongFlag)
	{
		this.passWrongFlag = passWrongFlag;
	}
	public int getPassMailSend()
	{
		return passMailSend;
	}
	public void setPassMailSend(int passMailSend)
	{
		this.passMailSend = passMailSend;
	}
	public String getPassThirdTime()
	{
		return passThirdTime;
	}
	public void setPassThirdTime(String passThirdTime)
	{
		this.passThirdTime = passThirdTime;
	}
	
	
	
	
	
}
