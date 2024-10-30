package com.pm.dao.forum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;
import com.pm.vo.forum.ForumBean;


public class ForumDAOImpl extends DaoBase {

	static Logger logger = Logger.getLogger(ForumDAOImpl.class);
	Connection connection = null;
	
	
	public void setConnection(Connection conn) {
		this.connection = conn;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public int addForum(ForumBean fb) throws Exception {
		String sql="insert into g_forum(classID,UserID,UserName,title,content,add_time,readNum,revertNum,vouch,taxis,revert_time) values(?,?,?,?,?,now(),?,?,?,?,now())";			
		logger.debug("���뻰���sql="+sql);
		PreparedStatement ps = connection.prepareStatement(sql);
		int i=1;
		ps.setInt(i++,fb.getClassID());
		ps.setInt(i++,fb.getUserID());
		ps.setString(i++,fb.getUserName());
		ps.setString(i++,fb.getTitle());
		ps.setString(i++,fb.getContent());
		//ps.setString(i++,fb.getAddTime());
		ps.setInt(i++,fb.getReadNum());
		ps.setInt(i++,fb.getRevertNum());
		ps.setInt(i++,fb.getVouch());
		ps.setInt(i++,fb.getTaxis());
		int m=ps.executeUpdate();
		ps.close();		
		return m;
	}

	
	public void deleteForum(int id) throws Exception {
		//ɾ�����������лظ�
		StringBuffer sql1 = new StringBuffer("delete from g_forum_revert where fid=").append(id);
		// ɾ������
		StringBuffer sql2 = new StringBuffer("delete from g_forum where id=").append(id);
		
		logger.debug("ɾ�����������лظ�sql :"+sql1);
		logger.debug("ɾ������sql :"+sql1);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql1.toString());
			stmt.executeUpdate(sql2.toString());
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}

	public List<ForumBean> getForum(int classID)throws Exception {
		//Ĭ�������µ�����
		String sql="select g_forum.*,g_forum_class.className from g_forum join g_forum_class on g_forum_class.classID=g_forum.classID order by id desc";
		if(classID!=0)//����
		{
			sql="select g_forum.*,g_forum_class.className from g_forum join g_forum_class on g_forum_class.classID=g_forum.classID where classID="+classID+" order by id desc";
		}
		logger.debug(sql.toString());
		List<ForumBean> v = new ArrayList<ForumBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		     while (rs.next()) {
		    	 ForumBean gi = new ForumBean();
		    	 gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("classID"));
			       gi.setClassName(rs.getString("className"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
		       v.add(gi);
		     }
		     rs.close();
		     ps.close();
		     return v;
	}
	
	
	public List<ForumBean> getAllForum(int typeid) throws Exception {
		//Ĭ�������µ�����
		String sql="select g_forum.*,g_forum_class.className from g_forum join g_forum_class on g_forum_class.classID=g_forum.classID order by id desc";
		if(typeid==2)//����
		{
			sql="select * from g_forum order by revertNum desc,id desc";
		}
		if(typeid==3)//����
		{
			sql="select * from g_forum order by taxis desc,id desc";
		}
		if(typeid==4)//ǿ���Ƽ�
		{
			sql="select * from g_forum  where vouch=1 order by id desc";
		}
		logger.debug(sql.toString());
		List<ForumBean> v = new ArrayList<ForumBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumBean gi = new ForumBean();
		    	 gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("classID"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       //gi.setContent(rs.getString("content"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
			       gi.setAddTime(rs.getString("add_time"));
		       v.add(gi);
		     }
		     rs.close();
		     ps.close();
		     return v;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}

	
	/**
	 * ���ݰ��Ĳ�ͬ��ȡ��ͬ�������б�
	 * @param typeid
	 * @return
	 */
	public QueryPage getAllForumByClassId(int classId,int page_no) throws Exception {
		QueryPage queryPage = null;
		int count = 0;
		stmt = connection.createStatement();
		String count_sql = "select count(*) from g_forum where taxis = 0 and classID=" + classId;
		logger.debug(count_sql);
		rs = stmt.executeQuery(count_sql);
		if( rs.next() )
		{
			count = rs.getInt(1);
		}
		rs.close();
		
		if ( page_no > (count%5==0?count/5:(count/5+1))) {
			page_no = (count%5==0?count/5:(count/5+1));
		}
		
		//String className = getForumNameById(classId+"");
		queryPage = new  QueryPage(page_no,count,5);
		List<ForumBean> v = new ArrayList<ForumBean>();
		if ( count == 0) {
			queryPage.setResult(v);
			return queryPage;
		}
		
		//���ӵ�����˳��, �Ȱ��Ƿ��ö�����,Ȼ������ظ�ʱ������,��󰴷���ʱ������
		String sql="select g_forum.id,g_forum.classID,g_forum.UserID,g_forum.UserName,g_forum.title,g_forum.content,g_forum.readNum,g_forum.revertNum" +
								",g_forum.vouch,g_forum.taxis,g_forum.add_time from g_forum  where taxis = 0 and g_forum.classId = "
						+classId+" order by revert_time desc,id desc limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
		//System.out.println(sql.toString()); 
		logger.debug(sql.toString());
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumBean gi = new ForumBean();
		    	 gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("g_forum.classID"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       //gi.setContent(rs.getString("content"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
			       gi.setVouch(rs.getInt("vouch"));
			       gi.setTaxis(rs.getInt("taxis"));
			       gi.setAddTime(rs.getString("add_time"));
		       v.add(gi);
		     }
		     rs.close();
		     ps.close();
		     queryPage.setResult(v);
		     return queryPage;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}
	
	public  ForumBean getByID(int id) throws Exception {
		String sql="select f.*,fc.className from g_forum as f join g_forum_class as fc on f.classID=fc.classID where id="+id;
		logger.debug(sql.toString());
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
			 ForumBean gi=null;
		     if(rs.next()) {
		    	   gi= new ForumBean();
			       gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("classID"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setContent(rs.getString("content"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
			       gi.setTaxis(rs.getInt("taxis"));
			       gi.setVouch(rs.getInt("vouch"));
			       gi.setAddTime(rs.getString("add_time"));
			       gi.setClassName(rs.getString("className"));
			      // logger.debug("gi.getclassN:"+gi.getClassName());
		     }
		     rs.close();
		     ps.close();
		     
		     return gi;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}


	
	/*public List<ForumBean> getForumByClassID(int classid, int typeid) throws Exception {
		String sql="select * from g_forum where classID="+classid+" order by id desc";
		if(typeid==2)
		{
			sql="select * from g_forum  where classID="+classid+" order by revertNum desc,id desc";
		}
		if(typeid==4)
		{
			sql="select * from g_forum  where vouch=1 order by id desc";
		}
		logger.debug(sql.toString());
		List<ForumBean> v = new ArrayList<ForumBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumBean gi = new ForumBean();
		    	 gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("classID"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       //gi.setContent(rs.getString("content"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
			      // gi.setAddTime(rs.getString("add_time"));
		       v.add(gi);
		     }
		     rs.close();
		     ps.close();
		     return v;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}*/


	
	public List<ForumBean> getRowForum(int row, int typeid) throws Exception {
		
		String sql="select top "+row+" * from g_forum  order by id desc";
		if(typeid==4)//�˴�����vouch=2�ǻ����̳��ҳҪ��ʾ������
		{
			sql="select top "+row+" * from g_forum  where vouch=2 order by id desc";
		}
		logger.debug(sql.toString());
		List<ForumBean> v = new ArrayList<ForumBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumBean gi = new ForumBean();
		    	 gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("classID"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       //gi.setContent(rs.getString("content"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
			      // gi.setAddTime(rs.getString("add_time"));
		       v.add(gi);
		     }
		     rs.close();
		     ps.close();
		     return v;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}

	
	public void updateForum(ForumBean fb) throws Exception {
		String sql="update g_forum set title=?,content=?,vouch=?,taxis=? where id=?";			
		PreparedStatement ps = connection.prepareStatement(sql);
		int i=1;
		ps.setString(i++,fb.getTitle());
		ps.setString(i++,fb.getContent());
		ps.setInt(i++,fb.getVouch());
		ps.setInt(i++,fb.getTaxis());
		ps.setInt(i++,fb.getId());
		int m=ps.executeUpdate();
		ps.close();
	}
	
	
	/**
	 * �������ӵ��������
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public int updateNum(int id,String name)throws Exception{
		// TODO Auto-generated method stub
		name=name.trim();
		String sql="update g_forum set "+name+"="+name+"+1 where id="+id;			
		PreparedStatement ps = connection.prepareStatement(sql);
		int m=ps.executeUpdate();
		ps.close();
		return m;
	}
	
	/**
	 * �������ӵĻظ����ͻ���ʱ��
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public void updateRevertTime(int id)throws Exception{
		
		String sql = "update g_forum set revertNum = revertNum + 1 ,revert_time = now() where id="+id;
		logger.debug("�������ӵĻظ����ͻ���ʱ���sql :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ����id��ȡ��̳����̳��
	 * @param id
	 * @return
	 */
	public String getForumNameById(String classID)
	{
		String sql = "select className from g_forum_class where classID="+classID;
		String className = "";
		logger.debug("����id��ȡ��̳����̳����sql :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				className = rs.getString("className");
			}
			rs.close();
			stmt.close();
			
			
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return className;	
	}
	
	/**
	 * �õ������ö�������
	 * @return
	 */
	public List<ForumBean> getAllTaxis( int classId) {
		List<ForumBean> list = new ArrayList<ForumBean>();
		String sql = "select * from g_forum where classID = "+classId+" and taxis > 0";
		logger.debug("�õ������ö������ӵ�sql :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ForumBean gi = null;
			while (rs.next()) {
		    	   gi = new ForumBean();
		    	   gi.setId(rs.getInt("id"));
			       gi.setClassID(rs.getInt("classID"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setTaxis(rs.getInt("taxis"));
			       gi.setVouch(rs.getInt("vouch"));
			       gi.setTitle(rs.getString("title"));
			       gi.setReadNum(rs.getInt("readNum"));
			       gi.setRevertNum(rs.getInt("revertNum"));
			       gi.setAddTime(rs.getString("add_time"));
			       list.add(gi);
			     }
			}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return list;
	}

	/**
	 * ȷ�������Ƿ���ʮ����֮�ڷ�����, �����������true,
	 * ���û������false.
	 * @param pk
	 * @return
	 */
	public boolean isInTemMinute(int p_pk)
	{
		String sql = "select * from g_forum where UserID = "+p_pk+" and now() < (add_time + INTERVAL 10 minute)";
		logger.debug("ȷ�������Ƿ���ʮ����֮�ڷ�������sql :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		boolean flag = false;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
				
			}
		}catch (SQLException e){
			e.printStackTrace();
		} finally	{
			dbConn.closeConn();
		}
		return flag;
	}

	/**
	 * ȷ���������Ƿ���ʮ�������й���ͬ���ݵ�����
	 * @param content
	 * @return
	 */
	public boolean haveSameContent(String content)
	{
		String sql = "select id from g_forum where now() < (add_time + INTERVAL 100000 minute) and content = '"
					+StringUtil.gbToISO(content)+"'";
		logger.debug("ȷ�������Ƿ���ʮ����֮�ڷ�������sql :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		boolean flag = false;
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag = true;
				
			}
		}catch (SQLException e){
			e.printStackTrace();
		} finally	{
			dbConn.closeConn();
		}
		return flag;
		
	}

	/**
	 * �õ�����Ա�б�
	 * @param classId
	 * @return
	 */
	public List<String> getManagerList(String classId)
	{
		List<String> managerList = new ArrayList<String>();
		//String sql = "select UserName from g_forum_manager where classID ='"+classId+"'";
		String sql = "select UserName from g_forum_manager";
        logger.debug("�õ�����Ա�б�="+sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try{
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	while (rs.next()) {
        		managerList.add(rs.getString("UserName"));        		
        	}
        }catch (SQLException e){
        	e.printStackTrace();
        } finally	{
        	dbConn.closeConn();
        }
		
		return managerList;
	}

	/**
	 * �Ƿ��ǹ���Ա
	 * @param pk
	 * @return
	 */
	public boolean isManager(int pPk)
	{
		String sql = "select UserName from g_forum_manager where UserID ='"+pPk+"'";
        logger.debug("�õ�����Ա�б�="+sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try{
        	stmt = conn.createStatement();
        	rs = stmt.executeQuery(sql);
        	if (rs.next()) {
        		return true;        		
        	}
        }catch (SQLException e){
        	e.printStackTrace();
        } finally	{
        	dbConn.closeConn();
        }
		return false;			
	}

	/**
	 * �����ö�����
	 * @param parseInt
	 * @param state
	 */
	public void zhiDing(int page_id, int state)
	{
		String sql = "update g_forum set taxis = "+state + " where id = "+page_id;
        logger.debug("�����ö�����="+sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try{
        	stmt = conn.createStatement();
        	stmt.executeUpdate(sql);
        	stmt.close();
        }catch (SQLException e){
        	e.printStackTrace();
        } finally	{
        	dbConn.closeConn();
        }
		
	}

	/**
	 * ������������
	 * @param page_id
	 * @param state
	 */
	public void lockForum(int page_id, int state)
	{
		String sql = "update g_forum set vouch = "+state + " where id = "+page_id;
        logger.debug("������������="+sql);
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
        conn = dbConn.getConn();
        try{
        	stmt = conn.createStatement();
        	stmt.executeUpdate(sql);
        	stmt.close();
        }catch (SQLException e){
        	e.printStackTrace();
        } finally	{
        	dbConn.closeConn();
        }		
	}
}
