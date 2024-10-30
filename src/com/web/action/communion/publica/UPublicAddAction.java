/**
 * 
 */
package com.web.action.communion.publica;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.embargo.EmbargoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.cache.staticcache.forbid.ForBidCache;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.player.RoleService;
import com.pub.ben.info.Expression;

/**
 * @author ��ƾ�
 * 
 * 9:40:46 AM
 */
public class UPublicAddAction extends Action
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// ����ʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String puTitle = request.getParameter("puTitle");
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
		if(puTitle.indexOf("GM") != -1){
			String hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("Gm") != -1){
			String hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if(puTitle.indexOf("gm") != -1){
			String hint = "�Բ������ķ����а�����ֹ�ַ�!";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		if (puTitle.length() > 30)
		{
			String hint1 = "��ֻ������30���ַ�";
			request.setAttribute("hint", hint1);
			return mapping.findForward("successno");
		}
		// �ж��Ƿ��ظ�����
		EmbargoDAO embargoDAO = new EmbargoDAO();
		String s = embargoDAO.isEmbargo(roleInfo.getBasicInfo().getPPk(), Time);
		if (s != null)
		{
			String hint = "�����ڹ�������Ƶ������" + s + "���ӣ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		
		//���ĵĵȼ�����
		int public_chat_grade_limit = GameConfig.getPublicChatGradeLimit();
		if (roleInfo.getBasicInfo().getGrade() < public_chat_grade_limit )
		{
			String hint = "�Բ���ֻ��"+public_chat_grade_limit+"�����ϵ���Ҳſ����ڹ���Ƶ�����ԣ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		
		if (Expression.hasForbidChar(puTitle,ForBidCache.FORBIDCOMM))
		{
			String hint = "�Բ������ķ����а�����ֹ�ַ�!";
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

		String hint = roleInfo.getStateInfo().isPublicChat();
		if( hint!=null )
		{
			request.setAttribute("hint", hint);
			return mapping.findForward("successno");
		}
		
		// ִ�в��빫�������¼
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setCTitle(puTitle);
		communionVO.setCType(1);
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		return mapping.findForward("success");
	}
}