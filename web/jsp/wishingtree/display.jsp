<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.dp.vo.store.newgood.*,com.dp.vo.store.player.*,com.ls.pub.util.*,com.pub.ben.info.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%
String display = (String)request.getAttribute("display");
if(display!=null&&!display.equals("")&&!display.equals("null")){
%><%=display %><%
}
%>
<br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/wishingtree.do">
<postfield name="cmd" value="index" />
</go>
返回
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>