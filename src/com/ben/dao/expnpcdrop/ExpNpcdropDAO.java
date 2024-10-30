package com.ben.dao.expnpcdrop;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.expnpcdrop.ExpNpcdropVO;
import com.pub.db.jygamedb.Jygamedb;

public class ExpNpcdropDAO
{
	Jygamedb con;


	/**
	 * �õ� ���е� npc ���侭�鱶��
	 * 
	 * @return
	 */
	public List<ExpNpcdropVO> getAllExpNpcDrop()
	{	
		List<ExpNpcdropVO> list = new ArrayList<ExpNpcdropVO>();
		ExpNpcdropVO expNpcdropVO = null;
		try {
			con = new Jygamedb();
			String sql = "select * from exp_npcdrop ";
			ResultSet rs = con.query(sql);
			while(rs.next()) {
				expNpcdropVO = new ExpNpcdropVO();
				expNpcdropVO.setEnPk(rs.getInt("en_pk"));
				expNpcdropVO.setDefaultExp(rs.getInt("default_exp")); 
				expNpcdropVO.setBeginTime(rs.getString("begin_time"));
				expNpcdropVO.setEndTime(rs.getString("end_time"));
				expNpcdropVO.setEnMultiple(rs.getInt("en_multiple"));
				expNpcdropVO.setEnCimelia(rs.getInt("en_cimelia"));
				expNpcdropVO.setEnforce(rs.getInt("enforce"));
				expNpcdropVO.setExpCimelia(rs.getInt("exp_cimelia"));
				expNpcdropVO.setAcquitFormat(rs.getInt("acquit_format"));
				list.add(expNpcdropVO);
			}
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			con.close();
		}
		
		return list;		
		
	}	
}
