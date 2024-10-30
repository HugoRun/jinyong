package com.lw.service.lottery;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.lottery.DrawALotteryDao;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.lw.vo.lottery.DrawALotteryVO;
import com.pm.dao.mail.MailBonusDao;
import com.pm.dao.mail.MailInfoDao;
import com.pm.service.mail.MailInfoService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.mail.MailBonusVO;

public class DrawALotteryService
{

	/** �õ��齱���� */
	private DrawALotteryVO getInfo(int id)
	{
		DrawALotteryDao dao = new DrawALotteryDao();
		DrawALotteryVO vo = dao.getDrawALotteryInfo(id);
		return vo;
	}

	/** �õ�ÿ�ֳ齱������ */
	private List<Integer> getPPKS(int id)
	{
		List<Integer> list = new ArrayList<Integer>();
		DrawALotteryDao dao = new DrawALotteryDao();
		DrawALotteryVO vo = getInfo(id);
		String[] level = vo.getLevel_content().split(",");
		int type = vo.getType();
		if (type == 1)
		{
			list = dao.getOnlinePlayer(vo.getDraw_people(), level[0], level[1]);
			return list;
		}
		else
			if (type == 2)
			{
				list = dao.getTodayRegPlayer(DateUtil.getTodayStr(), vo
						.getDraw_people(), level[0], level[1]);
				return list;
			}
			else
				if (type == 3)
				{
					list = dao.getPayPlayer(DateUtil.getTodayStr(), vo
							.getDraw_people(), level[0], level[1]);
					return list;
				}
				else
				{
					return null;
				}
	}

	/** ����ҷ��ʼ� */
	private String drawALottery(int id)
	{
		String name_str = "";
		DrawALotteryVO vo = getInfo(id);
		if (vo != null)
		{
			List<Integer> list = getPPKS(id);
			if (list != null)
			{
				for (int i = 0; i < list.size(); i++)
				{
					int p_pk = list.get(i);
					if (p_pk != 0)
					{
						MailInfoService ms = new MailInfoService();
						PartInfoDao dao = new PartInfoDao();
						String name = dao.getNameByPpk(p_pk);
						if (vo.getType() == 3)
						{
							DrawALotteryDao drawALotteryDao = new DrawALotteryDao();
							String title = "��ϲ����Ϊ�������";
							int yuanbao = drawALotteryDao.getChongzhiYb(p_pk,
									DateUtil.getTodayStr());
							String content = "����" + DateUtil.getTodayStr()
									+ "��" + vo.getLotter_name()
									+ "�г�Ϊ�������,��ý��ճ�ֵ��" + yuanbao
									+ ""+GameConfig.getYuanbaoName()+"��ͬ���!";
							// �õ���ҵĳ�ֵ���
							ms.insertBonusMail(p_pk, title, content, yuanbao
									+ "," + id);
						}
						else
						{
							String title = "��ϲ����Ϊ�������";
							String content = "����" + DateUtil.getTodayStr()
									+ "��" + vo.getLotter_name()
									+ "�г�Ϊ�������,���ϵͳ����" + getBonusView(id) + "!";
							ms.insertBonusMail(p_pk, title, content, vo.getId()
									+ "");
						}

						if (i != list.size() - 1)
						{
							name_str = name_str + name + ",";
						}
						else
						{
							name_str = name_str + name;
						}
					}
				}
			}
		}
		return name_str;
	}

	/** ����ϵͳ��Ϣ ���Ҹ���ҷ����ʼ� */
	public void getSystemInfo(int id)
	{
		DrawALotteryVO vo = getInfo(id);
		String name = drawALottery(id);
		if (name.equals(""))
		{
			return;
		}
		else
		{
			if (vo.getType() == 3)
			{
				String info = "����" + vo.getLotter_name() + "���������Ϊ" + name
						+ "!������ҽ���ý��ճ�ֵ���ͬ���,�����ʼ�����!";
				SystemInfoService ss = new SystemInfoService();
				ss.insertSystemInfoBySystem(info);
			}
			else
			{
				String info = "����" + vo.getLotter_name() + "���������Ϊ" + name
						+ "!������ҽ����" + getBonusView(id) + ",�����ʼ�����!";
				SystemInfoService ss = new SystemInfoService();
				ss.insertSystemInfoBySystem(info);
			}
		}

	}

	/** �õ���������ʾ */
	private String getBonusView(int id)
	{
		DrawALotteryVO vo = getInfo(id);
		String bonus = vo.getBonus_content();
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

	/** ��������ж�����Ʒ */
	private int getDrawBonusNum(int id)
	{
		int num = 0;
		DrawALotteryVO vo = getInfo(id);
		String bonus = vo.getBonus_content();
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
							num = num + 1;
						}
		}
		return num;
	}

	// �����ȡ�����Ĺ���
	public String playerCatchMoney(int p_pk, String id, int mail_id)
	{
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");
		MailBonusDao mailBonusDao = new MailBonusDao();
		MailInfoDao mdao = new MailInfoDao();
		MailBonusDao dao = new MailBonusDao();
		MailBonusVO mailBonusVO = mailBonusDao.getMailBonus(p_pk, mail_id);
		String[] mail = mailBonusVO.getBonus().split(",");
		if (mail.length == 2)
		{
			EconomyService economyService = new EconomyService();
			String bonus_yuanbao = mail[0];
			if (bonus_yuanbao != null && !bonus_yuanbao.equals(""))
			{
				economyService.addYuanbao(roleInfo.getBasicInfo().getUPk(),
						Integer.parseInt(bonus_yuanbao));
			}
			else
			{

			}
			dao.updateState(p_pk, mail_id);// ���������ȡ
			mdao.deleteMailByid(mail_id + "", p_pk);// ɾ���ʼ�
			return "�������" + bonus_yuanbao + ""+GameConfig.getYuanbaoName()+"!";
		}
		else
		{
			int bonusNum = getDrawBonusNum(Integer.parseInt(id));

			int wrapSpare = roleInfo.getBasicInfo().getWrapSpare();

			if (wrapSpare == 0 || wrapSpare < 0 || wrapSpare < bonusNum)
			{
				return null;
			}
			else
			{
				DrawALotteryVO vo = getInfo(Integer.parseInt(id));
				String bonus = vo.getBonus_content();

				String[] bonusprop = bonus.split(",");
				for (int i = 0; i < bonusprop.length; i++)
				{
					String[] prop = bonusprop[i].split("-");
					if (prop[0].equals("0"))
					{
						int laboragemoney = Integer.parseInt(prop[2]);

						// ���
						LogService logService = new LogService();
						logService.recordMoneyLog(roleInfo.getBasicInfo()
								.getPPk(), roleInfo.getBasicInfo().getName(),
								roleInfo.getBasicInfo().getCopper() + "",
								laboragemoney + "", "����");

						roleInfo.getBasicInfo().addCopper(laboragemoney);// �����ȡ����
						// ִ��ͳ��
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(6, StatisticsType.MONEY, laboragemoney,
								StatisticsType.DEDAO, StatisticsType.XITONG,
								p_pk);
					}
					else
						if (prop[0].equals("5"))
						{
							int experience = Integer.parseInt(prop[2]);

							// ���
							LogService logService = new LogService();
							logService.recordExpLog(roleInfo.getBasicInfo()
									.getPPk(), roleInfo.getBasicInfo()
									.getName(), roleInfo.getBasicInfo()
									.getCurExp(), experience + "", "ϵͳ");

							roleInfo.getBasicInfo().updateAddCurExp(experience);
						}
						else
						{
							GoodsService goodsService = new GoodsService();
							goodsService.putGoodsToWrap(p_pk, Integer
									.parseInt(prop[1]), Integer
									.parseInt(prop[0]), 0, Integer
									.parseInt(prop[2]),GameLogManager.G_SYSTEM);// ��һ��ϵͳ�����ĵ���
							// ִ��ͳ��
							GameSystemStatisticsService gsss = new GameSystemStatisticsService();
							gsss.addPropNum(Integer.parseInt(prop[1]), Integer
									.parseInt(prop[0]), Integer
									.parseInt(prop[2]), StatisticsType.DEDAO,
									StatisticsType.XITONG, p_pk);
						}
				}
				dao.updateState(p_pk, mail_id);// ���������ȡ
				mdao.deleteMailByid(mail_id + "", p_pk);// ɾ���ʼ�
				return "�������" + getBonusView(Integer.parseInt(id));
			}
		}
	}
}