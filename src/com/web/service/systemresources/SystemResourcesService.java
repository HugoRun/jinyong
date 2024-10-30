package com.web.service.systemresources;

import java.util.HashMap;

import net.sf.ehcache.CacheManager;

import com.ben.dao.sysresources.SysResourcesDAO;
import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.map.MapCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.dao.info.npc.NpcDao;
import com.ls.ben.dao.info.npc.NpcdropDao;
import com.ls.ben.dao.map.SceneDao;
import com.ls.ben.dao.menu.OperateMenuDao;
import com.ls.ben.dao.task.TaskDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.web.service.cache.CacheService;
import com.ls.web.service.room.RoomService;

public class SystemResourcesService
{
	/**
	 * 用户登录 判断用户登录
	 * 
	 * @param name
	 * @param paw
	 * @return
	 */
	public String login(String name, String paw)
	{
		String hint = null;
		SysResourcesDAO sysResourcesDAO = new SysResourcesDAO();
		if (sysResourcesDAO.isLogin(name, paw) == false)
		{
			hint = "用户名或者密码不对";
		}
		return hint;
	}

	/**
	 * 查询任务信息
	 * 
	 * @param task_id
	 * @return
	 */
	public static TaskVO getTaskView(String task_id)
	{
		TaskDao taskDao = new TaskDao();
		return taskDao.getTaskView(task_id);

	}

	/**
	 * 查询菜单信息
	 * 
	 * @param task_id
	 * @return
	 */
	public static OperateMenuVO getMenuView(String menu_id)
	{
		OperateMenuDao operateMenuDao = new OperateMenuDao();
		return operateMenuDao.getMenuById(Integer.parseInt(menu_id));

	}

	/**
	 * 查询scene地图信息
	 * 
	 * @param task_id
	 * @return
	 */
	public static SceneVO getSceneView(String sceneID)
	{
		SceneDao sceneDao = new SceneDao();
		return sceneDao.getByIdSceneView(sceneID);

	}

	/**
	 * 查询道具信息
	 * 
	 * @param task_id
	 * @return
	 */
	public static PropVO getPropView(String prop_id)
	{
		return PropCache.getPropById(Integer.parseInt(prop_id));

	}

	/**
	 * 查询NPC信息
	 * 
	 * @param task_id
	 * @return
	 */
	public static NpcVO getNPCView(String npc_id)
	{
		NpcDao npcDao = new NpcDao();
		return npcDao.getNpcById1(Integer.parseInt(npc_id));

	}

	/**
	 * 查询NPC掉落信息
	 * 
	 * @param task_id
	 * @return
	 */
	public static NpcdropVO getNPCDropView(String npc_drop_id)
	{
		NpcdropDao npcdropDao = new NpcdropDao();
		return npcdropDao.getNpcdropsById(npc_drop_id);

	}

	/**
	 * 全部重新加载任务
	 */
	public static void initTaskCache()
	{
		CacheManager manager = CacheManager.create();
		CacheService cacheService = new CacheService();
		cacheService.initTaskCache(manager);
	}

	/**
	 * 全部重新加载菜单
	 * @throws Exception 
	 */
	public static void initMenuCache() throws Exception
	{
		CacheManager manager = CacheManager.create();
		CacheService cacheService = new CacheService();
		cacheService.initMenuCache(manager);
	}

	/**
	 * 全部重新加载道具
	 */
	public static void initPropCache()
	{
		CacheManager manager = CacheManager.create();
		CacheService cacheService = new CacheService();
		cacheService.initPropCache(manager);
	}

	/**
	 * 全部重新加载NPC
	 */
	public static void initNPCCache()
	{
		CacheManager manager = CacheManager.create();
		CacheService cacheService = new CacheService();
		cacheService.initNpcCache(manager);
	}

	/**
	 * 重新加载一条任务
	 */
	public static void reloadOneTask(TaskVO taskVO)
	{
		TaskVO taskVO_cache = TaskCache.getById(taskVO.getTId() + "");
		taskVO_cache.setTBnzjms(taskVO.getTBnzjms());
		taskVO_cache.setTCycle(taskVO.getTCycle());
		taskVO_cache.setTDisplay(taskVO.getTDisplay());
		taskVO_cache.setTDjsc(taskVO.getTDjsc());
		taskVO_cache.setTEncouragement(taskVO.getTEncouragement());
		taskVO_cache.setTEncouragementNoZb(taskVO.getTEncouragementNoZb());
		taskVO_cache.setTEncouragementZb(taskVO.getTEncouragementZb());
		taskVO_cache.setTExp(taskVO.getTExp());
		taskVO_cache.setTGeidj(taskVO.getTGeidj());
		taskVO_cache.setTGeidjNumber(taskVO.getTGeidjNumber());
		taskVO_cache.setTGoods(taskVO.getTGoods());
		taskVO_cache.setTGoodsNumber(taskVO.getTGoodsNumber());
		taskVO_cache.setTGoodszb(taskVO.getTGoodszb());
		taskVO_cache.setTGoodszbNumber(taskVO.getTGoodszbNumber());
		taskVO_cache.setTId(taskVO.getTId());
		taskVO_cache.setTKey(taskVO.getTKey());
		taskVO_cache.setTKeyValue(taskVO.getTKeyValue());
		taskVO_cache.setTKilling(taskVO.getTKilling());
		taskVO_cache.setTKillingNo(taskVO.getTKillingNo());
		taskVO_cache.setTLevelDa(taskVO.getTLevelDa());
		taskVO_cache.setTLevelXiao(taskVO.getTLevelXiao());
		taskVO_cache.setTMidstGs(taskVO.getTMidstGs());
		taskVO_cache.setTMoney(taskVO.getTMoney());
		taskVO_cache.setTName(taskVO.getTName());
		taskVO_cache.setTNext(taskVO.getTNext());
		taskVO_cache.setTPoint(taskVO.getTPoint());
		taskVO_cache.setTSchool(taskVO.getTSchool());
		taskVO_cache.setTSw(taskVO.getTSw());
		taskVO_cache.setTSwType(taskVO.getTSwType());
		taskVO_cache.setTTishi(taskVO.getTTishi());
		taskVO_cache.setTType(taskVO.getTType());
		taskVO_cache.setTWncouragementNo(taskVO.getTWncouragementNo());
		taskVO_cache.setTZjdwp(taskVO.getTZjdwp());
		taskVO_cache.setTZjdwpNumber(taskVO.getTZjdwpNumber());
		taskVO_cache.setTZjms(taskVO.getTZjms());
		taskVO_cache.setTZu(taskVO.getTZu());
		taskVO_cache.setTZuxl(taskVO.getTZuxl());
	}

	/**
	 * 重新加载一条菜单
	 */
	public static void reloadOneMenu(OperateMenuVO operateMenuVO)
	{
		MenuCache menuCache = new MenuCache();
		OperateMenuVO operateMenuVO_cache = menuCache.getById(operateMenuVO
				.getId()
				+ "");
		operateMenuVO_cache.setId(operateMenuVO.getId());
		operateMenuVO_cache.setMenuCamp(operateMenuVO.getMenuCamp());
		operateMenuVO_cache.setMenuDayBegin(operateMenuVO.getMenuDayBegin());
		operateMenuVO_cache.setMenuDayEnd(operateMenuVO.getMenuDayEnd());
		operateMenuVO_cache.setMenuDialog(operateMenuVO.getMenuDialog());
		operateMenuVO_cache.setMenuFatherId(operateMenuVO.getMenuFatherId());
		operateMenuVO_cache.setMenuImg(operateMenuVO.getMenuImg());
		operateMenuVO_cache.setMenuName(operateMenuVO.getMenuName());
		operateMenuVO_cache.setMenuOperate1(operateMenuVO.getMenuOperate1());
		operateMenuVO_cache.setMenuOperate2(operateMenuVO.getMenuOperate2());
		operateMenuVO_cache.setMenuOperate3(operateMenuVO.getMenuOperate3());
		operateMenuVO_cache.setMenuOrder(operateMenuVO.getMenuOrder());
		operateMenuVO_cache.setMenuTaskFlag(operateMenuVO.getMenuTaskFlag());
		operateMenuVO_cache.setMenuTasksId(operateMenuVO.getMenuTasksId());
		operateMenuVO_cache.setMenuTimeBegin(operateMenuVO.getMenuTimeBegin());
		operateMenuVO_cache.setMenuTimeEnd(operateMenuVO.getMenuTimeEnd());
		operateMenuVO_cache.setMenuType(operateMenuVO.getMenuType());
		operateMenuVO_cache.setTaskPoint(operateMenuVO.getTaskPoint());
		if (operateMenuVO.getMenuMap() != operateMenuVO_cache.getMenuMap())
		{

			RoomService roomService = new RoomService();
			SceneVO cur_scene_old = roomService.getById(operateMenuVO_cache
					.getMenuMap()
					+ "");
			SceneVO cur_scene_new = roomService.getById(operateMenuVO
					.getMenuMap()
					+ "");
			HashMap<String, OperateMenuVO> fatherMenuList_old = cur_scene_old
					.getFatherMenuList();
			HashMap<String, OperateMenuVO> fatherMenuList_new = cur_scene_new
					.getFatherMenuList();
			operateMenuVO_cache.setMenuMap(operateMenuVO.getMenuMap());
			fatherMenuList_old.remove(operateMenuVO_cache.getId() + "");
			fatherMenuList_new.put(operateMenuVO_cache.getId() + "",
					operateMenuVO_cache);
		}
	}

	/**
	 * 重新加载一条scene
	 */
	public static void reloadOneScene(SceneVO sceneVO)
	{
		SceneCache sceneCache = new SceneCache();
		SceneVO sceneVO_cache = sceneCache.getById(sceneVO.getSceneID() + "");
		sceneVO_cache.setSceneAttribute(sceneVO.getSceneAttribute());
		sceneVO_cache.setSceneCoordinate(sceneVO.getSceneCoordinate());
		sceneVO_cache.setSceneDisplay(sceneVO.getSceneDisplay());
		sceneVO_cache.setSceneID(sceneVO.getSceneID());
		sceneVO_cache.setSceneJumpterm(sceneVO.getSceneJumpterm());
		sceneVO_cache.setSceneKen(sceneVO.getSceneKen());
		sceneVO_cache.setSceneLimit(sceneVO.getSceneLimit());
		sceneVO_cache.setSceneName(sceneVO.getSceneName());
		sceneVO_cache.setScenePhoto(sceneVO.getScenePhoto());
		sceneVO_cache.setSceneShang(sceneVO.getSceneShang());
		sceneVO_cache.setSceneSkill(sceneVO.getSceneSkill());
		sceneVO_cache.setSceneSwitch(sceneVO.getSceneSwitch());
		sceneVO_cache.setSceneXia(sceneVO.getSceneXia());
		sceneVO_cache.setSceneYou(sceneVO.getSceneYou());
		sceneVO_cache.setSceneZuo(sceneVO.getSceneZuo());
		if (sceneVO_cache.getSceneMapqy().equals(sceneVO.getSceneMapqy()))
		{
			MapCache mapCache = new MapCache();
			HashMap<String, MapVO> map_list = mapCache.getMapList();
			MapVO map = map_list.get(sceneVO.getSceneMapqy());
			sceneVO_cache.setMap(map);
			sceneVO_cache.setSceneMapqy(sceneVO.getSceneMapqy());
		}
	}

	/**
	 * 重新加载一条PROP
	 */
	public static void reloadOneProp(PropVO propVO)
	{
		PropVO propVO_cache = PropCache.getPropById(propVO.getPropID());
		propVO_cache.setMarriage(propVO.getMarriage());
		propVO_cache.setPropAccumulate(propVO.getPropAccumulate());
		propVO_cache.setPropAuctionPosition(propVO.getPropAuctionPosition());
		propVO_cache.setPropBonding(propVO.getPropBonding());
		propVO_cache.setPropClass(propVO.getPropClass());
		propVO_cache.setPropDisplay(propVO.getPropDisplay());
		propVO_cache.setPropDrop(propVO.getPropDrop());
		propVO_cache.setPropID(propVO.getPropID());
		propVO_cache.setPropJob(propVO.getPropJob());
		propVO_cache.setPropName(propVO.getPropName());
		propVO_cache.setPropOperate1(propVO.getPropOperate1());
		propVO_cache.setPropOperate2(propVO.getPropOperate2());
		propVO_cache.setPropOperate3(propVO.getPropOperate3());
		propVO_cache.setPropPic(propVO.getPropPic());
		propVO_cache.setPropPosition(propVO.getPropPosition());
		propVO_cache.setPropProtect(propVO.getPropProtect());
		propVO_cache.setPropReconfirm(propVO.getPropReconfirm());
		propVO_cache.setPropReLevel(propVO.getPropReLevel());
		propVO_cache.setPropSell(propVO.getPropSell());
		propVO_cache.setPropSex(propVO.getPropSex());
		propVO_cache.setPropUseControl(propVO.getPropUseControl());
		propVO_cache.setPropUsedegree(propVO.getPropUsedegree());
	}

	/**
	 * 重新加载一条NPC
	 */
	public static void reloadOneNPC(NpcVO npcVO)
	{
		NpcCache npcCache = new NpcCache();
		npcCache.reloadOneNPC(npcVO);
		NpcVO NpcVO_cache = npcCache.getById(npcVO.getNpcID());
		NpcVO_cache.setDefenceDa(npcVO.getDefenceDa());
		NpcVO_cache.setDefenceXiao(npcVO.getDefenceXiao());
		NpcVO_cache.setDrop(npcVO.getDrop());
		NpcVO_cache.setExp(npcVO.getExp());
		NpcVO_cache.setHuoFy(npcVO.getHuoFy());
		NpcVO_cache.setJinFy(npcVO.getJinFy());
		NpcVO_cache.setLevel(npcVO.getLevel());
		NpcVO_cache.setMoney(npcVO.getMoney());
		NpcVO_cache.setMuFy(npcVO.getMuFy());
		NpcVO_cache.setNpcHP(npcVO.getNpcHP());
		NpcVO_cache.setNpcID(npcVO.getNpcID());
		NpcVO_cache.setNpcKey(npcVO.getNpcKey());
		NpcVO_cache.setNpcName(npcVO.getNpcName());
		NpcVO_cache.setNpcRefurbishTime(npcVO.getNpcRefurbishTime());
		NpcVO_cache.setNpcType(npcVO.getNpcType());
		NpcVO_cache.setShuiFy(npcVO.getShuiFy());
		NpcVO_cache.setTake(npcVO.getTake());
		NpcVO_cache.setTuFy(npcVO.getTuFy());
	}
}
