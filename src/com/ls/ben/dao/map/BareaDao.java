
package com.ls.ben.dao.map;

import java.sql.SQLException;
import java.util.HashMap;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.map.BareaVO;
import com.ls.pub.db.DBConnection;



public class BareaDao extends DaoBase {
	
	/**
	 * ��ʼ��barea����barea��������ݶ����뵽�ڴ����ȥ
	 * @throws Exception 
	 * 
	 */
	public HashMap<String,BareaVO> getAllBarea() throws Exception{
		
		HashMap<String,BareaVO> result = null;
		int total_num = 0;
		BareaVO barea_info = null;
		
		String total_sql = "select count(*) from  barea";
		String sql = "select * from barea";
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
					throw new Exception("barea�����ݴ������ĵ����barea_Name="+barea_info.getBareaName());
				}
				
				result.put(barea_info.getBareaID()+"", barea_info);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("barea�����ݴ���"+e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}

}
