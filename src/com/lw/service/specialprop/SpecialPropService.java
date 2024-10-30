package com.lw.service.specialprop;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.property.PropertyModel;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.PropType;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.player.RoleService;
import com.lw.dao.specialprop.SpecialPropDAO;
import com.lw.vo.specialprop.SpecialPropVO;

public class SpecialPropService
{
	PropertyModel suitPropertyModel = new PropertyModel();
	/**
	 * ʹ��װ������Ʒ�ķ���HP
	 */
	private int useEquipHpItemHP(PartInfoVO player, SpecialPropVO vo)
	{

		// ���ߵ�����ʹ��Ҫ��
		if (player.getPHp() != player.getPMaxHp())
		{
			// ��Ʒ��Ѫ������
			String hp1 = vo.getPropoperate1();
			String[] x = hp1.split(",");
			Integer intx1 = Integer.valueOf(x[0]);// ÿ�غ�����Ѫ��
			Integer intx2 = Integer.valueOf(x[1]);// ʣ��Ѫ��
			int playerHp = player.getPMaxHp() - player.getPHp();// ���Ҫ�ӵ�Ѫ��

			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoById(player.getPPk()
					+ "");

			if (playerHp > intx1)
			{
				// Ѫ������
				player.setPHp(player.getPHp() + intx1);
				roleInfo.getBasicInfo().updateHp(player.getPHp());
				return intx1;
			}
			else
			{
				// Ѫ������
				player.setPHp(player.getPHp() + playerHp);
				roleInfo.getBasicInfo().updateHp(player.getPHp());
				return playerHp;
			}
			/**
			 * if (intx2 != 0) { if (playerHp > intx1) { if (intx2 < intx1) {
			 * intx2 = 0; x[1] = intx2.toString(); String hp2 = x[0] + "," +
			 * x[1] + "," + x[2] + "," + x[3] + "," + x[4] + "," + x[5]; //
			 * ������Ʒ��ʹ���� dao.updateEquipItemNumHM(player.getPPk(), i, hp2); //
			 * Ѫ������ player.setPHp(player.getPHp() + intx2); playerDao
			 * .updateHP(player.getPPk(), player.getPHp()); return intx2; } else {
			 * intx2 = intx2 - intx1; x[1] = intx2.toString(); String hp2 = x[0] +
			 * "," + x[1] + "," + x[2] + "," + x[3] + "," + x[4] + "," + x[5]; //
			 * ������Ʒ��ʹ���� dao.updateEquipItemNumHM(player.getPPk(), i, hp2); //
			 * Ѫ������ player.setPHp(player.getPHp() + intx1); playerDao
			 * .updateHP(player.getPPk(), player.getPHp()); return intx1; } }
			 * else { if (intx2 < playerHp) { intx2 = 0; x[1] =
			 * intx2.toString(); String hp2 = x[0] + "," + x[1] + "," + x[2] +
			 * "," + x[3] + "," + x[4] + "," + x[5]; // ������Ʒ��ʹ����
			 * dao.updateEquipItemNumHM(player.getPPk(), i, hp2); // Ѫ������
			 * player.setPHp(player.getPHp() + intx2); playerDao
			 * .updateHP(player.getPPk(), player.getPHp()); return intx2; } else {
			 * intx2 = intx2 - playerHp; x[1] = intx2.toString(); String hp2 =
			 * x[0] + "," + x[1] + "," + x[2] + "," + x[3] + "," + x[4] + "," +
			 * x[5]; // ������Ʒ��ʹ���� dao.updateEquipItemNumHM(player.getPPk(), i,
			 * hp2); // Ѫ������ player.setPHp(player.getPHp() + playerHp);
			 * playerDao .updateHP(player.getPPk(), player.getPHp()); return
			 * playerHp; } } } else { return 0; }
			 */
		}
		else
		{
			return -1;
		}
	}

	/**
	 * ʹ��װ������Ʒ�ķ���MP
	 */
	private int useEquipMpItemMP(PartInfoVO player, SpecialPropVO vo)
	{
		// ���ߵ�����ʹ��Ҫ��
		if (player.getPMp() != player.getPMaxMp())
		{
			// ��Ʒ��Ѫ������
			String mp1 = vo.getPropoperate1();
			String[] x = mp1.split(",");
			Integer intx1 = Integer.valueOf(x[3]);
			Integer intx2 = Integer.valueOf(x[4]);
			int playerMp = player.getPMaxMp() - player.getPMp();// ���Ҫ�ӵ�Ѫ��

			RoleService roleService = new RoleService();
			RoleEntity roleInfo = roleService.getRoleInfoById(player.getPPk()
					+ "");

			if (playerMp > intx1)
			{
				// Ѫ������
				player.setPMp(player.getPMp() + intx1);
				roleInfo.getBasicInfo().updateMp(player.getPMp());
				return intx1;
			}
			else
			{
				// Ѫ������
				player.setPMp(player.getPMp() + playerMp);
				roleInfo.getBasicInfo().updateMp(player.getPMp());
				return playerMp;
			}
			/**
			 * if (intx2 != 0) { if (playerMp > intx1) { if (intx2 < intx1) {
			 * intx2 = 0; x[4] = intx2.toString(); String mp2 = x[0] + "," +
			 * x[1] + "," + x[2] + "," + x[3] + "," + x[4] + "," + x[5]; //
			 * ������Ʒ��ʹ���� dao.updateEquipItemNumHM(player.getPPk(), i, mp2); //
			 * Ѫ������ player.setPMp(player.getPMp() + intx2); playerDao
			 * .updateMP(player.getPPk(), player.getPMp()); return intx2; } else {
			 * intx2 = intx2 - intx1; x[4] = intx2.toString(); String mp2 = x[0] +
			 * "," + x[1] + "," + x[2] + "," + x[3] + "," + x[4] + "," + x[5]; //
			 * ������Ʒ��ʹ���� dao.updateEquipItemNumHM(player.getPPk(), i, mp2); //
			 * Ѫ������ player.setPMp(player.getPMp() + intx1); playerDao
			 * .updateMP(player.getPPk(), player.getPMp()); return intx1; } }
			 * else { if (intx2 < playerMp) { intx2 = 0; x[4] =
			 * intx2.toString(); String mp2 = x[0] + "," + x[1] + "," + x[2] +
			 * "," + x[3] + "," + x[4] + "," + x[5]; // ������Ʒ��ʹ����
			 * dao.updateEquipItemNumHM(player.getPPk(), i, mp2); // Ѫ������
			 * player.setPMp(player.getPMp() + intx2); playerDao
			 * .updateMP(player.getPPk(), player.getPMp()); return intx2; } else {
			 * intx2 = intx2 - playerMp; x[4] = intx2.toString(); String mp2 =
			 * x[0] + "," + x[1] + "," + x[2] + "," + x[3] + "," + x[4] + "," +
			 * x[5]; // ������Ʒ��ʹ���� dao.updateEquipItemNumHM(player.getPPk(), i,
			 * mp2); // Ѫ������ player.setPMp(player.getPMp() + playerMp);
			 * playerDao .updateMP(player.getPPk(), player.getPMp()); return
			 * playerMp; } } } else { return 0; }
			 */
		}
		else
		{
			return -1;
		}

	}

	/** �õ���ҿ���װ����װ������ߵ����� */
	public List<PlayerPropGroupVO> getPlayerEquipNum(int p_pk)
	{
		SpecialPropDAO propGroupDao = new SpecialPropDAO();
		List<PlayerPropGroupVO> propGroupVO = propGroupDao.getEquipItem(p_pk);
		return propGroupVO;
	}

	/** װ������߷�ҳ���� */
	public List<PlayerPropGroupVO> getPlayerEquipList(int pPk, int thispage,
			int perpagenum)
	{
		SpecialPropDAO propGroupDao = new SpecialPropDAO();
		List<PlayerPropGroupVO> propGroupVO = propGroupDao.getEquipItemList(
				pPk, thispage, perpagenum);
		return propGroupVO;
	}

	/**
	 * �����Զ���Ѫ�������巽������ ���ص���ʹ����Ʒ������ �����0 ���ʾȫ����ʹ��
	 */
	/*
	 * public int updateEquipHpItemHP(PropVO prop, int p_pk, int num, String
	 * pg_pk1) { SpecialPropDAO dao = new SpecialPropDAO(); SpecialPropVO vo =
	 * dao.getSpecialProp(p_pk, pg_pk1); String hp1 = vo.getPropoperate1();
	 * String[] x = hp1.split(","); Integer intx2 = Integer.valueOf(x[1]);
	 * Integer intx3 = Integer.valueOf(x[2]); int add_hp =
	 * Integer.parseInt(prop.getPropOperate1()) * num; int per_hp =
	 * Integer.parseInt(prop.getPropOperate1()); if (!intx2.equals(intx3)) { if
	 * (intx2 + add_hp <= intx3) { intx2 = intx2 + add_hp; x[1] =
	 * intx2.toString(); String hp2 = x[0] + "," + x[1] + "," + x[2] + "," +
	 * x[3] + "," + x[4] + "," + x[5]; dao.updateEquipItemNumHMByPgpk(p_pk,
	 * pg_pk1, hp2); return 0; } else { int a = ((intx3 - intx2) / per_hp +
	 * ((intx3 - intx2) % per_hp == 0 ? 0 : 1)); String hp2 = x[0] + "," + x[2] +
	 * "," + x[2] + "," + x[3] + "," + x[4] + "," + x[5];
	 * dao.updateEquipItemNumHMByPgpk(p_pk, pg_pk1, hp2); num = a; return num; } }
	 * else { return -1; } }
	 * 
	 *//**
		 * �����Զ���Ѫ���������������� ���ص���ʹ����Ʒ������ �����0 ���ʾȫ����ʹ��
		 */
	/*
	 * public int updateEquipHpItemMP(PropVO prop, int p_pk, int num, String
	 * pg_pk1) { SpecialPropDAO dao = new SpecialPropDAO(); SpecialPropVO vo =
	 * dao.getSpecialProp(p_pk, pg_pk1); String mp1 = vo.getPropoperate1();
	 * String[] x = mp1.split(","); Integer intx2 = Integer.valueOf(x[4]);
	 * Integer intx3 = Integer.valueOf(x[5]); int add_mp =
	 * Integer.parseInt(prop.getPropOperate1()) * num; int per_mp =
	 * Integer.parseInt(prop.getPropOperate1()); if (!intx2.equals(intx3)) { if
	 * (intx2 + add_mp <= intx3) { intx2 = intx2 + add_mp; x[4] =
	 * intx2.toString(); String mp2 = x[0] + "," + x[1] + "," + x[2] + "," +
	 * x[3] + "," + x[4] + "," + x[5]; dao.updateEquipItemNumHMByPgpk(p_pk,
	 * pg_pk1, mp2); return 0; } else { int a = ((intx3 - intx2) / per_mp +
	 * ((intx3 - intx2) % per_mp == 0 ? 0 : 1)); String mp2 = x[0] + "," + x[1] +
	 * "," + x[2] + "," + x[3] + "," + x[5] + "," + x[5];
	 * dao.updateEquipItemNumHMByPgpk(p_pk, pg_pk1, mp2); num = a; return num; } }
	 * else { return -1; } }
	 */

	/** �ȼ� */

	private String getPropDisplay(String propReLevel)
	{
		String[] maxLevel = propReLevel.split(",");
		if (Integer.valueOf(maxLevel[1]) == 1000)
		{
			return maxLevel[0];
		}
		else
		{
			return propReLevel;
		}
	}

	public int getEquipItemOnByPropID(int p_pk, int prop_id)
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		SpecialPropDAO dao = new SpecialPropDAO();
		PlayerPropGroupDao pdao = new PlayerPropGroupDao();
		int pg_pk = playerPropGroupDao.getPgpkByProp(p_pk, prop_id);
		PlayerPropGroupVO pvo = pdao.getByPgPk(pg_pk);
		PropVO ppvo = PropCache.getPropById(pvo.getPropId());
		if (pg_pk != -1)
		{
			dao.insertSpecialProp(ppvo, p_pk, pg_pk);
			pdao.deletePropGroup(pg_pk);
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(p_pk, 1);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	/** ���װ������߸��� */
	public int getEquipItemOn(int p_pk, int pg_pk, int p_level)
	{
		RoleCache rc = new RoleCache();
		RoleEntity roleInfo = rc.getByPpk(p_pk);
		SpecialPropDAO dao = new SpecialPropDAO();
		PlayerPropGroupDao pdao = new PlayerPropGroupDao();
		PlayerPropGroupVO pvo = pdao.getByPgPk(pg_pk);
		PropVO ppvo = PropCache.getPropById(pvo.getPropId());

		int level = Integer.parseInt(getPropDisplay(ppvo.getPropReLevel()));

		if (level > p_level)
		{
			return 0;
		}

		SpecialPropVO vo = dao.getEquipPropByPgpk(String.valueOf(pg_pk));
		if (vo != null)
		{
			dao.updateEquipItemOn(String.valueOf(pg_pk));
			roleInfo.getEquipOnBody().getHunJieContent(1, p_pk, pvo.getPropId());
			pdao.deletePropGroup(pg_pk);
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(p_pk, 1);
			return 1;
		}
		else
		{
			dao.insertSpecialProp(ppvo, p_pk, pg_pk);
			roleInfo.getEquipOnBody().getHunJieContent(1, p_pk, pvo.getPropId());
			pdao.deletePropGroup(pg_pk);
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(p_pk, 1);
			return 1;
		}
	}

	/** �����ж��װ�� */
	public String getEquipItemOff(int p_pk)
	{
		String hint = null;

		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoById(p_pk + "");

		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipProp(p_pk);
		// �õ�����ʣ��Ŀռ�
		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();
		if (wrap_spare == 0)
		{
			hint = "�����񲻹��ˣ�";
			return hint;
		}
		else
		{
			int i = dao.getEquipItemHM(p_pk);
			if (i == 0)
			{
				return hint;
			}
			else
			{
				PropVO ppvo = PropCache.getPropById(i);
				PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
				propGroup.setPgType(ppvo.getPropPosition());
				propGroup.setPropBonding(ppvo.getPropBonding());
				propGroup.setPropProtect(ppvo.getPropProtect());
				propGroup.setPropIsReconfirm(ppvo.getPropReconfirm());
				propGroup.setPropUseControl(ppvo.getPropUseControl());
				propGroup.setPPk(p_pk);
				propGroup.setPropId(ppvo.getPropID());
				propGroup.setPropType(ppvo.getPropClass());
				propGroup.setPropNum(1);
				dao.addPropGroupEquipItem(propGroup, Integer.parseInt(vo
						.getPropoperate3()));
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(p_pk, -1);
				dao.updateEquipOff(vo.getSppk());
				roleInfo.getEquipOnBody().getHunJieContent(-1, p_pk,i);
				return hint;
			}
		}
	}

	/** �ж��Ƿ�ʹ����Ϊ0 ��0 ֹͣս�� */
	private int getEquipNum(int p_pk)
	{
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipProp(p_pk);
		if (vo != null)
		{

			String hm = vo.getPropoperate1();
			String[] x = hm.split(",");
			Integer intx1 = Integer.valueOf(x[1]);
			Integer intx2 = Integer.valueOf(x[4]);
			if (intx1 == 0 && intx2 == 0)
			{
				return 0;
			}
		}
		return 1;
	}

	/** ����ʹ��ʱ�� */
	private int getUserTime(int p_pk)
	{
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipProp(p_pk);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		if (vo != null)
		{

			String t1 = vo.getPropdate();
			try
			{
				Date d1 = dateformat.parse(t1);
				Date date = new Date();
				long i = (date.getTime() - d1.getTime()) / 60000;
				int t = Integer.parseInt(vo.getPropoperate2()) - (int) (i);
				if (t <= 0)
				{
					getEquipItemSign(p_pk, 3);
					return 0;
				}
				return t;
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
		}
		return -1;
	}

	/** ����ʹ��ʱ�� */
	public int getUserTimeByPgpk(int p_pk, String pg_pk)
	{
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipPropByPgpk(pg_pk);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String t1 = vo.getPropdate();
		try
		{
			Date d1 = dateformat.parse(t1);
			Date date = new Date();
			long i = (date.getTime() - d1.getTime()) / 60000;
			int t = Integer.parseInt(vo.getPropoperate2()) - (int) (i);
			if (t <= 0)
			{
				return 0;
			}
			return t;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/** ����װ�����ߵ� ���� */
	/*
	 * public String addEquipPropHpMp(int p_pk, int pg_pk, int prop_id, int
	 * prop_id1, int num, int type, String pg_pk1) { PlayerPropGroupDao pdao =
	 * new PlayerPropGroupDao(); PlayerPropGroupVO pgvo = pdao.getByPgPk(pg_pk);
	 * GoodsService gse = new GoodsService(); if (num > pgvo.getPropNum()) {
	 * return "�����������̫����"; } else { SpecialPropService se = new
	 * SpecialPropService(); PropDao dao = new PropDao(); PropVO prop =
	 * dao.getById(prop_id); PropVO prop1 = dao.getById(prop_id1);
	 * getEquipItemSign(p_pk, 0); int num1 = 0; if (type == 1) { num1 =
	 * se.updateEquipHpItemHP(prop, p_pk, num, pg_pk1); if (num1 == -1) { return
	 * "���ĵ����Ѿ�����"; } else { if (num1 == 0) { int add_hp =
	 * Integer.parseInt(prop.getPropOperate1()) num; gse.removeProps(p_pk,
	 * prop_id, num); return prop1.getPropName() + "δ����<br/>��ʹ����" +
	 * prop.getPropName() + "��" + num + "������HP" + add_hp; } else { int add_hp =
	 * Integer.parseInt(prop.getPropOperate1()) num1; gse.removeProps(p_pk,
	 * prop_id, num1); return prop1.getPropName() + "�Ѳ���<br/>��ʹ����" +
	 * prop.getPropName() + "��" + num1 + "������HP" + add_hp; } } } if (type == 2) {
	 * num1 = se.updateEquipHpItemMP(prop, p_pk, num, pg_pk1); if (num1 == -1) {
	 * return "���ĵ����Ѿ�����"; } else { if (num1 == 0) { int add_mp =
	 * Integer.parseInt(prop.getPropOperate1()) num; gse.removeProps(p_pk,
	 * prop_id, num); return prop1.getPropName() + "δ����<br/>��ʹ����" +
	 * prop.getPropName() + "��" + num + "������MP" + add_mp; } else {
	 * 
	 * int add_mp = Integer.parseInt(prop.getPropOperate1()) num1;
	 * gse.removeProps(p_pk, prop_id, num1); return prop1.getPropName() + "�Ѳ���<br/>��ʹ����" +
	 * prop.getPropName() + "��" + num1 + "������MP" + add_mp; } } } } return null; }
	 */

	/** �����߱�� �Դ��ж��Ƿ���ż�Ǹ���� */
	public void getEquipItemSign(int p_pk, int sign)
	{
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipProp(p_pk);
		if (vo != null && vo.getSign() == 3 && vo.getSign() == 4)
		{
			return;
		}
		else
		{
			dao.updateEquipItemSign(sign, p_pk);
		}
	}
	
	/** �����߱�� �Դ��ж��Ƿ���ż�Ǹ���� */
	public void getEquipItemSign1(int p_pk, int sign,int prop_id)
	{
		SpecialPropDAO dao = new SpecialPropDAO();
		dao.updateEquipItemSign1(sign, p_pk,prop_id);
		dao.updateall();
	}

	public String isUser(RoleEntity roleEntity)
	{
		SpecialPropDAO dao = new SpecialPropDAO();
		String display = "";
		SpecialPropVO vo = dao.getEquipProp(roleEntity.getBasicInfo().getPPk());
		if (vo != null)
		{
			PropVO propvo = PropCache.getPropById(vo.getPropid());
			if ( propvo!=null && propvo.getPropClass() == PropType.EQUIPPROP)
			{
				if (vo.getSign() != 3)
				{
					getUserTime(roleEntity.getBasicInfo().getPPk());
					if (vo.getSign() == 3)
					{

						display = "���ĵ���" + propvo.getPropName() + "ʹ��ʱ���ѵ�";
						return display;
					}
				}
				else
				{
					return null;
				}
			}
			if ( propvo!=null && propvo.getPropClass() == PropType.JIEHUN_JIEZHI)
			{
				if (vo.getSign() != 4)
				{
					int i = getUserDear(roleEntity.getBasicInfo().getPPk());
					if (i < 1)
					{

						display = "���" + propvo.getPropName()
								+ "�������۶��Ѿ�Ϊ��,�������Ӱ������۶ȷ��ɼ���ʹ��,ʹ��õ�廨�����Ӱ������۶�!";
						return display;
					}
				}
				else
				{
					return null;
				}
			}

		}

		return display;
	}

	private int getUserDear(int p_pk)
	{
		int i = new com.web.service.friend.FriendService().findLove_dear(p_pk);
		if (i <= 0)
		{
			getEquipItemSign(p_pk, 4);
		}
		return i;
	}

	public int delItem(int p_pk, int prop_id)
	{
		return new SpecialPropDAO().delItem(p_pk, prop_id);
	}

	public int[] getHpMpAdd(PartInfoVO player, RoleEntity roleEntity)
	{
		int[] x = new int[2];
		x[0] = -1;
		x[1] = -1;
		SpecialPropDAO dao = new SpecialPropDAO();
		SpecialPropVO vo = dao.getEquipProp(roleEntity.getBasicInfo().getPPk());
		if (vo != null)
		{
			if (vo.getSign() == 0)
			{
				String a = vo.getPropoperate1();
				String[] y = a.split(",");
				int y1 = Integer.valueOf(y[0]);
				int y2 = Integer.valueOf(y[3]);
				if (y1 != 0)
				{
					int hp = useEquipHpItemHP(player, vo);
					x[0] = hp;
				}
				if (y2 != 0)
				{
					int mp = useEquipMpItemMP(player, vo);
					x[1] = mp;
				}
			}
		}
		return x;
	}
}