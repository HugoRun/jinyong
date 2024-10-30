package com.ls.ben.dao.info.quiz;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.quiz.QuizVO;
import com.ls.iface.function.Probability;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:quiz_repository表操作
 * @author 刘帅 11:26:09 AM
 */
public class QuizDao extends DaoBase {
	/**
	 * 根据题目范围取到题目的出题概率
	 * @param confine
	 */
	public List<Probability> getQuizIdByConfine(String confine)
	{
		if( confine==null )
		{
			logger.debug("参数为空");
			return null;
		}
		String condition[] = confine.split(",");
		logger.debug("题库题目范围:" + confine);
		List<Probability> quizs = new ArrayList<Probability>();
		Probability quiz = null;
		String sql = "select quiz_id,quiz_probability from quiz_repository where quiz_id >= "+ condition[0]+" and quiz_id<="+ condition[1]+" order by quiz_id";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while( rs.next() )
			{
				quiz = new QuizVO();
				quiz.setId(rs.getInt("quiz_id"));
				quiz.setProbability(rs.getInt("quiz_probability"));
				quizs.add(quiz);
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return quizs;
	}
	
	/**
	 * 得到题目内容
	 * @param confine
	 */
	public QuizVO getContentById( int quiz_id )
	{
		logger.debug("题库题目id:" + quiz_id);
		QuizVO quiz = null;
		String sql = "select * from quiz_repository where quiz_id="+quiz_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				quiz = new QuizVO();
				quiz.setId(quiz_id);
				quiz.setQuizContent(StringUtil.isoToGBK(rs.getString("quiz_content")));
				quiz.setQuizAnswers(StringUtil.isoToGBK(rs.getString("quiz_answers")));
				quiz.setProbability(rs.getInt("quiz_probability"));
				quiz.setAwardExperience(rs.getLong("award_experience"));
				quiz.setAwardMoney(rs.getLong("award_money"));
				quiz.setQuziRightAnswer(rs.getInt("quzi_right_answer"));
				quiz.setAwardGoods(rs.getString("award_goods"));
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return quiz;
	}
	
	/**
	 * 加载题目内容
	 * @param confine
	 */
	public void loadContent( QuizVO quiz )
	{
		if( quiz==null )
		{
			logger.debug("参数为空");
			return;
		}
		logger.debug("题库题目id:" + quiz.getId());
		String sql = "select quiz_content,quiz_answers from quiz_repository where quiz_id="+quiz.getId();
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				//quiz = new QuizVO();
				quiz.setQuizContent(StringUtil.isoToGBK(rs.getString("quiz_content")));
				quiz.setQuizAnswers(StringUtil.isoToGBK(rs.getString("quiz_answers")));
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	
	/**
	 * 根据题目正确答案
	 * @param confine
	 */
	public int getRightAnswerById( int quiz_id ,int answer)
	{
		
		logger.debug("题库题目id:" + quiz_id);
		int  quiz_right_answer = -1;
		String sql = "select quiz_right_answer from quiz_repository where quiz_id="+quiz_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				quiz_right_answer = rs.getInt("quiz_right_answer");
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			logger.debug(e.toString());
		}finally
		{
			dbConn.closeConn();
		}
		return quiz_right_answer;
	}
	/**
	 * 根据题目得到题目奖励
	 * @param confine
	 */
	public QuizVO getAwardById( int quiz_id )
	{
		
		logger.debug("题库题目id:" + quiz_id);
		QuizVO quiz = null;
		String sql = "select * from quiz_repository where quiz_id="+quiz_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				quiz = new QuizVO();
				quiz.setId(quiz_id);
				quiz.setQuizContent(StringUtil.isoToGBK(rs.getString("quiz_content")));
				quiz.setQuizAnswers(StringUtil.isoToGBK(rs.getString("quiz_answers")));
				quiz.setProbability(rs.getInt("quiz_probability"));
				quiz.setAwardExperience(rs.getLong("award_experience"));
				quiz.setAwardMoney(rs.getLong("award_money"));
				quiz.setQuziRightAnswer(rs.getInt("quzi_right_answer"));
				quiz.setAwardGoods(rs.getString("award_goods"));	
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return quiz;
	}

	/**
	 * 得到所有题目的总数
	 * @return
	 */
	public int getQuizAllNumber()
	{
		int  quiz_size = -1;
		String sql = "select count(1) as quiz_size from quiz_repository";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				quiz_size = rs.getInt("quiz_size");
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			logger.debug(e.toString());
		}finally
		{
			dbConn.closeConn();
		}
		return quiz_size;
	}

	
	
	
	

}
