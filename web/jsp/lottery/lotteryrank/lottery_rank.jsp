<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.*,java.util.*" %>
<%@page import="com.ls.ben.vo.info.partinfo.PartInfoVO,com.ls.ben.dao.info.partinfo.PartInfoDao" %>
<%@page import="com.lw.dao.lottery.PlayerLotteryInfoDao" %>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*" %>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
	<% 
	RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
	
	List list = (List) request.getAttribute("lotteryrank");
	String rank = request.getAttribute("rank").toString();
	String money = request.getAttribute("money").toString();%>
	<%=roleInfo.getBasicInfo().getName()%>,欢迎您进入竞猜排名！<br/>
	您目前排名<%=rank %>位,共获得银两<%=money%><br/>
	<%	for(int i = 0; i<list.size();i++){
		PlayerLotteryInfoDao pdao = new PlayerLotteryInfoDao();
		PartInfoDao dao = new PartInfoDao();
	    PartInfoVO vo = dao.getPartInfoByID(Integer.parseInt(list.get(i).toString()));%>
	    <%if(pdao.getPlayerAllBonus(Integer.parseInt(list.get(i).toString())) !=0) {%>
		第<%=i+1 %>名 <%=StringUtil.isoToGBK(vo.getPName()) %>,
		获得银两<%=pdao.getPlayerAllBonus(Integer.parseInt(list.get(i).toString())) %><br/>
		<%
		}
		}
	  	 %>
	<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do?cmd=n1")%>"></go>返回竞猜主页面</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
