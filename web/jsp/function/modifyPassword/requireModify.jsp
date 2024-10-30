<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
    <%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s" %>
    <card id="login" title="<s:message key = "gamename"/>">
        <p>
            <%
                //String secondPass = (String)request.getParameter("pass_word");
                String uPk = request.getParameter("uPk");
                if (uPk == null || uPk.equals("")) {
                    uPk = (String) request.getAttribute("uPk");
                }
            %>
            修改游戏帐号登录密码必须验证该帐号的二级密码！请输入该帐号的二级密码:
            <br/>
            <input name="second_pass" type="text" size="6" maxlength="6" format="6N"/>
            <anchor>
                <go method="post"
                    href="<%=response.encodeURL(GameConfig.getContextPath()+"/modifyPassword.do?uPk = "+uPk)%>">
                    <postfield name="cmd" value="n1"/>
                    <postfield name="second_pass" value="$second_pass"/>
                </go>
                传送
            </anchor>
            <br/>
            <anchor>
                <go method="post"
                    href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/function.jsp")%>">
                </go>
                返回
            </anchor>
            <%@ include file="/init/init_time.jsp" %>
        </p>
    </card>
</wml>
