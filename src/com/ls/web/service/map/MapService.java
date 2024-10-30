
package com.ls.web.service.map;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.map.MapCache;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.web.service.room.RoomService;

/**
 * ����:
 * @author ��˧
 * 10:01:45 AM
 */
public class MapService {

	
	public static Logger logger =  Logger.getLogger("log.service");

	
	/**
	 * scene_id����barea�����ĵ�
	 * @param scene_id
	 * @return
	 */
	public int getBareaPointBySceneID(int scene_id)
	{
		RoomService roomService = new RoomService();
		SceneVO scene = roomService.getById(scene_id+"");
		return scene.getMap().getBarea().getBareaPoint();
	}
	
	public MapVO getById( String map_id )
	{
		MapCache mapCache = new MapCache();
		return mapCache.getById(map_id);
	}
	
	
	/**
	 * �õ���ͼ������
	 * @param map_id
	 * @return
	 */
	public String getDiaplayById( String map_id )
	{
		MapCache mapCache = new MapCache();
		MapVO mapvo = mapCache.getById(map_id);
		if(mapvo != null) {
			return mapvo.getMapDisplay();
		} else {
			return "";
		}
	}

}
