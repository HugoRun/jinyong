package com.ls.ben.dao.info.buff;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.pub.db.DBConnection;
import com.ls.web.service.log.DataErrorLog;

/**
 * ����:buff��
 * @author ��˧
 * 12:21:18 PM
 */
public class BuffDao extends DaoBase {
	
	private static Map<Integer,BuffVO> buff_cache = new HashMap<Integer,BuffVO>();
	
	/**
	 * ͨ��id�õ�buff��Ϣ
	 * @param buff_id
	 * @return
	 */
	public BuffVO getBuff( int buff_id)
	{
		BuffVO buff = buff_cache.get(buff_id);
		if( buff==null )
		{
			buff = this.getBuffByDB(buff_id);
			if( buff==null )
			{
				DataErrorLog.debugData("BuffDao.getBuff:��ЧbuffId="+buff_id);
			}
			else
			{
				buff_cache.put(buff_id, buff);
			}
		}
		return buff;
	}
	
	/**
	 * �õ�buff����
	 */
	public int getBuffType( String buff_id) 
	{
		BuffVO buff = this.getBuff(Integer.parseInt(buff_id));
		return buff.getBuffType();
	}
	
	/**
	 * �õ�buff����
	 */
	public String getBuffDisplay( int buff_id) 
	{
		BuffVO buff = this.getBuff(buff_id);
		return buff.getBuffDisplay();
	}
	
	/**
	 * �õ�buff��Ϣ
	 */
	private BuffVO getBuffByDB( int buff_id) 
	{
		BuffVO buff = null;
		String sql = "select * from buff where buff_id = "+buff_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() )
			{
				buff = new BuffEffectVO();
				buff.setBuffId(buff_id);
				
				buff.setBuffType(rs.getInt("buff_type"));
				buff.setBuffName(rs.getString("buff_name"));
				buff.setBuffDisplay(rs.getString("buff_display"));
				buff.setBuffEffectValue(rs.getInt("buff_effect_value"));
				
				buff.setBuffUseMode(rs.getInt("buff_use_mode"));
				buff.setBuffBoutOverlap(rs.getInt("buff_bout_overlap"));
				buff.setBuffTimeOverlap(rs.getInt("buff_time_overlap"));
				
				buff.setBuffBout(rs.getInt("buff_bout"));
				buff.setBuffTime(rs.getInt("buff_time"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		}
		finally
		{
			dbConn.closeConn();
		}
		return buff;
	}
}
