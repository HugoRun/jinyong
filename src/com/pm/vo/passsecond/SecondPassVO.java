package com.pm.vo.passsecond;

public class SecondPassVO
{
	/**二次密码表:主键*/
	public int passId; 
	/**  角色id */
	public int uPk;				
	/**  角色二级密码  */	
	public String secondPass;
	/**  角色首次修改二级密码错误时间   */
	public String passFirstTime;				
	/**   角色第二次修改二级密码错误时间 **/
	public String passSecondTime;		
	/**   角色第三次修改二级密码错误时间 **/
	public String passThirdTime;		
	/**  二级密码三次错误标志 1为禁止使用二级密码, 0为可以使用  */	
	public int passWrongFlag;									
	/**  二级密码设置邮件是否已经发送过 */
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
