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
	public final static int F_INVITE_MAIL=4;//���������ʼ�
	public final static int F_DISBAND_MAIL=5;//���ɽ�ɢ�����ʼ�
	
	public final static int HAVE_ATTACHMENT=10;//�и������ʼ�
	
	
	/**
	 * �����ʼ�����
	 */
	public String receiveAttachment(RoleEntity roleInfo,MailInfoVO mailInfo )
	{
		if( mailInfo==null || mailInfo.getMailType()!=HAVE_ATTACHMENT || StringUtils.isEmpty(mailInfo.getAttachmentStr()))
		{
			return "���ʼ�û�и���������ȡ";
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
	 * �鿴�Ƿ������ʼ�
	 * @prarm pPk ���˽�ɫid
	 * @return if have new mail,it will return the number of new mail,else return -1;
	 */
	public int havingNewMail(String pPk){
		MailInfoDao mailInfoDao = new MailInfoDao();
		return mailInfoDao.havingNewMail(pPk);
	}	
	
	/**
	 * �鿴���������ʼ��б�
	 * @param pPk ���˽�ɫid
	 * @return list 
	 */
	public QueryPage getPersonMailList(String pPk,int page_no){
		MailInfoDao mailDao = new MailInfoDao();
		return mailDao.getPersonMailList(pPk,page_no);
	}
	
	/**
	 * ����id�鿴�ʼ�
	 * @param pPk ���˽�ɫid
	 * @return MailInfoVO 
	 */
	public MailInfoVO getPersonMailView(String mailId){
		MailInfoDao mailDao = new MailInfoDao();
		return mailDao.getPersonMailView(mailId);
	}
	
	/** �����ʼ�idɾ���ʼ�  */
	public String deleteMailByid(String mailId,int u_pk,int pPk){
		MailInfoDao mailDao = new MailInfoDao();
		StringBuffer sf = new StringBuffer();
		int mail_type = mailDao.getMailTypeById(mailId);
		if(mail_type == 4) {
			SecondPassDao secondPass = new SecondPassDao();
			String hasSetting = secondPass.getUserLoginPawByUPk(u_pk);
			if(hasSetting == null || hasSetting.equals("")) {	
				sf.append("����û�����ù���������,�ʴ��ʼ����ɱ�ɾ��!");
			}
		} 
		else if ( mail_type == 6) {
			sf.append("�����ʼ�,�޷�ɾ��,��򿪴��ʼ�,���ȷ��,ȡ�ؽ�Ǯ��"+GameConfig.getYuanbaoName()+"���ʼ����Զ�ɾ��!");
		}
		else {
		
			int i = mailDao.deleteMailByid(mailId,pPk);
		
			if(i == -1 || i ==0){
				sf.append("ɾ���ʼ�ʧ�ܣ�������һ�Σ�");
			}else {
				sf.append("���ɹ�ɾ���˴��ʼ���");
			}
		
			//����ɾ��ȫ���������յ��ʼ��ķ���
			//mailDao.deleteMailIfOutSeven();
		}
		
		return sf.toString();
	}
	
	/** �����ʼ�idɾ���ʼ�  */
	public String deleteMailByid(String mailId,int pPk){
		MailInfoDao mailDao = new MailInfoDao();
		
		int i = mailDao.deleteMailByid(mailId,pPk);
		StringBuffer sf = new StringBuffer();
		
		if(i == -1 || i ==0){
			sf.append("ɾ���ʼ�ʧ�ܣ�������һ�Σ�");
		}else {
			sf.append("���ɹ�ɾ���˴��ʼ���");
		}
		
		//����ɾ��ȫ���������յ��ʼ��ķ���
		//mailDao.deleteMailIfOutSeven();
		
		
		return sf.toString();
	}
	/**
	 * ����pPk ɾ�����������ʼ�
	 * @param pk ���˽�ɫid
	 * @return if delete sussens return 1,else return -1;
	 */
	public String deletePersonMailBypPk(String pk){
		MailInfoDao mailDao = new MailInfoDao();
		StringBuffer sb = new StringBuffer();
		int i = mailDao.deletePersonMailBypPk(pk);
		if(i == -1 || i == 0){
			sb.append("ɾ�������ʼ�δ�ɹ���");
		}else {
			sb.append("���ѳɹ�ɾ�������ʼ���");
		}
		return sb.toString();
	}
	
	/**
	 * ����mailId���ʼ����Ķ�״̬��Ϊ�Ѷ�
	 * @param mailId 
	 */
	public void updateMailRead(String mailId){
		MailInfoDao mailDao = new MailInfoDao();
		mailDao.updateMailRead(mailId);
	}
	
	
	/**
	 * ������ͨ�ʼ�����
	 * @param receive_pk ������pk
	 * @param send_pk 	������pk
	 * @param title 	�ʼ�����
	 * @param content   �ʼ�����
	 * @return  
	 */
	public String sendPersonMail(int receive_pk,int send_pk,String title,String content){
		StringBuffer sb = new StringBuffer();
		int i = this.insertMail(receive_pk, send_pk,1,title,content,1);
		if(i == -1){
			sb.append("�ʼ�δ������");
		}else {
			sb.append("�ʼ����ͳɹ���");
		}
		return sb.toString();
	}
	
	/**
	 * ����ϵͳ�ʼ�����
	 * @param receive_pk ������pk
	 * @param send_pk 	������pk
	 * @param title 	�ʼ�����
	 * @param content   �ʼ�����
	 * @return
	 */
	public int sendMailBySystem(int receive_pk,String title,String content){
		return this.insertMail(receive_pk,-1,2,title,content,1);
	}
	
	/**
	 * ����ϵͳ�ʼ�����,ͬʱ����ϵͳ��Ϣ(�ƾ�ר��)
	 * @param receive_pk ������pk
	 * @param send_pk 	������pk
	 * @param title 	�ʼ�����
	 * @param content   �ʼ�����
	 * @return  
	 */
	public int sendMailAndSystemInfo(int receive_pk,String title,String content){
		SystemInfoService infoService = new SystemInfoService();
		infoService.insertSystemInfoBySystem(receive_pk,content);
		return insertMail(receive_pk,-1,2,title,content,1);
		
		
	}
	
	/**
	 * �����ʼ�����
	 * @param receive_pk ������pk
	 * @param send_pk 	������pk
	 * @param mail_type �ʼ����� 1Ϊ��ͨ�����ʼ�,2Ϊϵͳ�ʼ�.4Ϊ���ö��������ʼ�. 5��ʱɾ������,6ΪԪ���������ʼ�
	 * 								
	 * @param title 	�ʼ�����
	 * @param content   �ʼ�����
	 * @return  
	 */
	public String sendMail(int receive_pk,int send_pk,int mail_type,String title,String content){
		
		StringBuffer sb = new StringBuffer();
		if(content == null || content.equals("")) {
			return "�벻Ҫ�����ʼ�";
		}
		int i = this.insertMail(receive_pk, send_pk,mail_type,title,content,1);
		if(i == -1){
			sb.append("�ʼ�δ������");
		}else {
			sb.append("�ʼ����ͳɹ���");
		}
		return sb.toString();
	}
	
	/**
	 * �����ʼ�����
	 * @param receive_pk ������pk
	 * @param send_pk 	������pk
	 * @param mail_type �ʼ����� 1Ϊ��ͨ�����ʼ�,2Ϊϵͳ�ʼ�.4Ϊ���ö��������ʼ�. 5��ʱɾ������,6ΪԪ���������ʼ�
	 * @param title 	�ʼ�����
	 * @param content   �ʼ�����
	 * @return  
	 */
	public String sendMailUseByZjj(int receive_pk,int send_pk,int mail_type,String title,String content){
		
		if (content.length() > 100) {
			return "���ʼ�������̫��!";
		}
		StringBuffer sb = new StringBuffer();
		if(content == null || content.equals("")) {
			return "�벻Ҫ�����ʼ�";
		}
		int i = this.insertMail(receive_pk, send_pk,mail_type,title,content,1);
		if(i == -1){
			sb.append("�ʼ�δ������");
		}else {
			sb.append("�ʼ����ͳɹ���");
		}
		return sb.toString();
	}
	
	/**
	 * �����ʼ�����,�˺���������Ҫ�Բ�����
	 * @param receive_pk ������pk
	 * @param send_pk 	������pk
	 * @param mail_type �ʼ����� 1Ϊ��ͨ�����ʼ���2Ϊϵͳ�ʼ�.4Ϊ���ö��������ʼ�. 5��ʱɾ������,6ΪԪ���������ʼ�
	 * @param title 	�ʼ�����
	 * @param content   �ʼ�����
	 * @param improtant ��Ҫ�Բ�����Ĭ��Ϊ1������Խ����Ҫ��Խ��.��Ϊ�Ѷ�δ��֮��ڶ���������
	 * @return  if sussend return 1,else return -1.
	 */
	public int sendMailReply(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		return this.insertMail(receive_pk, send_pk,mail_type, title, content,improtant);
	}
	

	/**
	 * ɾ���������������ʼ�
	 * @param pk
	 */
	public void deleteSecondPassMail(int pk)
	{
		MailInfoDao mailDao = new MailInfoDao();		
		mailDao.deleteSecondPassMail(pk);
	}

	/**
	 * ����ҷ��;ż�֪ͨ�ʼ�
	 * @param pk
	*/
	public void send9Grademail(int p_pk)
	{		
		String mailTitle = "9��֪ͨ";
		String content = "��ϲ���ȼ��ﵽ9��,�������\"����é®\"�����,���ڴ�ڵ������ʽ�����ٰ�,��ʼ�µ�ð��.";
		sendMail(p_pk,-1,2, mailTitle, content);
	} 

	/**
	 *  ���ظ����Ƿ����ʼ�����Ϊmail_type���ʼ�
	 * @param pk
	 * @return
	 */
	public MailInfoVO getPersonMailTypeList(String pk,int mail_type)
	{
		MailInfoDao mailDao = new MailInfoDao();
		return mailDao.getPersonMailTypeList(pk,mail_type);
	}

	/**
	 * ����ҷ��� ����ʣ��ո����������ʼ�
	 * @param p_pk
	 */
	public void sendWrapSpareMail(int p_pk)
	{
		String mailTitle = "���İ���ʣ��ռ䲻����!";
		String content = "���İ���ʣ��ռ䲻���ˣ���ע���������ⶪʧ��Ʒ!";
		sendMail(p_pk,-1,2, mailTitle, content);
		
	}
	
	public void updateMail(int mailId, String content){
		new MailInfoDao().updateMail(mailId, content);
	}
	
	public int insertMailReturnId(int receive_pk,String title,String content){
		return insertMail(receive_pk, -1, 2, title, content, 1);
	}
	
	/**�����ʼ�����  ����ҷ��ͽ����ʼ�  ���ڽ��������������  �����ʼ�����Ϊ3*/
	public void insertBonusMail(int p_pk,String title,String content,String bonus){
		int mail_id = insertMail(p_pk, -1, 3, title, content, 1);
		if(mail_id != -1){
		MailBonusDao dao = new MailBonusDao();
		dao.insertBonus(p_pk, mail_id, bonus);
		}
	}
	
	/**
	 * �����ʼ�
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
	 * �����ʼ���Ϣ
	 * @prarm receive_pk 	������id 
	 * @prarm send_pk		������id
	 * @param mail_type		�ʼ����ͣ�1Ϊ��ͨ�ʼ���2Ϊϵͳ��
	 * @prarm title 		����
	 * @param context 		����
	 * @param improtant  	��Ҫ�ԣ�Ĭ��Ϊ1������Խ��Խ��Ҫ
	 * @return 				�����ʼ�����
	 */
	private int insertMail(int receive_pk,int send_pk,int mail_type,String title,String content,int improtant){
		return new MailInfoDao().addAndReturnKey(receive_pk, send_pk, mail_type, title, content, improtant);
	}
}
