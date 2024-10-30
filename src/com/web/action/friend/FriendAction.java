package com.web.action.friend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.vo.communion.CommunionVO;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.cache.dynamic.manual.chat.ChatInfoCahe;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.service.group.GroupNotifyService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.mail.MailInfoService;
import com.pub.ben.info.Expression;
import com.web.service.friend.BlacklistService;
import com.web.service.friend.FriendService;

/**
 * @author ��ƾ� ����:���� 9:40:46 AM
 */
public class FriendAction extends ActionBase
{
	/**
	 * ���Ӻ���
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);

		String hint = "";
		
		FriendService friendService = new FriendService();
		BlacklistService blacklistService = new BlacklistService();
		
		if (friendService.whetherfriend(roleInfo.getPPk(), pByPk) == false)
		{
			hint = "������Ѿ������ĺ����˲���Ҫ������ˣ�";
		}
		else if (friendService.friendupperlimit(roleInfo.getPPk()) == false)
		{
			hint = "���ĺ��������Ѿ��ﵽ���ޣ�";
		}
		//����ύ��ұ���������� ����
		else if(blacklistService.whetherblacklist(Integer.parseInt(pByPk),roleInfo.getPPk()+"") == false)
		{
			hint = "�Է��Խ������������,�������ټӶԷ�Ϊ����!";
		}
		else if (Integer.parseInt(pByPk) == roleInfo.getPPk())
		{
			hint = "�������Լ����Լ�����!";
		}
		else
		{
			// ���������ں������� ɾ��������Ȼ���ڼ������
			if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(),pByPk) == false)
			{
				blacklistService.deleteblacklist(roleInfo.getBasicInfo().getPPk(),pByPk);
			}
			friendService.addfriend(roleInfo.getPPk(), pByPk,pByName, Time);
			hint = "���ѽ�" + pByName + "�������ĺ����б��";
		}
		
		this.setHint(request, hint);
		return super.dispath(request, response, "/swapaction.do?cmd=n13&pPks="+pByPk);
	}

	/**
	 * ����List
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		FriendService friendService = new FriendService();
		QueryPage queryPage = new QueryPage();
		String page_Str = request.getParameter("page");
		int page = 0;
		if (page_Str != null)
		{
			page = Integer.parseInt(page_Str);
		}
		int size = friendService.getFriendNum(roleInfo.getBasicInfo().getPPk());
		List<FriendVO> friendlist = null;
		int pageall = 0;
		if(size!=0){
		pageall = size / queryPage.getPageSize() + (size % queryPage.getPageSize() == 0 ? 0 : 1);
		// ��ѯ�������
		friendlist = friendService.listfriend(roleInfo
				.getBasicInfo().getPPk(), page * queryPage.getPageSize(), queryPage.getPageSize());
		friendService.getFriendInMapName(friendlist);
		}
		request.setAttribute("pageall", pageall);
		request.setAttribute("page", page);
		request.setAttribute("friendlist", friendlist);
		return mapping.findForward("friendlist");
	}

	/**
	 * ����View
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		
		RoleEntity other = RoleService.getRoleInfoById(pByPk);
		
		String pByName = request.getParameter("pByName");
		String page = request.getParameter("page");
		
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("page", page);
		request.setAttribute("pByName", pByName);
		
		request.setAttribute("other", other);
		return mapping.findForward("friendview");
	}

	/**
	 * ��������
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("friendcolloguepage");
	}

	/**
	 * ��������
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		String title = request.getParameter("title");

		/** TODO:����и���� ���û��������ֱ�ӷ���������� */
		int type = 5;

		// �õ���ǰ�����Ϣ
		String hint = null;
		PlayerService playerService = new PlayerService();

		hint = playerService.checkRoleState(Integer.parseInt(pByPk),
				PlayerState.TALK);
		if (hint != null)
		{
			String hintaa = hint + ",ϵͳ�Ѿ�����ת������(��)��������!";
			MailInfoService mailInfoService = new MailInfoService();
			String mailtitle = "���Ժ���" + roleInfo.getBasicInfo().getName() + "���ʼ�";
			mailInfoService.sendMail(Integer.parseInt(pByPk), roleInfo.getBasicInfo().getPPk(), 1, mailtitle, title);
			request.setAttribute("hint", hintaa);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return mapping.findForward("friendcollogue");
		}
		
		
		// ִ�в��빫�������¼ c_pk,p_pk,p_name,p_pk_by,p_name_by,c_title,c_type,create_time
		CommunionVO communionVO = new CommunionVO();
		communionVO.setPPk(roleInfo.getBasicInfo().getPPk());
		communionVO.setPName(roleInfo.getBasicInfo().getName());
		communionVO.setPPkBy(Integer.parseInt(pByPk));
		communionVO.setPNameBy(pByName);
		communionVO.setCTitle(title);
		communionVO.setCType(type);
		communionVO.setCreateTime(Time);
		ChatInfoCahe publicChatInfoCahe = new ChatInfoCahe();
		publicChatInfoCahe.put(communionVO);
		
		/*uPrivatelyDAO.getUPrivatelyAdd(roleInfo.getBasicInfo().getPPk()
				+ "", dao
				.getPartName(roleInfo.getBasicInfo().getPPk() + ""), pByPk
				+ "", pByName, title, type + "", Time);*/
		hint = "��Ϣ�Ѿ����ͳ�ȥ��!";
		
		/*RoleService roleServiceother = new RoleService();
		RoleEntity b_role_info = roleServiceother.getRoleInfoById(pByPk + "");*/

		/*if (b_role_info != null)
		{
			
		}
		else
		{
			hint = "���ĺ���û������!ϵͳ�Ѿ�����ת������(��)��������!";
			// //System.out.println("������");
			MailInfoService mailInfoService = new MailInfoService();
			String mailtitle = "�������ĺ���" + roleInfo.getBasicInfo().getName()
					+ "���ʼ�";
			mailInfoService.sendMail(Integer.parseInt(pByPk), roleInfo
					.getBasicInfo().getPPk(), 1, mailtitle, title);
		}*/
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		request.setAttribute("hint", hint);
		return mapping.findForward("friendcollogue");
	}

	/**
	 * ɾ������
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		String page = request.getParameter("page");

		request.setAttribute("page", page);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("frienddelete");
	}

	/**
	 * ɾ������
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		FriendService friendService = new FriendService();
		FriendVO fv = friendService.viewfriend(roleInfo.getBasicInfo().getPPk(), pByPk);
		if(fv.getRelation()==1){
			request.setAttribute("message", "���Ƚ�������ϵ");
		}else if(fv.getRelation()==2){
			request.setAttribute("message", "���Ƚ��������ϵ");
		}else{
		friendService.deletefriend(roleInfo.getBasicInfo().getPPk(), pByPk);
		}
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return mapping.findForward("frienddeleteok");
	}

	/**
	 * �������
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		String pByPk = request.getParameter("pByPk");
		String pByName = request.getParameter("pByName");
		
		SceneVO sceneVO = roleInfo.getBasicInfo().getSceneInfo();
		MapVO mapVO = sceneVO.getMap();
		if(mapVO.getMapType() == MapType.TIANGUAN){
			String hint = "��������ս���,�����������!";
			super.setHint(request, hint);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return this.n3(mapping, form, request, response);
		}
		
		PlayerService playerService = new PlayerService();
		String hint = playerService.isRoleState(Integer.parseInt(pByPk), 2);
		if(hint != null){
			super.setHint(request, hint);
			request.setAttribute("pByPk", pByPk);
			request.setAttribute("pByName", pByName);
			return this.n3(mapping, form, request, response);
		} 
		GroupNotifyService groupNotifyService = new GroupNotifyService();
		
		
		// ����������
		String apply_hint = groupNotifyService.applyGroup(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(pByPk));
		
		//����ύ�������ɹ�����������뵯��ʽ��Ϣ����
		if(apply_hint == null)
		{
			RoleEntity pByPk_roleInfo = RoleService.getRoleInfoById(pByPk);
    		UMsgService uMsgService = new UMsgService();
    		UMessageInfoVO msgInfo = new UMessageInfoVO();
    		msgInfo.setMsgType(PopUpMsgType.MESSAGE_GROUP);
    		int group_id = pByPk_roleInfo.getStateInfo().getGroup_id();
    		if(group_id != -1){
    				msgInfo.setPPk(group_id);
    		} else{
    				msgInfo.setPPk(Integer.parseInt(pByPk));
    		}
    		apply_hint = "������Է��ύ���������";
    		msgInfo.setMsgPriority(PopUpMsgType.MESSAGE_GROUP_FIRST);
    		uMsgService.sendPopUpMsg(msgInfo);
		}
		
		super.setHint(request, apply_hint);
		request.setAttribute("pByPk", pByPk);
		request.setAttribute("pByName", pByName);
		return this.n3(mapping, form, request, response);
	}

	/**
	 * ���Ӻ���
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("friendaddpage");
	}

	/**
	 * ���Ӻ���
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		PartInfoDAO partInfoDAO = new PartInfoDAO();
		String pByName = request.getParameter("pByName");
		int pByPk = partInfoDAO.getPartPk(pByName);
		FriendService friendService = new FriendService();
		String hint = null;
		// ���������ַ�
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(pByName);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "������ֲ���ȷ!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (partInfoDAO.getPartTypeListName(pByName) == false)
		{
			hint = "����Ҳ�����!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if(partInfoDAO.getIsNewState(pByName))
		{
			hint = "����Ҵ������������׶β��ɼ�Ϊ���ѣ�";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (friendService.whetherfriend(roleInfo.getBasicInfo().getPPk(), pByPk
				+ "") == false)
		{
			hint = "������Ѿ������ĺ����˲���Ҫ�������!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (friendService.friendupperlimit(roleInfo.getBasicInfo().getPPk()) == false)
		{
			hint = "���ĺ��������Ѿ��ﵽ����!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		if (pByPk == roleInfo.getBasicInfo().getPPk())
		{
			hint = "�������Լ����Լ�����!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		// ���������ں������� ɾ��������Ȼ���ڼ������
		BlacklistService blacklistService = new BlacklistService();
		
		if(blacklistService.whetherblacklist(pByPk,roleInfo.getBasicInfo().getPPk()+"") == false)
		{
			hint = "�Է��Խ������������,�������ټӶԷ�Ϊ����!";
			request.setAttribute("hint", hint);
			return mapping.findForward("friendaddpageOK");
		}
		
		if (blacklistService.whetherblacklist(roleInfo.getBasicInfo().getPPk(),
				pByPk + "") == false)
		{
			blacklistService.deleteblacklist(roleInfo.getBasicInfo().getPPk(),
					pByPk + "");
		}

		friendService.addfriend(roleInfo.getBasicInfo().getPPk(), pByPk + "",
				pByName, Time);
		hint = "���ѽ�" + pByName + "�������ĺ����б��";
		request.setAttribute("hint", hint);
		return mapping.findForward("friendaddpageOK");
	}
}
