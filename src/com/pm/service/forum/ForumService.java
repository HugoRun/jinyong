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
	 * ����һ������
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
	 * �������Ͳ�ͬ����������
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
			logger.info("������γ���");
		} finally {
			dbConn.closeConn();
		}
		return new ArrayList<ForumBean>();
	}
	
	/**
	 * ���ݰ��Ĳ�ͬ��ȡ��ͬ�������б�
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
			logger.info("������γ���");
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
			logger.info("������γ���");
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
			logger.info("������γ���");
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
			logger.info("������γ���");
		} finally {
			dbConn.closeConn();
		}
		return null;
	}
	
	/**
	 * ��ȡ��̳����̳��
	 */
	public String getForumNameById(String id) {
		return wg.getForumNameById(id);
	}
	
	/**
	 * ȷ�������Ƿ���ʮ����֮�ڷ�����, �����������true,
	 * ���û������false.
	 * @param pk
	 * @return
	 */
	public boolean isInTenMinute(int p_pk)
	{
		// ����ǹ���Ա,�ǾͲ���������ͷ���ʱ������.
		boolean isManager = this.isManager(p_pk);
		if (isManager) {
			return false;
		}
		return wg.isInTemMinute(p_pk);		
	}
	
	/**
	 * �鿴�����Ƿ��Ѿ�����ͬ���ݵ������ˣ�ʱ������Ϊʮ����.
	 * @param pk
	 * @param content
	 */
	public String haveSameContent(int p_pk, String content)
	{
		//boolean flag = wg.haveSameContent(content);
		if(wg.haveSameContent(content) == true ){
			if(content.equals("content")) {
				return "��ʮ����������ͬ���ݵ�������,�벻Ҫ��������ͬ���ݵ�����!";
			}else {
				return "��ʮ����������ͬ��Ŀ��������,�벻Ҫ��������ͬ��Ŀ������!";
			}
		} else {
			return "";
		}
	}
	
	/**
	 * �õ�����Ա�б�
	 * @param classId
	 * @return
	 */
	public List<String> getManagerList(String classId)
	{
		return wg.getManagerList(classId);
	}
	
	/**
	 * �鿴�Լ��Ƿ��ǹ���Ա
	 * @param pk
	 * @return
	 */
	public boolean isManager(int pPk)
	{
		return wg.isManager(pPk);
	}
	
	/**
	 * ����Ƿ񱻽�ֹ����,�������ֹ����false,�����ֹ����true
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
