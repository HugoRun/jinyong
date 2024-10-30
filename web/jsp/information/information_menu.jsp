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
	%>
	在《新金》封测截至日期1月12日止!<br/>
    ·角色等级达到50级以上者可获得感恩大礼包×1<br/>
	·角色等级达到60级者可获得争霸大礼包×1<br/>
	·建立帮派的玩家可获得雄图大礼包×1<br/>
	以上奖品将在《新金》内测开始时发放到<br/>
	玩家所提交的当乐号所在帐号第一个角色的物品栏中.<br/>
	请务必认真填写！<br/>
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/information.do")%>">
	<postfield name="cmd" value="n2" />
	</go>
	 我50级了
	</anchor>
	<br/>
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/information.do")%>">
	<postfield name="cmd" value="n3" />
	</go>
	 我60级了
	</anchor>
	<br/>
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/information.do")%>">
	<postfield name="cmd" value="n4" />
	</go>
	 我是帮主
	</anchor>
	<br/>

	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
