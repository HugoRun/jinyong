<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../../../inc/faction_head.jsp"%>
领取青狼祝福需${fBuild.gameBuild.contribute }荣誉点，您确定要领取吗？<br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fBuild.do")%>" method="post">
<postfield name="cmd" value="use" />
<postfield name="bId" value="${fBuild.BId }" />
</go>
</anchor><br/>
--------------------<br/>
提示:荣誉点可通过斩杀敌对氏族成员，斩杀他种族人员，贡献氏族财产，积极参与氏族战、攻城战等方式获得！<br/>
<%@ include file="../../../../inc/return_tuteng_manage.jsp"%>