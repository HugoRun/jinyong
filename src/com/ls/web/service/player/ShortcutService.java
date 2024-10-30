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
 * ����:��ݼ�����
 * @author ��˧
 * 9:40:49 AM
 */
public class ShortcutService {

	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * �õ�һ����ҵĿ�ݼ�
	 * @param p_pk
	 * @return
	 */
	public List<ShortcutVO> getByPpk(int p_pk)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(p_pk);			
		return roleEntity.getRoleShortCutInfo().getShortList();
	}
	
	/**
	 * �õ�һ����ҵĿ�ݼ�
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
					display = display + "��"+propNum;
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
	 * ����һ����ݼ�
	 */
	public int updateShortcut(int sc_pk,int type,int operate_id,String pPk)
	{
		RoleEntity roleEntity = RoleCache.getByPpk(pPk);	
		
		String display = "";
		if( type==Shortcut.ATTACK ) 
		{
			display = "����";
			display = StringUtil.gbToISO("����");
		}
		else if( type==Shortcut.LOOKINFO )
		{
			display = "�鿴";
			display = StringUtil.gbToISO(display);
		}
		else if( type==Shortcut.CURE )
		{
			display = "ҩƷ��";
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
			display = "������";
		}
		else if( type==Shortcut.FLEE )
		{
			display = "����";
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
	 * ����sc_pk�õ�һ����ݼ�����ϸ��Ϣ
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
	 * �����ݼ��ϵ� �����Ѿ�����,��ô�Ƴ�������� 
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
	/***********ȡ����ݼ����������************/
	public String getReplaceName(String propName)
	{
		String temp="";
		temp=propName.replace("��","");
		temp=temp.replace("��","");
		temp=temp.replace("��","");
		temp=temp.replace("��", "");
		temp=temp.replace("(", "");
		temp=temp.replace(")", "");
		String name=temp;
		return name;
	}

}
