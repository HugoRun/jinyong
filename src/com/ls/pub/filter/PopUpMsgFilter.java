package com.ls.pub.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.player.PlayerState;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;

/**
 * ����:�����Ƿ��е���ʽ��Ϣ
 * 
 * @author ��˧ 4:12:27 PM
 */
public class PopUpMsgFilter implements Filter {

	private FilterConfig filterCfg;

	public PopUpMsgFilter() {
		super();
	}

	public void init(FilterConfig arg0) throws ServletException {
		this.filterCfg = arg0;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain) {
		
		try {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			
			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
	        
			if( roleInfo!=null )
			{
				int cur_state = roleInfo.getStateInfo().getCurState();
				
				if( cur_state==PlayerState.GENERAL)//ֻ�������ƽ��״̬�ŵ�����Ϣ
				{
					UMsgService uMsgService = new UMsgService();
					UMessageInfoVO uMsgInfo = uMsgService.getMsg(roleInfo.getPPk());
					if( uMsgInfo!=null )
					{	
						request.setAttribute("uMsgInfo", uMsgInfo);
						request.getRequestDispatcher("/popupmsg.do?cmd=n1").forward(request, response);
						return;
					}
					//�ж��Ƿ���PK״̬
					if( roleInfo.getPKState().getOtherNum()>0)//�ж��Ƿ����˹���
					{
						request.getRequestDispatcher("/pk.do?cmd=n7").forward(request, response);
						return;
					}
				} else if ( cur_state == PlayerState.EXTRA) { 
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
	}

}
