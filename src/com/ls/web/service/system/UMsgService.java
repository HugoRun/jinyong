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
 * @author ls ����:�û�������Ϣ�߼����� Mar 6, 2009
 */
public class UMsgService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * �����ɫ������Ϣ
	 */
	public void clear(int p_pk)
	{
		UMessageInfoDao msgDao = new UMessageInfoDao();
		msgDao.clear(p_pk);
	}
	
	
	/**
	 * �����ɫͬ����������Ϣ
	 */
	public void clear(int p_pk, int msg_type)
	{
		UMessageInfoDao msgDao = new UMessageInfoDao();
		msgDao.clear(p_pk,msg_type);
	}
	
	/**
	 * �õ�������Ϣ
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
	 * ���͵�����Ϣ
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
	 * ����Ϣ���д���
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
				result = processInstanceMsg(msg, roleInfo);// ���ظ�����Ϣ����
				break; 
			case PopUpMsgType.MESSAGE_SWAP:
				result = processSwapMsg(msg, roleInfo,request,response);// ���ؽ�����Ϣ����
				//�������״̬
				roleInfo.getStateInfo().setCurState(PlayerState.TRADE);
				break;
			case PopUpMsgType.MESSAGE_GROUP:
				result = processGroupMsg(msg, roleInfo,request,response);// ���������Ϣ����
				//�������״̬
				roleInfo.getStateInfo().setCurState(PlayerState.GROUP);
				break; 
			case PopUpMsgType.XITONG:
				msg.setResult(msg.getMsgOperate1());// ���ص���ʽϵͳ��Ϣ				
				break;
			case PopUpMsgType.SYS_TESHU_MSG:	// ϵͳ������Ϣ
				result = sysSpecialMsg(msg, roleInfo,request,response);
		}
		msgDao.deleteById(roleInfo.getPPk(),msg.getId());// ɾ����Ϣ
		return msg;
	}

	/**
	 * ϵͳ������Ϣ
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
			case PopUpMsgType.GO_UP_GRADE:// ##�ȼ����
				hint = popUpMsgService.SysSpecialGrade(msg,response,request);
				break;
			case PopUpMsgType.WRAP_LOWER_LIMIT:// ##�������
				hint = popUpMsgService.SysSpecialWrapLowerLimit(response,request);
				break;
			case PopUpMsgType.PET_FATIGUE:// ##�������
				hint = popUpMsgService.SysSpecialPetFatigue(response,request);
				break;
			case PopUpMsgType.ATTAIN_PROP_TYPE:// ��õ������ 
				hint = popUpMsgService.SysSpecialAttainProp(msg,response,request);
				break;
			case PopUpMsgType.TASK_INSTANCE://##�����������
				hint = popUpMsgService.SysSpecialTaskInstance(msg,response,request);
				break;
			case PopUpMsgType.TASK_30TONG://30�����ɽ����������
				hint = popUpMsgService.SysSpecialTask30Tong(response,request);
				break;
			case PopUpMsgType.TASK_30PK://30��PK�����������
				hint = popUpMsgService.SysSpecialTask30Pk(response,request);
				break;
			case PopUpMsgType.MENU_INSTANCE://�����˵�
				hint = popUpMsgService.SysSpecialMenuInstance(msg,response,request);
				break;
			case PopUpMsgType.MENU_SIEGE://�����˵�
				hint = popUpMsgService.SysSpecialMenuSiege(response,request);
				break;
			case PopUpMsgType.PROP_GRADE://�������������ȼ������
				hint = popUpMsgService.SysSpecialPropGrade(response,request);
				break;
			case PopUpMsgType.VIP_ENDTIME://�������������ȼ������
				hint = popUpMsgService.SysSpecialVipEndtime(response,request);
				break;
			case PopUpMsgType.PK_SWITCH://PK���� ���򿪵�ʱ��
				hint = popUpMsgService.SysSpecialPkSwitch(response,request);
				break;
			case PopUpMsgType.NEW_ROLE://�½�ɫ������Ϸ��ʾ
				hint = popUpMsgService.SysSpecialNewRole(response,request);
				break;
			/*case PopUpMsgType.CNN_TODAY://�̳ǽ��տ�Ѷ
				hint = popUpMsgService.loginHotDisplay(response,request);
				break;*/
			case PopUpMsgType.USE_PROP://ʹ�õ���
				hint = popUpMsgService.SysSpecialUseProp(msg,response,request);
				break;
		}
		msg.setResult(hint);
		return msg;
	}
	



	/**
	 * �Ը�����Ϣ����
	 */
	private UMessageInfoVO processInstanceMsg(UMessageInfoVO msg, RoleEntity roleInfo)
	{
		RoomService roomService = new RoomService(); 
		int new_scene_id = roomService.getResurrectionPoint(roleInfo);

		roleInfo.getBasicInfo().updateSceneId(new_scene_id+"");
		 
		msg.setResult("�����������,�����븱������");
		return msg;
	}

	/**
	 * �Խ�����Ϣ����
	 */
	private UMessageInfoVO processSwapMsg(UMessageInfoVO msg, RoleEntity roleInfo,HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();
		SellInfoDAO dao = new SellInfoDAO();
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		PartInfoDAO partInfoDAO = new PartInfoDAO();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		// ��ѯ�Լ���û�б�����
		if (dao.isSellInfoIdBy(roleInfo.getBasicInfo().getPPk()))
		{
			// TODO ������Ҫ������ҵ�״̬
			// ȡ����������
			SellInfoVO sellMode = (SellInfoVO) dao.getSellMode(roleInfo.getBasicInfo().getPPk());
			if (sellMode != null)
			{
				if (sellMode.getSellMode() == SellInfoVO.SELLMONEY)
				{
					// ��Ǯ����
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					result.append("���" + partInfoDAO.getPartName(vo.getPPk() + "") + "������" + (long)(vo.getSWpSilverMoney()) + "��ʯ<br/> ");
					result.append("<anchor> ");
					//result.append("<go method=\"post\" href=\"/sellinfoaction.do\"> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n2\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("ȷ��");
					result.append("</anchor> ");
					result.append("<anchor> ");
					//result.append("<go method=\"post\" href=\"/sellinfoaction.do\"> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n3\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("ȡ��");
					result.append("</anchor>");
				}
				if (sellMode.getSellMode() == SellInfoVO.SELLPROP)
				{
					// ���߽���
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					PropVO propVO = PropCache.getPropById(vo.getSWuping());
					long getSWpSilverMoney = 0;
					if ((long)vo.getSWpSilverMoney() > 0)
					{
						getSWpSilverMoney = vo.getSWpSilverMoney();
					}
					result.append("���������������ϸ�鿴�Է��Ľ�����Ʒ��<br/>" + partInfoDAO.getPartName(vo.getPPk() + "") + "��" + getSWpSilverMoney + "��ʯ�ļ۸���������");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n14\" /> ");
					result.append("<postfield name=\"prop_id\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(propVO.getPropName() + "*" + vo.getSWpNumber());
					result.append("</anchor><br/>");
					result.append("��");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n8\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n9\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor>���ܽ���");
				}
				if (sellMode.getSellMode() == SellInfoVO.SELLARM)
				{
					// װ������
					PlayerEquipDao playerEquipDao = new PlayerEquipDao();
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					PartInfoDAO daos = new PartInfoDAO();
					if (playerEquipDao.isHaveById(vo.getSWuping()) == false)
					{
						dao.deleteSelleInfo(sellMode.getSPk() + "");
						result.append(daos.getPartName(vo.getPPk() + "") + "ȡ����������װ������");
						msg.setResult(result.toString());
						return msg;
					}
					long getSWpSilverMoney = 0;
					if (vo.getSWpSilverMoney() > 0)
					{
						getSWpSilverMoney = vo.getSWpSilverMoney();
					}
					result.append("���������������ϸ�鿴�Է��Ľ�����Ʒ��<br/>" + partInfoDAO.getPartName(vo.getPPk() + "") + "��" + getSWpSilverMoney + "��ʯ�ļ۸���������");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n13\" /> ");
					result.append("<postfield name=\"pwPk\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(playerEquipDao.getByID(vo.getSWuping()).getFullName() );
					result.append("</anchor><br/>");
					result.append("��");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n5\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n6\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor>���ܽ���");
				}
				if (sellMode.getSellMode() == SellInfoVO.ZENGSONGARM)
				{
					// װ������
					PlayerEquipDao playerEquipDao = new PlayerEquipDao();
					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					PartInfoDAO daos = new PartInfoDAO();
					if (playerEquipDao.isHaveById(vo.getSWuping()) == false)
					{
						dao.deleteSelleInfo(sellMode.getSPk() + "");
						result.append(daos.getPartName(vo.getPPk() + "") + "ȡ����������װ������");
						msg.setResult(result.toString());
						return msg;
					} 
					result.append(partInfoDAO.getPartName(vo.getPPk() + "") + "���͸���");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n10\" /> ");
					result.append("<postfield name=\"pwPk\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(playerEquipDao.getNameById(vo.getSWuping()));
					result.append("</anchor><br/>");
					result.append("��");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n12\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n13\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor>��������");
				}
				if (sellMode.getSellMode() == SellInfoVO.ZENGSONGPROP)
				{
					// ���߽���

					SellInfoVO vo = (SellInfoVO) sellInfoDAO.getSellView(sellMode.getSPk());
					result.append(partInfoDAO.getPartName(vo.getPPk() + "") + "���͸���");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n14\" /> ");
					result.append("<postfield name=\"prop_id\" value=\"" + vo.getSWuping() + "\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append(PropCache.getPropNameById(vo.getSWuping()) + "*" + vo.getSWpNumber());
					result.append("</anchor><br/>");
					result.append("��");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n16\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor> ");
					result.append(" <anchor>");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n17\" /> ");
					result.append("<postfield name=\"sPk\" value=\"" + vo.getSPk() + "\" /> ");
					result.append("</go>");
					result.append("��");
					result.append("</anchor>��������");
				}
			}else{
				result.append("���׳�ʱ,ϵͳ�Զ�ȡ����������");
			}
		}
		if (petInfoDAO.getPetSellVs(roleInfo.getBasicInfo().getPPk() + ""))
		{
			// TODO ������Ҫ������ҵ�״̬
			// ȡ�����ｻ�ױ�����
			int ps_pk = petInfoDAO.getSellPet(roleInfo.getBasicInfo().getPPk());
			if(ps_pk == 0){
				result.append("���׳�ʱ,ϵͳ�Զ�ȡ����������");
				msg.setResult(result.toString());
				return msg;
			}
			PetSellVO vo = (PetSellVO) petInfoDAO.getPetSellView(ps_pk);
			PartInfoDAO daos = new PartInfoDAO();
			// �ж����󷽸ó����Ƿ�������
			if (petInfoDAO.isPetNot(vo.getPetId(), vo.getPPk()) == false)
			{
				result.append(daos.getPartName(vo.getPPk() + "")+ "ȡ���������ĳ��ｻ��");
				// ȷ�����׺�ɾ��
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
			result.append("���������������ϸ�鿴�Է��Ľ��׳��<br/>" + partInfoDAO.getPartName(vo.getPPk() + "") + "��" + getPsSilverMoney + "��ʯ" + getPsCopperMoney + "�ĵļ۸���������");
			result.append("<anchor> ");
			result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
			result.append("<postfield name=\"cmd\" value=\"n15\" /> ");
			result.append("<postfield name=\"pet_pk\" value=\"" + vo.getPetId() + "\" /> ");
			result.append("<postfield name=\"ps_pk\" value=\"" + vo.getPsPk() + "\" /> ");
			result.append("</go>");
			result.append(petInfoDAO.pet_name(vo.getPetId()));
			result.append("</anchor><br/>");
			result.append("��");
			result.append("<anchor> ");
			result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
			result.append("<postfield name=\"cmd\" value=\"n11\" /> ");
			result.append("<postfield name=\"ps_pk\" value=\"" + vo.getPsPk() + "\" /> ");
			result.append("</go>");
			result.append("��");
			result.append("</anchor> ");
			result.append(" <anchor>");
			result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")+"\">");
			result.append("<postfield name=\"cmd\" value=\"n12\" /> ");
			result.append("<postfield name=\"ps_pk\" value=\"" + vo.getPsPk() + "\" /> ");
			result.append("</go>");
			result.append("��");
			result.append("</anchor>���ܽ���");
		}
		msg.setResult(result.toString());
		return msg;
	}

	/**
	 * �������Ϣ�Ŀ���
	 */
	private UMessageInfoVO processGroupMsg(UMessageInfoVO msg, RoleEntity roleInfo,HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer result = new StringBuffer();
	
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		// �ж��Ƿ��������Ϣ
		int n_pk = groupNotifyService.isHaveNotify(roleInfo.getBasicInfo().getPPk());
		if (n_pk != -1)
		{
			groupNotifyService.updateNotifyFlay(n_pk);
			GroupNotifyVO groupNotify = groupNotifyService.getGroupNotify(roleInfo.getBasicInfo().getPPk());  
			if( groupNotify!=null )
			{
				groupNotifyService.deleteNotify(groupNotify.getNPk());
				if( groupNotify.getNotifyType()==GroupNotifyService.CREATE || groupNotify.getNotifyType()==GroupNotifyService.JOIN || groupNotify.getNotifyType()==GroupNotifyService.INVITE )//���֪ͨ
				{ 
					int a_pk = groupNotify.getNotifyedPk();
					int b_pk = groupNotify.getCreateNotifyPk();
					
					RoleEntity b_role_info = RoleService.getRoleInfoById(b_pk+"");
					if(b_role_info == null || b_role_info.isOnline()==false){
						result.append("�Է���Ҳ����� ");  
						groupNotifyService.clareNotify(b_pk);// ������֪ͨ
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
					result.append("</anchor>�����������������<br/>");
					result.append("<anchor> ");
					result.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/group.do")+"\">");
					result.append("<postfield name=\"cmd\" value=\"n4\" /> ");
					result.append("<postfield name=\"a_pk\" value=\"" + a_pk + "\" /> ");
					result.append("<postfield name=\"b_pk\" value=\"" + b_pk + "\" /> ");
					result.append("<postfield name=\"notify_type\" value=\"" + groupNotify.getNotifyType() + "\" /> "); 
					result.append("</go>");
					result.append("ͬ�����");
					result.append("</anchor>");  
				}
				else if( groupNotify.getNotifyType()==GroupNotifyService.GROUPHINT ) 
				{ 
					result.append("���Ѽ������");
				}
			} 
		}else{
			result.append("�������ȡ��");
		}
		msg.setResult(result.toString());
		return msg;
	}
}
