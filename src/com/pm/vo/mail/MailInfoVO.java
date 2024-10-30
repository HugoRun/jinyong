package com.pm.vo.mail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.item.ItemContainer;
import com.ls.pub.util.StringUtil;
import com.pm.service.mail.MailInfoService;

public class MailInfoVO
{

	/** �ʼ�id **/
	private int mailId;
	/** receive id **/
	private int receivePk;
	/** sender id **/
	private int sendPk;
	/** mail type ,1 �����ʼ�, 2 ϵͳ�ʼ�,3��ȡ��Ʒ�ʼ�,4�����ʼ�**/
	private int mailType;
	/** title  **/
	private String title;
	/** context **/
	private String content;
	/** unread logo ***/
	private int unread;
	
	/**  improtant **/
	private int improtant=1;
	/**  createTime **/
	private String createTime;
	
	/**
	 * �ʼ������ַ���
	 */
	private String attachmentStr;
	/**
	 * �ʼ�����
	 */
	private ItemContainer attachment;
	
	
	/**
	 * ��Ӹ���
	 */
	public void addAttachment(ItemContainer attachment)
	{
		this.attachment = attachment;
		attachmentStr = attachment.toString();
	}
	
	/**
	 * �õ��ʼ�����
	 * @return
	 */
	public ItemContainer getAttachment()
	{
		if( attachment==null )
		{
			attachment = new ItemContainer(attachmentStr);
		}
		return attachment;
	}
	/**
	 * ����PK������Ʒ�ʼ�
	 */
	public void createPKDropItemMail( int receivePPk, String title,String content,ItemContainer attachment)
	{
		this.createMail(receivePPk, title, content);
		this.mailType = MailInfoService.HAVE_ATTACHMENT;
		this.addAttachment(attachment);
	}
	
	
	/**
	 * ����һ���ʼ�
	 * @param receive_ppk
	 * @param title
	 */
	public void createMail( int receive_ppk ,String title,String content)
	{
		this.createMail(receive_ppk, -1, title, content, "");
	}
	
	/**
	 * ����һ���ʼ�
	 * @param receive_ppk
	 * @param send_ppk
	 * @param title
	 * @param content
	 * @param attachment
	 */
	public void createMail( int receive_ppk ,int send_ppk, String title,String content,String attachment)
	{
		receivePk = receive_ppk;
		sendPk = send_ppk;
		this.title = title;
		this.content = content;
		this.attachmentStr = attachment;
	}
	
	public int getMailId()
	{
		return mailId;
	}
	public void setMailId(int mailId)
	{
		this.mailId = mailId;
	}
	public int getReceivePk()
	{
		return receivePk;
	}
	public void setReceivePk(int receivePk)
	{
		this.receivePk = receivePk;
	}
	public int getSendPk()
	{
		return sendPk;
	}
	public void setSendPk(int sendPk)
	{
		this.sendPk = sendPk;
	}
	public String getSendName()
	{
		String name = null;
		switch(this.mailType)
		{
			case MailInfoService.OTHER_MAIL:
				PartInfoDao partInfoDao = new PartInfoDao();
				String senderName = partInfoDao.getNameByPpk(sendPk);
				if(senderName == null || senderName.equals("")){
					name = "����ʼ�";
				}else {
					name = StringUtil.isoToGBK(senderName);
				}
				break;
			case MailInfoService.SYSTEM_MAIL:name = "ϵͳ�ʼ�";break;
			case MailInfoService.ITEM_MAIL:name = "����";break;
			case MailInfoService.F_INVITE_MAIL:name = "�����ʼ�";break;
		}
		return name;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	public int getUnread()
	{
		return unread;
	}
	public void setUnread(int unread)
	{
		this.unread = unread;
	}
	public int getImprotant()
	{
		return improtant;
	}
	public void setImprotant(int improtant)
	{
		this.improtant = improtant;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public String getFormatCreateTime()
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd HH:mm");
		Date dt1 = null;
		try
		{
			dt1 = sf.parse(createTime);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		sf = new SimpleDateFormat("MM-dd HH:mm");
		return sf.format(dt1);
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public int getMailType()
	{
		return mailType;
	}
	public void setMailType(int mailType)
	{
		this.mailType = mailType;
	}

	public String getAttachmentStr()
	{
		return attachmentStr;
	}

	public void setAttachmentStr(String attachmentStr)
	{
		this.attachmentStr = attachmentStr;
	}
}
