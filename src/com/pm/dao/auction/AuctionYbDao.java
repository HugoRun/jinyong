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
	 * ��ȡ������Ԫ���б��е�ָ����Ԫ����Ϣ
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
			count_sql = "select count(*) from u_auction_yb where uyb_state=" + AuctionNumber.YUANSELLING + "";
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			String page_sql = "select * from u_auction_yb where  uyb_state = " + AuctionNumber.YUANSELLING + 
					" limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			
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
			logger.debug("���������������Ϊ : "+ybList.size());
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
	 * ���� ����Ԫ����id ��� Ԫ��������Ϣ
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybId(String uyb_id,int uyb_state)
	{
		String sql = "select * from u_auction_yb where uyb_id = "+uyb_id+" and uyb_state = "+uyb_state;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionYBVO vo = null;
		logger.debug("���� ����"+GameConfig.getYuanbaoName()+"��id ��� "+GameConfig.getYuanbaoName()+"������Ϣ="+sql);
		
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
	 * ���� ����Ԫ����id ��� Ԫ��������Ϣ
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybId(String uyb_id,int uyb_state,int pPk)
	{
		String sql = "select * from u_auction_yb where uyb_id = "+uyb_id+" and uyb_state = "+uyb_state + " and p_pk="+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionYBVO vo = null;
		logger.debug("���� ����"+GameConfig.getYuanbaoName()+"��id ���"+GameConfig.getYuanbaoName()+"������Ϣ="+sql);
		
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
	 * ������г������������������Ԫ�� ��Ϣ
	 * @return
	 */
	public List<AuctionYBVO> getAllAuctionYbList()
	{
		List<AuctionYBVO> list = new ArrayList<AuctionYBVO>();
		String sql = "select * from u_auction_yb where now() > (auction_time + INTERVAL 3 DAY) and uyb_state="+AuctionNumber.YUANSELLING;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionYBVO vo = null;
		logger.debug("������г������������������"+GameConfig.getYuanbaoName()+" ��Ϣ="+sql);
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
	 * ��������������ʧ�ܵ�Ԫ�������¼�״̬
	 * @param yuannotsell
	 */
	public void updateAllSailedYuanbaoToUnSaleEd()
	{
		String sql = "update u_auction_yb set uyb_state = "+AuctionNumber.YUANNOTSELL+" where now() > (auction_time + INTERVAL 3 DAY) and uyb_state="+AuctionNumber.YUANSELLING;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("��������������ʧ�ܵ�"+GameConfig.getYuanbaoName()+"�����¼�״̬="+sql);
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
	 * ������Ԫ��������Ϣ��״̬��ΪsellState
	 * @param uybId
	 * @param sellState
	 */
	
	public void updateYuanbaoState(int uybId, int sellState)
	{
		String sql = "update u_auction_yb set uyb_state = "+sellState+" where uyb_id="+uybId;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("������"+GameConfig.getYuanbaoName()+"������Ϣ��״̬��ΪsellState="+sql);
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
	 * ��������Ϣ���뵽Ԫ����������
	 * @param pPk
	 * @param paimaiYuanBao
	 * @param money
	 */
	public void insertYuanbao(int pPk, int paimaiYuanBao, long money)
	{
		String sql = "insert into u_auction_yb values (null,"+pPk+",1,"+paimaiYuanBao+","+money+",now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("��������Ϣ���뵽"+GameConfig.getYuanbaoName()+"��������="+sql);
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
	 * ��¼Ԫ������
	 * @param pPk ���������ߵ�pPk
	 * @param auctionYBVO	
	 * @param jiluString	Ҫ��¼��string
	 */
	public void insertYuanbaoInfo(int pPk, AuctionYBVO auctionYBVO,
			String jiluString)
	{
		String sql = "insert into u_auctionyb_auctioninfo values (null,"+pPk+
					","+auctionYBVO.getPPk()+",'"+jiluString+"',now())";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		logger.debug("��¼"+GameConfig.getYuanbaoName()+"����="+sql);
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
