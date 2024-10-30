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
 * �˵���������
 */
public class MenuAction extends ActionBase {
	Logger logger = Logger.getLogger("log.action");
	//��ʾ�����˵�����
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
		//��Ҵ����˵�npc
		UTaskDAO uTaskDAO = new UTaskDAO(); 
		//��ѯ���������Ǹ�������
		int taskType = 5;
		List<UTaskVO> list = uTaskDAO.getMenuId(p_pk+"",taskType);
		String resultWml = null; 
		TaskDAO taskDAO = new TaskDAO();
		int tPk = 0 ;
		String t_point = null;
		String duihua = null;
		String sss = null;
		if( menu.getMenuTaskFlag() == 1 )	//����˵� 
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
									duihua = "������������";
									sss = "������������";
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
									//����Ҫ�ж��Ѿ��������Ǹ�ֵ 9��27�� �򽨹���ҵ������Ҫ ȥ������һ������
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
		
		
		if( menu.getMenuType() == MenuType.STONE_UPGRADE )	//��ʯ�ϳɲ˵�
		{   
			return super.dispath(request, response, "/prop.do?cmd=upgradeIndex");
		}	
		else if( menu.getMenuType() == MenuType.EQUIP_INLAY_STONE )	//��Ƕ��ʯ�˵�
		{   
			return super.dispath(request, response, "/equip.do?cmd=inlayEquipList");
		}	
		else if( menu.getMenuType() == MenuType.DIALOG )	//�Ի��˵�
		{   
			resultWml = menuService.dialog(menu);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id); 
			request.setAttribute("menu_type", menu_type); 
			request.setAttribute("resultWml", resultWml);
			
			return mapping.findForward("display");
		}	
		else if( menu.getMenuType() == MenuType.TASKCARRY )	//�����Ͳ˵�
		{
			resultWml = menuService.taskCarry(menu,p_pk,request,response);
			if( resultWml==null )//ֱ�Ӵ���
			{
				return n3(mapping, form, request, response);
			}
		}
		else if( menu.getMenuType() == MenuType.INSTANCE_DOOR )	//�������Ͳ˵�
		{
			resultWml = menuService.instanceDoor(menu, roleInfo);
			if( resultWml==null )//ֱ�Ӵ���
			{
				return super.returnScene(request, response);
			}
		}
		else if( menu.getMenuType() == MenuType.NORMALCARRY )	//��ͨ���Ͳ˵�
		{
			resultWml = menuService.normalCarry(menu, roleInfo,request,response);
			if( resultWml==null )//ֱ�Ӵ���
			{
				return n3(mapping, form, request,response);
			}
		}
		else if( menu.getMenuType() == MenuType.CHUANSONG )	// ����ս����ͨ���Ͳ˵�
		{
			resultWml = menuService.tongSiegeCarry(menu, roleInfo,response);
			if( resultWml==null )//ֱ�Ӵ���
			{
				return n3(mapping, form, request, response);
			}
		}
		else if( menu.getMenuType() == MenuType.REST )//��Ϣ�˵�
		{
			resultWml = menuService.rest(menu, roleInfo);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id);
			request.setAttribute("menu_type", menu_type); 
			request.setAttribute("resultWml", resultWml); 
			return mapping.findForward("display");
		}
		else if( menu.getMenuType() == MenuType.FATHER )//���˵�
		{
			resultWml = menuService.fatherList(roleInfo,menu,request, response);
		}
		else if( menu.getMenuType() == MenuType.DEADNPC )//���Ա�������NPC�˵�
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
		else if( menu.getMenuType() == MenuType.FAILNPC )//���Դ�ܵ�NPC�˵�
		{
			menuService.createLoseNPCMenu(menu, roleInfo);
			return super.dispath(request, response, "/attackNPC.do?cmd=n4&pPk="+p_pk);
		}
		else if( menu.getMenuType() == MenuType.SAlE )//���˵�
		{
			return super.dispath(request, response, "/menu/sale.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.BUY )//��˵�
		{
			return super.dispath(request, response, "/buy.do?cmd=n1&menu_id="+menu_id);
		}
		else if( menu.getMenuType() == MenuType.EXCHANGE )//�һ��˵�
		{
			return super.dispath(request, response,"/menu/exchange.do?chair="+request.getParameter("chair"));
		}
		else if( menu.getMenuType() == MenuType.PETSALE )//������˵�
		{
			return super.dispath(request, response,"/menu/petsale.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AVOIDPKPROP )//��PK����
		{
			return super.dispath(request, response,"/avoidpkprop.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AVOIDPKPROP )//��PK����
		{
			return super.dispath(request, response,"/avoidpkprop.do?cmd=n1"+"&chair="+request.getParameter("chair"));
		} 
		
		else if( menu.getMenuType() == MenuType.TONG )//�������NPC�˵�
		{
			resultWml = menuService.tongjump(menu,request,response); 
		} 
		
		else if( menu.getMenuType() == MenuType.GOODSCCSTORAGE )//��Ʒ�ֿⴢ��˵�
		{
			request.setAttribute("menu_id", menu_id);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_type", menu_type); 
			return super.dispath(request, response,"/menu/storage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.GOODSQCSTORAGE )//��Ʒ�ֿ�ȡ���˵�
		{
			request.setAttribute("menu_id", menu_id);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_type", menu_type); 
			return super.dispath(request, response,"/menu/getStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.PETCCSTORAGE )//����ֿⴢ��˵�
		{
			return super.dispath(request, response,"/menu/addPetStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.PETQCSTORAGE )//����ֿ�ȡ���˵�
		{
			return super.dispath(request, response,"/menu/getPetStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.MONEYCCSTORAGE )//��Ǯ�ֿⴢ��˵�
		{
			return super.dispath(request, response,"/menu/addMoneyStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.MONEYQCSTORAGE )//��Ǯ�ֿ�ȡ���˵�
		{
			return super.dispath(request, response,"/menu/getMoneyStorage.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AUCTIONSELL )//�����������˵�
		{
			return super.dispath(request, response,"/menu/auction.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.AUCTIONBUY )//����������Ʒ�˵�
		{
			return super.dispath(request, response,"/menu/auctionBuy.do?cmd=n1");
		}
		else if( menu.getMenuType() == MenuType.CLOSEPK )//��PK���ز˵�
		{
			resultWml = menuService.closePK(menu, roleInfo);
		}
		else if( menu.getMenuType() == MenuType.OPENPK )//��PK���ز˵�
		{
			resultWml = menuService.openPK(menu, roleInfo);
		}
		else if( menu.getMenuType() == MenuType.AUCTIONHELP )//�������ֲ˵�
		{
			try {
				request.getRequestDispatcher("/menu/auctionGet.do?cmd=n4&moreOrNot=putong").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.AUCTIONHOUSE )//�����ֿ�˵�
		{
			try {
				request.getRequestDispatcher("/menu/auctionGet.do?cmd=n2").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}	
		else if( menu.getMenuType() == MenuType.PETSELL )//����������������
		{
			try {	
				request.getRequestDispatcher("/menu/auctionPetSell.do?cmd=n1").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.PETBUY )//���������������
		{
			try {
				request.getRequestDispatcher("/menu/auctionPetBuy.do?cmd=n1").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.AUCTIONPETHELP )//�������������ֲ˵�
		{
			try {
				request.getRequestDispatcher("/menu/auctionPetHelp.do?cmd=n1&moreOrNot=not").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.AUCTIONPETHOUSE )//�����������ֿ�˵�
		{
			try {
				request.getRequestDispatcher("/menu/auctionPetHouse.do?cmd=n2").forward(request, response);
			} catch (Exception e) {
			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.EQUIP_MAINTAIN )//����װ���˵�
		{	
			return super.dispath(request, response, "/equip.do?cmd=maintainIndex");
		}
		else if( menu.getMenuType() == MenuType.RECRUIT )//�����ļ
		{	
			resultWml = menuService.recruit(menu, roleInfo,request,response); 
		} 
		else if( menu.getMenuType() == MenuType.RELATION )//����ϵ
		{
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/relationaction.do?cmd=n1").forward(request, response); 
				
			}catch (Exception e) {

			}
			return null;
		}
		else if( menu.getMenuType() == MenuType.QUESTION )//����ϵͳ
		{
			return this.dispath(request, response, "/question.do?cmd=n1"+"&menu_id="+menu_id);
		}
		//else if( menu.getMenuType() == MenuType.DISPLAYOPERATE )//�����Բ˵�
		//{
		//	resultWml = menuService.displayFatherList(menu, userTempBean);
		//}
		else if( menu.getMenuType() == MenuType.BUFF )//buff�˵�
		{
			return super.dispath(request, response, "/buffMenu.do?cmd=n1"+"&menu_id="+menu_id);
		}else if(menu.getMenuType()==MenuType.BOOK){//book�˵�
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/bookMenu.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.EQUIP_CHANGE_WX){//װ������װ��
			try {
				roleInfo.createEquipProduct(EquipProduct.change_wx);
				request.getRequestDispatcher("/equip.do?cmd=productIndex").forward(request, response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(menu.getMenuType()==MenuType.EQUIP_UPGRADE){//װ������
			try {
				roleInfo.createEquipProduct(EquipProduct.upgrade);
				request.getRequestDispatcher("/equip.do?cmd=productIndex").forward(request, response); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(menu.getMenuType()==MenuType.DECOMPOSE){//װ���ֽ�
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/updecompose.do?cmd=n10"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}else if(menu.getMenuType()==MenuType.EQUIPTRANSLATE){//װ��ת��
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/wxc.do?cmd=open"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if( menu.getMenuType() == MenuType.TONGMAP )	//���ɵ�ͼ����
		{
			resultWml = menuService.TongMapCarry(menu, roleInfo);
			if( resultWml==null )//ֱ�Ӵ���
			{
				return n3(mapping, form, request, response);
			}
		}else if(menu.getMenuType()==MenuType.LOTTERY){//����
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/lottery.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.LABORAGE){//�칤��
			try {
				//response.sendRedirect(GameConfig.getContextPath()+"/relationaction.do?cmd=n1"+"&chair="+request.getParameter("chair")); 
				request.getRequestDispatcher("/laborage.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response); 
				
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGBUILD){//��Ὠ��
			try { 
				request.getRequestDispatcher("/build.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGRES){//�����Դ
			try { 
				request.getRequestDispatcher("/res.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGCONTRIBUTEMONEY){//�����Դ���׽�Ǯ
			try { 
				request.getRequestDispatcher("/contribute.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TONGCONTRIBUTEGOODS){//�����Դ������Ʒ
			try { 
				request.getRequestDispatcher("/contribute.do?cmd=n3"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.DISPENSERES){//�ַ������Դ
			try { 
				request.getRequestDispatcher("/dispense.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
					
			}
		}else if(menu.getMenuType()==MenuType.INFORMATION){//��������ύ
			try { 
				request.getRequestDispatcher("/information.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.FIELDMANAGER){// �������Ա
			try { 
				request.getRequestDispatcher("/fieldmanager.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.FIGHERFIELD){// �������а�
			try { 
				request.getRequestDispatcher("/fielduntangle.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.MAST){// ���
			try { 
				request.getRequestDispatcher("/fieldmast.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
			return null;
		}else if(menu.getMenuType()==MenuType.TONGMAPCARRYMENU){// ���ɵ�ͼ���Ͳ˵�
			try { 
				request.getRequestDispatcher("/tongmapcarrymenuaction.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.LIVESKILLMENU){// ����ܲ˵�
			try { 
				request.getRequestDispatcher("/liveskill.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.COOKMENU){// ��⿲˵�
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=1&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.LIANYAOMENU){// ��ҩ�˵�
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=2&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.DUANZAOMENU){// ����˵�
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=3&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.ZHIZAOMENU){// ֯��˵�
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=4&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.ZHUBAOMENU){// �鱦�˵�
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=5&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.NUJIANGMENU){// ľ���˵�
			try { 
				request.getRequestDispatcher("/synthesize.do?cmd=n1"+"&menu_id="+menu_id+"&s_type=6&chair="+request.getParameter("chair")).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.MENUTOUCHTASK){// ľ���˵�
			try { 
				request.getRequestDispatcher("/menutouchtask.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.VIPLABORAGE){//VIP��ȡ����
			try { 
				request.getRequestDispatcher("/vip.do?cmd=n3"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.SYSPRIZE){// ��ȡϵͳ�����˵�
			try { 
				request.getRequestDispatcher("/sysprize.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.GMKICK){// GM�������߹���
			try { 
				request.getRequestDispatcher("/gmkick.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.TIJIAO){// �ύ������Ʒ
			try { 
				request.getRequestDispatcher("/tijiao.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.SIGNUP){// ����ս����
			try { 
				request.getRequestDispatcher("/signup.do?cmd=n1"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.JOINUP){// ���빥��ս����
			try { 
				request.getRequestDispatcher("/signup.do?cmd=n3"+"&menu_id="+menu_id).forward(request, response);
			} catch (Exception e) {
				
			}
		}else if(menu.getMenuType()==MenuType.CITYDOOR){// ����ս����
		/*	TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
			resultWml = tongSiegeBattleService.dialog(menu,roleInfo);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id); 
			request.setAttribute("menu_type", menu_type); 
			request.setAttribute("resultWml", resultWml);*/
			
			return mapping.findForward("display");
		}else if(menu.getMenuType()==MenuType.DIAOXIANG){// ����ս����
			
			resultWml = menuService.dialog(menu);
			request.setAttribute("task_id", task_id);
			request.setAttribute("menu_id", menu_id); 
			request.setAttribute("menu_type", menu_type);
			request.setAttribute("resultWml", resultWml);
			
			return mapping.findForward("citydoordisplay");
			
		}else if(menu.getMenuType()==MenuType.ZHAOHUN){// ����������л��
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
			//���幦��
		{
			synchronized (Constant.JIEYI_IDS)
			{
				if(null==Constant.JIEYI_IDS||"".equals(Constant.JIEYI_IDS.trim()))
					Constant.JIEYI_IDS = menu.getMenuOperate1();
			}
			
			return mapping.findForward("jieyi");
		}
		
		else if(menu.getMenuType() == MenuType.JIECHUJIEYI){
			//������� 
			return mapping.findForward("jiechujieyi");
		}
		
		else if(menu.getMenuType() == MenuType.JIEHUN){
//			���
			synchronized (Constant.JIEHUN_IDS){
				if(null==Constant.JIEHUN_IDS||"".equals(Constant.JIEHUN_IDS.trim())){
					Constant.JIEHUN_IDS = menu.getMenuOperate1();
				}
			}
			return mapping.findForward("jiehun");
		}
		else if(menu.getMenuType() == MenuType.LIHUN){
//			���
			return mapping.findForward("lihun");
		}else if(menu.getMenuType() == MenuType.ZHAOTU){
//			��ͽ
			return mapping.findForward("zhaotu");
		}else if(menu.getMenuType() == MenuType.BAISHI){
			//��ʦ
			return mapping.findForward("baishi");
		}
		else if(menu.getMenuType() == MenuType.LEITAI){
			//����
			request.setAttribute("menu_id", menu.getId());
			return mapping.findForward("leitai");
		}
		else if(menu.getMenuType() == MenuType.INTO_LEITAI){
			//������̨
			request.setAttribute("menu_id", menu.getId());
			return mapping.findForward("intoleitai");
		}
		else if(menu.getMenuType() == MenuType.VIEW_COM_RESULT){
			//�鿴�����ɼ�
			request.setAttribute("menu_id", menu.getId());
			return mapping.findForward("view_com_result");
		}
		else if(menu.getMenuType() == MenuType.ACTIVE_LEITAI){
			//��̨�����
			return mapping.findForward("active_leitai");
		}else if(menu.getMenuType()== MenuType.SHEARE){
			//����˵�
			if(CompassService.useSheareMenu(menu.getId(), p_pk)){
				try {
					request.getRequestDispatcher("/menu/exchange.do?chair="+request.getParameter("chair")).forward(request, response);
					return null;
				} catch (Exception e) {

				} 
			}else{
				request.setAttribute("message", "���ź����Ѿ�������ȡ�˽�������");
				return mapping.findForward("mess");
			}
		}else if(menu.getMenuType()==MenuType.OLD_XIANG){
			//�ƾ�����
			if(CompassService.useOld_Xiang(menu, p_pk)){
				try {
					request.getRequestDispatcher("/menu/exchange.do?chair="+request.getParameter("chair")).forward(request, response);
					return null;
				} catch (Exception e) {

				} 
			}else{
				request.setAttribute("message", "���ź����Ѿ�������ȡ�˽�������");
				return mapping.findForward("mess");
			}
		}else if(menu.getMenuType() == MenuType.LIYONG){
			//��������
			request.setAttribute("menu", menu);
			return mapping.findForward("liyong");
		}else if(menu.getMenuType() == MenuType.LEITAI_ACTIVE){
			//��̨����
			if(menu.getMenuOperate2()!=null&&!"".equals(menu.getMenuOperate2().trim())){
				try{
					if(roleInfo.getBasicInfo().getGrade()<Integer.valueOf(menu.getMenuOperate2().trim())){
						request.setAttribute("message", "����̨��͵ȼ�����Ϊ"+menu.getMenuOperate2());
						return mapping.findForward("mess");
					}
					//������̨
					return mapping.findForward("leitaibaoming");
				}catch(Exception e){
					logger.info(e.getMessage());
					//������̨
					return mapping.findForward("leitaibaoming");
				}
			}
		}else if(menu.getMenuType()==MenuType.LEITAI_CHALLENGE_MAGAGER){
			//��ս��̨����Ա
			return mapping.findForward("challengeleitai");
		}else if(menu.getMenuType()==MenuType.BATTLE){
			//��ս��̨
			return mapping.findForward("battle");
		}
		else if( menu.getMenuType() == MenuType.UNCHARTEDROOMSERVICE )//�ؾ�
		{
			try { 
				request.getRequestDispatcher("/urs.do?cmd=n1"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.WISHINGTREE )//��Ը��
		{
			try { 
				request.getRequestDispatcher("/wishingtree.do?cmd=index").forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.WISHINGTREEGM )//��Ը��
		{
			try { 
				request.getRequestDispatcher("/wishingtree.do?cmd=indexGM").forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAICONTESTRANK )//���ɴ��������
		{
			try { 
				request.getRequestDispatcher("/menpai.do?cmd=rank"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAICONTESTBONUS )//���ɴ�����콱
		{
			try { 
				request.getRequestDispatcher("/menpai.do?cmd=get"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.TIANGUANINNOMAL)//���
		{
			try { 
				request.getRequestDispatcher("/tianguan.do?cmd=in"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAINPC)//����NPC
		{
			try { 
				request.getRequestDispatcher("/menpainpc.do?cmd=attack"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if( menu.getMenuType() == MenuType.MENPAINPCRANK)//����NPC
		{
			try { 
				request.getRequestDispatcher("/menpainpc.do?cmd=rank"+"&menu_id="+menu_id).forward(request,response);
			} catch (Exception e) {
			}
			
			return null;
		}
		else if(menu.getMenuType()==MenuType.PKREGIST)//PK�����
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n1").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.ENTERPKVIEW)//PK������������
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n3").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.SHOWVSTABLE)//PK��鿴�����
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n2&view=vs").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.SHOWPKRESULT)//PK��鿴�������
		{
			try
			{
				request.getRequestDispatcher("/pkactive.do?cmd=n2&view=rs").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.PKRULE)//PK��鿴��������
		{
			try
			{
				request.getRequestDispatcher("/jsp/attack/pk/pk_rule.jsp").forward(request, response);
			}
			catch (Exception e)
			{
			}
		}
		else if(menu.getMenuType()==MenuType.PKPRICE)//PK�ʤ�������ȡ��Ʒ
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
	
	
	
	
	//��ʾ���в˵�
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String map_id = request.getParameter("map_id");
		if( map_id==null )
		{
			logger.info("map_idΪ��!");
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
	
	
	//���Ͳ˵�����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		String menu_id = request.getParameter("menu_id");
		if( menu_id==null )
		{
			logger.info("***********************************menu_idΪ��!");
		}
		
		
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(menu_id));
		menuService.carrayExpend(menu, roleInfo);
		
		//�����µ�sceneId	
		String sceneIds = menu.getMenuOperate1();
		String sceneId = sceneIds.split(";")[0];
		
		roleInfo.getBasicInfo().updateSceneId(sceneId);
		return mapping.findForward("refurbish_scene");
	}
}