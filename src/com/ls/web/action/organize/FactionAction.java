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
 * �������
 */
public class FactionAction extends ActionBase
{
	/**
	 * ������ҳ��
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
	 * ���������б�
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
	 * ������Ϣ
	 */
	public ActionForward des(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String fId = request.getParameter("fId");
		request.setAttribute("pre", request.getParameter("pre"));
		Faction faction = null ;
		if( fId==null )//�Լ��İ�����Ϣ
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
		else//�鿴���˵İ�����Ϣ
		{
			faction = FactionService.getById(Integer.parseInt(fId));
			request.setAttribute("faction", faction);
			return mapping.findForward("other_faction_info");
		}
		
		
	}
	
	
	//***********************���ɹ���*****************************
	/**
	 * ���ɹ�����ҳ
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
	
	//******************��Ա����
	/**
	 * ɾ����Ա��ʾ
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
	 * ɾ����Ա
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
	 * ���ɳ�Ա�б�
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
	
	//******************��������
	/**
	 * ����������ʾ
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
			this.setHint(request, "����Ŀǰ�Ѵ���ߵȼ�");
			return mapping.findForward("upgrade_end");
		}
		request.setAttribute("material", material);
		return mapping.findForward("upgrade_hint");
	}
	
	/**
	 * ��������
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
	
	//******************��������
	/**
	 * ����������ʾ
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
			this.setHint(request, "����Ŀǰ�Ѵ���ߵȼ�");
			return mapping.findForward("upgrade_end");
		}
		request.setAttribute("material", material);
		return mapping.findForward("upgrade_citang_hint");
	}
	
	/**
	 * ��������
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
	
	//*****************�˳�����
	/**
	 * �˳�����
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
		this.setHint(request, "���ѳɹ��˳�������!");
		return mapping.findForward("return_manage_hint");
	}
	/**
	 * �˳�����ȷ��ҳ��
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
		if( roleEntity.getBasicInfo().getFJob()==Faction.ZUZHANG)//����ǰ���
		{
			return mapping.findForward("zuzhang_exit_hint");
		}
		else
		{
			return mapping.findForward("exit_hint");
		}
	}
	
	
	//*****************��ɢ����
	/**
	 * ��ɢ����ȷ��ҳ��
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
	 * ��ɢ����
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
	 * �ӹܽ�ɢ������
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
	
	//*****************ת���峤
	/**
	 * �����б�
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
	 * ת���峤��ʾ
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
			this.setError(request, "�޸ý�ɫ");
			return this.zlList(mapping, form, request, response);
		}
		this.setHint(request, "��ȷ�����峤һְת�ø�"+member.getName()+"��");
		request.setAttribute("faction", faction);
		request.setAttribute("mId", mId);
		return mapping.findForward("change_zuzhang_hint");
	}
	/**
	 * ת���峤
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
			this.setError(request, "�޸ý�ɫ");
			return this.zlList(mapping, form, request, response);
		}
		
		FactionService factionService = new FactionService();
		
		String hint = factionService.changeZuzhang(roleEntity, member);
		this.setHint(request, hint);
		request.setAttribute("faction", faction);
		return mapping.findForward("change_zuzhang_end");
	}
	
	//*****************��ļ
	/**
	 * ������ļ��Ϣ
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
	 * ������ļ��Ϣ
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
			this.setHint(request,"��ϲ��,������ļ��Ϣ�Ѿ�����,�����������!");
			return mapping.findForward("recruit_end");
		}
	}
	
	
	//****************�޸ĳƺ�
	/**
	 * ����ƺ�
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
			this.setError(request, "Ȩ�޲���");
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
			this.setError(request, "�ý�ɫ������");
			return this.titleMList(mapping, form, request, response);
		}
		member.getBasicInfo().changeFTitle(fTitle);
		this.setHint(request, "��ϲ����"+member.getName()+"�ĳƺ��Ѹ���Ϊ"+fTitle+"��");
		request.setAttribute("faction", faction);
		return mapping.findForward("change_title_end");
	}
	/**
	 * ����ƺ���ʾ
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
			this.setError(request, "�ý�ɫ������");
			request.setAttribute("mId", request.getParameter("mId"));
			request.setAttribute("faction", faction);
			return this.titleMList(mapping, form, request, response);
		}
		this.setHint(request, "��ȷ��Ҫ��"+member.getName()+"�ĳƺŸ���Ϊ"+fTitle+"��");
		request.setAttribute("title", fTitle);
		return mapping.findForward("change_title_hint");
	}
	/**
	 * ����ƺ�
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
	 * �޸ĳƺų�Ա�б�
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
	
	//***********����ְλ
	/**
	 * ѡ��ְλҳ��
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
	 * ѡ��ְλ
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
			this.setError(request, "�Ƿ�ְλ");
			request.setAttribute("mId", request.getParameter("mId"));
			request.setAttribute("basicInfo", roleEntity.getBasicInfo());
			request.setAttribute("faction", faction);
			return mapping.findForward("select_job");
		}
		String pPk =   request.getParameter("mId");
		RoleEntity member = RoleService.getRoleInfoById(pPk);
		if( member==null )
		{
			this.setError(request, "�ý�ɫ������");
			return this.jobMList(mapping, form, request, response);
		}
		this.setHint(request, "��ȷ����"+member.getName()+"��ְλ���Ϊ"+ExchangeUtil.getFJobName(Integer.parseInt(job_str))+"��");
		request.setAttribute("mId", pPk);
		request.setAttribute("job", job_str);
		request.setAttribute("faction", faction);
		return mapping.findForward("change_job_hint");
	}
	/**
	 * �ı�ְλ
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
			this.setError(request, "�Ƿ�ְλ");
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
	 * ���ְλ��Ա�б�
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
	
	//************����������
	/**
	 * ���������ʾ
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
	 * ����
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
	 * ��Ҷ�����Ĵ���
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
						this.setHint(request, "���ѳɹ����������");
					}
				}
				else
				{
					this.setHint(request, "�������ѽ�ɢ");
				}
			}
			else
			{
				this.setHint(request, "���Ѿܾ����������");
			}
		}
		else
		{
			this.setHint(request, "ϵͳ����");
		}
		MailInfoService mailInfoService = new MailInfoService(); 
		mailInfoService.deleteMailByid(mailId, roleEntity.getPPk());
		return mapping.findForward("return_hint");
	}
	
	//************����������
	/**
	 * �����������б�
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
	 * ������Ϣ�б�
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
	 *  ͬ��������
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
	 * ɾ������
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
	
	//****************************���ɹ���
	/**
	 * �����б�
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
	 * ɾ��������ʾ
	 */
	public ActionForward delNoticeHint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("id", request.getParameter("id"));
		return mapping.findForward("del_notice_hint");
	}
	
	/**
	 * ɾ������
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
	 * ���빫������
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
	 * ��������
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
	
	
	//****************************����������
	/**
	 * ����������
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
	
	//****************************���ɴ���
	/**
	 * ����������ҳ��
	 */
	public ActionForward createIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("create_index");
	}
	
	/**
	 * �����������
	 */
	public ActionForward inputFName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		RoleEntity roleEntity = super.getRoleEntity(request);
		
		FactionService factionService = new FactionService();
		String hint = factionService.isCreated(roleEntity);
		if( hint!=null )
		{
			this.setHint(request,  "�Բ������ĵȼ�����������ʯ���㣬��û����������ɴ������壡");
			return mapping.findForward("create_fail_hint");
		}
		
		return mapping.findForward("input_faction_name");
	}
	
	/**
	 * ��������
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
			hint = "��ϲ���ɹ�����������"+f_name+"���뾡�����������Ա���������������Աδ��10�ˣ����������彫�Զ������";
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
