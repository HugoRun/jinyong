package com.web.service.expnpcdrop;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ben.vo.expnpcdrop.ExpNpcdropVO;
import com.ls.ben.cache.staticcache.expNpcDrop.ExpNpcDropCache;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.DateUtil;
import com.pm.dao.systemInfo.SysInfoDao;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * 获取执行经验倍数的数据
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class ExpnpcdropService
{
	Logger logger = Logger.getLogger("log.service");
	
	public final static int ACQUIT_FORMATONE = 1;//表现格式 1(16:00:00这种格式)
	public final static int ACQUIT_FORMATWO = 2;//2(2009-01-16 16:37:45这种格式)
	/**
	 * 获取执行经验倍数的数据
	 */
	public int getExpNpcdrop()
	{  
		GameConfig.reloadSysExpMultiple();
		int exp = 1;
		int exp_cimelia = 1;//1是经验掉率 2是掉宝掉率  
		ExpNpcDropCache expNpcDropCache = new ExpNpcDropCache();
		ExpNpcdropVO expNpcdropVO = expNpcDropCache.getExpNpcdrop(exp_cimelia);
		
		if(expNpcdropVO == null){
			exp = 1;
		}else{ 
		if(expNpcdropVO.getAcquitFormat() == ExpnpcdropService.ACQUIT_FORMATONE){//表现格式 1(16:00:00这种格式)
			Date nowTime = new Date();
			Date begin = DateUtil.strToTime(expNpcdropVO.getBeginTime()); 
			Date end = DateUtil.strToTime(expNpcdropVO.getEndTime());
			if( nowTime.after(begin) && nowTime.before(end) )
			{
				exp = expNpcdropVO.getEnMultiple();
			}
		}else if(expNpcdropVO.getAcquitFormat() == ExpnpcdropService.ACQUIT_FORMATWO){//2(2009-01-16 16:37:45这种格式)
			Date nowTime = new Date();
			Date begin = DateUtil.strToDate(expNpcdropVO.getBeginTime());
			Date end = DateUtil.strToDate(expNpcdropVO.getEndTime()); 
			if (nowTime.after(begin) && nowTime.before(end))
			{
				exp = expNpcdropVO.getEnMultiple();
			}
		}
		}
		return exp;
	}
	
	/**
	 * 获取执行掉宝倍数的数据
	 */
	public int getCimeliaNpcdrop()
	{ 
		GameConfig.reloadSysDropMultiple();
		int exp = 1;
		int exp_cimelia = 2;//1是经验掉率 2是掉宝掉率  
		ExpNpcDropCache expNpcDropCache = new ExpNpcDropCache();
		ExpNpcdropVO expNpcdropVO = expNpcDropCache.getExpNpcdrop(exp_cimelia);
		
		if(expNpcdropVO == null){
			exp = 1;
		}else{
		if(expNpcdropVO.getAcquitFormat() == ExpnpcdropService.ACQUIT_FORMATONE){//表现格式 1(16:00:00这种格式)
			Date nowTime = new Date();
			Date begin = DateUtil.strToTime(expNpcdropVO.getBeginTime()); 
			Date end = DateUtil.strToTime(expNpcdropVO.getEndTime());
			if( nowTime.after(begin) && nowTime.before(end) )
			{
				exp = expNpcdropVO.getEnCimelia();
			}
		}else if(expNpcdropVO.getAcquitFormat() == ExpnpcdropService.ACQUIT_FORMATWO){//2(2009-01-16 16:37:45这种格式)
			Date nowTime = new Date();
			Date begin = DateUtil.strToDate(expNpcdropVO.getBeginTime());
			Date end = DateUtil.strToDate(expNpcdropVO.getEndTime()); 
			if (nowTime.after(begin) && nowTime.before(end))
			{
				exp = expNpcdropVO.getEnCimelia();
			}
		}
		}
		return exp;
	} 
	
	
	/**活动开始的时候 发送给玩家系统消息**/
	public void sendSystemMessageByExp(){
		int exp_cimelia = 2;//1是经验掉率 2是掉宝掉率 	
		ExpNpcDropCache expNpcDropCache = new ExpNpcDropCache();
		ExpNpcdropVO expNpcdropVO = expNpcDropCache.getExpNpcdrop(exp_cimelia);
		if(expNpcdropVO != null){
			if(expNpcdropVO.getAcquitFormat() == ExpnpcdropService.ACQUIT_FORMATONE){//表现格式 1(16:00:00这种格式)
					Date dt = new Date();
					DateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
					String dtstr = sf.format(dt);
					Date begin = DateUtil.strToDate(dtstr+" "+expNpcdropVO.getBeginTime());
					Date end = DateUtil.strToDate(dtstr+" "+expNpcdropVO.getEndTime());
					SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
					SystemInfoService systemInfoService = new SystemInfoService();
					for(int i = 0; i<5;i++){
						long time = begin.getTime() + 60000*i;
						long endtime = end.getTime() + 60000*i;
						String begintime_str = sd.format(time);
						String endtime_str = sd.format(endtime);
						systemInfoService.insertSystemInfoBySystem("系统双倍经验(掉宝)祝福已经开始了,"+expNpcdropVO.getEndTime()+"结束!",begintime_str);
						systemInfoService.insertSystemInfoBySystem("系统双倍经验(掉宝)祝福已经结束!", endtime_str);
					}
			}else if(expNpcdropVO.getAcquitFormat() == ExpnpcdropService.ACQUIT_FORMATWO){//2(2009-01-16 16:37:45这种格式)
				Date nowTime = new Date();
				Date beginq = DateUtil.strToDate(expNpcdropVO.getBeginTime()); 
				Date endq = DateUtil.strToDate(expNpcdropVO.getEndTime());
				if( nowTime.after(beginq) && nowTime.before(endq) )
				{
					SystemInfoService systemInfoService = new SystemInfoService();
					Date begin = DateUtil.strToDate(expNpcdropVO.getBeginTime());
					Date end = DateUtil.strToDate(expNpcdropVO.getEndTime());
					SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
					SimpleDateFormat sd1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					for(int i = 0; i<5;i++){
						long stime = begin.getTime() + 60000*i;
						long etime = end.getTime() + 60000*i;
						String begintime_str = sd.format(stime);
						String endtime_str = sd1.format(etime);
						systemInfoService.insertSystemInfoBySystem("系统双倍经验(掉宝)祝福已经开始了,"+expNpcdropVO.getEndTime()+"结束!", begintime_str);
						SysInfoDao sysInfoDao = new SysInfoDao();
						sysInfoDao.insertSysInfo(0,2,"系统双倍经验(掉宝)祝福已经结束!",endtime_str);
					}
				}
			}
		}
	} 
}
