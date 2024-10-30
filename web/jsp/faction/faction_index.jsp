<%@ page pageEncoding="UTF-8"%>
<%@ include file="inc/faction_head.jsp"%>
当您等级达到30级时，便可提交1枚氏族令和1000灵石创建1级氏族。<br/>
1级氏族可招收20名成员，设族长（前期为建立氏族之人，可转让）1名，长老2名，护法（精英成员）5名。<br/>
各种族只能加入相应种族所建立的氏族，即巫族人只能加入巫族氏族，妖族人只能加入妖族氏族。<br/>
<anchor>建立氏族<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=createIndex")%>" method="get"/></anchor><br/>
<anchor>申请加入氏族<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=applyFList&amp;page_no=1")%>" method="get"/></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>