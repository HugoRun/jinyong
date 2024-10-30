<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.web.service.player.*,com.ls.pub.constant.*" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.dao.info.partinfo.*"%> 
<%@page import="com.ls.web.service.goods.GoodsService"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		PartInfoDAO dao = new PartInfoDAO();
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
	您确定要给予
	<%=dao.getPartName(pByPk)%>
	<%=wName %>吗？
	<br/>
	<%
		String cc = request.getParameter("cc");
		if (cc != null) {
	%>
	您输入的金额不对
	<br/>
	<%
		}
	%>
	请输入要交易物品的金额
	<br/>
	<input name="pSilver"  type="text" format="*N" size="8" /><%=GameConfig.getMoneyUnitName() %><br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="wName" value="<%=wName %>" />
	<postfield name="pwPk" value="<%=pwPk %>" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	<postfield name="pSilver" value="$pSilver" />
	</go>
	确定
	</anchor>
	<br/>
	<%} %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/xiaoshou.do")%>">
	<postfield name="pByPk" value="<%=pByPk%>" />
	<postfield name="w_type" value="3" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
