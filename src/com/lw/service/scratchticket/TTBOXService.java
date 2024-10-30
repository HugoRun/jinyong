package com.lw.service.scratchticket;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.npc.NpcdropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.npc.NpcService;
import com.lw.vo.ttbox.TTBOXVO;

public class TTBOXService
{
	public String getTTBoxPrize(RoleEntity roleInfo, TTBOXVO vo,
			HttpServletResponse response, HttpServletRequest request)
	{
		String hint = "";
		GoodsService goodsService = new GoodsService();
		NpcService npcService = new NpcService();
		int result = goodsService.putGoodsToWrap(roleInfo.getPPk(), vo.getGoodsid(), vo.getGoodstype(), 0, 1,GameLogManager.G_BOX_DROP);
		if (result == -1)
		{
			npcService.deleteByPpk(roleInfo.getPPk());
			hint = "您的包裹不足以放下该物品!";
		}
		else
		{
			npcService.deleteByPpk(roleInfo.getPPk());
			hint = "您获得了" + vo.getGoodname();
		}
		return hint;
	}

	public Map<Integer, TTBOXVO> getAllMap(RoleEntity roleInfo,
			Map<Integer, TTBOXVO> map, int scratchticket_id)
	{
		NpcdropDao npcDropDao = new NpcdropDao();
		for (int i = 0; i < 3; i++)
		{
			PropVO propVO = PropCache.getPropById(scratchticket_id);
			String npcId = getNpcID(propVO);
			List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByScratchTicket(
					npcId, 0 + "");
			TTBOXVO vo = new TTBOXVO();
			NpcdropVO npcvo = npcdrops.get(MathUtil.getRandomBetweenXY(0,
					(npcdrops.size() - 1)));
			vo.setDropPK(npcvo.getNpcdropID());
			vo.setGoodname(npcvo.getGoodsName());
			vo.setGoodsid(npcvo.getGoodsId());
			vo.setGoodsnum(1);
			vo.setGoodstype(npcvo.getGoodsType());
			map.put(i, vo);
		}
		return map;
	}

	private String getNpcID(PropVO propVO)
	{
		String npc_id = "";
		String[] npc_id_bak = propVO.getPropOperate1().split(",");
		for (int i = 1; i < npc_id_bak.length; i++)
		{
			String[] npc_id_bak_bak = npc_id_bak[i].split("-");
			npc_id = npc_id + npc_id_bak_bak[0];
			if (i != npc_id_bak.length - 1)
			{
				npc_id = npc_id + ",";
			}

		}
		return npc_id;
	}
}
