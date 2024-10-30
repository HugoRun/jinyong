package com.ls.web.service.log;

import org.apache.commons.lang.StringUtils;

import com.ls.ben.dao.log.RoleLogDao;

/**
 * @author hd ��Ϸ����־�����
 */
public class LogService
{
	/**
	 * ��¼��ҵ�������־
	 */
	public void recordUpgradeLog(int p_pk, String role_name, String content)
	{
		RoleLogDao roleLogDao = new RoleLogDao();
		roleLogDao.insert(p_pk, role_name, content);
	}

	/***************************************************************************
	 * ��ҽ�Ǯ��¼
	 * 
	 **************************************************************************/
	public void recordMoneyLog(int p_pk, String role_name, String old_num,
			String new_num, String content)
	{
		RoleLogDao roleLogDao = new RoleLogDao();
		if (role_name == null)
		{
			return;
		}
		if (old_num == null)
		{
			return;
		}
		if (new_num == null)
		{
			return;
		}
		roleLogDao.insertRecordMoneyLog(p_pk, role_name, old_num, new_num,
				content);
	}

	/**
	 * ��ҽ��ӛ�
	 * 
	 * 
	 * 
	 */
	public void recordExpLog(int p_pk, String role_name, String old_num,
			String new_num, String content)
	{
		if( p_pk<=0 || StringUtils.isEmpty(role_name) || StringUtils.isNumeric(old_num)==false || StringUtils.isNumeric(new_num)==false )
		{
			return ;
		}
		
		if( Integer.parseInt(new_num)>Integer.parseInt(old_num) )
		{
			RoleLogDao roleLogDao = new RoleLogDao();
			roleLogDao.insertRecordExpLog(p_pk, role_name, old_num, new_num,content);
		}
	}

	/***************************************************************************
	 * ���Ԫ����¼
	 * 
	 * 
	 * 
	 * 
	 **************************************************************************/
	public void recordYBLog(int p_pk, String role_name, String old_num,
			String new_num, String content)
	{
		RoleLogDao roleLogDao = new RoleLogDao();
		if (role_name == null)
		{
			return;
		}
		if (old_num == null)
		{
			return;
		}
		if (new_num == null)
		{
			return;
		}
		roleLogDao
				.insertRecordYBLog(p_pk, role_name, old_num, new_num, content);
	}

	/***************************************************************************
	 * ��һ������Լ�¼
	 * 
	 * 
	 * 
	 * 
	 **************************************************************************/
	public void recordPlayerLog(int p_pk, String role_name, String old_num,
			String new_num, String content)
	{
		RoleLogDao roleLogDao = new RoleLogDao();
		if (role_name == null)
		{
			return;
		}
		if (old_num == null)
		{
			return;
		}
		if (new_num == null)
		{
			return;
		}
		roleLogDao.insertRecordPlayerLog(p_pk, role_name, old_num, new_num,
				content);
	}
}