package com.pm.service.auction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.PropertyService;
import com.pm.constant.AuctionNumber;
import com.pm.dao.auction.AuctionYbDao;
import com.pm.dao.mail.MailInfoDao;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.auction.AuctionYBVO;

public class AuctionYbService
{

	/**
	 * 获取元宝拍卖的list
	 * @param roleInfo
	 * @param page_no
	 * @return
	 */
	public QueryPage getYbPageList(RoleEntity roleInfo, int page_no,HttpServletRequest request,HttpServletResponse response)
	{
		// 物品三天下架
		this.threeDayDown(request,response);
		AuctionYbDao ybdao  = new AuctionYbDao();
		return ybdao.getYbPageList(roleInfo,page_no);		
	}

	/**
	 * 物品三天下架
	 */
	private void threeDayDown(HttpServletRequest request,HttpServletResponse response)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		List<AuctionYBVO> list = ybdao.getAllAuctionYbList(); 
		for(AuctionYBVO auctionYBVO:list) {
			this.sendGiveYbMail(auctionYBVO,request,response);
		}
		ybdao.updateAllSailedYuanbaoToUnSaleEd();	// 将超过三天拍卖失败的元宝置于下架状态
	}

	/**
	 * 根据 拍卖元宝表id 获得 元宝拍卖信息
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybId(String uyb_id,int uyb_state)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		return ybdao.getAuctionYbByUybId(uyb_id,uyb_state);
	}
	
	/**
	 * 根据 拍卖元宝表id 获得 元宝拍卖信息
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybIdAndPPk(String uyb_id,int uyb_state,int pPk)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		return ybdao.getAuctionYbByUybId(uyb_id,uyb_state,pPk);
	}

	/**
	 * 买元宝
	 * @param auctionYBVO
	 * @param roleInfo
	 * @return
	 */
	public synchronized String buyYuan(AuctionYBVO auctionYBVO, RoleEntity roleInfo,HttpServletRequest request,HttpServletResponse response)
	{
		String hint = null;
		if ( auctionYBVO == null) {
			hint = "该"+GameConfig.getYuanbaoName()+"已经被拍卖!";
			return hint;
		}
		
		AuctionYBVO vo = getAuctionYbByUybId(auctionYBVO.getUybId()+"", AuctionNumber.YUANSELLING);
		
		if ( vo == null) {
			hint = "该"+GameConfig.getYuanbaoName()+"已经被拍卖!";
			return hint;
		}
		
		if(roleInfo.getBasicInfo().getPPk() == auctionYBVO.getPPk()){
			hint = "您不能购买自己的东西!";
		}
		EconomyService economyService = new EconomyService();
		PropertyService propertyService = new PropertyService();
		
		// 从买家身上减去所 需要的金钱
		economyService.spendMoney(roleInfo.getBasicInfo().getPPk(),auctionYBVO.getYbPrice());
		
		// 将此条信息置为已经卖出状态
		this.updateYuanbaoState(auctionYBVO.getUybId(),AuctionNumber.YUANSELLED);
		
		long oldYuanBao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
		
		// 往买家身上加上所获得 的元宝
		economyService.addYuanbao(roleInfo.getBasicInfo().getUPk(), auctionYBVO.getYbNum());
		
		hint = "您花费银两"+MoneyUtil.changeCopperToStr(auctionYBVO.getYbPrice())+"购买了"+
				propertyService.getPlayerName(auctionYBVO.getPPk())+"拍卖的【"+GameConfig.getYuanbaoName()+"】×"+auctionYBVO.getYbNum()+"。您现有【仙晶】×"
				+(oldYuanBao+auctionYBVO.getYbNum())+".";
		
		
		String jiluString  = roleInfo.getBasicInfo().getName() + hint.substring(1);
		
		// 把这次事件记录下来
		this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),auctionYBVO,jiluString);
		
		// 给拍卖者发邮件,以便其能获得他所得的银两.
		try
		{
			this.sendGiveMoneyMail(roleInfo,auctionYBVO,request, response);
		}catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return hint;
	}

	/**
	 * 将此条信息的状态置为sellState
	 * @param uybId
	 * @param sellState
	 */
	private void updateYuanbaoState(int uybId, int sellState)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.updateYuanbaoState(uybId,sellState);
		
	}

	/**
	 * 三天拍卖失败,给拍卖者发邮件,以便其能获得他所得的元宝.
	 * @param roleInfo	买家的
	 * 
	 * @param auctionYBVO 拍卖的情况
	 */
	private void sendGiveYbMail(AuctionYBVO auctionYBVO,HttpServletRequest request,HttpServletResponse response)
	{
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("");
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ygFormat = new SimpleDateFormat("MM-dd");
		MailInfoDao mailDao = new MailInfoDao();
		String mailTitle = ""+GameConfig.getYuanbaoName()+"拍卖失败！";
		MailInfoService mailInfoService = new MailInfoService();
		int mailId = mailInfoService.sendMailReply(auctionYBVO.getPPk(),-1,6, mailTitle, sBuffer.toString(),1);
		
		Date dt = null;
		try
		{
			dt = dFormat.parse(auctionYBVO.getAuctionTime());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		sBuffer.append("您在"+ygFormat.format(dt)+"拍卖的【"+GameConfig.getYuanbaoName()+"】×"+auctionYBVO.getYbNum()+"无人购买<br/>");
		//sBuffer.append("<anchor><go method=\"post\" href=\"/auctionyb.do\">");
		sBuffer.append("<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do")+"\">");
		sBuffer.append("<postfield name=\"cmd\" value=\"n10\" />");
		sBuffer.append("<postfield name=\"uybId\" value=\""+auctionYBVO.getUybId()+"\" />");
		sBuffer.append("<postfield name=\"mailId\" value=\""+mailId+"\" />");
		sBuffer.append("</go>确定</anchor>");
		mailDao.updateMail(mailId,sBuffer.toString());
	}
	
	/**
	 * 给拍卖者发邮件,以便其能获得他所得的银两.
	 * @param roleInfo	买家的
	 * 
	 * @param auctionYBVO 拍卖的情况
	 * @throws ParseException 
	 */
	private void sendGiveMoneyMail(RoleEntity roleInfo, AuctionYBVO auctionYBVO,HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("");
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat ygormat = new SimpleDateFormat("MM-dd");
		MailInfoService mailInfoService = new MailInfoService();
		MailInfoDao mailDao = new MailInfoDao();
		String mailTitle = ""+GameConfig.getYuanbaoName()+"拍卖成功！";
		
		int mailId = mailInfoService.sendMailReply(auctionYBVO.getPPk(),-1,6, mailTitle, sBuffer.toString(),1);
		
		PropertyService propertyService = new PropertyService();
		
		Date dt = dFormat.parse(auctionYBVO.getAuctionTime());
		
		sBuffer.append("您在"+ygormat.format(dt)+"拍卖的【"+GameConfig.getYuanbaoName()+"】×"+auctionYBVO.getYbNum()+"已被"+
		propertyService.getPlayerName(roleInfo.getBasicInfo().getPPk())+"购买,"+"请取回您所得的金钱<br/>");
		
		sBuffer.append("<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do")+"\">");
		//sBuffer.append("<anchor><go method=\"post\" href=\"/auctionyb.do\">");
		sBuffer.append("<postfield name=\"cmd\" value=\"n9\" />");
		sBuffer.append("<postfield name=\"uybId\" value=\""+auctionYBVO.getUybId()+"\" />");
		sBuffer.append("<postfield name=\"mailId\" value=\""+mailId+"\" />");
		sBuffer.append("</go>确定</anchor>");
		mailDao.updateMail(mailId,sBuffer.toString());
	}

	/**
	 * 记录元宝拍卖
	 * @param jiluString
	 */
	private void recordYuanBaoAuction(int pPk, AuctionYBVO auctionYBVO ,String jiluString)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.insertYuanbaoInfo(pPk,auctionYBVO,jiluString);
		
	}

	/**
	 * 拍卖元宝
	 * @param roleInfo
	 * @param paimaiYuanBao
	 * @param money
	 * @return
	 */
	public String auctionYuanBao(RoleEntity roleInfo, int paimaiYuanBao,
			long money)
	{
		// 从玩家身上减去 元宝
		EconomyService economyService = new EconomyService();
		economyService.spendYuanbao(roleInfo.getBasicInfo().getUPk(),paimaiYuanBao );
		
		// 增加 拍卖信息到 元宝拍卖场
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.insertYuanbao(roleInfo.getBasicInfo().getPPk(),paimaiYuanBao,money);
		
		// 将此信息记录下来
		//String infoString = roleInfo.getBasicInfo().getName()+"拍卖了元宝*"+paimaiYuanBao+",价格是"+money+",时间是"+new Date();
		//this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getPPk(),infoString);
		
		// 返回字符串
		String hint = "您已经以"+MoneyUtil.changeCopperToStr(money)+"的价格拍卖了"+GameConfig.getYuanbaoName()+"×"+paimaiYuanBao+"!";
		return hint;
	}

	/**
	 * 根据邮件取回金钱
	 * @param auctionYBVO
	 * @param roleInfo
	 */
	public void getMoneyByUybId(AuctionYBVO auctionYBVO, RoleEntity roleInfo,String mailId)
	{
		// 将邮件的状态置为4，
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.updateYuanbaoState(auctionYBVO.getUybId(), AuctionNumber.BACKED);
		
		// 将 金钱 放到拍卖者的身上
		EconomyService economyService = new EconomyService();
		economyService.addMoney(roleInfo.getBasicInfo().getPPk(), auctionYBVO.getYbPrice());
		
		// 将此信息记录下来
		String recordString = roleInfo.getBasicInfo().getName()+"取回了在"+auctionYBVO.getAuctionTime()+"拍卖元宝*"+auctionYBVO.getYbNum()
								+"获得的金钱"+auctionYBVO.getYbPrice()+",时间为"+new Date();
				
		this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),auctionYBVO,recordString);
	}
	/**
	 * 根据邮件取回元宝
	 * @param auctionYBVO
	 * @param roleInfo
	 */
	public void getYuanBaoByUybId(AuctionYBVO auctionYBVO, RoleEntity roleInfo,String mailId)
	{
		// 将邮件的状态置为4，
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.updateYuanbaoState(auctionYBVO.getUybId(), AuctionNumber.BACKED);
		
		// 将 元宝 放到拍卖者的身上
		EconomyService economyService = new EconomyService();
		economyService.addYuanbao(roleInfo.getBasicInfo().getUPk(), auctionYBVO.getYbNum());
		
		// 将此信息记录下来
		String recordString = roleInfo.getBasicInfo().getName()+"取回了在"+auctionYBVO.getAuctionTime()+"拍卖的元宝*"+auctionYBVO.getYbNum()
								+",时间为"+new Date();
		this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),auctionYBVO,recordString);
		
	}

}
