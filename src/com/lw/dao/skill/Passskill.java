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
		String sql = "select max(sk_bj_multiple) as sk_bj_multiple,max(sk_mp_multiple) as sk_mp_multiple,max(sk_hp_multiple) as sk_hp_multiple," +
				"max(sk_gj_multiple) as sk_gj_multiple,max(sk_fy_multiple) as sk_fy_multiple,sum(sk_gj_add) as gjadd," +
				"sum(sk_fy_add) as fyadd,sum(sk_hp_add) as hpadd,sum(sk_mp_add) as mpadd from u_skill_info where sk_type = 0 and p_pk = " + p_pk;
		
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
		String sql = "select max(sk_gj_multiple) as sk_gj_multiple,max(sk_bj_multiple) as sk_bj_multiple,max(sk_hp_multiple) as sk_hp_multiple" +
				",max(sk_mp_multiple) as sk_mp_multiple,max(sk_fy_multiple) as sk_fy_multiple from u_skill_info where sk_type = 2 and p_pk = " + p_pk;
		
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
