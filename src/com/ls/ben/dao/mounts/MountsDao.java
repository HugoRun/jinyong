package com.ls.ben.dao.mounts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.pub.db.DBConnection;

/**
 * 坐骑Dao类
 * 
 * @author Thomas.lei
 * 
 */
public class MountsDao extends DaoBase
{
	private static Map<Integer, MountsVO> mount_cache = new HashMap<Integer, MountsVO>(
			100);

	/** *******为角色增加坐骑********* */
	public int addMounts(UserMountsVO uv)
	{
		String sql = "INSERT INTO u_mounts_temp (p_pk,mountID,mountState,mountLevel,getTime)values("
				+ uv.getPpk()
				+ ","
				+ uv.getMountsID()
				+ ","
				+ uv.getMountsState() + ","+uv.getMountsLevle()+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int num = 0;
		try
		{
			stmt = conn.createStatement();
			num = stmt.executeUpdate(sql);
			rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				uv.setId(rs.getInt(1));
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
		return num;
	}

	/** *********查询角色现在拥有的所有坐骑信息************ */
	public List<UserMountsVO> getUserMountsList(int ppk)
	{
		String sql = "SELECT * FROM u_mounts_temp WHERE p_pk=" + ppk + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		List<UserMountsVO> list = new ArrayList<UserMountsVO>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				UserMountsVO uv = new UserMountsVO();
				uv.setId(rs.getInt("id"));
				uv.setPpk(rs.getInt("p_pk"));
				uv.setMountsID(rs.getInt("mountID"));
				uv.setMountsState(rs.getInt("mountState"));
				uv.setGetTime(rs.getDate("getTime"));
				list.add(uv);
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return list;
	}

	/** **********根据坐骑ID查询坐骑的详细信息**************** */
	public MountsVO getMountsInfo(int mountID)
	{
		MountsVO mv = mount_cache.get(mountID);
		if (mv != null)
		{
			return mv;
		}

		String sql = "SELECT * FROM u_mounts_table WHERE id=" + mountID + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				mv = new MountsVO();
				mv.setId(rs.getInt("id"));
				mv.setName(rs.getString("name"));
				mv.setLevel(rs.getInt("level"));
				mv.setType(rs.getInt("type"));
				mv.setCarryNum1(rs.getInt("carryNum1"));
				mv.setOverPay1(rs.getInt("overPay1"));
				mv.setCarryNum2(rs.getInt("carryNum2"));
				mv.setOverPay2(rs.getInt("overPay2"));
				mv.setUplevelPay(rs.getInt("uplevelPay"));
				mv.setImage(rs.getString("image"));
				mv.setDisplay(rs.getString("display"));
				mv.setFunctionDisplay(rs.getString("functionDisplay"));
				mv.setBuff(rs.getString("buff"));
				mv.setSentPrice(rs.getInt("sentPrice"));
				mv.setNextLevelID(rs.getString("nextLevelID"));
				mv.setHightLevel(rs.getInt("hightLevel"));
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		if (mv != null)
		{
			this.mount_cache.put(mv.getId(), mv);
		}
		return mv;
	}

	/** *****************得到系统赠送坐骑****************** */
	public MountsVO getMountsInfoBySystem(int mountsType)
	{
		String sql = "SELECT * FROM u_mounts_table WHERE level=1 and type="
				+ mountsType + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		MountsVO mv = null;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				mv = new MountsVO();
				mv.setId(rs.getInt("id"));
				mv.setName(rs.getString("name"));
				mv.setLevel(rs.getInt("level"));
				mv.setType(rs.getInt("type"));
				mv.setCarryNum1(rs.getInt("carryNum1"));
				mv.setOverPay1(rs.getInt("overPay1"));
				mv.setCarryNum2(rs.getInt("carryNum2"));
				mv.setOverPay2(rs.getInt("overPay2"));
				mv.setUplevelPay(rs.getInt("uplevelPay"));
				mv.setImage(rs.getString("image"));
				mv.setDisplay(rs.getString("display"));
				mv.setFunctionDisplay(rs.getString("functionDisplay"));
				mv.setBuff(rs.getString("buff"));
				mv.setSentPrice(rs.getInt("sentPrice"));
				mv.setNextLevelID(rs.getString("nextLevelID"));
				mv.setHightLevel(rs.getInt("hightLevel"));
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return mv;
	}

	/** **********删除用户某个坐骑，坐骑升级的时候删除低等级坐骑************ */
	public void deleteUserMounts(int ppk, int mountsID)
	{
		String sql = "delete from u_mounts_temp WHERE p_pk=" + ppk
				+ " and mountID=" + mountsID + " limit 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** ***********用户点击取消骑乘或者是换乘的时候改变状态都为0***************** */
	public void updateMountState1(int ppk)
	{
		String sql = "update u_mounts_temp set mountState=0 WHERE p_pk=" + ppk
				+ "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** ********用户点击换乘则改变乘骑状态************ */
	public void updateMountState2(int ppk, int mountID)
	{
		String sql = "update u_mounts_temp set mountState=1 WHERE p_pk=" + ppk
				+ " and mountID=" + mountID + " limit 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** ********得到可供购买的坐骑********** */
	public List<MountsVO> getCanSentMounts()
	{
		String sql = "SELECT * FROM u_mounts_table WHERE level = 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		List<MountsVO> list = new ArrayList<MountsVO>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				MountsVO mv = new MountsVO();
				mv = new MountsVO();
				mv.setId(rs.getInt("id"));
				mv.setName(rs.getString("name"));
				mv.setLevel(rs.getInt("level"));
				mv.setType(rs.getInt("type"));
				mv.setCarryNum1(rs.getInt("carryNum1"));
				mv.setOverPay1(rs.getInt("overPay1"));
				mv.setCarryNum2(rs.getInt("carryNum2"));
				mv.setOverPay2(rs.getInt("overPay2"));
				mv.setUplevelPay(rs.getInt("uplevelPay"));
				mv.setImage(rs.getString("image"));
				mv.setDisplay(rs.getString("display"));
				mv.setFunctionDisplay(rs.getString("functionDisplay"));
				mv.setBuff(rs.getString("buff"));
				mv.setSentPrice(rs.getInt("sentPrice"));
				list.add(mv);

			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return list;
	}

	/** ***得到坐骑的排行榜信息*** */
	public List<UserMountsVO> getMountsRank()
	{
		String sql = "SELECT * FROM `u_mounts_temp` ORDER BY mountLevel DESC, getTime DESC LIMIT 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		List<UserMountsVO> list = new ArrayList<UserMountsVO>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				UserMountsVO uv = new UserMountsVO();
				uv.setId(rs.getInt("id"));
				uv.setPpk(rs.getInt("p_pk"));
				uv.setMountsID(rs.getInt("mountID"));
				uv.setMountsState(rs.getInt("mountState"));
				uv.setGetTime(rs.getDate("getTime"));
				list.add(uv);

			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return list;
	}

	/** *****得到拥有该坐骑的玩家角色名称***** */
	public String getPname(int p_pk)
	{
		String sql ="select p_name from u_part_info WHERE p_pk="+p_pk+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		String pName="";
		try
		{
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{
				pName=rs.getString("p_name");
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return pName;
	}
	/************删除玩家所有的坐骑*******/
	public void removeMountsInfo(int ppk)
	{
		String sql ="delete from u_mounts_temp WHERE p_pk="+ppk+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}
}
