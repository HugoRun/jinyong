package com.lw.dao.specialprop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.pub.db.DBConnection;
import com.lw.vo.specialprop.SpecialPropVO;

public class SpecialPropDAO extends DaoBase
{

	/**
	 * 调用玩家装备类道具是否存在(41为补血补内力型)
	 * 
	 */
	public List<PlayerPropGroupVO> getEquipItem(int pPk)
	{
		String sql = "select * from u_propgroup_info where p_pk = " + pPk
				+ " and prop_type = 41";
		logger.debug(sql);
		PlayerPropGroupVO propGroup = null;
		List<PlayerPropGroupVO> props = new ArrayList<PlayerPropGroupVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				propGroup = new PlayerPropGroupVO();
				propGroup.setPgPk(rs.getInt("pg_pk"));
				propGroup.setPPk(pPk);
				propGroup.setPropId(rs.getInt("prop_id"));
				propGroup.setPropType(rs.getInt("prop_type"));
				props.add(propGroup);
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
		return props;
	}

	/**
	 * 调用玩家装备类道具装备与否
	 * 
	 */
	public SpecialPropVO getEquipProp(int pPk)
	{
		String sql = "select * from u_special_item where p_pk = "+ pPk+" and prop_time = 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		SpecialPropVO vo = null;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new SpecialPropVO();
				vo.setSppk(rs.getInt("sp_pk"));
				vo.setPpk(pPk);
				vo.setPropid(rs.getInt("prop_id"));
				vo.setPropoperate1(rs.getString("prop_operate1"));
				vo.setPropoperate2(rs.getString("prop_operate2"));
				vo.setPropoperate3(rs.getString("prop_operate3"));
				vo.setPropdate(rs.getString("prop_date"));
				vo.setSign(rs.getInt("prop_sign"));
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

	/**
	 * 装备
	 */
	public void updateEquipItemOn(String pg_pk)
	{
		String sql = "update u_special_item set prop_time = 1 where prop_operate3 = "
				+ pg_pk;
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * 卸下
	 */
	public void updateEquipOff(int sp_pk)
	{
		String sql = "update u_special_item set prop_time = 0 where sp_pk =  "
				+ sp_pk;
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * 更新装备类道具的属性
	 */
	public void updateEquipItemNumHM(int p_pk, int prop_id, String prop_operate1)
	{
		String sql = "update u_special_item set prop_operate1 = '"
				+ prop_operate1 + "' where p_pk = " + p_pk+" and prop_time = 1 and prop_id = "
				+ prop_id;;
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * 补充的SQL语句
	 */
	public void updateEquipItemNumHMByPgpk(int p_pk, String pg_pk1,
			String prop_operate1)
	{
		String sql = "update u_special_item set prop_operate1 = '"
				+ prop_operate1 + "' where prop_operate3 = '" + pg_pk1 + "'";
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** 使用时间到 删除玩家的物品 */
	public int delEquipItem(int prop_id, int p_pk, int minute)
	{
		String sql = "delete from u_special_item where p_pk = " + p_pk
				+ " and prop_id = " + prop_id
				+ " and now() > ( prop_date + INTERVAL " + minute + " minute)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			int x = stmt.executeUpdate(sql);
			stmt.close();
			return x;
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return 0;
	}

	/** 特殊物品的表操作 */
	public void insertSpecialProp(PropVO propVO, int p_pk, int pg_pk)
	{
		String sql = "insert u_special_item (sp_pk,p_pk,prop_id,prop_operate1,prop_operate2,prop_operate3,prop_time,prop_date,prop_sign) values (null,?,?,?,?,?,1,now(),0)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, p_pk);
			ps.setInt(i++, propVO.getPropID());
			ps.setString(i++, propVO.getPropOperate1());
			ps.setString(i++, propVO.getPropOperate2());
			ps.setString(i++, pg_pk + "");

			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** 药品分页处理 */
	public List getID(int thispage, int perpagenum, int type1, int type2,
			int p_pk)
	{
		List list = null;
		int pg_pk = 0;
		String sql = "select pg_pk from u_propgroup_info where p_pk = "+p_pk+" and ( prop_type="
				+ type1 + " or prop_type=" + type2 + " ) "
				+ " limit " + perpagenum + " offset " + perpagenum
				* (thispage - 1);
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			list = new ArrayList();
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pg_pk = rs.getInt("pg_pk");
				list.add(pg_pk);
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

	public List<PlayerPropGroupVO> getEquipItemList(int pPk, int thispage,
			int perpagenum)
	{
		String sql = "select * from u_propgroup_info where p_pk = " + pPk
				+ " and prop_type in (41,67) limit " + perpagenum + " offset "
				+ perpagenum * (thispage - 1);
		logger.debug(sql);
		PlayerPropGroupVO propGroup = null;
		List<PlayerPropGroupVO> props = new ArrayList<PlayerPropGroupVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{

				propGroup = new PlayerPropGroupVO();
				propGroup.setPgPk(rs.getInt("pg_pk"));
				propGroup.setPPk(pPk);
				propGroup.setPropId(rs.getInt("prop_id"));
				propGroup.setPropType(rs.getInt("prop_type"));
				props.add(propGroup);
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
		return props;
	}

	/**
	 * 往包裹里增加道具
	 */
	public int addPropGroupEquipItem(PlayerPropGroupVO propGroup, int pg_pk)
	{
		if (propGroup == null)
		{
			logger.debug("propGroup空");
			return -1;
		}
		int result = -1;
		String sql = "insert into u_propgroup_info (pg_pk,p_pk,pg_type,prop_id,prop_type,prop_bonding,prop_protect,prop_isReconfirm,prop_use_control,prop_num,create_time) values (?,?,?,?,?,?,?,?,?,?,now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, pg_pk);
			ps.setInt(i++, propGroup.getPPk());
			ps.setInt(i++, propGroup.getPgType());
			ps.setInt(i++, propGroup.getPropId());
			ps.setInt(i++, propGroup.getPropType());

			ps.setInt(i++, propGroup.getPropBonding());
			ps.setInt(i++, propGroup.getPropProtect());
			ps.setInt(i++, propGroup.getPropIsReconfirm());

			ps.setInt(i++, propGroup.getPropUseControl());

			ps.setInt(i++, propGroup.getPropNum());

			ps.execute();
			ps.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());

		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * 调用玩家装备类道具装备与否
	 * 
	 */
	public int getEquipItemHM(int pPk)
	{
		String sql = "select prop_id from u_special_item where p_pk = "+ pPk +" and prop_time = 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				int i = rs.getInt("prop_id");
				return i;
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
		return 0;
	}

	/**
	 * 根据PG_PK得到道具信息
	 * 
	 */
	public SpecialPropVO getEquipPropByPgpk(String pg_pk)
	{
		String sql = "select * from u_special_item where prop_operate3 = "
				+ pg_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		SpecialPropVO vo = null;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new SpecialPropVO();
				vo.setSppk(rs.getInt("sp_pk"));
				vo.setPropid(rs.getInt("prop_id"));
				vo.setPropoperate1(rs.getString("prop_operate1"));
				vo.setPropoperate2(rs.getString("prop_operate2"));
				vo.setPropdate(rs.getString("prop_date"));
				vo.setSign(rs.getInt("prop_sign"));
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

	/**
	 * 标记 1为使用量用完 3为不能使用
	 */
	public void updateEquipItemSign(int sign, int p_pk)
	{
		String sql = "update u_special_item set prop_sign = " + sign
				+ " where p_pk =" + p_pk +" and prop_time = 1";
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	
	/**
	 * 标记 1为使用量用完 3为不能使用
	 */
	public void updateEquipItemSign1(int sign, int p_pk,int prop_id)
	{
		String sql = "update u_special_item set prop_sign = " + sign
				+ " where p_pk =" + p_pk +" and prop_id = "+prop_id;
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	public void updateall()
	{
		String sql = "update u_special_item set prop_sign = 0 where prop_sign = 4 ";
		logger.debug(sql);
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
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/** 使用时间到 删除玩家的物品 */
	public int delEquipItem(String pg_pk)
	{
		int x = 0;
		String sql = "delete from u_special_item where prop_operate3 = '"
				+ pg_pk + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			x = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return x;
	}

	/**
	 * 得到玩家更新数值的道具
	 * 
	 */
	public SpecialPropVO getSpecialProp(int pPk, String pg_pk)
	{
		String sql = "select * from u_special_item where p_pk = " + pPk +" and prop_operate3 = '"+ pg_pk + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		SpecialPropVO vo = null;
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new SpecialPropVO();
				vo.setSppk(rs.getInt("sp_pk"));
				vo.setPpk(pPk);
				vo.setPropid(rs.getInt("prop_id"));
				vo.setPropoperate1(rs.getString("prop_operate1"));
				vo.setPropoperate2(rs.getString("prop_operate2"));
				vo.setPropoperate3(rs.getString("prop_operate3"));
				vo.setProptime(rs.getInt("prop_time"));
				vo.setPropdate(rs.getString("prop_date"));
				vo.setSign(rs.getInt("prop_sign"));
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
	
	/** 使用时间到 删除玩家的物品 */
	public int delItem(int p_pk, int prop_id)
	{
		String sql = "delete from u_special_item where p_pk = " + p_pk+" and prop_id = " + prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			int x = stmt.executeUpdate(sql);
			stmt.close();
			return x;
		}
		catch (SQLException e)
		{
			logger.info(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return 0;
	}
	
}
