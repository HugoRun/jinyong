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
	 * �����û�������ֱ�
	 * @param pk
	 */
	public void updateIntegral(int pk) {
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateIntegeral(pk,getNowMouth());
	}

	/**
	 * ��õ�ǰ�·ݵı�ʾ
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
	 * ���������Դ���
	 * @param pk
	 * @return
	 */
	public int getConuniteWinNum(int pk)
	{
		int win_num = 0;
		QuestionDao questionDao = new QuestionDao();
		win_num = questionDao.getConuniteWinNum(pk,getNowMouth());
		logger.info("win_num="+win_num);
		//���û�У��Ͳ���һ��
		if(win_num == 0) {
			if(questionDao.getHasThisMouth(pk,getNowMouth()) == 0){
				questionDao.insertQiueInfo(pk,getNowMouth());
			}
		}
		return win_num;
	}					

	/**
	 * �����û���Ӧ�õõ��ľ���,��þ��鹫ʽ=��������������˵ȼ�ϵ����������Խ���ϵ��
	 *	�����˵ȼ�ϵ��=�����˵ȼ�/grade_arg,������Խ���ϵ��=1+������Դ���/10
	 * @param player
	 * @param conuniteWinNum
	 * @param grade_arg �ȼ�ϵͳ
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
	 * ��õ�ʮ��ʱ����ʾ��Ϣ
	 * @param pk
	 * @return
	 */
	public String getTenDisplay(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("���Ѿ�����˽��յ�10�����⣡<br/>");
		String integral = questionDao.getIntegral(pk,getNowMouth())+"";
		resultWml.append("�����ıȻ���:").append(integral).append("�֣�");
		String paiming = questionDao.getIntegralPaiMing(pk,getNowMouth())+"";
		resultWml.append("�����ı���λ:��").append(paiming).append("����");
		return resultWml.toString();
	}
	
	/**
	 * ��ô��������
	 * @return
	 */
	public List<QuestionVO> getQuestionRanking()
	{
		QuestionDao questionDao = new QuestionDao();
		List<QuestionVO> list = questionDao.getQuestionRanking(getNowMouth());
		return list;
	}

	/**
	 * ������������ȷ����Ϊ0.
	 * @param pk
	 */
	public void updateConutiuteWinToZero(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateupdateConutiuteWinToZero(pk,getNowMouth());
	}

	/**
	 * ��ÿ������ÿ�ն�����ʮ�����������������.
	 * @param pk
	 */
	public void addCouniuteDay(int pk)
	{
		//ÿ��ȫ����ʮ����,�ͼ�һ
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateupdateTenAll(pk,getNowMouth());
		
	}
	
	/**
	 * ����ǰʱ��������ݿⲢ�������־��Ϊ1 
	 * @param pk
	 */
	public void updateQuestionTimeAndFlag(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateQuestionTimeAndFlag(pk,getNowMouth());
	}

	/**
	 * ����ǰ��ҵĴ����־��Ϊ0
	 * @param pk
	 */
	public void updateQuestionFlagByPPk(int pk)
	{
		QuestionDao questionDao = new QuestionDao();
		questionDao.updateQuestionFlag(pk,getNowMouth(),0);
		
	}

	/**
	 * ���ݴ����־������Ӯ��
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
