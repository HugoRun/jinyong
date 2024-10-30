/**
 * 
 */
package com.web.action.communion.group;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.player.RoleService;
import com.pub.ben.info.Expression;

/**
 * @author ��ƾ�
 * 
 * 9:40:46 AM
 */
public class GroupAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String puTitle = request.getParameter("puTitle");
		String type = request.getParameter("type");
		String hint = null;
		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(puTitle);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "�����������зǷ��ַ������������룡";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}*/
		int flag = Expression.hasPublish(puTitle);
		if (flag == -1)
		{
			hint = "�����������зǷ��ַ������������룡";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if (puTitle.length() > 30)
		{
			String hint1 = "��ֻ������30���ַ�";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		if (roleInfo.getStateInfo().getGroup_id() == -1)
		{
			hint = "�Բ�����Ŀǰû�м�����飬�޷������Ƶ�����ԣ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if (Expression.hasForbidChar(puTitle,ForBidCache.FORBIDCOMM))
		{
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.trim().equals("")){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("��") != -1){
			hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		// ִ�в��빫�������¼ c_pk,p_pk,p_name,c_dui,c_title,c_type,create_time
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setCDui(roleInfo.getStateInfo().getGroup_id());
		communionVO.setCTitle(puTitle);
		communionVO.setCType(Integer.parseInt(type));
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		return mapping.findForward("success");
	}
}
