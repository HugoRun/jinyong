package com.lw.service.scratchticket;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.npc.NpcdropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.PlayerService;

public class ScratchTicketService
{
	/***************************************************************************
	 * @param 玩家数据
	 * @param 刮刮乐道具ID
	 * @param MAP
	 * @param 刮刮乐的位置
	 **************************************************************************/
	public String getScratchTicketPrize(RoleEntity roleInfo,
			int scratchticket_id, Map<Integer, String> map, String ss_num,
			HttpServletResponse response, HttpServletRequest request)
	{
		String hint = null;
		hint = dropPropByScratchTicket(roleInfo, scratchticket_id);
		if (hint != null)
		{
			return null;
		}
		NpcService npcService = new NpcService();

		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		int result = 0;

		if (dropgoods != null && dropgoods.size() != 0)
		{
			Random random = new Random();
			int num = random.nextInt(dropgoods.size());
			DropGoodsVO dropGoods = dropgoods.get(num);

			GoodsService goodsService = new GoodsService();
			result = goodsService.pickupGoods(roleInfo.getBasicInfo().getPPk(),
					dropGoods.getDPk(), 0, response, request);

			if (result == -1)
			{
				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			}
			else
			{
				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
				hint = "您获得了" + dropGoods.getGoodsName() + "×"
						+ dropGoods.getDropNum();
				map.put(Integer.parseInt(ss_num), dropGoods.getGoodsName());
				int num_bak = roleInfo.getBasicInfo().getScratchticketnum() - 1;
				if (num_bak < 1)
				{
					roleInfo.getBasicInfo().setScratchticketnum(0);
				}
				else
				{
					roleInfo.getBasicInfo().setScratchticketnum(num_bak);
				}
			}
		}
		return hint;
	}

	// 掉落物品
	private String dropPropByScratchTicket(RoleEntity roleInfo,
			int scratchticket_id)
	{
		String hint = null;
		// 判断是否可以拿到
		if (roleInfo.getBasicInfo().getWrapSpare() < 2)
		{
			hint = "您物品栏空间不够!";
			return hint;
		}
		// 开始掉落物品
		PropVO propVO = PropCache.getPropById(scratchticket_id);
		NpcService npcService = new NpcService();

		// 清楚原来的掉落物
		roleInfo.getDropSet().clearDropItem();
		// 黄金宝箱掉落八个物品
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(roleInfo
				.getBasicInfo().getPPk());
		npcService.dropGoodsByRareBoxByGOLD(roleInfo, player, propVO);
		return hint;
	}

	public Map<Integer, String> getAllMap(RoleEntity roleInfo,
			Map<Integer, String> map, int scratchticket_id)
	{
		NpcdropDao npcDropDao = new NpcdropDao();
		PropCache propCache = new PropCache();
		PropVO propVO = propCache.getPropById(scratchticket_id);
		String npcId = getNpcID(propVO);
		List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByScratchTicket(npcId,
				0 + "");
		for (int i = 1; i < 11; i++)
		{
			if (map.get(i).equals("*神*秘*宝*物*"))
			{
				NpcdropVO vo = npcdrops.get(i);
				map.put(i, vo.getGoodsName());
			}
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
