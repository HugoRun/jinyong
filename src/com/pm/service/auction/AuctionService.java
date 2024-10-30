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
	 * 拍卖装备
	 * 
	 * @param p_pk
	 * @param accouter_id
	 *            装备id
	 * @param accouter_num
	 *            装备数量
	 */
	public String removeAccouters(int u_pk, int p_pk, int pwPk, int propPrice,
			int payType, int auctionPrice)
	{
		StringBuffer resultWml = new StringBuffer();

		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk + "");

		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		PlayerEquipVO equip = playerEquipDao.getByID(pwPk);
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		// 判断要卖出的物品是否已经是赠送他人的物品
		String result = sellInfoDAO.getSellExistByPPkAndGoodsId(p_pk + "",
				equip.getEquipId() + "", GoodsType.EQUIP);

		if (result != null && !result.equals(""))
		{
			return result;
		}

		// 判断装备耐久为0的装备不可拍卖
		if (equip.isEffected() == false)
		{
			return "破损的装备不可拍卖！";
		}

		GoodsService goodsService = new GoodsService();
		// 绑定的装备不可交易不可拍卖
		String flag = goodsService.isBinded(equip.getPwPk(), GoodsType.EQUIP,
				ActionType.AUCTION);
		if (flag == null)
		{

			AuctionDAO auctionDao = new AuctionDAO();

			int auctionType = equip.getAuctionType();

			// 更改包裹里的位置
			PlayerEquipDao PlayerEquipDao = new PlayerEquipDao();
			int deleteValue = PlayerEquipDao.updatePosition(pwPk,
					Equip.ON_STORAGE);

			if (deleteValue != 0)
			{
				// 增加拍卖场里的装备
				auctionDao.addToAuction(u_pk, p_pk, equip, auctionType,
						propPrice, payType, auctionPrice);

				// 个人包裹容量加一
				EquipService equipService = new EquipService();
				equipService.addWrapSpare(p_pk, 1);

				// 监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(),
						roleInfo.getBasicInfo().getName(), roleInfo
								.getBasicInfo().getCopper()
								+ "",
						-(int) (propPrice * (1 - AuctionNumber.AUCTIONNUMBER))
								+ "", "拍卖缴税");

				// 从个人身上除去应缴税1为灵石2为元宝
				if(payType==2)
				{
					//进入拍卖场需要先缴纳100仙晶
					EconomyService es = new EconomyService();
					es.spendYuanbao(roleInfo.getBasicInfo().getUPk(),100);
				}

				String money = payType == 1 ? "灵石" : "仙晶";
				int moneyNum=payType==1?0:100;
				resultWml.append("您以" + propPrice + "" + money + "的价格拍卖了"
						+ StringUtil.isoToGBK(equip.getWName()) + ",收取手续费"
						+ moneyNum
						+ "" + money + ", 如要拍卖请继续！");
			}
			else
			{
				resultWml.append("您拍卖的物品好像不是归您所有!");
			}
		}
		else
		{
			resultWml.append(flag);
		}
		return resultWml.toString();
	}

	/**
	 * 增加道具到拍卖场
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
		logger.info("auctionService中的要拍卖的道具数量为:" + remove_num);
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
		logger.info("道具位置:" + auctionPosition);

		if (propGroup.getPropType() == PropType.EQUIPPROP
				|| propGroup.getPropType() == PropType.BOX_CURE)
		{
			return "特殊道具,请勿存进仓库!";
		}

		if (auctionPosition == 0)
		{
			resultWml.append("此道具不允许拍卖！");
		}
		else
		{
			GoodsService goodsService = new GoodsService();
			String flag = goodsService.isBinded(pg_pk, GoodsType.PROP,
					ActionType.EXCHANGE);
			if (flag == null)
			{
				// 删掉包裹里的道具
				removePropsFromWrap(propGroup, remove_num);

				// 增加拍卖场里的道具
				auctionDao.addPropToAuction(roleEntity.getBasicInfo().getUPk(),
						roleEntity.getBasicInfo().getPPk(), propGroup
								.getPropId(), GoodsType.PROP, propPrice,
						StringUtil.isoToGBK(propGroup.getPropName()),
						remove_num, propGroup, payType, auctionPrice);

				// 监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleEntity.getBasicInfo().getPPk(),
						roleEntity.getBasicInfo().getName(), roleEntity
								.getBasicInfo().getCopper()
								+ "",
						-(int) (propPrice * (1 - AuctionNumber.AUCTIONNUMBER))
								+ "", "拍卖缴税");

				// 从个人身上除去应缴税1为灵石2为元宝
				if(payType==2)
				{
					EconomyService es = new EconomyService();
					es
							.spendYuanbao(
									roleEntity.getBasicInfo().getUPk(),
									(int) (propPrice * (1 - AuctionNumber.AUCTIONNUMBER)));
				}

				String money = payType == 1 ? "灵石" : "仙晶";
				int moneyNum=payType==1?0:100;
				resultWml.append("您以" + propPrice + "" + money + "的价格拍卖了"
						+ StringUtil.isoToGBK(propGroup.getPropName())
						+ ",收取手续费"
						+ moneyNum
						+ "" + money + ", 如要拍卖请继续！");
			}
			else
			{
				resultWml.append(flag);
			}
		}
		return resultWml.toString();
	}

	/** 从包裹中卸掉一组道具 */
	private boolean removePropsFromWrap(PlayerPropGroupVO propGroup,
			int remove_num)
	{
		boolean flag = false;
		if (propGroup == null)
		{
			logger.info("propGroup为空");
			return false;
		}
		logger.info("卸掉的道具数量:" + remove_num);
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		if (propGroup.getPropNum() == remove_num) // 移除的数量等于道具组数量
		{
			propGroupDao.deletePropGroup(propGroup.getPgPk());
			// 此种情况个人包裹增加一个空格.
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(propGroup.getPPk(), 1);
			flag = true;
		}
		else
			if (propGroup.getPropNum() > remove_num)// //移除的数量小于道具组数量
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
	 * 分页:得到拍卖场里的auctionType类型的所有道具
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
	 * 分页:得到拍卖场里的特定类型的物品名
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
	 * 拍买装备, 将装备从拍卖场转到玩家的包裹
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
			return "您不能购买自己的物品!";
		}

		if (vo.getAuctionSell() != 1)
		{
			return "该物品正在竞拍中，价高者得!";
		}
		logger.info(" 拍卖场id : " + vo.getGoodsId());
		logger.info("购买者的id:" + pPk + " 物品拍卖者的id:" + vo.getPPk());
		// 从拍卖场更新用户要买的装备
		int buy = updateFromAuction(pPk, Integer.parseInt(auction_id));
		if (buy == -1)
		{
			resultWml = "拍买失败,此装备已经卖出";
			return resultWml;
		}
		else
		{
			resultWml = "拍买成功,请您去拍卖仓库领取自己的装备";
			// 更新金钱
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

		// 监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", -vo.getGoodsPrice() + "", "拍卖买装备");

		// 统计需要
		new RankService().updateAdd(vo.getPPk(), "sale", 1);

		return resultWml;
	}

	/** ****玩家用竞拍价购买时更新拍卖场物品状态 */
	public synchronized String setToWrapByAuction(int pPk, String auction_id,
			int buyPrice)
	{
		String resultWml = "";

		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");

		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO vo = auctionDao.getAuctionVOById(auction_id + "");

		if (roleInfo.getBasicInfo().getPPk() == vo.getPPk())
		{
			return "您不能购买自己的物品!";
		}

		if (vo.getAuctionSell() == 2)
		{
			return "该物品正在竞拍中，价高者得!";
		}
		logger.info(" 拍卖场id : " + vo.getGoodsId());
		logger.info("购买者的id:" + pPk + " 物品拍卖者的id:" + vo.getPPk());
		/** *这个UPK是为了退还给上次竞拍者元宝用的** */
		int u_pk = vo.getAuction_upk();
		int p_pk = vo.getAuction_ppk();
		int money = vo.getBuyPrice();
		// 从拍卖场更新用户要买的装备
		int buy = updateFromAuctionByAuction(Integer.parseInt(auction_id),
				roleInfo.getBasicInfo().getUPk(), pPk, buyPrice, roleInfo
						.getBasicInfo().getName());
		if (buy == -1)
		{
			resultWml = "竞拍失败失败，该物品已经出售";
			return resultWml;
		}
		else
		{
			resultWml = "您已以" + buyPrice + "灵石的价格竞拍" + vo.getGoodsName()
					+ "，若十分钟内无人参与该物的竞拍，则竞拍成功，请注意查看竞拍邮件";
			// 更新金钱
			int needmoney =buyPrice;
			if (vo.getPay_type() == 1)
			{
				// 减去新的竞拍者的灵石
				roleInfo.getBasicInfo().addCopper(-needmoney);
				// 返还原来竞拍者的灵石
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
				// 减去新的竞拍者的元宝
				es.spendYuanbao(roleInfo.getBasicInfo().getUPk(), needmoney);
				// 增加原来竞拍者的元宝
				es.addYuanbao(u_pk, money);
			}
			// 发送邮件给上一个竞拍者告知其竞拍失败且钱财已经退还
			sendAuctionFalseMailAuction(vo, p_pk);

		}
		return resultWml;
	}

	/**
	 * 处理竞拍，由定时器每5分钟执行一次，得到10分钟内没有别人参与竞拍的物品则竞拍成功给玩家发送物品发送邮件更改物品为卖出状态
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
	 * 处理拍卖，由定时器每天执行一次来处理拍卖场流拍或者过期的物品
	 */
	public void processAuctionFalse()
	{
		/** 超过三天还未被买的下架* */
		updateThanThreeDay();

		/** 自开始拍卖六日内未取回的流拍物品，将被系统没收* */
		updateThanSixDay();

		/** 自拍卖成功七日内，未取回的拍卖金钱被系统没收* */
		updateMoneySevenDay();
	}

	// 自拍卖成功七日内，未取回的拍卖金钱被系统没收
	private void updateMoneySevenDay()
	{
		AuctionInfoDAO auctionInfoDAO = new AuctionInfoDAO();
		AuctionDAO auctionDao = new AuctionDAO();

		// 获得拍卖成功七日内未取回拍卖金钱的列表
		List<AuctionVO> list = auctionDao.getThanSevenDay();
		MailInfoService mailInfo = new MailInfoService();

		if (list != null || list.size() != 0)
		{
			// 给拍卖成功七日内未取回拍卖金钱的拍卖者发拍卖提示到拍卖信息里.
			String info1 = "您拍卖";
			String info2 = "成功已经超过三天，由于您没有及时的取回银两，这些银两已经被系统收回！";
			String star = "×";
			String title = "拍卖场信息提示";
			String info3 = "系统消息请勿回复！";
			AuctionVO vo = null;
			for (int i = 0; i < list.size(); i++)
			{
				vo = list.get(i);
				auctionInfoDAO.insertAuctionInfo(vo, info1
						+ StringUtil.isoToGBK(vo.getGoodsName()) + star
						+ vo.getGoodsNumber() + info2);
				// 给拍卖成功七日的拍卖者发邮件
				mailInfo.sendMailBySystem(vo.getPPk(), title, info1
						+ StringUtil.isoToGBK(vo.getGoodsName()) + info2 + info3);
			}
		}

		auctionDao.updateMoneySevenDay();
	}

	// 自开始拍卖六日内未取回的流拍物品，将被系统没收
	private void updateThanSixDay()
	{
		AuctionInfoDAO auctionInfoDAO = new AuctionInfoDAO();
		AuctionDAO auctionDao = new AuctionDAO();

		// 获得拍卖六日内未取回的拍卖物品列表
		List<AuctionVO> list = auctionDao.getThanSixDayList();
		MailInfoService mailInfo = new MailInfoService();

		if (list != null || list.size() != 0)
		{
			// 给拍卖六日内未取回拍卖品的拍卖者发拍卖提示到拍卖信息里.
			String info1 = "您所拍卖的";
			String info2 = "进入流拍已经超过三天，由于您没有及时的取回物品，该物品已经会被系统收回！";
			String title = "拍卖场信息提示";
			String star = "×";
			String info3 = "系统消息请勿回复！";
			AuctionVO vo = null;
			for (int i = 0; i < list.size(); i++)
			{
				vo = list.get(i);
				auctionInfoDAO.insertAuctionInfo(vo, info1
						+ StringUtil.isoToGBK(vo.getGoodsName()) + star
						+ vo.getGoodsNumber() + info2);
				// 给超过六日的拍卖者发邮件
				mailInfo.sendMailBySystem(vo.getPPk(), title, info1
						+ StringUtil.isoToGBK(vo.getGoodsName())+ info2 + info3);
			}
		}

		auctionDao.deleteThanSixDay();
	}

	// 超过三天还未被买的下架,将发信息到拍卖信息表，并发邮件到其信箱
	private void updateThanThreeDay()
	{
		AuctionInfoDAO auctionInfoDAO = new AuctionInfoDAO();
		AuctionDAO auctionDao = new AuctionDAO();
		// 获得三天未被买的拍卖物品列表
		List<AuctionVO> list = auctionDao.getThanThreeDayList();
		MailInfoService mailInfo = new MailInfoService();

		if (list!=null || list.size() != 0)
		{
			// 给超过三天的拍卖品的拍卖者发拍卖提示到拍卖信息里.
			String info1 = "您所拍卖的";
			String info2 = "拍卖时间已经超过两天，现已退出拍卖，请您于三日内到拍卖场仓库取回物品！";
			String title = "拍卖场信息提示";
			String star = "×";
			String info3 = "系统消息请勿回复！";
			AuctionVO vo = null;
			for (int i = 0; i < list.size(); i++)
			{
				vo = list.get(i);
				if(vo.getAuctionFailed()!=2)
				{
					auctionInfoDAO.insertAuctionInfo(vo, info1
							+ StringUtil.isoToGBK(vo.getGoodsName()) + star
							+ vo.getGoodsNumber() + info2);
					// 给超过三天的拍卖者发邮件
					mailInfo.sendMailBySystem(vo.getPPk(), title, info1
							+ StringUtil.isoToGBK(vo.getGoodsName()) + info2 + info3);
				}
			}
		}

		auctionDao.updateThanThreeDay();

	}

	/**
	 * 更新用户买的物品，并发一条消息到拍卖信息表，（并发一条信息到他的邮箱,已经完成)
	 * 
	 * @param pPk
	 *            购买者id
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
		// 增加拍卖信息
		addAuctionInfo(pPk, vo);
		// 发邮件给卖主到其邮箱
		sendAuctionSussendMail(vo);
		if (vo.getAuctionSell() == 3)
		{
			// 发邮件给竞拍成功者
			sendAuctionSussendMailAuction(vo);
		}
		auctionDao.updateFromAuction(auction_id, pPk);
		return 1;
	}

	/**
	 * 竞拍后更新拍卖场物品的竞拍信息
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

	// 当拍卖成功时发邮件到其拍卖者邮箱
	private void sendAuctionSussendMail(AuctionVO auctionVO)
	{
		MailInfoService mail = new MailInfoService();
		String title = "拍卖场信息提示";
		StringBuffer content = new StringBuffer();
		content.append("您在拍卖场寄卖的物品").append(
				StringUtil.isoToGBK(auctionVO.getGoodsName())).append("×")
				.append(auctionVO.getGoodsNumber()).append(
						"已经被人买走，请您于三日内取回所卖钱财.").append("系统邮件,请勿回复!");
		mail.sendMailBySystem(Integer.valueOf(auctionVO.getPPk()), title,
				content.toString());

	}

	// 当竞拍成功后发邮件到其邮箱通知其竞拍成功
	private void sendAuctionSussendMailAuction(AuctionVO auctionVO)
	{
		MailInfoService mail = new MailInfoService();
		String title = "拍卖场信息提示";
		StringBuffer content = new StringBuffer();
		content.append("您在拍卖场参加竞拍的").append(
				StringUtil.isoToGBK(auctionVO.getGoodsName())).append("×")
				.append(auctionVO.getGoodsNumber()).append("已经竞拍成功请到拍卖仓库领取.")
				.append("系统邮件,请勿回复!");
		mail.sendMailBySystem(Integer.valueOf(auctionVO.getAuction_ppk()),
				title, content.toString());
	}

	// 发邮件给竞拍失败者
	private void sendAuctionFalseMailAuction(AuctionVO auctionVO, int ppk)
	{
		MailInfoService mail = new MailInfoService();
		String title = "拍卖场信息提示";
		StringBuffer content = new StringBuffer();
		content.append("您在拍卖场参加竞拍的").append(
				StringUtil.isoToGBK(auctionVO.getGoodsName())).append("×")
				.append(auctionVO.getGoodsNumber()).append(
						"已经被别人以更高的价格买走，您的钱财已经退还请注意查看.").append("系统邮件,请勿回复!");
		mail.sendMailBySystem(ppk, title, content.toString());
	}

	/**
	 * 如果物品被其他玩家购买，向拍卖信息表插入一条信息
	 * 
	 * @param pPk
	 *            购买者id
	 * @param auctionVO
	 */
	private void addAuctionInfo(int pPk, AuctionVO auctionVO)
	{

		AuctionInfoDAO auctionInfoDao = new AuctionInfoDAO();
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partvo = partInfoDao.getPartInfoByID(pPk);
		String name = partvo.getPName();
		StringBuffer con = new StringBuffer("您的");
		con.append(StringUtil.isoToGBK(auctionVO.getGoodsName())).append("×")
				.append(auctionVO.getGoodsNumber()).append("已经被").append(
						StringUtil.isoToGBK(name)).append("购买");
		auctionInfoDao.insertAuctionInfo(auctionVO, con.toString());

	}

	/** 根据角色id查询个人信息 */
	public PartInfoVO getPartInfo(int pPk)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		PartInfoVO partvo = partInfoDao.getPartInfoByID(pPk);

		return partvo;
	}

	/**
	 * 根据auction_id 查询拍卖信息
	 * 
	 * @param auction_id
	 *            拍卖表id
	 */
	public AuctionVO getAuctinVOById(String auction_id)
	{
		AuctionDAO auctionDao = new AuctionDAO();
		AuctionVO auctionVO = auctionDao.getAuctionVOById(auction_id);
		return auctionVO;
	}

	/**
	 * 拍买道具,将道具从拍卖场转到用户包裹中
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
			return "您不能购买自己的物品!";
		}

		if (vo.getAuctionSell() != 1)
		{
			return "该物品已经卖出!";
		}

		// 从拍卖场更新用户要买的道具
		int buy = updateFromAuction(pPk, Integer.parseInt(auction_id));

		if (buy == -1)
		{
			resultWml = "拍买失败";
			return resultWml;
		}
		else
		{
			resultWml = "拍买成功";
			// 更新金钱
			roleInfo.getBasicInfo().addCopper(-vo.getGoodsPrice());
		}

		// 往包裹里添加道具
		int add = putPropToWrap(pPk, vo.getGoodsId(), vo.getGoodsNumber(), vo);
		if (buy == -1 || add == -1)
		{
			resultWml = "拍买失败";
			return resultWml;
		}
		else
		{
			resultWml = "拍买成功";
		}
		// 减少包裹空格
		// 监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", -vo.getGoodsPrice() + "", "拍卖 买到道具");

		// 统计需要
		new RankService().updateAdd(vo.getPPk(), "sale", 1);
		return resultWml;
	}

	/**
	 * 把道具放到包裹里
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
			logger.debug("找不到这个道具");
			return -1;
		}

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量
		int current_num = propGroupDao.getPropNumByByPropID(p_pk, goods_id);// 现有的数量
		int total_num = current_num + goods_num;// 增加goods_num后的总数

		int current_groups = 0;// 现有的组数
		if (current_num != 0)
		{
			// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(p_pk,
					goods_id);
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// 增加goods_num个道具后的组数

		int need_groups = new_groups - current_groups;// 需要增加的道具组数

		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量

		int wrap_spare = roleInfo.getBasicInfo().getWrapSpare();// 剩余的包裹空间数

		if (wrap_spare <= need_groups)
		{
			logger.debug("包裹空间不够");
			return -1;
		}

		// 找到没有重叠慢的道具组
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup.setPgType(prop.getPropPosition());
		propGroup.setPPk(p_pk);

		propGroup.setPropId(prop.getPropID());
		propGroup.setPropType(prop.getPropClass());

		propGroup.setPropBonding(auctionVO.getWBonding());
		propGroup.setPropProtect(auctionVO.getWProtect());
		propGroup.setPropIsReconfirm(auctionVO.getWIsReconfirm());
		propGroup.setPropUseControl(auctionVO.getPropUseControl());

		// 添加新的道具组，数量都是accumulate_num
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
		// 包裹空间减少need_groups
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(p_pk, -need_groups);
		return 0;
	}

	/**
	 * 竞拍成功后把道具放进包裹里
	 */
	public void putPropToWrap(AuctionVO auctionVO)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PropVO prop = PropCache.getPropById(auctionVO.getGoodsId());
		if (prop == null)
		{
			logger.debug("找不到这个道具");
		}

		int accumulate_num = prop.getPropAccumulate();// 可以重叠的数量
		int current_num = propGroupDao.getPropNumByByPropID(auctionVO
				.getAuction_ppk(), auctionVO.getGoodsId());// 现有的数量
		int total_num = current_num + auctionVO.getGoodsNumber();// 增加goods_num后的总数

		int current_groups = 0;// 现有的组数
		if (current_num != 0)
		{
			// 因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			// current_groups =(current_num-1)/accumulate_num+1;
			current_groups = propGroupDao.getPropGroupNumByPropID(auctionVO
					.getAuction_ppk(), auctionVO.getGoodsId());
		}

		int new_groups = (total_num - 1) / accumulate_num + 1;// 增加goods_num个道具后的组数

		int need_groups = new_groups - current_groups;// 需要增加的道具组数

		int goodsgourp_goodsnum = total_num % accumulate_num;// 不完整道具组的道具数量

		// 找到没有重叠慢的道具组
		PlayerPropGroupVO propGroup = new PlayerPropGroupVO();
		propGroup.setPgType(prop.getPropPosition());
		propGroup.setPPk(auctionVO.getAuction_ppk());

		propGroup.setPropId(prop.getPropID());
		propGroup.setPropType(prop.getPropClass());

		propGroup.setPropBonding(auctionVO.getWBonding());
		propGroup.setPropProtect(auctionVO.getWProtect());
		propGroup.setPropIsReconfirm(auctionVO.getWIsReconfirm());
		propGroup.setPropUseControl(auctionVO.getPropUseControl());

		// 添加新的道具组，数量都是accumulate_num
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
		// 包裹空间减少need_groups
		EquipService equipService = new EquipService();
		equipService.addWrapSpare(auctionVO.getAuction_ppk(), -need_groups);
	}

	/**
	 * 根据角色id获得个人拍卖场仓库里的物品情况
	 * 
	 * @param pPk
	 *            个人角色id
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
	 * 根据角色id获得个人拍卖场仓库里的物品卖出后的金钱情况
	 * 
	 * @param pPk
	 *            个人角色id
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
	 * 将装备从拍卖场仓库转到玩家的包裹
	 * 
	 */
	public String setAuctionWrap(int pPk, AuctionVO auctionVO,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";

		logger.info(" 拍卖场id : " + auctionVO.getGoodsId() + " ,物品拍卖者的id:"
				+ auctionVO.getPPk());
		// 更改购买玩家的ppk为负 在卖家领完钱后此记录才能删除
		// 往包裹里添加装备
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
		//卖家没有取走钱不能删除此拍卖信息，把自己的PPK也就是买家PPk设为负数
		addUactionGetSuccessInfo(pPk, auctionVO);
		if (auctionVO.getUPk()>0&&pPk!=auctionVO.getPPk())
		{
			buy = updateFromAuctions(pPk,"auction_ppk",auctionVO);
		}
		//卖家已经取走了钱删除此拍卖记录
		else
		{
			buy = deleteFromAuction(pPk, auctionVO);
		}
		if (buy == -1)
		{
			resultWml = "取出失败";
		}
		else
		{
			resultWml = "您成功取回了" + auctionVO.getGoodsName() + "x"
					+ auctionVO.getGoodsNumber() + "";
			
		}

		return resultWml;
	}

	/**
	 * 将道具从拍卖场仓库转到用户包裹中
	 * 
	 */
	public String setAuctionPG(int pPk, AuctionVO auctionVO,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";

		// 更新买家的ppk为负数等卖家领取了钱以后才会删除此记录
		addUactionGetSuccessInfo(pPk, auctionVO);
		int buy = updateFromAuction(pPk,"auction_ppk",auctionVO);
		// 往包裹里添加道具
		int add = putPropToWrap(pPk, auctionVO.getGoodsId(), auctionVO
				.getGoodsNumber(), auctionVO);

		if (buy == -1 || add == -1)
		{
			resultWml = "取出失败";
		}
		else
		{
			resultWml = "您成功取回了" + auctionVO.getGoodsName() + "x"
					+ auctionVO.getGoodsNumber() + "";
		}

		return resultWml;
	}

	/**
	 * 删除用户买的物品，并发一条消息到拍卖信息表，（并发一条信息到他的邮箱,未完成)
	 * 
	 * @param pPk
	 *            购买者id
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
	 * 更新成功买家的ppk为负数
	 */
	private int updateFromAuction(int pPk,String fieldName, AuctionVO auctionVO)
	{

		int auction_id = auctionVO.getUAuctionId();
		AuctionDAO auctionDao = new AuctionDAO();
		int i=-1;
		/***取回购买的物品  卖家没有把钱取走***/
		if(pPk!=auctionVO.getPPk()&&auctionVO.getUPk()>0)
		{
			i = auctionDao.updateFromAuction(fieldName,auction_id);
		}
		/***取回购买的物品  卖家把钱取走了***/
		else if(pPk!=auctionVO.getPPk()&&auctionVO.getUPk()<0)
		{
			i=deleteFromAuction( pPk,auctionVO);
		}
		/***取回没有卖出的物品**/
		else if(pPk==auctionVO.getPPk()&&auctionVO.getAuctionFailed()==2)
		{
			i=deleteFromAuction( pPk,auctionVO);
		}
		/***取回卖出获得的钱财   买家取走物品**/
		else if(pPk==auctionVO.getPPk()&&auctionVO.getAuctionSell()==2&&auctionVO.getAuction_ppk()==-1)
		{
			i=deleteFromAuction( pPk,auctionVO);
		}
		/***取回卖出获得的钱财 买家没取走物品**/
		else if(pPk==auctionVO.getPPk()&&auctionVO.getAuctionSell()==2&&auctionVO.getAuction_ppk()!=-1)
		{
			i = auctionDao.updateFromAuction("u_pk",auction_id);
		}
		return i;
	}
	/**
	 * 更新成功买家的ppk为负数 取出钱财的时候
	 */
	private int updateFromAuctions(int pPk,String fieldName, AuctionVO auctionVO)
	{

		int auction_id = auctionVO.getUAuctionId();
		AuctionDAO auctionDao = new AuctionDAO();
		int i = auctionDao.updateFromAuction(fieldName,auction_id);
		return i;
	}

	/**
	 * 向拍卖信息表插入一条信息
	 * 
	 * @param pPk
	 *            购买者id
	 * @param auctionVO
	 */
	private void addAuctionDeleteInfo(int pPk, AuctionVO auctionVO)
	{

		AuctionInfoDAO auctionInfoDao = new AuctionInfoDAO();
		String money = auctionVO.getPay_type() == 1 ? "灵石" : "仙晶";
		StringBuffer con = new StringBuffer("您取回了");
		con.append(StringUtil.isoToGBK(auctionVO.getGoodsName())).append("*")
				.append(auctionVO.getGoodsNumber()).append("的卖钱").append(
						auctionVO.getGoodsPrice()).append(money);
		auctionInfoDao.insertAuctionInfo(auctionVO, con.toString());

	}

	/***************************************************************************
	 * 玩家购买成功取回插入一跳信息
	 */
	private void addUactionGetSuccessInfo(int pPk, AuctionVO auctionVO)
	{
		AuctionInfoDAO auctionInfoDao = new AuctionInfoDAO();
		StringBuffer con = null;
		if(pPk!=auctionVO.getPPk())
		{
			con = new StringBuffer("您取回了购买成功的");
		}
		else
		{
			con = new StringBuffer("您取回了没有卖出的");
		}
		con.append(StringUtil.isoToGBK(auctionVO.getGoodsName())).append("*")
				.append(auctionVO.getGoodsNumber());
		//auctionVO.setPPk(pPk);
		auctionInfoDao.insertAuction(pPk, con.toString());
	}

	/**
	 * 将金钱从拍卖场仓库转到用户包裹中
	 * 
	 */
	public String getAuctionMoney(int pPk, AuctionVO auctionVO,
			PartInfoVO partInfoVO)
	{
		String resultWml = "";
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk + "");
		// 监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo
				.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()
				+ "", auctionVO.getGoodsPrice() + "", "拍卖装备得到");

		String displayMoney="";
		// 更新金钱
		if (auctionVO.getPay_type() == 1)
		{
			long copper=auctionVO.getBuyPrice()<=0?auctionVO.getGoodsPrice()*95/100:auctionVO.getBuyPrice()*95/100;
			roleInfo.getBasicInfo().addCopper(copper);
			displayMoney=String.valueOf(copper);
		}
		else
		{
			/********仙晶拍卖扣除所得的10%的手续费**************/
			EconomyService es = new EconomyService();
			long addMoney=auctionVO.getBuyPrice()<=0?auctionVO.getGoodsPrice()*90/100:auctionVO.getAuction_price()*90/100;
			es.addYuanbao(roleInfo.getBasicInfo().getUPk(), addMoney);
			displayMoney=String.valueOf(addMoney);
		}
		// 执行统计
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(6, StatisticsType.MONEY, auctionVO.getGoodsPrice()
				- (int) (auctionVO.getGoodsPrice()), StatisticsType.USED,
				StatisticsType.SHUISHOU, pPk);
		int buy=0;
		//买家没有取走物品的时候不删除此记录
		addAuctionDeleteInfo(pPk, auctionVO);
		if (auctionVO.getAuction_ppk()>0)
		{
			buy = updateFromAuction(pPk,"u_pk",auctionVO);
		}
		//买家已经取走了物品删除记录
		else
		{
			buy = deleteFromAuction(pPk, auctionVO);
		}
		if (buy == -1)
		{
			resultWml = "取回失败";
		}
		else
		{
			String m = auctionVO.getPay_type() == 1 ? "灵石" : "仙晶";
			String moneyTemp=auctionVO.getPay_type()==1?"5%":"10%";
			resultWml = "取出成功,已扣除您"+moneyTemp+"的手续费您成功取回了" + displayMoney + "" + m + "";
		}

		return resultWml;
	}

	/**
	 * 根据角色id获得个人拍卖场仓库里的未卖出的物品情况
	 * 
	 * @param pPk
	 *            个人角色id
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
	 * 根据角色id获得个人拍卖场仓库里的被没收的情况
	 * 
	 * @param pPk
	 *            个人角色id
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
	 * 根据角色id获得个人拍卖场信息表里的信息
	 * 
	 * @param pPk
	 *            个人角色id
	 * @return list
	 */
	public QueryPage getAuctionInfoList(int pPk,int page_no)
	{
		List<AuctionInfoVO> goodsList = null;
		AuctionInfoDAO auctionDao = new AuctionInfoDAO();
		// 获得个人拍卖信息表里的信息
		QueryPage qp = auctionDao.getAuctionInfoList(pPk,page_no);
		// 删除个人拍卖信息表的100条开外的信息
		auctionDao.clearAuctionInfo(pPk);
		return qp;
	}

	/**
	 * 丢弃装备
	 * 
	 * @param p_pk
	 * @param accouter_id
	 *            装备
	 * @param accouter_type
	 *            装备类型，如是哪个表的（装备，武器，饰品）
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
	 * //logger.info("个人包裹里的装备:"+accouters_str); //生成要储存的装备的字符串 String
	 * accouter_str = accouter_id + "," + accouter_class + "," + accouter_type +
	 * "-";
	 * 
	 * logger.info("装备的字符串:"+accouter_str); //String accouters[] =
	 * accouters_str.split(accouter_str,2);//把扔掉的装备从字符串中排除
	 * //logger.info("装备的字符串[]"+accouters[0]+accouters[1]); //String
	 * new_accouters_str = accouters[0] + accouters[1];
	 * 
	 * //放入装备数据库更新 // wrapDao.updateAccoutersOfWrap(p_pk, new_accouters_str);
	 * 
	 * 
	 * PartInfoDao partInfoDAO = new PartInfoDao(); //增加玩家包裹剩余空间数量
	 * partInfoDAO.addWrapSpare(p_pk, 1); }
	 */

	/**
	 * 得到物品类型
	 * 
	 * @param goods_id
	 * @param goods_type
	 * @return
	 * 
	 * private int getGoodsClass(int goods_id, int goods_type) {
	 * 
	 * int goods_class = -1; //装备类型 if( goods_type==GoodsType.ACCOUTE ) {
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
