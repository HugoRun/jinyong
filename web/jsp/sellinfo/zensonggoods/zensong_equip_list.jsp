<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.QueryPage,java.util.List"%>
<% 
		String pByPk1s = request.getParameter("pByPk");//被请求人的ID
	    
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
<anchor>
<go method="post"    href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
<postfield name="cmd" value="n7" />
<postfield name="pByPk" value="<%=pByPk1s%>" />
<postfield name="pwPk" value="<%=equip.getPwPk()%>" />
</go>
<%=equip.getFullName()%>
</anchor>
<br/>
	<%
	 		}}
	 		
	 	}
%>
<%=equip_page.getPageFoot()%>