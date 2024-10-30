package com.ls.ben.cache.staticcache.pet;

import java.util.HashMap;

import com.ben.vo.pet.pet.PetVO;
import com.ls.ben.cache.CacheBase;

/**
 *  ������� ���ڴ���,�Ա���ʱȡ��
 * @author Administrator
 */
public class PetCache  extends CacheBase
{
	public static String PET_BY_ID = "pet_by_id";
	
	/**
	 * ͨ��id�õ� ������Ϣ
	 * @param scene_id
	 * @return
	 */
	public static PetVO getPetById(int pet_id)
	{
		if (pet_id == 0) {
			return null;
		}
		logger.debug("�õ�һ��������Ϣ");
		PetVO petvo = null;
		HashMap<Integer,PetVO> pet_skill = (HashMap<Integer,PetVO>)getElementValue(STATIC_CACHE_NAME, PET_BY_ID);
		
		petvo = pet_skill.get(pet_id);	
		logger.debug("petvo:"+petvo.getPetName());	
		return petvo;
	}
	
	
	
	
	
	/**
	 * ͨ��id�õ� npcId
	 * @param scene_id
	 * @return
	 */
	public int getNpcIdById(int pet_id)
	{
		if (pet_id == 0) {
			return 0;
		}
		logger.debug("�õ�һ��������Ϣ");
		PetVO petvo = null;
		HashMap<Integer,PetVO> pet_skill = (HashMap<Integer,PetVO>)getElementValue(STATIC_CACHE_NAME, PET_BY_ID);
		
		petvo = pet_skill.get(pet_id);	
		logger.debug("pet_npc_id:"+petvo.getNpcId());	
		return petvo.getNpcId();
	}
	
}
