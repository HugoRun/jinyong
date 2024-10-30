package com.ls.ben.dao.pkhite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.pkhite.PKHiteVO;
import com.ls.pub.db.DBConnection;

/**
 * ����pk���ϵͳ��dao
 * 
 * @author Thomas.lei
 */
public class PKHiteDao extends DaoBase
{
	/** *****�鿴�Ƿ������Ѿ��г�޼�¼,������򷵻ؼ�¼******* */
	public PKHiteVO checkIsHaveHiteRecord(int p_pk, int enemyPpk)
	{
		String sql = "select *from u_pk_hite where p_pk=" + p_pk
				+ " and enemyPpk=" + enemyPpk + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		PKHiteVO pv = null;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				pv = new PKHiteVO();
				pv.setId(rs.getInt("id"));
				pv.setP_pk(rs.getInt("p_pk"));
				pv.setEnemyPpk(rs.getInt("enemyPpk"));
				pv.setEnemyName(rs.getString("enemyName"));
				pv.setEnemyGrade(rs.getInt("enemyGrade"));
				pv.setHitePoint(rs.getInt("hitePoint"));
				pv.setGeneralPkCount(rs.getInt("generalPKcount"));
				pv.setActivePkCount(rs.getInt("activePKcount"));
				pv.setUpdateTime(rs.getDate("updateTime"));
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return pv;
	}

	/** ******��������һ���µĳ�޶���********** */
	public void addEnemy(PKHiteVO pv)
	{
		String sql = "insert into u_pk_hite (p_pk,enemyUpk,enemyPpk,enemyName,enemyGrade,hitePoint,generalPKcount,activePkcount,updateTime) values ("
				+ pv.getP_pk()
				+ ","
				+ pv.getEnemyUpk()
				+ ","
				+ pv.getEnemyPpk()
				+ ",'"
				+ pv.getEnemyName()
				+ "',"
				+ pv.getEnemyGrade()
				+ ","
				+ pv.getHitePoint()
				+ ","
				+ pv.getGeneralPkCount()
				+ ","
				+ pv.getActivePkCount()
				+ ",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
	}

	/** ******����Ѿ��г�޶�������³�޵�******** */
	public void updateHitePoint(PKHiteVO pv)
	{
		String sql = "update u_pk_hite set enemyGrade=" + pv.getEnemyGrade()
				+ ",hitePoint=" + pv.getHitePoint() + ",generalPKcount="
				+ pv.getGeneralPkCount() + ",activePkcount="
				+ pv.getActivePkCount() + ",updateTime=now() where id="
				+ pv.getId() + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
	}

	/** ********��ҳ��ѯ��ҵĳ�ޱ�*********** */
	public List<PKHiteVO> getEnemys(int ppk, int index, int limit)
	{
		String sql = "select*from u_pk_hite where p_pk=" + ppk
				+ " order by hitePoint desc limit " + index*limit + "," + limit + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		List<PKHiteVO> list = new ArrayList<PKHiteVO>();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				PKHiteVO pv = new PKHiteVO();
				pv.setId(rs.getInt("id"));
				pv.setP_pk(rs.getInt("p_pk"));
				pv.setEnemyUpk(rs.getInt("enemyUpk"));
				pv.setEnemyPpk(rs.getInt("enemyPpk"));
				pv.setEnemyName(rs.getString("enemyName"));
				pv.setEnemyGrade(rs.getInt("enemyGrade"));
				pv.setHitePoint(rs.getInt("hitePoint"));
				pv.setGeneralPkCount(rs.getInt("generalPKcount"));
				pv.setActivePkCount(rs.getInt("activePKcount"));
				pv.setUpdateTime(rs.getDate("updateTime"));
				list.add(pv);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();

		}
		return list;
	}

	/** ***********�õ���¼������************* */
	public int getRecordNum(int ppk)
	{
		String sql = "select count(*) as total from u_pk_hite where p_pk="
				+ ppk + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				count = rs.getInt("total");
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return count;
	}
	
	/** ***********���ɾ����ɫ��ʱ��ɾ�����к�����йصĳ����Ϣ************* */
	public void removeHiteInfo(int ppk)
	{
		String sql = "delete from u_pk_hite where p_pk="+ppk+" or enemyPpk="+ppk+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug(sql);
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}
}
