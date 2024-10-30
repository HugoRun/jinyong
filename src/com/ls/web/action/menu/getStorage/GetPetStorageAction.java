package com.ls.web.action.menu.getStorage;

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
import com.ls.pub.constant.Wrap;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.storage.StorageService;

public class GetPetStorageAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	// �ֿ���Ʒ�б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		WareHouseDao wareHouse = new WareHouseDao();


		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		int pPk = roleInfo.getBasicInfo().getPPk();
		int uPk = roleInfo.getBasicInfo().getUPk();

		request.setAttribute("pPk", pPk + "");
		request.setAttribute("id", uPk + "");


		/** ��ѯ���ݿ��иý�ɫ��û�н�Ǯ�ֿ� */
		int warehouseID = wareHouse.getWareHouseIdBypPk(uPk, pPk, Wrap.COPPER);

		// ����ֿ����û�и�type�ļ�¼�Ͳ����type��¼
		if (warehouseID == 0)
		{
			warehouseID = wareHouse.insertWareHouse(uPk, pPk, 7);
		}

		// �Ӳֿ�ȡ�ó����б�
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		List<PetInfoVO> pet_list = petInfoDAO.getPetInfoList(-pPk + "");
		request.setAttribute("pet_list", pet_list);
		return mapping.findForward("pet_list");
	}

	// ȡ������
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		StorageService storageService = new StorageService();
		String petPk = request.getParameter("petId");
		String pPk = (String) request.getSession().getAttribute("pPk");
		if (petPk == null || pPk == null)
		{
			return mapping.findForward("findnot");
		}
		int p_pk = Integer.valueOf(pPk);
		// ������ŵ��˰�����
		String putSussend = storageService.putPetFromWare(p_pk, Integer.valueOf(petPk));
		// ������Ӳֿ�ɾ��
		if (!putSussend.equals("1"))
		{
			request.setAttribute("resultWml", putSussend);
			return mapping.findForward("findnot");
		}
		request.setAttribute("pPk", pPk);
		return mapping.findForward("sussend");
	}

	/** �鿴������Ϣ */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String petPk = request.getParameter("petPk");
		PetService petSerivce = new PetService();
		String resultWml = petSerivce.getPetDisplayWml(Integer.parseInt(petPk));
		request.setAttribute("resultWml", resultWml);
		request.setAttribute("type", 2);
		return mapping.findForward("sussend");
	}
}
