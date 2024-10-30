<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String pByPk = (String) request.getAttribute("pByPk");
		String goods_display = (String) request.getAttribute("goods_display");
		String goodsName = (String) request.getAttribute("goodsName");
		String goods_id = (String) request.getAttribute("goods_id");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String w_type = (String) request.getAttribute("w_type");
		if (goods_display != null) {
	%>
	<%=goods_display%> 
	<%
		} else {
			out.print("空指针");
		}
	%>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellpropaction.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="goods_id" value="<%=goods_id %>" />
	<postfield name="goodsName" value="<%=goodsName %>" />
	<postfield name="pg_pk" value="<%=pg_pk %>" /> 
	<postfield name="pByPk" value="<%=pByPk%>" />
	<postfield name="w_type" value="<%=w_type%>" />
	</go>
	确定
	</anchor> 
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/xiaoshou.do")%>">
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="pByPk" value="<%=pByPk%>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
