package com.ls.web.action.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.deletepart.DeletePartDAO;
import com.ben.jms.JmsUtil;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.secondpass.SecondPassService;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;

/**
 * @author ls
 * ��ɫ��������½����Ϣ�鿴
 */
public class RoleAction extends ActionBase {

	Logger logger = Logger.getLogger("log.");
	
	/**
	 * �鿴��ɫ��Ϣ
	 */
	public ActionForward des(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String p_pk = request.getParameter("pPk");
		RoleEntity other = RoleService.getRoleInfoById(p_pk);
		
		String pre = request.getParameter("pre");
		
		request.setAttribute("other", other);
		request.setAttribute("pre", pre);
		return mapping.findForward("role_des");
	}
	
	/** 
	 * ��ɫ�����б�ҳ��
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("born_list");
	}
	
	/** 
	 * ���������ɫ��Ϣҳ��
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String race =  request.getParameter("race");
		
		String hint = ValidateService.validateRace(race);
		if( hint!=null )
		{
			this.setHint(request, hint);
			return mapping.findForward("born_list");
		}
		
		request.setAttribute("raceDes",ExchangeUtil.getRaceName(Integer.parseInt(race)));
		request.setAttribute("race",race);
		return mapping.findForward("input_role_info");
	}
	
	/** 
	 * ������ɫ����
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		String race = request.getParameter("race");
		String pName = request.getParameter("name");
		String pSex = request.getParameter("sex");
		String uPk = (String)session.getAttribute("uPk");
		
		if( uPk==null )//���uPkΪ�����µ�½
		{
			return mapping.findForward("login_index");
		}
		
		RoleService roleService = new RoleService();
		
		String hint = ValidateService.validateCreateRole(uPk, pName,pSex,race);
		
		if( hint!=null )//��֤��ҵ�����ʧ��
		{
			this.setHint(request, hint);
			request.setAttribute("race",race);
			request.setAttribute("raceDes",ExchangeUtil.getRaceName(Integer.parseInt(race)));
			return mapping.findForward("input_role_info");
		}
		
		RoleEntity role_info = roleService.createRole(uPk, pName, pSex, race);
		if( role_info==null )
		{
			this.setHint(request, "������ɫʧ��,������");
			request.setAttribute("race",race);
			request.setAttribute("raceDes",ExchangeUtil.getRaceName(Integer.parseInt(race)));
			return mapping.findForward("input_role_info");
		}
		
		JmsUtil.sendJmsRole((String)session.getAttribute("super_qudao"),(String)session.getAttribute("qudao"), (String)session.getAttribute("user_name"), pName, 1);
		//������������������ҳ��
		request.getSession().setAttribute("pPk", role_info.getPPk()+"");
		return super.dispath(request, response, "/guide.do");
	}
	
	/** 
	 * ɾ����ɫ����
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pPk = request.getParameter("pPk");
		RoleService roleService = new RoleService();
		roleService.setRoleDeleteState(Integer.parseInt(pPk));
		
		return mapping.findForward("role_list");
	}
	
	/** 
	 * �ָ�ɾ����ɫ����
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pPk = request.getParameter("pPk");
		RoleService roleService = new RoleService();
		roleService.resumeRole(Integer.parseInt(pPk));
		
		return mapping.findForward("role_list");
	}	
	
	/**
	 * ɾ����ɫȷ��ҳ��
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("pPk", request.getParameter("pPk"));
		request.setAttribute("pName", request.getParameter("pName"));
		request.setAttribute("pGrade", request.getParameter("pGrade"));
		return mapping.findForward("delete_role_comfirm");
	}
	
	/**
	 * 
	 * ����ָ���ɫ����ת��ȷ���Ƿ�ָ�ҳ��
	 * 
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pPk = request.getParameter("pPk");
		request.setAttribute("uPk", request.getParameter("uPk"));
		request.setAttribute("pPk", pPk);
		
		PartInfoDao infodao = new PartInfoDao();
		String pName = infodao.getNameByPpk(Integer.parseInt(pPk));
		request.setAttribute("pName", pName);
		
		return mapping.findForward("resumepartpage");
	}
	
	/**
	 * ����ɾ����ɫ,Ҫ���������������к˶�.
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{	
		String second_pass = request.getParameter("second_pass");
		String uPk = (String) request.getSession().getAttribute("uPk");
		if (uPk == null || uPk.equals("")) {			
			uPk = request.getParameter("uPk");
		}			
		String pPk = request.getParameter("pPk");

		if(pPk == null){
			return mapping.findForward("login_index");
		}

		
		request.setAttribute("uPk", uPk);
		request.setAttribute("pPk", pPk);	
		request.setAttribute("pGrade", request.getParameter("pGrade"));
							
		SecondPassService secondPassService = new SecondPassService();
		
		if(second_pass != null && !second_pass.equals("")) {	//������Ƿ�Ϊ��
			if(StringUtil.isNumber(second_pass)) {				//������ǲ��Ƕ������������.
				if(second_pass.length() == 6) {					//������ǲ�����λ
					SecondPassDao seconddao = new SecondPassDao();
					//���������ݿ��еĶ�������
					SecondPassVO secondPassVO = seconddao.getSecondPassTime(Integer.parseInt(uPk));
					if(secondPassVO == null || secondPassVO.getSecondPass() == null || secondPassVO.getSecondPass().equals("")) {
						String hint = "����û�����ö�������, �����޸ĵ�¼���룡";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}	
					//�����������������С��3,����������˶�.
					if(!secondPassService.checkSeconePass(Integer.parseInt(uPk),secondPassVO)) {
						if(secondPassVO.getSecondPass().equals(MD5.getMD5Str(second_pass))) {
							//ȷ��ɾ��ʱ��.
							DeletePartDAO deletePartDAO = new DeletePartDAO();
							//deletePartDAO.delete(pPk,uPk);
							
							PartInfoDao infoDao = new PartInfoDao();
							String pName = infoDao.getNameByPpk(Integer.parseInt(pPk));
							request.setAttribute("pName", pName);
							return mapping.findForward("sussendPass");
						} else {	
							secondPassService.insertErrorSecondPsw(Integer.parseInt(uPk)); 
							String hint = "";
							//����Ѿ�������,��ô����ҿ�����ʾҲ�᲻һ��.
							if(secondPassVO.getPassWrongFlag() == 2) {
								hint = "���Ѿ���24Сʱ���������������ʺŶ������룬Ϊ�˱������ʺŵİ�ȫ��24Сʱ�ڸ��ʺŲ�����ʹ�ö����������ɾ����ɫ����!!";
								
							} else {	
								hint = "�Բ���������Ķ�����������24Сʱ�������������Ķ������뽫�������޸����빦��һ�죡";
							}	
							request.setAttribute("hint", hint);
							return mapping.findForward("failedPass");
						}
					//����Ѿ�����3,�͸�����Ҳ����ٺ˶���.	
					} else {
						secondPassService.insertErrorSecondPsw(Integer.parseInt(uPk)); 
						String hint = "���Ѿ���24Сʱ���������������ʺŶ������룬Ϊ�˱������ʺŵİ�ȫ��24Сʱ�ڸ��ʺŲ�����ʹ�ö����������ɾ����ɫ����!!";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}	
				}
			}
			String hint = "�Բ���������Ķ��������ʽ����ȷ,��������Ϊ��λ������ɣ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("failedPass");
		}
		String hint = "�Բ����벻Ҫ��������룡";
		request.setAttribute("hint", hint);
		return mapping.findForward("failedPass");
	}
	
	/**
	 * ����ɾ����ɫ,��Ҫ���������������к˶�.
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{	
		String uPk = (String) request.getSession().getAttribute("uPk");
		if ( StringUtils.isEmpty(uPk)==true) 
		{			
			return this.dispath(request, response, "/login.do?cmd=n0");
		}			
		String pPk = request.getParameter("pPk");
		
		//ɾ����ɫ
		RoleService roleService = new RoleService();
		boolean result = roleService.delRole(uPk,pPk);
		if( result==false )
		{
			//ɾ��ʧ��
			this.setHint(request, "ɾ����ɫʧ��");
		}
		return this.dispath(request, response, "/login.do?cmd=n3");
	}
}