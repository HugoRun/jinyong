/**
 * 
 */
package com.pub;

import java.sql.ResultSet;

import com.ben.vo.info.partinfo.PartInfoVO;
import com.pub.db.mysql.SqlData;

/**
 * @author ��ƾ�
 * 
 * 3:43:32 PM
 */
public class SystemUserDao {
	SqlData con;

	 
	// �������жϽ�ɫMONEY�����ͭǮ����100��ʱ���ת��100ͭǮ==1����
	public PartInfoVO getMoney(String pPk) {
		try {
			con = new SqlData();
			String sql = "select * from u_part_info where p_pk='" + pPk + "'";
			ResultSet rs = con.query(sql);
			PartInfoVO vo = new PartInfoVO();
			while (rs.next()) {
				vo.setPCopper(rs.getString("p_copper"));
			}
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return null;
	}

	public void getMoneyUpdate(String pSilver, String pCopper, String pPk) {
		try {
			con = new SqlData();
			String sql = "update u_part_info set p_silver='" + pSilver
					+ "',p_copper='" + pCopper + "' where p_pk='" + pPk + "'";
			con.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

}
