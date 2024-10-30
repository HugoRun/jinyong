package com.lw.dao.instance;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.instance.InstanceInfoVO;
import com.ls.pub.db.DBConnection;

public class InstanceOutDao extends DaoBase
{
	Logger logger = Logger.getLogger(InstanceOutDao.class);

	public List<InstanceInfoVO> getInstanceInfo()
	{
		List<InstanceInfoVO> list = new ArrayList<InstanceInfoVO>();
		InstanceInfoVO instanceInfo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "SELECT * FROM instance_info ";
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				instanceInfo = new InstanceInfoVO();
				instanceInfo.setId(rs.getInt("id"));
				instanceInfo.setDisplay(rs.getString("display"));
				instanceInfo.setMapId(rs.getInt("map_id"));
				instanceInfo.setEnterSenceId(rs.getInt("enter_scene_id"));
				instanceInfo.setResetTimeDistance(rs
						.getInt("reset_time_distance"));
				instanceInfo.setPreResetTime(rs.getTimestamp("pre_reset_time"));
				instanceInfo.setLevelLimit(rs.getInt("level_limit"));
				instanceInfo.setGroupLimit(rs.getInt("group_limit"));
				instanceInfo.setBossSceneNum(rs.getInt("boss_scene_num"));
				list.add(instanceInfo);
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

}
