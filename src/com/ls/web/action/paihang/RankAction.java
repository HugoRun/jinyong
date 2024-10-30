package com.ls.web.action.paihang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.rank.model.RankConstants;
import com.ben.rank.model.RankVo;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.dao.faction.FactionDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.mounts.MountsDao;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.organize.faction.Faction;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.web.action.menu.BaseAction;
import com.ls.web.service.faction.FactionService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipDisplayService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.pet.PetService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;

public class RankAction extends BaseAction
{
	private RankService rankService = new RankService();

	// 具体排名
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String field = request.getParameter("field");
			if (field == null || "".equals(field.trim()))
			{
				setMessage(request, "出错了");
				return mapping.findForward(ERROR);
			}
			field = field.trim();
			
			setAttribute(request, "field", field);
			setAttribute(request, "detail", RankConstants.DETAIL.get(field));
			setAttribute(request, "first_des", RankConstants.FIRST_DES.get(field));
			
			if( field.indexOf("r_")!=-1 )
			{
				return this.role(mapping, form, request, response);
			}
			else if( field.indexOf("e_")!=-1 )
			{
				return this.equip(mapping, form, request, response);
			}
			else if( field.indexOf("f_")!=-1 )
			{
				return this.faction(mapping, form, request, response);
			}

			return mapping.findForward("detail");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}
	
	/**
	 * 角色排行榜
	 */
	private ActionForward role(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			int p_pk = getP_Pk(request);
			String field = request.getParameter("field");
			int type = RankConstants.FILED_TYPE.get(field);
			
			// 查询VIP特殊处理
			if ( field.equals(RankConstants.VIP))
			{
				List<RankVo> list = rankService.findVip();
				setAttribute(request, "list", list);
				int paimin = rankService.findOwnVIP(p_pk);
				setAttribute(request, "paimin", paimin + "");
			}
			// 威望暂时不做
			else if (field.equals(RankConstants.WEI_TASK))
			{
			}
			// 甜蜜
			else if ( field.equals(RankConstants.DEAR) )
			{
				List<RankVo> list = rankService.findDear();
				if (list != null)
				{
					List<RankVo> copyList = copyList(list);
					Set<String> set = new TreeSet<String>();
					for (RankVo rank : copyList)
					{
						if (set.contains(rank.getP_name().trim()) || set.contains(rank.getTong1() == null ? "": rank.getTong1().trim()))
						{
							list.remove(rank);
						}
						else
						{
							set.add(rank.getP_name().trim());
							set.add(rank.getTong1() == null ? "": rank.getTong1().trim());
						}
					}
				}
				setAttribute(request, "list", list);
				if (list == null || list.size() == 0)
				{
					setAttribute(request, "paimin", 0 + "");
				}
				else
				{
					int paimin = rankService.findOwnByField(p_pk,field, type);
					setAttribute(request, "paimin",paimin == 1 ? (paimin + "") : (paimin / 2+ paimin % 2 + ""));
				}
			}
			// 义气
			else if (field.equals(RankConstants.YI))
			{
				List<FriendVO> list = rankService.paiFriend(1);
				if (list != null)
				{
					List<FriendVO> copyList = copyListFriendVO(list);
					Map<Integer, Integer> map = new HashMap<Integer, Integer>();
					for (FriendVO rank : copyList)
					{
						if ((map.containsKey(rank.getPPk()) && map.get(rank.getPPk()).equals(rank.getFdPk()))
								|| (map.containsKey(rank.getFdPk()) && map.get(rank.getFdPk()).equals(rank.getPPk())))
						{
							list.remove(rank);
						}
						else
						{
							map.put(rank.getPPk(), rank.getFdPk());
							map.put(rank.getFdPk(), rank.getPPk());
						}
					}
				}
				List<RankVo> list1 = new ArrayList<RankVo>();
				RoleService rs = new RoleService();
				for (FriendVO fv : list)
				{
					RankVo rankvo = new RankVo();
					rankvo.setP_name(rs.getName(fv.getPPk() + "")[0]);
					rankvo.setP_pk(fv.getPPk());
					rankvo.setTong(fv.getDear());
					rankvo.setTong1(fv.getFdName());
					list1.add(rankvo);
				}
				setAttribute(request, "list", list1);
				if (list == null || list.size() == 0)
				{
					setAttribute(request, "paimin", 0 + "");
				}
				else
				{
					int paimin = rankService.getOwn(p_pk, 1);
					setAttribute(request, "paimin",paimin == 1 ? (paimin + "") : (paimin/ 2 + paimin % 2 + ""));
				}
			}
			// 江湖圣榜
			else if (field.equals(RankConstants.SHENG))
			{
				List<RankVo> list = rankService.findSheng();
				setAttribute(request, "list", list);
				if (list == null || list.size() == 0)
				{
					setAttribute(request, "paimin", "0");
				}
				else
				{
					int paimin = rankService.findOwnSheng(p_pk);
					setAttribute(request, "paimin",paimin + "");
				}
			}
			else
			{
				List<RankVo> list = rankService.findByField(field, type);
				setAttribute(request, "list", list);
				if (list == null || list.size() == 0)
				{
					setAttribute(request, "paimin", "0");
				}
				else
				{
					int paimin = rankService.findOwnByField(p_pk,field, type);
					setAttribute(request, "paimin",paimin + "");
				}
			}

			return mapping.findForward("detail");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}
	
	/**
	 * 装备排行榜
	 */
	private ActionForward equip(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String field = request.getParameter("field");
			
			List equip_list = null;
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			MountsDao md=new MountsDao();
			if ( field.equals(RankConstants.SHENBING) )
			{
				equip_list =playerEquipDao.getRankList(Equip.WEAPON);
			}
			else if( field.equals(RankConstants.XIANJIA) )
			{
				equip_list =playerEquipDao.getRankList(Equip.HAT,Equip.CLOTHING,Equip.TROUSERS,Equip.SHOES);
			}
			else if( field.equals(RankConstants.FABAO) )
			{
				equip_list =playerEquipDao.getRankList(Equip.JEWELRY);
			}
			//坐骑榜
			else if( field.equals(RankConstants.ZUOQI)  )
			{	
				equip_list=md.getMountsRank();
			}
			request.setAttribute("equip_list", equip_list);
			return mapping.findForward("arm_ranking");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}
	
	/**
	 * 帮派声望榜
	 * @param list
	 * @return
	 */
	private ActionForward faction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String field = request.getParameter("field");
		
		List<Faction> faction_list = null;
		
		FactionDao factionDao = new FactionDao();
		
		//帮派声望榜
		if ( field.equals(RankConstants.F_PRESTIGE) )
		{
			faction_list = factionDao.getPrestigeRank();
		}
		//帮派战力榜
		else if ( field.equals(RankConstants.F_ZHANLI) )
		{
			faction_list = factionDao.getZhanliRank();
		}
		//帮派财富榜
		else if ( field.equals(RankConstants.F_RICH) )
		{
			faction_list = factionDao.getRichRank();
		}
		
		request.setAttribute("faction_list", faction_list);
		return mapping.findForward("faction_ranking_list");
	}


	// 查看个人信息
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String field = request.getParameter("field");
			int type = 0;
			if (field != null)
			{
				int filed_type = RankConstants.FILED_TYPE.get(field.trim());
				// 个人
				if (filed_type == 22 || filed_type == 1 || filed_type == 6
						|| filed_type == 7 || filed_type == 2
						|| filed_type == 9 || filed_type == 10
						|| filed_type == 21 || filed_type == 24
						|| filed_type == 26 || filed_type == 29 || filed_type == 30 )
				{
					type = 1;
				}
				else
					if (filed_type == 11 || filed_type == 12 || filed_type == 4
							|| filed_type == 14 || filed_type == 15
							|| filed_type == 20)
					{
						type = 2;
					}
					else
						if (filed_type == 27 || filed_type == 28)
						{
							type = 4;
						}
						else
						{
							type = 3;
						}
			}
			setAttribute(request, "type", type + "");
			return mapping.findForward(INDEX);
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}

	

	// 查看个人信息
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String fd_pk = request.getParameter("fd_pk");
			if (fd_pk == null || "".equals(fd_pk.trim()))
			{
				setMessage(request, "出错了");
				return mapping.findForward(ERROR);
			}
			RoleEntity roleInfo = RoleService.getRoleInfoById(fd_pk);
			request.setAttribute("other", roleInfo);
			request.setAttribute("field", request.getParameter("field"));
			return mapping.findForward("partinfoview");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}

	// 查看装备属性
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String field=request.getParameter("field");
		String pwPk = request.getParameter("pwPk");
		String display="";
		try
		{
			if(!field.equals(RankConstants.ZUOQI))
			{
				GoodsService goodsService = new GoodsService();
				EquipDisplayService es = new EquipDisplayService();

				RoleEntity roleInfo = this.getRoleEntity(request);
				PlayerEquipVO equip = goodsService.getEquipByID(Integer.parseInt(pwPk));
				display = es.getEquipDisplay(roleInfo, equip, true);
			}
			else
			{
				int mountID=Integer.parseInt(pwPk);
				MountsService ms=new MountsService();
				display=ms.getMountsDisplay(mountID);
			}
			
			request.setAttribute("display", display);
			request.setAttribute("field", request.getParameter("field"));
			return mapping.findForward("arm_view");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}

	/**
	 * 查看帮派信息
	 */
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			/*
			 * TongRankingService tongRankingService = new TongRankingService();
			 * String tPk = request.getParameter("tpk"); RankingVO vo =
			 * tongRankingService.getTongRankingView(Integer .parseInt(tPk));
			 * request.setAttribute("rankingVO", vo);
			 */
			return mapping.findForward("faction_ranking_view");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}

	/**
	 * 申请加入帮派
	 */
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String fId = request.getParameter("fId");
			FactionService factionService = new FactionService();
			
			RoleEntity roleEntity = this.getRoleEntity(request);
			Faction faction = FactionService.getById(Integer.parseInt(fId));
			
			String hint = factionService.apply(roleEntity, faction);
			request.setAttribute("hint", hint);
			request.setAttribute("field", request.getParameter("field"));
			return mapping.findForward("faction_apply");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}

	// 宠物详细
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String pet_pk = request.getParameter("pet_pk");
			PetService petSerivce = new PetService();
			String resultWml = petSerivce.getPetDisplayWml(Integer.parseInt(pet_pk.trim()));
			request.setAttribute("pet_pk", pet_pk);
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("pet_info");
		}
		catch (Exception e)
		{
			setMessage(request, "出错了");
			return mapping.findForward(ERROR);
		}
	}
	private List<RankVo> copyList(List<RankVo> list)
	{
		if (list == null)
		{
			return new ArrayList<RankVo>();
		}
		else
		{
			return new ArrayList<RankVo>(list);
		}
	}

	private List<FriendVO> copyListFriendVO(List<FriendVO> list)
	{
		if (list == null)
		{
			return new ArrayList<FriendVO>();
		}
		else
		{
			return new ArrayList<FriendVO>(list);
		}
	}

}
