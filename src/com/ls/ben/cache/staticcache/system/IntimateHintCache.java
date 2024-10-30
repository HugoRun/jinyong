package com.ls.ben.cache.staticcache.system;

import com.ben.vo.intimatehint.IntimateHintVO;
import com.ls.ben.cache.CacheBase;
import com.ls.pub.util.MathUtil;

import java.util.List;

public class IntimateHintCache extends CacheBase {
    public static String HINT_LIST = "hint_list";

    /**
     * 通过id得到 随机信息
     * @return
     */
    public IntimateHintVO getRandomIntimateHint() {
        logger.debug("得到一个随机的武林小贴士");
        IntimateHintVO hint;
        List<IntimateHintVO> hintList = getElementValueUseList(STATIC_CACHE_NAME, HINT_LIST);
        if (hintList == null || hintList.isEmpty()) {
            return null;
        }
        int randomIndex = 0;
        randomIndex = MathUtil.getRandomBetweenXY(0, hintList.size() - 1);
        hint = hintList.get(randomIndex);
        logger.debug("random_index:" + randomIndex);
        return hint;
    }
}
