/**
 * 
 */
package com.pm.action.auctionpet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.player.RoleService;
import com.pm.constant.AuctionNumber;
import com.pm.service.auctionpet.AuctionPetService;

/**
 * @author Administrator
 *
 */
public class AuctionSellAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");
	
	//列出角色身上的宠物列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
//		UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
//		int pPk = userTempBean.getPPk();
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		//int menuId = Integer.valueOf((String) request.getAttribute("menu_id"));

		
		//从角色身上取得宠物列表
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		List<PetInfoVO> pet_list = petInfoDAO.getPetInfoList(pPk+"");
		
		logger.info("身上宠物数量"+pet_list.size());
		request.setAttribute("pet_list",pet_list);

		return mapping.findForward("pet_list");
	}
	
	// 查看宠物详细信息
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{    
		String petId = request.getParameter("petId");
		String petPk = request.getParameter("petPk");
		request.setAttribute("petId", petId);
		request.setAttribute("petPk", petPk);
		return mapping.findForward("petinfoview");

	}
	
	//执行拍卖操作
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{	
		
		String petPk = request.getParameter("petPk");
		
		
//		UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
//		int pPk = userTempBean.getPPk();
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		AuctionPetService auctionPetService = new AuctionPetService();
		
		
		
		String resultWml = "";
		// 检测银两输入是否正确
		String pet_silver = request.getParameter("pet_silver");
		if(pet_silver == null || pet_silver.equals("")){
			pet_silver = "0";
		} else {
			boolean flag = StringUtil.isNumber(pet_silver);
			if (!flag) {
				// 数量的格式不正确
				resultWml = "数量的格式不正确!";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("pet_display");	
			}
		}
		// 检测铜钱输入是否正确
		String pet_copper = request.getParameter("pet_copper");
		if(pet_copper == null || pet_copper.equals("")){
			pet_copper = "0";
		} else {
			boolean flag = StringUtil.isNumber(pet_copper);
			if (!flag) {
				// 数量的格式不正确
				resultWml = "数量的格式不正确!";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("pet_display");	
			}
		}

		
		logger.info("玩家输入的银:"+pet_silver+", 玩家输入的文:"+pet_copper+", petPk="+petPk);
				
		
		int input_money = 0;	
		try{
			input_money = (int)MoneyUtil.changeSilverAndCopperToCopper(Integer.valueOf(pet_silver), Integer.valueOf(pet_copper));
			int taxNum  = (int) (input_money*(1 - AuctionNumber.AUCTIONNUMBER));
			long nowMoneyNum = roleInfo.getBasicInfo().getCopper();
			if ( nowMoneyNum < taxNum) {	
				// 数量的格式不正确
				resultWml = "您身上的金钱不够此次的税负!";
				request.setAttribute("resultWml", resultWml);
				return mapping.findForward("pet_display");					
			}
		
		
		}catch (NumberFormatException e){
			// 数量的格式不正确
			resultWml = "正确输入物品拍卖价格";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("pet_display");
		}
			
		resultWml = auctionPetService.auctionPet(pPk,petPk,input_money);
		
		
		request.setAttribute("resultWml",resultWml);
		return mapping.findForward("pet_display");
	}
}
