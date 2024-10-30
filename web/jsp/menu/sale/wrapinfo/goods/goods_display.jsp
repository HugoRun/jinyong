<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${goods_display }<br/>
<anchor>卖出
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do?cmd=n2") %>">
<postfield name="w_type" value="${w_type }" />
<postfield name="page_no" value="${page_no }" />
<postfield name="goods_id" value="${pg_pk}" />
<postfield name="reconfirm" value="0" />
</go>
</anchor>
<br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/sale.do?cmd=n1") %>">
<postfield name="w_type" value="${w_type }" />
<postfield name="page_no" value="${page_no }" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
