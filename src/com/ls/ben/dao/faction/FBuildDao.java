package com.ls.ben.dao.faction;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.organize.faction.FBuild;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 帮派建筑
 */
public class FBuildDao extends BasicDaoSupport<FBuild>
{
	public FBuildDao()
	{
		super("f_build", DBConnection.GAME_USER_DB);
	}

	/**
	 * 得到帮派建筑数量
	 * @param fId
	 * @return
	 */
	public int getNumByFId( int fId )
	{
		int num = 0;
		String sql = "select count(*) from f_build where f_id="+fId;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				num = rs.getInt(1);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return num;
	}
	
	/**
	 * 升级
	 * @param fBuild
	 */
	public void upgrade( FBuild fBuild,int newBId )
	{
		if( fBuild==null )
		{
			return;
		}
		String sql = "update f_build  set " +
		"b_id=?"+
		" where f_id=? and b_id=?";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,newBId);
			ps.setInt(2,fBuild.getFId());
			ps.setInt(3,fBuild.getBId());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.toString();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 添加
	 * @param fId		帮派id
	 * @param bId		建筑id
	 * @return
	 */
	public int add( int fId,int bId)
	{
		int key = 0;
		
		String sql = "insert into f_build(f_id,b_id,b_1_grade_id) values (?,?,?)";

		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fId);
			ps.setInt(2, bId);
			ps.setInt(3, bId);
			ps.executeUpdate();
			ps.close();
		}catch (SQLException e1)
		{
			logger.debug(e1.toString());
		}
		catch (Exception e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return key;
	}
	
	/**
	 * 得到帮派已建好的一级建筑的id字符串
	 */
	public String get1GradeBuildIdList( int fId )
	{
		StringBuffer sb = new StringBuffer();
		String sql = "select b_1_grade_id from f_build where f_id="+fId;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			sb.append("0");
			while (rs.next())
			{
				sb.append(",").append(rs.getInt(1));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return sb.toString();
	}
	
	/**
	 * 得到帮派的建筑信息
	 * @param fId		帮派id
	 * @param bId		建筑id
	 * @return
	 */
	public FBuild getBuild(int fId,int bId)
	{
		return this.getOneBySql("where f_id="+fId+" and b_id="+bId);
	}
	
	/**
	 * 得到帮派建筑列表
	 */
	public QueryPage getBuildPageList(int fId,int page_no)
	{
		return super.loadPageList("where f_id="+fId, page_no);
	}
	
	
	@Override
	protected FBuild loadData(ResultSet rs) throws SQLException
	{
		FBuild fBuild = new FBuild();
		
		fBuild.setFId(rs.getInt("f_id"));
		fBuild.setBId(rs.getInt("b_id"));
		
		return fBuild;
	}

}
