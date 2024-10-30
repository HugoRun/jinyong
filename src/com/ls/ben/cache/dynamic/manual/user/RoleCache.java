package com.ls.ben.cache.dynamic.manual.user;

import com.ls.ben.cache.CacheBase;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.log.DataErrorLog;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author Administrator
 * 功能：角色信息缓存
 */
public class RoleCache extends CacheBase {
    public static RoleEntity getByPpk(String p_pk) {
        if (!StringUtil.isNumber(p_pk)) {
            try {
                throw new Exception("无效角色id:p_pk=" + p_pk);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return getByPpk(Integer.parseInt(p_pk.trim()));
    }

    /**
     * 从读取缓存角色信息
     * @param p_pk p_pk
     * @return RoleEntity
     */
    public static RoleEntity getByPpk(int p_pk) {
        Cache cache = manager.getCache("roleCache");

        Element element = cache.get(p_pk);
        if (element == null) {
            RoleEntity roleEntity;
            try {
                roleEntity = new RoleEntity(p_pk);
            } catch (Exception e) {
                DataErrorLog.debugData("RoleCache.getByPpk参数错误：无该角色,p_pk=" + p_pk);
                return null;
            }
            element = new Element(roleEntity.getPPk(), roleEntity);
            cache.put(element);
        }
        return (RoleEntity) element.getValue();
    }

}
