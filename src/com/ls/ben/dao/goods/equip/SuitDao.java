package com.ls.ben.dao.goods.equip;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.goods.equip.SuitVO;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.log.DataErrorLog;

/**
 * 功能:套装suit_info
 * 
 * @author 刘帅 10:36:34 PM
 */
public class SuitDao extends DaoBase
{
	/**
	 * 根据id获得suit
	 * 
	 * @param id
	 * @return
	 */
	public SuitVO getById(int suit_id)
	{
		SuitVO vo = null;
		String sql = "select * from suit_info where suit_id=" + suit_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				vo = new SuitVO();
				vo.setSuitId(rs.getInt("suit_id"));
				vo.setSuitName(rs.getString("suit_name"));
				
				vo.setTwoEffects(rs.getString("two_effects"));
				vo.setTwoEffectsDescribe(rs.getString("two_effects_describe"));
				
				vo.setThreeEffects(rs.getString("three_effects"));
				vo.setThreeEffectsDescribe(rs.getString("three_effects_describe"));
				
				vo.setFourEffects(rs.getString("four_effects"));
				vo.setFourEffectsDescribe(rs.getString("four_effects_describe"));
				
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
		if( vo==null )
		{
			DataErrorLog.debugData("无该套装，suit_id="+suit_id);
		}
		
		return vo;
	}

}
