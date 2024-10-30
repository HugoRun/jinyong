package com.ls.web.service.Quiz;

import org.apache.log4j.Logger;

import com.ls.ben.dao.info.quiz.QuizDao;
import com.ls.ben.vo.info.quiz.QuizVO;
import com.ls.pub.util.MathUtil;


/**
 * 功能: 题库操作
 * @author 刘帅
 * 6:20:59 PM
 */
public class QuizService {
	
	Logger logger =  Logger.getLogger("log.service");

	
	
	/**
	 * 根据范围得到随机题目的
	 * @param confine
	 * @return
	 */
	public QuizVO getRandomQuizByConfine( String confine )
	{
		if( confine==null )
		{
			logger.info("题目范围为空");
			return null;
		}
		QuizVO quiz = null;
		QuizDao quizDao = new QuizDao();
		String[] confines = confine.split(",");
		
		int quiz_id = (int)MathUtil.getRandomDoubleXY(Double.valueOf(confines[0]),Double.valueOf(confines[1]));
		if( quiz == null )
		{
			quiz = quizDao.getContentById(quiz_id);
		}
		logger.info("quiz_id="+quiz.getId());
		return quiz ;
	}
	
	/**
	 * 从所有题目中任意抽出一个
	 
	public QuizVO getRandomQuiz()
	{
		
		QuizVO quiz = null;
		QuizDao quizDao = new QuizDao();
		int confine = quizDao.getQuizAllNumber();
		quiz = getRandomQuizByConfine(confine+"");
		return quiz ;
	}*/
	
	/**
	 * 判读是否答对
	 */
	public boolean isRight( int quiz_id,int answer )
	{
		boolean result = false;
		
		QuizDao quizDao = new QuizDao();
		int right_answer = quizDao.getRightAnswerById(quiz_id, answer);
		if( answer == right_answer )
		{
			result = true;
		}
		return result;
	}
	
	/**
	 * 得到答题奖励信息
	 * @param quiz_id
	 * @return
	 */
	public  QuizVO  getAwardById( int quiz_id )
	{
		QuizDao quizDao = new QuizDao();
		return quizDao.getAwardById(quiz_id);
	}

	

}
