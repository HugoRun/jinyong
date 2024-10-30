<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.mall.CommodityVO,com.ls.pub.util.MoneyUtil"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	CommodityVO commodity = (CommodityVO)request.getAttribute("commodity");
	String prop_wml = (String)request.getAttribute("prop_wml");
	String type = (String)request.getAttribute("type");
	String sell_type = (String)request.getAttribute("sell_type");
	String c_id = (String)request.getAttribute("c_id");//商品id
	String page_no = (String)request.getAttribute("page_no");//返回地址
	String sell_num = (String)request.getAttribute("sell_num");//购买数量
	String user_discount_str = (String)request.getAttribute("user_discount");
	int user_discount = 100;
	if(user_discount_str!=null)
	{
		user_discount = Integer.parseInt(user_discount_str.trim());
	}
	
	if( sell_num==null )
	{
		sell_num = "1";//默认值为1
	}
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="mall" title="<s:message key = "gamename"/>">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
<%=prop_wml %>
--------------<br/>
售价:<%=commodity.getCurPrice(user_discount)%><%=sell_type%>
<%
	if( user_discount<100 || commodity.getDiscount()!=-1 )
	{
%>
(原价:<%=commodity.getOriginalPrice()%><%=sell_type%>)
<%
	} 
 %>
<br/>
<input type="text" name="sell_num" value="<%=sell_num %>" size="5" maxlength="3"/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n2")%>">
<postfield name="c_id" value="<%=c_id%>"/>
<postfield name="sell_num" value="$sell_num"/>
<postfield name="type" value="<%=type%>"/>
<postfield name="page_no" value="<%=page_no%>"/>
</go>购买
</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>