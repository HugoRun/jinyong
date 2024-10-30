package com.ls.pub.filter;

import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能:监听是否有弹出式消息
 *
 * @author 刘帅 4:12:27 PM
 */
public class SessionValidityFilter implements Filter {

    Logger logger = Logger.getLogger("log.service");
    private FilterConfig filterCfg;

    public SessionValidityFilter() {
        super();
    }

    public void init(FilterConfig arg0) throws ServletException {
        this.filterCfg = arg0;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/vnd.wap.wml; charset=UTF-8");
            // 排除过滤的文件
            if(request.getServletPath().startsWith("/login")){
                filterChain.doFilter(request, response);
                return;
            }
            //
            String uPk = (String) request.getSession().getAttribute("uPk");
            RoleService roleService = new RoleService();
            RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
            // 进行过滤
            if (uPk == null || roleInfo == null) {
                // 如果用户信息无效则跳转到登陆界面
                logger.debug("session监听器中的跳转到登陆界面时的uPk = " + uPk + ", roleInfo = " + roleInfo);
                request.getRequestDispatcher("/jsp/out_page.jsp").forward(request, response);
                return;
            } else if (roleInfo.getBasicInfo().getUPk() != Integer.parseInt(uPk)) {
                // 如果该角色不是登录账号的角色，让用重新登陆，防止用户篡改p_pk
                logger.debug("session监听器中的跳转到登陆界面时的uPk = " + uPk + ", roleInfo中的uPk = " + roleInfo.getBasicInfo().getUPk());
                request.getRequestDispatcher("/jsp/out_page.jsp").forward(request, response);
                return;
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void destroy() {
    }

}
