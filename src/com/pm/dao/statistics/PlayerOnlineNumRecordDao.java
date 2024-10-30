package com.pm.dao.statistics;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class PlayerOnlineNumRecordDao extends DaoBase 
{
	/**
	 * 获得当前在线人数
	 * @param pk
	 * @return
	 */
	public int getPlayerOnlineNum()
	{
		int flag = 0;
		String sql="select * from t_online limit 1";
		logger.debug("获得当前在线人数="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = rs.getInt("onlinecount");
			}
			rs.close();
			stmt.close();
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		
		return flag;
	}
	
	/**
	 * 减少当前在线人数一
	 * 
	 */
	public void reduceOnlineNum()
	{

		String sql="update t_online set onlinecount = onlinecount - 1";
		logger.debug("减少当前在线人数1="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
	}
	
	
	/**
	 * 增加在线人数数量
	 */
	public void addOnlineNumElse()
	{

		String sql="update t_online set onlinecount = onlinecount + 1";
		logger.debug("增加在线人数数量="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
	}
}
