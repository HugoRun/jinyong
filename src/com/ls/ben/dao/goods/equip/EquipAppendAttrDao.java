package com.ls.ben.dao.goods.equip;

import com.ls.ben.dao.DaoBase;
import com.ls.model.equip.EquipAppendAttri;
import com.ls.pub.db.DBConnection;

/**
 * ����:equip_append_attri��,װ��������ߵȼ�ʱ���ӵ����Ա�
 * @author ls
 */
public class EquipAppendAttrDao extends DaoBase
{
	/**
	 * ͨ��id�õ���Ϣ
	 * @param id
	 * @return
	 */
	public EquipAppendAttri getById( String id )
	{
		EquipAppendAttri appendAttribute = null;
		String sql = "select * from equip_append_attri where id="+id;
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
