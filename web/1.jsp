<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.goods.GoodsService" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
    <card id="login" title="">
        <p>
            <%
                String action = request.getParameter("action");
                String role_name = request.getParameter("roleName");
                String item_name = request.getParameter("itemName");
                String item_num = request.getParameter("itemNum");

                GoodsService goodsService = new GoodsService();

                String hint = "";

                if (action != null) {
                    if (action.equals("1")) {
                        hint = goodsService.putPropToWrap(role_name, item_name, item_num);
                    } else if (action.equals("2")) {
                        hint = goodsService.putEquipToWrap(role_name, item_name, item_num, request.getParameter("quality"));
                    }
                } else {
                    hint = "action参数错误";
                }

                if (hint != null) {
            %><%=hint %><br/><%
            }
        %>
            角色名称:
            <input name="roleName" type="text"/>
            <br/>
            物品名称:
            <input name="itemName" type="text"/>
            <br/>
            发放数量：
            <input name="itemNum" type="text"/>
            <br/>
            -------------------------
            <br/>
            <anchor>发道具
                <go method="post" href="<%=GameConfig.getContextPath()%>/1.jsp">
                    <postfield name="roleName" value="$(roleName)"/>
                    <postfield name="itemName" value="$(itemName)"/>
                    <postfield name="itemNum" value="$(itemNum)"/>
                    <postfield name="action" value="1"/>
                </go>
            </anchor>
            <br/>
            -------------------------
            <br/>
            选择装备品质：
            <select name="quality" value="0">
                <option value="0">白</option>
                <option value="1">蓝</option>
                <option value="2">绿</option>
                <option value="3">紫</option>
                <option value="4">橙</option>
            </select>
            <br/>
            <anchor>发装备
                <go method="post" href="<%=GameConfig.getContextPath()%>/1.jsp">
                    <postfield name="roleName" value="$(roleName)"/>
                    <postfield name="itemName" value="$(itemName)"/>
                    <postfield name="itemNum" value="$(itemNum)"/>
                    <postfield name="quality" value="$(quality)"/>
                    <postfield name="action" value="2"/>
                </go>
            </anchor>
            <table columns="1">
                <tr>
                    <td>Cell 1</td>
                </tr>
            </table>
        </p>
    </card>
</wml>