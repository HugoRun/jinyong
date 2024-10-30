<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pub.*,com.ben.vo.communion.CommunionVO,com.ben.dao.info.partinfo.*,com.web.service.communion.CommunionService"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="login" title="<bean:message key="gamename"/>">
<p>
种族聊天频道<br/>
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/communioninfoaction.do?cmd=n2")%>" ></go>
	刷新
	</anchor>
	<br />
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk() + "";

		String type = "2";
		if (request.getParameter("Type") != null) {
			type = request.getParameter("Type");
		}

		CommunionService communionService = new CommunionService();
		List list = communionService.getCampChatInfoList(roleInfo
				.getBasicInfo().getPRace(), type);

		if (list != null && list.size() != 0) {
			//分页显示初始化
			int no = 0;//当前页数
			int pageSize = 5;//每页最多显示条数
			int allSize = 1;//总记录数
			if (list != null && list.size() > 0) {
				allSize = list.size();

			}
			PageBean pageb = new PageBean();
			pageb.init(request.getParameter("pageNo"), pageSize, allSize,
					response.encodeURL(GameConfig.getContextPath()
							+ "/communioninfoaction.do?cmd=n2"));
			no = pageb.getIndex();
			for (int i = (no - 1) * pageSize; i < no * pageSize
					&& i < list.size(); i++) {
				CommunionVO vo = (CommunionVO) list.get(i);
				//if (roleInfo.getBasicInfo().getCamp() == vo.getCZhen()) {
	%>
	[种]
	<%
		if (Integer.parseInt(pPk) != vo.getPPk()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig
										.getContextPath()
										+ "/swapaction.do")%>">
	<postfield name="cmd" value="n14" />
	<postfield name="pPks" value="<%=vo.getPPk()%>" />
	</go>
	<%=vo.getPName()%>:
	</anchor>
	<%
		} else {
	%><%=vo.getPName()%>:
	<%
		}
	%><%=vo.getCTitle()%><br />
	<%
		//}
			}
			out.println(pageb.getFooter());
		}
	%>
	<%@ include file="/init/caidan.jsp"%>
	<br />
	<input name="puTitle" type="text" maxlength="20" />
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/campjl.do")%>">
	<postfield name="type" value="2" />
	<postfield name="puTitle" value="$puTitle" />
	</go>
	确定
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
