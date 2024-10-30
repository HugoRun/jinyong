package com.ls.ben.dao.faction;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.organize.faction.FNotice;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * ���ɹ���
 */
public class FNoticeDao extends BasicDaoSupport<FNotice>
{

	public FNoticeDao()
	{
		super("f_notice",DBConnection.GAME_USER_DB);
	}
	
	/**
	 * ���
	 * @param 
	 */
	public int add(FNotice notice)
	{
		int key = 0;
		
		if( notice==null )
		{
			return 0;
		}
		
		String sql = "insert into f_notice(f_id,content,create_time) values (?,?,now())";

		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			ps.setInt(1, notice.getFId());
			ps.setString(2, notice.getContent());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if( rs.next() )
			{
				notice.setId(rs.getInt(1));
			}
			rs.close();
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
	 * ͨ��id�õ�������Ϣ
	 */
	public FNotice getById( int id )
	{
		String condition_sql = "where id="+id;
		return super.getOneBySql(condition_sql);
	}
	/**
	 * �õ����ɵ����µ�һ������
	 */
	public FNotice getLastedNotice(int fId)
	{
		String condition_sql = "where f_id="+fId+" order by id desc limit 1";
		return super.getOneBySql(condition_sql);
	}
	
	/**
	 * ͨ��idɾ��������Ϣ
	 */
	public void delById( int id )
	{
		String condition_sql = "where id="+id;
		super.delBySql(condition_sql);
	}
	
	/**
	 * ��ҳ�б�
	 */
	public QueryPage getPageList(int f_id,int page_no)
	{
		String condition_sql = "where f_id="+f_id;
		String order_sql = "order by id desc";
		return super.loadPageList(condition_sql,order_sql, page_no);
	}

	protected FNotice loadData(ResultSet rs) throws SQLException
	{
		if( rs==null )
		{
			return null;
		}
		FNotice notice = new FNotice();
		notice.setId(rs.getInt("id"));
		notice.setFId(rs.getInt("f_id"));
		notice.setContent(rs.getString("content"));
		notice.setCreateTime(rs.getDate("create_time"));
		return notice;
	}

}
