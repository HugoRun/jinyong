<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
将氏族升至${material.grade }级,${material.MDes },您确定要将氏族升级吗?<br/>
<anchor>升级<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=upgrade")%>" method="get"/></anchor><br/>
--------------------<br/>
${material.effectDes }<br/>
当氏族升至2级时，祠堂也将具有升级功能<br/>
当祠堂达到2级时，便可建立图腾，氏族成员则可领取不同的祝福<br/>
祠堂等级越高，可建立的图腾也将越多，图腾可晋升的等级也将越高<br/>
--------------------<br/>
<%@ include file="../../inc/return_build_manage.jsp"%>