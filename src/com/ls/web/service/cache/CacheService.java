package com.ls.web.service.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.ben.dao.expnpcdrop.ExpNpcdropDAO;
import com.ben.dao.intimatehint.IntimateHintDAO;
import com.ben.dao.pet.PetDAO;
import com.ben.vo.expnpcdrop.ExpNpcdropVO;
import com.ben.vo.honour.TitleVO;
import com.ben.vo.intimatehint.IntimateHintVO;
import com.ben.vo.pet.pet.PetVO;
import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.CacheBase;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.dynamic.manual.view.ViewCache;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.expNpcDrop.ExpNpcDropCache;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.cache.staticcache.map.BareaCache;
import com.ls.ben.cache.staticcache.map.MapCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.cache.staticcache.pet.PetCache;
import com.ls.ben.cache.staticcache.pet.PetSkillCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.cache.staticcache.skill.SkillCache;
import com.ls.ben.cache.staticcache.system.IntimateHintCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.dao.goods.equip.GameEquipDao;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.npc.NpcDao;
import com.ls.ben.dao.info.pet.PetSkillDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.dao.map.BareaDao;
import com.ls.ben.dao.map.MapDao;
import com.ls.ben.dao.map.SceneDao;
import com.ls.ben.dao.menu.OperateMenuDao;
import com.ls.ben.dao.task.TaskDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.pet.PetSkillVO;
import com.ls.ben.vo.info.skill.SkillVO;
import com.ls.ben.vo.map.BareaVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.equip.GameEquip;
import com.pm.dao.forbid.ForBidNameDao;

/**
 * @author ls
 * 功能：系统缓存资源管理
 */
public class CacheService
{
	Logger logger = Logger.getLogger("log.cache");
	
	/**
	 * 初始化缓存
	 * @throws Exception 
	 */
	public void initCache() throws Exception
	{
		logger.info("初始化缓存。。。");
		CacheManager manager = CacheManager.create();
		
		initBareaCache(manager);
		initMapCache(manager);
		initSceneCache(manager);
		
		
		initViewCache(manager);
		initChatInfoCache(manager);
		
		initIntimateHintCache(manager);
		initMenuCache(manager);
		initTaskCache(manager);
		
		
		
		initNpcCache(manager);
		initSkillCache(manager);
		initAttackInfoCache(manager);
		initExpNpcDropCache(manager);
		
		//initPetSkill(manager);
		//initPet(manager);
		
		//initPkListCache(manager);
		initPropCache(manager);
		initEquipCache(manager);
		
		initForbid(manager);
		initTitle(manager);
		//initVip(manager);
		logger.info("初始化缓存结束。。。");
	}
	
	/**
	 * 初始化能被大家都打的npc菜单
	 * @param manager
	 
	private void initAllAttackNpc(CacheManager manager)
	{
		logger.info("初始化能被大家都打的npc菜单。。。");
		ForBidNameDao forBidNameDao = new ForBidNameDao();
		HashMap<Integer,String> allNpc = new HashMap<Integer, String>();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(NpcCache.FORBID_BY_TYPE, allNpc);
		cache.put(element);
		logger.info("初始化能被大家都打的npc菜单。。。");		
	}*/
	
	/**
	 * 初始化禁止名单
	 * @param manager
	 */
	private void initForbid(CacheManager manager)
	{
		logger.info("初始化禁止名单开始。。。");
		ForBidNameDao forBidNameDao = new ForBidNameDao();
		HashMap<Integer,String> forbidName =forBidNameDao.getForBidName();
		Cache cache = manager.getCache(ForBidCache.STATIC_CACHE_NAME);
		Element element = new Element(ForBidCache.FORBID_BY_TYPE, forbidName);
		cache.put(element);
		logger.info("初始化禁止名单结束。。。");		
	}

	/**
	 * 初始化道具list
	 * @param manager
	 */
	public void initPropCache(CacheManager manager)
	{
		logger.info("初始化道具list开始。。。");
		PropDao propDao = new PropDao();
		HashMap<Integer,PropVO> prop_list =propDao.getPropList();
		Cache cache = manager.getCache(PropCache.STATIC_CACHE_NAME);
		Element element = new Element(PropCache.PROP_BY_ID, prop_list);
		cache.put(element);
		logger.info("初始化道具list结束。。。");
	}
	
	/**
	 * 初始化装备缓存
	 * @param manager
	 */
	public void initEquipCache(CacheManager manager)
	{
		logger.info("初始化装备list开始。。。");
		GameEquipDao equipDao = new GameEquipDao();
		HashMap<Integer,GameEquip> equip_list =equipDao.findAll();
		Cache cache = manager.getCache(EquipCache.STATIC_CACHE_NAME);
		Element element = new Element(EquipCache.EQUIP_BY_ID, equip_list);
		cache.put(element);
		logger.info("初始化装备list结束。。。");
	}

	/**
	 * 初始化pkList
	 * @param manager
	 *//*
	private void initPkListCache(CacheManager manager)
	{	
		logger.info("初始化pkList开始。。。");
		HashMap<String,PKLogVO> pk_list = new HashMap<String,PKLogVO>(300);
		Cache cache = manager.getCache(PkCache.DYNAMIC_MANUAL_CACHE);
		Element element = new Element(PkCache.PKING_BY_PPK, pk_list);
		cache.put(element);
		logger.info("初始化pkList结束。。。");
	}*/	
	
	/**
	 * 初始化聊天信息缓存
	 * @param manager
	 */
	private void initChatInfoCache(CacheManager manager)
	{
		logger.info("初始化聊天开始。。。");
		HashMap<String,LinkedHashSet> public_list = new HashMap<String,LinkedHashSet>(2000);
		Cache cache = manager.getCache(ChatInfoCahe.DYNAMIC_MANUAL_CACHE);
		Element element = new Element(ChatInfoCahe.CHAT_PUBLIC_CACHE, public_list);
		cache.put(element);
		logger.info("初始化聊天缓存结束。。。");
	}
	
	
	/**
	 * 初始化视野信息缓存
	 * @param manager
	 */
	private void initViewCache(CacheManager manager)
	{
		logger.info("初始化视野缓存开始。。。");
		HashMap<String,LinkedHashSet> view_list = new HashMap<String,LinkedHashSet>(2000);
		Cache cache = manager.getCache(ViewCache.DYNAMIC_MANUAL_CACHE);
		Element element = new Element(ViewCache.VIEW_INFO_CACHE, view_list);
		cache.put(element);
		logger.info("初始化视野缓存结束。。。");
	}
	
	/**
	 * 初始化宠物技能控制消息
	 * @param manager
	 
	private void initPetSkillControl(CacheManager manager)
	{
		logger.info("初始化宠物技能控制消息。。。");
		PetSkillControlDao petSkillDao = new PetSkillControlDao();
		HashMap<Integer,PetSkillVO> pet_skill_list = petSkillDao();
		Cache cache = manager.getCache(IntimateHintCache.STATIC_CACHE_NAME);
		Element element = new Element(PetSkillCache.PETSKILL, pet_skill_list);
		cache.put(element);
		logger.info("初始化宠物技能控制消息。。。");
		
	}*/


	/**
	 * 初始化宠物信息
	 */
	private void initPet(CacheManager manager)
	{
		logger.info("初始化宠物信息。。。");
		PetDAO petDao = new PetDAO();
		HashMap<Integer,PetVO> pet_map = petDao.getAllPet();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME); 
		Element element = new Element(PetCache.PET_BY_ID, pet_map);
		cache.put(element);
		logger.info("初始化宠物信息。。。");
	}

	/**
	 * 初始化宠物技能信息
	 * @param manager
	 */
	private void initPetSkill(CacheManager manager)
	{
		logger.info("初始化宠物技能信息。。。");
		PetSkillDao petSkillDao = new PetSkillDao();
		HashMap<Integer,PetSkillVO> pet_skill_list = petSkillDao.getAllPetSkill();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(PetSkillCache.PETSKILL, pet_skill_list);
		cache.put(element);
		logger.info("初始化宠物技能信息。。。");		
	}


	/**
	 * 初始化武林小贴士信息缓存
	 * @param manager
	 */
	private void initIntimateHintCache(CacheManager manager)
	{
		logger.info("初始化武林小贴士缓存开始。。。");
		IntimateHintDAO intimateHintDAO = new IntimateHintDAO();
		List<IntimateHintVO> hint_list = intimateHintDAO.getAllIntimateHint();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(IntimateHintCache.HINT_LIST, hint_list);
		cache.put(element);
		logger.info("初始化武林小贴士缓存结束。。。");
	}
	
	
	
	/**
	 * menu缓存初始化
	 * @param manager
	 * @throws Exception 
	 */
	public void initMenuCache(CacheManager manager) throws Exception
	{
		logger.info("menu缓存初始化开始。。。");
		SceneCache sceneCache = new SceneCache();
		HashMap<String,SceneVO> scene_list = sceneCache.getSceneList();
		
		OperateMenuDao menuDao = new OperateMenuDao();
		HashMap<String,OperateMenuVO> tbl_menu_by_id = menuDao.getAllMenu(scene_list);
	    Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
	    Element element = new Element(MenuCache.MENU_BY_ID, tbl_menu_by_id);
        cache.put(element);
        logger.info("menu缓存初始化结束。。。");
	}
	
	/**
	 * task缓存初始化
	 * @param manager
	 */
	public void initTaskCache(CacheManager manager)
	{
		logger.info("task缓存初始化开始。。。");
		TaskDao taskDao = new TaskDao();
		HashMap<String,TaskVO> tbl_task_by_id = taskDao.getAllTask();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(TaskCache.TASK_BY_ID, tbl_task_by_id);
		cache.put(element);
		logger.info("task缓存初始化结束。。。");
	}

	/**
	 * scene缓存初始化
	 * @param manager
	 * @throws Exception 
	 */
	private void initSceneCache(CacheManager manager) throws Exception
	{
		logger.info("scene缓存初始化开始。。。");
		
		MapCache mapCache = new MapCache();
		HashMap<String,MapVO> map_list = mapCache.getMapList();
		
		SceneDao sceneDao = new SceneDao();
		HashMap<String,SceneVO> tbl_scene_by_id = sceneDao.getAllScene(map_list);
		
	    Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
	    Element element = new Element(SceneCache.SCENE_BY_ID, tbl_scene_by_id);
        cache.put(element);
        logger.info("scene缓存初始化结束。。。");
	}
	
	/**
	 * map缓存初始化
	 * @param manager
	 * @throws Exception 
	 */
	private void initMapCache(CacheManager manager) throws Exception
	{
		logger.info("map缓存初始化开始。。。");
		BareaCache bareaCache = new BareaCache();
		HashMap<String,BareaVO> barea_list = bareaCache.getBareaList();
		
		MapDao mapDao = new MapDao();
		HashMap<String,MapVO> tbl_map_by_id = mapDao.getAllMap(barea_list);
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(MapCache.MAP_BY_ID, tbl_map_by_id);
		cache.put(element);
		logger.info("map缓存初始化结束。。。");
	}
	/**
	 * 大区域缓存初始化
	 * @param manager
	 * @throws Exception 
	 */
	private void initBareaCache(CacheManager manager) throws Exception
	{
		logger.info("Barea缓存初始化开始。。。");
		BareaDao bareaDao = new BareaDao();
		HashMap<String,BareaVO> tbl_barea_by_id = bareaDao.getAllBarea();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(BareaCache.BAREA_BY_ID, tbl_barea_by_id);
		cache.put(element);
		logger.info("Barea缓存初始化结束。。。");
	}
	
	
	/**
	 * npc缓存初始化
	 * @param manager
	 */
	public void initNpcCache(CacheManager manager)
	{
		logger.info("npc缓存初始化开始。。。"); 
		NpcDao npcDao = new NpcDao();
		HashMap<String,NpcVO> tbl_npc_by_id = npcDao.getAllNpc();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(NpcCache.NPC_BY_ID, tbl_npc_by_id);
		cache.put(element);
		logger.info("npc缓存初始化结束。。。");
	}
	
	
	/**
	 * skill缓存初始化
	 * @param manager
	 */
	private void initSkillCache(CacheManager manager)
	{
		logger.info("skill缓存初始化开始。。。");
		SkillDao skillDao = new SkillDao();
		HashMap<String,SkillVO> tbl_skill_by_id = skillDao.getAllSkill();
		
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(SkillCache.SKILL_BY_ID, tbl_skill_by_id);
		cache.put(element);
		logger.info("skill缓存初始化结束。。。");
	}
	
	/**
	 * attack_info缓存初始化
	 * @param manager
	 */
	private void initAttackInfoCache(CacheManager manager)
	{
		logger.info("attack_info缓存初始化开始。。。");
		
		HashMap<String,Object[]> tbl_attack_by_PPk = new HashMap<String,Object[]>(10000);
		HashMap<String,Object> tbl_attack_by_scene = new HashMap<String,Object>(20);
		Cache cache = manager.getCache(CacheBase.DYNAMIC_MANUAL_CACHE);
		Element element = new Element(AttacckCache.ATTACKING_BY_PPK, tbl_attack_by_PPk);
		Element element1 = new Element(AttacckCache.ATTACKING_BY_SCENE, tbl_attack_by_scene);
		cache.put(element);
		cache.put(element1);
		logger.info("attack_info缓存初始化结束。。。");
	}
	
	
	/**
	 * npc 掉落经验 缓存初始化
	 * @param manager
	 */
	public void initExpNpcDropCache(CacheManager manager)
	{ 
		logger.info("npc掉落经验缓存初始化开始。。。");
		ExpNpcdropDAO expNpcDropDao = new ExpNpcdropDAO();
		List<ExpNpcdropVO> npc_dropExp_by_id = expNpcDropDao.getAllExpNpcDrop();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(ExpNpcDropCache.NPC_DROPEXP_BY_ID, npc_dropExp_by_id);
		cache.put(element);
		logger.info("npc掉落经验缓存初始化结束。。。");
	}
	/**
	 * 初始化称号
	 * @param manager
	 */
	public void initTitle(CacheManager manager){
		logger.info("初始化称号。。。");
		HashMap<String,TitleVO> title_cache = new HashMap<String, TitleVO>();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(TitleCache.HONOUR_BY_ID, title_cache);
		cache.put(element);
		logger.info("初始化称号结束。。。");
	}
	/**
	 * 初始化VIP
	 * @param manager
	 *//*
	public void initVip(CacheManager manager){
		logger.info("初始化VIP。。。");
		VipDao vipDao = new VipDao();
		HashMap vip_by_id = vipDao.getVip();
		Cache cache = manager.getCache(CacheBase.STATIC_CACHE_NAME);
		Element element = new Element(VipCach.VIP_BY_ID, vip_by_id);
		cache.put(element);
		logger.info("初始化VIP结束。。。");
	}*/
	
	/**
	 * 聊天 缓存初始化
	 * @param manager
	 */
	/*private void initChatCache(CacheManager manager)
	{ 
		logger.info("聊天容器缓存初始开始");
		CommunionDAO communionDAO = new CommunionDAO();
		List<ExpNpcdropVO> npc_dropExp_by_id = communionDAO.getAllExpNpcDrop();
		Cache cache = manager.getCache(ExpNpcDropCache.STATIC_CACHE_NAME);
		Element element = new Element(ExpNpcDropCache.NPC_DROPEXP_BY_ID, npc_dropExp_by_id);
		cache.put(element);
		logger.info("npc掉落经验缓存初始化结束。。。");
	}*/
	
}
