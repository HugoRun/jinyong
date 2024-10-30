<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.pub.ben.info.*,com.ben.vo.partwrap.PartEquipVO"
    pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %> 
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n7" />
    </go>
    状态
    </anchor>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n8" />
    </go>
    属性
    </anchor>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n9" />
    </go>
    装备
    </anchor>
    <br/>
    <%
          String pPk = (String) request.getSession().getAttribute("pPk");

        PartEquipVO partEquipVO1 = (PartEquipVO)request.getAttribute("partEquipVO1");
        PartEquipVO partEquipVO2 = (PartEquipVO)request.getAttribute("partEquipVO2");
        PartEquipVO partEquipVO3 = (PartEquipVO)request.getAttribute("partEquipVO3");
        PartEquipVO partEquipVO4 = (PartEquipVO)request.getAttribute("partEquipVO4");
        PartEquipVO partEquipVO5 = (PartEquipVO)request.getAttribute("partEquipVO5");
        PartEquipVO partEquipVO6 = (PartEquipVO)request.getAttribute("partEquipVO6");
        PartEquipVO partEquipVO7 = (PartEquipVO)request.getAttribute("partEquipVO7");
        PartEquipVO partEquipVO8 = (PartEquipVO)request.getAttribute("partEquipVO8");
    %>
    武器:
    <%if(partEquipVO1 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO1.getPwPk()%>" />
    </go>
    <%=partEquipVO1.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    头部:
    <%if(partEquipVO2 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO2.getPwPk()%>" />
    </go>
    <%=partEquipVO2.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    身体:
    <%if(partEquipVO3 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO3.getPwPk()%>" />
    </go>
    <%=partEquipVO3.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    腿部:
    <%if(partEquipVO4 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO4.getPwPk()%>" />
    </go>
    <%=partEquipVO4.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    脚部:
    <%if(partEquipVO5 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO5.getPwPk()%>" />
    </go>
    <%=partEquipVO5.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    戒指:
    <%if(partEquipVO6 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO6.getPwPk()%>" />
    </go>
    <%=partEquipVO6.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    手镯:
    <%if(partEquipVO7 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO7.getPwPk()%>" />
    </go>
    <%=partEquipVO7.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    项链:
    <%if(partEquipVO8 != null){ %>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pwPk" value="<%=partEquipVO8.getPwPk()%>" />
    </go>
    <%=partEquipVO8.getWName() %>
    </anchor>
    <%}else { %>无<%} %>
    <br/>
    套装属性（2件套）:
    <br/>
    套装属性（4件套）:
    <br/>
    套装属性（7件套）:
    <br/>
    五行加成:攻击13
    <br/>
    <anchor>
    <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
    <postfield name="cmd" value="n4" />
    <postfield name="pPk" value="<%=pPk %>" />
    </go>
    返回
    </anchor>
    <br/>
    <%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
