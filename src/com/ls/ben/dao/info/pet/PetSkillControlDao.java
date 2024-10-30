/**
 * 
 */
package com.ls.ben.dao.info.pet;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.pet.PetSkillControlVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:
 * 
 * @author 刘帅
 * 
 * 9:52:22 AM
 */
public class PetSkillControlDao extends DaoBase
{
	/**
	 * 通过宠物id和宠物技能id的到宠物技能出招几率
	 * 
	 * @param pet_id
	 * @param pet_skill_group
	 * @return
	 */
	public PetSkillControlVO getByPetAndSkillGroup(int pet_id, int pet_skill_group)
	{
		PetSkillControlVO petControl = null;
		String sql = "SELECT * FROM pet_skill_control where pet_id=" + pet_id
				+ " and pet_skill_group=" + pet_skill_group + " limit 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			logger.debug(sql);
			if (rs.next())
			{
				petControl = new PetSkillControlVO();
				petControl.setControlId(rs.getInt("control_id"));
				petControl.setPetId(pet_id);
				petControl.setControlDrop(rs.getInt("control_drop"));
				petControl.setPetSkGroup(pet_skill_group);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return petControl;
	}

}
