package com.pub.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;

public class ParamParseFilter implements Filter
{

	public void destroy()
	{
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException
	{
		
		if( GameConfig.getChannelId()==Channel.AIR )//空中网解析参数
		{
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			
			java.io.BufferedReader read = request.getReader();
			String line = read.readLine();
			if( line!=null)
			{
				ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request,line);
				chain.doFilter(requestWrapper,servletResponse);
				return;
			}
		}
		chain.doFilter(servletRequest,servletResponse);
	}

	public void init(FilterConfig arg0) throws ServletException
	{
		// TODO Auto-generated method stub

	}

}
