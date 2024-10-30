<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
Object w_type =  request.getAttribute("w_type");
Object propUseEffect =  request.getAttribute("propUseEffect");
Object pg_pk =  request.getAttribute("pg_pk");
Object goods_id =  request.getAttribute("goods_id");
Object goods_type =  request.getAttribute("goods_type");
Object page_no = (String)request.getSession().getAttribute("page_no");
List<FriendVO> list = (List<FriendVO>)request.getAttribute("list");
int nowPage = (Integer)request.getAttribute("nowPage");
int allPage = (Integer)request.getAttribute("allPage");
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%} %>
请选择目标:<br/>
<%if(list!=null&&list.size()>0){ 
for(FriendVO fv : list){
%>
<anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/brother.do") %>">
	<postfield name="fd_pk" value="<%=fv.getFdPk()%>" />
	<postfield name="fdName" value="<%=fv.getFdName() %>" />
	<postfield name="fdE" value="<%=fv.getExpShare() %>" />
	<postfield name="cmd" value="n6" />
	<postfield name="w_type" value="<%=w_type %>" />
	    <postfield name="pg_pk" value="<%=pg_pk %>" />
	    <postfield name="goods_id" value="<%=goods_id %>" />
	    <postfield name="goods_type" value="<%=goods_type %>" />
	    <postfield name="page_no" value="<%=page_no %>" />
	</go>
	<%=fv.getFdName()%>
	</anchor> 
	<br/>
<%}
	if(allPage>1){
	if(allPage >nowPage){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/brother.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage+1%>" />
	<postfield name="cmd" value="n5" />
	<postfield name="w_type" value="<%=w_type %>" />
	    <postfield name="pg_pk" value="<%=pg_pk %>" />
	    <postfield name="goods_id" value="<%=goods_id %>" />
	    <postfield name="goods_type" value="<%=goods_type %>" />
	    <postfield name="page_no" value="<%=page_no %>" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(nowPage > 1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/brother.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage-1%>" />
	<postfield name="w_type" value="<%=w_type %>" />
	    <postfield name="pg_pk" value="<%=pg_pk %>" />
	    <postfield name="goods_id" value="<%=goods_id %>" />
	    <postfield name="goods_type" value="<%=goods_type %>" />
	    <postfield name="page_no" value="<%=page_no %>" />
	<postfield name="cmd" value="n5" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=nowPage %>页/共<%=allPage %>页<%
	}
}else{ %>
没有好友
<%} %><br/>
<anchor>
	    <go method="post" href="/wrap.do">
	    <postfield name="cmd" value="n1" />
	    <postfield name="w_type" value="<%=w_type %>" />
	    <postfield name="pg_pk" value="<%=pg_pk %>" />
	    <postfield name="goods_id" value="<%=goods_id %>" />
	    <postfield name="goods_type" value="<%=goods_type %>" />
	    <postfield name="page_no" value="<%=page_no %>" />
	    </go>
	    返回
	    </anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
