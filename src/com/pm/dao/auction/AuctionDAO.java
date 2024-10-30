package com.pm.dao.auction;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;
import com.pm.vo.auction.AuctionVO;

public class AuctionDAO extends DaoBase
{

	/**
	 * ��װ������Ҹ��˰���ת��������
	 * 
	 * @param uPk
	 * @param pPk
	 * @param accouter_id
	 *            װ��id
	 * @param accouter_type
	 *            ��������������
	 * @param propPrice
	 *            ���ҳ��ļ۸�
	 * @param remove_num
	 *            ��Ʒ������
	 */
	public void addPropToAuction(int uPk, int pPk, int accouter_id,
			int accouter_type, int propPrice, String goodsName, int remove_num,
			PlayerPropGroupVO propGroup, int payType, int auctionPrice)
	{
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();

		String sql = "insert into u_auction(u_pk,p_pk,auction_type,pay_type,goods_id,goods_name,goods_number,auction_price,auction_price_auction,buy_price,auction_time,auction_failed,auction_sell,prop_use_control,w_Bonding,w_protect,w_isReconfirm) values('"
				+ uPk
				+ "','"
				+ pPk
				+ "','"
				+ accouter_type
				+ "',"
				+ payType
				+ ",'"
				+ accouter_id
				+ "','"
				+ StringUtil.gbToISO(goodsName)
				+ "','"
				+ remove_num
				+ "','"
				+ propPrice
				+ "',"
				+ auctionPrice
				+ ",0,'"
				+ sf.format(dt)
				+ "',1,1,'"
				+ propGroup.getPropUseControl()
				+ "','"
				+ propGroup.getPropBonding()
				+ "','"
				+ propGroup.getPropProtect()
				+ "','"
				+ propGroup.getPropIsReconfirm() + "')";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("auctionDAO�е� addToAuction��sql : " + sql);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{

			dbConn.closeConn();
		}
	}

	/**
	 * �õ����������pw_type���͵����еĵ���
	 * 
	 * @param p_pk
	 * @param auctionType
	 *            ���������߷���
	 * @return
	 */
	public QueryPage getPagePropsByPpk(int p_pk, int auctionType, int page_no,
			String sortType, int payType)
	{
		QueryPage queryPage = null;

		List<AuctionVO> props = new ArrayList<AuctionVO>();
		AuctionVO vo = new AuctionVO();

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql, page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "select count(*) from u_auction where auction_type="
					+ auctionType
					+ " and auction_failed = 1 and auction_sell != 2 and pay_type="
					+ payType + "";
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new QueryPage(page_no, count);

			if (sortType.equals("time"))
			{

				page_sql = "select * from u_auction where auction_failed = 1 and auction_sell != 2 and auction_type="
						+ auctionType
						+ " and pay_type="
						+ payType
						+ " order by auction_time desc "
						+ "limit "
						+ queryPage.getStartOfPage()
						+ ","
						+ queryPage.getPageSize();
			}
			else
				if (sortType.equals("value"))
				{
					page_sql = "select * from u_auction where auction_failed = 1 and auction_sell != 2 and auction_type="
							+ auctionType
							+ " and pay_type="
							+ payType
							+ " order by CAST(auction_price as UNSIGNED  INTEGER) asc "
							+ "limit "
							+ queryPage.getStartOfPage()
							+ ","
							+ queryPage.getPageSize();
				}
				else
				{
					page_sql = "select * from u_auction where  auction_failed = 1 and auction_sell != 2 and auction_type="
							+ auctionType
							+ "and pay_type="
							+ payType
							+ " limit "
							+ queryPage.getStartOfPage()
							+ ","
							+ queryPage.getPageSize();
				}
			logger.debug(page_sql);

			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(p_pk);
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				vo.setWZbGrade(rs.getInt("w_zb_grade"));
				vo.setSpecialcontent(rs.getInt("specialcontent"));
				if (vo.getWZbGrade() != 0)
				{
					vo.setGoodsName("+" + vo.getWZbGrade()
							+ rs.getString("goods_name"));
				}

				props.add(vo);
			}
			logger.debug("���������������Ϊ : " + props.size());
			rs.close();
			stmt.close();

			queryPage.setResult(props);

		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}

		return queryPage;
	}

	/**
	 * �õ�����������ض����ֵ�list
	 * 
	 * @param p_pk
	 * @param
	 * @param auctionType
	 *            ���������߷���
	 * @return
	 */
	public QueryPage getPagePropByName(int p_pk, String propName, int page_no,
			String sortType, int payType, int auctionType)
	{
		QueryPage queryPage = null;

		List<AuctionVO> props = new ArrayList<AuctionVO>();
		AuctionVO vo = new AuctionVO();

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql, page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "select count(*) from u_auction where auction_failed = 1 and auction_sell = 1 and pay_type="
					+ payType
					+ " and auction_type="
					+ auctionType
					+ " and goods_name like '%"
					+ StringUtil.gbToISO(propName)
					+ "%'";
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new QueryPage(page_no, count);
			if (sortType.equals("time"))
			{
				page_sql = "select * from u_auction where auction_failed = 1 and auction_sell = 1 and pay_type="
						+ payType
						+ " and auction_type="
						+ auctionType
						+ " and goods_name like '%"
						+ StringUtil.gbToISO(propName)
						+ "%' order by auction_time desc "
						+ "limit "
						+ queryPage.getStartOfPage()
						+ ","
						+ queryPage.getPageSize();
			}
			else
				if (sortType.equals("value"))
				{
					page_sql = "select * from u_auction where auction_failed = 1 and auction_sell = 1 and pay_type="
							+ payType
							+ " and auction_type="
							+ auctionType
							+ " and goods_name like '%"
							+ StringUtil.gbToISO(propName)
							+ "%' order by CAST(auction_price as UNSIGNED  INTEGER) asc "
							+ "limit "
							+ queryPage.getStartOfPage()
							+ ","
							+ queryPage.getPageSize();
				}
				else
				{
					page_sql = "select * from u_auction where auction_failed = 1 and auction_sell = 1 and pay_type="
							+ payType
							+ " and auction_type="
							+ auctionType
							+ " and goods_name like '%"
							+ StringUtil.gbToISO(propName)
							+ "%' order by auction_time desc "
							+ "limit "
							+ queryPage.getStartOfPage()
							+ ","
							+ queryPage.getPageSize();
				}
			logger.debug(page_sql);

			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(p_pk);
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));

				props.add(vo);
			}
			logger.debug("���������������Ϊ : " + props.size());
			rs.close();
			stmt.close();

			queryPage.setResult(props);

		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return queryPage;
	}

	/**
	 * ����auction_id ��ѯ������Ϣ
	 * 
	 * @param auction_id
	 *            ������id
	 */
	public AuctionVO getAuctionVOById(String auction_id)
	{
		AuctionVO vo = new AuctionVO();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select * from u_auction where auction_id = " + auction_id;

		try
		{
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				System.out.println(vo.getPPk());
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				vo.setPay_type(rs.getInt("pay_type"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				vo.setAuction_upk(rs.getInt("auction_upk"));
				vo.setAuction_ppk(rs.getInt("auction_ppk"));
				vo.setAuction_start_time(rs.getDate("auction_start_time"));
				vo.setPropUseControl(rs.getInt("prop_use_control"));
				vo.setTableType(rs.getInt("table_type"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setWDurability(rs.getInt("w_durability"));
				vo.setWDuraConsume(rs.getInt("w_dura_consume"));
				vo.setWBonding(rs.getInt("w_Bonding"));
				vo.setWProtect(rs.getInt("w_protect"));
				vo.setWIsReconfirm(rs.getInt("w_isReconfirm"));
				vo.setWPrice(rs.getInt("w_price"));

				vo.setWFyDa(rs.getInt("w_fy_da"));
				vo.setWFyXiao(rs.getInt("w_fy_xiao"));
				vo.setWGjXiao(rs.getInt("w_gj_xiao"));
				vo.setWGjDa(rs.getInt("w_gj_da"));
				vo.setWHp(rs.getInt("w_hp"));
				vo.setWMp(rs.getInt("w_mp"));
				vo.setWJinFy(rs.getInt("w_jin_fy"));

				vo.setWMuFy(rs.getInt("w_mu_fy"));
				vo.setWShuiFy(rs.getInt("w_shui_fy"));
				vo.setWHuoFy(rs.getInt("w_huo_fy"));
				vo.setWTuFy(rs.getInt("w_tu_fy"));

				vo.setWJinGj(rs.getInt("w_jin_gj"));
				vo.setWMuGj(rs.getInt("w_mu_gj"));
				vo.setWShuiGj(rs.getInt("w_shui_gj"));
				vo.setWHuoGj(rs.getInt("w_huo_gj"));
				vo.setWTuGj(rs.getInt("w_tu_gj"));

				vo.setWQuality(rs.getInt("w_quality"));
				vo.setWWxType(rs.getInt("w_wx_type"));
				vo.setSuitId(rs.getInt("suit_id"));
				vo.setWBuffIsEffected(rs.getInt("w_buff_isEffected"));
				vo.setEnchantType(rs.getString("enchant_type"));
				vo.setEnchantValue(rs.getInt("enchant_value"));

				vo.setWZjHp(rs.getInt("w_zj_hp"));
				vo.setWZjMp(rs.getInt("w_zj_mp"));
				vo.setWZjWxGj(rs.getInt("w_zj_wxgj"));
				vo.setWZjWxFy(rs.getInt("w_zj_wxfy"));
				vo.setWZbGrade(rs.getInt("w_zb_grade"));
				vo.setWBondingNum(rs.getInt("w_Bonding_Num"));
				vo.setSpecialcontent(rs.getInt("specialcontent"));
			}

			logger.debug("�����������Ʒ������ : "
					+ StringUtil.isoToGBK(vo.getGoodsName()));
			rs.close();
			stmt.close();

		}
		catch (SQLException e)
		{
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}

		return vo;
	}

	/**
	 * �����ɹ��󣬸�������id����������Ϣ,����1����ɹ�������-1����ʧ��
	 * 
	 */
	public void updateFromAuction(int auction_id, int auction_ppk)
	{
		String sql1 = "update u_auction set auction_sell = 2,auction_ppk="
				+ auction_ppk + " where auction_id=" + auction_id;
		String sql2 = "update u_auction set auction_time = now() where auction_id="
				+ auction_id;
		logger.debug("����������Ϣ״̬��sql :" + sql1 + "sql2 : " + sql2);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			ps = conn.prepareStatement(sql2);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ��Ҿ��ĺ������������Ʒ�ľ�����Ϣ
	 * 
	 */
	public void updateFromAuctionByAuction(int auction_id, int upk, int ppk,
			int auctionPrice, String buyName)
	{
		String sql = "update u_auction set auction_upk=" + upk
				+ ",auction_ppk=" + ppk
				+ ",auction_start_time=now(),buy_price=" + auctionPrice
				+ ",auction_sell=3 ,buy_name='" + buyName
				+ "' where auction_id=" + auction_id + "";
		logger.debug("����������Ϣ״̬��sql :" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/***************************************************************************
	 * �˻�������ʧ������ʯ
	 */
	public void addCoopperForFalseAuction(int p_pk, int cooper)
	{
		String sql = "update u_part_info set p_copper=p_copper+" + cooper
				+ " where p_pk=" + p_pk + "";
		logger.debug("���������ʯsql :" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ������Ʒ�Ĵ���ʮ������û�������˲��뾺�����ĳɹ�
	 * 
	 * ��ѯ�����Ͼ��ĳɹ������ľ�����Ϣ
	 */
	public List getAuctionSuccess()
	{
		String sql = "select*from u_auction where auction_failed=1 and auction_sell=3 and (now()-auction_start_time)>60*10";
		logger.debug("�õ����ĳɹ���sql :" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		List list = new ArrayList();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AuctionVO vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				vo.setPay_type(rs.getInt("pay_type"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				vo.setAuction_upk(rs.getInt("auction_upk"));
				vo.setAuction_ppk(rs.getInt("auction_ppk"));
				vo.setAuction_start_time(rs.getDate("auction_start_time"));
				vo.setPropUseControl(rs.getInt("prop_use_control"));
				vo.setTableType(rs.getInt("table_type"));
				vo.setGoodsType(rs.getInt("goods_type"));
				vo.setWDurability(rs.getInt("w_durability"));
				vo.setWDuraConsume(rs.getInt("w_dura_consume"));
				vo.setWBonding(rs.getInt("w_Bonding"));
				vo.setWProtect(rs.getInt("w_protect"));
				vo.setWIsReconfirm(rs.getInt("w_isReconfirm"));
				vo.setWPrice(rs.getInt("w_price"));

				vo.setWFyDa(rs.getInt("w_fy_da"));
				vo.setWFyXiao(rs.getInt("w_fy_xiao"));
				vo.setWGjXiao(rs.getInt("w_gj_xiao"));
				vo.setWGjDa(rs.getInt("w_gj_da"));
				vo.setWHp(rs.getInt("w_hp"));
				vo.setWMp(rs.getInt("w_mp"));
				vo.setWJinFy(rs.getInt("w_jin_fy"));

				vo.setWMuFy(rs.getInt("w_mu_fy"));
				vo.setWShuiFy(rs.getInt("w_shui_fy"));
				vo.setWHuoFy(rs.getInt("w_huo_fy"));
				vo.setWTuFy(rs.getInt("w_tu_fy"));

				vo.setWJinGj(rs.getInt("w_jin_gj"));
				vo.setWMuGj(rs.getInt("w_mu_gj"));
				vo.setWShuiGj(rs.getInt("w_shui_gj"));
				vo.setWHuoGj(rs.getInt("w_huo_gj"));
				vo.setWTuGj(rs.getInt("w_tu_gj"));

				vo.setWQuality(rs.getInt("w_quality"));
				vo.setWWxType(rs.getInt("w_wx_type"));
				vo.setSuitId(rs.getInt("suit_id"));
				vo.setWBuffIsEffected(rs.getInt("w_buff_isEffected"));
				vo.setEnchantType(rs.getString("enchant_type"));
				vo.setEnchantValue(rs.getInt("enchant_value"));

				vo.setWZjHp(rs.getInt("w_zj_hp"));
				vo.setWZjMp(rs.getInt("w_zj_mp"));
				vo.setWZjWxGj(rs.getInt("w_zj_wxgj"));
				vo.setWZjWxFy(rs.getInt("w_zj_wxfy"));
				vo.setWZbGrade(rs.getInt("w_zb_grade"));
				vo.setWBondingNum(rs.getInt("w_Bonding_Num"));
				vo.setSpecialcontent(rs.getInt("specialcontent"));
				list.add(vo);
			}
			ps.close();
		}
		catch (SQLException e)
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
	 * ����ʱ�䳬������Ľ��¼�
	 */
	public void updateThanThreeDay()
	{

		String sql = "update u_auction set auction_failed = 2 where auction_sell = 1 and now() > (auction_time + INTERVAL 2 DAY)";
		logger.debug("����������Ϣ��sql :" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ����ʱ�䳬������Ľ���û��
	 */
	public void deleteThanSixDay()
	{
		String sql = "delete from u_auction where auction_sell = 1 and now() > (auction_time + INTERVAL 5 DAY)";
		logger.debug("����������Ϣ��sql :" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * �����ɹ��Ľ�Ǯ�����ڻ�û��ȡ�صĽ���û��
	 */
	public void updateMoneySevenDay()
	{
		String sql = "delete from u_auction where auction_sell = 2 and now() > (auction_time + INTERVAL 5 DAY)";
		logger.debug("����������Ϣ��sql :" + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ������Ʒ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getGoodsList(String pPk, int auctionType)
	{
		List<AuctionVO> goodsList = new ArrayList<AuctionVO>();

		String sql = "select * from u_auction where p_pk="
				+ pPk
				+ " and auction_failed = 2 and auction_sell = 1 and auction_type="
				+ auctionType + "";
		String sql1 = "select * from u_auction where auction_ppk="
				+ pPk
				+ " and auction_failed =1 and auction_sell =2 and auction_type="
				+ auctionType + "";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("�������ֿ������Ʒ��sql :" + sql);
		AuctionVO vo = new AuctionVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				goodsList.add(vo);
			}
			rs = stmt.executeQuery(sql1);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				goodsList.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return goodsList;
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ������Ʒ������Ľ�Ǯ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getMoneyList(String pPk)
	{
		List<AuctionVO> goodsList = new ArrayList<AuctionVO>();

		String sql = "select * from u_auction where p_pk=" + pPk
				+ " and auction_failed = 1 and auction_sell = 2 and u_pk!=-1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("�������ֿ���Ľ�Ǯsql :" + sql);
		AuctionVO vo = new AuctionVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));
				vo.setPay_type(rs.getInt("pay_type"));
				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				goodsList.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return goodsList;
	}

	/**
	 * ɾ��id����������Ϣ,����1����ɹ�������-1����ʧ��
	 * 
	 */
	public int deleteFromAuction(int auction_id)
	{
		int i = -1;
		String sql1 = "delete from u_auction where auction_id=" + auction_id;

		logger.debug("����������Ϣ״̬��sql :" + sql1);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			i = 1;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return i;

	}

	/**
	 * ɾ��id����������Ϣ,����1����ɹ�������-1����ʧ��
	 * 
	 */
	public int updateFromAuction(String fieldName,int auction_id)
	{
		int i = -1;
		String sql1 = "update u_auction set "+fieldName+"=-1 where auction_id="
				+ auction_id;

		logger.debug("����������Ϣ״̬��sql :" + sql1);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.info(sql1);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps.close();
			i = 1;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return i;

	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ����δ��������Ʒ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getNotSellList(int pPk)
	{
		List<AuctionVO> goodsList = new ArrayList<AuctionVO>();

		String sql = "select * from u_auction where p_pk=" + pPk
				+ " and auction_failed = 2 and auction_sell = 1";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("�������ֿ����δ����sql :" + sql);
		AuctionVO vo = new AuctionVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				goodsList.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return goodsList;
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ����δ��������Ʒ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getNotSellGoodsList(int pPk)
	{
		List<AuctionVO> goodsList = new ArrayList<AuctionVO>();

		String sql = "select * from u_auction where p_pk="
				+ pPk
				+ " and auction_failed = 2 and auction_sell = 1 order by auction_time desc";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("�������ֿ����δ����sql :" + sql);
		AuctionVO vo = new AuctionVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				goodsList.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return goodsList;
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ���ı�û�յ����
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getLostGoodsLists(int pPk)
	{
		List<AuctionVO> goodsList = new ArrayList<AuctionVO>();

		String sql = "select * from u_auction where p_pk=" + pPk
				+ " and auction_failed = 3 order by auction_time desc";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		// logger.debug("�������ֿ���ı�û��sql :"+sql);
		AuctionVO vo = new AuctionVO();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUPk(rs.getInt("u_pk"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				vo.setAuctionSell(rs.getInt("auction_sell"));

				vo.setAuctionTime(rs.getString("auction_time"));
				vo.setAuctionType(rs.getInt("auction_type"));
				vo.setBuyName(rs.getString("buy_name"));
				vo.setBuyPrice(rs.getInt("buy_price"));
				vo.setGoodsId(rs.getInt("goods_id"));

				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				vo.setGoodsPrice(rs.getInt("auction_price"));
				vo.setAuction_price(rs.getInt("auction_price_auction"));
				goodsList.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return goodsList;
	}

	// ��ȡ����ʱ�䳬��������б�
	public List<AuctionVO> getThanThreeDayList()
	{
		String sql = "select * from u_auction where auction_sell = 1 and now() > (auction_time + INTERVAL 2 DAY)";
		logger.debug("��ȡ����һ��������������Ϣ��sql :" + sql);
		List<AuctionVO> list = new ArrayList<AuctionVO>();
		AuctionVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setAuctionFailed(rs.getInt("auction_failed"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	// �����Ʒ�����δȡ�ص���Ʒ�嵥
	public List<AuctionVO> getThanSixDayList()
	{
		String sql = "select * from u_auction where auction_sell = 1 and now() > (auction_time + INTERVAL 5 DAY)";
		logger.debug("��ȡ����һ��������������Ϣ��sql :" + sql);
		List<AuctionVO> list = new ArrayList<AuctionVO>();
		AuctionVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setGoodsName(rs.getString("goods_name"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	// �����Ʒ�����ɹ���������δȡ���������嵥
	public List<AuctionVO> getThanSevenDay()
	{
		String sql = "select * from u_auction where auction_sell = 2 and now() > (auction_time + INTERVAL 5 DAY)";
		logger.debug("��ȡ����һ��������������Ϣ��sql :" + sql);
		List<AuctionVO> list = new ArrayList<AuctionVO>();
		AuctionVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionVO();
				vo.setUAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setGoodsName(rs.getString("goods_name"));
				vo.setGoodsNumber(rs.getInt("goods_number"));
				// vo.setGoodsPrice(rs.getInt("auction_price"));
				list.add(vo);
			}
			rs.close();
			stmt.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	// ��װ����������������
	public void addToAuction(int u_pk, int p_pk, PlayerEquipVO vo,
			int auctionType, int propPrice, int payType, int auctionPrice)
	{
		String sql = "insert into u_auction values (null,"
				+ u_pk
				+ ","
				+ p_pk
				+ ","
				+ auctionType
				+ ","
				+ payType
				+ ","
				+ vo.getPwPk()
				+ ",'"
				+ vo.getFullName()
				+ "',1,"
				+ propPrice
				+ ","
				+ auctionPrice
				+ ",0,now(),0,0,now(),1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'',0,'','','','',0,0,0)";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("auctionDAO�е� addToAuction��sql : " + sql);
		logger.info(sql);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
	}

	/**
	 * ��װ����������ת�����˰���
	 * 
	 * @param pk
	 * @param auctionVO
	 * @return
	 */
	public int putGoodsToWrap(int pk, AuctionVO vo)
	{
		int i = 0;
		String sql = "insert into u_part_equip values(null,'" + pk + "','"
				+ vo.getTableType() + "','" + vo.getGoodsType() + "','"
				+ vo.getGoodsId() + "','" + vo.getGoodsName() + "','"
				+ vo.getWDurability() + "','" + vo.getWDuraConsume() + "','"
				+ vo.getWBonding() + "','" + vo.getWProtect() + "','"
				+ vo.getWIsReconfirm() + "','" + vo.getWPrice() + "','"
				+ vo.getWFyXiaoYuan() + "','" + vo.getWFyDaYuan() + "','"
				+ vo.getWGjXiaoYuan() + "','" + vo.getWGjDaYuan() + "','"
				+ vo.getWHp() + "','" + vo.getWMp() + "','" + vo.getWJinFy()
				+ "','" + vo.getWMuFy() + "','" + vo.getWShuiFy() + "','"
				+ vo.getWHuoFy() + "','" + vo.getWTuFy() + "','"
				+ vo.getWJinGj() + "','" + vo.getWMuGj() + "','"
				+ vo.getWShuiGj() + "','" + vo.getWHuoGj() + "','"
				+ vo.getWTuGj() + "','0','" + vo.getWQuality() + "','"
				+ vo.getSuitId() + "','" + vo.getWWxType() + "','"
				+ vo.getWBuffIsEffected() + "','" + vo.getEnchantType() + "',"
				+ vo.getEnchantValue() + "," + vo.getWZjHp() + ","
				+ vo.getWZjMp() + "," + vo.getWZjWxGj() + "," + vo.getWZjWxFy()
				+ "," + vo.getWZbGrade() + ",now(),0," + vo.getWBondingNum()
				+ "," + vo.getSpecialcontent() + ")";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("auctionDAO�е� addToAuction��sql : " + sql);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			i = ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{

			dbConn.closeConn();
		}
		return i;
	}

	/**
	 * ����������ӵ���
	 * 
	 * @param pk
	 * @param auctionVO
	 */
	public int insertPropGroupInfo(int pk, AuctionVO auctionVO, int pg_type,
			int prop_type, int prop_price)
	{
		int i = -1;
		String sql = "insert into u_propgroup_info values(null,'" + pk + "','"
				+ pg_type + "','" + auctionVO.getGoodsId() + "','" + prop_type
				+ "','" + auctionVO.getWBonding() + "','"
				+ auctionVO.getWProtect() + "','" + auctionVO.getWIsReconfirm()
				+ "','" + auctionVO.getPropUseControl() + "','"
				+ auctionVO.getGoodsName() + "','" + prop_price + "','"
				+ auctionVO.getGoodsNumber() + "',now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("auctionDAO�е� addToAuction��sql : " + sql);
		try
		{
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			i = ps.executeUpdate();
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			dbConn.closeConn();
		}
		return i;
	}
}
