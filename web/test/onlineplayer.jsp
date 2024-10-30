<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache" %>
<%
    response.setContentType("text/vnd.wap.wml");
    RoleCache roleCache = new RoleCache();
    int x = roleCache.getOnlineRoleNum();
%>
<wml>
    <%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s" %>
    <card id="onlineplayer" title="在线人数">
        <p>
            当前在线角色人数:<%=x %>
        </p>
    </card>
</wml>
