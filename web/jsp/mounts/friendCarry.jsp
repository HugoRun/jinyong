<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.vo.friend.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>


	好友列表: 
	<br/>
	<%
		String mountID=(String)request.getAttribute("mountID");
	   	String mountState=(String)request.getAttribute("mountState");
		List<FriendVO> friendlist=(List<FriendVO>)request.getAttribute("friendlist");
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
	<%=friendVO.getFdName()%>(<%=friendVO.getPMapName() %><%if(friendVO.getDear()>0){ %>,亲密度<%=friendVO.getDear() %><%} %>)
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n8" />
	<postfield name="mountsID" value="<%=mountID%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="scenceID" value="<%=friendVO.getPMap() %>" />
	</go>
	前往
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
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/mounts.do") %>"> 
	<postfield name="page" value="<%=pageno+1%>" />
	<postfield name="cmd" value="n6" />
	<postfield name="mountsID" value="<%=mountID%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(pageno != 0){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/mounts.do") %>"> 
	<postfield name="page" value="<%=pageno-1%>" />
	<postfield name="cmd" value="n6" />
	<postfield name="mountsID" value="<%=mountID%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=pageno+1 %>页/共<%=pageall %>页<%
	}
	}
	%>
	<br/>
		<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n15" />
	</go>
	返回
	</anchor><br/>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
