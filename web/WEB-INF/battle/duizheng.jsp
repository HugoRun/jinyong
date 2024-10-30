<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="java.util.List"%>
<%@page import="com.ben.leitai.battle.BattlePeo"%>
<%List<BattlePeo> list = (List)request.getAttribute("list"); 
if(list!=null){
int listSize = list.size();
for(int i = 0;i<listSize;i=i+2){
int j = i+1;
BattlePeo bp1 = list.get(i);
BattlePeo bp2 = (j>=listSize?null:list.get(j));
%>
<%=bp1.getName() %><%if(bp2!=null){ %>VS<%=bp2.getName() %><%}else{ %>轮空<%} %><br/>
<%}} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/battle.do?cmd=n1")%>" method="get"></go>返回</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>