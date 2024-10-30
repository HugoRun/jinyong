<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.map.SceneVO"%>
<%SceneVO scene = (SceneVO)request.getAttribute("scenevo"); %>
离线挂机说明：<br/>
1.	只能在冒险区域离线挂机<br/>
2.	挂机地点刷新的npc等级不能高于挂机角色等级<br/>
3.	一次挂机的时间不得大于6小时<br/>
4.	挂机所得物品直接放入角色物品栏，物品栏满后物品直接消失。<br/>
<%if(scene!=null){ %>
5.	你目前适合前往<%=scene.getSceneName() %>进行挂机。<br/>
<%} %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>">
<postfield name="cmd" value="n3" />
</go>
开始挂机
</anchor>
<br/>
<%if(scene!=null){ %>
前往
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/guaji.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="scene_id" value="<%=scene.getSceneID() %>" />
</go>
<%=scene.getSceneName() %> 
</anchor> <br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>