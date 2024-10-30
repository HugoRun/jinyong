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
	 * ��ȡԪ��������list
	 * @param roleInfo
	 * @param page_no
	 * @return
	 */
	public QueryPage getYbPageList(RoleEntity roleInfo, int page_no,HttpServletRequest request,HttpServletResponse response)
	{
		// ��Ʒ�����¼�
		this.threeDayDown(request,response);
		AuctionYbDao ybdao  = new AuctionYbDao();
		return ybdao.getYbPageList(roleInfo,page_no);		
	}

	/**
	 * ��Ʒ�����¼�
	 */
	private void threeDayDown(HttpServletRequest request,HttpServletResponse response)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		List<AuctionYBVO> list = ybdao.getAllAuctionYbList(); 
		for(AuctionYBVO auctionYBVO:list) {
			this.sendGiveYbMail(auctionYBVO,request,response);
		}
		ybdao.updateAllSailedYuanbaoToUnSaleEd();	// ��������������ʧ�ܵ�Ԫ�������¼�״̬
	}

	/**
	 * ���� ����Ԫ����id ��� Ԫ��������Ϣ
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybId(String uyb_id,int uyb_state)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		return ybdao.getAuctionYbByUybId(uyb_id,uyb_state);
	}
	
	/**
	 * ���� ����Ԫ����id ��� Ԫ��������Ϣ
	 * @param uyb_id
	 * @return
	 */
	public AuctionYBVO getAuctionYbByUybIdAndPPk(String uyb_id,int uyb_state,int pPk)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		return ybdao.getAuctionYbByUybId(uyb_id,uyb_state,pPk);
	}

	/**
	 * ��Ԫ��
	 * @param auctionYBVO
	 * @param roleInfo
	 * @return
	 */
	public synchronized String buyYuan(AuctionYBVO auctionYBVO, RoleEntity roleInfo,HttpServletRequest request,HttpServletResponse response)
	{
		String hint = null;
		if ( auctionYBVO == null) {
			hint = "��"+GameConfig.getYuanbaoName()+"�Ѿ�������!";
			return hint;
		}
		
		AuctionYBVO vo = getAuctionYbByUybId(auctionYBVO.getUybId()+"", AuctionNumber.YUANSELLING);
		
		if ( vo == null) {
			hint = "��"+GameConfig.getYuanbaoName()+"�Ѿ�������!";
			return hint;
		}
		
		if(roleInfo.getBasicInfo().getPPk() == auctionYBVO.getPPk()){
			hint = "�����ܹ����Լ��Ķ���!";
		}
		EconomyService economyService = new EconomyService();
		PropertyService propertyService = new PropertyService();
		
		// ��������ϼ�ȥ�� ��Ҫ�Ľ�Ǯ
		economyService.spendMoney(roleInfo.getBasicInfo().getPPk(),auctionYBVO.getYbPrice());
		
		// ��������Ϣ��Ϊ�Ѿ�����״̬
		this.updateYuanbaoState(auctionYBVO.getUybId(),AuctionNumber.YUANSELLED);
		
		long oldYuanBao = economyService.getYuanbao(roleInfo.getBasicInfo().getUPk());
		
		// ��������ϼ�������� ��Ԫ��
		economyService.addYuanbao(roleInfo.getBasicInfo().getUPk(), auctionYBVO.getYbNum());
		
		hint = "����������"+MoneyUtil.changeCopperToStr(auctionYBVO.getYbPrice())+"������"+
				propertyService.getPlayerName(auctionYBVO.getPPk())+"�����ġ�"+GameConfig.getYuanbaoName()+"����"+auctionYBVO.getYbNum()+"�������С��ɾ�����"
				+(oldYuanBao+auctionYBVO.getYbNum())+".";
		
		
		String jiluString  = roleInfo.getBasicInfo().getName() + hint.substring(1);
		
		// ������¼���¼����
		this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),auctionYBVO,jiluString);
		
		// �������߷��ʼ�,�Ա����ܻ�������õ�����.
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
	 * ��������Ϣ��״̬��ΪsellState
	 * @param uybId
	 * @param sellState
	 */
	private void updateYuanbaoState(int uybId, int sellState)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.updateYuanbaoState(uybId,sellState);
		
	}

	/**
	 * ��������ʧ��,�������߷��ʼ�,�Ա����ܻ�������õ�Ԫ��.
	 * @param roleInfo	��ҵ�
	 * 
	 * @param auctionYBVO ���������
	 */
	private void sendGiveYbMail(AuctionYBVO auctionYBVO,HttpServletRequest request,HttpServletResponse response)
	{
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("");
		
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ygFormat = new SimpleDateFormat("MM-dd");
		MailInfoDao mailDao = new MailInfoDao();
		String mailTitle = ""+GameConfig.getYuanbaoName()+"����ʧ�ܣ�";
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
		
		sBuffer.append("����"+ygFormat.format(dt)+"�����ġ�"+GameConfig.getYuanbaoName()+"����"+auctionYBVO.getYbNum()+"���˹���<br/>");
		//sBuffer.append("<anchor><go method=\"post\" href=\"/auctionyb.do\">");
		sBuffer.append("<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do")+"\">");
		sBuffer.append("<postfield name=\"cmd\" value=\"n10\" />");
		sBuffer.append("<postfield name=\"uybId\" value=\""+auctionYBVO.getUybId()+"\" />");
		sBuffer.append("<postfield name=\"mailId\" value=\""+mailId+"\" />");
		sBuffer.append("</go>ȷ��</anchor>");
		mailDao.updateMail(mailId,sBuffer.toString());
	}
	
	/**
	 * �������߷��ʼ�,�Ա����ܻ�������õ�����.
	 * @param roleInfo	��ҵ�
	 * 
	 * @param auctionYBVO ���������
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
		String mailTitle = ""+GameConfig.getYuanbaoName()+"�����ɹ���";
		
		int mailId = mailInfoService.sendMailReply(auctionYBVO.getPPk(),-1,6, mailTitle, sBuffer.toString(),1);
		
		PropertyService propertyService = new PropertyService();
		
		Date dt = dFormat.parse(auctionYBVO.getAuctionTime());
		
		sBuffer.append("����"+ygormat.format(dt)+"�����ġ�"+GameConfig.getYuanbaoName()+"����"+auctionYBVO.getYbNum()+"�ѱ�"+
		propertyService.getPlayerName(roleInfo.getBasicInfo().getPPk())+"����,"+"��ȡ�������õĽ�Ǯ<br/>");
		
		sBuffer.append("<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do")+"\">");
		//sBuffer.append("<anchor><go method=\"post\" href=\"/auctionyb.do\">");
		sBuffer.append("<postfield name=\"cmd\" value=\"n9\" />");
		sBuffer.append("<postfield name=\"uybId\" value=\""+auctionYBVO.getUybId()+"\" />");
		sBuffer.append("<postfield name=\"mailId\" value=\""+mailId+"\" />");
		sBuffer.append("</go>ȷ��</anchor>");
		mailDao.updateMail(mailId,sBuffer.toString());
	}

	/**
	 * ��¼Ԫ������
	 * @param jiluString
	 */
	private void recordYuanBaoAuction(int pPk, AuctionYBVO auctionYBVO ,String jiluString)
	{
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.insertYuanbaoInfo(pPk,auctionYBVO,jiluString);
		
	}

	/**
	 * ����Ԫ��
	 * @param roleInfo
	 * @param paimaiYuanBao
	 * @param money
	 * @return
	 */
	public String auctionYuanBao(RoleEntity roleInfo, int paimaiYuanBao,
			long money)
	{
		// ��������ϼ�ȥ Ԫ��
		EconomyService economyService = new EconomyService();
		economyService.spendYuanbao(roleInfo.getBasicInfo().getUPk(),paimaiYuanBao );
		
		// ���� ������Ϣ�� Ԫ��������
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.insertYuanbao(roleInfo.getBasicInfo().getPPk(),paimaiYuanBao,money);
		
		// ������Ϣ��¼����
		//String infoString = roleInfo.getBasicInfo().getName()+"������Ԫ��*"+paimaiYuanBao+",�۸���"+money+",ʱ����"+new Date();
		//this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getPPk(),infoString);
		
		// �����ַ���
		String hint = "���Ѿ���"+MoneyUtil.changeCopperToStr(money)+"�ļ۸�������"+GameConfig.getYuanbaoName()+"��"+paimaiYuanBao+"!";
		return hint;
	}

	/**
	 * �����ʼ�ȡ�ؽ�Ǯ
	 * @param auctionYBVO
	 * @param roleInfo
	 */
	public void getMoneyByUybId(AuctionYBVO auctionYBVO, RoleEntity roleInfo,String mailId)
	{
		// ���ʼ���״̬��Ϊ4��
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.updateYuanbaoState(auctionYBVO.getUybId(), AuctionNumber.BACKED);
		
		// �� ��Ǯ �ŵ������ߵ�����
		EconomyService economyService = new EconomyService();
		economyService.addMoney(roleInfo.getBasicInfo().getPPk(), auctionYBVO.getYbPrice());
		
		// ������Ϣ��¼����
		String recordString = roleInfo.getBasicInfo().getName()+"ȡ������"+auctionYBVO.getAuctionTime()+"����Ԫ��*"+auctionYBVO.getYbNum()
								+"��õĽ�Ǯ"+auctionYBVO.getYbPrice()+",ʱ��Ϊ"+new Date();
				
		this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),auctionYBVO,recordString);
	}
	/**
	 * �����ʼ�ȡ��Ԫ��
	 * @param auctionYBVO
	 * @param roleInfo
	 */
	public void getYuanBaoByUybId(AuctionYBVO auctionYBVO, RoleEntity roleInfo,String mailId)
	{
		// ���ʼ���״̬��Ϊ4��
		AuctionYbDao ybdao  = new AuctionYbDao();
		ybdao.updateYuanbaoState(auctionYBVO.getUybId(), AuctionNumber.BACKED);
		
		// �� Ԫ�� �ŵ������ߵ�����
		EconomyService economyService = new EconomyService();
		economyService.addYuanbao(roleInfo.getBasicInfo().getUPk(), auctionYBVO.getYbNum());
		
		// ������Ϣ��¼����
		String recordString = roleInfo.getBasicInfo().getName()+"ȡ������"+auctionYBVO.getAuctionTime()+"������Ԫ��*"+auctionYBVO.getYbNum()
								+",ʱ��Ϊ"+new Date();
		this.recordYuanBaoAuction(roleInfo.getBasicInfo().getPPk(),auctionYBVO,recordString);
		
	}

}
