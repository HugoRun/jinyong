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
		String sql = "insert into u_information values  (?,?,?)";
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
		String sql = "select * from u_information where u_pk = " + u_pk
				+ " and type = '" + type + "'";
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
		String sql = "select t_pk from u_tong_member where tm_rights = 1 and p_pk = "
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
