<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.display }
--------------------<br/>
<anchor>升级氏族<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=upgradeHint")%>" method="get"/></anchor>
|
<anchor>祠堂升级<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=upgradeCTHint")%>" method="get"/></anchor><br/>
<anchor>氏族财富
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do?cmd=fMList")%>" method="post">
<postfield name="page_no" value="1"/>
</go>
</anchor>
|
<anchor>图腾管理<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fBuild.do?cmd=ttIndex")%>" method="post">
<postfield name="page_no" value="1"/>
</go></anchor><br/>
祠堂可记录氏族声望，可存放氏族升级材料，当氏族声望与财富达到一定数量后，便可通过提交氏族令和灵石对氏族进行升级<br/>
当氏族等级达到2级时，祠堂也将具有升级功能<br/>
自祠堂等级达到2级时，氏族各成员便可于祠堂内领取各类祝福，随着祠堂的等级不断提升，所能领取的祝福种类也将越多，所能领取祝福的效果也将越高<br/>
<%@ include file="../../inc/return_faction.jsp"%>