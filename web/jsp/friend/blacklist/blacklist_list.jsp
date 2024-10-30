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
	黑名单列表:
	<br/>
	<%
		List list = (List) request.getAttribute("list");
		int pageall = Integer.parseInt(request.getAttribute("pageall").toString());
		int pagenum = Integer.parseInt(request.getAttribute("pagenum").toString());
		if (list != null && list.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				BlacklistVO blacklistVO = (BlacklistVO) list.get(i);
	%> 
	<%=blacklistVO.getBName()%>  
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
	<postfield name="cmd" value="n4" />
	<postfield name="pByPk" value="<%=blacklistVO.getBlPk() %>" />
	</go>
	踢出
	</anchor>
	<br/>
	<%
		}
		} else {
	%>您还没有黑名单!<br/><%
		}
	%>
	<%if(pageall!=0){
	if(pagenum !=pageall-1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
	<postfield name="page" value="<%=pagenum+1%>" />
	<postfield name="cmd" value="n3" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(pagenum != 0){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do")%>">
	<postfield name="page" value="<%=pagenum-1%>" />
	<postfield name="cmd" value="n3" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=pagenum+1 %>页/共<%=pageall %>页<%
	}
	%>
	<br/>
	<anchor>
	<go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>">
	</go>
	返回
	</anchor>
	
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
