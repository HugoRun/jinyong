package com.lw.service.menpaicontest;

import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.MapType;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.room.RoomService;
import com.lw.dao.menpaicontest.MenpaiContestDAO;
import com.lw.vo.menpaicontest.MenpaiContestPlayerVO;
import com.pm.service.systemInfo.SystemInfoService;

public class MenpaiContestService
{
	public static Logger logger = Logger.getLogger("log.service");

	/** *****��ҽ������ɱ���Ĵ���******* */

	// ����
	public void updatePlayerMenpaiContestState(RoleEntity roleinfo,String new_scene_id)
	{
		String scene_id = roleinfo.getBasicInfo().getSceneId();
		RoomService rs = new RoomService();
		if (scene_id == null || scene_id.equals("") || scene_id.equals("null"))
		{
			return;
		}
		else
		{
			SceneVO scene = rs.getById(scene_id + "");
			SceneVO scene_new = rs.getById(new_scene_id + "");
			if (MapType.MENPAICONTEST == scene_new.getMap().getMapType())
			{
				inMenpaiContestMap(roleinfo);
			}
			if (MapType.MENPAICONTEST == scene.getMap().getMapType())
			{
				outMenpaiContestMap(roleinfo);
			}
		}
	}

	// ����������ߺ���ؾ���ͼ��Ϣ
	public void updateOfflinePlayerMenpaiContestState(RoleEntity roleinfo)
	{
		String scene_id = roleinfo.getBasicInfo().getSceneId();
		RoomService rs = new RoomService();
		if (scene_id == null || scene_id.equals("") || scene_id.equals("null"))
		{
			return;
		}
		else
		{
			SceneVO scene = rs.getById(scene_id + "");
			if (MapType.MENPAICONTEST == scene.getMap().getMapType())
			{
				outMenpaiContestMap(roleinfo);
				int resurrection_point = rs.getResurrectionPoint(roleinfo);
				roleinfo.getBasicInfo().updateSceneId(resurrection_point + "");
			}
		}
	}

	// ������ҳ���
	private void outMenpaiContestMap(RoleEntity roleinfo)
	{
		// �ж�����Ƿ����
		boolean have = isHavePlayerData(roleinfo.getBasicInfo().getPPk());
		if (have == true)
		{
			updatePlayerOutData(roleinfo.getPPk());
		}
	}

	// ������ҽ���
	private void inMenpaiContestMap(RoleEntity roleinfo)
	{
		// �ж�����Ƿ����
		boolean have = isHavePlayerData(roleinfo.getPPk());
		if (have == true)
		{
			updatePlayerInData(roleinfo.getPPk(),roleinfo.getBasicInfo().getPRace());
		}
		else
		{
			insertPlayerData(roleinfo.getPPk(), roleinfo.getName(), roleinfo.getBasicInfo().getPRace());
			updatePlayerInData(roleinfo.getPPk(),roleinfo.getBasicInfo().getPRace());
		}
	}

	// ɱ����
	public void updatePlayerKillNum(int win_p_pk, int p_pk_kill)
	{
		RoleEntity roleinfo = RoleCache.getByPpk(win_p_pk);
		MenpaiContestDAO dao = new MenpaiContestDAO();
		RoomService rs = new RoomService();
		String scene_id = roleinfo.getBasicInfo().getSceneId();
		SceneVO scene = rs.getById(scene_id + "");
		if (MapType.MENPAICONTEST == scene.getMap().getMapType())
		{
			dao.updatePlayerKillData(win_p_pk);
			dao.updatePlayerBeiKillData(p_pk_kill, win_p_pk);
		}
	}

	// ����Ƿ��и�����
	private boolean isHavePlayerData(int p_pk)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		MenpaiContestPlayerVO vo = dao.selectPlayerData(p_pk);
		if (vo == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	// �����������
	private void insertPlayerData(int p_pk, String p_name, int p_type)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		dao.insertPlayerData(p_pk, p_name, p_type);
	}

	// ������ҳ�ȥ������
	private void updatePlayerOutData(int p_pk)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		dao.updatePlayerOutData(p_pk);
	}

	// ������ҽ�������
	private void updatePlayerInData(int p_pk, int type)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		dao.updatePlayerInData(p_pk);
	}

	/*// �ж���ҵĳ�ν
	public int getPlayerTitleType(RoleEntity roleinfo)
	{
		if (roleinfo.getBasicInfo().getTitle().equals("xunshandizi")
				|| roleinfo.getBasicInfo().getTitle().equals("wuxingqidizi")
				|| roleinfo.getBasicInfo().getTitle().equals("wuxingqijingrui")
				|| roleinfo.getBasicInfo().getTitle().equals("neitangdizi")
				|| roleinfo.getBasicInfo().getTitle().equals("neitangjingrui")
				|| roleinfo.getBasicInfo().getTitle().equals("huhuodizi"))
		{
			return 1;
		}
		if (roleinfo.getBasicInfo().getTitle().equals("yidaidizi")
				|| roleinfo.getBasicInfo().getTitle().equals("erdaidizi")
				|| roleinfo.getBasicInfo().getTitle().equals("sandaidizi")
				|| roleinfo.getBasicInfo().getTitle().equals("sidaidizi")
				|| roleinfo.getBasicInfo().getTitle().equals("wudaidizi")
				|| roleinfo.getBasicInfo().getTitle().equals("liudaidizi"))
		{
			return 2;
		}
		if (roleinfo.getBasicInfo().getTitle().equals("saodiseng")
				|| roleinfo.getBasicInfo().getTitle().equals("zhikeseng")
				|| roleinfo.getBasicInfo().getTitle().equals("wuseng")
				|| roleinfo.getBasicInfo().getTitle().equals("damotangdizi")
				|| roleinfo.getBasicInfo().getTitle().equals("luohantangdizi")
				|| roleinfo.getBasicInfo().getTitle().equals("cangjinggedizi"))
		{
			return 3;
		}
		return 0;
	}*/

	/** *****��ʱ������������ɱ���****** */
	public void menpaiContestReady()
	{
		SystemInfoService systemInfoService = new SystemInfoService();
		systemInfoService
				.insertSystemInfoBySystem("���ɱ����Ὣ��30���Ӻ�ʼ,�����ɵĵ��ӿ���ǰ���Լ������ɽ������������!");
	}

	public void menpaiContestRun()
	{
		SystemInfoService systemInfoService = new SystemInfoService();
		systemInfoService.insertSystemInfoBySystem("���ɱ����Ὺʼ�ˣ�ף��λ��������!");
	}

	public void menpaiContestOver(int p_type)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		SystemInfoService systemInfoService = new SystemInfoService();
		RoomService rs = new RoomService();
		List<MenpaiContestPlayerVO> list = dao.selectMenpaiOverDataList(p_type);
		String p_title = "";
		String p_lenger = "";
		if (p_type == 1)
		{
			p_title = "����";
			p_lenger = "����";
		}
		if (p_type == 2)
		{
			p_title = "ؤ��";
			p_lenger = "����";
		}
		if (p_type == 3)
		{
			p_title = "����";
			p_lenger = "����";
		}
		if (list != null && list.size() != 0)
		{

			if (list.size() == 1)
			{
				// ������ҵ�
				MenpaiContestPlayerVO one_bak = list.get(0);
				RoleEntity roleinfo = RoleCache.getByPpk(one_bak.getP_pk());
				// WIN_TYPE 3Ϊû����ȡ����
				dao.updatePlayerRankState(roleinfo.getBasicInfo().getPPk(), 3);
				systemInfoService
						.insertSystemInfoBySystem(roleinfo.getBasicInfo()
								.getName()
								+ "��"
								+ p_title
								+ "�����ɱ�������ӱ����,��Ϊ��"
								+ p_title
								+ "����ϯ����!");
				String scene_id = roleinfo.getBasicInfo().getSceneId();
				if (scene_id == null || scene_id.equals("")
						|| scene_id.equals("null"))
				{
				}
				else
				{
					int resurrection_point = rs.getResurrectionPoint(roleinfo);
					roleinfo.getBasicInfo().updateSceneId(
							resurrection_point + "");
				}
			}
			else
			{
				String name = "";
				MenpaiContestPlayerVO one_bak_1 = null;
				MenpaiContestPlayerVO two_bak_2 = null;
				for (int i = 0; i < list.size(); i++)
				{
					if (i != list.size() - 1)
					{
						one_bak_1 = list.get(i);
						two_bak_2 = list.get(i + 1);
						if (one_bak_1.getKill_num() > two_bak_2.getKill_num())
						{
							RoleEntity roleinfo_bak = RoleCache.getByPpk(one_bak_1.getP_pk());
							name = roleinfo_bak.getBasicInfo().getName();
							String scene_id = roleinfo_bak.getBasicInfo()
									.getSceneId();
							if (scene_id == null || scene_id.equals("")
									|| scene_id.equals("null"))
							{
								continue;
							}
							else
							{
								int resurrection_point = rs.getResurrectionPoint(roleinfo_bak);
								roleinfo_bak.getBasicInfo().updateSceneId(
										resurrection_point + "");
								if (i == 0)
								{
									dao.updatePlayerRankState(roleinfo_bak
											.getBasicInfo().getPPk(), 3);
									systemInfoService
											.insertSystemInfoBySystem(roleinfo_bak
													.getBasicInfo().getName()
													+ "��"
													+ p_title
													+ "�����ɱ�������ӱ����,��Ϊ��"
													+ p_title + "����ϯ����!");
									return;
								}
							}
							break;
						}
						else
						{
							RoleEntity roleinfo_one = RoleCache.getByPpk(one_bak_1.getP_pk());
							RoleEntity roleinfo_two = RoleCache.getByPpk(two_bak_2.getP_pk());
							// WIN_TYPE 2Ϊû�зֳ�ʤ��
							dao.updatePlayerRankState(roleinfo_one
									.getBasicInfo().getPPk(), 2);
							dao.updatePlayerRankState(roleinfo_two
									.getBasicInfo().getPPk(), 2);
							if (i == 0)
							{
								name = roleinfo_one.getBasicInfo().getName();
							}
							else
							{
								name = ","
										+ roleinfo_one.getBasicInfo().getName();
							}
							String scene_id = roleinfo_one.getBasicInfo()
									.getSceneId();
							if (scene_id == null || scene_id.equals("")
									|| scene_id.equals("null"))
							{
								continue;
							}
							else
							{
								int resurrection_point = rs.getResurrectionPoint(roleinfo_one);
								roleinfo_one.getBasicInfo().updateSceneId(
										resurrection_point + "");
							}
						}
					}
				}
				systemInfoService.insertSystemInfoBySystem(p_title
						+ "����ϯ����֮����Ȼδ�ó�����ʤ��," + p_lenger + "������" + name
						+ "������ս");
			}
		}
		else
		{
			systemInfoService.insertSystemInfoBySystem(p_title + "���������");
		}
	}

	// ��ҷ���
	public int updatePlayerGetPrizeData(RoleEntity roleinfo, int type)
	{
		String prop = GameConfig.getPlayerGetMenpaiContestProp(type).trim();
		GoodsService gs = new GoodsService();
		MenpaiContestDAO dao = new MenpaiContestDAO();
		// ������ҵ���
		String build = GameConfig.getPlayerGetMenpaiContestbulid(type).trim();
		MenuService menuService = new MenuService();
		OperateMenuVO menu = menuService.getMenuById(Integer.parseInt(build));
		String name = roleinfo.getBasicInfo().getName() + "�ĵ���";
		menu.setMenuName(name);
		MenuCache menuCache = new MenuCache();
		menuCache.reloadOneMenu(menu);
		dao.updatePlayerRankData(roleinfo.getBasicInfo().getPPk());
		return gs.putGoodsToWrap(roleinfo.getBasicInfo().getPPk(), Integer
				.parseInt(prop), 4, 1);
	}

	/** *****��ұ��������Ĵ���****** */
	public List<MenpaiContestPlayerVO> getPlayerRankList(int pageNo,
			String p_type)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		List<MenpaiContestPlayerVO> list = dao.selectPlayerRankDataList(pageNo,
				p_type);
		return list;
	}

	public int selectPlayerRankDataNum(String p_type)
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		return dao.selectPlayerRankDataNum(p_type);
	}

	public void kickPlayerout()
	{
		MenpaiContestDAO dao = new MenpaiContestDAO();
		List<MenpaiContestPlayerVO> list = dao.selectPlayerIn();
		if (list != null && list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				MenpaiContestPlayerVO vo = list.get(i);
				RoleEntity roleEntity = RoleCache.getByPpk(vo.getP_pk());
				if (roleEntity != null)
				{
					RoomService rs = new RoomService();
					int resurrection_point = rs.getResurrectionPoint(roleEntity);
					roleEntity.getBasicInfo().updateSceneId(resurrection_point + "");
				}
			}

		}
	}
}
