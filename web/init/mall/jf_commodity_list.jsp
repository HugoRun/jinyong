<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.dp.vo.store.newgood.*,com.dp.vo.store.player.*,com.ls.pub.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%List<NewGoodVO> nglist2=(List<NewGoodVO>)request.getAttribute("jifeng");
               if(nglist2!=null&&nglist2.size()!=0){
       		 %>公告:<%Iterator iter2=nglist2.iterator();
    		  while(iter2.hasNext()){
    			NewGoodVO ngv=(NewGoodVO)iter2.next();
    			%>
现积分X<%=ngv.getGsc()%>,可兑换
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n13" />
<postfield name="spid" value="<%=ngv.getGid()%>" />
</go>
【<%=StringUtil.isoToGB(ngv.getGname())%>】
</anchor>
X1,预购者从速!
<br />
<%}%>
<%}%>
<% List<NewGoodVO> jfsplist=(List<NewGoodVO>)request.getAttribute("jfsplist");
    	   if(jfsplist!=null&&jfsplist.size()!=0){%>
商场积分兑换商品:
<br />
<% Iterator  iter2=jfsplist.iterator();
    	         while(iter2.hasNext()){
    	         NewGoodVO ngvo=(NewGoodVO)iter2.next();%>
<%=ngvo.getGsc()%>积分兑换
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n14" />
<postfield name="spid" value="<%=ngvo.getGid()%>" />
</go><%=StringUtil.isoToGB(ngvo.getGname())%>
</anchor>
×1
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n12" />
<postfield name="spid" value="<%=ngvo.getGid()%>" />
</go>
兑换
</anchor>
<br />
<%}%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/emporiumaction.do")%>">
<postfield name="cmd" value="n11" />
</go>
更多积分兑换商品
</anchor>
<%}%>