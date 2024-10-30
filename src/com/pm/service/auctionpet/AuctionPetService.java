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
	
	/** �������� */
	public String auctionPet(int pPk ,String petPk,int input_money){
		StringBuffer sb = new StringBuffer();
		
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(pPk+"");
		
		PetInfoDAO petInfoDao = new PetInfoDAO();
		PetInfoVO petinfovo = petInfoDao.getPetInfoView(petPk,pPk);
		if(petinfovo == null) {
			sb.append("�˳�����ܲ���������!");
			return sb.toString();
		}
		
		if (petinfovo.getPetLonge() == 0) {
			sb.append("�˳��������Ϊ��!");
			return sb.toString();
		}
		
		if(petinfovo.getPetIsBring() == 1){
			sb.append("��ս�ĳ��ﲻ��������");
		}else {
			AuctionPetDao auctionPetDao = new AuctionPetDao();
			int status = auctionPetDao.insertAuctionPet(input_money, petinfovo);
			logger.info("����ɹ�״̬:"+status);
			petInfoDao.getPetInfoDelte(petPk);
			if(status == -1){
				sb.append("�Բ���ϵͳ�������Ժ����ԣ�~");
			}else if(status == 1){
				
				//���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -(int)(input_money*(1 - AuctionNumber.AUCTIONNUMBER))+"", "�������� ��˰");
				
				//�Ӹ������ϳ�ȥӦ��˰
				roleInfo.getBasicInfo().addCopper(-(int)(input_money*(1 - AuctionNumber.AUCTIONNUMBER)));
				
				sb.append("����").append(MoneyUtil.changeCopperToStr(input_money)).append("�ļ۸������˳���: ").append(StringUtil.isoToGBK(petinfovo.getPetName()))
				.append(",��ȡ������").append(MoneyUtil.changeCopperToStr((int)(input_money*(1 - AuctionNumber.AUCTIONNUMBER)))).append(",��Ҫ���������!");
			}
		} 
		return sb.toString();
	}


	//����Ƿ����㹻��Ǯȥ��
	public String auctionHasMoney(PartInfoVO partInfoVO, AuctionPetVO auctionPetVO)
	{
		String str = "1";
		
		long body_money = Long.valueOf(partInfoVO.getPCopper());			//���ϵ�Ǯ
		
		int pet_auction_price = auctionPetVO.getPetPrice();					//�ó���������۸�
		
		if(body_money < pet_auction_price){
			str = "���Ľ�Ǯ���㣡";
		}
		return str;
	}
	
	//����Ƿ����㹻�ո�ȥ��
	public String auctionhasEnoughSpace(String pPk)
	{
		String str = "1";
		WareHouseDao wareDao = new WareHouseDao();
		int petNumber = wareDao.getPetNumber(Integer.valueOf(pPk));
		 if(petNumber >= 6){
			 str = "��û���㹻�ĳ���ռ䣡";
		 }
		return str;
	}

	/**
	 * �������
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
			return "���ܹ����Լ������ĳ���!";
		}
		if(vo.getAuctionStatus() != 1){
			return "�ó����Ѿ�����!";
		}
		
		// ����ʱ��ر����
		petAuctionInfoAndMail(pPk,petPk,auctionPetVO);
		
		int pet_auction_price = auctionPetVO.getPetPrice();					//�ó���������۸�
		//���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -pet_auction_price+"", "�����򵽳���");
		//�Ӱ������Ƴ�input_num��ͭ��
		roleInfo.getBasicInfo().addCopper(-pet_auction_price);
		//������˳����
		auctionPetDao.insertPersonPet(pPk,auctionPetVO);
		return "����"+MoneyUtil.changeCopperToStr(auctionPetVO.getPetPrice())+"�۸�������"+StringUtil.isoToGBK(auctionPetVO.getPetName());
	}

	//�������߷��ʼ�֪ͨ������������Ϣ������������Ϣ��,���˳��������״̬��Ϊ2.
	private static void petAuctionInfoAndMail(int pPk, String petPk,AuctionPetVO auctionPetVO)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(pPk+"");
		
		//�����������״̬��Ϊ4,������״̬
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		auctionPetDao.updateAuctionStatus(petPk,4);
		
		//�������߷����ʼ�֪ͨ
		StringBuffer info = new StringBuffer();
		info.append("����").append(StringUtil.isoToGBK(auctionPetVO.getPetName())).append("��").append(
							StringUtil.isoToGBK(roleInfo.getBasicInfo().getName())).append("������! ");
		String title = "�����������ʼ�֪ͨ";
		
		//�����������Ϣ������Ϣ
		AuctionPetInfoDao petInfoDao = new AuctionPetInfoDao();
		petInfoDao.insertPetInfo(auctionPetVO.getPPk(),info.append("������������ȡ������������").toString());
		
		MailInfoService mailInfoService = new MailInfoService();
		mailInfoService.sendMailBySystem(auctionPetVO.getPPk(),title,info.append("(ϵͳ�ʼ�����ظ�!)").toString());
		
	}

	//���ָ��������list���������mores��not������������Ϊmore����10��
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
		//ɾ������ʱ��˳�����10�������ݼ�¼
		AuctionPetInfoDao auctionPetInfoDao = new AuctionPetInfoDao();
		auctionPetInfoDao.deleteSuperfluousInfo(pPk);
		return petinfolist;
	}

	//�õ����������Ľ�Ǯ�б�
	public List<AuctionPetVO> getAuctionPetMoneyList(int pPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		List<AuctionPetVO> list = auctionPetDao.getPetMoneyList(pPk);
		return list;
	}

	/** �õ�δ�������ĳ����б� */
	public List<AuctionPetVO> getAuctionPetGoodsList(int pPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		List<AuctionPetVO> list = auctionPetDao.getPetGoodsList(pPk);
		return list;
	}
	
	/** ����petPk�鿴�ó�����й���Ϣ */
	public AuctionPetVO getAuctionPetView(String petPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		AuctionPetVO vo = auctionPetDao.getPetInfoView(petPk);
		return vo;
	}
	
	/** ����petPk�鿴�ó���������۸� */
	public int getPetInfoPrice(String petPk)
	{
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		int price = auctionPetDao.getPetInfoPrice(petPk);
		return price;
	}

	/**
	 * ȡ�������������õ���,ɾ������������������¼��������Ϣ��
	 * ����������Ϣ��͸����ʼ���.
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
		
		//ȡ�������������õ�����
		//int backPrice = (int)(petPrice * AuctionNumber.AUCTIONNUMBER);
		int backPrice = (petPrice);
		
		//���
		LogService logService = new LogService();
		logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", backPrice+"", "ȡ���������������");
		
		roleInfo.getBasicInfo().addCopper(backPrice);
		
		//ɾ��������¼
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		int delete = auctionPetDao.deleteAuctionPet(petPk);
		
		//����Ϣ������������Ϣ��
		AuctionPetInfoDao petInfoDao = new AuctionPetInfoDao();
		sb.append("��ȡ��������").append(StringUtil.isoToGBK(petName)).append("���õ�"+MoneyUtil.changeCopperToStr(backPrice)).append("!");
		String title = "�����������ʼ�֪ͨ";
		petInfoDao.insertPetInfo(pPk,sb.toString());
		
		//�ʼ�֪ͨ
		MailInfoService mailInfoService = new MailInfoService();
		int mail = mailInfoService.sendMailBySystem(pPk,title,sb.append("(ϵͳ�ʼ�����ظ�!)").toString());
		
		if( delete != -1 && mail != -1){
			StringBuffer sbu = new StringBuffer();
			sbu.append("��ȡ��������").append(StringUtil.isoToGBK(petName)).append("���õ�"+MoneyUtil.changeCopperToStr(backPrice));
			return sbu.toString();
		}else {
			StringBuffer sbu = new StringBuffer();
			sbu.append("��ȡ���˽�Ǯʱ�����������Ժ����ԣ�");
			return sbu.toString();
		}
	}

	/**
	 * ȡ��δ������ȥ�ĳ��ɾ������������������¼��������Ϣ��
	 * ����������Ϣ��͸����ʼ���.
	 * @param pPk
	 * @param petPk
	 * @return
	 */
	public String getPetBack(int pPk, String petPk)
	{
		StringBuffer sb = new StringBuffer();
		
		//ȡ��δ������ȥ�ĳ���
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		AuctionPetVO vo = auctionPetDao.getPetInfoView(petPk);
		int flag = auctionPetDao.insertPersonPet(pPk,vo);
		
		//ɾ��������¼
		int delete = auctionPetDao.deleteAuctionPet(petPk);
		
		//����Ϣ������������Ϣ��
		AuctionPetInfoDao petInfoDao = new AuctionPetInfoDao();
		String petName = vo.getPetName();
		sb.append("��ȡ��������").append(StringUtil.isoToGBK(petName));
		String title = "�����������ʼ�֪ͨ";
		petInfoDao.insertPetInfo(pPk,sb.toString());
		
		//�ʼ�֪ͨ
		MailInfoService mailInfoService = new MailInfoService();
		int mail = mailInfoService.sendMailBySystem(pPk,title,sb.append("(ϵͳ�ʼ�����ظ�!)").toString());
		
		if(flag != -1 && delete != -1 && mail != -1){
			StringBuffer sbu = new StringBuffer();
			sbu.append("��ȡ��������").append(StringUtil.isoToGBK(petName));
			return sbu.toString();
		}else {
			StringBuffer sbu = new StringBuffer();
			sbu.append("��ȡ���˳���ʱ�����������Ժ����ԣ�");
			return sbu.toString();
		}
	}
	
	/**
	 * ��ҳ:�õ�����������ض����͵���Ʒ��
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPagePetByName(int p_pk,String pet_name,int page_no,String sortType)
	{
		AuctionPetDao auctionDao = new AuctionPetDao();
		return auctionDao.getPagePetByName(pet_name, page_no,sortType);
	}

	/**
	 * ��ҳ:�õ�����������ض����͵���Ʒ��
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
	
	//�����йس���������Ĳ���
	private void dealwith()
	{
		//�������컹δ������¼�
		updateThanThreeDay();
		
		//�Կ�ʼ����������δȡ�ص�������Ʒ������ϵͳû��
		updateThanSixDay();
		
		//�������ɹ������ڣ�δȡ�ص�������Ǯ��ϵͳû��
		updateMoneySevenDay();
		
	}


	//�������ɹ������ڣ�δȡ�ص�������Ǯ��ϵͳû��
	private void updateMoneySevenDay()
	{
		AuctionPetInfoDao auctionPetInfoDAO = new AuctionPetInfoDao();
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		
		//��������ɹ�������δȡ��������Ǯ���б�
		List<AuctionPetVO> list = auctionPetDao.getThanSevenDay();
		MailInfoService mailInfo = new MailInfoService();
		
		if(list == null || list.size() == 0){
			//�������ɹ�������δȡ��������Ǯ�������߷�������ʾ��������Ϣ��.
			String info1 = "������";
			String info2 = "�ɹ��Ѿ��������죬������û�м�ʱ��ȡ����������Щ�����Ѿ���ϵͳ�ջأ�";
			String title = "��������Ϣ��ʾ";
			String info3 = "ϵͳ��Ϣ����ظ���";
			AuctionPetVO vo = null;
			for(int i=0;i<list.size();i++){
					vo = list.get(i);
					auctionPetInfoDAO.insertAuctionInfo(vo,info1+StringUtil.isoToGBK(vo.getPetName())+info2);
					//�������ɹ����յ������߷��ʼ�
					mailInfo.sendMailBySystem(vo.getPPk(),title,info1+StringUtil.isoToGBK(vo.getPetName())+info2+info3);
			}
		}
		
		auctionPetDao.updateMoneySevenDay();
		
	}


	//�Կ�ʼ����������δȡ�ص����ĳ������ϵͳû��
	private void updateThanSixDay()
	{
		AuctionPetInfoDao auctionPetInfoDAO = new AuctionPetInfoDao();
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		
		//�������������δȡ�ص����������б�
		List<AuctionPetVO> list = auctionPetDao.getThanSixDayList();
		MailInfoService mailInfo = new MailInfoService();
		
		if(list == null || list.size() == 0){
			//������������δȡ�س���������߷�������ʾ��������Ϣ��.
			String info1 = "����������";
			String info2 = "�˳������Ѿ��������죬������û�м�ʱ��ȡ�س���ó����Ѿ��ᱻϵͳ�ջأ�";
			String title = "��������Ϣ��ʾ";
			String star = "��";
			String info3 = "ϵͳ��Ϣ����ظ���";
			AuctionPetVO vo = null;
			for(int i=0;i<list.size();i++){
					vo = list.get(i);
					auctionPetInfoDAO.insertAuctionInfo(vo,info1+StringUtil.isoToGBK(vo.getPetName())+info2);
					//���������յ������߷��ʼ�
					mailInfo.sendMailBySystem(vo.getPPk(),title,info1+StringUtil.isoToGBK(vo.getPetName())+info2+info3);
			}
		}
		
		auctionPetDao.deleteThanSixDay();
		
	}


	//�������컹δ������¼�
	private void updateThanThreeDay()
	{
		AuctionPetInfoDao auctionPetInfodao = new AuctionPetInfoDao();
		AuctionPetDao auctionPetDao = new AuctionPetDao();
		//�������δ��������������б�
		List<AuctionPetVO> list = auctionPetDao.getThanThreeDayList();
		MailInfoService mailInfo = new MailInfoService();
		
		if(list == null || list.size() == 0){
			//����������ĳ���������߷�������ʾ��������Ϣ��.
			String info1 = "����������";
			String info2 = "����ʱ���Ѿ��������죬�����˳������������������ڵ��������ֿ�ȡ�س��";
			String title = "������������Ϣ��ʾ";
			String star = "��";
			String info3 = "ϵͳ��Ϣ����ظ���";
			AuctionPetVO vo = null;
			for(int i=0;i<list.size();i++){
					vo = list.get(i);
					auctionPetInfodao.insertAuctionInfo(vo,info1+StringUtil.isoToGBK(vo.getPetName())+info2);
					//����������������߷��ʼ� 
					mailInfo.sendMailBySystem(vo.getPPk(),title,info1+StringUtil.isoToGBK(vo.getPetName())+info2+info3);
			}
		}
		auctionPetDao.updateThanThreeDay();
	}
}
