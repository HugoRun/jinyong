package com.pm.service.job.weekly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ben.rank.model.Rank;
import com.ben.shitu.model.ShituConstant;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Equip;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.pm.service.mail.MailInfoService;

public class WeekRankStatisticsJob implements Job
{
	Logger logger = Logger.getLogger("log.rank");

	/**
	 * 执行每周一凌晨零点的给JOB
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		JobDetail jobDetail = arg0.getJobDetail();  
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		
		String path = (String) jobDataMap.get("path");
		
		String vlaue1 = (String) jobDataMap.get("key1");
		String vlaue2 = (String) jobDataMap.get("key2");
		String vlaue3 = (String) jobDataMap.get("key3");
		String vlaue4 = (String) jobDataMap.get("key4");
		String vlaue5 = (String) jobDataMap.get("key5");
		String vlaue6 = (String) jobDataMap.get("key6");
		String vlaue7 = (String) jobDataMap.get("key7");
		String vlaue8 = (String) jobDataMap.get("key8");
		
		String vlaue9 = (String) jobDataMap.get("key9");
		String vlaue10 = (String) jobDataMap.get("key10");
		String vlaue11 = (String) jobDataMap.get("key11");
		String vlaue12 = (String) jobDataMap.get("key12");
		String vlaue13 = (String) jobDataMap.get("key13");
		String vlaue14 = (String) jobDataMap.get("key14");
		
		recordPaiMingData(vlaue1, path);
		recordPaiMingData(vlaue2, path);
		recordPaiMingData(vlaue3, path);
		recordPaiMingData(vlaue4, path);
		recordPaiMingData(vlaue5, path);
		
		recordPaiMingData(vlaue6, path);
		recordPaiMingData(vlaue7, path);
		recordPaiMingData(vlaue8, path);
		
		recordPaiMingData(vlaue9, path);
		recordPaiMingData(vlaue10, path);
		recordPaiMingData(vlaue11, path);
		
		recordPaiMingData(vlaue12, path);
		recordPaiMingData(vlaue13, path);
		recordPaiMingData(vlaue14, path);
		
		this.sendGongZiMail();
		logger.debug("邮件清理结束");
	}

	/**
	 * 每周一凌晨零点执行记录玩家排名信息
	 */
	public void recordPaiMingData(String vlaue,String path)
	{
		RankService rankService = new RankService();
		if ( vlaue.equals("tuxue")) {
			/*TongRankingService tongRankingService = new TongRankingService();
			List list = tongRankingService.getTongRankingList();
			chargeForPaiMing(list,vlaue,path);		*/
			return ;
		} else if ( vlaue.equals("congwu")) {
			List<PetInfoVO> list = rankService.fintbyExp();
			chargeForPaiMing(list,vlaue,path);		
			return ;
		}else if ( vlaue.equals("kuangshou")) {
			List<PetInfoVO> list = rankService.fintbyGONGji();
			chargeForPaiMing(list,vlaue,path);		
			return ;
		}else if ( vlaue.equals("shengbang")) {
			List<Rank> list = rankService.findSheng1();
			chargeForPaiMing(list,vlaue,path);		
			return ;
		} else if ( vlaue.equals("shenbin")) {
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			List equip_list = playerEquipDao.getRankList(Equip.WEAPON);
			chargeForPaiMing(equip_list,vlaue,path);		
			return ;
		}else if(vlaue.trim().equals("wei_task")){
//			统计威望
			return ;
		}else if(vlaue.trim().equals("yi")){
			List<FriendVO> list = rankService.paiFriend(1);
    		chargeForPaiMing(list,vlaue,path);	
    		return ;
		}
		else if(vlaue.trim().equals("p_exp")){
			List<Rank> list = rankService.findByFieldaLL(vlaue, 1);
    		chargeForPaiMing(list,vlaue,path);	
    		rankService.clear("p_exp");
    		for(Rank rank : list){
    			if(rank.getP_level()==ShituConstant.MAX_LEVEL){
    				rankService.updatePpk(rank.getId());
    			}
    		}
    		return ;
		}
		else if ( vlaue != null && !vlaue .equals("") && !vlaue.equals("null")) {
    		List<Rank> list = rankService.findByFieldaLL(vlaue, 0);
    		chargeForPaiMing(list,vlaue,path);	
    		if(!vlaue.trim().equals("money")&&!vlaue.trim().equals("credit")&&!vlaue.trim().equals("wei_task")){
    		rankService.clear(vlaue.trim());
    		}
		}
	}

	/**
	 * 
	 * @param list
	 * @param string
	 */
	private void chargeForPaiMing(List list, String field,String path)
	{
		if ( field.equals("killnpc")) {
			dealWithKillnpc(list,field,path);
		} else if ( field.equals("ans")) {
			dealWithAns(list,field,path);
		} else if ( field.equals("dear")) {
			dealWithDear(list,field,path);
		} else if ( field.equals("zhong")) {
			dealWithZhong(list,field,path);
		} else if ( field.equals("yi")) {
			dealWithYi(list,field,path);
		} else if ( field.equals("money")) {
			dealWithMoney(list,field,path);
		} else if ( field.equals("dead")) {
			dealWithDead(list,field,path);
		} else if ( field.equals("open")) {
			dealWithOpen(list,field,path);
		}
		else if ( field.equals("p_exp")) {
			dealWithExp(list,field,path);
		}else if ( field.equals("kill")) {
			dealWithKill(list,field,path);
		}else if ( field.equals("tuxue")) {
			dealWithTuxue(list,field,path);
		}else if ( field.equals("evil")) {
			dealWithEvil(list,field,path);
		}else if ( field.equals("killboss")) {
			dealWithKillboss(list,field,path);
		}else if ( field.equals("meng")) {
			dealWithMeng(list,field,path);
		}else if ( field.equals("sale")) {
			dealWithSale(list,field,path);
		}else if ( field.equals("vip")) {
			dealWithVip(list,field,path);
		}else if ( field.equals("rongyi")) {
			dealWithRongyi(list,field,path);
		}else if ( field.equals("congwu")) {
			dealWithCongWu(list,field,path);
		}	
		else if ( field.equals("yuanbao")) {
			dealWithYuanbao(list,field,path);
		}else if ( field.equals("kuangshou")) {
			dealWithKuangshou(list,field,path);
		}else if ( field.equals("shenbin")) {
			dealWithShenbin(list,field,path);
		}
		else if ( field.equals("credit")) {
			dealWithCredit(list,field,path);
		}else if ( field.equals("wei_task")) {
			
		}else if ( field.equals("shengbang")) {
			dealWithShengbang(list,field,path);
		}else if(field.trim().equals("boyi")){
			dealWithBoyi(list,field,path);
		}else if(field.trim().equals("lost")){
			dealWithLost(list,field,path);
		}
	}
	
	/**
	 * 迷宫
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithLost(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" lost : ").append(rank.getLost()).append(" lost_Time : ").append(rank.getLost_time())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);			
	}
	
	/**
	 * 元宝榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithBoyi(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" boyi_yuanbao : ").append(rank.getBoyi()).append(" boyi_yuanbaoTime : ").append(rank.getBoyi_time())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);			
	}

	/**
	 * 江湖圣榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithShengbang(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()+" name : "+rank.getP_name()+" exp : "+rank.getP_exp()+" zaixiantime : "+rank.getZhong()+" credit : "+rank.getCredit()).append("\r\n");
		}
		inputIO(paimingString,field,path);			
	}

	/**
	 * 声望榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithCredit(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" credit : ").append(rank.getCredit()).append(" creditTime : ").append(rank.getCredit_time())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);			
	}

	/**
	 * 神兵榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithShenbin(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		PlayerEquipVO equip = null;
		for ( int i=list.size()-1;i>=0;i--) {
			equip = (PlayerEquipVO) list.get(i);
			paimingString.append("ppk : "+equip.getPPk()).append(" name : "+equip.getRoleName()).append(" partEquipVOName : ").append(equip.getWName()).append(" PwPk : ").append(equip.getPwPk()).append("\r\n");		
		}
		inputIO(paimingString,field,path);			
	}

	/**
	 * 狂兽榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithKuangshou(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		PetInfoVO petInfoVO = null;
		for ( int i=list.size()-1;i>=0;i--) {
			petInfoVO = (PetInfoVO) list.get(i);
			paimingString.append("ppk ： ").append(petInfoVO.getPPk()).append(" petInfoVOName : ").append(petInfoVO.getPetName()).append(" pet_gj_da : ").append(petInfoVO.getPetGjDa())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);	
	}

	/**
	 * 元宝榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithYuanbao(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" yuanbao : ").append(rank.getYuanbao()).append(" yuanbaoTime : ").append(rank.getYuanbao_time())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);			
	}

	/**
	 * 宠物榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithCongWu(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		PetInfoVO petInfoVO = null;
		for ( int i=list.size()-1;i>=0;i--) {
			petInfoVO = (PetInfoVO) list.get(i);
			paimingString.append("ppk : "+petInfoVO.getPPk()).append(" petInfoVOName : ").append(petInfoVO.getPetName()).append(" petInfoVOExp : ").append(petInfoVO.getPetExp())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);		
	}

	/**
	 * 荣誉榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithRongyi(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" glory : ").append(rank.getGlory()).append(" gloryTime : ").append(rank.getGlory_time())
					.append("\r\n");		
		}
		inputIO(paimingString,field,path);	
	}

	/**
	 * 处理VIP
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithVip(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" vip : ").append(rank.getVip()).append(" vip_eff : ").append(rank.getVip_eff())
					.append(" vipTime : ").append(rank.getVip_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
		
	}

	/**
	 * 处理买卖榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithSale(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" sale : ").append(rank.getSale()).append(" saleTime : ").append(rank.getSale_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
	}

	/**
	 * 处理猛将榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithMeng(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" meng : ").append(rank.getMeng()).append(" mengTime : ").append(rank.getMeng_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
	}

	/**
	 * 处理击杀Boss
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithKillboss(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" killboss : ").append(rank.getKillboss()).append(" killbossTime : ").append(rank.getKillboss_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
		
	}

	/**
	 * 处理罪恶值
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithEvil(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" evil : ").append(rank.getEvil()).append(" evilTime : ").append(rank.getEvil_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
		
	}

	/**
	 * 处理屠血榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithTuxue(List list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
/*		RankingVO rankingVO = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rankingVO = (RankingVO) list.get(i);
			paimingString.append("bangID : "+rankingVO.getTPk()).append(" bangName : "+rankingVO.getTName()).append(" battleNumb : ").append(rankingVO.getBattleNumb()).append("\r\n");		
		}
*/		inputIO(paimingString,field,path);	
	}

	/**
	 * 处理杀人榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithKill(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" kill : ").append(rank.getKill()).append(" killTime : ").append(rank.getKill_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
	}

	/**
	 * 处理npc击杀榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithKillnpc(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" killnpc : ").append(rank.getKillnpc()).append(" killnpcTime : ").append(rank.getKillnpc_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);	
		
	}

	/**
	 * 处理开宝箱数
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithOpen(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" open : ").append(rank.getOpen()).append(" openTime : ").append(rank.getOpen_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);		
		
	}

	/**
	 * 处理死亡数
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithDead(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" dead : ").append(rank.getDead()).append(" deadTime : ").append(rank.getDead_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);		
	}

	/**
	 * 处理金钱
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithMoney(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" money : ").append(rank.getMoney()).append(" moneyTime : ").append(rank.getMoney_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);		
		
	}

	/**
	 * 处理义气榜
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithYi(List<FriendVO> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		FriendVO rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (FriendVO) list.get(i);
			paimingString.append("ppk : "+rank.getPPk()).append(" name : "+(new RoleService().getName(rank.getPPk()+"")[0])).append(" withwho : "+rank.getFdName()).append(" yi : ").append(rank.getDear()).append(" yiTime : ").append(rank.getTim()).append("\r\n");		
		}
		inputIO(paimingString,field,path);		
	}

	/**
	 * 处理忠心度
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithZhong(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" zhong : ").append(rank.getZhong()).append(" zhongTime : ").append(rank.getZhong_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);
		
	}

	/**
	 * 处理亲密度
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithDear(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" withwho : "+rank.getWho()).append(" dear : ").append(rank.getDear()).append(" dearTime : ").append(rank.getDear_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);
		
	}

	/**
	 * 
	 * @param list
	 * @param field
	 * @param path
	 */
	private void dealWithAns(List<Rank> list, String field, String path)
	{
		StringBuffer paimingString = new StringBuffer();		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" ans : ").append(rank.getAns()).append(" ansTime : ").append(rank.getAns_time()).append("\r\n");		
		}
		inputIO(paimingString,field,path);		
	}

	/**
	 * 处理对于经验排名的问题
	 * @param list
	 * @param field
	 */
	private void dealWithExp(List<Rank> list,String field,String path)
	{
		StringBuffer paimingString = new StringBuffer();
		
		Rank rank = null;
		for ( int i=list.size()-1;i>=0;i--) {
			rank = (Rank) list.get(i);
			paimingString.append("ppk : "+rank.getP_pk()).append(" name : "+rank.getP_name()).append(" exp : ").append(rank.getP_exp()).append(" expTime : ").append(rank.getP_exp_time()).append("\r\n");		
		}
		//inputIO(paimingString,field,path);
		inputIO(paimingString,field,path);
	}

	/**
	 * 将文件写入log中
	 * @param paimingString
	 * @param field
	 */
	private void inputLog(StringBuffer paimingString, String field)
	{
		logger.debug(field+"--------"+paimingString.toString());
	}

	/**
	 * 将文件写入硬盘中
	 * @param paimingString
	 * @param field
	 */
	private void inputIO(StringBuffer paimingString, String field,String path)
	{
		inputLog(paimingString,field);
		String todayStr = DateUtil.getTodayStr();		
		File file =  new File(GameConfig.getRankPath()+todayStr+field+".txt");
		boolean flag = makeDirectory(file);
		if (!flag ) {
			makeDirectory(file);
		}
		FileOutputStream fileOutputStream;
		try
		{
			fileOutputStream = new FileOutputStream(file);
    		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
    		outputStreamWriter.write(paimingString.toString());
    		
    		outputStreamWriter.flush();
    		fileOutputStream.flush();
    		outputStreamWriter.close();
    		fileOutputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	    * 创建指定的目录。
	    * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	    * <b>注意：可能会在返回false的时候创建部分父目录。</b>
	    * @param fileName 要创建的目录的目录名
	    * @return 完全创建成功时返回true，否则返回false。
	    */
	public static boolean makeDirectory(String fileName) {
	     File file = new File(fileName);
	     return makeDirectory(file);
	  }
	
	  /**
	    * 创建指定的目录。
	    * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
	    * <b>注意：可能会在返回false的时候创建部分父目录。</b>
	    * @param file 要创建的目录
	    * @return 完全创建成功时返回true，否则返回false。
	    */
	   public static boolean makeDirectory(File file) {
	     File parent = file.getParentFile();
	     if (parent != null) {
	       return parent.mkdirs();
	     }
	     return false;
	   }

	/**
	 * 执行每周一凌晨零点的给玩家发邮件
	 */
	private void sendGongZiMail() {

		MailInfoService mailInfoService = new MailInfoService();
		String mailContent = "一周内在线时间达到210分钟以上即可前往伏羲(妖)或后土(巫)领取工资!";
		String title = "系统工资通知";
		PartInfoDao partInfoDao = new PartInfoDao();
		List<Integer> allPPkList  = partInfoDao.getAllPPkList();
		int p_pk = 0;
		
		
//		MailInfoDao mailInfoDao = new MailInfoDao();
//		mailInfoDao.sendDataByBatch(allPPkList,title,mailContent);
		
		for ( int i = 0; i<allPPkList.size();i++) {
			p_pk = allPPkList.get(i);
			mailInfoService.sendMailBySystem(p_pk, title, mailContent);			
		}		
	}

}
