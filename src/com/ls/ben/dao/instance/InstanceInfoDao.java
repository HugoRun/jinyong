package com.ls.ben.dao.instance;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.instance.InstanceInfoVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 功能:副本信息
 * Feb 3, 2009
 */
public class InstanceInfoDao extends DaoBase
{
	/**
	 * 根据地点id得到副本信息
	 * @param map_id
	 * @return
	 */
	public InstanceInfoVO getByID( int id )
	{
		InstanceInfoVO instanceInfo = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			String sql = "SELECT * FROM instance_info WHERE id=" + id+" LIMIT 1";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				instanceInfo = new InstanceInfoVO();
				instanceInfo.setId(rs.getInt("id"));
				instanceInfo.setDisplay(rs.getString("display"));
				instanceInfo.setMapId(rs.getInt("map_id"));
				instanceInfo.setEnterSenceId(rs.getInt("enter_scene_id"));
				instanceInfo.setResetTimeDistance(rs.getInt("reset_time_distance"));
				instanceInfo.setPreResetTime(rs.getDate("pre_reset_time"));
				instanceInfo.setLevelLimit(rs.getInt("level_limit"));
				instanceInfo.setGroupLimit(rs.getInt("group_limit"));
				instanceInfo.setBossSceneNum(rs.getInt("boss_scene_num"));
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
		
		return instanceInfo;
	}
	/**
	 * 根据地点id得到副本信息
	 * @param map_id
	 * @return
	 */
	public InstanceInfoVO getByMapID( int map_id )
	{
		InstanceInfoVO instanceInfo = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			String sql = "SELECT * FROM instance_info WHERE map_id=" + map_id+" LIMIT 1";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				instanceInfo = new InstanceInfoVO();
				instanceInfo.setId(rs.getInt("id"));
				instanceInfo.setDisplay(rs.getString("display"));
				instanceInfo.setMapId(rs.getInt("map_id"));
				instanceInfo.setEnterSenceId(rs.getInt("enter_scene_id"));
				instanceInfo.setResetTimeDistance(rs.getInt("reset_time_distance"));
				instanceInfo.setPreResetTime(rs.getDate("pre_reset_time"));
				instanceInfo.setLevelLimit(rs.getInt("level_limit"));
				instanceInfo.setGroupLimit(rs.getInt("group_limit"));
				instanceInfo.setBossSceneNum(rs.getInt("boss_scene_num"));
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
		
		return instanceInfo;
	}
	
	/**
	 * 更新重置时间
	 * @param map_id
	 */
	public void updateResetTime(int map_id)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			String sql = "UPDATE instance_info SET pre_reset_time=now() WHERE map_id=" + map_id+"";
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
}
