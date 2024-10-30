package com.ben.dao.sysresources;
 
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.pub.db.DBConnection;

/**
 * 后台账号管理
 */
public class SysResourcesDAO extends BasicDaoSupport
{
	public SysResourcesDAO()
	{
		super("s_user_info", DBConnection.GAME_USER_DB);
	}

	/**
	 * 是否能登陆
	 * @param name
	 * @param paw
	 * @return
	 */
	public boolean isLogin(String name ,String paw)
	{
		String condition_sql = "where u_name = '"+name+"' AND u_paw='"+paw+"'";
		return super.isHaveBySql(condition_sql);
	}
	
	@Override
	protected Object loadData(ResultSet rs) throws SQLException
	{
		return null;
	}
}
