package com.ls.web.service.laba;

import java.util.ArrayList;
import java.util.Random;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.laba.labaVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.rank.RankService;

public class LaBaService
{
	Random rd = new Random();

	/**
	 * 一定概率，随机获得一个物品，作为开宝箱的奖励
	 * 
	 * @return
	 */
	public labaVO getGood(String prop_id, int p_pk, RoleEntity roleInfo,
			int other)
	{
		// 这里应该是从数据库中得到NPCid，然后按照概率从NPC中id抽取一个
		PropVO pv = PropCache.getPropById(Integer.parseInt(prop_id));
		String s_NPC[] = pv.getPropOperate1().split(",");
		// 然后根据得到的NPC_id得到一个物品的名字
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(roleInfo
				.getBasicInfo().getPPk());
		NpcService npcService = new NpcService();
		int npc_id = 0;
		int num = 0;
		if (other == 0)// 如果是新开宝箱
		{
			num = rd.nextInt(100);// 那么就得到一个随机数
		}
		else
		// 不是新开宝箱
		{
			num = other;// 给num赋值
		}
		String s_NPC_id[] = new String[2];
		int daNum = 0;// 大奖的结束值
		int zhongNum = 0;// 中将的结束值
		daNum = Integer.parseInt(s_NPC[0].split("-")[1]);
		zhongNum = Integer.parseInt(s_NPC[1].split("-")[1]);
		// 看num的值，然后根据几率范围确定得到的是大奖、中奖还是鼓励奖
		if (num >= 0 && num <= daNum)
		{
			s_NPC_id = s_NPC[0].split("-");
			npc_id = Integer.parseInt(s_NPC_id[0]);// 得到大奖npc
		}
		else
			if (num >= (daNum + 1) && num <= zhongNum)
			{
				s_NPC_id = s_NPC[1].split("-");
				npc_id = Integer.parseInt(s_NPC_id[0]);// 得到中奖npc
			}
			else
			{
				s_NPC_id = s_NPC[2].split("-");
				npc_id = Integer.parseInt(s_NPC_id[0]);// 得到鼓励奖npc
			}
		NpcdropVO npcVo = npcService
				.dropGoodsByLabaBoxTwo(npc_id, player, p_pk);// 然后从掉落表里得到一件物品
		// npcVo.setGoodsName(npcVo.getGoodsName() + "-" + num);//
		// 此num用来决定后两个奖的显示
		labaVO lv = new labaVO();
		lv.setNvo(npcVo);
		lv.setSNum(num);
		return lv;// 返回获得物品
	}

	/**
	 * 从已开出的宝箱物品重新抽取一个大奖、小奖或者鼓励
	 * 
	 * @return
	 */
	public ArrayList<String> getGoodTwo(String da_id, String zhong_id,
			String xiao_id, int daNum, int zhongNum, String NPC_DA,
			String NPC_ZHONG, String NPC_XIAO, String daName, String zhongName,
			String xiaoName, String daType, String zhongType, String xiaoType)
	{
		ArrayList<String> idAndName = new ArrayList<String>();
		// 这里应该是从数据库中得到NPCid，然后按照概率从NPC中id抽取一个
		String name = "";
		int num = rd.nextInt(100);
		// 看num的值，然后根据几率范围确定得到的是大奖、中奖还是鼓励奖
		if (num >= 0 && num <= daNum)
		{
			name = "大奖：" + daName;// 得到大奖npc
			idAndName.add(name);
			idAndName.add(da_id);
			idAndName.add(NPC_DA);
			idAndName.add(daType);
		}
		else
			if (num >= (daNum + 1) && num <= zhongNum)
			{
				name = "小奖：" + zhongName;// 得到中奖npc
				idAndName.add(name);
				idAndName.add(zhong_id);
				idAndName.add(NPC_ZHONG);
				idAndName.add(zhongType);
			}
			else
			{
				name = "鼓励奖：" + xiaoName;// 得到鼓励奖npc
				idAndName.add(name);
				idAndName.add(xiao_id);
				idAndName.add(NPC_XIAO);
				idAndName.add(xiaoType);
			}
		return idAndName;// 返回获得物品
	}

	/**
	 * 根据宝箱ID得到宝箱数据
	 * 
	 * @return
	 */
	public PropVO getGoodByID(String prop_id)
	{
		return PropCache.getPropById(Integer.parseInt(prop_id));// 返回获得道具类
	}

	/**
	 * 查看所需要的物品是否都在玩家身上
	 * 
	 * @param pg_pk
	 * @param gold_box_pgpk
	 * @param roleInfo
	 * @return
	 */
	public boolean checkHaveThisProp(String prop_id, RoleEntity roleInfo)
	{
		boolean boo = false;
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		boo = dao.getUserHasProp(roleInfo.getBasicInfo().getPPk(), Integer
				.parseInt(prop_id));
		return boo;
	}

	/**
	 * 把物品添加到背包里，并移除掉一个宝箱
	 * 
	 * @param goodId
	 * @param roleInfo
	 * @param prop_id
	 * @param p_pk
	 * @return
	 */
	public String getGoodAndRemoveABox(String goodId, int prop_id, int p_pk,
			int good_type)
	{
		GoodsService goodsService = new GoodsService();
		String name = "";
		// 把物品放入玩家背包里
		int a = goodsService.putGoodsToWrap(p_pk, Integer.parseInt(goodId),
				good_type, 1);
		if (a == -1)
		{
			name = "你的背包已满";
		}
		else
		{
			name = "物品已放入您的背包，请注意查看!";
		}
		return name;
	}

	/**
	 * 在点击使用的时候就移除掉一个宝箱
	 * 
	 * @param goodId
	 * @param roleInfo
	 * @param prop_id
	 * @param p_pk
	 * @return
	 */
	public void RemoveBox(RoleEntity roleInfo, int prop_id, int p_pk)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		GoodsService goodsService = new GoodsService();
		// 获得宝箱的pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo
				.getBasicInfo().getPPk(), prop_id);
		// 删除掉一个宝箱
		goodsService.removeProps(boxGroupVO, 1);
		// 记录下宝箱的信息
		String recordString = roleInfo.getBasicInfo().getName() + "一个拉霸宝箱";
		insertRecordInfo(roleInfo.getBasicInfo().getPPk(), 2, recordString);
	}

	/**
	 * 移除刷新宝箱所用的道具
	 * 
	 * @param goodId
	 * @param roleInfo
	 * @param prop_id
	 * @param p_pk
	 * @return
	 */
	public boolean RemovePropOfBox(RoleEntity roleInfo, int prop_id)
	{
		boolean boo = false;
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		GoodsService goodsService = new GoodsService();
		// 获得宝箱的pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo
				.getBasicInfo().getPPk(), prop_id);
		// 删除掉一个道具
		goodsService.removeProps(boxGroupVO, 1);
		boo = true;
		return boo;
	}

	/**
	 * 获得提示字
	 * 
	 * @return
	 */
	public String getWords()
	{
		String word = "";
		String words[] = { "虎虎生威", "恭喜發財", "五福臨門" };
		word = words[rd.nextInt(3)];
		return word;
	}

	/**
	 * 三个提示字一样
	 * 
	 * @param strs
	 * @param num
	 * @param goodid
	 * @param count
	 * @return
	 */
	public ArrayList<String> getListThree(String strs[], String num, int count)
	{
		ArrayList<String> al = new ArrayList<String>();
		String sWord = "";
		if (count == 0)
		{// 如果是第一次点
			if (num.equals("1")) // 如果点击的第一个
			{
				strs[0] = getWords();
			}
			if (num.equals("2")) // 如果点击的第二个
			{
				strs[1] = getWords();
			}
			if (num.equals("3")) // 如果点击的第三个
			{
				strs[2] = getWords();
			}
		}
		else
			if (count == 1 || count == 2)// 第二次点击
			{
				for (int i = 0; i < strs.length; i++)
				{
					if (!strs[i].equals("-1"))
					{
						sWord = strs[i];
						break;
					}
				}
				if (num.equals("1")) // 如果点击的第一个
				{
					strs[0] = sWord;
				}
				if (num.equals("2")) // 如果点击的第二个
				{
					strs[1] = sWord;
				}
				if (num.equals("3")) // 如果点击的第三个
				{
					strs[2] = sWord;
				}
			}
		al.add(strs[0]);
		al.add(strs[1]);
		al.add(strs[2]);
		return al;
	}

	/**
	 * 两个提示字一样
	 * 
	 * @param strs
	 * @param num
	 * @param goodid
	 * @param count
	 * @return
	 */
	public ArrayList<String> getListTwo(String strs[], String num, int count)
	{
		ArrayList<String> al = new ArrayList<String>();
		String ss[] = new String[2];
		Random rd = new Random();
		if (count == 0)
		{// 如果是第一次点
			if (num.equals("1")) // 如果点击的第一个
			{
				strs[0] = getWords();
			}
			if (num.equals("2")) // 如果点击的第二个
			{
				strs[1] = getWords();
			}
			if (num.equals("3")) // 如果点击的第三个
			{
				strs[2] = getWords();
			}
		}
		else
			if (count == 1)// 第二次点击
			{
				if (num.equals("1")) // 如果点击的第一个
				{
					strs[0] = getWords();
				}
				if (num.equals("2")) // 如果点击的第二个
				{
					strs[1] = getWords();
				}
				if (num.equals("3")) // 如果点击的第三个
				{
					strs[2] = getWords();
				}
			}
			else
				if (count == 2)
				{
					if (num.equals("1")) // 如果点击的第一个
					{
						if (strs[1].equals(strs[2]))
						{
							do
							{
								strs[0] = getWords();
							} while (strs[0].equals(strs[1]));
						}
						else
						{
							ss[0] = strs[1];
							ss[1] = strs[2];
							strs[0] = ss[rd.nextInt(2)];
						}
					}
					if (num.equals("2")) // 如果点击的第二个
					{
						if (strs[0].equals(strs[2]))
						{
							do
							{
								strs[1] = getWords();
							} while (strs[1].equals(strs[0]));
						}
						else
						{
							ss[0] = strs[0];
							ss[1] = strs[2];
							strs[1] = ss[rd.nextInt(2)];
						}
					}
					if (num.equals("3")) // 如果点击的第三个
					{
						if (strs[0].equals(strs[1]))
						{
							do
							{
								strs[2] = getWords();
							} while (strs[2].equals(strs[0]));
						}
						else
						{
							ss[0] = strs[0];
							ss[1] = strs[1];
							strs[2] = ss[rd.nextInt(2)];
						}
					}
				}
		al.add(strs[0]);
		al.add(strs[1]);
		al.add(strs[2]);
		return al;
	}

	/**
	 * 三个提示字都不一样
	 * 
	 * @param strs
	 * @param num
	 * @param goodid
	 * @param count
	 * @return
	 */
	public ArrayList<String> getList(String strs[], String num, int count)
	{
		ArrayList<String> al = new ArrayList<String>();
		if (count == 0)
		{// 如果是第一次点
			if (num.equals("1")) // 如果点击的第一个
			{
				strs[0] = getWords();
			}
			if (num.equals("2")) // 如果点击的第二个
			{
				strs[1] = getWords();
			}
			if (num.equals("3")) // 如果点击的第三个
			{
				strs[2] = getWords();
			}
		}
		else
			if (count == 1)// 第二次点击
			{
				String sWord = "";
				for (int i = 0; i < strs.length; i++)
				{
					if (!strs[i].equals("-1"))
					{
						sWord = strs[i];
						break;
					}
				}
				if (num.equals("1")) // 如果点击的第一个
				{
					do
					{
						strs[0] = getWords();
					} while (strs[0].equals(sWord));
				}
				if (num.equals("2")) // 如果点击的第二个
				{
					do
					{
						strs[1] = getWords();
					} while (strs[1].equals(sWord));
				}
				if (num.equals("3")) // 如果点击的第三个
				{
					do
					{
						strs[2] = getWords();
					} while (strs[2].equals(sWord));
				}
			}
			else
				if (count == 2)
				{
					if (num.equals("1")) // 如果点击的第一个
					{
						do
						{
							strs[0] = getWords();
						} while (strs[0].equals(strs[1])
								|| strs[0].equals(strs[2]));
					}
					if (num.equals("2")) // 如果点击的第二个
					{
						do
						{
							strs[1] = getWords();
						} while (strs[1].equals(strs[0])
								|| strs[1].equals(strs[2]));
					}
					if (num.equals("3")) // 如果点击的第三个
					{
						do
						{
							strs[2] = getWords();
						} while (strs[2].equals(strs[0])
								|| strs[2].equals(strs[1]));
					}
				}
		al.add(strs[0]);
		al.add(strs[1]);
		al.add(strs[2]);
		return al;
	}

	/**
	 * 把宝箱的信息记录下来
	 * 
	 * @param pk
	 * @param info_type
	 * @param content
	 */
	private void insertRecordInfo(int pPk, int info_type, String content)
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		playerPropGroupDao.sendRecordGoldInfo(pPk, info_type, content);
		if (info_type == 1)
		{
			// 统计需要
			new RankService().updateAdd(pPk, "open", 1);
		}
	}
}
