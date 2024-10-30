package com.ben.dao.honour;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ben.vo.honour.RoleTitleVO;
import com.ls.ben.dao.BasicDaoSupport;
import com.ls.pub.db.DBConnection;

/**
 * ��ҳƺ�
 */
public class RoleTitleDAO extends BasicDaoSupport<RoleTitleVO>
{
	public RoleTitleDAO()
	{
		super("u_title", DBConnection.GAME_USER_DB);
	}
	
	@Override
	protected RoleTitleVO loadData(ResultSet rs) throws SQLException
	{
		RoleTitleVO role_title = new RoleTitleVO();
		role_title.setId(rs.getInt("id"));
		role_title.setTId(rs.getInt("t_id"));
		role_title.setIsShow(rs.getInt("is_show"));
		role_title.setPPk(rs.getInt("p_pk"));
		role_title.setEndTime(rs.getLong("end_time"));
		return role_title;
	}
	
	/**
	 * ��ճƺ�
	 */
	public void clear( int p_pk)
	{
		String update_sql = "delete from u_title where p_pk ="+p_pk;
		super.executeUpdateSql(update_sql);
	}
	
	/**
	 * ����һ�����͵ĳƺ�
	 * @param p_pk
	 * @param ho_id
	 */
	public int add(RoleTitleVO roleTitle)
	{
		int id = -1;
		if( roleTitle==null )
		{
			return -1;
		}
		String sql = "insert into u_title(p_pk,t_id,end_time,is_show) values (?,?,?,?)";

		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, roleTitle.getPPk());
			ps.setInt(2, roleTitle.getTId());
			ps.setLong(3, roleTitle.getEndTime());
			ps.setInt(4, roleTitle.getIsShow());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys(); 
			if ( rs.next() ) { 
				id = rs.getInt(1); 
				roleTitle.setId(id);
			} 
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
		return id;
	}

	/**
	 * ͨ��idɾ���ƺ�
	 */
	public void delById( int pPk,int tId )
	{
		super.delBySql("where p_pk="+pPk+" and t_id="+tId);
	}
	
	/**
	 * �õ��ƺ��б�
	 */
	public List<RoleTitleVO> getListByPPk( int pPk )
	{
		return this.getListBySql("where p_pk="+pPk);
	}
	
	/**
	 * ��ʾ����
	 */
	public void updateIsShow( int pPk,int tId)
	{
		super.executeUpdateSql("update u_title set is_show=-is_show where p_pk="+pPk+" and t_id="+tId);
	}
	
}
