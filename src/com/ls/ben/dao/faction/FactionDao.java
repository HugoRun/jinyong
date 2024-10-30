package com.ls.ben.dao.faction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.organize.faction.Faction;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * ������Ϣ
 */
public class FactionDao extends BasicDaoSupport<Faction>
{
	
	public FactionDao()
	{
		super("f_faction", DBConnection.GAME_USER_DB);
	}

	/**
	 * ����ת���峤��ʱ��
	 */
	public void updateChangeZZHTime( int fId )
	{
		if( fId<=0 )
		{
			return ;
		}
		String sql = "update f_faction  set " +
		"changeZZHTime=now()"+
		" where id=?";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,fId);
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
	 * ���°��ɳ�Ա�ȼ��ܺ�
	 */
	public void updateMGradeTotal( int fId,int updateGrade )
	{
		if( fId<=0 )
		{
			return ;
		}
		String sql = "update f_faction  set " +
		"mGradeTotal=mGradeTotal+?"+
		" where id=?";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, updateGrade);
			ps.setInt(2,fId);
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
	 * ����
	 */
	public void save(Faction faction )
	{
		if( faction==null )
		{
			return;
		}
		String sql = "update f_faction  set " +
		"grade=?"+
		",memberNum=?"+
		",isDisband=?"+
		",citangGrade=?"+
		",prestige=?"+
		",mGradeTotal=?"+
		" where id=?";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, faction.getGrade());
			ps.setInt(2,faction.getMemberNum());
			ps.setBoolean(3, faction.getIsDisband());
			ps.setInt(4, faction.getCitangGrade());
			ps.setInt(5,faction.getPrestige());
			ps.setInt(6,faction.getMGradeTotal());
			ps.setInt(7,faction.getId());
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
	 * ����
	 * @param faction
	 * @return
	 */
	public int add(Faction faction)
	{
		int key = 0;
		if (faction == null)
		{
			return key;
		}
		
		String sql = "insert into f_faction(name,race,createTime,mGradeTotal) values (?,?,now(),?)";

		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, faction.getName());
			ps.setInt(2, faction.getRace());
			ps.setInt(3, faction.getMGradeTotal());
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys(); 
			if ( rs.next() ) { 
			key = rs.getInt(1); 
			faction.setId(key);
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
		return key;
	}
	
	/**
	 * �õ���������
	 * @return
	 */
	public List<Faction> getPrestigeRank()
	{
		String condition_sql = "where prestige>0 order by prestige desc,id limit 10";
		return super.getListBySql(condition_sql);
	}
	
	/**
	 * �õ�ս������
	 * @return
	 */
	public List<Faction> getZhanliRank()
	{
		String condition_sql = " order by grade desc,mGradeTotal desc,id limit 10";
		return super.getListBySql(condition_sql);
	}
	/**
	 * �õ��Ƹ�����
	 * @return
	 */
	public List<Faction> getRichRank()
	{
		String condition_sql = "where materialNum>0 order by materialNum desc,id limit 10";
		return super.getListBySql(condition_sql);
	}
	
	/**
	 * ͨ��idɾ��
	 */
	public int delById( int id )
	{
		String condition_sql = "where id="+id;
		return super.delBySql(condition_sql);
	}
	
	/**
	 * �õ���ɢ�����б�
	 * @param pName
	 * @return
	 */
	public List<Integer> getDisbandList()
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select id from f_faction where isDisband=1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				list.add(rs.getInt(1));
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
		return list;
	}

	/**
	 * ������ϲֿ���Ϣ
	 */
	public void saveStorageStr( int fId,String materialStr,int materialNum)
	{
		if( fId<=0 || materialStr==null || materialStr.equals("") )
		{
			return;
		}
		String sql = "update f_faction  set " +
		"storageStr=?"+
		",materialNum=?"+
		" where id=?";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, materialStr);
			ps.setInt(2,materialNum);
			ps.setInt(3,fId);
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
	 *�õ����ɲ�����Ϣ
	 */
	public String getStorageStr( int fId )
	{
		String result=null;
		String sql = "select storageStr from f_faction where id='"+fId+"'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			logger.debug(sql);
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				result = rs.getString(1);
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
	 * �Ƿ��и����ֵİ���
	 */
	public boolean isHaveName( String f_name )
	{
		boolean result = false;
		String sql = "select id from f_faction where name='"+f_name+"' limit 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
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
	 * ������Ϣ
	 */
	public Faction getById( int fId )
	{
		String condition_sql = "where id="+fId;
		return super.getOneBySql(condition_sql);
	}
	
	/**
	 * ���ɷ�ҳ�б�
	 */
	public QueryPage getPageList(int page_no)
	{
		String order_sql = "order by grade desc,memberNum desc,id";
		return super.loadPageList("",order_sql, page_no);
	}
	
	protected Faction loadData( ResultSet rs ) throws SQLException
	{
		if( rs==null )
		{
			return null;
		}
		Faction faction = new Faction();
		faction.setId(rs.getInt("id"));
		faction.setName(rs.getString("name"));
		faction.setGrade(rs.getInt("grade"));
		faction.setRace(rs.getInt("race"));
		faction.setMemberNum(rs.getInt("memberNum"));
		faction.setIsDisband(rs.getBoolean("isDisband"));
		faction.setCitangGrade(rs.getInt("citangGrade"));
		faction.setPrestige(rs.getInt("prestige"));
		faction.setCreateTime(rs.getDate("createTime"));
		faction.setMGradeTotal(rs.getInt("mGradeTotal"));
		faction.setChangeZZHTime(rs.getDate("changeZZHTime"));
		return faction;
	}
}