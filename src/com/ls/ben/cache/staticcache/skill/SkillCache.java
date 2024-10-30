package com.ls.ben.cache.staticcache.skill;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.info.skill.SkillVO;

public class SkillCache extends CacheBase
{
	public static String SKILL_BY_ID = "skill_by_id";
	
	/**
	 * 通过id得到skill信息
	 * @param skill_id
	 * @return
	 */
	public static SkillVO getById( int skill_id )
	{
		logger.debug("通过id得到skill信息:skill_id="+skill_id);
		SkillVO skill_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, SKILL_BY_ID);
		skill_info = (SkillVO)result.get(skill_id+"");
		logger.debug("skill:"+skill_info);
		return skill_info;
	}
	
	/**
	 * 通过id得到skill名称
	 * @param skill_id
	 * @return
	 */
	public static String getNameById( int skill_id )
	{
		logger.debug("通过id得到skill信息:skill_id="+skill_id);
		String skill_name = "";
		SkillVO skill_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, SKILL_BY_ID);
		skill_info = (SkillVO)result.get(skill_id+"");
		logger.debug("skill:"+skill_info);
		skill_name = skill_info.getSkName();
		
		
		return skill_name;
	}
	
	
	/**
	 * 通过id得到skill类型
	 * @param skill_id
	 * @return
	 */
	public static int getTypeById( int skill_id )
	{
		logger.debug("通过id得到skill类型:skill_id="+skill_id);
		int  type = 1;
		SkillVO skill_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, SKILL_BY_ID);
		skill_info = (SkillVO)result.get(skill_id+"");
		logger.debug("skill:"+skill_info);
		type = skill_info.getSkType();
		
		return type;
	}
	
	/**
	 * 通过id得到skill组id
	 * @param skill_id
	 * @return
	 */
	public static int getGroupById( String skill_id )
	{
		logger.debug("通过id得到skill类型:skill_id="+skill_id);
		int  sk_group = 1;
		SkillVO skill_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, SKILL_BY_ID);
		skill_info = (SkillVO)result.get(skill_id+"");
		logger.debug("skill:"+skill_info);
		sk_group = skill_info.getSkGroup();
		
		return sk_group;
	}
	
	/**
	 * 通过id得到skill下一级熟练度
	 * @param skill_id
	 * @return
	 */
	public int getNextSleightById( String skill_id )
	{
		logger.debug("通过id得到skill类型:skill_id="+skill_id);
		int  sk_next_sleight = 0;
		SkillVO skill_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, SKILL_BY_ID);
		skill_info = (SkillVO)result.get(skill_id+"");
		logger.debug("skill:"+skill_info);
		sk_next_sleight = skill_info.getSkNextSleight();
		
		return sk_next_sleight;
	}

}
