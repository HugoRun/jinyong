package com.lw.service.gmmail;

import com.lw.dao.gmmail.GmMailDao;
import com.pm.service.mail.MailInfoService;
import com.pub.ben.info.Expression;

public class GmMailService
{

	public String sendMailToGM(int p_pk, String p_name, String contents)
	{
		String hint = null;
		String content= contents.trim();
		int x = Expression.hasPublish(content);
		if (x == -1)
		{
			hint = "���ݲ��Ϸ�������������!";
			return hint;
		}
		if (content.length() > 500)
		{
			hint = "��������500��,����������!";
			return hint;
		}
		if (content == null || content.equals(""))
		{
			hint = "�벻Ҫ�����ʼ�!";
			return hint;
		}
		GmMailDao dao = new GmMailDao();
		int GM_pk = dao.getGmPpk();
		if (GM_pk == 0)
		{
			return null;
		}
		MailInfoService ms = new MailInfoService();
		String title = p_name + "�ķ���";
		int i = ms.sendMailReply(GM_pk, p_pk, 1, title, content, 1);
		if (i == -1)
		{
			hint = "�ʼ�δ����!";
			return hint;
		}
		else
		{
			hint = "�ʼ�����!";
			return hint;
		}

	}
}
