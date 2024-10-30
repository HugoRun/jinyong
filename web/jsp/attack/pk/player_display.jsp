<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
    <%
        String playerPic = (String) request.getAttribute("playerPicB");
        Fighter fighter = (Fighter) request.getAttribute("npc");
        if (fighter == null) {
            //System.out.println("空指针。。。");
        }
    %>
    返回首页
    <br />
    ------------------------
    <br />
    报时:11:42
    <br />
</p>
</card>
</wml>
