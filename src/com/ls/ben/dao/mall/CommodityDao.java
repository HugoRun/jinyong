package com.ls.ben.dao.mall;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;

/**
 * ���ܣ���Ʒdao
 */
public class CommodityDao extends BasicDaoSupport<CommodityVO>
{
	public CommodityDao()
	{
		super("commodity_info", DBConnection.GAME_DB);
	}

	/**
	 * �õ���ҳ������Ʒ,Ԫ����Ʒ,���п�����Ʒ
	 * @return
	 */
	public List<CommodityVO> getDiscountCommodityListOfMainPage()
	{
		return super.getListBySql("where buy_mode=1 and discount<>-1 and (commodity_total=-1 or commodity_total-sell_num>0) order by create_time desc limit 6");
	}
	
	/**
	 * �õ�������Ʒ,Ԫ����Ʒ,���п�����Ʒ
	 * @return
	 */
	public QueryPage getDiscountCommodityList(int page_no)
	{
		String condition_sql = "where buy_mode=1 and discount<>-1 and (commodity_total=-1 or commodity_total-sell_num>0)";
		String order_sql = "order by create_time desc";
		return super.loadPageList(condition_sql, order_sql, page_no);
	}
	
	/**
	 * ���¹�������
	 * @return
	 */
	public void addSellNum(int c_id,int sell_num )
	{
		String update_sql = "update commodity_info set sell_num = sell_num+"+sell_num+"  where id="+c_id+"";
		super.executeUpdateSql(update_sql);
	}
	
	/**
	 * ���¹�������
	 * @return
	 */
	public void addSellNumByHot(int c_id,int sell_num )
	{
		String sql = "update commodity_info set sell_num = sell_num+"+sell_num+" ,is_hotmall = is_hotmall + "+sell_num+" where id="+c_id+"";
		super.executeUpdateSql(sql);
	}
	
	/**
	 * �õ���Ʒ����
	 * @return
	 */
	public CommodityVO getCommodity(String c_id )
	{
		return super.getOneBySql("where id="+c_id);
	}
	
	
	/**
	 * �õ���Ʒ����
	 * @return
	 */
	public CommodityVO getPropCommodity(String prop_id )
	{
		return super.getOneBySql("where prop_id="+prop_id);
	}
	
	/**
	 * �õ���Ʒ��������
	 * @return
	 */
	public PropVO getProp(String c_id )
	{
		PropVO prop = null;
		
		String sql = "select b.* from commodity_info as a,prop as b  where a.prop_id=b.prop_id and a.id="+c_id+"";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) 
			{
				prop = new PropVO();
				prop.setPropID(rs.getInt("prop_id"));
				prop.setPropName(rs.getString("prop_name"));
				prop.setPropDisplay(rs.getString("prop_display"));
				prop.setPropSell(rs.getInt("prop_sell"));
				prop.setPropReLevel(rs.getString("prop_relevel"));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		finally
		{
			dbConn.closeConn();
		}
		
		return prop;
	}
	
	
	/**
	 * �õ���ҳ������Ʒ,��ҳ��ʾ5��������Ʒ
	 * @return
	 */
	public List<CommodityVO> getHotSellCommodityListOfMainPage()
	{
		String condition_sql = " where ((commodity_total<>-1 and commodity_total-sell_num>0) or commodity_total=-1) and buy_mode = 1 and sell_num>0 order by sell_num desc  limit 5";
		return super.getListBySql(condition_sql);
	}
	
	/**
	 * �õ���ҳ�Ƽ���Ʒ,��ҳ��ʾ5���Ƽ���Ʒ
	 * @return
	 */
	public List<CommodityVO> getNewSellCommodityListOfMainPage()
	{
		String condition_sql = "where ((commodity_total<>-1 and commodity_total-sell_num>0) or commodity_total=-1) and is_hot = 1 ";
		return super.getListBySql(condition_sql);
	}
	
	/**
	 * �õ���ҳ������Ʒ
	 * @return
	 */
	public QueryPage getHotSellCommodityList( int page_no )
	{
		String condition_sql = "where ((commodity_total<>-1 and commodity_total-sell_num>0) or commodity_total=-1) ";
		String order_sql = "order by create_time desc";
		return super.loadPageList(condition_sql, order_sql, page_no);
	}
	
	
	/**
	 * �õ���ҳԪ����Ʒ
	 * @return
	 */
	public QueryPage getYuanBaoCommodityList( String type,int page_no )
	{
		String condition_sql = "select count(*) from commodity_info where ((commodity_total<>-1 and commodity_total-sell_num>0) or commodity_total=-1)  and discount=-1 and  buy_mode=1 and type="+type;
		String order_sql = "order by create_time desc";
		return super.loadPageList(condition_sql, order_sql, page_no);
	}
	
	/**
	 * �õ���ҳ������Ʒ
	 * @return
	 */
	public QueryPage getJiFenCommodityList(int buy_mode,String type, int page_no )
	{
		String condition_sql = "where ((commodity_total<>-1 and commodity_total-sell_num>0) or commodity_total=-1) and  buy_mode="+buy_mode+" and type="+type;
		String order_sql = "order by order_num asc";
		return super.loadPageList(condition_sql, order_sql, page_no);
	}
	
	/**
	 * �õ���ҳ������Ʒ
	 * @return
	 */
	public List<CommodityVO> getJiFenCommodityListOfMainPage()
	{
		String condition_sql = "where ((commodity_total<>-1 and commodity_total-sell_num>0) or commodity_total=-1) and  buy_mode=2 order by sell_num desc limit 5";
		return super.getListBySql(condition_sql);
	}
	
	/**
	 * ��Ա��Ʒ
	 * @return
	 */
	public QueryPage getVIPCommodityList(int page_no)
	{
		String condition_sql = "where is_vip=1 and (commodity_total=-1 or commodity_total-sell_num>0)";
		String order_sql = "order by create_time desc";
		return super.loadPageList(condition_sql, order_sql, page_no);
	}
	/**
	 * ��ȡ���͵�ҳ��
	 * @return
	 */
	public List<CommodityVO> getListByType(int type)
	{
		return super.getListBySql("where type = "+type);
	}
	
	protected CommodityVO loadData( ResultSet rs ) throws SQLException
	{
		CommodityVO commodity_info = new CommodityVO();
		commodity_info.setId(rs.getInt("id"));
		commodity_info.setPropId(rs.getInt("prop_id"));
		commodity_info.setPropName(rs.getString("prop_name"));
		commodity_info.setOriginalPrice(rs.getInt("original_price"));
		commodity_info.setType(rs.getInt("type"));
		commodity_info.setBuyMode((rs.getInt("buy_mode")));
		commodity_info.setDiscount(rs.getInt("discount"));
		commodity_info.setCommodityTotal(rs.getInt("commodity_total"));
		commodity_info.setSellNum(rs.getInt("sell_num"));
		commodity_info.setIsUsedAfterBuy(rs.getInt("isUsedAfterBuy"));
		commodity_info.setIsVip(rs.getInt("is_vip"));
		return commodity_info;
	}
}
