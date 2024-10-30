/**
 * 
 */
package com.ls.web.service.system;

import com.ben.vo.intimatehint.IntimateHintVO;
import com.ls.ben.cache.staticcache.system.IntimateHintCache;

/**
 * 功能：武林小贴士
 * @author ls
 * Apr 7, 2009
 * 2:57:17 PM
 */
public class IntimateHintService
{
	/**
	 * 随机得到一个武林小贴士
	 */
	public IntimateHintVO getRandomIntimateHint()
	{
		IntimateHintCache intimateHintCache = new IntimateHintCache();
		return intimateHintCache.getRandomIntimateHint();
	}
}
