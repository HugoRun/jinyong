package com.web.action.sellinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.sellinfo.SellInfoVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.GoodsControlVO;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.ActionType;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.Wrap;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pub.ben.info.Expression;
import com.web.service.TaskService;
import com.web.service.friend.BlacklistService;

/**
 * @author Administrator ����
 */
public class JiaoYiAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	/**
	 * ��ת����ҳ��
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		PlayerService playerService = new PlayerService();
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		//�ж��Լ��Ľ��׿���ʱ�رյ��򲻿ɽ���
		if(roleInfo.getSettingInfo().getDealControl()==-1)
		{
			String hint="���Ľ��׿����ǹر�״̬���ɽ���!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		//���������
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "�����ڴ�����������״̬,�޷�����");
			return mapping.findForward("return_hint");
		}
		
		String pByPk = request.getParameter("pByPk");// ��������ID
		//�ж�����Ƿ�����
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "�����������!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		//�ж�����Ƿ������Լ��ĺ�����
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			String hints = "����������ĺ�������,����������(��)���н���.";
			request.setAttribute("hints", hints);
			return mapping.findForward("blacklisthint");
		}else if(res == 2){
			String hints = "���ڸ���ҵĺ�������,����������(��)���н���.";
			request.setAttribute("hints", hints);
			return mapping.findForward("blacklisthint");
		}
		
		//�ж����B��״̬�Ƿ���Խ��н���
		String hint = playerService.checkRoleState( Integer.parseInt(pByPk), PlayerState.TRADE);
		if(hint != null ){
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		
		//�ж϶Է�����Ƿ񿪽��׿���
		hint = playerService.isRoleState( Integer.parseInt(pByPk), 1);
		if(hint != null ){
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair"));
		}
		if(roleInfo.getPPk()==Integer.parseInt(pByPk))
		{
			String hints="�����Ժ��Լ����ף�";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hints);
		}
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("jiaiyipage");
	}
	/**
	 * ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String hint = (String)request.getAttribute("hint");
		String pByPk = request.getParameter("pByPk");// ��������ID
		//�ж�����Ƿ�����
		RoleEntity me = this.getRoleEntity(request);
		
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		if(other.isOnline()==false){
			try{
				String hints = "�����������!";
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
				return null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(me.getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			try{
			String hints = "����������ĺ�������,����������(��)���н���.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
			}
			catch(Exception e){
			e.printStackTrace();
		}
		}else if(res == 2){
			try{
			String hints = "���ڸ���ҵĺ�������,����������(��)���н���.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
		
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("me", me);
		request.setAttribute("other", other);
		request.setAttribute("hint", hint);
		return mapping.findForward("zengsongqianpage");
	}
	
	/**
	 * ������Ʒ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me = this.getRoleEntity(request);
		
		int p_pk = me.getPPk();
		
		String pByPk = request.getParameter("pByPk");//�������˵�ID
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		if(other.isOnline()==false)
		{
			String hint = "�����������!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		String w_type_str = request.getParameter("w_type");
		if (w_type_str == null)
		{
			w_type_str = (String) request.getAttribute("w_type");
		}
		if (w_type_str == null)
		{
			w_type_str = "3";
		}
		String page_no_str = request.getParameter("page_no");
		if (page_no_str == null)
		{
			page_no_str = (String) request.getAttribute("page_no");
		}

		int w_type=Wrap.EQUIP;;
		int page_no=1;

		if (w_type_str!= null)
		{
			w_type = Integer.parseInt(w_type_str);
		}

		if (page_no_str != null)
		{
			page_no = Integer.parseInt(page_no_str);
		}

		 
		String path = request.getServletPath();
		request.setAttribute("path", path);

		GoodsService goodsSerivce = new GoodsService();

		QueryPage item_page = null;
		switch(w_type)
		{
			case Wrap.CURE:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.CURE,page_no);break;
			case Wrap.BOOK:item_page = goodsSerivce.getPagePropList(p_pk,Wrap.BOOK, page_no);break;
			case Wrap.EQUIP:item_page = goodsSerivce.getPageSaleEquipOnWrap(p_pk, page_no);break;
			case Wrap.TASK:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.TASK,page_no);break;
			case Wrap.REST:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.REST,page_no);break;
			case Wrap.SHOP:item_page = goodsSerivce.getPagePropList(p_pk, Wrap.SHOP,page_no);break;
		}
		
		item_page.setURL(response, "/jiaoyi.do?cmd=n3&amp;w_type="+w_type+"&amp;pByPk="+pByPk);
		
		request.setAttribute("item_page", item_page);
		request.setAttribute("w_type", w_type_str);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsonggoodspage");
	}
	
	/**
	 * ��������
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		
		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");
		
		String pByPk = request.getParameter("pByPk");
		
		//�ж�����Ƿ�����
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "�����������!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			try{
			String hints = "����������ĺ�������,����������(��)���н���.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
			}
			catch(Exception e){
			e.printStackTrace();
		}
		}else if(res == 2){
			try{
			String hints = "���ڸ���ҵĺ�������,����������(��)���н���.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
		
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		
		if (goods_id == null || w_type == null || pg_pk == null) {
			////System.out.print("goods_id��w_type��pg_pkΪ��");
		}
		String goods_display = null;
		GoodsService goodsService = new GoodsService(); 
		goods_display = goodsService.getPropInfoWmlMai(roleInfo.getBasicInfo().getPPk(),Integer.parseInt(goods_id)); 
		String goodsName = PropCache.getPropNameById(Integer.parseInt(goods_id));
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("goods_display", goods_display);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsonggoodsview");
	} 
	
	/**
	 * ��������
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = this.getRoleEntity(request);
		String goods_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		String goodsName = request.getParameter("goodsName");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		//�ж�����Ƿ�����
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "�����������!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		BlacklistService blacklistService = new BlacklistService();
		int res = blacklistService.isBlacklist(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		if(res == 1){
			try{
			String hints = "����������ĺ�������,����������(��)���н���.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
			}
			catch(Exception e){
			e.printStackTrace();
		}
		}else if(res == 2){
			try{
			String hints = "���ڸ���ҵĺ�������,����������(��)���н���.";
			request.getRequestDispatcher("/pubbuckaction.do?hint="+hints).forward(request, response);
			return null;
		
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		request.setAttribute("w_type", w_type);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goodsName", goodsName);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsonggoodsnumber");
	} 
	
	/**
	 * ���߽���
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		//���������
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "�����ڴ�����������״̬,�޷�����");
			return mapping.findForward("return_hint");
		}
		
		String goods_id = request.getParameter("goods_id");
		String pg_pk = request.getParameter("pg_pk");
		String goodsName = request.getParameter("goodsName");
		String pByPk = request.getParameter("pByPk");
		String w_type = request.getParameter("w_type");
		String pSilver = "0";
		String pCopper = "0";
		String number = request.getParameter("number");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		
		//�ж�����Ƿ�����
		RoleEntity roleInfo1pByuPk = roleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "�����������!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		 String hint = null;
		if(number.length() > 3){
			 hint = "��������ȷ����!";
			    request.setAttribute("hint", hint);
				request.setAttribute("w_type", w_type);
				request.setAttribute("pByPk", pByPk); 
				return mapping.findForward("zengsongpropok");
		}
		 Pattern sum1 = Pattern.compile(Expression.positive_integer_contain0_regexp);
		 Matcher sum2 = sum1.matcher(number);
		 boolean sum3 = sum2.matches();
		 if(sum3==false){
			    hint = "��������ȷ����!";
			    request.setAttribute("hint", hint);
				request.setAttribute("w_type", w_type);
				request.setAttribute("pByPk", pByPk); 
				return mapping.findForward("zengsongpropok");
		 }
		 
		if(number == null || number.equals("") || Integer.parseInt(number) == 0){
			hint = "��������Ҫ���͵���Ʒ����";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}
		//�ж��Ƿ��ظ�����
		SellInfoDAO dao = new SellInfoDAO();
		String ss = dao.getSellExistByPPkAndGoodsId(roleInfo.getBasicInfo().getPPk()+"", goods_id, GoodsType.PROP);
		if(ss != null && !ss.equals("")){
			hint = ss;
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}
		
		//�жϵ�������������
		GoodsService goodsService = new GoodsService();
		hint = goodsService.isBinded(Integer.parseInt(pg_pk), GoodsType.PROP, ActionType.EXCHANGE);
		if(hint != null){
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}
		if (goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods_id)) < Integer.parseInt(number)) {
			hint = "��û��"+number+"��"+goodsName+"";
			request.setAttribute("hint", hint);
			request.setAttribute("w_type", w_type);
			request.setAttribute("pByPk", pByPk); 
			return mapping.findForward("zengsongpropok");
		}

		PartInfoDAO partInfoDAO = new PartInfoDAO(); 
		String name =null;
		try
		{
			name = partInfoDAO.getPartName(pByPk);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		
		int s = goodsService.isPropToWrap(Integer.parseInt(pByPk), Integer.parseInt(goods_id), Integer.parseInt(number));
		if(s != -1){ 
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, goods_id, GoodsType.PROP, Integer.parseInt(number), pSilver, pCopper, SellInfoVO.ZENGSONGPROP, Time);
			hint = "�����͸���"+name+number+"��"+goodsName+",��ȴ��Է����գ�";
			//��������뵯��ʽ��Ϣ����
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
			msgInfo.setPPk(Integer.parseInt(pByPk));
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			
		}else{
			hint = name+"û���㹻�İ�������!";
		}
		request.setAttribute("hint", hint);
		request.setAttribute("w_type", w_type);
		request.setAttribute("pByPk", pByPk); 
		return mapping.findForward("zengsongpropok");
	} 
	
	/**
	 * ��Ʒ����
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,false);
		
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("equip", equip);
		request.setAttribute("pByPk", pByPk);
		return mapping.findForward("zengsongequipview");
	}
	
	/**
	 * ��Ʒ����
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		String wName = request.getParameter("wName");
		String wProtect = request.getParameter("wProtect");
		String bangding = request.getParameter("bangding");
		 
		request.setAttribute("wProtect", wProtect);
		request.setAttribute("bangding", bangding);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("wName", wName);
		return mapping.findForward("zengsongequipnumber");
	}
	
	/**
	 * װ������
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		PartInfoDAO partInfoDAO = new PartInfoDAO();
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		//���������
		if( roleInfo.getBasicInfo().getPlayer_state_by_new()==1)
		{
			this.setHint(request, "�����ڴ�����������״̬,�޷�����");
			return mapping.findForward("return_hint");
		}
		
		String pByPk = request.getParameter("pByPk");
		String pwPk = request.getParameter("pwPk");
		String wName = request.getParameter("wName");
		
		String pSilver = "0";
		String pCopper = "0"; 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		//�ж�����Ƿ�����
		RoleEntity roleInfo1pByuPk = RoleService.getRoleInfoById(pByPk);
		if(roleInfo1pByuPk.isOnline()==false){
			String hint = "�����������!";
			return super.dispath(request, response, "/pubbuckaction.do?hint="+hint);
		}
		
		 String hint = null; 
		//�ж��Ƿ��ظ�����
		SellInfoDAO dao = new SellInfoDAO();
		String ss = dao.getSellExistByPPkAndGoodsId(roleInfo.getBasicInfo().getPPk()+"", pwPk, Wrap.EQUIP);
		if(ss != null && !ss.equals("")){
			hint = ss;
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("hint", hint);
			return mapping.findForward("zengsongequipok");
		}
		int number = 1;
		
		if(goodsService.isEnoughWrapSpace(Integer.parseInt(pByPk),number)){//����
			dao.getSellArmAdd(roleInfo.getBasicInfo().getPPk(), pByPk, pwPk, Wrap.EQUIP, number, pSilver, pCopper, SellInfoVO.ZENGSONGARM, Time);
			hint = "�����͸���"+partInfoDAO.getPartName(pByPk)+wName+",��ȴ��Է����գ�";
			//��������뵯��ʽ��Ϣ����
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO msgInfo = new UMessageInfoVO();
			msgInfo.setMsgType(PopUpMsgType.MESSAGE_SWAP);
			msgInfo.setPPk(Integer.parseInt(pByPk));
			msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_SWAP_FIRST);
			uMsgService.sendPopUpMsg(msgInfo);
			
		}else{
			hint = partInfoDAO.getPartName(pByPk)+"û���㹻�İ�������!";
		} 
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("hint", hint);
		return mapping.findForward("zengsongequipok");
	}
	
	/**
	 * װ�����ײ鿴
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,true);
		request.setAttribute("sPk", request.getParameter("sPk"));
		request.setAttribute("equip_display", equip_display);
		return mapping.findForward("armview");
	}
	
	/**
	 * װ������
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		String hint = null;
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		PartInfoDAO daos = new PartInfoDAO();
		if(playerEquipDao.isHaveById(vo.getSWuping()) == false){
			hint = daos.getPartName(vo.getPPk()+"") + "ȡ��������������";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		request.setAttribute("vo", vo);
		return mapping.findForward("sellarmpage");
	}
	
	/**
	 * װ������
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		GoodsService goodsService = new GoodsService();
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"�Է��������������ͣ�");
		if(vo == null){
			String hint = "�Է�ȡ��������"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//�ж����B��״̬�Ƿ���Խ��н���
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// �ж�Ǯ
		// �޸�Ǯ
		String hint = null;
		//�ж����뽻�����װ���Ƿ��ڰ���
		PlayerEquipVO equip = playerEquipDao.getByID(vo.getSWuping());
		if( equip.isTraded()==false || equip.isOwnByPPk(vo.getPPk())!=null ){
			hint = roleInfoBypPk.getBasicInfo().getName() + "ȡ��������������";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		long money = roleInfo.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1 * 100;
		long pPkmoney = money - money_bak;
		if (pPkmoney < 0)
		{
			hint = "��û���㹻��Ǯ";
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
		}
		else
		{
			if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), 1))
			{// ����
				//�ı�װ����������
				playerEquipDao.updateOwner(vo.getSWuping(), roleInfo.getBasicInfo().getPPk());
				// ������Ұ���ʣ��ռ�����
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(roleInfo.getBasicInfo().getPPk(), -1);
				equipService.addWrapSpare(vo.getPPk(), 1);
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				hint = "���ѻ����" + roleInfoBypPk.getBasicInfo().getName() + "������" + equip.getFullName() + "";
			}
			else
			{
				hint = "��û���㹻�İ����ռ�";
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
			}
		}
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.insertSellInfoRecord(vo.getPPk(), vo.getPByPk(), vo.getSWpType(), vo.getSWuping(), vo.getSWpNumber(), vo.getSWpSilverMoney());
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * װ������
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		// ȷ�����׺�ɾ��
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk); 
		String hint = "��ȡ������" + daos.getPartName(vo.getPPk() + "") + "������";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"���ź��Է�ȡ�����������ͣ�");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	/**
	 * ���߽��ײ鿴 
	 */
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String prop_id = request.getParameter("prop_id");
		String sPk = request.getParameter("sPk");
		
		GoodsService goodsService = new GoodsService();
		String propInfoWml = goodsService.getPropInfoWml(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(prop_id));
		request.setAttribute("sPk", sPk);
		request.setAttribute("propInfoWml", propInfoWml);
		return mapping.findForward("sellpropviewpage");
	}
	
	/**
	 * ���߽���
	 */
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		request.setAttribute("vo", vo);
		return mapping.findForward("sellproppage");
	}
	
	/**
	 * ���߽���
	 */
	public ActionForward n16(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		GoodsService goodsService = new GoodsService();
		TaskService taskService = new TaskService();
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"�Է��������������ͣ�");
		if(vo == null){
			String hint = "�Է�ȡ��������"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//�ж����B��״̬�Ƿ���Խ��н���
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// �ж�Ǯ
		// �޸�Ǯ
		String hint = null;
		//�жϵ����Ƿ��㹻
		if (goodsService.getPropNum(vo.getPPk(), vo.getSWuping()) < vo.getSWpNumber()) { 
			hint = roleInfoBypPk.getBasicInfo().getName() + "ȡ��������������";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		long money = roleInfo.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1 * 100;
		long pPkmoney = money - money_bak;
		if (pPkmoney < 0)
		{
			hint = "��û���㹻��Ǯ";
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
		}
		else
		{
			//if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), vo.getSWpNumber())) {// ����
				
				// ͨ������propID�жϵ����Ƿ��а�����
				// ������ɺ��޸İ������߰�Ϊʰȡ��
				PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
				GoodsControlVO gcvo = ppgdao.getPropControlgoods(vo.getSWuping());
				if (gcvo.getBonding() == BondingType.EXCHANGEBOND)
				{
					PropVO PropVO = PropCache.getPropById(vo.getSWuping());
					// ȡ���õ��ߵ������ֽ�ת��Ϊ��һ������
					// ������
					hint = taskService.getGeiDJService(roleInfo.getBasicInfo().getPPk(), PropVO.getPropOperate1(), GoodsType.PROP, vo.getSWpNumber()+ "");
					if(hint != null){
						hint = "��û���㹻�İ����ռ�";
						// ȷ�����׺�ɾ��
						SellInfoDAO dao = new SellInfoDAO();
						dao.deleteSelleInfo(sPk);
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					// ��������
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// ȷ�����׺�ɾ��
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					hint = "���ѻ����"+ roleInfoBypPk.getBasicInfo().getName() +"������"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
				else
				{
					// ������ 
					hint = taskService.getGeiDJService(roleInfo.getBasicInfo().getPPk(), vo.getSWuping()+ "", GoodsType.PROP, vo.getSWpNumber() + "");
					if(hint != null){
						hint = "��û���㹻�İ����ռ�";
						// ȷ�����׺�ɾ��
						SellInfoDAO dao = new SellInfoDAO();
						dao.deleteSelleInfo(sPk);
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					
					// �������� 
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// ȷ�����׺�ɾ��
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					
					hint = "���ѻ����"+ roleInfoBypPk.getBasicInfo().getName() +"������"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
		}
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.insertSellInfoRecord(vo.getPPk(), vo.getPByPk(), vo.getSWpType(), vo.getSWuping(), vo.getSWpNumber(), vo.getSWpSilverMoney());
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * ���߽���
	 */
	public ActionForward n17(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer
				.parseInt(sPk));
		// ȷ�����׺�ɾ��
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk);
		 
		String hint = "��ȡ������" + daos.getPartName(vo.getPPk() + "") + "�Ľ���";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"���ź����Է��ܾ����������ͣ�");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
}
