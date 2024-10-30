
<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,java.text.SimpleDateFormat" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%
	//废弃的文件
	
		String jumpterm2 = "0";
		if (request.getParameter("jumpterm") != null) {
			jumpterm2 = request.getParameter("jumpterm");
		} else if ((String) request.getAttribute("jumpterm") != null) {
			jumpterm2 = (String) request.getAttribute("jumpterm");
		}
		String pPk2 = null;
		if (request.getParameter("pPk") != null) {
			pPk2 = request.getParameter("pPk");
		} else if ((String) request.getAttribute("pPk") != null) {
			pPk2 = (String) request.getAttribute("pPk");
		}
		String mapid2 = null;
		if (request.getParameter("mapid") != null) {
			mapid2 = request.getParameter("mapid");
		} else if ((String) request.getAttribute("mapid") != null) {
			mapid2 = (String) request.getAttribute("mapid");
		}
		String uPk2 = null;
		if (request.getParameter("uPk") != null) {
			uPk2 = request.getParameter("uPk");
		} else if ((String) request.getAttribute("id") != null) {
			uPk2 = (String) request.getAttribute("id");
		}
	%>
<anchor>
<go method="post"
	href="<%=GameConfig.getContextPath()%>/jsp/attack/wrapinfo/attack_list_shu.jsp">
<postfield name="mapid" value="<%=mapid2%>" />
<postfield name="uPk" value="<%=uPk2%>" />
<postfield name="pPk" value="<%=pPk2%>" />
<postfield name="jumpterm" value="<%=jumpterm2%>" />
</go>
书卷|
</anchor>
<anchor>
<go method="post"
	href="<%=GameConfig.getContextPath()%>/jsp/attack/wrapinfo/attack_list.jsp">
<postfield name="mapid" value="<%=mapid2%>" />
<postfield name="uPk" value="<%=uPk2%>" />
<postfield name="pPk" value="<%=pPk2%>" />
<postfield name="jumpterm" value="<%=jumpterm2%>" />
</go>
药品|
</anchor>
<anchor>
<go method="post"
	href="<%=GameConfig.getContextPath()%>/jsp/attack/wrapinfo/attack_list_zb.jsp">
<postfield name="mapid" value="<%=mapid2%>" />
<postfield name="uPk" value="<%=uPk2%>" />
<postfield name="pPk" value="<%=pPk2%>" />
<postfield name="jumpterm" value="<%=jumpterm2%>" />
<postfield name="fenlei" value="3" />
</go>
装备|
</anchor>
<anchor>
<go method="post"
	href="<%=GameConfig.getContextPath()%>/jsp/attack/wrapinfo/attack_list_rw.jsp">
<postfield name="mapid" value="<%=mapid2%>" />
<postfield name="uPk" value="<%=uPk2%>" />
<postfield name="pPk" value="<%=pPk2%>" />
<postfield name="jumpterm" value="<%=jumpterm2%>" />
</go>
任务|
</anchor>
<anchor>
<go method="post"
	href="<%=GameConfig.getContextPath()%>/jsp/attack/wrapinfo/attack_list_qt.jsp">
<postfield name="mapid" value="<%=mapid2%>" />
<postfield name="uPk" value="<%=uPk2%>" />
<postfield name="pPk" value="<%=pPk2%>" />
<postfield name="jumpterm" value="<%=jumpterm2%>" />
</go>
其他
</anchor>