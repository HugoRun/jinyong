<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.info.partinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.dao.sellinfo.*,com.ben.vo.sellinfo.SellInfoVO"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>  
	<%   
	    SellInfoVO vo = (SellInfoVO)request.getAttribute("vo"); 
		PartInfoDAO dao = new PartInfoDAO(); 
	%>
	玩家<%=dao.getPartName(vo.getPPk()+"")%> 给予您<%=vo.getSWpSilverMoney() %>两<br/> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>"> 
	<postfield name="cmd" value="n2" />
	<postfield name="sPk" value="<%=vo.getSPk() %>" />  
	</go>
	确定
	</anchor>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>"> 
	<postfield name="cmd" value="n3" />
	<postfield name="sPk" value="<%=vo.getSPk() %>" />  
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
