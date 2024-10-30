<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.dp.vo.store.newgood.*,com.dp.vo.store.player.*,com.ls.pub.util.*,com.pub.ben.info.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="mall" title="<bean:message key="gamename"/>">
<p>
是否置顶此条祝福!<br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/wishingtree.do">
<postfield name="cmd" value="settop" />
<postfield name="wishing" value="$wishing" />
</go>
是
</anchor>
<br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/wishingtree.do">
<postfield name="cmd" value="index" />
<postfield name="wishing" value="$wishing" />
</go>
否
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>