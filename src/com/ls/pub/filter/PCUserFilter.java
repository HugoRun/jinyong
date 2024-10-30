package com.ls.pub.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 功能:判断是否是电脑用户
 *
 * @author 刘帅
 * 3:19:41 PM
 */
public class PCUserFilter implements Filter {
    // 是否使用此过滤器的开关
    int control_switch = 0;

    public void destroy() {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if (control_switch == 0) {
            // 不使用此过滤器
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String ua = request.getHeader("user-agent").toLowerCase();
        if (ua.contains("nt")) {
            // 如果ua里有nt则表示是电脑用户
            request.getRequestDispatcher("/comm/pc_user_hint.jsp").forward(request, servletResponse);
            return;
        }
        chain.doFilter(request, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        control_switch = Integer.parseInt(filterConfig.getInitParameter("control_switch"));
    }

}
