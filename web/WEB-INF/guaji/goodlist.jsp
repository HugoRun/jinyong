<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ls.ben.vo.info.npc.NpcdropVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.map.SceneVO"%>
<%String message = (String)request.getAttribute("message"); %>
<%=message==null?"":message.trim()+"<br/>" %>

请设置拾取的物品（请根据物品栏容量设置拾取物品多少）<br/>
在该地点挂机可能会掉落以下物品：  <br/>
<%
int allPage = (Integer)request.getAttribute("allPage");
int nowPage = (Integer)request.getAttribute("nowPage");
List<NpcdropVO> list = (List)request.getAttribute("list"); 
if(list!=null&&list.size()>0){
for(NpcdropVO nv : list){
%>
<%=nv.getGoodsName() %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n6" />
<postfield name="good_id" value="<%=nv.getGoodsId() %>" />
<postfield name="good_name" value="<%=nv.getGoodsName() %>" />
<postfield name="good_type" value="<%=nv.getGoodsType() %>" />
<postfield name="nowPage" value="<%=nowPage%>" />
</go>
拾取
</anchor><br/>

<%}
if(allPage>1){
	if(allPage >nowPage){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage+1%>" />
	<postfield name="cmd" value="n5" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(nowPage > 1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do") %>"> 
	<postfield name="nowPage" value="<%=nowPage-1%>" />
	<postfield name="cmd" value="n5" />
	</go>
	上一页
	</anchor> 
	<%
	}%>第<%=nowPage %>页/共<%=allPage %>页<%
	}
}else{ %>
没有掉落物品
<%} %>
<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do") %>"> 
	<postfield name="cmd" value="n7" />
	</go>
	下一步
	</anchor> 
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
