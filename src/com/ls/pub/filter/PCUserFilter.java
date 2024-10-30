package com.ls.pub.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * ����:�ж��Ƿ��ǵ����û�
 * @author ��˧
 * 3:19:41 PM
 */
public class PCUserFilter implements Filter
{
	int control_switch = 0;//�Ƿ�ʹ�ô˹������Ŀ���
	public void destroy()
	{

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException
	{

		if( control_switch==0 )//��ʹ�ô˹�����
		{
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		String ua = request.getHeader("user-agent").toLowerCase();
		if( ua.indexOf("nt")!=-1 )//���ua����nt���ʾ�ǵ����û�
		{
			request.getRequestDispatcher("/comm/pc_user_hint.jsp").forward(request, servletResponse); 
			return;
		}
		chain.doFilter(request, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		control_switch = Integer.parseInt(filterConfig.getInitParameter("control_switch"));
	}

}
