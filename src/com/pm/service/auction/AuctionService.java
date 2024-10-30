package com.pm.service.auction;

import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.sellinfo.SellInfoDAO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.ActionType;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.constant.AuctionNumber;
import com.pm.dao.auction.AuctionDAO;
import com.pm.dao.auction.AuctionInfoDAO;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.auction.AuctionInfoVO;
import com.pm.vo.auction.AuctionVO;

public class AuctionService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * ����װ��
	 * 
	 * @param p_pk
	 * @param accouter_id
	 *            װ��id
	 * @param accouter_num
	 *            װ������
	 */
	public String removeAccouters(int u_pk, int p_pk, int pwPk, int propPrice,
			int payType, int auctionPrice)
	{
		StringBuffer resultWml = new StringBuffer();

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		PlayerEquipVO equip = playerEquipDao.getByID(pwPk);
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		// �ж�Ҫ��������Ʒ�Ƿ��Ѿ����������˵���Ʒ
		String result = sellInfoDAO.getSellExistByPPkAndGoodsId(p_pk + "",
				equip.getEquipId() + "", GoodsType.EQUIP);

		if (result != null && !result.equals(""))
		{
			return result;
		}

		// �ж�װ���;�Ϊ0��װ����������
		if (equip.isEffected() == false)
		{
			return "�����װ������������";
		}

		GoodsService goodsService = new GoodsService();
		// �󶨵�װ�����ɽ��ײ�������
		String flag = goodsService.isBinded(equip.getPwPk(), GoodsType.EQUIP,
				ActionType.AUCTION);
		if (flag == null)
		{

			AuctionDAO auctionDao = new AuctionDAO();

			int auctionType = equip.getAuctionType();

			// ���İ������λ��
			PlayerEquipDao PlayerEquipDao = new PlayerEquipDao();
			int deleteValue = PlayerEquipDao.updatePosition(pwPk,
					Equip.ON_STORAGE);

			if (deleteValue != 0)
			{
				// �������������װ��
				auctionDao.addToAuction(u_pk, p_pk, equip, auctionType,
						propPrice, payType, auctionPrice);

				// ���˰���������һ
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(p_pk, 1);

				// ���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(),
						roleInfo.getBasicInfo().getName(), roleInfo
								.getBasicInfo().getCopper()
								+ "",
						-(int) (propPrice * (1 - AuctionNumber.AUCTIONNUMBER))
								+ "", "������˰");

				// �Ӹ������ϳ�ȥӦ��˰1Ϊ��ʯ2ΪԪ��
				if(payType==2)
				{
					//������������Ҫ�Ƚ���100�ɾ�
					EconomyService es = new EconomyService();
					es.spendYuanbao(roleInfo.getBasicInfo().getUPk(),100);
				}

				String money = payType == 1 ? "��ʯ" : "�ɾ�";
				int moneyNum=payType==1?0:100;
				resultWml.append("����" + propPrice + "" + money + "�ļ۸�������"
						+ StringUtil.isoToGBK(equip.getWName()) + ",��ȡ������"
						+ moneyNum
						+ "" + money + ", ��Ҫ�����������");
			}
			else
			{
				resultWml.append("����������Ʒ�����ǹ�������!");
			}
		}
		else
		{
			resultWml.append(flag);
		}
		return resultWml.toString();
	}

	/**
	 * ���ӵ��ߵ�������
	 * 
	 * @param pg_pk
	 * @param remove_num
	 * @param userTempBean
	 * @param propPrice
	 * @return
	 */
	public String addPropToAuction(int pg_pk, int remove_num,
			RoleEntity roleEntity, int propPrice, int payType, int auctionPrice)
	{
		logger.info("auctionService�е�Ҫ�����ĵ�������Ϊ:" + remove_num);
		AuctionDAO auctionDao = new AuctionDAO();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(pg_pk);

		StringBuffer resultWml = new StringBuffer();
		int propId = propGroup.getPropId();

		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		String result = sellInfoDAO.getSellExistByPPkAndGoodsId(propGroup
				.getPPk()
				+ "", propId + "", GoodsType.PROP);

		if (result != null && !result.equals(""))
		{
			return result;
		}

		// PropDao propDao = new PropDao();
		PropCache propCache = new PropCache();
		int auctionPosition = propCache.getPropAuctionPositionById(propId);
		logger.info("����λ��:" + auctionPosition);

		if (propGroup.getPropType() == PropType.EQUIPPROP
				|| propGroup.getPropType() == PropType.BOX_CURE)
		{
			return "�������,�������ֿ�!";
		}

		if (auctionPosition == 0)
		{
			resultWml.append("�˵��߲�����������");
		}
		else
		{
			GoodsService goodsService = new GoodsService();
			String flag = goodsService.isBinded(pg_pk, GoodsType.PROP,
					ActionType.EXCHANGE);
			if (flag == null)
			{
				// ɾ��������ĵ���
				removePropsFromWrap(propGroup, remove_num);

				// ������������ĵ���
				auctionDao.addPropToAuction(roleEntity.getBasicInfo().getUPk(),
						roleEntity.getBasicInfo().getPPk(), propGroup
								.getPropId(), GoodsType.PROP, propPrice,
						StringUtil.isoToGBK(propGroup.getPropName()),
						remove_num, propGroup, payType, auctionPrice);

				// ���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(),
						roleEntity.getBasicInfo().getName(), roleEntity
								.getBasicInfo().getCopper()
								+ "",
						-(int) (propPrice * (1 - AuctionNumber.AUCTIONNUMBER))
								+ "", "������˰");

				// �Ӹ������ϳ�ȥӦ��˰1Ϊ��ʯ2ΪԪ��
				if(payType==2)
				{
					EconomyService es = new EconomyService();
					es
							.spendYuanbao(
									roleEntity.getBasicInfo().getUPk(),
									(int) (propPrice * (1 - AuctionNumber.AUCTIONNUMBER)));
				}

				String money = payType == 1 ? "��ʯ" : "�ɾ�";
				int moneyNum=payType==1?0:100;
				resultWml.append("����" + propPrice + "" + money + "�ļ۸�������"
						+ StringUtil.isoToGBK(propGroup.getPropName())
						+ ",��ȡ������"
						+ moneyNum
						+ "" + money + ", ��Ҫ�����������");
			}
			else
			{
				resultWml.append(flag);
			}
		}
		return resultWml.toString();
	}

	/** �Ӱ�����ж��һ����� */
	private boolean removePropsFromWrap(PlayerPropGroupVO propGroup,
			int remove_num)
	{
		boolean flag = false;
		if (propGroup == null)
		{
			logger.info("propGroupΪ��");
			return false;
		}
		logger.info("ж���ĵ�������:" + remove_num);
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		if (propGroup.getPropNum() == remove_num) // �Ƴ����������ڵ���������
		{
			propGroupDao.deletePropGroup(propGroup.getPgPk());
			// ����������˰�������һ���ո�.
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(propGroup.getPPk(), 1);
			flag = true;
		}
		else
			if (propGroup.getPropNum() > remove_num)// //�Ƴ�������С�ڵ���������
			{
				propGroupDao.updatePropGroupNum(propGroup.getPgPk(), propGroup
						.getPropNum()
						- remove_num);
				flag = true;
			}
			else
				if (propGroup.getPropNum() < remove_num)
				{

				}
		return flag;
	}

	/**
	 * ��ҳ:�õ����������auctionType���͵����е���
	 * 
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePropList(int p_pk, int auctionType, int page_no,
			String sortType, int payType)
	{
		AuctionDAO auctionDao = new AuctionDAO();
		return auctionDao.getPagePropsByPpk(p_pk, auctionType, page_no,
				sortType, payType);
	}

	/**
	 * ��ҳ:�õ�����������ض����͵���Ʒ��
	 * 
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePropByName(int p_pk, String propName, int page_no,
			String sortType, int payType, int auctionType)
	{
		AuctionDAO auctionDao = new AuctionDAO();
		return auctionDao.getPagePropByName(p_pk, propName, page_no, sortType,
				payType, auctionType);
	}

	/**
	 * ����װ��, ��װ����������ת����ҵİ���
	 * 
	 */
	public synchronized String setToWrap(int pPk, String auction_id,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";

		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO vo = auctionDao.getAuctionVOById(auction_id + "");

		if (roleInfo.getBasicInfo().getPPk() == vo.getPPk())
		{
			return "�����ܹ����Լ�����Ʒ!";
		}

		if (vo.getAuctionSell() != 1)
		{
			return "����Ʒ���ھ����У��۸��ߵ�!";
		}
		logger.info(" ������id : " + vo.getGoodsId());
		logger.info("�����ߵ�id:" + pPk + " ��Ʒ�����ߵ�id:" + vo.getPPk());
		// �������������û�Ҫ���װ��
		int buy = updateFromAuction(pPk, Integer.parseInt(auction_id));
		if (buy == -1)
		{
			resultWml = "����ʧ��,��װ���Ѿ�����";
			return resultWml;
		}
		else
		{
			resultWml = "����ɹ�,����ȥ�����ֿ���ȡ�Լ���װ��";
			// ���½�Ǯ
			int needmoney = vo.getGoodsPrice();
			if (vo.getPay_type() == 1)
			{
				roleInfo.getBasicInfo().addCopper(-needmoney);
			}
			else
			{
				EconomyService es = new EconomyService();
				es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needmoney);
			}
		}

		// ���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", -vo.getGoodsPrice() + "", "������װ��");

		// ͳ����Ҫ
		new RankService().updateAdd(vo.getPPk(), "sale", 1);

		return resultWml;
	}

	/** ****����þ��ļ۹���ʱ������������Ʒ״̬ */
	public synchronized String setToWrapByAuction(int pPk, String auction_id,
			int buyPrice)
	{
		String resultWml = "";

		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO vo = auctionDao.getAuctionVOById(auction_id + "");

		if (roleInfo.getBasicInfo().getPPk() == vo.getPPk())
		{
			return "�����ܹ����Լ�����Ʒ!";
		}

		if (vo.getAuctionSell() == 2)
		{
			return "����Ʒ���ھ����У��۸��ߵ�!";
		}
		logger.info(" ������id : " + vo.getGoodsId());
		logger.info("�����ߵ�id:" + pPk + " ��Ʒ�����ߵ�id:" + vo.getPPk());
		/** *���UPK��Ϊ���˻����ϴξ�����Ԫ���õ�** */
		int u_pk = vo.getAuction_upk();
		int p_pk = vo.getAuction_ppk();
		int money = vo.getBuyPrice();
		// �������������û�Ҫ���װ��
		int buy = updateFromAuctionByAuction(Integer.parseInt(auction_id),
				roleInfo.getBasicInfo().getUPk(), pPk, buyPrice, roleInfo
						.getBasicInfo().getName());
		if (buy == -1)
		{
			resultWml = "����ʧ��ʧ�ܣ�����Ʒ�Ѿ�����";
			return resultWml;
		}
		else
		{
			resultWml = "������" + buyPrice + "��ʯ�ļ۸���" + vo.getGoodsName()
					+ "����ʮ���������˲������ľ��ģ����ĳɹ�����ע��鿴�����ʼ�";
			// ���½�Ǯ
			int needmoney =buyPrice;
			if (vo.getPay_type() == 1)
			{
				// ��ȥ�µľ����ߵ���ʯ
				roleInfo.getBasicInfo().addCopper(-needmoney);
				// ����ԭ�������ߵ���ʯ
				if(u_pk!=0&&p_pk!=0)
				{
					RoleEntity roleInfoFalse = RoleService.getRoleInfoById(p_pk
							+ "");
					if (roleInfoFalse != null)
					{
						roleInfoFalse.getBasicInfo().addCopper(money);
					}
					else
					{
						auctionDao.addCoopperForFalseAuction(p_pk, money);
					}
				}
			}
			else
			{
				EconomyService es = new EconomyService();
				// ��ȥ�µľ����ߵ�Ԫ��
				es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needmoney);
				// ����ԭ�������ߵ�Ԫ��
				es.addYuanbao(u_pk, money);
			}
			// �����ʼ�����һ�������߸�֪�侺��ʧ����Ǯ���Ѿ��˻�
			sendAuctionFalseMailAuction(vo, p_pk);

		}
		return resultWml;
	}

	/**
	 * �����ģ��ɶ�ʱ��ÿ5����ִ��һ�Σ��õ�10������û�б��˲��뾺�ĵ���Ʒ���ĳɹ�����ҷ�����Ʒ�����ʼ�������ƷΪ����״̬
	 */
	public void processAuction()
	{
		AuctionDAO auctionDao = new AuctionDAO();
		List list = auctionDao.getAuctionSuccess();
		for (int i = 0; i < list.size(); i++)
		{
			AuctionVO av = (AuctionVO) list.get(i);
			updateFromAuction(av.getAuction_ppk(), av.getUAuctionId());
		}
	}

	/**
	 * �����������ɶ�ʱ��ÿ��ִ��һ�����������������Ļ��߹��ڵ���Ʒ
	 */
	public void processAuctionFalse()
	{
		/** �������컹δ������¼�* */
		updateThanThreeDay();

		/** �Կ�ʼ����������δȡ�ص�������Ʒ������ϵͳû��* */
		updateThanSixDay();

		/** �������ɹ������ڣ�δȡ�ص�������Ǯ��ϵͳû��* */
		updateMoneySevenDay();
	}

	// �������ɹ������ڣ�δȡ�ص�������Ǯ��ϵͳû��
	private void updateMoneySevenDay()
	{
		AuctionInfoDAO auctionInfoDAO = new AuctionInfoDAO();
		AuctionDAO auctionDao = new AuctionDAO();

		// ��������ɹ�������δȡ��������Ǯ���б�
		List<AuctionVO> list = auctionDao.getThanSevenDay();
		MailInfoService mailInfo = new MailInfoService();

		if (list != null || list.size() != 0)
		{
			// �������ɹ�������δȡ��������Ǯ�������߷�������ʾ��������Ϣ��.
			String info1 = "������";
			String info2 = "�ɹ��Ѿ��������죬������û�м�ʱ��ȡ����������Щ�����Ѿ���ϵͳ�ջأ�";
			String star = "��";
			String title = "��������Ϣ��ʾ";
			String info3 = "ϵͳ��Ϣ����ظ���";
			AuctionVO vo = null;
			for (int i = 0; i < list.size(); i++)
			{
				vo = list.get(i);
				auctionInfoDAO.insertAuctionInfo(vo, info1
						+ StringUtil.isoToGBK(vo.getGoodsName()) + star
						+ vo.getGoodsNumber() + info2);
				// �������ɹ����յ������߷��ʼ�
				mailInfo.sendMailBySystem(vo.getPPk(), title, info1
						+ StringUtil.isoToGBK(vo.getGoodsName()) + info2 + info3);
			}
		}

		auctionDao.updateMoneySevenDay();
	}

	// �Կ�ʼ����������δȡ�ص�������Ʒ������ϵͳû��
	private void updateThanSixDay()
	{
		AuctionInfoDAO auctionInfoDAO = new AuctionInfoDAO();
		AuctionDAO auctionDao = new AuctionDAO();

		// �������������δȡ�ص�������Ʒ�б�
		List<AuctionVO> list = auctionDao.getThanSixDayList();
		MailInfoService mailInfo = new MailInfoService();

		if (list != null || list.size() != 0)
		{
			// ������������δȡ������Ʒ�������߷�������ʾ��������Ϣ��.
			String info1 = "����������";
			String info2 = "���������Ѿ��������죬������û�м�ʱ��ȡ����Ʒ������Ʒ�Ѿ��ᱻϵͳ�ջأ�";
			String title = "��������Ϣ��ʾ";
			String star = "��";
			String info3 = "ϵͳ��Ϣ����ظ���";
			AuctionVO vo = null;
			for (int i = 0; i < list.size(); i++)
			{
				vo = list.get(i);
				auctionInfoDAO.insertAuctionInfo(vo, info1
						+ StringUtil.isoToGBK(vo.getGoodsName()) + star
						+ vo.getGoodsNumber() + info2);
				// ���������յ������߷��ʼ�
				mailInfo.sendMailBySystem(vo.getPPk(), title, info1
						+ StringUtil.isoToGBK(vo.getGoodsName())+ info2 + info3);
			}
		}

		auctionDao.deleteThanSixDay();
	}

	// �������컹δ������¼�,������Ϣ��������Ϣ�������ʼ���������
	private void updateThanThreeDay()
	{
		AuctionInfoDAO auctionInfoDAO = new AuctionInfoDAO();
		AuctionDAO auctionDao = new AuctionDAO();
		// �������δ�����������Ʒ�б�
		List<AuctionVO> list = auctionDao.getThanThreeDayList();
		MailInfoService mailInfo = new MailInfoService();

		if (list!=null || list.size() != 0)
		{
			// ���������������Ʒ�������߷�������ʾ��������Ϣ��.
			String info1 = "����������";
			String info2 = "����ʱ���Ѿ��������죬�����˳������������������ڵ��������ֿ�ȡ����Ʒ��";
			String title = "��������Ϣ��ʾ";
			String star = "��";
			String info3 = "ϵͳ��Ϣ����ظ���";
			AuctionVO vo = null;
			for (int i = 0; i < list.size(); i++)
			{
				vo = list.get(i);
				if(vo.getAuctionFailed()!=2)
				{
					auctionInfoDAO.insertAuctionInfo(vo, info1
							+ StringUtil.isoToGBK(vo.getGoodsName()) + star
							+ vo.getGoodsNumber() + info2);
					// ����������������߷��ʼ�
					mailInfo.sendMailBySystem(vo.getPPk(), title, info1
							+ StringUtil.isoToGBK(vo.getGoodsName()) + info2 + info3);
				}
			}
		}

		auctionDao.updateThanThreeDay();

	}

	/**
	 * �����û������Ʒ������һ����Ϣ��������Ϣ��������һ����Ϣ����������,�Ѿ����)
	 * 
	 * @param pPk
	 *            ������id
	 * @param auctionVO
	 */
	private int updateFromAuction(int pPk, int auction_id)
	{
		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO vo = auctionDao.getAuctionVOById(auction_id + "");
		if (vo.getAuctionSell() == 2)
		{
			return -1;
		}
		// ����������Ϣ
		addAuctionInfo(pPk, vo);
		// ���ʼ���������������
		sendAuctionSussendMail(vo);
		if (vo.getAuctionSell() == 3)
		{
			// ���ʼ������ĳɹ���
			sendAuctionSussendMailAuction(vo);
		}
		auctionDao.updateFromAuction(auction_id, pPk);
		return 1;
	}

	/**
	 * ���ĺ������������Ʒ�ľ�����Ϣ
	 * 
	 * @param int
	 *            auction_id,
	 * @param int
	 *            upk,
	 * @param int
	 *            ppk,
	 * @param int
	 *            auctionPrice,
	 * @param String
	 *            buyName
	 */
	private int updateFromAuctionByAuction(int auction_id, int upk, int ppk,
			int auctionPrice, String buyName)
	{
		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO vo = auctionDao.getAuctionVOById(auction_id + "");
		if (vo.getAuctionSell() == 2)
		{
			return -1;
		}
		auctionDao.updateFromAuctionByAuction(auction_id, upk, ppk,
				auctionPrice, buyName);
		return 1;
	}

	// �������ɹ�ʱ���ʼ���������������
	private void sendAuctionSussendMail(AuctionVO auctionVO)
	{
		MailInfoService mail = new MailInfoService();
		String title = "��������Ϣ��ʾ";
		StringBuffer content = new StringBuffer();
		content.append("������������������Ʒ").append(
				StringUtil.isoToGBK(auctionVO.getGoodsName())).append("��")
				.append(auctionVO.getGoodsNumber()).append(
						"�Ѿ��������ߣ�������������ȡ������Ǯ��.").append("ϵͳ�ʼ�,����ظ�!");
		mail.sendMailBySystem(Integer.valueOf(auctionVO.getPPk()), title,
				content.toString());

	}

	// �����ĳɹ����ʼ���������֪ͨ�侺�ĳɹ�
	private void sendAuctionSussendMailAuction(AuctionVO auctionVO)
	{
		MailInfoService mail = new MailInfoService();
		String title = "��������Ϣ��ʾ";
		StringBuffer content = new StringBuffer();
		content.append("�����������μӾ��ĵ�").append(
				StringUtil.isoToGBK(auctionVO.getGoodsName())).append("��")
				.append(auctionVO.getGoodsNumber()).append("�Ѿ����ĳɹ��뵽�����ֿ���ȡ.")
				.append("ϵͳ�ʼ�,����ظ�!");
		mail.sendMailBySystem(Integer.valueOf(auctionVO.getAuction_ppk()),
				title, content.toString());
	}

	// ���ʼ�������ʧ����
	private void sendAuctionFalseMailAuction(AuctionVO auctionVO, int ppk)
	{
		MailInfoService mail = new MailInfoService();
		String title = "��������Ϣ��ʾ";
		StringBuffer content = new StringBuffer();
		content.append("�����������μӾ��ĵ�").append(
				StringUtil.isoToGBK(auctionVO.getGoodsName())).append("��")
				.append(auctionVO.getGoodsNumber()).append(
						"�Ѿ��������Ը��ߵļ۸����ߣ�����Ǯ���Ѿ��˻���ע��鿴.").append("ϵͳ�ʼ�,����ظ�!");
		mail.sendMailBySystem(ppk, title, content.toString());
	}

	/**
	 * �����Ʒ��������ҹ�����������Ϣ�����һ����Ϣ
	 * 
	 * @param pPk
	 *            ������id
	 * @param auctionVO
	 */
	private void addAuctionInfo(int pPk, AuctionVO auctionVO)
	{

		AuctionInfoDAO auctionInfoDao = new AuctionInfoDAO();
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partvo = partInfoDao.getPartInfoByID(pPk);
		String name = partvo.getPName();
		StringBuffer con = new StringBuffer("����");
		con.append(StringUtil.isoToGBK(auctionVO.getGoodsName())).append("��")
				.append(auctionVO.getGoodsNumber()).append("�Ѿ���").append(
						StringUtil.isoToGBK(name)).append("����");
		auctionInfoDao.insertAuctionInfo(auctionVO, con.toString());

	}

	/** ���ݽ�ɫid��ѯ������Ϣ */
	public PartInfoVO getPartInfo(int pPk)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partvo = partInfoDao.getPartInfoByID(pPk);

		return partvo;
	}

	/**
	 * ����auction_id ��ѯ������Ϣ
	 * 
	 * @param auction_id
	 *            ������id
	 */
	public AuctionVO getAuctinVOById(String auction_id)
	{
		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO auctionVO = auctionDao.getAuctionVOById(auction_id);
		return auctionVO;
	}

	/**
	 * �������,�����ߴ�������ת���û�������
	 * 
	 */
	public synchronized String setToPG(int pPk, String auction_id,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";

		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");
		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO vo = auctionDao.getAuctionVOById(auction_id);

		if (roleInfo.getBasicInfo().getPPk() == vo.getPPk())
		{
			return "�����ܹ����Լ�����Ʒ!";
		}

		if (vo.getAuctionSell() != 1)
		{
			return "����Ʒ�Ѿ�����!";
		}

		// �������������û�Ҫ��ĵ���
		int buy = updateFromAuction(pPk, Integer.parseInt(auction_id));

		if (buy == -1)
		{
			resultWml = "����ʧ��";
			return resultWml;
		}
		else
		{
			resultWml = "����ɹ�";
			// ���½�Ǯ
			roleInfo.getBasicInfo().addCopper(-vo.getGoodsPrice());
		}

		// ����������ӵ���
		int add = putPropToWrap(pPk, vo.getGoodsId(), vo.getGoodsNumber(), vo);
		if (buy == -1 || add == -1)
		{
			resultWml = "����ʧ��";
			return resultWml;
		}
		else
		{
			resultWml = "����ɹ�";
		}
		// ���ٰ����ո�
		// ���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", -vo.getGoodsPrice() + "", "���� �򵽵���");

		// ͳ����Ҫ
		new RankService().updateAdd(vo.getPPk(), "sale", 1);
		return resultWml;
	}

	/**
	 * �ѵ��߷ŵ�������
	 * 
	 * @param p_pk
	 * @param goods_id
	 * @param w_type
	 * @return
	 */
	public int putPropToWrap(int p_pk, int goods_id, int goods_num,
			AuctionVO auctionVO)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();

		PropVO prop = PropCache.getPropById(goods_id);
		if (prop == null)
		{
			logger.debug("�Ҳ����������");
			return -1;
		}

		int accumulate_num = prop.getPropAccumulate();// �����ص�������
		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// ���е�����
		int total_num = current_num + goods_num;// ����goods_num�������

		int current_groups = 0;// ���е�����
		if (current_num != 0)
		{
			// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// ����goods_num�����ߺ������

		int need_groups = new_groups - current_groups;// ��Ҫ���ӵĵ�������

		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// ʣ��İ����ռ���

		if (wrap_spare <= need_groups)
		{
			logger.debug("�����ռ䲻��");
			return -1;
		}

		// �ҵ�û���ص����ĵ�����
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup.setPgType(prop.getPropPosition());
		propGroup.setPPk(p_pk);

		propGroup.setPropId(prop.getPropID());
		propGroup.setPropType(prop.getPropClass());

		propGroup.setPropBonding(auctionVO.getWBonding());
		propGroup.setPropProtect(auctionVO.getWProtect());
		propGroup.setPropIsReconfirm(auctionVO.getWIsReconfirm());
		propGroup.setPropUseControl(auctionVO.getPropUseControl());

		// ����µĵ����飬��������accumulate_num
		propGroup.setPropNum(accumulate_num);
		for (int i = 0; i < need_groups; i++)
		{
			propGroupDao.addPropGroup(propGroup);
		}

		propGroup = propGroupDao.getPropGroupByPropID(p_pk, goods_id);
		if (goodsgourp_goodsnum != 0)
		{
			propGroupDao.updatePropGroupNum(propGroup.getPgPk(),
					goodsgourp_goodsnum);
		}
		// �����ռ����need_groups
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, -need_groups);
		return 0;
	}

	/**
	 * ���ĳɹ���ѵ��߷Ž�������
	 */
	public void putPropToWrap(AuctionVO auctionVO)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PropVO prop = PropCache.getPropById(auctionVO.getGoodsId());
		if (prop == null)
		{
			logger.debug("�Ҳ����������");
		}

		int accumulate_num = prop.getPropAccumulate();// �����ص�������
		int current_num = propGroupDao.getPropNumByByPropID(auctionVO
				.getAuction_ppk(), auctionVO.getGoodsId());// ���е�����
		int total_num = current_num + auctionVO.getGoodsNumber();// ����goods_num�������

		int current_groups = 0;// ���е�����
		if (current_num != 0)
		{
			// ��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(auctionVO
					.getAuction_ppk(), auctionVO.getGoodsId());
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// ����goods_num�����ߺ������

		int need_groups = new_groups - current_groups;// ��Ҫ���ӵĵ�������

		int goodsgourp_goodsnum = total_num % accumulate_num;// ������������ĵ�������

		// �ҵ�û���ص����ĵ�����
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup.setPgType(prop.getPropPosition());
		propGroup.setPPk(auctionVO.getAuction_ppk());

		propGroup.setPropId(prop.getPropID());
		propGroup.setPropType(prop.getPropClass());

		propGroup.setPropBonding(auctionVO.getWBonding());
		propGroup.setPropProtect(auctionVO.getWProtect());
		propGroup.setPropIsReconfirm(auctionVO.getWIsReconfirm());
		propGroup.setPropUseControl(auctionVO.getPropUseControl());

		// ����µĵ����飬��������accumulate_num
		propGroup.setPropNum(accumulate_num);
		for (int i = 0; i < need_groups; i++)
		{
			propGroupDao.addPropGroup(propGroup);
		}

		propGroup = propGroupDao.getPropGroupByPropID(auctionVO
				.getAuction_ppk(), auctionVO.getGoodsId());
		if (goodsgourp_goodsnum != 0)
		{
			propGroupDao.updatePropGroupNum(propGroup.getPgPk(),
					goodsgourp_goodsnum);
		}
		// �����ռ����need_groups
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(auctionVO.getAuction_ppk(), -need_groups);
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ������Ʒ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getGoodsList(String pPk, int auction_type)
	{
		List<AuctionVO> goodsList = null;
		AuctionDAO auctionDao = new AuctionDAO();
		goodsList = auctionDao.getGoodsList(pPk, auction_type);
		return goodsList;
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ������Ʒ������Ľ�Ǯ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getMoneyList(String pPk)
	{
		List<AuctionVO> goodsList = null;
		AuctionDAO auctionDao = new AuctionDAO();
		goodsList = auctionDao.getMoneyList(pPk);
		return goodsList;
	}

	/**
	 * ��װ�����������ֿ�ת����ҵİ���
	 * 
	 */
	public String setAuctionWrap(int pPk, AuctionVO auctionVO,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";

		logger.info(" ������id : " + auctionVO.getGoodsId() + " ,��Ʒ�����ߵ�id:"
				+ auctionVO.getPPk());
		// ���Ĺ�����ҵ�ppkΪ�� ����������Ǯ��˼�¼����ɾ��
		// �����������װ��
		EquipService equipService = new EquipService();
		PlayerEquipDao ped = new PlayerEquipDao();
		PlayerEquipVO pev=ped.getByID(auctionVO.getGoodsId());
		RoleEntity oldRoleInfo = RoleService.getRoleInfoById(pev.getPPk() + "");
		RoleEntity newRoleInfo = RoleService.getRoleInfoById(pPk + "");
		resultWml = equipService.updateOwner(oldRoleInfo, newRoleInfo, pev,GameLogManager.G_AUCTION);
		if (resultWml != null && resultWml != "")
		{
			return resultWml;
		}
		int buy = 0;
		//����û��ȡ��Ǯ����ɾ����������Ϣ�����Լ���PPKҲ�������PPk��Ϊ����
		addUactionGetSuccessInfo(pPk, auctionVO);
		if (auctionVO.getUPk()>0&&pPk!=auctionVO.getPPk())
		{
			buy = updateFromAuctions(pPk,"auction_ppk",auctionVO);
		}
		//�����Ѿ�ȡ����Ǯɾ����������¼
		else
		{
			buy = deleteFromAuction(pPk, auctionVO);
		}
		if (buy == -1)
		{
			resultWml = "ȡ��ʧ��";
		}
		else
		{
			resultWml = "���ɹ�ȡ����" + auctionVO.getGoodsName() + "x"
					+ auctionVO.getGoodsNumber() + "";
			
		}

		return resultWml;
	}

	/**
	 * �����ߴ��������ֿ�ת���û�������
	 * 
	 */
	public String setAuctionPG(int pPk, AuctionVO auctionVO,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";

		// ������ҵ�ppkΪ������������ȡ��Ǯ�Ժ�Ż�ɾ���˼�¼
		addUactionGetSuccessInfo(pPk, auctionVO);
		int buy = updateFromAuction(pPk,"auction_ppk",auctionVO);
		// ����������ӵ���
		int add = putPropToWrap(pPk, auctionVO.getGoodsId(), auctionVO
				.getGoodsNumber(), auctionVO);

		if (buy == -1 || add == -1)
		{
			resultWml = "ȡ��ʧ��";
		}
		else
		{
			resultWml = "���ɹ�ȡ����" + auctionVO.getGoodsName() + "x"
					+ auctionVO.getGoodsNumber() + "";
		}

		return resultWml;
	}

	/**
	 * ɾ���û������Ʒ������һ����Ϣ��������Ϣ��������һ����Ϣ����������,δ���)
	 * 
	 * @param pPk
	 *            ������id
	 * @param auctionVO
	 */
	private int deleteFromAuction(int pPk, AuctionVO auctionVO)
	{

		int auction_id = auctionVO.getUAuctionId();
		AuctionDAO auctionDao = new AuctionDAO();
		int i = auctionDao.deleteFromAuction(auction_id);
		return i;
	}

	/***************************************************************************
	 * ���³ɹ���ҵ�ppkΪ����
	 */
	private int updateFromAuction(int pPk,String fieldName, AuctionVO auctionVO)
	{

		int auction_id = auctionVO.getUAuctionId();
		AuctionDAO auctionDao = new AuctionDAO();
		int i=-1;
		/***ȡ�ع������Ʒ  ����û�а�Ǯȡ��***/
		if(pPk!=auctionVO.getPPk()&&auctionVO.getUPk()>0)
		{
			i = auctionDao.updateFromAuction(fieldName,auction_id);
		}
		/***ȡ�ع������Ʒ  ���Ұ�Ǯȡ����***/
		else if(pPk!=auctionVO.getPPk()&&auctionVO.getUPk()<0)
		{
			i=deleteFromAuction( pPk,auctionVO);
		}
		/***ȡ��û����������Ʒ**/
		else if(pPk==auctionVO.getPPk()&&auctionVO.getAuctionFailed()==2)
		{
			i=deleteFromAuction( pPk,auctionVO);
		}
		/***ȡ��������õ�Ǯ��   ���ȡ����Ʒ**/
		else if(pPk==auctionVO.getPPk()&&auctionVO.getAuctionSell()==2&&auctionVO.getAuction_ppk()==-1)
		{
			i=deleteFromAuction( pPk,auctionVO);
		}
		/***ȡ��������õ�Ǯ�� ���ûȡ����Ʒ**/
		else if(pPk==auctionVO.getPPk()&&auctionVO.getAuctionSell()==2&&auctionVO.getAuction_ppk()!=-1)
		{
			i = auctionDao.updateFromAuction("u_pk",auction_id);
		}
		return i;
	}
	/**
	 * ���³ɹ���ҵ�ppkΪ���� ȡ��Ǯ�Ƶ�ʱ��
	 */
	private int updateFromAuctions(int pPk,String fieldName, AuctionVO auctionVO)
	{

		int auction_id = auctionVO.getUAuctionId();
		AuctionDAO auctionDao = new AuctionDAO();
		int i = auctionDao.updateFromAuction(fieldName,auction_id);
		return i;
	}

	/**
	 * ��������Ϣ�����һ����Ϣ
	 * 
	 * @param pPk
	 *            ������id
	 * @param auctionVO
	 */
	private void addAuctionDeleteInfo(int pPk, AuctionVO auctionVO)
	{

		AuctionInfoDAO auctionInfoDao = new AuctionInfoDAO();
		String money = auctionVO.getPay_type() == 1 ? "��ʯ" : "�ɾ�";
		StringBuffer con = new StringBuffer("��ȡ����");
		con.append(StringUtil.isoToGBK(auctionVO.getGoodsName())).append("*")
				.append(auctionVO.getGoodsNumber()).append("����Ǯ").append(
						auctionVO.getGoodsPrice()).append(money);
		auctionInfoDao.insertAuctionInfo(auctionVO, con.toString());

	}

	/***************************************************************************
	 * ��ҹ���ɹ�ȡ�ز���һ����Ϣ
	 */
	private void addUactionGetSuccessInfo(int pPk, AuctionVO auctionVO)
	{
		AuctionInfoDAO auctionInfoDao = new AuctionInfoDAO();
		StringBuffer con = null;
		if(pPk!=auctionVO.getPPk())
		{
			con = new StringBuffer("��ȡ���˹���ɹ���");
		}
		else
		{
			con = new StringBuffer("��ȡ����û��������");
		}
		con.append(StringUtil.isoToGBK(auctionVO.getGoodsName())).append("*")
				.append(auctionVO.getGoodsNumber());
		//auctionVO.setPPk(pPk);
		auctionInfoDao.insertAuction(pPk, con.toString());
	}

	/**
	 * ����Ǯ���������ֿ�ת���û�������
	 * 
	 */
	public String getAuctionMoney(int pPk, AuctionVO auctionVO,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");
		// ���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", auctionVO.getGoodsPrice() + "", "����װ���õ�");

		String displayMoney="";
		// ���½�Ǯ
		if (auctionVO.getPay_type() == 1)
		{
			long copper=auctionVO.getBuyPrice()<=0?auctionVO.getGoodsPrice()*95/100:auctionVO.getBuyPrice()*95/100;
			roleInfo.getBasicInfo().addCopper(copper);
			displayMoney=String.valueOf(copper);
		}
		else
		{
			/********�ɾ������۳����õ�10%��������**************/
			EconomyService es = new EconomyService();
			long addMoney=auctionVO.getBuyPrice()<=0?auctionVO.getGoodsPrice()*90/100:auctionVO.getAuction_price()*90/100;
			es.addYuanbao(roleInfo.getBasicInfo().getUPk(), addMoney);
			displayMoney=String.valueOf(addMoney);
		}
		// ִ��ͳ��
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, auctionVO.getGoodsPrice()
				- (int) (auctionVO.getGoodsPrice()), StatisticsType.USED,
				StatisticsType.SHUISHOU, pPk);
		int buy=0;
		//���û��ȡ����Ʒ��ʱ��ɾ���˼�¼
		addAuctionDeleteInfo(pPk, auctionVO);
		if (auctionVO.getAuction_ppk()>0)
		{
			buy = updateFromAuction(pPk,"u_pk",auctionVO);
		}
		//����Ѿ�ȡ������Ʒɾ����¼
		else
		{
			buy = deleteFromAuction(pPk, auctionVO);
		}
		if (buy == -1)
		{
			resultWml = "ȡ��ʧ��";
		}
		else
		{
			String m = auctionVO.getPay_type() == 1 ? "��ʯ" : "�ɾ�";
			String moneyTemp=auctionVO.getPay_type()==1?"5%":"10%";
			resultWml = "ȡ���ɹ�,�ѿ۳���"+moneyTemp+"�����������ɹ�ȡ����" + displayMoney + "" + m + "";
		}

		return resultWml;
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ����δ��������Ʒ���
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getNotSellList(int pPk)
	{
		List<AuctionVO> goodsList = null;
		AuctionDAO auctionDao = new AuctionDAO();
		goodsList = auctionDao.getNotSellGoodsList(pPk);
		return goodsList;
	}

	/**
	 * ���ݽ�ɫid��ø����������ֿ���ı�û�յ����
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public List<AuctionVO> getLostGoodsList(int pPk)
	{
		List<AuctionVO> goodsList = null;
		AuctionDAO auctionDao = new AuctionDAO();
		goodsList = auctionDao.getLostGoodsLists(pPk);
		return goodsList;
	}

	/**
	 * ���ݽ�ɫid��ø�����������Ϣ�������Ϣ
	 * 
	 * @param pPk
	 *            ���˽�ɫid
	 * @return list
	 */
	public QueryPage getAuctionInfoList(int pPk,int page_no)
	{
		List<AuctionInfoVO> goodsList = null;
		AuctionInfoDAO auctionDao = new AuctionInfoDAO();
		// ��ø���������Ϣ�������Ϣ
		QueryPage qp = auctionDao.getAuctionInfoList(pPk,page_no);
		// ɾ������������Ϣ���100���������Ϣ
		auctionDao.clearAuctionInfo(pPk);
		return qp;
	}

	/**
	 * ����װ��
	 * 
	 * @param p_pk
	 * @param accouter_id
	 *            װ��
	 * @param accouter_type
	 *            װ�����ͣ������ĸ���ģ�װ������������Ʒ��
	 * 
	 * public void removeAccouter1(int p_pk,int accouter_id,int accouter_type) {
	 * int accouter_class = -1; accouter_class =
	 * getGoodsClass(accouter_id,accouter_type);
	 * 
	 * //PlayerWrapDao wrapDao = new PlayerWrapDao(); //PlayerWrapVO wrap =
	 * wrapDao.getByPpkWtype(p_pk, Wrap.ACCOUTER);
	 * 
	 * //String accouters_str = wrap.getWArticle();
	 * 
	 * //logger.info("���˰������װ��:"+accouters_str); //����Ҫ�����װ�����ַ��� String
	 * accouter_str = accouter_id + "," + accouter_class + "," + accouter_type +
	 * "-";
	 * 
	 * logger.info("װ�����ַ���:"+accouter_str); //String accouters[] =
	 * accouters_str.split(accouter_str,2);//���ӵ���װ�����ַ������ų�
	 * //logger.info("װ�����ַ���[]"+accouters[0]+accouters[1]); //String
	 * new_accouters_str = accouters[0] + accouters[1];
	 * 
	 * //����װ�����ݿ���� // wrapDao.updateAccoutersOfWrap(p_pk, new_accouters_str);
	 * 
	 * 
	 * PartInfoDao partInfoDAO = new PartInfoDao(); //������Ұ���ʣ��ռ�����
	 * partInfoDAO.addWrapSpare(p_pk, 1); }
	 */

	/**
	 * �õ���Ʒ����
	 * 
	 * @param goods_id
	 * @param goods_type
	 * @return
	 * 
	 * private int getGoodsClass(int goods_id, int goods_type) {
	 * 
	 * int goods_class = -1; //װ������ if( goods_type==GoodsType.ACCOUTE ) {
	 * AccouteDao accouteDao = new AccouteDao(); goods_class =
	 * accouteDao.getClassById(goods_id); } else if( goods_type==GoodsType.ARM ) {
	 * ArmDao armDao = new ArmDao(); goods_class =
	 * armDao.getClassById(goods_id); } else if( goods_type==GoodsType.JEWELRY ) {
	 * JewelryDao jewelryDao = new JewelryDao(); goods_class =
	 * jewelryDao.getClassById(goods_id); } else if( goods_type==GoodsType.PROP ) {
	 * PropDao propDao = new PropDao(); goods_class =
	 * propDao.getClassById(goods_id); } return goods_class; }
	 */
}
