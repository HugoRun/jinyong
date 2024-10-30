package com.pm.service.question;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.pm.dao.question.QuestionDao;
import com.pm.vo.question.QuestionVO;

public class QuestionService
{
	Logger logger =  Logger.getLogger("log.service");

	
	/**
	 * 更新用户答题积分表
	 * @param pk
	 */
	public void updateIntegral(int pk) {
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateIntegeral(pk,getNowMouth());
	}

	/**
	 * 获得当前月份的表示
	 * @return
	 */
	private String getNowMouth()
	{
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM");
		String mouth = df.format(dt);
		return mouth;
	}

	/**
	 * 获得连续答对次数
	 * @param pk
	 * @return
	 */
	public int getConuniteWinNum(int pk)
	{
		int win_num = 0;
		QuestionDao questionDao = new QuestionDao();
		win_num = questionDao.getConuniteWinNum(pk,getNowMouth());
		logger.info("win_num="+win_num);
		//如果没有，就插入一个
		if(win_num == 0) {
			if(questionDao.getHasThisMouth(pk,getNowMouth()) == 0){
				questionDao.insertQiueInfo(pk,getNowMouth());
			}
		}
		return win_num;
	}					

	/**
	 * 计算用户所应该得到的经验,获得经验公式=基本经验×答题人等级系数×连续答对奖励系数
	 *	答题人等级系数=答题人等级/grade_arg,连续答对奖励系数=1+连续答对次数/10
	 * @param player
	 * @param conuniteWinNum
	 * @param grade_arg 等级系统
	 * @return
	 */
	public int getAddExperience(int p_grade, int conuniteWinNum,long experience,int grade_arg)
	{
		double awaedExperience = (double)experience*p_grade/grade_arg;
		double awaedExperience2 = awaedExperience + awaedExperience*conuniteWinNum/10;
		logger.info("p_grade/grade_arg="+p_grade/grade_arg+" ,conuniteWinNum="+conuniteWinNum);
		logger.info("experience="+experience+" ,awaedExperience="+awaedExperience+" ,awaedExperience2="+awaedExperience2);
		return 	(int)awaedExperience2;
	}

	/**
	 * 获得第十题时的显示信息
	 * @param pk
	 * @return
	 */
	public String getTenDisplay(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("您已经完成了今日的10道答题！<br/>");
		String integral = questionDao.getIntegral(pk,getNowMouth())+"";
		resultWml.append("本月文比积分:").append(integral).append("分！");
		String paiming = questionDao.getIntegralPaiMing(pk,getNowMouth())+"";
		resultWml.append("本月文比排位:第").append(paiming).append("名！");
		return resultWml.toString();
	}
	
	/**
	 * 获得答题的排名
	 * @return
	 */
	public List<QuestionVO> getQuestionRanking()
	{
		QuestionDao questionDao = new QuestionDao();
		List<QuestionVO> list = questionDao.getQuestionRanking(getNowMouth());
		return list;
	}

	/**
	 * 将连续答题正确数置为0.
	 * @param pk
	 */
	public void updateConutiuteWinToZero(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateupdateConutiuteWinToZero(pk,getNowMouth());
	}

	/**
	 * 将每月连续每日都答完十道题的天数计算下来.
	 * @param pk
	 */
	public void addCouniuteDay(int pk)
	{
		//每有全答完十道题,就加一
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateupdateTenAll(pk,getNowMouth());
		
	}
	
	/**
	 * 将当前时间存入数据库并将答题标志置为1 
	 * @param pk
	 */
	public void updateQuestionTimeAndFlag(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateQuestionTimeAndFlag(pk,getNowMouth());
	}

	/**
	 * 将当前玩家的答题标志置为0
	 * @param pk
	 */
	public void updateQuestionFlagByPPk(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateQuestionFlag(pk,getNowMouth(),0);
		
	}

	/**
	 * 根据答题标志操作连赢数
	 * @param pk
	 */
	public void operateCountieWin(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		int flag = questionDao.getQuestionFlag(pk,getNowMouth());
		logger.info("flag="+flag);
		if(flag == 1) {
			updateConutiuteWinToZero(pk);
		}
		
	}

	
}
