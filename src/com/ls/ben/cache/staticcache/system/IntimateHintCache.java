package com.ls.ben.cache.staticcache.system;

import java.util.List;

import com.ben.vo.intimatehint.IntimateHintVO;
import com.ls.ben.cache.CacheBase;
import com.ls.pub.util.MathUtil;

public class IntimateHintCache extends CacheBase
{
	public static String HINT_LIST = "hint_list";
	
	/**
	 * ͨ��id�õ� �����Ϣ
	 * @param scene_id
	 * @return
	 */
	public IntimateHintVO getRandomIntimateHint()
	{
		logger.debug("�õ�һ�����������С��ʿ");
		IntimateHintVO hint = null;
		List<IntimateHintVO> hint_list = getElementValueUseList(STATIC_CACHE_NAME, HINT_LIST);
		
		int random_index = 0;
		random_index = MathUtil.getRandomBetweenXY(0, hint_list.size()-1);
		
		hint = hint_list.get(random_index);
		logger.debug("random_index:"+random_index);
		return hint;
	}
}