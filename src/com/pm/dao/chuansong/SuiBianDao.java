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
	 * 获得所需的可以传送的区域类型
	 * @return
	 */
	public List<SuiBianChuanVO> getChuanSongType()
	{
		List<SuiBianChuanVO> list = new ArrayList<SuiBianChuanVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		SuiBianChuanVO suiBianChuanVO = null;
		String sql = "SELECT distinct(carry_type_id),carry_type_name FROM carry_table_info";
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
	 * 根据类型获得可传送的　地点信息，　
	 * @param prace 玩家种族  妖 巫
	 * @param carrytype 传送类型   1场景2练级3boss
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
		/*****妖族*****/
		if(prace==1)
		{
			if(carryType==1)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (1,3) AND scene_id !=68";
			}
			else if(carryType==2)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (4,6)";
			}
			else if(carryType==3)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (7,9)";
			}
			else if(carryType==4)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (10)";
			}
				
		}
		/*****巫族*****/
		else
		{
			if(carryType==1)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (2,3) AND scene_id !=6";
			}
			else if(carryType==2)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (5,6)";
			}
			else if(carryType==3)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (8,9)";
			}
			else if(carryType==4)
			{
				sql="SELECT * FROM carry_table_info WHERE carry_type_id IN (10)";
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
	 * 根据ID来获取地点 信息
	 * @param carryId
	 * @return
	 */
	public SuiBianChuanVO getChuanSongById(String carryId)
	{
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		SuiBianChuanVO suiBianChuanVO = null;
		String sql = "SELECT * FROM carry_table_info WHERE carry_id="+carryId;
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
