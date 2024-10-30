/**
 * 
 */
package com.pm.dao.horta;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.horta.HortaVO;

/**
 * @author zhangjj
 *
 */
public class HortaDao extends DaoBase
{

	/**
	 * 获得所有类型的奖励类型
	 * @return
	 */
	public List<HortaVO> getMainList()
	{
		String sql = "SELECT * FROM system_hortation_info GROUP BY horta_type";
		List<HortaVO> list = new ArrayList<HortaVO>();
		
		HortaVO hortaVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug("获得所有类型的奖励类型="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{				
				hortaVO = new HortaVO();
				hortaVO.setHortaId(rs.getInt("horta_id"));
				hortaVO.setHortaType(rs.getInt("horta_type"));
				hortaVO.setHorta_name(rs.getString("horta_name"));
				list.add(hortaVO);
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
		
		return list;	
	}

	/**
	 * 获取某一奖励类型下所有的奖励内容
	 * 
	 * @return
	 */
	public List<HortaVO> getHortaSonList(String main_type)
	{
		String sql = "SELECT * FROM system_hortation_info WHERE isuseable != 0 AND horta_type="+main_type  +" ORDER BY horta_son_id DESC ";
		List<HortaVO> list = new ArrayList<HortaVO>();
		
		HortaVO hortaVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug("获取某一奖励类型下所有的奖励内容="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				hortaVO = new HortaVO();
				hortaVO.setHortaId(rs.getInt("horta_id"));
				hortaVO.setHortaType(rs.getInt("horta_type"));
				hortaVO.setHorta_name(rs.getString("horta_name"));
				hortaVO.setHortaSonId(rs.getInt("horta_son_id"));
				hortaVO.setHorta_sonName(rs.getString("horta_son_name"));
				hortaVO.setVipGrade(rs.getString("vip_grade"));
				hortaVO.setOnlineTime(rs.getInt("online_time"));
				hortaVO.setWjGrade(rs.getString("wj_grade"));
				hortaVO.setGiveGoods(rs.getString("give_goods"));
				hortaVO.setIsOnlyOne(rs.getInt("is_only_one"));
				hortaVO.setOnces(rs.getString("onces"));
				hortaVO.setHortaDisplay(rs.getString("horta_display"));
				list.add(hortaVO);
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
		
		return list;	
	}

	/**
	 * 根据ID获得奖励具体内容
	 * @param hor_id
	 * @return
	 */
	public HortaVO getHortaByHorId(String hor_id)
	{
		String sql = "SELECT * FROM system_hortation_info WHERE horta_id="+hor_id;
		
		HortaVO hortaVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug("获取某一奖励类型下所有的奖励内容="+sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{				
				hortaVO = new HortaVO();
				hortaVO.setHortaId(rs.getInt("horta_id"));
				hortaVO.setHortaType(rs.getInt("horta_type"));
				hortaVO.setHorta_name(rs.getString("horta_name"));
				hortaVO.setHortaSonId(rs.getInt("horta_son_id"));
				hortaVO.setHorta_sonName(rs.getString("horta_son_name"));
				hortaVO.setVipGrade(rs.getString("vip_grade"));
				hortaVO.setOnlineTime(rs.getInt("online_time"));
				hortaVO.setWjGrade(rs.getString("wj_grade"));
				hortaVO.setGiveGoods(rs.getString("give_goods"));
				hortaVO.setIsOnlyOne(rs.getInt("is_only_one"));
				hortaVO.setOnces(rs.getString("onces"));
				hortaVO.setHortaDisplay(rs.getString("horta_display"));
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
		return hortaVO;
	}
	
	
	

}
