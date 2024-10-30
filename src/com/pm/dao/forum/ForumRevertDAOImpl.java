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
import com.pm.vo.forum.ForumRevertBean;


public class ForumRevertDAOImpl extends DaoBase {

	static Logger logger = Logger.getLogger(ForumRevertDAOImpl.class);
	Connection connection = null;
	
	
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.connection = conn;
	}
	
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return connection;
	}
	
	/**
	 * 回复帖子
	 * @param frb
	 * @return
	 * @throws Exception
	 */
	public int addForumRevert(ForumRevertBean frb) throws Exception {
		
		String sql="insert into g_forum_revert(fid,UserID,UserName,content,addTime) values(?,?,?,?,now())";			
		PreparedStatement ps = connection.prepareStatement(sql);
		logger.debug(sql.toString());
		int i=1;
		ps.setInt(i++,frb.getFid());
		ps.setInt(i++,frb.getUserID());
		ps.setString(i++,frb.getUserName());
		ps.setString(i++,frb.getContent());
		//ps.setString(i++,frb.getAddTime());
		int m=ps.executeUpdate();
		ps.close();		
		return m;
	}
	public void deleteForumRevert(int id) throws Exception {
		// TODO Auto-generated method stub
		//删除留言回复
		StringBuffer sql=new StringBuffer("delete from g_forum_revert where id=").append(id);
		PreparedStatement ps = connection.prepareStatement(sql.toString());
		int m=ps.executeUpdate();
		ps.close();	
	}

	/**
	 * 按照页面获取指定的文章回复
	 * @param fid
	 * @param page_no
	 * @return
	 * @throws Exception
	 */
	public QueryPage getAllForumRevert(int fid,int page_no) throws Exception {
		QueryPage queryPage = null;
		 int count = 0;
		 
		 stmt = connection.createStatement();
			String count_sql = "select count(*) from g_forum_revert where fid=" + fid;
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
			
			
		queryPage = new  QueryPage(page_no,count,5);
		List<ForumRevertBean> list = new ArrayList<ForumRevertBean>();	
		if ( count == 0) {
			queryPage.setResult(list);
			return queryPage;
		}
		
		
		String sql="select * from g_forum_revert where fid="+fid+" order by id desc limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
		logger.debug(sql.toString());
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumRevertBean gi = new ForumRevertBean();
			       gi.setId(rs.getInt("id"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setContent(rs.getString("content"));
			       gi.setAddTime(rs.getString("addTime"));
			       list.add(gi);
		     }
		     rs.close();
		     ps.close();
		     queryPage.setResult(list);
		     return queryPage;
		   }
		   catch (SQLException ex) {
		     return null;
		   }
	}

	
	

	
	public int getNum(int fid) throws Exception {
		String sql="select count(*) from g_forum_revert where fid="+fid;
		logger.debug(sql.toString());
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
			   int m=0;
		    if(rs.next()) {
		    	m=rs.getInt(1);
		     }
		     rs.close();
		     ps.close();
		     return m;
		   }
		   catch (SQLException ex) {
		     return 0;
		   }
	}

	/**
	 * 得到指定的回复
	 * @param row
	 * @param fid
	 * @return
	 */
	public List<ForumRevertBean> getRowForumRevert(int row, int fid) throws Exception {
		String sql="select * from g_forum_revert where fid="+fid+" order by id desc limit 0,"+row;
		logger.debug(sql.toString());
		List<ForumRevertBean> v = new ArrayList<ForumRevertBean>();
		Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
		   try {
		     while (rs.next()) {
		    	 ForumRevertBean gi = new ForumRevertBean();
			       gi.setId(rs.getInt("id"));
			       gi.setUserID(rs.getInt("userID"));
			       gi.setUserName(rs.getString("userName"));
			       gi.setContent(rs.getString("content"));
			       gi.setAddTime(rs.getString("addTime"));
			       //gi.setUserImage(rs.getString("userImage"));
			      // gi.setNickName(rs.getString("nickName"));
			       //gi.setVIP(rs.getInt("vip"));
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

	
	public void updateForumRevert(ForumRevertBean frb) throws Exception {
		// TODO Auto-generated method stub
		String sql="update g_forum_revert set content=? where id=?";			
		PreparedStatement ps = connection.prepareStatement(sql);
		int i=1;
		ps.setString(i++,frb.getContent());
		ps.setInt(i++,frb.getId());
		int m=ps.executeUpdate();
		ps.close();
	}

	/**
	 * 
	 * @param row
	 * @param fid
	 * @return
	 */
	public ForumRevertBean getForumRevertById(String revertId)
	{
		String sql="select * from g_forum_revert where id="+ revertId;	
	   ForumRevertBean revertbean = null;
	   try {
		   Statement ps = connection.createStatement();		   
		   ResultSet rs = ps.executeQuery(sql);
	     while (rs.next()) {
	    	 revertbean = new ForumRevertBean();
	    	 revertbean.setId(Integer.valueOf(revertId));
	    	 revertbean.setFid(rs.getInt("fid"));
	    	 revertbean.setUserID(rs.getInt("UserID"));
	    	 revertbean.setUserName(rs.getString("UserName"));
	    	 revertbean.setContent(rs.getString("content"));
	    	 revertbean.setAddTime(rs.getString("addTime"));
	     }
	   }
	   catch (SQLException ex) {
	     return null;
	   }
	  return revertbean; 
		
	}

	
	/**
	 * 确定此人是否在二分钟内发过帖.如果发过返回true,
	 * 否则返回false.
	 * @param pk
	 * @return
	 */
	public boolean isInTwoMinute(int p_pk)
	{
		String sql = "select * from g_forum_revert where UserID = "+p_pk+" and now() < (addTime + INTERVAL 1 minute)";
		logger.debug("确定此人是否在十分钟之内发过帖的sql :"+sql);
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

}
