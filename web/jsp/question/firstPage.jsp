<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
1.答题系统每天12点至14点开放<br/>
2.20级以上玩家每天可以免费回答一轮<br/>
3.一轮10个题目，每题回答时间20秒<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/question.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="menu_id" value="<%=request.getAttribute("menu_id") %>" />
</go>
开始答题
</anchor>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/question.do")%>">
<postfield name="cmd" value="n4" />
<postfield name="menu_id" value="<%=request.getAttribute("menu_id") %>" />
</go>
查看排名
</anchor>
<br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
