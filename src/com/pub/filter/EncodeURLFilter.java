/**
 * 
 */
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

public class EncodeURLFilter implements Filter
{
	private String encode = "UTF-8";

	public void destroy()
	{

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		//request.setCharacterEncoding(this.encode);
		//response.setContentType("text/html;charset= " + encode);
		HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
		HttpServletResponse httpServletResponse = ((HttpServletResponse) response);
		httpServletResponse.encodeURL(httpServletRequest.getRequestURI());
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	public void init(FilterConfig config) throws ServletException
	{
		this.encode = config.getInitParameter("encode ");
	}

}
