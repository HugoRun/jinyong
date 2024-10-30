package com.ben.help;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class HelpDao extends DaoBase
{
	public List<Help> findBySuperId(Object super_id,int begin,int limit)
	{
		List<Help> list = new ArrayList<Help>();
		String sql = "SELECT * FROM help h WHERE h.super_id = " + super_id +" ORDER BY h.shunxu ASC limit "+begin+","+limit;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = get(rs);
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
	
	public int findBySuperId(Object super_id){
		int i = 0;
		String sql = "SELECT count(*) FROM help h WHERE h.super_id = " + super_id ;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				i = rs.getInt(1);
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
		return i;
	}

	private List<Help> get(ResultSet rs) throws java.sql.SQLException
	{
		List<Help> list = new ArrayList<Help>();
		if (rs != null)
		{
			while (rs.next())
			{
				Help help = new Help();
				help.setId(rs.getInt("id"));
				help.setDes(rs.getString("des"));
				help.setLevel_limit(rs.getInt("level_limit"));
				help.setLink_name(rs.getString("link_name"));
				help.setName(rs.getString("name"));
				help.setScene_id(rs.getInt("scene_id"));
				help.setShunxu(rs.getInt("shunxu"));
				help.setSuper_id(rs.getInt("super_id"));
				help.setType(rs.getInt("type"));
				help.setTask_men(rs.getInt("task_men"));
				help.setTask_zu(rs.getString("task_zu"));
				list.add(help);
			}
		}
		return list;
	}
	
	public Help findById(Object id){
		List<Help> list = new ArrayList<Help>();
		String sql = "SELECT * FROM help h WHERE h.id = "+id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = get(rs);
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
		return list==null||list.size()==0?null:list.get(0);
	}
	
	public List<Guai> findByDiaoluo(Object goods_id,Object type){
		List<Guai> list = new ArrayList<Guai>();
		String sql = "SELECT m.npc_ID, m.npc_Name, m.npc_Level FROM npcdrop n, npc m WHERE n.goods_id = "+goods_id+" AND n.goods_type = "+type+" AND n.npc_ID = m.npc_ID AND m.npc_Name LIKE '%(%' GROUP BY m.npc_ID LIMIT "+HelpConstant.COUNT;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getGuai(rs);
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
	
	
	public List<Guai> findByDiaoluo1(Object goods_id,Object type){
		List<Guai> list = new ArrayList<Guai>();
		String sql = "SELECT m.npc_ID, m.npc_Name, m.npc_Level, s.scene_Name, n.npcdrop_importance FROM npcdrop n, npc m, npcrefurbish o ,scene s WHERE n.goods_id = " + goods_id + " AND n.goods_type = " + type + " AND n.npc_ID = m.npc_ID AND o.npc_id = n.npc_id AND o.scene_id = s.scene_ID GROUP BY m.npc_ID ORDER BY npcdrop_importance DESC LIMIT "+HelpConstant.COUNT;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			list = getGuai1(rs);
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
	
	public String findScene(Object npc_id){
		String name = null;
		String sql = "SELECT s.scene_Name FROM npcrefurbish o, scene s WHERE o.scene_id = s.scene_ID AND o.npc_id = " + npc_id + " LIMIT 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				name = rs.getString("scene_Name");
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
		return name;
	}
	
	
	
	
	
	
	private List<Guai> getGuai(ResultSet rs) throws java.sql.SQLException
	{
		List<Guai> list = new ArrayList<Guai>();
		if (rs != null)
		{
			while (rs.next())
			{
				Guai guai = new Guai();
				guai.setGuai_id(rs.getInt("npc_ID"));
				guai.setLevel(rs.getInt("npc_Level"));
				guai.setName(rs.getString("npc_Name"));
				list.add(guai);
			}
		}
		return list;
	}
	
	private List<Guai> getGuai1(ResultSet rs) throws java.sql.SQLException
	{
		List<Guai> list = new ArrayList<Guai>();
		if (rs != null)
		{
			while (rs.next())
			{
				Guai guai = new Guai();
				guai.setGuai_id(rs.getInt("npc_ID"));
				guai.setLevel(rs.getInt("npc_Level"));
				guai.setName(rs.getString("npc_Name"));
				guai.setScene_name(rs.getString("scene_Name"));
				guai.setDropImprot(rs.getInt("npcdrop_importance"));
				list.add(guai);
			}
		}
		return list;
	}
}
