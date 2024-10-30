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
 * ����:�˵�npc���ܴ���
 * 
 * @author ��˧ 1:09:12 PM
 */
public class MenuService
{

	public static Logger logger = Logger.getLogger("log.service");

	/**
	 * ͨ��id�õ�һ���˵���Ϣ
	 */
	public OperateMenuVO getMenuById(int id)
	{
		MenuCache menuCache = new MenuCache();
		return menuCache.getById(id + "");
	}
	
	/**
	 * �жϸò˵��Ƿ���ָ��scene_id�Ĳ˵�
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
	 * ˢ�µ�ǰ�ص����Ч�˵�
	 * @param roleInfo
	 * @return
	 */
	public List<OperateMenuVO> menuRefurbish(RoleEntity roleInfo)
	{
		OperateMenuVO menu = null;
		
		SceneVO cur_scene = roleInfo.getBasicInfo().getSceneInfo();
		HashMap<String,OperateMenuVO> fatherMenuList = cur_scene.getFatherMenuList();// ���в˵�
		
		HashMap<String,OperateMenuVO> result_menus = new HashMap<String,OperateMenuVO>();
		
		TaskSubService taskSubService = new TaskSubService();
		
		if (fatherMenuList != null && fatherMenuList.size() != 0)
		{
			
			Iterator<String> it = fatherMenuList.keySet().iterator(); 
			String key = null;
			OperateMenuVO effect_menu = null;//��Ч�˵�
			while (it.hasNext()) 
			{  
				key = it.next().toString();
				menu = fatherMenuList.get(key);  
				//�ж��Ƿ�ɰ�ˢ��ʱ�����ˢ�²˵�
				if( !DateUtil.isEffectTime(menu.getMenuTimeBegin(), menu.getMenuTimeEnd(), menu.getMenuDayBegin(), menu.getMenuDayEnd(),menu.getWeekStr()))
				{
					//����ˢ��ʱ�����
					continue;
				}
				
				effect_menu = menu.clone();
				result_menus.put(key, effect_menu);
				
				// �ж��ò˵��������� ��һ������
				String menu_tasks_id = effect_menu.getMenuTasksId();
				if (menu_tasks_id != null && !menu_tasks_id.equals("") && !menu_tasks_id.equals("0"))
				{
					String[] tasks_ids = menu_tasks_id.split(",");// ȡ������ID
					
					TaskVO task_info = null;//������Ϣ
					CurTaskInfo role_task = null;//���δ��ɵ�����
					
					StringBuffer effect_task_ids = new StringBuffer();//��Ч������id,(��ҿ��Խ��ܵ�����id)
					
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
						role_task = (CurTaskInfo) roleInfo.getTaskInfo().getCurTaskList().getByZu(task_info.getTZu());//����������õ�����δ��ɴ�����
						
						//�жϸ������Ƿ�����ɺ��Ƿ��Ѿ����˸������������
						if (roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(task_info.getTZu()) == false && role_task==null )
						{
							//����������û�����Ҳû�н��ܹ�����������������ж�����Ľ�������
							if (task_info.getTCycle() == 0 && (task_info.getTLevelXiao() <= roleInfo.getBasicInfo().getGrade())
									&& roleInfo.getBasicInfo().getGrade() <= task_info.getTLevelDa()
									&& Integer.parseInt(task_info.getTZuxl()) == 1 && roleInfo.getTitleSet().isHaveByTitleStr(task_info.getTSchool()) )
							{
								effect_task_ids.append(tasks_ids[t] + ",");
							} 
						}
					}
					
					effect_menu.setMenuTasksId(effect_task_ids.toString());
					if( !effect_task_ids.toString().equals("") )//�������������ʾ!��
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
	 * �����������˵���Ϣ
	 * @param roleInfo
	 * @return
	 */
	public HashMap<String,OperateMenuVO> addAllTaskMenu(RoleEntity roleInfo,HashMap<String,OperateMenuVO> menu_list )
	{
		//�ӻ�����ȡ����ɫ���������б�
		List<CurTaskInfo> task_list = roleInfo.getTaskInfo().getCurTaskList().getCurTaskList();
		
		if (task_list != null && task_list.size() != 0)
		{
			String tPoint[] = null;//�м������
			int point_task_menu_id;//�м��˵�id
			int next_task_menu_id;//������һ���˵�id
			int cur_task_id;      //����id
			
			for (CurTaskInfo u_task:task_list )
			{
				// �ж��Ƿ���û��ɵ��м��
				if (u_task.getTType() == 5 && u_task.getTPoint() != null && !u_task.getTPoint().equals("") && !u_task.getTPoint().equals("0"))
				{
					tPoint = u_task.getTPoint().split(",");
					
					for (int c = 0; c < tPoint.length; c++)
					{
						cur_task_id = u_task.getTId();//��ǰ����id
						point_task_menu_id =  Integer.parseInt(tPoint[c]);//�м��˵�id
						addOneTaskMenu(roleInfo, menu_list, cur_task_id,point_task_menu_id);
					}
				}
				else//���û���м����м�����������
				{
					cur_task_id = u_task.getTNext();//��һ������id
					next_task_menu_id = u_task.getTXrwnpcId();//��һ������˵�id
					addOneTaskMenu(roleInfo, menu_list,cur_task_id, next_task_menu_id);
				}
			}
		}
		
		return menu_list;
	}

	/**
	 * ����һ������˵��������м��˵�����������һ���˵�
	 * @param roleInfo
	 * @param menus_result
	 * @param u_task                       ��Ҫ����˵����������id
	 * @param task_menu_id                 ����˵�id
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
			//�Ѿ��иò˵�
			cur_menu = (OperateMenuVO)menus_result.get(key);
			String menu_old_name = cur_menu.getMenuName();
			String menu_new_name = "";
			if( menu_old_name.indexOf("!") != -1 ) //����е�һ������
			{
				menu_new_name = menu_old_name.replace("!", "?");
			}
			else //������ǵ�һ������˵����Ѿ����м��Ĳ˵�
			{
				if( menu_old_name.indexOf("?") == -1 )//����ò˵����������˵�������ʺ�
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
			//����һ���µĲ˵�
			int scene_id = Integer.parseInt(roleInfo.getBasicInfo().getSceneId());
			OperateMenuVO new_task_menu = getMenuByIdAndSceneId(task_menu_id,scene_id);
			
			if( new_task_menu==null )
			{
				logger.debug("�˵����ڸó����£�����˵�id:"+task_menu_id+";��ǰscene_id:"+scene_id);
				return;
			}
			
			if( !DateUtil.isEffectTime(new_task_menu.getMenuTimeBegin(), new_task_menu.getMenuTimeEnd(), new_task_menu.getMenuDayBegin(), new_task_menu.getMenuDayEnd(),new_task_menu.getWeekStr()))
			{
				//����ˢ��ʱ�����
				return;
			}
			
			String menu_tasks_id = new_task_menu.getMenuTasksId();
			if (menu_tasks_id != null && !menu_tasks_id.equals("") && !menu_tasks_id.equals("0"))
			{
				String[] tasks_ids = menu_tasks_id.split(",");// ȡ������ID
				
				TaskVO task_info = null;//������Ϣ
				CurTaskInfo role_task = null;//���δ��ɵ�����
				StringBuffer effect_task_ids = new StringBuffer();//��Ч������id,(��ҿ��Խ��ܵ�����id)
				
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
					role_task = (CurTaskInfo) roleInfo.getTaskInfo().getCurTaskList().getByZu(task_info.getTZu());//����������õ�����δ��ɴ�����
					
					//�жϸ������Ƿ�����ɺ��Ƿ��Ѿ����˸������������
					if (roleInfo.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(task_info.getTZu()) == false && role_task==null )
					{
						//����������û�����Ҳû�н��ܹ�����������������ж�����Ľ�������
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
	 * ������²˵�
	 * @param roleInfo
	 * @param menus_result
	 * @param new_menu
	 * @param u_task
	 *//*
	private void addPointByCondition(RoleEntity roleInfo,HashMap menus_result,CurTaskInfo u_task )
	{
		String map_id = roleInfo.getBasicInfo().getSceneId();
		int npcid = u_task.getTXrwnpcId();
		
		// ���û���м�� ��ô�ж���������   
		OperateMenuVO new_menu = getMenuByIdAndSceneId(npcid,Integer.parseInt(map_id));
		
		if( menus_result ==null || new_menu==null )
		{
			return ;
		}
		int taskid = u_task.getTNext();
		//ͨ������ID��cacheȡ����������
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
	 * ά�޲˵�
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
	 * ���Դ�����npc�˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createDeadNPCMenu(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		RoleEntity roleEntity = (RoleEntity) object;
		int mapId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());

		// ����npc����
		String condition = menu.getMenuOperate1();

		// npcAttackDao.deleteByPpk(userTempBean.getPPk());// ���npc

		// ����npc
		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(roleEntity.getBasicInfo()
				.getPPk(), condition, NpcAttackVO.DEADNPC, mapId);

		return null;
	}

	/**
	 * ���Դ�ܵ�npc�˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createLoseNPCMenu(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		RoleEntity roleEntity = (RoleEntity) object;
		int mapId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());

		// ����npc����
		String condition = menu.getMenuOperate1();

		// npcAttackDao.deleteByPpk(userTempBean.getPPk());// ���npc

		// ����npc
		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(roleEntity.getBasicInfo()
				.getPPk(), condition, NpcAttackVO.LOSENPC, mapId);

		return null;
	}
	
	/**
	 * ���Ա���Ҵ��npc�˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createAllAttackNPCMenu(OperateMenuVO menu, RoleEntity roleEntity)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu��roleEntityΪ��");
			return null;
		}
		
		int sceneId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());
		
		// ����npc����
		String condition = menu.getMenuOperate2();

		NpcVO npc = NpcCache.getById(Integer.parseInt(condition));
		// ����npc
		NpcAttackDao npcAttackDao = new NpcAttackDao();		
		npcAttackDao.createByNpc(npc,roleEntity.getPPk(), 1, NpcAttackVO.DIAOXIANG, sceneId,0);
		

		return null;
	}
	
	/**
	 * ���˲����ֵĲ˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createZhaoAttackNPCMenu(OperateMenuVO menu, RoleEntity roleEntity,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu��roleEntityΪ��");
			return null;
		}
		
	/*	OperateMenuDao menuDao = new OperateMenuDao();
		menu = menuDao.getMenuById(menu.getId());
		
		int sceneId = Integer.parseInt(roleEntity.getBasicInfo().getSceneId());

		// ����npc����
		String condition = menu.getMenuOperate1();

		// �����ֶ�3��ŵ� ���л���������Ǹ���������
		String menuTongString = menu.getMenuOperate3();
		
		
		RoomService  roomService = new RoomService();
		SceneVO sceneVO = roomService.getById(sceneId+"");
		TongSiegeBattleService tongSiegeBattleService  = new TongSiegeBattleService();
		TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(Integer.parseInt(sceneVO.getSceneMapqy()));
		TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService.getSiegeTongInfo(tongSiegeBattleVO.getSiegeId()+"");
		
		// �����ű����ƺ��ܱ�����
		if ( tongSiegeControlVO.getNowPhase() < FinalNumber.FORTH) {
			String hint = "���ڻ����ܹ����л��!";
			return hint;
		}
		
		PartInfoDAO infoDAO = new PartInfoDAO();
		int tongPk = infoDAO.getPartTong(roleEntity.getBasicInfo().getPPk()+"");
		
		if ( menuTongString.equals(tongPk+"") ) {
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append("���л���ǹ��������!<br/>");
//			sBuffer.append("<anchor><go method=\"post\"   href=\""+response.encodeURL(GameConfig.getContextPath()+"/tongEndaction.do")+"\"> ");
//			sBuffer.append("<postfield name=\"cmd\" value=\"n3\" /> ");
//			sBuffer.append("<postfield name=\"menu_id\" value=\""+menu.getId()+"\" /> ");
//			sBuffer.append("</go>");
//			sBuffer.append("��ȡ�л��ף��");
//			sBuffer.append("</anchor>");
			
			return sBuffer.toString();			
		} else {
    		// ����npc
    		RefurbishService refurbishService = new RefurbishService();
    				
    		refurbishService.createNPCByCondition(roleEntity.getBasicInfo()
    				.getPPk(), condition, NpcAttackVO.ZHAOHUN, sceneId);
		}
		
*/
		return null;
	}
	

	/**
	 * ˢ�����npc
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String createFlagNPCMenu(OperateMenuVO menu, RoleEntity roleEntity)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu��roleEntityΪ��");
			return null;
		}
		String mapId = roleEntity.getBasicInfo().getSceneId();

		// ����npc����
		String condition = menu.getMenuOperate1();

		// ����npc
		RefurbishService refurbishService = new RefurbishService();
		refurbishService.createNPCByCondition(roleEntity.getPPk(),condition, NpcAttackVO.MAST, Integer.parseInt(mapId));
		 
		SystemInfoService systemInfoService = new SystemInfoService();
		RoomService roomService = new RoomService();
		String sceneName = roomService.getName(menu.getMenuMap());

		StringBuffer systemInfo = new StringBuffer("λ��");
		systemInfo.append(sceneName).append("����˱�");

		systemInfo.append("������!");

		systemInfoService.insertSystemInfoByBarea(systemInfo.toString(),
				FinalNumber.FIELDBAREA);

		return null;
	}

	/**
	 * �г����˵����Ӳ˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String fatherList(RoleEntity roleInfo,OperateMenuVO father_menu,HttpServletRequest request,HttpServletResponse response)
	{
		if (father_menu == null)
		{
			logger.debug("menuΪ��");
			return null;
		}
		StringBuffer result = new StringBuffer();
		OperateMenuVO menu = null;
		OperateMenuVO son_menu = null;

		OperateMenuDao menuDao = new OperateMenuDao();
		List<OperateMenuVO> menus = menuDao.getSonMenuByMap(father_menu.getId(),roleInfo.getBasicInfo().getPRace()); // �����Ӳ˵�
		List<OperateMenuVO> display_menus = null; // ��Ч�Ӳ˵�

		result.append(father_menu.getMenuName() + "<br/>");

		if (father_menu.getMenuDialog() != null
				&& !father_menu.getMenuDialog().equals(""))
			result.append(father_menu.getMenuDialog() + "<br/>");

		if (menus != null && menus.size() != 0)
		{
			logger.info("�Ӳ˵�����=" + menus.size());
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
			logger.info("��Ч�Ӳ˵�Ϊ=" + display_menus.size());
			for (int i = 0; i < display_menus.size(); i++)
			{
				son_menu = display_menus.get(i);

				// ����������Բ˵���ֻ��ʾ����, ���������¼�����.
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
	 * ��Ϣ�˵�����
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
			result.append("�����������������Ѽ���! ");
			
			//���
			LogService logService = new LogService();
			logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", "-"+need_money, "ס��");
			
			roleEntity.getBasicInfo().addCopper(-Integer.parseInt(need_money));
			// ִ��ͳ��
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(6, StatisticsType.MONEY, Integer
					.parseInt(need_money), StatisticsType.USED,
					StatisticsType.XITONG, roleEntity.getBasicInfo().getPPk());

		}
		else
		{
			result.append("���������㣡ס����Ҫ"
					+ MoneyUtil.changeCopperToStr(Integer.parseInt(need_money))
					+ "!");
		}

		return result.toString();
	}

	/*
	 * ���Ͳ˵������ֶ� menu_operate1���洫��Ŀ�ĵ� menu_operate2����Ҫ ����� ������ʽΪ:1,3-3,2-0,200
	 * ˵��:��ͬ������"-"�ָÿ�����߶���֮ǰ��ʾ����id,����֮���ʾ��������������idΪ0ʱ��ʾ��Ǯ
	 * menu_operate3�����Ƿ�ɾ���õ��� ˵��:����Ϊɾ��ʱ����ʾɾ����Ϊ�ձ�ʾ����ɾ��
	 */

	/**
	 * �����Ͳ˵�����
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String taskCarry(OperateMenuVO menu, int pPk,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null)
		{
			logger.debug("menu��objectΪ��");
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
			result.append("��û�г�������");
			return result.toString();
		}
		else
		{
			MountsVO mv= ms.getMountsInfo(uv.getMountsID());
			if(mv.getLevel()==1)//ֻ��һ�������������������
			{
				
				boolean isUse=tcs.isUseable(pPk, uv.getMountsID(), TimeControlService.ANOTHERPROP, mv.getCarryNum1());
				if(isUse)
				{
				}
				else//ÿ���ʹ�ô���������Ҫ�۷�
				{
					int needCopper=mv.getOverPay1();
					long haveCopper=role_info.getBasicInfo().getCopper();
					if(needCopper>haveCopper)
					{
						result.append("��������ʹ�ô���������Ҫ�۳���ʯ����ʯ���㲻�ܴ���");
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
		// û�д�������
		if (condition == null || condition.equals(""))
		{
			return null;
		}
		else
		// �д�������
		{
			boolean isCarryed = true; // �Ƿ���Դ���
			boolean isConfirm = false; // �Ƿ�Ҫ����ȷ��
			String conditions[] = condition.split("-");
			String prop[] = null;
			for (int i = 0; i < conditions.length; i++)
			{
				prop = conditions[i].split(",");
				if (prop[0].equals("0"))// Ҫ�����Ǯ��
				{
					long copper_num = role_info.getBasicInfo().getCopper();
					if (copper_num < Integer.parseInt(prop[1]))
					{
						isCarryed = false;
						result.append("<br/>��Ҫ"
								+ MoneyUtil.changeCopperToStr(prop[1])
								+ ",��Ŀǰ��:"
								+ MoneyUtil.changeCopperToStr((int) copper_num)
								+ ",���㡣");
					}
					else
					{
						condition_display.append("��Ҫ"
								+ MoneyUtil.changeCopperToStr(prop[1]));
					}
				}
				else
				// Ҫ����ǵ���
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
						result.append("<br/>��Ҫ" + prop[1] + "��" + prop_name
								+ ",��Ŀǰ��:" + prop_num + "��,����������");
					}
					else
					{
						condition_display.append(",��Ҫ" + prop[1] + "��"
								+ prop_name + "");
						isConfirm = true;
					}
				}
			}
			if (isCarryed)// ���Դ���,��Ҫ����ȷ��
			{
				/*
				 * if (menuTasksId == null || (menuTasksId != null &&
				 * menuTasksId.equals(""))) {
				 */
				if (isConfirm)
				{
					result.append("������Ҫ:");
					result.append(condition_display.toString());
					result.append("<br/>");
					result.append("<anchor> ");
					// �����и�/j yg��ame
					//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/menu.do\"> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/menu.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n3\" /> ");
					result.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
					result.append("</go>");
					result.append("ȷ��");
					result.append("</anchor>");
				}
				else
				{
					return null;// ֱ�Ӵ���
				}
				/* } */
			}
			else
			{
				return "��������������" + result.toString();
			}
		}

		return result.toString();
	}

	/**
	 * �������Ĵ��� condition��������
	 * 
	 * @param menu
	 * @param object
	 */
	public void carrayExpend(OperateMenuVO menu, RoleEntity roleEntity)
	{
		if (menu == null || roleEntity == null)
		{
			logger.debug("menu��roleEntityΪ��");
			return;
		}

		// ������Ʒ����
		String condition = menu.getMenuOperate2();
		if (!condition.equals(""))
		{
			String conditions[] = condition.split("-");
			String prop[] = null;
			for (int i = 0; i < conditions.length; i++)
			{
				prop = conditions[i].split(",");
				if (prop[2].equals("1"))// ������Ʒ
				{
					if (prop[0].equals("0"))// Ҫ�����Ǯ��
					{
						
						//���
						LogService logService = new LogService();
						logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", "-"+prop[1], "����");
						
						roleEntity.getBasicInfo().addCopper(
								-Integer.parseInt(prop[1]));// ���Ľ�Ǯ
						// ִ��ͳ��
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(6, StatisticsType.MONEY, Integer
								.parseInt(prop[1]), StatisticsType.USED,
								StatisticsType.CHUANSONG, roleEntity
										.getBasicInfo().getPPk());

					}
					else
					// Ҫ����ǵ���
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
				// ��������
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
	 * ��ͨ�Ի���˵�����
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
	 * ��Ὠ��ҳ����ת
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String tongjump(OperateMenuVO menu,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		// 1�ȼ� 2Ǯ 3��Ʒ
		StringBuffer result = new StringBuffer();
		result.append(menu.getMenuDialog());
		result.append("<br/>");
		result.append("<anchor> ");
		// �����и�/jy��game
		//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/tongaction.do\"> ");
		result.append("<go method=\"post\" sendreferer=\"true\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/tongaction.do")+"\">");
		result.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
		result.append("</go>");
		result.append("��Ҫ�������");
		result.append("</anchor>");
		return result.toString();
	}

	/**
	 * ��Ὠ��ҳ����ת
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String recruit(OperateMenuVO menu, Object object,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		StringBuffer result = new StringBuffer();
		result.append(menu.getMenuDialog());
		result.append("<br/>");
		result.append("<anchor> ");
		//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/recruitaction.do?cmd=n1\"> ");
		
		result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/recruitaction.do?cmd=n1")+"\">");
		
		result.append("</go>");
		result.append("������ļ");
		result.append("</anchor>");
		result.append("<br/>");
		result.append("<anchor> ");
		//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/tongbegaction.do?cmd=n1\"> ");
		
		result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/tongbegaction.do?cmd=n1")+"\">");
		
		result.append("</go>");
		result.append("�������");
		result.append("</anchor>");
		return result.toString();
	}

	/**
	 * ��������ڲ˵�
	 */
	public String instanceDoor(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		StringBuffer result = new StringBuffer();

		RoleEntity roleInfo = (RoleEntity) object;

		InstanceService instanceService = new InstanceService();
		GroupService groupService = new GroupService();

		String instance_id_str = menu.getMenuOperate1(); // ����id
		int instance_id = -1;

		try
		{
			instance_id = Integer.parseInt(instance_id_str);
		}
		catch (Exception e)
		{
			logger.info("����id:" + instance_id_str + ",��������");
		}

		String hint = null;

		InstanceInfoVO instanceInfo = null;
		instanceInfo = instanceService.getInfoById(instance_id);

		if (instanceInfo == null)
		{
			hint = "��Чinstance_id:" + instance_id;
			logger.info(hint);
			return hint;
		}

		int group_member_num = 0;// ������ڶ�������
		group_member_num = groupService.getGroupNumByMember(roleInfo
				.getBasicInfo().getPPk());

		if (group_member_num < instanceInfo.getGroupLimit())
		{
			result.append("������������,��Ҫ" + instanceInfo.getGroupLimit()
					+ "�˲��ܽ��븱��<br/>");
			logger.info(hint);
		}
		else
			if (roleInfo.getBasicInfo().getGrade() < instanceInfo
					.getLevelLimit())
			{
				result.append("�ȼ�����,��Ҫ" + instanceInfo.getLevelLimit()
						+ "���ſ��Խ��븱��<br/>");
				logger.info(hint);
			}
			else
			{

				if (instanceService.isReset(instanceInfo))// �ж��Ƿ�����
				{
					// ��������
					instanceService.reset(roleInfo.getBasicInfo().getPPk(),
							instanceInfo.getMapId());
				}
				roleInfo.getBasicInfo().updateSceneId(instanceInfo.getEnterSenceId() + "");// ������Ҳ�ɽ����С��
				//�жϸ�������
				/*if(menu.getMenuType() == Integer.parseInt(GameConfig.getPropertiesObject("menu_instance_type"))){
					new PopUpMsgService().addSysSpecialMsg(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(menu.getMenuOperate2()),0, PopUpMsgType.MENU_INSTANCE);
				}*/
				return null;
			}

		return result.toString();
	}

	/**
	 * ����ս�����Ͳ˵�
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
//			hintString = "ս����δ��ʼ,��ȴ�ս����ʼ!";
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
			hintString = "ս����δ��ʼ,��ȴ�ս����ʼ!";
		}
		return hintString;
	}
	
	
	
	/**
	 * ��ͨ���Ͳ˵�
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 */
	public String normalCarry(OperateMenuVO menu, Object object,HttpServletRequest request,HttpServletResponse response)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}

		String goods_require = menu.getMenuOperate2(); // ������ƷҪ��
		String grade_and_taskzu = menu.getMenuOperate3(); // ���͵ȼ�������Ҫ��

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
				task_zu_str = conditions[1]; // ��������Ҫ��
			}
		}

		RoleEntity roleEntity = (RoleEntity) object;

		StringBuffer result = new StringBuffer();
		StringBuffer condition_display = new StringBuffer();

		boolean isCarryed = true; // �Ƿ���Դ���
		boolean isConfirm = false; // �Ƿ�Ҫ����ȷ��
		
		
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
					result.append("<br/>���͵ȼ�Ҫ����"
							+ Integer.parseInt(grade_require[0]) + "������");
				}
				else
				{
					result.append("<br/>���͵ȼ�Ҫ����"
							+ Integer.parseInt(grade_require[0]) + "����"
							+ Integer.parseInt(grade_require[1]) + "��֮��");
				}
			}
		}
				

		if (task_zu_str != null && !task_zu_str.equals(""))
		{
			// �ж�����Ƿ���ɴ�������
			TaskSubService taskService = new TaskSubService();
			if (!taskService.isCompletedOneOfMany(roleEntity.getBasicInfo()
					.getPPk(), task_zu_str))
			{
				isCarryed = false;
				// result.append("<br/>û���������111111");
			}
		}

		// ��","���ָ���Ʒ����ͽ�ֹbuff����ID
		if (goods_require != null && !goods_require.equals(""))
		{
			String goods_requires[] = goods_require.split("-");
			String prop[] = null;
			for (int i = 0; i < goods_requires.length; i++)
			{
				prop = goods_requires[i].split(",");
				if (prop[0].equals("0"))// Ҫ�����Ǯ��
				{
					long copper_num = roleEntity.getBasicInfo().getCopper();

					if (copper_num < Integer.parseInt(prop[1]))
					{
						isCarryed = false;
						result.append("<br/>��Ҫ"
								+ MoneyUtil.changeCopperToStr(prop[1])
								+ ",��Ŀǰ��:"
								+ MoneyUtil.changeCopperToStr((int) copper_num)
								+ ",���㡣");
					}
					else
					{
						condition_display.append("��Ҫ"
								+ MoneyUtil.changeCopperToStr(prop[1]));
					}
				}
				else
				// Ҫ����ǵ���
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
						result.append("<br/>��Ҫ" + prop[1] + "��" + prop_name
								+ "����Ŀǰ��:" + prop_num + "��������������");
					}
					else
					{
						condition_display.append(",��Ҫ" + prop[1] + "��"
								+ prop_name + "<br/>");
						isConfirm = true;
					}
				}
			}
		}
		
		
		String buffEffectString = menu.getMenuOperate1();		
		if (buffEffectString != null && !buffEffectString.equals("") ) {
			String[] buffEffectStrings  =  buffEffectString.split(";");
			if ( buffEffectStrings.length >= 2 ) {		// ������ڵ���2,��˵����buff������
				String[] buffs = buffEffectStrings[1].split(",");
				if(buffs[0].equals("0")){
					
				}else{
					for ( int i=0;i<buffs.length;i++) {
						BuffEffectDao buffEffectDao = new BuffEffectDao();
						BuffDao buffDao = new BuffDao();
			    		int buffType = buffDao.getBuffType(buffs[i]);
			    		// �������ֵ����-1,�����Ѿ�������buff,��������buff��ID
			    		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
			    		if( buffEffectVO != null && buffEffectVO.getBuffId() == Integer.parseInt(buffs[i]) ) {
			    				result.append(".��������").append(buffEffectVO.getBuffName()).append("Ч��,��˲��ܴ���!");
			    				isCarryed = false;
			    		}
					}
				}
			}
			if( buffEffectStrings.length >= 3){
				// ��������
				String[] recourse = buffEffectStrings[2].split(",");
				if(recourse[0].equals("0")){
					
				}else{
				Integer cid=Integer.parseInt(recourse[0]);
				Integer ncount=Integer.parseInt(recourse[1]);
				Integer ppk=roleEntity.getBasicInfo().getPPk();
				CreaditService service=new CreditProce();
				Integer res=service.checkHaveCondition(ppk, cid, ncount);
				if(res==-1||res.equals(-1)){
					result.append(".��������").append("�����޴�����!");
	    			isCarryed = false;
				}else if(res==0||res.equals(0)){
					result.append(".��������").append("�������������Զһ�����Ʒ!");
	    			isCarryed = false;
				}
				}
			}
			
			if( buffEffectStrings.length >= 4){
				// �ƺ�����
				if( roleEntity.getTitleSet().isHaveByTitleStr(buffEffectStrings[3])==false)
				{
					result.append(".���ĳƺŲ���!");
					isCarryed = false;
				}
			}
			
		}
		
		if (isCarryed)// ���Դ���
		{
			if (isConfirm)
			{
				result.append("������Ҫ:");
				result.append(condition_display.toString());
				result.append("<anchor> ");
				//result.append("<go method=\"post\" sendreferer=\"true\" href=\"/menu.do\"> ");
				
				result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/menu.do")+"\">");
				
				result.append("<postfield name=\"cmd\" value=\"n3\" /> ");
				result.append("<postfield name=\"menu_id\" value=\"" + menu.getId() + "\" /> ");
				result.append("</go>");
				result.append("ȷ��");
				result.append("</anchor>");
			}
			else
			{
				return null;// ֱ�Ӵ���
			}

		}
		else
		{
			return "��������������" + result.toString();
		}

		return result.toString();
	}

	/**
	 * ���ɵ�ͼ����
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 */
	public String TongMapCarry(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}

		RoleEntity roleEntity = (RoleEntity) object;
		StringBuffer result = new StringBuffer();
		// �ж�����Ƿ��а���
		if (roleEntity.getBasicInfo().getFaction() != null)
		{
			return null;
		}
		else
		{
			// ������������
			return "��û�м������" + result.toString();
		}
	}

	/**
	 * �ر�pk���ز˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String closePK(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		StringBuffer result = new StringBuffer();
		RoleEntity roleEntity = (RoleEntity) object;
		
		Date pk_changetime = roleEntity.getBasicInfo().getPkChangeTime();
		if (roleEntity.getBasicInfo().getGrade() < 30)
		{
			result.append("����û�е�30��");
		}
		else if( roleEntity.getBasicInfo().getPkSwitch()==1 )
		{
			result.append("PK�������ǹر�״̬");
		}
		else if (!DateUtil.isOverdue(pk_changetime, 24 * 60 * 60))
		{
			result.append("���ܸı�PK״̬�������ϴ�״̬����ʱ��û�г���24Сʱ��");
		}
		else
		{
			result.append("PK�����ѹر�");
			roleEntity.getBasicInfo().updatePkSwitch(1);
		}
		
		return result.toString();
	}

	/**
	 * ��pk���ز˵�
	 * 
	 * @param menu
	 * @param object
	 * @return
	 */
	public String openPK(OperateMenuVO menu, Object object)
	{
		if (menu == null || object == null)
		{
			logger.debug("menu��objectΪ��");
			return null;
		}
		StringBuffer result = new StringBuffer();
		RoleEntity roleEntity = (RoleEntity) object;
		
		Date pk_changetime = roleEntity.getBasicInfo().getPkChangeTime();
		
		if (roleEntity.getBasicInfo().getGrade() < 30)
		{
			result.append("����û�е�30��");
		}
		else if (roleEntity.getBasicInfo().getPkSwitch()==2)
		{
			result.append("PK�������Ǵ�״̬");
		}
		else if (!DateUtil.isOverdue(pk_changetime, 24 * 60 * 60))
		{
			result.append("���ܸı�PK״̬�������ϴ�״̬����ʱ��û�г���24Сʱ��");
		}
		else
		{
			result.append("PK�����Ѵ�");
			roleEntity.getBasicInfo().updatePkSwitch(2);
		}

		return result.toString();
	}

	/**
	 * ���npc_id��������id
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
	 * ���menuvo���� sceneId��menuOperate1
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
	 * ���menuvo���� sceneId��menuOperate1
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
	 * ��������Ը��˵�
	 * 
	 * @param menu
	 * @param userTempBean
	 * @return
	 * 
	 * public String displayFatherList(OperateMenuVO father_menu, UserTempBean
	 * userTempBean) { if (father_menu == null) { logger.debug("menuΪ��"); return
	 * null; } StringBuffer result = new StringBuffer(); OperateMenuVO menu =
	 * null; OperateMenuVO son_menu = null;
	 * 
	 * OperateMenuDao menuDao = new OperateMenuDao(); List menus =
	 * menuDao.getSonMenuByMap(father_menu.getId()); //�����Ӳ˵� List display_menus =
	 * null; //��Ч�Ӳ˵�
	 * 
	 * result.append(father_menu.getMenuName() + "<br/>");
	 * result.append(father_menu.getMenuDialog() + "<br/>");
	 * 
	 * if (menus != null && menus.size() != 0) { logger.info("�Ӳ˵�����=" +
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
	 * display_menus = menus; logger.info("��Ч�Ӳ˵�Ϊ="+display_menus.size()); for
	 * (int i = 0; i < display_menus.size(); i++) { son_menu = (OperateMenuVO)
	 * display_menus.get(i);
	 * 
	 * result.append(son_menu.getMenuDialog()); result.append("<br/>");
	 * result.append("<anchor> "); result.append("<go method=\"post\"
	 * sendreferer=\"true\" href=\"/menu.do\"> "); //�����и�/j��ygame
	 * result.append("<postfield name=\"cmd\" value=\"n1\" /> ");
	 * result.append("<postfield name=\"menu_id\" value=\"" + son_menu.getId() +
	 * "\" /> "); result.append("</go>");
	 * result.append(son_menu.getMenuName()); result.append("</anchor><br/>"); } }
	 * return result.toString(); }
	 */
}
