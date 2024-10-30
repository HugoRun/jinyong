/**
 * 
 */
package com.ls.ben.cache.dynamic.manual.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import com.ben.vo.communion.CommunionVO;
import com.ls.ben.cache.CacheBase;
import com.ls.model.user.RoleEntity;

/**
 * @author HHJ 聊天频道
 */
public class ChatInfoCahe extends CacheBase
{
	public static String CHAT_PUBLIC_CACHE = "chat_public_cache";// 公共
	
	/**
	 * 通过聊天频道类型获得所有聊天信息
	 * 
	 * @param view
	 * @return
	 */
	public LinkedHashSet getPublicChatInfo(String chatType)
	{
		LinkedHashSet role_list = null;
		HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, CHAT_PUBLIC_CACHE);
		role_list = result.get(chatType);
		if (role_list == null)
		{
			role_list = new LinkedHashSet();
			result.put(chatType, role_list);
		}
		return role_list;
	}
	
	/**
	 * 聊天频道信息放入缓存通过聊天类型
	 * 
	 * @param roleInfo
	 * @return
	 */
	public void put(CommunionVO communion)
	{
		HashMap<String, LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, CHAT_PUBLIC_CACHE);
		LinkedHashSet role_list = result.get(communion.getCType() + "");
		if (role_list == null)
		{
			role_list = new LinkedHashSet();
			result.put(communion.getCType() + "", role_list);
		}
		role_list.add(communion);
	}
	
	/**
	 * 清除玩家缓存信息
	 * @param communion
	 */ 
	public void removeChat(int p_pk,String chatType)
	{ 
		
		HashMap<String,LinkedHashSet> result = getElementValue(DYNAMIC_MANUAL_CACHE, CHAT_PUBLIC_CACHE);
		LinkedHashSet role_list = result.get(chatType); 
		if ( role_list != null ) {
    		List list = new ArrayList(role_list);
    		for(int i = 0 ; i < list.size() ;i++){
    			CommunionVO vo = (CommunionVO) list.get(i);
    			if(p_pk == vo.getPPk()){
    				list.remove(i);
    				//System.out.println("玩家聊天这里清除出去");
    			}
    			role_list = new LinkedHashSet();
    			LinkedHashSet linkedHashSet = new LinkedHashSet(list);
    			result.put(chatType , linkedHashSet);
    		} 
		}
	}
}
