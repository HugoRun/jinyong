package com.pm.dao.auction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.db.DBConnection;
import com.pm.constant.AuctionNumber;
import com.pm.vo.auction.AuctionYBVO;

public class AuctionYbDao extends DaoBase {

	/**
	 * 获取拍卖的元宝列表中的指定的元宝信息
	 * @param roleInfo
	 * @param page_no
	 * @return
	 */
	public QueryPage getYbPageList(RoleEntity roleInfo, int page_no)
	{
		QueryPage queryPage = null;
		
		List<AuctionYBVO> ybList = new ArrayList<AuctionYBVO>();
		AuctionYBVO vo = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql;
		int count = 0;
		
		try
		{
			stmt = conn.createStatement();
			count_sql = "SELECT COUNT(*) FROM u_auction_yb WHERE uyb_state=" + AuctionNumber.YUANSELLING + "";
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			String page_sql = "SELECT * FROM u_auction_yb WHERE uyb_state = " + AuctionNumber.YUANSELLING + 
					" LIMIT " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			
			logger.debug(page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new AuctionYBVO();
				vo.setUybId(rs.getInt("uyb_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setUybState(rs.getInt("uyb_state"));
				vo.setYbNum(rs.getInt("yb_num"));
				vo.setYbPrice(rs.getInt("yb_price"));
				vo.setAuctionTime(rs.getString("auction_time"));
				ybList.add(vo);
			}
			logger.debug("拍卖场的搜索结果为 : "+ybList.size());
			rs.close();
			stmt.close();
			
			queryPage.setResult(ybList);

		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
 		
		return queryPage;
		
	}

	/**
	 * 根据 拍卖元宝表id 获得 元宝拍卖信息
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybId(String uyb_id,int uyb_state)
	{
		String sql = "SELECT * FROM u_auction_yb WHERE uyb_id = "+uyb_id+" AND uyb_state = "+uyb_state;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionYBVO vo = null;
		logger.debug("根据 拍卖"+GameConfig.getYuanbaoName()+"表id 获得 "+GameConfig.getYuanbaoName()+"拍卖信息="+sql);
		
		conn = dbConn.getConn();
		try
		{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		if( rs.next() )
    		{
    			vo = new AuctionYBVO();
    			vo.setUybId(rs.getInt("uyb_id"));
    			vo.setPPk(rs.getInt("p_pk"));
    			vo.setUybState(rs.getInt("uyb_state"));
    			vo.setYbNum(rs.getInt("yb_num"));
    			vo.setYbPrice(rs.getInt("yb_price"));
    			vo.setAuctionTime(rs.getString("auction_time"));
    		}
    		
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return vo;
	}
	
	/**
	 * 根据 拍卖元宝表id 获得 元宝拍卖信息
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybId(String uyb_id,int uyb_state,int pPk)
	{
		String sql = "SELECT * FROM u_auction_yb WHERE uyb_id = "+uyb_id+" AND uyb_state = "+uyb_state + " AND p_pk="+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionYBVO vo = null;
		logger.debug("根据 拍卖"+GameConfig.getYuanbaoName()+"表id 获得"+GameConfig.getYuanbaoName()+"拍卖信息="+sql);
		
		conn = dbConn.getConn();
		try
		{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		if( rs.next() )
    		{
    			vo = new AuctionYBVO();
    			vo.setUybId(rs.getInt("uyb_id"));
    			vo.setPPk(rs.getInt("p_pk"));
    			vo.setUybState(rs.getInt("uyb_state"));
    			vo.setYbNum(rs.getInt("yb_num"));
    			vo.setYbPrice(rs.getInt("yb_price"));
    			vo.setAuctionTime(rs.getString("auction_time"));
    		}
    		
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return vo;
	}

	/**
	 * 获得所有超过三天的正在卖出的元宝 信息
	 * @return
	 */
	public List<AuctionYBVO> getAllAuctionYbList()
	{
		List<AuctionYBVO> list = new ArrayList<AuctionYBVO>();
		String sql = "SELECT * FROM u_auction_yb WHERE now() > (auction_time + INTERVAL 3 DAY) AND uyb_state="+AuctionNumber.YUANSELLING;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionYBVO vo = null;
		logger.debug("获得所有超过三天的正在卖出的"+GameConfig.getYuanbaoName()+" 信息="+sql);
		conn = dbConn.getConn();
		try
		{
    		stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		while( rs.next() )
    		{
    			vo = new AuctionYBVO();
    			vo.setUybId(rs.getInt("uyb_id"));
    			vo.setPPk(rs.getInt("p_pk"));
    			vo.setUybState(rs.getInt("uyb_state"));
    			vo.setYbNum(rs.getInt("yb_num"));
    			vo.setYbPrice(rs.getInt("yb_price"));
    			vo.setAuctionTime(rs.getString("auction_time"));
    			list.add(vo);
    		}
    		
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	/**
	 * 将超过三天拍卖失败的元宝置于下架状态
	 * @param yuannotsell
	 */
	public void updateAllSailedYuanbaoToUnSaleEd()
	{
		String sql = "UPDATE u_auction_yb SET uyb_state = "+AuctionNumber.YUANNOTSELL+" WHERE now() > (auction_time + INTERVAL 3 DAY) AND uyb_state="+AuctionNumber.YUANSELLING;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("将超过三天拍卖失败的"+GameConfig.getYuanbaoName()+"置于下架状态="+sql);
		conn = dbConn.getConn();
		try
		{
    		stmt = conn.createStatement();
    		stmt.execute(sql);
    		stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}    		
	}

	/**
	 * 将此条元宝拍卖信息的状态置为sellState
	 * @param uybId
	 * @param sellState
	 */
	
	public void updateYuanbaoState(int uybId, int sellState)
	{
		String sql = "UPDATE u_auction_yb SET uyb_state = "+sellState+" WHERE uyb_id="+uybId;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("将此条"+GameConfig.getYuanbaoName()+"拍卖信息的状态置为sellState="+sql);
		try
		{
    		stmt = conn.createStatement();
    		stmt.execute(sql);
    		stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}  		
	}

	/**
	 * 将拍卖信息插入到元宝拍卖场中
	 * @param pPk
	 * @param paimaiYuanBao
	 * @param money
	 */
	public void insertYuanbao(int pPk, int paimaiYuanBao, long money)
	{
		String sql = "INSERT INTO u_auction_yb VALUES (null,"+pPk+",1,"+paimaiYuanBao+","+money+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("将拍卖信息插入到"+GameConfig.getYuanbaoName()+"拍卖场中="+sql);
		try
		{
    		stmt = conn.createStatement();
    		stmt.execute(sql);
    		stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}  		
	}

	/**
	 * 记录元宝拍卖
	 * @param pPk 主动操作者的pPk
	 * @param auctionYBVO	
	 * @param jiluString	要记录的string
	 */
	public void insertYuanbaoInfo(int pPk, AuctionYBVO auctionYBVO,
			String jiluString)
	{
		String sql = "INSERT INTO u_auctionyb_auctioninfo VALUES (null,"+pPk+
					","+auctionYBVO.getPPk()+",'"+jiluString+"',now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("记录"+GameConfig.getYuanbaoName()+"拍卖="+sql);
		try
		{
    		stmt = conn.createStatement();
    		stmt.execute(sql);
    		stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}  		
		
		
	}

	

	

}
