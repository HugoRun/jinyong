package com.lw.dao.information;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.information.InformationVO;

public class InformationDAO extends DaoBase
{

	/** 给玩家插入大礼包的信息 */
	public void setId(int u_pk, String id, String type)
	{
		String sql = "INSERT INTO u_information VALUES  (?,?,?)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, u_pk);
			ps.setString(i++, id);
			ps.setString(i++, type);

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** 获得玩家大礼包的信息 */
	public InformationVO getInfotmationByUpk(int u_pk, String type)
	{
		InformationVO vo = null;
		String sql = "SELECT * FROM u_information WHERE u_pk = " + u_pk
				+ " AND type = '" + type + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new InformationVO();
				vo.setId("id");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/** 得到玩家帮会的信息 */
	public int getInfotmationByTong(int p_pk)
	{
		int t_id = 0;
		String sql = "SELECT t_pk FROM u_tong_member WHERE tm_rights = 1 AND p_pk = "
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				t_id = rs.getInt("t_pk");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return t_id;
	}
}
