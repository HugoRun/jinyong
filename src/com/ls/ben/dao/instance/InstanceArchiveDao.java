package com.ls.ben.dao.instance;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.instance.InstanceArchiveVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * �����浵
 */
public class InstanceArchiveDao extends DaoBase
{
	/**
	 * ���븱���浵
	 */
	public void insertArchive( int p_pk,int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "insert into instance_archive(p_pk,map_id,dead_boss_record,create_time) values ("+p_pk+","+map_id+",'',now())";
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
	 * �õ���ɫ�Ƿ��и����浵
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
			String sql = "select p_pk from instance_archive where p_pk="+p_pk+" and map_id=" + map_id+" limit 1";
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
	 * �õ���ɫ�����浵
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
			String sql = "select * from instance_archive where p_pk="+p_pk+" and map_id=" + map_id+" limit 1";
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
	 * ����boss������¼
	 * @param p_pk
	 * @param map_id
	 * @param sence_id              boss�����ص�
	 */
	public void updateArchive( int p_pk,int map_id,int sence_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "update instance_archive set dead_boss_record=CONCAT(dead_boss_record,',','"+sence_id+"') where p_pk="+p_pk+" and map_id=" + map_id+"";
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
	 * ��ʼ�������浵
	 * @param p_pk
	 * @param map_id
	 */
	public void initArchive( int p_pk,int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "update instance_archive set dead_boss_record='',create_time=now() where p_pk="+p_pk+" and map_id=" + map_id+"";
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
	 * ������и����浵
	 * @param p_pk
	 * @param map_id
	 */
	public void clearAllArchive( int map_id )
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "update instance_archive set dead_boss_record='',create_time=now() where map_id=" + map_id+"";
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
