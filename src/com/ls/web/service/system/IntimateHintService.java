/**
 * 
 */
package com.ls.web.service.system;

import com.ben.vo.intimatehint.IntimateHintVO;
import com.ls.ben.cache.staticcache.system.IntimateHintCache;

/**
 * ���ܣ�����С��ʿ
 * @author ls
 * Apr 7, 2009
 * 2:57:17 PM
 */
public class IntimateHintService
{
	/**
	 * ����õ�һ������С��ʿ
	 */
	public IntimateHintVO getRandomIntimateHint()
	{
		IntimateHintCache intimateHintCache = new IntimateHintCache();
		return intimateHintCache.getRandomIntimateHint();
	}
}
