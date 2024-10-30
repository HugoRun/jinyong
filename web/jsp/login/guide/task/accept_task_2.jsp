<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
${role_name }：拜见帝江祖巫！帝江：${role_name }，你也即将晋升为祖巫真身，就不必多礼了。${role_name }：谢祖巫。帝江祖巫，不知眼下战况如何？帝江：哼，没想到帝俊和太一那厮还暗藏了一手…这周天星辰大阵也是非凡，竟然能阻挡住父神真身……<br/>
<br/>
<anchor>确定<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=end")%>" method="get"/></anchor>
</p>
</card>
</wml>