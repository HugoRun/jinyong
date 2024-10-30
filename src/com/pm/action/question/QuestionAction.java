/**
 * 
 */
package com.pm.action.question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.quiz.QuizVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.Quiz.QuizService;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.question.QuestionService;
import com.pm.vo.question.QuestionVO;
import com.web.service.TaskService;

/**
 * @author zhangjj
 *
 */
public class QuestionAction extends DispatchAction {

	Logger logger = Logger.getLogger("log.action");
	
	/**
	 *  ת�����ҳ��
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("menu_id", request.getParameter("menu_id"));
		logger.info(" request.getParameter('menu_id')="+ request.getParameter("menu_id"));
		
		return mapping.findForward("firstPage");
	}
	
	/**
	 *  ��ʼ����
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		String menu_id = request.getParameter("menu_id");
		request.setAttribute("menu_id", menu_id);
		
		//���ݴ����־,�����й���Ӯ���Ĳ���
		QuestionService questionService = new QuestionService();
		questionService.operateCountieWin(pPk);
		
		MenuService menuService = new MenuService();
		OperateMenuVO menuvo = menuService.getMenuById(Integer.parseInt(menu_id));
		
		if(roleInfo.getBasicInfo().getGrade() < Integer.valueOf(menuvo.getMenuOperate3())) {
			String resultWml = "���ĵȼ�����,������Ҫ�ȼ�"+menuvo.getMenuOperate3()+"!";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("failed");
		}
		
		TimeControlService timeControlService = new TimeControlService();
		//����˵����ֻ�ܴ�ʮ��
		if(!timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU,Integer.valueOf(menuvo.getMenuOperate2()))){
			String resultWml = "��ÿ�����ֻ�ܴ�10���⣡";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("failed");
		} else {
			QuizService quizService = new QuizService();
			QuizVO quiz = null;
			//��session�д洢�û��Ѿ��ش������
			ArrayList<Integer> arrayList = (ArrayList<Integer>)session.getAttribute("quizIdList");
			if(arrayList == null){
				logger.info("ÿ���״δ��⣡");
				quiz = quizService.getRandomQuizByConfine(menuvo.getMenuOperate1());// ����õ�һ����Ŀ
				arrayList = new ArrayList<Integer>();
				arrayList.add(quiz.getId());
				session.setAttribute("quizIdList", arrayList);
			} else {
				//�Ѿ����ʹ��Ĳ��ٴ�����.
				do{
					quiz = quizService.getRandomQuizByConfine(menuvo.getMenuOperate1());// ����õ�һ����Ŀ
					logger.info("quizId="+quiz.getId());
				}
				while (arrayList.contains(quiz.getId()));
					
				logger.info("arraylist="+arrayList.toString());
				arrayList.add(quiz.getId());
				session.setAttribute("quizIdList", arrayList);
				
			}
			//����ǰ��ʱ��ʹ����־�������ݿ���
			questionService.updateQuestionTimeAndFlag(roleInfo.getBasicInfo().getPPk());
			
			request.setAttribute("quiz",quiz);
			request.setAttribute("question_time",(new Date()).getTime());
			timeControlService.updateControlInfo(roleInfo.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU);
			request.setAttribute("number",timeControlService.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU));
			
			
			return mapping.findForward("questionPage");
		}
	}
	
	//�ش����⴦��
	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String pPk = (String) request.getSession().getAttribute("pPk");
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);
		
		String answer_time = request.getParameter("time");
		logger.info("answer_time="+answer_time);
		String quiz_id = request.getParameter("quiz_id");
		String player_answer = request.getParameter("player_answer");
		//String w_type = request.getParameter("w_type");
		String menu_id = request.getParameter("menu_id");
		request.setAttribute("menu_id", menu_id);
		
		if( quiz_id==null )
		{
			return null ;
		}
		//���Ƚ��û��Ĵ����־��Ϊ��,��ʾ��û���˳���Ϸ�ȵ�.
		QuestionService questionService = new QuestionService();
		questionService.updateQuestionFlagByPPk(roleEntity.getBasicInfo().getPPk());
		
		MenuService menuService = new MenuService();
		OperateMenuVO menuvo = menuService.getMenuById(Integer.parseInt(menu_id));
		
		TimeControlService timeControlService = new TimeControlService();
		
		Date dt = new Date();
		StringBuffer resultWml = new StringBuffer();
		
		if(answer_time == null || answer_time.equals("")) {
			logger.info("���⿪ʼʱ��Ϊ��");
			resultWml.append("�Բ��𣬻ش�ʱ������20����ѡ����ȷ�𰸣�");
			request.setAttribute("resultWml", resultWml.toString());
			//����ʤ���ĳ���
			questionService.updateConutiuteWinToZero(roleEntity.getBasicInfo().getPPk());
			int alreadyAnswerNum = timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU);
			request.setAttribute("alreadyAnswerNum",alreadyAnswerNum);
			return mapping.findForward("answer_over");
		}
		
		logger.info("answer_time="+answer_time+" ,,dt.getTime()="+dt.getTime());
		logger.info("���ⳬ��ʱ��="+(Long.valueOf(answer_time) < dt.getTime()-1000*20));
		
		if(Long.valueOf(answer_time) < dt.getTime()-1000*20) {
			resultWml.append("�Բ��𣬻ش�ʱ������20����ѡ����ȷ�𰸣�");
			request.setAttribute("resultWml", resultWml.toString());
			//����ʤ���ĳ���
			questionService.updateConutiuteWinToZero(roleEntity.getBasicInfo().getPPk());
			int alreadyAnswerNum = timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU);
			request.setAttribute("alreadyAnswerNum",alreadyAnswerNum);
			return mapping.findForward("answer_over");
		}
		
				
		QuizService quizService = new QuizService();
		//PlayerService playerService = new PlayerService();
		EconomyService economyServcie = new EconomyService();
		int conuniteWinNum = questionService.getConuniteWinNum(roleEntity.getBasicInfo().getPPk());
		
		QuizVO quiz = quizService.getAwardById(Integer.parseInt(quiz_id));
		if( Integer.parseInt(player_answer)==quiz.getQuziRightAnswer() )
		{
			if( quiz.getAwardMoney() > 0 )
			{
				economyServcie.addMoney(roleEntity.getBasicInfo().getPPk(), (int)quiz.getAwardMoney());
				resultWml.append("��ϲ��ѡ������ȷ�𰸣�<br/>�����"+MoneyUtil.changeCopperToStr((int)quiz.getAwardMoney())+"<br/>");
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, (int)quiz.getAwardMoney(), StatisticsType.DEDAO, StatisticsType.XITONG,roleEntity.getBasicInfo().getPPk());
				
			}
			else if( quiz.getAwardExperience() > 0 )
			{
				//PartInfoDao partInfoDao = new PartInfoDao();
				//PartInfoVO player = partInfoDao.getPartInfoByID(userTempBean.getPPk());
				
				int awaedExperience = questionService.getAddExperience(roleEntity.getBasicInfo().getGrade(),conuniteWinNum,quiz.getAwardExperience(),Integer.valueOf(menuvo.getMenuOperate3()));
				resultWml.append("��ϲ��ѡ������ȷ�𰸣�<br/>�������:����+"+awaedExperience + "��");
				//��������Ӿ���
				TaskService taskService = new TaskService();
				taskService.getAddExp(roleEntity,roleEntity.getBasicInfo().getPPk(), awaedExperience);
				
				//partInfoDao.updatExperience(player,Integer.valueOf(player.getPExperience())+awaedExperience);
			}
			questionService.updateIntegral(roleEntity.getBasicInfo().getPPk());
			//ͳ����Ҫ
			new RankService().updateAdd(pPk, "ans", 1);
			
		}
		else
		{
			String question_num = request.getParameter("question_num");
			logger.info("question_num="+question_num);
			if(question_num != null) {
				int question_number = Integer.valueOf(question_num);
				resultWml.append("�Բ�����ѡ���˴���Ĵ𰸣�<br/>�������Իش�").append((10-question_number)+"").append("����Ŀ��");
			} else {
				resultWml.append("�Բ�����ѡ���˴���Ĵ𰸣�");
			}
			//����ʤ���ĳ���
			questionService.updateConutiuteWinToZero(roleEntity.getBasicInfo().getPPk());
		}
		int alreadyAnswerNum = timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU);
		//int a = (Integer.valueOf(menuvo.getMenuOperate2())-1);
		//logger.info("i="+i+" ,a="+a);
		if(timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU) == (int)Integer.valueOf(menuvo.getMenuOperate2())) {
			resultWml.append(questionService.getTenDisplay(roleEntity.getBasicInfo().getPPk()));
			questionService.addCouniuteDay(roleEntity.getBasicInfo().getPPk());
		}
		
		request.setAttribute("alreadyAnswerNum",alreadyAnswerNum);
		request.setAttribute("resultWml", resultWml.toString());
		//request.setAttribute("w_type", w_type);
		return mapping.findForward("answer_over");
	}
	
	/**
	 *  �鿴����
	 */
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		//UserTempBean userTempBean = (UserTempBean) request.getSession().getAttribute("userTempBean");
		//HttpSession session = request.getSession();
		
//		RoleService roleService = new RoleService();
//		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		
		QuestionService questionService = new QuestionService();
		List<QuestionVO> ranklist = questionService.getQuestionRanking();
		request.setAttribute("ranklist",ranklist);
		request.setAttribute("menu_id",request.getParameter("menu_id"));
		
		return mapping.findForward("ranklist");
	}
}
	
