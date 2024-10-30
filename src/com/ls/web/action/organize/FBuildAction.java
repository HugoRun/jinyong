package com.ls.web.action.organize;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.model.organize.faction.FBuild;
import com.ls.model.organize.faction.Faction;
import com.ls.model.organize.faction.game.FGameBuild;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.web.action.ActionBase;
import com.ls.web.service.faction.FBuildService;

/**
 * @author ls
 * ���ɽ���
 */
public class FBuildAction extends ActionBase
{
	/**
	 * ���ɽ�����ҳ��
	 */
	public ActionForward buildIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		request.setAttribute("faction", faction);
		return mapping.findForward("build_index");
	}
	
	//***************ͼ�ڹ���
	/**
	 * ����
	 */
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		String bId = request.getParameter("bId");
		
		FBuildService fBuildService = new FBuildService();
		
		FGameBuild gameBuild = FBuildService.getGameBuildById(Integer.parseInt(bId));
		
		String hint = fBuildService.createTuteng(roleEntity,gameBuild);
		
		request.setAttribute("gameBuild", gameBuild);
		if( hint!=null )
		{
			this.setHint(request, hint);
			return mapping.findForward("create_fail");
		}
		else
		{
			return mapping.findForward("create_success");
		}
	}
	
	/**
	 * ������ҳ��
	 */
	public ActionForward createIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		FBuildService fBuildService = new FBuildService();
		QueryPage item_page = fBuildService.getUnBuildPageList(faction.getId(), this.getPageNo(request));
		item_page.setURL(response, "/fBuild.do?cmd=createIndex");
		request.setAttribute("item_page", item_page);
		return mapping.findForward("create_index");
	}
	/**
	 * ����
	 */
	public ActionForward upgrade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		String bId = request.getParameter("bId");
		
		FBuildService fBuildService = new FBuildService();
		
		FBuild fBuild = fBuildService.getFBuildById(faction.getId(), Integer.parseInt(bId));
		
		String hint = fBuildService.upgradeTuteng(roleEntity, fBuild);
		
		request.setAttribute("gameBuild", fBuild.getGameBuild().getNextGradeBuild());
		if( hint!=null )
		{
			this.setHint(request, hint);
			return mapping.findForward("upgrade_fail");
		}
		else
		{
			return mapping.findForward("upgrade_success");
		}
	}
	/**
	 * ������ҳ��
	 */
	public ActionForward upgradeIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		FBuildService fBuildService = new FBuildService();
		QueryPage item_page = fBuildService.getBuildPageList(faction.getId(), this.getPageNo(request));
		item_page.setURL(response, "/fBuild.do?cmd=upgradeIndex");
		request.setAttribute("item_page", item_page);
		return mapping.findForward("upgrade_index");
	}
	/**
	 * ͼ����ҳ��
	 */
	public ActionForward ttIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		
		FBuildService fBuildService = new FBuildService();
		
		QueryPage item_page = fBuildService.getBuildPageList(faction.getId(), this.getPageNo(request));
		item_page.setURL(response, "/fBuild.do?cmd=ttIndex");
		request.setAttribute("item_page", item_page);
		request.setAttribute("faction", faction);
		request.setAttribute("roleEntity", roleEntity);
		return mapping.findForward("tuteng_index");
	}
	
	/**
	 * ͼ����ϸ��Ϣ
	 */
	public ActionForward ttDes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		
		FBuildService fBuildService = new FBuildService();
		String bId = request.getParameter("bId");
		String pre = request.getParameter("pre");
		if( pre!=null )
		{
			if( pre.equals("createIndex"))
			{
				FGameBuild fBuild = FBuildService.getGameBuildById(Integer.parseInt(bId));
				request.setAttribute("fBuild", fBuild);
			}
			else if( pre.equals("ttIndex") || pre.equals("upgradeIndex") )
			{
				FBuild fBuild = fBuildService.getFBuildById(faction.getId(), Integer.parseInt(bId));
				request.setAttribute("fBuild", fBuild);
			}
			request.setAttribute("pre", pre);
			request.setAttribute("bId", bId);
		}
		
		return mapping.findForward("tuteng_des");
	}
	
	/**
	 * ʹ��ͼ����ʾҳ��
	 */
	public ActionForward useHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		
		FBuildService fBuildService = new FBuildService();
		String bId = request.getParameter("bId");
		FBuild fBuild = fBuildService.getFBuildById(faction.getId(), Integer.parseInt(bId));
		request.setAttribute("fBuild", fBuild);
		return mapping.findForward("use_hint");
	}
	/**
	 * ʹ��ͼ��
	 */
	public ActionForward use(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return super.dispath(request, response, "/faction.do?cmd=index");
		}
		else
		{
			request.setAttribute("faction", faction);
		}
		
		FBuildService fBuildService = new FBuildService();
		String bId = request.getParameter("bId");
		FBuild fBuild = fBuildService.getFBuildById(faction.getId(), Integer.parseInt(bId));
		String hint = fBuildService.useTuteng(roleEntity, fBuild);
		this.setHint(request, hint);
		return mapping.findForward("use_end");
	}
}
