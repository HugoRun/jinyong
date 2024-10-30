<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" import="java.util.List,com.lw.vo.wishingtree.WishingTreeVO,com.ls.pub.bean.QueryPage,com.ls.pub.config.GameConfig" %>
<%  
	QueryPage queryPage= (QueryPage)request.getAttribute("queryPage");	
	List<WishingTreeVO> wishing_list = (List<WishingTreeVO>)queryPage.getResult();	
	
	if( wishing_list!=null && wishing_list.size()!=0 )
	{
		for( WishingTreeVO wishing:wishing_list )
		{
		%>
		<%=wishing.getName() %>的祝福:<%=wishing.getWishing() %>!
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wishingtree.do?cmd=top")%>">
		<postfield name="id" value="<%=wishing.getId()%>"/>
		</go>置顶  
		</anchor>
		<br/>
		<%
		}
		if( queryPage.hasNextPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wishingtree.do?cmd=index")%>">
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()+1%>"/>
</go>下一页  
</anchor>
<%
		}
		if( queryPage.hasPreviousPage() )
		{
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wishingtree.do?cmd=index")%>">
<postfield name="page_no" value="<%=queryPage.getCurrentPageNo()-1%>"/>
</go>上一页  
</anchor>
<%
		 %>
第<%= queryPage.getCurrentPageNo()%>/<%=queryPage.getTotalPageCount() %>页
<%
	}
	}
	else
	{
%>
暂无祝福
<%
	}
%>
<br/>


		