package com.lw.dao.systemnotify;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.systemnotify.SystemNotifyVO;

public class SystemNotifyDao extends DaoBase
{
	/** �õ���������� */
	public List<SystemNotifyVO> getNotifyType(int type)
	{
		List<SystemNotifyVO> list = new ArrayList<SystemNotifyVO>();
		SystemNotifyVO vo = null;
		String sql = "select * from game_notify where isonline = 1 and type = "
				+ type + " order by ordernum  ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new SystemNotifyVO();
				vo.setId(rs.getInt("id"));
				vo.setNotifytitle(rs.getString("title"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	/** �õ���������� */
	public String getNotifyContent(int id)
	{
		String content = null;
		String sql = "select content from game_notify where id = " + id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				content = rs.getString("content");
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return content;
	}

	/**
	 * �õ���һ�����������
	 * 
	 * @return
	 */
	public SystemNotifyVO getFirstNotifyInfo()
	{
		SystemNotifyVO vo = null;
		String sql = "select * from game_notify where isonline = 1  order by ordernum  limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new SystemNotifyVO();
				vo.setId(rs.getInt("id"));
				vo.setNotifytitle(rs.getString("title"));
				;
				vo.setNotifycontent(rs.getString("content"));
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}
}
