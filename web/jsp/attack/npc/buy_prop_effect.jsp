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


    String pk = (String)request.getAttribute("pk");
    String ss = (String)request.getAttribute("reqchair");

%>
    是否使用【鸿蒙造化果】?
    <br />

    <% %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n15" />
    <postfield name="choice" value="1" />
    </go>
    是
    </anchor>|<anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n15" />
    <postfield name="choice" value="2" />
    </go>
    否
    </anchor>
    <br />
    <% %>
    <%@ include file="/init/init_dead_prop.jsp"%>

</p>
</card>
</wml>
