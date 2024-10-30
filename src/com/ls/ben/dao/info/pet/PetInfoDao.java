package com.ls.ben.dao.info.pet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:p_pet_info
 * 
 * @author 刘帅
 * 
 * 9:18:15 AM
 */
public class PetInfoDao extends DaoBase
{
	/**
	 * 得到p_pk的随身带的宠物，返回null则代表没有宠物在身上
	 * 
	 * @param p_pk
	 * @return
	 */
	public PetInfoVO getBringPetByPpk(int p_pk)
	{
		PetInfoVO petInfo = null;
		String sql = "SELECT * FROM  p_pet_info where p_pk=" + p_pk + " and pet_isBring=1 limit 1";
		logger.debug("得到p_pk的随身带的宠物="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petInfo = new PetInfoVO();
				petInfo.setPetPk(rs.getInt("pet_pk"));
				petInfo.setPPk(p_pk);
				petInfo.setPetId(rs.getInt("pet_id"));
				// petInfo.setNpcId(rs.getInt("npc_id"));
				petInfo.setPetName(rs.getString("pet_name"));
				petInfo.setPetNickname(rs.getString("pet_nickname"));

				petInfo.setPetGrade(rs.getInt("pet_grade"));
				petInfo.setPetGjDa(rs.getInt("pet_gj_da"));
				petInfo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				petInfo.setPetExp(rs.getInt("pet_exp"));
				petInfo.setPetXiaExp(rs.getInt("pet_xia_exp"));

				petInfo.setPetFatigue(rs.getInt("pet_fatigue"));
				petInfo.setPetGrow(rs.getDouble("pet_grow"));
				petInfo.setPetImg(rs.getString("pet_img"));
				petInfo.setPetIsBring(rs.getInt("pet_isBring"));
				petInfo.setPetLife(rs.getInt("pet_life"));
				petInfo.setPetLonge(rs.getInt("pet_longe"));

				petInfo.setPetSale(rs.getInt("pet_sale"));

				petInfo.setWx(rs.getInt("pet_wx"));
				petInfo.setWxValue(rs.getInt("pet_wx_value"));

				petInfo.setPetSkillOne(rs.getInt("pet_skill_one"));
				petInfo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				petInfo.setPetSkillThree(rs.getInt("pet_skill_three"));
				petInfo.setPetSkillFour(rs.getInt("pet_skill_four"));
				petInfo.setPetSkillFive(rs.getInt("pet_skill_five"));

				petInfo.setPetType(rs.getInt("pet_type"));

				/** **********************TODO*************** */
				petInfo.setPetViolenceDrop(rs.getDouble("pet_violence_drop"));
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return petInfo;
	}

	/**
	 * 得到p_pk的随身带的宠物，返回null则代表没有宠物在身上
	 * 
	 * @param p_pk
	 * @return
	 */
	public int getNumOfPet(int p_pk)
	{
		int pet_num = 0;
		String sql = "SELECT count(*) as sum from  p_pet_info where  p_pk="
				+ p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pet_num = rs.getInt("sum");
			}
			stmt.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return pet_num;
	}

	/**
	 * 得到可以被洗的宠物，没有在身上的且十级一上的包括十级
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<PetInfoVO> getInitPetList(int p_pk)
	{
		List<PetInfoVO> list = new ArrayList<PetInfoVO>();
		PetInfoVO petInfo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT pet_pk,pet_name,pet_nickname,pet_wx,pet_type from p_pet_info where  p_pk='"
				+ p_pk + "' and pet_isBring<>1";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				petInfo = new PetInfoVO();
				petInfo.setPetPk(rs.getInt("pet_pk"));
				petInfo.setPPk(p_pk);
				petInfo.setPetName(rs.getString("pet_name"));
				petInfo.setPetNickname(rs.getString("pet_nickname"));

				petInfo.setWx(rs.getInt("pet_wx"));

				petInfo.setPetType(rs.getInt("pet_type"));
				list.add(petInfo);
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
		return list;
	}
	
	/**
	 * 得到可以被洗的宠物，没有在身上的且十级一上的包括十级
	 * 
	 * @param p_pk
	 * @return
	 
	public HashMap<Integer,PetInfoVO> getAllPetList(int p_pk)
	{
		HashMap<Integer,PetInfoVO> map = new HashMap<Integer,PetInfoVO>();
		PetInfoVO petInfo = null;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		String sql = "SELECT pet_pk,pet_name,pet_nickname,pet_wx,pet_type from p_pet_info where  p_pk='"
				+ p_pk + "' and pet_isBring<>1";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				petInfo = new PetInfoVO();
				petInfo.setPetPk(rs.getInt("pet_pk"));
				petInfo.setPPk(p_pk);
				petInfo.setPetId(rs.getInt("pet_id"));
				petInfo.setNpcId(rs.getInt("npc_id"));
				petInfo.setPetName(rs.getString("pet_name"));
				petInfo.setPetNickname(rs.getString("pet_nickname"));

				petInfo.setPetGrade(rs.getInt("pet_grade"));
				petInfo.setPetGjDa(rs.getInt("pet_gj_da"));
				petInfo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				petInfo.setPetExp(rs.getInt("pet_exp"));
				petInfo.setPetXiaExp(rs.getInt("pet_xia_exp"));

				petInfo.setPetFatigue(rs.getInt("pet_fatigue"));
				petInfo.setPetGrow(rs.getDouble("pet_grow"));
				petInfo.setPetImg(rs.getString("pet_img"));
				petInfo.setPetIsBring(rs.getInt("pet_isBring"));
				petInfo.setPetLife(rs.getInt("pet_life"));
				petInfo.setPetLonge(rs.getInt("pet_longe"));

				petInfo.setPetSale(rs.getInt("pet_sale"));
				petInfo.setWx(rs.getInt("pet_wx"));
				petInfo.setWxValue(rs.getInt("pet_wx_value"));

				petInfo.setPetSkillOne(rs.getInt("pet_skill_one"));
				petInfo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				petInfo.setPetSkillThree(rs.getInt("pet_skill_three"));
				petInfo.setPetSkillFour(rs.getInt("pet_skill_four"));
				petInfo.setPetSkillFive(rs.getInt("pet_skill_five"));

				petInfo.setPetType(rs.getInt("pet_type"));
				petInfo.setPetViolenceDrop(rs.getDouble("pet_violence_drop"));
				map.put(rs.getInt("pet_pk"), petInfo);
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
		return list;
	}*/

	/**
	 * 更改宠物昵称
	 * 
	 * @param pet_pk
	 * @param pet_nickname
	 */
	public void updateNickName(int pet_pk, String pet_nickname)
	{
		String sql = "update p_pet_info set pet_nickname='"
				+ StringUtil.gbToISO(pet_nickname) + "'  where pet_pk="
				+ pet_pk;
		logger.debug(sql);
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
	

	/**
	 * 给宠物名字上加上*号
	 * 
	 * @param pet_pk
	 * @param pet_nickname
	 */
	public void updatePetName(String petpk)
	{
		String sql = "update p_pet_info set pet_name= concat(pet_name,'*') where pet_pk="+petpk;

    	logger.debug(sql);
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

	/**
	 * 得到宠物
	 * 
	 * @param pet_pk
	 * @return
	 */
	public PetInfoVO getPet(int pet_pk)
	{
		PetInfoVO petInfo = null;
		String sql = "SELECT * FROM  p_pet_info where pet_pk=" + pet_pk + "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				petInfo = new PetInfoVO();
				petInfo.setPetPk(pet_pk);
				petInfo.setPPk(rs.getInt("p_pk"));
				petInfo.setPetId(rs.getInt("pet_id"));
				petInfo.setPetName(rs.getString("pet_name"));
				petInfo.setPetNickname(rs.getString("pet_nickname"));

				petInfo.setPetGrade(rs.getInt("pet_grade"));
				petInfo.setPetGjDa(rs.getInt("pet_gj_da"));
				petInfo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				petInfo.setPetExp(rs.getInt("pet_exp"));
				petInfo.setPetBenExp(rs.getInt("pet_ben_exp"));
				petInfo.setPetXiaExp(rs.getInt("pet_xia_exp"));

				petInfo.setPetFatigue(rs.getInt("pet_fatigue"));
				petInfo.setPetGrow(rs.getDouble("pet_grow"));
				petInfo.setPetImg(rs.getString("pet_img"));
				petInfo.setPetIsBring(rs.getInt("pet_isBring"));
				petInfo.setPetLife(rs.getInt("pet_life"));
				petInfo.setPetLonge(rs.getInt("pet_longe"));
				petInfo.setPetSale(rs.getInt("pet_sale"));
				petInfo.setPetType(rs.getInt("pet_type"));

				petInfo.setWx(rs.getInt("pet_wx"));
				petInfo.setWxValue(rs.getInt("pet_wx_value"));

				petInfo.setPetSkillOne(rs.getInt("pet_skill_one"));
				petInfo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				petInfo.setPetSkillThree(rs.getInt("pet_skill_three"));
				petInfo.setPetSkillFour(rs.getInt("pet_skill_four"));
				petInfo.setPetSkillFive(rs.getInt("pet_skill_five"));

				petInfo.setPetInitNum(rs.getInt("pet_init_num"));
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
		return petInfo;
	}

	/**
	 * 洗宠物
	 * 
	 * @param pet
	 */
	public void initPet(int pet_pk, String pet_grow, int pet_xia_exp,
			int gj_xiao, int gj_da, int pet_sale, int pet_skill_one,
			int pet_skill_two, int pet_skill_three, int pet_skill_four,
			int pet_skill_five,String xing)
	{
		String sql = "update p_pet_info set " + "pet_nickname=concat(pet_name,'"+xing+"'),pet_init_num=pet_init_num+1,"
				+ "pet_grade=1," + "pet_exp=0," + "pet_ben_exp=0,"
				+ "pet_xia_exp=" + pet_xia_exp + ",pet_gj_xiao=" + gj_xiao
				+ ",pet_gj_da=" + gj_da + ",pet_sale=" + pet_sale
				+ ",pet_grow=" + pet_grow + ",pet_skill_one=" + pet_skill_one
				+ ",pet_skill_two=" + pet_skill_two + ",pet_skill_three="
				+ pet_skill_three + ",pet_skill_four=" + pet_skill_four
				+ ",pet_skill_five=" + pet_skill_five + "  where pet_pk="
				+ pet_pk;

		logger.debug(sql);
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
	
	//查看经验排名前十的宠物
	public List<PetInfoVO> fintbyExp(){
		List<PetInfoVO> list = new ArrayList<PetInfoVO>();
		String sql = "SELECT * FROM  p_pet_info p order by p.pet_exp desc limit 10";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PetInfoVO petInfo = new PetInfoVO();
				petInfo.setPetPk(rs.getInt("pet_pk"));
				petInfo.setPPk(rs.getInt("p_pk"));
				petInfo.setPetId(rs.getInt("pet_id"));
				petInfo.setPetName(rs.getString("pet_name"));
				petInfo.setPetNickname(rs.getString("pet_nickname"));

				petInfo.setPetGrade(rs.getInt("pet_grade"));
				petInfo.setPetGjDa(rs.getInt("pet_gj_da"));
				petInfo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				petInfo.setPetExp(rs.getInt("pet_exp"));
				petInfo.setPetBenExp(rs.getInt("pet_ben_exp"));
				petInfo.setPetXiaExp(rs.getInt("pet_xia_exp"));

				petInfo.setPetFatigue(rs.getInt("pet_fatigue"));
				petInfo.setPetGrow(rs.getDouble("pet_grow"));
				petInfo.setPetImg(rs.getString("pet_img"));
				petInfo.setPetIsBring(rs.getInt("pet_isBring"));
				petInfo.setPetLife(rs.getInt("pet_life"));
				petInfo.setPetLonge(rs.getInt("pet_longe"));
				petInfo.setPetSale(rs.getInt("pet_sale"));
				petInfo.setPetType(rs.getInt("pet_type"));

				petInfo.setWx(rs.getInt("pet_wx"));
				petInfo.setWxValue(rs.getInt("pet_wx_value"));

				petInfo.setPetSkillOne(rs.getInt("pet_skill_one"));
				petInfo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				petInfo.setPetSkillThree(rs.getInt("pet_skill_three"));
				petInfo.setPetSkillFour(rs.getInt("pet_skill_four"));
				petInfo.setPetSkillFive(rs.getInt("pet_skill_five"));

				petInfo.setPetInitNum(rs.getInt("pet_init_num"));
				list.add(petInfo);
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
	
	//查看攻击排名前十的宠物
	public List<PetInfoVO> fintbyGONGji(){
		List<PetInfoVO> list = new ArrayList<PetInfoVO>();
		String sql = "SELECT * FROM  p_pet_info p order by p.pet_gj_da desc limit 10";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PetInfoVO petInfo = new PetInfoVO();
				petInfo.setPetPk(rs.getInt("pet_pk"));
				petInfo.setPPk(rs.getInt("p_pk"));
				petInfo.setPetId(rs.getInt("pet_id"));
				petInfo.setPetName(rs.getString("pet_name"));
				petInfo.setPetNickname(rs.getString("pet_nickname"));

				petInfo.setPetGrade(rs.getInt("pet_grade"));
				petInfo.setPetGjDa(rs.getInt("pet_gj_da"));
				petInfo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				petInfo.setPetExp(rs.getInt("pet_exp"));
				petInfo.setPetBenExp(rs.getInt("pet_ben_exp"));
				petInfo.setPetXiaExp(rs.getInt("pet_xia_exp"));

				petInfo.setPetFatigue(rs.getInt("pet_fatigue"));
				petInfo.setPetGrow(rs.getDouble("pet_grow"));
				petInfo.setPetImg(rs.getString("pet_img"));
				petInfo.setPetIsBring(rs.getInt("pet_isBring"));
				petInfo.setPetLife(rs.getInt("pet_life"));
				petInfo.setPetLonge(rs.getInt("pet_longe"));
				petInfo.setPetSale(rs.getInt("pet_sale"));
				petInfo.setPetType(rs.getInt("pet_type"));

				petInfo.setWx(rs.getInt("pet_wx"));
				petInfo.setWxValue(rs.getInt("pet_wx_value"));

				petInfo.setPetSkillOne(rs.getInt("pet_skill_one"));
				petInfo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				petInfo.setPetSkillThree(rs.getInt("pet_skill_three"));
				petInfo.setPetSkillFour(rs.getInt("pet_skill_four"));
				petInfo.setPetSkillFive(rs.getInt("pet_skill_five"));

				petInfo.setPetInitNum(rs.getInt("pet_init_num"));
				list.add(petInfo);
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
	//查看自己的宠物经验排名
	public int findOwnExp(Object p_pk){
		int paimin = 0;
		if(p_pk==null){
			return paimin;
		}
		String sql = "SELECT count(*) from p_pet_info p where p.pet_exp >= (select max(pet_exp) from p_pet_info where p_pk = "+p_pk+")";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				paimin = rs.getInt(1);
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
			return paimin;
		}
	}
	
	//查看自己的宠物攻击排名
	public int findGjExp(Object p_pk){
		int paimin = 0;
		if(p_pk==null){
			return paimin;
		}
		String sql = "SELECT count(*) from p_pet_info p where p.pet_gj_da >= (select max(pet_gj_da) from p_pet_info where p_pk = "+p_pk+")";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				paimin = rs.getInt(1);
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
			return paimin;
		}
	}
}
