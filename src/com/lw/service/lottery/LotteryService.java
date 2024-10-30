package com.lw.service.lottery;

import java.util.Date;
import java.util.List;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.lottery.LotteryInfoDao;
import com.lw.dao.lottery.LotteryNumberDao;
import com.lw.dao.lottery.PlayerLotteryInfoDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.lottery.LotteryInfoVO;
import com.lw.vo.lottery.LotteryNumberVO;
import com.lw.vo.lottery.PlayerLotteryInfoVO;
import com.pm.service.mail.MailInfoService;

public class LotteryService
{
	/** �õ������н���Ʊ���� */
	public void getSysLotteryNum()
	{
		LotteryInfoDao dao = new LotteryInfoDao();
		int lotteryNum1 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum2 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum3 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum4 = MathUtil.getRandomBetweenXY(1, 3);
		String num = Num1(lotteryNum1) + "," + Num2(lotteryNum2) + ","
				+ Num3(lotteryNum3) + "," + Num4(lotteryNum4);
		dao.updateLotteryNumber(StringUtil.gbToISO(num));
	}

	/** �õ���Ʊ���ַ� */
	public String Num1(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "����";
		}
		else
			if (x == 2)
			{
				a = "�Ϸ�";
			}
			else
				if (x == 3)
				{
					a = "����";
				}
				else
					if (x == 4)
					{
						a = "����";
					}
		return a;
	}

	public String Num2(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "÷";
		}
		else
			if (x == 2)
			{
				a = "��";
			}
			else
				if (x == 3)
				{
					a = "��";
				}
				else
					if (x == 4)
					{
						a = "��";
					}
		return a;
	}

	public String Num3(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "��";
		}
		else
			if (x == 2)
			{
				a = "��";
			}
			else
				if (x == 3)
				{
					a = "��";
				}
				else
					if (x == 4)
					{
						a = "��";
					}
		return a;
	}

	public String Num4(int x)
	{
		String a = null;
		if (x == 1)
		{
			a = "����";
		}
		else
			if (x == 2)
			{
				a = "�װ�";
			}
			else
				if (x == 3)
				{
					a = "����";
				}
		return a;
	}

	/** �õ����ɴ����н���Ʊ���� */
	public void getSysLotteryCharityNum()
	{
		LotteryInfoDao dao = new LotteryInfoDao();
		int lotteryNum1 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum2 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum3 = MathUtil.getRandomBetweenXY(1, 4);
		int lotteryNum4 = MathUtil.getRandomBetweenXY(1, 3);
		String num = Num1(lotteryNum1) + "," + Num2(lotteryNum2) + ","
				+ Num3(lotteryNum3) + "," + Num4(lotteryNum4);
		dao.updateLotteryCharityNumber(StringUtil.gbToISO(num));
	}

	/** ����������Ҳ�Ʊ */
	public void playerLotteryNumber(int p_pk, String num_1, String num_2,
			String num_3, String num_4, int lottery_type, int p_add_money)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		LotteryNumberDao dao = new LotteryNumberDao();
		PlayerLotteryInfoDao infodao = new PlayerLotteryInfoDao();
		String lottery_number = num_1 + "," + num_2 + "," + num_3 + "," + num_4;
		dao.insertPlayerLottery(p_pk, StringUtil.gbToISO(lottery_number),
				lottery_type, p_add_money);
		infodao.addPlayerLotteryNum(p_pk);
		if (lottery_type == 0)
		{
			//���
			LogService logService = new LogService();
			logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -p_add_money+"", "���Ʊ");
			
			roleInfo.getBasicInfo().addCopper(-p_add_money);
			// ִ��ͳ��
			GameSystemStatisticsService gsss = new GameSystemStatisticsService();
			gsss.addPropNum(6, StatisticsType.MONEY, p_add_money,
					StatisticsType.USED, StatisticsType.BUY, p_pk);

		}
	}

	/** �н��� ���������Ӵ��ƽ����� */
	public void addSysCharityBonus(int num)
	{
		LotteryInfoDao infodao = new LotteryInfoDao();
		infodao.updateCharityBonus(num);
	}

	/** ���н������õ���ע�������н���� */

	public int getLotteryPerBonus()
	{
		LotteryInfoDao infodao = new LotteryInfoDao();
		LotteryNumberDao dao = new LotteryNumberDao();
		int num = dao.getLotteryPer(infodao.getLotteryNumberEveryday());
		LotteryInfoVO vo = infodao.getLotteryInfo();
		if (num > 0)
		{
			if (vo.getSysBonusType() == 0)
			{
				int bonus = vo.getLotteryBonus() + vo.getSysBonusNum();

				int playerbonus = (bonus / num);
				return playerbonus;
			}
			else
			{
				int bonus = vo.getLotteryBonus();
				int playerbonus = (bonus / num);
				return playerbonus;
			}
		}
		return 0;
	}

	/** �ж�����Ƿ�Ͷע */
	public boolean ifPlayerGetLottery(int p_pk, int lottery_type)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		if (dao.getLotteryNumber(p_pk, lottery_type) == null)
		{
			return true;
		}
		return false;
	}

	/**
	 * ����PPK�ж�����Ƿ��н�
	 * 
	 * @return 1Ϊ�н� 2Ϊ���н� 3Ϊû��Ͷע
	 */
	public int ifPlayerCatch(int p_pk)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		LotteryInfoDao infodao = new LotteryInfoDao();
		if (dao.getLotteryNumber(p_pk, 0) == null)
		{
			return 3;
		}
		else
		{
			if (dao.getLotteryNumber(p_pk, 0).equals(
					infodao.getLotteryNumberEveryday()))
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}
	}

	/**
	 * ����PPK�ж�����Ƿ��д��ƽ�
	 * 
	 * @return 1Ϊ�н� 2Ϊ���н� 3Ϊû��Ͷע
	 */
	public int ifPlayerCatchCharity(int p_pk)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		LotteryInfoDao infodao = new LotteryInfoDao();
		if (dao.getLotteryNumber(p_pk, 1) == null)
		{
			return 3;
		}
		else
		{
			if (dao.getLotteryNumber(p_pk, 1).equals(
					infodao.getLotteryCharityNumber()))
			{
				return 1;
			}
			else
			{
				return 2;
			}
		}
	}

	/** �õ���һ�õ����� */
	public int playerHaveMoney(int p_pk)
	{
		LotteryNumberDao numdao = new LotteryNumberDao();
		LotteryNumberVO numvo = numdao.getPlayerLottery();
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = playerdao.getLotteryInfoByPpk(p_pk);
		int money = vo.getLotteryPerBonus() * vo.getLotteryBonusMultiple()
				* numvo.getPlayerAddMoney();
		return money;
	}

	/** ��һ�ȡ���ƽ���Ľ�� */
	public int playerHaveCharityMoney(int p_pk)
	{
		LotteryInfoDao lotterydao = new LotteryInfoDao();
		LotteryInfoVO infovo = lotterydao.getLotteryInfo();
		LotteryNumberDao dao = new LotteryNumberDao();
		return infovo.getSysCharityBonus()
				/ dao.getLotteryNumber(infovo.getLotteryCharityNum(), 1);
	}

	/** �����ȡ���ƽ������ */
	public void playerCatchCharityMoney(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		LotteryInfoDao dao = new LotteryInfoDao();

		//���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", playerHaveCharityMoney(p_pk)+"", "���ƻ�");
		
		roleInfo.getBasicInfo().addCopper(playerHaveCharityMoney(p_pk));

		dao.delCharityBonus(playerHaveCharityMoney(p_pk));
	}

	/** ����н���ȡ��������� */
	public void playerCatchMoney(int p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		LotteryNumberDao numdao = new LotteryNumberDao();
		LotteryNumberVO numvo = numdao.getPlayerLottery();
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = playerdao.getLotteryInfoByPpk(p_pk);
		LotteryInfoDao lotterydao = new LotteryInfoDao();
		LotteryInfoVO infovo = lotterydao.getLotteryInfo();
		int lotterymoney = vo.getLotteryPerBonus() * numvo.getPlayerAddMoney();// ����н�����ܽ���
		int playgetmoney = lotterymoney * 85 / 100;// ��ҵõ���Ǯ
		int shuijin = lotterymoney - playgetmoney;// ˰��
		int charitymoney = shuijin / 5;// ���ƽ���

		// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, shuijin, StatisticsType.USED,
				StatisticsType.SHUISHOU, p_pk);

		if (infovo.getSysBonusType() == 0)
		{
			addSysCharityBonus(charitymoney);// ����콱 ˰���һ���ֹ�����ƽ���
			lotterydao.delLotteryBonus(lotterymoney);// ���ؽ���ݼ�
			lotterydao.updateLotterySunjoin(lotterymoney
					* (vo.getLotteryBonusMultiple() - 1));// ͳ��ϵͳ�����Ľ���

			//���
			LogService logService = new LogService();
			logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", lotterymoney * vo.getLotteryBonusMultiple()+"", "��Ʊ��");
			
			lotterymoney = lotterymoney + infovo.getSysBonusNum();
			roleInfo.getBasicInfo().addCopper(
					lotterymoney * vo.getLotteryBonusMultiple());// �����ȡ����
			// ִ��ͳ��
			gsss.addPropNum(6, StatisticsType.MONEY, lotterymoney
					* vo.getLotteryBonusMultiple(), StatisticsType.DEDAO,
					StatisticsType.BOCAI, p_pk);

			playerdao.updatePlayerCatch(p_pk);// ���������ȡ������
			playerdao.updatePlayerAllBonus(lotterymoney
					* vo.getLotteryBonusMultiple(), p_pk);// ������ۼӽ���
			playerdao.addPlayerWinNum(p_pk);// ����ҵĻ񽱱����ͻ񽱴���+1
		}
		else
		{
			addSysCharityBonus(charitymoney);// ����콱 ˰���һ���ֹ�����ƽ���
			lotterydao.delLotteryBonus(lotterymoney);// ���ؽ���ݼ�
			lotterydao.updateLotterySunjoin(lotterymoney
					* (vo.getLotteryBonusMultiple() - 1)
					* numvo.getPlayerAddMoney());// ͳ��ϵͳ�����Ľ���

			//���
			LogService logService = new LogService();
			logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", lotterymoney * vo.getLotteryBonusMultiple()+"", "��Ʊ��");
			
			roleInfo.getBasicInfo().addCopper(
					lotterymoney * vo.getLotteryBonusMultiple());// �����ȡ����

			// ִ��ͳ��
			gsss.addPropNum(6, StatisticsType.MONEY, lotterymoney
					* vo.getLotteryBonusMultiple(), StatisticsType.DEDAO,
					StatisticsType.BOCAI, p_pk);

			playerdao.updatePlayerCatch(p_pk);// ���������ȡ������
			playerdao.updatePlayerAllBonus(lotterymoney
					* vo.getLotteryBonusMultiple(), p_pk);// ������ۼӽ���
			GoodsService goodsService = new GoodsService();
			goodsService.putGoodsToWrap(p_pk, infovo.getSysBonusId(), infovo
					.getSysBonusType(), infovo.getSysBonusIntro(), infovo
					.getSysBonusNum(),GameLogManager.G_SYSTEM);// ��һ��ϵͳ�����ĵ���
			// ִ��ͳ��
			gsss.addPropNum(infovo.getSysBonusId(), infovo.getSysBonusType(),
					infovo.getSysBonusNum(), StatisticsType.DEDAO,
					StatisticsType.BOCAI, p_pk);

			playerdao.addPlayerWinNum(p_pk);// ����ҵĻ񽱱����ͻ񽱴���+1
		}
	}

	/** ��ҵ�����µ�ͬʱ���������Ϣ */
	public void buildPlayerLottery(int p_pk)
	{
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		if (playerGuess(p_pk) == true)
			playerdao.setPlayerLotteryMessage(p_pk);
	}

	/** �ж�����Ƿ�Ϊ��һ�ξ��� ��������Ҳ�Ʊ��Ϣ */
	public boolean playerGuess(int p_pk)
	{
		PlayerLotteryInfoDao playerdao = new PlayerLotteryInfoDao();
		if (playerdao.getLotteryInfoByPpk(p_pk) == null)
		{
			return true;
		}
		return false;
	}

	/** �ж�����Ƿ񾺲¹� trueΪ���¹� falseΪû���¹� */
	public boolean playerGuessLottery(int p_pk, int lottery_type)
	{
		LotteryNumberDao dao = new LotteryNumberDao();
		if (dao.getLotteryNumber(p_pk, lottery_type) == null)
		{
			return false;
		}
		return true;
	}

	/** ϵͳ�Զ����ɲ�Ʊ ���㵥ע���� ������Ҳ�Ʊ��Ϣ�� ��� ��ע������ */
	public void sysLottery()
	{
		Date date = new Date();
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		LotteryInfoDao ldao = new LotteryInfoDao();
		LotteryInfoVO vo = ldao.getLotteryInfo();
		if (date.getDate() == 1)
		{
			getSysLotteryCharityNum();
			getSysLotteryNum();
			dao.setPerMoney(getLotteryPerBonus());
		}
		else
		{
			getSysLotteryNum();
			dao.setPerMoney(getLotteryPerBonus());
		}
		LotteryNumberDao ndao = new LotteryNumberDao();
		int x = ndao.getLotteryNumber(vo.getLotteryNumberPerDay(), 0);
		ldao.updateLotteryWinNumber(x);

	}

	/** ϵͳÿ��3�� ������Ҳ�Ʊ��Ϣ������Ϣ ͬʱɾ����Ʊ�� ����ɾ��ϵͳ��Ʊ���� ɾ�����Ʋ�Ʊ���� */
	public void sysDel()
	{
		Date date = new Date();
		LotteryNumberDao numdao = new LotteryNumberDao();
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		if (date.getDate() == 2)
		{
			// dao.delPlayerMessage();
			numdao.delAll();
			dao.updatePlayerCatchBySys();
			dao.delPlayerMessageByMonth();
		}
		else
		{
			// dao.delPlayerMessage();
			numdao.delAll();
			dao.updatePlayerCatchBySys();
		}
	}

	/** �ж�����Ƿ��вμӴ��ƾ��µ��ʸ� */
	public boolean getPlayerCharityLottery(int p_pk)
	{
		PlayerLotteryInfoDao dao = new PlayerLotteryInfoDao();
		PlayerLotteryInfoVO vo = dao.getLotteryInfoByPpk(p_pk);
		if (vo.getLotteryNum() >= 15 && vo.getLotteryWinNum() == 0)
		{
			return true;
		}
		return false;
	}

	/** ϵͳ��Ϣ */
	public String sysLotteryMessage()
	{
		Date date = new Date();
		LotteryInfoDao dao = new LotteryInfoDao();
		LotteryInfoVO vo = dao.getLotteryInfo();
		String num = vo.getLotteryNumberPerDay();
		String num1 = vo.getLotteryCharityNum();
		if (date.getDate() == 1)
		{

			return "�н�����Ϊ" + num + " �����н�����Ϊ" + num1;
		}
		else
		{
			return "�н�����Ϊ" + num;
		}
	}

	/** ����ϵͳ�ʼ������ */
	public void setSysLotteryMail()
	{
		LotteryNumberDao ndao = new LotteryNumberDao();
		LotteryInfoDao dao = new LotteryInfoDao();
		MailInfoService ms = new MailInfoService();
		LotteryInfoVO vo = dao.getLotteryInfo();
		String num = vo.getLotteryNumberPerDay();// �н�����
		int x = ndao.getLotteryNumber(num, 0);// �н�����
		String today = DateUtil.getTodayStr().replaceAll("-", "");// ��������
		List<Integer> winplayer = ndao.getWinPlayer(num, 0);// �н���
		List<Integer> notwinplayer = ndao.getNotWinPlayer(num, 0);// û�н���
		List<Integer> allplayer = ndao.getAllPlayer(0);// ���Ʊ����
		String title = "��" + today + "�ڲ��ʿ���";
		if (allplayer != null && allplayer.size() != 0)
		{
			if (x == 0)
			{
				for (int i = 0; i < allplayer.size(); i++)
				{
					int ppk = allplayer.get(i);
					String playerlotterynum = ndao.getLotteryNumber(ppk, 0);
					String allBonus = MoneyUtil.changeCopperToStr(vo
							.getLotteryBonus());
					String contentallnotwin = "��" + today + "�ڲ��ʿ������н�����Ϊ" + num
							+ "����Ͷע��" + playerlotterynum + "����û���н������־��������н�������"
							+ allBonus + "��������һ�֣�ϣ����һ���н����˾�������";
					ms.sendMailBySystem(ppk, title, contentallnotwin);
				}
			}
			else
			{
				if (winplayer != null && winplayer.size() != 0)
				{
					for (int i = 0; i < winplayer.size(); i++)
					{
						int ppk = winplayer.get(i);
						String contentwin = "��" + today + "�ڲ��ʿ�������ϲ��Ͷע��" + num
								+ "�����н��ˣ����ڵ���ǰ�����´󺺴��콱��";
						ms.sendMailBySystem(ppk, title, contentwin);
					}
				}
				if (notwinplayer != null && notwinplayer.size() != 0)
				{
					for (int i = 0; i < notwinplayer.size(); i++)
					{
						int ppk = notwinplayer.get(i);
						String playerlotterynum = ndao.getLotteryNumber(ppk, 0);
						String contentnotwin = "��" + today + "�ڲ��ʿ������н�����Ϊ"
								+ num + "����Ͷע��" + playerlotterynum
								+ "����û���н���ϣ����һ���н����˾�������";
						ms.sendMailBySystem(ppk, title, contentnotwin);
					}
				}
			}
		}
	}

	/** ϵͳ׷�ӽ�������ʾ */
	public String sysOutLotteryBonus()
	{
		LotteryInfoDao ldao = new LotteryInfoDao();
		LotteryInfoVO vo = ldao.getLotteryInfo();
		
		if (vo.getSysBonusType() == 1)
		{
			int equip_id = vo.getSysBonusId();
			GameEquip equip = EquipCache.getById(equip_id);
			String intro = "";
			if (vo.getSysBonusIntro() == 1)
			{
				intro = "(��)";
			}
			if (vo.getSysBonusIntro() == 2)
			{
				intro = "(��)";
			}
			if (vo.getSysBonusIntro() == 3)
			{
				intro = "(��)";
			}
			String out = "ϵͳ׷�ӽ���:" + equip.getName()+ intro + "��" + vo.getSysBonusNum();
			return out;
		}
		if (vo.getSysBonusType() == 4)
		{
			PropVO pvo = PropCache.getPropById(vo.getSysBonusId());
			String out = "ϵͳ׷�ӽ���:" + pvo.getPropName() + "��"
					+ vo.getSysBonusNum();
			return out;
		}
		return "";
	}

	/** �ж�ϵͳʱ�� */
	public boolean sysTimeAtEight()
	{
		Date now = new Date();
		if (now.getHours() >= 8 && now.getHours() < 20)
		{
			return true;
		}
		return false;
	}

	public boolean sysTimeAtTwenty()
	{
		Date now = new Date();
		if (now.getHours() >= 20 && now.getHours() < 24)
		{
			return true;
		}
		return false;
	}

	/** �ж���ҵİ��� */
	public int getPlayerWrapSpare(int p_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		int warespare = role_info.getBasicInfo().getWrapSpare();
		LotteryInfoDao lotterydao = new LotteryInfoDao();
		LotteryInfoVO infovo = lotterydao.getLotteryInfo();
		if (infovo.getSysBonusType() != 0)
		{
			if (warespare < infovo.getSysBonusNum())
			{
				return -1;
			}
		}
		return 0;
	}

}
