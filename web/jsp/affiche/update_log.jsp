<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
    <%
        String uPk = null;
        if (request.getParameter("uPk") != null) {
            uPk = request.getParameter("uPk");
        } else {
            uPk = (String) request.getAttribute("uPk");
        }
    %>
    <s:message key="gamename" />
    3月3日更新:
    <br />
    1.修改杀死副本boss掉落物品异常的bug
    <br />
    2.修改了杀死副本boss但任务没有完成的bug
    <br />
    3.修改了杀死玩家掉落装备的属性重新生成的bug
    <br />
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/jsp/part/part_type.jsp")%>">
    <postfield name="uPk" value="<%=uPk%>" />
    </go>
    返回
    </anchor>
</p>
</card>
</wml>
