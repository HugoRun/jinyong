<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.MoneyUtil"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<%
    String prop_wml = (String) request.getAttribute("prop_wml");
    String c_id = (String) request.getAttribute("c_id");//商品id
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
    <%=prop_wml%>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackMallAction.do?cmd=n2")%>">
    </go>
    购买
    </anchor>
    <br />
    <anchor>
    <go
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/attackNPC.do?cmd=n17")%>"
        method="get"></go>
    返回
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>