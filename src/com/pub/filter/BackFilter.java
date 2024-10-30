package com.pub.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.system.SystemConfig;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.player.RoleService;
import com.lw.vo.sinasys.SinaSysVO;
import com.pm.constant.RandomChar;

public class BackFilter implements Filter
{

	Logger logger = Logger.getLogger("log.service");

	public void destroy()
	{

	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		WrapperResponse wrapperResponse = new WrapperResponse(response);
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();

		
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
		try
		{
			InstanceService is = new InstanceService();
			if(is.ifPlayerOut(role_info)==true){
				request.getRequestDispatcher("/pubbuckaction.do").forward(
						request, response);
			}
			
			List<String> urlOfNoNeedFilter = SystemConfig.UrlOfNoNeedFilter;// ����Ҫ���˵�url

			String curCheckStr = (String) session.getAttribute("curCheckStr");// ��ǰ����֤��
			String userCheckStr = request.getParameter("chair");// �ͻ��˴���������֤��

			if (Channel.SINA == GameConfig.getChannelId())
			{
				String wm = (String) session.getAttribute("wm");
				if (wm == null || wm.equals("null"))
				{
					String wm_bak = request.getParameter("wm");
					if(wm_bak == null || wm_bak.equals("null")){
						wm = "-";
					}else{
						wm = wm_bak;
					}
					session.setAttribute("wm", wm);
				}
				
				int x = LoginService.getOnlineNum();//��ǰ��������
				SinaSysVO vo = new SinaSysVO();
				vo.updateNewIP(wm, x);
				vo.updateNewMv(wm);
				vo.updateNewPv1(wm);
			}

			/*
			 * //ѹ��������
			 * request.getRequestDispatcher(requirpath).forward(request,response);
			 * return;
			 */

			if (urlOfNoNeedFilter.contains(servletPath))
			{
				// ����Ҫ���˵�url,ֱ�����󣬲�����֮��Ĺ�����
				request.getRequestDispatcher(servletPath).forward(request,
						response);
				return;
			}
			else
			{
				logger.info("preCheckStr=" + ";curCheckStr=" + curCheckStr
						+ ";userCheckStr=" + userCheckStr);

				if (userCheckStr != null && curCheckStr != null
						&& !userCheckStr.equals(curCheckStr))// ��ֹ��Һ���
				{
					logger.info("��Һ����ύ");
					request.getRequestDispatcher("/backActive.do").forward(
							request, response);
					return;
				}

				String newCheckStr = getCheckStr();// �����µ���֤��
				session.setAttribute("curCheckStr", newCheckStr);

				chain.doFilter(request, wrapperResponse);// ��������������

				//���浱ǰҳ��ű���Ϣ
				String previourFile = getPreiourFileStr(session,wrapperResponse, curCheckStr, newCheckStr);
				session.setAttribute("PreviourFile", previourFile);

				//���ͻ��˷��ͽű���Ϣ
				response.getWriter().print(previourFile);
				
			}
			// ***ѹ��������ʱȥ����֤...begin
			// chain.doFilter(request, wrapperResponse);
			// ***ѹ��������ʱȥ����֤...end
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if( role_info!=null )
			{
				role_info.save();
			}
		}

	}

	/**
	 * �õ���ǰҳ�������֤��Ľű�
	 * 
	 * @param cur_page_script
	 *            δ������֤��ʱ��ҳ��Ľű�
	 * @param oldCheckStr
	 * @param newCheckStr
	 * @return
	 */
	private String getPreiourFileStr(HttpSession session,
			WrapperResponse wrapperResponse, String oldCheckStr,
			String newCheckStr)
	{
		String cur_page_script = wrapperResponse.getContent();
		int wenHaoChar = cur_page_script.indexOf("<");
		if (wenHaoChar != -1)
		{
			cur_page_script = cur_page_script.substring(wenHaoChar);
		}

		String new_page_script = cur_page_script;// �õ���ǰҳ�������֤��Ľű�

		if (cur_page_script.indexOf("chair=" + oldCheckStr) != -1)
		{
			new_page_script = new_page_script.replaceAll(
					"chair=" + oldCheckStr, "chair=" + newCheckStr);// ���֮ǰҳ����chair�����滻���µ�
			return new_page_script;
		}
		if (new_page_script.indexOf("jsessionid") != -1)
		{
			new_page_script = new_page_script.replaceAll("" + session.getId()
					+ "\\?", "" + session.getId() + "?chair=" + newCheckStr
					+ "&amp;");// �в�action���ӵ��滻

			new_page_script = new_page_script.replaceAll("" + session.getId()
					+ "\"", "" + session.getId() + "?chair=" + newCheckStr
					+ "\"");// �޲�action���ӵ��滻

		}
		else
		{
			new_page_script = new_page_script.replaceAll(".do\\?", ".do?chair="
					+ newCheckStr + "&amp;");// �в�action���ӵ��滻

			new_page_script = new_page_script.replaceAll(".do\"", ".do?chair="
					+ newCheckStr + "\"");// �޲�action���ӵ��滻

			new_page_script = new_page_script.replaceAll(".jsp\\?",
					".jsp?chair=" + newCheckStr + "&amp;");// �޲�jsp���ӵ��滻

			new_page_script = new_page_script.replaceAll(".jsp\"",
					".jsp?chair=" + newCheckStr + "\"");// �޲�jsp���ӵ��滻
		}

		return new_page_script;
	}

	private String getCheckStr()
	{
		return RandomChar.getChars(RandomChar.RANDOM_ALL, 2);
	}

	public void init(FilterConfig arg0) throws ServletException
	{

	}

}
