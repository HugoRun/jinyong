<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.pub.constant.PropType"%>
<% 
	    String w_typeaa = (String)request.getAttribute("w_type");
	 	QueryPage prop_page = (QueryPage)request.getAttribute("item_page");
		List prop_list = (List)prop_page.getResult();
		PlayerPropGroupVO propGroup = null;
	 	if( prop_list!=null && prop_list.size()!= 0)
	 	{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="w_type" value="<%=w_typeaa%>" />
<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
<postfield name="goods_type" value="<%=propGroup.getPropType()%>" />
<postfield name="isReconfirm" value="<%=propGroup.getPropIsReconfirm()%>" />
<postfield name="page_no" value="<%=prop_page.getCurrentPageNo()%>" />
</go>
<%= propGroup.getPropName()%>x<%=propGroup.getPropNum() %>
</anchor>
<%if(Integer.parseInt(w_typeaa)!=4){ 
	 				if(propGroup.getPropType() == 41){%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
<postfield name="cmd" value="n22" />
<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
<postfield name="wupinlan" value="0" />
</go>
使用
</anchor>

<%}else if(propGroup.getPropType() == PropType.JIEHUN_JIEZHI){ %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
<postfield name="cmd" value="n18" />
<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
<postfield name="wupinlan" value="0" />
</go>
使用
</anchor>
<%} else{%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=w_typeaa%>" />
<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
<postfield name="goods_type" value="<%=propGroup.getPropType()%>" />
</go>
使用
</anchor>
<%} %>
|<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n7" />
<postfield name="w_type" value="<%=w_typeaa%>" />
<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
<postfield name="goods_type" value="<%=propGroup.getPropType()%>" />
<postfield name="isReconfirm" value="<%=propGroup.getPropIsReconfirm()%>" />
</go>
丢弃
</anchor>
<%} }%>