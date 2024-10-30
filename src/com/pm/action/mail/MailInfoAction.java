package com.pm.action.mail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.web.action.ActionBase;
import com.lw.service.lottery.DrawALotteryService;
import com.pm.dao.mail.MailBonusDao;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.mail.MailBonusVO;
import com.pm.vo.mail.MailInfoVO;

public class MailInfoAction extends ActionBase {
	/**
	 *  ����ʼ��б�
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		RoleEntity roleInfo = this.getRoleEntity(request);
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
			String page_no_str = request.getParameter("page_no");
			int page_no;
			
			if( page_no_str==null || page_no_str.equals("")) {
				page_no = 1;
			}else {
				page_no = Integer.parseInt(page_no_str);
			}
			
			MailInfoService mailService = new MailInfoService();
			QueryPage mail_info = mailService.getPersonMailList(p_pk+"",page_no);
		
			
			request.setAttribute("mail_info",mail_info);
			
			return mapping.findForward("mailList");
	}
	
	/**
	 *  ѡ��鿴ָ���ʼ�
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String mailId = request.getParameter("mailId");
		String page_no = request.getParameter("page_no");
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		MailInfoService mailService = new MailInfoService();
		MailInfoVO mailInfoVO = mailService.getPersonMailView(mailId);
		
		//���Ķ������ʼ���Ϊ�Ѷ�
		mailService.updateMailRead(mailId);
		//�ж�Ϊ��ʱɾ���ʼ������ʼ�ɾ��
		if(mailInfoVO.getMailType() == 5){
			mailService.deleteMailByid(mailId,u_pk,roleInfo.getBasicInfo().getPPk());
		}
		request.setAttribute("mailInfo",mailInfoVO);
		request.setAttribute("page_no",page_no);
		return mapping.findForward("mailView");
	}
	
	/**
	 *  ɾ��ָ���ʼ�
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		
		String mailId = request.getParameter("mailId");
				
		MailInfoService mailService = new MailInfoService();
			
		String resultWml =mailService.deleteMailByid(mailId,u_pk,roleInfo.getBasicInfo().getPPk());
		request.setAttribute("resultWml",resultWml);
		 
		return mapping.findForward("deleteMail");
	}
	
	/**
	 *  ɾ������ȫ��ָ���ʼ�
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleEntity roleInfo = this.getRoleEntity(request);
		int u_pk = roleInfo.getBasicInfo().getUPk();
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		String deleteSure = request.getParameter("deleteSure");
		
		MailInfoService mailService = new MailInfoService();
		MailInfoVO mailInfoVO = mailService.getPersonMailTypeList(p_pk+"",4);
		
		if(mailInfoVO != null) {
			SecondPassDao secondPass = new SecondPassDao();
			String hasSetting = secondPass.getUserLoginPawByUPk(u_pk);
			if(hasSetting == null || hasSetting.equals("")) {
				this.setHint(request, "����û�����ù���������,�������������ȫ��ɾ��!");
				return mapping.findForward("send_hint");
			}
		}
		
		mailInfoVO = mailService.getPersonMailTypeList(p_pk+"",6);
		if(mailInfoVO != null) {
			this.setHint(request, "�ʼ����������ʼ�("+GameConfig.getYuanbaoName()+"�����ʼ�),��򿪴��ʼ�,���ȷ��,ȡ�ؽ�Ǯ��Ԫ�����ʼ��Զ�ɾ��,Ȼ����ȥȫ��ɾ��!");
			return mapping.findForward("send_hint");
		}
		
		if(deleteSure == null || deleteSure == "" || deleteSure.equals("")){
	
			return mapping.findForward("deletesure");
		}else if(deleteSure.equals("deleteSure")){
			String resultWml =mailService.deletePersonMailBypPk(p_pk+"");
			request.setAttribute("resultWml",resultWml);
			return mapping.findForward("deleteMail");
		}else {
			return mapping.findForward("mailList");
		}
	}
	
	//�ʼ���ȡ����
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String mailId = request.getParameter("mail_id");
		DrawALotteryService ds = new DrawALotteryService();
		RoleEntity roleInfo = this.getRoleEntity(request);
		if(mailId!=null&&!mailId.equals("")){
			MailBonusDao dao = new MailBonusDao();
			MailBonusVO vo = dao.getMailBonus(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(mailId.trim()));
			String display = ds.playerCatchMoney(roleInfo.getBasicInfo().getPPk(), vo.getBonus(), Integer.parseInt(mailId.trim()));
			if(display == null||display.equals("")||display.equals("null")){
				request.setAttribute("resultWml","����Ԥ������������������ȡ!");
				return mapping.findForward("deleteMail");
			}else{
				request.setAttribute("resultWml",display);
				return mapping.findForward("deleteMail");
			}
		}else{
			request.setAttribute("resultWml","�ʼ�����");
			return mapping.findForward("deleteMail");
		}
	}
	/**
	 * ��ȡPK���ʸ�����Ʒ
	 */
	public ActionForward receiveAttachment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RoleEntity role_info = this.getRoleEntity(request);
		
		String mailId = request.getParameter("mailId");
		
		MailInfoService mailService = new MailInfoService();
		
		MailInfoVO mail_info = mailService.getPersonMailView(mailId);
		
		if( mail_info!=null )
		{
			String hint =  mailService.receiveAttachment(role_info, mail_info);
			if( hint==null )
			{
				mailService.deleteMailByid(mailId, role_info.getPPk());
				this.setHint(request, "�ɹ���ȡ����");
				return mapping.findForward("send_hint");
			}
			else
			{
				this.setHint(request, hint);
				return this.n2(mapping, form, request, response);
			}
		}
		
		return this.n1(mapping, form, request, response);
	}
}
