package com.pm.dao.chuansong;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.auction.AuctionYBVO;
import com.pm.vo.chuansong.SuiBianChuanVO;

public class SuiBianDao extends DaoBase
{

	/**
	 * �������Ŀ��Դ��͵���������
	 * @return
	 */
	public List<SuiBianChuanVO> getChuanSongType()
	{
		List<SuiBianChuanVO> list = new ArrayList<SuiBianChuanVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		SuiBianChuanVO suiBianChuanVO = null;
		String sql = "select distinct(carry_type_id),carry_type_name from carry_table_info";
		try
		{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		while( rs.next() )
    		{
    			suiBianChuanVO = new SuiBianChuanVO();
    			suiBianChuanVO.setTypeId(rs.getInt("carry_type_id"));
    			suiBianChuanVO.setTypeName(rs.getString("carry_type_name"));
    			list.add(suiBianChuanVO);
    		}
    		
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		
		
		
		return list;
	}

	/**
	 * �������ͻ�ÿɴ��͵ġ��ص���Ϣ����
	 * @param prace �������  �� ��
	 * @param carrytype ��������   1����2����3boss
	 * 
	 * @return
	 */
	public List<SuiBianChuanVO> getChuanSongBy(int prace,int carryType)
	{
		List<SuiBianChuanVO> list = new ArrayList<SuiBianChuanVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		SuiBianChuanVO suiBianChuanVO = null;
		String sql ="";
		/*****����*****/
		if(prace==1)
		{
			if(carryType==1)
			{
				sql="select*From carry_table_info where carry_type_id in (1,3) and scene_id !=68";
			}
			else if(carryType==2)
			{
				sql="select*From carry_table_info where carry_type_id in (4,6)";
			}
			else if(carryType==3)
			{
				sql="select*From carry_table_info where carry_type_id in (7,9)";
			}
			else if(carryType==4)
			{
				sql="select*From carry_table_info where carry_type_id in (10)";
			}
				
		}
		/*****����*****/
		else
		{
			if(carryType==1)
			{
				sql="select*From carry_table_info where carry_type_id in (2,3) and scene_id !=6";
			}
			else if(carryType==2)
			{
				sql="select*From carry_table_info where carry_type_id in (5,6)";
			}
			else if(carryType==3)
			{
				sql="select*From carry_table_info where carry_type_id in (8,9)";
			}
			else if(carryType==4)
			{
				sql="select*From carry_table_info where carry_type_id in (10)";
			}
		}
		try
		{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		while( rs.next() )
    		{
    			suiBianChuanVO = new SuiBianChuanVO();
    			suiBianChuanVO.setCarryId(rs.getInt("carry_id"));
    			suiBianChuanVO.setSceneId(rs.getInt("scene_id"));
    			suiBianChuanVO.setSceneName(rs.getString("scene_name"));
    			suiBianChuanVO.setTypeId(rs.getInt("carry_type_id"));
    			suiBianChuanVO.setTypeName(rs.getString("carry_type_name"));
    			suiBianChuanVO.setCarryGrade(rs.getInt("carry_grade"));
    			suiBianChuanVO.setPartGrade(rs.getString("part_grade"));
    			list.add(suiBianChuanVO);
    		}
    		
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	/**
	 * ����ID����ȡ�ص� ��Ϣ
	 * @param carryId
	 * @return
	 */
	public SuiBianChuanVO getChuanSongById(String carryId)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		SuiBianChuanVO suiBianChuanVO = null;
		String sql = "select * from carry_table_info where carry_id="+carryId;
		try
		{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		while( rs.next() )
    		{
    			suiBianChuanVO = new SuiBianChuanVO();
    			suiBianChuanVO.setCarryId(rs.getInt("carry_id"));
    			suiBianChuanVO.setSceneId(rs.getInt("scene_id"));
    			suiBianChuanVO.setSceneName(rs.getString("scene_name"));
    			suiBianChuanVO.setTypeId(rs.getInt("carry_type_id"));
    			suiBianChuanVO.setTypeName(rs.getString("carry_type_name"));
    			suiBianChuanVO.setCarryGrade(rs.getInt("carry_grade"));
    			
    		}
    		
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return suiBianChuanVO;
	}
	
	
	

}
