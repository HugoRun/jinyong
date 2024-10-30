package com.ls.model.organize.faction;

import java.util.Date;

import com.ls.pub.util.DateUtil;
import com.ls.web.service.faction.FactionService;

/**
 * @author ls
 * ������ļ��Ϣ
 */
public class FactionRecruit
{
	private static FactionRecruit fRecruit = null;
	
	private String content;//����
	private int fId;//����id
	private Date createTime;//����ʱ��
	
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
	 * ��ļ
	 * @param fId		����id
	 * @param rContent		��ļ����
	 */
	public void recruit(int fId,String rContent)
	{
		Faction faciton = FactionService.getById(fId);
		if( faciton!=null )
		{
			this.content = faciton.getGrade()+"������"+faciton.getFullName()+"��ļ��"+rContent;
			this.fId = fId;
			this.createTime = new Date();
		}
	}

	/**
	 * �Ƿ��а�����ļ����
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
