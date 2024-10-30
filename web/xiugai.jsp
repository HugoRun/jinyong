<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
    <%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
    <card id="login" title="<bean:message key="gamename"/>">
        <p>
            <% String s1 = request.getParameter("s1");
                String s2 = request.getParameter("s2");
            %>
            id
            <input name="s1" value="0" type="text" format="*N" size="5"/>
            <br/>
            掉落
            <input name="s2" value="0" type="text" format="*N" size="5"/>
            <br/>
            <%
                if (s1 == null || s2 == null) {
            %>无<%
            } else {
            } %>
        </p>
    </card>
</wml>