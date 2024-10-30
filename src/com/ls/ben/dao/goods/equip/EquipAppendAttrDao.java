package com.ls.ben.dao.goods.equip;

import com.ls.ben.dao.DaoBase;
import com.ls.model.equip.EquipAppendAttri;
import com.ls.pub.db.DBConnection;

/**
 * 功能:equip_append_attri表,装备升到最高等级时附加的属性表
 * @author ls
 */
public class EquipAppendAttrDao extends DaoBase
{
	/**
	 * 通过id得到信息
	 * @param id
	 * @return
	 */
	public EquipAppendAttri getById( String id )
	{
		EquipAppendAttri appendAttribute = null;
		String sql = "SELECT * FROM equip_append_attri WHERE id="+id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				appendAttribute = new EquipAppendAttri();
				appendAttribute.setAttriStr(rs.getString("attriStr"));
				appendAttribute.setAttriDes(rs.getString("attriDes"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		
		return appendAttribute;
	}
}
