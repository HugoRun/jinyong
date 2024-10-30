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
 * �����������ʱ���߼�
 */
public class OutLineService
{
	/**
	 * ��ɫ�����˳�ʱ����������
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
			roleInfo.logout();//�˳�����
			
			rs.updateOfflinePlayerUnchartedRoomState(roleInfo);//�����ҵ��ؾ���Ϣ
			rs.updateOfflinePlayerTianguanState(roleInfo);//�����ҵ������Ϣ
			ms.updateOfflinePlayerMenpaiContestState(roleInfo);//ɾ��������ɵ�ͼ��Ϣ
			
			clearView(roleInfo);//�����Ұ��Ϣ
			clearMsg(roleInfo);//�������ʽ��Ϣ
			attacckCache.clearNpcTempData(p_pk + "");// �����ɫ ��npcս����Ϣ
			groupNotifyService.clareNotify(p_pk);// ������֪ͨ
			communionService.clearChatInfos(p_pk);//���������Ϣ

			updatePlayerOnlineTimeByLaborage(roleInfo);//����ҵ�����ʱ��������ʱ��
			playerStatisticsService.updatePlayerOnlineTime(roleInfo);//�����������ʱ��
			friendDAO.updateFriendOnline(p_pk, 0);// ���ߺ��Ѵ���
		}catch(Exception e)
		{
			
		}
		finally
		{
			//�����������NPC�����
			int ppk = Constant.MENPAINPC.get(1);  
			if(roleInfo.getPPk()==ppk){
				Constant.MENPAINPC.put(1, 0);
				roleInfo.getBasicInfo().updateSceneId(210+"");
			}
			partInfoDao.updateLoginState(p_pk + "", 0);// ���µ�½״̬
		}
	}

	/**
	 * session����ʱ������
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
			 * ��ɫ�˳�����,	�ж�session��ֵ��roleEntity�е�session�ǲ���ͬһ��,
			 * �������ô����
			 * �������,�ж�roleEntity�е�session����Ӧ��pPk�Ƿ񻹴���,���������,��ô����,��������.
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

			loginService.loginoutRole(pPk);// ��ɫ�˳�����
			/*****������������ͬ��****/
			if(GameConfig.getChannelId()==Channel.WANXIANG)
			{
				com.ls.web.action.cooperate.youle.login.LoginService ls=new com.ls.web.action.cooperate.youle.login.LoginService();
				long onlineTime=(cur_session.getLastAccessedTime()-cur_session.getCreationTime())/1000/60;
				ls.synchronousDownLine(cur_session, onlineTime+"");
				ls.synchronousLoginState((String)cur_session.getAttribute("UserAccount"),"0",(String)cur_session.getAttribute("GameNO"));
				ls.synchronousGradeInfo(cur_session, roleInfo.getBasicInfo().getGrade()+"");
			}
			/***������������ͬ��**/
			if(GameConfig.getChannelId()==Channel.TELE)
			{
				com.ls.web.action.cooperate.tele.login.LoginService ls=new com.ls.web.action.cooperate.tele.login.LoginService();
				ls.loginOut(cur_session);
			}
			//�˺��˳�����
			statService.recordOnLineTime(cur_session.getCreationTime(), cur_session.getLastAccessedTime(), uPk, pPk);// ��¼����˺ŵ�����ʱ��,����������ݿ���.
			daoa.getloginStateTC("0", uPk);//�˺��˳�ʱ���Ϊ0
		}
		catch (Exception e)
		{
		}
	}
	
	/**
	 * �����ɫ��Ұ
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

	/** ����ҵ�����ʱ��������ʱ�� */
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
	 * �����ɫ�����Ϣ
	 */
	private void clearMsg(RoleEntity roleInfo)
	{
		int p_pk = roleInfo.getBasicInfo().getPPk();
		UMsgService uMsgService = new UMsgService();

		uMsgService.clear(p_pk);// �����ҵ���ʽ��Ϣ
	}

}