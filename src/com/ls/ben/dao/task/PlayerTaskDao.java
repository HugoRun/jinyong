package com.ls.ben.dao.task;

import java.util.HashMap;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:
 * 
 * @author 刘帅 10:36:42 AM
 */
public class PlayerTaskDao extends DaoBase {
	
	/**
	 * 根据pPk得到玩家所有任务
	 * @param p_pk
	 * @return
	 */
	public HashMap getPlayerAllTask (int p_pk)
	{
		HashMap tasks = new HashMap();
		String sql = "SELECT * FROM u_task WHERE p_pk=" + p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			PlayerSkillVO vo = null;

			while (rs.next())
			{
				vo = new PlayerSkillVO();
				vo.setSPk(rs.getInt("s_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setSkId(rs.getInt("sk_id"));
				vo.setSkName(StringUtil.isoToGBK(rs.getString("sk_name")));
				vo.setSkSleight(rs.getInt("sk_sleight"));
				vo.setSkUsetime(rs.getTimestamp("sk_usetime"));
				vo.setSkType(rs.getInt("sk_type"));
				vo.setSkGjMultiple(rs.getDouble("sk_gj_multiple"));
				vo.setSkFyMultiple(rs.getDouble("sk_fy_multiple"));
				vo.setSkHpMultiple(rs.getDouble("sk_hp_multiple"));
				vo.setSkMpMultiple(rs.getDouble("sk_mp_multiple"));
				vo.setSkBjMultiple(rs.getDouble("sk_bj_multiple"));
				vo.setSkGjAdd(rs.getInt("sk_gj_add"));
				vo.setSkFyAdd(rs.getInt("sk_fy_add"));
				vo.setSkHpAdd(rs.getInt("sk_hp_add"));
				vo.setSkMpAdd(rs.getInt("sk_mp_add"));
				vo.setSkGroup(rs.getInt("sk_group"));
				tasks.put(rs.getInt("s_pk"), vo);
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

		return tasks;
	}
}
