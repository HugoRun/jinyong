
package com.ls.ben.dao.map;

import java.sql.SQLException;
import java.util.HashMap;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.map.BareaVO;
import com.ls.pub.db.DBConnection;



public class BareaDao extends DaoBase {
	
	/**
	 * 初始化barea表，将barea表里的内容都放入到内存表里去
	 * @throws Exception 
	 * 
	 */
	public HashMap<String,BareaVO> getAllBarea() throws Exception{
		
		HashMap<String,BareaVO> result = null;
		int total_num = 0;
		BareaVO barea_info = null;
		
		String total_sql = "SELECT count(*) from `barea`";
		String sql = "SELECT * FROM `barea`";
		logger.debug(sql);
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(total_sql);
			if( rs.next() )
			{
				total_num = rs.getInt(1); 
			}
			
			result  = new HashMap<String,BareaVO>(total_num);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				barea_info = new BareaVO();
				
				barea_info.setBareaID(rs.getInt("barea_ID"));
				barea_info.setBareaName(rs.getString("barea_Name"));
				barea_info.setBareaPoint(rs.getInt("barea_point"));
				barea_info.setBareaDisplay(rs.getString("barea_display"));
				barea_info.setBareaType(rs.getInt("barea_type"));
				
				if( barea_info.getBareaPoint()<=0 )
				{
					throw new Exception("barea表数据错误：中心点错误，barea_Name="+barea_info.getBareaName());
				}
				
				result.put(barea_info.getBareaID()+"", barea_info);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("barea表数据错误："+e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}

}
