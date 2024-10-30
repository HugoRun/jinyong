<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pub.*,com.ben.vo.communion.CommunionVO,com.ben.dao.info.partinfo.*,com.web.service.communion.CommunionService"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	私聊聊天频道
	<br/>
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/communioninfoaction.do?cmd=n5")%>" ></go>
	刷新
	</anchor>
	<br />
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();

		String pByPk = (String) request.getAttribute("pByPk");
		String pByName = (String) request.getAttribute("pByName");
		String type = "5";
		if (request.getParameter("Type") != null) {
			type = request.getParameter("Type");
		}
		CommunionService communionService = new CommunionService();
		List list = communionService.getsrivatelyChatInfoList(pPk, type);
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
							+ "/communioninfoaction.do?cmd=n5"));
			no = pageb.getIndex();
			for (int i = (no - 1) * pageSize; i < no * pageSize
					&& i < list.size(); i++) {
				CommunionVO vo = (CommunionVO) list.get(i);
				//if(vo.getPPk() == pPk || vo.getPPkBy() == pPk){
	%>
	[私]
	<%
		if (pPk != vo.getPPk()) {
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
	%>
	<%=vo.getPName()%>:
	<%
		}
	%>
	<%=vo.getCTitle()%><br />
	<%
		//}
			}
			out.println(pageb.getFooter());
		}
	%>
	<%@ include file="/init/caidan.jsp"%>
	<br />
	<%
		String hint = (String) request.getAttribute("hint");
		if (hint != null) {
	%>
	<%=hint%><br />
	<%
		}
	%>
	<%
		if (pByPk != null && pByName != null) {
	%>
	您目前正在与<%=pByName%>聊天
	<br />
	消息内容:
	<br />
	<input name="upTitle" type="text" maxlength="20" />
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/privatelyjl.do")%>">
	<postfield name="upTitle" value="$upTitle" />
	<postfield name="type" value="5" />
	<postfield name="pNameBy" value="<%=pByName%>" />
	</go>
	确定
	</anchor>
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/communioninfoaction.do")%>">
	<postfield name="cmd" value="n8" />
	</go>
	与其他人聊天
	</anchor>
	<%
		} else {
	%>
	请输入玩家姓名:
	<br />
	<input name="pNameBy" type="text" maxlength="20" />
	<br />
	消息内容:
	<br />
	<input name="upTitle" type="text" maxlength="20" />
	<br />
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/privatelyjl.do")%>">
	<postfield name="upTitle" value="$upTitle" />
	<postfield name="type" value="5" />
	<postfield name="pNameBy" value="$pNameBy" />
	</go>
	确定
	</anchor>
	<%
		}
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
