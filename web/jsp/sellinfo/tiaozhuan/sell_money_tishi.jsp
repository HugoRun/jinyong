<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.info.partinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		String uPk = (String) session.getAttribute("uPk");
		 
		String pPk = request.getParameter("pPk"); 
		String silverMoney = request.getParameter("silverMoney");
		String copperMoney = request.getParameter("copperMoney");
		//修改钱
		PartInfoDAO daos = new PartInfoDAO();
		PartInfoVO vo = (PartInfoVO) daos.getPartView(pPk);

		PartInfoDAO daosq = new PartInfoDAO();
		PartInfoVO voqq = (PartInfoVO) daosq.getPartView(pPk);
		PartInfoVO partInfoVO = (PartInfoVO) daos.getPartTypeListPpk(pPk); 
		request.getRequestDispatcher("../../../partannal.do?mapid="
				+ vo.getPMap()
				+ "&uPk="
				+ uPk
				+ "&pPk="
				+ pPk
				+ "&pName="  
				+ partInfoVO.getPName()+"&chair="+session.getAttribute("checkStr")).forward(request,response);  
	%>
	您获得<%=silverMoney%>两<%=copperMoney%>文
	<br/> 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
