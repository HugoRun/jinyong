package com.ls.pub.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

/**
 * ����:��ʱ�¼�����������
 * @author ��˧ 4:12:27 PM
 */
public class TimerEventFilter implements Filter
{
	private FilterConfig filterCfg;

	public TimerEventFilter()
	{
		super();
	}

	public void init(FilterConfig arg0) throws ServletException
	{
		this.filterCfg = arg0;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
	{
		try
		{
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpServletRequest request = (HttpServletRequest) servletRequest;

			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

			if( roleInfo!=null )
			{
				//�����Ƿ��з�˲ʱʱ��
				roleInfo.getEventManager().listener();
			}
			
			filterChain.doFilter(request, response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void destroy()
	{
	}

}
