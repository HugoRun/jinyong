package com.pm.dao.renname;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.pkelimi.PKValueElimiVO;


public class RedNameDao extends DaoBase {
	/**
	 * �漴��ȡһ��������id
	 * @return
	 */
	
	public int getPerionRandomMapid(int map_type)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select scene_id from scene where scene_mapqy = (select map_id from map where map_type = "+map_type+")";
		logger.debug("�漴ѡ��һ������id="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				list.add(rs.getInt("scene_id"));
			}
			rs.close();
			stmt.close();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		if(list != null&&list.size()>0){
			Random rd = new Random();
			int i = rd.nextInt(list.size());
		
			return list.get(i);
		}else {
			logger.debug("�Ҳ���������");
			return 1;
		}
	}

	/**
	 * ��ü���scene��list
	 * @return
	 */
	public List<Integer> getRedNameMapidList()
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select scene_id from scene where scene_mapqy = (select map_id from map where map_type = 5)";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			logger.debug(" ��ü���scene��list="+sql);
			while (rs.next())
			{
				list.add(rs.getInt("scene_id"));
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}
	
	/**
	 * ���map_Id�µ�scene_Id��list
	 * @return
	 */
	public List<Integer> getRedNameMapidList(int map_Id)
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "select scene_id from scene where scene_mapqy = "+map_Id+"";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			logger.debug(" ��ü���scene��list="+sql);
			while (rs.next())
			{
				list.add(rs.getInt("scene_id"));
			}
			rs.close();
			stmt.close();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	/**
	 * ��ȡ����pkֵ���id
	 * @param pk
	 * @return
	 */
	public int getUserElimiId(String pk)
	{
		int elimi_id = -1;
		String sql = "select elimi_id from u_pkvalue_elimi where p_pk="+pk;
		logger.debug("�������pkֵ��id��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				elimi_id = rs.getInt("elimi_id");
			}
			rs.close();
			stmt.close();
			logger.debug("elimi_id="+elimi_id);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return elimi_id;
	}

	/**
	 * ��������pk���ֵ��
	 * @param pk
	 * @param pk_value
	 * @param oldMapid
	 */
	public void insertIntoElimi(String pk, int pk_value, String mapid)
	{
		String sql = "insert into u_pkvalue_elimination values(null,'"+pk+"','"+pk_value+"','"+mapid+"',now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}							
	}
	
	/**
	 * ����p_pk��ȡ����pkֵ�����Ϣ
	 * @param pk
	 * @return
	 */
	public PKValueElimiVO getUserPkvalueElimi(String pk)
	{ 
		PKValueElimiVO	vo = null;
		String sql = "select * from u_pkvalue_elimi where p_pk="+pk;
		logger.debug("�������pkֵ��id��sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				vo = new PKValueElimiVO();
				vo.setPkvalue_elimi(rs.getInt("elimi_id"));
				vo.setPPK(rs.getInt("p_pk"));
				vo.setPkvalue(rs.getInt("pk_value"));
				vo.setIsPerion(rs.getInt("is_prisonArea"));
				vo.setLastTime(rs.getString("last_time"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/**
	 * ��������pkֵ��
	 * @param pk
	 * @param reduce_pk_value
	 * @param dt3
	 */
	public void updatePkVlueElimi(String pk, int reduce_pk_value, Date dt3)
	{
		String sql = "update u_pkvlaue_elimination set pk_value= pkvalue - "+reduce_pk_value+" and last_time = "+dt3+" where p_pk = "+pk;
		logger.debug("��������pkֵ��="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ����pPkɾ������������
	 * @param pk
	 */
	public void deleteRedNameByPPk(String pk)
	{
		String sql = "delete from u_pkvlaue_elimination where p_pk = "+pk;
		logger.debug("ɾ��������¼��="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}
}
