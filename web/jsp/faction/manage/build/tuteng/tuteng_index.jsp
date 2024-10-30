<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../../inc/faction_head.jsp"%>
<anchor>建设图腾<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fBuild.do?cmd=createIndex")%>" method="post">
<postfield name="page_no" value="1"/>
</go></anchor>
|
<anchor>图腾升级<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fBuild.do?cmd=upgradeIndex")%>" method="post">
<postfield name="page_no" value="1"/>
</go></anchor>
<br/>
您的荣誉点为${roleEntity.basicInfo.FContribute}点!<br/>
图腾列表:<br/>
<c:forEach items="${item_page.result}" var="item">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>"> 
<postfield name="cmd" value="ttDes" />
<postfield name="bId" value="${item.BId }" />
<postfield name="pre" value="ttIndex" />
</go>
${item.name }
</anchor>
${item.simpleDes }
<anchor>领取祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>">
<postfield name="cmd" value="useHint" />
<postfield name="bId" value="${item.BId }" />
</go>
</anchor>
<br/>
</c:forEach>
${item_page.pageFoot }
<%@ include file="../../../inc/return_build_manage.jsp"%>