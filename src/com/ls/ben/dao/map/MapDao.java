package com.ls.ben.dao.map;


import java.util.HashMap;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.map.BareaVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.pub.db.DBConnection;


public class MapDao extends DaoBase {
	
	
	/**
	 * 初始化map表，将 map表里的内容都放入到内存表里去
	 * @throws Exception 
	 * 
	 */
	public HashMap<String, MapVO> getAllMap(HashMap<String,BareaVO> barea_list) throws Exception{
		
		HashMap<String, MapVO> result = null;
		int total_num = 0;
		MapVO map_info = null;
		BareaVO barea = null;
		
		String total_num_sql = "select count(*) from map";
		String sql = "select * from map";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(total_num_sql);
			if( rs.next() )
			{
				total_num = rs.getInt(1);
			}
			
			result = new HashMap<String, MapVO>(total_num);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				map_info = new MapVO();
				map_info.setMapID(rs.getInt("map_id"));
				map_info.setMapName(rs.getString("map_name"));
				map_info.setMapDisplay(rs.getString("map_display"));
				map_info.setMapSkill(rs.getString("map_skill"));
				map_info.setMapFrom(rs.getInt("map_from"));
				map_info.setMapType(rs.getInt("map_type"));
				
				barea = barea_list.get(map_info.getMapFrom()+"");
				
				if( barea==null )
				{
					String errer = "MapDao.getAllMap:加载区域数据错误，无该区域所在的barea_id，scene_id="+map_info.getMapID()+";barea_id="+map_info.getMapFrom();
					throw new Exception(errer);
				}
				else
				{
					map_info.setBarea(barea);
				}
				
				result.put(map_info.getMapID()+"", map_info);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("map信息加载内存失败，当前map_id为："+map_info.getMapID()+";错误信息:"+e.getMessage());
		}
		finally
		{
			dbConn.closeConn();
		}
		return result;
	}

}
