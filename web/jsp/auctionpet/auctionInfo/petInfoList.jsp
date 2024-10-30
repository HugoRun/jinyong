<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.auctionpet.*"%> 
<%@page import="com.ls.pub.util.*"%>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
拍卖助手:<br/>
<%
	List infoList = (List)request.getAttribute("infoList");
	String mores = (String)request.getAttribute("mores");
	
	if(infoList != null && infoList.size() != 0){
		for(int i=0;i < infoList.size(); i++){
			AuctionPetInfoVO infovo = (AuctionPetInfoVO)infoList.get(i);%>
			
			<%=StringUtil.isoToGB(infovo.getAuctionPetInfo()) %><br/>
	<%
		}
	}else {
		out.print("无");
	}
	%>
<br/><br/><br/>	
	<%if(mores != null && !mores.equals("more")){%>
	<anchor> 
		 	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetHelp.do")%>">
			<postfield name="cmd" value="n1" />
			<postfield name="moreOrNot" value="more" />
			</go>
			更多
		 </anchor><br/>
		 <%} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
