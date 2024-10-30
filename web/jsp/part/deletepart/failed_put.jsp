<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		
		String hint = (String)request.getAttribute("hint");
		String uPk = (String)request.getAttribute("uPk");
		String pPk = (String)request.getAttribute("pPk");
		String pGrade = request.getParameter("pGrade");
		if (pGrade == null || pGrade.equals("")) {
			pGrade = (String) request.getAttribute("pGrade");
		}
		if(hint != null && !hint.equals("")) {	
	 %>
	<%=hint %><br/>
	
	删除游戏角色需要验证帐号的二级密码，请输入该帐号的二级密码:<br/>
	 <input name="second_pass"  type="text" maxlength="6" />
	
	 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/role.do") %>">
	 <postfield name="cmd" value="n8" />	
	 <postfield name="second_pass" value="$second_pass" />
	 <postfield name="uPk" value="<%=uPk %>" />
	 <postfield name="pPk" value="<%=pPk %>" />
	 <postfield name="pGrade" value="<%=pGrade %>" />
	 </go>
      确定
	 </anchor>
<%} else { %>
	空指针！
<%} %>
<br/>
 <anchor>
	 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3") %>">
	 <postfield name="uPk" value="<%=uPk %>" />
	 </go>
	返回首页
	</anchor>
<br/>
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
