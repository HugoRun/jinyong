package com.ls.web.service.menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ben.vo.task.TaskVO;
import com.dp.dao.credit.CreditProce;
import com.dp.service.credit.CreaditService;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.npc.NpcAttackDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.menu.OperateMenuDao;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.instance.InstanceInfoVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.MenuType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.npc.RefurbishService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.task.TaskSubService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.constant.FinalNumber;
import com.pm.dao.tongsiege.TongSiegeBattleDao;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.tongsiege.TongSiegeControlVO;

/**
 * 功能:菜单npc功能处理
 * 
 * @author 刘帅 1:09:12 PM
 */
public class MenuService
{

	public static Logger logger = Logger.getLogger("log.service");

	/**
	 * 通过id得到一个菜单信息
	 */
	public OperateMenuVO getMenuById(int id)
	{
		MenuCache menuCache = new MenuCache();
		return menuCache.getById(id + "");
	}
	
	/**
	 * 判断该菜单是否在指定scene_id的菜单
	 */
	private OperateMenuVO getMenuByIdAndSceneId( int id,int scene_id )
	{
		OperateMenuVO new_menu_info = null;
		
		OperateMenuVO menu_info = getMenuById(id);
		if(menu_info!=null&& menu_info.getMenuMap()==scene_id)
		{
			new_menu_info = menu_info.clone();
		}
		return new_menu_info;
	}
	
	
	/**
	 * 刷新当前地点的有效菜单
	 * @param roleInfo
	 * @return
	 */
	public List<OperateMenuVO> menuRefurbish(RoleEntity roleInfo)
	{
		OperateMenuVO menu = null;
		
		SceneVO cur_scene = roleInfo.getBasicInfo().getSceneInfo();
		HashMap<String,OperateMenuVO> fatherMenuList = cur_scene.getFatherMenuList();// 所有菜单
		
		HashMap<String,OperateMenuVO> result_menus = new HashMap<String,OperateMenuVO>();
		
		TaskSubService taskSubService = new TaskSubService();
		
		if (fatherMenuList != null && fatherMenuList.size() != 0)
		{
			
			Iterator<String> it = fatherMenuList.keySet().iterator(); 
			String key = null;
			OperateMenuVO effect_menu = null;//有效菜单
			while (it.hasNext()) 
			{  
				key = it.next().toString();
				menu = fatherMenuList.get(key);  
				//判断是否可按刷新时间规则刷新菜单
				if( !DateUtil.isEffectTime(menu.getMenuTimeBegin(), menu.getMenuTimeEnd(), menu.getMenuDayBegin(), menu.getMenuDayEnd(),menu.getWeekStr()))
				{
					//符合刷新时间规则
					continue;
				}
				
				effect_menu = menu.clone();
				result_menus.put(key, effect_menu);
				
				// 判定该菜单是有任务 第一条任务
				String menu_tasks_id = effect_menu.getMenuTasksId();
				if (menu_tasks_id != null && !menu_tasks_id.equals("") && !menu_tasks_id.equals("0"))
				{
					String[] tasks_ids = menu_tasks_id.split(",");// 取出任务ID
					
					TaskVO task_info = null;//任务信息
					CurTaskInfo role_task = null;//玩家未完成的任务
					
					StringBuffer effect_task_ids = new StringBuffer();//有效的任务id,(玩家可以接受的任务id)
					
					for (int t = 0; t < tasks_ids.length; t++)
					{
						if (tasks_ids[t] == null || tasks_ids[t].equals(""))
						{
							continue;
						}
						task_info = taskSubService.getById(Integer.parseInt(tasks_ids[t])+ ""); 
						if (task_info == null)
						{
							continue;
						}
						role_task = (CurTaskInfo) roleInfo.getTaskInfo().getCurTaskList().getByZu(task_info.getTZu());//根据任务组得到身上未完成打任务
						
						//判断该任务是否已完成和是否已经接了该任务组的任务
						if (roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(task_info.getTZu()) == false && role_task==null )
						{
							//如果该任务既没有完成也没有接受过该任务组的任务，则判断任务的接受条件
							if (task_info.getTCycle() == 0 && (task_info.getTLevelXiao() <= roleInfo.getBasicInfo().getGrade())
									&& roleInfo.getBasicInfo().getGrade() <= task_info.getTLevelDa()
									&& Integer.parseInt(task_info.getTZuxl()) == 1 && roleInfo.getTitleSet().isHaveByTitleStr(task_info.getTSchool()) )
							{
								effect_task_ids.append(tasks_ids[t] + ",");
							} 
						}
					}
					
					effect_menu.setMenuTasksId(effect_task_ids.toString());
					if( !effect_task_ids.toString().equals("") )//如果有任务则显示!号
					{
						effect_menu.setMenuName("!" + effect_menu.getMenuName());
					}
				} 
			}
		}
		
		result_menus = addAllTaskMenu(roleInfo,result_menus);
		
		return new ArrayList<OperateMenuVO>(result_menus.values());
	}

	/**
	 * 添加所有任务菜单信息
	 * @param roleInfo
	 * @return
	 */
	public HashMap<String,OperateMenuVO> addAllTaskMenu(RoleEntity roleInfo,HashMap<String,OperateMenuVO> menu_list )
	{
		//从缓存中取出角色身上任务列表
		List<CurTaskInfo> task_list = roleInfo.getTaskInfo().getCurTaskList().getCurTaskList();
		
		if (task_list != null && task_list.size() != 0)
		{
			String tPoint[] = null;//中间点数组
			int point_task_menu_id;//中间点菜单id
			int next_task_menu_id;//任务下一条菜单id
			int cur_task_id;      //任务id
			
			for (CurTaskInfo u_task:task_list )
			{
				// 判断是否还有没完成的中简点
				if (u_task.getTType() == 5 && u_task.getTPoint() != null && !u_task.getTPoint().equals("") && !u_task.getTPoint().equals("0"))
				{
					tPoint = u_task.getTPoint().split(",");
					
					for (int c = 0; c < tPoint.length; c++)
					{
						cur_task_id = u_task.getTId();//当前任务id
						point_task_menu_id =  Integer.parseInt(tPoint[c]);//中间点菜单id
						addOneTaskMenu(roleInfo, menu_list, cur_task_id,point_task_menu_id);
					}
				}
				else//如果没有中间点或中间点任务已完成
				{
					cur_task_id = u_task.getTNext();//下一条任务id
					next_task_menu_id = u_task.getTXrwnpcId();//下一条任务菜单id
					addOneTaskMenu(roleInfo, menu_list,cur_task_id, next_task_menu_id);
				}
			}
		}
		
		return menu_list;
	}

	/**
	 * 增加一个任务菜单，包括中间点菜单，和任务下一条菜单
	 * @param roleInfo
	 * @param menus_result
	 * @param u_task                       需要加入菜单任务的任务id
	 * @param task_menu_id                 任务菜单id
	 */
	private void addOneTaskMenu(RoleEntity roleInfo,HashMap menus_result,int task_id ,int task_menu_id )
	{
		if( roleInfo==null || menus_result ==null )
		{
			return ;
		}
		TaskSubService taskSubService = new TaskSubService();
		String key = task_menu_id+"";
		OperateMenuVO cur_menu = null;
		if( menus_result.containsKey(key) )
		{
			//已经有该菜单
			cur_menu = (OperateMenuVO)menus_result.get(key);
			String menu_old_name = cur_menu.getMenuName();
			String menu_new_name = "";
			if( menu_old_name.indexOf("!") != -1 ) //如果有第一条任务
			{
				menu_new_name = menu_old_name.replace("!", "?");
			}
			else //如果不是第一条任务菜单且已经是中间点的菜单
			{
				if( menu_old_name.indexOf("?") == -1 )//如果该菜单是无任务打菜单，则加问号
				{
					menu_new_name = "?"+menu_old_name;
				}
				else
				{
					menu_new_name = menu_old_name;
				}
			}
			cur_menu.setMenuName(menu_new_name);
			cur_menu.setMenuTasksId(cur_menu.getMenuTasksId()+","+task_id);
		}
		else
		{
			//构造一个新的菜单
			int scene_id = Integer.parseInt(roleInfo.getBasicInfo().getSceneId());
			OperateMenuVO new_task_menu = getMenuByIdAndSceneId(task_menu_id,scene_id);
			
			if( new_task_menu==null )
			{
				logger.debug("菜单不在该场景下，任务菜单id:"+task_menu_id+";当前scene_id:"+scene_id);
				return;
			}
			
			if( !DateUtil.isEffectTime(new_task_menu.getMenuTimeBegin(), new_task_menu.getMenuTimeEnd(), new_task_menu.getMenuDayBegin(), new_task_menu.getMenuDayEnd(),new_task_menu.getWeekStr()))
			{
				//符合刷新时间规则
				return;
			}
			
			String menu_tasks_id = new_task_menu.getMenuTasksId();
			if (menu_tasks_id != null && !menu_tasks_id.equals("") && !menu_tasks_id.equals("0"))
			{
				String[] tasks_ids = menu_tasks_id.split(",");// 取出任务ID
				
				TaskVO task_info = null;//任务信息
				CurTaskInfo role_task = null;//玩家未完成的任务
				StringBuffer effect_task_ids = new StringBuffer();//有效的任务id,(玩家可以接受的任务id)
				
				for (int t = 0; t < tasks_ids.length; t++)
				{
					if (tasks_ids[t] == null || tasks_ids[t].equals(""))
					{
						continue;
					}
					task_info = taskSubService.getById(Integer.parseInt(tasks_ids[t])+ ""); 
					if (task_info == null)
					{
						continue;
					}
					role_task = (CurTaskInfo) roleInfo.getTaskInfo().getCurTaskList().getByZu(task_info.getTZu());//根据任务组得到身上未完成打任务
					
					//判断该任务是否已完成和是否已经接了该任务组的任务
					if (roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(task_info.getTZu()) == false && role_task==null )
					{
						//如果该任务既没有完成也没有接受过该任务组的任务，则判断任务的接受条件
						if (task_info.getTCycle() == 0 && (task_info.getTLevelXiao() <= roleInfo.getBasicInfo().getGrade())
								&& roleInfo.getBasicInfo().getGrade() <= task_info.getTLevelDa() 
								&& (roleInfo.getTitleSet().isHaveByTitleStr(task_info.getTSchool())==true)
								&& Integer.parseInt(task_info.getTZuxl()) == 1 )
						{
							effect_task_ids.append(tasks_ids[t] + ",");
						} 
					}
				}
				new_task_menu.setMenuTasksId(effect_task_ids.toString());
			} 
			
			new_task_menu.setMenuName("?"+new_task_menu.getMenuName());
			new_task_menu.setMenuTasksId(new_task_menu.getMenuTasksId()+","+task_id);
			menus_result.put(key, new_task_menu);//
		}
	}
	
	/**
	 * 任务的新菜单
	 * @param roleInfo
	 * @param menus_result
	 * @param new_menu
	 * @param u_task
	 *//*
	private void addPointByCondition(RoleEntity roleInfo,HashMap menus_result,CurTaskInfo u_task )
	{
		String map_id = roleInfo.getBasicInfo().getSceneId();
		int npcid = u_task.getTXrwnpcId();
		
		// 如果没有中间点 那么判断其他任务   
		OperateMenuVO new_menu = getMenuByIdAndSceneId(npcid,Integer.parseInt(map_id));
		
		if( menus_result ==null || new_menu==null )
		{
			return ;
		}
		int taskid = u_task.getTNext();
		//通过任务ID从cache取出任务数据
		TaskCache taskCache = new TaskCache();
		TaskVO taskVO = taskCache.getById(taskid+""); 
		if (taskVO.getTLevelXiao() <= roleInfo.getBasicInfo().getGrade())
		{
			if (roleInfo.getBasicInfo().getGrade() <= taskVO.getTLevelDa())
			{
				if (roleInfo.getBasicInfo().getSex() == taskVO.getTSex() || taskVO.getTSex() == 0)
				{
					if (taskVO.getTSchool().indexOf(roleInfo.getBasicInfo().getTitle()) != -1 || taskVO.getTSchool().equals("0"))
					{
						this.addPoint(roleInfo, menus_result, new_menu, u_task);
					}
				}
			}
		}
	}*/

	
	/**
	 * 维修菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String maintain(OperateMenuVO menu, Object object)
	{
		StringBuffer result = new StringBuffer();

		return result.toString();
	}

	/**
	 * 可以打死的npc菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createDeadNPCMenu(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		RoleEntity roleEntity = (RoleEntity) object;
		int mapId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());

		// 创建npc条件
		String condition = menu.getMenuOperate1();

		// npcAttackDao.deleteByPpk(userTempBean.getPPk());// 清空npc

		// 创建npc
		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(roleEntity.getBasicInfo()
				.getPPk(), condition, NpcAttackVO.DEADNPC, mapId);

		return null;
	}

	/**
	 * 可以打败的npc菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createLoseNPCMenu(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		RoleEntity roleEntity = (RoleEntity) object;
		int mapId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());

		// 创建npc条件
		String condition = menu.getMenuOperate1();

		// npcAttackDao.deleteByPpk(userTempBean.getPPk());// 清空npc

		// 创建npc
		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(roleEntity.getBasicInfo()
				.getPPk(), condition, NpcAttackVO.LOSENPC, mapId);

		return null;
	}
	
	/**
	 * 可以被大家打的npc菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createAllAttackNPCMenu(OperateMenuVO menu, RoleEntity roleEntity)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu或roleEntity为空");
			return null;
		}
		
		int sceneId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());
		
		// 创建npc条件
		String condition = menu.getMenuOperate2();

		NpcVO npc = NpcCache.getById(Integer.parseInt(condition));
		// 创建npc
		NpcAttackDao npcAttackDao = new NpcAttackDao();		
		npcAttackDao.createByNpc(npc,roleEntity.getPPk(), 1, NpcAttackVO.DIAOXIANG, sceneId,0);
		

		return null;
	}
	
	/**
	 * 打人不还手的菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createZhaoAttackNPCMenu(OperateMenuVO menu, RoleEntity roleEntity,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu或roleEntity为空");
			return null;
		}
		
	/*	OperateMenuDao menuDao = new OperateMenuDao();
		menu = menuDao.getMenuById(menu.getId());
		
		int sceneId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());

		// 创建npc条件
		String condition = menu.getMenuOperate1();

		// 特殊字段3存放的 此招魂幡是属于那个帮派所有
		String menuTongString = menu.getMenuOperate3();
		
		
		RoomService  roomService = new RoomService();
		SceneVO sceneVO = roomService.getById(sceneId+"");
		TongSiegeBattleService tongSiegeBattleService  = new TongSiegeBattleService();
		TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(Integer.parseInt(sceneVO.getSceneMapqy()));
		TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService.getSiegeTongInfo(tongSiegeBattleVO.getSiegeId()+"");
		
		// 到城门被攻破后方能被攻击
		if ( tongSiegeControlVO.getNowPhase() < FinalNumber.FORTH) {
			String hint = "现在还不能攻击招魂幡!";
			return hint;
		}
		
		PartInfoDAO infoDAO = new PartInfoDAO();
		int tongPk = infoDAO.getPartTong(roleEntity.getBasicInfo().getPPk()+"");
		
		if ( menuTongString.equals(tongPk+"") ) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("此招魂幡是贵帮派所有!<br/>");
//			sBuffer.append("<anchor><go method=\"post\"   href=\""+response.encodeURL(GameConfig.getContextPath()+"/tongEndaction.do")+"\"> ");
//			sBuffer.append("<postfield name=\"cmd\" value=\"n3\" /> ");
//			sBuffer.append("<postfield name=\"menu_id\" value=\""+menu.getId()+"\" /> ");
//			sBuffer.append("</go>");
//			sBuffer.append("领取招魂幡祝福");
//			sBuffer.append("</anchor>");
			
			return sBuffer.toString();			
		} else {
    		// 创建npc
    		RefurbishService refurbishService = new RefurbishService();
    				
    		refurbishService.createNPCByCondition(roleEntity.getBasicInfo()
    				.getPPk(), condition, NpcAttackVO.ZHAOHUN, sceneId);
		}
		
*/
		return null;
	}
	

	/**
	 * 刷新旗杆npc
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createFlagNPCMenu(OperateMenuVO menu, RoleEntity roleEntity)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu或roleEntity为空");
			return null;
		}
		String mapId = roleEntity.getBasicInfo().getSceneId();

		// 创建npc条件
		String condition = menu.getMenuOperate1();

		// 创建npc
		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(roleEntity.getPPk(),condition, NpcAttackVO.MAST, Integer.parseInt(mapId));
		 
		SystemInfoService systemInfoService = new SystemInfoService();
		RoomService roomService = new RoomService();
		String sceneName = roomService.getName(menu.getMenuMap());

		StringBuffer systemInfo = new StringBuffer("位于");
		systemInfo.append(sceneName).append("的旗杆被");

		systemInfo.append("攻击了!");

		systemInfoService.insertSystemInfoByBarea(systemInfo.toString(),
				FinalNumber.FIELDBAREA);

		return null;
	}

	/**
	 * 列出父菜单的子菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String fatherList(RoleEntity roleInfo,OperateMenuVO father_menu,HttpServletRequest request,HttpServletResponse response)
	{
		if (father_menu == null)
		{
			logger.debug("menu为空");
			return null;
		}
		StringBuffer result = new StringBuffer();
		OperateMenuVO menu = null;
		OperateMenuVO son_menu = null;

		OperateMenuDao menuDao = new OperateMenuDao();
		List<OperateMenuVO> menus = menuDao.getSonMenuByMap(father_menu.getId(),roleInfo.getBasicInfo().getPRace()); // 所有子菜单
		List<OperateMenuVO> display_menus = null; // 有效子菜单

		result.append(father_menu.getMenuName() + "<br/>");

		if (father_menu.getMenuDialog() != null
				&& !father_menu.getMenuDialog().equals(""))
			result.append(father_menu.getMenuDialog() + "<br/>");

		if (menus != null && menus.size() != 0)
		{
			logger.info("子菜单数量=" + menus.size());
			for (int i = menus.size() - 1; i >= 0; i--)
			{

				menu = menus.get(i);
				if (DateUtil.isTimenowBetweenBeginEnd(menu.getMenuDayBegin(),
						menu.getMenuDayEnd()))
				{

					continue;
				}
				else
					if (DateUtil.isDatenowBetweenBeginEnd(menu
							.getMenuTimeBegin(), menu.getMenuTimeEnd()))
					{
						continue;

					}
					else
						if (menu.getMenuDayBegin().trim().equals("")
								&& menu.getMenuDayEnd().trim().equals("")
								&& menu.getMenuTimeBegin().trim().equals("")
								&& menu.getMenuTimeEnd().trim().equals(""))
						{
							continue;
						}
						else
						{
							menus.remove(i);
						}
			}

			display_menus = menus;
			logger.info("有效子菜单为=" + display_menus.size());
			for (int i = 0; i < display_menus.size(); i++)
			{
				son_menu = display_menus.get(i);

				// 如果是描述性菜单就只显示其名, 不用在其下加链接.
				if (son_menu.getMenuType() == MenuType.DISPLAYOPERATE)
				{
					result.append(son_menu.getMenuName());
					result.append("<br/>");
				}
				else
				{
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/menu.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n1\" /> ");
					result.append("<postfield name=\"menu_id\" value=\"" + son_menu.getId() + "\"/> ");
					result.append("</go>");
					result.append(son_menu.getMenuName());
					result.append("</anchor><br/>");
				}
			}
		}
		return result.toString();
	}

	/**
	 * 休息菜单处理
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String rest(OperateMenuVO menu, Object object)
	{
		RoleEntity roleEntity = (RoleEntity) object;
		StringBuffer result = new StringBuffer();
		PlayerService se = new PlayerService();
		PartInfoVO vo = se.getPlayerByPpk(roleEntity.getBasicInfo().getPPk());
		int hp = vo.getPMaxHp();
		int mp = vo.getPMaxMp();

		roleEntity.getBasicInfo().updateHp(hp);
		roleEntity.getBasicInfo().updateMp(mp);

		String need_money = menu.getMenuOperate1();

		long money = roleEntity.getBasicInfo().getCopper();

		if (need_money != null && Integer.parseInt(need_money) <= money)
		{	
			result.append("您的体力，内力都已加满! ");
			
			//监控
			LogService logService = new LogService();
			logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", "-"+need_money, "住店");
			
			roleEntity.getBasicInfo().addCopper(-Integer.parseInt(need_money));
			// 执行统计
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(6, StatisticsType.MONEY, Integer
					.parseInt(need_money), StatisticsType.USED,
					StatisticsType.XITONG, roleEntity.getBasicInfo().getPPk());

		}
		else
		{
			result.append("您的银两足！住店需要"
					+ MoneyUtil.changeCopperToStr(Integer.parseInt(need_money))
					+ "!");
		}

		return result.toString();
	}

	/*
	 * 传送菜单功能字段 menu_operate1储存传输目的地 menu_operate2储存要 求道具 表现形式为:1,3-3,2-0,200
	 * 说明:不同道具用"-"分割；每个道具逗号之前表示道具id,逗号之后表示道具数量；道具id为0时表示金钱
	 * menu_operate3储存是否删除该道具 说明:内容为删除时，表示删除，为空表示不用删除
	 */

	/**
	 * 任务传送菜单处理
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String taskCarry(OperateMenuVO menu, int pPk,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		MountsService ms=new MountsService();
		TimeControlService tcs=new TimeControlService();
		RoleEntity role_info = RoleService.getRoleInfoById(pPk + "");
		String condition = menu.getMenuOperate2();
		StringBuffer result = new StringBuffer();
		StringBuffer condition_display = new StringBuffer();
		UserMountsVO uv=role_info.getMountSet().getCurMount();
		if(uv==null)
		{
			result.append("您没有乘骑坐骑");
			return result.toString();
		}
		else
		{
			MountsVO mv= ms.getMountsInfo(uv.getMountsID());
			if(mv.getLevel()==1)//只有一级坐骑才有任务传送限制
			{
				
				boolean isUse=tcs.isUseable(pPk, uv.getMountsID(), TimeControlService.ANOTHERPROP, mv.getCarryNum1());
				if(isUse)
				{
				}
				else//每天的使用次数已满需要扣费
				{
					int needCopper=mv.getOverPay1();
					long haveCopper=role_info.getBasicInfo().getCopper();
					if(needCopper>haveCopper)
					{
						result.append("今日坐骑使用次数已满需要扣除灵石，灵石不足不能传送");
						return result.toString();
					}
					else
					{
						tcs.updateControlInfo(pPk, uv.getMountsID(), uv.getMountsLevle());
						role_info.getBasicInfo().addCopper(-needCopper);
					}
				}
			}
			
		}
		// 没有传送条件
		if (condition == null || condition.equals(""))
		{
			return null;
		}
		else
		// 有传送条件
		{
			boolean isCarryed = true; // 是否可以传送
			boolean isConfirm = false; // 是否要二次确认
			String conditions[] = condition.split("-");
			String prop[] = null;
			for (int i = 0; i < conditions.length; i++)
			{
				prop = conditions[i].split(",");
				if (prop[0].equals("0"))// 要求的是钱数
				{
					long copper_num = role_info.getBasicInfo().getCopper();
					if (copper_num < Integer.parseInt(prop[1]))
					{
						isCarryed = false;
						result.append("<br/>需要"
								+ MoneyUtil.changeCopperToStr(prop[1])
								+ ",您目前有:"
								+ MoneyUtil.changeCopperToStr((int) copper_num)
								+ ",金额不足。");
					}
					else
					{
						condition_display.append("需要"
								+ MoneyUtil.changeCopperToStr(prop[1]));
					}
				}
				else
				// 要求的是道具
				{
					//PropDao propDao = new PropDao();
					PropCache propCache = new PropCache();
					PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
					String prop_name = propCache.getPropNameById(Integer
							.parseInt(prop[0]));
					int prop_num = propGroupDao.getPropNumByByPropID(pPk,
							Integer.parseInt(prop[0]));
					if (prop_num < Integer.parseInt(prop[1]))
					{
						isCarryed = false;
						result.append("<br/>需要" + prop[1] + "个" + prop_name
								+ ",您目前有:" + prop_num + "个,数量不够。");
					}
					else
					{
						condition_display.append(",需要" + prop[1] + "个"
								+ prop_name + "");
						isConfirm = true;
					}
				}
			}
			if (isCarryed)// 可以传送,但要二次确认
			{
				/*
				 * if (menuTasksId == null || (menuTasksId != null &&
				 * menuTasksId.equals(""))) {
				 */
				if (isConfirm)
				{
					result.append("传送需要:");
					result.append(condition_display.toString());
					result.append("<br/>");
					result.append("<anchor> ");
					// 这里有个/j yg、ame
					//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/menu.do\"> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/menu.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n3\" /> ");
					result.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
					result.append("</go>");
					result.append("确定");
					result.append("</anchor>");
				}
				else
				{
					return null;// 直接传送
				}
				/* } */
			}
			else
			{
				return "传送条件不符合" + result.toString();
			}
		}

		return result.toString();
	}

	/**
	 * 传送消耗处理 condition传送条件
	 * 
	 * @param menu
	 * @param object
	 */
	public void carrayExpend(OperateMenuVO menu, RoleEntity roleEntity)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu或roleEntity为空");
			return;
		}

		// 丢弃物品道具
		String condition = menu.getMenuOperate2();
		if (!condition.equals(""))
		{
			String conditions[] = condition.split("-");
			String prop[] = null;
			for (int i = 0; i < conditions.length; i++)
			{
				prop = conditions[i].split(",");
				if (prop[2].equals("1"))// 消耗物品
				{
					if (prop[0].equals("0"))// 要求的是钱数
					{
						
						//监控
						LogService logService = new LogService();
						logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", "-"+prop[1], "传送");
						
						roleEntity.getBasicInfo().addCopper(
								-Integer.parseInt(prop[1]));// 消耗金钱
						// 执行统计
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(6, StatisticsType.MONEY, Integer
								.parseInt(prop[1]), StatisticsType.USED,
								StatisticsType.CHUANSONG, roleEntity
										.getBasicInfo().getPPk());

					}
					else
					// 要求的是道具
					{
						GoodsService goodsService = new GoodsService();
						goodsService.removeProps(roleEntity
								.getBasicInfo().getPPk(), Integer
								.parseInt(prop[0]), Integer.parseInt(prop[1]),GameLogManager.R_EXCHANGE);
					}
				}

			}
		}
		String buffEffectString = menu.getMenuOperate1();		
		if (buffEffectString != null && !buffEffectString.equals("") ) {
			String[] buffEffectStrings  =  buffEffectString.split(";");
			if( buffEffectStrings.length >= 3){
				// 声望限制
				String[] recourse = buffEffectStrings[2].split(",");
				if(recourse[0].equals("0")){
					
				}else{
				Integer cid=Integer.parseInt(recourse[0]);
				Integer ncount=Integer.parseInt(recourse[1]);
				Integer ppk=roleEntity.getBasicInfo().getPPk();
				CreaditService service=new CreditProce();
				service.subtractCredit(ppk, cid, ncount);
				}	
			}
		}
	}

	/**
	 * 普通对话类菜单处理
	 * 
	 * @return
	 */
	public String dialog(OperateMenuVO menu)
	{
		StringBuffer result = new StringBuffer();
		result.append(menu.getMenuDialog());
		return result.toString();
	}

	/**
	 * 帮会建设页面跳转
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String tongjump(OperateMenuVO menu,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		// 1等级 2钱 3物品
		StringBuffer result = new StringBuffer();
		result.append(menu.getMenuDialog());
		result.append("<br/>");
		result.append("<anchor> ");
		// 这里有个/jy。game
		//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/tongaction.do\"> ");
		result.append("<go method=\"post\" sendreferer=\"true\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/tongaction.do")+"\">");
		result.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
		result.append("</go>");
		result.append("我要创建帮会");
		result.append("</anchor>");
		return result.toString();
	}

	/**
	 * 帮会建设页面跳转
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String recruit(OperateMenuVO menu, Object object,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		StringBuffer result = new StringBuffer();
		result.append(menu.getMenuDialog());
		result.append("<br/>");
		result.append("<anchor> ");
		//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/recruitaction.do?cmd=n1\"> ");
		
		result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/recruitaction.do?cmd=n1")+"\">");
		
		result.append("</go>");
		result.append("发布招募");
		result.append("</anchor>");
		result.append("<br/>");
		result.append("<anchor> ");
		//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/tongbegaction.do?cmd=n1\"> ");
		
		result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/tongbegaction.do?cmd=n1")+"\">");
		
		result.append("</go>");
		result.append("申请入帮");
		result.append("</anchor>");
		return result.toString();
	}

	/**
	 * 副本的入口菜单
	 */
	public String instanceDoor(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		StringBuffer result = new StringBuffer();

		RoleEntity roleInfo = (RoleEntity) object;

		InstanceService instanceService = new InstanceService();
		GroupService groupService = new GroupService();

		String instance_id_str = menu.getMenuOperate1(); // 副本id
		int instance_id = -1;

		try
		{
			instance_id = Integer.parseInt(instance_id_str);
		}
		catch (Exception e)
		{
			logger.info("副本id:" + instance_id_str + ",参数错误");
		}

		String hint = null;

		InstanceInfoVO instanceInfo = null;
		instanceInfo = instanceService.getInfoById(instance_id);

		if (instanceInfo == null)
		{
			hint = "无效instance_id:" + instance_id;
			logger.info(hint);
			return hint;
		}

		int group_member_num = 0;// 玩家所在队伍人数
		group_member_num = groupService.getGroupNumByMember(roleInfo
				.getBasicInfo().getPPk());

		if (group_member_num < instanceInfo.getGroupLimit())
		{
			result.append("队伍人数不够,需要" + instanceInfo.getGroupLimit()
					+ "人才能进入副本<br/>");
			logger.info(hint);
		}
		else
			if (roleInfo.getBasicInfo().getGrade() < instanceInfo
					.getLevelLimit())
			{
				result.append("等级不够,需要" + instanceInfo.getLevelLimit()
						+ "级才可以进入副本<br/>");
				logger.info(hint);
			}
			else
			{

				if (instanceService.isReset(instanceInfo))// 判断是否重置
				{
					// 副本重置
					instanceService.reset(roleInfo.getBasicInfo().getPPk(),
							instanceInfo.getMapId());
				}
				roleInfo.getBasicInfo().updateSceneId(instanceInfo.getEnterSenceId() + "");// 不重置也可进入打小怪
				//判断副本类型
				/*if(menu.getMenuType() == Integer.parseInt(GameConfig.getPropertiesObject("menu_instance_type"))){
					new PopUpMsgService().addSysSpecialMsg(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(menu.getMenuOperate2()),0, PopUpMsgType.MENU_INSTANCE);
				}*/
				return null;
			}

		return result.toString();
	}

	/**
	 * 攻城战场传送菜单
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 */
	public String tongSiegeCarry(OperateMenuVO menu, RoleEntity roleInfo,HttpServletResponse response)
	{
		String hintString = null;
		String siegeId = menu.getMenuOperate3();
		
		TongSiegeBattleDao tongSiegeBattleDao  = new TongSiegeBattleDao();
		TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleDao.getSiegeTongInfo(siegeId);
		
//		if ( tongSiegeControlVO.getNowPhase() != 0) {
//			hintString = "战斗尚未开始,请等待战斗开始!";
//		}
		Date tongStartTime = null;
		Date dt1 = new Date();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			tongStartTime = sdFormat.parse(tongSiegeControlVO.getSiegeStartTime());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		if ( dt1.getTime() < tongStartTime.getTime()) {
			hintString = "战斗尚未开始,请等待战斗开始!";
		}
		return hintString;
	}
	
	
	
	/**
	 * 普通传送菜单
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 */
	public String normalCarry(OperateMenuVO menu, Object object,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}

		String goods_require = menu.getMenuOperate2(); // 传送物品要求
		String grade_and_taskzu = menu.getMenuOperate3(); // 传送等级和任务要求

		String grade_require_str = null;
		String task_zu_str = null;
		if (grade_and_taskzu != null && grade_and_taskzu.length() != 0)
		{
			String[] conditions = grade_and_taskzu.split("-");

			if (conditions.length == 1)
			{
				grade_require_str = conditions[0];
			}

			if (conditions.length == 2)
			{
				grade_require_str = conditions[0];
				task_zu_str = conditions[1]; // 传送任务要求
			}
		}

		RoleEntity roleEntity = (RoleEntity) object;

		StringBuffer result = new StringBuffer();
		StringBuffer condition_display = new StringBuffer();

		boolean isCarryed = true; // 是否可以传送
		boolean isConfirm = false; // 是否要二次确认
		
		
		if (grade_require_str != null && !grade_require_str.equals(""))
		{
			String[] grade_require = grade_require_str.split(",");
			if (grade_require.length == 2
					&& (roleEntity.getBasicInfo().getGrade() < Integer
							.parseInt(grade_require[0]) || roleEntity
							.getBasicInfo().getGrade() > Integer
							.parseInt(grade_require[1])))
			{
				isCarryed = false;
				if (Integer.parseInt(grade_require[1]) == 1000)
				{
					result.append("<br/>传送等级要求在"
							+ Integer.parseInt(grade_require[0]) + "级以上");
				}
				else
				{
					result.append("<br/>传送等级要求在"
							+ Integer.parseInt(grade_require[0]) + "级到"
							+ Integer.parseInt(grade_require[1]) + "级之间");
				}
			}
		}
				

		if (task_zu_str != null && !task_zu_str.equals(""))
		{
			// 判断玩家是否完成此任务组
			TaskSubService taskService = new TaskSubService();
			if (!taskService.isCompletedOneOfMany(roleEntity.getBasicInfo()
					.getPPk(), task_zu_str))
			{
				isCarryed = false;
				// result.append("<br/>没有完成任务111111");
			}
		}

		// 以","来分隔物品需求和禁止buff传送ID
		if (goods_require != null && !goods_require.equals(""))
		{
			String goods_requires[] = goods_require.split("-");
			String prop[] = null;
			for (int i = 0; i < goods_requires.length; i++)
			{
				prop = goods_requires[i].split(",");
				if (prop[0].equals("0"))// 要求的是钱数
				{
					long copper_num = roleEntity.getBasicInfo().getCopper();

					if (copper_num < Integer.parseInt(prop[1]))
					{
						isCarryed = false;
						result.append("<br/>需要"
								+ MoneyUtil.changeCopperToStr(prop[1])
								+ ",您目前有:"
								+ MoneyUtil.changeCopperToStr((int) copper_num)
								+ ",金额不足。");
					}
					else
					{
						condition_display.append("需要"
								+ MoneyUtil.changeCopperToStr(prop[1]));
					}
				}
				else
				// 要求的是道具
				{
					//PropDao propDao = new PropDao();
					PropCache propCache = new PropCache();
					PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
					String prop_name = propCache.getPropNameById(Integer
							.parseInt(prop[0]));
					int prop_num = propGroupDao
							.getPropNumByByPropID(roleEntity.getBasicInfo()
									.getPPk(), Integer.parseInt(prop[0]));
					if (prop_num < Integer.parseInt(prop[1]))
					{
						isCarryed = false;
						result.append("<br/>需要" + prop[1] + "个" + prop_name
								+ "，您目前有:" + prop_num + "个，数量不够。");
					}
					else
					{
						condition_display.append(",需要" + prop[1] + "个"
								+ prop_name + "<br/>");
						isConfirm = true;
					}
				}
			}
		}
		
		
		String buffEffectString = menu.getMenuOperate1();		
		if (buffEffectString != null && !buffEffectString.equals("") ) {
			String[] buffEffectStrings  =  buffEffectString.split(";");
			if ( buffEffectStrings.length >= 2 ) {		// 如果大于等于2,则说明有buff类限制
				String[] buffs = buffEffectStrings[1].split(",");
				if(buffs[0].equals("0")){
					
				}else{
					for ( int i=0;i<buffs.length;i++) {
						BuffEffectDao buffEffectDao = new BuffEffectDao();
						BuffDao buffDao = new BuffDao();
			    		int buffType = buffDao.getBuffType(buffs[i]);
			    		// 如果返回值大于-1,代表已经有这种buff,返回这种buff的ID
			    		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
			    		if( buffEffectVO != null && buffEffectVO.getBuffId() == Integer.parseInt(buffs[i]) ) {
			    				result.append(".您身上有").append(buffEffectVO.getBuffName()).append("效果,因此不能传送!");
			    				isCarryed = false;
			    		}
					}
				}
			}
			if( buffEffectStrings.length >= 3){
				// 声望限制
				String[] recourse = buffEffectStrings[2].split(",");
				if(recourse[0].equals("0")){
					
				}else{
				Integer cid=Integer.parseInt(recourse[0]);
				Integer ncount=Integer.parseInt(recourse[1]);
				Integer ppk=roleEntity.getBasicInfo().getPPk();
				CreaditService service=new CreditProce();
				Integer res=service.checkHaveCondition(ppk, cid, ncount);
				if(res==-1||res.equals(-1)){
					result.append(".您身上有").append("您暂无此声望!");
	    			isCarryed = false;
				}else if(res==0||res.equals(0)){
					result.append(".您身上有").append("您的声望不足以兑换此物品!");
	    			isCarryed = false;
				}
				}
			}
			
			if( buffEffectStrings.length >= 4){
				// 称号限制
				if( roleEntity.getTitleSet().isHaveByTitleStr(buffEffectStrings[3])==false)
				{
					result.append(".您的称号不符!");
					isCarryed = false;
				}
			}
			
		}
		
		if (isCarryed)// 可以传送
		{
			if (isConfirm)
			{
				result.append("传送需要:");
				result.append(condition_display.toString());
				result.append("<anchor> ");
				//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/menu.do\"> ");
				
				result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/menu.do")+"\">");
				
				result.append("<postfield name=\"cmd\" value=\"n3\" /> ");
				result.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
				result.append("</go>");
				result.append("确定");
				result.append("</anchor>");
			}
			else
			{
				return null;// 直接传送
			}

		}
		else
		{
			return "传送条件不符合" + result.toString();
		}

		return result.toString();
	}

	/**
	 * 帮派地图传送
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 */
	public String TongMapCarry(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}

		RoleEntity roleEntity = (RoleEntity) object;
		StringBuffer result = new StringBuffer();
		// 判断玩家是否有帮派
		if (roleEntity.getBasicInfo().getFaction() != null)
		{
			return null;
		}
		else
		{
			// 传从条件不符
			return "您没有加入帮派" + result.toString();
		}
	}

	/**
	 * 关闭pk开关菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String closePK(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		StringBuffer result = new StringBuffer();
		RoleEntity roleEntity = (RoleEntity) object;
		
		Date pk_changetime = roleEntity.getBasicInfo().getPkChangeTime();
		if (roleEntity.getBasicInfo().getGrade() < 30)
		{
			result.append("您还没有到30级");
		}
		else if( roleEntity.getBasicInfo().getPkSwitch()==1 )
		{
			result.append("PK开关已是关闭状态");
		}
		else if (!DateUtil.isOverdue(pk_changetime, 24 * 60 * 60))
		{
			result.append("不能改变PK状态，距离上次状态更改时间没有超过24小时。");
		}
		else
		{
			result.append("PK开关已关闭");
			roleEntity.getBasicInfo().updatePkSwitch(1);
		}
		
		return result.toString();
	}

	/**
	 * 打开pk开关菜单
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String openPK(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu或object为空");
			return null;
		}
		StringBuffer result = new StringBuffer();
		RoleEntity roleEntity = (RoleEntity) object;
		
		Date pk_changetime = roleEntity.getBasicInfo().getPkChangeTime();
		
		if (roleEntity.getBasicInfo().getGrade() < 30)
		{
			result.append("您还没有到30级");
		}
		else if (roleEntity.getBasicInfo().getPkSwitch()==2)
		{
			result.append("PK开关已是打开状态");
		}
		else if (!DateUtil.isOverdue(pk_changetime, 24 * 60 * 60))
		{
			result.append("不能改变PK状态，距离上次状态更改时间没有超过24小时。");
		}
		else
		{
			result.append("PK开关已打开");
			roleEntity.getBasicInfo().updatePkSwitch(2);
		}

		return result.toString();
	}

	/**
	 * 获得npc_id依靠任务id
	 * 
	 * @param taskid
	 * @return
	 */
	public OperateMenuVO getNpcIdByTaskId(String taskid)
	{
		OperateMenuDao menuDao = new OperateMenuDao();
		OperateMenuVO npcId = menuDao.getNpcIdByTaskId(taskid);

		return npcId;
	}
	
	/**
	 * 获得menuvo依靠 sceneId和menuOperate1
	 * 
	 * @param taskid
	 * @return
	 */
	public OperateMenuVO getOperateMenuVOBySceneAndOperate1(String sceneId,String menuOperate1)
	{
		OperateMenuDao menuDao = new OperateMenuDao();
		OperateMenuVO npcId = menuDao.getOperateMenuVOBySceneAndOperate1(sceneId,menuOperate1);

		return npcId;
	} 
	
	/**
	 * 获得menuvo依靠 sceneId和menuOperate1
	 * 
	 * @param taskid
	 * @return
	 */
	public int updateZHAOHUN(String sceneId,String menuOperate1,int tongpk)
	{
		if ( tongpk != 0) {
    		OperateMenuDao menuDao = new OperateMenuDao();
    		return menuDao.updateZHAOHUN(sceneId,menuOperate1,tongpk);
		}
		return -1;
	}

	/**
	 * 获得描述性父菜单
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 * 
	 * public String displayFatherList(OperateMenuVO father_menu, UserTempBean
	 * userTempBean) { if (father_menu == null) { logger.debug("menu为空"); return
	 * null; } StringBuffer result = new StringBuffer(); OperateMenuVO menu =
	 * null; OperateMenuVO son_menu = null;
	 * 
	 * OperateMenuDao menuDao = new OperateMenuDao(); List menus =
	 * menuDao.getSonMenuByMap(father_menu.getId()); //所有子菜单 List display_menus =
	 * null; //有效子菜单
	 * 
	 * result.append(father_menu.getMenuName() + "<br/>");
	 * result.append(father_menu.getMenuDialog() + "<br/>");
	 * 
	 * if (menus != null && menus.size() != 0) { logger.info("子菜单数量=" +
	 * menus.size()); for (int i = menus.size()-1; i >= 0; i--) {
	 * 
	 * menu = (OperateMenuVO) menus.get(i); if
	 * (DateUtil.isTimenowBetweenBeginEnd(menu.getMenuDayBegin(),menu.getMenuDayEnd())) {
	 * 
	 * continue; } else if
	 * (DateUtil.isDatenowBetweenBeginEnd(menu.getMenuTimeBegin(),
	 * menu.getMenuTimeEnd())) { continue; } else if
	 * (menu.getMenuDayBegin().trim().equals("") &&
	 * menu.getMenuDayEnd().trim().equals("") &&
	 * menu.getMenuTimeBegin().trim().equals("") &&
	 * menu.getMenuTimeEnd().trim().equals("")) { continue; } else {
	 * menus.remove(i); } }
	 * 
	 * 
	 * display_menus = menus; logger.info("有效子菜单为="+display_menus.size()); for
	 * (int i = 0; i < display_menus.size(); i++) { son_menu = (OperateMenuVO)
	 * display_menus.get(i);
	 * 
	 * result.append(son_menu.getMenuDialog()); result.append("<br/>");
	 * result.append("<anchor> "); result.append("<go method=\"post\"
	 * sendreferer=\"true\" href=\"/menu.do\"> "); //这里有个/j、ygame
	 * result.append("<postfield name=\"cmd\" value=\"n1\" /> ");
	 * result.append("<postfield name=\"menu_id\" value=\"" + son_menu.getId() +
	 * "\" /> "); result.append("</go>");
	 * result.append(son_menu.getMenuName()); result.append("</anchor><br/>"); } }
	 * return result.toString(); }
	 */
}
