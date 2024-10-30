package com.ls.ben.dao.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.storage.WareHouseEquipVO;
import com.ls.ben.vo.storage.WareHouseVO;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Wrap;
import com.ls.pub.db.DBConnection;

/**
 * 
 * ����:�ֿ�����ݿ������
 * @author �ſ���
 * 10:27:18 AM
 */
public class WareHouseDao extends DaoBase {
	/**
	 * �õ���ҵĲֿ���Ϣ
	 * @param p_pk
	 * @return
	 */
	public WareHouseVO getStorageByPPk(int p_pk){

		WareHouseVO wareHouse = null;
		String sql = "select * from u_warehouse_info where  p_pk = " + p_pk + " and uw_type = "+Wrap.COPPER;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug(sql);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				wareHouse = new WareHouseVO();
				wareHouse.setUwId(rs.getInt("uw_id"));
				wareHouse.setPPk(rs.getInt("p_pk"));
				wareHouse.setUPk(rs.getInt("u_pk"));
				wareHouse.setUwPropType(rs.getInt("uw_prop_type"));
				wareHouse.setUwType(rs.getInt("uw_type"));
				
				wareHouse.setUwPropId(rs.getInt("uw_prop_id"));
				wareHouse.setUwMoney(rs.getString("uw_money"));
				wareHouse.setUwNumber(rs.getInt("uw_number"));
				wareHouse.setUwPropNumber(rs.getInt("uw_prop_number"));
				wareHouse.setUwArticle(rs.getString("uw_article"));
				
				wareHouse.setUwMoneyNumber(rs.getString("uw_money_number"));
				wareHouse.setUwPet(rs.getString("uw_pet"));
				wareHouse.setUwPetNumber(rs.getInt("uw_pet_number"));
				wareHouse.setUwWareHouseSpare(rs.getInt("uw_warehouse_spare"));
				wareHouse.setCreate_time(rs.getString("create_time"));
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			logger.debug(e.getStackTrace());
		}finally{ 
			dbConn.closeConn();
		}
		return wareHouse;
	}


	/** ����ɫ�Ƿ��ѽ���ĳ�����ϲֿ⣬���з��ظ������Ͳֿ�id��û�з���0  */
	public int getWareHouseIdBypPk(int uPk,int pPk,int type) {
		int i = 0;
		
		String sql = "select uw_id from u_warehouse_info where  p_pk = " + pPk + " and uw_type = " + type;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		logger.debug("wareHouseDao: " + sql);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next())
			{
				i = rs.getInt("uw_id");
			}
			rs.close();
			stmt.close();			
		} catch (Exception e) {
			e.printStackTrace();
			return i;
		} 
		finally{
			dbConn.closeConn();
		}
		return i;
	}


	/** ����pPk��type��ѯWareHouse  */
	public WareHouseVO getWareHouseIdBypPk(String pPk,int type) {
		WareHouseVO wareHouse = null;
		String sql = "select * from u_warehouse_info where  p_pk = '" + pPk + "' and uw_type = '" + type +"'";
		logger.debug("wareHouseDao ���getWareHouseIdBypPk��sql : " + sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next())
			{
				wareHouse = new WareHouseVO();
				wareHouse.setUwId(rs.getInt("uw_id"));
				wareHouse.setPPk(rs.getInt("p_pk"));
				wareHouse.setUPk(rs.getInt("u_pk"));
				wareHouse.setUwPropType(rs.getInt("uw_prop_type"));
				wareHouse.setUwType(rs.getInt("uw_type"));
				
				wareHouse.setUwPropId(rs.getInt("uw_prop_id"));
				wareHouse.setUwMoney(rs.getString("uw_money"));
				wareHouse.setUwNumber(rs.getInt("uw_number"));
				wareHouse.setUwPropNumber(rs.getInt("uw_prop_number"));
				wareHouse.setUwArticle(rs.getString("uw_article"));
				
				wareHouse.setUwMoneyNumber(rs.getString("uw_money_number"));
				wareHouse.setUwPet(rs.getString("uw_pet"));
				wareHouse.setUwPetNumber(rs.getInt("uw_pet_number"));
				wareHouse.setUwWareHouseSpare(rs.getInt("uw_warehouse_spare"));
				wareHouse.setCreate_time(rs.getString("create_time"));
				
			}
			rs.close();
			stmt.close();		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		finally{
			dbConn.closeConn();
		}
		return wareHouse;
	}
	
	
	/** 
	 * ���ݸ���p_pk��ѯ�ֿ�������
	 * @param p_pk ����id
	 * @return �ֿ�������
	 */
	public int getEmptyNum(int pPk){
		int wareSpare = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		String sql = "select uw_warehouse_spare from u_warehouse_info where p_pk = " + pPk + " and uw_type = " + Wrap.COPPER;
		logger.debug("���ݸ���p_pk��ѯ�ֿ���������sql=" + sql);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				wareSpare=rs.getInt("uw_warehouse_spare");
			}
			rs.close();
			stmt.close();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			dbConn.closeConn();
		}
		return wareSpare;
	}
	
	
	/** �ڲֿ���н�����ɫ��uw_type���͵����� */
	public int  insertWareHouse(int uPk, int pPk, int uw_type) {
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		
		
		int wareSpare = 80;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String sql = "insert into u_warehouse_info(uw_id,u_pk,p_pk,uw_type,uw_number,uw_article,uw_money,uw_pet,uw_money_number,uw_pet_number,uw_warehouse_spare,create_time) values(null,'"+ uPk +"','" + pPk +"','" + uw_type +"',"+wareSpare+",'',10000000000,0,0,5,'"+ wareSpare+"','"+sf.format(dt)+"')";
				
		logger.debug("warehouseDAO�е� ����ղֿ����ݱ��sql "+ sql);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			dbConn.closeConn();
		}
		return getWareHouseIdBypPk(uPk,pPk,uw_type);
	}
	
	
	
	/** ����id��ȡ�øý�ɫ�Ĳֿ������� */
	public int getWareSpareById(int pPk){
		int uw_warehouse_spare = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		String sql = "select * from u_warehouse_info where p_pk="+pPk+ " and uw_type="+Wrap.COPPER+" limit 1";
		logger.debug("warehouseDAO ��getWareSpareById ��sql : "+sql);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				uw_warehouse_spare = rs.getInt("uw_warehouse_spare");
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
				e.printStackTrace();
		}
		finally{
			dbConn.closeConn();
		}
		return uw_warehouse_spare;
	}
	
	/** ���ٸ��˲ֿ�Ŀ������ */
	public void reduceWareHouseSpare(int pPk,int remove_num){
		String sql = "update u_warehouse_info set uw_warehouse_spare = uw_warehouse_spare"+" - "+remove_num +" where p_pk="+pPk;
		logger.debug("pPk �Ĳֿ������ "+ remove_num+"����λ");
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		try
		{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();

		}catch (SQLException e) {
				e.printStackTrace();
		} finally{
			dbConn.closeConn();
		}
	}
	
	/**
	 * �õ���ɫ�ֿ���ĳ���͵ĵ��߷�ҳ�б�
	 * @param pPk
	 * @param type               ����
	 * @param page_no
	 * @return
	 */
	public QueryPage getPagePropsByPpk(int pPk,int type,int page_no){
		String condition_sql = "where p_pk=" + pPk+ " and uw_type=" + type;
		return this.loadPageList(condition_sql, page_no);
	}
	
	
	/** ���ݲֿ�id�ó��ֿ���Ϣ */
	public WareHouseVO getWareHouseVOByWareHouseId(String warehouseID,int p_pk){
		WareHouseVO wareHouse = null;
		String sql = "select * from u_warehouse_info where uw_id=" + warehouseID+" and p_pk="+p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				wareHouse = new WareHouseVO();
				wareHouse.setUwId(rs.getInt("uw_id"));
				wareHouse.setPPk(rs.getInt("p_pk"));
				wareHouse.setUPk(rs.getInt("u_pk"));
				wareHouse.setUwPropType(rs.getInt("uw_prop_type"));
				wareHouse.setUwType(rs.getInt("uw_type"));
				
				wareHouse.setUwPropId(rs.getInt("uw_prop_id"));
				wareHouse.setUwMoney(rs.getString("uw_money"));
				wareHouse.setUwNumber(rs.getInt("uw_number"));
				wareHouse.setUwPropNumber(rs.getInt("uw_prop_number"));
				wareHouse.setUwArticle(rs.getString("uw_article"));
				
				wareHouse.setUwMoneyNumber(rs.getString("uw_money_number"));
				wareHouse.setUwPet(rs.getString("uw_pet"));
				wareHouse.setUwPetNumber(rs.getInt("uw_pet_number"));
				wareHouse.setUwWareHouseSpare(rs.getInt("uw_warehouse_spare"));
				wareHouse.setCreate_time(rs.getString("create_time"));
				
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
		
		return wareHouse;
	}
	
	/** �Ӳֿ����õ�remove_num������ */
	public void reduceWareHouseProp(int pPk,int remove_num,WareHouseVO warehouseVO){
		
		int current_num = warehouseVO.getUwPropNumber() - remove_num;
		String sql = "update u_warehouse_info set uw_prop_number ='"+current_num+"' where uw_id=" + warehouseVO.getUwId();
		
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
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
	
	/** ɾ���ֿ����������Ϊ������ */
	public void deleteStorageZero(int pPk){
		String sql = "delete from u_warehouse_info where p_pk=" + pPk+" and uw_type != 8 and uw_prop_number = 0";

		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
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
	 *  ���ֿ����Ǯ 
	 *  @param pPk ��ɫid
	 *  @param input_num ������ֵ Ϊ��ʱ��Ǯ��Ϊ��ʱ��Ǯ��
	 *  @return ��ȷ���·���1�����򷵻�0
	 */
	public int addWareHouseMoney(int pPk,long input_num){
		int i = 0;
		String sql = "update u_warehouse_info set uw_money_number = uw_money_number + "+input_num +" where p_pk="+pPk;
		
		logger.debug("addWareHouseMoney : "+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			i = 1;
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return i;
	}
	
	/** ���������³�����ǽ����������д��е�pPk����Ϊ-pPk */
	public String putPetFromPerson(int pPk,int petPk,String diffent){
		String resultWml = "�˳����޷�����";
		String sql = "update p_pet_info set p_pk = "+pPk+" where pet_pk="+petPk;
		logger.debug("diffent"+diffent);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			if(diffent.equals("setIn")){
				resultWml = "�����Ѵ��浽�ֿ⣡";
			}else if(diffent.equals("getOut")){
				resultWml = "�����Ѵӳ��ﱣ������ȡ����";
			}
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return resultWml;
	}
	
	//���������ֿ�
	public String addPetToWare(int pPk,int petPk){
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String resuleWml = "-1";
		String petArticle = getPetArticle(pPk);
		
		String newPetArticle = petArticle + "-"+petPk;
		String sql = "update u_warehouse_info set uw_article ='"+newPetArticle +"' where uw_type ='7' and p_pk = "+pPk;
		logger.debug("�������"+sql);
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			resuleWml = "���Ѿ��ɹ������ﴢ�浽����ֿ� ��";
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return resuleWml;
	}
	
	//�������ó��ֿ�
	public String deletePetFromWare(int pPk,int petPk){
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String resuleWml = "-1";
		String petArticle = getPetArticle(pPk);
		logger.debug("�����article"+petArticle);
		String newPetArticle = "";
		String[] petArticles = new String[2];
		try{
			petArticles = petArticle.split(petPk+"",2);
		}catch (NullPointerException e1){
			e1.printStackTrace();
			petArticles[0] = petArticle;
		}
		
		
		newPetArticle = petArticles[0] + petArticles[1];
		logger.debug("�����neAarticle"+newPetArticle);
		String sql = "update u_warehouse_info set uw_article ='"+newPetArticle +"' where uw_type ='7' and p_pk = "+pPk;
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			resuleWml = "���Ѿ��ɹ��ӳ���ֿ�ȡ������ ��";
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return resuleWml;
	}
	
	
	/** ���ݽ�ɫpk��ô˽�ɫ�ĳ����ֶ� */
	public String getPetArticle(int pPk){
		String petArticle = "";
		String sql = "select uw_article from u_warehouse_info where p_pk = "+pPk+" and uw_type = 7";
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				petArticle = rs.getString("uw_article");
				logger.debug("�ֿ��еĳ����ֶ�"+petArticle);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return petArticle;
	}
	
	/** ����pPk�����³������������Ϊ��һ��������Ϊ��һ���� */
	public void updatePetNumber(int pPk,int number){
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "update u_warehouse_info set uw_pet = uw_pet +'"+ number+"' where p_pk="+pPk;
		logger.debug("warehouseDAO ��updatePetNumber : "+sql);
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
	
	/** ��ó�����е�����ĳ������� */
	public int getPetNumber(int pPk){
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String sql = "select count(*) from p_pet_info where p_pk="+pPk;
		//logger.debug("warehouseDAO getPetNumber : "+sql);
		int petNumber = 0;
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				petNumber = rs.getInt(1);
			}
			stmt.close();
			
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		logger.debug("petNumber : "+petNumber);
		return petNumber;
	}
	
	/**
	 * �õ�ĳһ�ֵ��ߵ�����
	 */
	public int getPropNumByByPropID(int p_pk, int prop_id)
	{
		int prop_num = 0;
		String sql = "select sum(uw_prop_number) as prop_sum from u_warehouse_info where p_pk="
				+ p_pk + " and uw_prop_id=" + prop_id + "";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_num = rs.getInt("prop_sum");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{

			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		logger.debug("�ֿ���ʣ�������"+prop_num);
		return prop_num;
	}
	
	/**
	 * ����һ�����
	 */
	public int addPropGroup(WareHouseVO warevo)
	{
		if (warevo == null)
		{
			logger.debug("warevo");
			return -1;
		}
		int result = -1;
		String sql = "insert into u_warehouse_info (u_pk,p_pk,uw_prop_id,uw_prop_number,uw_type,uw_prop_type,uw_article,create_time,prop_bonding,prop_protect,prop_isReconfirm,prop_use_control,prop_operate1) values (?,?,?,?,?,?,?,now(),?,?,?,?,?)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			int i = 1;
			ps.setInt(i++, warevo.getUPk());
			ps.setInt(i++, warevo.getPPk());

			ps.setInt(i++, warevo.getUwPropId());
			ps.setInt(i++, warevo.getUwPropNumber());
			ps.setInt(i++, warevo.getUwType());

			ps.setInt(i++, warevo.getUwPropType());

			ps.setString(i++, warevo.getUwArticle());
			ps.setInt(i++, warevo.getUw_bonding());
			ps.setInt(i++, warevo.getUw_protect());
			ps.setInt(i++, warevo.getUwIsReconfirm());
			ps.setInt(i++, warevo.getPropUseControl());
			ps.setString(i++, warevo.getPropOperate1());
			ps.execute();
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
	 * ����prop_id�õ��������ٵ�һ��
	 */
	public WareHouseVO getWareHouseByPropID(int p_pk, int prop_id)
	{
		WareHouseVO wareHouse = null;
		String sql = "select * from u_warehouse_info where p_pk=" + p_pk
				+ " and uw_prop_id=" + prop_id + " order by uw_prop_number limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				wareHouse = new WareHouseVO();
				wareHouse.setUwId(rs.getInt("uw_id"));
				wareHouse.setPPk(rs.getInt("p_pk"));
				wareHouse.setUPk(rs.getInt("u_pk"));
				wareHouse.setUwPropType(rs.getInt("uw_prop_type"));
				wareHouse.setUwType(rs.getInt("uw_type"));
				
				wareHouse.setUwPropId(rs.getInt("uw_prop_id"));
				wareHouse.setUwMoney(rs.getString("uw_money"));
				wareHouse.setUwNumber(rs.getInt("uw_number"));
				wareHouse.setUwPropNumber(rs.getInt("uw_prop_number"));
				wareHouse.setUwArticle(rs.getString("uw_article"));
				
				wareHouse.setUwMoneyNumber(rs.getString("uw_money_number"));
				wareHouse.setUwPet(rs.getString("uw_pet"));
				wareHouse.setUwPetNumber(rs.getInt("uw_pet_number"));
				wareHouse.setUwWareHouseSpare(rs.getInt("uw_warehouse_spare"));
				wareHouse.setCreate_time(rs.getString("create_time"));
				
				wareHouse.setUw_bonding(rs.getInt("prop_bonding"));
				wareHouse.setUw_protect(rs.getInt("prop_protect"));
				wareHouse.setUwIsReconfirm(rs.getInt("prop_isReconfirm"));
				wareHouse.setPropUseControl(rs.getInt("prop_use_control"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{

			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return wareHouse;
	}
	
	/**
	 * ���µ���������
	 * 
	 * @param pg_pk
	 * @param prop_num
	 *            �µĵ�������
	 * @return
	 */
	public int updatePropGroupNum(int uwId, int prop_num)
	{
		int result = -1;
		String sql = "update u_warehouse_info set uw_prop_number=" + prop_num
				+ " where uw_id=" + uwId;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{
			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return result;
	}


	/**
	 * ��װ���Ӳֿ�װ������
	 * @param p_pk
	 * @param vo
	 */
	public int putAccToWrap(int p_pk, WareHouseEquipVO vo)
	{
		int i = -1;
		String sql = "insert into u_part_equip values (null,'"+vo.getPPk()+"','"+vo.getTableType()+"','"
						+vo.getGoodsType()+"','"+vo.getWId()+"','"
						+vo.getWName()+"','"+vo.getWDurability()+"','"+vo.getWDuraConsume()+"','"+vo.getWBonding()+"','"+vo.getWProtect()+"','"
						+vo.getWIsreconfirm()+"','"+vo.getWPrice()+"','"+vo.getWFyXiaoYuan()+"','"+vo.getWFyDaYuan()+"','"+vo.getWGjXiaoYuan()+"','"
						
						
						+vo.getWGjDaYuan()+"','"+vo.getWHp()+"','"+vo.getWMp()+"','" 
						
						
						+vo.getWJinFy()+"','"+vo.getWMuFy()+"','"+vo.getWShuiFy()+"','"+vo.getWHuoFy()+"','"+vo.getWTuFy()+"','"
						
						
						+vo.getWJinGj()+"','"+vo.getWMuGj()+"','"+vo.getWShuiGj()+"','"+vo.getWHuoGj()+"','"+vo.getWTuGj()
						+"','"+vo.getWType()+"','"+vo.getWQuality()+"','"+vo.getSuitId()+"','"+vo.getWWxType()+"','"+vo.getWBuffIsEffected()
						
						
						+"','"+vo.getEnchantType()+"',"+vo.getEnchantValue()+","+vo.getWZjHp()+","+vo.getWZjMp()+","+vo.getWZjWxGj()
						+","+vo.getWZjWxFy()+","+vo.getWZbGrade()+",now(),"+vo.getPPoss()+","+vo.getWBondingNum()+","+vo.getSpecialcontent()+")";
		
		logger.debug("������˰������sql="+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			i = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return i;
	}
	


	/**
	 * ����prop_id�õ��������ٵ�һ��
	 */
	public int getPropGroupNumByPropID(int p_pk, int prop_id)
	{
		String sql = "select count(1) as ware_group from u_warehouse_info where p_pk=" + p_pk
				+ " and uw_prop_id=" + prop_id + "";
		logger.debug(sql);
		int prop_group = 0;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				prop_group = rs.getInt("ware_group");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{

			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return prop_group;

		
	}
	
	/**�õ�������ڵİ�������*/
	public int getPlayerWarehouseNum(int p_pk){
		int num = 0;
		String sql = "select uw_number from u_warehouse_info where uw_type = "+Wrap.COPPER+" and p_pk="+p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
			{
				num = rs.getInt("uw_number");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e)
		{

			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
		return num;
	}
	/**������Ұ�������*/
	public void updatePlayerWarehouseMaxNum(int p_pk,int num){
		String sql = "update u_warehouse_info set uw_number = uw_number + "+num+" where p_pk ="+p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e)
		{

			logger.debug(e.toString());
		} finally
		{
			dbConn.closeConn();
		}
	}
	/**�������ʣ���������*/
	public void updatePlayerWarehouseNum(int p_pk,int num){
		String sql = "update u_warehouse_info set uw_warehouse_spare = uw_warehouse_spare + "+num+" where p_pk ="+p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	 * ɾ������,����ɾ��
	 * @param pPk
	 * @param propId
	 * @param group_num
	 */
	public void deleteStroagePropGroup(int pPk,int propId, int group_num)
	{
		String sql = "delete from u_warehouse_info where p_pk = "+pPk+" and uw_prop_id="+propId +" limit "+group_num;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	 * ������ҵ��߸��ӵ�����
	 * @param p_pk
	 * @param propId
	 * @param propAccumulate
	 */
	public void updatePlayerWarehousePropNum(int p_pk,int propId,int propAccumulate){
		String sql = "update u_warehouse_info set uw_prop_number =  "+propAccumulate+" where p_pk ="+p_pk+" and uw_prop_id="+propId;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
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
	 * ���������ַ����͵�ǰҳ�ŵõ���ҳ����
	 */
	private QueryPage loadPageList( String condition_sql,int page_no)
	{
		QueryPage queryPage = null;
		
		List<WareHouseVO> list = new ArrayList<WareHouseVO>();
		
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		String count_sql, page_sql;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			count_sql = "select count(*) from u_warehouse_info "+condition_sql ;
			logger.debug(count_sql);
			rs = stmt.executeQuery(count_sql);
			if (rs.next())
			{
				count = rs.getInt(1);
			}
			rs.close();
			
			queryPage = new QueryPage(page_no, count);
			
			page_sql = "select * from u_warehouse_info "+condition_sql
			+ "  limit " + queryPage.getStartOfPage()
			+ "," + queryPage.getPageSize();
			logger.debug(page_sql);
			
			rs = stmt.executeQuery(page_sql);
			while (rs.next())
			{
				list.add(this.loadData(rs));
			}
			rs.close();
			stmt.close();
			
			queryPage.setResult(list);
			
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
	 * װ��һ����¼
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private WareHouseVO loadData( ResultSet rs ) throws SQLException
	{
		WareHouseVO wareHouse = new WareHouseVO();
		wareHouse.setUwId(rs.getInt("uw_id"));
		wareHouse.setPPk(rs.getInt("p_pk"));
		wareHouse.setUPk(rs.getInt("u_pk"));
		wareHouse.setUwPropType(rs.getInt("uw_prop_type"));
		wareHouse.setUwType(rs.getInt("uw_type"));
		
		wareHouse.setUwPropId(rs.getInt("uw_prop_id"));
		wareHouse.setUwMoney(rs.getString("uw_money"));
		wareHouse.setUwNumber(rs.getInt("uw_number"));
		wareHouse.setUwPropNumber(rs.getInt("uw_prop_number"));
		wareHouse.setUwArticle(rs.getString("uw_article"));
		
		wareHouse.setUwMoneyNumber(rs.getString("uw_money_number"));
		wareHouse.setUwPet(rs.getString("uw_pet"));
		wareHouse.setUwPetNumber(rs.getInt("uw_pet_number"));
		wareHouse.setUwWareHouseSpare(rs.getInt("uw_warehouse_spare"));
		wareHouse.setCreate_time(rs.getString("create_time"));
		return wareHouse;
	}
}
