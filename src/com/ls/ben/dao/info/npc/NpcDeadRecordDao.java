package com.ls.ben.dao.info.npc;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcDeadRecordVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 功能:npc_deadtime_record
 * Mar 5, 2009
 */
public class NpcDeadRecordDao extends DaoBase
{
	/**
	 * 插入一条记录
	 */
	public void incert( NpcDeadRecordVO npcDeadRecord  )
	{
		if( npcDeadRecord==null )
		{
			logger.debug("插入npcDeadRecord时为空");
			return; 
		}
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "INSERT INTO npc_dead_record VALUES (null,"+npcDeadRecord.getPPk()+
		","+npcDeadRecord.getNpcId()+","+npcDeadRecord.getSceneId()+","+npcDeadRecord.getMapId()+",now())";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement(); 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();

		}	
	}
	
	/**
	 * 更新npc死亡时间
	 * @param p_pk
	 * @param npc_id
	 * @param scene_id
	 */
	public void updateDeadTime( int p_pk,int npc_id,int scene_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "UPDATE npc_dead_record SET npc_deadtime=now() WHERE p_pk ="+p_pk+"  AND scene_id = "+scene_id+" AND npc_id="+npc_id;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement(); 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
			
		}	
	}
	/**
	 * 清除队伍的所有记录
	 * @param caption_pk
	 * @param map_id
	 */
	public void clear( int caption_pk )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "DELETE FROM npc_dead_record WHERE p_pk ="+caption_pk;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement(); 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
			
		}	
	}
	/**
	 * 清除map下的所有记录
	 * @param caption_pk
	 * @param map_id
	 */
	public void clearByMapID( int caption_pk,int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "DELETE FROM npc_dead_record WHERE p_pk ="+caption_pk+" AND map_id="+map_id;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement(); 
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();

		}	
	}
	
	/**
	 * 得到该地点的npc记录
	 */
	public NpcDeadRecordVO getRecord( int p_pk,int scene_id )
	{
		NpcDeadRecordVO npcRecord = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT *  FROM npc_dead_record WHERE p_pk ="+p_pk+" AND scene_id="+scene_id;
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				npcRecord = new NpcDeadRecordVO();
				npcRecord.setId(rs.getInt("id"));
				npcRecord.setPPk(p_pk);
				npcRecord.setNpcId(rs.getInt("npc_id"));
				npcRecord.setSceneId(scene_id);
				npcRecord.setMapId(rs.getInt("map_id"));
				npcRecord.setNpcDeadTime(rs.getTimestamp("npc_deadtime"));
			}
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();

		}
		
		return npcRecord;
	}
}
