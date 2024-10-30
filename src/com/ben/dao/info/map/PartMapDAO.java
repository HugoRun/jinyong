package com.ben.dao.info.map;

import com.ls.ben.cache.staticcache.map.MapCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.web.service.log.DataErrorLog;
import com.pub.db.jygamedb.Jygamedb;

/**
 * @author ��ƾ� ����jygame���еĳ����� 2:24:09 PM   sceneSwitch
 */
public class PartMapDAO {
	Jygamedb con;

	/**
	 * �����ѯ
	 */
	public String getsceneCoordinate(int scene_id) {
		try {
			return SceneCache.getById(scene_id+"").getSceneCoordinate();
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.debugData("�������ݴ���scene_id="+scene_id);
		} 
		return null;
	}
	
	/**
	 * ����:��ѯ������map����
	 */
	public int getmap(int scene_id) {
		try {
			return Integer.parseInt(SceneCache.getById(scene_id+"").getSceneMapqy());
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.debugData("�������ݴ���scene_id="+scene_id);
		}
		return 0;
	}
	/**
	 * ����:��ѯ������barea
	 */
	public int getbarea(int map_id) {
		try {
			return MapCache.getById(map_id+"").getMapFrom();
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.debugData("map�����ݴ���map_id="+map_id);
		} 
		return 0;
	}
	/**
	 * ����:��ѯ����ȼ�
	 */
	public int gettaskGrade(int tid) {
		try {
			return TaskCache.getById(tid+"").getTLevelXiao();
		} catch (Exception e) {
			e.printStackTrace();
			DataErrorLog.task(tid, "��ѯ����ȼ���tId="+tid);
		} 
		return 0;
	}
}
