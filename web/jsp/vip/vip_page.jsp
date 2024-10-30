<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${hint }<br/>
<anchor>返回
<go method="post" href="<%=GameConfig.getContextPath()%>/wrap.do">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="6" />
<postfield name="page_no" value="1" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
