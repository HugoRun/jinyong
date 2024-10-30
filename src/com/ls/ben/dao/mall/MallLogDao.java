/**
 * 
 */
package com.ls.ben.dao.mall;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.mall.MallLogVO;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * 功能：商城日志
 * @author ls
 * May 12, 2009
 * 4:29:36 PM
 */
public class MallLogDao extends DaoBase
{
	/**
	 * 插入商城日志
	 */
	public void insert(int u_pk,String role_name,String mall_log ,String propName,int propNum,int propPrice,int buyType)
	{
		if( mall_log==null )
		{
			logger.info("插入商城日志错误。。。。");
		}
		
		String sql = "INSERT INTO u_mall_log VALUES (null,"+u_pk+",'"+role_name+"','"+propName+"',"+propNum+","+propPrice+","+buyType+",'"+mall_log+"',now())";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * 得到商城日志列表,最近七天的记录
	 */
	public QueryPage getLogList( int u_pk ,int page_no )
	{
		QueryPage queryPage = null;
		
		List<MallLogVO> list = new ArrayList<MallLogVO>();
		MallLogVO mall_log = null;
		
		int count=0;
		
		String count_sql = "SELECT COUNT(*) FROM u_mall_log WHERE now() < (create_time + INTERVAL 7 DAY) AND u_pk = "+u_pk;
		String page_sql = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();
			
			queryPage = new QueryPage(page_no, count);
			
			page_sql = "SELECT mall_log FROM u_mall_log WHERE now() < (create_time + INTERVAL 7 DAY) AND u_pk = "+u_pk+"  ORDER BY create_time DESC LIMIT "
			+ queryPage.getStartOfPage() + ","
			+ queryPage.getPageSize();
			
			logger.debug(count_sql);
			logger.debug(page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while(rs.next()) 
			{
				mall_log = new MallLogVO();
				mall_log.setLog(rs.getString("mall_log"));
				list.add(mall_log);
			}
			
			queryPage.setResult(list);
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return queryPage;
	}
	
}

