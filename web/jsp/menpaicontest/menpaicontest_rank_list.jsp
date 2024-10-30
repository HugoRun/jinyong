<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
import="com.lw.vo.menpaicontest.MenpaiContestPlayerVO,com.ls.pub.config.GameConfig"
pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<% 
	List list = (List) request.getAttribute("list");
	int pageNo = Integer.parseInt(request.getAttribute("pageNo").toString());
	int pagenum = Integer.parseInt(request.getAttribute("pagenum").toString());
	String menpai = request.getAttribute("menpai").toString();
	%>
【<%=menpai %>排行榜】*依据获得首席弟子的次数进行排行*<br/>
	<%
	if(list!=null && list.size()!=0){
	for (int i = 0; i < list.size(); i++) {
			MenpaiContestPlayerVO vo = (MenpaiContestPlayerVO) list.get(i);
	if(pageNo == 0){
	if(i == 0){
	%>★<%=menpai %>第一★.<%=vo.getP_name()%>(<%=vo.getWin_num() %>次)<%
	}else{
	%><%=i+1 %>.<%=vo.getP_name()%>(<%=vo.getWin_num() %>次)<%
	}
	}else{
	%><%=10*(pageNo-1)+i+1 %>.<%=vo.getP_name()%>(<%=vo.getWin_num() %>次)<%
	}
		%><br/><%}
	%>
	
	<%
		if( pageNo != pagenum && pagenum !=1 )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menpai.do")%>"> 
	<postfield name="cmd" value="Rank" />
	<postfield name="pageNo" value="<%=pageNo+1%>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if(pagenum !=1 && pageNo != 1)
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menpai.do")%>"> 
	<postfield name="cmd" value="Rank" />
	<postfield name="pageNo" value="<%=pageNo-1%>" />
	</go>
	上一页
	</anchor>
	<%
		}
	}else{
	%>暂无排行<%
	}
		%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
