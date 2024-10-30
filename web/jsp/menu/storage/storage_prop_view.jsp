<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%> 
<%@page import="com.ls.ben.vo.info.partinfo.*,com.ls.pub.constant.*"%> 
<%
	String goods_display = (String)request.getAttribute("goods_display");
	if( goods_display!=null )
	{
%> 
<%=goods_display%>	
<%
	}
	else
	{
		out.print("空指针");
	}
 %> 
 	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="w_type" value="${w_type }" />
	<postfield name="pg_pk" value="${pg_pk }" />
	<postfield name="pageNo" value="${pageNo }" />
	</go>
	储存
	</anchor><br/>	 				
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="${w_type }" />
<postfield name="pageNo" value="${pageNo }" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>  