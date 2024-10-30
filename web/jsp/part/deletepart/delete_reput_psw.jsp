<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
     <%
     String uPk = (String)request.getParameter("uPk");
     String pPk = (String)request.getParameter("pPk");
     String pName = (String)request.getParameter("pName");
     String pGrade = (String)request.getParameter("pGrade");
     //System.out.println("pGrade="+pGrade);
     if(Integer.valueOf(pGrade) < 40) {
     %>
     
	删除游戏角色需要验证帐号的二级密码，请输入该帐号的二级密码:
	 <br/>
	<input name="second_pass"  type="text" size="6"  maxlength="6" format="6N" />	
	 <anchor>	
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do") %>">
	 <postfield name="cmd" value="n8" />
	 <postfield name="uPk" value="<%=uPk %>" />
	 <postfield name="pPk" value="<%=pPk %>" /> 
	 <postfield name="pGrade" value="<%=pGrade %>" /> 
	  <postfield name="second_pass" value="$second_pass" />
	 </go>	
      确定
	 </anchor>
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
