package com.ls.web.action.attack;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.Shortcut;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.ActionBase;
import com.ls.web.action.shortcut.AttackShortcutAction;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.PropUseService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.skill.SkillService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.systemInfo.SystemInfoService;

public class PKAttackAction extends ActionBase
{
	// �õ���ǰ�ص���������������б�
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		PlayerService playerService = new PlayerService();
		
		List<RoleEntity> players = playerService.getPlayersByView(roleInfo);
		playerService.addUserStateFlag(players);


		QueryPage item_page = new QueryPage(this.getPageNo(request),players);
		item_page.setURL(response, "/pk.do?cmd=n1");
		
		request.setAttribute("scene", roleInfo.getBasicInfo().getSceneInfo());
		request.setAttribute("item_page", item_page);
		request.setAttribute("me", roleInfo);
		return mapping.findForward("playerlist");
	}

	/*public void qianmianDoing(HttpServletRequest request, Fighter win,
			Fighter fail, SceneVO scene, HttpServletResponse response)
	{

		if (win != null && fail != null)
		{
			if (win.getPPk() == LangjunConstants.LANGJUN_PPK
					|| win.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
			{
				LangjunConstants.NAME_SET.add(win.getPPk());
			}
			if (fail.getPPk() == LangjunConstants.LANGJUN_PPK
					|| fail.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
			{
				LangjunConstants.NAME_SET.add(fail.getPPk());
			}
		}
		if (win != null && win.getPPk() == LangjunConstants.LANGJUN_PPK
				|| win.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
		{
			RoleEntity win1 = new RoleCache()
					.getByPpk(LangjunConstants.LANGJUN_PPK);
			if (win1 != null)
			{
				win1.getStateInfo().setCurState(PlayerState.GENERAL);
			}
		}
		if (win != null && fail != null
				&& fail.getPPk() == LangjunConstants.LANGJUN_PPK)
		{
			RoleEntity failre = new RoleService().getRoleInfoById(fail.getPPk()
					+ "");
			new SystemInfoService().insertSystemInfoBySystem("ĳλ������"
					+ scene.getMap().getMapName() + "��" + scene.getSceneName()
					+ "����ǧ���ɾ�����ʱ���ˣ���ǧ���ɾ����Ѿ���"
					+ failre.getBasicInfo().getRealName() + "�������뿪���ٴβ��䵽��Ⱥ֮�У�");
			MailInfoService mailInfoService = new MailInfoService();
			UMsgService uMsgService = new UMsgService();
			// ���ʧ�ܵ���ǧ���ɾ�
			// ʤ�����˳�Ϊǧ���ɾ�
			new LangjunUtil().becameLangjun(request, win.getPPk(), false);
			int mailId1 = mailInfoService.insertMailReturnId(win.getPPk(),
					"ǧ���ɾ�����", "aa");
			String help2 = "<anchor><go method=\"post\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/langjun.do") + "\">"
					+ "<postfield name=\"cmd\" value=\"n33\" />"
					+ "<postfield name=\"mailId\" value=\"" + mailId1 + "\" />"
					+ " </go>" + "��ȡ��ǧ���ɾ�������</anchor><br/>";
			mailInfoService.updateMail(mailId1,
					"��ϲ������ˡ�ǧ���ɾ���,��û�������һ��,�����.<br/>" + help2);
			UMessageInfoVO uif = new UMessageInfoVO();
			uif.setCreateTime(new Date());
			uif.setMsgPriority(PopUpMsgType.LANGJUN_FIRST);
			uif.setMsgType(PopUpMsgType.LANGJUN);
			uif.setPPk(fail.getPPk());
			uif.setMsgOperate1("��ǧ���ɾ����������������ˣ�");
			uMsgService.sendPopUpMsg(uif);
			new RankService().updateAdd(win.getPPk(), "killlang", 1);
		}
		if (win != null && fail != null
				&& fail.getPPk() == LangjunConstants.XIANHAI_LANGJUN)
		{
			RoleEntity re1 = new RoleCache()
					.getByPpk(LangjunConstants.XIANHAI_LANGJUN);
			if (re1 != null)
			{
				re1.getBasicInfo().setTemp_Name(
						re1.getBasicInfo().getRealName());
			}
			LangjunConstants.XIANHAI_LANGJUN = 0;
			UMsgService uMsgService = new UMsgService();
			UMessageInfoVO uif = new UMessageInfoVO();
			uif.setCreateTime(new Date());
			uif.setMsgPriority(PopUpMsgType.LANGJUN_FIRST);
			uif.setMsgType(PopUpMsgType.LANGJUN);
			uif.setPPk(win.getPPk());
			uif.setMsgOperate1("���ź�����ɱ�����ǡ�ǧ���ɾ�������������޷���ý�������");
			uMsgService.sendPopUpMsg(uif);
		}
	}*/

	// �Թ��е����ؾ���ͼ
	private boolean dropMiJing(RoleEntity re, Fighter win, Fighter fail)
	{
		if (re.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.COMPASS)
		{
			int result = new GoodsService().updateMiJingOwner(fail.getPPk(),
					win.getPPk());
			if (result > 0)
			{
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.LANGJUN_FIRST);
				uif.setMsgType(PopUpMsgType.LANGJUN);
				uif.setPPk(fail.getPPk());
				uif.setMsgOperate1("����ؾ���ͼ��" + win.getPName() + "�����ˡ�����");
				UMsgService uMsgService = new UMsgService();
				uMsgService.sendPopUpMsg(uif);
				new SystemInfoService().insertSystemInfoBySystem(fail
						.getPName()
						+ "��"
						+ win.getPName()
						+ "��"
						+ re.getBasicInfo().getSceneInfo().getSceneName()
						+ "ɱ�����ؾ���ͼ�䵽��" + win.getPName() + "�����У�");
			}
			return result > 0 ? true : false;
		}
		return false;
	}

	// �����������
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me = this.getRoleEntity(request);
		RoleEntity other = me.getPKState().getOtherOnFight();
		
		if( other==null )//���޹��������򷵻���Ϸ����
		{
			return this.returnScene(request, response);
		}
		
		//���������Ϣ
		this.setSysInfo(request, me);
		
		String sPk = (String) request.getAttribute("sPk");
		
		PlayerService playerService = new PlayerService();
		SkillService skillService = new SkillService();
		
		Fighter playerA = playerService.getFighterByPpk(me.getPPk());// ��������
		Fighter playerB = playerService.getFighterByPpk(other.getPPk());// ��������
		
		String contentdisplay_bak = playerA.getContentdisplay().replace(me.getName(), "��");
		playerA.setContentdisplay(contentdisplay_bak);
		//����Ǽ��ܹ������ж��Ƿ���ã�������þ�ֱ�ӿ۳�MP
		if (sPk != null)
		{
			playerService.loadPlayerSkill(playerA, Integer.parseInt(sPk));
			String skill_hint = skillService.isUsabled(playerA, playerA.getSkill());
			if (skill_hint != null)
			{
				this.setHint(request, skill_hint);
				return mapping.findForward("skillhint");
			}
		}
		
		AttackService attackService = new AttackService();
		String unAttack = (String) request.getAttribute("unAttack");
		// �����Է�
		attackService.attackPlayer(playerA, playerB, unAttack);
		
		/************PK��һ���غϺ���Ҫ���Ĵ���*************/
		
		if(playerA.isDead()||playerB.isDead())
		{
			AttackService.processPKOver(playerA, playerB,request);//����PK������Ľ��
			if (playerA.isDead())
			{
				me.getPKState().notifySelfDead(other,playerB.getKillDisplay());
				return mapping.findForward("dead_prop");
			}
			else if(playerB.isDead())
			{
				me.getPKState().notifySelfKill(other,playerB.getKillDisplay());
				return mapping.findForward("win");
			}
		}
		else
		{
			request.setAttribute("playerA", playerA);
			request.setAttribute("playerB", playerB);
			return mapping.findForward("attack");
		}
		return null;
	}
	
	// ����ս����ҳ��
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String other_ppk = request.getParameter("bPpk");
		
		RoleEntity me = this.getRoleEntity(request);
		RoleEntity other = RoleService.getRoleInfoById(other_ppk);
		
		String hint = me.getPKState().startAttack(other);//����PK����״̬
		
		if( hint!=null )
		{
			this.setHint(request, hint);
			return mapping.findForward("return_hint");
		}
		
		return this.n7(mapping, form, request, response);
	}

	//֪ͨ���˱�����
	public ActionForward notifyOtherDead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		UMessageInfoVO u_msg_info = (UMessageInfoVO) request.getAttribute("uMsgInfo");//����ʽ��Ϣ
		if( u_msg_info==null )
		{
			return super.returnScene(request, response);
		}
		
		StringBuffer over_display = new StringBuffer();
		
		over_display.append(u_msg_info.getMsgOperate1());
		
		request.setAttribute("over_display", over_display.toString());
		return mapping.findForward("over");
	}
	//֪ͨ�Լ�������
	public ActionForward notifySelfDead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		UMessageInfoVO u_msg_info = (UMessageInfoVO) request.getAttribute("uMsgInfo");//����ʽ��Ϣ
		if( u_msg_info==null )
		{
			return super.returnScene(request, response);
		}

		StringBuffer over_display = new StringBuffer();
		
		over_display.append(u_msg_info.getMsgOperate1());

		Fighter playerA = new Fighter();
		playerA.appendKillDisplay(over_display.toString());
		request.setAttribute("player", playerA);
		request.setAttribute("pk", "pk");
		return mapping.findForward("dead_prop");
	}
	
	//֪ͨɱ���Է�
	public ActionForward notifyKillOther(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		UMessageInfoVO u_msg_info = (UMessageInfoVO) request.getAttribute("uMsgInfo");//����ʽ��Ϣ
		if( u_msg_info==null )
		{
			return super.returnScene(request, response);
		}
		
		RoleEntity me = this.getRoleEntity(request);
		
		me.getStateInfo().setCurState(PlayerState.EXTRA);
		
		String dead_ppk = u_msg_info.getMsgOperate2();// ����
		String me_ppk = me.getPPk()+"";// ����

		StringBuffer over_display = new StringBuffer();
		
		over_display.append(u_msg_info.getMsgOperate1());
		
		request.setAttribute("over_display", over_display.toString());
		request.setAttribute("a_p_pk", dead_ppk);
		request.setAttribute("b_p_pk", me_ppk);
		request.setAttribute("type", 2);//2Ϊʤ����
		return mapping.findForward("over");
	}

	// ��ʾ�Է�����
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me =this.getRoleEntity(request);
		String bPpk = request.getParameter("bPpk");

		RoleEntity other = RoleCache.getByPpk(bPpk);
		
		request.setAttribute("other", other);
		request.setAttribute("me", me);
		return mapping.findForward("other_display");
	}

	// ��ݼ�����
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me = this.getRoleEntity(request);
		String sc_pk = request.getParameter("sc_pk");
		ShortcutVO shortcut = me.getRoleShortCutInfo().getShortByScPk(Integer.parseInt(sc_pk));

		if (shortcut.getScType() == Shortcut.ATTACK)// ��ͨ����
		{
			return n2(mapping, form, request, response);
		}
		else if (shortcut.getScType() == Shortcut.SKILL)// ���ܹ���
		{
			request.setAttribute("sPk", shortcut.getOperateId() + "");
			return n2(mapping, form, request, response);
		}
		else if (shortcut.getScType() == Shortcut.LOOKINFO)// �鿴�Է�����
		{
			return this.n5(mapping, form, request, response);
		}
		else if (shortcut.getScType() == Shortcut.DEFAULT || shortcut.getScType() == Shortcut.ATTACKPROP)// ���ÿ�ݼ�
		{
			request.getSession().setAttribute("retrun_url","/pk.do?cmd=n7");
			request.setAttribute("pk", "pk");
			return new AttackShortcutAction().n3(mapping, form, request,response);
		}
		else if (shortcut.getScType() == Shortcut.CURE)// ҩƷ��ݼ�
		{
			// �ж�pk���Ƿ����ʹ��ҩƷ
			if (me.getBasicInfo().getXuanyunhuihe() > 0)
			{
				this.setHint(request, "������ѣ�λغ�!����"+ me.getBasicInfo().getXuanyunhuihe()+ "���غϲ��ܷ���ҩ��");
				me.getBasicInfo().setXuanyunhuihe(me.getBasicInfo().getXuanyunhuihe() - 1);
				return mapping.findForward("skillhint");
			}
			PropUseService propService = new PropUseService();
			PropUseEffect propUseEffect = propService.usePropByPropID(me, shortcut.getOperateId(), 1, request,response);// ʹ��ҩƷ
			request.setAttribute("propUseEffect",propUseEffect);
			request.setAttribute("unAttack", "unAttack");
			return n11(mapping, form, request, response);
		}

		return null;
	}

	// ��ʾ֪ͨ����ҳ��
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me = this.getRoleEntity(request);
		
		//���������Ϣ
		this.setSysInfo(request, me);
		
		me.getStateInfo().setCurState(PlayerState.PKFIGHT);
		
		RoleEntity other = me.getPKState().getOtherOnFight();
		
		PlayerService playerService = new PlayerService();

		Fighter playerA = playerService.getFighterByPpk(me.getPPk());// ��������
		Fighter playerB = playerService.getFighterByPpk(other.getPPk());// ��������

		request.setAttribute("playerA", playerA);
		request.setAttribute("playerB", playerB);
		request.setAttribute("me", me);
		return mapping.findForward("display");
	}

	/*// PK��ȡװ��
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);

		String p_pk = request.getParameter("p_pk");
		String goods_id = request.getParameter("goods_id");
		String goods_type = request.getParameter("goods_type");
		String goods_num = request.getParameter("goods_num");
		String goods_name = request.getParameter("goods_name");
		String dp_pk = request.getParameter("dp_pk");
		DropgoodsPkDAO dropgoodsPkDAO = new DropgoodsPkDAO();
		DropgoodsPkVO dropgoodsPkVO = dropgoodsPkDAO.getDropGoodsPKByDppk(dp_pk);

		String a_p_pk = request.getParameter("a_p_pk");
		String b_p_pk = request.getParameter("b_p_pk");

		GoodsService goodsService = new GoodsService();
		String hint = null;
		if (Integer.parseInt(p_pk) == roleInfo.getPPk())
		{
			hint = "�����ܼ�ȡ���Լ�������Ʒ";
			request.setAttribute("hint", hint);
			return mapping.findForward("pickup");
		}
		
		List list = null;//������Ʒ�б�
		// ���������װ���Ļ� ��ôִ���������
		if (dropgoodsPkVO.getGoodsType() != GoodsType.PROP)
		{
			//�жϰ�������
			if (goodsService.isEnoughWrapSpace( roleInfo.getPPk(), 1))
			{
				//����������
				EquipService equipService = new EquipService();
				equipService.updateOwner( roleInfo,dropgoodsPkVO.getGoodsId());
				
				// ����ղż�ȡ����Ʒ
				dropgoodsPkDAO.getgetDropGoodsPKDelete(Integer.parseInt(dp_pk));
				hint = "����ȡ��" + goods_name;
				list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
				
			}
			else
			{
				list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
				hint = "���İ�����������";
			}

		}
		else
		{
			int d = goodsService.putGoodsToWrap(roleInfo.getPPk(), Integer.parseInt(goods_id), Integer.parseInt(goods_type), 
					Integer.valueOf(dropgoodsPkVO.getGoodsQuality()), Integer.parseInt(goods_num));
			if (d == -1)
			{
				list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
				hint = "���İ�����������";
			}
			else
			{
				// ����ղż�ȡ����Ʒ
				dropgoodsPkDAO.getgetDropGoodsPKDelete(Integer.parseInt(dp_pk));
				hint = "����ȡ��" + goods_name;
				list = dropgoodsPkDAO.getDropGoodsPKList(Integer.parseInt(p_pk), roleInfo.getPPk());
			}
		}
		request.setAttribute("a_p_pk", a_p_pk);
		request.setAttribute("b_p_pk", b_p_pk);
		request.setAttribute("list", list);
		request.setAttribute("hint", hint);
		return mapping.findForward("pickup");
	}*/

	// PKʤ��
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
			RoleEntity me = this.getRoleEntity(request);

			String type = request.getParameter("type");	// 3��ʧ���ߣ�2��ʤ����

			if( StringUtils.isEmpty(type)==false )
			{
				// ���Ϊʧ���ߣ���Ӧ�ûس�
				if (Integer.parseInt(type) == 3)
				{
					// �õ����������
					RoomService roomService = new RoomService();
					int resurrection_point = roomService.getResurrectionPoint(me);
					me.getBasicInfo().updateSceneId(resurrection_point + "");
				}

			}
			me.getStateInfo().setCurState(PlayerState.GENERAL);// ��λ���״̬
			return super.dispath(request, response, "/pubbuckaction.do?type=1");
	}


	// ��ʾ֪ͨ����ҳ��,�����빥��״̬.
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity me = this.getRoleEntity(request);
		
		String bPpk = request.getParameter("bPpk");

		PlayerService playerService = new PlayerService();

		if ( me.getBasicInfo().getXuanyunhuihe() > 0)
		{
			return n2(mapping, form, request, response);
		}
		Fighter playerA = playerService.getFighterByPpk(me.getPPk());// ��������
		Fighter playerB = playerService.getFighterByPpk(Integer.parseInt(bPpk));// ��������

		request.setAttribute("playerA", playerA);
		request.setAttribute("playerB", playerB);
		request.setAttribute("me", me);
		return mapping.findForward("display");
	}
	
	/**
	 * ����ϵͳ��Ϣ
	 * @param request
	 * @param roleInfo
	 */
	private void setSysInfo(HttpServletRequest request,RoleEntity me)
	{
		request.setAttribute("me", me);
		request.setAttribute("sys_info_list", me.getPKState().getSysInfoList());
		request.setAttribute("attack_warning",me.getPKState().getWarningDes());
	}
}