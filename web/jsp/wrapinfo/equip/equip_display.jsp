<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
${equip_display }
<anchor>使用
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
<postfield name="cmd" value="n5" />
<postfield name="pwPk" value="${equip.pwPk}" />
</go>
</anchor><br/>
<anchor>丢弃
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
<postfield name="cmd" value="n6" />
<postfield name="pwPk" value="${equip.pwPk}" />
</go>
</anchor><br/>
<anchor>展示
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equiprelela.do")%>"> 
<postfield name="cmd" value="n1" />
<postfield name="pwPk" value="${equip.pwPk}" />
<postfield name="return_type" value="wrap" />
</go>
</anchor><br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
<postfield name="cmd" value="n1" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
