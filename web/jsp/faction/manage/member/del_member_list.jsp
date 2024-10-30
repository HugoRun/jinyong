<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
总人数:${faction.memberNum }/${faction.maxMemberNum }<br/>
<%@ include file="../../inc/member_list.jsp"%>
<%@ include file="../../inc/return_faction.jsp"%>