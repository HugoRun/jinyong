<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="java.util.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="map" title="<bean:message key="gamename"/>">
<p>
    功能列表
    <br />
    系统设置
    <br />
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/shortcut.do?cmd=n1")%>"></go>
    快捷键设置
    </anchor>
    <br />
    帮会信息
    <br />
    队伍信息
    <br />
    黑名单
    <br />
    ----------------------
    <br />
    <anchor>
    <prev />
    返回
    </anchor>
    <%@ include file="/init/init_timeq.jsp"%>


</p>
</card>
</wml>
