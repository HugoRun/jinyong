package com.lw.service.system;

import com.lw.dao.player.SystemDao;

public class SystemAllkeyService
{
	/**�����������*/
	public String getAllKey()
	{
		String all_key = null;
		SystemDao dao = new SystemDao();
		all_key = dao.getAllKey();
		return all_key;
	}
}
