<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page
	import="com.ls.pub.util.StringUtil,com.pm.vo.mail.MailInfoVO,com.ls.pub.bean.QueryPage,java.text.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	QueryPage mail_info = (QueryPage) request.getAttribute("mail_info");
	List list = (List) mail_info.getResult();
	SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
	邮件列表|
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/sendMail.do?cmd=n3")%>"
		method="get"></go>
	写邮件
	</anchor>
	<br />
	<%
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				MailInfoVO mailInfoVO = (MailInfoVO) list.get(i);
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mail.do?cmd=n2")%>">
	<postfield name="mailId" value="<%=mailInfoVO.getMailId()%>" />
	<postfield name="page_no" value="<%=mail_info.getCurrentPageNo()%>" />
	</go>
	<%
		if (mailInfoVO.getTitle() == null
						|| mailInfoVO.getTitle() == ""
						|| mailInfoVO.getTitle().equals("")) {
	%>
	玩家邮件
	<%
		} else {
	%>
	<%=StringUtil.isoToGBK(mailInfoVO.getTitle())%>
	<%
		}
	%>
	</anchor>
	<%
		if (mailInfoVO.getUnread() == 1) {
	%>
	(未阅读)<%=sf.format(sf2.parse(mailInfoVO
										.getCreateTime()))%><br />
	<%
		} else {
	%>
	(已阅读)<%=sf.format(sf2.parse(mailInfoVO
										.getCreateTime()))%><br />
	<%
		}
	%>
	<%
		}
	%>
	<br />

	<%
		if (mail_info.hasNextPage()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mail.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=mail_info.getCurrentPageNo() + 1%>" />
	</go>
	下一页
	</anchor>
	<%
		}
			if (mail_info.hasPreviousPage()) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mail.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=mail_info.getCurrentPageNo() - 1%>" />
	</go>
	上一页
	</anchor>
	<%
		}

			//long totgle = mail_info.getTotalPageCount();
			//System.out.println("totgle="+totgle);
			if (mail_info.getCurrentPageNo() == 1
					&& mail_info.getTotalPageCount() > 2) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mail.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=mail_info.getTotalPageCount()%>" />
	</go>
	到末页
	</anchor>
	<%
		}
			if (mail_info.getCurrentPageNo() == mail_info
					.getTotalPageCount()
					&& mail_info.getTotalPageCount() > 2) {
	%>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()
									+ "/mail.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=1%>" />
	</go>
	到首页
	</anchor>
	<%
		}
	%>
	<br />
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mail.do?cmd=n4")%>"
		method="get"></go>
	全部删除
	</anchor>
	<%
		} else {
	%>
	您没有邮件！
	<%
		}
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
