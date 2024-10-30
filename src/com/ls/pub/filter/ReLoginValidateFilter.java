package com.ls.pub.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

public class ReLoginValidateFilter implements Filter
{

	private FilterConfig filterCfg;

	public ReLoginValidateFilter()
	{
		super();
		// TODO 自动生成构造函数存根
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
			HttpServletRequest request = (HttpServletRequest) servletRequest;

			HttpSession session = request.getSession();
			
			String cur_session_id = session.getId();
			
			RoleService roleService = new RoleService();
			
			RoleEntity role_info = roleService.getRoleInfoBySession(session);
			
			if ( role_info != null )
			{
				HttpSession old_session = role_info.getStateInfo().getSession();

				// 验证旧的sessionid和当前的sessionid是否一样，不能在不同的地方登陆同一账号
				if ( old_session!=null && !cur_session_id.equals(old_session.getId()))
				{
					// 如果不一样，跳到登陆页面，提示你被踢掉线了
					request.getRequestDispatcher("/comm/kick_outline_hint.jsp").forward(servletRequest, servletResponse);
					return;
				}
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void destroy()
	{
	}

}
