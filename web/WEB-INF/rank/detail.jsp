<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<c:choose>
<c:when test="${field=='r_ans'||field=='r_open'}">
<%@ include file="inc/huodong_menu.jsp"%>
</c:when>
<c:otherwise>
<%@ include file="inc/geren_menu.jsp"%>
</c:otherwise>
</c:choose>


<%@page import="com.ben.rank.model.Rank"%>
<%@page import="com.ben.rank.model.RankVo"%>

<%
String field = (String)request.getAttribute("field");
%>

<%List<RankVo> list = (List)request.getAttribute("list"); 
if(list==null||list.size()==0){
if(RankConstants.FILED_TYPE.get(field.trim())==13){
%>
暂未开放<br/>
<%}else{ %>
暂时没有排名<br/>
<%}} else{
int i = 0;
for(RankVo rank : list){
%>
<%
if(field!=null){
switch(RankConstants.FILED_TYPE.get(field.trim())) {
case 1: %><%=i==0?"★至尊★":((i+1)+".") %><%break;//等级
case 2: %><%=i==0?"★戮仙★":((i+1)+".")%><%break;//杀手
case 3: %><%=i==0?"★神仙侠侣★":((i+1)+".")%><%break;//爱情甜蜜
case 4: %><%=i==0?"最恶之人":i==1?"老二":i==2?"老三":i==3?"老四":i==4?"老五":i==5?"老六":i==6?"老七":i==7?"老八":i==8?"老九":i==9?"老小":""%><%break;//罪恶
case 5: %><%=i==0?"★无双国士★":((i+1)+".")%><%break;//荣誉
case 6: %><%=i==0?"★俗神★":((i+1)+".")%><%break;//金银
case 7: %><%=i==0?"★财神★":((i+1)+".")%><%break;//元宝
case 8: %><%=i==0?"★武林巨匠★":((i+1)+".")%><%break;//声望
case 9: %><%=i==0?"★福仙★":((i+1)+".")%><%break;//开光
case 10: %><%=i==0?"★至尊大帝★":((i+1)+".")%><%break;//VIP
case 11: %><%=""%><%break;//杀怪
case 12: %><%=i==0?"★屠夫★":((i+1)+".")%><%break;//杀怪
case 13: %><%=""%><%break;//威望榜
case 14: %><%=i==0?"★瘟神★":((i+1)+".")%><%break;//死亡榜
case 15: %><%=i==0?"★BOSS杀★":((i+1)+".")%><%break;//击杀榜
case 16: %><%=i==0?"★天朝重将★":((i+1)+".")%><%break;//忠贞榜
case 17: %><%=i==0?"★天赐商才★":((i+1)+".")%><%break;//生意榜
case 18: %><%=i==0?"★铁血义气★":((i+1)+".")%><%break;//义气榜
case 19: %><%=i==0?"★天师★":((i+1)+".")%><%break;//神算榜
case 20: %><%=i==0?"★无双猛士★":((i+1)+".")%><%break;//猛将榜
case 24: %><%=i==0?"★天下第一★":((i+1)+".")%><%break;//江湖圣榜
case 25: %><%=i==0?"★赌王★":((i+1)+".")%><%break;//博彩榜
case 26: %><%=i==0?"★冒险王★":((i+1)+".")%><%break;//探险榜
case 27: %><%=i==0?"★火眼金睛★":((i+1)+".")%><%break;//杀死千面郎君
case 28: %><%=i==0?"★凌波微步★":((i+1)+".")%><%break;//成为千面郎君
default :%><%=i==0?"★巨侠★":((i+1)+".")%><%break;//等级
}}
i++;
%>
<%if(field.trim().equals("dear")||field.trim().equals("yi")){ %>
<%=rank.getP_name().trim()  %>
<%}else{ %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n5")%>" method="post">
<postfield name="fd_pk" value="<%=rank.getP_pk() %>" />
<postfield name="field" value="${field }" />
</go>
<%=rank.getP_name().trim() %></anchor>
<%} %>
<%=rank.getP_menpai()==null||"".equals(rank.getP_menpai().trim())?"":"("+rank.getP_menpai().trim()+")" %>
<%
if(field!=null){
switch(RankConstants.FILED_TYPE.get(field.trim())) {
case 1: %><%="LV:"+rank.getP_level()%><%break;//等级
case 2: %><%="（杀戮"+rank.getTong()+"人）"%><%break;//杀手
case 3: %><%="和 "+rank.getTong1()+"（"+rank.getTong()+"）"%><%break;//爱情甜蜜
case 4: %><%="（"+rank.getTong()+"点）"%><%break;//罪恶
case 5: %><%="（"+rank.getTong()+"点）"%><%break;//荣誉
case 6: %><%="（"+rank.getTong()+"灵石）"%><%break;//金银
case 7: %><%="（"+rank.getTong()+""%><%=GameConfig.getYuanbaoName() %>)<%break;//元宝
case 8: %><%="（"+rank.getTong()+"点）"%><%break;//声望
case 9: %><%="（"+rank.getTong()+"个）"%><%break;//开光
case 11: %><%="暂未开放"%><%break;//杀怪
case 12: %><%="（"+rank.getTong()+"只）"%><%break;//杀怪
case 13: %><%="暂未开放"%><%break;//威望榜
case 14: %><%="（"+rank.getTong()+"次）"%><%break;//死亡榜
case 15: %><%="（"+rank.getTong()+"点）"%><%break;//击杀榜
case 16: %><%="（"+rank.getTong()+"分钟）"%><%break;//忠贞榜
case 17: %><%="（"+rank.getTong()+"次）"%><%break;//生意榜
case 18: %><%="和 "+rank.getTong1()+"（"+rank.getTong()+"点）"%><%break;//义气榜
case 19: %><%="（"+rank.getTong()+"道）"%><%break;//神算榜
case 20: %><%="（"+rank.getTong()+"回）"%><%break;//猛将榜
case 24: break;
case 25:%><%="("+rank.getTong()+""%><%=GameConfig.getYuanbaoName() %>)<%break;//猛将榜
case 26:%><%="("+rank.getTong()+"层)"%><%break;//探险榜
case 27:%><%="("+rank.getTong()+"次)"%><%break;//杀死千面郎君
case 28:%><%="("+rank.getTong()+"次)"%><%break;//成为千面郎君
default :%><%="LV:"+rank.getP_level()%><%break;//等级
}
%>
<br/>
<%}}%><br/><%} %>
<%String paimin = (String)request.getAttribute("paimin"); 
if(paimin==null||"".equals(paimin.trim())||"0".equals(paimin.trim())){
%>
您没有参加排名
<%}else{ %>
您的排名:第<%=paimin %>名
<%} %>
<%@ include file="/init/templete/game_foot.jsp"%>
