package com.ls.pub.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;

/**
 * ����:�����Ƿ��е���ʽ��Ϣ
 * 
 * @author ��˧ 4:12:27 PM
 */
public class SessionValidityFilter implements Filter
{

	Logger logger = Logger.getLogger("log.service");
	private FilterConfig filterCfg;

	public SessionValidityFilter()
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
			
			String uPk = (String)request.getSession().getAttribute("uPk");

			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

			if ( uPk==null || roleInfo==null)//����û���Ϣ��Ч����ת����½����
			{
				logger.info("session�������е���ת����½����ʱ��uPk="+uPk+",roleInfo="+roleInfo);
				request.getRequestDispatcher("/jsp/out_page.jsp").forward(request, response);
				return;
			}
			else if( roleInfo.getBasicInfo().getUPk() != Integer.parseInt(uPk) )
			{
				//����ý�ɫ���ǵ�¼�˺ŵĽ�ɫ���������µ�½����ֹ�û��۸�p_pk
				logger.info("session�������е���ת����½����ʱ��uPk="+uPk+",roleInfo�е�uPk="+roleInfo.getBasicInfo().getUPk());
				request.getRequestDispatcher("/jsp/out_page.jsp").forward(request, response);
				return;
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
