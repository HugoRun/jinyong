package com.lw.dao.pet.skill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.pet.PetSkillControlVO;
import com.ls.ben.vo.info.pet.PetSkillVO;
import com.ls.pub.db.DBConnection;

public class PetSkillLevelUpDao extends DaoBase
{

	/** 根据PPK和出战宠物信息得到宠物pet_pk */
	public int getPetPetpk(int p_pk)
	{
		int pet_pk = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_pk from p_pet_info where p_pk =" + p_pk
				+ " and pet_isBring = 1 ";
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_pk = rs.getInt("pet_pk");
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
		return pet_pk;
	}

	/** 根据PET PK 得到宠物的等级 */
	public int getPetGradeByPetpk(int pet_pk)
	{
		int pet_grade = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_grade from p_pet_info where pet_pk =" + pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_grade = rs.getInt("pet_grade");
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
		return pet_grade;
	}

	/** 根据技能ID得到宠物等级 */
	public int getPetGrade(int pet_skill_id)
	{
		int pet_Grade = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_Grade from pet_skill where pet_skill_id ="
				+ pet_skill_id;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_Grade = rs.getInt("pet_Grade");
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
		return pet_Grade;

	}

	/** 根据pet_pk得到pet_id */
	public int getPetPetidByPetpk(int pet_pk)
	{
		int pet_id = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_id from p_pet_info where pet_pk =" + pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_id = rs.getInt("pet_id");
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
		return pet_id;
	}

	/** 根据技能ID得到技能名称 */
	public String getPetSkillName(int pet_skill_id)
	{
		String pet_skill_name = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_name from pet_skill where pet_skill_id ="
				+ pet_skill_id;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_name = rs.getString("pet_skill_name");
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
		return pet_skill_name;
	}

	/** 根据技能组ID和技能等级ID取出技能ID */
	public int getSkillIDByLevel(int pet_skill_group, int pet_skill_level)
	{
		int pet_skill_id = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_id from pet_skill where pet_skill_group ="
				+ pet_skill_group + " and pet_skill_level =" + pet_skill_level;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_id = rs.getInt("pet_skill_id");
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
		return pet_skill_id;
	}

	/** 根据技能组ID和技能等级ID取出技能学习所需要的等级 */
	public int getSkillGradeByLevel(int pet_skill_group, int pet_skill_level)
	{
		int pet_grade = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_grade from pet_skill where pet_skill_group ="
				+ pet_skill_group + " and pet_skill_level =" + pet_skill_level;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_grade = rs.getInt("pet_grade");
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
		return pet_grade;
	}

	/** 根据技能ID到到技能组ID */
	public int getPetSkGroup1(int pet_skill_id)
	{
		int pet_skill_group = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_group from pet_skill where pet_skill_id ="
				+ pet_skill_id;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
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

	/** 根据技能ID得到技能等级 */
	public int getPetSkLevel1(int pet_skill_id)
	{
		int pet_skill_level = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_level from pet_skill where pet_skill_id ="
				+ pet_skill_id;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_level = rs.getInt("pet_skill_level");
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
		return pet_skill_level;

	}

	/** 根据宠物ID得到宠物技能组ID */
	public List getPetControlGroup(int pet_id)
	{
		String sql = "select pet_skill_group from pet_skill_control where pet_id ="
				+ pet_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List list = new ArrayList();
			while (rs.next())
			{
				PetSkillControlVO vo = new PetSkillControlVO();
				vo.setPetSkGroup(rs.getInt("pet_skill_group"));
				list.add(vo);

			}
			rs.close();
			stmt.close();
			return list;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return null;
	}

	/** 得到宠物所有1级的技能 */
	public List getPetSkLvOne()
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_id from pet_skill where pet_skill_level = 1 ";
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List list = new ArrayList();
			while (rs.next())
			{
				PetSkillVO vo = new PetSkillVO();
				vo.setPetSkillId(rs.getInt("pet_skill_id"));
				list.add(vo);

			}
			rs.close();
			stmt.close();
			return list;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return null;
	}

	/** 根据pet_pk得到宠物技能栏一号的技能 */
	public int getPetSkOne(int pet_pk)
	{
		int pet_skill_one = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_one from p_pet_info where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_one = rs.getInt("pet_skill_one");
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
		return pet_skill_one;

	}

	/** 根据pet_pk得到宠物技能栏二号的技能 */
	public int getPetSkTwo(int pet_pk)
	{
		int pet_skill_two = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_two from p_pet_info where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_two = rs.getInt("pet_skill_two");
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
		return pet_skill_two;

	}

	/** 根据pet_pk得到宠物技能栏三号的技能 */
	public int getPetSkThree(int pet_pk)
	{
		int pet_skill_three = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_three from p_pet_info where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_three = rs.getInt("pet_skill_three");
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
		return pet_skill_three;

	}

	/** 根据pet_pk得到宠物技能栏四号的技能 */
	public int getPetSkFour(int pet_pk)
	{
		int pet_skill_four = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_four from p_pet_info where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_four = rs.getInt("pet_skill_four");
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
		return pet_skill_four;

	}

	/** 根据pet_pk得到宠物技能栏五号的技能 */
	public int getPetSkFive(int pet_pk)
	{
		int pet_skill_five = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		String sql = "select pet_skill_five from p_pet_info where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_five = rs.getInt("pet_skill_five");
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
		return pet_skill_five;

	}

	/** 根据pet_pk得到宠物在一号技能栏技能升级 */
	public void updatePetSkOne(int pet_pk, int pet_skill_id)
	{

		String sql = "update p_pet_info set pet_skill_one = " + pet_skill_id
				+ " where pet_pk =" + pet_pk;
		logger.debug(sql);

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在二号技能栏技能升级 */
	public void updatePetSkTwo(int pet_pk, int pet_skill_id)
	{

		String sql = "update p_pet_info set pet_skill_two = " + pet_skill_id
				+ " where pet_pk =" + pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在三号技能栏技能升级 */
	public void updatePetSkThree(int pet_pk, int pet_skill_id)
	{

		String sql = "update p_pet_info set pet_skill_three = " + pet_skill_id
				+ " where pet_pk =" + pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在四号技能栏技能升级 */
	public void updatePetSkFour(int pet_pk, int pet_skill_id)
	{

		String sql = "update p_pet_info set pet_skill_four = " + pet_skill_id
				+ " where pet_pk =" + pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在五号技能栏技能升级 */
	public void updatePetSkFive(int pet_pk, int pet_skill_id)
	{

		String sql = "update p_pet_info set pet_skill_five = " + pet_skill_id
				+ " where pet_pk =" + pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在一号技能栏的技能忘记 */
	public void deletePetSkOne(int pet_pk)
	{

		String sql = "update p_pet_info set pet_skill_one = 0 where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在二号技能栏的技能忘记 */
	public void deletePetSkTwo(int pet_pk)
	{

		String sql = "update p_pet_info set pet_skill_two = 0 where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在三号技能栏的技能忘记 */
	public void deletePetSkThree(int pet_pk)
	{

		String sql = "update p_pet_info set pet_skill_three = 0 where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据宠物技能ID得到宠物在四号技能栏的技能忘记 */
	public void deletePetSkFour(int pet_pk)
	{

		String sql = "update p_pet_info set pet_skill_four = 0 where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据pet_pk得到宠物在五号技能栏的技能忘记 */
	public void deletePetSkFive(int pet_pk)
	{

		String sql = "update p_pet_info set pet_skill_five = 0 where pet_pk ="
				+ pet_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		Connection conn = dbConn.getConn();
		try
		{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

	/** 根据宠物技能控制表中的技能 得到宠物技能的显示* */
	public List<Integer> getPetSkillControl(int pet_id)
	{
		List<Integer> list = new ArrayList<Integer>();
		int pet_skill_id = 0;
		String sql = "select pet_skill_id from pet_skill_control where pet_id = "
				+ pet_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			Connection conn = dbConn.getConn();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				pet_skill_id = rs.getInt("pet_skill_id");
				list.add(pet_skill_id);
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
		return list;
	}
}
