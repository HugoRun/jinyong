<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<%
	String da = (String) request.getAttribute("da");
	String zhong = (String) request.getAttribute("zhong");
	String xiao = (String) request.getAttribute("xiao");
	String str_Word = (String) request.getAttribute("str_Word");	
	String str_Word_2 = (String) request.getAttribute("str_Word_2");	
	String str_Word_3 = (String) request.getAttribute("str_Word_3");
%>
<p>
	【虎运宝箱】
	<br />
	<br />
	<br />
	本宝箱
	<br />
	大奖为:<%=da %>
	<br />
	小奖为:<%=zhong %>
	<br />
	鼓励奖:<%=xiao %>
	<br />
	<%	if (str_Word.equals("-1")) {
	%>
	<anchor>
	<go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/laba.do?cmd=n1&amp;num=1")%>"	method="get"></go>
	幸运点点看
	</anchor>
	<%	} else {%>
	<anchor><%=str_Word%></anchor>
	<%	}%>
	<br />
	<%	if (str_Word_2.equals("-1")) {%>
	<anchor>
	<go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/laba.do?cmd=n1&amp;num=2")%>"	method="get"></go>
	幸运点点看
	</anchor>
	<%	} else {%>
	<anchor><%=str_Word_2%></anchor>
	<%	}%>
	<br />
	<%	if (str_Word_3.equals("-1")) {%>
	<anchor>
	<go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/laba.do?cmd=n1&amp;num=3")%>"	method="get"></go>
	幸运点点看
	</anchor>
	<%	} else {%>
	<anchor><%=str_Word_3%></anchor>
	<%	}%>
	<br />
	当幸运符内的提示信息全为一样时，可以获得大奖
	<br />
</p>
</card>
</wml>
