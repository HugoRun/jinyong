package com.pm.service.forum;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.pm.dao.forum.ForumDAOImpl;
import com.pm.dao.forum.ForumRevertDAOImpl;
import com.pm.vo.forum.ForumRevertBean;


public class ForumRevertService extends BaseService {

	static Logger logger = Logger.getLogger(ForumRevertService.class);
	private ForumRevertDAOImpl fr=new ForumRevertDAOImpl();
	DBConnection dbConn = null;
	
	/**
	 * 增加帖子的回复帖子
	 * @param frb
	 * @return
	 */
	public int addForumRevert(ForumRevertBean frb)
	{
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			fr.setConnection(conn);
			fr.addForumRevert(frb);
			ForumDAOImpl f=new ForumDAOImpl();
			
			f.updateRevertTime(frb.getFid());
			//conn.commit();
			return 1;
		} catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
			Database.rollback();
			return 0;
		} finally{
			dbConn.closeConn();
		}
	}
	public void deleteForumRevert(int id)
	{
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			fr.setConnection(conn);
			fr.deleteForumRevert(id);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
	}
	public void updateForumRevert(ForumRevertBean frb)
	{
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			fr.setConnection(conn);
			fr.updateForumRevert(frb);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			message = e.getMessage();
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
	}
	public int getNum(int fid)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			fr.setConnection(connection);
			return fr.getNum(fid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			message = e.getMessage();
		} finally {
			dbConn.closeConn();
		}
		return 0;
	}
	public QueryPage getAllForumRevert(int fid,int page_no)
	{
		Connection connection = null;
		try { 
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			fr.setConnection(connection);
			return fr.getAllForumRevert(fid,page_no);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			message = e.getMessage();
		} finally {
			dbConn.closeConn();
		}
		return null;
	}
	
	/**
	 * 得到指定的回复
	 * @param row
	 * @param fid
	 * @return
	 */
	public List getRowForumRevert(int row, int fid)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			fr.setConnection(connection);
			return fr.getRowForumRevert(row,fid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			message = e.getMessage();
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList();
	}
	
	/**
	 * 得到指定的回复
	 * @param row
	 * @param fid
	 * @return
	 */
	public ForumRevertBean getForumRevertById(String revertId)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			fr.setConnection(connection);
			return fr.getForumRevertById(revertId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			message = e.getMessage();
		} finally {
			dbConn.closeConn();
		}
		return null;
	}
	
	/**
	 * 确定此人是否在二分钟内发过帖.如果发过返回true,
	 * 否则返回false.
	 * @param pk
	 * @return
	 */
	public boolean isInTwoMinute(int pPk)
	{
		// 如果是管理员,那就不计算回帖和发帖时间限制.
		ForumService fas=new ForumService();
		boolean isManager = fas.isManager(pPk);
		if (isManager) {
			return false;
		}
		return fr.isInTwoMinute(pPk);
		
		
	}
}
