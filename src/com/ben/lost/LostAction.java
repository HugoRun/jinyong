package com.ben.lost;

import java.util.ArrayList;
import java.util.Date;

import javax.management.relation.RoleInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.quiz.QuizVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.action.menu.BaseAction;
import com.ls.web.service.Quiz.QuizService;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.rank.RankService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.question.QuestionService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.TaskService;

public class LostAction extends BaseAction
{
	// �ؾ���ͼ���ѱ�����
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			String where = request.getParameter("where");
			if (where == null || "".equals(where.trim()))
			{
				setMessage(request, "������");
				return mapping.findForward("mess");
			}
			BasicInfo bi = getBasicInfo(request);
			new PlayerService().updateSceneAndView(bi.getPPk(), Integer
					.parseInt(where.trim()));
			return mapping.findForward("refurbish_scene");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			setMessage(request, "������");
			return mapping.findForward("mess");
		}
	}

	// ռ�����˻ش�����
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String menu_id = request.getParameter("menu_id");
		request.setAttribute("menu_id", menu_id);
		MenuService menuService = new MenuService();
		OperateMenuVO menuvo = menuService.getMenuById(Integer.parseInt(menu_id.trim()));
		if(menuvo==null||null==menuvo.getMenuOperate1()||"".equals(menuvo.getMenuOperate1().trim())){
			setMessage(request, "������");
			return mapping.findForward("mess");
		}
		QuizService quizService = new QuizService();
		QuizVO quiz = quizService.getRandomQuizByConfine(menuvo
				.getMenuOperate1());// ����õ�һ����Ŀ
		request.setAttribute("quiz", quiz);
		request.setAttribute("question_time", (new Date()).getTime());
		return mapping.findForward("questionPagea");
	}

	// �ش����⴦��
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		String answer_time = request.getParameter("time");
		String quiz_id = request.getParameter("quiz_id");
		String player_answer = request.getParameter("player_answer");
		// String w_type = request.getParameter("w_type");
		String menu_id = request.getParameter("menu_id");
		request.setAttribute("menu_id", menu_id);

		if (quiz_id == null || player_answer == null
				|| "".equals(quiz_id.trim()) || "".equals(player_answer.trim()))
		{
			setMessage(request, "������");
			return mapping.findForward("mess");
		}

		MenuService menuService = new MenuService();
		OperateMenuVO menuvo = menuService.getMenuById(Integer.parseInt(menu_id
				.trim()));
		if (menuvo == null || menuvo.getMenuOperate2() == null
				|| menuvo.getMenuOperate3() == null
				|| "".equals(menuvo.getMenuOperate2().trim())
				|| "".equals(menuvo.getMenuOperate3().trim()))
		{
			setMessage(request, "������");
			return mapping.findForward("mess");
		}

		Date dt = new Date();

		if (answer_time == null || answer_time.equals("")
				|| Long.valueOf(answer_time.trim()) < dt.getTime() - 1000 * 20)
		{
			setMessage(request,
					"�Բ��𣬻ش�ʱ��Ӧ��20����ѡ����ȷ�𰸣�ռ������˫��һ�ӣ�һ�ɺ��̽��㴫�͵����Թ�����ڡ�");
			new PlayerService().updateSceneAndView(bi.getPPk(), Integer
					.parseInt(menuvo.getMenuOperate3().trim()));
		}
		else
		{

			QuizService quizService = new QuizService();
			QuizVO quiz = quizService.getAwardById(Integer.parseInt(quiz_id));
			if (Integer.parseInt(player_answer) == quiz.getQuziRightAnswer())
			{
				// �ش���ȷ
				setMessage(request, "��ϲ����������⣬ռ������˫��һ�ӣ�һ���׹⽫�㴫�͵����Թ��ĳ��ڡ�");
				new PlayerService().updateSceneAndView(bi.getPPk(), Integer
						.parseInt(menuvo.getMenuOperate2().trim()));
			}
			else
			{
				// �ش����
				setMessage(request, "���ź�����������⣬ռ������˫��һ�ӣ�һ�ɺ��̽��㴫�͵����Թ�����ڡ�");
				new PlayerService().updateSceneAndView(bi.getPPk(), Integer
						.parseInt(menuvo.getMenuOperate3().trim()));
			}
		}
		return mapping.findForward("mess");
	}
}
