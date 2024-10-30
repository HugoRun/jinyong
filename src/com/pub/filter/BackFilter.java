package com.pub.filter;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.constant.system.SystemConfig;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.player.RoleService;
import com.lw.vo.sinasys.SinaSysVO;
import com.pm.constant.RandomChar;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BackFilter implements Filter {

    Logger logger = Logger.getLogger("log.service");

    public void destroy() {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        WrapperResponse wrapperResponse = new WrapperResponse(response);
        HttpSession session = request.getSession();
        String servletPath = request.getServletPath();

        RoleService roleService = new RoleService();
        RoleEntity role_info = roleService.getRoleInfoBySession(request.getSession());
        try {
            InstanceService is = new InstanceService();
            if (is.ifPlayerOut(role_info)) {
                request.getRequestDispatcher("/pubbuckaction.do").forward(request, response);
            }
            // 不需要过滤的url
            List<String> urlOfNoNeedFilter = SystemConfig.UrlOfNoNeedFilter;
            // 当前的验证串
            String curCheckStr = (String) session.getAttribute("curCheckStr");
            // 客户端传过来的验证串
            String userCheckStr = request.getParameter("chair");

            if (Channel.SINA == GameConfig.getChannelId()) {
                String wm = (String) session.getAttribute("wm");
                if (wm == null || wm.equals("null")) {
                    String wm_bak = request.getParameter("wm");
                    if (wm_bak == null || wm_bak.equals("null")) {
                        wm = "-";
                    } else {
                        wm = wm_bak;
                    }
                    session.setAttribute("wm", wm);
                }

                int x = LoginService.getOnlineNum();//当前在线人数
                SinaSysVO vo = new SinaSysVO();
                vo.updateNewIP(wm, x);
                vo.updateNewMv(wm);
                vo.updateNewPv1(wm);
            }

            /*
             * //压力测试用
             * request.getRequestDispatcher(requirpath).forward(request,response);
             * return;
             */

            if (urlOfNoNeedFilter.contains(servletPath)) {
                // 不需要过滤的url,直接请求，不经过之后的过滤器
                request.getRequestDispatcher(servletPath).forward(request, response);
            } else {
                logger.info("preCheckStr = " + "; curCheckStr = " + curCheckStr + "; userCheckStr = " + userCheckStr);

                if (userCheckStr != null && curCheckStr != null && !userCheckStr.equals(curCheckStr)) {
                    // 防止玩家后退
                    logger.info("玩家后退提交");
                    request.getRequestDispatcher("/backActive.do").forward(request, response);
                    return;
                }
                // 生成新的验证串
                String newCheckStr = getCheckStr();
                session.setAttribute("curCheckStr", newCheckStr);
                // 串联其它过滤器
                chain.doFilter(request, wrapperResponse);

                //保存当前页面脚本信息
                String previourFile = getPreiourFileStr(session, wrapperResponse, curCheckStr, newCheckStr);
                session.setAttribute("PreviourFile", previourFile);

                //给客户端发送脚本信息
                response.getWriter().print(previourFile);

            }
            // ***压力测试临时去掉验证...begin
            // chain.doFilter(request, wrapperResponse);
            // ***压力测试临时去掉验证...end
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (role_info != null) {
                role_info.save();
            }
        }

    }

    /**
     * 得到当前页面加入验证码的脚本
     *
     * @param cur_page_script 未加入验证码时，页面的脚本
     * @param oldCheckStr
     * @param newCheckStr
     * @return
     */
    private String getPreiourFileStr(HttpSession session, WrapperResponse wrapperResponse, String oldCheckStr, String newCheckStr) {
        String cur_page_script = wrapperResponse.getContent();
        int wenHaoChar = cur_page_script.indexOf("<");
        if (wenHaoChar != -1) {
            cur_page_script = cur_page_script.substring(wenHaoChar);
        }
        // 得到当前页面加入验证码的脚本
        String new_page_script = cur_page_script;

        if (cur_page_script.contains("chair=" + oldCheckStr)) {
            // 如果之前页面有chair，则替换成新的
            new_page_script = new_page_script.replaceAll("chair=" + oldCheckStr, "chair=" + newCheckStr);
            return new_page_script;
        }
        if (new_page_script.contains("jsessionid")) {
            // 有参action连接的替换
            new_page_script = new_page_script.replaceAll(session.getId() + "\\?", session.getId() + "?chair=" + newCheckStr + "&amp;");
            // 无参action连接的替换
            new_page_script = new_page_script.replaceAll(session.getId() + "\"", session.getId() + "?chair=" + newCheckStr + "\"");

        } else {
            // 有参action连接的替换
            new_page_script = new_page_script.replaceAll(".do\\?", ".do?chair=" + newCheckStr + "&amp;");
            // 无参action连接的替换
            new_page_script = new_page_script.replaceAll(".do\"", ".do?chair=" + newCheckStr + "\"");
            // 无参jsp连接的替换
            new_page_script = new_page_script.replaceAll(".jsp\\?", ".jsp?chair=" + newCheckStr + "&amp;");
            // 无参jsp连接的替换
            new_page_script = new_page_script.replaceAll(".jsp\"", ".jsp?chair=" + newCheckStr + "\"");
        }
        return new_page_script;
    }

    private String getCheckStr() {
        return RandomChar.getChars(RandomChar.RANDOM_ALL, 2);
    }

    public void init(FilterConfig arg0) throws ServletException {

    }

}
