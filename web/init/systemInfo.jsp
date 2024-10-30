<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page
	import="java.util.*,com.pm.vo.sysInfo.SystemInfoVO,com.ls.ben.dao.info.partinfo.*"%>
<%@page import="java.text.*"%>

<% List<SystemInfoVO> sysInfolist = (List<SystemInfoVO>)request.getAttribute("sysInfo");
	if(sysInfolist != null && sysInfolist.size() != 0)
	 {
		for(SystemInfoVO infovo:sysInfolist){

			if(infovo != null && infovo.getSystemInfo() != null && !infovo.getSystemInfo().equals("") && !infovo.getSystemInfo().equals("null"))
			{
			if(infovo.getInfoType() != 4) {
				if(infovo.getInfoType() == 10){
					}%>
[系]系统消息:<%=StringUtil.isoToGBK(infovo.getSystemInfo()) %><br />
<%} else {
				// 小喇叭只有五秒钟的时间
				Date dt = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long sysInfoTime = sf.parse(infovo.getAppearTime()).getTime();
				if (dt.getTime() - sysInfoTime < 5000 ) {
					PartInfoDao infodao = new PartInfoDao();
					%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
<postfield name="cmd" value="n13" />
<postfield name="pPks" value="<%=infovo.getPPk() %>" />
<postfield name="backtype" value="mainmap" />
</go>
<%=infodao.getNameByPpk(infovo.getPPk())%>
</anchor>
大声呐喊:<%=infovo.getSystemInfo()%><br />
<%
					}
				}
			 %>
<%}} }%>