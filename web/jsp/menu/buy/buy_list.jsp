<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.npc.NpcShopVO,com.pub.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String menu_id = (String)request.getAttribute("menu_id");
	String resultWml = (String)request.getAttribute("resultWml");
	List npcshops = (List)request.getAttribute("npcshops");
	NpcShopVO npcshop = null;
	
	//分页显示初始化
		int no = 0;//当前页数
		int pageSize = 7;//每页最多显示条数
		int allSize = 1;//总记录数
		if (npcshops != null && npcshops.size() > 0) {
			allSize = npcshops.size();
		} 
		String pageNo = request.getParameter("pageNo");
		if(pageNo == null || pageNo.equals("")){
			pageNo = (String)request.getAttribute("pageNo");
		}
		
		PageBean pageb = new PageBean();
        pageb.init(pageNo,pageSize,allSize,response.encodeURL(GameConfig.getContextPath()+"/buy.do?cmd=n1&amp;menu_id="+menu_id));
		no = pageb.getIndex(); 
		
	if(resultWml != null && !resultWml.equals("")) {
%> 
<%=resultWml %>
<%} %>
物品列表<br/>
<%
//MoneyUtil.changeCopperToStr(npcshop.getNpcShopGoodsbuy() )
	if( npcshops!=null && npcshops.size()!=0 )
	{
		for(int i = (no - 1)*pageSize; i < no * pageSize&&i < npcshops.size() ; i++)
		{
			npcshop = (NpcShopVO)npcshops.get(i);
			%>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/buy.do")%>" >
			<postfield name="cmd" value="n2" />
			<postfield name="npcshop_id" value="<%=npcshop.getNpcshopId() %>" />
			<postfield name="menu_id" value="<%=menu_id %>" />
			<postfield name="pageNo" value="<%=no %>" />
			</go>
			<%=npcshop.getGoodsName() %>  
			</anchor>|<anchor>
			 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/buy.do") %>">
			 <postfield name="cmd" value="n3" />
			 <postfield name="menu_id" value="<%=menu_id %>" />
			 <postfield name="npcshop_id" value="<%=npcshop.getNpcshopId() %>" />
			 <postfield name="pageNo" value="<%=no %>" />
			 </go>
		      购买
			 </anchor>
			 <br/>
			<%
		}
		out.println(pageb.getFooter()); 
 	}
 	else
 	{
 		%>暂无物品可买<% 
 	}
  %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
