<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*,com.pub.*"%>
<%@page import="com.ls.pub.constant.Wrap"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.EconomyService"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%--
	QueryPage rest_page = (QueryPage)request.getAttribute("rest_page");
	List rest_list = (List)rest_page.getResult();

	PlayerPropGroupVO propGroup = null;--%>
     <%
     
     	RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		WrapinfoDAO dao1 = new WrapinfoDAO();
	    PartInfoVO vo1 = (PartInfoVO)dao1.geTsilver(pPk);
	    String table_type=request.getParameter("table_type");
	    EconomyService economyServcie = new EconomyService();
	    String backType=(String)request.getAttribute("backType");
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
		List cure_list = (List)request.getAttribute("cure_list");
		PlayerPropGroupVO propGroup = null;
		//分页显示初始化
		int no = 0;//当前页数
		int pageSize = 7;//每页最多显示条数
		int allSize = 1;//总记录数
		if (cure_list != null && cure_list.size() > 0) {
			allSize = cure_list.size();
		} 
		
		PageBean pageb = new PageBean();
        pageb.init(request.getParameter("pageNo"),pageSize,allSize,response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1"));
		no = pageb.getIndex(); 
	%>
		【拍卖场】<br/>
	财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)<br/>
<%@ include file="/jsp/auction/auctionMainPage/auction_general_menu.jsp"%><br/>
	 <%
	 	if( cure_list!=null && cure_list.size()!= 0)
	 	{
	 		for(int i = (no - 1)*pageSize; i < no * pageSize&&i < cure_list.size() ; i++)
	 		{
	 			propGroup = (PlayerPropGroupVO)cure_list.get(i);
	 			
	 			%>
	 				<anchor>
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n3")%>">
					<postfield name="w_type" value="<%=Wrap.CURE%>" />
					<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
					<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
					<postfield name="prop_type" value="<%=propGroup.getPropType()%>" />
					</go>
	 				<%= propGroup.getPropName()%>×<%=propGroup.getPropNum() %>
	 				</anchor>|<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/auction/auctionGoods/input_prop_price.jsp")%>">
					<postfield name="backType" value="<%=backType%>" />
					<postfield name="pageNum" value="<%=no%>" />
					<postfield name="w_type" value="<%=Wrap.CURE%>" />
					<postfield name="prop_id" value="<%=propGroup.getPropId()%>" />
					<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
					<postfield name="prop_type" value="<%=propGroup.getPropType()%>" />
					</go>
					拍卖
					</anchor>
	 				<br/>
	 				
	 			<%
	 		}
	 		out.print(pageb.getFooter()+pageb.getIndex()+"/"+pageb.getPageNum()+"<br/>"); 
	 	}
	 	else
	 	{
	 		%>
	 		无<br/>
	 		<%
	 	}
	  %> 
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionMainPage/auction_main_shi.jsp")%>" method="get"></go>
	返回
	</anchor>    
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
