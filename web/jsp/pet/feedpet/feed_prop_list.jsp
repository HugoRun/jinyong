<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
	宠物食物:
	<br />
	<%
		String w_type = (String) request.getAttribute("w_type");
		String pet_pk = (String) request.getAttribute("pet_pk");
		String petGrade = (String) request.getAttribute("petGrade");
		String petFatigue = (String) request.getAttribute("petFatigue");
		String petIsBring = (String) request.getAttribute("petIsBring");
		if (w_type == null) {
			w_type = "1";
		}
		QueryPage prop_page = (QueryPage) request.getAttribute("prop_page");
		List prop_list = (List) prop_page.getResult();
		PlayerPropGroupVO propGroup = null;
		if (prop_list != null && prop_list.size() != 0) {
			for (int i = 0; i < prop_list.size(); i++) {
				propGroup = (PlayerPropGroupVO) prop_list.get(i);
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/wrap.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
	<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
	<postfield name="goods_type" value="<%=propGroup.getPropType()%>" />
	</go>
	<%=propGroup.getPropName()%>x<%=propGroup.getPropNum()%>
	</anchor>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/wrap.do")%>">
	<postfield name="cmd" value="n11" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
	<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
	<postfield name="goods_type" value="<%=propGroup.getPropType()%>" />
	<postfield name="pet_pk" value="<%=pet_pk%>" />
	</go>
	使用
	</anchor>
	<br />
	<%
		}

		} else {
			out.print("无");
		}
	%>

	<%
		if (prop_page.hasNextPage()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/wrap.do?cmd=n10&amp;w_type=" + w_type)%>">
	<postfield name="page_no" value="<%=prop_page.getCurrentPageNo() + 1%>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if (prop_page.hasPreviousPage()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/wrap.do?cmd=n10&amp;w_type=" + w_type)%>">
	<postfield name="page_no" value="<%=prop_page.getCurrentPageNo() - 1%>" />
	</go>
	上一页
	</anchor>
	<%
		}
		if (prop_page.getCurrentPageNo() == 1
				&& prop_page.getTotalPageCount() > 2) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/wrap.do?cmd=n10&amp;w_type=" + w_type)%>">
	<postfield name="page_no" value="<%=prop_page.getTotalPageCount()%>" />
	</go>
	到末页
	</anchor>
	<%
		}
		if (prop_page.getCurrentPageNo() == prop_page.getTotalPageCount()
				&& prop_page.getTotalPageCount() > 2) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/wrap.do?cmd=n10&amp;w_type=" + w_type)%>">
	<postfield name="page_no" value="<%=1%>" />
	</go>
	到首页
	</anchor>
	<%
		}
	%>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/petinfoaction.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="petPk" value="<%=pet_pk%>" />
	<postfield name="petGrade" value="<%=petGrade%>" />
	<postfield name="petFatigue" value="<%=petFatigue%>" />
	<postfield name="petIsBring" value="<%=petIsBring%>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
