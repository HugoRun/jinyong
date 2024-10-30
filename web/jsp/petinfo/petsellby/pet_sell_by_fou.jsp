<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.info.partinfo.*,com.ben.vo.info.partinfo.PartInfoVO,com.ben.dao.petinfo.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>  
	<%  
	    String uPk = (String)session.getAttribute("uPk");
		String pPk = request.getParameter("pPk"); 
		String psPk = request.getParameter("psPk");
		PartInfoDAO daos = new PartInfoDAO();
		PartInfoVO vo = (PartInfoVO) daos.getPartView(pPk); 
		PetInfoDAO dd = new PetInfoDAO();  
		dd.getPetSellDelete(psPk);  
		PartInfoVO partInfoVO =(PartInfoVO) daos.getPartTypeListPpk(pPk); 
	  //  response.sendRedirect("../../../partannal.do?mapid="+vo.getPMap()+"&uPk="+uPk+"&pPk="+pPk+"&pName="+partInfoVO.getPName()+"&chair="+request.getParameter("chair"));
		request.getRequestDispatcher("../../../partannal.do?mapid="+vo.getPMap()+"&uPk="+uPk+"&pPk="+pPk+"&pName="+partInfoVO.getPName()+"&chair="+session.getAttribute("checkStr")).forward(request, response); 
		
	%> 
</p>
</card>
</wml>
