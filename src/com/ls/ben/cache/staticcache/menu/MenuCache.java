package com.ls.ben.cache.staticcache.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.CacheBase;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.menu.OperateMenuDao;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;

public class MenuCache extends CacheBase
{
	public static String MENU_BY_ID = "menu_by_id";

	/**
	 * ͨ��id�õ�menu��Ϣ
	 * 
	 * @param menu_id
	 * @return
	 */
	public OperateMenuVO getById(String menu_id)
	{
		logger.debug("ͨ��id�õ�menu��Ϣ:menu_id=" + menu_id);

		OperateMenuVO menu = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, MENU_BY_ID);
		menu = (OperateMenuVO) result.get(menu_id);

		logger.debug("menu:" + menu);
		return menu;
	}

	/**
	 * ͨ��vID�ĵ�pic��Ϣ
	 * 
	 * @param menu_id
	 * @return
	 */
	public String getMenuPicStr(int menu_id)
	{
		logger.debug("ͨ��id�õ�menu��Ϣ:pic=" + menu_id);

		OperateMenuVO menu = null;
		HashMap result = getElementValue(STATIC_CACHE_NAME, MENU_BY_ID);
		menu = (OperateMenuVO) result.get(menu_id+"");

		if (menu == null)
		{
			logger.info("ͨ��vID���pic��ϢΪ��");
			return "";
		}

		return menu.getMenuImg();
	}
	/**
	 * �õ�����menu��Ϣ
	 * @param scene_id
	 * @return
	 */
	public List getMenuList()
	{
		HashMap menu_list = getElementValue(STATIC_CACHE_NAME, MENU_BY_ID);
		List list = new ArrayList(menu_list.values());
		return list;
	}
	
	/**
	 * ���¼���һ���˵�
	 */
	public void reloadOneMenu(OperateMenuVO menu)
	{
		if (menu != null)
		{
			//OperateMenuVO oldMenu = getById(menu.getId()+"");
			HashMap result = getElementValue(STATIC_CACHE_NAME, MENU_BY_ID);
			result.remove(menu.getId() + "");
			SceneVO scene = null;
			SceneCache sceneCache = new SceneCache();
			HashMap<String,SceneVO> scene_list = sceneCache.getSceneList();
			if( menu.getMenuMap()!=0 && menu.getMenuFatherId()==0 && menu.getMenuTaskFlag()==0 )
			{
				//����Ǹ��˵����Ҳ�������˵�
				//scene = scene_list.remove(""+oldMenu.getMenuMap());
				scene = scene_list.get(""+menu.getMenuMap());
				scene.getFatherMenuList().put(menu.getId()+"", menu);
			}
			if(menu.getId() == 5413 || menu.getId() == 5414 || menu.getId() == 5415 || menu.getMenuType() == 98){
				OperateMenuDao dao = new OperateMenuDao();
				dao.updateOperateMenuMenpaiConstant(menu.getId()+"", menu.getMenuName());
				dao.updateOperateMenuMenpaiNpc(menu.getId()+"", menu.getMenuOperate4());
			}
			result.put(menu.getId() + "", menu);
		}
	}
}
