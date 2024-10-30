/**
 * 
 */
package com.pub.ben.info;

import java.util.*;

import org.apache.oro.text.regex.*;

import com.ls.pub.util.WmParser;

/**
 * @author ��ƾ� 11:45:46 AM 
 * ����: ʹ��������ʽ��֤���ݻ���ȡ����,���еķ���ȫΪ��̬�� ��Ҫ����: 
 * 1.isHardRegexpValidate(String source, String regexp) ���ִ�Сд���е�������ʽ���� 
 * 2.isSoftRegexpValidate(String source, String regexp) �����ִ�Сд��������ʽ���� 
 * 3.getHardRegexpMatchResult(String source, String regexp)������Ҫ����������(��Сд���е�������ʽ����) 
 * 4.getSoftRegexpMatchResult(String source,String regexp) ������Ҫ����������(�����ִ�Сд��������ʽ����) 
 * 5 getHardRegexpArray(String source, String regexp) ������Ҫ����������(��Сд���е�������ʽ����) 
 * 6.getSoftRegexpMatchResult(String source, String regexp) ������Ҫ����������(�����ִ�Сд��������ʽ����) 
 * 7.getBetweenSeparatorStr(final String originStr,final char leftSeparator,final char rightSeparator)�õ�ָ���ָ����м���ַ����ļ���
 * 
 */
public class Expression
{
	/** �����������Ӧ�ָ��� */
	static final Set<String> SEPARATOR_SET = new TreeSet<String>();
	{
		SEPARATOR_SET.add("(");
		SEPARATOR_SET.add(")");
		SEPARATOR_SET.add("[");
		SEPARATOR_SET.add("]");
		SEPARATOR_SET.add("{");
		SEPARATOR_SET.add("}");
		SEPARATOR_SET.add("<");
		SEPARATOR_SET.add(">");
	}

	/** ��Ÿ���������ʽ(��key->value����ʽ) */
	public static HashMap<String, String> regexpHash = new HashMap<String, String>();

	/** ��Ÿ���������ʽ(��key->value����ʽ) */
	public static List matchingResultList = new ArrayList();

	private Expression()
	{

	}

	/**
	 * ���� Regexp ʵ��
	 * 
	 * @return
	 */
	public static Expression getInstance()
	{
		return new Expression();
	}

	/**
	 * ƥ��ͼ��
	 * 
	 * 
	 * ��ʽ: /���·��/�ļ���.��׺ (��׺Ϊgif,dmp,png)
	 * 
	 * ƥ�� : /forum/head_icon/admini2005111_ff.gif �� admini2005111.dmp
	 * 
	 * 
	 * ��ƥ��: c:/admins4512.gif
	 * 
	 */
	public static final String icon_regexp = "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

	/**
	 * ƥ��email��ַ
	 * 
	 * 
	 * ��ʽ: XXX@XXX.XXX.XX
	 * 
	 * ƥ�� : foo@bar.com �� foobar@foobar.com.au
	 * 
	 * 
	 * ��ƥ��: foo@bar �� $$$@bar.com
	 * 
	 */
	public static final String email_regexp = "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3}$)";

	/**
	 * ƥ��ƥ�䲢��ȡurl
	 * 
	 * 
	 * ��ʽ: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	 * 
	 * ƥ�� : http://www.suncer.com ��news://www
	 * 
	 * 
	 * ��ȡ(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true
	 * 
	 * ��ƥ��: c:\window
	 * 
	 */
	public static final String url_regexp = "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * ƥ�䲢��ȡhttp
	 * 
	 * 
	 * ��ʽ: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX �� ftp://XXX.XXX.XXX ��
	 * https://XXX
	 * 
	 * ƥ�� : http://www.suncer.com:8080/index.html?login=true
	 * 
	 * 
	 * ��ȡ(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)=
	 * http://www.suncer.com:8080/index.html?login=true matchResult.group(1) =
	 * http matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080
	 * matchResult.group(4) = /index.html?login=true
	 * 
	 * ��ƥ��: news://www
	 * 
	 */
	public static final String http_regexp = "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * ƥ������
	 * 
	 * 
	 * ��ʽ(��λ��Ϊ0): XXXX-XX-XX �� XXXX XX XX �� XXXX-X-X
	 * 
	 * 
	 * ��Χ:1900--2099
	 * 
	 * 
	 * ƥ�� : 2005-04-04
	 * 
	 * 
	 * ��ƥ��: 01-01-01
	 * 
	 */
	public static final String date_regexp = "^((((19){1}|(20){1})d{2})|d{2})[-\\s]{1}[01]{1}d{1}[-\\s]{1}[0-3]{1}d{1}$";// ƥ������

	/**
	 * ƥ��绰
	 * 
	 * 
	 * ��ʽΪ: 0XXX-XXXXXX(10-13λ��λ����Ϊ0) ��0XXX XXXXXXX(10-13λ��λ����Ϊ0) ��
	 * 
	 * (0XXX)XXXXXXXX(11-14λ��λ����Ϊ0) �� XXXXXXXX(6-8λ��λ��Ϊ0) ��
	 * XXXXXXXXXXX(11λ��λ��Ϊ0)
	 * 
	 * 
	 * ƥ�� : 0371-123456 �� (0371)1234567 �� (0371)12345678 �� 010-123456 ��
	 * 010-12345678 �� 12345678912
	 * 
	 * 
	 * ��ƥ��: 1111-134355 �� 0123456789
	 * 
	 */
	public static final String phone_regexp = "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * ƥ�����֤
	 * 
	 * 
	 * ��ʽΪ: XXXXXXXXXX(10λ) �� XXXXXXXXXXXXX(13λ) �� XXXXXXXXXXXXXXX(15λ) ��
	 * XXXXXXXXXXXXXXXXXX(18λ)
	 * 
	 * 
	 * ƥ�� : 0123456789123
	 * 
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ID_card_regexp = "^\\d{10}|\\d{13}|\\d{15}|\\d{18}$";

	/**
	 * ƥ���ʱ����
	 * 
	 * 
	 * ��ʽΪ: XXXXXX(6λ)
	 * 
	 * 
	 * ƥ�� : 012345
	 * 
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ZIP_regexp = "^[0-9]{6}$";// ƥ���ʱ����

	/**
	 * �����������ַ���ƥ�� (�ַ����в��������� ��ѧ�η���^ ������' ˫����" �ֺ�; ����, ñ��: ��ѧ����- �Ҽ�����> �������<
	 * ��б��\ ���ո�,�Ʊ��,�س����� )
	 * 
	 * 
	 * ��ʽΪ: x �� һ��һ�ϵ��ַ�
	 * 
	 * 
	 * ƥ�� : 012345
	 * 
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String non_special_char_regexp = "^[^'\"\\;,:-<>\\s].+$";// ƥ���ʱ����

	/**
	 * ƥ��Ǹ������������� + 0)
	 */
	public static final String non_negative_integers_regexp = "^\\d+$";

	/**
	 * ƥ�䲻������ķǸ������������� > 0)
	 */
	public static final String non_zero_negative_integers_regexp = "^[1-9]+\\d*$";

	/**
	 * 
	 * ƥ��������
	 * 
	 */
	public static final String positive_integer_regexp = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ���������������� + 0��
	 * 
	 */
	public static final String positive_integer_contain0_regexp = "^[0-9]+$";
	
	/**
	 * 
	 * ƥ����������������� + 0��
	 * 
	 */
	public static final String non_positive_integers_regexp = "^((-\\d+)|(0+))$";

	/**
	 * 
	 * ƥ�为����
	 * 
	 */
	public static final String negative_integers_regexp = "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ������
	 * 
	 */
	public static final String integer_regexp = "^-?\\d+$";

	/**
	 * 
	 * ƥ��Ǹ����������������� + 0��
	 * 
	 */
	public static final String non_negative_rational_numbers_regexp = "^\\d+(\\.\\d+)?$";

	/**
	 * 
	 * ƥ����������
	 * 
	 */
	public static final String positive_rational_numbers_regexp = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 * 
	 * ƥ��������������������� + 0��
	 * 
	 */
	public static final String non_positive_rational_numbers_regexp = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

	/**
	 * 
	 * ƥ�为������
	 * 
	 */
	public static final String negative_rational_numbers_regexp = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 * 
	 * ƥ�両����
	 * 
	 */
	public static final String rational_numbers_regexp = "^(-?\\d+)(\\.\\d+)?$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��ɵ��ַ���
	 * 
	 */
	public static final String letter_regexp = "^[A-Za-z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ��,0-9���ֺͺ�����ɵ��ַ���
	 * 
	 */
	public static final String chinese_regexp = "^[A-Za-z0-9һ-\u9FA5]+$";
	
	/**
	 * 
	 * ƥ����26��Ӣ����ĸ�Ĵ�д��ɵ��ַ���
	 * 
	 */
	public static final String upward_letter_regexp = "^[A-Z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��Сд��ɵ��ַ���
	 * 
	 */
	public static final String lower_letter_regexp = "^[a-z]+$";

	/**
	 * 
	 * ƥ�������ֺ�26��Ӣ����ĸ��ɵ��ַ���
	 * 
	 */
	public static final String letter_number_regexp = "^[A-Za-z0-9]+$";

	/**
	 * 
	 * ƥ�������֡�26��Ӣ����ĸ�����»�����ɵ��ַ���
	 * 
	 */
	public static final String letter_number_underline_regexp = "^\\w+$";

	/**
	 * ƥ��Ǯ9λ����
	 */
	public static final String positive_integer_money = "^[0-9]{0,9}(\\.[0-9]{1,2})?$";
	
	/**
	 * ���������ʽ (��key->value����ʽ�洢)
	 * 
	 * @param regexpName
	 *            ��������ʽ���� `
	 * @param regexp
	 *            ��������ʽ����
	 */
	public void putRegexpHash(String regexpName, String regexp)
	{
		regexpHash.put(regexpName, regexp);
	}

	/**
	 * �õ�������ʽ���� (ͨ��key����ȡ��value[������ʽ����])
	 * 
	 * @param regexpName
	 *            ������ʽ����
	 * 
	 * @return ������ʽ����
	 */
	public String getRegexpHash(String regexpName)
	{
		if (regexpHash.get(regexpName) != null)
		{
			return regexpHash.get(regexpName);
		}
		else
		{
			////System.out.println("��regexpHash��û�д�������ʽ");
			return "";
		}
	}

	/**
	 * ���������ʽ��ŵ�Ԫ
	 */
	public void clearRegexpHash()
	{
		regexpHash.clear();
		return;
	}

	/**
	 * ��Сд���е�������ʽ����
	 * 
	 * @param source
	 *            �����Դ�ַ���
	 * 
	 * @param regexp
	 *            �����������ʽ
	 * 
	 * @return ���Դ�ַ�������Ҫ�󷵻���,���򷵻ؼ� ��:
	 *         Regexp.isHardRegexpValidate("ygj@suncer.com.cn",email_regexp) ������
	 */
	public static boolean isHardRegexpValidate(String source, String regexp)
	{

		try
		{
			// ���ڶ���������ʽ����ģ������
			PatternCompiler compiler = new Perl5Compiler();

			// ������ʽ�Ƚ��������
			PatternMatcher matcher = new Perl5Matcher();

			// ʵ����С��Сд���е�������ʽģ��
			Pattern hardPattern = compiler.compile(regexp);

			// ����������
			return matcher.contains(source, hardPattern);

		}
		catch (MalformedPatternException e)
		{
			e.printStackTrace();

		}
		return false;
	}

	/**
	 * @param �ַ�����
	 */
	public static String encoding(String src)
	{
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		if (src != null)
		{
			src = src.trim();
			for (int pos = 0; pos < src.length(); pos++)
			{
				switch (src.charAt(pos))
				{
					case '\"':
						result.append("\"");
						break;
					case '<':
						result.append("<");
						break;
					case '>':
						result.append(">");
						break;
					case '\'':
						result.append("&apos;");
						break;
					case '&':
						result.append("&");
						break;
					case '%':
						result.append("&pc;");
						break;
					case '_':
						result.append("&ul;");
						break;
					case '#':
						result.append("&shap;");
						break;
					case '?':
						result.append("&ques;");
						break;
					default:
						result.append(src.charAt(pos));
						break;
				}
			}
		}
		return result.toString();

	}

	/**
	 * �����������ַ�
	 */
	public static String decoding(String src)
	{
		if (src == null)
			return "";
		String result = src;
		result = result.replace("\"", "\"").replace("&apos;", "\'");
		result = result.replace("<", "<").replace(">", ">");
		result = result.replace("&", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}
	
	/**
	 * @param �ַ�����,�������µ� �ַ�ʱ,�ͷ���-1
	 */
	public static int hasPublish(String src)
	{
		if (src == null)
			return -1;
		
		src = src.trim();
		for (int pos = 0; pos < src.length(); pos++)
		{
			switch (src.charAt(pos))
			{	 
				case '@':
					return -1;
				case '#':
					return -1;
				case '$':
					return -1;	
				case '%':
					return -1;
				case '^':
					return -1;	
				case '&':
					return -1;
				case '*':
					return -1;
				case '\\':
					return -1;	
				case '/':
					return -1;
				case '<':
					return -1;	
				case '>':
					return -1;
			}
		}
		
		return 1;
	} 
	
	
	/**
	 * @param �ַ�����,�������µ� �ַ�ʱ,�ͷ���-1
	 */
	public static int hasPublishWithMail(String src)
	{
		if (src == null)
			return -1;
		
		src = src.trim();
		for (int pos = 0; pos < src.length(); pos++)
		{
			switch (src.charAt(pos))
			{	 
				case '@':
					return -1;
				case '#':
					return -1;
				case '$':
					return -1;	
				case '%':
					return -1;
				case '^':
					return -1;	
				case '&':
					return -1;
				case '*':
					return -1;
				case '\"':
					return -1;	
				case '/':
					return -1;
				case '<':
					return -1;	
				case '>':
					return -1;
			}
		}
		
		return 1;
	} 
	
	/**
	 * ��ø��˵���Ӫ��ʾ��1Ϊ���ɣ�2Ϊа��
	 * @param 
	 */
	public static String getCampName(int camp) {
		if(camp == 1) {
			return "����";
		} else if (camp == 2 ) {
			return "а��";
		} else {
			return "��";
		}
	}

	/**
	 * ���ֲ����� ��Υ�� �ַ�
	 * @param name
	 * @return
	 */
	public static boolean hasWeiFaChar(String name)
	{
		boolean flag = false;
		
		if ( ((name.indexOf("g") > -1) || (name.indexOf("G") > -1) ) 
				&& ((name.indexOf("m") > -1) || (name.indexOf("M") > -1)  )) {
			return true;
		}
		
		if ( (name.indexOf("��") > -1) && (name.indexOf("��") > -1) ) {
			return true;
		}
		
		if ( (name.indexOf("��") > -1) && (name.indexOf("��") > -1) ) {
			return true;
		}
		
		if ( (name.indexOf("ϵ") > -1) && (name.indexOf("ͳ") > -1) ) {
			return true;
		}
		
		return flag;
		
	}
	
	
	/**�ж������Ƿ�Ϊ��*/
	public static boolean hasSpace(String str){
		if(str.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * ����ɫ�����Ƿ��б���ֹ�ʣ������,����true�����û��,����false
	 * @param checkStr �����Ĵ�
	 * @param forbid_type	Ҫ��������
	 * @return
	 */
	public static boolean hasForbidChar(String checkStr,int forbid_type)
	{
		WmParser filterEngine=new WmParser();
		int backValue = filterEngine.filterStr(checkStr,forbid_type);
		if ( backValue != 0) {
			return true;
		} else {
			return false;
		}
		
	}
}
