package com.ben.dao.pet;

import java.sql.ResultSet;
import java.util.HashMap;

import com.ben.vo.pet.pet.PetShapeVO;
import com.ben.vo.pet.pet.PetVO;
import com.ben.vo.petinfo.PetInfoVO;
import com.pub.db.jygamedb.Jygamedb;
import com.pub.db.mysql.SqlData;

/**
 * 功能:宠物
 * 
 * @author 侯浩军 11:29:22 AM
 */
public class PetDAO {
	Jygamedb con;
	SqlData conn;

	/**
	 * 通过NPCID 去找相关宠物表的信息
	 */
	public PetVO getPetView(int npcId) {
		try {
			con = new Jygamedb();
			String sql = "select * from pet where npc_id='" + npcId + "'";
			ResultSet rs = con.query(sql);
			PetVO vo = null;
			if (rs.next()) {
				vo = new PetVO();
				vo.setPetId(rs.getInt("pet_id"));
				vo.setNpcId(rs.getInt("npc_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetDropDa(rs.getDouble("pet_drop_da"));
				vo.setPetDropXiao(rs.getDouble("pet_drop_xiao"));
				vo.setPetWx(rs.getString("pet_wx"));
				vo.setPetWxValue(rs.getString("pet_wx_value"));
				vo.setPetIsAutoGrow(rs.getInt("pet_isAutoGrow"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setSkillControl(rs.getInt("skill_control")); 
			    vo.setPetSkillOne(rs.getInt("pet_skill_one"));
			    vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
			    vo.setPetSkillThree(rs.getInt("pet_skill_three"));
			    vo.setPetSkillFour(rs.getInt("pet_skill_four"));
			    vo.setPetSkillFive(rs.getInt("pet_skill_five")); 
			    /*******************TODO*******************/
			    vo.setPetViolenceDorp(rs.getDouble("pet_violence_drop"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	
	public PetVO getPetViewByPetID(int pet_id) {
		try {
			con = new Jygamedb();
			String sql = "select * from pet where pet_id='" + pet_id + "'";
			ResultSet rs = con.query(sql);
			PetVO vo = null;
			if (rs.next()) {
				vo = new PetVO();
				vo.setPetId(rs.getInt("pet_id"));
				vo.setNpcId(rs.getInt("npc_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetDropDa(rs.getDouble("pet_drop_da"));
				vo.setPetDropXiao(rs.getDouble("pet_drop_xiao"));
				vo.setPetWx(rs.getString("pet_wx"));
				vo.setPetWxValue(rs.getString("pet_wx_value"));
				vo.setPetIsAutoGrow(rs.getInt("pet_isAutoGrow"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setSkillControl(rs.getInt("skill_control")); 
			    vo.setPetSkillOne(rs.getInt("pet_skill_one"));
			    vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
			    vo.setPetSkillThree(rs.getInt("pet_skill_three"));
			    vo.setPetSkillFour(rs.getInt("pet_skill_four"));
			    vo.setPetSkillFive(rs.getInt("pet_skill_five")); 
			    /*******************TODO*******************/
			    vo.setPetViolenceDorp(rs.getDouble("pet_violence_drop"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	/**
	 * 通过宠物等级和宠物ID和宠物类型 去找宠物成长信息
	 */
	public PetShapeVO getPetShapeView(int petType, int petLevel) {
		try {
			con = new Jygamedb();
			String sql = "select * from pet_shape where pet_type='"+petType+"' and shape_rating='"+petLevel+"'";
			ResultSet rs = con.query(sql);
			PetShapeVO vo = null;
			if (rs.next()) {
				vo = new PetShapeVO();
				vo.setShapeId(rs.getInt("shape_id"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setShapeRating(rs.getInt("shape_rating"));
				vo.setShapeSale(rs.getInt("shape_sale"));
				vo.setShapeBenExperience(rs.getString("shape_ben_experience"));
				vo.setShapeXiaExperience(rs.getString("shape_xia_experience"));
				vo.setShapeAttackXiao(rs.getString("shape_attack_xiao"));
				vo.setShapeAttackDa(rs.getString("shape_attack_da"));
				vo.setShapeType(rs.getInt("shape_type"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	
	/**
	 * 通过NPCID 取出NPC类型
	 */
	public int npcType(int npcId) {
		try {
			con = new Jygamedb();
			String sql = "select npc_type from npc where npc_ID='" + npcId + "'";
			ResultSet rs = con.query(sql);
			int npcType = 0;
			if (rs.next()) {
				npcType = rs.getInt("npc_type");
			}
			return npcType;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}
	
	/**
	 * 通过petID 取出NPCId
	 */
	public int getNpcId(int petId) {
		try {
			con = new Jygamedb();
			String sql = "select npc_id from pet where pet_id='" + petId + "'";
			ResultSet rs = con.query(sql);
			int npc_id = 0;
			if (rs.next()) {
				npc_id = rs.getInt("npc_id");
			}
			return npc_id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return 0;
	}
	
	/**
	 * 返回宠物名称
	 */
	public String getPetName(int petId) {
		try {
			con = new Jygamedb();
			String sql = "select pet_name from pet where pet_id='" + petId + "'";
			ResultSet rs = con.query(sql);
			String pet_name = null;
			if (rs.next()) {
				pet_name = rs.getString("pet_name");
			}
			return pet_name;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
	/**
	 * 递减宠物体力
	 */
	public void petFatigue(int pPk) {
		try {
			conn = new SqlData();
			String sql = "update p_pet_info set pet_fatigue=pet_fatigue-1 where p_pk='"+pPk+"' and pet_isBring=1";
			conn.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {	
			conn.close();
		} 
	}

	/**
	 * 获得 所有宠物信息
	 * @return
	 */
	public HashMap<Integer, PetVO> getAllPet()
	{
		int total_num = 0;
		String total_num_sql = "select count(*) from pet";
		String sql = "select * from pet";
		HashMap<Integer,PetVO> map = null;
		PetVO vo = null;
		try {
			con = new Jygamedb();
			ResultSet rs = con.query(total_num_sql);
			if( rs.next() )
			{
				total_num = rs.getInt(1);
			}
			map = new HashMap<Integer,PetVO>(total_num);
			
			
			rs = con.query(sql);
			while (rs.next()) {
				vo = new PetVO();
				vo.setPetId(rs.getInt("pet_id"));
				vo.setNpcId(rs.getInt("npc_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetDropDa(rs.getDouble("pet_drop_da"));
				vo.setPetDropXiao(rs.getDouble("pet_drop_xiao"));
				vo.setPetWx(rs.getString("pet_wx"));
				vo.setPetWxValue(rs.getString("pet_wx_value"));
				vo.setPetIsAutoGrow(rs.getInt("pet_isAutoGrow"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setSkillControl(rs.getInt("skill_control")); 
			    vo.setPetSkillOne(rs.getInt("pet_skill_one"));
			    vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
			    vo.setPetSkillThree(rs.getInt("pet_skill_three"));
			    vo.setPetSkillFour(rs.getInt("pet_skill_four"));
			    vo.setPetSkillFive(rs.getInt("pet_skill_five")); 
			    /*******************TODO*******************/
			    vo.setPetViolenceDorp(rs.getDouble("pet_violence_drop"));
			    map.put(rs.getInt("pet_id"), vo);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
}
