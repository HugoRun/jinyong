package com.lw.service.player;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.GoodsType;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.lw.dao.player.PlayerGetGamePrizeDao;
import com.lw.dao.player.PlayerStatisticsDao;
import com.lw.vo.player.PlayerGetGamePrizeVO;
import com.lw.vo.player.PlayerStatisticsVO;
import com.pm.service.systemInfo.SystemInfoService;

public class PlayerGetGamePrizeService
{
	/** �����ȡ���������� */
	public String playerGetSystemPrize(RoleEntity role_info, String u_passprot,
			int id)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		PlayerGetGamePrizeVO vo = dao.getPrizeByID(id, u_passprot);
		if (vo == null)
		{
			return "��û�иý�������";
		}
		else
		{
			if (vo.getState() == 1)
			{
				return "���Ѿ���ȡ�ý���";
			}
			else
			{
				if (vo.getP_pk() != 0
						&& vo.getP_pk() != role_info.getBasicInfo().getPPk())
				{
					return "�ý�ɫ������ȡ����";
				}
				String wrapdis = getWrap(role_info.getBasicInfo().getWrapSpare(), vo.getProp());
				if (wrapdis != null)
				{
					return wrapdis;
				}
				else
				{
					String[] split = vo.getProp().split(",");
					for (int i = 0; i < split.length; i++)
					{
						String[] result = split[i].split("-");
						GoodsService goodsService = new GoodsService();
						PropVO propvo = PropCache.getPropById(Integer.parseInt(result[0]));
						goodsService.putGoodsToWrap(role_info.getBasicInfo()
								.getPPk(), Integer.parseInt(result[0]),
								GoodsType.PROP, Integer.parseInt(result[1]));
						String content = role_info.getBasicInfo().getName()
								+ "��ȡ" + propvo.getPropName() + result[1] + "��";
						dao.insertPlayerPrizeInfo(role_info.getBasicInfo()
								.getUPk(), role_info.getBasicInfo().getPPk(),
								content);
					}
					dao.updatePlayerPrizeState(u_passprot, id);
					return "��ȡ�ɹ�";
				}
			}
		}
	}

	/** �õ���ҵ����б� */
	public PlayerGetGamePrizeVO getPlayerPrizeList(int id, String u_passprot)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		PlayerGetGamePrizeVO vo = dao.getPrizeByID(id, u_passprot);
		return vo;
	}

	/** �õ���ҽ�������ʾ */
	public List<PlayerGetGamePrizeVO> getPrizeType(String u_passprot)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		List<PlayerGetGamePrizeVO> list = dao.getPlayerPrizeList(u_passprot, 0,
				7);
		return list;
	}

	/** �õ���ҽ�������ʾ */
	public String getPlayerPrizeOut(int id, String u_passprot,
			HttpServletRequest request, HttpServletResponse response)
	{
		PlayerGetGamePrizeVO vo = getPlayerPrizeList(id, u_passprot);
		String prop = vo.getProp();
		String[] split = prop.split(",");
		StringBuffer resultWml = new StringBuffer();
		for (int i = 0; i < split.length; i++)
		{
			String[] result = split[i].split("-");
			PropVO propvo = PropCache.getPropById(Integer.parseInt(result[0]));
			resultWml.append("<anchor> ");
			resultWml.append("<go method=\"post\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/sysprize.do") + "\">");
			resultWml.append("<postfield name=\"cmd\" value=\"n4\" /> ");
			resultWml.append("<postfield name=\"id\" value=\"" + id + "\" /> ");
			resultWml.append("<postfield name=\"prop_id\" value=\"" + result[0]
					+ "\" /> ");
			resultWml.append("</go>");
			resultWml.append(propvo.getPropName());
			resultWml.append("</anchor> ");
			resultWml.append("x");
			resultWml.append(result[1]);
			if (i != split.length - 1)
			{
				resultWml.append(",");
			}
		}
		return resultWml.toString();
	}

	// �����ҵ��콱ID
	public String getUserID(int u_pk)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		return dao.getUserID(u_pk);
	}

	public String getWrap(int wrap, String prop_dis)
	{
		String[] split = prop_dis.split(",");
		for (int i = 0; i < split.length; i++)
		{
			String[] result = split[i].split("-");
			PropVO propvo = PropCache.getPropById(Integer.valueOf(result[0]));
			int accumulate = propvo.getPropAccumulate();
			int num = Integer.valueOf(result[1]) / accumulate
					+ (Integer.valueOf(result[1]) % accumulate == 0 ? 0 : 1);
			wrap = wrap - num;
		}
		if (wrap < 0)
		{
			return "�����ռ䲻��!";
		}
		else
		{
			return null;
		}
	}

	// ����������ͬ �ж�������һ�� һ�·���ΪUPKΪ�ж�����
	/** �����ȡ���������� */
	public String playerGetSystemPrizeByUpk(RoleEntity role_info, String u_pk,
			int id)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		PlayerGetGamePrizeVO vo = dao.getPrizeByIDByUpk(id, u_pk);
		if (vo == null)
		{
			return "��û�иý�������";
		}
		else
		{
			if (vo.getState() == 1)
			{
				return "���Ѿ���ȡ�ý���";
			}
			else
			{
				if (vo.getP_pk() != 0
						&& vo.getP_pk() != role_info.getBasicInfo().getPPk())
				{
					return "�ý�ɫ������ȡ����";
				}
				String wrapdis = getWrap(role_info.getBasicInfo()
						.getWrapSpare(), vo.getProp());
				if (wrapdis != null)
				{
					return wrapdis;
				}
				else
				{
					String[] split = vo.getProp().split(",");
					for (int i = 0; i < split.length; i++)
					{
						String[] result = split[i].split("-");
						GoodsService goodsService = new GoodsService();
						PropVO propvo = PropCache.getPropById(Integer.parseInt(result[0]));
						goodsService.putGoodsToWrap(role_info.getBasicInfo()
								.getPPk(), Integer.parseInt(result[0]),
								GoodsType.PROP, Integer.parseInt(result[1]));
						String content = role_info.getBasicInfo().getName()
								+ "��ȡ" + propvo.getPropName() + result[1] + "��";
						dao.insertPlayerPrizeInfo(role_info.getBasicInfo()
								.getUPk(), role_info.getBasicInfo().getPPk(),
								content);
					}
					dao.updatePlayerPrizeStateByUpk(u_pk, id);
					return "��ȡ�ɹ�";
				}
			}
		}
	}

	/** �õ���ҵ����б� */
	public PlayerGetGamePrizeVO getPlayerPrizeListByUpk(int id, String u_pk)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		PlayerGetGamePrizeVO vo = dao.getPrizeByIDByUpk(id, u_pk);
		return vo;
	}

	/** �õ���ҽ�������ʾ */
	public List<PlayerGetGamePrizeVO> getPrizeTypeByUpk(String u_pk)
	{
		PlayerGetGamePrizeDao dao = new PlayerGetGamePrizeDao();
		List<PlayerGetGamePrizeVO> list = dao.getPlayerPrizeListByUpk(u_pk, 0,
				7);
		return list;
	}

	/** �õ���ҽ�������ʾ */
	public String getPlayerPrizeOutByUpk(int id, String u_pk,
			HttpServletRequest request, HttpServletResponse response)
	{
		PlayerGetGamePrizeVO vo = getPlayerPrizeListByUpk(id, u_pk);
		String prop = vo.getProp();
		String[] split = prop.split(",");
		StringBuffer resultWml = new StringBuffer();
		for (int i = 0; i < split.length; i++)
		{
			String[] result = split[i].split("-");
			PropVO propvo = PropCache.getPropById(Integer.parseInt(result[0]));
			resultWml.append("<anchor> ");
			resultWml.append("<go method=\"post\" href=\""
					+ response.encodeURL(GameConfig.getContextPath()
							+ "/sysprize.do") + "\">");
			resultWml.append("<postfield name=\"cmd\" value=\"n4\" /> ");
			resultWml.append("<postfield name=\"id\" value=\"" + id + "\" /> ");
			resultWml.append("<postfield name=\"prop_id\" value=\"" + result[0]
					+ "\" /> ");
			resultWml.append("</go>");
			resultWml.append(propvo.getPropName());
			resultWml.append("</anchor> ");
			resultWml.append("x");
			resultWml.append(result[1]);
			if (i != split.length - 1)
			{
				resultWml.append(",");
			}
		}
		return resultWml.toString();
	}

	/** �����û����߽�����˵�� */
	public void getPlayerOnlineTimePrize(RoleEntity roleInfo, int state,
			String u_name)
	{
		PlayerStatisticsService ps = new PlayerStatisticsService();
		PlayerGetGamePrizeDao pggpdao = new PlayerGetGamePrizeDao();
		String name = "�󻹵�������";
		String prize_content = prize_content(roleInfo.getBasicInfo().getGrade());
		PlayerGetGamePrizeVO pggpvo = pggpdao.getPlayerIsHaveOnlineTimePrize(
				name, roleInfo.getBasicInfo().getUPk(), roleInfo.getBasicInfo()
						.getPPk(), state, ps.getTodayDate(), prize_content);
		if (pggpvo == null)
		{
			ps.updatePlayerOnlineTime(roleInfo);
			ps.playerStatisticsFlow(roleInfo);
			PlayerStatisticsDao dao = new PlayerStatisticsDao();
			PlayerStatisticsVO vo = dao.getPlayerInfo(roleInfo.getBasicInfo()
					.getUPk(), roleInfo.getBasicInfo().getPPk(), ps
					.getTodayDate());
			int time = vo.getOnlinetime();
			if (time > 299)
			{
				pggpdao.insertPlayerOnlinePrize(name, name, u_name, roleInfo
						.getBasicInfo().getUPk(), roleInfo.getBasicInfo()
						.getName(), roleInfo.getBasicInfo().getPPk(), state,
						prize_content);
			}
		}
		pggpdao.delPlayerOnlinePrize(ps.getTodayDate(), state);
	}

	public String prize_content(int grade)
	{
		String prize = "";
		if (grade < 30)
		{
			prize = "1525-1";
		}
		else
		{
			if (grade < 40)
			{
				prize = "1526-1";
			}
			else
			{
				if (grade < 50)
				{
					prize = "1527-1";
				}
				else
				{
					prize = "1528-1";
				}
			}
		}
		return prize;
	}

	/** *************�����ȥ���ֽ���****************** */
	public void getNewYearPrize(RoleEntity roleInfo)
	{
		if (GameConfig.getPlayerGetOnlinePrizeSwitch() == 0)
		{
			return;
		}
		if(roleInfo.getBasicInfo().getGrade() < 20){
			return;
		}
		if(roleInfo.getBasicInfo().getSex() == 1){
			return;
		}
		int prop_id = GameConfig.getPlayerGetOnlinePrizeProp();
		TimeControlService timeControlService = new TimeControlService();
		boolean get = timeControlService.isUseableByAll(roleInfo.getBasicInfo()
				.getPPk(), prop_id, TimeControlService.GETNEWYEARPRIZE, 1);
		if (get == true)// ������ȡ
		{
			// ��ȡ�����˳�
			GoodsService gs = new GoodsService();
			int have = gs.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(),
					prop_id, 4, 1);
			if (have == -1)// ��������
			{
				return;
			}
			else
			{
				timeControlService.updateControlInfoByAll(roleInfo
						.getBasicInfo().getPPk(), prop_id,
						TimeControlService.GETNEWYEARPRIZE);
				PropVO vo = PropCache.getPropById(prop_id);
				SystemInfoService systemInfoService = new SystemInfoService();
				systemInfoService.insertSystemInfoBySystem(roleInfo
						.getBasicInfo().getPPk(), "���Ѿ������һ��" + vo.getPropName()
						+ ",��л����������Ϸ��֧��!");
			}
		}
		else
		{
			return;
		}
	}
}
