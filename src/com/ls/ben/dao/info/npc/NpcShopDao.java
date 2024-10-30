package com.ls.ben.dao.info.npc;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcShopVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * ����:npcshop��
 * 
 * @author ��˧ 3:04:17 PM
 */
public class NpcShopDao extends DaoBase
{
	/**
	 * �õ��˵��������������Ʒ
	 * 
	 * @param menu_id
	 * @return
	 */
	public List<NpcShopVO> getListByMenuId(int menu_id)
	{
		List<NpcShopVO> npcshops = new ArrayList<NpcShopVO>();
		String sql = "select * from npcshop where npc_id=" + menu_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			NpcShopVO npcshop = null;
			while (rs.next())
			{
				npcshop = new NpcShopVO();
				npcshop.setNpcshopId(rs.getInt("npcshop_id"));
				npcshop.setNpcId(menu_id);
				npcshop.setGoodsId(rs.getInt("goods_id"));
				npcshop.setGoodsType(rs.getInt("goods_type"));
				npcshop.setGoodsName(StringUtil.isoToGBK(rs
						.getString("goods_name")));
				npcshop.setNpcShopGoodsbuy(rs.getInt("npc_shop_goodsbuy"));
				npcshops.add(npcshop);
			}
			rs.close();
			stmt.close();
			return npcshops;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return npcshops;
	}

	/**
	 * �õ��˵�ĳһid��Ʒ��Ϣ
	 * 
	 * @param menu_id
	 * @return
	 */
	public NpcShopVO getNpcShopById(int npcshop_id)
	{
		NpcShopVO npcshop = null;
		String sql = "select * from npcshop where npcshop_id=" + npcshop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				npcshop = new NpcShopVO();
				npcshop.setNpcshopId(npcshop_id);
				npcshop.setNpcId(rs.getInt("npc_id"));
				npcshop.setGoodsId(rs.getInt("goods_id"));
				npcshop.setGoodsType(rs.getInt("goods_type"));
				npcshop.setGoodsName(StringUtil.isoToGBK(rs
						.getString("goods_name")));
				npcshop.setNpcShopGoodsbuy(rs.getInt("npc_shop_goodsbuy"));
			}
			rs.close();
			stmt.close();
			return npcshop;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return npcshop;
	}

	/**
	 * �õ��˵��������������Ʒ
	 * 
	 * @param menu_id
	 * @return
	 */
	public int getPriceById(int npcshop_id)
	{
		int price = -1;
		String sql = "select npc_shop_goodsbuy from npcshop where npcshop_id="
				+ npcshop_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next())
			{
				price = rs.getInt("npc_shop_goodsbuy");
			}
			rs.close();
			stmt.close();
			return price;
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return price;
	}

}