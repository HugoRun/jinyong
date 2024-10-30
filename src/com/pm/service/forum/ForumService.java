package com.pm.service.forum;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.pm.dao.forum.ForumClassDAOImpl;
import com.pm.dao.forum.ForumDAOImpl;
import com.pm.vo.forum.ForumBean;
import com.pm.vo.forum.ForumClassBean;
import com.pm.vo.forum.ForumForbidVO;

public class ForumService {

	static Logger logger = Logger.getLogger(ForumService.class);
	private ForumDAOImpl wg=new ForumDAOImpl();
	DBConnection dbConn = null;
	
	/**
	 * 加入一个发言
	 * @param fb
	 * @return
	 */
	public int addForum(ForumBean fb)
	{  
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			dbConn.begin();
			wg.setConnection(conn);
			wg.addForum(fb);
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
	/**
	 * 根据类型不同更新其数量
	 * @param id
	 * @param name
	 * @return
	 */
	public int updateNum(int id,String name)
	{
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			wg.setConnection(conn);
			int m=wg.updateNum(id,name);
			//conn.commit();
			return m;
		}catch(Exception e){
			e.printStackTrace();
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
		return 0;
	}
	public void updateForum(ForumBean fb){
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			wg.setConnection(conn);
			wg.updateForum(fb);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
	}
	public void deleteForum(int id)
	{
		Connection conn=null;
		try{
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			wg.setConnection(conn);
			wg.deleteForum(id);
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			Database.rollback();
		}finally{
			dbConn.closeConn();
		}
	}
	
	public List<ForumBean> getAllForum(int typeid)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			
			connection = dbConn.getConn();
			wg.setConnection(connection);
			return wg.getAllForum(typeid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList<ForumBean>();
	}
	
	/**
	 * 根据板块的不同获取不同的帖子列表
	 * @param typeid
	 * @return
	 */
	public QueryPage getAllForumByClassId(int classId,int page_no)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			wg.setConnection(connection);
			return wg.getAllForumByClassId(classId,page_no);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
		} finally {
			dbConn.closeConn();
		}
		return null;
	}
	/*public List<ForumBean> getForumByClassID1(int classid, int typeid)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
			connection = dbConn.getConn();
			wg.setConnection(connection);
			return wg.getForumByClassID(classid,typeid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList<ForumBean>();
	}*/
	public List<ForumBean> getRowForum(int row, int typeid)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			wg.setConnection(connection);
			return wg.getRowForum(row,typeid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList<ForumBean>();
	}
	public  ForumBean getByID(int id)
	{
		Connection connection = null;
		try {
			dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			connection = dbConn.getConn();
			wg.setConnection(connection);
			return wg.getByID(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得网游出错！");
		} finally {
			dbConn.closeConn();
		}
		return null;
	}
	
	/**
	 * 获取论坛的论坛名
	 */
	public String getForumNameById(String id) {
		return wg.getForumNameById(id);
	}
	
	/**
	 * 确定此人是否在十分钟之内发过帖, 如果发过返回true,
	 * 如果没发返回false.
	 * @param pk
	 * @return
	 */
	public boolean isInTenMinute(int p_pk)
	{
		// 如果是管理员,那就不计算回帖和发帖时间限制.
		boolean isManager = this.isManager(p_pk);
		if (isManager) {
			return false;
		}
		return wg.isInTemMinute(p_pk);		
	}
	
	/**
	 * 查看此贴是否已经有相同内容的帖子了，时间现在为十分钟.
	 * @param pk
	 * @param content
	 */
	public String haveSameContent(int p_pk, String content)
	{
		//boolean flag = wg.haveSameContent(content);
		if(wg.haveSameContent(content) == true ){
			if(content.equals("content")) {
				return "在十分钟内有相同内容的帖子了,请不要连续发相同内容的帖子!";
			}else {
				return "在十分钟内有相同题目的帖子了,请不要连续发相同题目的帖子!";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * 得到管理员列表
	 * @param classId
	 * @return
	 */
	public List<String> getManagerList(String classId)
	{
		return wg.getManagerList(classId);
	}
	
	/**
	 * 查看自己是否是管理员
	 * @param pk
	 * @return
	 */
	public boolean isManager(int pPk)
	{
		return wg.isManager(pPk);
	}
	
	/**
	 * 检查是否被禁止发帖,如果不禁止返回false,如果禁止返回true
	 * @param pk
	 * @return
	 */
	public boolean checkForbid(int pPk)
	{
		ForumClassDAOImpl forumClassDAOImpl = new ForumClassDAOImpl();
		ForumForbidVO forumForbidVO = forumClassDAOImpl.isForBidIng(pPk+"");
		
		if (forumForbidVO != null) {
			Date dt = new Date();
			System.out.println(dt);
			Date forbidTimeDate = null;
			System.out.println(forumForbidVO.getForbidEndTime());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				forbidTimeDate = simpleDateFormat.parse(forumForbidVO.getForbidEndTime());
				System.out.println(forbidTimeDate);
				if ( forbidTimeDate.getTime() + forumForbidVO.getAddTime()* 60000 > dt.getTime()) {
					return true;
				} else {
					forumClassDAOImpl.deleteForumForbid(pPk);
				}
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}	
		
		return false;
	}
}
