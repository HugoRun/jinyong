package com.pm.service.clew;

import java.util.List;
import java.util.logging.Logger;

import com.ben.vo.friend.FriendVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.friend.FriendService;

/**
 * 专门负责登陆提示的类,现在是有 登陆好友提示,
 * 还有帮派 帮众登陆提示。
 * @author 张俊俊
 * @version 1.0
 */
public class ClewService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 好友登陆提示，当某玩家登陆后，他的好友会收到一条系统消息,提示他已经登陆 
	 */
	public void friendClew( String p_pk ) {
		
		FriendService friendService = new FriendService();
		List<FriendVO> list = friendService.addMyFriend(Integer.parseInt(p_pk));
		
		if ( list != null && list.size() != 0) {
    		FriendVO friendVO = null;
    		RoleCache roleCache = new RoleCache();
    		RoleEntity roleEntity = roleCache.getByPpk(p_pk);
    		String sceneId = roleEntity.getBasicInfo().getSceneId();
    		
    		SceneCache sceneCache = new SceneCache();
    		SceneVO sceneVO = sceneCache.getById(sceneId);
    		SystemInfoService infoService = new SystemInfoService();
    		
    		for ( int i=0;i <list.size();i++) {
    			friendVO = list.get(i);
    			StringBuffer infoString = new StringBuffer("您的好友");
    			infoString.append(roleEntity.getBasicInfo().getName()).append("已经在").append(sceneVO.getSceneName()).append("上线了!");
    			infoService.insertSystemInfoBySystem(friendVO.getPPk(), infoString.toString());
    		}
		}
	}
	
	/**
	 * 登陆提示
	 */
	public void loginClew( String p_pk) {
		if (p_pk == null || p_pk.equals("")) {
			logger.info("p_pk为空值!");
			return ;
		}
		friendClew(p_pk);
		
	}
	
}
