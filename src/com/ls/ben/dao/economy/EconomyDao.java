package com.ls.ben.dao.economy;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class EconomyDao extends DaoBase
{
	/**
	 * 得到元宝数量
	 */
	public long getYuanbao(int u_pk)
	{
		long yuanbao = 0;
		String sql = "SELECT yuanbao FROM u_login_info WHERE u_pk = " + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				yuanbao = rs.getLong("yuanbao");
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
		return yuanbao;
	}


	/**
	 * 更新元宝数量
	 * @param u_pk
	 * @param updat_yuanbao_num                   可正可负
	 */
	public void updateYuanbao(int u_pk, long updat_yuanbao_num)
	{
		String sql = "UPDATE u_login_info SET  yuanbao = yuanbao+" + updat_yuanbao_num +" WHERE u_pk = " + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}
	/**
	 * 得到积分数量
	 */
	public int getJifen(int u_pk)
	{
		int jifen = 0;
		String sql = "SELECT jifen FROM u_login_info WHERE u_pk = " + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				jifen = rs.getInt("jifen");
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
		return jifen;
	}
	
	
	/**
	 * 更新积分数量
	 * @param u_pk
	 * @param updat_yuanbao_num                   可正可负
	 */
	public void updateJifen(int u_pk, int updat_jifen_num)
	{
		String sql = "UPDATE u_login_info SET  jifen = jifen+" + updat_jifen_num +" WHERE u_pk = " + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

}
