package com.ls.ben.dao.goods.equip;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.goods.equip.EquipAppendAttributeVO;
import com.ls.pub.db.DBConnection;

/**
 * 功能:equip_append_attribute表
 * @author 刘帅
 * Oct 13, 2008  3:26:30 PM
 */
public class EquipAppendAttributeDao extends DaoBase
{
	/**
	 * 得到装备附加属性,除hp和mp外
	 * @param equip_type
	 * @param equip_class
	 * @param player_level
	 * @return
	 */
	public List<EquipAppendAttributeVO> getRandomAttributes( int equip_type,int equip_level,int quality,int attribute_num )
	{
		List<EquipAppendAttributeVO> appendAttributes = new ArrayList<EquipAppendAttributeVO>();
		EquipAppendAttributeVO appendAttribute = null;
		String sql = "SELECT * FROM equip_append_attribute WHERE attribute_type not IN (1,2) AND equip_type=" + equip_type + " AND quality="+quality+" AND level_lower <=" + equip_level + " AND "+ equip_level+"<=level_upper ORDER BY rand() LIMIT " + attribute_num;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			while (rs.next()) {
				appendAttribute = new EquipAppendAttributeVO();
				appendAttribute.setAttributeType(rs.getInt("attribute_type"));
				appendAttribute.setValueArea(rs.getString("value_area"));
				logger.debug("valueArea="+appendAttribute.getValueArea());
				appendAttribute.setValueProbability(rs.getString("value_probability"));
				appendAttribute.setQuality(quality);
				appendAttributes.add(appendAttribute);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		
		return appendAttributes;
	}
	
	/**
	 * 得到装备附加属性，仅hp和mp
	 * @param equip_type
	 * @param equip_class
	 * @param player_level
	 * @return
	 */
	public List<EquipAppendAttributeVO> getRandomAttributesByHpMp( int equip_type,int equip_level,int quality )
	{
		List<EquipAppendAttributeVO> appendAttributes = new ArrayList<EquipAppendAttributeVO>();
		EquipAppendAttributeVO appendAttribute = null;
		String sql = "SELECT * FROM equip_append_attribute WHERE attribute_type IN (1,2) AND equip_type=" + equip_type + " AND quality="+quality+" AND level_lower <=" + equip_level + " AND "+ equip_level+"<=level_upper ORDER BY rand() ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			while (rs.next()) {
				appendAttribute = new EquipAppendAttributeVO();
				appendAttribute.setAttributeType(rs.getInt("attribute_type"));
				appendAttribute.setValueArea(rs.getString("value_area"));
				logger.debug("valueArea="+appendAttribute.getValueArea());
				appendAttribute.setValueProbability(rs.getString("value_probability"));
				appendAttribute.setQuality(quality);
				appendAttributes.add(appendAttribute);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		
		return appendAttributes;
	}
}
