package com.lw.service.synthesize;

import com.lw.dao.synthesize.PlayerSynthesizeDao;

public class PlayerSynthesizeService
{
	// ����Ƿ���д��䷽
	public boolean isHaveSynthesize(int p_pk, int s_id)
	{
		PlayerSynthesizeDao dao = new PlayerSynthesizeDao();
		if (dao.getPlayerSynthesize(p_pk, s_id) == 0)
		{
			return true;
		}
		return false;
	}
}
