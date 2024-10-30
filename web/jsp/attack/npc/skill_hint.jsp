<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
    <%
        String hint = (String) request.getAttribute("hint");
    %>
    <%=hint%><br />

    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n14")%>"></go>
    返回游戏
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
