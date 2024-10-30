package com.ls.ben.cache.staticcache.npc;

import java.util.HashMap;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.info.npc.NpcVO;

public class NpcCache extends CacheBase
{
	public static String NPC_BY_ID = "npc_by_id";
	
	public static String All_NPC_MAP = "all_npc_map";
	
	/**
	 * 通过id得到npc信息
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
	 * 通过id 得到 npc等级
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
	 * 通过id 得到 npc名字
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
	 * 重新加在一条NPC数据
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
