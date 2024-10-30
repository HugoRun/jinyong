package com.ben;

import com.ls.ben.cache.staticcache.skill.SkillCache;

/**
 * @author 侯浩军
 * <p>
 * 4:58:49 PM
 */
public class EntityDAO {

    /**
     * 拼接字符串
     */
    public String getArrayValue(String[] pks) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < pks.length; i++) {
            if (i + 1 == pks.length) {
                sb.append(pks[i]);
            } else {
                sb.append(pks[i]).append("-");
            }
        }
        if (pks.length > 0) {
            return sb.toString();
        } else {
            return "0";
        }
    }

    /**
     * 功能:技能的名称
     */
    public String getSkillName(String id) {
        try {
            return SkillCache.getById(Integer.parseInt(id)).getSkName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
