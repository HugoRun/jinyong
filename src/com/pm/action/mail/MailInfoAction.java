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
	 *  获得邮件列表
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
	 *  选择查看指定邮件
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String mailId = request.getParameter("mailId");
		String page_no = request.getParameter("page_no");
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		int u_pk = roleInfo.getBasicInfo().getUPk();
		MailInfoService mailService = new MailInfoService();
		MailInfoVO mailInfoVO = mailService.getPersonMailView(mailId);
		
		//将阅读过的邮件置为已读
		mailService.updateMailRead(mailId);
		//判断为及时删除邮件将该邮件删除
		if(mailInfoVO.getMailType() == 5){
			mailService.deleteMailByid(mailId,u_pk,roleInfo.getBasicInfo().getPPk());
		}
		request.setAttribute("mailInfo",mailInfoVO);
		request.setAttribute("page_no",page_no);
		return mapping.findForward("mailView");
	}
	
	/**
	 *  删除指定邮件
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
	 *  删除个人全部指定邮件
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
				this.setHint(request, "您还没有设置过二级密码,请设置密码后再全部删除!");
				return mapping.findForward("send_hint");
			}
		}
		
		mailInfoVO = mailService.getPersonMailTypeList(p_pk+"",6);
		if(mailInfoVO != null) {
			this.setHint(request, "邮件中有特殊邮件("+GameConfig.getYuanbaoName()+"拍卖邮件),请打开此邮件,点击确定,取回金钱或元宝后邮件自动删除,然后再去全部删除!");
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
	
	//邮件领取奖励
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
				request.setAttribute("resultWml","请您预留包裹格数后再来领取!");
				return mapping.findForward("deleteMail");
			}else{
				request.setAttribute("resultWml",display);
				return mapping.findForward("deleteMail");
			}
		}else{
			request.setAttribute("resultWml","邮件错误");
			return mapping.findForward("deleteMail");
		}
	}
	/**
	 * 领取PK掉率附件物品
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
				this.setHint(request, "成功领取附件");
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
