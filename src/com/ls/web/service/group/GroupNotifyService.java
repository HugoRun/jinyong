package com.ls.web.service.group;

import org.apache.log4j.Logger;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ls.ben.dao.info.group.GroupNotifyDao;
import com.ls.ben.vo.info.group.GroupNotifyVO;
import com.ls.model.group.GroupModel;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;

/**
 * 功能:组队通知管理
 * 
 * @author 刘帅 3:11:25 PM
 */
public class GroupNotifyService
{

	// 通知类型
	public static final int CREATE = 1;// 组队,创建新的队伍
	public static final int DISBAND = 2;// 解散
	public static final int JOIN = 3;// 申请加入
	public static final int KICKED = 4;// 被队长踢出队伍
	public static final int LEAVE = 5;// 离开队伍
	public static final int INVITE = 6;// 队长邀请玩家加入队伍
	public static final int GROUPHINT = 7;// 组队提醒

	Logger logger = Logger.getLogger("log.service");

	/**
	 * 玩家a向玩家b申请组队
	 * 
	 * @param a_pk
	 * @param b_pk
	 * @return 返回null表示提交组队申请成功
	 */
	public String applyGroup(int a_pk, int b_pk)
	{
		String group_hint = null;

		GroupService groupService = new GroupService();
		RoleService roleService = new RoleService();
		
		if (a_pk == b_pk)
		{
			return "对不起,您不能和自己组队!";
		}

		RoleEntity b_role_info = roleService.getRoleInfoById(b_pk + "");

		if (b_role_info == null)
		{
			return "该用户已离线";
		}

		if (isNotifyPlayerB(a_pk, b_pk))
		{
			return "您已提交了组队申请";
		}
		else
		{
			int a_captain_pk = groupService.getCaptionPk(a_pk);
			int b_captain_pk = groupService.getCaptionPk(b_pk);

			if (a_captain_pk == -1 && b_captain_pk == -1)// 玩家a玩家b都不在组队状态中
			{
				return createNewGroup(a_pk, b_pk);
			}
			else
				if (a_captain_pk != -1 && a_captain_pk == b_captain_pk)
				{
					return group_hint = "您已和对方在同一队伍";
				}
				else
					if (a_captain_pk != -1) // 玩家a在组队状态
					{
						if (a_captain_pk == a_pk)// 玩家a是队长
						{
							if (b_captain_pk != -1)
							{
								return group_hint = "对方已经在队伍中";
							}
							else
							{
								return group_hint = inviteMember(a_pk, b_pk);
							}
						}
						else
						{
							group_hint = "您不是队长没有权限邀请别的玩家加入队伍";
						}
						return group_hint;
					}
					else
						if (b_captain_pk != -1) // 玩家b在组队状态
						{
							return joinGroup(a_pk, b_captain_pk);
						}

		}

		return group_hint;
	}


	/**
	 * 队长邀请玩家加入队伍
	 */
	private String inviteMember(int captain_pk, int member_pk)
	{
		String group_hint = null;
		GroupService groupService = new GroupService();
		int group_num = groupService.getGroupNumByCaptain(captain_pk);
		if (group_num >= GroupModel.MEMBER_MAX_NUM)
		{
			group_hint = "本队人数已满";
		}
		else
		{
			if (isNotifyPlayerB(captain_pk, member_pk))// 以发过组队通知
			{
				group_hint = "您已提出了组队申请";
			}
			else
			{
				// group_hint = "您向对方提出了组队申请";
				addNotify(captain_pk, member_pk, INVITE);
			}

		}

		return group_hint;
	}

	/**
	 * 申请加入队伍
	 */
	private String joinGroup(int member_pk, int captain_pk)
	{
		String group_hint = null;
		GroupService groupService = new GroupService();

		int group_num = groupService.getGroupNumByCaptain(captain_pk);
		if (group_num >= GroupModel.MEMBER_MAX_NUM)
		{
			group_hint = "对方队伍人数已满";
		}
		else
		{
			if (isNotifyPlayerB(member_pk, captain_pk))// 以发过组队通知
			{
				group_hint = "您向对方提出了组队申请";
			}
			else
			{
				// group_hint = "您向对方提出了组队申请";
				addNotify(member_pk, captain_pk, JOIN);
			}

		}
		return group_hint;
	}

	/**
	 * 组建新的队伍
	 */
	private String createNewGroup(int a_pk, int b_pk)
	{
		String group_hint = null;
		addNotify(a_pk, b_pk, CREATE);
		return group_hint;
	}

	/**
	 * 添加组队通知
	 * 
	 * @param a_pk
	 *            创造通知的玩家
	 * @param b_pk
	 *            被通知的玩家
	 * @param notify_type
	 *            通知类型
	 */
	public void addNotify(int create_notfiy_pk, int notified_pk, int notify_type)
	{

		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		// 判断create_notfiy_pk是否向notified_pk发出组队请求
		if (isNotifyPlayerB(create_notfiy_pk, notified_pk))
		{
			logger.info("已向对方发送组队请求");
		}
		else
		{
			GroupNotifyVO groupNotify = new GroupNotifyVO();
			groupNotify.setCreateNotifyPk(create_notfiy_pk);
			groupNotify.setNotifyedPk(notified_pk);
			groupNotify.setNotifyType(notify_type);
			groupNotifyDao.add(groupNotify);
		}
	}

	/**
	 * 得到组队消息
	 * 
	 * @param p_pk
	 * @return
	 */
	public GroupNotifyVO getGroupNotify(int p_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		return groupNotifyDao.getNotifyInfo(p_pk);
	}

	/**
	 * 判断是否有组队消息
	 */
	public int isHaveNotify(int p_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		return groupNotifyDao.isHaveNotify(p_pk);
	}

	/**
	 * 更新通知状态
	 */
	public void updateNotifyFlay(int n_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.updateNotifyFlag(n_pk);
	}

	/**
	 * 删除指定通知
	 * 
	 * @param n_pk
	 */
	public void deleteNotify(int n_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.delete(n_pk);
	}

	/**
	 * 删除指定人的通知
	 * 
	 * @param n_pk
	 */
	public void clareNotify(int p_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.clareNotify(p_pk);
	}

	/**
	 * 判断玩家a是否向玩家b提交过组队通知
	 */
	public boolean isNotifyPlayerB(int a_pk, int b_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		return groupNotifyDao.isNotifyPlayerB(a_pk, b_pk);
	}

	/**
	 * 玩家a向玩家b申请组队
	 * 
	 * @param a_pk
	 * @param b_pk
	 * @return 返回null表示提交组队申请成功
	 */
	public String applyGroupBySearch(int a_pk, int b_pk)
	{
		String group_hint = null;
		GroupService groupService = new GroupService();
		// int flag = isAreaAllowGroup(a_pk,b_pk);
		// if(flag == 1){
		// return group_hint = "您所在地图不允许组队！";
		// }else if(flag == 2){
		// return group_hint = "对方所在地图不允许组队！";
		// }else {

		if (isNotifyPlayerB(a_pk, b_pk))
		{
			return "您已提交了组队申请";
		}
		else
		{
			int a_captain_pk = groupService.getCaptionPk(a_pk);
			int b_captain_pk = groupService.getCaptionPk(b_pk);

			if (a_captain_pk == -1 && b_captain_pk == -1)// 玩家a玩家b都不在组队状态中
			{
				return createNewGroup(a_pk, b_pk);
			}
			else
				if (a_captain_pk != -1 && a_captain_pk == b_captain_pk)
				{
					return group_hint = "您已和对方在同一队伍";
				}
				else
					if (a_captain_pk != -1) // 玩家a在组队状态
					{
						group_hint = "你已经在队伍中";
						return group_hint;
					}
					else
						if (b_captain_pk != -1) // 玩家b在组队状态
						{
							return joinGroup(a_pk, b_captain_pk);
						}

		}

		return group_hint;
	}
}
