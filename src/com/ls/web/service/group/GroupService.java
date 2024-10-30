package com.ls.web.service.group;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.info.group.GroupNotifyDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.group.GroupModel;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

/**
 * ����:��ӹ���
 * @author ��˧ 9:49:09 AM
 */

public class GroupService
{
	private static final LinkedHashMap<String,GroupModel> all_groups = new LinkedHashMap<String,GroupModel>(400);
	
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * �õ���Ϸ��ǰ���ж���
	 */
	public static List<GroupModel> getGroupList()
	{
		return new ArrayList<GroupModel>(all_groups.values());
	}
	
	/**�����LIST��ҳ**/
	public static List<GroupModel> getGroupListByPage(int page)
	{
		int perpage = 7;
		List<GroupModel> list = getGroupList();
		List<GroupModel> grouplist = new ArrayList<GroupModel>();
		int pagenum = (page+1)*perpage;//�ж��Ƿ�Ϊ���һҳ
		if(list.size()<pagenum)
		{
			for(int i = page*perpage;i<list.size();i++)
			{
				GroupModel gm = list.get(i);
				grouplist.add(gm);
			}
		}else
		{
			for(int i = page*perpage;i<pagenum;i++)
			{
				GroupModel gm = list.get(i);
				grouplist.add(gm);
			}
		}
		return grouplist;
	}
	
	/**
	 * ��ǰ��������
	 */
	public static int getGroupNum()
	{
		return all_groups.size();
	}
	
	
	/**
	 * �����ж������Ƴ�ָ������
	 */
	public static void removeGroupFromAllGroups( String caption_id )
	{
		if( caption_id !=null )
		{
			all_groups.remove(caption_id);
		}
	}
	
	/**
	 * �����ж������Ƴ�ָ������
	 */
	public static void cancelGroupInfo(GroupModel group_info )
	{
		if( group_info!=null )
		{
			all_groups.remove(group_info.getCaptianID()+"");
			group_info = null;
		}
	}
	
	/**
	 * �Ǽ������Ϣ
	 */
	public static void registerGroupInfo( GroupModel group_info )
	{
		if( group_info!=null )
		{
			all_groups.put(group_info.getCaptianID()+"", group_info);
			group_info = null;
		}
	}
	
	/**
	 * �õ���������Ϣ
	 */
	public GroupModel getGroupInfo( int p_pk )
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if( role_info==null )
		{
			return null;
		}
		
		GroupModel group_info = role_info.getStateInfo().getGroupInfo();
		
		if( group_info==null )
		{
			return null;
		}
		return group_info;
	}
	
	/**
	 * �õ����������ĳ�Ա����
	 */
	public int getGroupNumByMember(int member_pk)
	{
		GroupModel group_info = getGroupInfo(member_pk);
		if( group_info==null )
		{
			return -1;
		}
		return group_info.getMemberNum();
	}

	/**
	 * ͨ���ӳ�id�õ���Ա����
	 * 
	 * @param captain_pk
	 * @return
	 */
	public int getGroupNumByCaptain(int captain_pk)
	{
		return getGroupNumByMember(captain_pk);
	}

	/**
	 * �ж�������ڶ���Ķӳ�id ����ӳ�idΪ-1��ʾ��һ�û�����
	 */
	public int getCaptionPk(int p_pk)
	{
		GroupModel group_info = getGroupInfo(p_pk);
		if( group_info==null )
		{
			return -1;
		}
		return group_info.getCaptianID();
	}

	/**
	 * ���A�������B�Ķ���
	 * 
	 * @param a_p_pk
	 *            ���A
	 * @param b_p_pk
	 *            ���B
	 * @return ���ض����б�ʱ��ʾ�ɹ�����Ϊ��ʱ��ʾ��ʾ������Ϣ
	 */
	public GroupModel joinGroup(int a_p_pk, int b_p_pk)
	{
		GroupModel group_info = getGroupInfo(b_p_pk);

		if (group_info==null)
		{
			group_info = createNewGroup(b_p_pk, a_p_pk);// �����¶���
		}
		else
		{  
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoById(a_p_pk+"");
			group_info.addMember(roleInfo);// �������
		}
		return group_info;
	}

	/**
	 * �����µĶ���
	 * @param captain_pk                       �ӳ�id
	 * @param member_pk						   ��Աid
	 * @return
	 */
	private GroupModel createNewGroup(int captain_pk, int member_pk)
	{
		GroupModel group_info = new GroupModel();
		
		RoleEntity member_pk_roleInfo = RoleService.getRoleInfoById(member_pk);
		RoleEntity captain_pk_roleInfo = RoleService.getRoleInfoById(captain_pk);
		
		if( member_pk_roleInfo==null || captain_pk_roleInfo==null )
		{
			return null;
		}
		
		group_info.createGroup(captain_pk_roleInfo,member_pk_roleInfo);
		return group_info;
	}

	/**
	 * �ӳ�ɾ����Ա
	 */
	public void kickMember(int member_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(member_pk);
		
		if( role_info==null )
		{
			return ;
		}
		
		GroupModel group_info = role_info.getStateInfo().getGroupInfo();
		
		if( group_info==null )
		{
			return ;
		}
		if( group_info==null )
		{
			return;
		}
		group_info.leaveGroup(role_info);
	}

	/**
	 * ��ɢ����
	 * @param g_caption_pk
	 */
	public void disband(int g_caption_pk)
	{
		GroupModel group_info = getGroupInfo(g_caption_pk);
		group_info.disbandGroup();
	}

	/**
	 * �õ������Ա�б�
	 */
	public List<RoleEntity> getMembersList(int p_pk)
	{
		GroupModel group_info = getGroupInfo(p_pk);
		if( group_info==null )
		{
			return null;
		}
		return group_info.getMemberList();
	}

	/**
	 * �뿪����
	 */
	public void leaveGroup(int pPk)
	{
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk);
		leaveGroup(roleEntity);
	}
	/**
	 * �뿪����
	 */
	public void leaveGroup(RoleEntity roleEntity)
	{
		GroupModel group_info = roleEntity.getStateInfo().getGroupInfo();
		if( group_info!=null )
		{
			group_info.leaveGroup(roleEntity);
		}
	}


	/**
	 * ս��ʱ������Ҽ������Ч��
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
		
		GroupModel group_info = getGroupInfo(player.getPPk());
		
		if( group_info!=null )
		{
			group_info.loadGroupEffect(player);
		}
	}
	

	/**
	 * ���ݶ��������õ����Ч��ֵ
	 * 
	 * @param group_num
	 * @return
	 */
	public int getGroupEffectPercent(int p_pk, int mapId)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		if( role_info==null )
		{
			return 0;
		}
		
		GroupModel group_info = role_info.getStateInfo().getGroupInfo();
		
		if( group_info==null )
		{
			return 0;
		}
		
		return group_info.getGroupEffectValue(role_info);
	}

	
	/**
	 * ����������ʱ��������������ʱ��Ϣ
	 */
	public void initTempGroupInfo()
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.clearAllNotify();
	}
	
	
	/**�õ���������Ϣ����ʾ*/
	public String getGroupEffectPercentDisplay(RoleEntity role_info)
	{
		return role_info.getStateInfo().getGroupInfo().getGroupEffectDisplay(role_info);
	}
}
