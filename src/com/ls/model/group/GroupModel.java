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
 * ���ܣ����ģ��
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
	 * ����һ������
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
		
		// ���ӳ�����֪ͨ����֮���齨����
		groupNotifyService.addNotify(member_id, captian_id,GroupNotifyService.GROUPHINT);
		sendAddNewMemberSysInfoToAllMembers(member_id);//�¼����Ա������г�Ա����ϵͳ��Ϣ
		
		GroupService.registerGroupInfo(this);//ע�������Ϣ
	}
	
	/**
	 * �õ������б�
	 */
	public List<RoleEntity> getMemberList()
	{
		ArrayList<RoleEntity> member_list = new ArrayList<RoleEntity>(group_members.values());
		return member_list;
	}
	
	/**
	 * �õ��ӳ�id
	 */
	public int getCaptianID()
	{
		return captian_id;
	}
	
	/**
	 * �õ��ӳ���Ϣ
	 */
	public RoleEntity getCaptianInfo()
	{
		return getMemberInfo( captian_id );
	}
	
	/**
	 * �õ������Ա��Ϣ
	 */
	public RoleEntity getMemberInfo( int member_id )
	{
		return group_members.get(member_id+"");
	}
	
	/**
	 * �õ���Ա����
	 */
	public int getMemberNum()
	{
		return group_members.size();
	}
	
	/**
	 * ��ӳ�Ա
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
		

		if ( isFull() )//��������
		{
			//��������ʱ����ӳ����е����֪ͨ
			groupNotifyService.clareNotify(captian_id);
		}
		// ���A������飬������A���������֪ͨ
		groupNotifyService.clareNotify(new_member_info_id);
		
		//�¼����Ա������г�Ա����ϵͳ��Ϣ
		sendAddNewMemberSysInfoToAllMembers(new_member_info_id);
	}
	
	/**
	 * �뿪����
	 * @param member_info
	 */
	public void leaveGroup( int member_id )
	{
		RoleEntity member_info = getMemberInfo(member_id);
		leaveGroup(member_info);
	}
	
	/**
	 * �뿪����
	 * @param member_info
	 */
	public void leaveGroup( RoleEntity member )
	{
		if( member==null)
		{
			return;
		}
		
		String sysinfo_content = member.getBasicInfo().getName()+"�뿪����";
		//�����������˷���ϵͳ��Ϣ
		sendSysInfoToAllMembers(sysinfo_content);
		
		if(group_members.size()<=2 )//�������С�������˾�ֱ�ӽ�ɢ
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
			if( member_id==this.captian_id )//����ӳ��뿪���ƽ��ӳ�
			{
				changeCaption();
			}
			
			if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )//����ڸ�������������Ϣ�˳�����
			{
				msgInfo.setMsgType(PopUpMsgType.INSTANCE);
				msgInfo.setPPk(member.getBasicInfo().getPPk());
				uMsgService.sendPopUpMsg(msgInfo);
			}
		}
	}
	
	/**
	 * �ƽ��ӳ�
	 */
	private void changeCaption()
	{
		List<RoleEntity> member_list = getMemberList();
		if( member_list.size()>1 )
		{
			GroupService.cancelGroupInfo(this);//ע�������Ϣ��ԭ�ӳ���
			this.captian_id = member_list.get(0).getBasicInfo().getPPk();
			GroupService.registerGroupInfo(this);//�Ǽ������Ϣ(�����ӳ�)
		}
	}
	
	/**
	 * ��ɢ����
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
				if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )//��������������ڸ��������򣬵�����Ϣ�˳�����
				{
					msgInfo.setMsgType(PopUpMsgType.INSTANCE);
					msgInfo.setPPk(member.getBasicInfo().getPPk());
					uMsgService.sendPopUpMsg(msgInfo);
				}
			}
		}
		//�������ĸ���С�ֵ��������м�¼
		instanceService.clearAllDeadRecord(captian_id);
		GroupService.cancelGroupInfo(this);//ע�������Ϣ
	}
	
	/**
	 * ����ڸ��������ң������������������ϵ����ڸ�������ʱ�������Ч��ֵ
	 * ���㹫ʽ��ƽ���ȼ�*(1/���1�ȼ�+1/���2�ȼ�+1/���3�ȼ���������)*20
	 * @param member_num_in_instance      �ڸ�������ĳ�Ա��         
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
			double fenshu_total = 0;//(1/���1�ȼ�+1/���2�ȼ�+1/���3�ȼ���������)
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
	 * �õ���������ĳ�Ա������
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
	 * ��ͨ����µ����Ч��ֵ
	 * @return
	 */
	private int getGroupEffectValueByNormal()
	{
		int groupEffectValue = 0;
		switch (group_members.size())
		{
			case 2:
			{
				// 2�����
				groupEffectValue = 5;
				break;
			}
			case 3:
			{
				// 3�����
				groupEffectValue = 10;
				break;
			}
			case 4:
			{
				// 4�����
				groupEffectValue = 15;
				break;
			}
			case 5:
			{
				// 5�����
				groupEffectValue = 20;
				break;
			}
		}
		return groupEffectValue;
	}
	
	/**
	 * ����Ч�����͵õ������͵�Ч��ֵ
	 */
	public int getGroupEffectValue(RoleEntity role_info)
	{
		if ( role_info == null )
		{
			logger.debug("��ɫ��ϢΪ��");
			return 0;
		}
		
		int groupEffectValue = 0;
		
		int p_pk = role_info.getBasicInfo().getPPk();
		int scene_id = Integer.parseInt(role_info.getBasicInfo().getSceneId());
		
		RoomService roomService = new RoomService();
		
		//�жϸõص��Ƿ����������
		if( roomService.isLimitedByAttribute(scene_id, RoomService.GROUP_EFFECT) )
		{
			return 0;//���������Ч��
		}
		
		List<RoleEntity> member_list_in_instance = getMemberListInInstance();
		//�ж��Ƿ��Ǹ�����Ӽӳ�Ч��,����ڸ��������ң������������������ϵ����ڸ�������ʱ�������Ч��ֵ
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
			//���ؽ��ͽ������Ӹ���ֵ
			int spicEffect = new MyServiceImpl().addTeamEffect(p_pk);
			groupEffectValue =groupEffectValue+ Math.round((float)((float)groupEffectValue*(float)spicEffect/100));
		}
		return groupEffectValue;
	}
	
	/**
	 * �������Ч������ʾ
	 */
	public String getGroupEffectDisplay(RoleEntity role_info)
	{
		StringBuffer result = new StringBuffer();
		
		int scene_id = Integer.parseInt(role_info.getBasicInfo().getSceneId());
		
		int groupEffectValue = getGroupEffectValue(role_info); 
		
		RoomService roomService = new RoomService();
		
		if( roomService.isSpecifyMapType(scene_id, MapType.INSTANCE) )
		{
			result.append("��ӹ���Ч������"+groupEffectValue+"%<br/>");
			result.append("��ӷ���Ч������"+groupEffectValue+"%");
		}
		else
		{
			result.append("��ӹ���Ч������"+groupEffectValue+"%<br/>");
			result.append("��ӷ���Ч������"+groupEffectValue+"%<br/>");
			result.append("��Ӿ���Ч������"+groupEffectValue+"%");
		}
		
		return result.toString();
	}
	
	/**
	 * ����Ҽ������Ч��
	 * @param player
	 */
	public void loadGroupEffect(PartInfoVO player)
	{
		if (player == null)
		{
			logger.info("����Ϊ��");
			return;
		}
		logger.debug("�鿴�û����ڵ�ͼ�Ƿ���Ч! "+player.getPMap());

		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		
    	int groupEffectPercent = getGroupEffectValue(role_info);
    	player.setGjSubjoin(player.getGjSubjoin() + groupEffectPercent);
    	player.setFySubjoin(player.getFySubjoin() + groupEffectPercent);
	}
	
	/**
	 * ���a�����b�Ƿ���ͬһ������
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
	 * �ж϶����Ƿ�����
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
	 * ����ϵͳ��Ϣ�����г�Ա
	 * @param systeminfo_content    ��Ϣ����
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
	 * �¼����Ա������г�Ա����ϵͳ��Ϣ
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
			
			String to_new_member_sysinfo = "��������"+caption_info.getBasicInfo().getName()+"�Ķ���";//���Լ�������Ϣ
			String to_other_member_sysinfo = new_member_info.getBasicInfo().getName()+"�������";//������������Ա����Ϣ
			
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
