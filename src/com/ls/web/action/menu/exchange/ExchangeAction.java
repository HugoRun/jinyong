package com.ls.web.action.menu.exchange;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.menu.exchange.ExchangeService;
import com.ls.web.service.player.RoleService;

public class ExchangeAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");
	
	//�г��ɶһ�����Ʒ
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		
		if( menu_id==null )
		{
			logger.info("exchangeAction��menu_idΪ��!");
		}
		
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		request.setAttribute("menu_id", menu_id);
		request.setAttribute("pPk", roleInfo.getBasicInfo().getPPk()+"");
		request.setAttribute("uPk", roleInfo.getBasicInfo().getUPk()+"");
		request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
		
		MenuService menuService = new MenuService();
		OperateMenuVO vo = menuService.getMenuById(Integer.parseInt(menu_id));
		
		request.setAttribute("operate2", vo.getMenuOperate2());
		String operate3s = vo.getMenuOperate3();
		String[] operate3 = operate3s.split(";");
		
		//�����������ֽڵĵ��������ֿ��Ƶ��Ƿ�֧������ת������, 1Ϊ֧��,0Ϊ��֧��.
		if(operate3.length >= 6) {
			//logger.info("operate3s="+operate3s);
			request.setAttribute("operate3",operate3[5] );
		}
		ExchangeService exservice  = new ExchangeService(); 
		
		List<String> exchange_list = exservice.getChangeDisplayList(vo);
		List<String> aimchange_list = exservice.getAimChangeList(vo);
		
		request.setAttribute("exchange_list", exchange_list);
		request.setAttribute("aimchange_list", aimchange_list);
		request.setAttribute("menu", vo);
		return mapping.findForward("list");
	}
	
	//�һ���Ʒ
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk()+"";
		
		String mapid = request.getParameter("mapid");
		String menu_id = request.getParameter("menu_id");
		
		request.setAttribute("pPk",pPk);
		request.setAttribute("mapid",mapid);
		
		String address = request.getParameter("address");
		logger.info("address : "+address);
		
		ExchangeService exservice  = new ExchangeService(); 
		
		MenuService menuService = new MenuService();
		OperateMenuVO operateMenuVO = menuService.getMenuById(Integer.parseInt(menu_id));
		
		
		String[] contorl = operateMenuVO.getMenuOperate3().split(";");		//��ȡ�һ���������
		
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		logger.info("Ŀǰ�ȼ�:"+partInfoVO.getPGrade()+",Ҫ��ȼ�:"+contorl[0]+"�Լ��Ա�:"+partInfoVO.getPSex()+",Ҫ���Ա�:"+contorl[1]);
		if(!contorl[0].equals("0") ){
			String[] grade = contorl[0].split("-");
			if(Integer.valueOf(grade[0]) > partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ����㣡�ȼ����Ҫ��"+grade[0];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else if(Integer.valueOf(grade[1]) < partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ�����Ҫ�󣡵ȼ����Ҫ��"+grade[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if(Integer.valueOf(contorl[1]) != 0){
			if(Integer.valueOf(contorl[1]) != partInfoVO.getPSex()){
				String resultWml = "�Բ��������Ա�����������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		logger.info("���Ļ���״��:"+partInfoVO.getPHarness()+",Ҫ�����״��:"+contorl[2]);
		if(Integer.valueOf(contorl[2]) != 0){
			if(Integer.valueOf(contorl[2]) != partInfoVO.getPHarness()){
				String resultWml = "�Բ������Ļ���״��������������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if (!contorl[3].equals("0") && roleInfo.getTitleSet().isHaveByTitleStr(contorl[3])==false )
		{
			String resultWml = "�Բ������ĳ�ν״��������������";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		if (!contorl[4].equals("0"))
		{
			TimeControlService timeControlService = new TimeControlService();
			logger.info("����Ƿ����:"+timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))+",����ʹ�ô���:"+contorl[4]);
			
			if(!timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))){
				String resultWml = "�Բ����������Ѿ�ʹ��"+contorl[4]+"�Σ��Ѿ��ﵽ���ƣ�������������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else {
				
			}
		}
		
		//�ж��Ƿ����㹻��ԭ����
		String result = exservice.getPPkHasGoods(pPk,address,operateMenuVO,1);
		logger.info("ԭ�����Ƿ��㹻: "+result);
		String[] results = result.split(",");
		if(Integer.valueOf(results[0]) == -1){
			String resultWml = results[1];
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}else if(Integer.valueOf(results[0]) == 1){
			//�ж��Ƿ����㹻�Ŀռ������װ�����߻��Ǯ
			String hasWareSpare = exservice.getHasWareSpare(pPk,operateMenuVO,address,1);
			logger.info("�Ƿ����㹻�ո��� :"+hasWareSpare);
			String hasWareSpares = hasWareSpare.split(",")[0];
			if(Integer.valueOf(hasWareSpares) == 1){
				String resultWml = exservice.exchangeGoods(pPk,operateMenuVO,address,1);
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else {
				String resultWml = hasWareSpare.split(",")[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		return mapping.findForward("list");
	}
	
	//�鿴���һ���Ʒ����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		//��Ʒ����,4Ϊ����,1Ϊװ��
		String goodsType = request.getParameter("goodsType");
		String goodsId = request.getParameter("goodsId");
		
		request.setAttribute("menu_id",(String)request.getParameter("menu_id"));
		
		String resultWml = "";
		if(goodsType.equals(GoodsType.PROP+"")) {
			GoodsService goodsService = new GoodsService();
			resultWml = goodsService.getPropInfoWmlMai(pPk, Integer.valueOf(goodsId));
						
		}else{
			EquipDisplayService equipDisplayService = new EquipDisplayService();
			equipDisplayService.getEquipDisplay(roleInfo, Integer.valueOf(goodsId), false);
		}
		
		request.setAttribute("resultWml",resultWml);
		
		return mapping.findForward("goodsView");
	}
	
	//�һ���Ʒ
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pPk  = roleInfo.getBasicInfo().getPPk()+"";
		
		String mapid = request.getParameter("mapid");
		String menu_id = request.getParameter("menu_id");
		
		String exchange_nums = request.getParameter("exchange_num");
		
		
		request.setAttribute("pPk",pPk);
		request.setAttribute("mapid",mapid);
		
		
		int exchange_num = 1;
		if(exchange_nums != null && !exchange_nums.equals("")) {
			if(StringUtil.isNumber(exchange_nums)) {
				exchange_num = Integer.valueOf(exchange_nums);
			}
		}
		if(exchange_num < 1){
			String resultWml = "�Բ���,��������ȷ����!";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		
		String address = request.getParameter("address");
		logger.info("address="+address+" ,exchange_nums="+exchange_nums);
		
		ExchangeService exservice  = new ExchangeService(); 
		
		MenuService menuService = new MenuService();
		OperateMenuVO operateMenuVO = menuService.getMenuById(Integer.parseInt(menu_id));
		
		
		String[] contorl = operateMenuVO.getMenuOperate3().split(";");		//��ȡ�һ���������
		
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		//�Զһ��������������ж�
		logger.info("Ŀǰ�ȼ�:"+partInfoVO.getPGrade()+",Ҫ��ȼ�:"+contorl[0]+"�Լ��Ա�:"+partInfoVO.getPSex()+",Ҫ���Ա�:"+contorl[1]);
		if(!contorl[0].equals("0") ){
			String[] grade = contorl[0].split("-");
			if(Integer.valueOf(grade[0]) > partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ����㣡�ȼ����Ҫ��"+grade[0];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			} else if(Integer.valueOf(grade[1]) < partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ�����Ҫ�󣡵ȼ����Ҫ��"+grade[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if(Integer.valueOf(contorl[1]) != 0){
			if(Integer.valueOf(contorl[1]) != partInfoVO.getPSex()){
				String resultWml = "�Բ��������Ա�����������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		logger.info("���Ļ���״��:"+partInfoVO.getPHarness()+",Ҫ�����״��:"+contorl[2]);
		if(Integer.valueOf(contorl[2]) != 0){
			if(Integer.valueOf(contorl[2]) != partInfoVO.getPHarness()){
				String resultWml = "�Բ������Ļ���״��������������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if (!contorl[3].equals("0") && roleInfo.getTitleSet().isHaveByTitleStr(contorl[3])==false)
		{
			String resultWml = "�Բ������ĳ�ν״��������������";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		if (!contorl[4].equals("0"))
		{
			TimeControlService timeControlService = new TimeControlService();
			logger.info("����Ƿ����:"+timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))+",����ʹ�ô���:"+contorl[4]);
			
			if(!timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))){
				String resultWml = "�Բ����������Ѿ�ʹ��"+contorl[4]+"�Σ��Ѿ��ﵽ���ƣ�������������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else {
				timeControlService.updateControlInfo(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU);
			}
		}
		
		//�ж��Ƿ����㹻��ԭ����
		String result = exservice.getPPkHasGoods(pPk,address,operateMenuVO,exchange_num);
		logger.info("ԭ�����Ƿ��㹻: "+result);
		String[] results = result.split(",");
		if(Integer.valueOf(results[0]) == -1){
			String resultWml = results[1];
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}else if(Integer.valueOf(results[0]) == 1){
			//�ж��Ƿ����㹻�Ŀռ������װ�����߻��Ǯ
			String hasWareSpare = exservice.getHasWareSpare(pPk,operateMenuVO,address,exchange_num);
			logger.info("�Ƿ����㹻�ո��� :"+hasWareSpare);
			String hasWareSpares = hasWareSpare.split(",")[0];
			if(Integer.valueOf(hasWareSpares) == 1){
				String resultWml = exservice.exchangeGoods(pPk,operateMenuVO,address,exchange_num);
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else {
				String resultWml = hasWareSpare.split(",")[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		return mapping.findForward("list");
	}
}