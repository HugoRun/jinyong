<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String pwPk = (String) request.getAttribute("pwPk");
		String wName = (String) request.getAttribute("wName");
		String tableType = (String) request.getAttribute("tableType");
		String goodsType = (String) request.getAttribute("goodsType");
		String wId = (String) request.getAttribute("wId");
		
	%>
	该物品装备后将与您绑定，不可再交易或拍卖。您确定要装备吗？
	<br/>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
	<postfield name="cmd" value="n14" />
	<postfield name="pwPk" value="<%=pwPk %>" />
	<postfield name="wName" value="<%=wName %>" />
	<postfield name="tableType" value="<%=tableType %>" />
	<postfield name="goodsType" value="<%=goodsType %>" />
	<postfield name="wId" value="<%=wId %>" /> 
	</go>
	确定
	</anchor>  
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="3" />
	</go>
	取消
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
