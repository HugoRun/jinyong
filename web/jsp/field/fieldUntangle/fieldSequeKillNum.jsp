<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auction.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*,com.pm.vo.field.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="com.pub.ben.info.Expression"%>
<%@page import="com.pm.vo.constant.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%
	QueryPage field_kill = (QueryPage)request.getAttribute("field_kill");
	List<FieldKillVO> kill_list = (List<FieldKillVO>)field_kill.getResult();
	String menu_id = (String)request.getAttribute("menu_id");
	
	FieldKillVO vo = null;
	%>
	本场排行榜
	<br/>
		
	 <%
	 	if( kill_list!=null && kill_list.size()!= 0)
	 	{
	 		for( int i=0;i<kill_list.size();i++)
	 		{
	 			vo = (FieldKillVO)kill_list.get(i);
	 			
	 			%> 
	 				<%=(field_kill.getCurrentPageNo()-1)*field_kill.getPageSize()+i+1 %>.<%=vo.getKillerName() %>&nbsp;
	 				(<%=Expression.getCampName(vo.getKillerCamp()) %>)&nbsp;<%=vo.getKillerGrade() %>级
	 				&nbsp;杀死<%=vo.getKilledNum() %><br/>
	 			<%
	 		}
	 	}
	 	else
	 	{
	 		out.print("无");
	 	}
	  %> 
	    <%
		if( field_kill.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fielduntangle.do?cmd=n2")%>">
	<postfield name="page_no" value="<%=field_kill.getCurrentPageNo()+1%>" />
	</go>
	下一页
	</anchor>
	  
	<%
		}
		if( field_kill.hasPreviousPage() )
		{
	%> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fielduntangle.do?cmd=n2")%>">
	<postfield name="page_no" value="<%=field_kill.getCurrentPageNo()-1%>" />
	</go>
	上一页
	</anchor> 
	<%
		}
	 %>
	  <br/>
	  <anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fielduntangle.do")%>">
			<postfield name="cmd" value="n1" />
			<postfield name="menu_id" value="<%=menu_id %>" />
			</go>
			返回
		 </anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
