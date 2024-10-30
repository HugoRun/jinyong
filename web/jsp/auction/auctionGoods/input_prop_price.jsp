<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ben.dao.wrapinfo.WrapinfoDAO"%>
<%@page import="com.ben.vo.info.partinfo.PartInfoVO"%>
<%@page import="com.ls.web.service.player.EconomyService"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<% 
	    RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		WrapinfoDAO dao1 = new WrapinfoDAO();
	    PartInfoVO vo1 = (PartInfoVO)dao1.geTsilver(pPk);
	    EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
		String payType=(String)request.getSession().getAttribute("pay_type");
		String moneyName=payType.equals("1")?"灵石":"仙晶";
		String prop_type = request.getParameter("prop_type"); 
		String w_type = request.getParameter("w_type");
		String pageNo= request.getParameter("pageNum");
		if(w_type!=null&&pageNo!=null)
		{
			request.getSession().setAttribute("w_type",w_type);
			request.getSession().setAttribute("pageNo",pageNo);
		}
		String pg_pk = request.getParameter("pg_pk");
		if(pg_pk == null || pg_pk.equals("")){
			pg_pk = (String)request.getAttribute("pg_pk");
			w_type=(String)request.getAttribute("w_type");
		}
		String resultWml =(String)request.getAttribute("resultWml");
		if(resultWml != null){
%> 
	<%=resultWml %><br/>
	<%} %>
【拍卖场】<br/>
财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)<br/>	
 请输入您要拍卖的价格:<br/>
 一口价：<input name="prop_silver"  type="text" size="5"  maxlength="5" format="5N" /><%=moneyName%><br/>
 竞拍价：<input name="prop_copper"  type="text" size="5"  maxlength="5" format="5N" /><%=moneyName%><br/>
 拍数量: <input name="prop_num"  type="text" size="5"  maxlength="5" format="5N" />数量
 <br/>
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do") %>">
	<postfield name="cmd" value="n2" />
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	<postfield name="prop_type" value="<%=prop_type %>" />
	 <postfield name="prop_silver" value="$prop_silver" />
	 <postfield name="prop_copper" value="$prop_copper" />
	 <postfield name="prop_num" value="$prop_num" />
	 </go>
		确定
	 </anchor><br/>
 <anchor>
 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1") %>">
 <postfield name="w_type" value="<%=w_type %>" />
  <postfield name="table_type" value="<%=request.getSession().getAttribute("w_type") %>" />
 <postfield name="pageNo" value="<%=request.getSession().getAttribute("pageNo")%>" />
 </go>
 返回
 </anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
