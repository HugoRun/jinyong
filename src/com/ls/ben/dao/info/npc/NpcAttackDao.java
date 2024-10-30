package com.ls.ben.dao.info.npc;

import java.util.HashMap;
import java.util.List;

import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.dao.DaoBase;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.pub.db.DBConnection;

public class NpcAttackDao extends DaoBase {
	
	/**
	 * 根据npc表建立临时表npc
	 * @param npc
	 * @param p_pk
	 * @param insidePk 内存中的主键
	 * @return
	 */
	public NpcFighter createByNpc(NpcVO npc,int p_pk,int isAttacked,int npc_type,int mapId,int insidePk)
	{
		NpcFighter temp_npc = null;
		if( npc==null  )
		{
			logger.debug("参数错误:npc为空");
			return null;
		}
		temp_npc = new NpcFighter();
		//设置主动攻击开关
		temp_npc.setID(insidePk);
		temp_npc.setPPk(p_pk);
		temp_npc.setNpcID(npc.getNpcID());
		temp_npc.setNpcName(npc.getNpcName());
		temp_npc.setCurrentHP(npc.getNpcHP());
		temp_npc.setNpcHP(npc.getNpcHP());
		temp_npc.setDefenceDa(npc.getDefenceDa());
		temp_npc.setDefenceXiao(npc.getDefenceXiao());
		temp_npc.setNpcIsAttack(isAttacked);//设置攻击状态
		
		temp_npc.setJinFy(npc.getJinFy());
		temp_npc.setMuFy(npc.getMuFy());
		temp_npc.setShuiFy(npc.getShuiFy());
		temp_npc.setHuoFy(npc.getHuoFy());
		temp_npc.setTuFy(npc.getTuFy());
		
		
		temp_npc.setDrop(npc.getDrop());
		temp_npc.setLevel(npc.getLevel());
		temp_npc.setExp(npc.getExp());
		temp_npc.setMoney(npc.getMoney());
		temp_npc.setTake(npc.getTake());
		temp_npc.setSceneId(mapId);
		temp_npc.setNpcKey(temp_npc.getPPk()+ "" + System.currentTimeMillis());
		temp_npc.setNpcType(npc_type);
		
		AttacckCache attacckCache = new AttacckCache();
		if ( npc_type == NpcAttackVO.CITYDOOR || npc_type == NpcAttackVO.DIAOXIANG) {
			HashMap hashMap = attacckCache.getNpcWithSceneByPPkd();
			hashMap.put(mapId+"", temp_npc);
			
		} else {
    		List<NpcFighter> list = null;
    		list = attacckCache.getZDAttackNpc(p_pk, AttacckCache.ZDATTACKNPC);
    		list.add(temp_npc);
		}
	
	/**
	 * 根据npc表建立临时表npc
	 * @param npc
	 * @param p_pk
	 * @return
	 */
		/*
	public void createByNpc(List npcs )
	{
		if( npcs==null )
		{
			logger.debug("持久化npcs为空");
			return ;
		}
		NpcAttackVO temp_npc = null;
		String sql = "insert into n_attack_info (p_pk,n_current_HP,n_attackswitch,npc_ID,npc_Name,npc_HP," +
		"npc_defence_da,npc_defence_xiao,npc_jin_fy," +
		"npc_mu_fy,npc_shui_fy,npc_huo_fy,npc_tu_fy,npc_drop,npc_Level,npc_EXP,npc_money,npc_take,npc_refurbish_time,scene_id,npc_isAttack,create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			ps = conn.prepareStatement(sql);
			for(int j=0;j<npcs.size();j++)
			{
				temp_npc = (NpcAttackVO)npcs.get(j);
				int i=1;
				ps.setInt(i++, temp_npc.getPPk());
				ps.setInt(i++,temp_npc.getCurrentHP());
				ps.setInt(i++,temp_npc.getNAttackswitch());
				ps.setInt(i++,temp_npc.getNpcID());
				ps.setString(i++,temp_npc.getNpcName());
				ps.setInt(i++,temp_npc.getNpcHP());
				ps.setInt(i++,temp_npc.getDefenceDa());
				ps.setInt(i++,temp_npc.getDefenceXiao());
				
				ps.setInt(i++,temp_npc.getJinFy());
				ps.setInt(i++,temp_npc.getMuFy());
				ps.setInt(i++,temp_npc.getShuiFy());
				ps.setInt(i++,temp_npc.getHuoFy());
				ps.setInt(i++,temp_npc.getTuFy());
				
				ps.setInt(i++,temp_npc.getDrop());
				ps.setInt(i++,temp_npc.getLevel());
				ps.setInt(i++,temp_npc.getExp());
				ps.setString(i++, temp_npc.getMoney());
				ps.setInt(i++,temp_npc.getTake());
				
				ps.setInt(i++, temp_npc.getNpcRefurbishTime());
				ps.setInt(i++, temp_npc.getSceneId());
				
				ps.setInt(i++, temp_npc.getNAttackswitch());
				ps.execute();
			}
//			ps.executeBatch();
			ps.close();	
			logger.debug("刷出"+npcs.size()+"个NPC");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();
		}
		
	}*/
	
	
	
	
		
		
//		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
//		conn = dbConn.getConn();
//		try
//		{
//		
//			String sql = "insert into n_attack_info (p_pk,n_current_HP,n_attackswitch,npc_ID,npc_Name,npc_HP," +
//					"npc_defence_da,npc_defence_xiao,npc_jin_fy," +
//					"npc_mu_fy,npc_shui_fy,npc_huo_fy,npc_tu_fy,npc_drop,npc_Level,npc_EXP,npc_money,npc_take,scene_id,npc_key,npc_isAttack,npc_type,create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
//			logger.debug(sql);
//			PreparedStatement ps = conn.prepareStatement(sql);
//			int i=1;
//			ps.setInt(i++, temp_npc.getPPk());
//			ps.setInt(i++,temp_npc.getCurrentHP());
//			ps.setInt(i++,temp_npc.getNAttackswitch());
//			ps.setInt(i++,temp_npc.getNpcID());
//			ps.setString(i++,temp_npc.getNpcName());
//			ps.setInt(i++,temp_npc.getNpcHP());
//			ps.setInt(i++,temp_npc.getDefenceDa());
//			ps.setInt(i++,temp_npc.getDefenceXiao());
//			
//			ps.setInt(i++,temp_npc.getJinFy());
//			ps.setInt(i++,temp_npc.getMuFy());
//			ps.setInt(i++,temp_npc.getShuiFy());
//			ps.setInt(i++,temp_npc.getHuoFy());
//			ps.setInt(i++,temp_npc.getTuFy());
//		
//			ps.setInt(i++,temp_npc.getDrop());
//			ps.setInt(i++,temp_npc.getLevel());
//			ps.setInt(i++,temp_npc.getExp());
//			ps.setString(i++, temp_npc.getMoney());
//			ps.setInt(i++,temp_npc.getTake());
//			ps.setInt(i++, temp_npc.getSceneId());
//			ps.setString(i++,temp_npc.getNpcKey());
//			ps.setInt(i++, temp_npc.getNpcIsAttack());
//			ps.setInt(i++, temp_npc.getNpcType());
//			ps.executeUpdate();
//			ps.close();	
//			temp_npc.setID(getIDbyKey(temp_npc.getNpcKey()));
			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			dbConn.closeConn();
//		}
		
		return temp_npc;
	}
	
	
	
	/*public int getIDbyKey(String key)
	{
		int id = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select n_pk from n_attack_info where npc_key="+key;
			logger.debug(sql);
			stmt = conn.createStatement(); // 生成prepareStatement 对象
			rs = stmt.executeQuery(sql); 
			if( rs.next() )
			{
				id = rs.getInt("n_pk");
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return id;
		}
		finally 
		{
			dbConn.closeConn();

		}		
		
		return id;
	}*/
	
	/**
	 * 根据id得到一只npc
	 * @param id
	 * @return
	 *//*
	public NpcAttackVO getByID(int id)
	{
		NpcAttackVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select * from n_attack_info where n_pk="+id;
			logger.debug(sql);
			stmt = conn.createStatement(); // 生成prepareStatement 对象
			rs = stmt.executeQuery(sql); 
			if( rs.next())
			{
				vo = new NpcAttackVO();
				vo.setID(rs.getInt("n_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setCurrentHP(rs.getInt("n_current_HP"));
				vo.setNAttackswitch(rs.getInt("n_attackswitch"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setNpcName(rs.getString("npc_name"));
				vo.setNpcHP(rs.getInt("npc_HP"));
				
				vo.setDefenceDa(rs.getInt("npc_defence_da"));
				vo.setDefenceXiao(rs.getInt("npc_defence_xiao"));
				
				vo.setJinFy(rs.getInt("npc_jin_fy"));
				vo.setMuFy(rs.getInt("npc_mu_fy"));
				vo.setShuiFy(rs.getInt("npc_shui_fy"));
				vo.setHuoFy(rs.getInt("npc_huo_fy"));
				vo.setTuFy(rs.getInt("npc_tu_fy"));
				
				vo.setDrop(rs.getInt("npc_drop"));
				vo.setLevel(rs.getInt("npc_Level"));
				vo.setExp(rs.getInt("npc_EXP"));
				vo.setMoney(rs.getString("npc_money"));
				vo.setTake(rs.getInt("npc_take"));
				
				vo.setNpcRefurbishTime(rs.getInt("npc_refurbish_time"));
				
				vo.setNpcIsAttack(rs.getInt("npc_isAttack"));
				vo.setNpcType(rs.getInt("npc_type"));
				
				vo.setDizzyBoutNum(rs.getInt("dizzy_bout_num"));
				
				rs.close();
				stmt.close();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally 
		{
			dbConn.closeConn();

		}
		
		return vo;
	}*/
	
	
	
	/**
	 * 更新当前血量，每回合角色给npc 造成的伤害和宠物给npc造成的伤害
	 * @param id
	 * @param hp
	 * @return
	 *//*
	public int updateHPAndCharacterPetInjure(int id,int characterInjure,int petInjure)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update n_attack_info set n_current_HP = n_current_HP -" + characterInjure + "-"+petInjure+"  where n_pk=" + id;
			logger.debug(sql);
			stmt = conn.createStatement(); // 生成prepareStatement 对象
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return result;
		}
		finally 
		{
			dbConn.closeConn();

		}
		return result;
	}
	*//**
	 * 更新当前血量
	 * @param id
	 * @param hp
	 * @return
	 *//*
	public int updateCurrentHP(int id,int injure )
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update n_attack_info set n_current_HP = n_current_HP -" + injure+"  where n_pk=" + id;
			logger.debug(sql);
			stmt = conn.createStatement(); // 生成prepareStatement 对象
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return result;
		}
		finally 
		{
			dbConn.closeConn();
			
		}
		return result;
	}
	
	
	
	*//**
	 * 得到当前战斗的npc
	 * @param p_pk
	 * @return
	 *//*
	public NpcAttackVO getOneAttackNPCByPpk(int p_pk,int scene_id)
	{
		NpcAttackVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "select * from n_attack_info where npc_isAttack=1 and p_pk=" + p_pk + "  and scene_id = "+scene_id+"  order by n_pk limit 1";

			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				vo = new NpcAttackVO();
				vo.setID(rs.getInt("n_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setCurrentHP(rs.getInt("n_current_HP"));
				vo.setNAttackswitch(rs.getInt("n_attackswitch"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setNpcName(rs.getString("npc_name"));
				vo.setNpcHP(rs.getInt("npc_HP"));
				
				vo.setDefenceDa(rs.getInt("npc_defence_da"));
				vo.setDefenceXiao(rs.getInt("npc_defence_xiao"));
				
				vo.setJinFy(rs.getInt("npc_jin_fy"));
				vo.setMuFy(rs.getInt("npc_mu_fy"));
				vo.setShuiFy(rs.getInt("npc_shui_fy"));
				vo.setHuoFy(rs.getInt("npc_huo_fy"));
				vo.setTuFy(rs.getInt("npc_tu_fy"));
				
				vo.setDrop(rs.getInt("npc_drop"));
				vo.setLevel(rs.getInt("npc_Level"));
				vo.setMoney(rs.getString("npc_money"));
				vo.setExp(rs.getInt("npc_EXP"));
				vo.setTake(rs.getInt("npc_take"));
				
				vo.setNpcRefurbishTime(rs.getInt("npc_refurbish_time"));
				vo.setSceneId(rs.getInt("scene_id"));
				
				vo.setNpcIsAttack(rs.getInt("npc_isAttack"));
				vo.setNpcType(rs.getInt("npc_type"));
				
				vo.setDizzyBoutNum(rs.getInt("dizzy_bout_num"));
				
			}
			rs.close();
			stmt.close();
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();

		}
		
		return vo;
	}*/
	
	/**
	 * 清除当前角色刷新出的npcs
	 * @param p_pk
	 * @return
	 *//*
	public int deleteByPpk(int p_pk)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "delete from n_attack_info where p_pk=" + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	*//**
	 * 清除当前角色刷新出的npcs
	 * @param p_pk
	 * @return
	 *//*
	public int deleteByPpk(int p_pk,int state)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "delete from n_attack_info where p_pk='"+p_pk+"' and npc_isAttack="+state;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	*//**
	 * npc死亡，在表里清除
	 * @param p_pk
	 * @return
	 *//*
	public int deleteByNpk(int n_pk)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "delete from n_attack_info where n_pk=" + n_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return result;
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}*/
	
	/**
	 * 把一只npc处于攻击状态
	 * @param n_pk
	 * @return
	 *//*
	public int updateNpcAttackStat(int n_pk)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set npc_isAttack=1   where  n_pk=" + n_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	*//**
	 * 把所有主动攻击的npc处于攻击状态
	 * @param p_pk
	 * @return
	 *//*
	public int updateNpcsAttackStat(int p_pk)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set npc_isAttack=1   where n_attackswitch=1 and p_pk=" + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	*//**
	 * 初始化处于攻击状态npcs的每回合伤害值
	 * @param p_pk
	 * @return
	 *//*
	public int initNpcsBoutInjure(int p_pk)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set npc_bout_injure=0,npc_pet_injure=0   where npc_isAttack=1 and p_pk=" + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}*/
	
	/**
	 * 得到角色对应的，处于攻击状态的所有npc
	 * @param p_pk
	 * @return
	 *//*
	public List<NpcFighter> getAttackNPCs(int p_pk ,int scene_id)
	{
		List<NpcFighter> list = new ArrayList<NpcFighter>();
		NpcFighter vo = null;
		String sql = "select * from n_attack_info where npc_isAttack=1 and p_pk=" + p_pk + " and scene_id = "+scene_id+" order by n_pk";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			while (rs.next()) {
				vo = new NpcFighter();
				vo.setID(rs.getInt("n_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setCurrentHP(rs.getInt("n_current_HP"));
				vo.setNAttackswitch(rs.getInt("n_attackswitch"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setNpcName(rs.getString("npc_name"));
				vo.setNpcHP(rs.getInt("npc_HP"));
				
				vo.setDefenceDa(rs.getInt("npc_defence_da"));
				vo.setDefenceXiao(rs.getInt("npc_defence_xiao"));
				
				vo.setJinFy(rs.getInt("npc_jin_fy"));
				vo.setMuFy(rs.getInt("npc_mu_fy"));
				vo.setShuiFy(rs.getInt("npc_shui_fy"));
				vo.setHuoFy(rs.getInt("npc_huo_fy"));
				vo.setTuFy(rs.getInt("npc_tu_fy"));
				
				vo.setDrop(rs.getInt("npc_drop"));
				vo.setLevel(rs.getInt("npc_Level"));
				vo.setMoney(rs.getString("npc_money"));
				vo.setExp(rs.getInt("npc_EXP"));
				vo.setTake(rs.getInt("npc_take"));
				vo.setSceneId(rs.getInt("scene_id"));
				
				vo.setNpcRefurbishTime(rs.getInt("npc_refurbish_time"));
				
				vo.setNpcIsAttack(rs.getInt("npc_isAttack"));
				vo.setNpcType(rs.getInt("npc_type"));
				
				vo.setDizzyBoutNum(rs.getInt("dizzy_bout_num"));
				
				list.add(vo);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally 
		{
			dbConn.closeConn();
		}
		return list;
	}
	
	*//**
	 * 判断是否有处于攻击状态的npc
	 * @param p_pk
	 * @return
	 *//*
	public boolean isHaveAttackNPC(int p_pk,int scene_id)
	{
		boolean isHave = false;
		String sql = "";
		sql = "select count(*) as sum from n_attack_info where npc_isAttack=1 and p_pk=" + p_pk + " and scene_id = "+scene_id+"  order by n_pk";
		logger.debug(sql);
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try {
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				if( rs.getInt("sum")>0 )
				{
					isHave = true;
				}
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally 
		{
			dbConn.closeConn();
		}
		return isHave;
	}
	
	
	*//**
	 * 得到用户的所有遇到的npc
	 * @param p_pk
	 * @return
	 *//*
	public List getNPCsByPpk(int p_pk,int scene_id )
	{
		List<NpcAttackVO> list = new ArrayList();
		NpcAttackVO vo = null;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "select * from n_attack_info where p_pk=" + p_pk + " and scene_id = "+scene_id+"  order by n_pk";
			logger.debug(sql);
			stmt = conn.createStatement(); // 生成prepareStatement 对象
			rs = stmt.executeQuery(sql); 
			while (rs.next()) {
				vo = new NpcAttackVO();
				vo.setID(rs.getInt("n_pk"));
				vo.setPPk(rs.getInt("p_pk"));
				vo.setCurrentHP(rs.getInt("n_current_HP"));
				vo.setNAttackswitch(rs.getInt("n_attackswitch"));
				vo.setNpcID(rs.getInt("npc_ID"));
				vo.setNpcName(rs.getString("npc_name"));
				vo.setNpcHP(rs.getInt("npc_HP"));
				
				vo.setDefenceDa(rs.getInt("npc_defence_da"));
				vo.setDefenceXiao(rs.getInt("npc_defence_xiao"));
				
				vo.setJinFy(rs.getInt("npc_jin_fy"));
				vo.setMuFy(rs.getInt("npc_mu_fy"));
				vo.setShuiFy(rs.getInt("npc_shui_fy"));
				vo.setHuoFy(rs.getInt("npc_huo_fy"));
				vo.setTuFy(rs.getInt("npc_tu_fy"));
				
				vo.setDrop(rs.getInt("npc_drop"));
				vo.setLevel(rs.getInt("npc_Level"));
				vo.setMoney(rs.getString("npc_money"));
				vo.setExp(rs.getInt("npc_EXP"));
				vo.setTake(rs.getInt("npc_take"));
				
				vo.setNpcRefurbishTime(rs.getInt("npc_refurbish_time"));

				vo.setNpcIsAttack(rs.getInt("npc_isAttack"));
				vo.setNpcType(rs.getInt("npc_type"));
				
				vo.setDizzyBoutNum(rs.getInt("dizzy_bout_num"));
				
				list.add(vo);
			}
			rs.close();
			stmt.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbConn.closeConn();

		}
		
		return list;
	}
	
	*//**
	 * 判断是否有主动攻击的npc
	 * @param p_pk
	 * @return
	 *//*
	public NpcAttackVO isInitiativeAttackNPC(int p_pk)
	{
		NpcAttackVO  npc = null;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try {
			String sql = "select  * from n_attack_info where p_pk=" + p_pk + " and n_attackswitch=1  limit 1";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				npc = new NpcAttackVO();
				npc.setID(rs.getInt("n_pk"));
				npc.setPPk(rs.getInt("p_pk"));
				npc.setCurrentHP(rs.getInt("n_current_HP"));
				npc.setNpcID(rs.getInt("npc_ID"));
				npc.setNpcName(rs.getString("npc_name"));
				npc.setNpcHP(rs.getInt("npc_HP"));
				
				npc.setDefenceDa(rs.getInt("npc_defence_da"));
				npc.setDefenceXiao(rs.getInt("npc_defence_xiao"));
				
				npc.setJinFy(rs.getInt("npc_jin_fy"));
				npc.setMuFy(rs.getInt("npc_mu_fy"));
				npc.setShuiFy(rs.getInt("npc_shui_fy"));
				npc.setHuoFy(rs.getInt("npc_huo_fy"));
				npc.setTuFy(rs.getInt("npc_tu_fy"));
				
				npc.setDrop(rs.getInt("npc_drop"));
				npc.setLevel(rs.getInt("npc_Level"));
				npc.setExp(rs.getInt("npc_EXP"));
				npc.setMoney(rs.getString("npc_money"));
				npc.setTake(rs.getInt("npc_take"));
				npc.setNpcIsAttack(rs.getInt("npc_isAttack"));
				npc.setNpcType(rs.getInt("npc_type"));
				
				npc.setDizzyBoutNum(rs.getInt("dizzy_bout_num"));
				
				rs.close();
				stmt.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return npc;
		} finally 
		{
			dbConn.closeConn();
		}
		return npc;
	}
	
	*//**
	 * 判断是否还有npc
	 * @param args
	 *//*
	public boolean isHaveNpcByPpk(int p_pk)
	{
		boolean result = false;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "select n_pk from n_attack_info where p_pk=" + p_pk + " limit 1";
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if( rs.next() )
			{
				result = true;
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally 
		{
			dbConn.closeConn();
		}

		return result;
	}*/
	
/*	*//**
	 * 更新击晕状态剩余回合数
	 *//*
	public int updateDizzyBoutNumOfNPC( int n_pk ,int change_bout)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set dizzy_bout_num = dizzy_bout_num+"+change_bout+"  where n_pk=" + n_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	

	*//**
	 * 增加玩家战斗的npcs的击晕状态的回合数
	 * @param p_pk
	 * @param change_bout
	 *//*
	public int addDizzyBoutNumOfNPCs( int p_pk ,int dizzy_bout_num)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set dizzy_bout_num = "+dizzy_bout_num+"  where npc_isAttack=1 && dizzy_bout_num=0  && p_pk=" + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
*/
	/*
	 * 更新中毒状态剩余回合数
	 */
	public int updatePoisonBoutNumOfNPC( int n_pk ,int change_bout)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set poison_bout_num = poison_bout_num+"+change_bout+"  where n_pk=" + n_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/*
	 * 增加玩家战斗的npcs的中毒状态回合数
	 * @param p_pk
	 * @param poison_round_num
	 */
	public int addPoisonBoutNumOfNPCs(int p_pk,int poison_bout_num)
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update  n_attack_info set poison_bout_num= "+poison_bout_num+"  where npc_isAttack=1 && poison_bout_num=0  && p_pk=" + p_pk;
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}
	
	/*
	 * 更新当前npc的血量
	 */
	public int updateCurrentHP(int id,int injure )
	{
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.GAME_USER_DB);
		conn = dbConn.getConn();
		try
		{
			String sql = "update n_attack_info set n_current_HP = n_current_HP -" + injure+"  where n_pk=" + id;
			logger.debug(sql);
			stmt = conn.createStatement(); // 生成prepareStatement 对象
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return result;
		}
		finally 
		{
			dbConn.closeConn();
			
		}
		return result;
	}


	/**
	 * 根据个人角色id, npcid,和mapid来删除一个npc,
	 * 如果多于一个, 那就选择n_pk最小的
	 * @param p_pk
	 * @param npc_id
	 * @param mapid
	 * @return
	 *//*
	public int deleteNpcByMapNpcPPk(int p_pk, int npc_id, String mapid)
	{
		String sql = "delete from n_attack_info where p_pk="+p_pk+" and npc_ID="+npc_id+" and scene_id="+mapid+" limit 1";
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}

	*//**
	 * 根据个人角色id, npcid,和mapid来删除一个正在攻击的npc,
	 * 如果多于一个, 那就选择n_pk最小的
	 * @param p_pk
	 * @param npc_id
	 * @param mapid
	 * @return
	 *//*
	public int deleteNpcByMapNpcSwitch(int p_pk, int npc_id, String mapid)
	{
		String sql = "delete from n_attack_info where npc_isAttack = 1 and p_pk="+p_pk+" and npc_ID="+npc_id+" and scene_id="+mapid+" limit 1";
		int result = -1;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement(); 
			result = stmt.executeUpdate(sql); 
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		return result;
	}*/


	/**
	 * 判断此地是否还有其他的同类型主动攻击型怪, 如果有返回
	 * 个数, 如果没有返回-1
	 * @param pk
	 * @param map
	 * @param npcID
	 * @return
	 *//*
	public int getNpcIsDropGoods(int pk, String map, int npcID)
	{
		int i = -1;
		String sql = "select count(1) as all_num from n_attack_info where n_attackswitch=1 and p_pk="+pk+" and npc_ID="+npcID+" and scene_id="+map;
		DBConnection dbConn = new DBConnection(DBConnection.JYGAMEUSER_DB);
		conn = dbConn.getConn();
		logger.debug(" 判断此地是否还有其他的同类型主动攻击型怪="+sql);
		try
		{
			logger.debug(sql);
			stmt = conn.createStatement(); 
			rs = stmt.executeQuery(sql); 
			if (rs.next()) {
				i = rs.getInt("all_num");
			}
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			dbConn.closeConn();
		}
		if(i == 0) {
			i = -1;
		}
		return i;
	}*/
	
}
