package com.ls.web.action.menu.buff;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.menu.buff.BuffMenuService;


public class BuffMenuAction extends DispatchAction {


	Logger logger = Logger.getLogger("log.action");
	
	//显示单个菜单详情
	/**public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String menu_id = request.getParameter("menu_id");
		
		UserTempBean userTempBean = null;
		
		userTempBean = (UserTempBean)request.getSession().getAttribute("userTempBean");
		
		//request.setAttribute("menu_id", menu_id);
		request.setAttribute("pPk", userTempBean.getPPk());
		//request.setAttribute("uPk", userTempBean.getUPk());
		
		OperateMenuDao operateMenuDao = new OperateMenuDao();
		OperateMenuVO buffmenuvo = operateMenuDao.getMenuById(Integer.valueOf(menu_id));
		
		request.setAttribute("buffmenuvo", buffmenuvo);
		
		return mapping.findForward("buffmenuview");
	}**/
	

	//进行加buff操作
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleEntity roleEntity = RoleCache.getByPpk(pPk);
		
		String menu_id = request.getParameter("menu_id");
		
		request.setAttribute("pPk",pPk);
				
		BuffMenuService bfservice  = new BuffMenuService(); 
		
		MenuService menuService = new MenuService();
		OperateMenuVO operateMenuVO = menuService.getMenuById(Integer.parseInt(menu_id));
		
		int buffId = Integer.valueOf(operateMenuVO.getMenuOperate1());
		BuffDao buffDao = new BuffDao();
		BuffVO buffvo = buffDao.getBuff(buffId);
		
		
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		// 如果返回值大于-1,代表已经有这种buff,返回这种buff的ID
		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		//int result = buffEffectDao.hasAlreadyBuff(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		if ( buffEffectVO != null && buffEffectVO.getBuffId() != buffId) {
			BuffVO resultbuffvo = buffDao.getBuff(buffEffectVO.getBuffId());
			String resultWml = "此祝福效果将覆盖掉"+resultbuffvo.getBuffName()+"祝福效果, 还需要领取吗?";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("reconfirm");
		}
		
		
		String[] contorl = operateMenuVO.getMenuOperate3().split(";");		//获取兑换控制条件
		
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		logger.info("目前等级:"+partInfoVO.getPGrade()+",要求等级:"+contorl[0]+"自己性别:"+partInfoVO.getPSex()+",要求性别:"+contorl[1]);
		if(!contorl[0].equals("0") ){
			String[] grade = contorl[0].split("-");
			if(Integer.valueOf(grade[0]) > partInfoVO.getPGrade()){
				String resultWml = "对不起，您的等级不足！等级最低要求"+grade[0];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			} else if(Integer.valueOf(grade[1]) < partInfoVO.getPGrade()){
				String resultWml = "对不起，您的等级超出要求！等级最高要求"+grade[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if(Integer.valueOf(contorl[1]) != 0){
			if(Integer.valueOf(contorl[1]) != partInfoVO.getPSex()){
				String resultWml = "对不起，您的性别不满足条件！";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		logger.info("您的婚姻状况:"+partInfoVO.getPHarness()+",要求婚姻状况:"+contorl[2]);
		if(Integer.valueOf(contorl[2]) != 0){
			if(Integer.valueOf(contorl[2]) != partInfoVO.getPHarness()){
				String resultWml = "对不起，您的婚姻状况不满足条件！";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		
		if (!contorl[3].equals("0") && roleEntity.getTitleSet().isHaveByTitleStr(contorl[3])==false )
		{
			String resultWml = "对不起，您的称谓状况不满足条件！";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		if (!contorl[4].equals("0"))
		{
			TimeControlService timeControlService = new TimeControlService();
			logger.info("这次是否可用:"+timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))+",允许使用次数:"+contorl[4]);
			
			if(!timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))){
				String resultWml = "对不起，您本日已经使用"+contorl[4]+"次，已经达到限制，请明日再来！";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else {
				timeControlService.updateControlInfo(Integer.parseInt(pPk), Integer.parseInt(menu_id), TimeControlService.MENU);
			}
		}
		
		bfservice.setBuffStatus(Integer.parseInt(pPk),Integer.parseInt(operateMenuVO.getMenuOperate1()));
		request.setAttribute("menu_id",menu_id);
		request.setAttribute("buffvo",buffvo);
		return mapping.findForward("buffAddSussend");
	}
	
	 // 进行加buff操作
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleEntity roleEntity = RoleCache.getByPpk(pPk);
		
		String menu_id = request.getParameter("menu_id");
		String reconfirm = request.getParameter("reconfirm");
						
		BuffMenuService bfservice  = new BuffMenuService(); 
		
		MenuService menuService = new MenuService();
		OperateMenuVO operateMenuVO = menuService.getMenuById(Integer.parseInt(menu_id));
		
		int buffId = Integer.valueOf(operateMenuVO.getMenuOperate1());
		BuffDao buffDao = new BuffDao();
		BuffVO buffvo = buffDao.getBuff(buffId);
		
		
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		// 如果返回值大于-1,代表已经有这种buff,返回这种buff的ID
		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		//int result = buffEffectDao.hasAlreadyBuff(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		if ( buffEffectVO != null && buffEffectVO.getBuffId() == buffId) {
			BuffVO resultbuffvo = buffDao.getBuff(buffEffectVO.getBuffId());
			String resultWml = "：此祝福效果将覆盖掉"+resultbuffvo.getBuffName()+"祝福效果";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("reconfirm");
		}
		
		
		String[] contorl = operateMenuVO.getMenuOperate3().split(";");		//获取兑换控制条件
		
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		logger.info("目前等级:"+partInfoVO.getPGrade()+",要求等级:"+contorl[0]+"自己性别:"+partInfoVO.getPSex()+",要求性别:"+contorl[1]);
		if(!contorl[0].equals("0") ){
			String[] grade = contorl[0].split("-");
			if(Integer.valueOf(grade[0]) > partInfoVO.getPGrade()){
				String resultWml = "对不起，您的等级不足！等级最低要求"+grade[0];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			} else if(Integer.valueOf(grade[1]) < partInfoVO.getPGrade()){
				String resultWml = "对不起，您的等级超出要求！等级最高要求"+grade[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if(Integer.valueOf(contorl[1]) != 0){
			if(Integer.valueOf(contorl[1]) != partInfoVO.getPSex()){
				String resultWml = "对不起，您的性别不满足条件！";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		logger.info("您的婚姻状况:"+partInfoVO.getPHarness()+",要求婚姻状况:"+contorl[2]);
		if(Integer.valueOf(contorl[2]) != 0){
			if(Integer.valueOf(contorl[2]) != partInfoVO.getPHarness()){
				String resultWml = "对不起，您的婚姻状况不满足条件！";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if (!contorl[3].equals("0") && roleEntity.getTitleSet().isHaveByTitleStr(contorl[3])==false )
		{
			String resultWml = "对不起，您的称谓状况不满足条件！";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		if (!contorl[4].equals("0"))
		{
			TimeControlService timeControlService = new TimeControlService();
			logger.info("这次是否可用:"+timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))+",允许使用次数:"+contorl[4]);
			
			if(!timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))){
				String resultWml = "对不起，您本日已经使用"+contorl[4]+"次，已经达到限制，请明日再来！";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}else {
				timeControlService.updateControlInfo(Integer.parseInt(pPk), Integer.parseInt(menu_id), TimeControlService.MENU);
			}
		}
		
		bfservice.setBuffStatus(Integer.parseInt(pPk),Integer.parseInt(operateMenuVO.getMenuOperate1()));
		request.setAttribute("menu_id",menu_id);
		request.setAttribute("buffvo",buffvo);
		return mapping.findForward("buffAddSussend");
	}
	
}
