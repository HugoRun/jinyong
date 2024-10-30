<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
请输入您要材料的数量:<br/>
<input name="num" size="3" maxlength="3" format="*N" /><br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do")%>">
<postfield name="cmd" value="contribute" />
<postfield name="pg_pk" value="${pg_pk}" />
<postfield name="num" value="$(num)" />
</go>
</anchor>
<br/>
<anchor>全部贡献
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do")%>">
<postfield name="cmd" value="contributeAll" />
<postfield name="pg_pk" value="${pg_pk}" />
</go>
</anchor>
<br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/fStorage.do")%>">
<postfield name="cmd" value="wMList" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
