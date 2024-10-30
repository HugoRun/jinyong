package com.ls.model.group;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.group.GroupNotifyService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * 功能：组队模型
 * @author ls
 * Aug 10, 2009
 * 3:36:33 PM
 */
public class GroupModel
{
	Logger logger = Logger.getLogger("log.service");
	public static final int MEMBER_MAX_NUM = 5;
	
	private LinkedHashMap<String,RoleEntity> group_members = new LinkedHashMap<String,RoleEntity>(MEMBER_MAX_NUM);
	
	private int captian_id = -1;
	
	/**
	 * 构造一个队伍
	 * @param caption
	 * @param member
	 */
	public void createGroup(RoleEntity caption_info,RoleEntity member_info)
	{
		if( caption_info==null || member_info==null )
		{
			return;
		}
		
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		
		int captian_id = caption_info.getBasicInfo().getPPk();
		int member_id = member_info.getBasicInfo().getPPk();
		
		group_members.put(captian_id+"", caption_info);
		group_members.put(member_id+"", member_info);
		
		caption_info.getStateInfo().setGroupInfo(this);
		member_info.getStateInfo().setGroupInfo(this);
		
		this.captian_id = captian_id;
		
		// 给队长发出通知，告之已组建队伍
		groupNotifyService.addNotify(member_id, captian_id,GroupNotifyService.GROUPHINT);
		sendAddNewMemberSysInfoToAllMembers(member_id);//新加入成员后给所有成员发送系统消息
		
		GroupService.registerGroupInfo(this);//注册队伍信息
	}
	
	/**
	 * 得到队伍列表
	 */
	public List<RoleEntity> getMemberList()
	{
		ArrayList<RoleEntity> member_list = new ArrayList<RoleEntity>(group_members.values());
		return member_list;
	}
	
	/**
	 * 得到队长id
	 */
	public int getCaptianID()
	{
		return captian_id;
	}
	
	/**
	 * 得到队长信息
	 */
	public RoleEntity getCaptianInfo()
	{
		return getMemberInfo( captian_id );
	}
	
	/**
	 * 得到队伍成员信息
	 */
	public RoleEntity getMemberInfo( int member_id )
	{
		return group_members.get(member_id+"");
	}
	
	/**
	 * 得到成员数量
	 */
	public int getMemberNum()
	{
		return group_members.size();
	}
	
	/**
	 * 添加成员
	 * @param member_info
	 */
	public void addMember(RoleEntity new_member_info)
	{
		if( new_member_info==null || group_members.size()>=MEMBER_MAX_NUM )
		{
			return;
		}
		
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		
		int new_member_info_id = new_member_info.getBasicInfo().getPPk();
		group_members.put(new_member_info_id+"", new_member_info);
		new_member_info.getStateInfo().setGroupInfo(this);
		

		if ( isFull() )//队伍已满
		{
			//队伍已满时清除队长所有的组队通知
			groupNotifyService.clareNotify(captian_id);
		}
		// 玩家A加入队伍，清楚玩家A的所有组队通知
		groupNotifyService.clareNotify(new_member_info_id);
		
		//新加入成员后给所有成员发送系统消息
		sendAddNewMemberSysInfoToAllMembers(new_member_info_id);
	}
	
	/**
	 * 离开队伍
	 * @param member_info
	 */
	public void leaveGroup( int member_id )
	{
		RoleEntity member_info = getMemberInfo(member_id);
		leaveGroup(member_info);
	}
	
	/**
	 * 离开队伍
	 * @param member_info
	 */
	public void leaveGroup( RoleEntity member )
	{
		if( member==null)
		{
			return;
		}
		
		String sysinfo_content = member.getBasicInfo().getName()+"离开队伍";
		//给队伍所有人发送系统消息
		sendSysInfoToAllMembers(sysinfo_content);
		
		if(group_members.size()<=2 )//如果队伍小于两个人就直接解散
		{
			disbandGroup();
		}
		else
		{
			RoomService roomService = new RoomService();
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			
			int scene_id = Integer.parseInt(member.getBasicInfo().getSceneId());
			int member_id = member.getBasicInfo().getPPk();
			
			group_members.remove(member_id+"");
			member.getStateInfo().setGroupInfo(null);
			if( member_id==this.captian_id )//如果队长离开则移交队长
			{
				changeCaption();
			}
			
			if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )//玩家在副本区，弹出消息退出副本
			{
				msgInfo.setMsgType(PopUpMsgType.INSTANCE);
				msgInfo.setPPk(member.getBasicInfo().getPPk());
				uMsgService.sendPopUpMsg(msgInfo);
			}
		}
	}
	
	/**
	 * 移交队长
	 */
	private void changeCaption()
	{
		List<RoleEntity> member_list = getMemberList();
		if( member_list.size()>1 )
		{
			GroupService.cancelGroupInfo(this);//注销组队信息（原队长）
			this.captian_id = member_list.get(0).getBasicInfo().getPPk();
			GroupService.registerGroupInfo(this);//登记组队信息(更换队长)
		}
	}
	
	/**
	 * 解散队伍
	 */
	public void disbandGroup()
	{
		RoomService roomService = new RoomService();
		InstanceService instanceService = new InstanceService();
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO msgInfo = new UMessageInfoVO();
		
		List<RoleEntity> member_list = getMemberList();
		for( RoleEntity member:member_list )
		{
			if( member!=null )
			{
				member.getStateInfo().setGroupInfo(null);
				int scene_id = Integer.parseInt(member.getBasicInfo().getSceneId());
				if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )//如果队伍中有人在副本区域则，弹出消息退出副本
				{
					msgInfo.setMsgType(PopUpMsgType.INSTANCE);
					msgInfo.setPPk(member.getBasicInfo().getPPk());
					uMsgService.sendPopUpMsg(msgInfo);
				}
			}
		}
		//清除队伍的副本小怪的死亡所有记录
		instanceService.clearAllDeadRecord(captian_id);
		GroupService.cancelGroupInfo(this);//注销组队信息
	}
	
	/**
	 * 玩家在副本区域且，有两个或者两个以上的人在副本区域时，的组队效果值
	 * 计算公式：平均等级*(1/玩家1等级+1/玩家2等级+1/玩家3等级。。。。)*20
	 * @param member_num_in_instance      在副本区域的成员数         
	 * @return
	 */
	private int getGroupEffectValueByInstanceArea( List<RoleEntity> member_list_in_instance )
	{
		int groupEffectValue = 0;
		if( member_list_in_instance!=null && member_list_in_instance.size()!=0 )
		{
			int member_num_in_instance = member_list_in_instance.size();
			int grade_total = 0;
			int role_grade = 1;
			double fenshu_total = 0;//(1/玩家1等级+1/玩家2等级+1/玩家3等级。。。。)
			for( RoleEntity member:member_list_in_instance )
			{
				role_grade = member.getBasicInfo().getGrade();
				grade_total += role_grade;
				fenshu_total += 1.0/role_grade;
			}
			
			groupEffectValue = (int)(grade_total/member_num_in_instance*(fenshu_total)*20);
		}
		return groupEffectValue;
	}
	
	/**
	 * 得到副本区域的成员的数量
	 * @return
	 */
	private List<RoleEntity> getMemberListInInstance()
	{
		List<RoleEntity> member_list_in_instance = new ArrayList<RoleEntity>();
		
		RoomService roomService = new RoomService();
		
		List<RoleEntity> member_list = getMemberList();
		for( RoleEntity member:member_list )
		{
			int scene_id = Integer.parseInt(member.getBasicInfo().getSceneId());
			if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )
			{
				member_list_in_instance.add(member);
			}
		}
		return member_list_in_instance;
	}
	
	/**
	 * 普通情况下的组队效果值
	 * @return
	 */
	private int getGroupEffectValueByNormal()
	{
		int groupEffectValue = 0;
		switch (group_members.size())
		{
			case 2:
			{
				// 2人组队
				groupEffectValue = 5;
				break;
			}
			case 3:
			{
				// 3人组队
				groupEffectValue = 10;
				break;
			}
			case 4:
			{
				// 4人组队
				groupEffectValue = 15;
				break;
			}
			case 5:
			{
				// 5人组队
				groupEffectValue = 20;
				break;
			}
		}
		return groupEffectValue;
	}
	
	/**
	 * 根据效果类型得到该类型的效果值
	 */
	public int getGroupEffectValue(RoleEntity role_info)
	{
		if ( role_info == null )
		{
			logger.debug("角色信息为空");
			return 0;
		}
		
		int groupEffectValue = 0;
		
		int p_pk = role_info.getBasicInfo().getPPk();
		int scene_id = Integer.parseInt(role_info.getBasicInfo().getSceneId());
		
		RoomService roomService = new RoomService();
		
		//判断该地点是否有组队限制
		if( roomService.isLimitedByAttribute(scene_id, RoomService.GROUP_EFFECT) )
		{
			return 0;//限制组队无效果
		}
		
		List<RoleEntity> member_list_in_instance = getMemberListInInstance();
		//判断是否是副本组队加成效果,玩家在副本区域且，有两个或者两个以上的人在副本区域时，的组队效果值
		if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) && member_list_in_instance.size()>=2)
		{
			groupEffectValue = getGroupEffectValueByInstanceArea(member_list_in_instance);
		}
		else
		{
			groupEffectValue = getGroupEffectValueByNormal();
		}
		
		
		if(groupEffectValue!=0)
		{
			//加载结婚和结义的组队附加值
			int spicEffect = new MyServiceImpl().addTeamEffect(p_pk);
			groupEffectValue =groupEffectValue+ Math.round((float)((float)groupEffectValue*(float)spicEffect/100));
		}
		return groupEffectValue;
	}
	
	/**
	 * 根据组队效果的显示
	 */
	public String getGroupEffectDisplay(RoleEntity role_info)
	{
		StringBuffer result = new StringBuffer();
		
		int scene_id = Integer.parseInt(role_info.getBasicInfo().getSceneId());
		
		int groupEffectValue = getGroupEffectValue(role_info); 
		
		RoomService roomService = new RoomService();
		
		if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )
		{
			result.append("组队攻击效果增加"+groupEffectValue+"%<br/>");
			result.append("组队防御效果增加"+groupEffectValue+"%");
		}
		else
		{
			result.append("组队攻击效果增加"+groupEffectValue+"%<br/>");
			result.append("组队防御效果增加"+groupEffectValue+"%<br/>");
			result.append("组队经验效果增加"+groupEffectValue+"%");
		}
		
		return result.toString();
	}
	
	/**
	 * 给玩家加载组队效果
	 * @param player
	 */
	public void loadGroupEffect(PartInfoVO player)
	{
		if (player == null)
		{
			logger.info("参数为空");
			return;
		}
		logger.debug("查看用户所在地图是否有效! "+player.getPMap());

		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		
    	int groupEffectPercent = getGroupEffectValue(role_info);
    	player.setGjSubjoin(player.getGjSubjoin() + groupEffectPercent);
    	player.setFySubjoin(player.getFySubjoin() + groupEffectPercent);
	}
	
	/**
	 * 玩家a和玩家b是否在同一个队伍
	 */
	public boolean isCaptian(int p_pk )
	{
		if( p_pk==captian_id )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 判断队伍是否满了
	 */
	public boolean isFull()
	{
		if( group_members.size()>=MEMBER_MAX_NUM)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 发送系统消息给所有成员
	 * @param systeminfo_content    消息内容
	 */
	private void sendSysInfoToAllMembers(String systeminfo_content)
	{
		SystemInfoService systemInfoService = new SystemInfoService();

		List<RoleEntity> members = this.getMemberList();

		if (members != null && members.size() != 0)
		{
			int p_pk = 0;
			for (RoleEntity member : members)
			{
				p_pk = member.getBasicInfo().getPPk();
				systemInfoService.insertSystemInfoBySystem(p_pk,systeminfo_content);		
			}
		}
	}
	
	/**
	 * 新加入成员后给所有成员发送系统消息
	 * @param new_member_id
	 */
	private void sendAddNewMemberSysInfoToAllMembers( int new_member_id )
	{
		SystemInfoService systemInfoService = new SystemInfoService();
		
		List<RoleEntity> members = this.getMemberList();
		
		if (members != null && members.size() != 0)
		{
			RoleEntity new_member_info = getMemberInfo(new_member_id);
			RoleEntity caption_info = getCaptianInfo();
			
			String to_new_member_sysinfo = "您加入了"+caption_info.getBasicInfo().getName()+"的队伍";//给自己发的消息
			String to_other_member_sysinfo = new_member_info.getBasicInfo().getName()+"加入队伍";//给队伍其他成员的消息
			
			int p_pk = 0;
			String systeminfo_content = "";
			for (RoleEntity member : members)
			{
				p_pk = member.getBasicInfo().getPPk();
				if( p_pk==new_member_id )
				{
					
					systeminfo_content = to_new_member_sysinfo;
				}
				else
				{
					systeminfo_content = to_other_member_sysinfo;
				}
				systemInfoService.insertSystemInfoBySystem(p_pk,systeminfo_content);		
			}
		}
	}
	
}
