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

	// 登陆游客帐号
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
			return "该游客号不存在请重新输入";
		}
		else
		{
			// 关联游客帐号
			RegisterDao registerDao = new RegisterDao();
			registerDao.relationPassportSina(sina_uid, passport_visitor);
			return "";
		}

	}

	// 生成游客帐号
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

	// 使用已有的游客帐号进入游戏
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
