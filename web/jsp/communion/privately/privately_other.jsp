<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pub.*,com.ben.vo.communion.CommunionVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.model.user.*,com.ls.web.service.player.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	私聊聊天频道
	<br />
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		List list = (List) request.getAttribute("list");
		if (list != null && list.size() != 0) {
			//分页显示初始化
			int no = 0;//当前页数
			int pageSize = 8;//每页最多显示条数
			int allSize = 1;//总记录数
			if (list != null && list.size() > 0) {
				allSize = list.size();

			}
			PageBean pageb = new PageBean();
			pageb.init(request.getParameter("pageNo"), pageSize, allSize,
					response.encodeURL(GameConfig.getContextPath()
							+ "/communioninfoaction.do?cmd=n8"));
			no = pageb.getIndex();
			String user_list = "";

			for (int i = (no - 1) * pageSize; i < no * pageSize
					&& i < list.size(); i++) {
				CommunionVO vo = (CommunionVO) list.get(i);
				if (vo.getPPkBy() != Integer.parseInt(pPk)) {

					if (user_list.indexOf(vo.getPPkBy() + "") == -1) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig
											.getContextPath()
											+ "/communioninfoaction.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="pByPk" value="<%=vo.getPPkBy()%>" />
	<postfield name="pByName" value="<%=vo.getPNameBy()%>" />
	</go>
	<%=vo.getPNameBy()%>
	</anchor>
	<br />
	<%
		}
					user_list += vo.getPPkBy() + ",";
				}
			}
			out.println(pageb.getFooter());
		}
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
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
