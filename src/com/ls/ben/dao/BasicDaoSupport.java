package com.ls.ben.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * �°�dao�ĸ���
 */
public abstract class BasicDaoSupport<Item>
{
	protected Logger logger = Logger.getLogger("log.dao");
	protected Connection conn = null;
	protected Statement stmt = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	
	private String talbe_name = "";
	private int db_type;
	
	public BasicDaoSupport(String talbe_name,int db_type)
	{
		this.talbe_name = " " + talbe_name+" ";
		this.db_type = db_type;
	}
	
	/**
	 * ִ��updateSql
	 * @param update_sql
	 */
	protected int executeUpdateSql( String update_sql )
	{
		int result = 0;
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		logger.debug("sql="+update_sql);
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(update_sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/**
	 * �õ������ַ�������������
	 */
	protected int getNumBySql( String condition_sql)
	{
		String sql = "select count(*) from "+this.talbe_name+condition_sql;
		int result = 0;
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = rs.getInt(1);
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
		return result;
	}
	
	/**
	 * ���������ַ����ж��Ƿ��и�����������
	 * @param condition_sql
	 * @return
	 */
	protected boolean isHaveBySql( String condition_sql)
	{
		String sql = "select * from "+this.talbe_name+condition_sql+" limit 1";
		boolean result = false;
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = true;
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
		return result;
	}
	
	/**
	 * ���������б�
	 * @param sql
	 * @return
	 */
	protected List<Integer> getKeyListBySql(String sql )
	{
		List<Integer> item_list = new ArrayList<Integer>();
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				item_list.add(rs.getInt(1));
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
		return item_list;
	}
	
	/**
	 * �����ַ����õ��б�
	 * @param sql
	 * @return
	 */
	protected List<Item> getListBySql(String condition_sql )
	{
		String sql = "select * from "+this.talbe_name+condition_sql;
		List<Item> item_list = new ArrayList<Item>();
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				item_list.add(this.loadData(rs));
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
		return item_list;
	}
	
	/**
	 * ���������ַ���ɾ��
	 * @param condition_sql
	 * @return      ɾ��������
	 */
	protected int delBySql( String condition_sql )
	{
		String sql = "delete from "+this.talbe_name+condition_sql;
		int result = 0;
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		logger.debug("sql="+sql);
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/**
	 * ͨ���ַ����õ�һ������
	 * @return
	 */
	protected Item getOneBySql(String condition_sql)
	{
		String sql = "select * from "+this.talbe_name+condition_sql;
		Item object = null;
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				object = this.loadData(rs);
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
		return object;
	}
	
	/**
	 * ���������ַ����͵�ǰҳ�ŵõ���ҳ����
	 */
	protected QueryPage loadPageList( String condition_sql,int page_no)
	{
		return this.loadPageList(condition_sql, "", page_no);
	}
	/**
	 * ���������ַ����͵�ǰҳ�ŵõ���ҳ����
	 */
	protected QueryPage loadPageList( String condition_sql,String order_sql,int page_no)
	{
		QueryPage queryPage = null;
		
		List<Item> equip_list = new ArrayList<Item>();
		Item object = null;
		
		DBConnection dbConn = new DBConnection(db_type);
		conn = dbConn.getConn();
		String count_sql, page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "select count(*) from "+this.talbe_name+condition_sql ;
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();
			
			queryPage = new QueryPage(page_no, count);
			
			page_sql = "select * from "+this.talbe_name+" "+condition_sql+" "+order_sql
			+ "  limit " + queryPage.getStartOfPage()
			+ "," + queryPage.getPageSize();
			logger.debug(page_sql);
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				object = this.loadData(rs);
				equip_list.add(object);
			}
			rs.close();
			stmt.close();
			
			queryPage.setResult(equip_list);
			
		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return queryPage;
	}
	/**����resultsetװ�ض���*/
	protected abstract Item loadData( ResultSet rs ) throws SQLException;
}
