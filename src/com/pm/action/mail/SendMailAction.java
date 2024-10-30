package com.pm.action.mail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.PropertyService;
import com.ls.web.service.player.RoleService;
import com.pm.service.mail.MailInfoService;
import com.pub.ben.info.Expression;
import com.web.service.friend.BlacklistService;

public class SendMailAction extends DispatchAction {

	/*
	 * Generated Methods
	 */
	Logger logger = Logger.getLogger("log.action");
	
	/**
	 *  ����ûظ����ܷ����ʼ�
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		String sendPk = request.getParameter("sendPk");
		BlacklistService blacklistService = new BlacklistService();
		if(blacklistService.whetherblacklist(Integer.parseInt(sendPk),roleInfo.getBasicInfo().getPPk()+"") == false)
		{
			String hints = "�Է��Խ������������,����������Է������ʼ�!";
			request.setAttribute("hints", hints);
			return mapping.findForward("blacklisthint");
		}
		
		String mail_type = request.getParameter("mail_type");
		//String title = request.getParameter("mail_title");
		String content = request.getParameter("mail_content");
		logger.info("mailTiTle=,mail_content="+content);
		String resultWml = "";
		
		boolean flag = false ;
		if(Expression.hasPublish(content) == -1) {
			resultWml = "�������˷Ƿ�����, ����������!";
		}else {		
			if(mail_type.equals("1")){
				MailInfoService mailInfoService = new MailInfoService();
				/*RoleEntity role_info = roleService.getRoleInfoById(sendPk);
				if(role_info!=null){
					String mail_title = "����"+role_info.getBasicInfo().getName() +"���ʼ�";//7��27���޸�
					resultWml = mailInfoService.sendMailUseByZjj(Integer.valueOf(sendPk),p_pk,1,mail_title,content);
				}else{
					PropertyService propertyService = new PropertyService();
					String mail_title = "����"+propertyService.getPlayerName(Integer.parseInt(sendPk)) +"���ʼ�";//7��27���޸�
					resultWml = mailInfoService.sendMailUseByZjj(Integer.valueOf(sendPk),p_pk,1,mail_title,content);
				}*/
				String mail_title = "����"+roleInfo.getBasicInfo().getName() +"���ʼ�";//7��27���޸�
				resultWml = mailInfoService.sendMailUseByZjj(Integer.valueOf(sendPk),p_pk,1,mail_title,content);
			} else {
				resultWml = "ϵͳ�ʼ�������ظ���";
			}
		}
		request.setAttribute("resultWml",resultWml);
		return mapping.findForward("send_hint");
	}
	
	/**
	 *  ���ֱ��д�ʼ�����
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		
		String receive_name = request.getParameter("receive_name");
		//String title = request.getParameter("mail_title");
		String content = request.getParameter("mail_content");
		
		
		StringBuffer resultWml = new StringBuffer();
		if ( receive_name.length() > 10) {
			resultWml.append("����������������!");
			request.setAttribute("resultWml",resultWml.toString());
			return mapping.findForward("send_hint");
		}
		if(Expression.hasPublishWithMail(receive_name) == -1) {
			resultWml.append("����������������!");
			
		} else {
			PartInfoDao partInfoDao = new PartInfoDao();
			MailInfoService mailInfoService = new MailInfoService();
			int sendPk =partInfoDao.getByName(receive_name);
			logger.info("sendPk="+sendPk);
			
			//�ж�����Ƿ������Լ��ĺ�����
			BlacklistService blacklistService = new BlacklistService();
			int res = blacklistService.isBlacklist(p_pk, sendPk);
			if(res == 1){
				String hints = "����������ĺ�������,����������(��)�����ʼ�ͨ��.";
				request.setAttribute("hints", hints);
				return mapping.findForward("blacklisthint");
			}else if(res == 2){
				String hints = "���ڸ���ҵĺ�������,����������(��)�����ʼ�ͨ��.";
				request.setAttribute("hints", hints);
				return mapping.findForward("blacklisthint");
			}
			//����ύ��ұ���������� ����
			if(blacklistService.whetherblacklist(sendPk,roleInfo.getBasicInfo().getPPk()+"") == false)
			{
				String hints = "�Է��Խ������������,����������Է������ʼ�!";
				request.setAttribute("hints", hints);
				return mapping.findForward("blacklisthint");
			}
			if(sendPk == -1 || sendPk == 0){
				resultWml.append("�����ڸ���ң�");
			} else {
				if(Expression.hasPublish(content) == -1) {
					resultWml.append("�������˷Ƿ�����, ����������!");
				} else {
					/*RoleEntity role_info = roleService.getRoleInfoById(sendPk+"");
					if(role_info!=null){
						String mail_title = "����"+role_info.getBasicInfo().getName() +"���ʼ�";//7��27���޸�
						resultWml.append(mailInfoService.sendMailUseByZjj(Integer.valueOf(sendPk),p_pk,1,mail_title,content));
					}else{
						PropertyService propertyService = new PropertyService();
						String mail_title = "����"+propertyService.getPlayerName(sendPk) +"���ʼ�";//7��27���޸�
						resultWml.append(mailInfoService.sendMailUseByZjj(Integer.valueOf(sendPk),p_pk,1,mail_title,content));
					}*/
					String mail_title = "����"+roleInfo.getBasicInfo().getName() +"���ʼ�";//7��27���޸�
					resultWml.append(mailInfoService.sendMailUseByZjj(Integer.valueOf(sendPk),p_pk,1,mail_title,content));
				}
			}
		}
		
		request.setAttribute("hint",resultWml.toString());
		return mapping.findForward("send_hint");
	}
	
	/**
	 *  ��ҵ��д�ʼ���ת���ʼ�����ҳ��
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("writeMail");
	}
	
	/**
	 *  ��ҵ���ָ��ʼ���ת���ʼ�����ҳ��
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String sendPk = (String)request.getParameter("sendPk");
		String mail_type = (String) request.getParameter("mail_type");
		String from_type = (String) request.getParameter("from_type");
		
		PartInfoDao partInfoDao = new PartInfoDao();
		String senderName = partInfoDao.getNameByPpk(Integer.valueOf(sendPk));
		
		
		request.setAttribute("sendPk", sendPk);
		request.setAttribute("senderName", senderName);
		request.setAttribute("mail_type", mail_type);
		request.setAttribute("from_type", from_type);
		return mapping.findForward("write_back");
	}
}
