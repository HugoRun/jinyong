<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
    <%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s" %>
    <card id="timeout" title="<s:message key = "gamename"/>">
        <p>
            <%
                String out_time_hint = GameConfig.getTimeoutHint();
                String login_platform_url = GameConfig.getUrlOfLoginPlatform();
            %>
                <%=out_time_hint%>
            <br/>
            <%@ include file="/init/return_url/return_zhuanqu.jsp" %>
        </p>
    </card>
</wml>