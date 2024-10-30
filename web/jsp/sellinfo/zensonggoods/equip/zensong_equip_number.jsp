<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.web.service.player.*,com.ls.pub.constant.*" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.dao.info.partinfo.*"%> 
<%@page import="com.ls.web.service.goods.GoodsService"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		PartInfoDAO dao = new PartInfoDAO();
			String pByuPk = (String) request.getAttribute("pByuPk");
			String pByPk = (String) request.getAttribute("pByPk");
			String pwPk = (String) request.getAttribute("pwPk");
			String wName = (String) request.getAttribute("wName");
			String wProtect = (String) request.getAttribute("wProtect");
			String bangding = (String) request.getAttribute("bangding");
			GoodsService goodsService = new GoodsService();
			String hint = goodsService.isBinded(Integer.parseInt(pwPk), 2, ActionType.EXCHANGE);
			if(hint != null ){
			out.println(hint +"<br/>");
			}else{
	%>
	您确定要赠送给<%=dao.getPartName(pByPk)%><%=wName %>吗？
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="wName" value="<%=wName %>" />
	<postfield name="pwPk" value="<%=pwPk %>" />
	<postfield name="pByuPk" value="<%=pByuPk%>" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	</go>
	确定
	</anchor>
	<br/>
	<%} %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="pByuPk" value="<%=pByuPk%>" />
	<postfield name="pByPk" value="<%=pByPk%>" />
	<postfield name="w_type" value="3" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
