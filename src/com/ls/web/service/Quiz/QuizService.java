package com.ls.web.service.Quiz;

import org.apache.log4j.Logger;

import com.ls.ben.dao.info.quiz.QuizDao;
import com.ls.ben.vo.info.quiz.QuizVO;
import com.ls.pub.util.MathUtil;


/**
 * ����: ������
 * @author ��˧
 * 6:20:59 PM
 */
public class QuizService {
	
	Logger logger =  Logger.getLogger("log.service");

	
	
	/**
	 * ���ݷ�Χ�õ������Ŀ��
	 * @param confine
	 * @return
	 */
	public QuizVO getRandomQuizByConfine( String confine )
	{
		if( confine==null )
		{
			logger.info("��Ŀ��ΧΪ��");
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
	 * ��������Ŀ��������һ��
	 
	public QuizVO getRandomQuiz()
	{
		
		QuizVO quiz = null;
		QuizDao quizDao = new QuizDao();
		int confine = quizDao.getQuizAllNumber();
		quiz = getRandomQuizByConfine(confine+"");
		return quiz ;
	}*/
	
	/**
	 * �ж��Ƿ���
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
	 * �õ����⽱����Ϣ
	 * @param quiz_id
	 * @return
	 */
	public  QuizVO  getAwardById( int quiz_id )
	{
		QuizDao quizDao = new QuizDao();
		return quizDao.getAwardById(quiz_id);
	}

	

}
