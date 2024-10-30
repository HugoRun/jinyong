package com.ls.ben.cache.staticcache.expNpcDrop;

import java.util.List;

import net.sf.ehcache.CacheManager;

import com.ben.dao.expnpcdrop.ExpNpcdropDAO;
import com.ben.vo.expnpcdrop.ExpNpcdropVO;
import com.ls.ben.cache.CacheBase;
import com.ls.web.service.cache.CacheService;

/**
 * 处理有关npc掉落经验、掉落金钱倍数表
 * @author Administrator
 *
 */
public class ExpNpcDropCache extends CacheBase
{
	public static String NPC_DROPEXP_BY_ID = "npc_dropExp_by_id";
	/**
	 * 获取执行经验倍数的数据
	 * @param enforce  执行 0 不执行 1执行
	 * @param exp_cimelia 1是经验掉率 2是掉宝掉率
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
	 * 重新加载掉落表
	 */
	public void reloadDrop(){
		CacheManager manager = CacheManager.create();
		CacheService cacheService = new CacheService();
		cacheService.initExpNpcDropCache(manager);
	}
}
