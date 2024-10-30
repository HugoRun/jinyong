<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.quiz.QuizVO"%>
<%
	QuizVO quiz = (QuizVO)request.getAttribute("quiz");
	String menu_id = (String)request.getAttribute("menu_id");
	if(menu_id == null || menu_id.equals("")){
		menu_id = request.getParameter("menu_id");
	}
	if( quiz!=null )
	{
%> 
	<%=quiz.getQuizContent() %><br/>
	<%String answer_list[] =	quiz.getQuizAnswers().split(",");
	
		for(int i=0;i<answer_list.length;i++)
		{
		%>	
			 <anchor>
			 <go  method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lost.do") %>">
			 <postfield name="cmd" value="n4"/>
			 <postfield name="quiz_id" value="<%=quiz.getId() %>" />
			 <postfield name="player_answer" value="<%=(i+1) %>" />
			 <postfield name="time" value="<%=request.getAttribute("question_time") %>" />
			 <postfield name="menu_id" value="<%=menu_id %>"/>
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
<%@ include file="/WEB-INF/inc/footer.jsp"%>
