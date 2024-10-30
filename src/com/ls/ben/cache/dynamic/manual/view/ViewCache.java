package com.ls.ben.cache.dynamic.manual.view;

import com.ls.ben.cache.CacheBase;
import com.ls.model.user.RoleEntity;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * 功能：视野缓存
 *
 * @author ls
 * Apr 13, 2009
 * 5:04:30 PM
 */
public class ViewCache extends CacheBase {
    public static String VIEW_INFO_CACHE = "view_info_cache";


    /**
     * 通过视野得到该视野下的所有角色
     *
     * @param view
     * @return
     */
    public LinkedHashSet<RoleEntity> getRolesByView(String view) {
        logger.debug("视野:view = " + view);
        LinkedHashSet roleList;
        HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, VIEW_INFO_CACHE);
        roleList = result.computeIfAbsent(view, k -> new LinkedHashSet<RoleEntity>());
        return roleList;
    }


    /**
     * 把玩家角色信息放入缓存
     *
     * @param roleInfo
     * @return
     */
    public void put(String view, RoleEntity roleInfo) {
        if (roleInfo == null || view == null || view.isEmpty()) {
            logger.debug("视野=" + view + ";玩家信息：" + roleInfo + "；缓存玩家视野时出错：characterInfo为NULL");
        }
        logger.debug("缓存玩家信息，角色id:" + roleInfo.getBasicInfo().getPPk() + ";角色名：" + roleInfo.getBasicInfo().getName() + ";视野=view");
        HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, VIEW_INFO_CACHE);
        LinkedHashSet role_list = result.computeIfAbsent(view, k -> new LinkedHashSet<RoleEntity>());
        role_list.add(roleInfo);
    }

    /**
     * 把玩家角色信息移除缓存
     *
     * @param p_pk
     * @return
     */
    public void remove(String view, RoleEntity roleInfo) {
        logger.debug("缓存玩家信息，角色id:" + roleInfo.getBasicInfo().getPPk() + ";角色名：" + roleInfo.getBasicInfo().getName() + ";视野=" + view);

        HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, VIEW_INFO_CACHE);
        LinkedHashSet role_list = result.get(view);

        if (role_list == null) {
            role_list = new LinkedHashSet<RoleEntity>();
            result.put(view, role_list);
        }

        role_list.remove(roleInfo);

        logger.debug("把玩家角色信息移除缓存,角色id:" + roleInfo.getBasicInfo().getPPk() + ";角色名：" + roleInfo.getBasicInfo().getName());
    }
}
