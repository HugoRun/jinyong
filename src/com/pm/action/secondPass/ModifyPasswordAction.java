package com.pm.action.secondPass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.model.user.RoleEntity;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.player.RoleService;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.secondpass.SecondPassService;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;

public class ModifyPasswordAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	//ת���������ҳ��
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int uPk = roleInfo.getBasicInfo().getUPk();
		
		logger.info("uPk="+uPk);
		request.setAttribute("uPk", uPk);
		String second_pass = request.getParameter("second_pass");
		SecondPassService secondPassService = new SecondPassService();
		
		if(second_pass != null && !second_pass.equals("")) {	//������Ƿ�Ϊ��
			if(StringUtil.isNumber(second_pass)) {				//������ǲ��Ƕ������������.
				if(second_pass.length() == 6) {					//������ǲ�����λ
					SecondPassDao seconddao = new SecondPassDao();
					//���������ݿ��еĶ�������
					SecondPassVO secondPassVO = seconddao.getSecondPassTime(uPk);
					if(secondPassVO.getSecondPass() == null || secondPassVO.getSecondPass().equals("")) {
						String hint = "����û�����ö�������, �����޸ĵ�¼���룡";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}
					//�����������������С��3,����������˶�.
					if(!secondPassService.checkSeconePass(uPk,secondPassVO)) {
						if(secondPassVO.getSecondPass().equals(MD5.getMD5Str(second_pass))) {
							request.setAttribute("authenPass", "erjipasswordistrue");
							return mapping.findForward("sussendPass");
						} else {
							secondPassService.insertErrorSecondPsw(uPk); 
							String hint = "";
							//����Ѿ�������,��ô����ҿ�����ʾҲ�᲻һ��.
							if(secondPassVO.getPassWrongFlag() == 2) {
								hint = "���Ѿ���24Сʱ���������������ʺŶ������룬Ϊ�˱������ʺŵİ�ȫ��24Сʱ�ڸ��ʺŲ�����ʹ���޸���Ϸ��¼���빦�ܣ���";
								
							} else {
								hint = "�Բ���������Ķ�����������24Сʱ�������������Ķ������뽫�������޸����빦��һ�죡";
							}
							request.setAttribute("hint", hint);
							return mapping.findForward("failedPass");
						}
					//����Ѿ�����3,�͸�����Ҳ����ٺ˶���.	
					} else {
						secondPassService.insertErrorSecondPsw(uPk); 
						String hint = "���Ѿ���24Сʱ���������������ʺŶ������룬Ϊ�˱������ʺŵİ�ȫ��24Сʱ�ڸ��ʺŲ�����ʹ���޸���Ϸ��¼���빦�ܣ���";
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
	
	//�޸ĵ�¼����
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		String pass_word = request.getParameter("pass_word");
		
		if(pass_word != null && !pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(pass_word)) {			//��¼����Ϊ���ֺ���ĸ�Ļ��
				if(pass_word.length() >=5 && pass_word.length() <= 11) {	//��¼����ĳ�����5��11֮��	
					request.setAttribute("firstPassword", pass_word);
					request.setAttribute("authenPass", "reputpassword");
					return mapping.findForward("firstpassword_true");
				}
			}
			String hint = "�Բ����������������ϲ����Ϲ涨����Ϸ�ʺ�����Ϊ5-11λ,0-9,a-z���!";
			request.setAttribute("hint", hint);
			request.setAttribute("authenPass", "erjipasswordistrue");
			return mapping.findForward("sussendPass");
		}
		String hint = "�Բ������벻��Ϊ��!";
		request.setAttribute("authenPass", "erjipasswordistrue");
		request.setAttribute("hint", hint);
		return mapping.findForward("sussendPass");
	}
	
	//�޸ĵ�¼����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String firstPassword = request.getParameter("firstPassword");
		String re_pass_word = request.getParameter("re_pass_word");
		
		// ���ܻ��ڴ˴����ֿ�ָ�������Ϊȡ����uPk֮���ԭ�� �Ȳ��Ժ��ٸġ�
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int uPk = roleInfo.getBasicInfo().getUPk();
				
		//logger.info("re_pass_word="+re_pass_word+",equals="+re_pass_word.equals(firstPassword));
		if(re_pass_word == null || re_pass_word.equals("")) {		
			String hint = "�Բ������벻��Ϊ��!";
			request.setAttribute("hint", hint);
			request.setAttribute("firstPassword", firstPassword);
			request.setAttribute("authenPass", "reputpassword");
			return mapping.findForward("firstpassword_true");
		}
		
		if(re_pass_word != null && !re_pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(re_pass_word)) {			//��¼����Ϊ���ֺ���ĸ�Ļ��
				if(re_pass_word.length() >=5 && re_pass_word.length() <= 11) {	//��¼����ĳ�����5��11֮��	
					
					if (!firstPassword.equals(re_pass_word)) {
						String hint = "�Բ�����ǰ�������µ��ʺŵ�¼���벻ͬ���������������µ��ʺŵ�¼����:!";
						request.setAttribute("hint", hint);
						request.setAttribute("firstPassword", firstPassword);
						request.setAttribute("authenPass", "reputpassword");
						return mapping.findForward("sussendPass");
					} else {
						
						request.setAttribute("pass_word", re_pass_word);
						SecondPassService secondPassService = new SecondPassService();
						secondPassService.updateLoginPaw(uPk, MD5.getMD5Str(re_pass_word));
						
						return mapping.findForward("sussend_set_psw");
					}
				}
			
			String hint = "�Բ����������������ϲ����Ϲ涨����Ϸ�ʺ�����Ϊ5-11λ,0-9,a-z���!";
			request.setAttribute("hint", hint);
			request.setAttribute("firstPassword", firstPassword);
			request.setAttribute("authenPass", "reputpassword");
			return mapping.findForward("firstpassword_true");
			}
		}
		String hint = "�Բ�������������벻��Ϊ��!";
		request.setAttribute("hint", hint);
		request.setAttribute("firstPassword", firstPassword);
		request.setAttribute("authenPass", "reputpassword");
		return mapping.findForward("firstpassword_true");
	}
	
	
}
