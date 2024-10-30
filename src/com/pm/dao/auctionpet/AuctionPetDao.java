package com.pm.dao.auctionpet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.dao.DaoBase;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;
import com.pm.vo.auctionpet.AuctionPetInfoVO;
import com.pm.vo.auctionpet.AuctionPetVO;

public class AuctionPetDao extends DaoBase 
{
	/**
	 * ������ϸ��Ϣ�鿴,����petpk
	 */
	public AuctionPetVO getPetInfoView(String petPk) {
		String sql = "select * from u_auction_pet where pet_pk='" + petPk+ "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionPetVO vo = null;
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setAuctionStatus(rs.getInt("auction_status"));
				vo.setPetPrice(rs.getInt("pet_price"));
				vo.setPetAuctionTime(rs.getString("pet_auction_time"));
				vo.setPetPk(rs.getInt("pet_pk"));
				
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetGrade(rs.getInt("pet_grade"));
				
				vo.setPetExp(rs.getString("pet_exp"));
				vo.setPetBenExp(rs.getString("pet_ben_exp"));
				vo.setPetXiaExp(rs.getString("pet_xia_exp"));
				vo.setPetGjXiao(rs.getInt("pet_gj_xiao"));
				vo.setPetGjDa(rs.getInt("pet_gj_da"));
				
				vo.setPetSale(rs.getInt("pet_sale"));
				vo.setPetImg(rs.getString("pet_img"));
				vo.setPetGrow(rs.getDouble("pet_grow"));
				vo.setPetWx(rs.getInt("pet_wx"));
				vo.setPetWxValue(rs.getInt("pet_wx_value"));
				
				vo.setPetSkillOne(rs.getInt("pet_skill_one"));
				vo.setPetSkillTwo(rs.getInt("pet_skill_two"));
				vo.setPetSkillThree(rs.getInt("pet_skill_three"));
				vo.setPetSkillFour(rs.getInt("pet_skill_four"));
				vo.setPetSkillFive(rs.getInt("pet_skill_five"));
				
				vo.setPetLife(rs.getInt("pet_life"));
				vo.setPetIsAutoGrow(rs.getInt("pet_isAutoGrow"));
				vo.setPetType(rs.getInt("pet_type"));
				vo.setPetIsBring(rs.getInt("pet_isBring"));
				vo.setPetFatigue(rs.getInt("pet_fatigue"));
				
				vo.setPetLonge(rs.getInt("pet_longe"));
				vo.setLongeNumber(rs.getInt("longe_number"));
				vo.setLongeNumberOk(rs.getInt("longe_number_ok"));
				vo.setSkillControl(rs.getInt("skill_control"));
				vo.setPetInitNum(rs.getInt("pet_init_num"));
				vo.setPetViolenceDrop(rs.getDouble("pet_violence_drop"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return vo;
	}
	
	//������������Ϣ
	public int insertAuctionPet(int input_money,PetInfoVO vo){
		int i = -1;
		String sql = "insert into u_auction_pet values (null,1,"+input_money+",now(),"+vo.getPetPk()+","+
						vo.getPPk()+","+vo.getPetId()+",'"+vo.getBasicPetName()+"','"+vo.getBasicPetName()+"',"+
						vo.getPetGrade()+",'"+vo.getPetExp()+"','"+vo.getPetExp()+"','"+vo.getPetXiaExp()+"',"+vo.getPetGjXiao()+","+
						vo.getPetGjDa()+","+vo.getPetSale()+",'',"+vo.getPetGrow()+","+vo.getPetWx()+","+
						vo.getPetWxValue()+","+vo.getPetSkillOne()+","+vo.getPetSkillTwo()+","+vo.getPetSkillThree()+","+vo.getPetSkillFour()+","+
						vo.getPetSkillFive()+","+vo.getPetLife()+","+vo.getPetIsAutoGrow()+","+vo.getPetType()+","+vo.getPetIsBring()+","+vo.getPetFatigue()+","+
						vo.getPetLonge()+","+vo.getLongeNumber()+","+vo.getLongeNumberOk()+","+vo.getSkillControl()+","+vo.getPetInitNum()+","+vo.getPetViolenceDrop()+")";
		logger.debug("���������������sql"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		
		return i;
	}

	/** ���ָ����pet_list */
	public QueryPage getPetList(int page_no,String searchType)
	{
		logger.debug("page_no="+page_no+" ,searchType="+searchType);
		QueryPage queryPage = null;
		
		List<AuctionPetVO> list = new ArrayList<AuctionPetVO>();
		AuctionPetVO vo = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql,page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "select count(*) from u_auction_pet where auction_status = 1";
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			
			if(searchType.equals("time")){
			
				page_sql = "select * from u_auction_pet where auction_status = 1 order by pet_auction_time desc " +
						"limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			}else if(searchType.equals("money")){
				page_sql = "select * from u_auction_pet where auction_status = 1 order by CAST(pet_price as UNSIGNED  INTEGER) asc " +
						"limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			}else {
				page_sql = "select * from u_auction_pet where auction_status = 1 order by pet_auction_time desc " + 
						"limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			}
			logger.debug(page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPetPrice(rs.getInt("pet_price"));
				vo.setPetAuctionTime(rs.getString("pet_auction_time"));
				vo.setPetPk(rs.getInt("pet_pk"));

				vo.setPetId(rs.getInt("pet_id"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetNickname(rs.getString("pet_nickname"));
				vo.setPetWx(rs.getInt("pet_wx"));
				
				
				list.add(vo);
			}
			logger.debug("���������������Ϊ : "+list.size());
			rs.close();
			stmt.close();
			queryPage.setResult(list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		
		return queryPage;
	}
	
	//������˳������Ϣ
	public int insertPersonPet(int pPk, AuctionPetVO vo){
		int i = -1;
		String sql = "insert into p_pet_info values (null,"+Integer.valueOf(pPk)+","
					+vo.getPetId()+",'"+vo.getPetName()+"','"+vo.getPetNickname()+"',"
					
					+vo.getPetGrade()+",'"+vo.getPetExp()+"','"+vo.getPetBenExp()+"','"+vo.getPetXiaExp()+"','"
					
					+vo.getPetGjXiao()+"','"+vo.getPetGjDa()+"',"+vo.getPetSale()+",'"+vo.getPetImg()+"',"+vo.getPetGrow()+","
					
					+vo.getPetWx()+","+vo.getPetWxValue()+","+vo.getPetSkillOne()+","+vo.getPetSkillTwo()
					
					+","+vo.getPetSkillThree()+","+vo.getPetSkillFour()+","+vo.getPetSkillFive()+",'"+vo.getPetLife()+"','"
					
					+vo.getPetIsAutoGrow()+"',"+vo.getPetIsBring()+","+vo.getPetFatigue()+","+vo.getPetLonge()+","
					
					+vo.getLongeNumber()+","+vo.getLongeNumberOk()+",'"+vo.getSkillControl()+"','"+vo.getPetType()+"','"+vo.getPetInitNum()+"','"
					+vo.getPetViolenceDrop()+"')";
		logger.debug("������˳�����sql"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			i = 1;
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return i;
	}

	// ���¸����������״̬
	public void updateAuctionStatus(String petPk, int i)
	{
		String sql = "update u_auction_pet set auction_status = "+i+" where pet_pk="+petPk;
		logger.debug("���¸����������״̬��sql"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
	}

	//���ָ��������petInfoList
	public List<AuctionPetInfoVO> getPetInfoList(int pPk, int i)
	{
		List<AuctionPetInfoVO> list = new ArrayList<AuctionPetInfoVO>();
		String sql = "select * from u_auctionpet_info where p_pk="+pPk+" limit "+i;
		logger.debug("���ָ��������petInfo: "+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionPetInfoVO vo = null;
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				vo = new AuctionPetInfoVO();
				vo.setAuctionPetInfoId(rs.getInt("auctionpet_info_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setAuctionPetInfo(rs.getString("auction_pet_info"));
				vo.setAddInfoTime(rs.getString("addInfoTime"));
				list.add(vo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	//�õ����������Ľ�Ǯ�б�
	public List<AuctionPetVO> getPetMoneyList(int pPk)
	{
		List<AuctionPetVO> list = new ArrayList<AuctionPetVO>();
		String sql = "select * from u_auction_pet where auction_status = 4 and p_pk="+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionPetVO vo = null;
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetPrice(rs.getInt("pet_price"));
				list.add(vo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}

	//�õ�δ�������ĳ����б�
	public List<AuctionPetVO> getPetGoodsList(int pPk)
	{
		List<AuctionPetVO> list = new ArrayList<AuctionPetVO>();
		String sql = "select * from u_auction_pet where auction_status = 2 and p_pk="+pPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		AuctionPetVO vo = null;
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetPrice(rs.getInt("pet_price"));
				list.add(vo);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return list;
	}
	
	/**
	 * ����petpk��ѯ�ó���������۸�
	 */
	public int getPetInfoPrice(String petPk) {
		
		int petPrice = 0;
		String sql = "select pet_price from u_auction_pet where pet_pk='" + petPk+ "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				petPrice = rs.getInt("pet_price");
			}
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
			return petPrice;
	}

	/**
	 * ����pepPkɾ��ָ�������������¼
	 * @param petPk
	 * @return
	 */
	public int deleteAuctionPet(String petPk)
	{
		int result = -1;
		String sql = "delete from u_auction_pet where pet_pk="+petPk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try {
			conn = dbConn.getConn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
			result = 1;
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/**
	 * ����petpk��ѯ�ó��������
	 */
	public String getPetInfoName(String petPk) {
		
		String petName = "";
		String sql = "select pet_name from u_auction_pet where pet_pk='" + petPk+ "'";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		try {
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				petName = rs.getString("pet_name");
			}
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
				dbConn.closeConn();
		}
			return petName;
	}

	/**
	 * �õ�����������ض����ֵĳ����list
	 * 
	 * @param 
	 * @param auctionType
	 *            ���������߷���
	 * @return
	 */
	public QueryPage getPagePetByName(String pet_name, int page_no,String sortType)
	{
		QueryPage queryPage = null;
		
		List<AuctionPetVO> pets = new ArrayList<AuctionPetVO>();
		AuctionPetVO vo = null;
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql,page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			 count_sql = "select count(*) from u_auction_pet where auction_status = 1 and pet_name like '%" + StringUtil.gbToISO(pet_name) + "%'";
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if( rs.next() )
			{
				count = rs.getInt(1);
			}
			rs.close();

			queryPage = new  QueryPage(page_no,count);
			if(sortType.equals("time")){
				page_sql = "select * from u_auction_pet where auction_status = 1 and pet_name like '%" + StringUtil.gbToISO(pet_name) + "%' order by pet_auction_time desc " +
						"limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			}else if(sortType.equals("money")){
				page_sql = "select * from u_auction_pet where auction_status = 1 and pet_name like '%" + StringUtil.gbToISO(pet_name) + "%' order by CAST(pet_price as UNSIGNED  INTEGER) asc " +
				"limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			}else {
				page_sql = "select * from u_auction_pet where auction_status = 1 and pet_name like '%" + StringUtil.gbToISO(pet_name) + "%' order by pet_auction_time desc " +
				"limit " + queryPage.getStartOfPage() + ","+queryPage.getPageSize();
			}
			logger.debug(page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPetPk(rs.getInt("pet_pk"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetPrice(rs.getInt("pet_price"));
				vo.setPetWx(rs.getInt("pet_wx"));
				
				pets.add(vo);
			}
			logger.debug("���������������Ϊ : "+pets.size());
			rs.close();
			stmt.close();
			
			queryPage.setResult(pets);

		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return queryPage;
	}

	public List<AuctionPetVO> getThanThreeDayList()
	{
		String sql = "select * from u_auction_pet where auction_status = 1 and now() > (pet_auction_time + INTERVAL 3 DAY)";
		 logger.debug("��ȡ����һ�������ĳ���������Ϣ��sql :"+sql);
		 List<AuctionPetVO> list = new ArrayList<AuctionPetVO>();
		 AuctionPetVO vo = null;
		 DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetName(rs.getString("pet_name"));
				list.add(vo);				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
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
		 String sql = "update u_auction_pet set auction_status = 2 where now() > (pet_auction_time + INTERVAL 3 DAY) and auction_status = 1";
		 logger.debug("����������Ϣ��sql :"+sql);
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try{
				conn = dbConn.getConn();
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					dbConn.closeConn();
				}
		
	}

	//���������δȡ�س������Ʒ�嵥
	public List<AuctionPetVO> getThanSixDayList()
	{
		String sql = "select * from u_auction_pet where auction_status = 2 and now() > (pet_auction_time + INTERVAL 6 DAY)";
		 logger.debug("��ȡ����һ�������ĳ���������Ϣ��sql :"+sql);
		 List<AuctionPetVO> list = new ArrayList<AuctionPetVO>();
		 AuctionPetVO vo = null;
		 DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetName(rs.getString("pet_name"));
				list.add(vo);				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}	
		return list;
	}

	/**
	 * ����ʱ�䳬������Ľ���û��
	 */
	public void deleteThanSixDay()
	{
		 String sql = "delete from u_auction_pet where auction_status = 2 and now() > (pet_auction_time + INTERVAL 6 DAY)";
		 logger.debug("����������Ϣ��sql :"+sql);
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try{
				conn = dbConn.getConn();
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					dbConn.closeConn();
				}
		
	}

	//�����Ʒ�����ɹ���������δȡ���������嵥
	public List<AuctionPetVO> getThanSevenDay()
	{
		String sql = "select * from u_auction_pet where auction_status = 4 and now() > (pet_auction_time + INTERVAL 7 DAY)";
		 logger.debug("��ȡ����һ��������������Ϣ��sql :"+sql);
		 List<AuctionPetVO> list = new ArrayList<AuctionPetVO>();
		 AuctionPetVO vo = null;
		 DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				vo = new AuctionPetVO();
				vo.setAuctionId(rs.getInt("auction_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setPetName(rs.getString("pet_name"));
				vo.setPetPrice(rs.getInt("pet_price"));
				list.add(vo);				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			dbConn.closeConn();
		}	
		return list;
	}

	/**
	 * �����ɹ��Ľ�Ǯ�����ڻ�û��ȡ�صĽ���û��
	 */
	public void updateMoneySevenDay()
	{
		String sql = "delete from u_auction_pet where auction_status = 4 and now() > (pet_auction_time + INTERVAL 7 DAY)";
		 logger.debug("����������Ϣ��sql :"+sql);
			DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
			conn = dbConn.getConn();
			try{
				conn = dbConn.getConn();
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
				ps.close();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					dbConn.closeConn();
				}
		
	}

}
