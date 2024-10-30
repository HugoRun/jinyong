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
	 * ���ݲֿ�id�õ��ֿ���Ϣ 
	 * @param p_pk
	 * @return
	 */
	public  WareHouseVO getWareHouseByPPk(int p_pk) {
		WareHouseDao wareHouse = new WareHouseDao();
		return wareHouse.getStorageByPPk(p_pk);
	}
	
	
	//�洢��Ǯ���Ӱ������ֿ�
	public String removeMoneyToWare(int pPk,long input_num){
		String resultWml = "";
		int inputNum = (int)input_num;
		WareHouseDao wareHouse = new WareHouseDao();
		
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk+"");
		roleEntity.getBasicInfo().addCopper(-inputNum);
		
		//���ֿ��м���input_num��ͭ��
		wareHouse.addWareHouseMoney(pPk,inputNum);
		
		resultWml = "��������Ǯׯ����˽�Ǯ:"+MoneyUtil.changeCopperToStr(inputNum);
		
		return resultWml;
	}	
	
	
	/**
	 * ��ֿⴢ�����
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
			return "����Ʒ�Ѳ�������!";
		}
		
		if(propGroup.getPropType() == PropType.EQUIPPROP || propGroup.getPropType() == PropType.BOX_CURE) {
			return "�������,�������ֿ�!";
		}
		
		//����Ƿ����Ʒ�Ѿ�������
		String result = sellInfoDAO.getSellExistByPPkAndGoodsId(propGroup.getPPk()+"", propGroup.getPropId()+"", GoodsType.PROP);
		
		if(result == null || result.equals("")) {
			//�Ӱ���ж������
			removePropsFromWrap(propGroup, prop_num);
			setStorageProp(propGroup,prop_num,roleInfo);
			
			resultWml.append("���Ѿ�����"+prop_num+"��"+StringUtil.isoToGBK(propGroup.getPropName())+", ���Ҫ���������");
			return resultWml.toString();
		} else {
			
			return result;
		}
	}
	
	/**
	 * �ֿ�ȡ�����߲���
	 * @param p_pk
	 * @param prop_id
	 * @param prop_num
	 * @return
	 */
	public String getStorageProps(int propId,int remove_num,RoleEntity roleEntity,WareHouseVO wareHouseVO)
	{
		if ( wareHouseVO == null) {
			return "�ֿ���û�д���Ʒ!";
		}
		StringBuffer resultWml = new StringBuffer();
		
		GoodsService goodsService = new GoodsService();
		
		//װ������
		goodsService.putPropToWrap(roleEntity.getBasicInfo().getPPk(), propId, remove_num,GameLogManager.G_STORAGE);
		//ɾ���ֿ�	
		removePropsFromStorage(roleEntity.getBasicInfo().getPPk(),remove_num,wareHouseVO);
	
		//ɾ���ֿ��������Ϊ������
		WareHouseDao wareHouse = new WareHouseDao();
		wareHouse.deleteStorageZero(roleEntity.getBasicInfo().getPPk());
		
		
		resultWml.append("���Ѿ�ȡ����"+remove_num+"��"+StringUtil.isoToGBK(wareHouseVO.getUwArticle())+" !");
		
		return resultWml.toString();
	}
	
	/** �Ӱ�����ж��һ����� */
	public boolean removePropsFromWrap(PlayerPropGroupVO propGroup,int remove_num)
	{
		boolean flag = false;
		if( propGroup==null )
		{
			return false; 
		}
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		if( propGroup.getPropNum() == remove_num )  //�Ƴ����������ڵ���������
		{
			
			propGroupDao.deletePropGroup(propGroup.getPgPk());
			
			//����������˰�������һ���ո�.
			EquipService equipService = new EquipService();
			equipService.addWrapSpare(propGroup.getPPk(),1);
			flag = true;
		}
		else if( propGroup.getPropNum()>remove_num )// //�Ƴ�������С�ڵ���������
		{
			propGroupDao.updatePropGroupNum(propGroup.getPgPk(),propGroup.getPropNum()-remove_num);
			flag = true;
		}
		return flag;
	}
	
	
	/** 
	 *  �Ӳֿ����õ�remove_num������
	 * 	
	 */
	public void removePropsFromStorage(int pPk,int remove_num,WareHouseVO warehouseVO){
		WareHouseDao warehouseDao = new WareHouseDao();
		if(remove_num == warehouseVO.getUwPropNumber()){
			warehouseDao.reduceWareHouseProp(pPk,remove_num,warehouseVO);
			//����������Ӳֿ�һ���ո�
			warehouseDao.reduceWareHouseSpare(pPk,-1);
		}else {
			warehouseDao.reduceWareHouseProp(pPk,remove_num,warehouseVO);
		}
	}
	
	/** 
	 * �ѵ���װ���ֿ�
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
		
		int accumulate_num = prop.getPropAccumulate();//�����ص�������
		int current_num = wareHouseDao.getPropNumByByPropID(roleEntity.getPPk(), propGroup.getPropId());//���е�����
		int total_num = current_num + remove_num;//����goods_num�������
		int current_groups = 0;//���е�����
		if( current_num!=0 )
		{
			//current_groups =(current_num-1)/accumulate_num+1;
			//��Ϊ��ҵİ�������Ʒ���Ӳ�һ����������״̬, ���Կ�������ɢ��,����Ҫ�����ݿ���ȡ�������� .
			//current_groups =(current_num-1)/accumulate_num+1;
			current_groups = wareHouseDao.getPropGroupNumByPropID(roleEntity.getPPk(), propGroup.getPropId());
		}

		int new_groups = (total_num-1)/accumulate_num+1;//����goods_num�����ߺ������
		
		int need_groups = new_groups - current_groups;//��Ҫ���ӵĵ�������
		
		int goodsgourp_goodsnum  =  total_num%accumulate_num;//������������ĵ�������
		
		int wrap_spare = wareHouseDao.getEmptyNum(roleEntity.getPPk());//ʣ��İ����ռ���
		
		if( need_groups>0 && wrap_spare<need_groups )
		{
			return false;
		}
		
		if( need_groups>0 )//����µĵ�����
		{
			//�ҵ�û���ص����ĵ�����
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
			
			
			//����µĵ����飬��������accumulate_num
			for( int i=0;i<need_groups;i++)
			{
				wareHouseDao.addPropGroup(warevo);
			}
		}
		
		if( need_groups<0)//ɾ������ĵ�����
		{
			int delete_group_num = -need_groups;
			wareHouseDao.deleteStroagePropGroup(roleEntity.getPPk(),propGroup.getPropId(),delete_group_num);
		}
		
		// ����Ҵ˵��ߵ�ÿ�������ڲֿ������������Ϊ���
		wareHouseDao.updatePlayerWarehousePropNum(roleEntity.getPPk(),propGroup.getPropId(),accumulate_num);
		
		WareHouseVO warehousevo = null;
		warehousevo = wareHouseDao.getWareHouseByPropID(roleEntity.getPPk(), propGroup.getPropId());
		if( goodsgourp_goodsnum!=0 )
		{
			wareHouseDao.updatePropGroupNum(warehousevo.getUwId(), goodsgourp_goodsnum);
		}
		
		// ���²ֿ������� 
		wareHouseDao.reduceWareHouseSpare(roleEntity.getPPk(),need_groups);
		
		return flag;
	}
	
	/** 
	 * ����id��ȡ�øý�ɫ�Ĳֿ������� 
	 * @param warehouseID	�ֿ���id
	 * @param propGroup		PlayerGroupVO��
	 * @return int 	��ɫ�ֿ�ո���
	 */
	public int getWareSpareById(int pPk){
		WareHouseDao wareHouse = new WareHouseDao(); 
		return wareHouse.getWareSpareById(pPk);
	}
	
	
	/**
	 * ����װ�����ֿ�
	 * @param p_pk
	 * @param pw_pk   װ��id
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
		
		//�޸�װ�����ڵ�λ�õ��ֿ�
		playerEquipDao.updatePosition(equip.getPwPk(), Equip.ON_STORAGE);
		
		//�����ݿ��вֿ���������һ
		wareHouse.reduceWareHouseSpare(p_pk,1);
		
		//������Ұ���ʣ��ռ�����һ
		equipService.addWrapSpare(p_pk, 1);			
		
		resultWml.append("��������"+StringUtil.isoToGBK(equip.getFullName())+", ��Ҫ�����������");
		return resultWml.toString();
	}
	
	
	
	/**
	 * �Ӳֿ���ȡ��װ��
	 * @param p_pk
	 * @param WPk   װ��id
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
		
		//�޸�װ�����ڵ�λ�õ�����
		playerEquipDao.updatePosition(equip.getPwPk(), Equip.ON_WRAP);
		
		//�����ݿ��вֿ���������һ
		wareHouse.reduceWareHouseSpare(p_pk,-1);
		//������Ұ���ʣ��ռ�����һ
		equipService.addWrapSpare(p_pk, -1);		
		
		resultWml.append("��ȡ����"+StringUtil.isoToGBK(equip.getFullName())+", ��Ҫȡ���������");
		return resultWml.toString();
	}
	
	
	/**
	 * �õ���ɫ�ֿ���ĳ���͵ĵ���list
	 * @param p_pk ��ɫid
	 * @param type �����ڲֿ��еķ���
	 * @return  
	 */
	public QueryPage getPagePropList(int pPk,int type,int page_no ){ 
		WareHouseDao wareHouseDao = new WareHouseDao();
		return wareHouseDao.getPagePropsByPpk(pPk,type,page_no);
	}
	
	/**
	 * ��ҳ:�õ���Ҳֿ����װ��
	 * @param p_pk
	 * @return
	 */
	public QueryPage getPageEquipOnStorage(int p_pk,int page_no)
	{
		PlayerEquipDao equipDao = new PlayerEquipDao();
		return equipDao.getPageByPosition(p_pk, page_no,Equip.ON_STORAGE);
	}
	
	
	
	/** ȡ���ֿ���Ľ�Ǯ */
	public String removeMoneyToWrap(int pPk,long remove_money){
		String resultWml = "";

		WareHouseDao wareHouse = new WareHouseDao();
		//�Ӱ����м���input_num��ͭ��
		RoleEntity roleEntity = RoleService.getRoleInfoById(pPk+"");
		roleEntity.getBasicInfo().addCopper((int)remove_money);
		
		//���ֿ��м���input_num��ͭ�� 
		wareHouse.addWareHouseMoney(pPk,-remove_money);
		
		resultWml = "��������Ǯׯȡ���˽�Ǯ:"+MoneyUtil.changeCopperToStr(remove_money);
		
		return resultWml;
	
	}
	
	/** 
	 *	�Ӳֿ�ȡ��������ǽ����������д��е�-pPk����ΪpPk,
	 *	��ɹ�����1�����򷵻�-1.�������pPkΪ�������ɱ�ʾ�ӽ�
	 *	����Ӳֿ�ŵ��˰�����
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
				return "��������Я������ĳ�����!";
		}	
	}
	
	
	
	/**
	 * ��ȡ�ֿ����Ѵ�����������
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
	 *	���������³�����ǽ����������д��е�pPk����Ϊ-pPk,
	 *	��ɹ�����1�����򷵻�-1.�������pPkΪ�������ɱ�ʾ�ӽ�
	 *	������˰����ŵ��ֿ���
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
			return "�ó����Ѳ�������!";
		}
		if(petInfovo.getPetIsBring() == 1){
			return "�뽫������ս��״̬!";
		}else{
			if(petNumber < 5){
				wareHouse.updatePetNumber(pPk,1);
				return wareHouse.putPetFromPerson(p_Pk,petPk,diffent);
			}else {
				return "�ֿ������������ֻ�ܴ����ֻ����!";
			}
		}
	}


}
