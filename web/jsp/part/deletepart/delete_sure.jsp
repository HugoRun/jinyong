<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
     <%
     String uPk = (String)request.getParameter("uPk");
     String pPk = (String)request.getParameter("pPk");
     request.getSession().setAttribute("pPk",pPk);
     String pName = (String)request.getParameter("pName");
     String pGrade = (String)request.getParameter("pGrade");
     //System.out.println("pGrade="+pGrade);
     if(Integer.valueOf(pGrade) < 40) {
     %>
     您确定要删除<%=pName %>吗?<br/>
	 <anchor>	
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do") %>">
	 <postfield name="cmd" value="n9" />
	 <postfield name="uPk" value="<%=uPk %>" />
	 <postfield name="pPk" value="<%=pPk %>" /> 
	 <postfield name="pGrade" value="<%=pGrade %>" /> 
	 </go>	
      确定
	 </anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")%>"></go>取消</anchor><br/>
	 <%} else { %>
	 四十级以上的人物不可被删除！
	 <br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")%>"></go>确定</anchor><br/>
	 <%} %>
	 <br/>
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3") %>">
	 <postfield name="uPk" value="<%=uPk %>" />
	 </go>
	返回首页
	</anchor>
</p>
</card>
</wml>
