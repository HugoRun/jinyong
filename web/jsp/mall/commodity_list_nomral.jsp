<%@page pageEncoding="UTF-8" import="java.util.List,com.ls.ben.vo.mall.CommodityVO,com.ls.pub.bean.QueryPage,com.ls.pub.config.GameConfig" %>
<%  
	String type = (String)request.getAttribute("type");
	String sell_type = (String)request.getAttribute("sell_type");
	QueryPage queryPage= (QueryPage)request.getAttribute("queryPage");	
	List<CommodityVO> hotsell_commoditys = (List<CommodityVO>)queryPage.getResult();	
	
	if( hotsell_commoditys!=null && hotsell_commoditys.size()!=0 )
	{
		for( CommodityVO commodity:hotsell_commoditys )
		{
//有折扣商品：[购]★5折★2倍经验卡仅55元宝（剩余65）
//无折扣商品：[购]增气丸一100元宝
%>
[<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post"><postfield name="c_id" value="<%=commodity.getId()%>"/><postfield name="page_no" value="<%= queryPage.getCurrentPageNo()%>"/></go>购</anchor>]
<% 
			if( commodity.getDiscount()==-1 )//不打折扣商品显示
			{
%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post">
<postfield name="c_id" value="<%=commodity.getId()%>"/>
<postfield name="type" value="<%=type %>"/>
<postfield name="page_no" value="<%= queryPage.getCurrentPageNo()%>"/>
</go>
<%= commodity.getPropName()%>
</anchor>
<%=commodity.getCurPrice(100)%><%=sell_type%>
<br/>
<%
			}
			else//有折扣商品显示
			{
%>
★<%= commodity.getDiscountDisplay()%>折★
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n6")%>" method="post">
<postfield name="c_id" value="<%=commodity.getId()%>"/>
<postfield name="type" value="<%=type %>"/>
<postfield name="page_no" value="<%= queryPage.getCurrentPageNo()%>"/>
</go>
<%= commodity.getPropName()%>
</anchor>
仅<%=commodity.getCurPrice(100)%><%=sell_type%>
<%
	if( commodity.getCommodityTotal()!=-1 )
	{
%>(剩余<%= commodity.getStoreNum()%>)<%
	}
%>
<br/>
<%
			}
		}//for end
		if( queryPage.hasNextPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="<%=type %>"/>
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()+1%>"/>
</go>下一页  
</anchor>
<%
		}
		if( queryPage.hasPreviousPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
<postfield name="type" value="<%=type %>"/>
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()-1%>"/>
</go>上一页  
</anchor>
<%
		}
		if ( queryPage.getCurrentPageNo() == 1 && queryPage.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
	<postfield name="type" value="<%=type %>"/>
	<postfield name="page_no" value="<%=queryPage.getTotalPageCount() %>"/>
	</go>
	到末页
	</anchor>
	 <%} 
	 if ( queryPage.getCurrentPageNo() == queryPage.getTotalPageCount() && queryPage.getTotalPageCount() > 2 ) 
	 {	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>">
	<postfield name="type" value="<%=type %>"/>
	<postfield name="page_no" value="<%= 1%>"/>
	</go>
	到首页
	</anchor>	 
	<%}  %>
第<%= queryPage.getCurrentPageNo()%>/<%=queryPage.getTotalPageCount() %>页
<%
	}
	else
	{
%>
暂无商品
<%
	}
%>
<br/>