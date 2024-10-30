<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.QueryPage,java.util.List"%>
<% 
	    String w_typeaa = (String)request.getAttribute("w_type");
	 	QueryPage equip_page = (QueryPage)request.getAttribute("item_page");
		List equip_list = (List)equip_page.getResult();
		PlayerEquipVO equip = null;
	 	if( equip_list!=null && equip_list.size()!= 0)
	 	{
	 		for( int i=0;i<equip_list.size();i++)
	 		{
	 			equip = (PlayerEquipVO)equip_list.get(i);
	 			if( equip!=null ){
%>
<anchor><%=equip.getFullName()%>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do")%>">
<postfield name="cmd" value="n4" />
<postfield name="w_type" value="${w_type }" />
<postfield name="page_no" value="${page_no }" />
<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
</go>
</anchor>|<anchor>卖出
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="page_no" value="${page_no}" />
<postfield name="w_type" value="${w_type }" />
<postfield name="goods_id" value="<%=equip.getPwPk()%>" />
<postfield name="reconfirm" value="<%=equip.getWIsreconfirm()%>" />
</go></anchor> 
<br/>
<%
	 		}}
	 	}
%>
<%=equip_page.getPageFoot()%>

