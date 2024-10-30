package com.web.action.pet;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ben.dao.petinfo.PetInfoDAO;
import com.ls.ben.cache.staticcache.pet.PetSkillCache;
import com.ls.ben.dao.info.pet.PetSkillDao;
import com.ls.ben.vo.info.pet.PetSkillVO;
import com.ls.web.service.pet.PetService;
import com.lw.dao.pet.skill.PetSkillLevelUpDao;
import com.lw.service.pet.PetSkillDisplayService;
import com.lw.service.skill.PetSkillLevelUpService;
import com.pub.ben.info.Expression;

/**
 * @author ��ƾ� ����
 */
public class PetInfoAction extends DispatchAction
{
	/**
	 * �����б�
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward("petinfolist");
	}

	/**
	 * �鿴������ϸ��Ϣ
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String petPk = request.getParameter("petPk");
		String petGrade = request.getParameter("petGrade");
		String petFatigue = request.getParameter("petFatigue");
		String petIsBring = request.getParameter("petIsBring");
		PetService petService = new PetService();

		String resultWml = petService.getPetDisplayWmlOwn(Integer
				.parseInt(petPk), request, response);
		request.setAttribute("petIsBring", petIsBring);
		request.setAttribute("petGrade", petGrade);
		request.setAttribute("petFatigue", petFatigue);
		request.setAttribute("petPk", petPk);
		request.setAttribute("resultWml", resultWml);
		return mapping.findForward("petinfoview");

	}

	/**
	 * ���ĳ����ǳ�
	 */
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String petPk = request.getParameter("petPk");
		String pet_nickname = request.getParameter("pet_nickname").trim();

		String petGrade = request.getParameter("petGrade");
		String petFatigue = request.getParameter("petFatigue");
		String petIsBring = request.getParameter("petIsBring");

		String pPk = (String) request.getSession().getAttribute("pPk");

		// ��֤pet_nickname
		// todo
		String hint = null;// ��֤ʧ����ʾ
		Pattern p = Pattern.compile(Expression.chinese_regexp);
		Matcher m = p.matcher(pet_nickname);
		boolean b = m.matches();
		if (b == false)
		{
			hint = "��������Ϊ����,Ӣ�Ļ������ַ����";
		}

		if (pet_nickname.equals(""))
		{
			hint = "����Ϊ��";
		}
		else
		{
			if (pet_nickname.length() > 4)
			{
				hint = "���ܳ���4���ַ�";
			}
		}

		if (hint == null)
		{
			PetService petService = new PetService();
			petService.updateNickName(Integer.parseInt(petPk), pet_nickname,
					pPk);

			String resultWml = petService.getPetDisplayWml(Integer
					.parseInt(petPk));
			request.setAttribute("resultWml", resultWml);
			request.setAttribute("petPk", petPk);
			request.setAttribute("petIsBring", petIsBring);
			request.setAttribute("petGrade", petGrade);
			request.setAttribute("petFatigue", petFatigue);
			return mapping.findForward("petinfoview");
		}
		else
		{
			request.setAttribute("hint", hint);
			request.setAttribute("petPk", petPk);
			request.setAttribute("petIsBring", petIsBring);
			request.setAttribute("petGrade", petGrade);
			request.setAttribute("petFatigue", petFatigue);
			return mapping.findForward("pet_rename");
		}

	}

	/**
	 * ���ĳ����ǳ�ҳ��
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// String petPk = request.getParameter("petPk");
		// String petGrade = request.getParameter("petGrade");
		// String petFatigue = request.getParameter("petFatigue");
		// String petIsBring = request.getParameter("petIsBring");
		request.setAttribute("petIsBring", request.getParameter("petIsBring"));
		request.setAttribute("petGrade", request.getParameter("petGrade"));
		request.setAttribute("petFatigue", request.getParameter("petFatigue"));
		request.setAttribute("petPk", request.getParameter("petPk"));
		return mapping.findForward("pet_rename");

	}

	/**
	 * ��������ҳ��
	 */
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String petPk = request.getParameter("petPk");
			String petGrade = request.getParameter("petGrade");
			String petFatigue = request.getParameter("petFatigue");
			String petIsBring = request.getParameter("petIsBring");
			PetInfoDAO petInfoDAO = new PetInfoDAO();
			String petName = petInfoDAO.pet_name(Integer.parseInt(petPk));
			String hint = null;
			if (Integer.parseInt(petIsBring) == 1)
			{
				hint = "���ĳ���" + petName + "����ս��״̬��������!";
			}
			else
			{
				hint = "���Ƿ�Ҫɾ������" + petName + "��?";
			}
			request.setAttribute("petIsBring", petIsBring);
			request.setAttribute("petGrade", petGrade);
			request.setAttribute("petFatigue", petFatigue);
			request.setAttribute("petPk", petPk);
			request.setAttribute("hint", hint);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward("pet_delete");

	}

	/** ��ʾ������ѧ���ļ��� */
	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		// String petPk = request.getParameter("petPk");
		// String petGrade = request.getParameter("petGrade");
		// String petFatigue = request.getParameter("petFatigue");
		// String petIsBring = request.getParameter("petIsBring");
		String petPk = request.getParameter("petPk");
		PetSkillLevelUpDao pdao = new PetSkillLevelUpDao();
		int pet_id = pdao.getPetPetidByPetpk(Integer.parseInt(petPk));
		PetSkillCache petCache = new PetSkillCache();
		PetSkillLevelUpService sk = new PetSkillLevelUpService();
		String hint = "";
		if (pet_id == 0)
		{
			hint = "û�п���ѧϰ�ļ���!";
		}
		else
		{
			List<Integer> list = sk.getPetSkillControl(pet_id);
			if (list == null || list.size() == 0)
			{
				hint = "û�п���ѧϰ�ļ���!";
			}
			else
			{
				for (int i = 0; i < list.size(); i++)
				{
					int id = Integer.parseInt(list.get(i).toString());
					PetSkillVO vo = petCache.getPetSkillById(id);
					if (vo.getPetSkLevel() == 0)
					{
						hint = hint
								+ petCache.getName(id).substring(0,
										(petCache.getName(id).length() - 1));
					}
					else
					{
						hint = hint
								+ petCache.getName(id).substring(0,
										(petCache.getName(id).length() - 2));
					}
					if (i != list.size() - 1)
					{
						hint = hint + ",";
					}
				}
			}
		}
		request.setAttribute("hint", hint);
		request.setAttribute("petIsBring", request.getParameter("petIsBring"));
		request.setAttribute("petGrade", request.getParameter("petGrade"));
		request.setAttribute("petFatigue", request.getParameter("petFatigue"));
		request.setAttribute("petPk", request.getParameter("petPk"));
		return mapping.findForward("pet_skill_list");
	}

	/** ��ʾ������ѧ���ļ��� */
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int skill_id = Integer.parseInt(request.getParameter("skill_id"));
		PetSkillDisplayService petSkillDisplayService = new PetSkillDisplayService();
		String display = petSkillDisplayService.getPetSkillDisplay(skill_id);
		request.setAttribute("petIsBring", request.getParameter("petIsBring"));
		request.setAttribute("petGrade", request.getParameter("petGrade"));
		request.setAttribute("petFatigue", request.getParameter("petFatigue"));
		request.setAttribute("petPk", request.getParameter("petPk"));
		request.setAttribute("display", display);
		return mapping.findForward("pet_skill_display");
	}
}
