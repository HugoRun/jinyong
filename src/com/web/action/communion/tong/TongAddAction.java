/**
 * 
 */
package com.web.action.communion.tong;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.player.RoleService;
import com.pub.ben.info.Expression;

/**
 * @author ��ƾ�
 * 
 * 9:40:46 AM
 */
public class TongAddAction extends Action
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

		/*Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(puTitle);
		boolean b = m.matches();
		if (b == false)
		{
			String hint1 = "�����������зǷ��ַ������������룡";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}*/
		int flag = Expression.hasPublish(puTitle);
		if (flag == -1)
		{
			String hint1 = "�����������зǷ��ַ������������룡";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		if (puTitle.length() > 30)
		{
			String hint1 = "��ֻ������30���ַ�";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		// �����ɫû������򷵻�
		if (roleInfo.getBasicInfo().getFaction() == null)
		{
			String hint = "�Բ�����Ŀǰû�м�����ɣ��޷��ڰ��Ƶ�����ԣ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.trim().equals("")){
			String hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("��") != -1){
			String hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		// ִ�в��빫�������¼ c_pk,p_pk,p_name,c_bang,c_title,c_type,create_time
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setCBang(roleInfo.getBasicInfo().getFaction().getId());
		communionVO.setCTitle(puTitle);
		communionVO.setCType(Integer.parseInt(type));
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		return mapping.findForward("success");

	}
}
