package com.ls.model.organize.faction;

import com.ls.model.organize.faction.game.FGameBuild;
import com.ls.web.service.faction.FBuildService;

/**
 * @author ls
 * ���ɽ���
 */
public class FBuild
{
	private int fId;//����id
	private int bId;//����id
	
	
	/**
	 * ��ϸ��Ϣ
	 * @return
	 */
	public String getDisplay()
	{
		return this.getGameBuild().getDisplay();
	}
	
	/**
	 * ��������Ϣ
	 * @return
	 */
	public String getSimpleDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(").append(this.getGameBuild().getGrade()).append("��,ף��:").append(this.getGameBuild().getBuffName()).append(")");
		return sb.toString();
	}
	
	public String getName()
	{
		return this.getGameBuild().getName();
	}
	
	/**
	 * �Ƿ������
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
	 * �õ����ɽ�����Ϣ
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
