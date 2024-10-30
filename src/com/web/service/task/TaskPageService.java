package com.web.service.task;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ben.dao.info.map.PartMapDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.task.TaskVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.room.RoomService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.web.service.TaskService;
import com.web.service.TaskXunWuService;

/**
 * 功能:任务提示进度
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class TaskPageService
{

	/**
	 * 杀死怪后任务提示
	 */
	public String getGeiDJService(int npcID, String npcName, int pPk)
	{
		// 获取NPCID
		String hint = null;
		int npcid = npcID;
		String tType = "2";
		UTaskDAO dao = new UTaskDAO();
		List<UTaskVO> list = dao.getUTaskNpcId(pPk + "", npcid + "", tType);
		if (list != null)
		{
			String tasShaGuaiXiaYiBu = "";
			for (int i = 0; i < list.size(); i++)
			{
				UTaskVO vo = list.get(i);
				if ((vo.getTKillingOk() + 1) <= vo.getTKillingNo())
				{
				hint = "任务:" +  vo.getTaskInfo().getTName() + "（"+ npcName + "）" + (vo.getTKillingOk() + 1)+ "/" + vo.getTKillingNo() + "";
				if((vo.getTKillingOk() + 1) ==  vo.getTKillingNo()){
					TaskVO task = TaskCache.getById(vo.getTId()+"");//获取任务的相关内容
					String[] taskKeyValue = this.regroupTaskId(task.getTKeyValue()).split(",");
					tasShaGuaiXiaYiBu = this.tasShaGuaiXiaYiBu(taskKeyValue[(taskKeyValue.length - 1)], vo.getTId() + "");
				}
				return hint+tasShaGuaiXiaYiBu;
				}
			}
		}
		return hint;
	}

	/**
	 * 寻物类任务提示
	 */

	public String Find(int pPk, int goodid, int goodtype,RoleEntity roleInfo)
	{
		// 获取NPCID
		String hint = null;
		String tType = "4";
		UTaskDAO dao = new UTaskDAO();
		List<UTaskVO> list = dao.getGoods(pPk + "", tType);
		GoodsService goodsService = new GoodsService();
		TaskXunWuService taskXunWuService = new TaskXunWuService();
		if (list != null && list.size() != 0)
		{
			String tasShaGuaiXiaYiBu = "";
			for (int i = 0; i < list.size(); i++)
			{
				UTaskVO vo = list.get(i);
				if (vo.getTGoods() != null && !vo.getTGoods().equals(""))
				{// 取出任务中的物品
					String[] goods = vo.getTGoods().split(",");
					String[] goodsnumber = vo.getTGoodsNo().split(",");
					for (int gd = 0; gd < goods.length; gd++)
					{
						if (goodid == Integer.parseInt(goods[gd]))
						{
						int dd = goodsService.getPropNum(pPk, Integer.parseInt(goods[gd]));// 取出包裹里的物品数量
						if (dd <= Integer.parseInt(goodsnumber[gd])){
						hint = "任务:"+ vo.getTaskInfo().getTName()+ "（"+ goodsService.getGoodsName(Integer.parseInt(goods[gd]), 4)+ "）" + dd + "/" + goodsnumber[gd]+ "";
						if(taskXunWuService.getXunWuDiscernService(vo.getTId() + "", roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getUPk())){
							TaskVO task = TaskCache.getById(vo.getTId()+"");//获取任务的相关内容
							String[] taskKeyValue = this.regroupTaskId(task.getTKeyValue()).split(",");
							tasShaGuaiXiaYiBu = this.tasShaGuaiXiaYiBu(taskKeyValue[(taskKeyValue.length - 1)], vo.getTId() + "");
						}
						return hint+tasShaGuaiXiaYiBu;
						}
						}
					}
				}
			}
		}
		return hint;
	}

	/**
	 * 寻物类任务提示
	 */

	public String Complex(int pPk, int goodid, int tPk)
	{
		// 获取NPCID
		String hint = null;
		if(goodid == 0){
			return hint;
		}
		UTaskDAO dao = new UTaskDAO();
		UTaskVO vo = dao.getPoint(pPk + "", tPk);
		GoodsService goodsService = new GoodsService();
		int dd = goodsService.getPropNum(pPk, goodid);// 取出包裹里的物品数量
		if (vo.getTGoodsNo() != null && !vo.getTGoodsNo().equals("") && !vo.getTGoodsNo().equals("0")){
				hint = "任务:" + vo.getTaskInfo().getTName() + "（" + goodsService.getGoodsName(goodid, 4) + "）" + (dd + 1) + "/" + vo.getTGoodsNo() + "";
		}
		return hint;
	}

	/**
	 * 返回中简点任务下一步
	 * @param task_id
	 * @param menu_id
	 * @return
	 */ 
	public String taskZhongJianDianXiaYiBu(RoleEntity roleInfo,String menu_id,String tPk,HttpServletRequest request,HttpServletResponse response,int tPoint){ 
		StringBuffer hint = new StringBuffer();
		MenuService menuService = new MenuService();
		
		CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(tPk);//获取玩家身上任务内容
		TaskVO taskVOCache = TaskCache.getById(curTaskInfo.getTId()+"");//获取任务的相关内容
		if(curTaskInfo.getTPoint() != null && !curTaskInfo.getTPoint().equals("0") && !curTaskInfo.getTPoint().equals("") && !curTaskInfo.getTPoint().equals("null")){
			String getTPoint[] = this.regroupTaskId(curTaskInfo.getTPoint()).split(","); 
			String ddc11 = "";
			for(int i = 0 ; i < getTPoint.length ; i++){
				if (Integer.parseInt(getTPoint[i])!=tPoint) {
					 String tt = getTPoint[i] + ",";
					 ddc11 += tt; 
				 }
			}
			String s[] = this.regroupTaskId(ddc11).split(",");
			if(s[0] != null && !s[0].equals("") && !s[0].equals("null") && !s[0].equals("0")){
				OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(s[0]));//获取菜单的相关内容
				hint.append("<anchor> "); 
				hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10")+"\">");
				hint.append("<postfield name=\"taskKeyValue\" value=\"" + menu.getMenuMap()+"" + "\" /> ");
				hint.append("<postfield name=\"tId\" value=\"" + curTaskInfo.getTId() + "\" /> ");
				hint.append("</go>");
				if(!taskVOCache.getTKey().equals(""))
				{
					hint.append("前往");
					hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
				}
				hint.append("</anchor>");
				return hint.toString();
			}else{ 
				String[] taskKeyValue = this.regroupTaskId(taskVOCache.getTKeyValue()).split(",");
				hint.append("<anchor> "); 
				hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10")+"\">");
				hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue[(taskKeyValue.length - 1)] + "\" /> ");
				hint.append("<postfield name=\"tId\" value=\"" + curTaskInfo.getTId() + "\" /> ");
				hint.append("</go>");
				if(!taskVOCache.getTKey().equals(""))
				{
					hint.append("前往");
					hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
				}
				hint.append("</anchor>");
				return hint.toString();
			} 
		}else{
			String[] taskKeyValue = this.regroupTaskId(taskVOCache.getTKeyValue()).split(",");
			hint.append("<anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10")+"\">");
			hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue[(taskKeyValue.length - 1)] + "\" /> ");
			hint.append("<postfield name=\"tId\" value=\"" + curTaskInfo.getTId() + "\" /> ");
			hint.append("</go>");
			if(!taskVOCache.getTKey().equals(""))
			{
				hint.append("前往");
				hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
			}
			hint.append("</anchor>");
			return hint.toString();
		}
	}
	
	/**
	 * 返回中简点任务下一步
	 * @param task_id
	 * @param menu_id
	 * @return
	 */
	public String tasShaGuaiXiaYiBu(String taskKeyValue,String tId){
		    StringBuffer hint = new StringBuffer();
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10\">");
			hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue + "\" /> ");
			hint.append("<postfield name=\"tId\" value=\"" + tId + "\" /> ");
			hint.append("</go>");
			TaskVO taskVOCache = TaskCache.getById(tId);
			if(!taskVOCache.getTKey().equals(""))
			{
				hint.append("前往");
				hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
			}
			hint.append("</anchor>");
			return hint.toString();
	}
	
	/**
	 * 返回中简点任务下一步
	 * @param task_id
	 * @param menu_id
	 * @return
	 */
	public String tasShaGuaiXiaYiBuShaGuai(String taskKeyValue,String tId,String player_point_legth,String point_legth){
		    StringBuffer hint = new StringBuffer();
		    if(!player_point_legth.equals("0")){
		    	hint.append("完成:"+player_point_legth+"/"+point_legth+"<br/>");
		    }
			hint.append("<anchor> "); 
			hint.append("<go method=\"post\" href=\""+GameConfig.getContextPath()+"/taskinfoaction.do?cmd=n10\">");
			hint.append("<postfield name=\"taskKeyValue\" value=\"" + taskKeyValue + "\" /> ");
			hint.append("<postfield name=\"tId\" value=\"" + tId + "\" /> ");
			hint.append("</go>");
			TaskVO taskVOCache = TaskCache.getById(tId);
			if(!taskVOCache.getTKey().equals(""))
			{
				hint.append("前往");
				hint.append(taskVOCache.getTKey().split("-").length!=2?taskVOCache.getTKey():taskVOCache.getTKey().split("-")[1]);
			}
			hint.append("</anchor>");
			return hint.toString();
	}
	
	// 任务跳转
	public String key(String key, String keyvalue, String tishi,HttpServletRequest request,
			HttpServletResponse response)
	{
		String[] ss = key.split(",");
		String[] ee = keyvalue.split(",");
		String qq = null;
		for (int i = 0; i < ss.length; i++)
		{
			if (i == 0)
			{
				qq = tishi.replace(ss[i],
								"<anchor><go method=\"post\"  href=\""
										+ response.encodeURL(GameConfig.getContextPath()+"/menu.do")
										+ "\"><postfield name=\"cmd\" value=\"n1\" /><postfield name=\"menu_id\" value=\""
										+ ee[i] + "\" /></go>" + ss[i]
										+ "</anchor> ");
			}
			if (i != 0)
			{
				qq = qq.replace(ss[i],
								"<anchor><go method=\"post\" href=\""
										+ response.encodeURL(GameConfig.getContextPath()+"/menu.do")
										+ "\"><postfield name=\"cmd\" value=\"n1\" /><postfield name=\"menu_id\" value=\""
										+ ee[i] + "\" /></go>" + ss[i]
										+ "</anchor> ");
			}
		}
		return qq;
	}

	/*
	 * 执行杀怪任务
	 */
	public String deleteTeskPorint(RoleEntity roleEntity)
	{
		String deadnpcxiayibu = null;
		if (roleEntity.getTaskInfo().getTaskId() != -1 && roleEntity.getTaskInfo().getTaskPoint() != null && !roleEntity.getTaskInfo().getTaskPoint().equals("") && roleEntity.getTaskInfo().getTaskMenu() != -1)
		{
			int tPk = roleEntity.getTaskInfo().getTaskId();// 任务ID
			String taskPorint = roleEntity.getTaskInfo().getTaskPoint();// 任务中间点DI
			String menuID = roleEntity.getTaskInfo().getTaskMenu() + "";// 菜单ID
			MenuService ms = new MenuService();
			OperateMenuVO mvo = ms.getMenuById(Integer.parseInt(menuID));
			String[] npc = mvo.getMenuOperate1().split(",");
			if(npc[0].equals(roleEntity.getBasicInfo().getAttack_npc()+"")){
			if (taskPorint.indexOf(menuID) != -1)
			{
				String tPoint[] = taskPorint.split(",");
				String ddc11 = "";
				for (int i = 0; i < tPoint.length; i++)
				{
					String ddc = "";
					if (Integer.parseInt(tPoint[i]) != Integer.parseInt(menuID))
					{
						ddc = tPoint[i] + ",";
						ddc11 += ddc;
					}
				}
				roleEntity.getTaskInfo().setTaskPoint(ddc11);
				CurTaskInfo curTaskInfo = (CurTaskInfo) roleEntity.getTaskInfo().getCurTaskList().getById(tPk + "");
				curTaskInfo.updatePoint(ddc11); 
				//在这里判断一下下一步任务
				String s[] = ddc11.split(",");
				MenuService menuService = new MenuService();
				
				TaskVO taskVOCache = TaskCache.getById(curTaskInfo.getTId()+"");//获取任务的相关内容
				
				int point_num = getPointNum(taskVOCache.getTPoint()); 
				int player_point_num = 0; 
				
				if(s[0] != null && !s[0].equals("") && !s[0].equals("null") && !s[0].equals("0")){
					player_point_num = point_num - getPointNum(ddc11);
					OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(s[0]));//获取菜单的相关内容
					deadnpcxiayibu = this.tasShaGuaiXiaYiBuShaGuai(menu.getMenuMap()+"", curTaskInfo.getTId()+"",player_point_num+"",point_num+"");
				}else{
					if(taskVOCache.getTKeyValue() != null && !taskVOCache.getTKeyValue().equals("") && !taskVOCache.getTKeyValue().equals("null")){
						player_point_num = point_num;
						String[] taskKeyValue = this.regroupTaskId(taskVOCache.getTKeyValue()).split(",");
						deadnpcxiayibu = this.tasShaGuaiXiaYiBuShaGuai(taskKeyValue[(taskKeyValue.length - 1)], curTaskInfo.getTId()+"",player_point_num+"",point_num+"");
					}
				}
				CurTaskInfo vo = roleEntity.getTaskInfo().getCurTaskList().getById(tPk + "");
				TaskService taskService = new TaskService();
				if (vo.getTMidstGs() != null && !vo.getTMidstGs().equals("0") && !vo.getTMidstGs().equals(""))
				{
					String number = "1";
					taskService.getGeiDJService(roleEntity.getBasicInfo().getPPk(), vo.getTMidstGs(), GoodsType.PROP,number);
				}
				if (vo.getTMidstZb() != null && !vo.getTMidstZb().equals("0") && !vo.getTMidstZb().equals(""))
				{
					taskService.getGeiZBService(roleEntity.getBasicInfo().getPPk(), vo.getTMidstZb(), 1 + "");
				}
			}
			}
			roleEntity.getTaskInfo().setTaskId(-1);
			roleEntity.getTaskInfo().setTaskMenu(-1);
			roleEntity.getTaskInfo().setTaskPoint("");
		}
		return deadnpcxiayibu;
	}

	/**
	 * 任务传送
	 * @param carrySceneId    要传送到的场景id
	 * @param taskid		  任务id
	 * @return
	 */
	public String taskRemit(RoleEntity roleInfo,String carrySceneId,String taskid)
	{
		String old_scene_id = roleInfo.getBasicInfo().getSceneId();
		if( old_scene_id.equals(carrySceneId))
		{
			//如果任务传送的是本地则不做处理
			return null;
		}
		
		String hint = null;
		
		RoomService roomService = new RoomService();
		int map_type = roomService.getMapType(Integer.valueOf(old_scene_id));
		
		//如果任务在监狱, 
		if(map_type == MapType.PRISON) {
			hint = "对不起,人在监狱,好好改造!"; 
			return hint;
		}
		//判断是否可以传出
		hint  = roomService.isCarryedOut(Integer.parseInt(old_scene_id));
		if(hint != null){
			return hint;
		}
		//判断是否可以传入
		hint  = roomService.isCarryedIn(Integer.parseInt(carrySceneId));
		if(hint != null){
			return hint;
		}
		/*****当前乘骑坐骑****/
		MountsService ms=new MountsService();
		hint=ms.useMountsByTask(roleInfo, carrySceneId);
		return hint;
		
	}
	/**
	 * 重组任务ID列表
	 * @param task_id
	 * @return
	 */
	public String regroupTaskId(String task_id)
	{
		String regroupTaskId = "";
		if (task_id != null && !task_id.equals(""))
		{
			String[] taskid = task_id.split(",");
			for(int i = 0 ; i < taskid.length ; i++){ 
				if (taskid[i] == null || taskid[i].equals(""))
				{
					continue;
				}
				regroupTaskId += taskid[i]+",";
			}
		}
		return regroupTaskId;
	}
	
	private int getPointNum(String point){
		String[] task_point = point.split(",");
		int legth = (task_point.length)-1;
		if(task_point[legth] == null ||task_point[legth].equals("")){
			return legth;
		}else{
			return legth+1;
		}
	}
	
	public String getTaskComplete(RoleEntity roleEntity,int tPk){
		CurTaskInfo curTaskInfo = (CurTaskInfo) roleEntity.getTaskInfo().getCurTaskList().getById(tPk + "");
		String ddc11 = curTaskInfo.getTPoint();
		String s[] = ddc11.split(",");
		
		TaskVO taskVOCache = TaskCache.getById(curTaskInfo.getTId()+"");//获取任务的相关内容
		
		int point_num = getPointNum(taskVOCache.getTPoint()); 
		int player_point_num = 0; 
		
		if(s[0] != null && !s[0].equals("") && !s[0].equals("null") && !s[0].equals("0")){
			player_point_num = point_num - getPointNum(ddc11);
			return "完成:"+player_point_num+"/"+point_num+"<br/>";
		}else{
			player_point_num = point_num;
			return "完成:"+player_point_num+"/"+point_num+"<br/>";
		}
	}
}
