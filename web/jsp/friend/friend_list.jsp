<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.vo.friend.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
<%String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
 %>
 <%=message %><br/>
 <%} %>

	好友列表: 
	<anchor>
	<go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do?cmd=n9") %>" > </go>
	增加好友 
	</anchor>
	<br/>
	<%
	    String hint = (String)request.getAttribute("hint");
	    if(hint!=null){
	    %>
	    <%=hint %><br/> 
	    <% 
	    }
		List friendlist = (List) request.getAttribute("friendlist");
		if(friendlist == null){
	%> 您还没有好友！<br/><%
		}else{
		int pageall = Integer.parseInt(request.getAttribute("pageall").toString());
		int pageno = Integer.parseInt(request.getAttribute("page").toString());
		if (friendlist != null && friendlist.size()!=0) {
			for (int i = 0; i < friendlist.size(); i++) {
				FriendVO friendVO = (FriendVO) friendlist.get(i);
				if(friendVO.getLogin_state()==1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="pByPk" value="<%=friendVO.getFdPk() %>" />
	<postfield name="pByName" value="<%=friendVO.getFdName()%>" />
	<postfield name="page" value="<%=pageno%>" />
	<postfield name="cmd" value="n3" />
	</go>
	<%=friendVO.getFdName()%>(<%=friendVO.getPMapName() %><%if(friendVO.getDear()>0){ %>,亲密度<%=friendVO.getDear() %><%} %>)
	</anchor> 
	<br/>
	<%
	}else
	{%>
	<%=friendVO.getFdName()%>(离线)
	<br/> 
	<%}
	}
	}
	if(pageall!=0){
	if(pageno !=pageall-1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="page" value="<%=pageno+1%>" />
	<postfield name="cmd" value="n2" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(pageno != 0){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="page" value="<%=pageno-1%>" />
	<postfield name="cmd" value="n2" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=pageno+1 %>页/共<%=pageall %>页<%
	}
	}
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
