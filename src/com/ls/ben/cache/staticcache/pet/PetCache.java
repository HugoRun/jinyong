package com.ls.ben.cache.staticcache.pet;

import java.util.HashMap;

import com.ben.vo.pet.pet.PetVO;
import com.ls.ben.cache.CacheBase;

/**
 *  保存宠物 在内存中,以便随时取用
 * @author Administrator
 */
public class PetCache  extends CacheBase
{
	public static String PET_BY_ID = "pet_by_id";
	
	/**
	 * 通过id得到 宠物信息
	 * @param scene_id
	 * @return
	 */
	public static PetVO getPetById(int pet_id)
	{
		if (pet_id == 0) {
			return null;
		}
		logger.debug("得到一个宠物信息");
		PetVO petvo = null;
		HashMap<Integer,PetVO> pet_skill = (HashMap<Integer,PetVO>)getElementValue(STATIC_CACHE_NAME, PET_BY_ID);
		
		petvo = pet_skill.get(pet_id);	
		logger.debug("petvo:"+petvo.getPetName());	
		return petvo;
	}
	
	
	
	
	
	/**
	 * 通过id得到 npcId
	 * @param scene_id
	 * @return
	 */
	public int getNpcIdById(int pet_id)
	{
		if (pet_id == 0) {
			return 0;
		}
		logger.debug("得到一个宠物信息");
		PetVO petvo = null;
		HashMap<Integer,PetVO> pet_skill = (HashMap<Integer,PetVO>)getElementValue(STATIC_CACHE_NAME, PET_BY_ID);
		
		petvo = pet_skill.get(pet_id);	
		logger.debug("pet_npc_id:"+petvo.getNpcId());	
		return petvo.getNpcId();
	}
	
}
