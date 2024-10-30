package com.ben.dao.task;

import java.util.HashMap;

import com.ben.vo.task.UtaskCompleteVO;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

/**
 * 玩家已经完成的任务
 * @author hhj
 *
 */
public class TaskCompleteDao extends DaoBase
{

	/**
	 * 返回角色已经完成的任务
	 * 
	 * @param p_pk
	 * @return List
	 */
	public HashMap getPlayerTaskComplete(int p_pk)
	{
		HashMap askComplete = new HashMap();
		String sql = "SELECT * FROM u_task_complete where p_pk=" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			UtaskCompleteVO vo = null; 
			while (rs.next())
			{
				vo = new UtaskCompleteVO();
				vo.setCPk(rs.getInt("c_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setTaskZu(rs.getString("task_zu"));
				askComplete.put(rs.getString("task_zu")+"", vo);
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

		return askComplete;
	}
}
