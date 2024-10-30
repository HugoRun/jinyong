<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%String sb = (String)request.getAttribute("sb");
String message = (String)request.getAttribute("message");
 %>
 
 <%=sb==null?"":sb.trim() %>
<%=message==null?"":message.trim() %><br/>

<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n5" />
</go>
下一步
</anchor>
<br/>


<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sky/bill.do?cmd=n0")%>" method="get"></go>我要充值</anchor><br/>
    

<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
