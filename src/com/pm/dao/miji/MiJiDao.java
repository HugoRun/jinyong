/**
 * 
 */
package com.pm.dao.miji;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.miji.MiJiVO;

/**
 * @author zhangjj
 *
 */
public class MiJiDao extends DaoBase {

	
	/**
	 * 
	 */
	public MiJiVO getMiJiById ( int mijiId) {
		String sql = "select * from miji_info where mj_id = "+mijiId;
		MiJiVO miJiVO = null;
		logger.debug("ս���е������Ӫ���Ի�ԭ="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				miJiVO = new MiJiVO();
				miJiVO.setMjId(rs.getInt("mj_id"));
				miJiVO.setMjInfo(rs.getString("mj_info"));
			}
			stmt.close();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally
		{
			
			dbConn.closeConn();
		}
		return miJiVO;
	}
}