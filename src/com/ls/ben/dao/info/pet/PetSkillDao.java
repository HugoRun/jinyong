/**
 * 
 */
package com.ls.ben.dao.info.pet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ben.vo.pet.pet.PetVO;
import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.pet.PetSkillVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:pet_skill
 * 
 * @author 刘帅
 * 
 * 9:39:25 AM
 */
public class PetSkillDao extends DaoBase
{
	/**
	 * 通过id得到一个技能
	 * 
	 * @param pet_skill_id
	 * @return
	 */
	public PetSkillVO getById1(int pet_skill_id)
	{
		PetSkillVO petSkill = null;
		String sql = "SELECT * FROM pet_skill where  pet_skill_id="
				+ pet_skill_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petSkill = new PetSkillVO();
				petSkill.setPetSkillId(rs.getInt("pet_skill_id"));
				petSkill.setPetSkillName(rs.getString("pet_skill_name"));
				petSkill.setPetSkillType(rs.getInt("pet_skill_type"));
				petSkill.setPetSkillArea(rs.getInt("pet_skill_area"));
				petSkill.setPetSkillGjDa(rs.getInt("pet_skill_gj_da"));
				petSkill.setPetSkillGjXiao(rs.getInt("pet_skill_gj_xiao"));
				petSkill.setPetSkillBewrite(rs.getString("pet_skill_bewrite"));
				// petSkill.setPetViolenceDropMultiple(rs
				// .getInt("pet_skill_violence_drop_mutiple"));
				petSkill.setPetSkillSeveral(rs.getInt("pet_skill_several"));
				petSkill
						.setPetSkillMultiple(rs.getDouble("pet_skill_multiple"));
				petSkill.setPetInjureMultiple(rs
						.getDouble("pet_skill_injure_multiple"));
				petSkill.setPetViolenceDropMultiple(rs
						.getDouble("pet_skill_violence_drop_multiple"));
				petSkill.setPetSkGroup(rs.getInt("pet_skill_group"));
				petSkill.setPetSkLevel(rs.getInt("pet_skill_level"));
				petSkill.setPetGrade(rs.getInt("pet_grade"));

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
		return petSkill;
	}

	
	/**
	 * 得到所有技能,仅初始化 时使用
	 * 
	 * @param pet_skill_id
	 * @return	
	 */
	public HashMap<Integer,PetSkillVO> getAllPetSkill()
	{
		HashMap<Integer,PetSkillVO> map = null;
		int total_num = 0;
		String total_num_sql = "SELECT count(*) from pet_skill";
		
		PetSkillVO petSkill = null;
		String sql = "SELECT * FROM pet_skill";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
	
		logger.debug("得到所有技能="+sql);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(total_num_sql);
			if( rs.next() )
			{
				total_num = rs.getInt(1);
			}
			map = new HashMap<Integer,PetSkillVO>(total_num);
			
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				petSkill = new PetSkillVO();
				petSkill.setPetSkillId(rs.getInt("pet_skill_id"));
				petSkill.setPetSkillName(rs.getString("pet_skill_name"));
				petSkill.setPetSkillType(rs.getInt("pet_skill_type"));
				petSkill.setPetSkillArea(rs.getInt("pet_skill_area"));
				petSkill.setPetSkillGjDa(rs.getInt("pet_skill_gj_da"));
				
				
				petSkill.setPetSkillGjXiao(rs.getInt("pet_skill_gj_xiao"));
				petSkill.setPetSkillBewrite(rs.getString("pet_skill_bewrite"));
				// petSkill.setPetViolenceDropMultiple(rs.getInt("pet_skill_violence_drop_mutiple"));
				petSkill.setPetSkillSeveral(rs.getInt("pet_skill_several"));
				petSkill.setPetSkillMultiple(rs.getDouble("pet_skill_multiple"));
				petSkill.setPetInjureMultiple(rs.getDouble("pet_skill_injure_multiple"));
				
				
				petSkill.setPetViolenceDropMultiple(rs.getDouble("pet_skill_violence_drop_multiple"));
				petSkill.setPetSkGroup(rs.getInt("pet_skill_group"));
				petSkill.setPetSkLevel(rs.getInt("pet_skill_level"));
				petSkill.setPetGrade(rs.getInt("pet_grade"));
				
				map.put(rs.getInt("pet_skill_id"), petSkill);
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
		return map;
	}
	
	/**
	 * 通过id得到宠物技能名称
	 * 
	 * @param pet_skill_id
	 * @return
	 */
	public String getName1(int pet_skill_id)
	{
		String petSkillName = null;
		String sql = "SELECT * FROM pet_skill where  pet_skill_id="
				+ pet_skill_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petSkillName = StringUtil.isoToGBK(rs
						.getString("pet_skill_name"))
						+ ",";
			}
			if (petSkillName == null)
			{
				petSkillName = "";
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
		return petSkillName;
	}

	/**
	 * 得到宠物攻击加乘
	 */
	public double getInjureMultiple1(int pet_skill_id)
	{
		double petSkillInjureMultiple = 0;
		String sql = "SELECT * FROM pet_skill where  pet_skill_id="
				+ pet_skill_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petSkillInjureMultiple = rs
						.getDouble("pet_skill_injure_multiple");
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
		return petSkillInjureMultiple;
	}

	/**
	 * 得到宠物攻击类型 攻击次数
	 */
	public int getSeveral(int pet_skill_id)
	{
		int petSkillSeveral = 0;
		String sql = "SELECT * FROM pet_skill where  pet_skill_id="
				+ pet_skill_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petSkillSeveral = rs.getInt("pet_skill_several");
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
		return petSkillSeveral;
	}

	/**
	 * 得到宠物攻击类型 攻击次数
	 */
	public int getSeveral(String pet_skill_name)
	{
		int petSkillSeveral = 0;
		String sql = "SELECT * FROM pet_skill where  pet_skill_name='"
				+ StringUtil.gbToISO(pet_skill_name) + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petSkillSeveral = rs.getInt("pet_skill_several");
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
		return petSkillSeveral;
	}

	/**
	 * 得到宠物技能所在的组id
	 */
	public int getGroupID1(int pet_skill_id)
	{
		int pet_skill_group = 1;
		String sql = "SELECT pet_skill_group from pet_skill where  pet_skill_id="
				+ pet_skill_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pet_skill_group = rs.getInt("pet_skill_group");
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
		return pet_skill_group;
	}

	/**
	 * 得到宠物技能类型 返回 -1没有技能 0 为被动技能 1为主动技能
	 */
	public int getType1(int pet_skill_id)
	{
		int petSkillType = -1;
		String sql = "SELECT * FROM pet_skill where  pet_skill_id="
				+ pet_skill_id;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petSkillType = rs.getInt("pet_skill_type");
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
		return petSkillType;
	}

	/** 得到宠物身上的被动技能 */
	public List<PetSkillVO> getPassSkill(int skill_one, int skill_two,
			int skill_three, int skill_four, int skill_five)
	{
		PetSkillVO vo = null;
		List<PetSkillVO> list = new ArrayList<PetSkillVO>();
		String sql = "SELECT * FROM pet_skill where pet_skill_id in ("
				+ skill_one + "," + skill_two + "," + skill_three + ","
				+ skill_four + "," + skill_five + ") and pet_skill_type = 0";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new PetSkillVO();
				vo.setPetSkillMultiple(rs.getDouble("pet_skill_injure_multiple"));
				vo.setPetViolenceDropMultiple(rs.getDouble("pet_skill_violence_drop_multiple"));
				list.add(vo);
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}
}
