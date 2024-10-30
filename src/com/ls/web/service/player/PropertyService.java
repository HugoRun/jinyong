package com.ls.web.service.player;

import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.RoleEntity;

/**
 * 此类为修改 玩家属性的 类。
 * @author Administrator
 *
 */
public class PropertyService
{
	
	/**
	 * 在修改 玩家的 hp属性时 ， 会首先检查内存中 此玩家是否在线，
	 * 如果玩家不在线，则直接变更数据库，否则就调用 roleEntity 相关的方法
	 */
	public void updateHpProperty(int p_pk, int p_hp) {
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			// 如果playerB确实在线，那么更新内存和数据库
			roleInfo.getBasicInfo().updateHp(p_hp);
		} 
	}

	/**
	 * 在修改 玩家的 mp属性时 ， 会首先检查内存中 此玩家是否在线，
	 * 如果玩家不在线，则直接变更数据库，否则就调用 roleEntity 相关的方法
	 */
	public void updateMpProperty(int p_pk, int p_mp)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			roleInfo.getBasicInfo().updateMp(p_mp);
		} 
	}
	
	/**
	 * 在修改 玩家的 exp属性时 ， 会首先检查内存中 此玩家是否在线，
	 * 如果玩家不在线，则直接变更数据库，否则就调用 roleEntity 相关的方法
	 */
	public void updateExpProperty(int p_pk, long exp)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			// 如果playerB确实在线，那么更新内存和数据库
			roleInfo.getBasicInfo().updateCurExp(exp+"");
		} 
	}
	
	/**
	 * 在修改 玩家的 exp属性时 ， 会首先检查内存中 此玩家是否在线，
	 * 如果玩家不在线，则直接变更数据库，否则就调用 roleEntity 相关的方法
	 */
	public void updateAddExpProperty(int p_pk, long exp)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");
		if ( roleInfo != null) {
			// 如果playerB确实在线，那么更新内存和数据库
			roleInfo.getBasicInfo().updateAddCurExp(exp);
		} 
	}
	
	/**
	 * 得到玩家的  名字
	 */
	public String getPlayerName(int p_pk)
	{
		String enemy_name = "";
		RoleEntity other_roleInfo = RoleService.getRoleInfoById(p_pk+"");
		if ( other_roleInfo != null) {
			enemy_name = other_roleInfo.getBasicInfo().getRealName();
		} else {
			PartInfoDao infodao = new PartInfoDao();
			enemy_name = infodao.getNameByPpk(p_pk);
		}
		return enemy_name;
	}

}
