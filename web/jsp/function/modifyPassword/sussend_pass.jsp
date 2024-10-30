<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.*" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
    <%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s" %>
    <card id="login" title="<s:message key = "gamename"/>">
        <p>
            <%

                String auth = (String) request.getAttribute("authenPass");
                String hint = (String) request.getAttribute("hint");
                if (auth.equals("erjipasswordistrue")) {
                    if (hint != null && !hint.equals("")) {
            %>
                <%=hint %>
            <%} %>

            请输入您新的帐号登录密码:
            <input name="pass_word" type="text" size="6" maxlength="11" format="11M"/>
            <br/>
            <anchor>
                <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/modifyPassword.do")%>">
                    <postfield name="cmd" value="n2"/>
                    <postfield name="pass_word" value="$pass_word"/>
                </go>
                传送
            </anchor>
            <%} else { %>
            空指针！
            <%} %>
            <%@ include file="/init/init_time.jsp" %>
        </p>
    </card>
</wml>
