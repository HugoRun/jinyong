package com.pm.service.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ben.jms.JmsUtil;
import com.ben.jms.QSQ;
import com.ben.jms.QudaoDetail;
import com.ben.jms.QudaoMessage;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.login.LoginService;
import com.pm.dao.statistics.StatisticsDao;
import com.pm.dao.systemInfo.SysInfoDao;
/**
 * //在线玩家人数统计
 * @author Administrator
 *
 */
public class GameOnlineNumRecordJob implements Job{   
	
	 public void execute(JobExecutionContext context)  {
		 
		 int nowOnlineNum = LoginService.getOnlineNum();//当前在线人数
		 
			StatisticsDao statDao = new StatisticsDao();
			
			int nowNum = statDao.getNowHourNum(getNowHours(),getToday());
			
			
			if(nowOnlineNum < 0) {
				nowOnlineNum = 0;
			}
			
			if ( nowOnlineNum >= nowNum) {
				statDao.insertPlayerOnlineNumRecord(nowOnlineNum, getNowHours(), getToday());
			}
			
			//删除超过十五分钟的系统消息
			SysInfoDao sysInfoDao = new SysInfoDao();
			sysInfoDao.deleteMoreFifteenMinutes();
			
			//发送jms消息
			if(GameConfig.jmsIsOn()){
				synchronized (JmsUtil.QYDAODETAIL_MAP)
				{
					for(QSQ qsq : JmsUtil.QYDAODETAIL_MAP.keySet()){
						QudaoDetail qd = JmsUtil.QYDAODETAIL_MAP.get(qsq);
						QudaoMessage qm = new QudaoMessage();
						qm.setFenqu(GameConfig.getAreaId());
						qm.setMax_peo(qd.getMax_peo());
						qm.setNow_peo(qd.getNow_peo());
						qm.setQudao(qsq.getQudao());
						qm.setSuper_qudao(qsq.getSuper_qudao());
						JmsUtil.sendQudaoMessage(qm);
						qd.setMax_peo(qd.getNow_peo());
					}
				}
			}
	 }
	 
	 
	 	/**
		 * 获得当前时间的小时格式化表示
		 * @return
		 */
		private static String getNowHours()
		{
			Date dt = new Date();
			//DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			DateFormat sf = new SimpleDateFormat("HH"); 
			String dtstr = sf.format(dt);
			return dtstr;
		}
		
		/**
		 * 获得每天时间的格式化表示
		 * @return
		 */
		private static String getToday()
		{
			Date dt = new Date();
			//DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			DateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
			String dtstr = sf.format(dt);
			return dtstr;
		}

}
