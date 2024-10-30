<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="com.ben.pk.active.PKVs"%>
<%@page import="com.ls.ben.vo.pkhite.PKHiteVO"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.ben.vo.mounts.UserMountsVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
	<%
		String message=(String)request.getAttribute("message");
		String mountID=(String)request.getAttribute("mountsID");
		String nextLevelMountsID=(String)request.getAttribute("nextLevelMountsID");
	 %>
	 <%=message%>
	 <anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="mountsID" value="<%=mountID %>" />
	<postfield name="nextLevelMountsID" value="<%=nextLevelMountsID%>" />
	<postfield name="confirm" value="true" />
	</go><br/>
	确定
	</anchor><br/>
	 <anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n16" />
	</go>
	返回
	</anchor>
	<br />
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
