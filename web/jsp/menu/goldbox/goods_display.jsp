<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pub.ben.info.*,com.ls.ben.vo.info.partinfo.*,com.ls.ben.vo.info.attack.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%  
	String goods_display = (String) request.getAttribute("goods_display");
	String gold_box_pgpk = (String) request.getAttribute("gold_box_pgpk");
	String w_type = (String) request.getAttribute("w_type");
	String goods_id = (String) request.getAttribute("goods_id");
	 
    if( goods_display!=null )
	{
		out.println(goods_display);
	}
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do")%>" >
<postfield name="cmd" value="n7" />
<postfield name="w_type" value="<%=w_type%>" />
<postfield name="goods_id" value="<%=goods_id%>" />
<postfield name="gold_box_pgpk" value="<%=gold_box_pgpk%>" />
</go>
返回 
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
 