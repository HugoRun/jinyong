package com.ls.ben.cache.staticcache.pet;

import java.util.HashMap;
import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.info.pet.PetSkillVO;

/**
 * ������＼�� ���ڴ���,�Ա���ʱȡ��
 * @author Administrator	
 *
 */
public class PetSkillCache  extends CacheBase
{
	public static String PETSKILL = "pet_skill";
	
	
	/**
	 * ͨ��id�õ� ���＼����Ϣ
	 * @param scene_id
	 * @return
	 */
	public PetSkillVO getPetSkillById(int pet_skill_id)
	{
		logger.debug("�õ�һ�����＼����Ϣ");
		PetSkillVO petskill = null;
		HashMap<Integer,PetSkillVO> pet_skill = (HashMap<Integer,PetSkillVO>)getElementValue(STATIC_CACHE_NAME, PETSKILL);
		
		petskill = pet_skill.get(pet_skill_id);		
		logger.debug("petskill:"+petskill.getPetSkillName());
		return petskill;
	}
	
	/**
	 * �õ����＼�ܵ�����
	 * @param pet_skill_id
	 */
	public int getPetSkillType(int pet_skill_id) {
		
		logger.debug("�õ�һ�����＼�ܵ�����="+pet_skill_id);
		
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
	 * ��ó��＼����id
	 * @param pet_skill_id
	 * @return
	 */
	public int getGroupID(int pet_skill_id)
	{
		logger.debug("�õ�һ�����＼����id="+pet_skill_id);
		
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
	 * ��ó��＼������
	 * @param pet_skill_id
	 * @return
	 */
	public String getName(int pet_skill_id)
	{
		logger.debug("�õ�һ�����＼������="+pet_skill_id);
		
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
	 * ��ó��＼�ܵȼ�
	 * @param pet_skill_id
	 * @return
	 */
	public int getPetSkLevel(int pet_skill_id) {
		logger.debug("�õ�һ�����＼�ܵȼ�="+pet_skill_id);
		
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
	 * �õ����﹥���ӳ�
	 * @param pet_skill_id
	 * @return
	 */
	public double getInjureMultiple(int pet_skill_id) {
		logger.debug("�õ����﹥���ӳ�="+pet_skill_id);
		
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
	 * �õ����﹥������
	 * @param pet_skill_id
	 * @return
	 */
	public int getSeveral(String pet_skill_name) {
		logger.debug("�õ����﹥���ӳ�="+pet_skill_name);
		
		int pet_skill_several = 0;
		if (pet_skill_name == null || pet_skill_name.equals("")) {
			return 0;
		}
		
		if (pet_skill_name.equals("��ͨ����")) {
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
