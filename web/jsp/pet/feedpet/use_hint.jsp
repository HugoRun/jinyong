<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page
	import="com.ls.ben.vo.info.effect.*,com.ls.ben.dao.info.partinfo.PlayerPropGroupDao,com.ls.ben.vo.info.partinfo.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<%
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		String w_type = (String) request.getAttribute("w_type");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String goods_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");
		String pet_pk = (String) request.getAttribute("pet_pk");
		String pagetype = (String) request.getAttribute("pagetype");
		PropUseEffect propUseEffect = (PropUseEffect) request
				.getAttribute("propUseEffect");
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer
				.parseInt(pg_pk));
		String page_no = (String)request.getSession().getAttribute("page_no");
		if (propUseEffect != null) {
			if (propUseEffect.isEffected()) {
	%>
	<%=propUseEffect.getEffectDisplay()%><br/>

	<%
		} else {
	%>
	<%=propUseEffect.getNoUseDisplay()%><br/>

	<%
		}
		} else {
			out.print("空指针");
		}
		if (propUseEffect.getIsPetFatigue() == 1 && propGroup != null) {//表示没有加满
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="pagetype" value="<%=pagetype %>" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="pg_pk" value="<%=pg_pk%>" />
	<postfield name="goods_id" value="<%=goods_id%>" />
	<postfield name="goods_type" value="<%=goods_type%>" />
	<postfield name="pet_pk" value="<%=pet_pk%>" />
	</go>
	继续喂食
	</anchor>
	<br/>
	<%
		}//else if (propUseEffect.getIsPetFatigue() == 1 && propGroup == null) {//表示没有加满
		if(Integer.parseInt(pagetype) == 1){
		%>
		<anchor>
	    <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	    <postfield name="cmd" value="n1" />
	    <postfield name="w_type" value="<%=w_type%>" />
	    <postfield name="pg_pk" value="<%=pg_pk%>" />
	    <postfield name="goods_id" value="<%=goods_id%>" />
	    <postfield name="goods_type" value="<%=goods_type%>" />
	    <postfield name="pet_pk" value="<%=pet_pk%>" />
	    <postfield name="page_no" value="<%=page_no%>" />
	    </go>
	    返回
	    </anchor>
		<%}else{%>
		<anchor>
	    <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	    <postfield name="cmd" value="n10" />
	    <postfield name="w_type" value="<%=w_type%>" />
	    <postfield name="pg_pk" value="<%=pg_pk%>" />
	    <postfield name="goods_id" value="<%=goods_id%>" />
	    <postfield name="goods_type" value="<%=goods_type%>" />
	    <postfield name="pet_pk" value="<%=pet_pk%>" />
	    <postfield name="page_no" value="<%=page_no%>" />
	    </go>
	    返回
	    </anchor>
		<%}%> 
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
