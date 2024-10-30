/**
 * 
 */
package com.ben.dao.intimatehint;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.intimatehint.IntimateHintVO;
import com.pub.db.jygamedb.JyGameDB;

/**
 * @author 侯浩军
 * 
 * 10:44:00 AM
 */

public class IntimateHintDAO
{
	JyGameDB con;
	/**
	 * 得到所有武林小贴士
	 */
	public List<IntimateHintVO> getAllIntimateHint()
	{
		List<IntimateHintVO> hint_list = null;
		int total_num = 0;
		
		String total_num_sql = "SELECT count(*) FROM `u_intimate_hint`";
		String sql = "SELECT * FROM `u_intimate_hint`";
		
		IntimateHintVO vo = null;
		try
		{
			con = new JyGameDB();
			
			ResultSet rs = con.query(total_num_sql);
			if( rs.next() )
			{
				total_num = rs.getInt(1);
			}
			
			hint_list = new ArrayList<IntimateHintVO>(total_num);
			
			rs = con.query(sql);
			while(rs.next())
			{
				vo = new IntimateHintVO();
				vo.setHPk(rs.getInt("h_pk"));
				vo.setHHint(rs.getString("h_hint"));
				vo.setHContent(rs.getString("h_content"));
				hint_list.add(vo);
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		return hint_list;
	}
	

}
