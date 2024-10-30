package com.pub.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper
{
	private Map<String,String> userParams = new HashMap<String,String>();

	public ParameterRequestWrapper(HttpServletRequest request,String userParamsStr)
	{
		super(request);
		if( userParamsStr!=null )
		{
			String[] params = userParamsStr.split("&");
			if( params!=null && params.length>0 )
			{
				for(String param:params)
				{
					String[] temp = param.split("=");
					if( temp.length>0 )
					{
						if(temp.length==2)
						{
							try
							{
								userParams.put(temp[0], URLDecoder.decode(temp[1],"utf8"));
							}
							catch (UnsupportedEncodingException e)
							{
								e.printStackTrace();
							}
						}
						else
						{
							userParams.put(temp[0], "");
						}
					}
				}
			}
		}
	}
	
	public String getParameter(String name)
	{
		String value = this.getRequest().getParameter(name);
		if( value==null )
		{
			value = userParams.get(name);
		}
		return value;
	}

}
