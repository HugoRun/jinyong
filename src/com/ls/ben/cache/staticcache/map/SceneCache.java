package com.ls.ben.cache.staticcache.map;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.ls.ben.cache.CacheBase;
import com.ls.ben.dao.map.SceneDao;
import com.ls.ben.vo.map.SceneVO;
import com.ls.web.service.log.DataErrorLog;

public class SceneCache extends CacheBase
{
	public static String SCENE_BY_ID = "scene_by_id";
	
	/**
	 * 通过id得到scene信息
	 * @param scene_id
	 * @return
	 */
	public static SceneVO getById( String scene_id )
	{
		if( StringUtils.isEmpty(scene_id)==true )
		{
			DataErrorLog.debugData("SceneCache.getById:参数错误：scene_id="+scene_id);
			return null;
		}
		SceneVO scene = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, SCENE_BY_ID);
		scene = (SceneVO)result.get(scene_id);
		
		if( scene==null )
		{
			SceneDao sceneDao = new SceneDao();
			scene = sceneDao.getByIdSceneView(scene_id);
			if( scene!=null )
			{
				result.put(scene.getSceneID()+"", scene);
			}
			else
			{
				DataErrorLog.debugData("数据错误，无该场景：scene_id="+scene_id);
			}
		}
		return scene;
	}
	
	/**
	 * 得到所有scene信息
	 * @param scene_id
	 * @return
	 */
	public HashMap<String,SceneVO> getSceneList()
	{
		HashMap<String,SceneVO> scene_list = getElementValue(STATIC_CACHE_NAME, SCENE_BY_ID);
		return scene_list;
	}
	
	/**
	 * 重新加在一条scene信息
	 */
	public void reloadOneScene(SceneVO sceneVO)
	{
		if (sceneVO != null)
		{
			SceneVO scene = getById(sceneVO.getSceneID()+"");
			scene.setSceneID(sceneVO.getSceneID());
			scene.setSceneName(sceneVO.getSceneName());
			scene.setSceneCoordinate(sceneVO.getSceneCoordinate());
			scene.setSceneLimit(sceneVO.getSceneLimit());
			
			scene.setSceneJumpterm(sceneVO.getSceneJumpterm());
			scene.setSceneAttribute(sceneVO.getSceneAttribute());
			scene.setSceneSwitch(sceneVO.getSceneSwitch());
			scene.setSceneKen(sceneVO.getSceneKen());
			
			scene.setSceneSkill(sceneVO.getSceneSkill());
			scene.setScenePhoto(sceneVO.getScenePhoto());
			scene.setSceneDisplay(sceneVO.getSceneDisplay());
			scene.setSceneMapqy(sceneVO.getSceneMapqy());
			
			scene.setSceneShang(sceneVO.getSceneShang());
			scene.setSceneXia(sceneVO.getSceneXia());
			scene.setSceneZuo(sceneVO.getSceneZuo());
			scene.setSceneYou(sceneVO.getSceneYou());
			
			HashMap<String,SceneVO> scene_list = getElementValue(STATIC_CACHE_NAME, SCENE_BY_ID);
			scene_list.remove(sceneVO.getSceneID()+"");
			scene_list.put(scene.getSceneID()+"", scene);
			
			
			//result.remove(sceneVO.getSceneID()+"");
			//重载MAP
			//MapCache mapCache = new MapCache();
			//MapVO mapVO = mapCache.getById(sceneVO.getSceneMapqy());
			//sceneVO.setMap(mapVO);
			//result.put(sceneVO.getSceneID()+"", sceneVO);
		}
	}
}
