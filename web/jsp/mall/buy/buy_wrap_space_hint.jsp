<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.mall.CommodityVO,com.ls.pub.util.MoneyUtil"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	CommodityVO commodity = (CommodityVO)request.getAttribute("commodity");
	String prop_wml = (String)request.getAttribute("prop_wml");
	String type = (String)request.getAttribute("type");
	String sell_type = (String)request.getAttribute("sell_type");
	String c_id = (String)request.getAttribute("c_id");//商品id
	String page_no = (String)request.getAttribute("page_no");//返回地址
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="mall" title="<bean:message key="gamename"/>">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n9")%>" method="post">
<postfield name="page_no" value="<%=page_no%>"/>
<postfield name="type" value="<%=type%>"/>
</go>
返回上级
</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>