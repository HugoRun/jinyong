<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.pm.dao.secondpass.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String uPk = (String)request.getSession().getAttribute("uPk");
		SecondPassDao seconddao = new SecondPassDao();
			
		boolean flag = seconddao.hasAlreadySecondPass(uPk);
		if (flag) {
		 %>
				您已经设置过二级密码了!
	<%} else { %>
				请输入您的二级密码(6位0～9数值组合):
				<br/>
				<input name="secondPass"  type="text"  maxlength="6" />
				<br/>
				<anchor>
				<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/secondPass.do")%>">
				<postfield name="cmd" value="n1" />
				<postfield name="secondPass" value="$secondPass" />
				</go>
				传送
				</anchor>
	<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
