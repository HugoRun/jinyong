package com.pm.vo.sysInfo;

public class SystemInfoVO
{

	/** systemInfo table's id **/
	private int sysInfoId;
	/** person's id **/
	private int pPk;
	/** systemInfo's type **/
	private int infoType;
	/** systemInfo table's content **/
	private String systemInfo;
	/** time when this info create   **/
	private String createTime;
	/** time when appear in the player eye */
	private String appearTime;
	
	
	
	public String getAppearTime()
	{
		return appearTime;
	}
	public void setAppearTime(String appearTime)
	{
		this.appearTime = appearTime;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public int getSysInfoId()
	{
		return sysInfoId;
	}
	public void setSysInfoId(int sysInfoId)
	{
		this.sysInfoId = sysInfoId;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getInfoType()
	{
		return infoType;
	}
	public void setInfoType(int infoType)
	{
		this.infoType = infoType;
	}
	public String getSystemInfo()
	{
		return systemInfo;
	}
	public void setSystemInfo(String systemInfo)
	{
		this.systemInfo = systemInfo;
	}
	
}
