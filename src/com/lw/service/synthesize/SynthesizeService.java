package com.lw.service.synthesize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.synthesize.SynthesizeDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.synthesize.SynthesizeVO;

public class SynthesizeService
{
	Logger logger = Logger.getLogger("log.service");

	/** 得到玩家可以使用的配方列表 */
	public List<SynthesizeVO> getSynthesizePropList(int s_type, int s_level,
			int thispage, int perpagenum)
	{
		SynthesizeDao dao = new SynthesizeDao();
		return dao.getSynthesizeList(s_type, s_level, thispage, perpagenum);
	}

	/** 根据等级和技能类型得到玩家可以使用的配方 */
	public List<SynthesizeVO> getSynthesizeProp(int s_type, int s_level)
	{
		SynthesizeDao dao = new SynthesizeDao();
		return dao.getSynthesize(s_type, s_level);

	}

	/** 得到配方生成物品的名称 */
	public List getSynthesizePropInfo(int s_type, int s_level)
	{
		List namelist = new ArrayList();
		List<SynthesizeVO> list = getSynthesizeProp(s_type, s_level);
		for (int i = 0; i < list.size(); i++)
		{
			SynthesizeVO vo = list.get(i);
			String name = getSynthesizePropName(vo);
			namelist.add(name);
		}
		return namelist;
	}

	/** 名称分页 */
	public List<String> getSynthesizePropInfoList(int s_type, int s_level,
			int thispage, int perpagenum)
	{
		List list = getSynthesizePropInfo(s_type, s_level);
		int totalSize = list.size();
		List<String> currentPageV = new ArrayList<String>();
		/** 总页数: */
		int totalPageNum = totalSize / perpagenum;

		if (totalSize % perpagenum > 0)
		{
			totalPageNum = totalSize / perpagenum + 1;
		}
		/** 当前的页数:pageNum； */

		for (int j = 0; j < list.size(); j++)
		{

			if ((j >= (thispage - 1) * perpagenum)
					&& (j < thispage * perpagenum))
			{
				String name = (String) list.get(j);
				currentPageV.add(name);
			}
			if (currentPageV.size() == perpagenum)
			{
				break;
			}
		}
		return currentPageV;
	}

	/** 得到配方ID */
	public List getSynthesizePropInfoID(int s_type, int s_level)
	{
		List idlist = new ArrayList();
		List<SynthesizeVO> list = getSynthesizeProp(s_type, s_level);
		for (int i = 0; i < list.size(); i++)
		{
			SynthesizeVO vo = list.get(i);
			int s_id = vo.getSynthesizeID();
			idlist.add(s_id);
		}
		return idlist;
	}

	/** 得到配方ID最大的技能熟练度 */
	public List getSynthesizePropInfoMaxSleight(int s_type, int s_level)
	{
		List maxsleightlist = new ArrayList();
		SynthesizeDao dao = new SynthesizeDao();
		List<SynthesizeVO> list = getSynthesizeProp(s_type, s_level);
		for (int i = 0; i < list.size(); i++)
		{
			SynthesizeVO vo = list.get(i);
			int s_id = vo.getSynthesizeID();
			int s_sleight = dao.getSynthesizeMaxSleight(s_id);
			maxsleightlist.add(s_sleight);
		}
		return maxsleightlist;
	}

	/** 得到配方ID需要的技能熟练度 */
	public List getSynthesizePropInfoMinSleight(int s_type, int s_level)
	{
		List minsleightlist = new ArrayList();
		SynthesizeDao dao = new SynthesizeDao();
		List<SynthesizeVO> list = getSynthesizeProp(s_type, s_level);
		for (int i = 0; i < list.size(); i++)
		{
			SynthesizeVO vo = list.get(i);
			int s_id = vo.getSynthesizeID();
			int s_sleight = dao.getSynthesizeMinSleight(s_id);
			minsleightlist.add(s_sleight);
		}
		return minsleightlist;
	}

	/** 根据配方ID得到使用该配方获得技能熟练度 */
	public int getSynthesizeSleight(int s_id)
	{
		SynthesizeDao dao = new SynthesizeDao();
		return dao.getSynthesizeSleight(s_id);
	}

	/**
	 * 获得原材料的显示字符串
	 * 
	 * @param vo
	 * @return
	 */
	public List<String> getSynthesizeList(SynthesizeVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getProp(); // 需要兑换的原材料
		// String operate2 = vo.getSynthesizeProp(); //兑换的目的品
		String[] reChange = operate1.split(";"); // 需要兑换的原材料的数组
		// String[] reChange2 = operate2.split(";"); //兑换的目的品的数组
		if (reChange == null)
		{
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;

		for (int i = 0; i < length; i++)
		{
			String[] materials = reChange[i].split(","); // 每个兑换的原材料可能不止一种
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for (int a = 0; a < materials.length; a++)
			{
				String[] unit = materials[a].split("-");
				if (unit[0].equals("d"))
				{ // 如果原材料是道具的处理情况
					PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
					sb.append("-").append(propvo.getPropName()).append("×").append(unit[3]);
				}
				else
					if (unit[0].equals("z"))
					{ // 如果原材料是装备的处理情况
						sb.append("-");
						int equip_id = Integer.valueOf(unit[1]);
						GameEquip equip = EquipCache.getById(equip_id);
						sb.append(equip.getName()).append("×").append(unit[2]);
					}
					else
						if (unit[0].equals("j"))
						{ // 如果原材料是金钱的处理情况
							sb.append("-").append(
									MoneyUtil.changeCopperToStr(unit[1]));

						}
			}
			logger.info("i的value: " + i + " 原材料装入字段" + sb.toString());
			list.add(sb.toString());
		}
		return list;
	}

	/**
	 * 获得合成物品的显示字符串
	 * 
	 * @param vo
	 * @return
	 */
	public List<String> getSynthesizeChangeList(SynthesizeVO vo)
	{
		List<String> list = null;
		String operate = vo.getSynthesizeProp(); // 兑换的目的品
		String[] reChange = operate.split(";"); // 兑换的目的品的数组
		if (reChange == null)
		{
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		for (int i = 0; i < length; i++)
		{
			StringBuffer sb = new StringBuffer();
			String[] materials = reChange[i].split(","); // 每个兑换的兑换品可能不止一种

			for (int a = 0; a < materials.length; a++)
			{ // 对每个兑换品进行不同的处理
				String[] unit = materials[a].split("-");
				if (unit[0].equals("z"))
				{ // 如果兑换品是装备的处理情况
					int equip_id = Integer.valueOf(unit[1]);
					GameEquip equip = EquipCache.getById(equip_id);
					sb.append(equip.getName());
					// sb.append("×").append(unit[3]);
				}
				else
					if (unit[0].equals("d"))
					{ // 如果兑换品是道具的处理情况
						PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
						propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
						sb.append(propvo.getPropName());
						// sb.append("×").append(unit[3]);
					}
				if (a + 1 != materials.length)
				{
					sb.append("-");
				}
			}
			logger.info("i的value: " + i + " 兑换品装入字段" + sb.toString());
			list.add(sb.toString());
		}
		return list;
	}

	/** 获得合成类物品的名称 */
	public String getSynthesizePropName(SynthesizeVO vo)
	{
		String operate = vo.getSynthesizeProp(); // 兑换的目的品
		String[] materials = operate.split("-"); // 每个兑换的兑换品可能不止一种
		if (materials[0].equals("z"))
		{ // 如果兑换品是装备的处理情况
			int equip_id = Integer.valueOf(materials[1]);
			GameEquip equip = EquipCache.getById(equip_id);
			return equip.getName();
		}
		else
			if (materials[0].equals("d"))
			{ // 如果兑换品是道具的处理情况
				PropVO propvo = PropCache.getPropById(Integer.valueOf(materials[2]));
				propvo = PropCache.getPropById(Integer.valueOf(materials[2]));
				return propvo.getPropName();
			}
		return "";
	}

	// 检查玩家是否有足够的包裹以便容纳兑换过的物品
	public String getHasWareSpare(String pPk, SynthesizeVO vo, String address,
			int exchange_num)
	{
		int i = 1;
		String result = "";
		RoleEntity role_info = RoleService.getRoleInfoById(pPk);
		int warespare = role_info.getBasicInfo().getWrapSpare(); // 得到玩家包裹剩余空格数

		String[] articles = vo.getSynthesizeProp().split(";"); // 所有兑换品的字符串
		String article = articles[Integer.valueOf(address)]; // 所要兑换兑换品的字符串
		logger.info("兑换品的article:" + article);

		String[] recourses = article.split(",");
		for (int t = 0; t < recourses.length; t++)
		{

			String[] recourse = recourses[t].split("-");
			if (recourse[0].equals("z"))
			{
				// 身上包裹空间小于所能放入的空间时
				if (warespare < Integer.valueOf(recourse[3]) * exchange_num)
				{
					i = -1;
					result = "您身上的包裹空间不足！请先整理空间！";
				}
			}
			else
				if (recourse[0].equals("d"))
				{
					PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
					int accumulate = propvo.getPropAccumulate();
					if (accumulate == 1)
					{ // 对于道具不可叠加的情况
						if (warespare < Integer.valueOf(recourse[3])
								* exchange_num)
						{
							i = -1;
							result = "您身上的包裹空间不足！请先整理空间！";
						}
					}
					else
					{ // 对于道具可叠加的情况
						if (Integer.valueOf(recourse[3]) * exchange_num <= accumulate)
						{
							if (warespare < 1)
							{
								i = -1;
								result = "您身上的包裹空间不足！请先整理空间！";
							}
						}
						else
						{
							int requireNum = Integer.valueOf(recourse[3])
									* exchange_num / accumulate + 1;
							if (warespare < requireNum)
							{
								i = -1;
								result = "您身上的包裹空间不足！请先整理空间！";
							}
						}
					}
				}
		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(result);
		return sbf.toString();
	}

	/**
	 * 判断个人的原材料是否足够 下面对原材料是道具和是装备的情况 分别进行了判断
	 * 
	 * @param pPk
	 *            个人角色id
	 * @param address
	 *            所要兑换原材料在所有源材料的位置，从0开始计数
	 * @param menu_id
	 *            目录id
	 * @return 如果成功返回1，否则返回-1.
	 */
	public String getPPkHasGoods(String pPk, String address, SynthesizeVO vo,
			int exchange_num)
	{
		int i = 1;
		String sb = "";
		String[] articles = vo.getProp().split(";"); // 所有原材料的字符串

		String article = articles[Integer.valueOf(address)]; // 所要兑换原材料的字符串
		logger.info("原材料的article:" + article);

		String[] recourses = article.split(",");
		for (int t = 0; t < recourses.length; t++)
		{

			String[] recourse = recourses[t].split("-");

			if (recourse[0].equals("d"))
			{
				int number = Integer.valueOf(recourse[3]); // 原材料需要的个数
				number = exchange_num * number; // 兑换所需要的个数
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int current_num = propGroupDao.getPropNumByByPropID(Integer
						.valueOf(pPk), Integer.valueOf(recourse[2]));// 现有的数量
				if (current_num < number)
				{
					i = -1;
					sb = "您身上的原材料不足！";
				}
			}
			else
				if (recourse[0].equals("z"))
				{
					int number = Integer.valueOf(recourse[3]); // 原材料需要的个数
					number = exchange_num * number; // 兑换所需要的个数
					// int wrapClass =
					// storageService.getGoodsClass(Integer.valueOf(recourse[2]),Integer.valueOf(recourse[1]));
					// 查找装备
					// String wrapStr = recourse[2]+
					// ","+String.valueOf(wrapClass)+","+recourse[1];
					// logger.info("装备字符串:"+wrapStr);
					PlayerEquipDao equipDao = new PlayerEquipDao();
					int equip_num = equipDao.getEquipNumByEquipId(Integer
							.valueOf(pPk), Integer.valueOf(recourse[2]),
							Integer.valueOf(recourse[1]));
					if (equip_num < number)
					{
						i = -1;
						sb = "您身上的原材料不足！";
					}
				}
				else
					if (recourse[0].equals("j"))
					{
						PartInfoDao partInfoDao = new PartInfoDao();
						PartInfoVO partInfoVO = partInfoDao
								.getPartInfoByID(Integer.valueOf(pPk));
						long copper = Long.valueOf(partInfoVO.getPCopper());
						copper = exchange_num * copper; // 兑换所需要的金钱
						if (copper < Long.valueOf(recourse[1]))
						{
							i = -1;
							sb = "您身上金钱不足！";
						}
					}

		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(sb);
		return sbf.toString();
	}

	/**
	 * 兑换物品
	 */
	public String exchangeGoods(String pPk, SynthesizeVO vo, String address,
			int exchange_num)
	{
		StringBuffer resultWml = new StringBuffer();
		String[] articles = vo.getProp().split(";"); // 所有原材料的字符串
		String[] dharticles = vo.getSynthesizeProp().split(";"); // 所有兑换品的字符串

		String article = articles[Integer.valueOf(address)]; // 所要兑换原材料的字符串
		String dharticle = dharticles[Integer.valueOf(address)]; // 所要兑换兑换品的字符串

		getMaterialsFromWrap(pPk, article, exchange_num); // 从玩家身上中去掉兑换的原材料
		String getExchangeGoods = sendDuiHuanToWrap(pPk, dharticle,
				exchange_num); // 给玩家发放兑换品

		resultWml.append("生产已成功！");
		resultWml.append(getExchangeGoods);
		return resultWml.toString();
	}

	// 给玩家发放兑换品
	private String sendDuiHuanToWrap(String pPk, String dharticle,
			int exchange_num)
	{
		if (dharticle.equals("0"))
		{
			logger.info("不给玩家发放物品！");
			return "";
		}
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("您生产了");
		String[] articles = dharticle.split(",");
		for (int t = 0; t < articles.length; t++)
		{
			String[] unit = articles[t].split("-");
			if (unit[0].equals("d"))
			{ // 给玩家身上增加道具
				GoodsService goodsService = new GoodsService();
				goodsService.putGoodsToWrap(Integer.valueOf(pPk), Integer
						.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer
						.valueOf(unit[3])
						* exchange_num);
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.valueOf(unit[2]), 4, Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
				

				resultWml.append(goodsService.getGoodsName(Integer
						.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("×").append(
						Integer.valueOf(unit[3]) * exchange_num);
			}
			else
				if (unit[0].equals("z"))
				{ // 给玩家身上增加装备
					GoodsService goodsService = new GoodsService();
					for (int i = 0; i < Integer.valueOf(unit[3]); i++)
					{ // 要给几个装备就给几次
						goodsService.putGoodsToWrap(Integer.valueOf(pPk),
								Integer.valueOf(unit[2]), Integer
										.valueOf(unit[1]), Integer
										.valueOf(unit[3])
										* exchange_num);
						//执行统计
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
						
						
					}

					resultWml.append(goodsService.getGoodsName(Integer
							.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("×").append(
							Integer.valueOf(unit[3]) * exchange_num);
				}
			if (t + 1 < articles.length)
				resultWml.append(",");
		}
		resultWml.append(".");
		return resultWml.toString();
	}

	// 从玩家身上中去掉兑换的原材料
	private String getMaterialsFromWrap(String pPk, String article,
			int exchange_num)
	{
		if (article.equals("0"))
		{
			logger.info("不从玩家身上扣除如何物品！");
			return "";
		}
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("您失去了");
		String[] articles = article.split(",");
		for (int t = 0; t < articles.length; t++)
		{
			String[] unit = articles[t].split("-");
			if (unit[0].equals("d"))
			{ // 从玩家身上减去道具
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(Integer.valueOf(pPk), Integer
						.valueOf(unit[2]), Integer.valueOf(unit[3])
						* exchange_num,GameLogManager.R_EXCHANGE);
				resultWml.append(goodsService.getGoodsName(Integer
						.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("×").append(
						Integer.valueOf(unit[3]) * exchange_num);
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3]) * exchange_num, StatisticsType.USED, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
				
			}
			else
				if (unit[0].equals("z"))
				{ // 从玩家身上减去装备

					GoodsService goodsService = new GoodsService();
					for (int a = 0; a < Integer.valueOf(unit[2])*exchange_num; a++)
					{
						goodsService.removeEquipByEquipID(Integer.valueOf(pPk), Integer.valueOf(unit[1]));
					}
					resultWml.append(goodsService.getGoodsName(Integer
							.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("×").append(
							Integer.valueOf(unit[3]) * exchange_num);
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3]) * exchange_num, StatisticsType.USED, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
					
				}
				else
					if (unit[0].equals("j"))
					{
						RoleService roleService = new RoleService();
						RoleEntity roleInfo = roleService.getRoleInfoById(pPk);
						
						//监控
						LogService logService = new LogService();
						logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", "-"+unit[1], "生产东西");
						
						// 从玩家身上减去金钱
						roleInfo.getBasicInfo().addCopper(-Integer.valueOf(unit[1]));
						resultWml.append(MoneyUtil.changeCopperToStr(Integer
								.valueOf(unit[1])
								* exchange_num));
						//执行统计
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(6, StatisticsType.MONEY, Integer.parseInt(unit[1]), StatisticsType.USED, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
						
					}
			if (t + 1 < articles.length)
				resultWml.append(",");
		}
		resultWml.append(",");
		return resultWml.toString();
	}

	/** 得到玩家身上原材料的数量 */
	public List<String> getPlayerPropList(int p_pk, SynthesizeVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getProp(); // 需要兑换的原材料
		// String operate2 = vo.getSynthesizeProp(); //兑换的目的品
		String[] reChange = operate1.split(";"); // 需要兑换的原材料的数组
		// String[] reChange2 = operate2.split(";"); //兑换的目的品的数组
		if (reChange == null)
		{
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;

		for (int i = 0; i < length; i++)
		{
			String[] materials = reChange[i].split(","); // 每个兑换的原材料可能不止一种
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for (int a = 0; a < materials.length; a++)
			{
				String[] unit = materials[a].split("-");
				if (unit[0].equals("d"))
				{ // 如果原材料是道具的处理情况
					PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
					PlayerPropGroupDao dao = new PlayerPropGroupDao();
					int num = dao.getPropNumByByPropID(p_pk, Integer
							.valueOf(unit[2]));
					sb.append("-");

					sb.append(propvo.getPropName());
					sb.append("×").append(num);
				}
				else
					if (unit[0].equals("z"))
					{ // 如果原材料是装备的处理情况
						sb.append("-");
						
						int equip_id = Integer.valueOf(materials[1]);
						GameEquip equip = EquipCache.getById(equip_id);
						
						PlayerEquipDao dao = new PlayerEquipDao();
						int num = dao.getEquipNum(p_pk, equip_id);
						
						sb.append(equip.getName());
						sb.append("×").append(num);
					}
					else
						if (unit[0].equals("j"))
						{ // 如果原材料是金钱的处理情况
							PartInfoDao partInfoDao = new PartInfoDao();
							PartInfoVO partInfoVO = partInfoDao
									.getPartInfoByID(p_pk);
							Long copper = Long.valueOf(partInfoVO
									.getPCopper());
							sb.append("-").append("银两").append(
									MoneyUtil.changeCopperToStr(copper));

						}
			}
			logger.info("i的value: " + i + " 原材料装入字段" + sb.toString());
			list.add(sb.toString());
		}
		return list;
	}

	/** 得到玩家身上原材料的数量 */
	public int getNum(int p_pk, SynthesizeVO vo)
	{
		List<Integer> list = null;
		String operate1 = vo.getProp(); // 需要兑换的原材料
		// String operate2 = vo.getSynthesizeProp(); //兑换的目的品
		String[] reChange = operate1.split(";"); // 需要兑换的原材料的数组
		// String[] reChange2 = operate2.split(";"); //兑换的目的品的数组
		if (reChange == null)
		{
			return 0;
		}
		list = new ArrayList<Integer>();
		int length = reChange.length;
		for (int i = 0; i < length; i++)
		{
			String[] materials = reChange[i].split(","); // 每个兑换的原材料可能不止一种
			StringBuffer sb = new StringBuffer();
			for (int a = 0; a < materials.length; a++)
			{
				String[] unit = materials[a].split("-");
				if (unit[0].equals("d"))
				{ // 如果原材料是道具的处理情况
					PlayerPropGroupDao dao = new PlayerPropGroupDao();
					int num = dao.getPropNumByByPropID(p_pk, Integer
							.valueOf(unit[2]));
					int all = Integer.valueOf(num / Integer.valueOf(unit[3]));
					list.add(all);
				}
				else
					if (unit[0].equals("z"))
					{ // 如果原材料是装备的处理情况
						sb.append("-");
						
						int equip_id = Integer.valueOf(materials[1]);
						int need_num = Integer.valueOf(unit[2]);
						
						PlayerEquipDao dao = new PlayerEquipDao();
						int have_num = dao.getEquipNum(p_pk, equip_id);
						
						list.add(have_num/need_num);
					}
					else
						if (unit[0].equals("j"))
						{ // 如果原材料是金钱的处理情况
							PartInfoDao partInfoDao = new PartInfoDao();
							PartInfoVO partInfoVO = partInfoDao
									.getPartInfoByID(p_pk);
							long copper = Long.valueOf(partInfoVO
									.getPCopper());
							int all = Integer.valueOf((int) (copper
									/ Integer.valueOf(unit[1])));
							list.add(all);

						}
			}
		}
		Collections.sort(list);
		return list.get(0);
	}

}
