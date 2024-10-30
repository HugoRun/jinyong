<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
欢迎您来到洪荒世界,请选择您的种族:<br/>
<img src="/image/role/race/yao.png" alt="yaozu"/><br/>
先天神邸，天地间第一批生灵，神通无敌，执掌天庭……<br/>
<anchor>妖族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do?cmd=n2")%>">
<postfield name="race" value="1" />
</go>
</anchor><br/>

<img src="/image/role/race/wu.png" alt="wuzu"/><br/>
盘古精血所化，不尊圣人，只拜盘古，肉身无敌，战天战地，统御大地……<br/>
<anchor>巫族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do?cmd=n2")%>">
<postfield name="race" value="2" />
</go>
</anchor><br/>	
<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")%>" method="get"></go>
返回上一级
</anchor>
<br/>
<%@ include file="/init/return_url/return_zhuanqu.jsp"%>
</p>
</card>
</wml>
