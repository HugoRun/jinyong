package com.ls.web.action.mounts;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.vo.friend.FriendVO;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.vo.info.partinfo.TimeControlVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.ben.vo.pkhite.PKHiteVO;
import com.ls.model.group.GroupModel;
import com.ls.model.organize.faction.Faction;
import com.ls.model.user.MountSet;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.DateUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.faction.FactionService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.pk.PKHiteService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.pm.service.chuansong.SuiBianChuanService;
import com.pm.vo.chuansong.SuiBianChuanVO;
import com.web.service.friend.FriendService;
/**
 * 处理坐骑action
 * @author Thomas.lei
 */
public class MountsAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	//得到场景传送信息
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		//1妖族2巫族两大种族类型prace
		SuiBianChuanService scs=new SuiBianChuanService();
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),1);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("scencelist");
	}
	//得到练级传送信息
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		SuiBianChuanService scs=new SuiBianChuanService();
		//类型4为练级区域
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),2);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("lianjilist");
	}
	//得到BOSS传送的信息
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		SuiBianChuanService scs=new SuiBianChuanService();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		//类型8为BOSS区域
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),3);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("bosslist");
	}
	//得到副本传送信息
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		SuiBianChuanService scs=new SuiBianChuanService();
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		//类型5为副本区域
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),4);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("fubenlist");
	}
	//得到队友传送信息
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		GroupModel group_info = roleInfo.getStateInfo().getGroupInfo();
		
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		request.setAttribute("group_info",group_info);
		return mapping.findForward("duiyoulist");
	}
	//得到好友的传送信息
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request
				.getSession());
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
		// 查询在线玩家
		friendlist = friendService.listfriend(roleInfo
				.getBasicInfo().getPPk(), page * queryPage.getPageSize(), queryPage.getPageSize());
		friendService.getFriendInMapName(friendlist);
		}
		request.setAttribute("pageall", pageall);
		request.setAttribute("page", page);
		request.setAttribute("friendlist", friendlist);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("friendlist");
	}
	//得到仇敌的传送信息
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		String index=request.getParameter("index");
		String pPk = (String) request.getSession().getAttribute("pPk");
		if(index==null)
		{
			index="0";
		}
		else
		{
			int temp=Integer.parseInt(index)-1;
			index=temp+"";
		}
		PKHiteService ps=new PKHiteService();
		List<PKHiteVO> list= ps.getEnemys(Integer.parseInt(pPk), Integer.parseInt(index), 5);
		QueryPage qp=new QueryPage(5*Integer.parseInt(index),ps.getRecordNum(Integer.parseInt(pPk)),5,list);
		request.setAttribute("queryPage",qp);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("enemylist");
	}
	
	//得到氏族成员的信息
	public ActionForward n18(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		String mountState=(String) request.getParameter("mountState");
		String carryGrade=(String)request.getParameter("carryGrade");
		if(carryGrade!=null)
		{
			request.getSession().setAttribute("carryGrade",(String)request.getParameter("carryGrade"));
		}
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		String message="";
		if(faction==null)
		{
			message="您还没有氏族信息";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getMemListPageList(faction.getId(), this.getPageNo(request));
		item_page.setURL(response, "/mounts.do?cmd=n18");
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		request.setAttribute("item_page", item_page);
		return mapping.findForward("factionlist");
	}
	//处理场景传送和处理练级传送和处理好友传送和处理队友传送处理BOSS传送和副本传送和仇敌追杀
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String message="";
		RoleService roleService = new RoleService();
		String mountsID=request.getParameter("mountsID");
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		UserMountsVO umv= roleInfo.getMountSet().getCurMount();
		String carryGradeStr=(String)request.getSession().getAttribute("carryGrade");
		int carryGrade=carryGradeStr==null?Integer.parseInt(request.getParameter("carryGrade")):Integer.parseInt(carryGradeStr);
		if(umv==null||umv.getMountsLevle()==1||umv.getMountsLevle()<carryGrade||!(umv.getId()+"").equals(mountsID))
		{
			message="您还没有乘坐坐骑或者您的坐骑等级太低不能传送！";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		String scenceID=(String) request.getParameter("scenceID");
		String mountID=umv.getMountsID()+"";
		String mountState=umv.getMountsState()+"";
		if("0".equals(mountState))
		{
			message="不是乘骑状态，不能传送";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		MountsService ms=new MountsService();
		EconomyService es=new EconomyService();
		TimeControlService tcs=new TimeControlService();
		MountsVO mv= ms.getMountsInfo(Integer.parseInt(mountID));
		
		SceneVO new_scene = SceneCache.getById(scenceID);
		
		//不能传送到非自己种族主城
		String hint = new_scene.isEntered(roleInfo);
		if(hint!=null)
		{
			request.setAttribute("message",hint);
			return mapping.findForward("hint");
		}
		/*********红名传送要附加处理********/
		int needCopper=roleInfo.isRedname()?mv.getOverPay1()*2:mv.getOverPay1();
		int carryNum=roleInfo.isRedname()?mv.getCarryNum1()/2:mv.getCarryNum1();
		if(carryGrade==2)
		{
			if(mv.getLevel()==carryGrade)
			{
				boolean isUse=tcs.isUseable(roleInfo.getBasicInfo().getPPk(), mv.getId(), mv.getLevel(),carryNum);
				if(!isUse)
				{
					long haveCopper=roleInfo.getBasicInfo().getCopper();
					if(needCopper>haveCopper)
					{
						message="今日传送次数已满"+carryNum+"次，您灵石不足不能传送";
						request.setAttribute("message",message);
						return mapping.findForward("hint");
					}
					else
					{
						roleInfo.getBasicInfo().addCopper(-needCopper);
						request.setAttribute("mountsHint", "您的坐骑今日免费传送次数已满"+carryNum+"次扣除"+needCopper+"灵石");
					}
				}
				tcs.updateControlInfo(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			}
		}
		if(carryGrade==3)
		{
			if(mv.getLevel()==carryGrade)
			{
				boolean isUse=tcs.isUseable(roleInfo.getBasicInfo().getPPk(), mv.getId(), mv.getLevel(), carryNum);
				if(!isUse)
				{
					long haveCopper=es.getYuanbao(roleInfo.getUPk());
					boolean can=true;
					if(GameConfig.getChannelId()==Channel.TELE)
					{
						can=ms.mountCarryForTele(request, roleInfo, mv.getId()+"", mv.getLevel()+"");
					}
					else
					{
						can=needCopper>haveCopper?true:false;
					}
					if(can)
					{
						message="今日传送次数已满"+carryNum+"次需要扣除"+GameConfig.getYuanbaoName()+"，"+GameConfig.getYuanbaoName()+"不足请充值";
						request.setAttribute("message",message);
						return mapping.findForward("chongzhi");
					}
					else
					{
						if(GameConfig.getChannelId()!=Channel.TELE)
						{
							es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needCopper);
						}
						request.setAttribute("mountsHint", "您的坐骑今日免费传送次数已满"+carryNum+"次扣除"+needCopper+"仙晶");
					}
				}
				tcs.updateControlInfo(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			}
		}
		if(carryGrade==4)
		{
			if(mv.getLevel()==carryGrade)
			{
				boolean isUse=tcs.isUseable(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel(), carryNum);
				if(!isUse)
				{
					long haveCopper=es.getYuanbao(roleInfo.getUPk());
					boolean can=true;
					/**电信平台去平台扣费**/
					if(GameConfig.getChannelId()==Channel.TELE)
					{
						can=ms.mountCarryForTele(request, roleInfo, mv.getId()+"", mv.getLevel()+"");
					}
					else
					{
						can=needCopper>haveCopper?true:false;
					}
					if(can)
					{
						message="今日传送次数已满"+carryNum+"次需要扣除仙晶，仙晶不足请充值";
						request.setAttribute("message",message);
						return mapping.findForward("chongzhi");
					}
					else
					{
						if(GameConfig.getChannelId()!=Channel.TELE)
						{
							es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needCopper);
						}
						request.setAttribute("mountsHint", "您的坐骑今日免费传送次数已满"+carryNum+"次扣除"+needCopper+"仙晶");
					}
				}
				tcs.updateControlInfo(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			}
		}
		roleInfo.getBasicInfo().updateSceneId(scenceID);
		return mapping.findForward("refurbish_scene");
	}
	//处理坐骑升级
	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String confirm=(String) request.getParameter("confirm");
		String mountsID=(String) request.getParameter("mountsID");
		String nextLevelMountsIDS=(String) request.getParameter("nextLevelMountsID");
		String[] ids=nextLevelMountsIDS.split(",");
		Random rd=new Random();
		int randomNum= rd.nextInt(2);
		String nextLevelMountsID=ids[randomNum];
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MountsService ms=new MountsService();
		String message="";
		MountsVO mv= ms.getMountsInfo(Integer.parseInt(nextLevelMountsID));
		int num=0;
		if(confirm==null)
		{
			message="您的坐骑升级为"+mv.getLevel()+"级坐骑需花费"+mv.getUplevelPay()+GameConfig.getYuanbaoName()+",您确定升级吗？";
			request.setAttribute("message",message);
			request.setAttribute("mountsID",mountsID);
			request.setAttribute("nextLevelMountsID", nextLevelMountsIDS);
			return mapping.findForward("confirmup");
		}
		if(mv.getLevel()==5)
		{
			message="不可升级！5级坐骑功能尚未开放敬请留意官方通知";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		else
		{
			if(GameConfig.getChannelId()==Channel.TELE)
			{
				num=roleInfo.getMountSet().upgrade(request, Integer.parseInt(mountsID),Integer.parseInt(nextLevelMountsID));
			}
			else
			{
				num=roleInfo.getMountSet().upgrade(Integer.parseInt(mountsID), Integer.parseInt(nextLevelMountsID));
			}
		}
		if(num==1)
		{
			message="恭喜您，您的"+mv.getName()+"已升至"+mv.getLevel()+"级坐骑，"+mv.getFunctionDisplay()+"！";
			request.setAttribute("isUpLeve", "yes");
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		if(num==2)
		{
			message="您的"+GameConfig.getYuanbaoName()+"不足，请先充值";
			request.setAttribute("message",message);
			return mapping.findForward("chongzhi");
		}
		message="升级出错，请联系GM";
		request.setAttribute("message",message);
		return mapping.findForward("hint");
	}
	//处理取消乘骑
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		roleInfo.getMountSet().cancelCurMount();
		return n16(mapping,form,request,response);
	}
	//处理玩家点击换乘坐骑
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		roleInfo.getMountSet().changeCurMount(Integer.parseInt(mountID));
		return n16(mapping,form,request,response);
	}
	//处理玩家遗弃坐骑
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String confirm=(String) request.getParameter("confirm");
		String mountID=(String) request.getParameter("mountID");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MountsVO mv= roleInfo.getMountSet().getMounts(Integer.parseInt(mountID)).getMountInfo();
		String message="";
		if(confirm==null)
		{
			message="你确定遗弃【"+mv.getName()+"】吗？";
			request.setAttribute("message",message);
			request.setAttribute("mountID",mountID);
			return mapping.findForward("confirmdelete");
		}
		roleInfo.getMountSet().deleteMount(Integer.parseInt(mountID));
		return n16(mapping,form,request,response);
	}
	//处理玩家购买坐骑
	public ActionForward n13(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String confirm=(String) request.getParameter("confirm");
		String mountID=(String) request.getParameter("mountID");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MountsService ms=new MountsService();
		String message="";
		if(confirm==null)
		{
			MountsVO mv= ms.getMountsInfo(Integer.parseInt(mountID));
			int type=mv.getType();
			String strType="";
			if(type==1)
				strType="走兽类";
			if(type==2)
				strType="飞禽类";
			if(type==3)
				strType="鳞甲类";
			message=""+mv.getName()+"为"+strType+"，升至5级有可能进化为【玉麒麟】【白虎】等神兽，你确定要购买此坐骑吗?";
			request.setAttribute("message",message);
			request.setAttribute("mountID",mountID);
			return  mapping.findForward("confirmbuy");
		}
		int num=0;
		/****电信消费渠道***/
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			num=roleInfo.getMountSet().buyMounts(request, Integer.parseInt(mountID));
		}
		/****普通购买****/
		else
		{
			num=roleInfo.getMountSet().buyMounts(Integer.parseInt(mountID));
		}
		if(num==1)
		{
			MountsVO mv= ms.getMountsInfo(Integer.parseInt(mountID));
			message="恭喜您，您已成功拥有了"+mv.getLevel()+"级【"+mv.getName()+"】,祝您终将拥有【玉麒麟】【白虎】等神兽！";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		if(num==2)
		{
			message="您已经有此坐骑，不可再购买";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		if(num==3)
		{
			message="对不起您的"+GameConfig.getYuanbaoName()+"不足，请充值";
			request.setAttribute("message",message);
			return mapping.findForward("chongzhi");
		}
		message="购买出错，请联系GM";
		request.setAttribute("message",message);
		return mapping.findForward("hint");
	}
	//处理得到可以出售的坐骑
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		MountsService ms=new MountsService();
		List<MountsVO> list=ms.getCanSentMounts();
		request.setAttribute("list",list);
		return mapping.findForward("buymounts");
	}
	//处理进入坐骑页面
	public ActionForward n15(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		UserMountsVO uv=roleInfo.getMountSet().getCurMount();
		if(uv==null)
		{
			List<UserMountsVO>  list=roleInfo.getMountSet().getUserMountsList();
			request.setAttribute("list",list);
			return mapping.findForward("mountslist");
		}
		else
		{
			MountsVO mv=uv.getMountInfo();
			TimeControlService tcs=new TimeControlService();
			TimeControlVO tcv= tcs.getControlObject(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			int useNum=0;
			if(tcv!=null)
			{
				if(DateUtil.isSameDay(tcv.getUseDatetime()))
				{
					useNum= tcs.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
				}
			}
			request.setAttribute("useNum", useNum+"");
			request.setAttribute("mountsVO",uv);
			request.setAttribute("mountState", uv.getMountsState()+"");
			return mapping.findForward("mountsinfo");
		}
	}
	//处理得到玩家现在有得坐骑
	public ActionForward n16(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String args=(String) request.getParameter("args");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		List<UserMountsVO> list= roleInfo.getMountSet().getUserMountsList();
		request.setAttribute("list",list);
		if("up".equals(args))
		{
			return mapping.findForward("mountsup");
		}
		return mapping.findForward("mountslist");
		
	}
	//更具坐骑的ID得到坐骑的详细信息
	public ActionForward n17(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountsID=(String) request.getParameter("mountsID");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		MountsService ms=new MountsService();
		UserMountsVO uv=roleInfo.getMountSet().getMounts(Integer.parseInt(mountsID));
		MountsVO mv= ms.getMountsInfo(uv.getMountsID());
		TimeControlService tcs=new TimeControlService();
		TimeControlVO tcv= tcs.getControlObject(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
		int useNum=0;
		if(tcv!=null)
		{
			if(DateUtil.isSameDay(tcv.getUseDatetime()))
			{
				useNum= tcs.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			}
		}
		String mountState="";
		if(uv!=null&&(mv.getId()==uv.getMountsID()) )
		{
			mountState="1";
		}
		else
		{
			mountState="0";
		}
		request.setAttribute("useNum", useNum+"");
		request.setAttribute("mountsVO",uv);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("mountinfo");
	}
	//得到适合当前等级玩家练级的练级地点
	public ActionForward n19(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String mountID=roleInfo.getMountSet().getCurMId()+"";
		MountsService ms=new MountsService();
		List<SuiBianChuanVO> list=ms.getCurLianji(roleInfo.getBasicInfo().getPRace(),roleInfo.getBasicInfo().getGrade());
		String lianji= ms.getStrForLianji(list, roleInfo.getBasicInfo().getGrade(),mountID);
		request.setAttribute("lianji", lianji);
		return mapping.findForward("lianji");
	}
	//得到指定npc的信息以及掉落信息
	public ActionForward n20(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String npcId=request.getParameter("npcId");
		MountsService ms=new MountsService();
		String npcStr= ms.getStrForNpc(Integer.parseInt(npcId),roleInfo.getSettingInfo().getNpcPic());
		request.setAttribute("npcStr",npcStr);
		return mapping.findForward("npcinfo");
	}
	//得到指定npc所掉落物品的详细信息
	public ActionForward n21(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String goodId=request.getParameter("goodId");
		String goodType=request.getParameter("goodType");
		MountsService ms=new MountsService();
		String npcDropStr=ms.getStrForNpcDrop(Integer.parseInt(goodId),Integer.parseInt(goodType));
		request.setAttribute("npcStr",npcDropStr);
		return mapping.findForward("npcinfo");
	}
	
}
