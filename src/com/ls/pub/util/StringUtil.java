/**
 * 
 */
package com.ls.pub.util;


/**
 * ����:
 * @author ��˧
 *
 * 1:31:28 PM
 */
public class StringUtil {

	/**
	 * �ַ�������ת��:ISO-8859-1ת��gb2312
	 * @param str
	 * @return
	 */
	public static String isoToGB(String str)
	{
		/*if( str==null )
		{
			return null;
		}
		try {
			return new String(str ,"gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}*/
		return str;
	}
	
	 /**
	   * �Ƿ�Ϊ����
	   * @param str String
	   * @return boolean
	   */
	  public static boolean isNumber(String str) { 
		 if(str==null||str.trim().equals(""))
		 {
			 return false;
		 }
	    String num = "0123456789";
	    boolean Flag = true;
	    char c[] = str.toCharArray();

	    for (int i = 0; i <c.length; i++) {
	      if (num.indexOf(c[i]) < 0) {
	        Flag = false; 
	        break;
	      }
	    }
	    return Flag;
	  }
	  
	  /**
	   * �Ƿ�Ϊ���ֺ���ĸ�Ļ����
	   * @param str String
	   * @return boolean
	   */
	  public static boolean isNumberAndChar(String str) { 
		 if(str==null||str.trim().equals(""))
		 {
			 return false;
		 }
	    String num = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    boolean Flag = true;
	    char c[] = str.toCharArray();

	    for (int i = 0; i <c.length; i++) {
	      if (num.indexOf(c[i]) < 0) {
	        Flag = false; 
	        break;
	      }
	    }
	    return Flag;
	  }
	
	/**
	 * �ַ�������ת��:ISO-8859-1ת��gbk
	 * @param str
	 * @return
	 */
	public static String isoToGBK(String str)
	{
		/*if( str==null )
		{
			return null;
		}
		try {
			return new String(str ,"gbk");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}*/
		return str;
	}
	
	/**
	 * �ַ�������ת��:gbkת��ISO-8859-1
	 * @param str
	 * @return
	 */
	public static String gbToISO(String str)
	{
	/*	if( str==null )
		{
			return null;
		}
		try {
			return new String(str.getBytes("gbk"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}*/
		return str;
	}
	
	
	/**
	 * sql������ַ�����������û��''���������ַ���������''������
	 */
	public static String processStringCondition( String str_conditon )
	{
		StringBuffer new_str_condition = new StringBuffer();
		if( str_conditon==null || str_conditon.equals(""))
		{
			return "''";
		}
		String[] conditon =  str_conditon.split(",");
		int i=0;
		new_str_condition.append("'"+conditon[0]+"'");
		for( i=1;i<conditon.length;i++ )
		{
			new_str_condition.append(",'"+conditon[i]+"'");
		}
		
		return new_str_condition.toString();
	}
	
	

	 /**
	  * ��ȡ�ַ���
	  * @param str
	  * @param begin
	  * @param length
	  * @return
	  */
	public static String subString(String str,int begin,int length)
	{
		String s;
		if(str==null||str.trim().equals(""))
		{
			s="";
		}else
		{
			StringBuffer a=new StringBuffer(str);
			if(a.length()>(begin+length))
			{
				s=a.substring(begin,begin+length);
			}
			else
			{
				s=a.toString();
			}
		}
		return s;
	}
	/**
	 * �����ַ���
	 * @param args
	 * @return
	 */
	public static String split(String args){
		String q[] = args.split(",");
		String e = "";
		for(int i = 0 ; i < q.length ; i++){
			String r = "";
			if(q[i] != null && !q[i].equals("") && !q[i].equals("null")){
				e = e+q[i];
				if((i + 1) != q.length){
					e = e+",";
				}
			}
			
		}
		return e;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  
	}

}
