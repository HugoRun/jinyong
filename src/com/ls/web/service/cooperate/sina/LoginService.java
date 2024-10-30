package com.ls.web.service.cooperate.sina;

import org.apache.log4j.Logger;

import com.ls.ben.dao.register.RegisterDao;
import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.cooperate.dangle.PassportService;

public class LoginService
{
	Logger logger = Logger.getLogger("log.service");

	// ��½�ο��ʺ�
	public String loginByVisitorSina(String passport)
	{
		String passport_bak = "";
		if (passport == null || passport.equals(""))
		{
			for (int i = 0; i < 1000; i++)
			{
				passport_bak = bulidVisitorPassport();
				boolean login_bak = isHaveThisVisitorPassport(passport_bak);
				if (login_bak == false)
				{
					return passport_bak;
				}
			}
		}
		else
		{
			passport = "visitor" + passport;
			boolean login = isHaveThisVisitorPassport(passport);
			if (login == true)
			{

			}
			else
			{
				for (int i = 0; i < 1000; i++)
				{
					passport_bak = bulidVisitorPassport();
					boolean login_bak = isHaveThisVisitorPassport(passport_bak);
					if (login_bak == false)
					{
						return passport_bak;
					}
				}
			}
		}
		return passport_bak;
	}

	public String relationPassportSina(String sina_uid, String passport_visitor)
	{
		PassportService ps = new PassportService();
		PassportVO passportvo = null;
		passport_visitor = "visitor" + passport_visitor;
		passportvo = ps.getPassportInfoByUserID(passport_visitor, Channel.SINA);
		if (passportvo == null)
		{
			return "���οͺŲ���������������";
		}
		else
		{
			// �����ο��ʺ�
			RegisterDao registerDao = new RegisterDao();
			registerDao.relationPassportSina(sina_uid, passport_visitor);
			return "";
		}

	}

	// �����ο��ʺ�
	private String bulidVisitorPassport()
	{
		String passport = "";
		for (int i = 0; i < 8; i++)
		{
			int x = MathUtil.getRandomBetweenXY(0, 9);
			passport = x + passport;
		}
		passport = "visitor" + passport;
		return passport;
	}

	// ʹ�����е��ο��ʺŽ�����Ϸ
	private boolean isHaveThisVisitorPassport(String passport)
	{
		PassportService ps = new PassportService();
		PassportVO passportvo = null;
		passportvo = ps.getPassportInfoByUserID(passport, Channel.SINA);
		if (passportvo != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
