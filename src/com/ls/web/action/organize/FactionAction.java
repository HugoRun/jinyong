package com.ls.web.action.organize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.organize.faction.Faction;
import com.ls.model.organize.faction.game.FUpgradeMaterial;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.action.ActionBase;
import com.ls.web.service.faction.FactionService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.pm.service.mail.MailInfoService;

/**
 * @author ls
 * 帮派相关
 */
public class FactionAction extends ActionBase
{
	/**
	 * 帮派首页面
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction!=null )
		{
			request.setAttribute("faction", faction);
			return mapping.findForward("my_faction");
		}
		
		return mapping.findForward("faction_index");
	}
	
	/**
	 * 其他帮派列表
	 */
	public ActionForward otherFList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getPageList(super.getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=otherFList");
		request.setAttribute("item_page", item_page);
		
		request.setAttribute("pre", "otherFList");
		return mapping.findForward("other_faction_list");
	}
	
	/**
	 * 帮派信息
	 */
	public ActionForward des(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String fId = request.getParameter("fId");
		request.setAttribute("pre", request.getParameter("pre"));
		Faction faction = null ;
		if( fId==null )//自己的帮派信息
		{
			RoleEntity roleEntity = super.getRoleEntity(request);
			faction = roleEntity.getBasicInfo().getFaction();
			if( faction==null )
			{
				return this.index(mapping, form, request, response);
			}
			request.setAttribute("faction", faction);
			return mapping.findForward("faction_info");
		}
		else//查看别人的帮派信息
		{
			faction = FactionService.getById(Integer.parseInt(fId));
			request.setAttribute("faction", faction);
			return mapping.findForward("other_faction_info");
		}
		
		
	}
	
	
	//***********************帮派管理*****************************
	/**
	 * 帮派管理首页
	 */
	public ActionForward manageIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		request.setAttribute("faction", faction);
		return mapping.findForward("manage_index_"+roleEntity.getBasicInfo().getFJob());
	}
	
	//******************成员管理
	/**
	 * 删除成员提示
	 */
	public ActionForward delHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String mId = request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(mId);
		request.setAttribute("member", member);
		return mapping.findForward("del_member_hint");
	}
	/**
	 * 删除成员
	 */
	public ActionForward delMem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		String mId = request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(mId);
		
		FactionService factionService = new FactionService();
		String hint = factionService.delMember(roleEntity, member);
		this.setHint(request, hint);
		return mapping.findForward("del_member_end");
	}
	/**
	 * 帮派成员列表
	 */
	public ActionForward memList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		
		QueryPage item_page = factionService.getMemListPageList(faction.getId(), this.getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=memList");
		
		if(  roleEntity.getBasicInfo().getIsDelMember() )
		{
			request.setAttribute("del", "del");
		}
		
		request.setAttribute("item_page", item_page);
		request.setAttribute("faction", faction);
		request.setAttribute("roleEntity", roleEntity);
		request.setAttribute("backtype", "memList");
		return mapping.findForward("del_member_list");
	}
	
	//******************帮派升级
	/**
	 * 帮派升级提示
	 */
	public ActionForward upgradeHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		else
		{
			request.setAttribute("faction",faction);
		}
		
		FUpgradeMaterial material = faction.getUpgradeMaterial(FUpgradeMaterial.F_UPGRADE);
		if( material==null )
		{
			this.setHint(request, "氏族目前已达最高等级");
			return mapping.findForward("upgrade_end");
		}
		request.setAttribute("material", material);
		return mapping.findForward("upgrade_hint");
	}
	
	/**
	 * 帮派升级
	 */
	public ActionForward upgrade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		String hint = factionService.upgradeFaction(roleEntity);
		this.setHint(request, hint);
		return mapping.findForward("upgrade_end");
	}
	
	//******************祠堂升级
	/**
	 * 祠堂升级提示
	 */
	public ActionForward upgradeCTHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		else
		{
			request.setAttribute("faction",faction);
		}
		
		FUpgradeMaterial material = faction.getUpgradeMaterial(FUpgradeMaterial.C_UPGRADE);
		if( material==null )
		{
			this.setHint(request, "祠堂目前已达最高等级");
			return mapping.findForward("upgrade_end");
		}
		request.setAttribute("material", material);
		return mapping.findForward("upgrade_citang_hint");
	}
	
	/**
	 * 祠堂升级
	 */
	public ActionForward upgradeCT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		String hint = factionService.upgradeCitang(roleEntity);
		this.setHint(request, hint);
		return mapping.findForward("upgrade_end");
	}
	
	//*****************退出帮派
	/**
	 * 退出帮派
	 */
	public ActionForward exit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		faction.delMember(roleEntity);
		this.setHint(request, "您已成功退出了氏族!");
		return mapping.findForward("return_manage_hint");
	}
	/**
	 * 退出帮派确认页面
	 */
	public ActionForward exitHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		if( roleEntity.getBasicInfo().getFJob()==Faction.ZUZHANG)//如果是帮主
		{
			return mapping.findForward("zuzhang_exit_hint");
		}
		else
		{
			return mapping.findForward("exit_hint");
		}
	}
	
	
	//*****************解散帮派
	/**
	 * 解散帮派确认页面
	 */
	public ActionForward disbandHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		request.setAttribute("faction", faction);
		return mapping.findForward("disband_hint");
	}
	/**
	 * 解散帮派
	 */
	public ActionForward disband(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.disband(roleEntity, response);
		this.setHint(request, hint);
		return mapping.findForward("return_manage_hint");
	}
	/**
	 * 接管解散的氏族
	 */
	public ActionForward assume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		FactionService factionService = new FactionService();
		
		String hint = factionService.assume(roleEntity);
		this.setHint(request, hint);
		return mapping.findForward("return_manage_hint");
	}
	
	//*****************转让族长
	/**
	 * 长老列表
	 */
	public ActionForward zlList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getZhanglaoListPageList(faction.getId(), getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=zlList");
		
		if(  roleEntity.getBasicInfo().getIsChangeZuzhang() )
		{
			request.setAttribute("changeZZH", "changeZZH");
		}
		
		request.setAttribute("faction", faction);
		request.setAttribute("roleEntity", roleEntity);
		request.setAttribute("item_page", item_page);
		request.setAttribute("backtype", "zlList");
		return mapping.findForward("member_list");
	}
	/**
	 * 转让族长提示
	 */
	public ActionForward changeZZHHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		String mId = request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(mId);
		if( member==null )
		{
			this.setError(request, "无该角色");
			return this.zlList(mapping, form, request, response);
		}
		this.setHint(request, "您确定将族长一职转让给"+member.getName()+"吗？");
		request.setAttribute("faction", faction);
		request.setAttribute("mId", mId);
		return mapping.findForward("change_zuzhang_hint");
	}
	/**
	 * 转让族长
	 * @return
	 */
	public ActionForward changeZZH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		String mId = request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(mId);
		if( member==null )
		{
			this.setError(request, "无该角色");
			return this.zlList(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.changeZuzhang(roleEntity, member);
		this.setHint(request, hint);
		request.setAttribute("faction", faction);
		return mapping.findForward("change_zuzhang_end");
	}
	
	//*****************招募
	/**
	 * 输入招募信息
	 */
	public ActionForward inputRecruit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		return mapping.findForward("input_recruit");
	}
	/**
	 * 发布招募信息
	 */
	public ActionForward recruit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		request.setAttribute("faction", faction);
		FactionService factionService = new FactionService();
		String hint = factionService.recruit(roleEntity,request.getParameter("rInfo").trim());
		if( hint!=null )
		{
			this.setError(request, hint);
			return mapping.findForward("input_recruit");
		}
		else
		{
			this.setHint(request,"恭喜您,您的招募信息已经发出,请您敬候佳音!");
			return mapping.findForward("recruit_end");
		}
	}
	
	
	//****************修改称号
	/**
	 * 变更称号
	 */
	public ActionForward changeTitle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		request.setAttribute("mId", request.getParameter("mId"));
		request.setAttribute("faction", faction);
		
		if( roleEntity.getBasicInfo().isOperateByFJob(Faction.ZHANGLAO)==false )
		{
			this.setError(request, "权限不够");
			return mapping.findForward("input_title");
		}
		
		String fTitle = request.getParameter("fTitle");
		String hint = ValidateService.validateBasicInput(fTitle, 5);
		if( hint!=null )
		{
			this.setError(request, hint);
			return mapping.findForward("input_title");
		}
		
		String mId = request.getParameter("mId");
		
		RoleEntity member = RoleService.getRoleInfoById(mId);
		if( member==null )
		{
			this.setError(request, "该角色不存在");
			return this.titleMList(mapping, form, request, response);
		}
		member.getBasicInfo().changeFTitle(fTitle);
		this.setHint(request, "恭喜您，"+member.getName()+"的称号已更改为"+fTitle+"！");
		request.setAttribute("faction", faction);
		return mapping.findForward("change_title_end");
	}
	/**
	 * 变更称号提示
	 */
	public ActionForward changeTitleHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		request.setAttribute("mId", request.getParameter("mId"));
		request.setAttribute("faction", faction);
		
		String fTitle = request.getParameter("fTitle").trim();
		
		String hint = ValidateService.validateBasicInput(fTitle, 5);
		if( hint!=null )
		{
			this.setError(request, hint);
			return mapping.findForward("input_title");
		}
		RoleEntity member = RoleService.getRoleInfoById(request.getParameter("mId"));
		if( member==null )
		{
			this.setError(request, "该角色不存在");
			request.setAttribute("mId", request.getParameter("mId"));
			request.setAttribute("faction", faction);
			return this.titleMList(mapping, form, request, response);
		}
		this.setHint(request, "您确定要将"+member.getName()+"的称号更改为"+fTitle+"吗？");
		request.setAttribute("title", fTitle);
		return mapping.findForward("change_title_hint");
	}
	/**
	 * 输入称号
	 */
	public ActionForward inputTitle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		request.setAttribute("mId", request.getParameter("mId"));
		request.setAttribute("faction", faction);
		return mapping.findForward("input_title");
	}
	
	/**
	 * 修改称号成员列表
	 */
	public ActionForward titleMList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getMemListPageList(faction.getId(), getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=titleMList");
		
		if(  roleEntity.getBasicInfo().getIsManageApply() )
		{
			request.setAttribute("title", "title");
		}
		
		request.setAttribute("roleEntity", roleEntity);
		request.setAttribute("faction", faction);
		request.setAttribute("item_page", item_page);
		request.setAttribute("backtype", "titleMList");
		return mapping.findForward("member_list");
	}
	
	//***********更改职位
	/**
	 * 选择职位页面
	 */
	public ActionForward selectJobHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		request.setAttribute("mId", request.getParameter("mId"));
		request.setAttribute("basicInfo", roleEntity.getBasicInfo());
		request.setAttribute("faction", faction);
		return mapping.findForward("select_job");
	}
	/**
	 * 选择职位
	 */
	public ActionForward selectJob(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		String job_str = request.getParameter("job");
		if( job_str==null || Integer.parseInt(job_str)<Faction.ZUZHONG && Integer.parseInt(job_str)>=Faction.ZUZHANG )
		{
			this.setError(request, "非法职位");
			request.setAttribute("mId", request.getParameter("mId"));
			request.setAttribute("basicInfo", roleEntity.getBasicInfo());
			request.setAttribute("faction", faction);
			return mapping.findForward("select_job");
		}
		String pPk =   request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(pPk);
		if( member==null )
		{
			this.setError(request, "该角色不存在");
			return this.jobMList(mapping, form, request, response);
		}
		this.setHint(request, "您确定将"+member.getName()+"的职位变更为"+ExchangeUtil.getFJobName(Integer.parseInt(job_str))+"？");
		request.setAttribute("mId", pPk);
		request.setAttribute("job", job_str);
		request.setAttribute("faction", faction);
		return mapping.findForward("change_job_hint");
	}
	/**
	 * 改变职位
	 */
	public ActionForward changeJob(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		String job_str = request.getParameter("job");
		if( job_str==null || Integer.parseInt(job_str)<Faction.ZUZHONG && Integer.parseInt(job_str)>=Faction.ZUZHANG )
		{
			this.setError(request, "非法职位");
			request.setAttribute("mId", request.getParameter("mId"));
			request.setAttribute("basicInfo", roleEntity.getBasicInfo());
			request.setAttribute("faction", faction);
			return mapping.findForward("select_job");
		}
		String pPk =   request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(pPk);
		
		FactionService factionService = new FactionService();
		String hint = factionService.changeJob(roleEntity, member, Integer.parseInt(job_str));
		this.setHint(request, hint);
		request.setAttribute("faction", faction);
		return mapping.findForward("change_job_end");
	}
	/**
	 * 变更职位成员列表
	 */
	public ActionForward jobMList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getMemListPageList(faction.getId(), getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=jobMList");
		
		if(  roleEntity.getBasicInfo().getIsManageApply() )
		{
			request.setAttribute("job", "job");
		}
		
		request.setAttribute("faction", faction);
		request.setAttribute("roleEntity", roleEntity);
		request.setAttribute("item_page", item_page);
		request.setAttribute("backtype", "jobMList");
		return mapping.findForward("member_list");
	}
	
	//************邀请加入帮派
	/**
	 * 邀请入帮提示
	 */
	public ActionForward inviteHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		request.setAttribute("faction", faction);
		return mapping.findForward("invite_hint");
	}
	/**
	 * 邀请
	 */
	public ActionForward invite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		String invited_role_name = request.getParameter("roleName");
		
		FactionService factionService = new FactionService();
		String hint = factionService.invite(roleEntity, invited_role_name,response);
		if( hint!=null )
		{
			this.setError(request, hint);
		}
		request.setAttribute("faction", faction);
		return mapping.findForward("invite_hint");
	}
	/**
	 * 玩家对邀请的处理
	 */
	public ActionForward inviteResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String fId = request.getParameter("fId");
		String mailId = request.getParameter("mailId");
		String result = request.getParameter("result");
		
		RoleEntity roleEntity = super.getRoleEntity(request);
		
		Faction faction = FactionService.getById(Integer.parseInt(fId));
		if( result!=null )
		{
			if( result.equals("1"))
			{
				if( faction!=null )
				{
					String hint = faction.addMember( roleEntity);
					if( hint!=null )
					{
						this.setHint(request, hint);
					}
					else
					{
						this.setHint(request, "你已成功加入该氏族");
					}
				}
				else
				{
					this.setHint(request, "该氏族已解散");
				}
			}
			else
			{
				this.setHint(request, "你已拒绝加入该氏族");
			}
		}
		else
		{
			this.setHint(request, "系统错误");
		}
		MailInfoService mailInfoService = new MailInfoService(); 
		mailInfoService.deleteMailByid(mailId, roleEntity.getPPk());
		return mapping.findForward("return_hint");
	}
	
	//************申请入帮管理
	/**
	 * 申请入帮，帮派列表
	 */
	public ActionForward applyFList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getPageList(super.getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=applyFList");
		request.setAttribute("item_page", item_page);
		
		request.setAttribute("pre", "applyFList");
		return mapping.findForward("apply_faction_list");
	}
	/**
	 * 申请信息列表
	 */
	public ActionForward applyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		QueryPage item_page = factionService.getApplyListPageList(faction.getId(), getPageNo(request));
		item_page.setURL(response, "/faction.do?cmd=applyList");
		
		if(  roleEntity.getBasicInfo().getIsManageApply() )
		{
			request.setAttribute("apply", "apply");
		}
		request.setAttribute("item_page", item_page);
		request.setAttribute("faction", faction);
		return mapping.findForward("apply_list");
	}
	/**
	 *  同意加入帮派
	 */
	public ActionForward agreeApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String aId = request.getParameter("aId");
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.agreeApply(roleEntity, Integer.parseInt(aId));
		if( hint!=null )
		{
			this.setError(request, hint);
		}
		
		return this.applyList(mapping, form, request, response);
	}
	/**
	 * 删除申请
	 */
	public ActionForward delApply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String aId = request.getParameter("aId");
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.delApply(roleEntity, Integer.parseInt(aId));
		if( hint!=null )
		{
			this.setError(request, hint);
		}
		
		return this.applyList(mapping, form, request, response);
	}
	
	//****************************帮派公告
	/**
	 * 公告列表
	 */
	public ActionForward noticeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		int page_no = super.getPageNo(request);
		
		FactionService factionService = new FactionService();
		
		QueryPage item_page = factionService.getNoticePageList(faction.getId(), page_no);
		item_page.setURL(response, "/faction.do?cmd=noticeList");
		
		if(  roleEntity.getBasicInfo().getIsDelNotice() )
		{
			request.setAttribute("del", "del");
		}
		if(  roleEntity.getBasicInfo().getIsPublishNotice())
		{
			request.setAttribute("add", "add");
		}
		request.setAttribute("item_page", item_page);
		request.setAttribute("faction", faction);
		return mapping.findForward("notice_list");
	}
	/**
	 * 删除公告提示
	 */
	public ActionForward delNoticeHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("id", request.getParameter("id"));
		return mapping.findForward("del_notice_hint");
	}
	
	/**
	 * 删除公告
	 */
	public ActionForward delNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		
		RoleEntity roleEntity = super.getRoleEntity(request);
		FactionService factionService = new FactionService();
		
		String hint = factionService.delNotice(roleEntity, Integer.parseInt(id));
		if( hint!=null )
		{
			this.setError(request, hint);
		}
		
		return this.noticeList(mapping, form, request, response);
	}
	
	/**
	 * 输入公告内容
	 */
	public ActionForward inputNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		return mapping.findForward("input_notice");
	}
	/**
	 * 发布公告
	 */
	public ActionForward publishNotice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String content = request.getParameter("content").trim();
		
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return this.index(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.publishNotice(roleEntity, content);
		if( hint!=null )
		{
			this.setError(request, hint);
			return mapping.findForward("input_notice");
		}
		
		return this.index(mapping, form, request, response);
	}
	
	
	//****************************申请加入帮派
	/**
	 * 申请加入帮派
	 */
	public ActionForward apply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String fId = request.getParameter("fId");
		
		FactionService factionService = new FactionService();
		
		RoleEntity roleEntity = this.getRoleEntity(request);
		Faction faction = FactionService.getById(Integer.parseInt(fId));
		
		String hint = factionService.apply(roleEntity, faction);
		this.setHint(request, hint);
		return mapping.findForward("apply_hint");
	}
	
	//****************************帮派创建
	/**
	 * 创建帮派首页面
	 */
	public ActionForward createIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("create_index");
	}
	
	/**
	 * 输入帮派名字
	 */
	public ActionForward inputFName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		
		FactionService factionService = new FactionService();
		String hint = factionService.isCreated(roleEntity);
		if( hint!=null )
		{
			this.setHint(request,  "对不起，您的等级不够，或灵石不足，或没有氏族令，不可创建氏族！");
			return mapping.findForward("create_fail_hint");
		}
		
		return mapping.findForward("input_faction_name");
	}
	
	/**
	 * 创建帮派
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction!=null )
		{
			return this.index(mapping, form, request, response);
		}
		
		String f_name = request.getParameter("fName").trim();
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.create(roleEntity, f_name);
		if( hint==null)
		{
			hint = "恭喜您成功创建了氏族"+f_name+"，请尽快招纳氏族成员，若三日内氏族成员未满10人，则您的氏族将自动解除！";
			this.setHint(request, hint);
			return mapping.findForward("return_faction_hint");
		}
		else
		{
			this.setError(request, hint);
			return mapping.findForward("input_faction_name");
		}
	}
	
}
