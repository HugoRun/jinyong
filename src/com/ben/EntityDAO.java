package com.ben;

import com.ls.ben.cache.staticcache.skill.SkillCache;

/**
 * @author ��ƾ�
 * 
 * 4:58:49 PM
 */
public class EntityDAO {

	/**
	 * ƴ���ַ���
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
	 * ����:���ܵ�����
	 */
	public String getSkillName(String id) {
		try {
			return SkillCache.getById(Integer.parseInt(id)).getSkName();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "";
	}
}
