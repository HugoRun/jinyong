<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.Set"%>
<%@page import="com.ben.guaji.vo.GoodVo"%>
你设置了拾取以下物品：<br/>
<%Set<GoodVo> set = (Set)request.getAttribute("set");
String mess = (String)request.getAttribute("mess");
String pinzhi1 = (String)request.getAttribute("pinzhi1");
if(set!=null&&set.size()>0){
for(GoodVo gv : set){

%>
<%=gv.getGood_name() %><br/>
<%} }else{%>

你没有拾取任何物品<br/>
<%} %>
<%if(pinzhi1!=null&&!pinzhi1.trim().equals("0")){ %>
你还设置了<%=mess %>装备！<br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n9" />
</go>
重新设置   
</anchor>
<br/>

<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>" method="post">
<postfield name="cmd" value="n10" />
</go>
开始挂机
</anchor>
<br/>


<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
