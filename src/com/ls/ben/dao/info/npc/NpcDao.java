package com.ls.ben.dao.info.npc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:
 * @author 刘帅
 * 3:15:41 PM
 */
public class NpcDao extends DaoBase
{
	/**
	 * 根据id得到一个npc
	 * @param id
	 * @return
	 */
	public NpcVO getNpcById1(int id)
	{
		NpcVO npc = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "SELECT * FROM npc where npc_ID=" + id;
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				npc = this.loadData(rs);
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return npc;
	}
	
	


	public HashMap<String, NpcVO> getAllNpc()
	{
		HashMap<String, NpcVO> result = null;
		int total_num = 0;
		NpcVO npc = null;
		
		String total_num_sql = "SELECT count(*) from npc";
		String sql = "SELECT * FROM npc";
		logger.debug(sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(total_num_sql);
			
			if( rs.next() )
			{
				total_num = rs.getInt(1);
			}
			
			result = new HashMap<String, NpcVO>(total_num);
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				npc = this.loadData(rs);
				result.put(npc.getNpcID()+"", npc);
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	private NpcVO loadData(ResultSet rs) throws SQLException
	{
		if( rs==null )
		{
			return null;
		}
		NpcVO npc = new NpcVO();
		npc.setNpcID(rs.getInt("npc_ID"));
		npc.setNpcName(rs.getString("npc_Name"));
		npc.setNpcHP(rs.getInt("npc_HP"));
		npc.setDefenceDa(rs.getInt("npc_defence_da"));
		npc.setDefenceXiao(rs.getInt("npc_defence_xiao"));

		npc.setJinFy(rs.getInt("npc_jin_fy"));
		npc.setMuFy(rs.getInt("npc_mu_fy"));
		npc.setShuiFy(rs.getInt("npc_shui_fy"));
		npc.setHuoFy(rs.getInt("npc_huo_fy"));
		npc.setTuFy(rs.getInt("npc_tu_fy"));

		npc.setDrop(rs.getInt("npc_drop"));
		npc.setLevel(rs.getInt("npc_Level"));
		npc.setExp(rs.getInt("npc_EXP"));
		npc.setMoney(rs.getString("npc_money"));
		npc.setNpcRefurbishTime(rs.getInt("npc_refurbish_time"));
		npc.setTake(rs.getInt("npc_take"));
		npc.setNpcType(rs.getInt("npc_type"));
		npc.setPic(rs.getString("npc_pic"));
		return npc;
	}
}
