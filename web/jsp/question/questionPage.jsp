<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.quiz.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>  
<%
	QuizVO quiz = (QuizVO)request.getAttribute("quiz");
	String menu_id = (String)request.getAttribute("menu_id");
	if(menu_id == null || menu_id.equals("")){
		menu_id = request.getParameter("menu_id");
	}
	if( quiz!=null )
	{
%> 
第<%=request.getAttribute("number")  %>题<br/>
	<%=quiz.getQuizContent() %><br/>
	<%String answer_list[] =	quiz.getQuizAnswers().split(",");
	
		for(int i=0;i<answer_list.length;i++)
		{
		%>	
			 <anchor>
			 <go  method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/question.do") %>">
			 <postfield name="cmd" value="n3"/>
			 <postfield name="quiz_id" value="<%=quiz.getId() %>" />
			 <postfield name="player_answer" value="<%=(i+1) %>" />
			 <postfield name="time" value="<%=request.getAttribute("question_time") %>" />
			 <postfield name="menu_id" value="<%=menu_id %>"/>
			 <postfield name="question_num" value="<%=request.getAttribute("number") %>"/>
			 </go>
			 <%=answer_list[i] %>
			 </anchor>
			 <br/>
	<%} %>
<%
	}
	else
	{
		out.print("空指针");
	}%> 

<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
