/**
 * 
 */
package com.pm.action.auctionpet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.service.player.RoleService;
import com.pm.dao.auctionpet.AuctionPetDao;
import com.pm.service.auctionpet.AuctionPetService;
import com.pm.vo.auctionpet.AuctionPetVO;

/**
 * @author zhangjj
 *
 */
public class AuctionBuyAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");
	
	//列出宠物拍卖场的宠物列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		request.setAttribute("pPk", pPk + "");
		request.setAttribute("uPk", roleInfo.getBasicInfo().getUPk() + "");

		request.setAttribute("mapid", roleInfo.getBasicInfo().getSceneId()+"");
		
		String searchType = request.getParameter("searchType");
		if(searchType == null || searchType.equals("")){
			searchType = "putong";
		}
		
		String page_no_str = request.getParameter("page_no");
		request.setAttribute("searchType",searchType);
		request.setAttribute("page_no",page_no_str);
		
		int page_no = 0;
		if( page_no_str==null ) {
			page_no = 1;
		}else {
			page_no = Integer.parseInt(page_no_str);
		}
		
		AuctionPetService auctionPetService = new AuctionPetService();
		QueryPage pet_page = auctionPetService.getPagePropList(page_no,searchType);
		request.setAttribute("pet_page", pet_page);
		
		return mapping.findForward("auction_pet_list");
	}
	
	// 查看宠物详细信息
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{    
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("searchType", request.getParameter("searchType"));
		String petId = request.getParameter("petId");
		String petPk = request.getParameter("petPk");
		request.setAttribute("petId", petId);
		request.setAttribute("petPk", petPk);
		return mapping.findForward("petinfoview");
	}
	
	// 执行拍买功能
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{    
		String petPk = request.getParameter("petPk");
		String pet_name = request.getParameter("pet_name");
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
				
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		AuctionPetVO auctionPetVO = auctionPetDao.getPetInfoView(petPk);
		
		AuctionPetService auctionPetService = new AuctionPetService();
		String hasMoney = auctionPetService.auctionHasMoney(partInfoVO,auctionPetVO);
		
		request.setAttribute("pet_name", pet_name);
		if(hasMoney.equals("1")){
			String hasWareSpace = auctionPetService.auctionhasEnoughSpace(pPk+"");
			if(hasWareSpace.equals("1")){
				//执行操作
				String resultWml = auctionPetService.auctionBuyPet(Integer.valueOf(pPk),petPk,auctionPetVO);
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("display");
			}else {												//人物身上没有足够的宠物空间.
				request.setAttribute("resultWml", hasWareSpace);
				return mapping.findForward("display");
			}
		}else {													//人物身上没有足够的金钱.	
			request.setAttribute("resultWml", hasMoney);
			return mapping.findForward("display");
		}
	}
	
	// 特定物品列表
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{	
		String auctionType = request.getParameter("auctionType");
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		String page_no_str = request.getParameter("page_no");
		String sortType = request.getParameter("sortType");
		logger.info("排序分类 : "+sortType);
		
		String pet_name = request.getParameter("pet_name");
		
		int page_no;
		
		if( page_no_str==null )
		{
				page_no = 1;
			}else {
				page_no = Integer.parseInt(page_no_str);
			}
			if(pet_name == null || pet_name.equals("")){
				logger.info("auctionBuyAction中petName : "+pet_name);
				String resultWml = "请您输入正确的物品名";
				request.setAttribute("resultWml", resultWml);
			
			}else {
		
				request.setAttribute("sortType",sortType);
				request.setAttribute("pet_name",pet_name);
		
				AuctionPetService auctionPetService = new AuctionPetService();
		
				QueryPage name_page = auctionPetService.getPagePetByName(pPk,pet_name,page_no,sortType);
				request.setAttribute("name_page",name_page);
				return mapping.findForward("nameList");
			}
		return mapping.findForward("display");
		
		}
	
	// 搜索查看宠物详细信息
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{    
		request.setAttribute("page_no", request.getParameter("page_no"));
		request.setAttribute("pet_name", request.getParameter("pet_name"));
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();		
		request.setAttribute("pPk", pPk+"");
		
		String petId = request.getParameter("petId");
		String petPk = request.getParameter("petPk");
		request.setAttribute("petId", petId);
		request.setAttribute("petPk", petPk);
		return mapping.findForward("petinfoviewsearch");
	}
}
