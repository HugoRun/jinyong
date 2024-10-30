<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.pub.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
     <%
     String uPk = (String)request.getAttribute("uPk");
     
     String pName = (String)request.getAttribute("pName");
     String pGrade = (String)request.getAttribute("pGrade");
     if(Integer.valueOf(pGrade) <40) {
     if(GameConfig.getChannelId() == Channel.AIR){
     %>名称为  <%=StringUtil.isoToGBK(pName) %>的角色将永久删除，该角色删除后无法恢复，你确认要永久删除吗？<%
     }else{
     %>名称为  <%=StringUtil.isoToGBK(pName) %>  的角色24小时后永久删除，24小时内该角色可以被恢复！<% 
     }
     } else { %>
	 四十级以上的人物不可被删除！
	 <%} %>
	 <br/>
<% if(GameConfig.getChannelId() == Channel.AIR){
%><anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n12")%>"></go>确定</anchor><br/> <%
}else{
%><anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n1")%>"></go>确定</anchor><br/> <%
} %>	
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3") %>">
<postfield name="uPk" value="<%=uPk %>" />
</go>返回首页</anchor>
</p>
</card>
</wml>
