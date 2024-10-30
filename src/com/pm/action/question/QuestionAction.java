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
	 *  转向答题页面
	 */
	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("menu_id", request.getParameter("menu_id"));
		logger.info(" request.getParameter('menu_id')="+ request.getParameter("menu_id"));
		
		return mapping.findForward("firstPage");
	}
	
	/**
	 *  开始答题
	 */
	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		String menu_id = request.getParameter("menu_id");
		request.setAttribute("menu_id", menu_id);
		
		//根据答题标志,进行有关连赢数的操作
		QuestionService questionService = new QuestionService();
		questionService.operateCountieWin(pPk);
		
		MenuService menuService = new MenuService();
		OperateMenuVO menuvo = menuService.getMenuById(Integer.parseInt(menu_id));
		
		if(roleInfo.getBasicInfo().getGrade() < Integer.valueOf(menuvo.getMenuOperate3())) {
			String resultWml = "您的等级不够,答题需要等级"+menuvo.getMenuOperate3()+"!";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("failed");
		}
		
		TimeControlService timeControlService = new TimeControlService();
		//答题菜单最多只能答十次
		if(!timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU,Integer.valueOf(menuvo.getMenuOperate2()))){
			String resultWml = "您每日最多只能答10道题！";
			request.setAttribute("resultWml", resultWml);
			return mapping.findForward("failed");
		} else {
			QuizService quizService = new QuizService();
			QuizVO quiz = null;
			//在session中存储用户已经回答的问题
			ArrayList<Integer> arrayList = (ArrayList<Integer>)session.getAttribute("quizIdList");
			if(arrayList == null){
				logger.info("每日首次答题！");
				quiz = quizService.getRandomQuizByConfine(menuvo.getMenuOperate1());// 随机得到一个题目
				arrayList = new ArrayList<Integer>();
				arrayList.add(quiz.getId());
				session.setAttribute("quizIdList", arrayList);
			} else {
				//已经提问过的不再次提问.
				do{
					quiz = quizService.getRandomQuizByConfine(menuvo.getMenuOperate1());// 随机得到一个题目
					logger.info("quizId="+quiz.getId());
				}
				while (arrayList.contains(quiz.getId()));
					
				logger.info("arraylist="+arrayList.toString());
				arrayList.add(quiz.getId());
				session.setAttribute("quizIdList", arrayList);
				
			}
			//将当前的时间和答题标志存入数据库中
			questionService.updateQuestionTimeAndFlag(roleInfo.getBasicInfo().getPPk());
			
			request.setAttribute("quiz",quiz);
			request.setAttribute("question_time",(new Date()).getTime());
			timeControlService.updateControlInfo(roleInfo.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU);
			request.setAttribute("number",timeControlService.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU));
			
			
			return mapping.findForward("questionPage");
		}
	}
	
	//回答问题处理
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
		//首先将用户的答题标志置为零,表示他没有退出游戏等等.
		QuestionService questionService = new QuestionService();
		questionService.updateQuestionFlagByPPk(roleEntity.getBasicInfo().getPPk());
		
		MenuService menuService = new MenuService();
		OperateMenuVO menuvo = menuService.getMenuById(Integer.parseInt(menu_id));
		
		TimeControlService timeControlService = new TimeControlService();
		
		Date dt = new Date();
		StringBuffer resultWml = new StringBuffer();
		
		if(answer_time == null || answer_time.equals("")) {
			logger.info("答题开始时间为零");
			resultWml.append("对不起，回答超时！请在20秒内选择正确答案！");
			request.setAttribute("resultWml", resultWml.toString());
			//将连胜数改成零
			questionService.updateConutiuteWinToZero(roleEntity.getBasicInfo().getPPk());
			int alreadyAnswerNum = timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), Integer.valueOf(menu_id), TimeControlService.MENU);
			request.setAttribute("alreadyAnswerNum",alreadyAnswerNum);
			return mapping.findForward("answer_over");
		}
		
		logger.info("answer_time="+answer_time+" ,,dt.getTime()="+dt.getTime());
		logger.info("答题超出时间="+(Long.valueOf(answer_time) < dt.getTime()-1000*20));
		
		if(Long.valueOf(answer_time) < dt.getTime()-1000*20) {
			resultWml.append("对不起，回答超时！请在20秒内选择正确答案！");
			request.setAttribute("resultWml", resultWml.toString());
			//将连胜数改成零
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
				resultWml.append("恭喜您选择了正确答案！<br/>您获得"+MoneyUtil.changeCopperToStr((int)quiz.getAwardMoney())+"<br/>");
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, (int)quiz.getAwardMoney(), StatisticsType.DEDAO, StatisticsType.XITONG,roleEntity.getBasicInfo().getPPk());
				
			}
			else if( quiz.getAwardExperience() > 0 )
			{
				//PartInfoDao partInfoDao = new PartInfoDao();
				//PartInfoVO player = partInfoDao.getPartInfoByID(userTempBean.getPPk());
				
				int awaedExperience = questionService.getAddExperience(roleEntity.getBasicInfo().getGrade(),conuniteWinNum,quiz.getAwardExperience(),Integer.valueOf(menuvo.getMenuOperate3()));
				resultWml.append("恭喜您选择了正确答案！<br/>您获得了:经验+"+awaedExperience + "点");
				//给玩家增加经验
				TaskService taskService = new TaskService();
				taskService.getAddExp(roleEntity,roleEntity.getBasicInfo().getPPk(), awaedExperience);
				
				//partInfoDao.updatExperience(player,Integer.valueOf(player.getPExperience())+awaedExperience);
			}
			questionService.updateIntegral(roleEntity.getBasicInfo().getPPk());
			//统计需要
			new RankService().updateAdd(pPk, "ans", 1);
			
		}
		else
		{
			String question_num = request.getParameter("question_num");
			logger.info("question_num="+question_num);
			if(question_num != null) {
				int question_number = Integer.valueOf(question_num);
				resultWml.append("对不起，您选择了错误的答案！<br/>您还可以回答").append((10-question_number)+"").append("道题目！");
			} else {
				resultWml.append("对不起，您选择了错误的答案！");
			}
			//将连胜数改成零
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
	 *  查看排名
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
	
