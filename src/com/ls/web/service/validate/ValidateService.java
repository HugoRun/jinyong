/**
 * 
 */
package com.ls.web.service.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.ben.dao.login.LoginDao;
import com.ls.pub.config.GameConfig;
import com.pub.ben.info.Expression;

/**
 * @author ls 验证用户的输入，返回验证失败的字符串，返回为空时表示验证成功
 */
public class ValidateService
{

	/**
	 * 验证角是否可以创建角色时
	 * @param 
	 * @return
	 */
	public static String validateCreateRole(String uPk, String role_name,String sex,String race)
	{
		String hint = null;

		// 用户名匹配为字符型
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(role_name);
		boolean b = m.matches();
		if (b == false)
		{
			return "数字,英文或中文字符组合";
		}

		if (role_name.indexOf(" ") != -1)
		{
			return hint = "不能有空格";
		}

		if (role_name == null || role_name.equals(""))
		{
			return hint = "不能为空";
		}

		if (role_name.length() < 2)
		{
			return hint = "角色名长度不够";
		}
		if (role_name.length() > 5)
		{
			return hint = "角色名长度超过限制";
		}
		
		if (Expression.hasWeiFaChar(role_name))
		{
			return "名字中请不要有gm、客服等字样!";
		}
		
		if (Expression.hasForbidChar(role_name,ForBidCache.FORBIDNAME))
		{
			return "名字中包含禁止字符!";
		}

		if( StringUtils.isNumeric(sex)==false || Integer.parseInt(sex)>2 || Integer.parseInt(sex)<1 )
		{
			return "非法性别";
		}
		
		hint = validateRace(race);
		if( hint!=null )
		{
			return hint;
		}
		
		/**
		 * 判断角色名不能重复
		 */
		PartInfoDAO dao = new PartInfoDAO();
		if (dao.getPartTypeListName(role_name))
		{
			return hint = "您输入的名字已被人占用，请重新输入";
		}

		// 返回用户拥有多少个角色 定义最多不能超过5个
		int role_num = dao.getRoleNum(uPk);
		if (role_num >= GameConfig.getPlayerNum())
		{
			return hint = "您不能创建这么多角色。";
		}

		// 判断角色名不能带有被禁止的关键字
		if (dao.getForbidName(role_name))
		{
			return hint = "您输入的名字有被禁用字符,请重新输入";
		}

		return hint;
	}

	/**
	 * 验证用户名的合法性
	 * 
	 * @param user_name
	 * @return
	 */
	public String validateUserName(String user_name)
	{
		String hint = null;

		// 用户名匹配为字符型
		Pattern p = Pattern.compile(Expression.letter_number_regexp);
		Matcher m = p.matcher(user_name);
		boolean b = m.matches();
		if (b == false)
		{
			return "账号请输入大小写英文字符和数字字符";
		}

		if (user_name.indexOf(" ") != -1)
		{
			return "账号不能有空格出现";
		}
		if (user_name == null || user_name.equals(""))
		{
			return "账号不能为空";
		}
		if (user_name.length() < 5)
		{
			return "账号位数不能小于5位";
		}
		else
			if (user_name.length() > 11)
			{
				return "账号位数不能大于11位";
			}

		if (Expression.hasWeiFaChar(user_name))
		{
			return "名字中请不要有gm、客服等字样!";
		}

		return hint;
	}

	/**
	 * 验证密码的合法性
	 * 
	 * @param user_name
	 * @return
	 */
	public String validatePwd(String pwd)
	{
		String hint = null;

		Pattern p = Pattern.compile(Expression.letter_number_regexp);
		Matcher pw = p.matcher(pwd);
		boolean pp = pw.matches();

		if (pp == false)
		{
			return "密码请输入大小写英文字符和数字字符";
		}

		if (pwd.length() < 5)
		{
			return "密码位数不能小于5位";
		}
		else
			if (pwd.length() > 11)
			{
				return "密码位数不能大于11位";
			}

		if (pwd.indexOf(" ") != -1)
		{
			return "密码不能有空格出现";
		}
		if (pwd == null || pwd.equals(""))
		{
			return "密码不能为空";
		}

		return hint;
	}

	/**
	 * 验证注册账号的用户名和密码的合法性
	 * 
	 * @return
	 */
	public String validateRegisterUsernameAndPwd(String user_name, String pwd)
	{
		String hint = null;
		
		// 验证用户名格式的合法性
		if( StringUtils.isEmpty(user_name))
		{
			return "请输入正确的用户名!";
		}
		
		Pattern p = Pattern.compile(Expression.letter_number_regexp);
		if (user_name.length() > 11)
		{
			return "对不起，游戏帐号为6-11位数字和大小写英文字符组合；密码为6位数字和大小写英文字符组合。重新注册！";
		}

		if (pwd.length() != 6)
		{
			return "对不起，游戏帐号为6-11位数字和大小写英文字符组合；密码为6位数字和大小写英文字符组合。重新注册！";
		}

		Matcher m1 = p.matcher(user_name);
		boolean b1 = m1.matches();
		if (b1 == false)
		{
			return "请输入正确的用户名!";
		}
		// 验证密码格式的合法性
		if (pwd == null || pwd.trim().equals(""))
		{
			return "请输入正确的密码!";
		}

		Matcher m2 = p.matcher(pwd);
		boolean b2 = m2.matches();
		if (b2 == false)
		{
			return "请输入正确的密码!";
		}
		// 验证用户名的存在
		LoginDao loginDao = new LoginDao();
		if (loginDao.isHaveName(user_name))
		{
			hint = "用户名已经存在";
			return hint;
		}

		hint = validateUserName(user_name);

		if (hint != null)
		{
			return "对不起，此帐号已经注册。重新注册！";
		}

		return hint;
	}
	
	/**
	 * 验证是否是非0正整数
	 */
	public static String validateNonZeroNegativeIntegers(String num)
	{
		String hint = null;
		
		if( num==null )
		{
			return hint = "非法输入";
		}
		
		// 用户名匹配为字符型
		Pattern p = Pattern.compile(Expression.non_zero_negative_integers_regexp);
		Matcher m = p.matcher(num.trim());
		if(!m.matches())
		{
			hint = "非法输入";
		}
		
		return hint;
	}

	
	/**
	 * 验证帮派名称的合法性
	 * 
	 * @param role_name
	 * @return
	 */
	public String validateTongName(String tong_name)
	{
		String hint = null;

		// 用户名匹配为字符型
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(tong_name);
		boolean b = m.matches();
		if (b == false)
		{
			return "帮派名称为数字,英文或中文字符组合";
		}

		if (tong_name.indexOf(" ") != -1)
		{
			return hint = "帮派名称不能有空格";
		}

		if (tong_name == null || tong_name.equals(""))
		{
			return hint = "帮派名称不能为空";
		}

		if (Expression.hasWeiFaChar(tong_name))
		{
			return "帮派名称中请不要有gm、客服等字样!";
		}
		
		if (Expression.hasForbidChar(tong_name,ForBidCache.FORBIDNAME))
		{
			return "帮派名称中包含禁止字符!";
		}
		
		if (tong_name.length() > 5)
		{
			return "帮派名称不能长于5位";
		}

		PartInfoDAO dao = new PartInfoDAO();

		// 判断角色名不能带有被禁止的关键字
		if (dao.getForbidName(tong_name))
		{
			return hint = "帮派名称有被禁用字符,请重新输入";
		}

		return hint;
	}
	
	/**
	 * 基本输入验证
	 */
	public static String validateBasicInput(String input_content,int limit_length )
	{
		if( StringUtils.isEmpty(input_content) )
		{
			return "输入不能为空";
		}
		
		if (Expression.hasWeiFaChar(input_content))
		{
			return "包含gm、客服等字样的非法字符";
		}
		
		if( Expression.hasPublish(input_content)==-1 )
		{
			return "包含有非法字符";
		}
		
		if( input_content.length() > limit_length)
		{
			return "不能长于"+limit_length+"位";
		}
		
		if (Expression.hasForbidChar(input_content,ForBidCache.FORBIDNAME))
		{
			return "包含禁止字符!";
		}
		
/*		PartInfoDAO dao = new PartInfoDAO();
		if (dao.getForbidName(input_content))
		{
			return "有被禁用字符";
		}
*/		
		return null;
	}
	
	
	/**
	 * 验证种族是否合法
	 * @param race
	 * @return
	 */
	public static String validateRace( String race )
	{
		if( StringUtils.isNumeric(race)==false || Integer.parseInt(race)>2 || Integer.parseInt(race)<1 )
		{
			return "非法种族";
		}
		return null;
	}
	
}
