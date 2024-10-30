<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auction.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="com.pm.vo.constant.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String display = (String) request.getAttribute("display");
		String page_no = (String) request.getAttribute("page_no");
		String sortType = (String) request.getAttribute("sortType");
		String propName = (String) request.getAttribute("propName");
	%>
<%=display%><br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="page_no" value="<%=page_no %>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	<postfield name="prop_name" value="<%=request.getAttribute("propName") %>" />
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>