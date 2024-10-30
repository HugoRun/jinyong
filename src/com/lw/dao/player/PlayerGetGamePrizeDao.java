package com.lw.dao.player;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.lw.vo.player.PlayerGetGamePrizeVO;

public class PlayerGetGamePrizeDao extends DaoBase
{

	/** 根据玩家u_pk 得到玩家要领取的道具列表 */
	public List<PlayerGetGamePrizeVO> getPlayerPrizeList(String u_passprot,
			int pageno, int perpage)
	{
		List<PlayerGetGamePrizeVO> list = new ArrayList<PlayerGetGamePrizeVO>();
		PlayerGetGamePrizeVO vo = null;
		String sql = "select * from game_prize where state != 1 and u_passprot = '"
				+ u_passprot + "' limit " + pageno + "," + perpage;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new PlayerGetGamePrizeVO();
				vo.setId(rs.getInt("id"));
				vo.setPrizetype(rs.getString("prize_type"));
				vo.setPrizedisplay(rs.getString("prize_display"));
				vo.setPassprot(rs.getString("u_passprot"));
				vo.setU_pk(rs.getInt("u_pk"));
				vo.setP_name(rs.getString("u_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setProp(rs.getString("prop"));
				vo.setState(rs.getInt("state"));
				vo.setCreatetime(rs.getTimestamp("create_time"));
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

	/** 更新玩家的领取状态 */
	public void updatePlayerPrizeState(String u_passprot, int id)
	{
		String sql = "update game_prize set state = 1 , create_time = now() where id = "
				+ id + " and u_passprot = '" + u_passprot + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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

	/** 给玩家领取记录表里添加数据 */
	public void insertPlayerPrizeInfo(int u_pk, int p_pk, String content)
	{
		String sql = "insert into game_prize_info values (null," + u_pk + ","
				+ p_pk + ",'" + content + "',now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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

	/** 得到玩家的单条数据 */
	public PlayerGetGamePrizeVO getPrizeByID(int id, String u_passprot)
	{
		String sql = "select * from game_prize where id = " + id
				+ " and u_passprot = '" + u_passprot + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		PlayerGetGamePrizeVO vo = new PlayerGetGamePrizeVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerGetGamePrizeVO();
				vo.setId(rs.getInt("id"));
				vo.setPrizetype(rs.getString("prize_type"));
				vo.setPrizedisplay(rs.getString("prize_display"));
				vo.setPassprot(rs.getString("u_passprot"));
				vo.setU_pk(rs.getInt("u_pk"));
				vo.setP_name(rs.getString("u_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setProp(rs.getString("prop"));
				vo.setState(rs.getInt("state"));
				vo.setCreatetime(rs.getTimestamp("create_time"));
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

	/** 得到玩家领取道具的总数量 */
	public int getPlayerPrizeNum(int u_pk)
	{
		int x = 0;
		String sql = "select count(*) as num from game_prize where state != 1 and u_pk = "
				+ u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				x = rs.getInt("num");
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
		return x;
	}

	/** 根据玩家u_pk 得到玩家要领取的道具列表 */
	public PlayerGetGamePrizeVO getPlayerPrize(int id)
	{
		PlayerGetGamePrizeVO vo = new PlayerGetGamePrizeVO();
		String sql = "select * from game_prize where id = " + id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo.setId(rs.getInt("id"));
				vo.setPrizetype(rs.getString("prize_type"));
				vo.setPrizedisplay(rs.getString("prize_display"));
				vo.setPassprot(rs.getString("u_passprot"));
				vo.setU_pk(rs.getInt("u_pk"));
				vo.setP_name(rs.getString("u_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setProp(rs.getString("prop"));
				vo.setState(rs.getInt("state"));
				vo.setCreatetime(rs.getTimestamp("create_time"));
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

	public String getUserID(int u_pk)
	{
		String user_id = null;
		String sql = "select user_id from u_passport_info where u_pk = " + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				user_id = rs.getString("user_id");
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
		return user_id;
	}

	// 一下方法为UPK为条件
	/** 根据玩家u_pk 得到玩家要领取的道具列表 */
	public List<PlayerGetGamePrizeVO> getPlayerPrizeListByUpk(String u_pk,
			int pageno, int perpage)
	{
		List<PlayerGetGamePrizeVO> list = new ArrayList<PlayerGetGamePrizeVO>();
		PlayerGetGamePrizeVO vo = null;
		String sql = "select * from game_prize where state != 1 and u_pk = '"
				+ u_pk + "' limit " + pageno + "," + perpage;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new PlayerGetGamePrizeVO();
				vo.setId(rs.getInt("id"));
				vo.setPrizetype(rs.getString("prize_type"));
				vo.setPrizedisplay(rs.getString("prize_display"));
				vo.setPassprot(rs.getString("u_passprot"));
				vo.setU_pk(rs.getInt("u_pk"));
				vo.setP_name(rs.getString("u_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setProp(rs.getString("prop"));
				vo.setState(rs.getInt("state"));
				vo.setCreatetime(rs.getTimestamp("create_time"));
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

	/** 更新玩家的领取状态 */
	public void updatePlayerPrizeStateByUpk(String u_pk, int id)
	{
		String sql = "update game_prize set state = 1 , create_time = now() where id = "
				+ id + " and u_pk = '" + u_pk + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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

	/** 得到玩家的单条数据 */
	public PlayerGetGamePrizeVO getPrizeByIDByUpk(int id, String u_pk)
	{
		String sql = "select * from game_prize where id = " + id
				+ " and u_pk = '" + u_pk + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		PlayerGetGamePrizeVO vo = new PlayerGetGamePrizeVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerGetGamePrizeVO();
				vo.setId(rs.getInt("id"));
				vo.setPrizetype(rs.getString("prize_type"));
				vo.setPrizedisplay(rs.getString("prize_display"));
				vo.setPassprot(rs.getString("u_passprot"));
				vo.setU_pk(rs.getInt("u_pk"));
				vo.setP_name(rs.getString("u_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setProp(rs.getString("prop"));
				vo.setState(rs.getInt("state"));
				vo.setCreatetime(rs.getTimestamp("create_time"));
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

	/** 得到玩家是否有在线奖励的数据 */
	public PlayerGetGamePrizeVO getPlayerIsHaveOnlineTimePrize(
			String prize_name, int u_pk, int p_pk, int state, String time,
			String prize_content)
	{
		String sql = "select * from game_prize where u_pk = " + u_pk
				+ " and p_pk = " + p_pk + " and create_time like '%" + time
				+ "%' and prop = '" + prize_content + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		PlayerGetGamePrizeVO vo = null;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerGetGamePrizeVO();
				vo.setId(rs.getInt("id"));
				vo.setPrizetype(rs.getString("prize_type"));
				vo.setPrizedisplay(rs.getString("prize_display"));
				vo.setPassprot(rs.getString("u_passprot"));
				vo.setU_pk(rs.getInt("u_pk"));
				vo.setP_name(rs.getString("u_name"));
				vo.setP_pk(rs.getInt("p_pk"));
				vo.setProp(rs.getString("prop"));
				vo.setState(rs.getInt("state"));
				vo.setCreatetime(rs.getTimestamp("create_time"));
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

	public void insertPlayerOnlinePrize(String prize_name,
			String prize_display, String u_passport, int u_pk, String name,
			int p_pk, int state, String prize_content)
	{
		String sql = "insert into game_prize values (null,'" + prize_name
				+ "','" + prize_display + "','" + u_passport + "'," + u_pk
				+ ",'" + name + "'," + p_pk + "," + state + ",'"
				+ prize_content + "',now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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

	public void delPlayerOnlinePrize(String time, int state)
	{
		String sql = "delete from game_prize where create_time < '" + time
				+ "' and state = " + state;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
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
