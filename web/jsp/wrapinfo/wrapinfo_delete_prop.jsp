<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.web.service.goods.GoodsService"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String pg_pk = (String) request.getAttribute("pg_pk");
			String goods_id = (String) request.getAttribute("goods_id");
			String goods_type = (String) request.getAttribute("goods_type");
			String isReconfirm = (String) request.getAttribute("isReconfirm");
			String hint = (String) request.getAttribute("hint");
			GoodsService goodsService = new GoodsService();
	%>
<%if (hint != null) {%><%=hint%><br/><%}%>
请输入您要丢弃<%=goodsService.getGoodsName(Integer.parseInt(goods_id), 4)%>的数量:<br/>
<input name="goodsNumber" maxlength="6" format="*N" /><br/>
	<anchor>确定
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do")%>">
	<postfield name="cmd" value="n12" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	<postfield name="goodsNumber" value="$goodsNumber" />
	<postfield name="isReconfirm" value="<%=isReconfirm%>" />
	</go>
	</anchor>
	<br/>
	<anchor>全部丢弃
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do")%>">
	<postfield name="cmd" value="n12" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	<postfield name="isReconfirm" value="<%=isReconfirm%>" />
	</go>
	</anchor>
	<br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do")%>"><postfield name="cmd" value="n1" /></go></anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
