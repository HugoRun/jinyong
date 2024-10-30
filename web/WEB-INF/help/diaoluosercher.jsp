<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%String message = (String)request.getAttribute("message"); 
Object id = request.getAttribute("id");
%>
<%=message==null?"":message.trim()+"<br/>" %>
请选择物品类型后输入您要查询的物品名称，然后点击“查询”<br/>
<%Object type = request.getAttribute("type"); %>
<input type="text" name="name" maxlength="20" value="输入物品名称"/>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n7" />
<postfield name="type" value="<%=type %>" />
<postfield name="name" value="$(name)" />
<postfield name="id" value="<%=id %>" />
</go>查询</anchor>
<br/>
 <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/help.do")%>" method="post">
<postfield name="cmd" value="n2" />
<postfield name="id" value="<%=id %>" />
</go>返回</anchor><br/>
<anchor><go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get" ></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
