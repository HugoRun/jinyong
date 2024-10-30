package com.ben.tiaozhan;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.shitu.model.DateUtil;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.player.PlayerState;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.action.menu.BaseAction;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.ShortcutService;
import com.ls.web.service.system.UMsgService;

public class TiaoZhanAction extends BaseAction
{
	private PlayerService ps = new PlayerService();

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		request.setAttribute("pg_pk",
				request.getParameter("pg_pk") == null ? request
						.getAttribute("pg_pk") : request.getParameter("pg_pk"));
		String name = request.getParameter("name");
		if (name == null || "".equals(name.trim()))
		{
			setMessage(request, "����������");
			return mapping.findForward("tiaozhan");
		}
		int ppk = roleService.getByName(name.trim());
		if (ppk == -1 || ppk == 0)
		{
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		RoleEntity re = roleService.getRoleInfoById(ppk + "");
		if (re == null)
		{
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		if(re.getBasicInfo().getName().equalsIgnoreCase("gm")){
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		int re_ppk = re.getBasicInfo().getPPk();
		if (!(re.getStateInfo().getCurState() == PlayerState.GENERAL
				|| re.getStateInfo().getCurState() == PlayerState.TRADE
				|| re.getStateInfo().getCurState() == PlayerState.GROUP || re
				.getStateInfo().getCurState() == PlayerState.TALK))
		{
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		if (!(re.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.SAFE || re
				.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.DANGER))
		{
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		if(TiaozhanConstant.TIAOZHAN.containsKey(bi.getPPk())){
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		if (re_ppk == bi.getPPk())
		{
			setMessage(request, "�������Զ��Լ�������ս��");
			return mapping.findForward("tiaozhan");
		}
		if (TiaozhanConstant.TIAOZHAN.containsKey(re_ppk)
				|| TiaozhanConstant.TIAOZHAN.containsValue(re_ppk))
		{
			setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
			return mapping.findForward("tiaozhan");
		}
		String pg_pk = request.getParameter("pg_pk");
		if (pg_pk != null && !"".equals(pg_pk.trim()))
		{
			PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
			PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer
					.parseInt(pg_pk.trim()));
			if (propGroup != null)
			{
				if (TiaozhanConstant.TIAOZHAN.containsKey(re_ppk)
						|| TiaozhanConstant.TIAOZHAN.containsValue(re_ppk))
				{
					setMessage(request, "��Ŀǰ�޷��Ը���ҷ�����ս��");
					return mapping.findForward("tiaozhan");
				}
				TiaozhanConstant.TIAOZHAN_TIME.put(re_ppk, new Date());
				TiaozhanConstant.TIAOZHAN.put(bi.getPPk(), re_ppk);
				TiaozhanConstant.TIAOZHAN.put(re_ppk, bi.getPPk());
				goodsService.removeProps(propGroup, 1);
				setMessage(request, "���Ѿ��ɹ���" + name.trim() + "��������ս��");
				UMsgService uMsgService = new UMsgService();
				UMessageInfoVO uif = new UMessageInfoVO();
				uif.setCreateTime(new Date());
				uif.setMsgPriority(PopUpMsgType.TIAOZHAN_FIRST);
				uif.setMsgType(PopUpMsgType.TIAOZHAN);
				uif.setPPk(re_ppk);
				uif.setResult(bi.getName());
				uif.setMsgOperate1(bi.getPPk() + "");
				uMsgService.sendPopUpMsg(uif);
				systemInfoService.insertSystemInfoBySystem(bi.getRealName() + "��"
						+ name.trim() + "����ս�飡��֪" + name.trim() + "������Ӧ�ԣ�");
				
				return mapping.findForward(ERROR);
			}
			else
			{
				setMessage(request, "�õ��߲���ʹ��");
				return mapping.findForward(ERROR);
			}
		}
		else
		{
			setMessage(request, "�õ��߲���ʹ��");
			return mapping.findForward(ERROR);
		}
	}
	
	private String getSystemMessage(String name1,String name2){
		Random r = new Random();
		int numm = r.nextInt(5);
		String mess = name1 + "��"+ name2 + "�İ������𣬾�Ȼû�е�������ս�飡";
		switch(numm){
			case 0:break;
			case 1: mess = name1+"��"+name2+"��������ѹ��,��Ȼ���ĵ�������,���µ�ֻ��"+name2+"���ӵ�Ŀ�⡣";break;
			case 2: mess = name2+"���ظ��ĵ���ž�죬��"+name1+"������������ë���˲���,�ӵĻ����,���ֵ��������!��";break;
			case 3: mess = name2+"��"+name1+"��ȥ����Ӱ������:\"��С��,��Ҫ��,���Ǵ�ս���ٻغ�\"��";break;
			case 4: mess = name2+"��"+name1+"�ڿ���ʽ׼����ս,"+name1+"ͻȻ��ʧ��,"+name2+"��̾��\""+name1+"�������Ͳ�Ҫ����,�˷�ʱ��\"��";break;
			default : break;
		}
		return mess;
	}

	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String caozuo = request.getParameter("caozuo");
		String tiao_ppk = request.getParameter("tiao_ppk");
		if (tiao_ppk == null || "".equals(tiao_ppk.trim()))
		{
			setMessage(request, "������");
			return mapping.findForward(ERROR);
		}

		RoleEntity reown = getRoleEntity(request);
		BasicInfo bi = reown.getBasicInfo();
		RoleEntity re = roleService.getRoleInfoById(tiao_ppk + "");
		String tiao_name = roleService.getName(tiao_ppk)[0];
		if (re == null)
		{
			TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
			if (TiaozhanConstant.TIAOZHAN.containsKey(bi.getPPk()))
			{
				int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
				TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
				TiaozhanConstant.TIAOZHAN.remove(pk);
			}
			systemInfoService.insertSystemInfoBySystem(getSystemMessage(tiao_name, bi.getRealName()));
			setMessage(request, tiao_name + "�����İ������𣬾�Ȼû�е�������ս�飡");
			return mapping.findForward(ERROR);
		}
		if (caozuo == null || "".equals(caozuo.trim()))
		{
			TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
			int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(pk);
			systemInfoService.insertSystemInfoBySystem(getSystemMessage(bi.getRealName(),tiao_name));
			return mapping.findForward("refurbish_scene");
		}
		Date date = TiaozhanConstant.TIAOZHAN_TIME.get(bi.getPPk());
		if (DateUtil.checkMin(date, TiaozhanConstant.OVER_TIME))
		{
			setMessage(request, "��������1������û�н���ѡ����˱���Ϊ�ܾ���ս");
			TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
			int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(pk);
			systemInfoService.insertSystemInfoBySystem(getSystemMessage(bi.getRealName(),tiao_name));
			return mapping.findForward(ERROR);
		}
		int maptype = re.getBasicInfo().getSceneInfo().getMap().getMapType();
		if (maptype != MapType.SAFE && maptype != MapType.DANGER)
		{
			TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
			int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(pk);
			systemInfoService.insertSystemInfoBySystem(getSystemMessage(tiao_name, bi.getRealName()));
			setMessage(request, tiao_name + "�����İ������𣬾�Ȼû�е�������ս�飡");
			return mapping.findForward(ERROR);
		}
		int state = re.getStateInfo().getCurState();
		if (!(state == PlayerState.GENERAL || state == PlayerState.TRADE
				|| state == PlayerState.GROUP || state == PlayerState.TALK))
		{
			TiaozhanConstant.TIAOZHAN_TIME.remove(bi.getPPk());
			int pk = TiaozhanConstant.TIAOZHAN.get(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(bi.getPPk());
			TiaozhanConstant.TIAOZHAN.remove(pk);
			systemInfoService.insertSystemInfoBySystem(getSystemMessage(tiao_name, bi.getRealName()));
			setMessage(request, tiao_name + "�����İ������𣬾�Ȼû�е�������ս�飡");
			return mapping.findForward(ERROR);
		}
		else
		{
			int bppk = re.getBasicInfo().getPPk();
			// ������̨
			systemInfoService.insertSystemInfoBySystem(bi.getRealName() + "������"
					+ tiao_name + "����ս��˫��վ����������̨�ϣ�");
			ps.updateSceneAndView(bppk, TiaozhanConstant.TIAOZHAN_SCENE);
			ps.updateSceneAndView(bi.getPPk(), TiaozhanConstant.TIAOZHAN_SCENE);
			ShortcutService shortcutService = new ShortcutService();
			
			reown.getPKState().startAttack(re);//����re
			
			Fighter playerA = new Fighter();// ��������
			Fighter playerB = new Fighter();// ��������
			ps.loadFighterByPpk(playerA, bi.getPPk());
			ps.loadFighterByPpk(playerB, bppk);

			List<ShortcutVO> shortcuts = shortcutService.getByPpk(bi.getPPk());

			request.setAttribute("playerA", playerA);
			request.setAttribute("playerB", playerB);
			request.setAttribute("shortcuts", shortcuts);
			request.setAttribute("tong", "0");
			// //System.out.println("Action N7 = -----
			// "+request.getParameter("chair"));
			String unAttack = (String) request.getAttribute("unAttack");
			request.setAttribute("unAttack", unAttack);
			request.setAttribute("reqchair", request.getParameter("chair"));
			return mapping.findForward("display");
		}
	}
}
