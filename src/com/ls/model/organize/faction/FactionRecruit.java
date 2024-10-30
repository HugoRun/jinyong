package com.ls.model.organize.faction;

import java.util.Date;

import com.ls.pub.util.DateUtil;
import com.ls.web.service.faction.FactionService;

/**
 * @author ls
 * 帮派招募信息
 */
public class FactionRecruit
{
	private static FactionRecruit fRecruit = null;
	
	private String content;//内容
	private int fId;//帮派id
	private Date createTime;//创建时间
	
	private FactionRecruit()
	{
		
	}
	
	public static FactionRecruit getInstance()
	{
		if( fRecruit==null )
		{
			fRecruit = new FactionRecruit();
		}
		return fRecruit;
	}
	
	/**
	 * 招募
	 * @param fId		帮派id
	 * @param rContent		招募内容
	 */
	public void recruit(int fId,String rContent)
	{
		Faction faciton = FactionService.getById(fId);
		if( faciton!=null )
		{
			this.content = faciton.getGrade()+"级氏族"+faciton.getFullName()+"招募："+rContent;
			this.fId = fId;
			this.createTime = new Date();
		}
	}

	/**
	 * 是否有帮派招募内容
	 */
	public boolean getIsRecruit()
	{
		if( createTime!=null && DateUtil.isOverdue(createTime,2*DateUtil.MINUTE)==false )
		{
			return true;
		}
		createTime = null;
		return false;
	}
	
	public String getContent()
	{
		return content;
	}

	public int getFId()
	{
		return fId;
	}
}
