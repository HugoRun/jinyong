<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%
	PlayerVO pvo = (PlayerVO) request.getAttribute("pinfo");
	int yuanbao = 0;
	int jifen = 0;
	if (pvo != null) {
		yuanbao = pvo.getGpywb();
		jifen = pvo.getGpyjf();
	}
	RoleService roleService = new RoleService();
	RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	//System.out.println(roleInfo.getBasicInfo().getCamp());
%>
<%=roleInfo.getBasicInfo().getName()%>您好!欢迎光临游戏商城!
<br />
您尚有【<%=GameConfig.getYuanbaoName() %>】×<%=yuanbao%>,商场积分<%=jifen%>!
<br />
<anchor>
<go method="get"
	href="<%=GameConfig.getContextPath()%>/emporiumaction.do?cmd=n3" ></go>
百宝囊
</anchor>
<anchor>
<go method="get"
	href="<%=GameConfig.getContextPath()%>/auctionyb.do?cmd=n1" ></go>
元宝交易
</anchor>
<br />