<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page
	import="com.ls.ben.vo.info.effect.*,com.ls.ben.dao.info.partinfo.PlayerPropGroupDao,com.ls.ben.vo.info.partinfo.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String w_type = (String) request.getAttribute("w_type");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String goods_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");
		String page_no = (String)request.getSession().getAttribute("page_no");
	%>
使用该道具后宠物等级大于角色等级10级以上,导致其暂时不能出战,你确定使用吗?<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n23" />
<postfield name="w_type" value="<%=w_type%>" />
<postfield name="pg_pk" value="<%=pg_pk%>" />
<postfield name="goods_id" value="<%=goods_id%>" />
<postfield name="goods_type" value="<%=goods_type%>" />
</go>
确定
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1")%>">
<postfield name="w_type" value="<%=w_type %>" />
<postfield name="page_no" value="<%=page_no %>" />
</go>
返回
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
