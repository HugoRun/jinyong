package com.ls.ben.dao.instance;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.instance.InstanceArchiveVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 副本存档
 */
public class InstanceArchiveDao extends DaoBase
{
	/**
	 * 插入副本存档
	 */
	public void insertArchive( int p_pk,int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "INSERT INTO instance_archive(p_pk,map_id,dead_boss_record,create_time) VALUES ("+p_pk+","+map_id+",'',now())";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 得到角色是否有副本存档
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public boolean isHaveArchive( int p_pk,int map_id )
	{
		boolean result = false;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "SELECT p_pk FROM instance_archive WHERE p_pk="+p_pk+" AND map_id=" + map_id+" LIMIT 1";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				result = true;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return result;
	}
	/**
	 * 得到角色副本存档
	 * @param p_pk
	 * @param map_id
	 * @return
	 */
	public InstanceArchiveVO getArchive( int p_pk,int map_id )
	{
		InstanceArchiveVO instanceArchive = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "SELECT * FROM instance_archive WHERE p_pk="+p_pk+" AND map_id=" + map_id+" LIMIT 1";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				instanceArchive = new InstanceArchiveVO();
				instanceArchive.setPPk(p_pk);
				instanceArchive.setMapId(map_id);
				instanceArchive.setDeadBossRecord(rs.getString("dead_boss_record"));
				instanceArchive.setCreateTime(rs.getDate("create_time"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return instanceArchive;
	}
	
	/**
	 * 更新boss死亡记录
	 * @param p_pk
	 * @param map_id
	 * @param sence_id              boss死亡地点
	 */
	public void updateArchive( int p_pk,int map_id,int sence_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "UPDATE instance_archive SET dead_boss_record=CONCAT(dead_boss_record,',','"+sence_id+"') WHERE p_pk="+p_pk+" AND map_id=" + map_id+"";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	/**
	 * 初始化副本存档
	 * @param p_pk
	 * @param map_id
	 */
	public void initArchive( int p_pk,int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "UPDATE instance_archive SET dead_boss_record='',create_time=now() WHERE p_pk="+p_pk+" AND map_id=" + map_id+"";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	/**
	 * 清除所有副本存档
	 * @param p_pk
	 * @param map_id
	 */
	public void clearAllArchive( int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "UPDATE instance_archive SET dead_boss_record='',create_time=now() WHERE map_id=" + map_id+"";
			logger.debug(sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
}
