<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.info.partinfo.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
	    PartInfoDAO dao = new PartInfoDAO();
		String goods_id = (String) request.getAttribute("goods_id");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String goodsName = (String) request.getAttribute("goodsName");
		String pByPk = (String) request.getAttribute("pByPk");
		String w_type = (String) request.getAttribute("w_type");
		String number = (String) request.getAttribute("number");
	%>
	你交易<%=goodsName%> ×<%=number %>给<%=dao.getPartName(pByPk)%> ！
	<br/>
	请输入要交易物品的金额<br/>
	<input name="pSilver"  type="text" format="*N" maxlength="8" /><%=GameConfig.getMoneyUnitName() %><br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellpropaction.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="goods_id" value="<%=goods_id %>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goodsName" value="<%=goodsName%>" />
	<postfield name="pByPk" value="<%=pByPk%>" />
	<postfield name="w_type" value="<%=w_type%>" /> 
	<postfield name="pSilver" value="$pSilver" />
	<postfield name="number" value="<%=number %>" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/xiaoshou.do")%>">
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="pByPk" value="<%=pByPk%>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
