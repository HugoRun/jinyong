package com.ls.model.organize.faction;

import java.util.Date;

import com.ls.pub.util.DateUtil;

/**
 * @author ls
 * 帮派公告(一个帮派有多个公告)
 */
public class FNotice
{
	private int id;
	private int fId;
	private String content;
	private Date createTime;
	
	public FNotice(){}
	public FNotice(int f_id,String content)
	{
		this.fId = f_id;
		this.content = content;
		this.createTime = new Date();
	}
	
	/**
	 * 得到时间描述
	 * @return
	 */
	public String getTimeDes()
	{
		return DateUtil.formatDateToStr(createTime, "yyyy-MM-dd");
	}
	
	public int getId()
	{
		return id;
	}
	public int getFId()
	{
		return fId;
	}
	public String getContent()
	{
		return content;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setFId(int id)
	{
		fId = id;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}
