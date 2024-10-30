package com.ls.ben.dao.goods.equip;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.goods.equip.EquipMaterialVO;
import com.ls.pub.db.DBConnection;

/**
 * @author ls
 * 装备升级材料
 */
public class EquipMaterialDao extends DaoBase
{
	/**
	 * 根据品质和等级得到升级需要的信息
	 * @param quality
	 * @param grade
	 * @return
	 */
	public EquipMaterialVO getByQualityAndGrade(int quality,int grade)
	{
		EquipMaterialVO equip_material = null;
		String sql = "select * from equip_material where quality=" + quality+" and grade="+grade+" limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				equip_material = new EquipMaterialVO();
				equip_material.setQuality(rs.getInt("quality"));
				equip_material.setGrade(rs.getInt("grade"));
				equip_material.setMaterial1(rs.getInt("material_1"));
				equip_material.setMaterial2(rs.getInt("material_2"));
				equip_material.setNeedMoney(rs.getInt("need_money"));
				equip_material.setRate(rs.getInt("rate"));
			}
			rs.close();
			stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return equip_material;
	}
}
