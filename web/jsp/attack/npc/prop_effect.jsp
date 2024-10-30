<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.attack.*"%>
<%@page import="com.ls.ben.vo.info.attack.DropGoodsVO"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
    <%
        String hint = (String) request.getAttribute("hint");
        //PartInfoVO player = (PartInfoVO)request.getAttribute("player");

        if (hint != null && !hint.equals("")) {
    %>

    <%=hint%><br />
    <%
        } else {
    %>
    空指针！
    <br />
    <%
        }
    %>
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/pubbuckaction.do")%>"></go>
    继续
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
