<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.effect.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String quiz_id = (String)request.getAttribute("quiz_id");
	String pg_pk = (String)request.getAttribute("pg_pk");
	PropUseEffect propUseEffect = (PropUseEffect)request.getAttribute("propUseEffect");
	if( propUseEffect!=null )
	{
 		if( propUseEffect.getIsEffected() )
 		{
 		   
 		%> 
 		
		<%=propUseEffect.getEffectDisplay() %><br/>
		
		<%  
		
		String answer_list[] =	propUseEffect.getEffectValue().split(",");
		for(int i=0;i<answer_list.length;i++)
		{
		%>
			 <anchor>
			 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap/quiz.do?cmd=n1") %>">
			 <postfield name="cmd" value="n1"/>
			 <postfield name="quiz_id" value="<%=propUseEffect.getEffectValue1() %>" />
			 <postfield name="player_answer" value="<%=(i+1) %>" />
			 <postfield name="pg_pk" value="<%=pg_pk %>" />
			 </go>
			 <%=answer_list[i] %>
			 </anchor>
			 <br/>
		<%
		}
 		}
 		else
 		{
 		%> 
		<%=propUseEffect.getNoUseDisplay() %>
 
		<%
 		}
	}
	else
	{
		out.print("空指针");
	}
 %> 
<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>"></go></anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
