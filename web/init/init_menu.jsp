<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.pub.constant.MenuType"%>
<%@page import="com.ben.lost.CompassService"%>
<%
    List menus = (List) request.getAttribute("menus");
    OperateMenuVO menu = null;
    if (menus != null && menus.size() != 0) {
%>
<%
    for (int i = 0; i < menus.size(); i++) {
            String im = "";
            menu = (OperateMenuVO) menus.get(i);
            if (menu.getMenuType() == MenuType.SHEARE) {
                String pPk = (String) session.getAttribute("pPk");
                //共享菜单
                menu = CompassService.getCurrentMenu(menu, Integer.parseInt(pPk.trim()));
			}
			if (menu != null) {

				if (menu.getMenuName().indexOf("!") != -1) {
					menu.setMenuName(menu.getMenuName().replaceAll("!",""));
					im = "<img src=\"" + GameConfig.getGameUrl()+ "/image/task/1.png"+ "\"  alt=\"!\"/>";
				}
				if (menu.getMenuName().indexOf("?") != -1) {
					menu.setMenuName(menu.getMenuName().replaceAll("\\?", ""));
					im = "<img src=\"" + GameConfig.getGameUrl()+ "/image/task/2.png"+ "\"  alt=\"?\"/>";
				}
%>
<%=im%>
<anchor><%=menu.getMenuName()%>
<go method="post" href="<%=response.encodeURL(GameConfig .getContextPath() + "/menu.do?cmd=n1")%>">
<postfield name="menu_id" value="<%=menu.getId()%>" />
<postfield name="menu_type" value="<%=menu.getMenuType()%>" />
<postfield name="task_id" value="<%=menu.getMenuTasksId()%>" />
</go>
</anchor><br/>
<%
	}
		}
	}
%>