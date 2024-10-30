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
import com.ls.pub.db.DBConnection;
import com.pm.vo.forum.ForumClassBean;
import com.pm.vo.forum.ForumForbidVO;



public class ForumClassDAOImpl extends DaoBase {

	static Logger logger = Logger.getLogger(ForumClassDAOImpl.class);
	Connection connection = null;
	
	
	public void setConnection(Connection conn) {
		this.connection = conn;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public int addForumClass(ForumClassBean fcb) throws Exception {
		String sql="insert into g_forum_class(fid,UserID,UserName,className,smallName,addTime) values(?,?,?,?,?,?)";			
		PreparedStatement ps = connection.prepareStatement(sql);
		int i=1;
		ps.setInt(i++,fcb.getFid());
		ps.setInt(i++,fcb.getUserID());
		ps.setString(i++,fcb.getUserName());
		ps.setString(i++,fcb.getClassName());
		ps.setString(i++,fcb.getSmallName());
		ps.setString(i++,fcb.getAddTime());
		int m=ps.executeUpdate();
		ps.close();		
		return m;
	}

	
	public void deleteForumClass(int id) throws Exception {
		// TODO Auto-generated method stub
		//ɾ����������Ŀ
		StringBuffer sql=new StringBuffer("delete from g_forum_class where fid=").append(id);
		PreparedStatement ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		
		//ɾ������
		sql.delete(0,sql.length()-1); 
		sql.append("delete from g_forum_class where classID=").append(id);
		logger.debug("ɾ������: "+sql.toString());
		ps = connection.prepareStatement(sql.toString());
		ps.executeUpdate();
		ps.close();	
	}

	
	public List<ForumClassBean> getAllForumClass() throws Exception {
		String sql="select * from g_forum_class order by classID asc";
		logger.debug(sql.toString());
		List<ForumClassBean> v = new ArrayList<ForumClassBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   ForumClassBean gi = null;
		   try {
		     while (rs.next()) {
		    	   gi = new ForumClassBean();
			       gi.setClassID(rs.getInt("classID"));
			       gi.setFid(rs.getInt("fid"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setClassName(rs.getString("className"));
			       gi.setSmallName(rs.getString("smallName"));
			       gi.setAddTime(rs.getString("addTime"));
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

	
	public ForumClassBean getByID(int id) throws Exception {
		String sql="select * from g_forum_class where classID="+id;
		logger.debug(sql.toString());
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
			   ForumClassBean gi =null;
		    if(rs.next()) {
		    	  gi=new ForumClassBean();
			       gi.setClassID(rs.getInt("classID"));
			       gi.setFid(rs.getInt("fid"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setClassName(rs.getString("className"));
			       gi.setAddTime(rs.getString("addTime"));
		     }
		     rs.close();
		     ps.close();
		     return gi;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}

	

	
	public List<ForumClassBean> getForumClass(int fid) throws Exception {
		String sql="select * from g_forum_class where fid="+fid+" order by classID desc";
		logger.debug(sql.toString());
		List<ForumClassBean> v = new ArrayList<ForumClassBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumClassBean gi = new ForumClassBean();
			       gi.setClassID(rs.getInt("classID"));
			       gi.setFid(rs.getInt("fid"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setClassName(rs.getString("className"));
			       gi.setSmallName(rs.getString("smallName"));
			       gi.setAddTime(rs.getString("addTime"));
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
	public List<ForumClassBean> getRowForumClass(int row,int fid)throws Exception {
		String sql="select top "+row+" * from g_forum_class where fid="+fid+" order by classID desc";
		logger.debug(sql.toString());
		List<ForumClassBean> v = new ArrayList<ForumClassBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumClassBean gi = new ForumClassBean();
			       gi.setClassID(rs.getInt("classID"));
			       gi.setFid(rs.getInt("fid"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setClassName(rs.getString("className"));
			       gi.setSmallName(rs.getString("smallName"));
			       gi.setAddTime(rs.getString("addTime"));
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
	

	
	public void updateForumClass(ForumClassBean fcb) throws Exception {
		String sql="update g_forum_class set fid=?,userID=?,userName=?,className=? where classID=?";			
		PreparedStatement ps = connection.prepareStatement(sql);
		int i=1;
		ps.setInt(i++,fcb.getFid());
		ps.setInt(i++,fcb.getUserID());
		ps.setString(i++,fcb.getUserName());
		ps.setString(i++,fcb.getClassName());
		ps.setInt(i++,fcb.getClassID());
		ps.executeUpdate();
		ps.close();
	}

	/**
	 * �鿴��pPk�Ƿ��ڽ�ֹ����������
	 * @param pk
	 * @return
	 */
	public ForumForbidVO isForBidIng(String pPk)
	{
		ForumForbidVO forbidVO = null;
		String sql="select * from g_forum_forbid where p_pk="+pPk;			
		logger.debug(sql.toString());
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			
		try {
			conn = dbConn.getConn();
		    Statement stmt = conn.createStatement();	
		  ResultSet rs = stmt.executeQuery(sql);
			
		  if(rs.next()) {
			  forbidVO = new ForumForbidVO();
			  forbidVO.setFrId(rs.getInt("fr_id"));
			  forbidVO.setForbidType(rs.getInt("forbid_type"));
			  forbidVO.setPPk(rs.getInt("p_pk"));
			  forbidVO.setPName(rs.getString("p_name"));
			  forbidVO.setAddTime(rs.getInt("add_time"));
			  forbidVO.setForbidEndTime(rs.getString("forbid_end_time"));			  
		   }
		     rs.close();
		     stmt.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			dbConn.closeConn();
		}
		return forbidVO;
	}

	/**
	 * ��ֹ����,����Ҽӵ���ֹ���������
	 * @param pk
	 * @param name
	 * @param type
	 */
	public void addForbidName(String pk, String name, int type,int forbid_time)
	{
		String sql = "insert into g_forum_forbid values (null,"+type+","+pk+",'"+name+"', "+forbid_time
						+",now())";
        logger.debug("����Ҽӵ���ֹ���������="+sql.toString());
        DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
		    Statement stmt = conn.createStatement();	
		    stmt.execute(sql);
		    stmt.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			dbConn.closeConn();
		}	
	}

	/**
	 * ɾ������
	 * @param pk
	 */
	public void deleteForumForbid(int pPk)
	{
		String sql = "delete from g_forum_forbid where p_pk = "+pPk;
		logger.debug("����Ҽӵ���ֹ���������="+sql.toString());
	    DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
		    Statement stmt = conn.createStatement();	
		    stmt.execute(sql);
		    stmt.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
		}finally {
			dbConn.closeConn();
		}	
		
	}

}
