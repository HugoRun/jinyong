<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
    <%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
    <card id="login" title="<bean:message key="gamename"/>">
        <p>
            <%
                String hint = (String) request.getAttribute("hint");
                if (hint != null) {
            %><%=hint %><br/><%
            }
        %>
            账号:
            <input name="uName" type="text" maxlength="11"/>
            <br/>
            密码:<input name="uPaw" type="password" maxlength="11"/>(5-11位,0-9,a-z组合)
            <br/>
            <anchor>
                <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do")%>">
                    <postfield name="cmd" value="n1"/>
                    <postfield name="name" value="$uName"/>
                    <postfield name="paw" value="$uPaw"/>
                </go>
                确定
            </anchor>
            <br/>
            <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n7")%>" method="get"></go>快速注册
            </anchor>
            <br/>
            <anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/index.jsp")%>" method="get"></go>返回上级
            </anchor>
            <br/>
            如果您第一次登陆，您输入的账号和密码就将是您的注册信息，请牢记。
            <br/>
            <%
                String display = (String) request.getAttribute("display");
            %>
                <%=display %>
            <br/>
            <%@ include file="/init/return_url/return_zhuanqu.jsp" %>
        </p>
    </card>
</wml>
	