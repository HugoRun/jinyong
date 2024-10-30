<%@page contentType="text/vnd.wap.wml"
    import="java.util.*,com.ls.model.user.RoleEntity,com.ls.web.service.player.RoleService,com.ben.vo.communion.CommunionVOPage,com.web.service.communion.*"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.web.service.friend.BlacklistService"%>
<%
    RoleService roleService = new RoleService();
    RoleEntity roleInfos = roleService.getRoleInfoBySession(request.getSession());
    RoleService roleServiceComm = new RoleService();
    RoleEntity roleInfoComm = roleServiceComm
            .getRoleInfoBySession(request.getSession());
    CommunionService communionService = new CommunionService();
    int size = 0;
    List CommunionVOPagelist = communionService
            .CommunionList(roleInfoComm);
    if (CommunionVOPagelist.size() > 5) {
        size = 5;
    } else {
        size = CommunionVOPagelist.size();
    }
    if (CommunionVOPagelist != null & size != 0) {
    	for (int t = 0; t < size; t++) {
			CommunionVOPage communionVOPage = (CommunionVOPage) CommunionVOPagelist.get(t);
			BlacklistService bls=new BlacklistService();
			System.out.println(roleInfos.getPPk());
			System.out.println(communionVOPage.getPPk());
			int temp= bls.isBlacklist(roleInfos.getPPk(),communionVOPage.getPPk());
			if(temp==1||temp==2)
			{
				continue;
			}
			if (communionVOPage.getPName() != null
					&& communionVOPage.getCTitle() != null) {
				if (roleInfoComm.getBasicInfo().getPPk() != communionVOPage
						.getPPk()) {
%><anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig
											.getContextPath()
											+ "/swapaction.do?cmd=n13")%>">
<postfield name="pPks" value="<%=communionVOPage.getPPk()%>" />
<postfield name="backtype" value="3" />
</go><%=communionVOPage.getPName()%></anchor>
<%
	} else {
%><%=communionVOPage.getPName()%>
<%
	}
//String comm = new String("".getBytes("utf-8"), "utf-8");
%><%=communionVOPage.getCTitle()%><br />
<%
	}
		}
	}
%>