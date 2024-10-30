package com.ls.web.action.menu.storage;

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
import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.storage.StorageService;

public class AddPetStorageAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	// 身上宠物列表
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		WareHouseDao wareHouse = new WareHouseDao();

		StorageService storageService = new StorageService();

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		int pPk = roleInfo.getBasicInfo().getPPk();
		int uPk = roleInfo.getBasicInfo().getUPk();

		request.setAttribute("pPk", pPk + "");
		request.setAttribute("uPk", uPk + "");

		/** 查询数据库中该角色有没有宠物仓库 */
		int warehouseID = wareHouse.getWareHouseIdBypPk(uPk, pPk, 7);

		// 如果仓库表里没有该type的记录就插入该type记录
		if (warehouseID == 0)
		{
			warehouseID = wareHouse.insertWareHouse(uPk, pPk, 7);
		}

		// 获得仓库中宠物已储存数量
		int petStorageNumber = storageService.getPetNumber(pPk);
		request.setAttribute("petStorageNumber", petStorageNumber + "");
		logger.info("仓库宠物数量" + petStorageNumber);

		// 从角色身上取得宠物列表
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		List<PetInfoVO> pet_list = petInfoDAO.getPetInfoList(pPk + "");

		logger.info("身上宠物数量" + pet_list.size());

		request.setAttribute("pet_list", pet_list);
		request.setAttribute("warehouseID", warehouseID);
		return mapping.findForward("pet_list");
	}

	// 储存宠物
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		StorageService storageService = new StorageService();
		String petPk = request.getParameter("petId");
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		if (petPk == null || pPk == null)
		{
			return mapping.findForward("findnot");
		}

		// 将宠物从人物身上拿下
		String putSussend = storageService.putPetFromPerson(Integer
				.valueOf(pPk), Integer.valueOf(petPk));
		// 将宠物加入仓库
		// String addSussend =
		// storageService.addPetToWare(Integer.valueOf(pPk),Integer.valueOf(petPk));
		// if(addSussend.equals("-1")){
		// request.setAttribute("resultWml",addSussend);
		// return mapping.findForward("findnot");
		// }
		if (!putSussend.equals("1"))
		{
			request.setAttribute("resultWml", putSussend);
			return mapping.findForward("findnot");
		}
		request.setAttribute("pPk", pPk + "");
		return mapping.findForward("sussend");
	}

	/** 查看宠物信息 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String petPk = request.getParameter("petPk");
		PetService petSerivce = new PetService();
		String resultWml = petSerivce.getPetDisplayWml(Integer.parseInt(petPk));
		request.setAttribute("resultWml", resultWml);
		request.setAttribute("type", 1);
		return mapping.findForward("sussend");
	}
}
