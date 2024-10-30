package com.ls.web.action.cooperate.youvb;

import com.ls.ben.vo.cooperate.dangle.PassportVO;
import com.ls.pub.constant.Channel;
import com.ls.pub.yeepay.HttpUtils;
import com.ls.web.service.cooperate.dangle.PassportService;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginAction extends Action {
    Logger logger = Logger.getLogger("log.service");

    /**
     * 游玩吧渠道登录
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws IOException
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String xsid = request.getParameter("xsid");
        String login_params = request.getQueryString();
        String login_ip = request.getRemoteAddr();
        Map<String, String> map = new HashMap<String, String>();
        map.put("xsid", xsid);
        String url = "http://login.youvb.cn/ul/y_xsid.jsp";
        List result = null;
        try {
            // 发起请求
            result = HttpUtils.URLGet(url, map);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        String rs = "";
        for (int i = 0; i < result.size(); i++) {
            rs = (String) result.get(i);
            if (rs != null && !rs.equals("")) {
                break;
            }
        }
        // 验证游玩吧的用户
        if (rs != null && rs.equals("1")) {
            logger.info("用户验证失败");
            return mapping.findForward("fail");
        } else {
            String[] passport_youvb = rs.split("@@@@@");

            PassportService passportService = new PassportService();
            PassportVO passport = passportService.loginFromYouvb(passport_youvb[0], login_ip);

            // 登陆验证失败
            if (passport == null || passport.getUPk() == -1) {
                logger.info("用户验证失败");
                return mapping.findForward("fail");
            } else {
                login_params = login_params.replaceAll("&", "&amp;");
                int uPk = passport.getUPk();
                String skyid = passport.getUserId();
                session.setAttribute("uPk", uPk + "");
                session.setAttribute("skyid", skyid);
                session.setAttribute("xsid", xsid);
                session.setAttribute("user_name", skyid);
                session.setAttribute("channel_id", Channel.YOUVB + "");
                session.setAttribute("login_params", login_params);// 登陆参数
                session.setAttribute("ssid", passport_youvb[0]);
                return mapping.findForward("success");
            }
        }
    }
}
