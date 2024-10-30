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
			hint = "内容不合法，请重新输入!";
			return hint;
		}
		if (content.length() > 500)
		{
			hint = "字数超过500字,请重新输入!";
			return hint;
		}
		if (content == null || content.equals(""))
		{
			hint = "请不要发空邮件!";
			return hint;
		}
		GmMailDao dao = new GmMailDao();
		int GM_pk = dao.getGmPpk();
		if (GM_pk == 0)
		{
			return null;
		}
		MailInfoService ms = new MailInfoService();
		String title = p_name + "的反馈";
		int i = ms.sendMailReply(GM_pk, p_pk, 1, title, content, 1);
		if (i == -1)
		{
			hint = "邮件未发出!";
			return hint;
		}
		else
		{
			hint = "邮件发出!";
			return hint;
		}

	}
}
