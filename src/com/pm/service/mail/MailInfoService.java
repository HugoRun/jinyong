package com.pm.service.mail;

import org.apache.commons.lang.StringUtils;

import com.ls.model.item.ItemContainer;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.pm.dao.mail.MailBonusDao;
import com.pm.dao.mail.MailInfoDao;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.mail.MailInfoVO;

public class MailInfoService
{
	public final static int OTHER_MAIL=1;
	public final static int SYSTEM_MAIL=2;
	public final static int ITEM_MAIL=3;
	public final static int F_INVITE_MAIL=4;//帮派邀请邮件
	public final static int F_DISBAND_MAIL=5;//帮派解散提醒邮件
	
	public final static int HAVE_ATTACHMENT=10;//有附件的邮件
	
	
	/**
	 * 接收邮件附件
	 */
	public String receiveAttachment(RoleEntity roleInfo,MailInfoVO mailInfo )
	{
		if( mailInfo==null || mailInfo.getMailType()!=HAVE_ATTACHMENT || StringUtils.isEmpty(mailInfo.getAttachmentStr()))
		{
			return "该邮件没有附件可以领取";
		}
		
		ItemContainer attachment = mailInfo.getAttachment();
		
		String hint = attachment.gainItems(roleInfo,GameLogManager.G_EMAIL);
		if( hint!=null )
		{
			return hint;
		}
		
		return null;
	}
	
	/**
	 * 查看是否有新邮件
	 * @prarm pPk 个人角色id
	 * @return if have new mail,it will return the number of new mail,else return -1;
	 */
	public int havingNewMail(String pPk){
		MailInfoDao mailInfoDao = new MailInfoDao();
		return mailInfoDao.havingNewMail(pPk);
	}	
	
	/**
	 * 查看个人所有邮件列表
	 * @param pPk 个人角色id
	 * @return list 
	 */
	public QueryPage getPersonMailList(String pPk,int page_no){
		MailInfoDao mailDao = new MailInfoDao();
		return mailDao.getPersonMailList(pPk,page_no);
	}
	
	/**
	 * 根据id查看邮件
	 * @param pPk 个人角色id
	 * @return MailInfoVO 
	 */
	public MailInfoVO getPersonMailView(String mailId){
		MailInfoDao mailDao = new MailInfoDao();
		return mailDao.getPersonMailView(mailId);
	}
	
	/** 根据邮件id删除邮件  */
	public String deleteMailByid(String mailId,int u_pk,int pPk){
		MailInfoDao mailDao = new MailInfoDao();
		StringBuffer sf = new StringBuffer();
		int mail_type = mailDao.getMailTypeById(mailId);
		if(mail_type == 4) {
			SecondPassDao secondPass = new SecondPassDao();
			String hasSetting = secondPass.getUserLoginPawByUPk(u_pk);
			if(hasSetting == null || hasSetting.equals("")) {	
				sf.append("您还没有设置过二级密码,故此邮件不可被删除!");
			}
		} 
		else if ( mail_type == 6) {
			sf.append("特殊邮件,无法删除,请打开此邮件,点击确定,取回金钱或"+GameConfig.getYuanbaoName()+"后邮件可自动删除!");
		}
		else {
		
			int i = mailDao.deleteMailByid(mailId,pPk);
		
			if(i == -1 || i ==0){
				sf.append("删除邮件失败，请再试一次！");
			}else {
				sf.append("您成功删除了此邮件！");
			}
		
			//调用删除全部超过七日的邮件的方法
			//mailDao.deleteMailIfOutSeven();
		}
		
		return sf.toString();
	}
	
	/** 根据邮件id删除邮件  */
	public String deleteMailByid(String mailId,int pPk){
		MailInfoDao mailDao = new MailInfoDao();
		
		int i = mailDao.deleteMailByid(mailId,pPk);
		StringBuffer sf = new StringBuffer();
		
		if(i == -1 || i ==0){
			sf.append("删除邮件失败，请再试一次！");
		}else {
			sf.append("您成功删除了此邮件！");
		}
		
		//调用删除全部超过七日的邮件的方法
		//mailDao.deleteMailIfOutSeven();
		
		
		return sf.toString();
	}
	/**
	 * 根据pPk 删除个人所有邮件
	 * @param pk 个人角色id
	 * @return if delete sussens return 1,else return -1;
	 */
	public String deletePersonMailBypPk(String pk){
		MailInfoDao mailDao = new MailInfoDao();
		StringBuffer sb = new StringBuffer();
		int i = mailDao.deletePersonMailBypPk(pk);
		if(i == -1 || i == 0){
			sb.append("删除所有邮件未成功！");
		}else {
			sb.append("您已成功删除所有邮件！");
		}
		return sb.toString();
	}
	
	/**
	 * 根据mailId将邮件的阅读状态置为已读
	 * @param mailId 
	 */
	public void updateMailRead(String mailId){
		MailInfoDao mailDao = new MailInfoDao();
		mailDao.updateMailRead(mailId);
	}
	
	
	/**
	 * 发送普通邮件功能
	 * @param receive_pk 接收者pk
	 * @param send_pk 	发送者pk
	 * @param title 	邮件标题
	 * @param content   邮件内容
	 * @return  
	 */
	public String sendPersonMail(int receive_pk,int send_pk,String title,String content){
		StringBuffer sb = new StringBuffer();
		int i = this.insertMail(receive_pk, send_pk,1,title,content,1);
		if(i == -1){
			sb.append("邮件未发出！");
		}else {
			sb.append("邮件发送成功！");
		}
		return sb.toString();
	}
	
	/**
	 * 发送系统邮件功能
	 * @param receive_pk 接收者pk
	 * @param send_pk 	发送者pk
	 * @param title 	邮件标题
	 * @param content   邮件内容
	 * @return
	 */
	public int sendMailBySystem(int receive_pk,String title,String content){
		return this.insertMail(receive_pk,-1,2,title,content,1);
	}
	
	/**
	 * 发送系统邮件功能,同时发送系统消息(浩军专用)
	 * @param receive_pk 接收者pk
	 * @param send_pk 	发送者pk
	 * @param title 	邮件标题
	 * @param content   邮件内容
	 * @return  
	 */
	public int sendMailAndSystemInfo(int receive_pk,String title,String content){
		SystemInfoService infoService = new SystemInfoService();
		infoService.insertSystemInfoBySystem(receive_pk,content);
		return insertMail(receive_pk,-1,2,title,content,1);
		
		
	}
	
	/**
	 * 发送邮件功能
	 * @param receive_pk 接收者pk
	 * @param send_pk 	发送者pk
	 * @param mail_type 邮件类型 1为普通个人邮件,2为系统邮件.4为设置二级密码邮件. 5即时删除类型,6为元宝拍卖场邮件
	 * 								
	 * @param title 	邮件标题
	 * @param content   邮件内容
	 * @return  
	 */
	public String sendMail(int receive_pk,int send_pk,int mail_type,String title,String content){
		
		StringBuffer sb = new StringBuffer();
		if(content == null || content.equals("")) {
			return "请不要发空邮件";
		}
		int i = this.insertMail(receive_pk, send_pk,mail_type,title,content,1);
		if(i == -1){
			sb.append("邮件未发出！");
		}else {
			sb.append("邮件发送成功！");
		}
		return sb.toString();
	}
	
	/**
	 * 发送邮件功能
	 * @param receive_pk 接收者pk
	 * @param send_pk 	发送者pk
	 * @param mail_type 邮件类型 1为普通个人邮件,2为系统邮件.4为设置二级密码邮件. 5即时删除类型,6为元宝拍卖场邮件
	 * @param title 	邮件标题
	 * @param content   邮件内容
	 * @return  
	 */
	public String sendMailUseByZjj(int receive_pk,int send_pk,int mail_type,String title,String content){
		
		if (content.length() > 100) {
			return "您邮件的内容太长!";
		}
		StringBuffer sb = new StringBuffer();
		if(content == null || content.equals("")) {
			return "请不要发空邮件";
		}
		int i = this.insertMail(receive_pk, send_pk,mail_type,title,content,1);
		if(i == -1){
			sb.append("邮件未发出！");
		}else {
			sb.append("邮件发送成功！");
		}
		return sb.toString();
	}
	
	/**
	 * 发送邮件功能,此函数包含重要性参数，
	 * @param receive_pk 接收者pk
	 * @param send_pk 	发送者pk
	 * @param mail_type 邮件类型 1为普通个人邮件，2为系统邮件.4为设置二级密码邮件. 5即时删除类型,6为元宝拍卖场邮件
	 * @param title 	邮件标题
	 * @param content   邮件内容
	 * @param improtant 重要性参数，默认为1，数字越大重要性越高.此为已读未读之后第二优先排序
	 * @return  if sussend return 1,else return -1.
	 */
	public int sendMailReply(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		return this.insertMail(receive_pk, send_pk,mail_type, title, content,improtant);
	}
	

	/**
	 * 删除二级密码设置邮件
	 * @param pk
	 */
	public void deleteSecondPassMail(int pk)
	{
		MailInfoDao mailDao = new MailInfoDao();		
		mailDao.deleteSecondPassMail(pk);
	}

	/**
	 * 给玩家发送九级通知邮件
	 * @param pk
	*/
	public void send9Grademail(int p_pk)
	{		
		String mailTitle = "9级通知";
		String content = "恭喜您等级达到9级,当您完成\"初出茅庐\"任务后,可在村口点击车把式传往临安,开始新的冒险.";
		sendMail(p_pk,-1,2, mailTitle, content);
	} 

	/**
	 *  返回个人是否有邮件类型为mail_type的邮件
	 * @param pk
	 * @return
	 */
	public MailInfoVO getPersonMailTypeList(String pk,int mail_type)
	{
		MailInfoDao mailDao = new MailInfoDao();
		return mailDao.getPersonMailTypeList(pk,mail_type);
	}

	/**
	 * 给玩家发送 包裹剩余空格数不够的邮件
	 * @param p_pk
	 */
	public void sendWrapSpareMail(int p_pk)
	{
		String mailTitle = "您的包裹剩余空间不多了!";
		String content = "您的包裹剩余空间不多了，请注意清理以免丢失物品!";
		sendMail(p_pk,-1,2, mailTitle, content);
		
	}
	
	public void updateMail(int mailId, String content){
		new MailInfoDao().updateMail(mailId, content);
	}
	
	public int insertMailReturnId(int receive_pk,String title,String content){
		return insertMail(receive_pk, -1, 2, title, content, 1);
	}
	
	/**奖励邮件发送  给玩家发送奖励邮件  并在奖励表里添加数据  奖励邮件类型为3*/
	public void insertBonusMail(int p_pk,String title,String content,String bonus){
		int mail_id = insertMail(p_pk, -1, 3, title, content, 1);
		if(mail_id != -1){
		MailBonusDao dao = new MailBonusDao();
		dao.insertBonus(p_pk, mail_id, bonus);
		}
	}
	
	/**
	 * 发送邮件
	 * @param mail
	 * @return
	 */
	public int sendMail(MailInfoVO mail){
		if( mail==null )
		{
			return -1;
		}
		return new MailInfoDao().addAndReturnKey(mail);
	}
	
	/**
	 * 插入邮件信息
	 * @prarm receive_pk 	收信人id 
	 * @prarm send_pk		发信人id
	 * @param mail_type		邮件类型，1为普通邮件，2为系统。
	 * @prarm title 		标题
	 * @param context 		内容
	 * @param improtant  	重要性，默认为1，数字越大越重要
	 * @return 				返回邮件主键
	 */
	private int insertMail(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		return new MailInfoDao().addAndReturnKey(receive_pk, send_pk, mail_type, title, content, improtant);
	}
}
