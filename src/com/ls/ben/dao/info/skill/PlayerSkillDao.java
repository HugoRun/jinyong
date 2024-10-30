package com.ls.ben.dao.info.skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:u_skill_info
 * @author 刘帅 4:44:01 PM
 */
public class PlayerSkillDao extends BasicDaoSupport<PlayerSkillVO>
{
	public PlayerSkillDao()
	{
		super("u_skill_info", DBConnection.GAME_USER_DB);
	}

	
	/**
	 * 清空玩家的技能
	 */
	public void clear(int p_pk)
	{
		String update_sql = "delete from u_skill_info where p_pk="+p_pk;
		super.executeUpdateSql(update_sql);
	}
	
	/**
	 * 根据角色p_pk返回角色所拥有的所有攻击技能
	 * @param p_pk
	 * @return List
	 */
	public List<PlayerSkillVO> getAttackSkills(int p_pk)
	{
		List<PlayerSkillVO> skills = new ArrayList<PlayerSkillVO>();
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk+" and sk_id<>1 and sk_type=1";
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
				skills.add(vo);
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
		
		return skills;
	}
	
	/**
	 * 根据角色p_pk返回角色所拥有的所有技能
	 * @param p_pk
	 * @return List
	 */
	public List<PlayerSkillVO> getPlayerSkills(int p_pk)
	{
		List<PlayerSkillVO> skills = new ArrayList<PlayerSkillVO>();
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk;
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
				skills.add(vo);
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

		return skills;
	}

	/**
	 * 根据角色p_pk返回角色所拥有的所有技能
	 * 
	 * @param p_pk
	 * @return List
	 */
	public HashMap<Integer, PlayerSkillVO> getPlayerAllSkill(int p_pk)
	{
		HashMap<Integer, PlayerSkillVO> skills = new HashMap<Integer, PlayerSkillVO>();
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk;
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
				skills.put(rs.getInt("s_pk"), vo);
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

		return skills;
	}

	/**
	 * 根据角色p_pk返回角色所拥有的所有技能,除了宠物捕捉技能和生活技能
	 * 
	 * @param p_pk
	 * @return List
	 */
	public List<PlayerSkillVO> getSkillsWithoutCatchPet(int p_pk)
	{
		List<PlayerSkillVO> skills = new ArrayList<PlayerSkillVO>();
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk
				+ " and sk_type in (1,4)";
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
				skills.add(vo);
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

		return skills;
	}

	/**
	 * 更新技能使用时间和熟练度
	 * 
	 * @param p_pk
	 * @param sk_id
	 * @return
	 */
	public int updateUsetimeAndSleight(int p_pk, int sk_id)
	{
		int result = -1;
		String sql = "update u_skill_info set sk_sleight = sk_sleight+1 ,sk_usetime=now() where p_pk="
				+ p_pk + " and sk_id=" + sk_id;
		logger.debug("更新技能使用时间和熟练度=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
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
		return result;
	}

	/**
	 * 
	 * @param s_pk
	 * @return
	 */
	public PlayerSkillVO getById(int s_pk)
	{
		PlayerSkillVO vo = null;
		String sql = "SELECT * FROM u_skill_info where s_pk=" + s_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
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
			}
			rs.close();
			stmt.close();
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/**
	 * 
	 * @param s_pk
	 * @return
	 */
	public PlayerSkillVO getPlayerSkillInfo(int p_pk, int sk_id)
	{
		PlayerSkillVO vo = null;
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk
				+ " and sk_id=" + sk_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
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
			}
			rs.close();
			stmt.close();
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/**
	 * 得到技能名字
	 * 
	 * @param s_pk
	 * @return
	 */
	public String getNameById(int s_pk)
	{
		String name = "";
		String sql = "SELECT sk_name from u_skill_info where s_pk=" + s_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				name = rs.getString("sk_name");
			}
			rs.close();
			stmt.close();

		}
		catch (Exception e)
		{
			logger.debug(sql);
		}
		finally
		{
			dbConn.closeConn();
		}
		return name;
	}

	/**
	 * 学习技能
	 */
	public void add(PlayerSkillVO playerSkill)
	{
		if (playerSkill == null)
		{
			logger.debug("playerSkill空");
			return;
		}
		String sql = "INSERT INTO u_skill_info values (null,?,?,?,0,now(),now(),?,?,?,?,?,?,?,?,?,?,?)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, playerSkill.getPPk());
			ps.setInt(i++, playerSkill.getSkId());
			ps.setString(i++, playerSkill.getSkName());
			ps.setInt(i++, playerSkill.getSkType());
			ps.setDouble(i++, playerSkill.getSkGjMultiple());
			ps.setDouble(i++, playerSkill.getSkFyMultiple());
			ps.setDouble(i++, playerSkill.getSkHpMultiple());
			ps.setDouble(i++, playerSkill.getSkMpMultiple());
			ps.setDouble(i++, playerSkill.getSkBjMultiple());
			ps.setInt(i++, playerSkill.getSkGjAdd());
			ps.setInt(i++, playerSkill.getSkFyAdd());
			ps.setInt(i++, playerSkill.getSkHpAdd());
			ps.setInt(i++, playerSkill.getSkMpAdd());
			ps.setInt(i++, playerSkill.getSkGroup());

			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			if( rs.next() )
			{
				playerSkill.setSPk(rs.getInt(1));
			}
			rs.close();
			ps.close();
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

	/**
	 * 得到玩家技能id为sk_id的技能
	 * 
	 * @param s_pk
	 * @return
	 */
	public PlayerSkillVO getBySkId(int p_pk, int sk_id)
	{
		PlayerSkillVO vo = null;
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk
				+ " and sk_id=" + sk_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerSkillVO();
				vo.setSPk(rs.getInt("s_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setSkId(rs.getInt("sk_id"));
				vo.setSkType(rs.getInt("sk_type"));
				vo.setSkName(rs.getString("sk_name"));
				vo.setSkSleight(rs.getInt("sk_sleight"));
				vo.setSkUsetime(rs.getTimestamp("sk_usetime"));

			}
			rs.close();
			stmt.close();
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/**
	 * 判断是否有捕捉宠物的技能,技能的id为1
	 * 
	 * @param p_pk
	 * @return
	 */
	public boolean isHaveCatchPetSkill(int p_pk)
	{
		boolean result = false;
		String sql = "SELECT sk_id from u_skill_info where p_pk=" + p_pk
				+ " and sk_id=1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = true;
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
		return result;
	}

	/**
	 * 根据ppk和技能id获得sk_id
	 * 
	 * @param skill_id
	 * @param p_pk
	 * @return
	 */
	public int getIdByName(String skill_id, String p_pk)
	{
		String sql = "SELECT s_pk from u_skill_info where p_pk=" + p_pk
				+ " and sk_id='" + skill_id + "'";
		int sk_id = 0;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				sk_id = rs.getInt("s_pk");
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
		return sk_id;
	}

	/**
	 * 根据技能组得到技能信息
	 * 
	 * @param p_pk
	 * @param sk_group
	 * @return
	 */
	public PlayerSkillVO getSkillInfoByGroup(int p_pk, int sk_group)
	{
		PlayerSkillVO vo = null;
		String sql = "SELECT * FROM u_skill_info where p_pk=" + p_pk
				+ " and sk_group=" + sk_group;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new PlayerSkillVO();
				vo.setSPk(rs.getInt("s_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setSkId(rs.getInt("sk_id"));
				vo.setSkName(rs.getString("sk_name"));
				vo.setSkSleight(rs.getInt("sk_sleight"));
				vo.setSkUsetime(rs.getTimestamp("sk_usetime"));
			}
			rs.close();
			stmt.close();
			return vo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/**
	 * 更新生活技能使用时间和熟练度
	 * 
	 * @param p_pk
	 * @param sk_Group
	 * @param sleight
	 * @return
	 */
	public int updateUsetimeAndSleight(int p_pk, int sk_group, int sleight)
	{
		int result = -1;
		String sql = "update u_skill_info set sk_sleight = " + sleight
				+ " ,sk_usetime=now() where p_pk=" + p_pk + " and sk_group="
				+ sk_group;
		logger.debug("更新技能使用时间和熟练度=" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
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
		return result;
	}

	// 更新被动技能
	public void updatePassSkill(int p_pk, SkillVO vo,int sk_id_old)
	{
		String sql = "update u_skill_info set sk_id = '" + vo.getSkId()
				+ "', sk_name = '" + vo.getSkName() + "', sk_gj_multiple = '"
				+ vo.getSkGjMultiple() + "', sk_fy_multiple = '"
				+ vo.getSkFyMultiple() + "', sk_hp_multiple = '"
				+ vo.getSkHpMultiple() + "', sk_mp_multiple = '"
				+ vo.getSkMpMultiple() + "', sk_bj_multiple = '"
				+ vo.getSkBjMultiple() + "', sk_gj_add = '" + vo.getSkGjAdd()
				+ "', sk_fy_add = '" + vo.getSkFyAdd() + "', sk_hp_add = '"
				+ vo.getSkHpAdd() + "', sk_mp_add = '" + vo.getSkMpAdd()
				+ "' where p_pk = '" + p_pk + "' and sk_id = '" + sk_id_old + "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
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
	

	@Override
	protected PlayerSkillVO loadData(ResultSet rs) throws SQLException
	{
		PlayerSkillVO u_skill = new PlayerSkillVO();
		u_skill.setSPk(rs.getInt("s_pk"));
		u_skill.setPPk(rs.getInt("p_pk"));
		u_skill.setSkId(rs.getInt("sk_id"));
		u_skill.setSkName(StringUtil.isoToGBK(rs.getString("sk_name")));
		u_skill.setSkSleight(rs.getInt("sk_sleight"));
		u_skill.setSkUsetime(rs.getTimestamp("sk_usetime"));
		u_skill.setSkType(rs.getInt("sk_type"));
		u_skill.setSkGjMultiple(rs.getDouble("sk_gj_multiple"));
		u_skill.setSkFyMultiple(rs.getDouble("sk_fy_multiple"));
		u_skill.setSkHpMultiple(rs.getDouble("sk_hp_multiple"));
		u_skill.setSkMpMultiple(rs.getDouble("sk_mp_multiple"));
		u_skill.setSkBjMultiple(rs.getDouble("sk_bj_multiple"));
		u_skill.setSkGjAdd(rs.getInt("sk_gj_add"));
		u_skill.setSkFyAdd(rs.getInt("sk_fy_add"));
		u_skill.setSkHpAdd(rs.getInt("sk_hp_add"));
		u_skill.setSkMpAdd(rs.getInt("sk_mp_add"));
		u_skill.setSkGroup(rs.getInt("sk_group"));
		return u_skill;
	}
}
