<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*,com.pub.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	
	<%--
	QueryPage rest_page = (QueryPage)request.getAttribute("rest_page");
	List rest_list = (List)rest_page.getResult();

	PlayerPropGroupVO propGroup = null;--%>
     <%
	List rest_list = (List)request.getAttribute("rest_list");
	PlayerPropGroupVO propGroup = null;
	//分页显示初始化
		int no = 0;//当前页数
		int pageSize = 7;//每页最多显示条数
		int allSize = 1;//总记录数
		if (rest_list != null && rest_list.size() > 0) {
			allSize = rest_list.size();
		} 
		
		PageBean pageb = new PageBean();
        pageb.init(request.getParameter("pageNo"),pageSize,allSize,response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1&amp;w_type="+Wrap.REST));
		no = pageb.getIndex(); 
	%>
<%@ include file="auction_menu.jsp"%><br/>
	 <%
	 	if( rest_list!=null && rest_list.size()!= 0)
	 	{
	 		for(int i = (no - 1)*pageSize; i < no * pageSize&&i < rest_list.size() ; i++)
	 		{
	 			propGroup = (PlayerPropGroupVO)rest_list.get(i);
	 			
	 			%>
	 				<anchor>
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do")%>">
					<postfield name="cmd" value="n3" />
					<postfield name="w_type" value="<%=Wrap.REST%>" />
					<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
					<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
					<postfield name="prop_type" value="<%=propGroup.getPropType()%>" />
					</go>
	 				<%= propGroup.getPropName()%>×<%=propGroup.getPropNum() %>
	 				</anchor>|<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/auction/auctionGoods/input_prop_price.jsp")%>">
					<postfield name="cmd" value="n2" />
					<postfield name="w_type" value="<%=Wrap.REST%>" />
					<postfield name="prop_id" value="<%=propGroup.getPropId()%>" />
					<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
					<postfield name="prop_type" value="<%=propGroup.getPropType()%>" />
					</go>
					拍卖
					</anchor> 
	 				<br/>
	 				
	 			<%
	 		}
	 		out.println(pageb.getFooter()+pageb.getIndex()+"/"+pageb.getPageNum()+"<br/>"); 
	 		
	 	}
	 	else
	 	{
	 		out.print("无");
	 	}
	  %> 
	   
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
