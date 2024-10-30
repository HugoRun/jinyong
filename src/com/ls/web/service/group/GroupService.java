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
 * 功能:组队管理
 * @author 刘帅 9:49:09 AM
 */

public class GroupService
{
	private static final LinkedHashMap<String,GroupModel> all_groups = new LinkedHashMap<String,GroupModel>(400);
	
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 得到游戏当前所有队伍
	 */
	public static List<GroupModel> getGroupList()
	{
		return new ArrayList<GroupModel>(all_groups.values());
	}
	
	/**队伍的LIST分页**/
	public static List<GroupModel> getGroupListByPage(int page)
	{
		int perpage = 7;
		List<GroupModel> list = getGroupList();
		List<GroupModel> grouplist = new ArrayList<GroupModel>();
		int pagenum = (page+1)*perpage;//判断是否为最后一页
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
	 * 当前队伍数量
	 */
	public static int getGroupNum()
	{
		return all_groups.size();
	}
	
	
	/**
	 * 从所有队伍中移除指定队伍
	 */
	public static void removeGroupFromAllGroups( String caption_id )
	{
		if( caption_id !=null )
		{
			all_groups.remove(caption_id);
		}
	}
	
	/**
	 * 从所有队伍中移除指定队伍
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
	 * 登记组队信息
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
	 * 得到玩家组队信息
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
	 * 得到玩家所在组的成员数量
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
	 * 通过队长id得到成员数量
	 * 
	 * @param captain_pk
	 * @return
	 */
	public int getGroupNumByCaptain(int captain_pk)
	{
		return getGroupNumByMember(captain_pk);
	}

	/**
	 * 判断玩家所在队伍的队长id 如果队长id为-1表示玩家还没有组队
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
	 * 玩家A加入玩家B的队伍
	 * 
	 * @param a_p_pk
	 *            玩家A
	 * @param b_p_pk
	 *            玩家B
	 * @return 返回队伍列表时表示成功，不为空时表示提示错误信息
	 */
	public GroupModel joinGroup(int a_p_pk, int b_p_pk)
	{
		GroupModel group_info = getGroupInfo(b_p_pk);

		if (group_info==null)
		{
			group_info = createNewGroup(b_p_pk, a_p_pk);// 创建新队伍
		}
		else
		{  
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoById(a_p_pk+"");
			group_info.addMember(roleInfo);// 加入队伍
		}
		return group_info;
	}

	/**
	 * 创建新的队伍
	 * @param captain_pk                       队长id
	 * @param member_pk						   队员id
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
	 * 队长删除成员
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
	 * 解散队伍
	 * @param g_caption_pk
	 */
	public void disband(int g_caption_pk)
	{
		GroupModel group_info = getGroupInfo(g_caption_pk);
		group_info.disbandGroup();
	}

	/**
	 * 得到队伍成员列表
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
	 * 离开队伍
	 */
	public void leaveGroup(int pPk)
	{
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk);
		leaveGroup(roleEntity);
	}
	/**
	 * 离开队伍
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
	 * 战斗时，给玩家加载组队效果
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
		
		GroupModel group_info = getGroupInfo(player.getPPk());
		
		if( group_info!=null )
		{
			group_info.loadGroupEffect(player);
		}
	}
	

	/**
	 * 根据队伍人数得到组队效果值
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
	 * 服务器重启时，清空所有组队临时信息
	 */
	public void initTempGroupInfo()
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.clearAllNotify();
	}
	
	
	/**得到玩家组队信息的显示*/
	public String getGroupEffectPercentDisplay(RoleEntity role_info)
	{
		return role_info.getStateInfo().getGroupInfo().getGroupEffectDisplay(role_info);
	}
}
