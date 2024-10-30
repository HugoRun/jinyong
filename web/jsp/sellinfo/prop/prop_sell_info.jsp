<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.info.partinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page
	import="com.ben.dao.sellinfo.*,com.ben.vo.sellinfo.SellInfoVO,com.ben.*,com.ls.pub.constant.*,com.ls.ben.dao.goods.prop.PropDao"%>
<%@page import="com.ls.ben.cache.staticcache.prop.PropCache,com.ls.ben.vo.goods.prop.PropVO"%> 
<%
 	response.setContentType("text/vnd.wap.wml");
 %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>  
	<%
  		SellInfoVO vo = (SellInfoVO)request.getAttribute("vo");
  			PartInfoDAO dao = new PartInfoDAO(); 
  			SellInfoDAO sellInfoDAO = new SellInfoDAO();
  			PropVO propVO = PropCache.getPropById(vo.getSWuping());
  	%>
	<%=dao.getPartName(vo.getPPk()+"")%>以<%if(vo.getSWpSilverMoney() > 0){ %> <%=vo.getSWpSilverMoney() %>灵石<%}%>的价格与您交易
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>"> 
	<postfield name="cmd" value="n14" />
	<postfield name="prop_id" value="<%=vo.getSWuping() %>" />
	<postfield name="sPk" value="<%=vo.getSPk() %>" />
	</go>
	<%=propVO.getPropName() %>*<%=vo.getSWpNumber() %>
	</anchor>
	<br/>您
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>"> 
	<postfield name="cmd" value="n8" />
	<postfield name="sPk" value="<%=vo.getSPk() %>" />
	</go>
	是
	</anchor>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="sPk" value="<%=vo.getSPk() %>" />
	</go>
	否
	</anchor>
	接受交易
</p>
</card>
</wml>
