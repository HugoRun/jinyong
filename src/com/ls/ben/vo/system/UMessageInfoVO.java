package com.ls.ben.vo.system;

import java.util.Date;

/**
 * @author ls
 * ����:��Ҫ����ҳ���ϵͳ��Ϣ��(u_popup_msg)
 * Mar 6, 2009
 */
public class UMessageInfoVO
{
	private int id;/**����*/
	private int pPk; /**������Ϣ��ҵ�id*/
    private int msgType;/**��Ϣ����*/
    private String msgOperate1="";/**�����ֽڣ��洢����Ϣ��Ҫ�Ĳ�����Ϣ*/
    private String msgOperate2="";/**�����ֽڣ��洢����Ϣ��Ҫ�Ĳ�����Ϣ*/
    private int msgPriority=1;/**��Ϣ�����ȼ�*/
    private Date createTime;/**����ʱ��*/
    private String result;/**����ҳ����ʾ��Ϣ*/
    
    
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
