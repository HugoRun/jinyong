package com.ls.pub.util.http.parseContent;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：解析形如：key:value的响应内容
 * @author ls
 * Jun 19, 2009
 * 10:20:17 AM
 */
public class ParseNormalContent {
	public Map<String,String> parse( String responseContent )
	{
		Map<String,String> result = new HashMap<String,String>();
		
		if( responseContent==null )
		{
			return result;
		}
		
		String[] lines = responseContent.split("\r\n");
		
		String[] param = null;
		
		for( int i=0;i<lines.length;i++ )
		{
			param = lines[i].split(":");
			if( param.length!=2 )
			{
				continue;
			}
			else
			{
				result.put(param[0], param[1]);
			}
			if( param[0].equals(" result10"))
			{
				System.out.println("a");
			}
		}
		
		return result;
	}
}
