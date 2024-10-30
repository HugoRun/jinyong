<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
    <%
        String uPk = null;
        if (request.getParameter("uPk") != null) {
            uPk = request.getParameter("uPk");
        } else {
            uPk = (String) request.getAttribute("uPk");
        }
    %>
    金庸创世录冲级大赛6月26日18点顺利结束，现将获奖名单公布如下，奖品6月30日发放，获奖者可前往大散关
    <br />
    岳雷处领取。
    <br />
    第1名：血杀狂刀
    <br />
    奖励60级、连击三必杀三强力三狂暴三、满成长率【白虎宠物蛋】×1，【<%=GameConfig.getYuanbaoQuanName()%>】×1000
    <br />
    第2名：逍遥的小丑 奖励30级、连击二必杀二强力二狂暴二、满成长率【白虎宠物蛋】×1，【元宝卷】×800
    <br />
    第3名：剑帝vs剑魔
    <br />
    奖励30级、连击二必杀二强力二狂暴二、满成长率【白虎宠物蛋】×1，【<%=GameConfig.getYuanbaoQuanName() %>】×500
    <br />
    第4、5名：紫藤依依，萧鹤风
    <br />
    奖励1级、连击一必杀一强力一狂暴一、满成长率【白虎宠物蛋】×1，【<%=GameConfig.getYuanbaoQuanName() %>】×500
    <br />
    第6～10名：冰痕、颠簸怕苦、王者至尊、无情剑圣、卡通虎
    <br />
    奖励1级、连击一必杀一强力一狂暴一、满成长率【白虎宠物蛋】×1，【<%=GameConfig.getYuanbaoQuanName() %>】×300
    <br />
    第11～30名：龙行天下、刘德华、断箭、影子、沉默D游侠、还没玩过、为情痴狂、风油精、随风远行、易水
    <br />
    寒、倩芊、天下无双、七夜、骷髅兵0邪0、逍遥道人、坎斯尼、踏雪无痕、拾草、我是来搞笑的、天使的礼物
    <br />
    奖励【元宝卷】×300
    <br />
    <anchor>
    <go method="get"
        href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/login.do?cmd=n3")%>" ></go>
    返回
    </anchor>
</p>
</card>
</wml>