package com.pm.action.secondPass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ben.vo.logininfo.LoginInfoVO;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.StringUtil;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.secondpass.SecondPassService;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;

public class ZhiModifyPasswordAction extends DispatchAction 
{

	Logger logger = Logger.getLogger("log.action");
	//ת��δ��¼ǰ���޸ĵ�¼����Ķ�����������ҳ��
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String uName = request.getParameter("name");
		LoginInfoDAO infodao = new LoginInfoDAO();
		LoginInfoVO infovo = infodao.getUserInfoLoginName(uName);
		int uPk = infovo.getUPk();
		if(uPk == 0) {
			String uPk1 = (String)request.getSession().getAttribute("uPk");
			uPk = Integer.valueOf(uPk1);
		} 
		logger.info("uPk1111="+uPk);
		
		request.getSession().setAttribute("uPk", uPk+"");
		request.setAttribute("uPk", uPk+"");
		request.setAttribute("authenPass", "erjipasswordistrue");
		request.setAttribute("zhijie", "henzhijie");
		return mapping.findForward("startPass");
		
	}
	
	//����������������֤
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String uPk1 = (String)request.getSession().getAttribute("uPk");
		if(uPk1 == null ){
			uPk1 = request.getParameter("uPk");
		}
		request.setAttribute("uPk", uPk1);
		logger.info("uPk="+uPk1);
		int uPk = Integer.valueOf(uPk1);
		
		String second_pass = request.getParameter("second_pass");
		logger.info("second_pass="+second_pass);
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
								hint = "������ĸ��ʺŵĶ������������24Сʱ���������δ���Ķ������룬���ʺŵĵ�¼����������24Сʱ�ڲ��ɱ�����!";
								
							} else {
								hint = "���ʺ��ѱ�������24Сʱ�ڲ����޸���Ϸ��¼���룡";
							}
							request.setAttribute("hint", hint);
							return mapping.findForward("failedPass");
						}
					//����Ѿ�����3,�͸�����Ҳ����ٺ˶���.	
					} else {
						secondPassService.insertErrorSecondPsw(uPk); 
						String hint = "���ʺ��ѱ�������24Сʱ�ڲ����޸���Ϸ��¼���룡";
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
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String uPk1 = (String)request.getSession().getAttribute("uPk");
		if(uPk1 == null ){
			uPk1 = request.getParameter("uPk");
		}
		
		logger.info("uPk="+uPk1);
		int uPk = Integer.valueOf(uPk1);
		
		String getTodayStr = DateUtil.getTodayStr();
		String auth = MD5.getMD5Str(getTodayStr).substring(1,6);
		request.setAttribute("auth", auth);
		
		String pass_word = request.getParameter("pass_word");
		logger.info("pass_word="+pass_word);
		
		if(pass_word != null && !pass_word.equals("")) {		
			if(StringUtil.isNumberAndChar(pass_word)) {			//��¼����Ϊ���ֺ���ĸ�Ļ��
				if(pass_word.length() >=5 && pass_word.length() <= 11) {	//��¼����ĳ�����5��11֮��	
					SecondPassDao seconddao = new SecondPassDao();
					String oldPass = seconddao.getUserLoginPawByUPk(uPk);
					String newPass = MD5.getMD5Str(pass_word);
					if(!newPass.equals(oldPass)) {				//�����¼����Ͷ���������ͬ�Ͳ�����ͨ��,���������.
						SecondPassService secondPassService = new SecondPassService();
						secondPassService.updateLoginPaw(uPk,newPass);
						request.setAttribute("pass_word", pass_word); 
						return mapping.findForward("sussendupdateLoginPsw");
					}else {
						String hint = "���Ķ������벻�ܺ͵�¼��ͬ!";
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
	
	//ת��δ��¼ǰ���޸ĵ�¼����Ķ�����������ҳ��
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		return mapping.findForward("");
	}

}
