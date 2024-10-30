package com.ls.model.property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.ls.ben.dao.info.partinfo.ShortcutDao;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.model.user.UserBase;

/**
 * 存储玩家的快捷键信息
 * @author Administrator
 *
 */
public class RoleShortCutInfo extends UserBase
{
	private ShortcutDao shortcutDao;
	private LinkedHashMap<Integer,ShortcutVO> shortCut;
	
	/**
	 * 构造函数
	 * @param p_pk
	 */
	public RoleShortCutInfo(int p_pk)
	{
		super(p_pk);
		shortcutDao = new ShortcutDao();
		shortCut = shortcutDao.getAllByPpk(p_pk);
	}
	
	
	/**
	 * 得到玩家的 快捷键 列表
	 * @return
	 */
	public List<ShortcutVO> getShortList()
	{
		// 因为获得的快捷键值是混乱的.,所以用key值强制 转成按顺序排列的 键值
		return new ArrayList<ShortcutVO>(shortCut.values());
	}
	
	/**
	 * 修改缓存快捷键名称
	 */
	public void updateShortcutName(int sk_pk,String skillName){
		List  list = getShortList();
		for(int i = 0 ; i < list.size() ; i++){
			ShortcutVO vo = (ShortcutVO)list.get(i);
			if(vo.getOperateId() == sk_pk && vo.getScType() == 1){
				updateShortcut(vo.getScPk(),vo.getScType(),skillName, sk_pk);
				ShortcutDao shortcutDao = new ShortcutDao();
				shortcutDao.updateByPpk(vo.getScPk(),vo.getScType(),skillName, sk_pk);
			}
		}
	}
	/**
	 * 使用 个人快捷键主键 
	 * @param s_pk
	 * @return
	 */
	public ShortcutVO getShortByScPk(int sc_pk)
	{
		return (ShortcutVO) shortCut.get(sc_pk);
	}


	/**
	 * 更新 
	 * @param sc_pk
	 * @param type
	 * @param display
	 * @param operate_id
	 */
	public int updateShortcut(int sc_pk, int type, String sc_display,
			int operate_id)
	{
		if ( sc_pk == 0 || sc_display == null ) {
			return 0;
		}
		ShortcutVO shortcutVO = getShortByScPk(sc_pk);
		if (shortcutVO == null) {
			return 0;
		}
		shortcutVO.setScType(type);
		shortcutVO.setScDisplay(sc_display);
		shortcutVO.setOperateId(operate_id);	
		
		return shortcutDao.updateByPpk(sc_pk, type, sc_display,operate_id);
	}



	/**
	 * 把所有快捷键恢复到初始值
	 */
	public void clearShortcut()
	{
		boolean is_init = false;//是否有快捷键恢复初始值
		List<ShortcutVO> list = getShortList();
		for ( ShortcutVO shortcutVO :list) {
			if( shortcutVO.init() )
			{
				is_init = true;
			}
		}
		if( is_init==true )
		{
			ShortcutDao shortcutDao = new ShortcutDao();
			shortcutDao.clearShortcut(p_pk);
		}
	}
	
	/**
	 * 如果快捷键上的 道具已经用完,那么移除这个道具 ,
	 * @param p_pk
	 * @param operate_Id 操作id
	 */
	public void clearShortcutByOperateId(int p_pk, int operate_Id)
	{
		List<ShortcutVO> list = getShortList();
		for ( ShortcutVO shortcutVO :list) {
			if (shortcutVO.getOperateId() == operate_Id) {
				shortcutVO.setScType(0);
				shortcutVO.setScDisplay(shortcutVO.getScName());				
			}			
		}	
	}	
	
	
	/**
	 * 使用 快捷键id得到玩家的 具体快捷键, 
	 * @return
	 */
	

	
}
