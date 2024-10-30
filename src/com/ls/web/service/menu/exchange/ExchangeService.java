package com.ls.web.service.menu.exchange;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dp.dao.credit.CreditProce;
import com.dp.service.credit.CreaditService;
import com.dp.vo.credit.PlayerCreditVO;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.log.LogService;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

public class ExchangeService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 获得原材料的显示字符串
	 * @param vo	
	 * @return
	 */
	public List<String> getChangeDisplayList(OperateMenuVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getMenuOperate1();				//需要兑换的原材料	
		//String operate2 = vo.getMenuOperate2();			//兑换的目的品
		String[] reChange = operate1.split(";");			//需要兑换的原材料的数组
		//String[] reChange2 = operate2.split(";");			//兑换的目的品的数组
		if(reChange == null) {
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		
		for(int i=0;i<length;i++){					
			String[] materials = reChange[i].split(",");	//每个兑换的原材料可能不止一种
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for(int a = 0;a<materials.length;a++){
				String[] unit = materials[a].split("-");
				if(unit[0].equals("d")){					//如果原材料是道具的处理情况
					PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
					sb.append("-");
					sb.append(propvo.getPropName());
					sb.append("×").append(unit[3]);
				}else if(unit[0].equals("z")){				//如果原材料是装备的处理情况
					sb.append("-");
					int equip_id = Integer.valueOf(unit[1]);
					GameEquip equip = EquipCache.getById(equip_id);
					sb.append(equip.getName()).append("×").append(unit[2]);
				}else if(unit[0].equals("j")){		//如果原材料是金钱的处理情况
					sb.append("-").append(MoneyUtil.changeCopperToStr(unit[1]));
					
				}else if(unit[0].equals("y")){		//如果原材料是经验的处理情况
					sb.append("-").append(unit[1]);
					sb.append("经验");
				}else if(unit[0].equals("s")){		//如果原材料是声望的处理情况
					sb.append("-");
					Integer cid=Integer.parseInt(unit[1]);
					CreaditService service=new CreditProce();
					PlayerCreditVO pcv=service.getPcvDisplay(cid);
					sb.append(pcv.getCreditname());
					sb.append("×").append(unit[2]);
				}else if(unit[0].equals("r")){		//如果原材料是荣誉的处理情况
					sb.append("-").append(unit[1]);
					sb.append("荣誉");
				}
			}
				logger.info("i的value: "+i+" 原材料装入字段"+sb.toString());
				list.add(sb.toString());
			}
			return list;
		}
	
	/**
	 * 获得原材料的显示字符串
	 * @param vo	
	 * @return
	
	public List<String> getChangeDisplayList1(OperateMenuVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getMenuOperate1();				//需要兑换的原材料	
		//String operate2 = vo.getMenuOperate2();			//兑换的目的品
		String[] reChange = operate1.split(";");			//需要兑换的原材料的数组
		//String[] reChange2 = operate2.split(";");			//兑换的目的品的数组
		if(reChange == null){
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		
		for(int i=0;i<length;i++){					
			String[] materials = reChange[i].split(",");	//每个兑换的原材料可能不止一种
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for(int a = 0;a<materials.length;a++){
				String[] unit = materials[a].split("-");
				if(unit[0].equals("d")){					//如果原材料是道具的处理情况
					PropDao propDao = new PropDao();
					PropVO propvo = propDao.getById(Integer.valueOf(unit[2]));
					sb.append("-");
					sb.append(unit[3]).append("-");
					sb.append(propvo.getPropName());
					
				}else if(unit[0].equals("z")){				//如果原材料是装备的处理情况
					sb.append("-");
					sb.append(unit[3]).append("-");
					if(unit[1].equals("1")){				//1是装备，2是武器，3是首饰
						AccouteDao aDao = new AccouteDao();
						AccouteVO avo = aDao.getById(Integer.valueOf(unit[2]));
						sb.append(avo.getAccName());					
					}else if(unit[1].equals("3")){			
						JewelryDao aDao = new JewelryDao();
						JewelryVO avo = aDao.getById(Integer.valueOf(unit[2]));
						sb.append(avo.getJewName());					
					}else if(unit[1].equals("2")){			
						ArmDao aDao = new ArmDao();
						ArmVO avo = aDao.getById(Integer.valueOf(unit[2]));
						sb.append(avo.getArmName());
					}
				}else if(unit[0].equals("j")){		//如果原材料是金钱的处理情况
					sb.append("-").append(unit[1]);
					sb.append("-").append("文");
				}else if(unit[0].equals("y")){		//如果原材料是经验的处理情况
					sb.append("-").append(unit[1]);
					sb.append("-").append("经验");
				}else if(unit[0].equals("s")){		//如果原材料是声望的处理情况
					sb.append("-").append(unit[1]);
					sb.append("-").append("声望");
				}
			}
				logger.info("i的value: "+i+" 原材料装入字段"+sb.toString());
				list.add(sb.toString());
			}
			return list;
		}
	 */
	
	/**
	 * 获得兑换品的显示字符串
	 * @param vo	
	 * @return
	 */
	public List<String> getAimChangeList(OperateMenuVO vo) {
		List<String> list = null;
		String operate = vo.getMenuOperate2();				//兑换的目的品
		String[] reChange = operate.split(";");			//兑换的目的品的数组
		if(reChange == null){
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		for(int i=0;i<length;i++){	
			StringBuffer sb = new StringBuffer();
			String[] materials = reChange[i].split(",");	//每个兑换的兑换品可能不止一种
			
			for(int a = 0;a<materials.length;a++) {			//对每个兑换品进行不同的处理
			String[] unit = materials[a].split("-");		
			if(unit[0].equals("z")){						//如果兑换品是装备的处理情况
				int equip_id = Integer.valueOf(unit[1]);
				GameEquip equip = EquipCache.getById(equip_id);
				sb.append(equip.getName());
				//sb.append("×").append(unit[2]);
			}else if(unit[0].equals("d")){				//如果兑换品是道具的处理情况
				PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
				propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
				sb.append(propvo.getPropName());
				//sb.append("×").append(unit[3]);
			}else if(unit[0].equals("j")){				//如果兑换品是金钱的处理情况
				sb.append(MoneyUtil.changeCopperToStr(unit[1]));
				
			}else if(unit[0].equals("y")){				//如果兑换品是经验的处理情况
				sb.append(unit[1]).append("经验");
				
			}else if(unit[0].equals("s")){				//如果兑换品是声望的处理情况		
				Integer cid=Integer.parseInt(unit[1]);
				CreaditService service=new CreditProce();
				PlayerCreditVO pcv=service.getPcvDisplay(cid);
				if( pcv==null )
				{
					DataErrorLog.debugData("兑换菜单数据错误，无该声望，cid="+cid);
				}
				sb.append(pcv.getCreditname());
				sb.append("×").append(unit[2]);
			}else if(unit[0].equals("r")){				//如果兑换品是荣誉的处理情况		
				sb.append(unit[1]);
				sb.append("荣誉");
			}else if(unit[0].equals("yb")){				//如果兑换品是荣誉的处理情况		
				sb.append(unit[1]);
				sb.append(GameConfig.getYuanbaoName());
			}
			if(a+1 != materials.length){
				sb.append("-");
			}
			}
			logger.info("i的value: "+i+" 兑换品装入字段"+sb.toString());
			list.add(sb.toString());
			}
		return list;
	}
			/*String[] aa = reChange[i].split("-");
			if(aa[0].equals("d")){				//如果原材料是道具的处理情况
				//PropDao propDao = new PropDao();
				PropVO propvo = propDao.getById(Integer.valueOf(aa[2]));
				StringBuffer sb = new StringBuffer();
				sb.append(i);
				sb.append("-");
				sb.append(aa[3]).append("-");
				sb.append(propvo.getPropName()).append("-");

				String[] bb = reChange2[i].split("-");
				sb.append(bb[3]).append("-");
				*  /
				if(bb[0].equals("d")){				//如果被兑换物是道具的处理情况
					propvo = propDao.getById(Integer.valueOf(bb[2]));
					sb.append(propvo.getPropName());
				}else if(bb[0].equals("z")){		//如果被兑换物是装备的处理情况
					if(bb[1].equals("1")){			//1是装备，2是武器，3是首饰
						AccouteDao aDao = new AccouteDao();
						AccouteVO avo = aDao.getById(Integer.valueOf(bb[2]));
						sb.append(avo.getAccName());					
					}else if(bb[1].equals("3")){			
						JewelryDao aDao = new JewelryDao();
						JewelryVO avo = aDao.getById(Integer.valueOf(bb[2]));
						sb.append(avo.getJewName());					
					}else if(bb[1].equals("2")){			
						ArmDao aDao = new ArmDao();
						ArmVO avo = aDao.getById(Integer.valueOf(bb[2]));
						sb.append(avo.getArmName());
					}
				}
				logger.info("i的value: "+i+" 道具的装入字段"+sb.toString());
				list.add(sb.toString());
			}else if(aa[0].equals("z")){		//如果原材料是装备的处理情况
				StringBuffer sb = new StringBuffer();
				sb.append(i).append("-");
				sb.append(aa[3]).append("-");
				if(aa[1].equals("1")){			//1是装备，2是武器，3是首饰
					AccouteDao aDao = new AccouteDao();
					AccouteVO avo = aDao.getById(Integer.valueOf(aa[2]));
					sb.append(avo.getAccName()).append("-");					
				}else if(aa[1].equals("3")){			
					JewelryDao aDao = new JewelryDao();
					JewelryVO avo = aDao.getById(Integer.valueOf(aa[2]));
					sb.append(avo.getJewName()).append("-");					
				}else if(aa[1].equals("2")){			
					ArmDao aDao = new ArmDao();
					ArmVO avo = aDao.getById(Integer.valueOf(aa[2]));
					sb.append(avo.getArmName()).append("-");
				}
				String[] bb = reChange2[i].split("-");
				sb.append(bb[3]).append("-");
				
			
				if(bb[0].equals("d")){						//如果被兑换物是道具的处理情况
					PropDao propDao = new PropDao();
					PropVO propvo = propDao.getById(Integer.valueOf(bb[2]));
					sb.append(propvo.getPropName());
				}else if(bb[0].equals("z")){				//如果被兑换物是装备的处理情况
					if(bb[1].equals("1")){			//1是装备，2是武器，3是首饰
						AccouteDao aDao = new AccouteDao();
						AccouteVO avo = aDao.getById(Integer.valueOf(bb[2]));
						sb.append(avo.getAccName());					
					}else if(bb[1].equals("3")){			
						JewelryDao aDao = new JewelryDao();
						JewelryVO avo = aDao.getById(Integer.valueOf(bb[2]));
						sb.append(avo.getJewName());					
					}else if(bb[1].equals("2")){			
						ArmDao aDao = new ArmDao();
						ArmVO avo = aDao.getById(Integer.valueOf(bb[2]));
						sb.append(avo.getArmName());
					}
				}
				logger.info("i的value: "+i+" 装备的装入字段"+sb.toString());
				list.add(sb.toString());
			}*/
		

	/*
	 * 判断个人的原材料是否足够
	 * 下面对原材料是道具和是装备的情况
	 * 分别进行了判断
	 * @param pPk 个人角色id
	 * @param address 所要兑换原材料在所有源材料的位置，从0开始计数
	 * @param menu_id 目录id
	 * @return 如果成功返回1，否则返回-1.
	 */
	public String getPPkHasGoods(String pPk, String address,OperateMenuVO vo,int exchange_num)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk);
		
		int i = 1;
		String sb = "";
		String[] articles = vo.getMenuOperate1().split(";");	//所有原材料的字符串
		
		String article = articles[Integer.valueOf(address)];	//所要兑换原材料的字符串
		logger.info("原材料的article:"+article);
		
		String[] recourses = article.split(",");	
		for(int t= 0;t<recourses.length;t++) {
		
			String[] recourse = recourses[t].split("-");
			
			if(recourse[0].equals("d")){
				int number = Integer.valueOf(recourse[3]);				//原材料需要的个数
				number = exchange_num*number;							//兑换所需要的个数
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int current_num = propGroupDao.getPropNumByByPropID(Integer.valueOf(pPk), Integer.valueOf(recourse[2]));//现有的数量
				if(current_num < number){
					i = -1;
					sb = "您身上的原材料不足！";
				}
			} else if(recourse[0].equals("z")){
				int number = Integer.valueOf(recourse[2]);				//原材料需要的个数
				number = exchange_num*number;							//兑换所需要的个数
				
				//int wrapClass = storageService.getGoodsClass(Integer.valueOf(recourse[2]),Integer.valueOf(recourse[1]));
				//查找装备
				//String wrapStr = recourse[2]+ ","+String.valueOf(wrapClass)+","+recourse[1];
				//logger.info("装备字符串:"+wrapStr);
				PlayerEquipDao equipDao = new PlayerEquipDao();
				int equip_num = equipDao.getEquipNumByEquipId(Integer.valueOf(pPk),Integer.valueOf(recourse[2]),Integer.valueOf(recourse[1]));
				if(equip_num < number){
					i = -1;
					sb = "您身上的原材料不足！";
				}
			} else if(recourse[0].equals("j")){
				long copper = roleInfo.getBasicInfo().getCopper();
				copper = exchange_num*copper;							//兑换所需要的金钱
				if(copper < Long.valueOf(recourse[1])){
					i = -1;
					sb = "您身上金钱不足！";
				}
			} else if(recourse[0].equals("y")){
				long experience = Long.valueOf(roleInfo.getBasicInfo().getCurExp());
				experience = experience * exchange_num;
				
				if(experience < Integer.valueOf(recourse[1])){
					i = -1;
					sb = "您的经验不足！";
				}
			}else if(recourse[0].equals("s")){
				Integer cid=Integer.parseInt(recourse[1]);
				Integer ncount=Integer.parseInt(recourse[2])*exchange_num;
				Integer ppk=Integer.parseInt(pPk);
				CreaditService service=new CreditProce();
				Integer res=service.checkHaveCondition(ppk, cid, ncount);
				if(res==-1||res.equals(-1)){
					i=-1;
					sb="您暂无此声望!";
				}else if(res==0||res.equals(0)){
					i=-1;
					sb="您的声望不足以兑换此物品!";
				}
			}else if(recourse[0].equals("r")){					// 帮派荣誉处理
				Integer ncount=Integer.parseInt(recourse[1])*exchange_num;
				Integer ppk=Integer.parseInt(pPk);
				CreaditService service=new CreditProce();	
				Integer res=service.checkHonorCondition(ppk,ncount);
				if(res==-1||res.equals(-1)){
					i=-1;
					sb="您暂无此荣誉!";
				}else if(res==0||res.equals(0)){
					i=-1;
					sb="您的荣誉值不足以兑换此物品!";
				}
			}
		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(sb);
		return sbf.toString();
	}
	
	/**
	 * 消耗材料
	 * @param roleEntity
	 * @param needMaterialsStr
	 * @return
	 */
	public String consumeMaterials(RoleEntity roleEntity,String needMaterialsStr)
	{
		return this.consumeMaterials(roleEntity, needMaterialsStr, 1);
	}
	
	/**
	 * 消耗材料
	 * @param roleEntity
	 * @param needMaterialsStr                 材料字符串
	 * @param needGroupNum					   需要材料的组数
	 */
	public String consumeMaterials(RoleEntity roleEntity,String needMaterialsStr,int needGroupNum)
	{
		if( roleEntity!=null || needMaterialsStr==null || needGroupNum<=0)
		{
			return "参数错误";
		}
		String[] need_material_str_list = needMaterialsStr.split(",");	
		
		for(String need_material_str:need_material_str_list) 
		{
			String[] temp = need_material_str.split("-");
			
			if(temp[0].equals("d")){								//从玩家身上减去道具
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(roleEntity.getPPk(),Integer.valueOf(temp[2]),Integer.valueOf(temp[3])*needGroupNum,GameLogManager.R_EXCHANGE);
			}else if(temp[0].equals("z")){							//从玩家身上减去装备
				GoodsService goodsService = new GoodsService();
				for(int a=0;a<Integer.parseInt(temp[2])*needGroupNum;a++){
					goodsService.removeEquipByEquipID(roleEntity.getPPk(),Integer.parseInt(temp[1]));
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(temp[2]), Integer.valueOf(temp[1]), Integer.valueOf(temp[2])*needGroupNum, StatisticsType.USED, StatisticsType.DUIHUAN,roleEntity.getPPk());
				}
			}else if(temp[0].equals("j")){							//从玩家身上减去金钱
				//监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleEntity.getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", -Integer.valueOf(temp[1])*needGroupNum+"", "兑换失去");
				roleEntity.getBasicInfo().addCopper(-Integer.valueOf(temp[1])*needGroupNum);
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, Integer.valueOf(temp[1])*needGroupNum, StatisticsType.USED, StatisticsType.DUIHUAN,roleEntity.getPPk());
				
			}else if(temp[0].equals("y")){							//从玩家身上减去经验
				//监控
				LogService logService = new LogService();
				logService.recordExpLog(roleEntity.getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCurExp(), Integer.parseInt(temp[1])*needGroupNum+"", "兑换扣掉");
				
				roleEntity.getBasicInfo().updateAddCurExp(-Integer.parseInt(temp[1])*needGroupNum);
				
			} else if(temp[0].equals("s")) {							//从玩家身上减去声望
				Integer cid=Integer.parseInt(temp[1]);
				Integer ncount=Integer.parseInt(temp[2])*needGroupNum;
				CreaditService service=new CreditProce();
				service.subtractCredit(roleEntity.getPPk(), cid, ncount);
			 } else if(temp[0].equals("r")) {						//给玩家身上减去帮派荣誉
				Integer ncount=Integer.parseInt(temp[1])*needGroupNum;
				roleEntity.getBasicInfo().addFContribute(-ncount);
			}	
		}
		return null;
	}
	
	/**
	 * 判断是否有足够的材料
	 */
	public String isEnoughMaterials( RoleEntity roleEntity,String needMaterialsStr)
	{
		return this.isEnoughMaterials(roleEntity, needMaterialsStr, 1);
	}
	
	/**
	 * 判断是否有足够的材料
	 * @param roleEntity
	 * @param needMaterialsStr                 材料字符串
	 * @param needGroupNum					   需要材料的组数
	 * @return
	 */
	public String isEnoughMaterials( RoleEntity roleEntity,String needMaterialsStr,int needGroupNum)
	{
		if( roleEntity!=null || needMaterialsStr==null || needGroupNum<=0)
		{
			return "参数错误";
		}
		String[] need_material_str_list = needMaterialsStr.split(",");	
		
		for(String need_material_str:need_material_str_list) 
		{
			String[] temp = need_material_str.split("-");
			
			if(temp[0].equals("d"))
			{
				int number = Integer.valueOf(temp[3]);				//原材料需要的个数
				number = needGroupNum*number;							//兑换所需要的个数
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int current_num = propGroupDao.getPropNumByByPropID(roleEntity.getPPk(), Integer.valueOf(temp[2]));//现有的数量
				if(current_num < number){
					return "您身上的材料不足！";
				}
			} 
			else if(temp[0].equals("z"))
			{
				int number = Integer.valueOf(temp[2]);				//原材料需要的个数
				number = needGroupNum*number;							//兑换所需要的个数
				PlayerEquipDao equipDao = new PlayerEquipDao();
				int equip_num = equipDao.getEquipNumByEquipId(roleEntity.getPPk(),Integer.valueOf(temp[2]),Integer.valueOf(temp[1]));
				if(equip_num < number){
					return "您身上的材料不足！";
				}
			} 
			else if(temp[0].equals("j"))
			{
				if( roleEntity.getBasicInfo().isEnoughMoney(Integer.valueOf(temp[1])*needGroupNum)==false)
				{
					return "您身上的材料不足！";
				}
			} 
			else if(temp[0].equals("y"))
			{
				long experience = Long.valueOf(roleEntity.getBasicInfo().getCurExp());
				experience = experience * needGroupNum;
				if(experience < Integer.valueOf(temp[1])){
					return "您身上的材料不足！";
				}
			}
			else if(temp[0].equals("s"))
			{
				Integer cid=Integer.parseInt(temp[1]);
				Integer need_num=Integer.parseInt(temp[2])*needGroupNum;
				CreaditService service=new CreditProce();
				Integer res=service.checkHaveCondition(roleEntity.getPPk(), cid, need_num);
				if(res==-1||res.equals(-1)){
					return "您暂无此声望!";
				}else if(res==0||res.equals(0)){
					return "您的声望不足以兑换此物品!";
				}
			}
			else if(temp[0].equals("r"))
			{					// 帮派荣誉处理
				Integer need_num=Integer.parseInt(temp[1])*needGroupNum;
				if( roleEntity.getBasicInfo().isEnoughFContribute(need_num)==false)
				{
					return "您的荣誉值不足!";
				}
			}
		}
		return null;
	}
	
	//得到某个字符在字符串中出现的次数
	public int getAppearNumber(String supString,String subString){
		int  cnt=0,start  =  0;
		while(start!=supString.length()){
			int a = supString.indexOf(subString,start);
			if(a!=-1) 
			{
			    cnt++;
			    start = a+1;
			}
			else
			break;
		}
		return cnt;
	}
	
	//检查玩家是否有足够的包裹以便容纳兑换过的物品
	public String getHasWareSpare(String pPk,OperateMenuVO vo,String address,int exchange_num){
		int i = 1;
		String result = "";
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk);
		
		int warespare = roleInfo.getBasicInfo().getWrapSpare();				//得到玩家包裹剩余空格数
		
		String[] articles = vo.getMenuOperate2().split(";");	//所有兑换品的字符串
		String article = articles[Integer.valueOf(address)];	//所要兑换兑换品的字符串
		logger.info("兑换品的article:"+article);
		
		String[] recourses = article.split(",");	
		for(int t= 0;t<recourses.length;t++) {
			
			String[] recourse = recourses[t].split("-");
			if(recourse[0].equals("z")){
				warespare = warespare -Integer.valueOf(recourse[3]) * exchange_num;
				//身上包裹空间小于所能放入的空间时
				if(warespare < 0){
					i = -1;
					result = "您身上的包裹空间不足！请先整理空间！";
				}
			} else if(recourse[0].equals("d")){
				PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
				int accumulate = propvo.getPropAccumulate();
				int num = Integer.valueOf(recourse[3])/accumulate +(Integer.valueOf(recourse[3])%accumulate == 0? 0:1);
				warespare = warespare -num;
				//PropDao propDAO = new PropDao();
					if(warespare < 0){
						i = -1;
						result = "您身上的包裹空间不足！请先整理空间！";
    			}
			}else if(recourse[0].equals("j")){
			 	long partCopper = roleInfo.getBasicInfo().getCopper();
			 	logger.info("身上的钱:"+partCopper+",兑换给的钱:"+Long.valueOf(recourse[1])*exchange_num);
			 	
			 	if(partCopper + Long.valueOf(recourse[1])*exchange_num > 1000000000){
			 		i = -1;
			 		result = "您已经不能携带更多的银了！";
			 	}
			}
		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(result);
		return sbf.toString();
	}
	
	/**
	 *  兑换物品
	 */
	public String exchangeGoods(String pPk,OperateMenuVO vo,String address,int exchange_num){
		StringBuffer resultWml = new StringBuffer();
		String[] articles = vo.getMenuOperate1().split(";");	//所有原材料的字符串
		String[] dharticles = vo.getMenuOperate2().split(";");	//所有兑换品的字符串
		
		String article = articles[Integer.valueOf(address)];	//所要兑换原材料的字符串
		String dharticle = dharticles[Integer.valueOf(address)];	//所要兑换兑换品的字符串
		
		String outMaterials = getMaterialsFromWrap(pPk,article,exchange_num);				//从玩家身上中去掉兑换的原材料
		String getExchangeGoods = sendDuiHuanToWrap(pPk,dharticle,exchange_num);			//给玩家发放兑换品
		
		resultWml.append("兑换已成功！");
		resultWml.append(outMaterials);
		resultWml.append(getExchangeGoods);
		
		TimeControlService timeControlService = new TimeControlService();
		timeControlService.updateControlInfo(Integer.valueOf(pPk), Integer.valueOf(vo.getId()), TimeControlService.MENU);
		
		return resultWml.toString();
	}

	//给玩家发放兑换品
	private String sendDuiHuanToWrap(String pPk, String dharticle,int exchange_num)
	{
		if(dharticle.equals("0")){
			logger.info("不给玩家发放物品！");
			return "";
		}
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk);
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("您获得了");
		String[]  articles = dharticle.split(",");
		for(int t=0;t<articles.length;t++){
			String[] unit = articles[t].split("-");
			if(unit[0].equals("d")){							//给玩家身上增加道具
				GoodsService goodsService = new GoodsService();
				goodsService.putGoodsToWrap(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3])*exchange_num);
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3])*exchange_num, StatisticsType.DEDAO, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
				
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("×").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("z")){						//给玩家身上增加装备
				GoodsService goodsService = new GoodsService();
				for(int i = 0;i<Integer.valueOf(unit[3]);i++){	//要给几个装备就给几次
					goodsService.putGoodsToWrap(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3])*exchange_num);
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3])*exchange_num, StatisticsType.DEDAO, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
					
				}
				
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("×").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("j")){						//给玩家身上增加金钱
				
				//监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", Integer.valueOf(unit[1])*exchange_num+"", "兑换得到");
				
				roleInfo.getBasicInfo().addCopper(Integer.valueOf(unit[1])*exchange_num);
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY,Integer.valueOf(unit[1])*exchange_num, StatisticsType.DEDAO, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
				
				resultWml.append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])*exchange_num));
			}else if(unit[0].equals("y")){						//给玩家身上增加经验
				//监控
				LogService logService = new LogService();
				logService.recordExpLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCurExp(), Integer.parseInt(unit[1])*exchange_num+"", "兑换得到");
				
				roleInfo.getBasicInfo().updateAddCurExp(Integer.parseInt(unit[1])*exchange_num);
				resultWml.append(Integer.valueOf(unit[1])*exchange_num+"经验");
			}else if(unit[0].equals("s")){						//给玩家身上增加声望
				Integer ppk=Integer.parseInt(pPk);
				Integer cid=Integer.parseInt(unit[1]);
				Integer ncount=Integer.parseInt(unit[2])*exchange_num;
				CreaditService service=new CreditProce();
				service.addPlayerCredit(ppk, cid, ncount);
				PlayerCreditVO pcv=service.getPcvDisplay(cid);
				resultWml.append(pcv.getCreditname()).append("×").append(ncount);
			}else if(unit[0].equals("r")){						//给玩家身上增加荣誉
				Integer ppk=Integer.parseInt(pPk);
				Integer ncount=Integer.parseInt(unit[1])*exchange_num;
				CreaditService service=new CreditProce();
				service.addPlayerHonor(ppk,ncount);
				resultWml.append("荣誉值").append("×").append(ncount);											
			}else if(unit[0].equals("yb")) {					//给玩家身上增加元宝
				logger.info("给玩家身上增加"+GameConfig.getYuanbaoName()+"=");
				logger.info("兑换时的ppk="+pPk);
				int uPk = roleInfo.getUPk();
				
				resultWml.append(GameConfig.getYuanbaoName()).append("×").append(Integer.parseInt(unit[1])*exchange_num);	
				
				
				EconomyService economyService = new EconomyService();
				//给玩家增加元宝
				economyService.addYuanbao(roleInfo.getBasicInfo().getPPk(),uPk, Integer.parseInt(unit[1])*exchange_num,StatisticsType.DUIHUAN);
			}
			
			
			if(t+1 < articles.length)
				resultWml.append(",");
		}	
		resultWml.append(".");
		return resultWml.toString();
	}

	//从玩家身上中去掉兑换的原材料
	private String getMaterialsFromWrap(String pPk, String article,int exchange_num)
	{
		if(article.equals("0")){
			logger.info("不从玩家身上扣除如何物品！");
			return "";
		}
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk);
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("您失去了");
		String[]  articles = article.split(",");
		for(int t=0;t<articles.length;t++){
			String[] unit = articles[t].split("-");
			if(unit[0].equals("d")){								//从玩家身上减去道具
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[3])*exchange_num,GameLogManager.R_EXCHANGE);
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("×").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("z")){							//从玩家身上减去装备
				
				GoodsService goodsService = new GoodsService();

				for(int a=0;a<Integer.parseInt(unit[2])*exchange_num;a++){
					goodsService.removeEquipByEquipID(Integer.parseInt(pPk),Integer.parseInt(unit[1]));
					//执行统计
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[2])*exchange_num, StatisticsType.USED, StatisticsType.DUIHUAN,Integer.parseInt(pPk));

				}
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("×").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("j")){							//从玩家身上减去金钱
				
				//监控
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -Integer.valueOf(unit[1])*exchange_num+"", "兑换失去");
				
				roleInfo.getBasicInfo().addCopper(-Integer.valueOf(unit[1])*exchange_num);
				
				//执行统计
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, Integer.valueOf(unit[1])*exchange_num, StatisticsType.USED, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
				
				resultWml.append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])*exchange_num));
			}else if(unit[0].equals("y")){							//从玩家身上减去经验
//				PartInfoDao partInfoDao = new PartInfoDao();
//				partInfoDao.updateExperience(Integer.valueOf(pPk),-Integer.valueOf(unit[1])*exchange_num);
				
				//监控
				LogService logService = new LogService();
				logService.recordExpLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCurExp(), Integer.parseInt(unit[1])*exchange_num+"", "兑换扣掉");
				
				
				
				roleInfo.getBasicInfo().updateAddCurExp(-Integer.parseInt(unit[1])*exchange_num);
				
				resultWml.append(Integer.valueOf(unit[1])*exchange_num+"经验");
			} else if(unit[0].equals("s")) {							//从玩家身上减去声望
				Integer cid=Integer.parseInt(unit[1]);
				Integer ncount=Integer.parseInt(unit[2])*exchange_num;
				Integer ppk=Integer.parseInt(pPk);
				CreaditService service=new CreditProce();
				service.subtractCredit(ppk, cid, ncount);
				PlayerCreditVO pcv=service.getPcvDisplay(cid);
				resultWml.append(pcv.getCreditname()).append("×").append(ncount);
			 } else if(unit[0].equals("r")) {						//给玩家身上减去荣誉
				Integer ppk=Integer.parseInt(pPk);
				Integer ncount=Integer.parseInt(unit[1])*exchange_num;
				CreaditService service=new CreditProce();
				service.subtractHonor(ppk,ncount);
				resultWml.append("荣誉").append("×").append(ncount);
			}	
			if(t+1 < articles.length)
				resultWml.append(",");
		}
		resultWml.append(",");
		return resultWml.toString();
	}
}
