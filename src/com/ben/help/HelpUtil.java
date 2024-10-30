package com.ben.help;

import com.ls.pub.config.GameConfig;

public class HelpUtil
{

	public static String getDes(String des, String nowp, int id,Object nowPage)
	{
		if(des==null){
			return "";
		}
		
		String SHANGYIYE = " <anchor><go href=\""+GameConfig.getContextPath()+"/help.do"+"\" method=\"post\">"
				+ "<postfield name=\"cmd\" value=\"n3\" />"
				+ "<postfield name=\"id\" value=\""
				+ id
				+ "\" />"
				+ "<postfield name=\"nowp\" value=\""
				+ (Integer.parseInt(nowp.trim()) - 1)
				+ "\" />"
				+ "<postfield name=\"nowPage\" value=\""
				+ nowPage
				+ "\" /></go>"
				+ "上一页</anchor>";
		String XIAYIYE = " <anchor><go href=\""+GameConfig.getContextPath()+"/help.do"+"\" method=\"post\">"
				+ "<postfield name=\"cmd\" value=\"n3\" />"
				+ "<postfield name=\"id\" value=\""
				+ id
				+ "\" />"
				+ "<postfield name=\"nowPage\" value=\""
				+ nowPage
				+ "\" />"
				+ "<postfield name=\"nowp\" value=\""
				+ (Integer.parseInt(nowp.trim()) + 1)
				+ "\" /></go>"
				+ "下一页</anchor>";

		int i = (nowp == null || "".equals(nowp.trim()) ? 1 : Integer
				.parseInt(nowp.trim()));
		if(i<=0){
			i = 1;
		}
		if ((i - 1) * HelpConstant.EACH_LENGTH > des.length())
		{
			return des+"<br/>";
		}
		else
			if (i * HelpConstant.EACH_LENGTH > des.length())
			{
				return check(des.substring((i - 1) * HelpConstant.EACH_LENGTH, des
						.length()))+"<br/>"+(i>1?SHANGYIYE:"");
			}
			else
			{
				return check(des.substring((i - 1) * HelpConstant.EACH_LENGTH, i
						* HelpConstant.EACH_LENGTH))+"<br/>"+(i>1?SHANGYIYE:"")+(i * HelpConstant.EACH_LENGTH < des.length()?XIAYIYE:"");
			}
	}
	
	private static String check(String des){
		if(des==null){
			return "";
		}
		if(des.startsWith("<br/>")){
			des = des.replaceFirst("<br/>", "");
		}
		if(des.startsWith("br/>")){
			des = des.replaceFirst("br/>", "");
		}
		if(des.startsWith("r/>")){
			des = des.replaceFirst("r/>", "");
		}
		if(des.startsWith("/>")){
			des = des.replaceFirst("/>", "");
		}
		if(des.startsWith(">")){
			des = des.replaceFirst(">", "");
		}
		if(des.endsWith("<br/>")){
			int i = des.lastIndexOf("<br/>");
			des = des.substring(0, i);
		}
		if(des.endsWith("<br/")){
			int i = des.lastIndexOf("<br/");
			des = des.substring(0, i);
		}
		if(des.endsWith("<br")){
			int i = des.lastIndexOf("<br");
			des = des.substring(0, i);
		}
		if(des.endsWith("<b")){
			int i = des.lastIndexOf("<b");
			des = des.substring(0, i);
		}
		if(des.endsWith("<")){
			int i = des.lastIndexOf("<");
			des = des.substring(0, i);
		}
		return des;
	}
	
}
