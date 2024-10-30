package com.ls.ben.dao.info.buff;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.pub.db.DBConnection;
import com.ls.pub.util.StringUtil;

/**
 * 功能:u_buffeffect_info表
 * 
 * @author 刘帅 12:31:24 PM
 */
public class BuffEffectDao extends DaoBase {
	/**
	 * 添加buff效果
	 * 
	 * @param buffEffect
	 */
	public void add(BuffEffectVO buffEffect) {
		if (buffEffect == null) {
			logger.debug("参数为空");
		}
		buffEffect.log();
		String sql = "INSERT INTO u_buffeffect_info  values (null,?,?,?,?,?,?,?,?,now(),?,?,?,?,?)";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			ps = conn.prepareStatement(sql);

			int i = 1;
			ps.setInt(i++, buffEffect.getBuffId());
			ps.setString(i++, buffEffect.getBuffName());
			ps.setString(i++, buffEffect.getBuffDisplay());
			ps.setInt(i++, buffEffect.getBuffType());
			ps.setInt(i++, buffEffect.getBuffEffectValue());

			ps.setInt(i++, buffEffect.getSpareBout());
			ps.setInt(i++, buffEffect.getBuffBout());
			ps.setInt(i++, buffEffect.getBuffTime());

			ps.setInt(i++, buffEffect.getBuffUseMode());
			ps.setInt(i++, buffEffect.getBuffBoutOverlap());
			ps.setInt(i++, buffEffect.getBuffTimeOverlap());

			ps.setInt(i++, buffEffect.getEffectObject());
			ps.setInt(i++, buffEffect.getEffectObjectType());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
	}

	/**
	 * 删除指定buff效果
	 * 
	 * @param bf_pk
	 */
	public void deleteByID(int bf_pk) {
		String sql = "delete from u_buffeffect_info where bf_pk=" + bf_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
	}
	
	/**
	 * 得到指定作用对象的buff效果集合
	 * @param effect_object
	 * @param effect_object_type
	 * @return
	 */
	public List<BuffEffectVO> getBuffEffects( int effect_object,int effect_object_type )
	{
		List<BuffEffectVO> buffEffects = new ArrayList<BuffEffectVO>();
		BuffEffectVO buffEffect = null;
		String sql = "SELECT * FROM u_buffeffect_info where effect_object=" + effect_object+" and effect_object_type="+effect_object_type+" group by buff_type order by  bf_pk,buff_type desc";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				buffEffect = new  BuffEffectVO();
				
				buffEffect.setBfPk(rs.getInt("bf_pk"));
				buffEffect.setBuffId(rs.getInt("buff_id"));
				buffEffect.setBuffType(rs.getInt("buff_type"));
				buffEffect.setBuffName(rs.getString("buff_name"));
				buffEffect.setBuffDisplay(rs.getString("buff_display"));
				buffEffect.setBuffEffectValue(rs.getInt("buff_effect_value"));
				
				buffEffect.setSpareBout(rs.getInt("spare_bout"));
				buffEffect.setBuffBout(rs.getInt("buff_bout"));
				buffEffect.setBuffTime(rs.getInt("buff_time"));
				buffEffect.setUseTime(rs.getTimestamp("use_time"));
				
				buffEffect.setBuffUseMode(rs.getInt("buff_use_mode"));
				buffEffect.setBuffBoutOverlap(rs.getInt("buff_bout_overlap"));
				buffEffect.setBuffTimeOverlap(rs.getInt("buff_time_overlap"));
				
				buffEffect.setEffectObject(effect_object);
				buffEffect.setEffectObjectType(effect_object_type);
				
				buffEffects.add(buffEffect);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		
		return buffEffects;
	}
	
	
	/**
	 * 得到指定作用对象的buff效果集合
	 * @param effect_object
	 * @param effect_object_type
	 * @return
	 */
	public String getBuffListDescribe( int effect_object,int effect_object_type )
	{
		StringBuffer buff_list_describe = new StringBuffer();
		String sql = "SELECT * FROM u_buffeffect_info where effect_object=" + effect_object+" and effect_object_type="+effect_object_type+" group by buff_type order by  bf_pk,buff_type desc ";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				String buff_name = StringUtil.isoToGBK(rs.getString("buff_name"));
				
				if( buff_list_describe.toString().equals(""))
				{
					buff_list_describe.append(buff_name);
				}
				else
				{
					buff_list_describe.append(","+buff_name);
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		
		if( buff_list_describe.toString().equals(""))
		{
			buff_list_describe.append("无");
		}
		
		return buff_list_describe.toString();
	}
	
	
	/**
	 * 得到指定作用对象的buff效果集合
	 * @param effect_object
	 * @param effect_object_type
	 * @return
	 */
	public BuffEffectVO getBuffEffectByBuffType( int effect_object,int effect_object_type,int buff_type )
	{
		BuffEffectVO buffEffect = null;
		String sql = "SELECT * FROM u_buffeffect_info where effect_object=" + effect_object+" and effect_object_type="+effect_object_type+" and buff_type="+buff_type + " order by bf_pk limit 1";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				buffEffect = new  BuffEffectVO();
				
				buffEffect.setBfPk(rs.getInt("bf_pk"));
				buffEffect.setBuffId(rs.getInt("buff_id"));
				buffEffect.setBuffType(rs.getInt("buff_type"));
				buffEffect.setBuffName(rs.getString("buff_name"));
				buffEffect.setBuffDisplay(rs.getString("buff_display"));
				buffEffect.setBuffEffectValue(rs.getInt("buff_effect_value"));
				
				buffEffect.setSpareBout(rs.getInt("spare_bout"));
				buffEffect.setBuffBout(rs.getInt("buff_bout"));
				buffEffect.setBuffTime(rs.getInt("buff_time"));
				buffEffect.setUseTime(rs.getTimestamp("use_time"));
				
				buffEffect.setBuffUseMode(rs.getInt("buff_use_mode"));
				buffEffect.setBuffBoutOverlap(rs.getInt("buff_bout_overlap"));
				buffEffect.setBuffTimeOverlap(rs.getInt("buff_time_overlap"));
				
				buffEffect.setEffectObject(effect_object);
				buffEffect.setEffectObjectType(effect_object_type);
				
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		
		return buffEffect;
	}
	
	
	/**
	 * 更新所有回合制剩余的回合数
	 */
	public void updateSpareBout( int p_pk )
	{
		String sql = "update  u_buffeffect_info  set spare_bout=spare_bout-1 where buff_bout<>0 and effect_object="+p_pk;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
	}
	
	/**
	 * 清楚某一实体的所有buff
	 */
	public void clearBuffEffect( int object_id,int object_type )
	{
		String sql = "delete from u_buffeffect_info where effect_object=" + object_id+ " and effect_object_type="+object_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
	}
	
	
	/**
	 * 清楚某一实体的某一特定buff_id的buff
	 */
	public void clearBuffEffectByBuffId( int object_id,int object_type,int buff_id )
	{
		String sql = "delete from u_buffeffect_info where effect_object=" + object_id+ " and effect_object_type="+object_type+" and buff_id="+buff_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
	}

	/**
	 * 找出某对象是否已经某类型的buff,如果没有返回-1,否则返回总共有几条同类型buff
	 * @param object_id
	 * @param object_type
	 * @param buff_type
	 * @return
	 */
	public int hasAlreadyBuff(int object_id, int object_type, int buff_type)
	{
		int i = 0;
		String sql = "SELECT count(1) as all_num from u_buffeffect_info where effect_object="+object_id+" and effect_object_type="+object_type
						+" and buff_type="+buff_type;
		
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				i = rs.getInt("all_num");
			}
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		if(i == 0) {
			i = -1;
		}
		return i;
	}

	/**
	 * 找出某对象是否已经某类型的buff
	 * @param object_id
	 * @param object_type
	 * @param buff_type
	 * @return
	 */
	public BuffEffectVO hasAlreadyBuff(int object_id, int object_type, String buff_type)
	{
		
		String sql = "SELECT * FROM u_buffeffect_info where effect_object="+object_id+" and effect_object_type="+object_type
						+" and buff_type="+buff_type;
		
		logger.debug(sql);
		BuffEffectVO buffEffect = null;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next())
			{
				buffEffect = new  BuffEffectVO();
				
				buffEffect.setBfPk(rs.getInt("bf_pk"));
				buffEffect.setBuffId(rs.getInt("buff_id"));
				buffEffect.setBuffType(rs.getInt("buff_type"));
				buffEffect.setBuffName(rs.getString("buff_name"));
				buffEffect.setBuffDisplay(rs.getString("buff_display"));
				buffEffect.setBuffEffectValue(rs.getInt("buff_effect_value"));
				
				buffEffect.setSpareBout(rs.getInt("spare_bout"));
				buffEffect.setBuffBout(rs.getInt("buff_bout"));
				buffEffect.setBuffTime(rs.getInt("buff_time"));
				buffEffect.setUseTime(rs.getTimestamp("use_time"));
				
				buffEffect.setBuffUseMode(rs.getInt("buff_use_mode"));
				buffEffect.setBuffBoutOverlap(rs.getInt("buff_bout_overlap"));
				buffEffect.setBuffTimeOverlap(rs.getInt("buff_time_overlap"));
				
				buffEffect.setEffectObject(object_id);
				buffEffect.setEffectObjectType(object_type);
			}
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		
		return buffEffect;
	}
	
	/**
	 * 删除某对象身上已经有的同类型的buff
	 * @param object_id
	 * @param object_type
	 * @param buff_type
	 */
	public void deleteAlreadyBuff(int object_id, int object_type, int buff_type)
	{
		String sql = "delete from u_buffeffect_info where effect_object="+object_id+" and effect_object_type="+object_type
						+" and buff_type="+buff_type;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
	}

	/**
	 * 如果buff的时间可以叠加, 那么将时间叠加在一起.
	 * @param valueOf
	 * @param player
	 * @param valueOf2
	 */
	public void updateBuffEffect(int pPk, int buff_id, int buff_time)
	{
		String sql = "update u_buffeffect_info set buff_time = buff_time +"+ buff_time+" where effect_object = "+pPk+" and buff_id = "+buff_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		
	}


	/**
	 * 如果buff的回合数可以叠加, 那么将回合数叠加在一起.
	 * @param valueOf
	 * @param player
	 * @param valueOf2
	 */
	public void updateBuffBoutEffect(int pPk, int buff_id, int buff_bout)
	{
		String sql = "update u_buffeffect_info set buff_bout = buff_bout +"+ buff_bout+" where pPk = "+pPk+" and buff_id = "+buff_id;
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			logger.debug(e.toString());
		} finally {
			dbConn.closeConn();
		}
		
	}	 
}
