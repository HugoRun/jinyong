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
	 * 获得所有类型的奖励类型
	 * @return
	 */
	public List<HortaVO> getMainList()
	{
		HortaDao hortadao = new HortaDao();
		
		return hortadao.getMainList();
	}

	/**
	 * 获取某一奖励类型下所有的奖励内容
	 * 
	 * @return
	 */
	public List<HortaVO> getHortaSonList(String main_type)
	{
		HortaDao hortadao = new HortaDao();		
		return hortadao.getHortaSonList(main_type);
	}

	/**
	 * 检测奖励条件中那些可以被完成
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
			hint = "没有奖励选项!";
			return hint;
		}
		
		HortaVO hortaVO = null;
		for ( int i = horList.size() - 1;i>= 0;i--) {
			hortaVO = horList.get(i);
			if ( isUseAble(roleEntity,hortaVO)) {
				// 如果能领取
				ableList.add(hortaVO);
			} else {
//				// 如果不能领取
				String hint1 = onlyCheckEnoughNumber(hortaVO,roleEntity);
		        if ( hint1 == null || hint1.equals("") || hint1.equals("null") ) {
		        	unAbleList.add(hortaVO);
		        }				
			}
		}
		return null;
	}

	/**
	 * 是否可以领取,如果可以领取就返回true,否则返回false
	 * @param roleEntity
	 * @param hortaVO
	 * @return
	 */
	private boolean isUseAble(RoleEntity roleEntity, HortaVO hortaVO)
	{
		/**
		 * 会员等级判断
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
				hortaVO.setDisplay("需要成为"+vip_info.getName());
				return false;
			}
		}
		
		
		// 等级判断
		if ( !hortaVO.getWjGrade().equals("0")) {
			String[] wjGrade =  hortaVO.getWjGrade().split(",");  
			int grade  =  roleEntity.getBasicInfo().getGrade();
			// 如果不在写定的级域内,则返回false
			if ( grade < Integer.parseInt(wjGrade[0]) || grade > Integer.parseInt(wjGrade[1])) {
				hortaVO.setDisplay("还差"+(Integer.parseInt(wjGrade[0]) - grade) +"级");
				//hortaVO.setDisplay("需要等级在"+wjGrade[0]+"-"+wjGrade[1]+"之间!");
				return false;			
			}
		}
	
        
        /**
         * 玩家在线时间
         */
        if ( hortaVO.getOnlineTime() != 0) {
        	int priz_online_time = roleEntity.getStateInfo().getPrizeOnlineTime();//可用领奖的在线时间
        	
        	if ( hortaVO.getOnlineTime() > priz_online_time ) {
        		double chazhi = hortaVO.getOnlineTime() - priz_online_time;
        		hortaVO.setDisplay("还差"+DateUtil.returnStr(chazhi));
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
	 * 重载的方法
	 * 
	 */
	public String checkAbleHorta(HortaVO hortaVO, RoleEntity roleEntity)
	{
		
		String hint = null;		
		/**
		 * 会员等级判断
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
				hint = "您的会员等级不够!";
				return hint;
			}
		}
		
		// 等级判断
		if ( !hortaVO.getWjGrade().equals("0")) {
			String[] wjGrade =  hortaVO.getWjGrade().split(",");  
			int grade  =  roleEntity.getBasicInfo().getGrade();
			// 如果不在写定的级域内,则返回false
			if ( grade < Integer.parseInt(wjGrade[0]) || grade > Integer.parseInt(wjGrade[1])) {
				hint = "您等级不够!领取此奖励需要等级在"+wjGrade[0]+"-"+wjGrade[1]+"之间!";
				
				return hint;
			}
		}
        /**
         * 玩家在线时间
         */
        if ( hortaVO.getOnlineTime() != 0) {
        	int priz_online_time = roleEntity.getStateInfo().getPrizeOnlineTime();//可用领奖的在线时间
        	if ( hortaVO.getOnlineTime() > priz_online_time ) {
        		hint = "您的在线时间无法领取此奖励!";
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
	 * 返回是否能领取物品,如果不行,返回原因,如果可以,返回null
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
	 * 是否有足够的领取次数
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
				return "此奖励您最多只能领取"+isonlyone+"次!";				
			}
			
		} else if ( isonlyone == 0) {
			if ( timeControlService.isUseable(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI,onces)) {
				timeControlService.updateControlInfo(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI);
				return null;
			} else {
				return "此奖励您每天最多只能领取"+isonlyone+"次!";				
			}
		}		
		return "您不能领取!";
	}
	
	
	/**
	 * 是否有足够的领取次数
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
				return "此奖励您最多只能领取"+isonlyone+"次!";				
			}
			
		} else if ( isonlyone == 0) {
			if ( timeControlService.isUseable(roleEntity.getBasicInfo().getPPk(), hortaVO.getHortaId(), TimeControlService.JIANGLI,onces)) {
				return null;
			} else {
				return "此奖励您每天最多只能领取"+isonlyone+"次!";				
			}
		}		
		return "您不能领取!";
	}

	/**
	 * 检查玩家是否有足够的包裹
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
					//身上包裹空间小于所能放入的空间时
					warespare = warespare -1;
					if(warespare < 0 ){
						i = -1;
						result = "您身上的包裹空间不足！请先整理空间！";
						return result;
					}
				} else if(recourse[0].equals("d")){
					PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
					int accumulate = propvo.getPropAccumulate();
					int num = Integer.valueOf(recourse[3])/accumulate +(Integer.valueOf(recourse[3])%accumulate == 0? 0:1);
					warespare = warespare -num;
					if(warespare <0){
						i = -1;
						result = "您身上的包裹空间不足！请先整理空间！";
						return result;
					}
				} else if(recourse[0].equals("j")){
				 	long partCopper = roleEntity.getBasicInfo().getCopper();
				 	logger.info("身上的钱:"+partCopper+",兑换给的钱:"+Long.valueOf(recourse[1]));
				 	
				 	if(partCopper + Long.valueOf(recourse[1]) > 1000000000){
				 		i = -1;
				 		result = "您已经不能携带更多的银了！";
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
					//身上包裹空间小于所能放入的空间时
					warespare = warespare -1;
					if(warespare < 0 ){
						i = -1;
						result = "您身上的包裹空间不足！请先整理空间！";
						return result;
					}
				} else if(recourse[0].equals("d")){
					PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
					int accumulate = propvo.getPropAccumulate();
					int num = Integer.valueOf(recourse[3])/accumulate +(Integer.valueOf(recourse[3])%accumulate == 0? 0:1);
					warespare = warespare -num;
					if(warespare <0){
						i = -1;
						result = "您身上的包裹空间不足！请先整理空间！";
						return result;
					}
				} else if(recourse[0].equals("j")){
				 	long partCopper = roleEntity.getBasicInfo().getCopper();
				 	logger.info("身上的钱:"+partCopper+",兑换给的钱:"+Long.valueOf(recourse[1]));
				 	
				 	if(partCopper + Long.valueOf(recourse[1]) > 1000000000){
				 		i = -1;
				 		result = "您已经不能携带更多的银了！";
				 		return result;
				 	}
				}
			}
		}
		return result;
	}

	/**
	 * 给玩家奖励物品
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
		resultWml.append("您获得了");
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
				if(unit[0].equals("d")){							//给玩家身上增加道具
					GoodsService goodsService = new GoodsService();
					goodsService.putGoodsToWrap(pPk,Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3]));
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("×").append(Integer.valueOf(unit[3]));
				}else if(unit[0].equals("j")){						//给玩家身上增加金钱				
					//监控
					LogService logService = new LogService();
					logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", Integer.valueOf(unit[1])+"", "兑换得到");
					
					roleInfo.getBasicInfo().addCopper(Integer.valueOf(unit[1]));
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(6, StatisticsType.MONEY,Integer.valueOf(unit[1]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append("金钱").append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])));
				}
				if (t < giveGoods.length-1 ){
					resultWml.append(",");
				}
			}
		}else{
			String[] giveGoods = giveGoodStrings.split(",");
			for(int t=0;t<giveGoods.length;t++){
				String[] unit = giveGoods[t].split("-");
				if(unit[0].equals("d")){							//给玩家身上增加道具
					GoodsService goodsService = new GoodsService();
					goodsService.putGoodsToWrap(pPk,Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3]));
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("×").append(Integer.valueOf(unit[3]));
				}else if(unit[0].equals("z")){						//给玩家身上增加装备
					GoodsService goodsService = new GoodsService();
					for(int i = 0;i<Integer.valueOf(unit[3]);i++){	//要给几个装备就给几次
						goodsService.putGoodsToWrap(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3]));
						//执行统计
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
						
					}
					resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
					resultWml.append("×").append(Integer.valueOf(unit[3]));
				}else if(unit[0].equals("j")){						//给玩家身上增加金钱				
					//监控
					LogService logService = new LogService();
					logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", Integer.valueOf(unit[1])+"", "兑换得到");
					
					roleInfo.getBasicInfo().addCopper(Integer.valueOf(unit[1]));
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(6, StatisticsType.MONEY,Integer.valueOf(unit[1]), StatisticsType.DEDAO, StatisticsType.JIANGLI,pPk);
					
					resultWml.append("金钱").append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])));
				}else if(unit[0].equals("y")){						//给玩家身上增加经验
					//监控
					LogService logService = new LogService();
					logService.recordExpLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCurExp(), Integer.parseInt(unit[1])+"", "兑换得到");
					
					roleInfo.getBasicInfo().updateAddCurExp(Integer.parseInt(unit[1]));
					resultWml.append(Integer.valueOf(unit[1])+"经验");
				}else if(unit[0].equals("s")){						//给玩家身上增加声望
					Integer cid=Integer.parseInt(unit[1]);
					Integer ncount=Integer.parseInt(unit[2]);
					CreaditService service=new CreditProce();
					service.addPlayerCredit(pPk, cid, ncount);
					PlayerCreditVO pcv=service.getPcvDisplay(cid);
					resultWml.append(pcv.getCreditname()).append("×").append(ncount);
				}else if(unit[0].equals("r")){						//给玩家身上增加荣誉
					Integer ncount=Integer.parseInt(unit[1]);
					CreaditService service=new CreditProce();
					service.addPlayerHonor(pPk,ncount);
					resultWml.append("荣誉值").append("×").append(ncount);											
				}else if(unit[0].equals("yb")) {					//给玩家身上增加元宝
					logger.info("给玩家身上增加"+GameConfig.getYuanbaoName()+"="+unit[1]);
					PartInfoDAO partInfoDAO = new PartInfoDAO();
					int uPk = partInfoDAO.getPartuPk(pPk);
					resultWml.append(GameConfig.getYuanbaoName()).append("×").append(Integer.parseInt(unit[1]));					
					
					EconomyService economyService = new EconomyService();
					//给玩家增加元宝 
					economyService.addYuanbao(pPk, uPk, Integer.parseInt(unit[1]),StatisticsType.JIANGLI);
				}
				
				if (t < giveGoods.length-1 ){
					resultWml.append(",");
				}
			}	
		}
		if( hortaVO.getHortaType()==3 )//如果是在线领奖，则增加所领奖的在线时间
		{
			long now_time = Calendar.getInstance().getTimeInMillis();
			roleInfo.getStateInfo().setPrizeConsumeTime((int)(now_time - roleInfo.getStateInfo().getLoginTime()) / 1000);
		}
		
		return resultWml.toString();
	}

	/**
	 * 确定选项中是否有可以领取的
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
					hortatypevo.setDisplay("(新)");
					hortHint="(新)";
				}
			}
		}
		return hortHint;
	}
}
