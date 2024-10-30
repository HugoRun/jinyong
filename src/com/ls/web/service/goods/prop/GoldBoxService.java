package com.ls.web.service.goods.prop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.iface.function.Probability;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.PropType;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.rank.RankService;
import com.pm.constant.NpcGaiLv;
import com.pm.dao.untangle.UntangLeDao;
import com.pm.service.systemInfo.SystemInfoService;

public class GoldBoxService
{

	/**
	 * 打开黄金宝箱
	 * @param pg_pk
	 * @param gold_box_pgpk
	 * @param roleInfo
	 * @return
	 */
	public String openGoldBox(String pg_pk, String gold_box_pgpk,
			RoleEntity roleInfo)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		String reuslWmlString = "";
		GoodsService goodsService = new GoodsService();
		
		if (roleInfo.getBasicInfo().getWrapSpare() < 1) {
			reuslWmlString = "您物品栏空间不够,请保留足够的空间再尝试开启宝箱!";
			return reuslWmlString;
		}
		
		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10级以下不能打开黄金宝箱!";
			return reuslWmlString;
		}
	 	 // 首先检验这两个道具是不是真的都在玩家身上。
		 if ( checkHaveThisProp(pg_pk,Integer.parseInt(gold_box_pgpk),roleInfo)) {
			 TimeControlService timeControlService = new TimeControlService();
			// 查看其是否在 100次的范围之内
				if ( timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, 
								TimeControlService.ANOTHERPROP, 100)) {
				
    			 	// 开始掉落物品
    			 	PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(gold_box_pgpk));
    			 	PropVO propVO = PropCache.getPropById(groupVO.getPropId());
    			 	//String npcId = getDuiYingGrade(roleInfo,propVO);
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// 清楚原来的掉落物
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// 黄金宝箱掉落八个物品
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// 删除掉这两个道具
    			 	PlayerPropGroupVO groupVO2 = dao.getByPgPk(Integer.parseInt(pg_pk));
    			 	goodsService.removeProps(groupVO2, 1);
    			 	goodsService.removeProps(groupVO, 1);
    			 	
    			 	// 记录下宝箱的信息
    			 	String recordString = roleInfo.getBasicInfo().getName()+"天眼符一个宝箱";
    				insertRecordInfo(roleInfo.getBasicInfo().getPPk(),2,recordString);
    				
				} else {
					 reuslWmlString = "这两个道具可能已经不在您身上了!";
					
				}
		 } else {
			 reuslWmlString = "您开启宝箱超过100次了!";
		 }
		return reuslWmlString;
	}
	
	
	/**
	 * 打开其他宝箱(非黄金宝箱)
	 * @param pg_pk
	 * @param gold_box_pgpk
	 * @param roleInfo
	 * @return
	 */
	public String openGoldBoxByOther(String pg_pk, String gold_box_pgpk,
			RoleEntity roleInfo)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		String reuslWmlString = "";
		GoodsService goodsService = new GoodsService();
		
		if (roleInfo.getBasicInfo().getWrapSpare() < 1) {
			reuslWmlString = "您物品栏空间不够,请保留足够的空间再尝试开启宝箱!";
			return reuslWmlString;
		}
		
		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10级以下不能打开此宝箱!";
			return reuslWmlString;
		}
	 	 // 首先检验这两个道具是不是真的都在玩家身上。
		 if ( checkHaveThisProp(pg_pk,Integer.parseInt(gold_box_pgpk),roleInfo)) {
			 
    			 	// 开始掉落物品
    			 	PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(gold_box_pgpk));
    			 	PropVO propVO = PropCache.getPropById(groupVO.getPropId());
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// 清楚原来的掉落物
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// 黄金宝箱掉落八个物品
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// 删除掉这两个道具
    			 	PlayerPropGroupVO groupVO2 = dao.getByPgPk(Integer.parseInt(pg_pk));
    			 	goodsService.removeProps(groupVO2, 1);
    			 	goodsService.removeProps(groupVO, 1);
    			 	
    			 	
			 } else {
				 reuslWmlString = "这两个道具可能已经不在您身上了!";				
    		 }
		return reuslWmlString;
	}

	/**
	 * 开启黄金宝箱消息
	 */
	private String openGoldBoxInfo(RoleEntity roleInfo)
	{
		TimeControlService timeControlService = new TimeControlService();
		int already_num = timeControlService.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(), 
				PropType.GOLD_BOX, TimeControlService.ANOTHERPROP) ;
		String info = "";
		if (already_num % 10 == 0 && already_num != 0) {
			SystemInfoService infoService = new SystemInfoService();
			info = "哇～"+roleInfo.getBasicInfo().getName()+"实在是太富有了，他（她）今天已经开启"+
					already_num+"个鸿蒙仙藏，并获得系统赠送的【仙藏玉符】一个！";
			infoService.insertSystemInfoBySystem(info) ;
			GoodsService goodsService = new GoodsService();
			PropDao propDao = new PropDao();
			int prop_id = propDao.getPropIdByType(PropType.GOLD_KEY);
			goodsService.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(), prop_id, GoodsType.PROP, 1);
		}
			
		
		return info;
	}

	/**
	 * 把宝箱的信息记录下来
	 * @param pk
	 * @param info_type
	 * @param content
	 */
	private void insertRecordInfo(int pPk, int info_type, String content)
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		playerPropGroupDao.sendRecordGoldInfo(pPk,info_type,content);
			if(info_type == 1){
				//统计需要
				new RankService().updateAdd(pPk, "open", 1);
			}	
	}


	/**
	 * 得到对应
	 * @param roleInfo
	 * @param propVO 
	 * @return
	 */
	public int getDuiYingGrade(RoleEntity roleInfo, PropVO propVO)
	{
		// 第一特殊字段是 npc分段
		String propOperate1 = propVO.getPropOperate1();
		// 第三特殊字段是 等级分段
		String gradeFenBu = propVO.getPropOperate3();
		String[] npcIdStrings = propOperate1.split(";");
		
		// 得到当前等级段的位置
		int gradeZhi = getGradeNum(gradeFenBu,roleInfo.getBasicInfo().getGrade());
		String npcString = npcIdStrings[gradeZhi];
		
		List<Probability> list = new ArrayList<Probability>();
		NpcGaiLv npcGaiLv = null;
		String[] npcGaolv = npcString.split(",");
		for ( int i =0;i<npcGaolv.length;i++) {
			npcGaiLv = new NpcGaiLv();
			String[]  npcBenStrings = npcGaolv[i].split("-");
			npcGaiLv.setId(Integer.parseInt(npcBenStrings[0]));
			npcGaiLv.setProbability(Integer.parseInt(npcBenStrings[1]));
			list.add(npcGaiLv);
		}
		
		Probability probability = MathUtil.getRandomEntityFromList(list,MathUtil.DENOMINATOR);
		int npcID = probability.getId();
		
		return npcID;
	} 
	
	
	/**
	 * 得到等级在字符串中的位置
	 * @param gradeFenBu 形如40,50,60 这样的字符串
	 * @param grade	玩家等级
	 * @return
	 */
	public int getGradeNum(String gradeFenBu, int grade)
	{
		String[] gradeFeBu = gradeFenBu.split(",");
		
		if ( grade <= Integer.parseInt(gradeFeBu[0])) {
			return 0;
		} else if ( grade > Integer.parseInt(gradeFeBu[0]) && grade <= Integer.parseInt(gradeFeBu[1])) {
			return 1;
		} else if  ( grade > Integer.parseInt(gradeFeBu[1]) && grade <= Integer.parseInt(gradeFeBu[2])) {
			return 2;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[2]) && grade <= Integer.parseInt(gradeFeBu[3])) {
			return 3;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[3]) && grade <= Integer.parseInt(gradeFeBu[4])) {
			return 4;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[4]) && grade <= Integer.parseInt(gradeFeBu[5])) {
			return 5;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[5]) && grade <= Integer.parseInt(gradeFeBu[6])) {
			return 6;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[6]) && grade <= Integer.parseInt(gradeFeBu[7])) {
			return 7;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[7]) && grade <= Integer.parseInt(gradeFeBu[8])) {
			return 8;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[8]) && grade <= Integer.parseInt(gradeFeBu[9])) {
			return 9;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[9]) && grade <= Integer.parseInt(gradeFeBu[10])) {
			return 10;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[10]) && grade <= Integer.parseInt(gradeFeBu[11])) {
			return 11;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[11]) && grade <= Integer.parseInt(gradeFeBu[12])) {
			return 12;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[12]) && grade <= Integer.parseInt(gradeFeBu[13])) {
			return 13;
		}  else if  ( grade > Integer.parseInt(gradeFeBu[13])) {
			return 14;
		} 
		return 0;
	}

	/**
	 * 查看所需要的物品是否都在玩家身上
	 * @param pg_pk
	 * @param gold_box_pgpk
	 * @param roleInfo
	 * @return
	 */
	private boolean checkHaveThisProp(String pg_pk, int gold_box_pgpk,
			RoleEntity roleInfo)
	{
		// 
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
	 	PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(pg_pk),roleInfo.getBasicInfo().getPPk());
		 
		//List<Integer> list = dao.getHpMp(roleInfo.getBasicInfo().getPPk(), PropType.TIANYANFU);
	 	if (groupVO == null) {
	 		return false;
	 	}
	 	PlayerPropGroupVO boxVO = dao.getByPgPk(gold_box_pgpk,roleInfo.getBasicInfo().getPPk());
		
	 	//List<Integer> list2 = dao.getHpMp(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX);
	 	if (boxVO == null) {
	 		return false;
	 	}
		return true;
	}

	/**
	 * 查看其是否有金钥匙
	 * @param roleInfo
	 * @return
	 */
	public boolean checkHaveGoldYueShi(RoleEntity roleInfo,String key_id)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		//int prop_num = dao.getPropNumByPropType(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_KEY);
		boolean flag = dao.getUserHasProp(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(key_id));
		
		return flag;
		
	}	

	/**
	 * 获得黄金宝箱里的物品
	 * @param roleInfo
	 * @param key_id 金钥匙ID
	 */
	public String  getGoldBoxGoods(RoleEntity roleInfo,String key_id,PropVO keyVO,PropVO boxVO,HttpServletResponse response,HttpServletRequest request)
	{
		TimeControlService timeControlService = new TimeControlService();
		NpcService npcService = new NpcService();
		
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		int result = 0;
		String resultWmlString = "";
		
		
		if (dropgoods!= null && dropgoods.size() != 0) {
			// 查看其是否拥有金钥匙
			if ( checkHaveGoldYueShi(roleInfo,key_id)) {
				// 查看其是否在 101次的范围之内
				if ( timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, 
								TimeControlService.ANOTHERPROP, 101)) {
				
    			Random random = new Random();
    			int num = random.nextInt(dropgoods.size()) ;
    			DropGoodsVO dropGoods = dropgoods.get(num);
    			
    			
    			// 将玩家应该获得的物品信息记录下来
    			String hintString = roleInfo.getBasicInfo().getName()+"用了金钥匙,应该获得"+dropGoods.getGoodsName()+"*"+dropGoods.getDropNum();
    			insertRecordInfo(roleInfo.getBasicInfo().getPPk(),2,hintString);
    			
    			GoodsService goodsService = new GoodsService();
    			result = goodsService.pickupGoods(roleInfo.getBasicInfo().getPPk(), dropGoods.getDPk(),0,response,request);
    			
    			if ( result == -1) {
    				resultWmlString = "您的包裹空间不够!";
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    			} else {
    				this.getGoldBoxInfo(dropGoods,roleInfo,timeControlService);
    				goodsService.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(key_id), 1,GameLogManager.R_USE);
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    				
    				insertRecordInfo(roleInfo.getBasicInfo().getPPk(),1,hintString);
    				// 给次数控制增加1    			 	
    			 	timeControlService.updateControlInfo(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, TimeControlService.ANOTHERPROP);
    			 	//玩家的开宝箱总次数增加
    			 	playerOpenGoldBox(roleInfo.getBasicInfo().getUPk(), roleInfo.getBasicInfo().getPPk());
    			 	
    			 	// 额外的宝箱消息
    				String reuslWmlString = openGoldBoxInfo(roleInfo);
    			 	
    			 	resultWmlString = "您使用了一个"+keyVO.getPropName()+"打开了"+boxVO.getPropName()+"，一件金光闪闪的灵宝呈现眼前!<br/> 您获得了"+dropGoods.getGoodsName()+"×"+dropGoods.getDropNum()
					+"<br/>"+reuslWmlString;
    			}
				} else {
					// 开启黄金宝箱超过100的
					resultWmlString = "您开启宝箱超过100次了!";
					npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
					
				}
			} else {
				// 如果没有金钥匙就开启宝箱失败
				resultWmlString = "您没有"+keyVO.getPropName()+",开启失败!";
				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			}
		}else {
			resultWmlString = "您已经开启过宝箱!";			
		}
		return resultWmlString;
	}
	
	/**
	 * 获得其他宝箱(非黄金宝箱)里的物品
	 * @param roleInfo
	 * @param key_id 金钥匙ID
	 */
	public String  getGoldBoxGoodsByOtherBox(RoleEntity roleInfo,String key_id,PropVO keyVO,PropVO boxVO,HttpServletResponse response,HttpServletRequest request)
	{
		//TimeControlService timeControlService = new TimeControlService();
		NpcService npcService = new NpcService();
		
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		int result = 0;
		String resultWmlString = "";
				
		if (dropgoods!= null && dropgoods.size() != 0) {
			// 查看其是否拥有金钥匙
			if ( checkHaveGoldYueShi(roleInfo,key_id)) {
				
    			Random random = new Random();
    			int num = random.nextInt(dropgoods.size()) ;
    			DropGoodsVO dropGoods = dropgoods.get(num);
    			GoodsService goodsService = new GoodsService();
    			result = goodsService.pickupGoods(roleInfo.getBasicInfo().getPPk(), dropGoods.getDPk(),0,response,request);
    			
    			if ( result == -1) {
    				resultWmlString = "您的包裹空间不够!";
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    			} else {
    				goodsService.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(key_id), 1,GameLogManager.R_USE);
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    				resultWmlString = "您使用了一个"+keyVO.getPropName()+"打开了"+boxVO.getPropName()+"，一件金光闪闪的宝贝闪现在您眼前!<br/> 您获得了"+dropGoods.getGoodsName()+"×"+dropGoods.getDropNum();	
    			}
				
			} else {
				// 如果没有金钥匙就开启宝箱失败
				resultWmlString = "您没有"+keyVO.getPropName()+",开启失败!";
				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			}
		}else {
			resultWmlString = "您已经开启过宝箱!";			
		}
		return resultWmlString;
	}
	
	
	
	
	

	/**
	 * 捡取到特殊物品时发消息
	 * @param dropGoods
	 * @param roleInfo
	 */
	private void getGoldBoxInfo(DropGoodsVO dropGoods, RoleEntity roleInfo,TimeControlService timeControlService)
	{
		String info = "";
		if ( dropGoods.getGoodsImportance() == 1) {
    		SystemInfoService infoService = new SystemInfoService();
    		int already_num = timeControlService.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(), 
    				PropType.GOLD_BOX, TimeControlService.ANOTHERPROP) ;
    		info = "恭喜"+roleInfo.getBasicInfo().getName()+"开启第"+already_num+"个黄金宝箱时获得了"+dropGoods.getGoodsName()+"!";
    		infoService.insertSystemInfoBySystem(info) ;
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		playerPropGroupDao.sendGoldInfo(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getGrade(),
							dropGoods.getGoodsId(),dropGoods.getGoodsName(),dropGoods.getGoodsQuality());
		
	}

	/**
	 * 放弃黄金宝箱里的物品
	 * @param roleInfo
	 * @return
	 */
	public String dropGoldBoxGoods(RoleEntity roleInfo,PropVO boxVO)
	{
		
		NpcService npcService = new NpcService();
		npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
		
		String hint = "您放弃了"+boxVO.getPropName()+"里的物品!";
		return hint;
	}
	
	/**
	 * 用金钥匙打开其他宝箱(非黄金宝箱)
	 * @param pg_pk
	 * @param gold_box_pgpk
	 * @param roleInfo
	 * @return
	 */
	public String zhiJieOpenGoldBoxByOther(String pg_pk,
			RoleEntity roleInfo,String box_id)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		String reuslWmlString = "";
		GoodsService goodsService = new GoodsService();
		
		// 获得黄金宝箱的id
		int prop_id = Integer.parseInt(box_id);
		
		if (roleInfo.getBasicInfo().getWrapSpare() < 1) {
			reuslWmlString = "您物品栏空间不够,请保留足够的空间再尝试开启宝箱!";
			return reuslWmlString;
		}

		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10级以下不能打开此宝箱!";
			return reuslWmlString;
		}
		// 获得黄金宝箱的pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo.getBasicInfo().getPPk(),prop_id);
		
		PlayerPropGroupVO keyGroupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	 // 首先检验这两个道具是不是真的都在玩家身上。
		 if ( keyGroupVO != null) {
			 
					// 开始掉落物品
    			 	PropVO propVO = PropCache.getPropById(prop_id);
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// 清楚原来的掉落物
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// 黄金宝箱掉落八个物品
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// 删除掉黄金宝箱
    			 	goodsService.removeProps(boxGroupVO, 1);
    			 	
    			 	
			} else {
			 reuslWmlString = "黄金宝箱可能已经不在您身上了!";
			}
		return reuslWmlString;
	}
	
	
	/**
	 * 判断是否可以打开黄金宝箱
	 * @param pg_pk
	 * @param box_id
	 * @param roleInfo
	 * @return
	 */
	public String isOpenGoldBox(String pg_pk,
			RoleEntity roleInfo,String box_id)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		String reuslWmlString = "";
		GoodsService goodsService = new GoodsService();
		
		int prop_id = Integer.parseInt(box_id);
		//判断是否可以拿到金钥匙
		if (roleInfo.getBasicInfo().getWrapSpare() < 2) {
			reuslWmlString = "您物品栏空间不够,请保留足够的空间再尝试开启宝箱!";
			return reuslWmlString;
		}

		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10级以下不能打开黄金宝箱!";
			return reuslWmlString;
		}
		// 获得黄金宝箱的pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo.getBasicInfo().getPPk(),prop_id);
		
		PlayerPropGroupVO keyGroupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	 // 首先检验这两个道具是不是真的都在玩家身上。
		 if ( keyGroupVO != null) {
			 TimeControlService timeControlService = new TimeControlService();
			// 查看其是否在 100次的范围之内
				if ( timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, 
								TimeControlService.ANOTHERPROP, 100)) {
					
					// 开始掉落物品
    			 	PropVO propVO = PropCache.getPropById(prop_id);
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// 清楚原来的掉落物
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// 黄金宝箱掉落八个物品
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// 删除掉黄金宝箱
    			 	goodsService.removeProps(boxGroupVO, 1);
    			 	
				} else {
					 reuslWmlString = "您每天最多只能开启100个黄金宝箱!";
				}
		 } else {
			 reuslWmlString = "黄金宝箱可能已经不在您身上了!";
		 }
		return reuslWmlString;
	}
	
	//分页
	public QueryPage getGoldBoxList(int p_pk, String  prop_id,int page_no)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		return dao.getGoldBoxList(p_pk, prop_id,page_no);
	}
	
	//判断玩家是否有黄金宝箱记录
	private void playerOpenGoldBox(int u_pk,int p_pk){
		UntangLeDao dao = new UntangLeDao();
		int result = dao.getPlayerGoldBoxRecord(u_pk, p_pk);
		if(result == -1){
			dao.insertPlayerGoldBoxRecord(u_pk, p_pk);
			dao.updatePlayerGoldBoxRecord(u_pk, p_pk);
		}else{
			dao.updatePlayerGoldBoxRecord(u_pk, p_pk);
		}
	}
}
