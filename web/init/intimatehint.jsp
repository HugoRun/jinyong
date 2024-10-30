<%@page contentType="text/vnd.wap.wml"
    import="com.ben.vo.intimatehint.IntimateHintVO" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%
    IntimateHintVO intimateHint = (IntimateHintVO) request
            .getAttribute("intimateHint");
    if (intimateHint != null) {
%><%=intimateHint.getHHint()%>:<%=intimateHint.getHContent()%>
<%
    }
%>