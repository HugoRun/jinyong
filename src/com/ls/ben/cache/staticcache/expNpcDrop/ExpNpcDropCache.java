package com.ls.ben.cache.staticcache.expNpcDrop;

import java.util.List;

import net.sf.ehcache.CacheManager;

import com.ben.dao.expnpcdrop.ExpNpcdropDAO;
import com.ben.vo.expnpcdrop.ExpNpcdropVO;
import com.ls.ben.cache.CacheBase;
import com.ls.web.service.cache.CacheService;

/**
 * �����й�npc���侭�顢�����Ǯ������
 * @author Administrator
 *
 */
public class ExpNpcDropCache extends CacheBase
{
	public static String NPC_DROPEXP_BY_ID = "npc_dropExp_by_id";
	/**
	 * ��ȡִ�о��鱶��������
	 * @param enforce  ִ�� 0 ��ִ�� 1ִ��
	 * @param exp_cimelia 1�Ǿ������ 2�ǵ�������
	 * @return
	 */
	public ExpNpcdropVO getExpNpcdrop (int exp_cimelia) {
		ExpNpcdropVO expNpcdropVO = null;
		List<ExpNpcdropVO> expNpcDropList = getElementValueUseList(STATIC_CACHE_NAME,NPC_DROPEXP_BY_ID);
		for (ExpNpcdropVO expvo:expNpcDropList) {
			if ( expvo.getEnforce() == 1) {
				if (expvo.getExpCimelia() == exp_cimelia ) {
					expNpcdropVO = expvo;
					break ;
				}
			}
		}	
		return expNpcdropVO;
	}
	
	/**
	 * ���¼��ص����
	 */
	public void reloadDrop(){
		CacheManager manager = CacheManager.create();
		CacheService cacheService = new CacheService();
		cacheService.initExpNpcDropCache(manager);
	}
}
