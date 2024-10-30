package com.pm.service.auctionpet;

import java.util.List;

import org.apache.log4j.Logger;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.RoleService;
import com.pm.constant.AuctionNumber;
import com.pm.dao.auctionpet.AuctionPetDao;
import com.pm.dao.auctionpet.AuctionPetInfoDao;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.auctionpet.AuctionPetInfoVO;
import com.pm.vo.auctionpet.AuctionPetVO;

public class AuctionPetService
{

	Logger logger = Logger.getLogger("log.service");
	
	/** 拍卖宠物 */
	public String auctionPet(int pPk ,String petPk,int input_money){
		StringBuffer sb = new StringBuffer();
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(pPk+"");
		
		PetInfoDAO petInfoDao = new PetInfoDAO();
		PetInfoVO petinfovo = petInfoDao.getPetInfoView(petPk,pPk);
		if(petinfovo == null) {
			sb.append("此宠物可能不归您所有!");
			return sb.toString();
		}
		
		if (petinfovo.getPetLonge() == 0) {
			sb.append("此宠物的寿命为零!");
			return sb.toString();
		}
		
		if(petinfovo.getPetIsBring() == 1){
			sb.append("出战的宠物不能拍卖！");
		}else {
			AuctionPetDao auctionPetDao = new AuctionPetDao();
			int status = auctionPetDao.insertAuctionPet(input_money, petinfovo);
			logger.info("插入成功状态:"+status);
			petInfoDao.getPetInfoDelte(petPk);
			if(status == -1){
				sb.append("对不起，系统错误，请稍后再试！~");
			}else if(status == 1){
				
				//监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -(int)(input_money*(1 - AuctionNumber.AUCTIONNUMBER))+"", "拍卖宠物 缴税");
				
				//从个人身上除去应缴税
				roleInfo.getBasicInfo().addCopper(-(int)(input_money*(1 - AuctionNumber.AUCTIONNUMBER)));
				
				sb.append("您以").append(MoneyUtil.changeCopperToStr(input_money)).append("的价格拍卖了宠物: ").append(StringUtil.isoToGBK(petinfovo.getPetName()))
				.append(",收取手续费").append(MoneyUtil.changeCopperToStr((int)(input_money*(1 - AuctionNumber.AUCTIONNUMBER)))).append(",如要拍卖请继续!");
			}
		} 
		return sb.toString();
	}


	//玩家是否有足够金钱去买
	public String auctionHasMoney(PartInfoVO partInfoVO, AuctionPetVO auctionPetVO)
	{
		String str = "1";
		
		long body_money = Long.valueOf(partInfoVO.getPCopper());			//身上的钱
		
		int pet_auction_price = auctionPetVO.getPetPrice();					//该宠物的拍卖价格
		
		if(body_money < pet_auction_price){
			str = "您的金钱不足！";
		}
		return str;
	}
	
	//玩家是否有足够空格去买
	public String auctionhasEnoughSpace(String pPk)
	{
		String str = "1";
		WareHouseDao wareDao = new WareHouseDao();
		int petNumber = wareDao.getPetNumber(Integer.valueOf(pPk));
		 if(petNumber >= 6){
			 str = "您没有足够的宠物空间！";
		 }
		return str;
	}

	/**
	 * 拍买宠物
	 * @param pPk
	 * @param petPk
	 * @param auctionPetVO
	 */
	public synchronized static String auctionBuyPet(int pPk, String petPk,AuctionPetVO auctionPetVO)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(pPk+"");
		
		AuctionPetDao auctionPetDao = new AuctionPetDao();
			
		
		AuctionPetVO vo = auctionPetDao.getPetInfoView(auctionPetVO.getPetPk()+"");
		if(vo.getPPk() == pPk){
			return "不能购买自己拍卖的宠物!";
		}
		if(vo.getAuctionStatus() != 1){
			return "该宠物已经卖出!";
		}
		
		// 拍卖时相关表操作
		petAuctionInfoAndMail(pPk,petPk,auctionPetVO);
		
		int pet_auction_price = auctionPetVO.getPetPrice();					//该宠物的拍卖价格
		//监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -pet_auction_price+"", "拍卖买到宠物");
		//从包裹中移除input_num个铜板
		roleInfo.getBasicInfo().addCopper(-pet_auction_price);
		//插入个人宠物表
		auctionPetDao.insertPersonPet(pPk,auctionPetVO);
		return "您以"+MoneyUtil.changeCopperToStr(auctionPetVO.getPetPrice())+"价格拍买了"+StringUtil.isoToGBK(auctionPetVO.getPetName());
	}

	//对拍卖者发邮件通知，并发拍卖信息到宠物拍卖信息表,将此宠物的拍卖状态置为2.
	private static void petAuctionInfoAndMail(int pPk, String petPk,AuctionPetVO auctionPetVO)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(pPk+"");
		
		//将宠物的拍卖状态置为4,即卖出状态
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		auctionPetDao.updateAuctionStatus(petPk,4);
		
		//对拍卖者发送邮件通知
		StringBuffer info = new StringBuffer();
		info.append("您的").append(StringUtil.isoToGBK(auctionPetVO.getPetName())).append("被").append(
							StringUtil.isoToGBK(roleInfo.getBasicInfo().getName())).append("买走了! ");
		String title = "宠物拍卖场邮件通知";
		
		//向宠物拍卖信息表发送信息
		AuctionPetInfoDao petInfoDao = new AuctionPetInfoDao();
		petInfoDao.insertPetInfo(auctionPetVO.getPPk(),info.append("请您于六天内取回所卖银两！").toString());
		
		MailInfoService mailInfoService = new MailInfoService();
		mailInfoService.sendMailBySystem(auctionPetVO.getPPk(),title,info.append("(系统邮件请勿回复!)").toString());
		
	}

	//获得指定数量的list，其中如果mores是not就是三条，而为more就是10条
	public List<AuctionPetInfoVO> getPetInfoList(int pPk, String mores)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		List<AuctionPetInfoVO> petinfolist = null;
		if(mores.equals("not")){
			petinfolist = auctionPetDao.getPetInfoList(pPk,3);
		}else if(mores.equals("more")){
			petinfolist = auctionPetDao.getPetInfoList(pPk,10);
		}else {
			petinfolist = auctionPetDao.getPetInfoList(pPk,3);
		}
		//删除掉按时间顺序多余10条的数据记录
		AuctionPetInfoDao auctionPetInfoDao = new AuctionPetInfoDao();
		auctionPetInfoDao.deleteSuperfluousInfo(pPk);
		return petinfolist;
	}

	//得到拍卖宠物后的金钱列表
	public List<AuctionPetVO> getAuctionPetMoneyList(int pPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		List<AuctionPetVO> list = auctionPetDao.getPetMoneyList(pPk);
		return list;
	}

	/** 得到未拍卖出的宠物列表 */
	public List<AuctionPetVO> getAuctionPetGoodsList(int pPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		List<AuctionPetVO> list = auctionPetDao.getPetGoodsList(pPk);
		return list;
	}
	
	/** 根据petPk查看该宠物的有关信息 */
	public AuctionPetVO getAuctionPetView(String petPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		AuctionPetVO vo = auctionPetDao.getPetInfoView(petPk);
		return vo;
	}
	
	/** 根据petPk查看该宠物的拍卖价格 */
	public int getPetInfoPrice(String petPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		int price = auctionPetDao.getPetInfoPrice(petPk);
		return price;
	}

	/**
	 * 取回拍卖宠物所得的银,删除宠物拍卖场此条记录，并发信息到
	 * 宠物拍卖信息表和个人邮件中.
	 * @param pk
	 * @param petPk
	 * @param petPrice
	 * @return
	 */
	public String getPetMoneyBack(int pPk, String petPk, int petPrice)
	{
		StringBuffer sb = new StringBuffer();
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(pPk+"");

		
		AuctionPetDao petDao = new AuctionPetDao();
		String petName = petDao.getPetInfoName(petPk);
		
		//取回拍卖宠物所得的银子
		//int backPrice = (int)(petPrice * AuctionNumber.AUCTIONNUMBER);
		int backPrice = (petPrice);
		
		//监控
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", backPrice+"", "取回拍卖宠物的银两");
		
		roleInfo.getBasicInfo().addCopper(backPrice);
		
		//删除此条记录
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		int delete = auctionPetDao.deleteAuctionPet(petPk);
		
		//发信息到宠物拍卖信息表
		AuctionPetInfoDao petInfoDao = new AuctionPetInfoDao();
		sb.append("您取回了拍卖").append(StringUtil.isoToGBK(petName)).append("所得的"+MoneyUtil.changeCopperToStr(backPrice)).append("!");
		String title = "宠物拍卖场邮件通知";
		petInfoDao.insertPetInfo(pPk,sb.toString());
		
		//邮件通知
		MailInfoService mailInfoService = new MailInfoService();
		int mail = mailInfoService.sendMailBySystem(pPk,title,sb.append("(系统邮件请勿回复!)").toString());
		
		if( delete != -1 && mail != -1){
			StringBuffer sbu = new StringBuffer();
			sbu.append("您取回了拍卖").append(StringUtil.isoToGBK(petName)).append("所得的"+MoneyUtil.changeCopperToStr(backPrice));
			return sbu.toString();
		}else {
			StringBuffer sbu = new StringBuffer();
			sbu.append("您取回了金钱时发生错误，请稍后再试！");
			return sbu.toString();
		}
	}

	/**
	 * 取回未拍卖出去的宠物，删除宠物拍卖场此条记录，并发信息到
	 * 宠物拍卖信息表和个人邮件中.
	 * @param pPk
	 * @param petPk
	 * @return
	 */
	public String getPetBack(int pPk, String petPk)
	{
		StringBuffer sb = new StringBuffer();
		
		//取回未拍卖出去的宠物
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		AuctionPetVO vo = auctionPetDao.getPetInfoView(petPk);
		int flag = auctionPetDao.insertPersonPet(pPk,vo);
		
		//删除此条记录
		int delete = auctionPetDao.deleteAuctionPet(petPk);
		
		//发信息到宠物拍卖信息表
		AuctionPetInfoDao petInfoDao = new AuctionPetInfoDao();
		String petName = vo.getPetName();
		sb.append("您取回了拍卖").append(StringUtil.isoToGBK(petName));
		String title = "宠物拍卖场邮件通知";
		petInfoDao.insertPetInfo(pPk,sb.toString());
		
		//邮件通知
		MailInfoService mailInfoService = new MailInfoService();
		int mail = mailInfoService.sendMailBySystem(pPk,title,sb.append("(系统邮件请勿回复!)").toString());
		
		if(flag != -1 && delete != -1 && mail != -1){
			StringBuffer sbu = new StringBuffer();
			sbu.append("您取回了拍卖").append(StringUtil.isoToGBK(petName));
			return sbu.toString();
		}else {
			StringBuffer sbu = new StringBuffer();
			sbu.append("您取回了宠物时发生错误，请稍后再试！");
			return sbu.toString();
		}
	}
	
	/**
	 * 分页:得到拍卖场里的特定类型的物品名
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePetByName(int p_pk,String pet_name,int page_no,String sortType)
	{
		AuctionPetDao auctionDao = new AuctionPetDao();
		return auctionDao.getPagePetByName(pet_name, page_no,sortType);
	}

	/**
	 * 分页:得到拍卖场里的特定类型的物品名
	 * @param page_no
	 * @param searchType
	 * @return
	 */
	public QueryPage getPagePropList(int page_no, String searchType)
	{
		dealwith();
		
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		QueryPage queryPage = auctionPetDao.getPetList(page_no,searchType);
		
		return queryPage;
	}
	
	//处理有关宠物拍卖表的操作
	private void dealwith()
	{
		//超过三天还未被买的下架
		updateThanThreeDay();
		
		//自开始拍卖六日内未取回的流拍物品，将被系统没收
		updateThanSixDay();
		
		//自拍卖成功七日内，未取回的拍卖金钱被系统没收
		updateMoneySevenDay();
		
	}


	//自拍卖成功七日内，未取回的拍卖金钱被系统没收
	private void updateMoneySevenDay()
	{
		AuctionPetInfoDao auctionPetInfoDAO = new AuctionPetInfoDao();
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		
		//获得拍卖成功七日内未取回拍卖金钱的列表
		List<AuctionPetVO> list = auctionPetDao.getThanSevenDay();
		MailInfoService mailInfo = new MailInfoService();
		
		if(list == null || list.size() == 0){
			//给拍卖成功七日内未取回拍卖金钱的拍卖者发拍卖提示到拍卖信息里.
			String info1 = "您拍卖";
			String info2 = "成功已经超过七天，由于您没有及时的取回银两，这些银两已经被系统收回！";
			String title = "拍卖场信息提示";
			String info3 = "系统消息请勿回复！";
			AuctionPetVO vo = null;
			for(int i=0;i<list.size();i++){
					vo = list.get(i);
					auctionPetInfoDAO.insertAuctionInfo(vo,info1+StringUtil.isoToGBK(vo.getPetName())+info2);
					//给拍卖成功七日的拍卖者发邮件
					mailInfo.sendMailBySystem(vo.getPPk(),title,info1+StringUtil.isoToGBK(vo.getPetName())+info2+info3);
			}
		}
		
		auctionPetDao.updateMoneySevenDay();
		
	}


	//自开始拍卖六日内未取回的流拍宠物，将被系统没收
	private void updateThanSixDay()
	{
		AuctionPetInfoDao auctionPetInfoDAO = new AuctionPetInfoDao();
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		
		//获得拍卖六日内未取回的拍卖宠物列表
		List<AuctionPetVO> list = auctionPetDao.getThanSixDayList();
		MailInfoService mailInfo = new MailInfoService();
		
		if(list == null || list.size() == 0){
			//给拍卖六日内未取回宠物的拍卖者发拍卖提示到拍卖信息里.
			String info1 = "您所拍卖的";
			String info2 = "退出拍卖已经超过三天，由于您没有及时的取回宠物，该宠物已经会被系统收回！";
			String title = "拍卖场信息提示";
			String star = "×";
			String info3 = "系统消息请勿回复！";
			AuctionPetVO vo = null;
			for(int i=0;i<list.size();i++){
					vo = list.get(i);
					auctionPetInfoDAO.insertAuctionInfo(vo,info1+StringUtil.isoToGBK(vo.getPetName())+info2);
					//给超过六日的拍卖者发邮件
					mailInfo.sendMailBySystem(vo.getPPk(),title,info1+StringUtil.isoToGBK(vo.getPetName())+info2+info3);
			}
		}
		
		auctionPetDao.deleteThanSixDay();
		
	}


	//超过三天还未被买的下架
	private void updateThanThreeDay()
	{
		AuctionPetInfoDao auctionPetInfodao = new AuctionPetInfoDao();
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		//获得三天未被买的拍卖宠物列表
		List<AuctionPetVO> list = auctionPetDao.getThanThreeDayList();
		MailInfoService mailInfo = new MailInfoService();
		
		if(list == null || list.size() == 0){
			//给超过三天的宠物的拍卖者发拍卖提示到拍卖信息里.
			String info1 = "您所拍卖的";
			String info2 = "拍卖时间已经超过三天，现已退出拍卖，请您于三日内到拍卖场仓库取回宠物！";
			String title = "宠物拍卖场信息提示";
			String star = "×";
			String info3 = "系统消息请勿回复！";
			AuctionPetVO vo = null;
			for(int i=0;i<list.size();i++){
					vo = list.get(i);
					auctionPetInfodao.insertAuctionInfo(vo,info1+StringUtil.isoToGBK(vo.getPetName())+info2);
					//给超过三天的拍卖者发邮件 
					mailInfo.sendMailBySystem(vo.getPPk(),title,info1+StringUtil.isoToGBK(vo.getPetName())+info2+info3);
			}
		}
		auctionPetDao.updateThanThreeDay();
	}
}
