package com.pub.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterDisplayFilter implements Filter
{

	public void destroy()
	{
		
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException
	{
		Date dt = new Date();
		
		
		
		
		
	}

	public void init(FilterConfig arg0) throws ServletException
	{
		
	}

}
