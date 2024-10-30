package com.pm.dao.question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.question.QuestionVO;

public class QuestionDao extends DaoBase {
	
	/**
	 * 更新用户积分
	 * @param pk
	 * @param mouth
	 */
	public void updateIntegeral(int pk,String mouth)
	{
		String sql = "UPDATE u_quiz_info SET integral = integral +1, conunite_win = conunite_win +1 WHERE p_pk="+pk+" AND mouth LIKE '%"+mouth+"%'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("sql"+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}


	/**
	 * 获得连续答对次数
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getConuniteWinNum(int pk, String nowMouth)
	{
		String sql = "SELECT * FROM u_quiz_info WHERE p_pk="+pk+" AND mouth = '"+nowMouth+"'";
		int win_num = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("sql"+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				win_num = rs.getInt("conunite_win");
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return win_num;
	}


	/**
	 * 获得个人的积分
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getIntegral(int pk, String nowMouth)
	{
		String sql = "SELECT * FROM u_quiz_info WHERE p_pk="+pk+" AND mouth = '"+nowMouth+"'";
		int win_num = 0;
		logger.debug("sql"+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				win_num = rs.getInt("integral");
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return win_num;
	}


	/**
	 * 
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getIntegralPaiMing(int pk, String nowMouth)
	{
		String sql = " SELECT (SELECT COUNT(1) FROM u_quiz_info WHERE integral >= (SELECT integral FROM u_quiz_info WHERE p_pk = "+pk
						+" AND mouth = '"+nowMouth+"') AND mouth = '"+nowMouth +"' ORDER BY integral DESC LIMIT 1) AS rank FROM u_quiz_info WHERE p_pk = "
						+pk+" AND mouth = '"+nowMouth+"'";
		String sql1 = "SELECT (SELECT COUNT(1) FROM u_quiz_info "
							+ "where integral >= (SELECT integral FROM u_quiz_info "
							+ "where p_pk = 9 AND mouth = '2008-11' ) "
							+ "AND p_pk = 9 AND mouth = '2008-11' ORDER BY integral DESC LIMIT 1) "
							+ "as rank FROM u_quiz_info WHERE p_pk = 9 AND mouth = '2008-11' ";
		int win_num = 0;
		logger.debug("sql"+sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				win_num = rs.getInt("rank");
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
		return win_num;
	}


	/**
	 * 插入个人答题信息表
	 * @param pk
	 * @param nowMouth
	 */
	public void insertQiueInfo(int pk, String nowMouth)
	{
		String sql = "INSERT INTO u_quiz_info VALUES( null,"+pk+",0,0,'"+nowMouth+"',0,now(),0)";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("sql"+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}


	/**
	 * 查看是否已经有本月的记录
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getHasThisMouth(int pk, String nowMouth)
	{
		int flag = 0;
		String sql = "SELECT COUNT(1) AS num FROM u_quiz_info WHERE p_pk = "+pk+" AND mouth = '"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				flag = rs.getInt("num");
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return flag;
	}


	/**
	 * 获得答题的排名
	 * @return
	 */
	public List<QuestionVO> getQuestionRanking(String nowMouth)
	{
		List<QuestionVO> ranklist = new ArrayList<QuestionVO>();
		QuestionVO questionvo = null;
		String sql = "SELECT id,up.p_pk,integral,mouth,p_name FROM u_quiz_info uq,u_part_info up WHERE mouth = '"+nowMouth+"' AND uq.p_pk = up.p_pk ORDER BY integral desc,last_time ASC LIMIT 10";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				questionvo = new QuestionVO();
				questionvo.setId(rs.getInt("id"));
				questionvo.setP_pk(rs.getInt("p_pk"));
				questionvo.setIntegral(rs.getInt("integral"));
				
				questionvo.setMouth(rs.getString("mouth"));
				questionvo.setPName(rs.getString("p_name"));
				ranklist.add(questionvo);
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return ranklist;
	}


	/**
	 * 将连续答题正确数置为0.
	 * @param pk
	 */
	public void updateupdateConutiuteWinToZero(int pk, String nowMouth)
	{
		String sql = "UPDATE u_quiz_info SET conunite_win = 0 WHERE p_pk = "+pk+" AND mouth = '"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("sql"+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
	}


	/**
	 * 将每月连续每日都答完十道题的天数计算下来.
	 * @param pk
	 */
	public void updateupdateTenAll(int pk, String nowMouth)
	{
		String sql = "UPDATE u_quiz_info SET conunite_day = conunite_day + 1 WHERE p_pk="+pk+" AND mouth='"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("sql"+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		
	}


	/**
	 * 将当前时间存入数据库并将答题标志置为1 
	 * @param pk
	 * @param nowMouth 当前月份
	 */
	public void updateQuestionTimeAndFlag(int pk, String nowMouth)
	{
		String sql = "UPDATE u_quiz_info SET last_time = now(), answer_flag = 1 WHERE p_pk="+pk+" AND mouth='"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("将当前时间存入数据库并将答题标志置为1="+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}


	/**
	 * 将当前玩家的答题标志置为flag.
	 * @param pk
	 * @param nowMouth
	 * @param i
	 */
	public void updateQuestionFlag(int pk, String nowMouth, int flag)
	{
		String sql = "UPDATE u_quiz_info SET answer_flag = 0  WHERE p_pk = "+pk+" AND mouth='"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("将当前玩家的答题标志置为flag.="+sql);
		
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
	}


	/**
	 * 得到玩家的答题标志
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getQuestionFlag(int pk, String nowMouth)
	{
		String sql = "SELECT answer_flag FROM u_quiz_info WHERE p_pk="+pk+" AND mouth='"
						+nowMouth+"' ";
		logger.debug("得到玩家的答题标志="+sql);
		int flag = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = rs.getInt("answer_flag");
			}
		}catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
		return flag;
	}
}
