package com.ben.dao.info.map;

import com.ls.ben.cache.staticcache.map.MapCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.web.service.log.DataErrorLog;
import com.pub.db.jygamedb.Jygamedb;

/**
 * @author 侯浩军 调用jygame库中的场景表 2:24:09 PM   sceneSwitch
 */
public class PartMapDAO {
	Jygamedb con;

	/**
	 * 坐标查询
	 */
	public String getsceneCoordinate(int scene_id) {
		try {
			return SceneCache.getById(scene_id+"").getSceneCoordinate();
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.debugData("场景数据错误，scene_id="+scene_id);
		} 
		return null;
	}
	
	/**
	 * 功能:查询场景的map区域
	 */
	public int getmap(int scene_id) {
		try {
			return Integer.parseInt(SceneCache.getById(scene_id+"").getSceneMapqy());
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.debugData("场景数据错误，scene_id="+scene_id);
		}
		return 0;
	}
	/**
	 * 功能:查询场景的barea
	 */
	public int getbarea(int map_id) {
		try {
			return MapCache.getById(map_id+"").getMapFrom();
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.debugData("map表数据错误，map_id="+map_id);
		} 
		return 0;
	}
	/**
	 * 功能:查询任务等级
	 */
	public int gettaskGrade(int tid) {
		try {
			return TaskCache.getById(tid+"").getTLevelXiao();
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.task(tid, "查询任务等级，tId="+tid);
		} 
		return 0;
	}
}
