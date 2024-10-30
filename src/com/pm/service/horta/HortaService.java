package com.pm.service.horta;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.dp.dao.credit.CreditProce;
import com.dp.service.credit.CreaditService;
import com.dp.vo.credit.PlayerCreditVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.model.vip.VipManager;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.EconomyService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.dao.horta.HortaDao;
import com.pm.vo.horta.HortaVO;

public class HortaService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ����������͵Ľ�������
	 * @return
	 */
	public List<HortaVO> getMainList()
	{
		HortaDao hortadao = new HortaDao();
		
		return hortadao.getMainList();
	}

	/**
	 * ��ȡĳһ�������������еĽ�������
	 * 
	 * @return
	 */
	public List<HortaVO> getHortaSonList(String main_type)
	{
		HortaDao hortadao = new HortaDao();		
		return hortadao.getHortaSonList(main_type);
	}

	/**
	 * ��⽱����������Щ���Ա����
	 * @param horList
	 * @param ableList
	 * @param unAbleList
	 * @param roleEntity
	 */
	public String checkAbleList(List<HortaVO> horList, List<HortaVO> ableList,
			List<HortaVO> unAbleList, RoleEntity roleEntity)
	{
		String hint = null;
		if ( horList == null || horList.size() == 0) {
			hint = "û�н���ѡ��!";
			return hint;
		}
		
		HortaVO hortaVO = null;
		for ( int i = horList.size() - 1;i>= 0;i--) {
			hortaVO = horList.get(i);
			if ( isUseAble(roleEntity,hortaVO)) {
				// �������ȡ
				ableList.add(hortaVO);
			} else {
//				// ���������ȡ
				String hint1 = onlyCheckEnoughNumber(hortaVO,roleEntity);
		        if ( hint1 == null || hint1.equals("") || hint1.equals("null") ) {
		        	unAbleList.add(hortaVO);
		        }				
			}
		}
		return null;
	}

	/**
	 * �Ƿ������ȡ,���������ȡ�ͷ���true,���򷵻�false
	 * @param roleEntity
	 * @param hortaVO
	 * @return
	 */
	private boolean isUseAble(RoleEntity roleEntity, HortaVO hortaVO)
	{
		/**
		 * ��Ա�ȼ��ж�
		 */
		if ( !hortaVO.getVipGrade().equals("0")) {
			boolean flag = false;
			Vip vip = roleEntity.getTitleSet().getVIP();
			if ( vip != null) {
					String myvip = vip.getLevel()+"";
					String[] vipid_str =  hortaVO.getVipGrade().split(",");
					for(int i = 0 ; i<vipid_str.length;i++){
						if(myvip.equals(vipid_str[i])){
							flag = true;
							break;
						}
					}
			}
			if (!flag) {
				String vip_level = hortaVO.getVipGrade();
				Vip vip_info = VipManager.getByLevel(Integer.parseInt(vip_level));
				hortaVO.setDisplay("��Ҫ��Ϊ"+vip_info.getName());
				return false;
			}
		}
		
		
		// �ȼ��ж�
		if ( !hortaVO.getWjGrade().equals("0")) {
			String[] wjGrade =  hortaVO.getWjGrade().split(",");  
			int grade  =  roleEntity.getBasicInfo().getGrade();
			// �������д���ļ�����,�򷵻�false
			if ( grade < Integer.parseInt(wjGrade[0]) || grade > Integer.parseInt(wjGrade[1])) {
				hortaVO.setDisplay("����"+(Integer.parseInt(wjGrade[0]) - grade) +"��");
				//hortaVO.setDisplay("��Ҫ�ȼ���"+wjGrade[0]+"-"+wjGrade[1]+"֮��!");
				return false;			
			}
		}
	
        
        /**
         * �������ʱ��
         */
        if ( hortaVO.getOnlineTime() != 0) {
        	int priz_online_time = roleEntity.getStateInfo().getPrizeOnlineTime();//�����콱������ʱ��
        	
        	if ( hortaVO.getOnlineTime() > priz_online_time ) {
        		double chazhi = hortaVO.getOnlineTime() - priz_online_time;
        		hortaVO.setDisplay("����"+DateUtil.returnStr(chazhi));
        		return false;
        	} 
        }
        
        String hint = onlyCheckEnoughNumber(hortaVO,roleEntity);
        if ( hint != null && !hint.equals("") && !hint.equals("null") ) {
        	return false;        	
        }
		        
		return true;
	}
	
	
	/**
	 * ���صķ���
	 * 
	 */
	public String checkAbleHorta(HortaVO hortaVO, RoleEntity roleEntity)
	{
		
		String hint = null;		
		/**
		 * ��Ա�ȼ��ж�
		 */
		if ( !hortaVO.getVipGrade().equals("0")) {
			boolean flag = true;
			Vip vip = roleEntity.getTitleSet().getVIP();
			if ( vip != null) {
					String myvip = ","+vip.getLevel()+",";
					if ( hortaVO.getVipGrade().indexOf(myvip) > -1 ) {
						flag = false ;
					}
			}
			
			if ( !flag) {
				hint = "���Ļ�Ա�ȼ�����!";
				return hint;
			}
		}
		
		// �ȼ��ж�
		if ( !hortaVO.getWjGrade().equals("0")) {
			String[] wjGrade =  hortaVO.getWjGrade().split(",");  
			int grade  =  roleEntity.getBasicInfo().getGrade();
			// �������д���ļ�����,�򷵻�false
			if ( grade < Integer.parseInt(wjGrade[0]) || grade > Integer.parseInt(wjGrade[1])) {
				hint = "���ȼ�����!��ȡ�˽�����Ҫ�ȼ���"+wjGrade[0]+"-"+wjGrade[1]+"֮��!";
				
				return hint;
			}
		}
        /**
         * �������ʱ��
         */
        if ( hortaVO.getOnlineTime() != 0) {
        	int priz_online_time = roleEntity.getStateInfo().getPrizeOnlineTime();//�����콱������ʱ��
        	if ( hortaVO.getOnlineTime() > priz_online_time ) {
        		hint = "��������ʱ���޷���ȡ�˽���!";
				return hint;
        	}
        }
        
        hint = checkHasEnoughWrapSpare(hortaVO,roleEntity);
        if ( hint != null && !hint.equals("") && !hint.equals("null")) {
			return hint;
		}
        hint = checkEnoughNumber(hortaVO,roleEntity);
        return hint;
	}
	

	/**
	 * �����Ƿ�����ȡ��Ʒ,�������,����ԭ��,�������,����null
	 * @param hor_id
	 * @param roleEntity
	 * @return
	 */
	public String checkAbleHorta(String hor_id, RoleEntity roleEntity)
	{
		HortaDao hortadao = new HortaDao();
		HortaVO hortaVO = hortadao.getHortaByHorId(hor_id);
		return this.checkAbleHorta(hortaVO, roleEntity);
        
	}

	/**
	 * �Ƿ����㹻����ȡ����
	 * @param hortaVO
	 * @param roleEntity
	 * @return
	 */
	private String checkEnoughNumber(HortaVO hortaVO, RoleEntity roleEntity)
	{
		TimeControlService timeControlService = new TimeControlService();
		int isonlyone = hortaVO.getIsOnlyOne();
		int onces = Integer.parseInt(hortaVO.getOnces());
		if ( isonlyone > 0) {
			int numb = timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(),TimeControlService.JIANGLI);
			if ( numb < isonlyone) {
				timeControlService.updateControlInfo(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI);
				return null;
			} else {
				return "�˽��������ֻ����ȡ"+isonlyone+"��!";				
			}
			
		} else if ( isonlyone == 0) {
			if ( timeControlService.isUseable(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI,onces)) {
				timeControlService.updateControlInfo(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI);
				return null;
			} else {
				return "�˽�����ÿ�����ֻ����ȡ"+isonlyone+"��!";				
			}
		}		
		return "��������ȡ!";
	}
	
	
	/**
	 * �Ƿ����㹻����ȡ����
	 * @param hortaVO
	 * @param roleEntity
	 * @return
	 */
	private String onlyCheckEnoughNumber(HortaVO hortaVO, RoleEntity roleEntity)
	{
		TimeControlService timeControlService = new TimeControlService();
		int isonlyone = hortaVO.getIsOnlyOne();
		int onces = Integer.parseInt(hortaVO.getOnces());
		if ( isonlyone > 0) {
			int numb = timeControlService.alreadyUseNumber(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(),TimeControlService.JIANGLI);
			if ( numb < isonlyone) {
				return null;
			} else {
				return "�˽��������ֻ����ȡ"+isonlyone+"��!";				
			}
			
		} else if ( isonlyone == 0) {
			if ( timeControlService.isUseable(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI,onces)) {
				return null;
			} else {
				return "�˽�����ÿ�����ֻ����ȡ"+isonlyone+"��!";				
			}
		}		
		return "��������ȡ!";
	}

	/**
	 * �������Ƿ����㹻�İ���
	 * @param hortaVO
	 * @param roleEntity
	 * @return
	 */
	private String checkHasEnoughWrapSpare(HortaVO hortaVO,
			RoleEntity roleEntity)
	{
		String giveGoodStrings = hortaVO.getGiveGoods();
		if ( giveGoodStrings == null || giveGoodStrings.equals("") || giveGoodStrings.equals("null")) {
			return null;
		}
		
		String result = null;
		
		int warespare = roleEntity.getBasicInfo().getWrapSpare();
		if(hortaVO.getHortaType() == 1){
			String[] giveGoods = giveGoodStrings.split(";");
			int week = new Date().getDay();
			String [] giveGoods_week = giveGoods[week].split(",");
			for ( int i=0;i<giveGoods_week.length;i++) {
				String[] recourse = giveGoods_week[i].split("-");
				if(recourse[0].equals("z")){
					//���ϰ����ռ�С�����ܷ���Ŀռ�ʱ
					warespare = warespare -1;
					if(warespare < 0 ){
						i = -1;
						result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
						return result;
					}
				} else if(recourse[0].equals("d")){
					PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
					int accumulate = propvo.getPropAccumulate();
					int num = Integer.valueOf(recourse[3])/accumulate +(Integer.valueOf(recourse[3])%accumulate == 0? 0:1);
					warespare = warespare -num;
					if(warespare <0){
						i = -1;
						result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
						return result;
					}
				} else if(recourse[0].equals("j")){
				 	long partCopper = roleEntity.getBasicInfo().getCopper();
				 	logger.info("���ϵ�Ǯ:"+partCopper+",�һ�����Ǯ:"+Long.valueOf(recourse[1]));
				 	
				 	if(partCopper + Long.valueOf(recourse[1]) > 1000000000){
				 		i = -1;
				 		result = "���Ѿ�����Я����������ˣ�";
				 		return result;
				 	}
				}
			
			}
		}else{
			String[] giveGoods = giveGoodStrings.split(",");
			for ( int i=0;i<giveGoods.length;i++) {
				String goodsString = giveGoods[i];
				String[] recourse = goodsString.split("-");
				if(recourse[0].equals("z")){
					//���ϰ����ռ�С�����ܷ���Ŀռ�ʱ
					warespare = warespare -1;
					if(warespare < 0 ){
						i = -1;
						result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
						return result;
					}
				} else if(recourse[0].equals("d")){
					PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
					int accumulate = propvo.getPropAccumulate();
					int num = Integer.valueOf(recourse[3])/accumulate +(Integer.valueOf(recourse[3])%accumulate == 0? 0:1);
					warespare = warespare -num;
					if(warespare <0){
						i = -1;
						result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
						return result;
					}
				} else if(recourse[0].equals("j")){
				 	long partCopper = roleEntity.getBasicInfo().getCopper();
				 	logger.info("���ϵ�Ǯ:"+partCopper+",�һ�����Ǯ:"+Long.valueOf(recourse[1]));
				 	
				 	if(partCopper + Long.valueOf(recourse[1]) > 1000000000){
				 		i = -1;
				 		result = "���Ѿ�����Я����������ˣ�";
				 		return result;
				 	}
				}
			}
		}
		return result;
	}

	/**
	 * ����ҽ�����Ʒ
	 * @param hor_id
	 * @param roleEntity
	 * @return
	 */
	public String giveJiangLi(String hor_id, RoleEntity roleInfo)
	{
		HortaDao hortadao = new HortaDao();
		HortaVO hortaVO = hortadao.getHortaByHorId(hor_id);
		int pPk = roleInfo.getBasicInfo().getPPk();
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("�������");
		String giveGoodStrings = hortaVO.getGiveGoods();
		if ( giveGoodStrings == null || giveGoodStrings.equals("") || giveGoodStrings.equals("null")) {
			return null;
		}
		
		if(hortaVO.getHortaType() == 1){
			String[] giveGoods = giveGoodStrings.split(";");
			int week = new Date().getDay();
			String [] giveGoods_week = giveGoods[week].split(",");
			for(int t=0;t<giveGoods_week.length;t++){
				String[] unit = giveGoods_week[t].split("-");
				if(unit[0].equals("d")){							//������������ӵ���
					GoodsService goodsService = new GoodsService();
					goodsService.putGoodsToWrap(pPk,Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3]));
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("��").append(Integer.valueOf(unit[3]));
				}else if(unit[0].equals("j")){						//������������ӽ�Ǯ				
					//���
					LogService logService = new LogService();
					logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", Integer.valueOf(unit[1])+"", "�һ��õ�");
					
					roleInfo.getBasicInfo().addCopper(Integer.valueOf(unit[1]));
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(6, StatisticsType.MONEY,Integer.valueOf(unit[1]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append("��Ǯ").append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])));
				}
				if (t < giveGoods.length-1 ){
					resultWml.append(",");
				}
			}
		}else{
			String[] giveGoods = giveGoodStrings.split(",");
			for(int t=0;t<giveGoods.length;t++){
				String[] unit = giveGoods[t].split("-");
				if(unit[0].equals("d")){							//������������ӵ���
					GoodsService goodsService = new GoodsService();
					goodsService.putGoodsToWrap(pPk,Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3]));
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("��").append(Integer.valueOf(unit[3]));
				}else if(unit[0].equals("z")){						//�������������װ��
					GoodsService goodsService = new GoodsService();
					for(int i = 0;i<Integer.valueOf(unit[3]);i++){	//Ҫ������װ���͸�����
						goodsService.putGoodsToWrap(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3]));
						//ִ��ͳ��
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
						
					}
					resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("��").append(Integer.valueOf(unit[3]));
				}else if(unit[0].equals("j")){						//������������ӽ�Ǯ				
					//���
					LogService logService = new LogService();
					logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", Integer.valueOf(unit[1])+"", "�һ��õ�");
					
					roleInfo.getBasicInfo().addCopper(Integer.valueOf(unit[1]));
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(6, StatisticsType.MONEY,Integer.valueOf(unit[1]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append("��Ǯ").append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])));
				}else if(unit[0].equals("y")){						//������������Ӿ���
					//���
					LogService logService = new LogService();
					logService.recordExpLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCurExp(), Integer.parseInt(unit[1])+"", "�һ��õ�");
					
					roleInfo.getBasicInfo().updateAddCurExp(Integer.parseInt(unit[1]));
					resultWml.append(Integer.valueOf(unit[1])+"����");
				}else if(unit[0].equals("s")){						//�����������������
					Integer cid=Integer.parseInt(unit[1]);
					Integer ncount=Integer.parseInt(unit[2]);
					CreaditService service=new CreditProce();
					service.addPlayerCredit(pPk, cid, ncount);
					PlayerCreditVO pcv=service.getPcvDisplay(cid);
					resultWml.append(pcv.getCreditname()).append("��").append(ncount);
				}else if(unit[0].equals("r")){						//�����������������
					Integer ncount=Integer.parseInt(unit[1]);
					CreaditService service=new CreditProce();
					service.addPlayerHonor(pPk,ncount);
					resultWml.append("����ֵ").append("��").append(ncount);											
				}else if(unit[0].equals("yb")) {					//�������������Ԫ��
					logger.info("�������������"+GameConfig.getYuanbaoName()+"="+unit[1]);
					PartInfoDAO partInfoDAO = new PartInfoDAO();
					int uPk = partInfoDAO.getPartuPk(pPk);
					resultWml.append(GameConfig.getYuanbaoName()).append("��").append(Integer.parseInt(unit[1]));					
					
					EconomyService economyService = new EconomyService();
					//���������Ԫ�� 
					economyService.addYuanbao(pPk, uPk, Integer.parseInt(unit[1]),StatisticsType.JIANGLI);
				}
				
				if (t < giveGoods.length-1 ){
					resultWml.append(",");
				}
			}	
		}
		if( hortaVO.getHortaType()==3 )//����������콱�����������콱������ʱ��
		{
			long now_time = Calendar.getInstance().getTimeInMillis();
			roleInfo.getStateInfo().setPrizeConsumeTime((int)(now_time - roleInfo.getStateInfo().getLoginTime()) / 1000);
		}
		
		return resultWml.toString();
	}

	/**
	 * ȷ��ѡ�����Ƿ��п�����ȡ��
	 * @param horList
	 * @param roleEntity
	 */
	public String checkHasNew(List<HortaVO> horList, RoleEntity roleEntity)
	{
		HortaVO hortatypevo = null;
		String hortHint="";
		for ( int i=0;i<horList.size();i++) {
			hortatypevo = horList.get(i);
			int main_type =  hortatypevo.getHortaType();
			List<HortaVO> list = this.getHortaSonList(main_type+"");
			HortaVO hortaVO = null;
			for ( int t=0;t<list.size();t++) {
				hortaVO = list.get(t);
				boolean flag = this.isUseAble(roleEntity,hortaVO);
				if ( flag) {
					hortatypevo.setDisplay("(��)");
					hortHint="(��)";
				}
			}
		}
		return hortHint;
	}
}
