package com.ls.web.service.player;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.info.partinfo.ShortcutDao;
import com.ls.ben.dao.info.skill.PlayerSkillDao;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.model.property.RoleShortCutInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.Shortcut;
import com.ls.pub.util.StringUtil;

/**
 * 功能:快捷键控制
 * @author 刘帅
 * 9:40:49 AM
 */
public class ShortcutService {

	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 得到一个玩家的快捷键
	 * @param p_pk
	 * @return
	 */
	public List<ShortcutVO> getByPpk(int p_pk)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);			
		return roleEntity.getRoleShortCutInfo().getShortList();
	}
	
	/**
	 * 得到一个玩家的快捷键
	 * @param p_pk
	 * @return
	 */
	public List<ShortcutVO> getByPpkWithDeal(int p_pk)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);			
		
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		List<ShortcutVO> list2 =  roleShortCutInfo.getShortList();
		List<ShortcutVO> list =  new ArrayList<ShortcutVO>();
		list = list2;
		ShortcutVO shortcutVO = null;
		for ( int i=0;i<list.size();i++) {
			shortcutVO = list.get(i);
			if ( shortcutVO.getScType() == 3) {
				com.ls.ben.vo.goods.prop.PropVO  propVO = PropCache.getPropById(shortcutVO.getOperateId());
				if (propVO.getPropClass() == PropType.BOX_CURE) {
					String display = shortcutVO.getScDisplay();
					if (display.length() > 3) {
						display = display.substring(0,3);
					}
					PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
					int propNum = playerPropGroupDao.getPropNumByByPropID(p_pk, shortcutVO.getOperateId());
					display = display + "×"+propNum;
					shortcutVO.setScDisplay(display);
				}
			} else {
				if (shortcutVO.getScDisplay().length() > 3) {
					shortcutVO.setScDisplay(shortcutVO.getScDisplay().substring(0,3));
				}				
			}			
		}
		return list;
	}
	

	/**
	 * 更新一个快捷键
	 */
	public int updateShortcut(int sc_pk,int type,int operate_id,String pPk)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(pPk);	
		
		String display = "";
		if( type==Shortcut.ATTACK ) 
		{
			display = "攻击";
			display = StringUtil.gbToISO("攻击");
		}
		else if( type==Shortcut.LOOKINFO )
		{
			display = "查看";
			display = StringUtil.gbToISO(display);
		}
		else if( type==Shortcut.CURE )
		{
			display = "药品名";
			String prop_name = PropCache.getPropById(operate_id).getPropName();
			display = getReplaceName(prop_name);
		}
		else if( type==Shortcut.SKILL )
		{
			PlayerSkillVO skill = roleEntity.getRoleSkillInfo().getSkillBySPk(operate_id);
			display = skill.getSkName();
		}
		else if( type==Shortcut.BOOK )
		{
			display = "卷轴名";
		}
		else if( type==Shortcut.FLEE )
		{
			display = "逃跑";
		}
		else if( type==Shortcut.ATTACKPROP )
		{
			display =  PropCache.getPropById(operate_id).getPropName();;
			display = getReplaceName(display);
		}
		
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		
		return roleShortCutInfo.updateShortcut(sc_pk,type,display,operate_id);	
	}
	
	
	

	
	/**
	 * 根据sc_pk得到一个快捷键的详细信息
	 */
	public ShortcutVO getByScPk(int sc_pk,String pPk)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(pPk);			
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		return roleShortCutInfo.getShortByScPk(sc_pk);		
	}
	
	
	
	/**
	 * 
	 * @param p_pk
	 * @return
	 */
	public List getSkills(int p_pk)
	{
		PlayerSkillDao skillDao = new PlayerSkillDao();
		return skillDao.getSkillsWithoutCatchPet(p_pk);
	}


	/**
	 * 如果快捷键上的 道具已经用完,那么移除这个道具 
	 * 
	 */
	public void clearShortcutoperate_id(int p_pk, int propID)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);			
		RoleShortCutInfo roleShortCutInfo = roleEntity.getRoleShortCutInfo();
		
		roleShortCutInfo.clearShortcutByOperateId(p_pk,propID);
		
		ShortcutDao shortcutDao = new ShortcutDao();
		shortcutDao.clearShortcutoperate_id(p_pk, propID);
		
	}
	/***********取出快捷键的特殊符号************/
	public String getReplaceName(String propName)
	{
		String temp="";
		temp=propName.replace("【","");
		temp=temp.replace("】","");
		temp=temp.replace("〖","");
		temp=temp.replace("〗", "");
		temp=temp.replace("(", "");
		temp=temp.replace(")", "");
		String name=temp;
		return name;
	}

}
