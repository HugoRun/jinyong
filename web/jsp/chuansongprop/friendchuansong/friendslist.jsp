<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.vo.friend.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.langjun.util.LangjunConstants"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	请选择你要传送到好友的所在地点: 
	<br/>
	<%
		String w_type = (String) request.getAttribute("w_type");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String prop_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");
		List friendlist = (List) request.getAttribute("friendlist");
		int pageall = Integer.parseInt(request.getAttribute("pageall").toString());
		int pageno = Integer.parseInt(request.getAttribute("page").toString());
		if (friendlist != null && friendlist.size()!=0) {
			for (int i = 0; i < friendlist.size(); i++) {
				FriendVO friendVO = (FriendVO) friendlist.get(i);
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendscs.do") %>"> 
	<postfield name="p_pk" value="<%=friendVO.getFdPk() %>" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	<postfield name="page" value="<%=pageno%>" />
	<postfield name="cmd" value="n2" />
	</go>
	<%if(friendVO.getFdPk()!=LangjunConstants.LANGJUN_PPK&&friendVO.getFdPk()!=LangjunConstants.XIANHAI_LANGJUN){ %>
	<%=friendVO.getFdName()%>
	(<%=friendVO.getPMapName() %>)
	<%}else{ %>
	<%=LangjunConstants.LANGJUN_NAME %>
	<%} %>
	</anchor> 
	<br/>
	<%
	}
	}
	if(pageall!=0){
	if(pageno !=pageall-1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendscs.do") %>"> 
	<postfield name="page" value="<%=pageno+1%>" />
	<postfield name="cmd" value="n1" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(pageno != 0){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendscs.do") %>"> 
	<postfield name="page" value="<%=pageno-1%>" />
	<postfield name="cmd" value="n1" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=pageno+1 %>页/共<%=pageall %>页<%
	}
	%>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do") %>"> 
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="goods_type" value="<%=goods_type %>" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	<postfield name="goods_id" value="<%=prop_id %>" />
	</go>
	返回
	</anchor>
</p>
</card>
</wml>
