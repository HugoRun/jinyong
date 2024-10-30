/**
 *
 */
package com.ls.ben.cache.dynamic.manual.chat;

import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.CacheBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author HHJ 聊天频道
 */
public class ChatInfoCahe extends CacheBase {
    // 公共
    public static String CHAT_PUBLIC_CACHE = "chat_public_cache";

    /**
     * 通过聊天频道类型获得所有聊天信息
     *
     * @param chatType chatType
     * @return LinkedHashSet
     */
    public LinkedHashSet getPublicChatInfo(String chatType) {
        LinkedHashSet roleList = null;
        HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, CHAT_PUBLIC_CACHE);
        roleList = result.get(chatType);
        if (roleList == null) {
            roleList = new LinkedHashSet();
            result.put(chatType, roleList);
        }
        return roleList;
    }

    /**
     * 聊天频道信息放入缓存通过聊天类型
     *
     * @param communion communion
     */
    public void put(CommunionVO communion) {
        HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, CHAT_PUBLIC_CACHE);
        LinkedHashSet roleList = result.get(communion.getCType() + "");
        if (roleList == null) {
            roleList = new LinkedHashSet();
            result.put(communion.getCType() + "", roleList);
        }
        roleList.add(communion);
    }

    /**
     * 清除玩家缓存信息
     *
     * @param p_pk     p_pk
     * @param chatType chatType
     */
    public void removeChat(int p_pk, String chatType) {
        HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, CHAT_PUBLIC_CACHE);
        LinkedHashSet roleList = result.get(chatType);
        if (roleList != null) {
            List list = new ArrayList(roleList);
            for (int i = 0; i < list.size(); i++) {
                CommunionVO vo = (CommunionVO) list.get(i);
                if (p_pk == vo.getPPk()) {
                    list.remove(i);
                    //System.out.println("玩家聊天这里清除出去");
                }
                roleList = new LinkedHashSet();
                LinkedHashSet linkedHashSet = new LinkedHashSet(list);
                result.put(chatType, linkedHashSet);
            }
        }
    }
}
