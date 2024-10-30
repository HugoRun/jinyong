package com.ls.model.property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.ls.ben.dao.info.partinfo.ShortcutDao;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.model.user.UserBase;

/**
 * �洢��ҵĿ�ݼ���Ϣ
 * @author Administrator
 *
 */
public class RoleShortCutInfo extends UserBase
{
	private ShortcutDao shortcutDao;
	private LinkedHashMap<Integer,ShortcutVO> shortCut;
	
	/**
	 * ���캯��
	 * @param p_pk
	 */
	public RoleShortCutInfo(int p_pk)
	{
		super(p_pk);
		shortcutDao = new ShortcutDao();
		shortCut = shortcutDao.getAllByPpk(p_pk);
	}
	
	
	/**
	 * �õ���ҵ� ��ݼ� �б�
	 * @return
	 */
	public List<ShortcutVO> getShortList()
	{
		// ��Ϊ��õĿ�ݼ�ֵ�ǻ��ҵ�.,������keyֵǿ�� ת�ɰ�˳�����е� ��ֵ
		return new ArrayList<ShortcutVO>(shortCut.values());
	}
	
	/**
	 * �޸Ļ����ݼ�����
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
	 * ʹ�� ���˿�ݼ����� 
	 * @param s_pk
	 * @return
	 */
	public ShortcutVO getShortByScPk(int sc_pk)
	{
		return (ShortcutVO) shortCut.get(sc_pk);
	}


	/**
	 * ���� 
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
	 * �����п�ݼ��ָ�����ʼֵ
	 */
	public void clearShortcut()
	{
		boolean is_init = false;//�Ƿ��п�ݼ��ָ���ʼֵ
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
	 * �����ݼ��ϵ� �����Ѿ�����,��ô�Ƴ�������� ,
	 * @param p_pk
	 * @param operate_Id ����id
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
	 * ʹ�� ��ݼ�id�õ���ҵ� �����ݼ�, 
	 * @return
	 */
	

	
}
