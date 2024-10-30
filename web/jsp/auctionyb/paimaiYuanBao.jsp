<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auction.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*,com.pm.vo.auction.*"%>
<%@page import="com.pm.vo.constant.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	int paimaiYuanBao = (Integer)request.getAttribute("paimaiYuanBao");
	String hint = (String)request.getAttribute("hint");
	if (hint != null){
	%>
	<%=hint %>
	<%} %>
您决定卖出【<%=GameConfig.getYuanbaoName() %>】×<%=paimaiYuanBao %>，请输入卖出的价格：<br/>
<input name="copper"  type="text"  maxlength="5"  /><%=GameConfig.getMoneyUnitName() %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n8") %>">
<postfield name="paimaiYuanBao" value="<%=paimaiYuanBao %>" />
<postfield name="copper" value="$(copper)" />
</go>
确定
</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n5")%>" ></go>返回</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>
