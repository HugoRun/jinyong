package com.ls.ben.vo.cooperate.dangle;

/**
 * @author ls ����:����ͨ��֤��Ϣ Jan 10, 2009
 */
public class PassportVO
{
	/** ID */
	private int id;
	/** �����û�id */
	private String userId;
	/** �����û����� */
	private String userName;
	/** �����û���ƽ̨��״̬��1��ʾע���û���2��ʾע���û� */
	private int userState;
	/** ��Ϸ����˺�id */
	private int uPk;
	private int channelId;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public int getUserState()
	{
		return userState;
	}

	public void setUserState(int userState)
	{
		this.userState = userState;
	}

	public int getUPk()
	{
		return uPk;
	}

	public void setUPk(int pk)
	{
		uPk = pk;
	}

	public int getChannelId()
	{
		return channelId;
	}

	public void setChannelId(int channelId)
	{
		this.channelId = channelId;
	}
}
