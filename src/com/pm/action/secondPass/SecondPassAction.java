package com.pm.action.secondPass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ls.pub.util.StringUtil;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.mail.MailInfoService;
import com.pm.service.secondpass.SecondPassService;
import com.pub.MD5;


public class SecondPassAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	
	//ת���������ҳ��
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//String pPk = request.getParameter("pPk");	//����pPk������εõ�,��Ҫ������һ������
		String secondPass = request.getParameter("secondPass");
		String uPk = (String)request.getSession().getAttribute("uPk");
		logger.info("�޸Ķ�������ʱ��upk="+uPk);
		
		if(secondPass != null && !secondPass.equals("")) {
			boolean flag = StringUtil.isNumber(secondPass);
			if(flag == true) {	
				if(secondPass.length() == 6) {	
					
					LoginInfoDAO infoDao = new LoginInfoDAO();
					String nowpaw = infoDao.getUserLoginPawByUPk( Integer.parseInt(uPk));
					if(nowpaw.equals(MD5.getMD5Str(secondPass))) {
						String hint = "�Բ��𣬶������벻�����¼������ͬ!";
						request.setAttribute("hint", hint);
						return mapping.findForward("failedPass");
					}					
					
					SecondPassService secondPassService = new SecondPassService();
					// ��ǰ����Ϊ�ڷ��ʼ�ʱֱ�ӽ����˳�ʼ������������ֻ��Ҫupdate���ɣ�����û�г�ʼ���ˣ�ֻ���ڴ˽���insert����
					secondPassService.insertSecondPass(Integer.parseInt(uPk),secondPass);
					request.setAttribute("secondPass",secondPass);
					request.setAttribute("authenPass", "shizhen");
					
					//��ȷ���޸Ķ��������, ɾ���޸Ķ��������ʼ�
					MailInfoService mailInfoService = new MailInfoService();
					mailInfoService.deleteSecondPassMail(Integer.parseInt(uPk));
					return mapping.findForward("sussendPass");
				}	
			}		
			String hint = "�Բ���������Ķ���������ϲ����Ϲ涨����Ϸ�ʺŵĶ�������Ϊ6λ0��9��ֵ���!";
			request.setAttribute("hint", hint);
			return mapping.findForward("failedPass");
		}		
		//String hint = "�������벻��Ϊ��!";
		
		SecondPassService secondPassService = new SecondPassService();
		
		String hint = secondPassService.getHasSetSecondPassword(uPk);
		request.setAttribute("hint", hint);
		return mapping.findForward("failedPass");
	}	
		
	
	// ���ö�������
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String pass_word = request.getParameter("pass_word");
		
		if(pass_word != null && !pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(pass_word)) {			//��¼����Ϊ���ֺ���ĸ�Ļ��
				if(pass_word.length() >=5 && pass_word.length() <= 11) {	//��¼����ĳ�����5��11֮��	
					SecondPassDao seconddao = new SecondPassDao();
					String uPk = (String)request.getSession().getAttribute("uPk");
					logger.info("�޸�����ʱ��upk="+uPk);
					
					String oldPass = seconddao.getUserLoginPawByUPk(Integer.parseInt(uPk));
					String newPass = MD5.getMD5Str(pass_word);
					if(!newPass.equals(oldPass)) {				//�����¼����Ͷ���������ͬ�Ͳ�����ͨ��,���������.
						SecondPassService secondPassService = new SecondPassService();
						secondPassService.updateLoginPaw(Integer.parseInt(uPk),newPass);
						request.setAttribute("pass_word", pass_word);
						return mapping.findForward("sussendupdateLoginPsw");
					}else {
						String hint = "���Ķ������벻�ܺ͵�¼������ͬ!";
						request.setAttribute("hint", hint);
						return mapping.findForward("password_wrong");
					}
				}
			}
			String hint = "�Բ����������������ϲ����Ϲ涨����Ϸ�ʺ�����Ϊ5-11λ,0-9,a-z���!";
			request.setAttribute("hint", hint);
			return mapping.findForward("password_wrong");
		}
		String hint = "�Բ���, �����������������Ϊ��������!";
		request.setAttribute("hint", hint);
		return mapping.findForward("password_wrong");
	}
}
