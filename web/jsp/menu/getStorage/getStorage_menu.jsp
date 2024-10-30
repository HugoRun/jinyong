<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.constant.Wrap"%>
<%@page import="com.ls.ben.dao.storage.WareHouseDao"%> 
<%
	String pPk1 = (String)session.getAttribute("pPk");

	WareHouseDao dao1 = new WareHouseDao();
	
	int emptyNum = 0;
	int num = 0;
    emptyNum = dao1.getEmptyNum(Integer.parseInt(pPk1));
	num = dao1.getPlayerWarehouseNum(Integer.parseInt(pPk1));
	String resultWml=(String)request.getAttribute("resultWml");
	if(resultWml!=null)
	{
		%>
			<%=resultWml%><br/>
		<%
	}
%> 
【仓库】
<anchor>增加仓库栏位<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n6") %>"></go></anchor><br/>
仓库格数:<%=emptyNum %>/<%=num %><br/> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do?cmd=n1&amp;w_type="+Wrap.CURE)%>"></go>药品</anchor> |
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do?cmd=n1&amp;w_type="+Wrap.EQUIP)%>"></go>装备</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do?cmd=n1&amp;w_type="+Wrap.BOOK)%>"></go>仙书</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do?cmd=n1&amp;w_type="+Wrap.REST)%>"></go>材料</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do?cmd=n1&amp;w_type="+Wrap.SHOP)%>"></go>商城</anchor>