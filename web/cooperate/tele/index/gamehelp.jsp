<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%><%@taglib
	uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="aboute" title="<s:message key = "gamename"/>">
<p>
	游戏详细帮助信息可以在游戏主页面下方的“助”来查看。
	<br />
	在洪荒的世界中，装备分为武器，防具（头饰、衣服、腰带、鞋子）和3件法宝，<br/>即玩家共可穿着8件装备，其中法宝可重复穿戴。装备共有五种品质，<br/>具体为普通装备(白装)、宝器(蓝装)、后天灵器(绿装)、先天灵宝(紫装)、<br/>先天至宝(橙装)，其中先天至宝属不可掉落装备，只为活动赠与，<br/>且为唯一一件。套装穿着齐全，还可获得而外的属性加成效果。装备还具有通过特殊的材料，<br/>进行升级、五行转换、镶嵌宝石等功能，具体请详见游戏中的帮助信息。<br/>
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/login.do?cmd=n3")%>"
		method="get"></go>
	返回上一级
	</anchor>
</p>
</card>
</wml>
