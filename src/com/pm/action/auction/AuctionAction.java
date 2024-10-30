package com.pm.action.auction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Auctiontype;
import com.ls.pub.constant.Wrap;
import com.ls.pub.util.StringUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.player.EconomyService;
import com.pm.constant.AuctionNumber;
import com.pm.service.auction.AuctionService;

public class AuctionAction extends ActionBase{

	Logger logger =  Logger.getLogger("log.action");
	
	// �����б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int w_type=Auctiontype.PROP;
		RoleEntity  roleInfo = this.getRoleEntity(request);
		int pPk = roleInfo.getBasicInfo().getPPk();
		EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao(roleInfo.getBasicInfo().getUPk());
		request.setAttribute("yuanbao", "" + yuanbao);

		GoodsService goodsSerivce = new GoodsService();
		if (w_type == Auctiontype.PROP)
		{
			List cure_list = goodsSerivce.getPlayerPropList(pPk, 0);
			request.setAttribute("backType", w_type+"");
			request.setAttribute("cure_list", cure_list);
			return mapping.findForward("cure_list");
		}
		return null;
	}
	//װ���б�
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
	    String table_type=request.getParameter("table_type");
	    request.getSession().setAttribute("table_type", table_type);
	    String page_no=request.getParameter("page_no");
	    GoodsService gs=new GoodsService();
	    if(page_no==null||page_no.equals("null"))
	    {
	    	page_no="1";
	    }
	    QueryPage queryPage=gs.getPageEquipOnWrap(roleInfo.getPPk(), Integer.parseInt(table_type), Integer.parseInt(page_no));
	    queryPage.setURL(response, "/menu/auction.do?cmd=n5&amp;w_type="+table_type);
		request.setAttribute("queryPage",queryPage);
		request.setAttribute("table_type", table_type);
		return mapping.findForward("equip_list");
	}
	
	// ��������	
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		/*****1��ʯ����2�ɾ�����********************/
		String pay_type=(String)request.getSession().getAttribute("pay_type");
		/*****************************************/
		String backType=(String)request.getParameter("table_type");
		String pageNum=(String)request.getParameter("pageNo");
		logger.info("leihaoyi"+pay_type);
		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		//���������
		if( roleInfo.getIsRookie())
		{
			this.setHint(request, "�����ڴ�����������״̬,�޷�����");
			return mapping.findForward("return_hint");
		}
		
		int pPk = roleInfo.getBasicInfo().getPPk();
		EconomyService es=new EconomyService();
		long money= es.getYuanbao(roleInfo.getUPk());
		long copper=roleInfo.getBasicInfo().getCopper();
		
		//װ�����ͣ�1��ҩƷ��2���飬3��װ����4������5������
		String w_type = request.getParameter("w_type");
		request.setAttribute("w_type",w_type);

		String resultWml = null;
		
		GoodsService goodsSerivce = new GoodsService();
		AuctionService auctionService = new AuctionService();
		
		//�鿴�Ƿ�Ϊ����������Ʒ�����Ϊ����������Ʒ��֪ͨ��Ҳ���.
		
		String pwPk = request.getParameter("pwPk");
		String pg_pk = request.getParameter("pg_pk");
		
		// ���һ�ڼ�����ʱ����ȷ
		String prop_silver = request.getParameter("prop_silver");
		if(prop_silver == null || prop_silver.equals("")){
			prop_silver = "0";
		} else {
			boolean flag = StringUtil.isNumber(prop_silver);
			if (!flag) {
				resultWml = "����ȷ����һ�ڼۼ۸�!";
				request.setAttribute("w_type", w_type);
				request.setAttribute("resultWml", resultWml);
				if (Integer.parseInt(w_type) == Wrap.EQUIP) {
    				request.setAttribute("pwPk", pwPk);
    				return mapping.findForward("reput_zb_price");	
				} else {
					request.setAttribute("pg_pk", pg_pk);
    				return mapping.findForward("reput_prop_numPrice");	
				}
			}
		}
		// ��⾺�ļ������Ƿ���ȷ
		String prop_copper = request.getParameter("prop_copper");
		if(prop_copper == null || prop_copper.equals("")){
			prop_copper = "0";
		} else {
			boolean flag = StringUtil.isNumber(prop_copper);
			if (!flag) {
				resultWml = "����ȷ���뾺�ļ۸�!";
				request.setAttribute("w_type", w_type);
				request.setAttribute("resultWml", resultWml);
				if (Integer.parseInt(w_type) == Wrap.EQUIP) {
    				request.setAttribute("pwPk", pwPk);
    				return mapping.findForward("reput_zb_price");	
				} else {
					request.setAttribute("pg_pk", pg_pk);
    				return mapping.findForward("reput_prop_price");	
				}
			}
		}
		
		int propPrice = Integer.parseInt(prop_silver);
		int propAuctionPrice=Integer.parseInt(prop_copper);
		if(propPrice<=propAuctionPrice)
		{
			resultWml = "��������������,һ�ڼ۱�����ھ��ļ�!";
			request.setAttribute("w_type", w_type);
			request.setAttribute("resultWml", resultWml);
			if (Integer.parseInt(w_type) == Wrap.EQUIP) {
				request.setAttribute("pwPk", pwPk);
				return mapping.findForward("reput_zb_price");	
			} else {
				request.setAttribute("pg_pk", pg_pk);
				return mapping.findForward("reput_prop_price");	
			}
		}
		/******�ɾ����������������轻100�ɾ�*******/
		if(pay_type=="2")
		{
			if(money<100)
			{
				resultWml = "�����ϵ��ɾ������˴ε�˰��!";
				if(Integer.parseInt(w_type) == Wrap.EQUIP)
				{
					request.setAttribute("w_type", w_type);
					request.setAttribute("pwPk", pwPk);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("table_type", backType);
					request.setAttribute("pageNo", pageNum);
					return mapping.findForward("reput_zb_price");
				}
				else
				{
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("resultWml", resultWml);
					return mapping.findForward("reput_prop_price");
				}
				
			}
		}
		// ����װ��
		if (Integer.parseInt(w_type) == Wrap.EQUIP)
		{
			try{	
				int auctionPrice=Integer.parseInt(prop_copper);
				if ( propPrice <= 0||auctionPrice<=0) {		
					// �����ĸ�ʽ����ȷ	
					resultWml = "��Ʒ�۸���ļ۸񲻵�Ϊ��!";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pwPk", pwPk);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("table_type", backType);
					request.setAttribute("pageNo", pageNum);
					return mapping.findForward("reput_zb_price");					
				}
				logger.info("action �е�w_type : "+w_type+"copper : "+propPrice);
				resultWml = auctionService.removeAccouters(roleInfo.getBasicInfo().getUPk(),pPk,
						Integer.parseInt(pwPk),propPrice,Integer.parseInt(pay_type),Integer.parseInt(prop_copper));
			}catch (NumberFormatException e){
					// �����ĸ�ʽ����ȷ
					resultWml = "��ȷ������Ʒ�����۸�";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pwPk", pwPk);
					request.setAttribute("resultWml", resultWml);
					request.setAttribute("table_type", backType);
					request.setAttribute("pageNo", pageNum);
					return mapping.findForward("reput_zb_price");
			}
		} else
		{
			//�ǵ��ߵ����
			
			
			/* ��� */
			PlayerPropGroupVO goodsGroup = goodsSerivce.getGoodsGroupByPgPk(Integer.parseInt(pg_pk));
			
			String prop_num_str = request.getParameter("prop_num");
			logger.info("AuctionAction �е� pg_pk ֵΪ: "+pg_pk+", prop_num_str��ֵΪ��:��"+prop_num_str);
			int prop_num;
			try {
					prop_num = Integer.parseInt(prop_num_str);
					int auctionPrice=Integer.parseInt(prop_copper);
					if ( propPrice <= 0 || auctionPrice<=0) {		
						// �����ĸ�ʽ����ȷ	
						resultWml = "��Ʒ�۸���ļ۸񲻵�Ϊ��!";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("reput_prop_price");					
					}
					logger.info("action �е�w_type : "+w_type+"copper : "+propPrice);
					
					if(prop_num <= 0) {
						// ����Ϊ��
						resultWml = "�Բ���������Ʒ����Ҫ��һ����";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						
						return mapping.findForward("reput_prop_price");
					}
					if(prop_num < 0) {
						// ����Ϊ��
						resultWml = "�Բ�����������ȷ���֣�";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						return mapping.findForward("reput_prop_price");
					}
					if (goodsGroup.getPropNum() >= prop_num) {
							resultWml = auctionService.addPropToAuction(Integer.parseInt(pg_pk),prop_num,roleInfo,propPrice,Integer.parseInt(pay_type),Integer.parseInt(prop_copper));
					} else
					{
						// ��������
						resultWml = "�Բ��𣬸���Ʒ����������";
						request.setAttribute("w_type", w_type);
						request.setAttribute("pg_pk", pg_pk);
						request.setAttribute("resultWml", resultWml);
						
						return mapping.findForward("reput_prop_price");
					}
				} catch (NumberFormatException e)
				{
					// �����ĸ�ʽ����ȷ
					resultWml = "��ȷ������Ʒ�����������۸�";
					request.setAttribute("w_type", w_type);
					request.setAttribute("pg_pk", pg_pk);
					request.setAttribute("resultWml", resultWml);
					
					return mapping.findForward("reput_prop_price");
				}
			}
		
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("auction_hint");
	}
	
	// ����ʱ����������Ʒ����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RoleEntity  roleInfo = this.getRoleEntity(request);
		
		String w_type = request.getParameter("w_type");
		String pg_pk = request.getParameter("pg_pk");
		String goods_id = request.getParameter("goods_id");


		String prop_display = null;
		GoodsService goodsService = new GoodsService();
		
		prop_display = goodsService.getPropInfoWml(roleInfo.getPPk(),Integer.parseInt(goods_id),pg_pk);
		
		request.setAttribute("w_type", w_type);
		request.setAttribute("pg_pk", pg_pk);
		request.setAttribute("goods_id", goods_id);
		request.setAttribute("resultWml", prop_display);
		return mapping.findForward("prop_display");
	}
	
	/**
	 * ����ʱ����װ����Ʒ����
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();

		RoleEntity roleInfo = this.getRoleEntity(request);

		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = (PlayerEquipVO) goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip, true);

		request.setAttribute("equip_display", equip_display);
		request.setAttribute("equip", equip);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("table_type",request.getParameter("table_type"));
		
		return mapping.findForward("equip_display");
	}
}
