<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pub.ben.info.*,com.ls.ben.vo.info.partinfo.*,com.ls.pub.bean.QueryPage,com.ls.ben.vo.goods.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		QueryPage gold_box_list = (QueryPage)request.getAttribute("gold_box_list");
		  PropVO boxvo = (PropVO) request.getAttribute("boxVO");
		  List<PlayerPropGroupVO> list = (List<PlayerPropGroupVO>)gold_box_list.getResult(); 
		  PlayerPropGroupVO playerPropGroupVO = null;
		  String page_no = (String) request.getAttribute("page_no");
		  String w_type = (String) request.getAttribute("w_type");
		   String pg_pk = (String) request.getAttribute("pg_pk");
		    String goods_id = (String) request.getAttribute("goods_id");
		     String goods_type = (String) request.getAttribute("goods_type");
		  int pg_id = 0;
	%>
	<%
	if(list!=null && list.size() != 0){
		for (int i=0;i<list.size();i++) {
		playerPropGroupVO = list.get(i);
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n2")%>" >
	<postfield name="gold_box_pgpk" value="<%=playerPropGroupVO.getPgPk() %>" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	</go>
	<%=playerPropGroupVO.getPropName() %>
	</anchor>
	<br/>
	<% 
	}
	
		if( gold_box_list.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=gold_box_list.getCurrentPageNo()+1%>" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if( gold_box_list.hasPreviousPage() )
		{
	%> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=gold_box_list.getCurrentPageNo()-1%>" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	</go>
	上一页
	</anchor>
	<%
		}
		if ( gold_box_list.getCurrentPageNo() == 1 && gold_box_list.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n1")%>">
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	<postfield name="page_no" value="<%=gold_box_list.getTotalPageCount() %>"/>
	</go>
	到末页
	</anchor>
	 <%} 
	if ( gold_box_list.getCurrentPageNo() == gold_box_list.getTotalPageCount() && gold_box_list.getTotalPageCount() > 2 ) 
	 {	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n1")%>">
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	<postfield name="page_no" value="<%= 1%>"/>
	</go>
	到首页
	</anchor>	 
	<%}  
		}else {
	out.println("对不起，您没有"+boxvo.getPropName()+"！");
	 }%> 
	 <br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>" >
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="page_no" value="<%=page_no%>" />
	</go>
	返回 
	</anchor><%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
 