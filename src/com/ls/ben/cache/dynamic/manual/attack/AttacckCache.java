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
 * ����ˢ�´����npc, �Լ��������
 * @author �ſ���
 *
 */
public class AttacckCache extends CacheBase
{
	public static String ATTACKING_BY_PPK = "attacking_by_pPk";
	
	// ��scene����,�������͵�npcÿ��sceneֻ����һ��,��ʼ���ܱ���Ҷ����npc�˵�
	public static String ATTACKING_BY_SCENE   = "attacking_by_scene";
	
	
	public static int ZDATTACKNPC = 0;
	public static int BDATTACKNPC = 1;
	
	public static int ALL = 2;
	
	/**
	 * ͨ��pPk�õ�attack_info��Ϣ
	 * @param npc_id
	 * @return
	 */
	public Object[] getByPPkd( int pPk )
	{
		logger.debug("ͨ��id�õ�NpcAttackVO��Ϣ:pPk="+pPk);
		Object[] npc_info = null;
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		npc_info = result.get(pPk+"");
		
		logger.debug("NpcAttackVO:"+npc_info);
		return npc_info;
	}
	
	/**
	 * ͨ�� �õ��ܱ���Ҵ��npc_attack_info��Ϣ
	 * @param npc_id
	 * @return
	 */
	public HashMap<String, Object> getNpcWithSceneByPPkd()
	{
		//logger.debug("ͨ��sceneId�õ�NpcAttackVO��Ϣ:pPk="+sceneId);
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
	 * ͨ��sceneId�õ�attack_info��Ϣ
	 * @param npc_id
	 * @return
	 */
	public Object getNpcWithSceneByPPkd( String sceneId )
	{
		logger.debug("ͨ��sceneId�õ�NpcAttackVO��Ϣ:pPk="+sceneId);
		Object npc_info = null;
		HashMap<String, Object> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_SCENE);
		npc_info =	result.get(sceneId);
		
		logger.debug("NpcAttackVO:"+npc_info);
		return npc_info;
	}
	
	/**
	 * ͨ��pPk�洢attack_info��Ϣ
	 * @param npc_id
	 * @return
	 */
	public void setByPPkd( int pPk,Object[] attack )
	{
		logger.debug("ͨ��id�õ�NpcAttackVO��Ϣ:pPk="+pPk);
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
	 * �õ���ǰ�� �������� npc����Ϣ
	 * @param npc_type 0Ϊ���������͹֣�1Ϊ���������͹�
	 */
	public  List<NpcFighter> getZDAttackNpc( int pPk, int npc_type) {
		logger.debug("ͨ��id�õ�NpcAttackVO��Ϣ:pPk="+pPk);
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
	 * session���ٵ�ʱ�� ���ڴ���ȥ�� pPk����Ӧ��npcս����Ϣ
	 * @param pPk 
	 */
	public void clearNpcTempData(String pPk) {	
		
		HashMap<String, Object[]> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_PPK);
		result.remove(pPk);
		
	}	
	
	/**
	 * ˢ�µ�ͼʱ�� 
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
	 * �����ǰ�ص�Ŀɱ������˴��npc
	 * @param sceneId
	 */
	public void clearNpcBySceneId(String sceneId)
	{
		logger.debug("�����ǰ�ص�Ŀɱ������˴��npc:sceneId="+sceneId);
		HashMap<String, Object> result = getElementValue(DYNAMIC_MANUAL_CACHE, ATTACKING_BY_SCENE);
		result.remove(sceneId);
				
	}
	
	
	/**
	 * ���� ��������npc��
	 */
	public void destoryZDNpc(int pPk) {
		 List<NpcFighter> list = this.getZDAttackNpc(pPk, this.ZDATTACKNPC);
		 if (list != null && list.size() != 0 ) {
			 list.clear();
			 list = null;
		 }
	}
	
	
	/**
	 * ���� ��������npc��
	 */
	public void destoryBDNpc(int pPk) {
		 List<NpcFighter> list = this.getZDAttackNpc(pPk, this.BDATTACKNPC);
		 if (list != null && list.size() != 0 ) {
			 list.clear();
			 list = null;
		 }
	}
	
	/**
	 * ���� ��ɫˢ�������е� npc
	 */
	public void destoryAllNpc(int pPk) {
		destoryZDNpc(pPk);
		destoryBDNpc(pPk);
	}

	/**
	 * ��ʼ��npc
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
