package com.pm.dao.question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.question.QuestionVO;

public class QuestionDao extends DaoBase {
	
	/**
	 * �����û�����
	 * @param pk
	 * @param mouth
	 */
	public void updateIntegeral(int pk,String mouth)
	{
		String sql = "update u_quiz_info set integral = integral +1, conunite_win = conunite_win +1 where p_pk="+pk+" and mouth like '%"+mouth+"%'";
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
	 * ���������Դ���
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getConuniteWinNum(int pk, String nowMouth)
	{
		String sql = "select * from u_quiz_info where p_pk="+pk+" and mouth = '"+nowMouth+"'";
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
	 * ��ø��˵Ļ���
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getIntegral(int pk, String nowMouth)
	{
		String sql = "select * from u_quiz_info where p_pk="+pk+" and mouth = '"+nowMouth+"'";
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
		String sql = " select (select count(1) from u_quiz_info where integral >= (select integral from u_quiz_info where p_pk = "+pk
						+" and mouth = '"+nowMouth+"') and mouth = '"+nowMouth +"' order by integral desc limit 1) as rank from u_quiz_info where p_pk = "
						+pk+" and mouth = '"+nowMouth+"'";
		String sql1 = "select (select count(1) from u_quiz_info "
							+ "where integral >= (select integral from u_quiz_info "
							+ "where p_pk = 9 and mouth = '2008-11' ) "
							+ "and p_pk = 9 and mouth = '2008-11' order by integral desc limit 1) "
							+ "as rank from u_quiz_info where p_pk = 9 and mouth = '2008-11' ";
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
	 * ������˴�����Ϣ��
	 * @param pk
	 * @param nowMouth
	 */
	public void insertQiueInfo(int pk, String nowMouth)
	{
		String sql = "insert into u_quiz_info values( null,"+pk+",0,0,'"+nowMouth+"',0,now(),0)";
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
	 * �鿴�Ƿ��Ѿ��б��µļ�¼
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getHasThisMouth(int pk, String nowMouth)
	{
		int flag = 0;
		String sql = "select count(1) as num from u_quiz_info where p_pk = "+pk+" and mouth = '"+nowMouth+"'";
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
	 * ��ô��������
	 * @return
	 */
	public List<QuestionVO> getQuestionRanking(String nowMouth)
	{
		List<QuestionVO> ranklist = new ArrayList<QuestionVO>();
		QuestionVO questionvo = null;
		String sql = "select id,up.p_pk,integral,mouth,p_name from u_quiz_info uq,u_part_info up where mouth = '"+nowMouth+"' and uq.p_pk = up.p_pk order by integral desc,last_time asc limit 10";
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
	 * ������������ȷ����Ϊ0.
	 * @param pk
	 */
	public void updateupdateConutiuteWinToZero(int pk, String nowMouth)
	{
		String sql = "update u_quiz_info set conunite_win = 0 where p_pk = "+pk+" and mouth = '"+nowMouth+"'";
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
	 * ��ÿ������ÿ�ն�����ʮ�����������������.
	 * @param pk
	 */
	public void updateupdateTenAll(int pk, String nowMouth)
	{
		String sql = "update u_quiz_info set conunite_day = conunite_day + 1 where p_pk="+pk+" and mouth='"+nowMouth+"'";
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
	 * ����ǰʱ��������ݿⲢ�������־��Ϊ1 
	 * @param pk
	 * @param nowMouth ��ǰ�·�
	 */
	public void updateQuestionTimeAndFlag(int pk, String nowMouth)
	{
		String sql = "update u_quiz_info set last_time = now(), answer_flag = 1 where p_pk="+pk+" and mouth='"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("����ǰʱ��������ݿⲢ�������־��Ϊ1="+sql);
		
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
	 * ����ǰ��ҵĴ����־��Ϊflag.
	 * @param pk
	 * @param nowMouth
	 * @param i
	 */
	public void updateQuestionFlag(int pk, String nowMouth, int flag)
	{
		String sql = "update u_quiz_info set answer_flag = 0  where p_pk = "+pk+" and mouth='"+nowMouth+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("����ǰ��ҵĴ����־��Ϊflag.="+sql);
		
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
	 * �õ���ҵĴ����־
	 * @param pk
	 * @param nowMouth
	 * @return
	 */
	public int getQuestionFlag(int pk, String nowMouth)
	{
		String sql = "select answer_flag from u_quiz_info where p_pk="+pk+" and mouth='"
						+nowMouth+"' ";
		logger.debug("�õ���ҵĴ����־="+sql);
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
