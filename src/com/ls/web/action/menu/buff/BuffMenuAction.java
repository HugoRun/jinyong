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
	
	//��ʾ�����˵�����
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
	

	//���м�buff����
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
		// �������ֵ����-1,�����Ѿ�������buff,��������buff��ID
		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		//int result = buffEffectDao.hasAlreadyBuff(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		if ( buffEffectVO != null && buffEffectVO.getBuffId() != buffId) {
			BuffVO resultbuffvo = buffDao.getBuff(buffEffectVO.getBuffId());
			String resultWml = "��ף��Ч�������ǵ�"+resultbuffvo.getBuffName()+"ף��Ч��, ����Ҫ��ȡ��?";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("reconfirm");
		}
		
		
		String[] contorl = operateMenuVO.getMenuOperate3().split(";");		//��ȡ�һ���������
		
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		logger.info("Ŀǰ�ȼ�:"+partInfoVO.getPGrade()+",Ҫ��ȼ�:"+contorl[0]+"�Լ��Ա�:"+partInfoVO.getPSex()+",Ҫ���Ա�:"+contorl[1]);
		if(!contorl[0].equals("0") ){
			String[] grade = contorl[0].split("-");
			if(Integer.valueOf(grade[0]) > partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ����㣡�ȼ����Ҫ��"+grade[0];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			} else if(Integer.valueOf(grade[1]) < partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ�����Ҫ�󣡵ȼ����Ҫ��"+grade[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if(Integer.valueOf(contorl[1]) != 0){
			if(Integer.valueOf(contorl[1]) != partInfoVO.getPSex()){
				String resultWml = "�Բ��������Ա�����������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		logger.info("���Ļ���״��:"+partInfoVO.getPHarness()+",Ҫ�����״��:"+contorl[2]);
		if(Integer.valueOf(contorl[2]) != 0){
			if(Integer.valueOf(contorl[2]) != partInfoVO.getPHarness()){
				String resultWml = "�Բ������Ļ���״��������������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		
		if (!contorl[3].equals("0") && roleEntity.getTitleSet().isHaveByTitleStr(contorl[3])==false )
		{
			String resultWml = "�Բ������ĳ�ν״��������������";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		if (!contorl[4].equals("0"))
		{
			TimeControlService timeControlService = new TimeControlService();
			logger.info("����Ƿ����:"+timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))+",����ʹ�ô���:"+contorl[4]);
			
			if(!timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))){
				String resultWml = "�Բ����������Ѿ�ʹ��"+contorl[4]+"�Σ��Ѿ��ﵽ���ƣ�������������";
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
	
	 // ���м�buff����
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
		// �������ֵ����-1,�����Ѿ�������buff,��������buff��ID
		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		//int result = buffEffectDao.hasAlreadyBuff(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffvo.getBuffType());
		if ( buffEffectVO != null && buffEffectVO.getBuffId() == buffId) {
			BuffVO resultbuffvo = buffDao.getBuff(buffEffectVO.getBuffId());
			String resultWml = "����ף��Ч�������ǵ�"+resultbuffvo.getBuffName()+"ף��Ч��";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("reconfirm");
		}
		
		
		String[] contorl = operateMenuVO.getMenuOperate3().split(";");		//��ȡ�һ���������
		
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partInfoVO = partInfoDao.getPartInfoByID(Integer.valueOf(pPk));
		
		logger.info("Ŀǰ�ȼ�:"+partInfoVO.getPGrade()+",Ҫ��ȼ�:"+contorl[0]+"�Լ��Ա�:"+partInfoVO.getPSex()+",Ҫ���Ա�:"+contorl[1]);
		if(!contorl[0].equals("0") ){
			String[] grade = contorl[0].split("-");
			if(Integer.valueOf(grade[0]) > partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ����㣡�ȼ����Ҫ��"+grade[0];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			} else if(Integer.valueOf(grade[1]) < partInfoVO.getPGrade()){
				String resultWml = "�Բ������ĵȼ�����Ҫ�󣡵ȼ����Ҫ��"+grade[1];
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if(Integer.valueOf(contorl[1]) != 0){
			if(Integer.valueOf(contorl[1]) != partInfoVO.getPSex()){
				String resultWml = "�Բ��������Ա�����������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		logger.info("���Ļ���״��:"+partInfoVO.getPHarness()+",Ҫ�����״��:"+contorl[2]);
		if(Integer.valueOf(contorl[2]) != 0){
			if(Integer.valueOf(contorl[2]) != partInfoVO.getPHarness()){
				String resultWml = "�Բ������Ļ���״��������������";
				request.setAttribute("resultWml",resultWml);
				request.setAttribute("menu_id",menu_id);
				return mapping.findForward("display");
			}
		}
		if (!contorl[3].equals("0") && roleEntity.getTitleSet().isHaveByTitleStr(contorl[3])==false )
		{
			String resultWml = "�Բ������ĳ�ν״��������������";
			request.setAttribute("resultWml",resultWml);
			request.setAttribute("menu_id",menu_id);
			return mapping.findForward("display");
		}
		if (!contorl[4].equals("0"))
		{
			TimeControlService timeControlService = new TimeControlService();
			logger.info("����Ƿ����:"+timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))+",����ʹ�ô���:"+contorl[4]);
			
			if(!timeControlService.isUseable(Integer.valueOf(pPk), Integer.valueOf(menu_id), TimeControlService.MENU, Integer.valueOf(contorl[4]))){
				String resultWml = "�Բ����������Ѿ�ʹ��"+contorl[4]+"�Σ��Ѿ��ﵽ���ƣ�������������";
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
