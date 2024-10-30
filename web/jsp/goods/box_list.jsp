<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.attack.DropGoodsVO,com.ls.ben.vo.info.attack.DropExpMoneyVO"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*,com.ls.pub.util.*"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>

<%
	String ss = (String)request.getAttribute("ss");
	//String goods_name = (String)request.getAttribute("goods_name");
	List<DropGoodsVO> dropgoods = (List)request.getAttribute("dropgoods");
	DropExpMoneyVO expmoneyvo = (DropExpMoneyVO)request.getAttribute("dropExpMoney");
	
	DropGoodsVO dropGoods = null;
%>

<card id="login" title="<s:message key = "gamename"/>">
<p> 
	<%	if(expmoneyvo != null ){
	
		if( expmoneyvo.getDropExp()!=0 )
		{
	 %>
			您获得了经验<%=expmoneyvo.getDropExp() %>！<br/>
	<%
		}
	 %>
	 <%	
		if( expmoneyvo.getDropMoney()!=0 )
		{
	 %>
			您获得了金钱<%=MoneyUtil.changeCopperToStr(expmoneyvo.getDropMoney()) %>！<br/>
	<%
		}
		}
	 %>
	<%if(ss!=null){ %>
	<%=ss %><br/>
	<%} %>
	
	<%
		if (dropgoods != null && dropgoods.size() != 0) {
			for (int i = 0; i < dropgoods.size(); i++) {
				dropGoods = (DropGoodsVO) dropgoods.get(i);
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="drop_pk" value="<%=dropGoods.getDPk()%>" />
	<postfield name="goods_id" value="<%=dropGoods.getGoodsId()%>" />
	<postfield name="goods_type" value="<%=dropGoods.getGoodsType()%>" />
	</go>		
	<%=StringUtil.isoToGB(dropGoods.getGoodsName())%>×<%=dropGoods.getDropNum()%>
	
	</anchor>
	<%
		if ((i + 1) != dropgoods.size()) {
					out.print(",");
				}
			}
			out.print("<br/>");
		}
		
		else
		{
			out.println( "无物品<br/>" );
		}
	%> 
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do?cmd=n3")%>"></go>继续</anchor><br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
