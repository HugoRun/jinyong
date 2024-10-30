package com.ls.ben.vo.cooperate.dangle;

/**
 * @author ls 功能:当乐通行证信息 Jan 10, 2009
 */
public class PassportVO
{
	/** ID */
	private int id;
	/** 当乐用户id */
	private String userId;
	/** 当乐用户名称 */
	private String userName;
	/** 当乐用户在平台的状态，1表示注册用户，2表示注销用户 */
	private int userState;
	/** 游戏玩家账号id */
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
