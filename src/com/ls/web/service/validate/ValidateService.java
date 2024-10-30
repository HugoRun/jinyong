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
 * @author ls ��֤�û������룬������֤ʧ�ܵ��ַ���������Ϊ��ʱ��ʾ��֤�ɹ�
 */
public class ValidateService
{

	/**
	 * ��֤���Ƿ���Դ�����ɫʱ
	 * @param 
	 * @return
	 */
	public static String validateCreateRole(String uPk, String role_name,String sex,String race)
	{
		String hint = null;

		// �û���ƥ��Ϊ�ַ���
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(role_name);
		boolean b = m.matches();
		if (b == false)
		{
			return "����,Ӣ�Ļ������ַ����";
		}

		if (role_name.indexOf(" ") != -1)
		{
			return hint = "�����пո�";
		}

		if (role_name == null || role_name.equals(""))
		{
			return hint = "����Ϊ��";
		}

		if (role_name.length() < 2)
		{
			return hint = "��ɫ�����Ȳ���";
		}
		if (role_name.length() > 5)
		{
			return hint = "��ɫ�����ȳ�������";
		}
		
		if (Expression.hasWeiFaChar(role_name))
		{
			return "�������벻Ҫ��gm���ͷ�������!";
		}
		
		if (Expression.hasForbidChar(role_name,ForBidCache.FORBIDNAME))
		{
			return "�����а�����ֹ�ַ�!";
		}

		if( StringUtils.isNumeric(sex)==false || Integer.parseInt(sex)>2 || Integer.parseInt(sex)<1 )
		{
			return "�Ƿ��Ա�";
		}
		
		hint = validateRace(race);
		if( hint!=null )
		{
			return hint;
		}
		
		/**
		 * �жϽ�ɫ�������ظ�
		 */
		PartInfoDAO dao = new PartInfoDAO();
		if (dao.getPartTypeListName(role_name))
		{
			return hint = "������������ѱ���ռ�ã�����������";
		}

		// �����û�ӵ�ж��ٸ���ɫ ������಻�ܳ���5��
		int role_num = dao.getRoleNum(uPk);
		if (role_num >= GameConfig.getPlayerNum())
		{
			return hint = "�����ܴ�����ô���ɫ��";
		}

		// �жϽ�ɫ�����ܴ��б���ֹ�Ĺؼ���
		if (dao.getForbidName(role_name))
		{
			return hint = "������������б������ַ�,����������";
		}

		return hint;
	}

	/**
	 * ��֤�û����ĺϷ���
	 * 
	 * @param user_name
	 * @return
	 */
	public String validateUserName(String user_name)
	{
		String hint = null;

		// �û���ƥ��Ϊ�ַ���
		Pattern p = Pattern.compile(Expression.letter_number_regexp);
		Matcher m = p.matcher(user_name);
		boolean b = m.matches();
		if (b == false)
		{
			return "�˺��������СдӢ���ַ��������ַ�";
		}

		if (user_name.indexOf(" ") != -1)
		{
			return "�˺Ų����пո����";
		}
		if (user_name == null || user_name.equals(""))
		{
			return "�˺Ų���Ϊ��";
		}
		if (user_name.length() < 5)
		{
			return "�˺�λ������С��5λ";
		}
		else
			if (user_name.length() > 11)
			{
				return "�˺�λ�����ܴ���11λ";
			}

		if (Expression.hasWeiFaChar(user_name))
		{
			return "�������벻Ҫ��gm���ͷ�������!";
		}

		return hint;
	}

	/**
	 * ��֤����ĺϷ���
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
			return "�����������СдӢ���ַ��������ַ�";
		}

		if (pwd.length() < 5)
		{
			return "����λ������С��5λ";
		}
		else
			if (pwd.length() > 11)
			{
				return "����λ�����ܴ���11λ";
			}

		if (pwd.indexOf(" ") != -1)
		{
			return "���벻���пո����";
		}
		if (pwd == null || pwd.equals(""))
		{
			return "���벻��Ϊ��";
		}

		return hint;
	}

	/**
	 * ��֤ע���˺ŵ��û���������ĺϷ���
	 * 
	 * @return
	 */
	public String validateRegisterUsernameAndPwd(String user_name, String pwd)
	{
		String hint = null;
		
		// ��֤�û�����ʽ�ĺϷ���
		if( StringUtils.isEmpty(user_name))
		{
			return "��������ȷ���û���!";
		}
		
		Pattern p = Pattern.compile(Expression.letter_number_regexp);
		if (user_name.length() > 11)
		{
			return "�Բ�����Ϸ�ʺ�Ϊ6-11λ���ֺʹ�СдӢ���ַ���ϣ�����Ϊ6λ���ֺʹ�СдӢ���ַ���ϡ�����ע�ᣡ";
		}

		if (pwd.length() != 6)
		{
			return "�Բ�����Ϸ�ʺ�Ϊ6-11λ���ֺʹ�СдӢ���ַ���ϣ�����Ϊ6λ���ֺʹ�СдӢ���ַ���ϡ�����ע�ᣡ";
		}

		Matcher m1 = p.matcher(user_name);
		boolean b1 = m1.matches();
		if (b1 == false)
		{
			return "��������ȷ���û���!";
		}
		// ��֤�����ʽ�ĺϷ���
		if (pwd == null || pwd.trim().equals(""))
		{
			return "��������ȷ������!";
		}

		Matcher m2 = p.matcher(pwd);
		boolean b2 = m2.matches();
		if (b2 == false)
		{
			return "��������ȷ������!";
		}
		// ��֤�û����Ĵ���
		LoginDao loginDao = new LoginDao();
		if (loginDao.isHaveName(user_name))
		{
			hint = "�û����Ѿ�����";
			return hint;
		}

		hint = validateUserName(user_name);

		if (hint != null)
		{
			return "�Բ��𣬴��ʺ��Ѿ�ע�ᡣ����ע�ᣡ";
		}

		return hint;
	}
	
	/**
	 * ��֤�Ƿ��Ƿ�0������
	 */
	public static String validateNonZeroNegativeIntegers(String num)
	{
		String hint = null;
		
		if( num==null )
		{
			return hint = "�Ƿ�����";
		}
		
		// �û���ƥ��Ϊ�ַ���
		Pattern p = Pattern.compile(Expression.non_zero_negative_integers_regexp);
		Matcher m = p.matcher(num.trim());
		if(!m.matches())
		{
			hint = "�Ƿ�����";
		}
		
		return hint;
	}

	
	/**
	 * ��֤�������ƵĺϷ���
	 * 
	 * @param role_name
	 * @return
	 */
	public String validateTongName(String tong_name)
	{
		String hint = null;

		// �û���ƥ��Ϊ�ַ���
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(tong_name);
		boolean b = m.matches();
		if (b == false)
		{
			return "��������Ϊ����,Ӣ�Ļ������ַ����";
		}

		if (tong_name.indexOf(" ") != -1)
		{
			return hint = "�������Ʋ����пո�";
		}

		if (tong_name == null || tong_name.equals(""))
		{
			return hint = "�������Ʋ���Ϊ��";
		}

		if (Expression.hasWeiFaChar(tong_name))
		{
			return "�����������벻Ҫ��gm���ͷ�������!";
		}
		
		if (Expression.hasForbidChar(tong_name,ForBidCache.FORBIDNAME))
		{
			return "���������а�����ֹ�ַ�!";
		}
		
		if (tong_name.length() > 5)
		{
			return "�������Ʋ��ܳ���5λ";
		}

		PartInfoDAO dao = new PartInfoDAO();

		// �жϽ�ɫ�����ܴ��б���ֹ�Ĺؼ���
		if (dao.getForbidName(tong_name))
		{
			return hint = "���������б������ַ�,����������";
		}

		return hint;
	}
	
	/**
	 * ����������֤
	 */
	public static String validateBasicInput(String input_content,int limit_length )
	{
		if( StringUtils.isEmpty(input_content) )
		{
			return "���벻��Ϊ��";
		}
		
		if (Expression.hasWeiFaChar(input_content))
		{
			return "����gm���ͷ��������ķǷ��ַ�";
		}
		
		if( Expression.hasPublish(input_content)==-1 )
		{
			return "�����зǷ��ַ�";
		}
		
		if( input_content.length() > limit_length)
		{
			return "���ܳ���"+limit_length+"λ";
		}
		
		if (Expression.hasForbidChar(input_content,ForBidCache.FORBIDNAME))
		{
			return "������ֹ�ַ�!";
		}
		
/*		PartInfoDAO dao = new PartInfoDAO();
		if (dao.getForbidName(input_content))
		{
			return "�б������ַ�";
		}
*/		
		return null;
	}
	
	
	/**
	 * ��֤�����Ƿ�Ϸ�
	 * @param race
	 * @return
	 */
	public static String validateRace( String race )
	{
		if( StringUtils.isNumeric(race)==false || Integer.parseInt(race)>2 || Integer.parseInt(race)<1 )
		{
			return "�Ƿ�����";
		}
		return null;
	}
	
}
