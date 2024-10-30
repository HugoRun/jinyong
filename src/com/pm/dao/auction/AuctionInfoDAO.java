package com.pm.dao.auction;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;
import com.pm.vo.auction.AuctionInfoVO;
import com.pm.vo.auction.AuctionVO;

public class AuctionInfoDAO extends DaoBase
{
	/**
	 * @param pPk 个人角色id
	 * @return	list 
	 */
	public QueryPage getAuctionInfoList(int pPk,int page_no){
		QueryPage queryPage = null;
		List<AuctionInfoVO> list = new ArrayList<AuctionInfoVO>();
		String countSql="select count(*) from u_auction_info where p_pk="+pPk;
		AuctionInfoVO auctionInfoVO = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		int count=0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(countSql);
			if (rs.next())
			{
				count=rs.getInt(1);
			} 
			rs.close();
			rs.close();

			queryPage = new QueryPage(page_no, count);
			stmt = conn.createStatement();
			String sql = "SELECT * FROM u_auction_info where p_pk=" + pPk+" order by addInfoTime desc limit "+queryPage.getStartOfPage()+","+queryPage.getPageSize()+" ";
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				auctionInfoVO = new AuctionInfoVO();
				auctionInfoVO.setAuctionInfoId(rs.getInt("auction_info_id"));
				auctionInfoVO.setPPk(rs.getInt("p_pk"));
				auctionInfoVO.setAuctionInfo(rs.getString("auction_info"));
				list.add(auctionInfoVO);
				
			} 
			rs.close();
			stmt.close();
		}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
			queryPage.setResult(list);
			return queryPage;
		}
	/**
	 * 
	 * @param auctionId 拍卖信息表id
	 * @return	AuctionInfoVO 拍卖信息VO
	 */
	public AuctionInfoVO getAuctionView(int auctionId){
		AuctionInfoVO auctionInfoVO = null;
		String sql = "SELECT * FROM u_auction_info where auction_id=" + auctionId;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				auctionInfoVO = new AuctionInfoVO();
				auctionInfoVO.setAuctionInfoId(rs.getInt("auction_info_id"));
				auctionInfoVO.setPPk(rs.getInt("p_pk"));
				auctionInfoVO.setAuctionInfo(rs.getString("auction_info"));
				rs.close();
				stmt.close();
			} 
		}catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
		return auctionInfoVO;
	}
	
	/**
	 * @param 
	 * @param
	 */
	public void insertAuction(int pPk,String auction_info){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql =  "INSERT INTO u_auction_info values(null,"+pPk+",'"+StringUtil.gbToISO(auction_info)+"','"+sf.format(dt)+"')";
		logger.debug(sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		}
	
	/**
	 * 向拍卖信息表插入一条信息,
	 * @param pPk 购买者id
	 * @param auctionVO
	 * @con 需要插入的语句
	 */
	public void insertAuctionInfo(AuctionVO auctionVO,String con){
		int pPk2 = auctionVO.getPPk();			//拍卖者id
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		
		String sql = "INSERT INTO u_auction_info values(null,"+pPk2+",'"+StringUtil.gbToISO(con)+"','"+sf.format(dt)+"')";
		logger.debug("向拍卖信息表中插入信息 :"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		try{
			
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();

			} 
		catch (NullPointerException e)
		{
			logger.debug("nullPoint");
			e.printStackTrace();
		} 
			catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				dbConn.closeConn();
			}
	}
	
	/**
	 * 删除按时间排序的10条之后的个人拍卖信息记录
	 * @param pPk 个人角色id
	 */
	public void clearAuctionInfo(int pPk){
		String sql = "SELECT auction_info_id from u_auction_info where p_pk="+pPk+" order by addInfoTime desc ";
		//logger.debug("从拍卖信息表中取出信息id :"+sql);
		List<Integer> list = new ArrayList<Integer>();
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				list.add(rs.getInt("auction_info_id"));
			}
			rs.close();
			stmt.close();
		}catch (NullPointerException e)
		{
			logger.debug("nullPoint1");
			e.printStackTrace();
		} catch (SQLException e)
		{
			logger.debug("sql1");
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		} 
		String sql1 = "delete from u_auction_info where p_pk="+pPk+" and auction_info_id=";
		DBConnection dbConn2 = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn2.getConn();
	
		try
		{
			stmt = conn.createStatement();
			for(int i = 100; i < list.size(); i++){
				stmt.executeUpdate(sql1+list.get(i));
				logger.debug(sql1+list.get(i));
			}
			stmt.close();
		}catch (NullPointerException e)
		{
			logger.debug("nullPoint2");
			e.printStackTrace();
		}catch (SQLException e)
		{
			logger.debug("sql2");
			e.printStackTrace();
		}finally
		{
			
			dbConn2.closeConn();
		} 

		}
	}

