package com.ls.model.organize.faction;

import com.ls.model.organize.faction.game.FGameBuild;
import com.ls.web.service.faction.FBuildService;

/**
 * @author ls
 * 帮派建筑
 */
public class FBuild
{
	private int fId;//帮派id
	private int bId;//建筑id
	
	
	/**
	 * 详细信息
	 * @return
	 */
	public String getDisplay()
	{
		return this.getGameBuild().getDisplay();
	}
	
	/**
	 * 简单描述信息
	 * @return
	 */
	public String getSimpleDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(").append(this.getGameBuild().getGrade()).append("级,祝福:").append(this.getGameBuild().getBuffName()).append(")");
		return sb.toString();
	}
	
	public String getName()
	{
		return this.getGameBuild().getName();
	}
	
	/**
	 * 是否可升级
	 * @return
	 */
	public boolean getIsUpgraded()
	{
		FGameBuild next_build = getGameBuild().getNextGradeBuild();
		if( next_build!=null )
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 得到帮派建筑信息
	 * @return
	 */
	public FGameBuild getGameBuild()
	{
		return FBuildService.getGameBuildById(bId);
	}

	public int getFId()
	{
		return fId;
	}

	public void setFId(int id)
	{
		fId = id;
	}

	public int getBId()
	{
		return bId;
	}

	public void setBId(int id)
	{
		bId = id;
	}
}
