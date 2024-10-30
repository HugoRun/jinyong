package com.ls.ben.dao.goods.equip;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.ls.ben.dao.BasicDaoSupport;
import com.ls.model.equip.GameEquip;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * @author ls
 * ��Ϸװ��
 */
public class GameEquipDao extends BasicDaoSupport<GameEquip>
{
	
	public GameEquipDao()
	{
		super("game_equip", DBConnection.GAME_DB);
	}

	/**
	 * ͨ��id�õ�װ����Ϣ
	 */
	public GameEquip getById(int equipId)
	{
		return super.getOneBySql("where id="+equipId);
	}
	
	/**
	 * ����װ�����ֵõ�װ��id
	 * @return
	 */
	public int getIdByName(String name ){
		int id = -1;
		String sql = "select id from game_equip where name='"+name+"'";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				id = rs.getInt("id");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return id;
	}
	
	/**
	 * ����װ������
	 * @return
	 */
	public HashMap<Integer,GameEquip> findAll(){
		HashMap<Integer,GameEquip> map = new HashMap<Integer,GameEquip>();
		String sql = "select * from game_equip";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			while (rs.next()) {
				GameEquip gameEqup = this.loadData(rs);
				map.put(gameEqup.getId(), gameEqup);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return map;
	}
	
	
	protected GameEquip loadData(ResultSet rs) throws SQLException
	{
		if( rs!=null)
		{
			GameEquip gameEquip = new GameEquip();
			
			gameEquip.setId(rs.getInt("id"));
			gameEquip.setName(StringUtil.isoToGBK(rs.getString("name")));
			gameEquip.setDes(StringUtil.isoToGBK(rs.getString("des")));
			gameEquip.setType(rs.getInt("type"));
			gameEquip.setPic(rs.getString("pic"));
			gameEquip.setPrice(rs.getInt("price"));
			gameEquip.setEndure(rs.getInt("endure"));
			
			gameEquip.setGrade(rs.getInt("grade"));
			gameEquip.setSex(rs.getInt("sex"));
			gameEquip.setJob(rs.getInt("job"));
			gameEquip.setIsMarried(rs.getInt("isMarried"));
			gameEquip.setIsProtected(rs.getInt("isProtected"));
			gameEquip.setIsReconfirm(rs.getInt("isReconfirm"));
			gameEquip.setIsBind(rs.getInt("isBind"));
			gameEquip.setMinAtt(rs.getInt("minAtt"));
			gameEquip.setMaxAtt(rs.getInt("maxAtt"));
			gameEquip.setMinDef(rs.getInt("minDef"));
			gameEquip.setMaxDef(rs.getInt("maxDef"));
			
			gameEquip.setOtherAttriStr(rs.getString("otherAttriStr"));
			gameEquip.setAppendAttriStr(rs.getString("appendAttriStr"));
			
			gameEquip.setSuitId(rs.getInt("suitId"));
			gameEquip.setQualityRate(rs.getInt("qualityRate"));
			return gameEquip;
		}
		
		return null;
	}
	/**
	 * ����������������ģ����ѯ��׼ȷ������
	 */
	public String getEquipName(String name)
	{
		String equipName="";
		String sql = "select name from game_equip where name like '%"+name+"%' limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				equipName= rs.getString("name");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return equipName;
	}
}
