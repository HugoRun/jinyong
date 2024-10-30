package com.lw.dao.skill;

import java.sql.SQLException;

import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.pub.db.DBConnection;
import com.pm.vo.passiveskill.PassSkillVO;

/**
 * 
 * 10:45:53 AM
 */
public class Passskill extends SkillDao
{


	/**
	 * 得到玩家的被动技能属性
	 * @param pk
	 * @return
	 */
	public PassSkillVO getPassSkillByPPk(int p_pk)
	{
		PassSkillVO passSkillVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT MAX(sk_bj_multiple) AS sk_bj_multiple, MAX(sk_mp_multiple) AS sk_mp_multiple, MAX(sk_hp_multiple) AS sk_hp_multiple," +
				"MAX(sk_gj_multiple) AS sk_gj_multiple, MAX(sk_fy_multiple) AS sk_fy_multiple, SUM(sk_gj_add) AS gjadd," +
				"SUM (sk_fy_add) AS fyadd, SUM(sk_hp_add) AS hpadd, SUM(sk_mp_add) AS mpadd FROM u_skill_info WHERE sk_type = 0 AND p_pk = " + p_pk;
		
		logger.debug("得到玩家的被动技能属性="+sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				passSkillVO = new PassSkillVO();
				passSkillVO.setSkBjMultiple(rs.getDouble("sk_bj_multiple"));
				passSkillVO.setSkFyMultiple(rs.getDouble("sk_fy_multiple"));
				passSkillVO.setSkGjMultiple(rs.getDouble("sk_gj_multiple"));
				passSkillVO.setSkHpMultiple(rs.getDouble("sk_hp_multiple"));
				passSkillVO.setSkMpMultiple(rs.getDouble("sk_mp_multiple"));
				
				passSkillVO.setSkFyAdd(rs.getInt("fyadd"));
				passSkillVO.setSkGjAdd(rs.getInt("gjadd"));
				passSkillVO.setSkHpAdd(rs.getInt("hpadd"));
				passSkillVO.setSkMpAdd(rs.getInt("mpadd"));
				
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return passSkillVO;		
	}
	
	/**玩家五行属性技能**/
	/**
	 * 得到玩家的被动技能属性
	 * @param pk
	 * @return
	 */
	public PassSkillVO getPassSkillWXByPPk(int p_pk)
	{
		PassSkillVO passSkillVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT MAX(sk_gj_multiple) AS sk_gj_multiple, MAX(sk_bj_multiple) AS sk_bj_multiple, MAX(sk_hp_multiple) AS sk_hp_multiple" +
				", MAX(sk_mp_multiple) AS sk_mp_multiple, MAX(sk_fy_multiple) AS sk_fy_multiple FROM u_skill_info WHERE sk_type = 2 AND p_pk = " + p_pk;
		
		logger.debug("得到玩家的被动技能属性="+sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				passSkillVO = new PassSkillVO();
				passSkillVO.setSkJMultiple(rs.getDouble("sk_gj_multiple"));
				passSkillVO.setSkMMultiple(rs.getDouble("sk_fy_multiple"));
				passSkillVO.setSkSMultiple(rs.getDouble("sk_hp_multiple"));
				passSkillVO.setSkHMultiple(rs.getDouble("sk_mp_multiple"));
				passSkillVO.setSkTMultiple(rs.getDouble("sk_bj_multiple"));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return passSkillVO;		
	}
	
}
