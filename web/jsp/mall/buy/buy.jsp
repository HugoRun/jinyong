<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.goods.prop.PropVO,com.ls.pub.util.MoneyUtil,com.ls.ben.vo.mall.CommodityVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	String type = (String)request.getAttribute("type");
	String page_no = (String)request.getAttribute("page_no");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1&amp;w_type=6")%>" method="get"></go>进入包裹商城栏</anchor><br/>
<%	
	if( type!=null )
	{
%>
<%
	}
%>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>