package com.lw.service.systemnotify;

import java.util.List;

import com.lw.dao.systemnotify.SystemNotifyDao;
import com.lw.vo.systemnotify.SystemNotifyVO;

public class SystemNotifyService
{
	/** �õ�������� */
	public List<SystemNotifyVO> getNotifyTitle(int type)
	{
		SystemNotifyDao dao = new SystemNotifyDao();
		List<SystemNotifyVO> list = dao.getNotifyType(type);
		return list;
	}

	/** �õ��������� */
	public String getNotifyContent(int id)
	{
		SystemNotifyDao dao = new SystemNotifyDao();
		return dao.getNotifyContent(id);
	}
	
	/**
	 * �õ���һ�����������
	 * @return
	 */
	public SystemNotifyVO getFirstNotifyInfo()
	{
		SystemNotifyDao dao = new SystemNotifyDao();
		return dao.getFirstNotifyInfo();
	}
}
