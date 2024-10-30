package com.lw.service.systemnotify;

import java.util.List;

import com.lw.dao.systemnotify.SystemNotifyDao;
import com.lw.vo.systemnotify.SystemNotifyVO;

public class SystemNotifyService
{
	/** 得到公告标题 */
	public List<SystemNotifyVO> getNotifyTitle(int type)
	{
		SystemNotifyDao dao = new SystemNotifyDao();
		List<SystemNotifyVO> list = dao.getNotifyType(type);
		return list;
	}

	/** 得到公告内容 */
	public String getNotifyContent(int id)
	{
		SystemNotifyDao dao = new SystemNotifyDao();
		return dao.getNotifyContent(id);
	}
	
	/**
	 * 得到第一条公告的内容
	 * @return
	 */
	public SystemNotifyVO getFirstNotifyInfo()
	{
		SystemNotifyDao dao = new SystemNotifyDao();
		return dao.getFirstNotifyInfo();
	}
}
