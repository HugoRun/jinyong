package com.pm.dao.setting;

import java.sql.SQLException;

import com.ls.ben.dao.DaoBase;
import com.ls.pub.db.DBConnection;
import com.pm.vo.setting.SettingVO;

public class SettingDao extends DaoBase 
{

	/**
	 * ����pPk�ڴ�����ɫͬʱ������ɫ��ϵͳ���ñ�
	 * @param pPk
	 */
	public void createSysSetting (int pPk)
	{
		String sql = "insert into s_setting_info (p_pk) values ("+pPk+")";
		logger.debug("������ɫʱ����ϵͳ����:"+sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
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

	/**
	 * �õ�ϵͳ���ñ��еĲ����б�
	 * @param p_pk
	 * @return
	 */
	public SettingVO getSettingInfo(String p_pk)
	{
		
		String sql = "select * from s_setting_info where p_pk="+p_pk;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		logger.debug("�õ����ò���="+sql);
		SettingVO vo = null;
		try{
			conn = dbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				vo = new SettingVO();
				vo.setSettingId(rs.getInt("setting_id"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setGoodsPic(rs.getInt("goods_pic"));
				vo.setPersonPic(rs.getInt("person_pic"));
				vo.setNpcPic(rs.getInt("npc_pic"));
				vo.setPetPic(rs.getInt("pet_pic"));
				vo.setOperatePic(rs.getInt("operate_pic"));
				vo.setDealControl(rs.getInt("deal_control"));
				vo.setPublicComm(rs.getInt("public_comm"));
				vo.setCampComm(rs.getInt("camp_comm"));
				vo.setDuiwuComm(rs.getInt("duiwu_comm"));
				vo.setTongComm(rs.getInt("tong_comm"));
				vo.setSecretComm(rs.getInt("secret_comm"));
				vo.setIndexComm(rs.getInt("index_comm"));
				vo.setNpcHpUp(rs.getInt("npc_hp_position"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		return vo;
	}
	
}
