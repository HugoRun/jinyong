package com.ls.web.service.control;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.ben.dao.info.partinfo.TimeControlDao;
import com.ls.ben.vo.info.partinfo.TimeControlVO;
import com.ls.pub.util.DateUtil;

/**
 * 功能:控制表（需要时间或使用次数控制的对象）
 * @author 刘帅
 * Sep 25, 2008  3:22:39 PM
 */
public class TimeControlService
{
	//控制对象类型
	public static final int PROP = 1;
	public static final int MENU = 2;
	
	//道具类型
	public static final int ANOTHERPROP = 3;
	
	//菜单触发任务
	public static final int MENUTOUCHTASK = 4;
	//帮派类型
	public static final int TONG  = 5;
	//技能类型
	public static final int SKILL  = 6;
	//技能类型
	public static final int VIPLABORAGE  = 7;
	// 奖励类型
	public static final int JIANGLI  = 8;
	//BUFF的弹出式消息
	public static final int BUFFPOPMSG  = 9;
	//领取新年礼包或者道具
	public static final int GETNEWYEARPRIZE  = 10;
	//帮派建筑使用(领取图腾buff)
	public static final int F_USE_BUILD  = 11;
	
	
	/**
	 * 更新对象使用信息
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 */
	public void updateControlInfo( int p_pk,int object_id,int object_type )
	{
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		//判断是否有控制信息
		if( timeControl==null )
		{
			timeControlDao.add(p_pk, object_id, object_type);
		}
		else
		{
			if( DateUtil.isSameDay(timeControl.getUseDatetime()) )//上次使用就是当天
			{
				//更新使用状态（使用时间和使用次数）
				timeControlDao.updateUseState(p_pk, object_id, object_type);
			}
			else
			{
				//当天第一次使用
				timeControlDao.updateFirstTimeState(p_pk, object_id, object_type);
			}
		}
	}
	
	
	
	/**
	 * 根据控制信息判断是否可用
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * @param max_use_times   每天最多使用次数
	 * @return
	 */
	public boolean isUseable(int p_pk,int object_id,int object_type,int max_use_times )
	{
		boolean isUseable = true;
		
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		//上次使用就是当天,且使用次数就是最大使用次数
		if( timeControl!=null && DateUtil.isSameDay(timeControl.getUseDatetime()) && max_use_times<=timeControl.getUseTimes() )
		{
			isUseable =false;
		}
		
		return isUseable;
	}
	
	/**
	 * 根据控制信息判断是否可用
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * @param max_use_time   每次使用间隔次数,单位为分钟
	 * @return
	 */
	public boolean isUseableWithNum(int p_pk,int object_id,int object_type,int max_use_time )
	{
		boolean isUseable = true;
		
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		//上次使用就是当天,且使用次数就是最大使用次数
		if( timeControl!=null && DateUtil.isSameDay(timeControl.getUseDatetime())  )
		{
			Date dt = new Date();
			SimpleDateFormat sd = new SimpleDateFormat();
			
			if(dt.getTime() - timeControl.getUseDatetime().getTime() < max_use_time*60*1000 ) {										
				isUseable =false;
			}
		}
		
		return isUseable;
	}
	
	/**
	 * 得到该控制对象已经被用了多少次.
	 */
	public int alreadyUseNumber(int p_pk,int object_id,int object_type) {
		int num = 0;
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		if(timeControl != null && timeControl.getId() != 0) {
			num = timeControl.getUseTimes();
		}
		return num;
	}
	
	/**
	 * 得到该控制对象
	 */
	public TimeControlVO getControlObject(int p_pk,int object_id,int object_type) {
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		return timeControl;
	}
	
	/**
	 * 更新对象使用信息,这个类主要是更改这个方法以适应以下这种需求，即:
	 * 			要求某物品使用后,在一定的时间内有某种作用,
	 * 比如免掉经验道具,使用后在某段时间内人死亡可以不掉经验.
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * @param minutes 以分钟为单位，插入的时间为现在加上time
	 */
	public void updateControlInfoByTime( int p_pk,int object_id,int object_type,int minutes )
	{
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		//判断是否有控制信息
		if( timeControl==null )
		{
			timeControlDao.add(p_pk, object_id, object_type,minutes);
		}
		else
		{
			if( DateUtil.isSameDay(timeControl.getUseDatetime()) )//上次使用就是当天
			{
				//更新使用状态（使用时间和使用次数）
				//timeControlDao.updateUseState(p_pk, object_id, object_type);
				Date dta = timeControl.getUseDatetime();
				//如果数据库中的时间比现在的时间要晚, 就在数据库中的时间基础加上minute，否则就在现在时间上加minute
				if(dta.after(new Date())) {
					
					timeControlDao.updateTimeStateByTime(p_pk, object_id, object_type,minutes);
				} else {
					timeControlDao.updateFirstTimeStateByTime(p_pk, object_id, object_type,minutes);
				}				
			}
			else
			{
				//当天第一次使用
				timeControlDao.updateFirstTimeStateByTime(p_pk, object_id, object_type,minutes);
			}
		}
	}
	
	
	/**
	 * 根据控制信息判断是否可用
	 * @param p_pk
	 * @param object_id
	 * @param object_type
	 * 为false时意为在需要控制, 为true时意为不要控制
	 * @return
	 */
	public boolean isUseableToTimeControl(int p_pk,int object_id,int object_type )
	{
		boolean isUseable = true;
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		Date dt = new Date();
		//当timeControl不为空,且当前时间比数据库里存的时间要早.
		if( timeControl!=null && dt.before(timeControl.getUseDatetime()) )
		{
			isUseable =false;
		}
		return isUseable;
	}
	
	/**判断是否使用过该BUFF**/
	public boolean isUseableByAll(int p_pk,int object_id,int object_type,int max_use_times )
	{
		boolean isUseable = true;
		
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		
		//上次使用就是当天,且使用次数就是最大使用次数
		if( timeControl!=null)
		{
			isUseable =false;
		}
		
		return isUseable;
	}
	
	/**给玩家更新使用*/
	public void updateControlInfoByAll( int p_pk,int object_id,int object_type )
	{
		TimeControlDao timeControlDao = new TimeControlDao();
		TimeControlVO timeControl = timeControlDao.getControlInfo(p_pk, object_id, object_type);
		//判断是否有控制信息
		if( timeControl==null )
		{
			timeControlDao.add(p_pk, object_id, object_type);
		}
	}
}
