package com.ls.ben.cache.dynamic.manual.user;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.ls.ben.cache.CacheBase;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author Administrator
 * ���ܣ���ɫ��Ϣ����
 */
public class RoleCache extends CacheBase
{
	public static RoleEntity getByPpk( String p_pk )
	{
		if( StringUtil.isNumber(p_pk)==false )
		{
			try
			{
				throw new Exception("��Ч��ɫid:p_pk="+p_pk);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		return getByPpk( Integer.parseInt(p_pk.trim()));
	}
	
	/**
	 * �Ӷ�ȡ�����ɫ��Ϣ
	 * @param p_pk
	 * @return
	 */
	public static RoleEntity getByPpk( int p_pk )
	{
		Cache cache =manager.getCache("roleCache");
		
		Element element  = cache.get(p_pk);
		if( element==null )
		{
			RoleEntity roleEntity;
			try
			{
				roleEntity = new RoleEntity(p_pk);
			}
			catch (Exception e)
			{
				DataErrorLog.debugData("RoleCache.getByPpk���������޸ý�ɫ,p_pk="+p_pk);
				return null;
			}
			element = new Element(roleEntity.getPPk(), roleEntity);
			cache.put(element);
		}
		return (RoleEntity) element.getValue();
	}
	
}
