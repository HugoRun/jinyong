<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		List list = (List)request.getAttribute("propList");
		int pagenum = Integer.parseInt(request.getAttribute("pagenum").toString());
		int thispage = Integer.parseInt(request.getAttribute("thispage").toString());
		String wupinlan = (String)request.getAttribute("wupinlan");
	%>
	<% 
		if(list.size() != 0){
		for(int i = 0; i < list.size();i++){
		PlayerPropGroupVO propGroupVO = (PlayerPropGroupVO)list.get(i);
		%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
	<postfield name="cmd" value="n2" /> 
	<postfield name="pg_pk" value="<%=propGroupVO.getPgPk()%>" /> 
	<postfield name="goods_id" value="<%=propGroupVO.getPropId()%>" /> 
	<postfield name="goods_type" value="41" />
	<postfield name="wupinlan" value="<%=wupinlan%>" /> 
	</go>
	<%=propGroupVO.getPropName()%>
	</anchor>
		--------
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n22" /> 
	<postfield name="pg_pk" value="<%=propGroupVO.getPgPk()%>" /> 
	<postfield name="wupinlan" value="<%=wupinlan%>" /> 
	</go>
	使用
	</anchor>
	<br/>
	<%
		}
		}else{out.print("无");
		}
	%> <%
		if( thispage != pagenum && pagenum !=0)
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n21" />
	<postfield name="thispage" value="<%=thispage+1%>" />
	<postfield name="wupinlan" value="<%=wupinlan%>" /> 
	</go>
	下一页
	</anchor>
	<%
		}
		if(thispage !=1 && pagenum !=0)
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n21" />
	<postfield name="thispage" value="<%=thispage-1%>" />
	<postfield name="wupinlan" value="<%=wupinlan%>" /> 
	</go>
	上一页
	</anchor>
	<%
		}
	 %>
	 <br/> 
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>" ></go>返回</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
