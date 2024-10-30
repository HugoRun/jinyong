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
 * ר�Ÿ����½��ʾ����,�������� ��½������ʾ,
 * ���а��� ���ڵ�½��ʾ��
 * @author �ſ���
 * @version 1.0
 */
public class ClewService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * ���ѵ�½��ʾ����ĳ��ҵ�½�����ĺ��ѻ��յ�һ��ϵͳ��Ϣ,��ʾ���Ѿ���½ 
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
    			StringBuffer infoString = new StringBuffer("���ĺ���");
    			infoString.append(roleEntity.getBasicInfo().getName()).append("�Ѿ���").append(sceneVO.getSceneName()).append("������!");
    			infoService.insertSystemInfoBySystem(friendVO.getPPk(), infoString.toString());
    		}
		}
	}
	
	/**
	 * ��½��ʾ
	 */
	public void loginClew( String p_pk) {
		if (p_pk == null || p_pk.equals("")) {
			logger.info("p_pkΪ��ֵ!");
			return ;
		}
		friendClew(p_pk);
		
	}
	
}
