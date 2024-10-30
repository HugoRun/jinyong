package com.ls.ben.dao.faction;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.organize.faction.FApplyInfo;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 申请入帮表
 */
public class FApplyInfoDao extends BasicDaoSupport<FApplyInfo>
{
	
	public FApplyInfoDao()
	{
		super("f_apply_info", DBConnection.GAME_USER_DB);
	}

	public FApplyInfo getById( int aId )
	{
		return super.getOneBySql("where id="+aId);
	}
	
	public int delById( int aId )
	{
		return super.delBySql("where id="+aId);
	}
	
	/**
	 * 添加
	 * @param 
	 */
	public int add( int p_pk,int f_id)
	{
		int key = 0;
		
		String sql = "insert into f_apply_info(p_pk,f_id,create_time) values (?,?,now())";

		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, p_pk);
			ps.setInt(2, f_id);
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
	 * 是否申请过该帮派
	 */
	public boolean isHave( int p_pk ,int f_id )
	{
		String condition_sql = "where f_id="+f_id+" and p_pk="+p_pk;
		return this.isHaveBySql(condition_sql);
	}
	
	/**
	 * 删除玩家的所有申请
	 * @param p_pk
	 */
	public void delByPPk(int p_pk )
	{
		super.delBySql("where p_pk="+p_pk);
	}
	
	/**
	 * 分页列表
	 */
	public QueryPage getPageList(int f_id ,int page_no)
	{
		return super.loadPageList("where f_id="+f_id, page_no);
	}

	
	protected FApplyInfo loadData(ResultSet rs) throws SQLException
	{
		FApplyInfo fApplyInfo = new FApplyInfo();
		fApplyInfo.setId(rs.getInt("id"));
		fApplyInfo.setFId(rs.getInt("f_id"));
		fApplyInfo.setPPk(rs.getInt("p_pk"));
		return fApplyInfo;
	}

}
