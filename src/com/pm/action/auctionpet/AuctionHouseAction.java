package com.pm.action.auctionpet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.pm.service.auction.AuctionService;
import com.pm.service.auctionpet.AuctionPetService;
import com.pm.vo.auctionpet.AuctionPetInfoVO;
import com.pm.vo.auctionpet.AuctionPetVO;

public class AuctionHouseAction extends DispatchAction{
	
	Logger logger =  Logger.getLogger("log.action");
	
	// ��������Ϣ����
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		
		
		String mores = request.getParameter("moreOrNot");
		logger.info("mores : "+mores);
		
		AuctionPetService auctionPetService = new AuctionPetService();
		
		List<AuctionPetInfoVO> infoList = auctionPetService.getPetInfoList(pPk,mores);
		request.setAttribute("infoList",infoList);
		request.setAttribute("mores",mores);
		request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
		return mapping.findForward("auction_pet_info");
	}
	
	// �������ֿ�
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
//		UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
//		int pPk = userTempBean.getPPk();
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		AuctionPetService auctionPetService = new AuctionPetService();
		
		List<AuctionPetVO> money_list = auctionPetService.getAuctionPetMoneyList(pPk);
		List<AuctionPetVO> goods_list = auctionPetService.getAuctionPetGoodsList(pPk);
		
		request.setAttribute("money_list",money_list);
		request.setAttribute("goods_list",goods_list);
		request.setAttribute("pPk",pPk);
		request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
		
		return mapping.findForward("auction_pet_house");
	}		
		
	// ȡ���������ֿ������Ʒ
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
//		UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
//		String pPk = userTempBean.getPPk()+"";
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		
		request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
		
		AuctionPetService auctionPetService = new AuctionPetService();
		
		String petPk = request.getParameter("petPk");
		String flag = request.getParameter("flag");			//��ȷ��ȡ�ص�����Ʒ��������
		logger.info("petPk="+petPk+", flag="+flag);
		
		if(flag.equals("money")){
			AuctionService auctionSerivce = new AuctionService();
			PartInfoVO partvo = auctionSerivce.getPartInfo(Integer.valueOf(pPk));
			Long copper = Long.valueOf(partvo.getPCopper());
			int petPrice = auctionPetService.getPetInfoPrice(petPk);
			//���ֻ��Я��1000����
			if(copper + petPrice < 1000000000){
				String resultWml = auctionPetService.getPetMoneyBack(Integer.valueOf(pPk),petPk,petPrice);
				request.setAttribute("resultWml",resultWml);
				return mapping.findForward("pet_display");
			}else {
				String resultWml = "��������Я����������ˣ�";
				request.setAttribute("resultWml",resultWml);
				return mapping.findForward("pet_display");
			}
		}else if(flag.equals("goods")){
			WareHouseDao wareDao = new WareHouseDao();
			int petNumber = wareDao.getPetNumber(Integer.valueOf(pPk));
			if(petNumber < 6){
				String resultWml = auctionPetService.getPetBack(Integer.valueOf(pPk),petPk);
				request.setAttribute("resultWml",resultWml);
				return mapping.findForward("pet_display");
				
			}else {
				String resultWml = "��û���㹻�ĳ���ռ��ˣ�";
				request.setAttribute("resultWml",resultWml);
				return mapping.findForward("pet_display");
			}
			
		}else {
			String resultWml = "��ִ���˴�����������Ժ����ԣ�";
			request.setAttribute("resultWml",resultWml);
			return mapping.findForward("pet_display");
		}
	}
}
