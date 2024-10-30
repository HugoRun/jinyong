package com.pm.service.outLine;

import java.util.Date;
import java.util.LinkedHashSet;

import javax.servlet.http.HttpSession;

import com.ben.dao.friend.FriendDAO;
import com.ben.dao.logininfo.LoginInfoDAO;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.dynamic.manual.view.ViewCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.service.group.GroupNotifyService;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.system.UMsgService;
import com.lw.dao.laborage.PlayerLaborageDao;
import com.lw.service.UnchartedRoom.UnchartedRoomService;
import com.lw.service.menpaicontest.MenpaiContestService;
import com.lw.service.player.PlayerStatisticsService;
import com.pm.service.statistics.StatisticsService;
import com.web.jieyi.util.Constant;
import com.web.service.communion.CommunionService;

/**
 * 处理玩家离线时的逻辑
 */
public class OutLineService
{
	/**
	 * 角色离线退出时的清理工作
	 * @param roleInfo
	 */
	public void outLineClear(RoleEntity roleInfo)
	{
		if (roleInfo == null)
		{
			return;
		}

		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		FriendDAO friendDAO = new FriendDAO();
		PartInfoDao partInfoDao = new PartInfoDao();

		AttacckCache attacckCache = new AttacckCache();
		UnchartedRoomService rs = new UnchartedRoomService();
		MenpaiContestService ms = new MenpaiContestService();
		
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		CommunionService communionService = new CommunionService();
		PlayerStatisticsService playerStatisticsService = new PlayerStatisticsService();
		try{
			roleInfo.logout();//退出处理
			
			rs.updateOfflinePlayerUnchartedRoomState(roleInfo);//清楚玩家的秘境信息
			rs.updateOfflinePlayerTianguanState(roleInfo);//清楚玩家的天关信息
			ms.updateOfflinePlayerMenpaiContestState(roleInfo);//删除玩家门派地图信息
			
			clearView(roleInfo);//清除视野信息
			clearMsg(roleInfo);//清除弹出式消息
			attacckCache.clearNpcTempData(p_pk + "");// 清除角色 的npc战斗信息
			groupNotifyService.clareNotify(p_pk);// 清除组队通知
			communionService.clearChatInfos(p_pk);//清除聊天信息

			updatePlayerOnlineTimeByLaborage(roleInfo);//给玩家的在线时间表里加入时间
			playerStatisticsService.updatePlayerOnlineTime(roleInfo);//更新玩家在线时间
			friendDAO.updateFriendOnline(p_pk, 0);// 离线好友处理
		}catch(Exception e)
		{
			
		}
		finally
		{
			//处理玩家门派NPC的情况
			int ppk = Constant.MENPAINPC.get(1);  
			if(roleInfo.getPPk()==ppk){
				Constant.MENPAINPC.put(1, 0);
				roleInfo.getBasicInfo().updateSceneId(210+"");
			}
			partInfoDao.updateLoginState(p_pk + "", 0);// 更新登陆状态
		}
	}

	/**
	 * session销毁时的清理
	 */
	public void destorySessionClear(HttpSession cur_session)
	{
		if (cur_session == null)
		{
			return;
		}
		
		String uPk = (String) cur_session.getAttribute("uPk");
		String pPk = (String) cur_session.getAttribute("pPk");

		if (uPk == null || pPk == null)
		{
			return;
		}
		try
		{
			/**
			 * 角色退出处理,	判断session的值和roleEntity中的session是不是同一个,
			 * 如果是那么销毁
			 * 如果不是,判断roleEntity中的session所对应的pPk是否还存在,如果不存在,那么销毁,否则不销毁.
			 */
			RoleEntity roleInfo = RoleCache.getByPpk(pPk);
			
			if ( roleInfo!= null && roleInfo.isOnline()) 
			{
				HttpSession role_session = roleInfo.getStateInfo().getSession();
				String role_sid  = role_session.getId();
				String cur_sid = cur_session.getId();
				
				if ( !cur_sid.equals(role_sid) ) 
				{
					String pPk2 = (String)role_session.getAttribute("pPk");
					if ( pPk2 != null && !pPk2.equals("") && !pPk2.equals("null")) {
						return ;
					}
				}
			}
			
			LoginInfoDAO daoa = new LoginInfoDAO();
			StatisticsService statService = new StatisticsService();
			LoginService loginService = new LoginService();

			loginService.loginoutRole(pPk);// 角色退出处理
			/*****游乐渠道下线同步****/
			if(GameConfig.getChannelId()==Channel.WANXIANG)
			{
				com.ls.web.action.cooperate.youle.login.LoginService ls=new com.ls.web.action.cooperate.youle.login.LoginService();
				long onlineTime=(cur_session.getLastAccessedTime()-cur_session.getCreationTime())/1000/60;
				ls.synchronousDownLine(cur_session, onlineTime+"");
				ls.synchronousLoginState((String)cur_session.getAttribute("UserAccount"),"0",(String)cur_session.getAttribute("GameNO"));
				ls.synchronousGradeInfo(cur_session, roleInfo.getBasicInfo().getGrade()+"");
			}
			/***电信渠道下线同步**/
			if(GameConfig.getChannelId()==Channel.TELE)
			{
				com.ls.web.action.cooperate.tele.login.LoginService ls=new com.ls.web.action.cooperate.tele.login.LoginService();
				ls.loginOut(cur_session);
			}
			//账号退出处理
			statService.recordOnLineTime(cur_session.getCreationTime(), cur_session.getLastAccessedTime(), uPk, pPk);// 记录玩家账号的在线时间,将其存入数据库中.
			daoa.getloginStateTC("0", uPk);//账号退出时标记为0
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * 清除角色视野
	 * 
	 * @param roleInfo
	 */
	private void clearView(RoleEntity roleInfo)
	{
		ViewCache viewCahce = new ViewCache();
		String view = roleInfo.getStateInfo().getView();
		LinkedHashSet<RoleEntity> role_list = viewCahce.getRolesByView(view);
		role_list.remove(roleInfo);
	}

	/** 给玩家的在线时间表里加入时间 */
	private void updatePlayerOnlineTimeByLaborage(RoleEntity roleInfo)
	{
		PlayerLaborageDao pldao = new PlayerLaborageDao();
		Date date = new Date();
		int laborage_this_time = (int) (date.getTime() - roleInfo
				.getStateInfo().getLoginTime()) / 60000;
		if (laborage_this_time > 0)
		{
			pldao.updatePlayerOnlineTime(roleInfo.getBasicInfo().getPPk(),
					laborage_this_time);
		}
	}

	/**
	 * 清除角色相关消息
	 */
	private void clearMsg(RoleEntity roleInfo)
	{
		int p_pk = roleInfo.getBasicInfo().getPPk();
		UMsgService uMsgService = new UMsgService();

		uMsgService.clear(p_pk);// 清除玩家弹出式消息
	}

}
