<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="map">
<%
	String good_Name = (String) request.getAttribute("jName");
	String str_Word = (String) request.getAttribute("str_Word");
	String str_Word_2 = (String) request.getAttribute("str_Word_2");
	String str_Word_3 = (String) request.getAttribute("str_Word_3");
	String da = (String) request.getAttribute("da");
	String zhong = (String) request.getAttribute("zhong");
	String xiao = (String) request.getAttribute("xiao");
	String cueWord=(String)request.getAttribute("cueWord");
%>
<p>
	【虎运宝箱】
	<br/>
	<br/>
	本宝箱
	<br/>
	大奖为：<%=da %>
	<br/>
	小奖为：<%=zhong %>
	<br/>
	鼓励奖：<%=xiao %>
	<br/>
	<anchor>
	<%=str_Word%>
	</anchor>
	<br/>
	<anchor>
	<%=str_Word_2%>
	</anchor>
	<br/>
	<anchor>
	<%=str_Word_3%>
	</anchor>
	<br/>
	恭喜你获得了<%=good_Name%>
	<br/>
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/laba.do?cmd=n2")%>" 	method="get"></go>
	我不满意，再刷一次
	</anchor>
	<br/>
	<%=cueWord %>
	<br/>
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/laba.do?cmd=n3")%>" 	method="get"></go>
	领取奖励
	</anchor>
</p>
</card>
</wml>
