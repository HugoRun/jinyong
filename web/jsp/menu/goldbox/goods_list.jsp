<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pub.ben.info.*,java.util.*,com.ls.ben.vo.info.partinfo.*,com.ls.ben.vo.info.attack.*,com.ls.ben.vo.goods.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	String gold_box_pgpk = (String)request.getAttribute("gold_box_pgpk");
	  List<DropGoodsVO> dropgoods = (List<DropGoodsVO>)request.getAttribute("dropgoods");
	  PropVO keyvo = (PropVO)request.getAttribute("keyVO");
	  PropVO propVO = (PropVO)request.getAttribute("propVO");
	  PlayerPropGroupVO boxvo = (PlayerPropGroupVO)request.getAttribute("boxvo");
	  DropGoodsVO dropGoodsVO = null;
	 String w_type = (String) request.getAttribute("w_type");
	 String hintString = (String) request.getAttribute("hintString");
    if( hintString!=null )
	{
		out.println(hintString);
	}
	if(dropgoods!=null)
	{
		if(propVO!=null )
		{
	out.println("您使用了一个"+propVO.getPropName()+"，发现宝箱内的宝物时刻变幻！(打开可获得以下物品中的一件)<br/>");
		}
		for (int i=0;i<dropgoods.size();i++) 
		{
	dropGoodsVO = dropgoods.get(i);
%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n3")%>" >
	<postfield name="goods_id" value="<%=dropGoodsVO.getGoodsId()%>" />
	<postfield name="goods_type" value="<%=dropGoodsVO.getGoodsType()%>" />
	<postfield name="w_type" value="<%=w_type%>" />
	<postfield name="gold_box_pgpk" value="<%=gold_box_pgpk%>" />
	</go>
	<%=dropGoodsVO.getGoodsName() %>
	</anchor>
<% 
		}
		out.println("<br/>点击可查看物品介绍，装备可查看属性!<br/>");
	} 
	else 
	{
		out.println("<br/>对不起，您没有"+boxvo.getPropName()+"!<br/>");
	}
%> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n4")%>" ></go>打开宝箱</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goldBox.do?cmd=n5")%>" ></go>丢弃宝箱</anchor><br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
 