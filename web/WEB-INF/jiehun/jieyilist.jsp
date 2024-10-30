<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%FriendVO fo = (FriendVO)request.getAttribute("friendVo");
if(fo==null){
 %>
 您还没有结婚呢<br/>
<%}else{ %>
你选择与<%=fo.getFdName() %>解除婚姻关系，将导致彼此：<br/>
1.	爱情甜蜜指数清零<br/>
2.	好友亲密度清零<br/>
3.	没有额外的组队效果加成<br/>
4.	组队战斗中不可分享对方打怪经验<br/>
5.	组队战斗取消增加彼此5%气血上限<br/>
6.	不可使用心印符<br/>
7.	不可装备结婚戒指<br/>

<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="pByPk" value="<%=fo.getFdPk() %>" />
	<postfield name="pByName" value="<%=fo.getFdName()%>" />
	<postfield name="cmd" value="n12" />
	</go>
	协议离婚
	</anchor> <br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do") %>"> 
	<postfield name="pByPk" value="<%=fo.getFdPk() %>" />
	<postfield name="pByName" value="<%=fo.getFdName()%>" />
	<postfield name="cmd" value="n17" />
	</go>
	强制离婚
	</anchor> <br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
