/**
 * 
 */
package com.ben.vo.checkpcrequest;

/**
 * @author 侯浩军 屏蔽PC电脑用户访问
 * 
 */
public class CheckPcRequestVO
{
	/** 黑名单ID */
	private int ipPk;
	/** IP开始 */
	private String ipBegin;
	/** IP结束 */
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
