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
        String petName = (String) request.getAttribute("npcname");
        String npcId = (String) request.getAttribute("npcId");
    %>
    您已经捕捉到<%=petName%>了
    <br />
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n5")%>"></go>
    继续
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
