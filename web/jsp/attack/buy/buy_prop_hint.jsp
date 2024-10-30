<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.MoneyUtil,com.ls.ben.vo.mall.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<%
    CommodityVO CommodityVO = (CommodityVO) request
            .getAttribute("commodity");
    String hint = (String) request.getAttribute("hint");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
    <%=hint%>
    <br />
    <%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>