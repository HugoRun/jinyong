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

	/** �õ���ҿ���ʹ�õ��䷽�б� */
	public List<SynthesizeVO> getSynthesizePropList(int s_type, int s_level,
			int thispage, int perpagenum)
	{
		SynthesizeDao dao = new SynthesizeDao();
		return dao.getSynthesizeList(s_type, s_level, thispage, perpagenum);
	}

	/** ���ݵȼ��ͼ������͵õ���ҿ���ʹ�õ��䷽ */
	public List<SynthesizeVO> getSynthesizeProp(int s_type, int s_level)
	{
		SynthesizeDao dao = new SynthesizeDao();
		return dao.getSynthesize(s_type, s_level);

	}

	/** �õ��䷽������Ʒ������ */
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

	/** ���Ʒ�ҳ */
	public List<String> getSynthesizePropInfoList(int s_type, int s_level,
			int thispage, int perpagenum)
	{
		List list = getSynthesizePropInfo(s_type, s_level);
		int totalSize = list.size();
		List<String> currentPageV = new ArrayList<String>();
		/** ��ҳ��: */
		int totalPageNum = totalSize / perpagenum;

		if (totalSize % perpagenum > 0)
		{
			totalPageNum = totalSize / perpagenum + 1;
		}
		/** ��ǰ��ҳ��:pageNum�� */

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

	/** �õ��䷽ID */
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

	/** �õ��䷽ID���ļ��������� */
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

	/** �õ��䷽ID��Ҫ�ļ��������� */
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

	/** �����䷽ID�õ�ʹ�ø��䷽��ü��������� */
	public int getSynthesizeSleight(int s_id)
	{
		SynthesizeDao dao = new SynthesizeDao();
		return dao.getSynthesizeSleight(s_id);
	}

	/**
	 * ���ԭ���ϵ���ʾ�ַ���
	 * 
	 * @param vo
	 * @return
	 */
	public List<String> getSynthesizeList(SynthesizeVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getProp(); // ��Ҫ�һ���ԭ����
		// String operate2 = vo.getSynthesizeProp(); //�һ���Ŀ��Ʒ
		String[] reChange = operate1.split(";"); // ��Ҫ�һ���ԭ���ϵ�����
		// String[] reChange2 = operate2.split(";"); //�һ���Ŀ��Ʒ������
		if (reChange == null)
		{
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;

		for (int i = 0; i < length; i++)
		{
			String[] materials = reChange[i].split(","); // ÿ���һ���ԭ���Ͽ��ܲ�ֹһ��
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for (int a = 0; a < materials.length; a++)
			{
				String[] unit = materials[a].split("-");
				if (unit[0].equals("d"))
				{ // ���ԭ�����ǵ��ߵĴ������
					PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
					sb.append("-").append(propvo.getPropName()).append("��").append(unit[3]);
				}
				else
					if (unit[0].equals("z"))
					{ // ���ԭ������װ���Ĵ������
						sb.append("-");
						int equip_id = Integer.valueOf(unit[1]);
						GameEquip equip = EquipCache.getById(equip_id);
						sb.append(equip.getName()).append("��").append(unit[2]);
					}
					else
						if (unit[0].equals("j"))
						{ // ���ԭ�����ǽ�Ǯ�Ĵ������
							sb.append("-").append(
									MoneyUtil.changeCopperToStr(unit[1]));

						}
			}
			logger.info("i��value: " + i + " ԭ����װ���ֶ�" + sb.toString());
			list.add(sb.toString());
		}
		return list;
	}

	/**
	 * ��úϳ���Ʒ����ʾ�ַ���
	 * 
	 * @param vo
	 * @return
	 */
	public List<String> getSynthesizeChangeList(SynthesizeVO vo)
	{
		List<String> list = null;
		String operate = vo.getSynthesizeProp(); // �һ���Ŀ��Ʒ
		String[] reChange = operate.split(";"); // �һ���Ŀ��Ʒ������
		if (reChange == null)
		{
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		for (int i = 0; i < length; i++)
		{
			StringBuffer sb = new StringBuffer();
			String[] materials = reChange[i].split(","); // ÿ���һ��Ķһ�Ʒ���ܲ�ֹһ��

			for (int a = 0; a < materials.length; a++)
			{ // ��ÿ���һ�Ʒ���в�ͬ�Ĵ���
				String[] unit = materials[a].split("-");
				if (unit[0].equals("z"))
				{ // ����һ�Ʒ��װ���Ĵ������
					int equip_id = Integer.valueOf(unit[1]);
					GameEquip equip = EquipCache.getById(equip_id);
					sb.append(equip.getName());
					// sb.append("��").append(unit[3]);
				}
				else
					if (unit[0].equals("d"))
					{ // ����һ�Ʒ�ǵ��ߵĴ������
						PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
						propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
						sb.append(propvo.getPropName());
						// sb.append("��").append(unit[3]);
					}
				if (a + 1 != materials.length)
				{
					sb.append("-");
				}
			}
			logger.info("i��value: " + i + " �һ�Ʒװ���ֶ�" + sb.toString());
			list.add(sb.toString());
		}
		return list;
	}

	/** ��úϳ�����Ʒ������ */
	public String getSynthesizePropName(SynthesizeVO vo)
	{
		String operate = vo.getSynthesizeProp(); // �һ���Ŀ��Ʒ
		String[] materials = operate.split("-"); // ÿ���һ��Ķһ�Ʒ���ܲ�ֹһ��
		if (materials[0].equals("z"))
		{ // ����һ�Ʒ��װ���Ĵ������
			int equip_id = Integer.valueOf(materials[1]);
			GameEquip equip = EquipCache.getById(equip_id);
			return equip.getName();
		}
		else
			if (materials[0].equals("d"))
			{ // ����һ�Ʒ�ǵ��ߵĴ������
				PropVO propvo = PropCache.getPropById(Integer.valueOf(materials[2]));
				propvo = PropCache.getPropById(Integer.valueOf(materials[2]));
				return propvo.getPropName();
			}
		return "";
	}

	// �������Ƿ����㹻�İ����Ա����ɶһ�������Ʒ
	public String getHasWareSpare(String pPk, SynthesizeVO vo, String address,
			int exchange_num)
	{
		int i = 1;
		String result = "";
		RoleEntity role_info = RoleService.getRoleInfoById(pPk);
		int warespare = role_info.getBasicInfo().getWrapSpare(); // �õ���Ұ���ʣ��ո���

		String[] articles = vo.getSynthesizeProp().split(";"); // ���жһ�Ʒ���ַ���
		String article = articles[Integer.valueOf(address)]; // ��Ҫ�һ��һ�Ʒ���ַ���
		logger.info("�һ�Ʒ��article:" + article);

		String[] recourses = article.split(",");
		for (int t = 0; t < recourses.length; t++)
		{

			String[] recourse = recourses[t].split("-");
			if (recourse[0].equals("z"))
			{
				// ���ϰ����ռ�С�����ܷ���Ŀռ�ʱ
				if (warespare < Integer.valueOf(recourse[3]) * exchange_num)
				{
					i = -1;
					result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
				}
			}
			else
				if (recourse[0].equals("d"))
				{
					PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
					int accumulate = propvo.getPropAccumulate();
					if (accumulate == 1)
					{ // ���ڵ��߲��ɵ��ӵ����
						if (warespare < Integer.valueOf(recourse[3])
								* exchange_num)
						{
							i = -1;
							result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
						}
					}
					else
					{ // ���ڵ��߿ɵ��ӵ����
						if (Integer.valueOf(recourse[3]) * exchange_num <= accumulate)
						{
							if (warespare < 1)
							{
								i = -1;
								result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
							}
						}
						else
						{
							int requireNum = Integer.valueOf(recourse[3])
									* exchange_num / accumulate + 1;
							if (warespare < requireNum)
							{
								i = -1;
								result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
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
	 * �жϸ��˵�ԭ�����Ƿ��㹻 �����ԭ�����ǵ��ߺ���װ������� �ֱ�������ж�
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @param address
	 *            ��Ҫ�һ�ԭ����������Դ���ϵ�λ�ã���0��ʼ����
	 * @param menu_id
	 *            Ŀ¼id
	 * @return ����ɹ�����1�����򷵻�-1.
	 */
	public String getPPkHasGoods(String pPk, String address, SynthesizeVO vo,
			int exchange_num)
	{
		int i = 1;
		String sb = "";
		String[] articles = vo.getProp().split(";"); // ����ԭ���ϵ��ַ���

		String article = articles[Integer.valueOf(address)]; // ��Ҫ�һ�ԭ���ϵ��ַ���
		logger.info("ԭ���ϵ�article:" + article);

		String[] recourses = article.split(",");
		for (int t = 0; t < recourses.length; t++)
		{

			String[] recourse = recourses[t].split("-");

			if (recourse[0].equals("d"))
			{
				int number = Integer.valueOf(recourse[3]); // ԭ������Ҫ�ĸ���
				number = exchange_num * number; // �һ�����Ҫ�ĸ���
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int current_num = propGroupDao.getPropNumByByPropID(Integer
						.valueOf(pPk), Integer.valueOf(recourse[2]));// ���е�����
				if (current_num < number)
				{
					i = -1;
					sb = "�����ϵ�ԭ���ϲ��㣡";
				}
			}
			else
				if (recourse[0].equals("z"))
				{
					int number = Integer.valueOf(recourse[3]); // ԭ������Ҫ�ĸ���
					number = exchange_num * number; // �һ�����Ҫ�ĸ���
					// int wrapClass =
					// storageService.getGoodsClass(Integer.valueOf(recourse[2]),Integer.valueOf(recourse[1]));
					// ����װ��
					// String wrapStr = recourse[2]+
					// ","+String.valueOf(wrapClass)+","+recourse[1];
					// logger.info("װ���ַ���:"+wrapStr);
					PlayerEquipDao equipDao = new PlayerEquipDao();
					int equip_num = equipDao.getEquipNumByEquipId(Integer
							.valueOf(pPk), Integer.valueOf(recourse[2]),
							Integer.valueOf(recourse[1]));
					if (equip_num < number)
					{
						i = -1;
						sb = "�����ϵ�ԭ���ϲ��㣡";
					}
				}
				else
					if (recourse[0].equals("j"))
					{
						PartInfoDao partInfoDao = new PartInfoDao();
						PartInfoVO partInfoVO = partInfoDao
								.getPartInfoByID(Integer.valueOf(pPk));
						long copper = Long.valueOf(partInfoVO.getPCopper());
						copper = exchange_num * copper; // �һ�����Ҫ�Ľ�Ǯ
						if (copper < Long.valueOf(recourse[1]))
						{
							i = -1;
							sb = "�����Ͻ�Ǯ���㣡";
						}
					}

		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(sb);
		return sbf.toString();
	}

	/**
	 * �һ���Ʒ
	 */
	public String exchangeGoods(String pPk, SynthesizeVO vo, String address,
			int exchange_num)
	{
		StringBuffer resultWml = new StringBuffer();
		String[] articles = vo.getProp().split(";"); // ����ԭ���ϵ��ַ���
		String[] dharticles = vo.getSynthesizeProp().split(";"); // ���жһ�Ʒ���ַ���

		String article = articles[Integer.valueOf(address)]; // ��Ҫ�һ�ԭ���ϵ��ַ���
		String dharticle = dharticles[Integer.valueOf(address)]; // ��Ҫ�һ��һ�Ʒ���ַ���

		getMaterialsFromWrap(pPk, article, exchange_num); // �����������ȥ���һ���ԭ����
		String getExchangeGoods = sendDuiHuanToWrap(pPk, dharticle,
				exchange_num); // ����ҷ��Ŷһ�Ʒ

		resultWml.append("�����ѳɹ���");
		resultWml.append(getExchangeGoods);
		return resultWml.toString();
	}

	// ����ҷ��Ŷһ�Ʒ
	private String sendDuiHuanToWrap(String pPk, String dharticle,
			int exchange_num)
	{
		if (dharticle.equals("0"))
		{
			logger.info("������ҷ�����Ʒ��");
			return "";
		}
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("��������");
		String[] articles = dharticle.split(",");
		for (int t = 0; t < articles.length; t++)
		{
			String[] unit = articles[t].split("-");
			if (unit[0].equals("d"))
			{ // ������������ӵ���
				GoodsService goodsService = new GoodsService();
				goodsService.putGoodsToWrap(Integer.valueOf(pPk), Integer
						.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer
						.valueOf(unit[3])
						* exchange_num);
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.valueOf(unit[2]), 4, Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
				

				resultWml.append(goodsService.getGoodsName(Integer
						.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("��").append(
						Integer.valueOf(unit[3]) * exchange_num);
			}
			else
				if (unit[0].equals("z"))
				{ // �������������װ��
					GoodsService goodsService = new GoodsService();
					for (int i = 0; i < Integer.valueOf(unit[3]); i++)
					{ // Ҫ������װ���͸�����
						goodsService.putGoodsToWrap(Integer.valueOf(pPk),
								Integer.valueOf(unit[2]), Integer
										.valueOf(unit[1]), Integer
										.valueOf(unit[3])
										* exchange_num);
						//ִ��ͳ��
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
						
						
					}

					resultWml.append(goodsService.getGoodsName(Integer
							.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("��").append(
							Integer.valueOf(unit[3]) * exchange_num);
				}
			if (t + 1 < articles.length)
				resultWml.append(",");
		}
		resultWml.append(".");
		return resultWml.toString();
	}

	// �����������ȥ���һ���ԭ����
	private String getMaterialsFromWrap(String pPk, String article,
			int exchange_num)
	{
		if (article.equals("0"))
		{
			logger.info("����������Ͽ۳������Ʒ��");
			return "";
		}
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("��ʧȥ��");
		String[] articles = article.split(",");
		for (int t = 0; t < articles.length; t++)
		{
			String[] unit = articles[t].split("-");
			if (unit[0].equals("d"))
			{ // ��������ϼ�ȥ����
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(Integer.valueOf(pPk), Integer
						.valueOf(unit[2]), Integer.valueOf(unit[3])
						* exchange_num,GameLogManager.R_EXCHANGE);
				resultWml.append(goodsService.getGoodsName(Integer
						.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("��").append(
						Integer.valueOf(unit[3]) * exchange_num);
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3]) * exchange_num, StatisticsType.USED, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
				
			}
			else
				if (unit[0].equals("z"))
				{ // ��������ϼ�ȥװ��

					GoodsService goodsService = new GoodsService();
					for (int a = 0; a < Integer.valueOf(unit[2])*exchange_num; a++)
					{
						goodsService.removeEquipByEquipID(Integer.valueOf(pPk), Integer.valueOf(unit[1]));
					}
					resultWml.append(goodsService.getGoodsName(Integer
							.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("��").append(
							Integer.valueOf(unit[3]) * exchange_num);
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3]) * exchange_num, StatisticsType.USED, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
					
				}
				else
					if (unit[0].equals("j"))
					{
						RoleService roleService = new RoleService();
						RoleEntity roleInfo = roleService.getRoleInfoById(pPk);
						
						//���
						LogService logService = new LogService();
						logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", "-"+unit[1], "��������");
						
						// ��������ϼ�ȥ��Ǯ
						roleInfo.getBasicInfo().addCopper(-Integer.valueOf(unit[1]));
						resultWml.append(MoneyUtil.changeCopperToStr(Integer
								.valueOf(unit[1])
								* exchange_num));
						//ִ��ͳ��
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(6, StatisticsType.MONEY, Integer.parseInt(unit[1]), StatisticsType.USED, StatisticsType.SHENGCHAN,Integer.parseInt(pPk));
						
					}
			if (t + 1 < articles.length)
				resultWml.append(",");
		}
		resultWml.append(",");
		return resultWml.toString();
	}

	/** �õ��������ԭ���ϵ����� */
	public List<String> getPlayerPropList(int p_pk, SynthesizeVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getProp(); // ��Ҫ�һ���ԭ����
		// String operate2 = vo.getSynthesizeProp(); //�һ���Ŀ��Ʒ
		String[] reChange = operate1.split(";"); // ��Ҫ�һ���ԭ���ϵ�����
		// String[] reChange2 = operate2.split(";"); //�һ���Ŀ��Ʒ������
		if (reChange == null)
		{
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;

		for (int i = 0; i < length; i++)
		{
			String[] materials = reChange[i].split(","); // ÿ���һ���ԭ���Ͽ��ܲ�ֹһ��
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for (int a = 0; a < materials.length; a++)
			{
				String[] unit = materials[a].split("-");
				if (unit[0].equals("d"))
				{ // ���ԭ�����ǵ��ߵĴ������
					PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
					PlayerPropGroupDao dao = new PlayerPropGroupDao();
					int num = dao.getPropNumByByPropID(p_pk, Integer
							.valueOf(unit[2]));
					sb.append("-");

					sb.append(propvo.getPropName());
					sb.append("��").append(num);
				}
				else
					if (unit[0].equals("z"))
					{ // ���ԭ������װ���Ĵ������
						sb.append("-");
						
						int equip_id = Integer.valueOf(materials[1]);
						GameEquip equip = EquipCache.getById(equip_id);
						
						PlayerEquipDao dao = new PlayerEquipDao();
						int num = dao.getEquipNum(p_pk, equip_id);
						
						sb.append(equip.getName());
						sb.append("��").append(num);
					}
					else
						if (unit[0].equals("j"))
						{ // ���ԭ�����ǽ�Ǯ�Ĵ������
							PartInfoDao partInfoDao = new PartInfoDao();
							PartInfoVO partInfoVO = partInfoDao
									.getPartInfoByID(p_pk);
							Long copper = Long.valueOf(partInfoVO
									.getPCopper());
							sb.append("-").append("����").append(
									MoneyUtil.changeCopperToStr(copper));

						}
			}
			logger.info("i��value: " + i + " ԭ����װ���ֶ�" + sb.toString());
			list.add(sb.toString());
		}
		return list;
	}

	/** �õ��������ԭ���ϵ����� */
	public int getNum(int p_pk, SynthesizeVO vo)
	{
		List<Integer> list = null;
		String operate1 = vo.getProp(); // ��Ҫ�һ���ԭ����
		// String operate2 = vo.getSynthesizeProp(); //�һ���Ŀ��Ʒ
		String[] reChange = operate1.split(";"); // ��Ҫ�һ���ԭ���ϵ�����
		// String[] reChange2 = operate2.split(";"); //�һ���Ŀ��Ʒ������
		if (reChange == null)
		{
			return 0;
		}
		list = new ArrayList<Integer>();
		int length = reChange.length;
		for (int i = 0; i < length; i++)
		{
			String[] materials = reChange[i].split(","); // ÿ���һ���ԭ���Ͽ��ܲ�ֹһ��
			StringBuffer sb = new StringBuffer();
			for (int a = 0; a < materials.length; a++)
			{
				String[] unit = materials[a].split("-");
				if (unit[0].equals("d"))
				{ // ���ԭ�����ǵ��ߵĴ������
					PlayerPropGroupDao dao = new PlayerPropGroupDao();
					int num = dao.getPropNumByByPropID(p_pk, Integer
							.valueOf(unit[2]));
					int all = Integer.valueOf(num / Integer.valueOf(unit[3]));
					list.add(all);
				}
				else
					if (unit[0].equals("z"))
					{ // ���ԭ������װ���Ĵ������
						sb.append("-");
						
						int equip_id = Integer.valueOf(materials[1]);
						int need_num = Integer.valueOf(unit[2]);
						
						PlayerEquipDao dao = new PlayerEquipDao();
						int have_num = dao.getEquipNum(p_pk, equip_id);
						
						list.add(have_num/need_num);
					}
					else
						if (unit[0].equals("j"))
						{ // ���ԭ�����ǽ�Ǯ�Ĵ������
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
