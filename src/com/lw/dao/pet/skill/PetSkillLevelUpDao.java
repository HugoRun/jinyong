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

	/** ����PPK�ͳ�ս������Ϣ�õ�����pet_pk */
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

	/** ����PET PK �õ�����ĵȼ� */
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

	/** ���ݼ���ID�õ�����ȼ� */
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

	/** ����pet_pk�õ�pet_id */
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

	/** ���ݼ���ID�õ��������� */
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

	/** ���ݼ�����ID�ͼ��ܵȼ�IDȡ������ID */
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

	/** ���ݼ�����ID�ͼ��ܵȼ�IDȡ������ѧϰ����Ҫ�ĵȼ� */
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

	/** ���ݼ���ID����������ID */
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

	/** ���ݼ���ID�õ����ܵȼ� */
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

	/** ���ݳ���ID�õ����＼����ID */
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

	/** �õ���������1���ļ��� */
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

	/** ����pet_pk�õ����＼����һ�ŵļ��� */
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

	/** ����pet_pk�õ����＼�������ŵļ��� */
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

	/** ����pet_pk�õ����＼�������ŵļ��� */
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

	/** ����pet_pk�õ����＼�����ĺŵļ��� */
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

	/** ����pet_pk�õ����＼������ŵļ��� */
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

	/** ����pet_pk�õ�������һ�ż������������� */
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

	/** ����pet_pk�õ������ڶ��ż������������� */
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

	/** ����pet_pk�õ����������ż������������� */
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

	/** ����pet_pk�õ��������ĺż������������� */
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

	/** ����pet_pk�õ���������ż������������� */
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

	/** ����pet_pk�õ�������һ�ż������ļ������� */
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

	/** ����pet_pk�õ������ڶ��ż������ļ������� */
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

	/** ����pet_pk�õ����������ż������ļ������� */
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

	/** ���ݳ��＼��ID�õ��������ĺż������ļ������� */
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

	/** ����pet_pk�õ���������ż������ļ������� */
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

	/** ���ݳ��＼�ܿ��Ʊ��еļ��� �õ����＼�ܵ���ʾ* */
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
