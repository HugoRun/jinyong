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
 * @author Administrator 交易
 */
public class SellInfoAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 * 金钱交易
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
	 * 金钱交易
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
			String hint = "对方取消了交易"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//判断玩家B的状态是否可以进行交易
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){ 
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				systemInfo="交易失败，对方不是可交易状态！";
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// 修改对方玩家钱
		String hint = null;
		
		long money = roleInfoBypPk.getBasicInfo().getCopper();
		long money_bak_1 = vo.getSWpSilverMoney();
		long money_bak = money_bak_1;
		long pByPkmoney = money - money_bak;
		if(pByPkmoney < 0){
			hint = roleInfoBypPk.getBasicInfo().getName() + "取消了与您的交易";
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk); 
		}else{
			roleInfoBypPk.getBasicInfo().addCopper(-(money_bak));//减少金钱
			roleInfo.getBasicInfo().addCopper(money_bak);//增加金钱 
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			hint = "您已获得由" + roleInfoBypPk.getBasicInfo().getName() + "给您的" + (long)(vo.getSWpSilverMoney()) + GameConfig.getMoneyUnitName()+ "！";
			systemInfo="恭喜您交易成功！";
		}
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(roleInfoBypPk.getPPk(),systemInfo);
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * 金钱交易
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer.parseInt(sPk));
		// 确定交易后删除
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk); 
		String hint = "您取消了与" + daos.getPartName(vo.getPPk() + "") + "的交易";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"很遗憾！对方拒绝了您的交易请求!");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * 装备交易
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
			hint = daos.getPartName(vo.getPPk()+"") + "取消了与您的交易";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			return mapping.findForward("sellmoeyok");
		}
		request.setAttribute("vo", vo);
		return mapping.findForward("sellarmpage");
	}

	/**
	 * 装备交易
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
			String hint = "对方取消了交易"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//判断玩家B的状态是否可以进行交易
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				sysotemInfo="交易失败，对方不可交易！";
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// 判断钱
		// 修改钱
		String hint = null;
		//判断申请交易玩家装备是否还在包裹
		PlayerEquipVO equip = playerEquipDao.getByID(vo.getSWuping());
		if( equip.isTraded()==false || equip.isOwnByPPk(vo.getPPk())!=null){
			hint = roleInfoBypPk.getBasicInfo().getName() + "取消了与您的交易";
			request.setAttribute("hint", hint);
			// 确定交易后删除
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
			hint = "您没有足够的钱";
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			sysotemInfo="交易失败，对方没有足够的钱";
		}
		else
		{
			if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), 1))
			{// 够了
				
				roleInfo.getBasicInfo().addCopper(- (money_bak));
				// 减少交易接受玩家钱 
				
				// 给请求交易玩家增加钱 
				roleInfoBypPk.getBasicInfo().addCopper((money_bak));

				//更改装备所有人
				playerEquipDao.updateOwner(vo.getSWuping(), roleInfo.getBasicInfo().getPPk());
				// 增加玩家包裹剩余空间数量
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(roleInfo.getBasicInfo().getPPk(), -1);
				equipService.addWrapSpare(vo.getPPk(), 1);
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				hint = "您已获得由" + roleInfoBypPk.getBasicInfo().getName() + "给您的" + equip.getFullName() + "";
				sysotemInfo="恭喜，您的交易成功！";
			}
			else
			{
				hint = "您没有足够的包裹空间";
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				sysotemInfo="交易失败，对方包裹空间不足！";
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
	 * 装备交易
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer
				.parseInt(sPk));
		// 确定交易后删除
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk); 
		
		String hint = "您取消了与" + daos.getPartName(vo.getPPk() + "") + "的交易";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"很遗憾！对方取消了和您的交易！");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}

	/**
	 * 道具交易
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
			hint = daos.getPartName(vo.getPPk()+"") + "取消了与您的交易";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.getSelleInfoDeMon(sPk);
			return mapping.findForward("sellmoeyok");
		}*/
		request.setAttribute("vo", vo);
		return mapping.findForward("sellproppage");
	}

	/**
	 * 道具交易
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
			String hint = "对方取消了交易"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//判断玩家B的状态是否可以进行交易
			PlayerService playerService = new PlayerService();
			String hint = playerService.isRoleState(vo.getPPk(),1);
			if(hint != null ){
				// 确定交易后删除
				SellInfoDAO dao = new SellInfoDAO();
				dao.deleteSelleInfo(sPk);
				systemInfo="交易失败，对方处于不可交易状态!";
				request.getRequestDispatcher("/pubbuckaction.do?hint="+hint+"&chair="+request.getParameter("chair")).forward(request, response);
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		RoleEntity roleInfoBypPk = roleService.getRoleInfoById(vo.getPPk()+"");
		// 判断钱
		// 修改钱
		String hint = null;
		//判断道具是否足够
		if (goodsService.getPropNum(vo.getPPk(), vo.getSWuping()) < vo.getSWpNumber()) { 
			hint = roleInfoBypPk.getBasicInfo().getName() + "取消了与您的交易";
			request.setAttribute("hint", hint);
			// 确定交易后删除
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
			hint = "您没有足够的钱";
			// 确定交易后删除
			SellInfoDAO dao = new SellInfoDAO();
			dao.deleteSelleInfo(sPk);
			systemInfo="交易失败，对方没有足够的钱！";
		}
		else
		{
			//if (goodsService.isEnoughWrapSpace(roleInfo.getBasicInfo().getPPk(), vo.getSWpNumber())) {// 够了
				
				// 通过道具propID判断道具是否有绑定属性
				// 交易完成后修改包裹道具绑定为拾取绑定
				PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
				GoodsControlVO gcvo = ppgdao.getPropControlgoods(vo.getSWuping());
				if (gcvo.getBonding() == BondingType.EXCHANGEBOND)
				{
					PropVO PropVO = PropCache.getPropById(vo.getSWuping());
					// 取出该道具的特殊字节转向为另一个道具
					// 给道具
					hint = taskService.getGeiDJService(roleInfo.getBasicInfo().getPPk(), PropVO.getPropOperate1(), GoodsType.PROP, vo.getSWpNumber()+ "");
					if(hint != null){
						hint = "您没有足够的包裹空间";
						// 确定交易后删除
						SellInfoDAO dao = new SellInfoDAO();
						dao.deleteSelleInfo(sPk);
						systemInfo="交易失败！对方包裹空间不足！";
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					// 消除道具
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// 确定交易后删除
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					
					roleInfo.getBasicInfo().addCopper(- (money_bak));//减去金钱
					roleInfoBypPk.getBasicInfo().addCopper((money_bak));//增加金钱
					systemInfo="恭喜您，您的交易已经成功！";
					hint = "您已获得由"+ roleInfoBypPk.getBasicInfo().getName() +"给您的"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
				else
				{
					// 给道具 
					hint = taskService.getGeiDJService(roleInfo.getBasicInfo().getPPk(), vo.getSWuping()+ "", GoodsType.PROP, vo.getSWpNumber() + "");
					if(hint != null){
						hint = "您没有足够的包裹空间";
						// 确定交易后删除
						SellInfoDAO dao = new SellInfoDAO();
						dao.deleteSelleInfo(sPk);
						systemInfo="交易失败！对方包裹空间不足！";
						request.setAttribute("hint", hint);
						return mapping.findForward("sellmoeyok");
					}
					
					// 消除道具 
					goodsService.removeProps(vo.getPPk(), vo.getSWuping(), vo.getSWpNumber(),GameLogManager.R_TRADE);
					// 确定交易后删除
					SellInfoDAO dao = new SellInfoDAO();
					dao.deleteSelleInfo(sPk);
					
					roleInfo.getBasicInfo().addCopper(- (money_bak));//减去金钱
					roleInfoBypPk.getBasicInfo().addCopper((money_bak));//增加金钱
					systemInfo="恭喜您！您的交易已经成功！";
					hint = "您已获得由"+ roleInfoBypPk.getBasicInfo().getName() +"给您的"+PropCache.getPropNameById(vo.getSWuping())+"";
				}
			/*}else{
				hint = "您没有足够的包裹空间";
				// 确定交易后删除
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
	 * 道具交易
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		String sPk = request.getParameter("sPk");
		PartInfoDAO daos = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(Integer
				.parseInt(sPk));
		// 确定交易后删除
		SellInfoDAO dao = new SellInfoDAO();
		dao.deleteSelleInfo(sPk);
		 
		String hint = "您取消了与" + daos.getPartName(vo.getPPk() + "") + "的交易";
		SystemInfoService sifs=new SystemInfoService();
		sifs.sendSystemInfoByTransaction(vo.getPPk(),"很遗憾！对方拒绝了您的交易请求");
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	
	/**
	 * 宠物交易
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ps_pk = request.getParameter("ps_pk");
		PetInfoDAO petInfoDAO = new PetInfoDAO(); 
		PetSellVO vo = (PetSellVO)petInfoDAO.getPetSellView(Integer.parseInt(ps_pk));
		PartInfoDAO daos = new PartInfoDAO();
		//判断请求方该宠物是否还在身上
		String hint = null;
		if(petInfoDAO.isPetNot(vo.getPetId(), vo.getPPk()) == false){
			hint = daos.getPartName(vo.getPPk()+"") + "取消了与您的交易";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			petInfoDAO.getPetSellDelete(ps_pk);
			return mapping.findForward("sellmoeyok");
		}
		request.setAttribute("vo", vo);
		request.setAttribute("ps_pk", ps_pk);
		return mapping.findForward("sellpetpage");
	}
	/**
	 * 宠物交易
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
			String hint = "对方取消了交易"; 
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		try{
			//判断玩家B的状态是否可以进行交易
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
		//钱是否足够
		long pPkmoney = (long)roleInfo.getBasicInfo().getCopper() - ((long)(vo.getPsSilverMoney()) * 100 + (long)vo.getPsCopperMoney());
		if (pPkmoney < 0)
		{
			hint = "您没有足够的钱";
			petInfoDAO.getPetSellDelete(ps_pk);
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		//判断该宠物是否参战 如果参加 不让交易
		int isBring = petInfoDAO.isBring(vo.getPetId());
		if(isBring == 1){
			hint = "对方取消了与您的宠物交易";
			petInfoDAO.getPetSellDelete(ps_pk);
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		//判断请求方该宠物是否还在身上
		if(petInfoDAO.isPetNot(vo.getPetId(), vo.getPPk()) == false){
			hint = roleInfoBypPk.getBasicInfo().getName() + "取消了与您的交易";
			request.setAttribute("hint", hint);
			// 确定交易后删除
			petInfoDAO.getPetSellDelete(ps_pk);
			return mapping.findForward("sellmoeyok");
		}
		//判断宠物是否有空余存放
		List list = petInfoDAO.getPetInfoList(roleInfo.getBasicInfo().getPPk()+"");
		if(list.size() >= 6){
			hint = "您不能在携带更多宠物了";
			petInfoDAO.getPetSellDelete(ps_pk);
			request.setAttribute("hint", hint);
			return mapping.findForward("sellmoeyok");
		}
		//一切没有问题 删除接受方的钱
		roleInfo.getBasicInfo().addCopper(- (vo.getPsSilverMoney() * 100 + vo.getPsCopperMoney()));
		//取出发送方的钱修改掉
		roleInfoBypPk.getBasicInfo().addCopper((vo.getPsSilverMoney() * 100 + vo.getPsCopperMoney()));
		//这个是修改宠物主人 
		PetInfoDAO dd = new PetInfoDAO();  
		String petName = petInfoDAO.pet_name(vo.getPetId());
		dd.getPetzhuren(vo.getPByPk(),vo.getPetId(),petName); 
		// 确定交易后删除
		petInfoDAO.getPetSellDelete(ps_pk);
		hint = "您已获得由"+roleInfoBypPk.getBasicInfo().getName()+"给您的宠物";
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	
	/**
	 * 宠物交易
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String ps_pk = request.getParameter("ps_pk");
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		PetSellVO vo = (PetSellVO)petInfoDAO.getPetSellView(Integer.parseInt(ps_pk));
		PartInfoDAO daos = new PartInfoDAO();
		petInfoDAO.getPetSellDelete(ps_pk);
		String hint = "您取消了与" + daos.getPartName(vo.getPPk() + "") + "的交易";
		request.setAttribute("hint", hint);
		return mapping.findForward("sellmoeyok");
	}
	
	/**
	 * 装备交易查看
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
	 * 道具交易查看 
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
	/**宠物交易查看*/
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
