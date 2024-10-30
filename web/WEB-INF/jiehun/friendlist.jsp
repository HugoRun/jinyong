<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
	好友列表: 
	<br/>
	    <% 
		List friendlist = (List) request.getAttribute("friendlist");
		if(friendlist == null){
	%> 您还没有好友！<br/><%
		}else{
		int pageall = Integer.parseInt(request.getAttribute("pageall").toString());
		int pageno = Integer.parseInt(request.getAttribute("page").toString());
		if (friendlist != null && friendlist.size()!=0) {
			for (int i = 0; i < friendlist.size(); i++) {
				FriendVO friendVO = (FriendVO) friendlist.get(i);
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="pByPk" value="<%=friendVO.getFdPk() %>" />
	<postfield name="pByName" value="<%=friendVO.getFdName()%>" />
	<postfield name="cmd" value="n8" />
	</go>
	<%=friendVO.getFdName()%>
	</anchor> 
	<br/>
	
	<%
	}
	}
	if(pageall!=0){
	if(pageno !=pageall-1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="page" value="<%=pageno+1%>" />
	<postfield name="cmd" value="n7" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(pageno != 0){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="page" value="<%=pageno-1%>" />
	<postfield name="cmd" value="n7" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=pageno+1 %>页/共<%=pageall %>页<%
	}
	}
	%>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="n5" />
	 </go>
返回</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
