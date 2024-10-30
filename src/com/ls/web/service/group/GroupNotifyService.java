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
 * ����:���֪ͨ����
 * 
 * @author ��˧ 3:11:25 PM
 */
public class GroupNotifyService
{

	// ֪ͨ����
	public static final int CREATE = 1;// ���,�����µĶ���
	public static final int DISBAND = 2;// ��ɢ
	public static final int JOIN = 3;// �������
	public static final int KICKED = 4;// ���ӳ��߳�����
	public static final int LEAVE = 5;// �뿪����
	public static final int INVITE = 6;// �ӳ�������Ҽ������
	public static final int GROUPHINT = 7;// �������

	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���a�����b�������
	 * 
	 * @param a_pk
	 * @param b_pk
	 * @return ����null��ʾ�ύ�������ɹ�
	 */
	public String applyGroup(int a_pk, int b_pk)
	{
		String group_hint = null;

		GroupService groupService = new GroupService();
		RoleService roleService = new RoleService();
		
		if (a_pk == b_pk)
		{
			return "�Բ���,�����ܺ��Լ����!";
		}

		RoleEntity b_role_info = roleService.getRoleInfoById(b_pk + "");

		if (b_role_info == null)
		{
			return "���û�������";
		}

		if (isNotifyPlayerB(a_pk, b_pk))
		{
			return "�����ύ���������";
		}
		else
		{
			int a_captain_pk = groupService.getCaptionPk(a_pk);
			int b_captain_pk = groupService.getCaptionPk(b_pk);

			if (a_captain_pk == -1 && b_captain_pk == -1)// ���a���b���������״̬��
			{
				return createNewGroup(a_pk, b_pk);
			}
			else
				if (a_captain_pk != -1 && a_captain_pk == b_captain_pk)
				{
					return group_hint = "���ѺͶԷ���ͬһ����";
				}
				else
					if (a_captain_pk != -1) // ���a�����״̬
					{
						if (a_captain_pk == a_pk)// ���a�Ƕӳ�
						{
							if (b_captain_pk != -1)
							{
								return group_hint = "�Է��Ѿ��ڶ�����";
							}
							else
							{
								return group_hint = inviteMember(a_pk, b_pk);
							}
						}
						else
						{
							group_hint = "�����Ƕӳ�û��Ȩ����������Ҽ������";
						}
						return group_hint;
					}
					else
						if (b_captain_pk != -1) // ���b�����״̬
						{
							return joinGroup(a_pk, b_captain_pk);
						}

		}

		return group_hint;
	}


	/**
	 * �ӳ�������Ҽ������
	 */
	private String inviteMember(int captain_pk, int member_pk)
	{
		String group_hint = null;
		GroupService groupService = new GroupService();
		int group_num = groupService.getGroupNumByCaptain(captain_pk);
		if (group_num >= GroupModel.MEMBER_MAX_NUM)
		{
			group_hint = "������������";
		}
		else
		{
			if (isNotifyPlayerB(captain_pk, member_pk))// �Է������֪ͨ
			{
				group_hint = "����������������";
			}
			else
			{
				// group_hint = "����Է�������������";
				addNotify(captain_pk, member_pk, INVITE);
			}

		}

		return group_hint;
	}

	/**
	 * ����������
	 */
	private String joinGroup(int member_pk, int captain_pk)
	{
		String group_hint = null;
		GroupService groupService = new GroupService();

		int group_num = groupService.getGroupNumByCaptain(captain_pk);
		if (group_num >= GroupModel.MEMBER_MAX_NUM)
		{
			group_hint = "�Է�������������";
		}
		else
		{
			if (isNotifyPlayerB(member_pk, captain_pk))// �Է������֪ͨ
			{
				group_hint = "����Է�������������";
			}
			else
			{
				// group_hint = "����Է�������������";
				addNotify(member_pk, captain_pk, JOIN);
			}

		}
		return group_hint;
	}

	/**
	 * �齨�µĶ���
	 */
	private String createNewGroup(int a_pk, int b_pk)
	{
		String group_hint = null;
		addNotify(a_pk, b_pk, CREATE);
		return group_hint;
	}

	/**
	 * ������֪ͨ
	 * 
	 * @param a_pk
	 *            ����֪ͨ�����
	 * @param b_pk
	 *            ��֪ͨ�����
	 * @param notify_type
	 *            ֪ͨ����
	 */
	public void addNotify(int create_notfiy_pk, int notified_pk, int notify_type)
	{

		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		// �ж�create_notfiy_pk�Ƿ���notified_pk�����������
		if (isNotifyPlayerB(create_notfiy_pk, notified_pk))
		{
			logger.info("����Է������������");
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
	 * �õ������Ϣ
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
	 * �ж��Ƿ��������Ϣ
	 */
	public int isHaveNotify(int p_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		return groupNotifyDao.isHaveNotify(p_pk);
	}

	/**
	 * ����֪ͨ״̬
	 */
	public void updateNotifyFlay(int n_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.updateNotifyFlag(n_pk);
	}

	/**
	 * ɾ��ָ��֪ͨ
	 * 
	 * @param n_pk
	 */
	public void deleteNotify(int n_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.delete(n_pk);
	}

	/**
	 * ɾ��ָ���˵�֪ͨ
	 * 
	 * @param n_pk
	 */
	public void clareNotify(int p_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		groupNotifyDao.clareNotify(p_pk);
	}

	/**
	 * �ж����a�Ƿ������b�ύ�����֪ͨ
	 */
	public boolean isNotifyPlayerB(int a_pk, int b_pk)
	{
		GroupNotifyDao groupNotifyDao = new GroupNotifyDao();
		return groupNotifyDao.isNotifyPlayerB(a_pk, b_pk);
	}

	/**
	 * ���a�����b�������
	 * 
	 * @param a_pk
	 * @param b_pk
	 * @return ����null��ʾ�ύ�������ɹ�
	 */
	public String applyGroupBySearch(int a_pk, int b_pk)
	{
		String group_hint = null;
		GroupService groupService = new GroupService();
		// int flag = isAreaAllowGroup(a_pk,b_pk);
		// if(flag == 1){
		// return group_hint = "�����ڵ�ͼ��������ӣ�";
		// }else if(flag == 2){
		// return group_hint = "�Է����ڵ�ͼ��������ӣ�";
		// }else {

		if (isNotifyPlayerB(a_pk, b_pk))
		{
			return "�����ύ���������";
		}
		else
		{
			int a_captain_pk = groupService.getCaptionPk(a_pk);
			int b_captain_pk = groupService.getCaptionPk(b_pk);

			if (a_captain_pk == -1 && b_captain_pk == -1)// ���a���b���������״̬��
			{
				return createNewGroup(a_pk, b_pk);
			}
			else
				if (a_captain_pk != -1 && a_captain_pk == b_captain_pk)
				{
					return group_hint = "���ѺͶԷ���ͬһ����";
				}
				else
					if (a_captain_pk != -1) // ���a�����״̬
					{
						group_hint = "���Ѿ��ڶ�����";
						return group_hint;
					}
					else
						if (b_captain_pk != -1) // ���b�����״̬
						{
							return joinGroup(a_pk, b_captain_pk);
						}

		}

		return group_hint;
	}
}
