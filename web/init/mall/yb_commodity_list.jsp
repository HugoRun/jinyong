<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.dp.vo.store.newgood.*,com.dp.vo.store.player.*,com.ls.pub.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%List<NewGoodVO> nglist1=(List<NewGoodVO>)request.getAttribute("teseg");
   			if(nglist1!=null&&nglist1.size()!=0){
   			 %>公告:<%
    		Iterator iter1=nglist1.iterator();
    		while(iter1.hasNext()){
    			NewGoodVO ngv=(NewGoodVO)iter1.next();
    			%>
<%if(ngv.getGoption()!=null){%><%=StringUtil.isoToGB(ngv.getGoption())+""%>
<%}%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n8" />
<postfield name="spid" value="<%=ngv.getGid()%>" />
</go><%=StringUtil.isoToGB(ngv.getGname())%>
</anchor>
【<%=GameConfig.getYuanbaoName() %>】×<%=ngv.getGyb()%>
<%if(ngv.getGisxs()==1){%>每天的<%=ngv.getGsta()%>点到<%=ngv.getGeta()%>点限时<%}%>
<%if(ngv.getGisxs()==2){%>在<%=ngv.getGstb()%>到<%=ngv.getGetb()%>内<%}%>
<%if(ngv.getGisxs()==3){%>每人限量<%=ngv.getGlimit()%>个<%}%>
<%if(ngv.getGisxs()==4){%>每天的<%=ngv.getGsta()%>点到<%=ngv.getGeta()%>点,每人限量<%=ngv.getGlimit()%>个<%}%>
<%if(ngv.getGisxs()==5){%>从<%=ngv.getGstb()%>到<%=ngv.getGetb()%>每人限量<%=ngv.getGlimit()%>个<%}%>
<%if(ngv.getGisxs()==6){%>每天的<%=ngv.getGsta()%>点到<%=ngv.getGeta()%>点,打<%=ngv.getGzk()%>折<%}%>
<%if(ngv.getGisxs()==7){%>从<%=ngv.getGstb()%>到<%=ngv.getGetb()%>打<%=ngv.getGzk()%>折<%}%>
<%if(ngv.getGisxs()==8){%>打<%=ngv.getGzk()%>折<%}%>
出售,预购者从速!
<br />
<%}
    		}%>
<%
    		List<NewGoodVO> nglist=(List<NewGoodVO>)request.getAttribute("nglist");
    		if(nglist!=null&&nglist.size()!=0){%>
今日商城特色商品:
<br />
<%Iterator iter=nglist.iterator();
    		while(iter.hasNext()){
    			NewGoodVO ngv=(NewGoodVO)iter.next();
    			%>
<% if(ngv.getGoption()!=null){%><%=StringUtil.isoToGB(ngv.getGoption())+","%>
<%}%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n8" />
<postfield name="spid" value="<%=ngv.getGid()%>" />
</go><%=StringUtil.isoToGB(ngv.getGname())%>
</anchor>
价格【元宝】×<%=ngv.getGyb()%>
<%if(ngv.getGisxs()==1){%>每天的<%=ngv.getGsta()%>点到<%=ngv.getGeta()%>点限时<%}%>
<%if(ngv.getGisxs()==2){%>在<%=ngv.getGstb()%>到<%=ngv.getGetb()%>内<%}%>
<%if(ngv.getGisxs()==3){%>每人限量<%=ngv.getGlimit()%>个<%}%>
<%if(ngv.getGisxs()==4){%>每天的<%=ngv.getGsta()%>点到<%=ngv.getGeta()%>点,每人限量<%=ngv.getGlimit()%>个<%}%>
<%if(ngv.getGisxs()==5){%>从<%=ngv.getGstb()%>到<%=ngv.getGetb()%>每人限量<%=ngv.getGlimit()%>个<%}%>
<%if(ngv.getGisxs()==6){%>每天的<%=ngv.getGsta()%>点到<%=ngv.getGeta()%>点,打<%=ngv.getGzk()%>折<%}%>
<%if(ngv.getGisxs()==7){%>从<%=ngv.getGstb()%>到<%=ngv.getGetb()%>打<%=ngv.getGzk()%>折<%}%>
<%if(ngv.getGisxs()==8){%>打<%=ngv.getGzk()%>折<%}%>
出售！
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="spid" value="<%=ngv.getGid()%>" />
</go>
购买
</anchor>
<br />
<%
    			}
    			%>
-------------------------------
<br />
<%
    		}
    	%>
<%
   	    	List<WeekGoodVO> wglist=(List<WeekGoodVO>)request.getAttribute("wglist");
   	    	if(wglist!=null&&wglist.size()!=0){%>
本周热销商品:
<br />
<%
   	    	Iterator iter1=wglist.iterator();
   	    	while(iter1.hasNext()){
   	    		WeekGoodVO wgv=(WeekGoodVO)iter1.next();
   	    		%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n8" />
<postfield name="spid" value="<%=wgv.getGid()%>" />
</go><%=StringUtil.isoToGB(wgv.getGname())%>
</anchor>
(已卖出<%=wgv.getJycount()%>份)
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="spid" value="<%=wgv.getGid()%>" />
</go>
购买
</anchor>
<br />
<%
   	    	}
   	     %>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n10" />
</go>
更多热销商品
</anchor>
<br />
----------------------
<br />
<%}%>