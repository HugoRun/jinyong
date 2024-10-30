package com.ls.ben.dao.economy;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;

public class EconomyDao extends DaoBase
{
	/**
	 * �õ�Ԫ������
	 */
	public long getYuanbao(int u_pk)
	{
		long yuanbao = 0;
		String sql = "select yuanbao from u_login_info where u_pk=" + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				yuanbao = rs.getLong("yuanbao");
			}
			rs.close();
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
		return yuanbao;
	}


	/**
	 * ����Ԫ������
	 * @param u_pk
	 * @param updat_yuanbao_num                   �����ɸ�
	 */
	public void updateYuanbao(int u_pk, long updat_yuanbao_num)
	{
		String sql = "update u_login_info set  yuanbao = yuanbao+" + updat_yuanbao_num +" where u_pk=" + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}
	/**
	 * �õ���������
	 */
	public int getJifen(int u_pk)
	{
		int jifen = 0;
		String sql = "select jifen from u_login_info where u_pk=" + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				jifen = rs.getInt("jifen");
			}
			rs.close();
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
		return jifen;
	}
	
	
	/**
	 * ���»�������
	 * @param u_pk
	 * @param updat_yuanbao_num                   �����ɸ�
	 */
	public void updateJifen(int u_pk, int updat_jifen_num)
	{
		String sql = "update u_login_info set  jifen = jifen+" + updat_jifen_num +" where u_pk=" + u_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	}

}
