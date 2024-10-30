<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
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
<%@page import="com.pm.vo.chuansong.SuiBianChuanVO"%>
<%@page import="com.pub.PageBean"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="act" title="<bean:message key="gamename"/>">
<p>
	请选择您要传送的场景地点:
	<br />
	<%
		List<SuiBianChuanVO> list = (List<SuiBianChuanVO>) request
				.getAttribute("list");
		String mountID = (String) request.getAttribute("mountID");
		String mountState = (String) request.getAttribute("mountState");
		SuiBianChuanVO suiBianChuanVO = null;
		//分页显示初始化 
		int no = 0;//当前页数
		int pageSize = 7;//每页最多显示条数
		int allSize = 1;//总记录数
		if (list != null && list.size() > 0) {
			allSize = list.size();
		}
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null || pageNo.equals("") || pageNo.equals("null")) {
			pageNo = (String) request.getAttribute("pageNo");
		}
		PageBean pageb = new PageBean();
		pageb.init(pageNo, pageSize, allSize, response.encodeURL(GameConfig
				.getContextPath()
				+ "/mounts.do?cmd=n1&amp;mountID="
				+ mountID
				+ "&amp;mountState=" + mountState));
		no = pageb.getIndex();

		if (list != null && list.size() != 0) {
			for (int i = (no - 1) * pageSize; i < no * pageSize
					&& i < list.size(); i++) {
				suiBianChuanVO = list.get(i);
	%>
	<%=suiBianChuanVO.getSceneName()%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mounts.do")%>">
	<postfield name="cmd" value="n8" />
	<postfield name="mountsID" value="<%=mountID%>" />
	<postfield name="mountState" value="<%=mountState%>" />
	<postfield name="scenceID" value="<%=suiBianChuanVO.getSceneId() %>" />
	</go>
	前往
	</anchor><br/>
	<%
		}
		} else {
		}

		if (pageb.getFooter().equals("")) {
			out
					.print(pageb.getIndex() + "/" + pageb.getPageNum()
							+ "<br/>");
		} else {
			out.print(pageb.getFooter() + "<br/>" + pageb.getIndex() + "/"
					+ pageb.getPageNum() + "<br/>");
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



	<br />
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
