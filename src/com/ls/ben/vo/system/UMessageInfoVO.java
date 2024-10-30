package com.ls.ben.vo.system;

import java.util.Date;

/**
 * @author ls
 * 功能:需要弹出页面的系统消息表(u_popup_msg)
 * Mar 6, 2009
 */
public class UMessageInfoVO
{
	private int id;/**主键*/
	private int pPk; /**接受消息玩家的id*/
    private int msgType;/**消息类型*/
    private String msgOperate1="";/**功能字节，存储该消息需要的参数信息*/
    private String msgOperate2="";/**功能字节，存储该消息需要的参数信息*/
    private int msgPriority=1;/**消息的优先级*/
    private Date createTime;/**创建时间*/
    private String result;/**返回页面提示消息*/
    
    
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getPPk()
	{
		return pPk;
	}
	public void setPPk(int pk)
	{
		pPk = pk;
	}
	public int getMsgType()
	{
		return msgType;
	}
	public void setMsgType(int msgType)
	{
		this.msgType = msgType;
	}
	public String getMsgOperate1()
	{
		return msgOperate1;
	}
	public void setMsgOperate1(String msgOperate1)
	{
		this.msgOperate1 = msgOperate1;
	}
	public int getMsgPriority()
	{
		return msgPriority;
	}
	public void setMsgPriority(int msgPriority)
	{
		this.msgPriority = msgPriority;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public String getMsgOperate2()
	{
		return msgOperate2;
	}
	public void setMsgOperate2(String msgOperate2)
	{
		this.msgOperate2 = msgOperate2;
	}
}
