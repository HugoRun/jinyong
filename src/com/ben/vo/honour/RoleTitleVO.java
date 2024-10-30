package com.ben.vo.honour;

import com.ben.dao.honour.RoleTitleDAO;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.model.event.EventManager;
import com.ls.model.event.TitleEvent;
import com.ls.pub.util.DateUtil;

/**
 * 玩家称号信息
 */
public class RoleTitleVO
{
	/** 主键id */
	private int id;
	/** 角色主键 */
	private int pPk;
	/** 称号主键 */
	private int tId;
	/** 是否显示:-1表示不显示，1表示显示 */
	private int isShow=-1;
	/**
	 * 结束时间，0表示无时间限制
	 */
	private long endTime;
	
	public RoleTitleVO()
	{
		
	}
	
	
	/**
	 * 新增加一个称号
	 * @param pPk
	 * @param tId			称号id
	 * @param useTime		使用时间（单位小时）
	 */
	public RoleTitleVO(int pPk,TitleVO newTitle )
	{
		this.pPk = pPk;
		this.tId = newTitle.getId();
		if( newTitle.getUseTime()>0 )
		{
			endTime = System.currentTimeMillis()+newTitle.getUseTime()*DateUtil.HOUR*1000;
		}
	}

	/**
	 * 生成一个定时事件
	 */
	public void createTimerEvent(EventManager eventManager)
	{
		//带有效期的称号生成一个定时事件
		if( eventManager!=null && this.endTime>0)
		{
			eventManager.add(new TitleEvent(this));
		}
	}
	
	/**
	 * 移除该定时事件
	 */
	public void removeTimerEvent(EventManager eventManager)
	{
		//带有效期的称号生成一个定时事件
		if( eventManager!=null && this.endTime>0)
		{
			eventManager.remove(new TitleEvent(this));
		}
	}
	
	/**
	 * 更改该称号是否显示
	 */
	public void updateIsShow()
	{
		this.isShow = -this.isShow;
		RoleTitleDAO roleTitleDAO = new RoleTitleDAO();
		roleTitleDAO.updateIsShow(pPk, tId);
	}
	
	/**
	 * 得到附加的属性字符串
	 * @return
	 */
	public String getAttriStr()
	{
		return this.getTitleInfo().getAttriStr();
	}
	/**
	 * 得到称号描述
	 * @return
	 */
	public String getDes()
	{
		return this.getTitleInfo().getDes();
	}
	/**
	 * 得到称号名称
	 * @return
	 */
	public String getName()
	{
		return this.getTitleInfo().getName();
	}
	/**
	 * 得到称号类型名称
	 * @return
	 */
	public String getTypeName()
	{
		return this.getTitleInfo().getTypeName();
	}
	/**
	 * 得到称号类型
	 * @return
	 */
	public int getType()
	{
		return this.getTitleInfo().getType();
	}
	
	public TitleVO getTitleInfo()
	{
		return TitleCache.getById(tId);
	}
	
	public String getSimpleLeftTimeDes()
	{
		if( this.getEndTime()<=0 )
		{
			return "";
		}
		
		long left_time = this.getEndTime()-System.currentTimeMillis();
		
		int left_time_for_min = (int) (left_time/(DateUtil.MINUTE*1000));//剩余的分钟
		
		String des_str = DateUtil.returnTimeStr(left_time_for_min);
		
		return des_str;
		
	}
	
	public String getLeftTimeDes()
	{
		if( this.getEndTime()<=0 )
		{
			return "";
		}
		
		long left_time = this.getEndTime()-System.currentTimeMillis();
		
		int left_time_for_min = (int) (left_time/(DateUtil.MINUTE*1000));//剩余的分钟
		
		String des_str = DateUtil.returnTimeStr(left_time_for_min);
		
		return "(剩余时间:" + des_str + ")";
		
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
	public int getTId()
	{
		return tId;
	}
	public void setTId(int id)
	{
		tId = id;
	}
	public long getEndTime()
	{
		return endTime;
	}
	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}


	public int getIsShow()
	{
		return isShow;
	}


	public void setIsShow(int isShow)
	{
		this.isShow = isShow;
	}
	
}
