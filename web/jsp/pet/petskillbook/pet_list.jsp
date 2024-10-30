<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.petinfo.*,com.ben.vo.petinfo.PetInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		  String pPk =  request.getSession().getAttribute("pPk")+"";
		
		String w_type = (String) request.getAttribute("w_type");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String goods_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");
		String hint = (String) request.getAttribute("hint");
		String page_no = (String)request.getSession().getAttribute("page_no");
	%>
	【宠物栏】
	<%
		if (hint != null) {
	%>
	<br/><%=hint%>
	<%
		}
	%>
	<%
		PetInfoDAO dao = new PetInfoDAO();
		List list = dao.getPetInfoList(pPk);
		for (int i = 0; i < list.size(); i++) {
			PetInfoVO vo = (PetInfoVO) list.get(i);
	%>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n15" />
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	<postfield name="pet_pk" value="<%=vo.getPetPk() %>" />
	</go>
	<%=vo.getPetNickname()%>
	</anchor>  
	<%
		}
	%>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="page_no" value="<%=page_no %>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
