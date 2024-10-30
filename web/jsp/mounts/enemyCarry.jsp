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
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
选择你要传送的仇敌地点:
	<%
		String mountID=(String)request.getAttribute("mountID");
	   	String mountState=(String)request.getAttribute("mountState");
		QueryPage qp = (QueryPage) request.getAttribute("queryPage");
		List<PKHiteVO> list = (List<PKHiteVO>) qp.getResult();
		if (list != null && list.size() != 0) {
			RoleCache roleCache = new RoleCache();
			for (int i = 0; i < list.size(); i++) {
				PKHiteVO pv = list.get(i);
				int ppk = pv.getEnemyPpk();
				int upk=pv.getEnemyUpk();
				RoleEntity roleEntity = roleCache.getByPpk(ppk);
				String view=roleCache.getByPpk(pv.getP_pk()).getStateInfo().getView();
				String state = roleEntity == null ? "不在线" : roleEntity.getBasicInfo().getSceneInfo().getSceneName();
	%>
	<br/>
	<%=StringUtil.isoToGB(pv.getEnemyName())%> 仇恨度(<%=pv.getHitePoint()%>),(<%=state%>)
	<%
	if(roleEntity.isOnline())
	{
		%>
		<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n8" />
	<postfield name="mountsID" value="<%=mountID%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="scenceID" value="<%=roleEntity.getBasicInfo().getSceneId()%>" />
	</go>
	前往
	</anchor>
		
		<%
	}
		}
		}

		if (qp.hasPreviousPage()) {
	%><br/>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do?cmd=n7")%>">
	<postfield name="index" value="<%=qp.getCurrentPageNo() - 1%>" />
	</go>
	上一页
	</anchor>
	<%
		}
		if (qp.hasNextPage()) {
	%><br/>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do?cmd=n7")%>">
	<postfield name="index" value="<%=qp.getCurrentPageNo() + 1%>" />
	</go>
	下一页
	</anchor>|
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mounts.do?cmd=n7")%>">
	<postfield name="index" value="<%=qp.getTotalPageCount()%>" />
	</go>
	到尾页
	</anchor>
	<%
		}
	%>
	<br />
		<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/mounts.do")%>">
	<postfield name="cmd" value="n15" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
