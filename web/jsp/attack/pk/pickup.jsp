<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.ben.vo.info.attack.pk.DropgoodsPkVO"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="act" title="<s:message key = "gamename"/>">
<p>
    <%
        String hint = (String) request.getAttribute("hint");
        String a_p_pk = (String) request.getAttribute("a_p_pk");
        String b_p_pk = (String) request.getAttribute("b_p_pk");

        List list = (List) request.getAttribute("list");
        if (hint != null) {
    %>
    <%=hint%><br />
    <%
        }
    %>
    <%
        if (list != null && list.size() != 0) {
    %>
    掉落的物品有:
    <br />
    <%
        for (int i = 0; i < list.size(); i++) {
                DropgoodsPkVO dropgoodsPkVO = (DropgoodsPkVO) list.get(i);
    %>
    <anchor>
    <go method="post"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                                    + "/pk.do")%>">
    <postfield name="cmd" value="n8" />
    <postfield name="dp_pk" value="<%=dropgoodsPkVO.getDpPk()%>" />
    <postfield name="p_pk" value="<%=dropgoodsPkVO.getAPPk()%>" />
    <postfield name="goods_id" value="<%=dropgoodsPkVO.getGoodsId()%>" />
    <postfield name="goods_type" value="<%=dropgoodsPkVO.getGoodsType()%>" />
    <postfield name="goods_num" value="<%=dropgoodsPkVO.getDropNum()%>" />
    <postfield name="goods_name"
        value="<%=StringUtil.isoToGB(dropgoodsPkVO.getGoodsName())%>" />
    <postfield name="a_p_pk" value="<%=a_p_pk%>" />
    <postfield name="b_p_pk" value="<%=b_p_pk%>" />
    </go>
    <%=StringUtil.isoToGB(dropgoodsPkVO.getGoodsName())%>x<%=dropgoodsPkVO.getDropNum()%>
    </anchor>
    <%
        if ((i + 1) != list.size()) {
                    out.print(",");
                }
            }
    %><br />
    <%
        }
    %>
    <anchor>
    <go method="post"
        href="<%=response
                            .encodeURL(GameConfig.getContextPath() + "/pk.do")%>">
    <postfield name="cmd" value="n9" />
    <postfield name="type" value="1" />
    <postfield name="pPk" value="<%=(String) session.getAttribute("pPk")%>" />
    <postfield name="a_p_pk" value="<%=a_p_pk%>" />
    <postfield name="b_p_pk" value="<%=b_p_pk%>" />
    </go>
    继续
    </anchor>
    <br />
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
