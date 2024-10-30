package com.ls.ben.cache.dynamic.manual.view;

import java.util.HashMap;
import java.util.LinkedHashSet;

import com.ls.ben.cache.CacheBase;
import com.ls.model.user.RoleEntity;

/**
 * ���ܣ���Ұ����
 * @author ls
 * Apr 13, 2009
 * 5:04:30 PM
 */
public class ViewCache extends CacheBase
{
	public static String VIEW_INFO_CACHE = "view_info_cache";
	
	
	/**
	 * ͨ����Ұ�õ�����Ұ�µ����н�ɫ
	 * @param view
	 * @return
	 */
	public LinkedHashSet<RoleEntity> getRolesByView( String view )
	{
		logger.debug("��Ұ:view="+view);
		
		LinkedHashSet<RoleEntity> role_list = null;
		HashMap<String,LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, VIEW_INFO_CACHE);
		role_list = result.get(view);
		if( role_list==null )
		{
			role_list = new LinkedHashSet<RoleEntity>();
			result.put(view, role_list);
		}
		return role_list;
	}
	
	
	/**
	 * ����ҽ�ɫ��Ϣ���뻺��
	 * @param roleInfo
	 * @return
	 */
	public void put( String view,RoleEntity roleInfo )
	{
		if( roleInfo==null || view==null || view.equals("") )
		{
			logger.debug("��Ұ="+view+";�����Ϣ��"+roleInfo+"�����������Ұʱ����characterInfoΪNULL");
		}
		
		logger.debug("���������Ϣ����ɫid:"+roleInfo.getBasicInfo().getPPk()+";��ɫ����"+roleInfo.getBasicInfo().getName()+";��Ұ=view");
		
		HashMap<String,LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, VIEW_INFO_CACHE);
		LinkedHashSet role_list = result.get(view);
		
		if( role_list==null )
		{
			role_list = new LinkedHashSet<RoleEntity>();
			result.put(view, role_list);
		}
		
		role_list.add(roleInfo);
		
	}
	
    /**
	 * ����ҽ�ɫ��Ϣ�Ƴ�����
	 * @param p_pk
	 * @return
	 */
	public void remove( String view,RoleEntity roleInfo  )
	{
		logger.debug("���������Ϣ����ɫid:"+roleInfo.getBasicInfo().getPPk()+";��ɫ����"+roleInfo.getBasicInfo().getName()+";��Ұ="+view);
		
		HashMap<String,LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, VIEW_INFO_CACHE);
		LinkedHashSet role_list = result.get(view);
		
		if( role_list==null )
		{
			role_list = new LinkedHashSet<RoleEntity>();
			result.put(view, role_list);
		}
		
		role_list.remove(roleInfo);
		
		logger.debug("����ҽ�ɫ��Ϣ�Ƴ�����,��ɫid:"+roleInfo.getBasicInfo().getPPk()+";��ɫ����"+roleInfo.getBasicInfo().getName());
	}
}
