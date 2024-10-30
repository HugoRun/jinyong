package com.ls.ben.dao.info.npc;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcDeadRecordVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * ����:npc_deadtime_record
 * Mar 5, 2009
 */
public class NpcDeadRecordDao extends DaoBase
{
	/**
	 * ����һ����¼
	 */
	public void incert( NpcDeadRecordVO npcDeadRecord  )
	{
		if( npcDeadRecord==null )
		{
			logger.debug("����npcDeadRecordʱΪ��");
			return; 
		}
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "insert into npc_dead_record values (null,"+npcDeadRecord.getPPk()+
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
	 * ����npc����ʱ��
	 * @param p_pk
	 * @param npc_id
	 * @param scene_id
	 */
	public void updateDeadTime( int p_pk,int npc_id,int scene_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "update npc_dead_record set npc_deadtime=now() where p_pk ="+p_pk+"  and scene_id = "+scene_id+" and npc_id="+npc_id;
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
	 * �����������м�¼
	 * @param caption_pk
	 * @param map_id
	 */
	public void clear( int caption_pk )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "delete from npc_dead_record where p_pk ="+caption_pk;
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
	 * ���map�µ����м�¼
	 * @param caption_pk
	 * @param map_id
	 */
	public void clearByMapID( int caption_pk,int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "delete from npc_dead_record where p_pk ="+caption_pk+" and map_id="+map_id;
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
	 * �õ��õص��npc��¼
	 */
	public NpcDeadRecordVO getRecord( int p_pk,int scene_id )
	{
		NpcDeadRecordVO npcRecord = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select *  from npc_dead_record where p_pk ="+p_pk+" and scene_id="+scene_id;
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
