package com.ls.ben.dao.goods.prop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:道具表
 * @author 刘帅
 * 6:16:15 PM
 */
public class PropDao extends BasicDaoSupport<PropVO>
{
	public PropDao()
	{
		super("prop", DBConnection.GAME_DB);
	}

	/**
	 * 通过id得到道具信息
	 * @param propId
	 * @return
	 */
	public PropVO getById( int propId )
	{
		return this.getOneBySql("where prop_ID="+propId);
	}
	
	/**
	 * 加载所有道具信息
	 * @return
	 */
	public HashMap<Integer,PropVO> getPropList() 
	{
		HashMap<Integer,PropVO> map = new HashMap<Integer,PropVO>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		String sql = "SELECT * FROM prop";
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				PropVO vo = this.loadData(rs);
				map.put(rs.getInt("prop_ID"), vo);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return map;
	}
	
	/**
	 * 根据道具的类型来获得一个此种类的道具的id
	 * @param prop_class
	 * @return
	 */
	public int getPropIdByType(int prop_class)
	{
		int prop_id = 0;
		String sql = "SELECT prop_ID FROM prop WHERE prop_class=" + prop_class
				+ " LIMIT 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_id = rs.getInt("prop_ID");
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
		return prop_id;
	}
	
	/**
	 * 根据道具的名字来获得一个此种类的道具的id
	 * @param propName		道具名字
	 * @return
	 */
	public int getPropIdByName(String propName)
	{
		int prop_id = -1;
		String sql = "SELECT prop_ID FROM prop WHERE prop_name='" + propName+ "' LIMIT 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_id = rs.getInt("prop_ID");
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
		return prop_id;
	}

	/**
	 * 根据buff道具的buff种类来获得一个此种类的道具的id
	 * @param prop_operate1
	 * @return
	 */
	public String getPropNameByType(String prop_operate1)
	{
		String prop_Name = "";
		String sql = "SELECT prop_Name FROM prop WHERE prop_class=23 AND prop_operate1 = '"
				+ prop_operate1 + "'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_Name = rs.getString("prop_Name");
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
		return prop_Name;
	}

	/**
	 * 根据等级和类型判断该物品的ID
	 * @param prop_level
	 * @param prop_type
	 * @return
	 */
	public int getPropIDByPropLevel(String prop_level, int prop_type)
	{
		int prop_id = 0;
		String sql = "SELECT prop_id FROM prop WHERE prop_ReLevel = '"
				+ prop_level + "' AND prop_class = " + prop_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_id = rs.getInt("prop_id");
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
		return prop_id;
	}
	
	/**
	 * 根据道具的类型来获得一个此种类的道具的id
	 * 除了两个体验卡373，374
	 * @return
	 */
	public List<PropVO> getListByType(int prop_class )
	{
		List<PropVO> list = new ArrayList<PropVO>();
		String sql = "SELECT prop_ID,prop_Name FROM prop WHERE prop_class=" + prop_class+" AND prop_ID != 373 AND prop_ID != 374";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		try { 
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			PropVO vo = null ;
			while (rs.next()) {
				vo = new PropVO();
				vo.setPropID(rs.getInt("prop_ID"));
				vo.setPropName(StringUtil.isoToGBK(rs.getString("prop_Name")));
				list.add(vo);
			}
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

	@Override
	protected PropVO loadData(ResultSet rs) throws SQLException
	{
		PropVO prop = new PropVO();
		prop.setPropID(rs.getInt("prop_ID"));
		prop.setPropName(StringUtil.isoToGBK(rs.getString("prop_Name")));
		prop.setPropClass(rs.getInt("prop_class"));
		prop.setPropReLevel(rs.getString("prop_ReLevel"));
		prop.setPropBonding(rs.getInt("prop_bonding"));
		prop.setPropAccumulate(rs.getInt("prop_accumulate"));
		prop.setPropDisplay(StringUtil.isoToGBK(rs.getString("prop_display")));
		prop.setPropSell(rs.getInt("prop_sell"));
		prop.setPropJob(rs.getString("prop_job"));
		prop.setPropDrop(rs.getString("prop_drop"));
		prop.setPropUsedegree(rs.getInt("prop_usedegree"));
		prop.setPropSex(rs.getInt("prop_sex"));
		prop.setPropProtect(rs.getInt("prop_protect"));
		prop.setPropPic(rs.getString("prop_pic"));
		prop.setPropOperate1(rs.getString("prop_operate1"));
		prop.setPropOperate2(rs.getString("prop_operate2"));
		prop.setPropOperate3(rs.getString("prop_operate3"));
		prop.setPropPosition(rs.getInt("prop_position"));
		prop.setPropAuctionPosition(rs.getInt("prop_auctionPosition"));
		prop.setPropReconfirm(rs.getInt("prop_reconfirm"));
		prop.setPropUseControl(rs.getInt("prop_use_control"));
		return prop;
	}
	/**
	 * 根据玩家输入的名称模糊查询出准确的名称
	 */
	public String getPropName(String name)
	{
		String propName="";
		String sql = "SELECT prop_name FROM prop WHERE prop_name LIKE '%"+name+"%' LIMIT 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				propName= rs.getString("prop_Name");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return propName;
	}
}
