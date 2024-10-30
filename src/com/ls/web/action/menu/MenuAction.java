package com.ls.web.action.menu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.task.TaskDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.lost.CompassService;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.equip.EquipProduct;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MenuType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.RoleService;
import com.web.jieyi.util.Constant;
import com.web.service.TaskFuHeService;
import com.web.service.task.TaskPageService;

/**
 * @author ls
 * 菜单操作管理
 */
public class MenuAction extends ActionBase {
	Logger logger = Logger.getLogger("log.action");
	//显示单个菜单详情
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String menu_id = request.getParameter("menu_id");
		String task_id = request.getParameter("task_id");
		String menu_type = request.getParameter("menu_type");
		if(menu_id==null){
			menu_id = (String)request.getAttribute("menu_id");
		}if(task_id==null){
			task_id = (String)request.getAttribute("task_id");
		}if(menu_type==null){
			menu_type = (String)request.getAttribute("menu_type");
		}	
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(menu_id));
		//玩家触发菜单npc
		UTaskDAO uTaskDAO = new UTaskDAO(); 
		//查询任务类型是复合类型
		int taskType = 5;
		List<UTaskVO> list = uTaskDAO.getMenuId(p_pk+"",taskType);
		String resultWml = null; 
		TaskDAO taskDAO = new TaskDAO();
		int tPk = 0 ;
		String t_point = null;
		String duihua = null;
		String sss = null;
		if( menu.getMenuTaskFlag() == 1 )	//任务菜单 
		{	
			TaskFuHeService taskFuHeService = new TaskFuHeService();
			List<UTaskVO> tMidstGs = new ArrayList<UTaskVO>(); 
			UTaskVO uTaskVO = null;
			String task_title = null;
			String taskXiaYiBu = null;
			TaskPageService taskPageService = new TaskPageService();
			if(list!=null && list.size()!=0){
				for(int c = 0 ; c < list.size() ; c++){
					UTaskVO vo = list.get(c); 
					t_point = vo.getTPoint(); 
					tPk = vo.getTPk();
					if(t_point!=null && !t_point.equals("") && !t_point.equals("0")){ 
						int number = 1;
						
						String tPoint[] = t_point.split(",");
						String ddc11 = "";
						for(int i = 0 ; i < tPoint.length ; i++){
							if(Integer.parseInt(tPoint[i])==menu.getId()){
								GoodsService  goodsService = new GoodsService();
								if(goodsService.isEnoughWrapSpace(p_pk, number) == false){
									duihua = "包裹格数不够";
									sss = "包裹格数不够";
								}else{
								String	guozhongjiadanjiaowupin = taskFuHeService.guozhongjiadanjiaowupin(p_pk, vo);
								if(guozhongjiadanjiaowupin != null){
									duihua = guozhongjiadanjiaowupin; 
									sss = guozhongjiadanjiaowupin;
								}else{
									uTaskVO = new UTaskVO(); 
									uTaskVO.setTMidstGs(vo.getTMidstGs());
									uTaskVO.setTMidstZb(vo.getTMidstZb()); 
									duihua = taskDAO.t_zjms(vo.getTId()+"");
									task_title = taskPageService.Complex(p_pk, Integer.parseInt(vo.getTMidstGs()), vo.getTId());
									//这里要判断已经抵消的那个值 9月27日 因建国大业任务需要 去掉此下一步功能
									taskXiaYiBu = taskPageService.taskZhongJianDianXiaYiBu(roleInfo, menu_id, vo.getTPk()+"",request,response,Integer.parseInt(tPoint[i]));
									tMidstGs.add(uTaskVO);
									if(menu.getMenuType() == 11){ 
										roleInfo.getTaskInfo().setTaskId(tPk);
										roleInfo.getTaskInfo().setTaskPoint(t_point);
										roleInfo.getTaskInfo().setTaskMenu(menu.getId()); 
										duihua = null;
								    }
								}
							    } 
							}
						 if(sss == null){
							 if (Integer.parseInt(tPoint[i])!=menu.getId()) {
								 String ddc = tPoint[i] + ",";
								 ddc11 += ddc; 
							 }
						 }
						}
						if(sss == null){
						if(menu.getMenuType() != 11){
							CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(vo.getTPk()+"");
							curTaskInfo.updatePoint(ddc11);
					      } 
						} 
					}
				}
			}
			request.setAttribute("task_title", task_title);
			request.setAttribute("taskXiaYiBu", taskXiaYiBu);
			request.setAttribute("tMidstGs", tMidstGs);
			request.setAttribute("duihua", duihua); 
		}
		
		
		if( menu.getMenuType() == MenuType.STONE_UPGRADE )	//宝石合成菜单
		{   
			return super.dispath(request, response, "/prop.do?cmd=upgradeIndex");
		}	
		else if( menu.getMenuType() == MenuType.EQUIP_INLAY_STONE )	//镶嵌宝石菜单
		{   
			return super.dispath(request, response, "/equip.do?cmd=inlayEquipList");
		}	
		else if( menu.getMenuType() == MenuType.DIALOG )	//对话菜单
		{   
			resultWml = menuService.dialog(menu);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id); 
			request.setAttribute("menu_type", menu_type); 
			request.setAttribute("resultWml", resultWml);
			
			return mapping.findForward("display");
		}	
		else if( menu.getMenuType() == MenuType.TASKCARRY )	//任务传送菜单
		{
			resultWml = menuService.taskCarry(menu,p_pk,request,response);
			if( resultWml==null )//直接传送
			{
				return n3(mapping, form, request, response);
			}
		}
		else if( menu.getMenuType() == MenuType.INSTANCE_DOOR )	//副本传送菜单
		{
			resultWml = menuService.instanceDoor(menu, roleInfo);
			if( resultWml==null )//直接传送
			{
				return super.returnScene(request, response);
			}
		}
		else if( menu.getMenuType() == MenuType.NORMALCARRY )	//普通传送菜单
		{
			resultWml = menuService.normalCarry(menu, roleInfo,request,response);
			if( resultWml==null )//直接传送
			{
				return n3(mapping, form, request,response);
			}
		}
		else if( menu.getMenuType() == MenuType.CHUANSONG )	// 攻城战场普通传送菜单
		{
			resultWml = menuService.tongSiegeCarry(menu, roleInfo,response);
			if( resultWml==null )//直接传送
			{
				return n3(mapping, form, request, response);
			}
		}
		else if( menu.getMenuType() == MenuType.REST )//休息菜单
		{
			resultWml = menuService.rest(menu, roleInfo);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id);
			request.setAttribute("menu_type", menu_type); 
			request.setAttribute("resultWml", resultWml); 
			return mapping.findForward("display");
		}
		else if( menu.getMenuType() == MenuType.FATHER )//父菜单
		{
			resultWml = menuService.fatherList(roleInfo,menu,request, response);
		}
		else if( menu.getMenuType() == MenuType.DEADNPC )//可以被打死的NPC菜单
		{
			if (duihua != null) {
				request.setAttribute("task_id", task_id);
				request.setAttribute("menu_id", menu_id);
				request.setAttribute("menu_type", menu_type); 
				request.setAttribute("resultWml", resultWml); 
				return mapping.findForward("display");
			}else{
				menuService.createDeadNPCMenu(menu, roleInfo);
				return super.dispath(request, response, "/attackNPC.do?cmd=n4&pPk="+roleInfo.getBasicInfo().getPPk());
			}
		}
		else if( menu.getMenuType() == MenuType.FAILNPC )//可以打败的NPC菜单
		{
			menuService.createLoseNPCMenu(menu, roleInfo);
			return super.dispath(request, response, "/attackNPC.do?cmd=n4&pPk="+p_pk);
		}
		else if( menu.getMenuType() == MenuType.SAlE )//卖菜单
		{
			return super.dispath(request, response, "/menu/sale.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.BUY )//买菜单
		{
			return super.dispath(request, response, "/buy.do?cmd=n1&menu_id="+menu_id);
		}
		else if( menu.getMenuType() == MenuType.EXCHANGE )//兑换菜单
		{
			return super.dispath(request, response,"/menu/exchange.do?chair="+request.getParameter("chair"));
		}
		else if( menu.getMenuType() == MenuType.PETSALE )//卖宠物菜单
		{
			return super.dispath(request, response,"/menu/petsale.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AVOIDPKPROP )//免PK道具
		{
			return super.dispath(request, response,"/avoidpkprop.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AVOIDPKPROP )//免PK道具
		{
			return super.dispath(request, response,"/avoidpkprop.do?cmd=n1"+"&chair="+request.getParameter("chair"));
		} 
		
		else if( menu.getMenuType() == MenuType.TONG )//帮会申请NPC菜单
		{
			resultWml = menuService.tongjump(menu,request,response); 
		} 
		
		else if( menu.getMenuType() == MenuType.GOODSCCSTORAGE )//物品仓库储存菜单
		{
			request.setAttribute("menu_id", menu_id);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_type", menu_type); 
			return super.dispath(request, response,"/menu/storage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.GOODSQCSTORAGE )//物品仓库取出菜单
		{
			request.setAttribute("menu_id", menu_id);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_type", menu_type); 
			return super.dispath(request, response,"/menu/getStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.PETCCSTORAGE )//宠物仓库储存菜单
		{
			return super.dispath(request, response,"/menu/addPetStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.PETQCSTORAGE )//宠物仓库取出菜单
		{
			return super.dispath(request, response,"/menu/getPetStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.MONEYCCSTORAGE )//金钱仓库储存菜单
		{
			return super.dispath(request, response,"/menu/addMoneyStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.MONEYQCSTORAGE )//金钱仓库取出菜单
		{
			return super.dispath(request, response,"/menu/getMoneyStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AUCTIONSELL )//拍卖场拍卖菜单
		{
			return super.dispath(request, response,"/menu/auction.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AUCTIONBUY )//拍卖场买物品菜单
		{
			return super.dispath(request, response,"/menu/auctionBuy.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.CLOSEPK )//关PK开关菜单
		{
			resultWml = menuService.closePK(menu, roleInfo);
		}
		else if( menu.getMenuType() == MenuType.OPENPK )//开PK开关菜单
		{
			resultWml = menuService.openPK(menu, roleInfo);
		}
		else if( menu.getMenuType() == MenuType.AUCTIONHELP )//拍卖助手菜单
		{
			try {
				request.getRequestDispatcher("/menu/auctionGet.do?cmd=n4&moreOrNot=putong").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.AUCTIONHOUSE )//拍卖仓库菜单
		{
			try {
				request.getRequestDispatcher("/menu/auctionGet.do?cmd=n2").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}	
		else if( menu.getMenuType() == MenuType.PETSELL )//宠物拍卖场卖宠物
		{
			try {	
				request.getRequestDispatcher("/menu/auctionPetSell.do?cmd=n1").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.PETBUY )//宠物拍卖场买宠物
		{
			try {
				request.getRequestDispatcher("/menu/auctionPetBuy.do?cmd=n1").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.AUCTIONPETHELP )//宠物拍卖场助手菜单
		{
			try {
				request.getRequestDispatcher("/menu/auctionPetHelp.do?cmd=n1&moreOrNot=not").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.AUCTIONPETHOUSE )//宠物拍卖场仓库菜单
		{
			try {
				request.getRequestDispatcher("/menu/auctionPetHouse.do?cmd=n2").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.EQUIP_MAINTAIN )//修理装备菜单
		{	
			return super.dispath(request, response, "/equip.do?cmd=maintainIndex");
		}
		else if( menu.getMenuType() == MenuType.RECRUIT )//帮会招募
		{	
			resultWml = menuService.recruit(menu, roleInfo,request,response); 
		} 
		else if( menu.getMenuType() == MenuType.RELATION )//帮会关系
		{
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/relationaction.do?cmd=n1").forward(request, response); 
				
			}catch (Exception e) {

			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.QUESTION )//答题系统
		{
			return this.dispath(request, response, "/question.do?cmd=n1"+"&menu_id="+menu_id);
		}
		//else if( menu.getMenuType() == MenuType.DISPLAYOPERATE )//描述性菜单
		//{
		//	resultWml = menuService.displayFatherList(menu, userTempBean);
		//}
		else if( menu.getMenuType() == MenuType.BUFF )//buff菜单
		{
			return super.dispath(request, response, "/buffMenu.do?cmd=n1"+"&menu_id="+menu_id);
		}else if(menu.getMenuType()==MenuType.BOOK){//book菜单
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/bookMenu.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.EQUIP_CHANGE_WX){//装备五行装换
			try {
				roleInfo.createEquipProduct(EquipProduct.change_wx);
				request.getRequestDispatcher("/equip.do?cmd=productIndex").forward(request, response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(menu.getMenuType()==MenuType.EQUIP_UPGRADE){//装备升级
			try {
				roleInfo.createEquipProduct(EquipProduct.upgrade);
				request.getRequestDispatcher("/equip.do?cmd=productIndex").forward(request, response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(menu.getMenuType()==MenuType.DECOMPOSE){//装备分解
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/updecompose.do?cmd=n10"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(menu.getMenuType()==MenuType.EQUIPTRANSLATE){//装备转化
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/wxc.do?cmd=open"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if( menu.getMenuType() == MenuType.TONGMAP )	//帮派地图传送
		{
			resultWml = menuService.TongMapCarry(menu, roleInfo);
			if( resultWml==null )//直接传送
			{
				return n3(mapping, form, request, response);
			}
		}else if(menu.getMenuType()==MenuType.LOTTERY){//竞猜
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/lottery.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.LABORAGE){//领工资
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/laborage.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGBUILD){//帮会建筑
			try { 
				request.getRequestDispatcher("/build.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGRES){//帮会资源
			try { 
				request.getRequestDispatcher("/res.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGCONTRIBUTEMONEY){//帮会资源捐献金钱
			try { 
				request.getRequestDispatcher("/contribute.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGCONTRIBUTEGOODS){//帮会资源捐献物品
			try { 
				request.getRequestDispatcher("/contribute.do?cmd=n3"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.DISPENSERES){//分发帮会资源
			try { 
				request.getRequestDispatcher("/dispense.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
					
			}
		}else if(menu.getMenuType()==MenuType.INFORMATION){//封测资料提交
			try { 
				request.getRequestDispatcher("/information.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.FIELDMANAGER){// 演武管理员
			try { 
				request.getRequestDispatcher("/fieldmanager.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.FIGHERFIELD){// 演武排行榜
			try { 
				request.getRequestDispatcher("/fielduntangle.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.MAST){// 旗杆
			try { 
				request.getRequestDispatcher("/fieldmast.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
			return null;
		}else if(menu.getMenuType()==MenuType.TONGMAPCARRYMENU){// 帮派地图传送菜单
			try { 
				request.getRequestDispatcher("/tongmapcarrymenuaction.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.LIVESKILLMENU){// 生活技能菜单
			try { 
				request.getRequestDispatcher("/liveskill.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.COOKMENU){// 烹饪菜单
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=1&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.LIANYAOMENU){// 炼药菜单
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=2&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.DUANZAOMENU){// 锻造菜单
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=3&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.ZHIZAOMENU){// 织造菜单
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=4&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.ZHUBAOMENU){// 珠宝菜单
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=5&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.NUJIANGMENU){// 木匠菜单
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=6&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.MENUTOUCHTASK){// 木匠菜单
			try { 
				request.getRequestDispatcher("/menutouchtask.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.VIPLABORAGE){//VIP领取工资
			try { 
				request.getRequestDispatcher("/vip.do?cmd=n3"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.SYSPRIZE){// 领取系统奖励菜单
			try { 
				request.getRequestDispatcher("/sysprize.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.GMKICK){// GM踢人下线功能
			try { 
				request.getRequestDispatcher("/gmkick.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TIJIAO){// 提交帮派物品
			try { 
				request.getRequestDispatcher("/tijiao.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.SIGNUP){// 攻城战报名
			try { 
				request.getRequestDispatcher("/signup.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.JOINUP){// 进入攻城战区域
			try { 
				request.getRequestDispatcher("/signup.do?cmd=n3"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.CITYDOOR){// 攻城战大门
		/*	TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
			resultWml = tongSiegeBattleService.dialog(menu,roleInfo);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id); 
			request.setAttribute("menu_type", menu_type); 
			request.setAttribute("resultWml", resultWml);*/
			
			return mapping.findForward("display");
		}else if(menu.getMenuType()==MenuType.DIAOXIANG){// 攻城战雕像
			
			resultWml = menuService.dialog(menu);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id); 
			request.setAttribute("menu_type", menu_type);
			request.setAttribute("resultWml", resultWml);
			
			return mapping.findForward("citydoordisplay");
			
		}else if(menu.getMenuType()==MenuType.ZHAOHUN){// 攻城区域的招魂幡
			resultWml = menuService.createZhaoAttackNPCMenu(menu, roleInfo,request,response);
			if ( resultWml == null) {
    			try { 
    				//response.sendRedirect(GameConfig.getContextPath()+"/attackNPC.do?cmd=n4&pPk="+userTempBean.getPPk()+"&chair="+request.getParameter("chair"));
    				request.getRequestDispatcher("/attackNPC.do?cmd=n4&pPk="+p_pk).forward(request,response);
    				
    			} catch (Exception e) {
    
    			}
    			return null;
			} 
		}
		else if(menu.getMenuType()== MenuType.JIEYI)
			//结义功能
		{
			synchronized (Constant.JIEYI_IDS)
			{
				if(null==Constant.JIEYI_IDS||"".equals(Constant.JIEYI_IDS.trim()))
					Constant.JIEYI_IDS = menu.getMenuOperate1();
			}
			
			return mapping.findForward("jieyi");
		}
		
		else if(menu.getMenuType() == MenuType.JIECHUJIEYI){
			//解除结义 
			return mapping.findForward("jiechujieyi");
		}
		
		else if(menu.getMenuType() == MenuType.JIEHUN){
//			结婚
			synchronized (Constant.JIEHUN_IDS){
				if(null==Constant.JIEHUN_IDS||"".equals(Constant.JIEHUN_IDS.trim())){
					Constant.JIEHUN_IDS = menu.getMenuOperate1();
				}
			}
			return mapping.findForward("jiehun");
		}
		else if(menu.getMenuType() == MenuType.LIHUN){
//			离婚
			return mapping.findForward("lihun");
		}else if(menu.getMenuType() == MenuType.ZHAOTU){
//			招徒
			return mapping.findForward("zhaotu");
		}else if(menu.getMenuType() == MenuType.BAISHI){
			//拜师
			return mapping.findForward("baishi");
		}
		else if(menu.getMenuType() == MenuType.LEITAI){
			//打擂
			request.setAttribute("menu_id", menu.getId());
			return mapping.findForward("leitai");
		}
		else if(menu.getMenuType() == MenuType.INTO_LEITAI){
			//进入擂台
			request.setAttribute("menu_id", menu.getId());
			return mapping.findForward("intoleitai");
		}
		else if(menu.getMenuType() == MenuType.VIEW_COM_RESULT){
			//查看比赛成绩
			request.setAttribute("menu_id", menu.getId());
			return mapping.findForward("view_com_result");
		}
		else if(menu.getMenuType() == MenuType.ACTIVE_LEITAI){
			//擂台对阵表
			return mapping.findForward("active_leitai");
		}else if(menu.getMenuType()== MenuType.SHEARE){
			//共享菜单
			if(CompassService.useSheareMenu(menu.getId(), p_pk)){
				try {
					request.getRequestDispatcher("/menu/exchange.do?chair="+request.getParameter("chair")).forward(request, response);
					return null;
				} catch (Exception e) {

				} 
			}else{
				request.setAttribute("message", "很遗憾，已经有人领取了奖励……");
				return mapping.findForward("mess");
			}
		}else if(menu.getMenuType()==MenuType.OLD_XIANG){
			//破旧箱子
			if(CompassService.useOld_Xiang(menu, p_pk)){
				try {
					request.getRequestDispatcher("/menu/exchange.do?chair="+request.getParameter("chair")).forward(request, response);
					return null;
				} catch (Exception e) {

				} 
			}else{
				request.setAttribute("message", "很遗憾，已经有人领取了奖励……");
				return mapping.findForward("mess");
			}
		}else if(menu.getMenuType() == MenuType.LIYONG){
			//答题老人
			request.setAttribute("menu", menu);
			return mapping.findForward("liyong");
		}else if(menu.getMenuType() == MenuType.LEITAI_ACTIVE){
			//擂台报名
			if(menu.getMenuOperate2()!=null&&!"".equals(menu.getMenuOperate2().trim())){
				try{
					if(roleInfo.getBasicInfo().getGrade()<Integer.valueOf(menu.getMenuOperate2().trim())){
						request.setAttribute("message", "该擂台最低等级限制为"+menu.getMenuOperate2());
						return mapping.findForward("mess");
					}
					//进入擂台
					return mapping.findForward("leitaibaoming");
				}catch(Exception e){
					logger.info(e.getMessage());
					//进入擂台
					return mapping.findForward("leitaibaoming");
				}
			}
		}else if(menu.getMenuType()==MenuType.LEITAI_CHALLENGE_MAGAGER){
			//挑战擂台管理员
			return mapping.findForward("challengeleitai");
		}else if(menu.getMenuType()==MenuType.BATTLE){
			//对战擂台
			return mapping.findForward("battle");
		}
		else if( menu.getMenuType() == MenuType.UNCHARTEDROOMSERVICE )//秘境
		{
			try { 
				request.getRequestDispatcher("/urs.do?cmd=n1"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.WISHINGTREE )//许愿树
		{
			try { 
				request.getRequestDispatcher("/wishingtree.do?cmd=index").forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.WISHINGTREEGM )//许愿树
		{
			try { 
				request.getRequestDispatcher("/wishingtree.do?cmd=indexGM").forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAICONTESTRANK )//门派大弟子排名
		{
			try { 
				request.getRequestDispatcher("/menpai.do?cmd=rank"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAICONTESTBONUS )//门派大弟子领奖
		{
			try { 
				request.getRequestDispatcher("/menpai.do?cmd=get"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.TIANGUANINNOMAL)//天关
		{
			try { 
				request.getRequestDispatcher("/tianguan.do?cmd=in"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAINPC)//门派NPC
		{
			try { 
				request.getRequestDispatcher("/menpainpc.do?cmd=attack"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAINPCRANK)//门派NPC
		{
			try { 
				request.getRequestDispatcher("/menpainpc.do?cmd=rank"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if(menu.getMenuType()==MenuType.PKREGIST)//PK活动报名
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n1").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.ENTERPKVIEW)//PK活动进入比赛场地
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n3").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.SHOWVSTABLE)//PK活动查看对阵表
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n2&view=vs").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.SHOWPKRESULT)//PK活动查看比赛结果
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n2&view=rs").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.PKRULE)//PK活动查看比赛规则
		{
			try
			{
				request.getRequestDispatcher("/jsp/attack/pk/pk_rule.jsp").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.PKPRICE)//PK活动胜利玩家领取奖品
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n6").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		request.setAttribute("task_id", task_id);
		request.setAttribute("menu_id", menu_id);
		request.setAttribute("menu_type", menu_type); 
		request.setAttribute("resultWml", resultWml); 
		return mapping.findForward("display");
	}
	
	
	
	
	//显示所有菜单
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String map_id = request.getParameter("map_id");
		if( map_id==null )
		{
			logger.info("map_id为空!");
		}
        //UserTempBean  userTempBean = (UserTempBean)request.getSession().getAttribute("userTempBean");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
        MenuService menuService = new MenuService();
		List<OperateMenuVO> menus = menuService.menuRefurbish(roleInfo);
		//List<OperateMenuVO> taskmenus = menuService.getTaskMenuNPC(roleInfo,Integer.parseInt(map_id),roleInfo.getBasicInfo().getPPk());
		//request.setAttribute("taskmenus", taskmenus);
		request.setAttribute("menus", menus);
		return mapping.findForward("list");
	}
	
	
	//传送菜单处理
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String menu_id = request.getParameter("menu_id");
		if( menu_id==null )
		{
			logger.info("***********************************menu_id为空!");
		}
		
		
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(menu_id));
		menuService.carrayExpend(menu, roleInfo);
		
		//设置新的sceneId	
		String sceneIds = menu.getMenuOperate1();
		String sceneId = sceneIds.split(";")[0];
		
		roleInfo.getBasicInfo().updateSceneId(sceneId);
		return mapping.findForward("refurbish_scene");
	}
}