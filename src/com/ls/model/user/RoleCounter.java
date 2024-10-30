package com.ls.model.user;

/**
 * @author ls
 * 角色相关计数器(各种不需要保存数据库的计数器)
 */
public class RoleCounter
{
	private int killNpcNum = 0;//杀死至少比自己等级大的普通npc的数量
	
	/**
	 * 增加杀死npc的数量
	 * @param addNum
	 */
	public void addKillNpcNum()
	{
		killNpcNum++;
	}

	public int getKillNpcNum()
	{
		return killNpcNum;
	}
	
}
