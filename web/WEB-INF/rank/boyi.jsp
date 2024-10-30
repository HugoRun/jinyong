<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.lw.vo.lotterynew.LotteryOutPrintVO"%>
<%@page import="com.ls.ben.vo.info.pet.PetInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.web.service.player.RoleService"%>
【博彩榜】*根据彩票中奖金额进行排行*(每月重置)<br/>
<%
RoleService roleService = new RoleService();
List<LotteryOutPrintVO> list = (List)request.getAttribute("list"); 
if(list==null||list.size()==0){
%>
暂时没有排名<br/>
<%} else{
int i = 0;
for(LotteryOutPrintVO petInfoVO : list){
%>
<%if(i==0){ %>
★赌王★
<%}else{%>
<%=(i+1)+"." %>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n5")%>" method="post">
<postfield name="fd_pk" value="<%=petInfoVO.getP_pk() %>" />
<%=roleService.getName(petInfoVO.getP_pk()+"")[0] %></anchor></go>（
<%=petInfoVO.getBonus()%><%=GameConfig.getYuanbaoName() %>
）<br/>
<%i++;}%><br/><%} %>
<%String paimin = (String)request.getAttribute("paimin"); 
if(paimin==null||"".equals(paimin.trim())||"0".equals(paimin.trim())){
%>
您没有参加排名<br/>
<%}else{ %>
您的排名:第<%=paimin %>名<br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n6")%>" method="post">
<postfield name="field" value="boyi" />
</go>返回上级</anchor><br/>
<%@ include file="/WEB-INF/rank/beInclude.jsp"%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
