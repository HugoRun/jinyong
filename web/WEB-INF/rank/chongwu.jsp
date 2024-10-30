<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.pet.PetInfoVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<% String from = (String)request.getAttribute("from");
if(from==null||"".equals(from.trim())||"0".equals(from.trim())){
%>
【宠物榜】*依据宠物等级进行排行*（每月重置）<br/>
<%}else{ %>
【狂兽榜】*依据宠物攻击进行排行*（每月重置）<br/>
<%} %>
<%
RoleService roleService = new RoleService();
List<PetInfoVO> list = (List)request.getAttribute("list"); 
if(list==null||list.size()==0){
%>
暂时没有排名<br/>
<%} else{
int i = 0;
for(PetInfoVO petInfoVO : list){
%>
<%if(i==0){ 
if(from==null||"".equals(from.trim())||"0".equals(from.trim())){
%>
★珍兽★
<%}else{ %>
★狂兽★
<%}}else{%>
<%=(i+1)+"." %>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n10")%>" method="post">
<postfield name="pet_pk" value="<%=petInfoVO.getPetPk()%>" /></go>
<%=petInfoVO.getPetName() %></anchor>
等级<%=petInfoVO.getPetGrade() %>
<%String p_name =roleService.getName(petInfoVO.getPPk()+"")[0];
if(p_name!=null&&!"".equals(p_name.trim())&&!"null".equals(p_name.trim())){
  %>
（
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n5")%>" method="post">
<postfield name="fd_pk" value="<%=petInfoVO.getPPk() %>" /></go>
<%=p_name  %></anchor>
）
<%} %><br/>
<%i++;}%><br/><%} %>
<%String paimin = (String)request.getAttribute("paimin"); 
if(paimin==null||"".equals(paimin.trim())||"0".equals(paimin.trim())){
%>
您没有参加排名<br/>
<%}else{ %>
您的排名:第<%=paimin %>名<br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n6")%>" method="post">
<postfield name="field" value="chongwu" /></go>
返回上级</anchor><br/>
<%@ include file="/WEB-INF/rank/beInclude.jsp"%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
