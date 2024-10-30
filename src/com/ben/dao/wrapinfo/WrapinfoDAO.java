package com.ben.dao.wrapinfo;

import java.sql.ResultSet;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.pub.db.mysql.SqlData;

/**
 * @author 侯浩军
 * 
 * 6:45:36 PM
 */
public class WrapinfoDAO {
	SqlData con;

	// 通过角色ID 查找出该角色的银两 
	public PartInfoVO geTsilver(String pPk) {
		try {
			con = new SqlData();
			String sql = "SELECT * FROM u_part_info WHERE p_pk = '" + pPk + "'";
			ResultSet rs = con.query(sql);
			PartInfoVO vo = new PartInfoVO();
			while (rs.next()) {
				vo.setPCopper(rs.getString("p_copper"));
				vo.setPWrapContent(rs.getInt("p_wrap_content"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}
}
