<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		String uPk = (String) request.getAttribute("uPk");
		if (uPk == null) {
			uPk = request.getParameter("uPk");
		}
		String login_params = (String) session.getAttribute("login_params");

		login_params = login_params.replaceAll("&", "&amp;");
	%>
	注意:6月9日更新后，玩家以前保存的游戏书签失效，请大家从专区登录后重新保存书签，对大家造成的不便深表歉意。<br/>
	登录后将此页面保存为书签，下次点击书签就可直接登录游戏了！
	<br />
	<img src="<%=GameConfig.getGameUrl()%>/image/logo.png"  alt=""/>
	<br />
	天下风云出我辈
	<br />
	一入江湖岁月催
	<br />
	皇图霸业谈笑中
	<br />
	不胜人生一场醉
	<br />
	请选择分区:
	<br />
	<anchor>
	<go method="get" href="<%=GameConfig.getContextPath()%>/login.do?cmd=n3" ></go>
	飞狐区（全新开放）
	</anchor>
	<br />
	<br />
	<anchor>
	<go href="http://189hi.cn/plaf/wml/gacs.jw?act=gogame&amp;gameid=10"
		method="post">
	</go>
	返回专区
	</anchor>
	<br />
	<anchor>
	<go href="http://189hi.cn/plaf/wml/gacs.jw?act=index" method="post">
	</go>
	返回189HI.CN
	</anchor>
</p>
</card>
</wml>
