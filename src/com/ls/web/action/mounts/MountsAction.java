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
 * ��������action
 * @author Thomas.lei
 */
public class MountsAction extends ActionBase
{
	Logger logger = Logger.getLogger("log.action");
	//�õ�����������Ϣ
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
		
		//1����2����������������prace
		SuiBianChuanService scs=new SuiBianChuanService();
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),1);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("scencelist");
	}
	//�õ�����������Ϣ
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
		//����4Ϊ��������
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),2);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("lianjilist");
	}
	//�õ�BOSS���͵���Ϣ
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
		//����8ΪBOSS����
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),3);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("bosslist");
	}
	//�õ�����������Ϣ
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
		//����5Ϊ��������
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(roleInfo.getBasicInfo().getPRace(),4);
		request.setAttribute("list",list);
		request.setAttribute("mountID", mountID);
		request.setAttribute("mountState",mountState);
		return mapping.findForward("fubenlist");
	}
	//�õ����Ѵ�����Ϣ
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
	//�õ����ѵĴ�����Ϣ
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
		// ��ѯ�������
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
	//�õ���еĴ�����Ϣ
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
	
	//�õ������Ա����Ϣ
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
			message="����û��������Ϣ";
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
	//���������ͺʹ����������ͺʹ�����Ѵ��ͺʹ�����Ѵ��ʹ���BOSS���ͺ͸������ͺͳ��׷ɱ
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
			message="����û�г������������������ȼ�̫�Ͳ��ܴ��ͣ�";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		String scenceID=(String) request.getParameter("scenceID");
		String mountID=umv.getMountsID()+"";
		String mountState=umv.getMountsState()+"";
		if("0".equals(mountState))
		{
			message="���ǳ���״̬�����ܴ���";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		MountsService ms=new MountsService();
		EconomyService es=new EconomyService();
		TimeControlService tcs=new TimeControlService();
		MountsVO mv= ms.getMountsInfo(Integer.parseInt(mountID));
		
		SceneVO new_scene = SceneCache.getById(scenceID);
		
		//���ܴ��͵����Լ���������
		String hint = new_scene.isEntered(roleInfo);
		if(hint!=null)
		{
			request.setAttribute("message",hint);
			return mapping.findForward("hint");
		}
		/*********��������Ҫ���Ӵ���********/
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
						message="���մ��ʹ�������"+carryNum+"�Σ�����ʯ���㲻�ܴ���";
						request.setAttribute("message",message);
						return mapping.findForward("hint");
					}
					else
					{
						roleInfo.getBasicInfo().addCopper(-needCopper);
						request.setAttribute("mountsHint", "�������������Ѵ��ʹ�������"+carryNum+"�ο۳�"+needCopper+"��ʯ");
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
						message="���մ��ʹ�������"+carryNum+"����Ҫ�۳�"+GameConfig.getYuanbaoName()+"��"+GameConfig.getYuanbaoName()+"�������ֵ";
						request.setAttribute("message",message);
						return mapping.findForward("chongzhi");
					}
					else
					{
						if(GameConfig.getChannelId()!=Channel.TELE)
						{
							es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needCopper);
						}
						request.setAttribute("mountsHint", "�������������Ѵ��ʹ�������"+carryNum+"�ο۳�"+needCopper+"�ɾ�");
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
					/**����ƽ̨ȥƽ̨�۷�**/
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
						message="���մ��ʹ�������"+carryNum+"����Ҫ�۳��ɾ����ɾ��������ֵ";
						request.setAttribute("message",message);
						return mapping.findForward("chongzhi");
					}
					else
					{
						if(GameConfig.getChannelId()!=Channel.TELE)
						{
							es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needCopper);
						}
						request.setAttribute("mountsHint", "�������������Ѵ��ʹ�������"+carryNum+"�ο۳�"+needCopper+"�ɾ�");
					}
				}
				tcs.updateControlInfo(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			}
		}
		roleInfo.getBasicInfo().updateSceneId(scenceID);
		return mapping.findForward("refurbish_scene");
	}
	//������������
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
			message="������������Ϊ"+mv.getLevel()+"�������軨��"+mv.getUplevelPay()+GameConfig.getYuanbaoName()+",��ȷ��������";
			request.setAttribute("message",message);
			request.setAttribute("mountsID",mountsID);
			request.setAttribute("nextLevelMountsID", nextLevelMountsIDS);
			return mapping.findForward("confirmup");
		}
		if(mv.getLevel()==5)
		{
			message="����������5�����﹦����δ���ž�������ٷ�֪ͨ";
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
			message="��ϲ��������"+mv.getName()+"������"+mv.getLevel()+"�����"+mv.getFunctionDisplay()+"��";
			request.setAttribute("isUpLeve", "yes");
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		if(num==2)
		{
			message="����"+GameConfig.getYuanbaoName()+"���㣬���ȳ�ֵ";
			request.setAttribute("message",message);
			return mapping.findForward("chongzhi");
		}
		message="������������ϵGM";
		request.setAttribute("message",message);
		return mapping.findForward("hint");
	}
	//����ȡ������
	public ActionForward n10(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		roleInfo.getMountSet().cancelCurMount();
		return n16(mapping,form,request,response);
	}
	//������ҵ����������
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mountID=(String) request.getParameter("mountID");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		roleInfo.getMountSet().changeCurMount(Integer.parseInt(mountID));
		return n16(mapping,form,request,response);
	}
	//���������������
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
			message="��ȷ��������"+mv.getName()+"����";
			request.setAttribute("message",message);
			request.setAttribute("mountID",mountID);
			return mapping.findForward("confirmdelete");
		}
		roleInfo.getMountSet().deleteMount(Integer.parseInt(mountID));
		return n16(mapping,form,request,response);
	}
	//������ҹ�������
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
				strType="������";
			if(type==2)
				strType="������";
			if(type==3)
				strType="�ۼ���";
			message=""+mv.getName()+"Ϊ"+strType+"������5���п��ܽ���Ϊ�������롿���׻��������ޣ���ȷ��Ҫ�����������?";
			request.setAttribute("message",message);
			request.setAttribute("mountID",mountID);
			return  mapping.findForward("confirmbuy");
		}
		int num=0;
		/****������������***/
		if(GameConfig.getChannelId()==Channel.TELE)
		{
			num=roleInfo.getMountSet().buyMounts(request, Integer.parseInt(mountID));
		}
		/****��ͨ����****/
		else
		{
			num=roleInfo.getMountSet().buyMounts(Integer.parseInt(mountID));
		}
		if(num==1)
		{
			MountsVO mv= ms.getMountsInfo(Integer.parseInt(mountID));
			message="��ϲ�������ѳɹ�ӵ����"+mv.getLevel()+"����"+mv.getName()+"��,ף���ս�ӵ�С������롿���׻��������ޣ�";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		if(num==2)
		{
			message="���Ѿ��д���������ٹ���";
			request.setAttribute("message",message);
			return mapping.findForward("hint");
		}
		if(num==3)
		{
			message="�Բ�������"+GameConfig.getYuanbaoName()+"���㣬���ֵ";
			request.setAttribute("message",message);
			return mapping.findForward("chongzhi");
		}
		message="�����������ϵGM";
		request.setAttribute("message",message);
		return mapping.findForward("hint");
	}
	//����õ����Գ��۵�����
	public ActionForward n14(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		MountsService ms=new MountsService();
		List<MountsVO> list=ms.getCanSentMounts();
		request.setAttribute("list",list);
		return mapping.findForward("buymounts");
	}
	//�����������ҳ��
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
	//����õ���������е�����
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
	//���������ID�õ��������ϸ��Ϣ
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
	//�õ��ʺϵ�ǰ�ȼ���������������ص�
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
	//�õ�ָ��npc����Ϣ�Լ�������Ϣ
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
	//�õ�ָ��npc��������Ʒ����ϸ��Ϣ
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
