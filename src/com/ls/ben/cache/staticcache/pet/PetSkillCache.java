package com.ls.ben.cache.staticcache.pet;

import java.util.HashMap;
import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.info.pet.PetSkillVO;

/**
 * 保存宠物技能 在内存中,以便随时取用
 * @author Administrator	
 *
 */
public class PetSkillCache  extends CacheBase
{
	public static String PETSKILL = "pet_skill";
	
	
	/**
	 * 通过id得到 宠物技能信息
	 * @param scene_id
	 * @return
	 */
	public PetSkillVO getPetSkillById(int pet_skill_id)
	{
		logger.debug("得到一个宠物技能信息");
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		logger.debug("petskill:"+petskill.getPetSkillName());
		return petskill;
	}
	
	/**
	 * 得到宠物技能的类型
	 * @param pet_skill_id
	 */
	public int getPetSkillType(int pet_skill_id) {
		
		logger.debug("得到一个宠物技能的类型="+pet_skill_id);
		
		if (pet_skill_id == 0) {
			return 0;
		}
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		
		logger.debug("petskilltype:"+petskill.getPetSkillType());
		return petskill.getPetSkillType();
	}
		
	/**
	 * 获得宠物技能组id
	 * @param pet_skill_id
	 * @return
	 */
	public int getGroupID(int pet_skill_id)
	{
		logger.debug("得到一个宠物技能组id="+pet_skill_id);
		
		if (pet_skill_id == 0) {
			return 0;
		}
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		
		logger.debug("getPetSkGroup:"+petskill.getPetSkGroup());
		return petskill.getPetSkGroup();
	}

	/**
	 * 获得宠物技能名字
	 * @param pet_skill_id
	 * @return
	 */
	public String getName(int pet_skill_id)
	{
		logger.debug("得到一个宠物技能名字="+pet_skill_id);
		
		if (pet_skill_id == 0) {
			return "";
		}
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		
		logger.debug("getPetSkillName:"+petskill.getPetSkillName());
		return petskill.getPetSkillName()+",";
		
	}	
	
	/**
	 * 获得宠物技能等级
	 * @param pet_skill_id
	 * @return
	 */
	public int getPetSkLevel(int pet_skill_id) {
		logger.debug("得到一个宠物技能等级="+pet_skill_id);
		
		if (pet_skill_id == 0) {
			return 0;
		}
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		
		logger.debug("getPetSkLevel:"+petskill.getPetSkLevel());
		return petskill.getPetSkLevel();
	}
	
	
	/**
	 * 得到宠物攻击加乘
	 * @param pet_skill_id
	 * @return
	 */
	public double getInjureMultiple(int pet_skill_id) {
		logger.debug("得到宠物攻击加乘="+pet_skill_id);
		
		if (pet_skill_id == 0) {
			return 0;
		}
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		
		logger.debug("getPetInjureMultiple:"+petskill.getPetInjureMultiple());
		return petskill.getPetInjureMultiple();
	}
	
	
	/**
	 * 得到宠物攻击次数
	 * @param pet_skill_id
	 * @return
	 */
	public int getSeveral(String pet_skill_name) {
		logger.debug("得到宠物攻击加乘="+pet_skill_name);
		
		int pet_skill_several = 0;
		if (pet_skill_name == null || pet_skill_name.equals("")) {
			return 0;
		}
		
		if (pet_skill_name.equals("普通攻击")) {
			return 1;
		}
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		for ( Integer i = 1; i <= pet_skill.size();i++) {
			petskill = pet_skill.get(i);
			if( petskill==null )
			{
				System.out.println("");
			}
			if ( pet_skill_name.equals(petskill.getPetSkillName())) {
				pet_skill_several = petskill.getPetSkillSeveral();		
				return pet_skill_several;
			}
		}	
		
		logger.debug("pet_skill_several:"+petskill.getPetSkillSeveral());
		return pet_skill_several;
	}
}
