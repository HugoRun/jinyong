package com.ls.web.service.storage;
import java.util.List;

import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.dao.sellinfo.SellInfoDAO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.storage.WareHouseVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.Wrap;
import com.ls.pub.util.MoneyUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.player.RoleService;
import com.pm.constant.SpecialNumber;

public class StorageService {
	
	/**
	 * 根据仓库id得到仓库信息 
	 * @param p_pk
	 * @return
	 */
	public  WareHouseVO getWareHouseByPPk(int p_pk) {
		WareHouseDao wareHouse = new WareHouseDao();
		return wareHouse.getStorageByPPk(p_pk);
	}
	
	
	//存储金钱，从包裹到仓库
	public String removeMoneyToWare(int pPk,long input_num){
		String resultWml = "";
		int inputNum = (int)input_num;
		WareHouseDao wareHouse = new WareHouseDao();
		
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk+"");
		roleEntity.getBasicInfo().addCopper(-inputNum);
		
		//往仓库中加入input_num个铜板
		wareHouse.addWareHouseMoney(pPk,inputNum);
		
		resultWml = "您本次在钱庄存放了金钱:"+MoneyUtil.changeCopperToStr(inputNum);
		
		return resultWml;
	}	
	
	
	/**
	 * 向仓库储存道具
	 * @param p_pk
	 * @param prop_id
	 * @param prop_num
	 * @return
	 */
	public String storeProps(int pg_pk,int prop_num,RoleEntity roleInfo)
	{
		StringBuffer resultWml = new StringBuffer();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(pg_pk);
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		
		if(propGroup.getPPk() != roleInfo.getBasicInfo().getPPk()){
			return "该物品已不属于您!";
		}
		
		if(propGroup.getPropType() == PropType.EQUIPPROP || propGroup.getPropType() == PropType.BOX_CURE) {
			return "特殊道具,请勿存进仓库!";
		}
		
		//检查是否此物品已经被卖出
		String result = sellInfoDAO.getSellExistByPPkAndGoodsId(propGroup.getPPk()+"", propGroup.getPropId()+"", GoodsType.PROP);
		
		if(result == null || result.equals("")) {
			//从包裹卸掉道具
			removePropsFromWrap(propGroup, prop_num);
			setStorageProp(propGroup,prop_num,roleInfo);
			
			resultWml.append("您已经储存"+prop_num+"个"+StringUtil.isoToGBK(propGroup.getPropName())+", 如果要储存请继续");
			return resultWml.toString();
		} else {
			
			return result;
		}
	}
	
	/**
	 * 仓库取出道具操作
	 * @param p_pk
	 * @param prop_id
	 * @param prop_num
	 * @return
	 */
	public String getStorageProps(int propId,int remove_num,RoleEntity roleEntity,WareHouseVO wareHouseVO)
	{
		if ( wareHouseVO == null) {
			return "仓库中没有此物品!";
		}
		StringBuffer resultWml = new StringBuffer();
		
		GoodsService goodsService = new GoodsService();
		
		//装进包裹
		goodsService.putPropToWrap(roleEntity.getBasicInfo().getPPk(), propId, remove_num,GameLogManager.G_STORAGE);
		//删除仓库	
		removePropsFromStorage(roleEntity.getBasicInfo().getPPk(),remove_num,wareHouseVO);
	
		//删除仓库道具数量为零的情况
		WareHouseDao wareHouse = new WareHouseDao();
		wareHouse.deleteStorageZero(roleEntity.getBasicInfo().getPPk());
		
		
		resultWml.append("您已经取出了"+remove_num+"个"+StringUtil.isoToGBK(wareHouseVO.getUwArticle())+" !");
		
		return resultWml.toString();
	}
	
	/** 从包裹中卸掉一组道具 */
	public boolean removePropsFromWrap(PlayerPropGroupVO propGroup,int remove_num)
	{
		boolean flag = false;
		if( propGroup==null )
		{
			return false; 
		}
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		if( propGroup.getPropNum() == remove_num )  //移除的数量等于道具组数量
		{
			
			propGroupDao.deletePropGroup(propGroup.getPgPk());
			
			//此种情况个人包裹增加一个空格.
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(propGroup.getPPk(),1);
			flag = true;
		}
		else if( propGroup.getPropNum()>remove_num )// //移除的数量小于道具组数量
		{
			propGroupDao.updatePropGroupNum(propGroup.getPgPk(),propGroup.getPropNum()-remove_num);
			flag = true;
		}
		return flag;
	}
	
	
	/** 
	 *  从仓库里拿掉remove_num个道具
	 * 	
	 */
	public void removePropsFromStorage(int pPk,int remove_num,WareHouseVO warehouseVO){
		WareHouseDao warehouseDao = new WareHouseDao();
		if(remove_num == warehouseVO.getUwPropNumber()){
			warehouseDao.reduceWareHouseProp(pPk,remove_num,warehouseVO);
			//此种情况增加仓库一个空格
			warehouseDao.reduceWareHouseSpare(pPk,-1);
		}else {
			warehouseDao.reduceWareHouseProp(pPk,remove_num,warehouseVO);
		}
	}
	
	/** 
	 * 把道具装进仓库
	 * 
	 */
	public boolean setStorageProp(PlayerPropGroupVO propGroup,int remove_num,RoleEntity roleEntity) {
		boolean flag = true;
		WareHouseDao wareHouseDao = new WareHouseDao();
		if( propGroup==null || remove_num <= 0)
		{
			return false;
		}
		
		PropVO prop = PropCache.getPropById(propGroup.getPropId());
		
		if(prop == null){
			return false;
		}
		
		int accumulate_num = prop.getPropAccumulate();//可以重叠的数量
		int current_num = wareHouseDao.getPropNumByByPropID(roleEntity.getPPk(), propGroup.getPropId());//现有的数量
		int total_num = current_num + remove_num;//增加goods_num后的总数
		int current_groups = 0;//现有的组数
		if( current_num!=0 )
		{
			//current_groups =(current_num-1)/accumulate_num+1;
			//因为玩家的包裹该物品格子不一定都是理想状态, 所以可能有零散的,故需要从数据库中取现有组数 .
			//current_groups =(current_num-1)/accumulate_num+1;
			current_groups = wareHouseDao.getPropGroupNumByPropID(roleEntity.getPPk(), propGroup.getPropId());
		}

		int new_groups = (total_num-1)/accumulate_num+1;//增加goods_num个道具后的组数
		
		int need_groups = new_groups - current_groups;//需要增加的道具组数
		
		int goodsgourp_goodsnum  =  total_num%accumulate_num;//不完整道具组的道具数量
		
		int wrap_spare = wareHouseDao.getEmptyNum(roleEntity.getPPk());//剩余的包裹空间数
		
		if( need_groups>0 && wrap_spare<need_groups )
		{
			return false;
		}
		
		if( need_groups>0 )//添加新的道具组
		{
			//找到没有重叠慢的道具组
			WareHouseVO warevo = new WareHouseVO();
			warevo.setPPk(roleEntity.getBasicInfo().getPPk());
			warevo.setUPk(roleEntity.getBasicInfo().getUPk());
			warevo.setUwPropId(prop.getPropID());
			
			warevo.setUwPropNumber(accumulate_num);
			warevo.setUwType(prop.getPropPosition());
			warevo.setUwPropType(prop.getPropClass());
			
			warevo.setUwArticle(StringUtil.gbToISO(prop.getPropName()));
			warevo.setUw_bonding(propGroup.getPropBonding());
			warevo.setUw_protect(propGroup.getPropProtect());
			warevo.setUwIsReconfirm(propGroup.getPropIsReconfirm());
			warevo.setPropUseControl(propGroup.getPropUseControl());
			
			if(propGroup.getPropType() == PropType.BOX_CURE ) {
				PlayerPropGroupDao ppgdao = new PlayerPropGroupDao();
				int uspId = ppgdao.getSpecialId(roleEntity.getPPk(),propGroup.getPgPk(),SpecialNumber.KUNZHUANG);
				warevo.setPropOperate1(uspId+"");
			}
			
			
			//添加新的道具组，数量都是accumulate_num
			for( int i=0;i<need_groups;i++)
			{
				wareHouseDao.addPropGroup(warevo);
			}
		}
		
		if( need_groups<0)//删除多余的道具组
		{
			int delete_group_num = -need_groups;
			wareHouseDao.deleteStroagePropGroup(roleEntity.getPPk(),propGroup.getPropId(),delete_group_num);
		}
		
		// 将玩家此道具的每格子现在仓库道具数量都置为最大
		wareHouseDao.updatePlayerWarehousePropNum(roleEntity.getPPk(),propGroup.getPropId(),accumulate_num);
		
		WareHouseVO warehousevo = null;
		warehousevo = wareHouseDao.getWareHouseByPropID(roleEntity.getPPk(), propGroup.getPropId());
		if( goodsgourp_goodsnum!=0 )
		{
			wareHouseDao.updatePropGroupNum(warehousevo.getUwId(), goodsgourp_goodsnum);
		}
		
		// 更新仓库空余格数 
		wareHouseDao.reduceWareHouseSpare(roleEntity.getPPk(),need_groups);
		
		return flag;
	}
	
	/** 
	 * 根据id来取得该角色的仓库空余格数 
	 * @param warehouseID	仓库表的id
	 * @param propGroup		PlayerGroupVO类
	 * @return int 	角色仓库空格数
	 */
	public int getWareSpareById(int pPk){
		WareHouseDao wareHouse = new WareHouseDao(); 
		return wareHouse.getWareSpareById(pPk);
	}
	
	
	/**
	 * 储存装备到仓库
	 * @param p_pk
	 * @param pw_pk   装备id
	 */
	public String storeEquip(int p_pk,int pw_pk )
	{
		StringBuffer resultWml = new StringBuffer();
		
		EquipService equipService = new EquipService();
		WareHouseDao wareHouse = new WareHouseDao();
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		SellInfoDAO sellInfoDAO = new SellInfoDAO();
		
		PlayerEquipVO equip = playerEquipDao.getByID(pw_pk);
		
		String hint =  equip.isOwnByPPk(p_pk);
		if( hint!=null )
		{
			return hint;
		}
		
		String result = sellInfoDAO.getSellExistByPPkAndGoodsId(p_pk+"", equip.getEquipId()+"", Wrap.EQUIP);
		
		if(result != null && !result.equals(""))  {
			return result;
		}
		
		//修改装备所在的位置到仓库
		playerEquipDao.updatePosition(equip.getPwPk(), Equip.ON_STORAGE);
		
		//将数据库中仓库空余格数减一
		wareHouse.reduceWareHouseSpare(p_pk,1);
		
		//增加玩家包裹剩余空间数量一
		equipService.addWrapSpare(p_pk, 1);			
		
		resultWml.append("您储存了"+StringUtil.isoToGBK(equip.getFullName())+", 如要储存请继续！");
		return resultWml.toString();
	}
	
	
	
	/**
	 * 从仓库里取出装备
	 * @param p_pk
	 * @param WPk   装备id
	 */
	public String takeoutEquip(int p_pk,int WPk)
	{
		StringBuffer resultWml = new StringBuffer();
		
		EquipService equipService = new EquipService();
		WareHouseDao wareHouse = new WareHouseDao();
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		
		PlayerEquipVO equip = playerEquipDao.getByID(WPk);
		
		String hint =  equip.isOwnByPPk(p_pk);
		if( hint!=null )
		{
			return hint;
		}
		
		//修改装备所在的位置到包裹
		playerEquipDao.updatePosition(equip.getPwPk(), Equip.ON_WRAP);
		
		//将数据库中仓库空余格数加一
		wareHouse.reduceWareHouseSpare(p_pk,-1);
		//减少玩家包裹剩余空间数量一
		equipService.addWrapSpare(p_pk, -1);		
		
		resultWml.append("您取出了"+StringUtil.isoToGBK(equip.getFullName())+", 如要取出请继续！");
		return resultWml.toString();
	}
	
	
	/**
	 * 得到角色仓库中某类型的道具list
	 * @param p_pk 角色id
	 * @param type 道具在仓库中的分类
	 * @return  
	 */
	public QueryPage getPagePropList(int pPk,int type,int page_no ){ 
		WareHouseDao wareHouseDao = new WareHouseDao();
		return wareHouseDao.getPagePropsByPpk(pPk,type,page_no);
	}
	
	/**
	 * 分页:得到玩家仓库里的装备
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageEquipOnStorage(int p_pk,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageByPosition(p_pk, page_no,Equip.ON_STORAGE);
	}
	
	
	
	/** 取出仓库里的金钱 */
	public String removeMoneyToWrap(int pPk,long remove_money){
		String resultWml = "";

		WareHouseDao wareHouse = new WareHouseDao();
		//从包裹中加入input_num个铜板
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk+"");
		roleEntity.getBasicInfo().addCopper((int)remove_money);
		
		//往仓库中减少input_num个铜板 
		wareHouse.addWareHouseMoney(pPk,-remove_money);
		
		resultWml = "您本次在钱庄取出了金钱:"+MoneyUtil.changeCopperToStr(remove_money);
		
		return resultWml;
	
	}
	
	/** 
	 *	从仓库取出宠物，就是将人物宠物表中此行的-pPk设置为pPk,
	 *	如成功返回1，否则返回-1.如果所传pPk为负数，可表示从将
	 *	宠物从仓库放到人包裹中
	 */
	public String putPetFromWare(int pPk,int petPk){
		String diffent = "getOut";
		int p_Pk = -pPk;
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		WareHouseDao wareHouse = new WareHouseDao();
		List<PetInfoVO> pet_list = petInfoDAO.getPetInfoList(pPk+"");
		
		if(pet_list == null){
			wareHouse.updatePetNumber(pPk,-1);
			return wareHouse.putPetFromPerson(p_Pk,petPk,diffent);
		}else if(pet_list.size() < 5){
			wareHouse.updatePetNumber(pPk,-1);
			return wareHouse.putPetFromPerson(-p_Pk,petPk,diffent);
		}else {
				return "您不能再携带更多的宠物了!";
		}	
	}
	
	
	
	/**
	 * 获取仓库中已储存宠物的数量
	 * @param pPk
	 * @param uPk
	 * @param warehouseId
	 */
	public int getPetNumber(int pPk){
		WareHouseDao wareHouse = new WareHouseDao();
		int petNumber = wareHouse.getPetNumber(-pPk);
		return petNumber;
	}
	
	
	/** 
	 *	从身上拿下宠物，就是将人物宠物表中此行的pPk设置为-pPk,
	 *	如成功返回1，否则返回-1.如果所传pPk为负数，可表示从将
	 *	宠物从人包裹放到仓库中
	 */
	public String putPetFromPerson(int pPk,int petPk){
		String diffent = "setIn";
		int p_Pk = -pPk;
		WareHouseDao wareHouse = new WareHouseDao();
		WareHouseVO warevo = new WareHouseVO();
		warevo = wareHouse.getWareHouseIdBypPk(pPk+"",7);
		int petNumber = Integer.valueOf(warevo.getUwPet());
		PetInfoDAO petInfoDao = new PetInfoDAO();
		PetInfoVO petInfovo = petInfoDao.getPetInfoView(String.valueOf(petPk));
		if(petInfovo.getPPk() != pPk){
			return "该宠物已不属于您!";
		}
		if(petInfovo.getPetIsBring() == 1){
			return "请将宠物解除战斗状态!";
		}else{
			if(petNumber < 5){
				wareHouse.updatePetNumber(pPk,1);
				return wareHouse.putPetFromPerson(p_Pk,petPk,diffent);
			}else {
				return "仓库已满，您最多只能存放五只宠物!";
			}
		}
	}


}
