package com.ls.pub.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ls.model.monitor.ClickSpeedMonitor;
import com.ls.pub.config.GameConfig;
import com.lw.dao.player.SystemDao;

/**
 * ����:������ҵ���ٶ�
 * @author ��˧
 * 3:19:41 PM
 */
public class SpeedControlFilter implements Filter
{

	int time_distance = 1000;//���������ʱ��
	
	public void destroy()
	{

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpSession session = request.getSession();
		List<String> uRlList = new ArrayList<String>();
		uRlList.add("/unfilter.do");
		uRlList.add("/logintransmit.do");
		uRlList.add("/channel.jsp");
		if( session!=null )
		{
			String pPk = (String)session.getAttribute("pPk");
			String uPk = (String)session.getAttribute("uPk");
			ClickSpeedMonitor clickSpeedMonitor = (ClickSpeedMonitor)session.getAttribute("ClickSpeedMonitor");
			String requirpath = request.getServletPath();
			///unfilter.do
			if( clickSpeedMonitor!=null )
			{
				if ( uRlList.contains(requirpath))
				{
					//����Ҫ���˵�url,ֱ�����󣬲�����֮��Ĺ�����
					chain.doFilter(request, servletResponse);
					return;
				}
				long cur_time = Calendar.getInstance().getTimeInMillis();
				
				if( clickSpeedMonitor.isQuickClickSpeed(cur_time))//�ж�����Ƿ����ٶȹ���
				{
					//�ض�����ʾ
					request.getRequestDispatcher("/jsp/time_confine.jsp").forward(request, servletResponse); 
					return;
				} 
				String hint = clickSpeedMonitor.monitor(uPk,pPk,request.getRemoteAddr(),cur_time);//�����ҵ���ٶ�
				if( GameConfig.isDealExceptionUserSwitch() )//�ж�ϵͳ�Ƿ�����ҵĵ���ٶ�
				{
					if(hint != null)
					{
						//�ض�����ʾ
						//����ý�ɫ���ǵ�¼�˺ŵĽ�ɫ���������µ�½����ֹ�û��۸�p_pk
						request.getRequestDispatcher("/login.do?cmd=n9").forward(request, servletResponse);
						return;
					}
				}
			}
			else
			{
				clickSpeedMonitor = new ClickSpeedMonitor(time_distance);
				session.setAttribute("ClickSpeedMonitor",clickSpeedMonitor);
			} 
		}
		chain.doFilter(request, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		time_distance = Integer.parseInt(filterConfig.getInitParameter("time_distance"));
	}

}
