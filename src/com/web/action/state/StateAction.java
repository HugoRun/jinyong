package com.web.action.state;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.info.skill.SkillDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.model.equip.EquipOnBody;
import com.ls.model.equip.PositionOnEquip;
import com.ls.model.property.RoleSkillInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.action.ActionBase;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.skill.SkillService;
import com.lw.dao.specialprop.SpecialPropDAO;
import com.lw.service.specialprop.SpecialPropService;
import com.lw.vo.specialprop.SpecialPropVO;
import com.pm.service.pic.PicService;
import com.web.service.friend.FriendService;

/**
 * @author 侯浩军 状态
 */
public class StateAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");

	/**
	 * 查看自己状态
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleInfo = this.getRoleEntity(request);
		
		int p_pk = roleInfo.getBasicInfo().getPPk();
		PicService picService = new PicService();
		BuffEffectService buffService = new BuffEffectService();

		// 获取人物图片
		String playerPic = picService.getPlayerPicStr(roleInfo, roleInfo.getBasicInfo().getPPk());
		String buff_list_describe = buffService.getBuffListDescribe(p_pk);
		
		int glory_value = roleInfo.getBasicInfo().getFContribute();//帮派贡献

		//得到结义结婚
		FriendService friendService = new FriendService();
		String  jieyiname = friendService.returnjieyilist(p_pk);
		String  jiehunname = friendService.returnjiehun(p_pk);
		
		request.setAttribute("jieyiname", jieyiname);
		request.setAttribute("jiehunname", jiehunname);
		request.setAttribute("roleInfo", roleInfo);
		request.setAttribute("glory_value", glory_value + "");
		request.setAttribute("buff_list_describe", buff_list_describe);
		request.setAttribute("playerPic", playerPic);
		return mapping.findForward("State");
	}

	/**
	 * 查看别人状态
	 */
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String other_pk = request.getParameter("pPks");

		RoleEntity roleInfo = this.getRoleEntity(request);

		PartInfoVO player = new PartInfoVO();

		PlayerService playerService = new PlayerService();
		PicService picService = new PicService();
		// 加载任务信息
		player = playerService.getPlayerBasicInfo(Integer.parseInt(other_pk));
		// 获取人物图片
		String playerPic = picService.getPlayerPicStr(roleInfo, other_pk);

		request.setAttribute("pPks", other_pk);

		request.setAttribute("playerPic", playerPic);
		request.setAttribute("player", player);
		return mapping.findForward("other_state");
	}

	/**
	 * 人物属性
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(p_pk);
		request.setAttribute("player", player);
		return mapping.findForward("attribute");
	}

	/**
	 * 查看对方装备详情
	 */
	public ActionForward n17(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity my_roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String other_pk = request.getParameter("pPks");

		String pwPk = request.getParameter("pwPk");

		PlayerEquipVO other_equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		
		String equip_display = equipDisplayService.getEquipDisplay(my_roleInfo,other_equip, true);

		request.setAttribute("equip_display", equip_display);
		request.setAttribute("pPks", other_pk);
		return mapping.findForward("other_accouterview");
	}

	/**
	 * 查看别人装备
	 */
	public ActionForward n16(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		
		String other_p_pk = request.getParameter("other_p_pk");

		request.setAttribute("pPks", other_p_pk);

		RoleEntity other_role = RoleService.getRoleInfoById(other_p_pk);
		EquipOnBody equip_on_body = other_role.getEquipOnBody();
		
		String suitEffectDescribte = equip_on_body.getSuitEffectDes();
		String wxTypeAttribute = equip_on_body.getWXAppendDes();
		request.setAttribute("wxTypeAttribute", wxTypeAttribute);
		request.setAttribute("suitEffectDescribte", suitEffectDescribte);
		request.setAttribute("partEquipVO1", equip_on_body.getByPositin(PositionOnEquip.WEAPON));
		request.setAttribute("partEquipVO2", equip_on_body.getByPositin(PositionOnEquip.HAT));
		request.setAttribute("partEquipVO3", equip_on_body.getByPositin(PositionOnEquip.CLOTHING));
		request.setAttribute("partEquipVO4", equip_on_body.getByPositin(PositionOnEquip.TROUSERS));
		request.setAttribute("partEquipVO5", equip_on_body.getByPositin(PositionOnEquip.SHOES));
		request.setAttribute("partEquipVO6", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_1));
		request.setAttribute("partEquipVO7", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_2));
		request.setAttribute("partEquipVO8", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_3));
		
		//获得携带宠物
		//PetInfoVO petInfoVO = other_role.getRolePetInfo().getBringPet();
		//request.setAttribute("petInfoVO", petInfoVO);
		return mapping.findForward("other_accouter");
	}

	/**
	 * 查看自己装备
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		String hint = (String) request.getAttribute("hint");
		
		EquipOnBody equip_on_body = roleInfo.getEquipOnBody();
		
		SpecialPropDAO spdao = new SpecialPropDAO();
		PropVO vo = null;
		int id = spdao.getEquipItemHM(p_pk);
		if (id != 0)
		{
			PropVO vo1 = PropCache.getPropById(id);
			if(vo1!=null){
			vo = new PropVO();
			vo.setPropID(vo1.getPropID());
			vo.setPropClass(vo1.getPropClass());
			vo.setPropName(vo1.getPropName());
			}
		}
		String suitEffectDescribte = equip_on_body.getSuitEffectDes();
		String wxTypeAttribute = equip_on_body.getWXAppendDes();
		request.setAttribute("wxTypeAttribute", wxTypeAttribute);
		request.setAttribute("suitEffectDescribte", suitEffectDescribte);
		request.setAttribute("hint", hint);
		request.setAttribute("partEquipVO1", equip_on_body.getByPositin(PositionOnEquip.WEAPON));
		request.setAttribute("partEquipVO2", equip_on_body.getByPositin(PositionOnEquip.HAT));
		request.setAttribute("partEquipVO3", equip_on_body.getByPositin(PositionOnEquip.CLOTHING));
		request.setAttribute("partEquipVO4", equip_on_body.getByPositin(PositionOnEquip.TROUSERS));
		request.setAttribute("partEquipVO5", equip_on_body.getByPositin(PositionOnEquip.SHOES));
		request.setAttribute("partEquipVO6", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_1));
		request.setAttribute("partEquipVO7", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_2));
		request.setAttribute("partEquipVO8", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_3));
		request.setAttribute("propVO", vo);
		return mapping.findForward("accouter");
	}

	/**
	 * 查看自己的装备详情
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String pwPk = request.getParameter("pwPk");
		String position = request.getParameter("position");

		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));

		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,false);
		request.setAttribute("equip_display", equip_display);
		request.setAttribute("pwPk", pwPk);
		request.setAttribute("position", position);
		return mapping.findForward("accouterview");
	}

	/**
	 * 人物技能
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);

		RoleSkillInfo useSkill = roleEntity.getRoleSkillInfo();
		List<PlayerSkillVO> skills = useSkill.getSkillList();

		request.setAttribute("skills", skills);
		return mapping.findForward("skill_list");
	}

	/**
	 * 查看技能详情
	 */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		String sk_id = request.getParameter("sk_id");

		PlayerSkillVO skill = null;
		int next_sleight = 0;

		SkillDao skillDao = new SkillDao();
		SkillService ss = new SkillService();

		RoleSkillInfo userSkill = roleEntity.getRoleSkillInfo();

		skill = userSkill.getSkillBySkId(sk_id);
		skillDao.loadPlayerSkillDetailByInside(skill);

		next_sleight = ss.getNextLevelSleight(Integer.parseInt(sk_id));

		request.setAttribute("skill", skill);
		request.setAttribute("next_sleight", next_sleight + "");
		return mapping.findForward("skill_display");
	}

	/**
	 * 杀怪人物状态
	 */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		/*
		 * PartInfoDAO dao = new PartInfoDAO(); PartInfoVO vo = (PartInfoVO)
		 * dao.getPartView(userTempBean.getUPk() + "", userTempBean.getPPk() +
		 * "");
		 */
		PlayerService playerService = new PlayerService();
		PartInfoVO player = null;
		player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
		request.setAttribute("player", player);
		return mapping.findForward("attackState");
	}

	/**
	 * 杀怪人物属性
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		PlayerService playerService = new PlayerService();
		PartInfoVO player = null;
		player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
		request.setAttribute("player", player);
		return mapping.findForward("attackattribute");
	}

	/**
	 * 杀怪人物装备
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		EquipOnBody equip_on_body = roleInfo.getEquipOnBody();
		
		request.setAttribute("partEquipVO1", equip_on_body.getByPositin(PositionOnEquip.WEAPON));
		request.setAttribute("partEquipVO2", equip_on_body.getByPositin(PositionOnEquip.HAT));
		request.setAttribute("partEquipVO3", equip_on_body.getByPositin(PositionOnEquip.CLOTHING));
		request.setAttribute("partEquipVO4", equip_on_body.getByPositin(PositionOnEquip.TROUSERS));
		request.setAttribute("partEquipVO5", equip_on_body.getByPositin(PositionOnEquip.SHOES));
		request.setAttribute("partEquipVO6", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_1));
		request.setAttribute("partEquipVO7", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_2));
		request.setAttribute("partEquipVO8", equip_on_body.getByPositin(PositionOnEquip.JEWELRY_3));
		return mapping.findForward("attackaccouter");
	}

	/**
	 * 角色更换装备List
	 */
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String position = request.getParameter("position");
		String page_no = request.getParameter("page_no");
		if( page_no==null )
		{
			page_no = "1";
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		GoodsService goodsService = new GoodsService();
		int equipType = PositionOnEquip.getEquipByPosotion(Integer.parseInt(position));
		QueryPage equip_page = goodsService.getPageByTypeOnWrap(roleInfo.getBasicInfo().getPPk(), equipType,Integer.parseInt(page_no));
		request.setAttribute("equip_page", equip_page);
		request.setAttribute("position", position);
		request.setAttribute("page_no", page_no);
		return mapping.findForward("replacinglist");
	}

	/**
	 * 角色更换穿装备
	 */
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String pwPk = request.getParameter("pwPk");
		String position = request.getParameter("position");

		EquipService equipService = new EquipService();
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
		String hint = equipService.puton(roleInfo,equip,Integer.parseInt(position));
		if (hint != null)
		{
			request.setAttribute("hint", hint+"<br/>");
			return n10(mapping, form, request, response);
		}
		return mapping.findForward("replacingupdate");
	}

	/**
	 * 人物装备查看
	 */
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		GoodsService goodsService = new GoodsService();
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String pwPk = request.getParameter("pwPk");
		String position = request.getParameter("position");
		PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));

		String equip_display = equipDisplayService.getEquipDisplay(roleInfo,equip,false);

		request.setAttribute("equip_display", equip_display);
		request.setAttribute("position", position);
		request.setAttribute("pwPk", pwPk);
		return mapping.findForward("replacingview");
	}

	/**
	 * 卸下人物装备
	 */
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		EquipService equipService = new EquipService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());

		String pwPk = request.getParameter("pwPk");
		String position = request.getParameter("position");
		
		String hint = equipService.takeoff(roleInfo, Integer.parseInt(pwPk),Integer.parseInt(position));
		request.setAttribute("hint", hint);
		return n3(mapping, form, request, response);
	}

	/**
	 * 祝福列表
	 */
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		BuffEffectService buffEffectService = new BuffEffectService();

		String buff_list_describe = buffEffectService.getBuffListWml(roleInfo
				.getBasicInfo().getPPk());

		request.setAttribute("buff_list_describe", buff_list_describe);
		return mapping.findForward("buff_list");
	}

	/** 人物装备可装备道具 */
	public ActionForward n18(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		int p_level = roleInfo.getBasicInfo().getGrade();

		int pg_pk = Integer.parseInt(request.getParameter("pg_pk").toString());
		String wupinlan = request.getParameter("wupinlan");
		SpecialPropService sps = new SpecialPropService();
		PlayerPropGroupDao pdao = new PlayerPropGroupDao();
		PlayerPropGroupVO pvo = pdao.getByPgPk(pg_pk);
		PropVO ppvo = PropCache.getPropById(pvo.getPropId());
		int sex = ppvo.getPropSex();
		if(sex != 0 && roleInfo.getBasicInfo().getSex() != sex){
			request.setAttribute("wupinlan", wupinlan);
			request.setAttribute("display", "您的性别不符");
			return mapping.findForward("prop_display");
		}
		String hint = sps.getEquipItemOff(p_pk);
		if (hint == null)
		{
			int x = sps.getEquipItemOn(p_pk, pg_pk, p_level);

			if (x == 0)
			{
				request.setAttribute("wupinlan", wupinlan);
				request.setAttribute("display", "您的等级不够");
				return mapping.findForward("prop_display");
			}
			else
			{
				request.setAttribute("wupinlan", wupinlan);
				request.setAttribute("display", "道具已装备");
				return mapping.findForward("prop_display");
			}

		}
		else
		{
			request.setAttribute("display", hint);
			request.setAttribute("wupinlan", wupinlan);
			return mapping.findForward("prop_display");
		}
	}

	/** 人物卸下装备类道具 */
	public ActionForward n19(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());

		SpecialPropService pus = new SpecialPropService();
		String hint = pus.getEquipItemOff(roleInfo.getBasicInfo().getPPk());
		request.setAttribute("hint", hint);
		return n3(mapping, form, request, response);
	}

	/** 查看装备类道具详情 */
	public ActionForward n20(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		int prop_id = Integer.parseInt(request.getParameter("prop_id")
				.toString());
		String wupinlan = request.getParameter("wupinlan");
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipProp(p_pk);
		GoodsService goodsService = new GoodsService();
		String display = goodsService.getEquipPropInfoWml(p_pk, prop_id, "2",
				vo.getPropoperate3(), "41", null, wupinlan,request, response);
		request.setAttribute("wupinlan", wupinlan);
		request.setAttribute("display", display);
		return mapping.findForward("prop_display");
	}

	/** 得到装备类道具的列表 */
	public ActionForward n21(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();

		String wupinlan = request.getParameter("wupinlan");
		int thispage;
		if (request.getParameter("thispage") == null)
		{
			thispage = 1;
		}
		else
		{
			String page_no = request.getParameter("thispage");
			thispage = Integer.parseInt(page_no);
		}
		SpecialPropService pus = new SpecialPropService();
		int num = pus.getPlayerEquipNum(p_pk).size();
		int pagenum = num / 7 + (num % 7 == 0 ? 0 : 1);
		List<PlayerPropGroupVO> propGroupVO = pus.getPlayerEquipList(p_pk,
				thispage, 7);
		request.setAttribute("thispage", thispage);
		request.setAttribute("pagenum", pagenum);
		request.setAttribute("propList", propGroupVO);
		request.setAttribute("wupinlan", wupinlan);
		return mapping.findForward("prop_list");
	}

	/** 给玩家装备类道具的判断 */
	public ActionForward n22(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		int p_level = roleInfo.getBasicInfo().getGrade();
		int pg_pk = Integer.parseInt(request.getParameter("pg_pk").toString());
		String wupinlan = request.getParameter("wupinlan");
		SpecialPropService sps = new SpecialPropService();
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipPropByPgpk(String.valueOf(pg_pk));
		if (vo == null)
		{
			request.setAttribute("wupinlan", wupinlan);
			request.setAttribute("pg_pk", pg_pk);
			return mapping.findForward("prop_submit");
		}
		else
		{
			if (vo.getSign() == 3)
			{
				request.setAttribute("wupinlan", wupinlan);
				request.setAttribute("display", "该道具的使用时间已到,不能再使用!");
				return mapping.findForward("prop_display");
			}
			else
			{
				String hint = sps.getEquipItemOff(p_pk);
				if (hint == null)
				{
					int x = sps.getEquipItemOn(p_pk, pg_pk, p_level);

					if (x == 0)
					{
						request.setAttribute("wupinlan", wupinlan);
						request.setAttribute("display", "您的等级不够");
						return mapping.findForward("prop_display");
					}
					else
					{
						request.setAttribute("wupinlan", wupinlan);
						request.setAttribute("display", "道具已装备");
						return mapping.findForward("prop_display");
					}
				}
				else
				{
					request.setAttribute("display", hint);
					request.setAttribute("wupinlan", wupinlan);
					return mapping.findForward("prop_display");
				}
			}
		}
	}

	/** 查看别人宠物状态 */
	public ActionForward n23(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String other_pk = request.getParameter("pPks");

		RoleService roleService = new RoleService();
		
		RoleEntity other_role = roleService.getRoleInfoById(other_pk);
		
		PetService ps = new PetService();
		PetInfoVO vo = other_role.getRolePetInfo().getBringPet();
		String petWml = null;
		if (vo != null)
		{
			petWml = ps.getOtherPetDisplayWml(vo.getPetPk());
		}
		request.setAttribute("pPks", other_pk);
		request.setAttribute("petWml", petWml);
		return mapping.findForward("petview");
	}
	
	/**
	 * 查看自己屬性
	 */
	public ActionForward n24(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		PlayerService playerService = new PlayerService();
		PicService picService = new PicService();
		
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int p_pk = roleInfo.getBasicInfo().getPPk();
		int evil_value = roleInfo.getBasicInfo().getEvilValue();
		
		PartInfoVO player = playerService.getPlayerByPpk(p_pk);
		// 获取人物图片
		String playerPic = picService.getPlayerPicStr(roleInfo, roleInfo.getBasicInfo().getPPk());
		
		int glory_value = roleInfo.getBasicInfo().getFContribute();//帮派贡献
		
		request.setAttribute("pk_value", evil_value);
		request.setAttribute("player", player);
		request.setAttribute("roleInfo", roleInfo);
		request.setAttribute("glory_value", glory_value + "");
		request.setAttribute("playerPic", playerPic);
		
		return mapping.findForward("shuxing");
	}
}
