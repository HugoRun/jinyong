package com.ls.web.service.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.petsell.PetSellVO;
import com.ben.vo.sellinfo.SellInfoVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.system.UMessageInfoDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.group.GroupNotifyVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.group.GroupNotifyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.web.service.popupmsg.PopUpMsgService;

/**
 * @author ls 功能:用户弹出消息逻辑管理 Mar 6, 2009
 */
public class UMsgService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * 清除角色所有消息
	 */
	public void clear(int p_pk)
	{
		UMessageInfoDao msgDao = new UMessageInfoDao();
		msgDao.clear(p_pk);
	}
	
	
	/**
	 * 清除角色同类型所有消息
	 */
	public void clear(int p_pk, int msg_type)
	{
		UMessageInfoDao msgDao = new UMessageInfoDao();
		msgDao.clear(p_pk,msg_type);
	}
	
	/**
	 * 得到弹出消息
	 * 
	 * @param p_pk
	 * @return
	 */
	public UMessageInfoVO getMsg(int p_pk)
	{
		UMessageInfoDao msgDao = new UMessageInfoDao();
		return msgDao.get(p_pk);
	}

	/**
	 * 发送弹出消息
	 */
	public void sendPopUpMsg(UMessageInfoVO msg)
	{
		if (msg == null)
		{
			return;
		}
		UMessageInfoDao msgDao = new UMessageInfoDao();
		msgDao.insert(msg);
	}

	/**
	 * 对消息进行处理
	 * 
	 * @param msg
	 * @param userBean
	 * @return
	 */
	public UMessageInfoVO processMsg(UMessageInfoVO msg, RoleEntity roleInfo,HttpServletRequest request,HttpServletResponse response)
	{
		if (msg == null || roleInfo == null)
		{
			return null;
		}
		UMessageInfoVO result = null;

		UMessageInfoDao msgDao = new UMessageInfoDao();

		switch (msg.getMsgType())
		{
			case PopUpMsgType.INSTANCE:
				result = processInstanceMsg(msg, roleInfo);// 返回副本消息内容
				break; 
			case PopUpMsgType.MESSAGE_SWAP:
				result = processSwapMsg(msg, roleInfo,request,response);// 返回交易消息内容
				//跟新玩家状态
				roleInfo.getStateInfo().setCurState(PlayerState.TRADE);
				break;
			case PopUpMsgType.MESSAGE_GROUP:
				result = processGroupMsg(msg, roleInfo,request,response);// 返回组队消息内容
				//跟新玩家状态
				roleInfo.getStateInfo().setCurState(PlayerState.GROUP);
				break; 
			case PopUpMsgType.XITONG:
				msg.setResult(msg.getMsgOperate1());// 返回弹出式系统消息				
				break;
			case PopUpMsgType.SYS_TESHU_MSG:	// 系统特殊消息
				result = sysSpecialMsg(msg, roleInfo,request,response);
		}
		msgDao.deleteById(roleInfo.getPPk(),msg.getId());// 删除消息
		return msg;
	}

	/**
	 * 系统特殊消息
	 * @param msg
	 * @param roleInfo
	 * @param request
	 * @param response
	 * @param resources
	 * @return
	 */
	private UMessageInfoVO sysSpecialMsg(UMessageInfoVO msg, RoleEntity roleInfo,HttpServletRequest request, HttpServletResponse response)
	{
		String hint = null;
		PopUpMsgService popUpMsgService = new PopUpMsgService();
		switch (Integer.parseInt(msg.getMsgOperate1()))
		{
			case PopUpMsgType.GO_UP_GRADE:// ##等级情况
				hint = popUpMsgService.SysSpecialGrade(msg,response,request);
				break;
			case PopUpMsgType.WRAP_LOWER_LIMIT:// ##包裹情况
				hint = popUpMsgService.SysSpecialWrapLowerLimit(response,request);
				break;
			case PopUpMsgType.PET_FATIGUE:// ##宠物情况
				hint = popUpMsgService.SysSpecialPetFatigue(response,request);
				break;
			case PopUpMsgType.ATTAIN_PROP_TYPE:// 获得道具情况 
				hint = popUpMsgService.SysSpecialAttainProp(msg,response,request);
				break;
			case PopUpMsgType.TASK_INSTANCE://##副本任务情况
				hint = popUpMsgService.SysSpecialTaskInstance(msg,response,request);
				break;
			case PopUpMsgType.TASK_30TONG://30级帮派结束任务情况
				hint = popUpMsgService.SysSpecialTask30Tong(response,request);
				break;
			case PopUpMsgType.TASK_30PK://30级PK结束任务情况
				hint = popUpMsgService.SysSpecialTask30Pk(response,request);
				break;
			case PopUpMsgType.MENU_INSTANCE://副本菜单
				hint = popUpMsgService.SysSpecialMenuInstance(msg,response,request);
				break;
			case PopUpMsgType.MENU_SIEGE://副本菜单
				hint = popUpMsgService.SysSpecialMenuSiege(response,request);
				break;
			case PopUpMsgType.PROP_GRADE://特殊道具在特殊等级的情况
				hint = popUpMsgService.SysSpecialPropGrade(response,request);
				break;
			case PopUpMsgType.VIP_ENDTIME://特殊道具在特殊等级的情况
				hint = popUpMsgService.SysSpecialVipEndtime(response,request);
				break;
			case PopUpMsgType.PK_SWITCH://PK开关 当打开的时候
				hint = popUpMsgService.SysSpecialPkSwitch(response,request);
				break;
			case PopUpMsgType.NEW_ROLE://新角色进入游戏提示
				hint = popUpMsgService.SysSpecialNewRole(response,request);
				break;
			/*case PopUpMsgType.CNN_TODAY://商城今日快讯
				hint = popUpMsgService.loginHotDisplay(response,request);
				break;*/
			case PopUpMsgType.USE_PROP://使用道具
				hint = popUpMsgService.SysSpecialUseProp(msg,response,request);
				break;
		}
		msg.setResult(hint);
		return msg;
	}
	



	/**
	 * 对副本消息处理
	 */
	private UMessageInfoVO processInstanceMsg(UMessageInfoVO msg, RoleEntity roleInfo)
	{
		RoomService roomService = new RoomService(); 
		int new_scene_id = roomService.getResurrectionPoint(roleInfo);

		roleInfo.getBasicInfo().updateSceneId(new_scene_id+"");
		 
		msg.setResult("您已脱离队伍,已脱离副本区域");
		return msg;
	}

	/**
	 * 对交易消息处理
	 */
	private UMessageInfoVO processSwapMsg(UMessageInfoVO msg, RoleEntity roleInfo,HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();
		SellInfoDAO dao = new SellInfoDAO();
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		PartInfoDAO partInfoDAO = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		// 查询自己有没有被交易
		if (dao.isSellInfoIdBy(roleInfo.getBasicInfo().getPPk()))
		{
			// TODO 这里需要增加玩家的状态
			// 取出交易类型
			SellInfoVO sellMode = (SellInfoVO) dao.getSellMode(roleInfo.getBasicInfo().getPPk());
			if (sellMode != null)
			{
				if (sellMode.getSellMode() == SellInfoVO.SELLMONEY)
				{
					// 金钱交易
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					result.append("玩家" + partInfoDAO.getPartName(vo.getPPk() + "") + "给予您" + (long)(vo.getSWpSilverMoney()) + "灵石<br/> ");
					result.append("<anchor> ");
					//result.append("<go method=\"post\" href=\"/sellinfoaction.do\"> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n2\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("确定");
					result.append("</anchor> ");
					result.append("<anchor> ");
					//result.append("<go method=\"post\" href=\"/sellinfoaction.do\"> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n3\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("取消");
					result.append("</anchor>");
				}
				if (sellMode.getSellMode() == SellInfoVO.SELLPROP)
				{
					// 道具交易
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					PropVO propVO = PropCache.getPropById(vo.getSWuping());
					long getSWpSilverMoney = 0;
					if ((long)vo.getSWpSilverMoney() > 0)
					{
						getSWpSilverMoney = vo.getSWpSilverMoney();
					}
					result.append("交易需谨慎，请仔细查看对方的交易物品！<br/>" + partInfoDAO.getPartName(vo.getPPk() + "") + "以" + getSWpSilverMoney + "灵石的价格与您交易");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n14\" /> ");
					result.append("<postfield name=\"prop_id\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(propVO.getPropName() + "*" + vo.getSWpNumber());
					result.append("</anchor><br/>");
					result.append("您");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n8\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("是");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n9\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("否");
					result.append("</anchor>接受交易");
				}
				if (sellMode.getSellMode() == SellInfoVO.SELLARM)
				{
					// 装备交易
					PlayerEquipDao playerEquipDao = new PlayerEquipDao();
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					PartInfoDAO daos = new PartInfoDAO();
					if (playerEquipDao.isHaveById(vo.getSWuping()) == false)
					{
						dao.deleteSelleInfo(sellMode.getSPk() + "");
						result.append(daos.getPartName(vo.getPPk() + "") + "取消了与您的装备交易");
						msg.setResult(result.toString());
						return msg;
					}
					long getSWpSilverMoney = 0;
					if (vo.getSWpSilverMoney() > 0)
					{
						getSWpSilverMoney = vo.getSWpSilverMoney();
					}
					result.append("交易需谨慎，请仔细查看对方的交易物品！<br/>" + partInfoDAO.getPartName(vo.getPPk() + "") + "以" + getSWpSilverMoney + "灵石的价格与您交易");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n13\" /> ");
					result.append("<postfield name=\"pwPk\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(playerEquipDao.getByID(vo.getSWuping()).getFullName() );
					result.append("</anchor><br/>");
					result.append("您");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n5\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("是");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n6\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("否");
					result.append("</anchor>接受交易");
				}
				if (sellMode.getSellMode() == SellInfoVO.ZENGSONGARM)
				{
					// 装备交易
					PlayerEquipDao playerEquipDao = new PlayerEquipDao();
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					PartInfoDAO daos = new PartInfoDAO();
					if (playerEquipDao.isHaveById(vo.getSWuping()) == false)
					{
						dao.deleteSelleInfo(sellMode.getSPk() + "");
						result.append(daos.getPartName(vo.getPPk() + "") + "取消了与您的装备赠送");
						msg.setResult(result.toString());
						return msg;
					} 
					result.append(partInfoDAO.getPartName(vo.getPPk() + "") + "赠送给您");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n10\" /> ");
					result.append("<postfield name=\"pwPk\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(playerEquipDao.getNameById(vo.getSWuping()));
					result.append("</anchor><br/>");
					result.append("您");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n12\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("是");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n13\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("否");
					result.append("</anchor>接受赠送");
				}
				if (sellMode.getSellMode() == SellInfoVO.ZENGSONGPROP)
				{
					// 道具交易

					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					result.append(partInfoDAO.getPartName(vo.getPPk() + "") + "赠送给您");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n14\" /> ");
					result.append("<postfield name=\"prop_id\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(PropCache.getPropNameById(vo.getSWuping()) + "*" + vo.getSWpNumber());
					result.append("</anchor><br/>");
					result.append("您");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n16\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("是");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n17\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("否");
					result.append("</anchor>接受赠送");
				}
			}else{
				result.append("交易超时,系统自动取消交易请求。");
			}
		}
		if (petInfoDAO.getPetSellVs(roleInfo.getBasicInfo().getPPk() + ""))
		{
			// TODO 这里需要增加玩家的状态
			// 取出宠物交易表主键
			int ps_pk = petInfoDAO.getSellPet(roleInfo.getBasicInfo().getPPk());
			if(ps_pk == 0){
				result.append("交易超时,系统自动取消交易请求。");
				msg.setResult(result.toString());
				return msg;
			}
			PetSellVO vo = (PetSellVO) petInfoDAO.getPetSellView(ps_pk);
			PartInfoDAO daos = new PartInfoDAO();
			// 判断请求方该宠物是否还在身上
			if (petInfoDAO.isPetNot(vo.getPetId(), vo.getPPk()) == false)
			{
				result.append(daos.getPartName(vo.getPPk() + "")+ "取消了与您的宠物交易");
				// 确定交易后删除
				petInfoDAO.getPetSellDelete(ps_pk + "");
				msg.setResult(result.toString());
				return msg;
			}

			
			int getPsSilverMoney = 0;
			if (vo.getPsSilverMoney() > 0)
			{
				getPsSilverMoney = vo.getPsSilverMoney();
			}
			int getPsCopperMoney = 0;
			if (vo.getPsCopperMoney() > 0)
			{
				getPsCopperMoney = vo.getPsCopperMoney();
			}
			result.append("交易需谨慎，请仔细查看对方的交易宠物！<br/>" + partInfoDAO.getPartName(vo.getPPk() + "") + "以" + getPsSilverMoney + "灵石" + getPsCopperMoney + "文的价格与您交易");
			result.append("<anchor> ");
			result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
			result.append("<postfield name=\"cmd\" value=\"n15\" /> ");
			result.append("<postfield name=\"pet_pk\" value=\"" + vo.getPetId() + "\" /> ");
			result.append("<postfield name=\"ps_pk\" value=\"" + vo.getPsPk() + "\" /> ");
			result.append("</go>");
			result.append(petInfoDAO.pet_name(vo.getPetId()));
			result.append("</anchor><br/>");
			result.append("您");
			result.append("<anchor> ");
			result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
			result.append("<postfield name=\"cmd\" value=\"n11\" /> ");
			result.append("<postfield name=\"ps_pk\" value=\"" + vo.getPsPk() + "\" /> ");
			result.append("</go>");
			result.append("是");
			result.append("</anchor> ");
			result.append(" <anchor>");
			result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
			result.append("<postfield name=\"cmd\" value=\"n12\" /> ");
			result.append("<postfield name=\"ps_pk\" value=\"" + vo.getPsPk() + "\" /> ");
			result.append("</go>");
			result.append("否");
			result.append("</anchor>接受交易");
		}
		msg.setResult(result.toString());
		return msg;
	}

	/**
	 * 对组队消息的控制
	 */
	private UMessageInfoVO processGroupMsg(UMessageInfoVO msg, RoleEntity roleInfo,HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();
	
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		// 判断是否有组队消息
		int n_pk = groupNotifyService.isHaveNotify(roleInfo.getBasicInfo().getPPk());
		if (n_pk != -1)
		{
			groupNotifyService.updateNotifyFlay(n_pk);
			GroupNotifyVO groupNotify = groupNotifyService.getGroupNotify(roleInfo.getBasicInfo().getPPk());  
			if( groupNotify!=null )
			{
				groupNotifyService.deleteNotify(groupNotify.getNPk());
				if( groupNotify.getNotifyType()==GroupNotifyService.CREATE || groupNotify.getNotifyType()==GroupNotifyService.JOIN || groupNotify.getNotifyType()==GroupNotifyService.INVITE )//组队通知
				{ 
					int a_pk = groupNotify.getNotifyedPk();
					int b_pk = groupNotify.getCreateNotifyPk();
					
					RoleEntity b_role_info = RoleService.getRoleInfoById(b_pk+"");
					if(b_role_info == null || b_role_info.isOnline()==false){
						result.append("对方玩家不在线 ");  
						groupNotifyService.clareNotify(b_pk);// 清除组队通知
						msg.setResult(result.toString());
						return msg;
					}
					String name = b_role_info.getBasicInfo().getName();
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/group.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n10\" /> ");
					result.append("<postfield name=\"a_pk\" value=\"" + a_pk + "\" /> ");
					result.append("<postfield name=\"b_pk\" value=\"" + b_pk + "\" /> ");
					result.append("<postfield name=\"notify_type\" value=\"" + groupNotify.getNotifyType() + "\"/> "); 
					result.append("</go>");
					result.append(name);
					result.append("</anchor>玩家向您提出组队申请<br/>");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/group.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n4\" /> ");
					result.append("<postfield name=\"a_pk\" value=\"" + a_pk + "\" /> ");
					result.append("<postfield name=\"b_pk\" value=\"" + b_pk + "\" /> ");
					result.append("<postfield name=\"notify_type\" value=\"" + groupNotify.getNotifyType() + "\" /> "); 
					result.append("</go>");
					result.append("同意组队");
					result.append("</anchor>");  
				}
				else if( groupNotify.getNotifyType()==GroupNotifyService.GROUPHINT ) 
				{ 
					result.append("您已加入队伍");
				}
			} 
		}else{
			result.append("组队请求被取消");
		}
		msg.setResult(result.toString());
		return msg;
	}
}
