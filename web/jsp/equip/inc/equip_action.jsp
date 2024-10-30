<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.*"%>
<%@ page import="com.ls.model.equip.EquipProduct" %>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
<postfield name="cmd" value="n4" />
<postfield name="pwPk" value="${pwPk }" />
</go>
使用
</anchor>