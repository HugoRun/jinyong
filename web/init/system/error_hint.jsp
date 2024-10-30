<%@page pageEncoding="UTF-8"%>
<%
    String hint = (String)request.getAttribute("hint");
    if( hint!=null )
    {
%><%=hint%><br/><%
    }
%>