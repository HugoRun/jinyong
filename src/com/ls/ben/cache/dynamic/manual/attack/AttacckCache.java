package com.ls.ben.cache.dynamic.manual.attack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.vo.info.npc.NpcFighter;

/**
 * 
 * 处理刷新处理的npc, 以及相关事务
 * @author 张俊俊
 *
 */
public class AttacckCache extends CacheBase
{
	public static String ATTACKING_BY_PPK = "attacking_by_pPk";
	
	// 按scene来分,这种类型的npc每个scene只能有一个,初始化能被大家都打的npc菜单
	public static String ATTACKING_BY_SCENE   = "attacking_by_scene";
	
	
	public static int ZDATTACKNPC = 0;
	public static int BDATTACKNPC = 1;
	
	public static int ALL = 2;
	
	/**
	 * 通过pPk得到attack_info信息
	 * @param npc_id
	 * @return
	 */
	public Object[] getByPPkd( int pPk )
	{
		logger.debug("通过id得到NpcAttackVO信息:pPk="+pPk);
		Object[] npc_info = null;
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		npc_info = result.get(pPk+"");
		
		logger.debug("NpcAttackVO:"+npc_info);
		return npc_info;
	}
	
	/**
	 * 通过 得到能被大家打的npc_attack_info信息
	 * @param npc_id
	 * @return
	 */
	public HashMap<String, Object> getNpcWithSceneByPPkd()
	{
		//logger.debug("通过sceneId得到NpcAttackVO信息:pPk="+sceneId);
		Object[] npc_info = null;
		HashMap<String, Object> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_SCENE);
			
		Set<String> set = result.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()) {
			String sceneId = iterator.next();
			NpcFighter npcAttackVO = (NpcFighter) result.get(sceneId);
			if ( npcAttackVO.isDead()) {
				result.remove(sceneId);
			}
		}
		logger.debug("HashMap<String, Object[]>:"+npc_info);
		return result;
	}
	
	/**
	 * 通过sceneId得到attack_info信息
	 * @param npc_id
	 * @return
	 */
	public Object getNpcWithSceneByPPkd( String sceneId )
	{
		logger.debug("通过sceneId得到NpcAttackVO信息:pPk="+sceneId);
		Object npc_info = null;
		HashMap<String, Object> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_SCENE);
		npc_info =	result.get(sceneId);
		
		logger.debug("NpcAttackVO:"+npc_info);
		return npc_info;
	}
	
	/**
	 * 通过pPk存储attack_info信息
	 * @param npc_id
	 * @return
	 */
	public void setByPPkd( int pPk,Object[] attack )
	{
		logger.debug("通过id得到NpcAttackVO信息:pPk="+pPk);
		Object[] npc_info = null;
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		npc_info = result.get(pPk+"");
		
		logger.debug("attack="+attack);
		
		if(npc_info == null ) {
			logger.debug("npc_info2="+(npc_info == null));
			result.put(pPk+"",attack);
			
		} else {
			result.remove(pPk+"");
			result.put(pPk+"",attack);
			
		}		
	}
	
	/**
	 * 得到当前的 主动攻击 npc的信息
	 * @param npc_type 0为主动攻击型怪，1为被动攻击型怪
	 */
	public  List<NpcFighter> getZDAttackNpc( int pPk, int npc_type) {
		logger.debug("通过id得到NpcAttackVO信息:pPk="+pPk);
		Object[] npc_info = null;
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		npc_info = result.get(pPk+"");
		logger.debug("NpcAttackVO:"+npc_info);

		if(npc_info == null ) {
			return null;				
		} else {
//			list = (List<NpcFighter>) npc_info[npc_type];
			return (List<NpcFighter>) npc_info[npc_type];					
		}
	}
	
	
	
	/**
	 * session销毁的时候 从内存中去除 pPk所对应的npc战斗信息
	 * @param pPk 
	 */
	public void clearNpcTempData(String pPk) {	
		
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		result.remove(pPk);
		
	}	
	
	/**
	 * 刷新地图时， 
	 * @param pPk 
	 */
	public void clearNpcByRefurish(int pPk) {
		
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		Object[] npc_info = result.get(pPk+"");
		
		if (npc_info != null) {
			if (npc_info[0] != null) {
				((List<NpcFighter>)npc_info[0]).clear();	
				npc_info[0] = null;
			}	
		}
	}
	
	/**
	 * 清楚当前地点的可被所有人打的npc
	 * @param sceneId
	 */
	public void clearNpcBySceneId(String sceneId)
	{
		logger.debug("清楚当前地点的可被所有人打的npc:sceneId="+sceneId);
		HashMap<String, Object> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_SCENE);
		result.remove(sceneId);
				
	}
	
	
	/**
	 * 销毁 主动攻击npc怪
	 */
	public void destoryZDNpc(int pPk) {
		 List<NpcFighter> list = this.getZDAttackNpc(pPk, this.ZDATTACKNPC);
		 if (list != null && list.size() != 0 ) {
			 list.clear();
			 list = null;
		 }
	}
	
	
	/**
	 * 销毁 被动攻击npc怪
	 */
	public void destoryBDNpc(int pPk) {
		 List<NpcFighter> list = this.getZDAttackNpc(pPk, this.BDATTACKNPC);
		 if (list != null && list.size() != 0 ) {
			 list.clear();
			 list = null;
		 }
	}
	
	/**
	 * 销毁 角色刷出的所有的 npc
	 */
	public void destoryAllNpc(int pPk) {
		destoryZDNpc(pPk);
		destoryBDNpc(pPk);
	}

	/**
	 * 初始化npc
	 * @param parseInt
	 */
	public void initNpc(int pPk)
	{
		List<NpcFighter> zdNpcs = new ArrayList<NpcFighter>();
		List<NpcFighter> bdNpcs = new ArrayList<NpcFighter>();
		Object[] attack = new Object[2];
		attack[0] = zdNpcs;
		attack[1] = bdNpcs;
		this.setByPPkd(pPk, attack);
		
	}

	
	
}
