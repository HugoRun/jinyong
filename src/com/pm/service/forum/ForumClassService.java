package com.pm.service.forum;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.pub.db.DBConnection;
import com.pm.dao.forum.ForumClassDAOImpl;
import com.pm.vo.forum.ForumClassBean;

public class ForumClassService {

	Logger logger = Logger.getLogger(ForumClassService.class);
	private ForumClassDAOImpl fc=new ForumClassDAOImpl();
	DBConnection dbConn = null;
	Connection conn=null;
	
	public int addForumClass(ForumClassBean fcb)
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			fc.setConnection(conn);
			fc.addForumClass(fcb);
			conn.commit();
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			Database.rollback();
			return 0;
		}finally{
			dbConn.closeConn();
		}
	}
	public void deleteForumClass(int id)
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			fc.setConnection(conn);
			fc.deleteForumClass(id);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
	}
	/**
	 * 获得论坛分类列表
	 * @return
	 */
	public List<ForumClassBean> getAllForumClass()
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			fc.setConnection(conn);
			return fc.getAllForumClass();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList<ForumClassBean>();
	}
	
	
	public ForumClassBean getByID(int id)
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			fc.setConnection(conn);
			return fc.getByID(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			
		} finally {
			dbConn.closeConn();
		}
		return null;
	}
	public List getForumClass(int fid)
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			fc.setConnection(conn);
			return fc.getForumClass(fid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList();
	}
	public List getRowForumClass(int row,int fid)
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			fc.setConnection(conn);
			return fc.getRowForumClass(row,fid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
			
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList();
	}
	public void updateForumClass(ForumClassBean fcb)
	{
		dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			fc.setConnection(conn);
			fc.updateForumClass(fcb);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
	}
}
