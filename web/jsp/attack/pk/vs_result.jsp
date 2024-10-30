<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="com.ben.pk.active.PKVs"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
	<%
		QueryPage qp = (QueryPage) request.getAttribute("queryPage");
		List<PKVs> list = (List<PKVs>) qp.getResult();
		if (list != null && list.size() != 0) 
		{
			for (int i = 0; i < list.size(); i++) {
				PKVs vs = list.get(i);
	%><%=vs.getRoleAName() + "     对阵     "+vs.getRoleBName()%><br />
	<br />
	<%
		}
		}
		if (qp.hasPreviousPage()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/pkactive.do?cmd=n2")%>">
	<postfield name="index" value="<%=qp.getCurrentPageNo() - 1%>" />
	<postfield name="view" value="vs" />
	</go>
	上一页
	</anchor>
	<%
		}
		if (qp.hasNextPage()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/pkactive.do?cmd=n2")%>">
	<postfield name="index" value="<%=qp.getCurrentPageNo() + 1%>" />
	<postfield name="view" value="vs" />
	</go>
	/下一页
	</anchor>
	<%
		}
	%>
	<br />
	<anchor>
	<go method="post" href="/pkactive.do?cmd=n5">
	<postfield name="cmd" value="n5" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
