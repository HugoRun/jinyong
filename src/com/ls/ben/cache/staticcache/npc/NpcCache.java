package com.ls.ben.cache.staticcache.npc;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.info.npc.NpcVO;

public class NpcCache extends CacheBase
{
	public static String NPC_BY_ID = "npc_by_id";
	
	public static String All_NPC_MAP = "all_npc_map";
	
	/**
	 * ͨ��id�õ�npc��Ϣ
	 * @param npc_id
	 * @return
	 */
	public static NpcVO getById( int npc_id )
	{
		NpcVO npc_info = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, NPC_BY_ID);
		npc_info = (NpcVO)result.get(npc_id+"");
		return npc_info;
	}

	/**
	 * ͨ��id �õ� npc�ȼ�
	 * @param npc_id
	 * @return
	 */
	public static int getNpcGradeById(int npc_id)
	{
		NpcVO npc_info = getById(npc_id);
		if(npc_info != null )
		{
			return npc_info.getLevel();
		}	
		else
		{
			return 0;
		}
	}
	
	/**
	 * ͨ��id �õ� npc����
	 * @param npc_id
	 * @return
	 */
	public static String getNpcNameById(int npc_id)
	{
		NpcVO npc_info = getById(npc_id);
		if(npc_info != null ) 
		{
			return npc_info.getNpcName();
		}	
		else
		{
			return "";
		}
	}
	 /**
	 * ���¼���һ��NPC����
	 */
	public void reloadOneNPC(NpcVO npcVO)
	{
		if (npcVO != null)
		{
			HashMap result = getElementValue(STATIC_CACHE_NAME, NPC_BY_ID);
			result.remove(npcVO.getNpcID()+"");
			result.put(npcVO.getNpcID()+"", npcVO);
		}
	}

}