package com.ls.ben.dao.task;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.task.AcceptTaskListVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:accept_task_list
 * @author 刘帅
 * Oct 20, 2008  6:22:43 PM
 */
public class AcceptTaskListDao extends DaoBase
{
	/**
	 * 更具道具id得到备选任务列表
	 * @param prop_id
	 * @return
	 */
	public List<AcceptTaskListVO> getTaskList( int touch_id ,int task_type)
	{
		List<AcceptTaskListVO> task_list = new ArrayList<AcceptTaskListVO>();
		AcceptTaskListVO task_area = null; 
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			String sql = "SELECT * FROM accept_task_list WHERE touch_id='"+touch_id+"' AND task_type="+task_type;
			logger.debug(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				task_area = new AcceptTaskListVO();
				
				task_area.setId(rs.getInt("id"));
				task_area.setTouchId(touch_id);
				task_area.setTaskArea(rs.getString("task_area"));
				task_area.setProbability(rs.getInt("probability"));
				
				task_list.add(task_area);
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
		
		return task_list;
	}
}