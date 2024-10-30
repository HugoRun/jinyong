package com.pm.action.tijiao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

public class TiJiaoAction extends DispatchAction
{
	Logger logger = Logger.getLogger("log.action");
	
	
	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	// �鿴�����ж�����
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		MenuCache menuCache = new MenuCache();
		OperateMenuVO menuVO =  menuCache.getById(menu_id);
		
		String propOperate1 = menuVO.getMenuOperate1();
		String[] props = propOperate1.split(",");
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int di =  playerPropGroupDao.getPropNumByByPropID(p_pk, Integer.parseInt(props[0]));	// ��
		int yi =  playerPropGroupDao.getPropNumByByPropID(p_pk, Integer.parseInt(props[1]));	// һ	
		int bang =  playerPropGroupDao.getPropNumByByPropID(p_pk, Integer.parseInt(props[2]));	// ��
		
		di = di > yi ? yi : di;
		di = di > bang ? bang : di;
		
		request.setAttribute("props", props);
		request.setAttribute("di", di);
		request.setAttribute("menu_id", menu_id);
		return mapping.findForward("start_tijiao");
	}
	
	
	
	// ��Ʒ�б�
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		String numberString = request.getParameter("number");
		String menu_id = request.getParameter("menu_id");
		
		MenuCache menuCache = new MenuCache();
		OperateMenuVO menuVO =  menuCache.getById(menu_id);
		
		String propOperate1 = menuVO.getMenuOperate1();
		String[] props = propOperate1.split(",");
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int di =  playerPropGroupDao.getPropNumByByPropID(p_pk, Integer.parseInt(props[0]));	// ��
		int yi =  playerPropGroupDao.getPropNumByByPropID(p_pk, Integer.parseInt(props[1]));	// һ	
		int bang =  playerPropGroupDao.getPropNumByByPropID(p_pk, Integer.parseInt(props[2]));	// ��
		
		di = di > yi ? yi : di;
		di = di > bang ? bang : di;
		
		int number  = 0;
		try
		{
			number = Integer.parseInt(numberString);
			if ( number <= 0) {
				request.setAttribute("hint", "�ύ������Ӧ������!");
				request.setAttribute("props", props);
				request.setAttribute("di", di);
				request.setAttribute("menu_id", menu_id);
				return mapping.findForward("start_tijiao");
			} else  if (number > di){
				request.setAttribute("hint", "������û����ô����!");
				request.setAttribute("props", props);
				request.setAttribute("di", di);
				request.setAttribute("menu_id", menu_id);
				return mapping.findForward("start_tijiao");
			} else {
				if (roleInfo.getBasicInfo().getFaction() == null) {
					request.setAttribute("hint", "�Բ���,����û�����,�޷��ύ��Ʒ!");
					request.setAttribute("props", props);
					request.setAttribute("di", di);
					request.setAttribute("menu_id", menu_id);
					return mapping.findForward("start_tijiao");
				} else {
					/*GoodsService goodsService = new GoodsService();
					goodsService.removeProps(p_pk, Integer.parseInt(props[0]),number);
					goodsService.removeProps(p_pk, Integer.parseInt(props[1]),number);
					goodsService.removeProps(p_pk, Integer.parseInt(props[2]),number);
					BattleService battleService = new BattleService();
					
					battleService.addUserTongManslaughter(tong_pk, p_pk,number*10);
					String hint = "���ύ��"+number+"�׵�һ����֣����Ӱ��\"Ѫ��\"����"+(number*10)+"��!";
					request.setAttribute("hint", hint);*/
					return mapping.findForward("hint");
				}				
			}
		}
		catch (Exception e)
		{
			request.setAttribute("hint", "����ȷ�����ύ����!");
			request.setAttribute("props", props);
			request.setAttribute("di", di);
			request.setAttribute("menu_id", menu_id);
			return mapping.findForward("start_tijiao");
		}
	}

}
