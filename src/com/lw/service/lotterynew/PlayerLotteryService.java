package com.lw.service.lotterynew;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.rank.RankService;
import com.lw.dao.lotterynew.LotteryDAO;
import com.lw.dao.lotterynew.PlayerLotteryDAO;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.lotterynew.LotteryOutPrintVO;
import com.lw.vo.lotterynew.LotteryVO;
import com.lw.vo.lotterynew.PlayerLotteryVO;

public class PlayerLotteryService
{
	Logger logger = Logger.getLogger(PlayerLotteryService.class);

	/** ��ҹ����Ʊ* */
	private void playerBuyLottery(PlayerLotteryVO vo)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		dao.insertPlayerLotteryInfo(vo);
	}

	/** *�õ���ҵ��н��ȼ� */
	public int playerCatchLotteyLevel(String lottery_num, String sys_lottery)
	{
		int x = 0;
		if (sys_lottery == null)
		{
			return x;
		}
		String[] lottery_num_temp = lottery_num.split(",");
		String[] sys_lottery_temp = sys_lottery.split(",");
		if (lottery_num_temp[0].equals(sys_lottery_temp[0]))
		{
			x = x + 1;
		}
		if (lottery_num_temp[1].equals(sys_lottery_temp[1]))
		{
			x = x + 1;
		}
		if (lottery_num_temp[2].equals(sys_lottery_temp[2]))
		{
			x = x + 1;
		}
		if (lottery_num_temp[3].equals(sys_lottery_temp[3]))
		{
			x = x + 1;
		}
		return x;
	}

	// �Ƿ���Բμ�Ͷע
	public boolean playerCanGetLottery(int p_pk)
	{
		LotteryService ls = new LotteryService();
		String lottery_date = ls.getSysLotteryDate();
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		PlayerLotteryVO vo_temp = dao.getPlayerLotteryInfo(p_pk, lottery_date);
		if (vo_temp == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/** �����Ʊ���� */
	public String buyLottery(RoleEntity role_info, String lottery_num,
			int lottery_zhu)
	{
		LotteryService ls = new LotteryService();
		PlayerLotteryVO vo = new PlayerLotteryVO();
		String lottery_date = ls.getSysLotteryDate();
		if (lottery_zhu < 0)
		{
			lottery_zhu = 2;
		}
		if (lottery_zhu > 999)
		{
			lottery_zhu = 999;
		}
		EconomyService economyService = new EconomyService();
		long user_yb = economyService.getYuanbao(role_info.getBasicInfo()
				.getUPk());
		int yb = 50 * lottery_zhu;
		if (yb > user_yb)
		{
			return "����"+GameConfig.getYuanbaoName()+"����!";
		}
		else
		{
			LotteryDAO lotteryDAO = new LotteryDAO();
			LotteryVO lotteryVO = ls.selectLotteryInfoByDate(lottery_date);
			// �õ��н��ȼ�
			int lv = playerCatchLotteyLevel(lottery_num, lotteryVO
					.getLottery_content());
			vo.setP_pk(role_info.getBasicInfo().getPPk());
			vo.setLottery_date(lottery_date);
			vo.setLottery_content(lottery_num);
			vo.setLottery_zhu(lottery_zhu);
			vo.setLottery_bonus_lv(lv);
			if (lv == 3)
			{
				vo.setLottery_bonus(5000);
			}
			else
				if (lv == 2)
				{
					vo.setLottery_bonus(100);
				}
				else
				{
					vo.setLottery_bonus(0);
				}
			// ���ɲ�Ʊ
			playerBuyLottery(vo);
			// �۳�Ԫ��
			economyService.spendYuanbao(role_info.getBasicInfo().getUPk(), yb);
			// ��������н�������
			String catch_player = ls.changePlayer(lv, lotteryVO
					.getLottery_catch_player(), lottery_zhu);
			lotteryDAO.updateLotteryAllybAndPlayer(lottery_date, yb,
					catch_player);

			// �õ�����ʱ��
			Date date = new Date();
			int hour_temp = date.getHours();
			int hour = 0;
			if (hour_temp != 23)
			{
				hour = hour_temp + 1;
			}
			String lottery_num_out = ls.getLotteryPerInfo(lottery_num);
			return "������Ͷע:" + lottery_num_out + ",��ֵ" + yb + ""+GameConfig.getYuanbaoName()+"!���ھ���" + hour
					+ "�㿪�������ڿ������24Сʱ����ȡ����!";
		}
	}

	/** �����ȡ����* */
	public String playerCatchLotteryBonus(RoleEntity role_info,
			String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		LotteryDAO lotteryDAO = new LotteryDAO();
		LotteryService ls = new LotteryService();
		EconomyService economyService = new EconomyService();
		LotteryVO sys_vo = ls.getSysLotteryInfo();
		PlayerLotteryVO vo = dao.getPlayerLotteryInfo(role_info.getBasicInfo()
				.getPPk(), lottery_date);

		if (vo == null)
		{
			return "��û�й���" + lottery_date + "�ڲ�Ʊ!";
		}
		else
		{
			if (vo.getIs_have() == 0)
			{
				long get_yb = Long.parseLong(vo.getLottery_bonus()
						* vo.getLottery_zhu() + "")
						* (1000 - sys_vo.getSys_lottery_tax()) / 1000;
				String bonus_sys = "";
				if (vo.getLottery_bonus_lv() == 4)
				{
					// ����ͷ���Ľ���
					if (sys_vo.getSys_lottery_bonus() != null)
					{
						int bonusNum = getLaborageBonusNum(sys_vo
								.getSys_lottery_bonus());

						int wrapSpare = role_info.getBasicInfo().getWrapSpare();
						if (wrapSpare == 0 || wrapSpare < 0
								|| wrapSpare < bonusNum)
						{
							return "����������,���������!";
						}
						else
						{
							String[] bonusprop = sys_vo.getSys_lottery_bonus()
									.split(",");
							for (int i = 0; i < bonusprop.length; i++)
							{
								String[] prop = bonusprop[i].split("-");
								if (prop[0].equals("0"))
								{
									int laboragemoney = Integer
											.parseInt(prop[2]);

									// ���
									LogService logService = new LogService();
									logService.recordMoneyLog(role_info
											.getBasicInfo().getPPk(), role_info
											.getBasicInfo().getName(),
											role_info.getBasicInfo()
													.getCopper()
													+ "", laboragemoney + "",
											"��Ʊ�õ�");

									role_info.getBasicInfo().addCopper(
											laboragemoney);// �����ȡ����
									// ִ��ͳ��
									GameSystemStatisticsService gsss = new GameSystemStatisticsService();
									gsss.addPropNum(6, StatisticsType.MONEY,
											laboragemoney,
											StatisticsType.DEDAO,
											StatisticsType.XITONG, role_info
													.getBasicInfo().getPPk());
								}
								else
									if (prop[0].equals("5"))
									{
										int experience = Integer
												.parseInt(prop[2]);

										// ���
										LogService logService = new LogService();
										logService.recordExpLog(role_info
												.getBasicInfo().getPPk(),
												role_info.getBasicInfo()
														.getName(), role_info
														.getBasicInfo()
														.getCurExp(),
												experience + "", "��Ʊ�õ�");

										role_info.getBasicInfo()
												.updateAddCurExp(experience);
									}
									else
									{
										GoodsService goodsService = new GoodsService();
										goodsService.putGoodsToWrap(role_info
												.getBasicInfo().getPPk(),
												Integer.parseInt(prop[1]),
												Integer.parseInt(prop[0]), 0,
												Integer.parseInt(prop[2]),GameLogManager.G_SYSTEM);// ��һ��ϵͳ�����ĵ���
										// ִ��ͳ��
										GameSystemStatisticsService gsss = new GameSystemStatisticsService();
										gsss.addPropNum(Integer
												.parseInt(prop[1]), Integer
												.parseInt(prop[0]), Integer
												.parseInt(prop[2]),
												StatisticsType.DEDAO,
												StatisticsType.XITONG,
												role_info.getBasicInfo()
														.getPPk());
									}
							}
						}
						bonus_sys = getLaborageView(sys_vo
								.getSys_lottery_bonus());
					}

					economyService.addYuanbao(
							role_info.getBasicInfo().getUPk(), get_yb);
					dao.updatePlayerCatchBonus(role_info.getBasicInfo()
							.getPPk(), lottery_date);
					lotteryDAO.updateLotteryCatchyb(lottery_date, vo
							.getLottery_bonus()
							* vo.getLottery_zhu());
					// ��¼�����ȡ����������
					RankService rankService = new RankService();
					rankService.updateAdd(role_info.getBasicInfo().getPPk(),
							"boyi", get_yb);
					return "����ȡ�˵�" + lottery_date + "���н�����:"+GameConfig.getYuanbaoName()+"" + get_yb + " "
							+ bonus_sys;
				}
				else
					if (vo.getLottery_bonus_lv() < 2)
					{
						return "���������" + lottery_date + "�ڲ�Ʊû���н�,���´μ���Ŭ��!";
					}
					else
					{
						economyService.addYuanbao(role_info.getBasicInfo()
								.getUPk(), get_yb);
						dao.updatePlayerCatchBonus(role_info.getBasicInfo()
								.getPPk(), lottery_date);
						lotteryDAO.updateLotteryCatchyb(lottery_date, vo
								.getLottery_bonus()
								* vo.getLottery_zhu());
						// ��¼�����ȡ����������
						RankService rankService = new RankService();
						rankService.updateAdd(
								role_info.getBasicInfo().getPPk(), "boyi",
								get_yb);
						return "����ȡ�˵�" + lottery_date + "���н�����:"+GameConfig.getYuanbaoName()+"" + get_yb;
					}
			}
			else
			{
				return "������ȡ������";
			}
		}
	}

	// �õ�ͷ������
	public long getLotteryFristBonus(String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getLotteryFristBonus(lottery_date);
	}

	// �õ���Ͷע����
	public long getLotteryAllZhu(String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getLotteryAllZhu(lottery_date);
	}

	// �õ���Ҳ�Ʊ��ʷ��¼
	public List<LotteryOutPrintVO> getPlayerLotteryHistory(int p_pk, int page,
			int perpage)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		List<LotteryOutPrintVO> list = dao.getPlayerLotteryHistory(p_pk, page,
				perpage);
		return list;
	}

	// �õ���Ҳ�Ʊ��Ͷע����
	public int getPlayerLotteryAllNum(int p_pk)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getPlayerLotteryAllNum(p_pk);
	}

	/** ��������ж�����Ʒ */
	public int getLaborageBonusNum(String bonus)
	{
		int num = 0;
		if (bonus == null)
		{
			return num;
		}
		String[] bonusprop = bonus.split(",");
		for (int i = 0; i < bonusprop.length; i++)
		{
			String[] prop = bonusprop[i].split("-");
			if (prop[0].equals("1"))
			{
				num = num + 1;
			}
			else
				if (prop[0].equals("2"))
				{
					num = num + 1;
				}
				else
					if (prop[0].equals("3"))
					{
						num = num + 1;
					}
					else
						if (prop[0].equals("4"))
						{
							PropCache pc = new PropCache();
							int prop_num_ac = pc.getPropAccumulate(Integer
									.parseInt(prop[1]));
							num = num
									+ (Integer.parseInt(prop[2]) / prop_num_ac + (Integer
											.parseInt(prop[2])
											% prop_num_ac == 0 ? 0 : 1));
						}
		}
		return num;
	}

	// �õ�������Ʒ����ʾ
	public String getLaborageView(String bonus)
	{
		if (bonus == null)
		{
			return "��";
		}
		StringBuffer sb = new StringBuffer();
		String[] bonusprop = bonus.split(",");
		for (int i = 0; i < bonusprop.length; i++)
		{
			String[] prop = bonusprop[i].split("-");
			if (prop[0].equals("1"))
			{
				int equip_id = Integer.parseInt(prop[1]);
				GameEquip equip = EquipCache.getById(equip_id);
				sb.append(equip.getName() + "��" + prop[2]);
			}
			else if (prop[0].equals("4"))
			{
				int prop_id = Integer.parseInt(prop[1]);
				PropVO pvo =  PropCache.getPropById(prop_id);
				sb.append(pvo.getPropName() + "��" + prop[2]);
			}
			else if (prop[0].equals("5"))
			{
				sb.append("����  " + prop[2]);
			}
			else
			{
				sb.append("����"+ MoneyUtil.changeCopperToStr(prop[2]));
			}
			if (i != bonusprop.length - 1)
			{
				sb.append(",");
			}
		}
		return sb.toString();
	}

	// Ͷע����
	public int getLotteryTimeToday(int p_pk, String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getPlayerLotteryNumToday(p_pk, lottery_date, 0);
	}

	// �н�����
	public int getLotteryBonusTimeToday(int p_pk, String lottery_date)
	{
		PlayerLotteryDAO dao = new PlayerLotteryDAO();
		return dao.getPlayerLotteryNumToday(p_pk, lottery_date, 1);
	}
}
