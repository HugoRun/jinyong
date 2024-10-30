/**
 * 
 */
package com.ls.ben.dao.mall;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.mall.UMallStoreVO;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * 功能：百宝囊(u_mall_store)玩家商城商品仓库
 * @author ls
 * May 12, 2009
 * 4:38:54 PM
 */
public class UMallStoreDao extends DaoBase
{
	/**
	 * 删除百宝囊商品
	 */
	public void delete(int u_pk,int prop_id )
	{
		String sql = "DELETE FROM u_mall_store WHERE u_pk = "+u_pk+" AND prop_id="+prop_id;
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
	 * 插入百宝囊商品
	 */
	public void insert(int u_pk,int prop_id,String prop_name,int prop_num )
	{
		String sql = "INSERT INTO u_mall_store VALUES (null,"+u_pk+","+prop_id+",'"+prop_name+"',"+prop_num+",now())";
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
	 * 增加百宝囊商品数量
	 */
	public void updatePropNum(int u_pk,int prop_id,int prop_num )
	{
		String sql = "UPDATE u_mall_store SET last_buy_time = now(),commodity_num=commodity_num+"+prop_num + " WHERE u_pk = "+u_pk+" AND prop_id="+prop_id+"";
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
	 * 判断百宝囊内是否已有该物品
	 */
	public boolean isHave(int u_pk,int prop_id)
	{
		boolean result = false;
		String sql = "SELECT id FROM u_mall_store WHERE u_pk = "+u_pk+" AND prop_id="+prop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				result = true;
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/**
	 * 得到百宝囊列表
	 */
	public QueryPage getLogList( int u_pk ,int page_no )
	{
		QueryPage queryPage = null;
		
		List<UMallStoreVO> list = new ArrayList<UMallStoreVO>();
		UMallStoreVO uMallStore = null;
		
		int count=0;
		
		String count_sql = "SELECT COUNT(*) FROM u_mall_store WHERE u_pk = "+u_pk;
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
			
			page_sql = "SELECT * FROM u_mall_store WHERE u_pk = "+u_pk+" ORDER BY last_buy_time DESC LIMIT "
			+ queryPage.getStartOfPage() + ","
			+ queryPage.getPageSize();
			
			logger.debug(count_sql);
			logger.debug(page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while(rs.next()) 
			{
				uMallStore = new UMallStoreVO();
				uMallStore.setPropId(rs.getInt("prop_id"));
				uMallStore.setPropName(rs.getString("prop_name"));
				uMallStore.setCommodityNum(rs.getInt("commodity_num"));
				list.add(uMallStore);
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
	
	
	/**
	 * 得到百宝囊列表
	 */
	public UMallStoreVO getMallStorePropInfo( int u_pk ,int prop_id )
	{
		UMallStoreVO uMallStore = null;
		
		String sql = "SELECT commodity_num,prop_name FROM u_mall_store WHERE u_pk = "+u_pk+" AND prop_id="+prop_id;
		logger.debug(sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				uMallStore = new UMallStoreVO();
				uMallStore.setPropId(prop_id);
				uMallStore.setUPk(u_pk);
				uMallStore.setCommodityNum(rs.getInt(1));
				uMallStore.setPropName(rs.getString(2));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return uMallStore;
	}
}
