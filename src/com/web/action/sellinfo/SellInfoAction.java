package com.web.action.sellinfo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.petsell.PetSellVO;
import com.ben.vo.sellinfo.SellInfoVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.GoodsControlVO;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.BondingType;
import com.ls.pub.constant.GoodsType;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.TaskService;

/**
 * @author Administrator ����
 */
public class SellInfoAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 * ��Ǯ����
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		request.setAttribute("vo", vo);
		return mapping.findForward("sellmoeypage");
	}

	/**
	 * ��Ǯ����
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		String systemInfo="";
		if(vo == null){
			String hint = "�Է�ȡ���˽���"; 
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
				systemInfo="����ʧ�ܣ��Է����ǿɽ���״̬��";
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// �޸ĶԷ����Ǯ
		String hint = null;
		
		long money = roleInfoBypPk.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1;
		long pByPkmoney = money - money_bak;
		if(pByPkmoney < 0){
			hint = roleInfoBypPk.getBasicInfo().getName() + "ȡ���������Ľ���";
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk); 
		}else{
			roleInfoBypPk.getBasicInfo().addCopper(-(money_bak));//���ٽ�Ǯ
			roleInfo.getBasicInfo().addCopper(money_bak);//���ӽ�Ǯ 
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			hint = "���ѻ����" + roleInfoBypPk.getBasicInfo().getName() + "������" + (long)(vo.getSWpSilverMoney()) + GameConfig.getMoneyUnitName()+ "��";
			systemInfo="��ϲ�����׳ɹ���";
		}
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(roleInfoBypPk.getPPk(),systemInfo);
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * ��Ǯ����
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		// ȷ�����׺�ɾ��
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk); 
		String hint = "��ȡ������" + daos.getPartName(vo.getPPk() + "") + "�Ľ���";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"���ź����Է��ܾ������Ľ�������!");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * װ������
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		String hint = null;
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		PartInfoDAO daos = new PartInfoDAO();
		if(playerEquipDao.isHaveById(vo.getSWuping()) == false){
			hint = daos.getPartName(vo.getPPk()+"") + "ȡ���������Ľ���";
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
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		GoodsService goodsService = new GoodsService();
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		String sysotemInfo="";
		if(vo == null){
			String hint = "�Է�ȡ���˽���"; 
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
				sysotemInfo="����ʧ�ܣ��Է����ɽ��ף�";
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
		if( equip.isTraded()==false || equip.isOwnByPPk(vo.getPPk())!=null){
			hint = roleInfoBypPk.getBasicInfo().getName() + "ȡ���������Ľ���";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		long money = roleInfo.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1;
		long pPkmoney = money - money_bak;
		if (pPkmoney < 0)
		{
			hint = "��û���㹻��Ǯ";
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			sysotemInfo="����ʧ�ܣ��Է�û���㹻��Ǯ";
		}
		else
		{
			if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), 1))
			{// ����
				
				roleInfo.getBasicInfo().addCopper(- (money_bak));
				// ���ٽ��׽������Ǯ 
				
				// ���������������Ǯ 
				roleInfoBypPk.getBasicInfo().addCopper((money_bak));

				//����װ��������
				playerEquipDao.updateOwner(vo.getSWuping(), roleInfo.getBasicInfo().getPPk());
				// ������Ұ���ʣ��ռ�����
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(roleInfo.getBasicInfo().getPPk(), -1);
				equipService.addWrapSpare(vo.getPPk(), 1);
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				hint = "���ѻ����" + roleInfoBypPk.getBasicInfo().getName() + "������" + equip.getFullName() + "";
				sysotemInfo="��ϲ�����Ľ��׳ɹ���";
			}
			else
			{
				hint = "��û���㹻�İ����ռ�";
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				sysotemInfo="����ʧ�ܣ��Է������ռ䲻�㣡";
			}
		}
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(roleInfoBypPk.getPPk(),sysotemInfo);
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.insertSellInfoRecord(vo.getPPk(), vo.getPByPk(), vo.getSWpType(), vo.getSWuping(), vo.getSWpNumber(), vo.getSWpSilverMoney());
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * װ������
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
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
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"���ź����Է�ȡ���˺����Ľ��ף�");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * ���߽���
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		/*String hint = null;
		PartWrapDAO equipDao = new PartWrapDAO();
		PartInfoDAO daos = new PartInfoDAO();
		if(equipDao.partequip(vo.getSWuping()) == false){
			hint = daos.getPartName(vo.getPPk()+"") + "ȡ���������Ľ���";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.getSelleInfoDeMon(sPk);
			return mapping.findForward("sellmoeyok");
		}*/
		request.setAttribute("vo", vo);
		return mapping.findForward("sellproppage");
	}

	/**
	 * ���߽���
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String sPk = request.getParameter("sPk");
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		GoodsService goodsService = new GoodsService();
		TaskService taskService = new TaskService();
		String systemInfo="";
		if(vo == null){
			String hint = "�Է�ȡ���˽���"; 
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
				systemInfo="����ʧ�ܣ��Է����ڲ��ɽ���״̬!";
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
			hint = roleInfoBypPk.getBasicInfo().getName() + "ȡ���������Ľ���";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		long money = roleInfo.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1;
		long pPkmoney = money - money_bak;
		if (pPkmoney < 0)
		{
			hint = "��û���㹻��Ǯ";
			// ȷ�����׺�ɾ��
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			systemInfo="����ʧ�ܣ��Է�û���㹻��Ǯ��";
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
						systemInfo="����ʧ�ܣ��Է������ռ䲻�㣡";
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					// ��������
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// ȷ�����׺�ɾ��
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					
					roleInfo.getBasicInfo().addCopper(- (money_bak));//��ȥ��Ǯ
					roleInfoBypPk.getBasicInfo().addCopper((money_bak));//���ӽ�Ǯ
					systemInfo="��ϲ�������Ľ����Ѿ��ɹ���";
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
						systemInfo="����ʧ�ܣ��Է������ռ䲻�㣡";
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					
					// �������� 
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// ȷ�����׺�ɾ��
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					
					roleInfo.getBasicInfo().addCopper(- (money_bak));//��ȥ��Ǯ
					roleInfoBypPk.getBasicInfo().addCopper((money_bak));//���ӽ�Ǯ
					systemInfo="��ϲ�������Ľ����Ѿ��ɹ���";
					hint = "���ѻ����"+ roleInfoBypPk.getBasicInfo().getName() +"������"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
			/*}else{
				hint = "��û���㹻�İ����ռ�";
				// ȷ�����׺�ɾ��
				SellInfoDAO dao = new SellInfoDAO();
				dao.getSelleInfoDeMon(sPk);
			}*/
		}
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(roleInfoBypPk.getPPk(),systemInfo);
		GameSystemStatisticsService gs = new GameSystemStatisticsService();
		gs.insertSellInfoRecord(vo.getPPk(), vo.getPByPk(), vo.getSWpType(), vo.getSWuping(), vo.getSWpNumber(), vo.getSWpSilverMoney());
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * ���߽���
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
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
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"���ź����Է��ܾ������Ľ�������");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	
	/**
	 * ���ｻ��
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ps_pk = request.getParameter("ps_pk");
		PetInfoDAO petInfoDAO = new PetInfoDAO(); 
		PetSellVO vo = (PetSellVO)petInfoDAO.getPetSellView(Integer.parseInt(ps_pk));
		PartInfoDAO daos = new PartInfoDAO();
		//�ж����󷽸ó����Ƿ�������
		String hint = null;
		if(petInfoDAO.isPetNot(vo.getPetId(), vo.getPPk()) == false){
			hint = daos.getPartName(vo.getPPk()+"") + "ȡ���������Ľ���";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			petInfoDAO.getPetSellDelete(ps_pk);
			return mapping.findForward("sellmoeyok");
		}
		request.setAttribute("vo", vo);
		request.setAttribute("ps_pk", ps_pk);
		return mapping.findForward("sellpetpage");
	}
	/**
	 * ���ｻ��
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession()); 
		String ps_pk = request.getParameter("ps_pk");
		PetInfoDAO petInfoDAO = new PetInfoDAO(); 
		PetSellVO vo = (PetSellVO)petInfoDAO.getPetSellView(Integer.parseInt(ps_pk));
		 
		if(vo == null){
			String hint = "�Է�ȡ���˽���"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//�ж����B��״̬�Ƿ���Խ��н���
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		
		String hint = null;
		//Ǯ�Ƿ��㹻
		long pPkmoney = (long)roleInfo.getBasicInfo().getCopper() - ((long)(vo.getPsSilverMoney()) * 100 + (long)vo.getPsCopperMoney());
		if (pPkmoney < 0)
		{
			hint = "��û���㹻��Ǯ";
			petInfoDAO.getPetSellDelete(ps_pk);
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		//�жϸó����Ƿ��ս ����μ� ���ý���
		int isBring = petInfoDAO.isBring(vo.getPetId());
		if(isBring == 1){
			hint = "�Է�ȡ���������ĳ��ｻ��";
			petInfoDAO.getPetSellDelete(ps_pk);
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		//�ж����󷽸ó����Ƿ�������
		if(petInfoDAO.isPetNot(vo.getPetId(), vo.getPPk()) == false){
			hint = roleInfoBypPk.getBasicInfo().getName() + "ȡ���������Ľ���";
			request.setAttribute("hint", hint);
			// ȷ�����׺�ɾ��
			petInfoDAO.getPetSellDelete(ps_pk);
			return mapping.findForward("sellmoeyok");
		}
		//�жϳ����Ƿ��п�����
		List list = petInfoDAO.getPetInfoList(roleInfo.getBasicInfo().getPPk()+"");
		if(list.size() >= 6){
			hint = "��������Я�����������";
			petInfoDAO.getPetSellDelete(ps_pk);
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		//һ��û������ ɾ�����ܷ���Ǯ
		roleInfo.getBasicInfo().addCopper(- (vo.getPsSilverMoney() * 100 + vo.getPsCopperMoney()));
		//ȡ�����ͷ���Ǯ�޸ĵ�
		roleInfoBypPk.getBasicInfo().addCopper((vo.getPsSilverMoney() * 100 + vo.getPsCopperMoney()));
		//������޸ĳ������� 
		PetInfoDAO dd = new PetInfoDAO();  
		String petName = petInfoDAO.pet_name(vo.getPetId());
		dd.getPetzhuren(vo.getPByPk(),vo.getPetId(),petName); 
		// ȷ�����׺�ɾ��
		petInfoDAO.getPetSellDelete(ps_pk);
		hint = "���ѻ����"+roleInfoBypPk.getBasicInfo().getName()+"�����ĳ���";
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	
	/**
	 * ���ｻ��
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ps_pk = request.getParameter("ps_pk");
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		PetSellVO vo = (PetSellVO)petInfoDAO.getPetSellView(Integer.parseInt(ps_pk));
		PartInfoDAO daos = new PartInfoDAO();
		petInfoDAO.getPetSellDelete(ps_pk);
		String hint = "��ȡ������" + daos.getPartName(vo.getPPk() + "") + "�Ľ���";
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	
	/**
	 * װ�����ײ鿴
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		GoodsService goodsService = new GoodsService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		 
		String pwPk = request.getParameter("pwPk");
		
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(roleInfo, equip, true);
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("sPk", request.getParameter("sPk"));
		return mapping.findForward("sellaccviewpage");
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
	/**���ｻ�ײ鿴*/
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String petPk = request.getParameter("pet_pk");
		String ps_pk = request.getParameter("ps_pk");
		PetService petService = new PetService(); 
		String resultWml = petService.getPetDisplayWml(Integer.parseInt(petPk));
		request.setAttribute("ps_pk", ps_pk);
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("sellpetviewpage");
	}
	
}
