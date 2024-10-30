package com.ls.pub.filter;

import com.ls.model.monitor.ClickSpeedMonitor;
import com.ls.pub.config.GameConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 功能:控制玩家点击速度
 *
 * @author 刘帅
 * 3:19:41 PM
 */
public class SpeedControlFilter implements Filter {
    // 两秒间隔点击时间
    int time_distance = 1000;

    public void destroy() {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        List<String> uRlList = new ArrayList<String>();
        uRlList.add("/unfilter.do");
        uRlList.add("/logintransmit.do");
        uRlList.add("/channel.jsp");
        if (session != null) {
            String pPk = (String) session.getAttribute("pPk");
            String uPk = (String) session.getAttribute("uPk");
            ClickSpeedMonitor clickSpeedMonitor = (ClickSpeedMonitor) session.getAttribute("ClickSpeedMonitor");
            String requirpath = request.getServletPath();
            // unfilter.do
            if (clickSpeedMonitor != null) {
                if (uRlList.contains(requirpath)) {
                    // 不需要过滤的url,直接请求，不经过之后的过滤器
                    chain.doFilter(request, servletResponse);
                    return;
                }
                long cur_time = Calendar.getInstance().getTimeInMillis();

                if (clickSpeedMonitor.isQuickClickSpeed(cur_time)) {
                    // 判断玩家是否点击速度过快
                    // 重定向提示
                    request.getRequestDispatcher("/jsp/time_confine.jsp").forward(request, servletResponse);
                    return;
                }
                // 监控玩家点击速度
                String hint = clickSpeedMonitor.monitor(uPk, pPk, request.getRemoteAddr(), cur_time);
                if (GameConfig.isDealExceptionUserSwitch()) {
                    // 判断系统是否监控玩家的点击速度
                    if (hint != null) {
                        // 重定向提示
                        // 如果该角色不是登录账号的角色，让用重新登陆，防止用户篡改p_pk
                        request.getRequestDispatcher("/login.do?cmd=n9").forward(request, servletResponse);
                        return;
                    }
                }
            } else {
                clickSpeedMonitor = new ClickSpeedMonitor(time_distance);
                session.setAttribute("ClickSpeedMonitor", clickSpeedMonitor);
            }
        }
        chain.doFilter(request, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        time_distance = Integer.parseInt(filterConfig.getInitParameter("time_distance"));
    }

}
