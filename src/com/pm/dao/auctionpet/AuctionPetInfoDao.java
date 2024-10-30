package com.pm.dao.auctionpet;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;
import com.pm.vo.auctionpet.AuctionPetVO;

public class AuctionPetInfoDao extends DaoBase 
{
	//����
	public void insertPetInfo(int pPk, String str)
	{
		String sql = "insert into u_auctionpet_info values(null,"+pPk+",'"+StringUtil.gbToISO(str)+"',now())";
		logger.debug("�������������Ϣ���sql:"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}
	
	/**
	 * ��ʱ��˳��ɾ������10������Ϣ��¼
	 * @param pk
	 */
	public int deleteSuperfluousInfo(int pPk)
	{
		int result = -1;
		String sql = "delete from u_auctionpet_info where auctionpet_info_id not in ( select auctionpet_info_id from " +
				"( select auctionpet_info_id from u_auctionpet_info order by addInfoTime desc " +
				"limit 8) b ) and p_pk ="+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			result = 1;
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}

	/**
	 * �����������Ϣ�����һ����Ϣ,
	 * @param pPk ������id
	 * @param AuctionPetVO
	 * @con ��Ҫ��������
	 */
	public void insertAuctionInfo(AuctionPetVO vo, String con)
	{
		int pPk2 = vo.getPPk();			//������id
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		
		String sql = "insert into u_auctionpet_info values(null,"+pPk2+",'"+StringUtil.gbToISO(con)+"','"+sf.format(dt)+"')";
		logger.debug("�����������Ϣ���в�����Ϣ :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		try{
			
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();

			} 
		catch (NullPointerException e)
		{
			logger.debug("nullPoint");
			e.printStackTrace();
		} 
			catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		
	}
}
