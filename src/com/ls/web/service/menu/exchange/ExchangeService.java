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
	 * ���ԭ���ϵ���ʾ�ַ���
	 * @param vo	
	 * @return
	 */
	public List<String> getChangeDisplayList(OperateMenuVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getMenuOperate1();				//��Ҫ�һ���ԭ����	
		//String operate2 = vo.getMenuOperate2();			//�һ���Ŀ��Ʒ
		String[] reChange = operate1.split(";");			//��Ҫ�һ���ԭ���ϵ�����
		//String[] reChange2 = operate2.split(";");			//�һ���Ŀ��Ʒ������
		if(reChange == null) {
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		
		for(int i=0;i<length;i++){					
			String[] materials = reChange[i].split(",");	//ÿ���һ���ԭ���Ͽ��ܲ�ֹһ��
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for(int a = 0;a<materials.length;a++){
				String[] unit = materials[a].split("-");
				if(unit[0].equals("d")){					//���ԭ�����ǵ��ߵĴ������
					PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
					sb.append("-");
					sb.append(propvo.getPropName());
					sb.append("��").append(unit[3]);
				}else if(unit[0].equals("z")){				//���ԭ������װ���Ĵ������
					sb.append("-");
					int equip_id = Integer.valueOf(unit[1]);
					GameEquip equip = EquipCache.getById(equip_id);
					sb.append(equip.getName()).append("��").append(unit[2]);
				}else if(unit[0].equals("j")){		//���ԭ�����ǽ�Ǯ�Ĵ������
					sb.append("-").append(MoneyUtil.changeCopperToStr(unit[1]));
					
				}else if(unit[0].equals("y")){		//���ԭ�����Ǿ���Ĵ������
					sb.append("-").append(unit[1]);
					sb.append("����");
				}else if(unit[0].equals("s")){		//���ԭ�����������Ĵ������
					sb.append("-");
					Integer cid=Integer.parseInt(unit[1]);
					CreaditService service=new CreditProce();
					PlayerCreditVO pcv=service.getPcvDisplay(cid);
					sb.append(pcv.getCreditname());
					sb.append("��").append(unit[2]);
				}else if(unit[0].equals("r")){		//���ԭ�����������Ĵ������
					sb.append("-").append(unit[1]);
					sb.append("����");
				}
			}
				logger.info("i��value: "+i+" ԭ����װ���ֶ�"+sb.toString());
				list.add(sb.toString());
			}
			return list;
		}
	
	/**
	 * ���ԭ���ϵ���ʾ�ַ���
	 * @param vo	
	 * @return
	
	public List<String> getChangeDisplayList1(OperateMenuVO vo)
	{
		List<String> list = null;
		String operate1 = vo.getMenuOperate1();				//��Ҫ�һ���ԭ����	
		//String operate2 = vo.getMenuOperate2();			//�һ���Ŀ��Ʒ
		String[] reChange = operate1.split(";");			//��Ҫ�һ���ԭ���ϵ�����
		//String[] reChange2 = operate2.split(";");			//�һ���Ŀ��Ʒ������
		if(reChange == null){
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		
		for(int i=0;i<length;i++){					
			String[] materials = reChange[i].split(",");	//ÿ���һ���ԭ���Ͽ��ܲ�ֹһ��
			StringBuffer sb = new StringBuffer();
			sb.append(i);
			for(int a = 0;a<materials.length;a++){
				String[] unit = materials[a].split("-");
				if(unit[0].equals("d")){					//���ԭ�����ǵ��ߵĴ������
					PropDao propDao = new PropDao();
					PropVO propvo = propDao.getById(Integer.valueOf(unit[2]));
					sb.append("-");
					sb.append(unit[3]).append("-");
					sb.append(propvo.getPropName());
					
				}else if(unit[0].equals("z")){				//���ԭ������װ���Ĵ������
					sb.append("-");
					sb.append(unit[3]).append("-");
					if(unit[1].equals("1")){				//1��װ����2��������3������
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
				}else if(unit[0].equals("j")){		//���ԭ�����ǽ�Ǯ�Ĵ������
					sb.append("-").append(unit[1]);
					sb.append("-").append("��");
				}else if(unit[0].equals("y")){		//���ԭ�����Ǿ���Ĵ������
					sb.append("-").append(unit[1]);
					sb.append("-").append("����");
				}else if(unit[0].equals("s")){		//���ԭ�����������Ĵ������
					sb.append("-").append(unit[1]);
					sb.append("-").append("����");
				}
			}
				logger.info("i��value: "+i+" ԭ����װ���ֶ�"+sb.toString());
				list.add(sb.toString());
			}
			return list;
		}
	 */
	
	/**
	 * ��öһ�Ʒ����ʾ�ַ���
	 * @param vo	
	 * @return
	 */
	public List<String> getAimChangeList(OperateMenuVO vo) {
		List<String> list = null;
		String operate = vo.getMenuOperate2();				//�һ���Ŀ��Ʒ
		String[] reChange = operate.split(";");			//�һ���Ŀ��Ʒ������
		if(reChange == null){
			return list;
		}
		list = new ArrayList<String>();
		int length = reChange.length;
		for(int i=0;i<length;i++){	
			StringBuffer sb = new StringBuffer();
			String[] materials = reChange[i].split(",");	//ÿ���һ��Ķһ�Ʒ���ܲ�ֹһ��
			
			for(int a = 0;a<materials.length;a++) {			//��ÿ���һ�Ʒ���в�ͬ�Ĵ���
			String[] unit = materials[a].split("-");		
			if(unit[0].equals("z")){						//����һ�Ʒ��װ���Ĵ������
				int equip_id = Integer.valueOf(unit[1]);
				GameEquip equip = EquipCache.getById(equip_id);
				sb.append(equip.getName());
				//sb.append("��").append(unit[2]);
			}else if(unit[0].equals("d")){				//����һ�Ʒ�ǵ��ߵĴ������
				PropVO propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
				propvo = PropCache.getPropById(Integer.valueOf(unit[2]));
				sb.append(propvo.getPropName());
				//sb.append("��").append(unit[3]);
			}else if(unit[0].equals("j")){				//����һ�Ʒ�ǽ�Ǯ�Ĵ������
				sb.append(MoneyUtil.changeCopperToStr(unit[1]));
				
			}else if(unit[0].equals("y")){				//����һ�Ʒ�Ǿ���Ĵ������
				sb.append(unit[1]).append("����");
				
			}else if(unit[0].equals("s")){				//����һ�Ʒ�������Ĵ������		
				Integer cid=Integer.parseInt(unit[1]);
				CreaditService service=new CreditProce();
				PlayerCreditVO pcv=service.getPcvDisplay(cid);
				if( pcv==null )
				{
					DataErrorLog.debugData("�һ��˵����ݴ����޸�������cid="+cid);
				}
				sb.append(pcv.getCreditname());
				sb.append("��").append(unit[2]);
			}else if(unit[0].equals("r")){				//����һ�Ʒ�������Ĵ������		
				sb.append(unit[1]);
				sb.append("����");
			}else if(unit[0].equals("yb")){				//����һ�Ʒ�������Ĵ������		
				sb.append(unit[1]);
				sb.append(GameConfig.getYuanbaoName());
			}
			if(a+1 != materials.length){
				sb.append("-");
			}
			}
			logger.info("i��value: "+i+" �һ�Ʒװ���ֶ�"+sb.toString());
			list.add(sb.toString());
			}
		return list;
	}
			/*String[] aa = reChange[i].split("-");
			if(aa[0].equals("d")){				//���ԭ�����ǵ��ߵĴ������
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
				if(bb[0].equals("d")){				//������һ����ǵ��ߵĴ������
					propvo = propDao.getById(Integer.valueOf(bb[2]));
					sb.append(propvo.getPropName());
				}else if(bb[0].equals("z")){		//������һ�����װ���Ĵ������
					if(bb[1].equals("1")){			//1��װ����2��������3������
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
				logger.info("i��value: "+i+" ���ߵ�װ���ֶ�"+sb.toString());
				list.add(sb.toString());
			}else if(aa[0].equals("z")){		//���ԭ������װ���Ĵ������
				StringBuffer sb = new StringBuffer();
				sb.append(i).append("-");
				sb.append(aa[3]).append("-");
				if(aa[1].equals("1")){			//1��װ����2��������3������
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
				
			
				if(bb[0].equals("d")){						//������һ����ǵ��ߵĴ������
					PropDao propDao = new PropDao();
					PropVO propvo = propDao.getById(Integer.valueOf(bb[2]));
					sb.append(propvo.getPropName());
				}else if(bb[0].equals("z")){				//������һ�����װ���Ĵ������
					if(bb[1].equals("1")){			//1��װ����2��������3������
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
				logger.info("i��value: "+i+" װ����װ���ֶ�"+sb.toString());
				list.add(sb.toString());
			}*/
		

	/*
	 * �жϸ��˵�ԭ�����Ƿ��㹻
	 * �����ԭ�����ǵ��ߺ���װ�������
	 * �ֱ�������ж�
	 * @param pPk ���˽�ɫid
	 * @param address ��Ҫ�һ�ԭ����������Դ���ϵ�λ�ã���0��ʼ����
	 * @param menu_id Ŀ¼id
	 * @return ����ɹ�����1�����򷵻�-1.
	 */
	public String getPPkHasGoods(String pPk, String address,OperateMenuVO vo,int exchange_num)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(pPk);
		
		int i = 1;
		String sb = "";
		String[] articles = vo.getMenuOperate1().split(";");	//����ԭ���ϵ��ַ���
		
		String article = articles[Integer.valueOf(address)];	//��Ҫ�һ�ԭ���ϵ��ַ���
		logger.info("ԭ���ϵ�article:"+article);
		
		String[] recourses = article.split(",");	
		for(int t= 0;t<recourses.length;t++) {
		
			String[] recourse = recourses[t].split("-");
			
			if(recourse[0].equals("d")){
				int number = Integer.valueOf(recourse[3]);				//ԭ������Ҫ�ĸ���
				number = exchange_num*number;							//�һ�����Ҫ�ĸ���
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int current_num = propGroupDao.getPropNumByByPropID(Integer.valueOf(pPk), Integer.valueOf(recourse[2]));//���е�����
				if(current_num < number){
					i = -1;
					sb = "�����ϵ�ԭ���ϲ��㣡";
				}
			} else if(recourse[0].equals("z")){
				int number = Integer.valueOf(recourse[2]);				//ԭ������Ҫ�ĸ���
				number = exchange_num*number;							//�һ�����Ҫ�ĸ���
				
				//int wrapClass = storageService.getGoodsClass(Integer.valueOf(recourse[2]),Integer.valueOf(recourse[1]));
				//����װ��
				//String wrapStr = recourse[2]+ ","+String.valueOf(wrapClass)+","+recourse[1];
				//logger.info("װ���ַ���:"+wrapStr);
				PlayerEquipDao equipDao = new PlayerEquipDao();
				int equip_num = equipDao.getEquipNumByEquipId(Integer.valueOf(pPk),Integer.valueOf(recourse[2]),Integer.valueOf(recourse[1]));
				if(equip_num < number){
					i = -1;
					sb = "�����ϵ�ԭ���ϲ��㣡";
				}
			} else if(recourse[0].equals("j")){
				long copper = roleInfo.getBasicInfo().getCopper();
				copper = exchange_num*copper;							//�һ�����Ҫ�Ľ�Ǯ
				if(copper < Long.valueOf(recourse[1])){
					i = -1;
					sb = "�����Ͻ�Ǯ���㣡";
				}
			} else if(recourse[0].equals("y")){
				long experience = Long.valueOf(roleInfo.getBasicInfo().getCurExp());
				experience = experience * exchange_num;
				
				if(experience < Integer.valueOf(recourse[1])){
					i = -1;
					sb = "���ľ��鲻�㣡";
				}
			}else if(recourse[0].equals("s")){
				Integer cid=Integer.parseInt(recourse[1]);
				Integer ncount=Integer.parseInt(recourse[2])*exchange_num;
				Integer ppk=Integer.parseInt(pPk);
				CreaditService service=new CreditProce();
				Integer res=service.checkHaveCondition(ppk, cid, ncount);
				if(res==-1||res.equals(-1)){
					i=-1;
					sb="�����޴�����!";
				}else if(res==0||res.equals(0)){
					i=-1;
					sb="�������������Զһ�����Ʒ!";
				}
			}else if(recourse[0].equals("r")){					// ������������
				Integer ncount=Integer.parseInt(recourse[1])*exchange_num;
				Integer ppk=Integer.parseInt(pPk);
				CreaditService service=new CreditProce();	
				Integer res=service.checkHonorCondition(ppk,ncount);
				if(res==-1||res.equals(-1)){
					i=-1;
					sb="�����޴�����!";
				}else if(res==0||res.equals(0)){
					i=-1;
					sb="��������ֵ�����Զһ�����Ʒ!";
				}
			}
		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(sb);
		return sbf.toString();
	}
	
	/**
	 * ���Ĳ���
	 * @param roleEntity
	 * @param needMaterialsStr
	 * @return
	 */
	public String consumeMaterials(RoleEntity roleEntity,String needMaterialsStr)
	{
		return this.consumeMaterials(roleEntity, needMaterialsStr, 1);
	}
	
	/**
	 * ���Ĳ���
	 * @param roleEntity
	 * @param needMaterialsStr                 �����ַ���
	 * @param needGroupNum					   ��Ҫ���ϵ�����
	 */
	public String consumeMaterials(RoleEntity roleEntity,String needMaterialsStr,int needGroupNum)
	{
		if( roleEntity!=null || needMaterialsStr==null || needGroupNum<=0)
		{
			return "��������";
		}
		String[] need_material_str_list = needMaterialsStr.split(",");	
		
		for(String need_material_str:need_material_str_list) 
		{
			String[] temp = need_material_str.split("-");
			
			if(temp[0].equals("d")){								//��������ϼ�ȥ����
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(roleEntity.getPPk(),Integer.valueOf(temp[2]),Integer.valueOf(temp[3])*needGroupNum,GameLogManager.R_EXCHANGE);
			}else if(temp[0].equals("z")){							//��������ϼ�ȥװ��
				GoodsService goodsService = new GoodsService();
				for(int a=0;a<Integer.parseInt(temp[2])*needGroupNum;a++){
					goodsService.removeEquipByEquipID(roleEntity.getPPk(),Integer.parseInt(temp[1]));
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(temp[2]), Integer.valueOf(temp[1]), Integer.valueOf(temp[2])*needGroupNum, StatisticsType.USED, StatisticsType.DUIHUAN,roleEntity.getPPk());
				}
			}else if(temp[0].equals("j")){							//��������ϼ�ȥ��Ǯ
				//���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleEntity.getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCopper()+"", -Integer.valueOf(temp[1])*needGroupNum+"", "�һ�ʧȥ");
				roleEntity.getBasicInfo().addCopper(-Integer.valueOf(temp[1])*needGroupNum);
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, Integer.valueOf(temp[1])*needGroupNum, StatisticsType.USED, StatisticsType.DUIHUAN,roleEntity.getPPk());
				
			}else if(temp[0].equals("y")){							//��������ϼ�ȥ����
				//���
				LogService logService = new LogService();
				logService.recordExpLog(roleEntity.getPPk(), roleEntity.getBasicInfo().getName(), roleEntity.getBasicInfo().getCurExp(), Integer.parseInt(temp[1])*needGroupNum+"", "�һ��۵�");
				
				roleEntity.getBasicInfo().updateAddCurExp(-Integer.parseInt(temp[1])*needGroupNum);
				
			} else if(temp[0].equals("s")) {							//��������ϼ�ȥ����
				Integer cid=Integer.parseInt(temp[1]);
				Integer ncount=Integer.parseInt(temp[2])*needGroupNum;
				CreaditService service=new CreditProce();
				service.subtractCredit(roleEntity.getPPk(), cid, ncount);
			 } else if(temp[0].equals("r")) {						//��������ϼ�ȥ��������
				Integer ncount=Integer.parseInt(temp[1])*needGroupNum;
				roleEntity.getBasicInfo().addFContribute(-ncount);
			}	
		}
		return null;
	}
	
	/**
	 * �ж��Ƿ����㹻�Ĳ���
	 */
	public String isEnoughMaterials( RoleEntity roleEntity,String needMaterialsStr)
	{
		return this.isEnoughMaterials(roleEntity, needMaterialsStr, 1);
	}
	
	/**
	 * �ж��Ƿ����㹻�Ĳ���
	 * @param roleEntity
	 * @param needMaterialsStr                 �����ַ���
	 * @param needGroupNum					   ��Ҫ���ϵ�����
	 * @return
	 */
	public String isEnoughMaterials( RoleEntity roleEntity,String needMaterialsStr,int needGroupNum)
	{
		if( roleEntity!=null || needMaterialsStr==null || needGroupNum<=0)
		{
			return "��������";
		}
		String[] need_material_str_list = needMaterialsStr.split(",");	
		
		for(String need_material_str:need_material_str_list) 
		{
			String[] temp = need_material_str.split("-");
			
			if(temp[0].equals("d"))
			{
				int number = Integer.valueOf(temp[3]);				//ԭ������Ҫ�ĸ���
				number = needGroupNum*number;							//�һ�����Ҫ�ĸ���
				PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
				int current_num = propGroupDao.getPropNumByByPropID(roleEntity.getPPk(), Integer.valueOf(temp[2]));//���е�����
				if(current_num < number){
					return "�����ϵĲ��ϲ��㣡";
				}
			} 
			else if(temp[0].equals("z"))
			{
				int number = Integer.valueOf(temp[2]);				//ԭ������Ҫ�ĸ���
				number = needGroupNum*number;							//�һ�����Ҫ�ĸ���
				PlayerEquipDao equipDao = new PlayerEquipDao();
				int equip_num = equipDao.getEquipNumByEquipId(roleEntity.getPPk(),Integer.valueOf(temp[2]),Integer.valueOf(temp[1]));
				if(equip_num < number){
					return "�����ϵĲ��ϲ��㣡";
				}
			} 
			else if(temp[0].equals("j"))
			{
				if( roleEntity.getBasicInfo().isEnoughMoney(Integer.valueOf(temp[1])*needGroupNum)==false)
				{
					return "�����ϵĲ��ϲ��㣡";
				}
			} 
			else if(temp[0].equals("y"))
			{
				long experience = Long.valueOf(roleEntity.getBasicInfo().getCurExp());
				experience = experience * needGroupNum;
				if(experience < Integer.valueOf(temp[1])){
					return "�����ϵĲ��ϲ��㣡";
				}
			}
			else if(temp[0].equals("s"))
			{
				Integer cid=Integer.parseInt(temp[1]);
				Integer need_num=Integer.parseInt(temp[2])*needGroupNum;
				CreaditService service=new CreditProce();
				Integer res=service.checkHaveCondition(roleEntity.getPPk(), cid, need_num);
				if(res==-1||res.equals(-1)){
					return "�����޴�����!";
				}else if(res==0||res.equals(0)){
					return "�������������Զһ�����Ʒ!";
				}
			}
			else if(temp[0].equals("r"))
			{					// ������������
				Integer need_num=Integer.parseInt(temp[1])*needGroupNum;
				if( roleEntity.getBasicInfo().isEnoughFContribute(need_num)==false)
				{
					return "��������ֵ����!";
				}
			}
		}
		return null;
	}
	
	//�õ�ĳ���ַ����ַ����г��ֵĴ���
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
	
	//�������Ƿ����㹻�İ����Ա����ɶһ�������Ʒ
	public String getHasWareSpare(String pPk,OperateMenuVO vo,String address,int exchange_num){
		int i = 1;
		String result = "";
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk);
		
		int warespare = roleInfo.getBasicInfo().getWrapSpare();				//�õ���Ұ���ʣ��ո���
		
		String[] articles = vo.getMenuOperate2().split(";");	//���жһ�Ʒ���ַ���
		String article = articles[Integer.valueOf(address)];	//��Ҫ�һ��һ�Ʒ���ַ���
		logger.info("�һ�Ʒ��article:"+article);
		
		String[] recourses = article.split(",");	
		for(int t= 0;t<recourses.length;t++) {
			
			String[] recourse = recourses[t].split("-");
			if(recourse[0].equals("z")){
				warespare = warespare -Integer.valueOf(recourse[3]) * exchange_num;
				//���ϰ����ռ�С�����ܷ���Ŀռ�ʱ
				if(warespare < 0){
					i = -1;
					result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
				}
			} else if(recourse[0].equals("d")){
				PropVO propvo = PropCache.getPropById(Integer.valueOf(recourse[2]));
				int accumulate = propvo.getPropAccumulate();
				int num = Integer.valueOf(recourse[3])/accumulate +(Integer.valueOf(recourse[3])%accumulate == 0? 0:1);
				warespare = warespare -num;
				//PropDao propDAO = new PropDao();
					if(warespare < 0){
						i = -1;
						result = "�����ϵİ����ռ䲻�㣡��������ռ䣡";
    			}
			}else if(recourse[0].equals("j")){
			 	long partCopper = roleInfo.getBasicInfo().getCopper();
			 	logger.info("���ϵ�Ǯ:"+partCopper+",�һ�����Ǯ:"+Long.valueOf(recourse[1])*exchange_num);
			 	
			 	if(partCopper + Long.valueOf(recourse[1])*exchange_num > 1000000000){
			 		i = -1;
			 		result = "���Ѿ�����Я����������ˣ�";
			 	}
			}
		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(i).append(",").append(result);
		return sbf.toString();
	}
	
	/**
	 *  �һ���Ʒ
	 */
	public String exchangeGoods(String pPk,OperateMenuVO vo,String address,int exchange_num){
		StringBuffer resultWml = new StringBuffer();
		String[] articles = vo.getMenuOperate1().split(";");	//����ԭ���ϵ��ַ���
		String[] dharticles = vo.getMenuOperate2().split(";");	//���жһ�Ʒ���ַ���
		
		String article = articles[Integer.valueOf(address)];	//��Ҫ�һ�ԭ���ϵ��ַ���
		String dharticle = dharticles[Integer.valueOf(address)];	//��Ҫ�һ��һ�Ʒ���ַ���
		
		String outMaterials = getMaterialsFromWrap(pPk,article,exchange_num);				//�����������ȥ���һ���ԭ����
		String getExchangeGoods = sendDuiHuanToWrap(pPk,dharticle,exchange_num);			//����ҷ��Ŷһ�Ʒ
		
		resultWml.append("�һ��ѳɹ���");
		resultWml.append(outMaterials);
		resultWml.append(getExchangeGoods);
		
		TimeControlService timeControlService = new TimeControlService();
		timeControlService.updateControlInfo(Integer.valueOf(pPk), Integer.valueOf(vo.getId()), TimeControlService.MENU);
		
		return resultWml.toString();
	}

	//����ҷ��Ŷһ�Ʒ
	private String sendDuiHuanToWrap(String pPk, String dharticle,int exchange_num)
	{
		if(dharticle.equals("0")){
			logger.info("������ҷ�����Ʒ��");
			return "";
		}
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk);
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("�������");
		String[]  articles = dharticle.split(",");
		for(int t=0;t<articles.length;t++){
			String[] unit = articles[t].split("-");
			if(unit[0].equals("d")){							//������������ӵ���
				GoodsService goodsService = new GoodsService();
				goodsService.putGoodsToWrap(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3])*exchange_num);
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(Integer.valueOf(unit[2]), StatisticsType.PROP, Integer.valueOf(unit[3])*exchange_num, StatisticsType.DEDAO, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
				
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("��").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("z")){						//�������������װ��
				GoodsService goodsService = new GoodsService();
				for(int i = 0;i<Integer.valueOf(unit[3]);i++){	//Ҫ������װ���͸�����
					goodsService.putGoodsToWrap(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[1]),Integer.valueOf(unit[3])*exchange_num);
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[3])*exchange_num, StatisticsType.DEDAO, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
					
				}
				
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("��").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("j")){						//������������ӽ�Ǯ
				
				//���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", Integer.valueOf(unit[1])*exchange_num+"", "�һ��õ�");
				
				roleInfo.getBasicInfo().addCopper(Integer.valueOf(unit[1])*exchange_num);
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY,Integer.valueOf(unit[1])*exchange_num, StatisticsType.DEDAO, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
				
				resultWml.append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])*exchange_num));
			}else if(unit[0].equals("y")){						//������������Ӿ���
				//���
				LogService logService = new LogService();
				logService.recordExpLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCurExp(), Integer.parseInt(unit[1])*exchange_num+"", "�һ��õ�");
				
				roleInfo.getBasicInfo().updateAddCurExp(Integer.parseInt(unit[1])*exchange_num);
				resultWml.append(Integer.valueOf(unit[1])*exchange_num+"����");
			}else if(unit[0].equals("s")){						//�����������������
				Integer ppk=Integer.parseInt(pPk);
				Integer cid=Integer.parseInt(unit[1]);
				Integer ncount=Integer.parseInt(unit[2])*exchange_num;
				CreaditService service=new CreditProce();
				service.addPlayerCredit(ppk, cid, ncount);
				PlayerCreditVO pcv=service.getPcvDisplay(cid);
				resultWml.append(pcv.getCreditname()).append("��").append(ncount);
			}else if(unit[0].equals("r")){						//�����������������
				Integer ppk=Integer.parseInt(pPk);
				Integer ncount=Integer.parseInt(unit[1])*exchange_num;
				CreaditService service=new CreditProce();
				service.addPlayerHonor(ppk,ncount);
				resultWml.append("����ֵ").append("��").append(ncount);											
			}else if(unit[0].equals("yb")) {					//�������������Ԫ��
				logger.info("�������������"+GameConfig.getYuanbaoName()+"=");
				logger.info("�һ�ʱ��ppk="+pPk);
				int uPk = roleInfo.getUPk();
				
				resultWml.append(GameConfig.getYuanbaoName()).append("��").append(Integer.parseInt(unit[1])*exchange_num);	
				
				
				EconomyService economyService = new EconomyService();
				//���������Ԫ��
				economyService.addYuanbao(roleInfo.getBasicInfo().getPPk(),uPk, Integer.parseInt(unit[1])*exchange_num,StatisticsType.DUIHUAN);
			}
			
			
			if(t+1 < articles.length)
				resultWml.append(",");
		}	
		resultWml.append(".");
		return resultWml.toString();
	}

	//�����������ȥ���һ���ԭ����
	private String getMaterialsFromWrap(String pPk, String article,int exchange_num)
	{
		if(article.equals("0")){
			logger.info("����������Ͽ۳������Ʒ��");
			return "";
		}
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk);
		
		StringBuffer resultWml = new StringBuffer();
		resultWml.append("��ʧȥ��");
		String[]  articles = article.split(",");
		for(int t=0;t<articles.length;t++){
			String[] unit = articles[t].split("-");
			if(unit[0].equals("d")){								//��������ϼ�ȥ����
				GoodsService goodsService = new GoodsService();
				goodsService.removeProps(Integer.valueOf(pPk),Integer.valueOf(unit[2]),Integer.valueOf(unit[3])*exchange_num,GameLogManager.R_EXCHANGE);
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("��").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("z")){							//��������ϼ�ȥװ��
				
				GoodsService goodsService = new GoodsService();

				for(int a=0;a<Integer.parseInt(unit[2])*exchange_num;a++){
					goodsService.removeEquipByEquipID(Integer.parseInt(pPk),Integer.parseInt(unit[1]));
					//ִ��ͳ��
					GameSystemStatisticsService gsss = new GameSystemStatisticsService();
					gsss.addPropNum(Integer.valueOf(unit[2]), Integer.valueOf(unit[1]), Integer.valueOf(unit[2])*exchange_num, StatisticsType.USED, StatisticsType.DUIHUAN,Integer.parseInt(pPk));

				}
				resultWml.append(goodsService.getGoodsName(Integer.valueOf(unit[2]), Integer.valueOf(unit[1])));
				resultWml.append("��").append(Integer.valueOf(unit[3])*exchange_num);
			}else if(unit[0].equals("j")){							//��������ϼ�ȥ��Ǯ
				
				//���
				LogService logService = new LogService();
				logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", -Integer.valueOf(unit[1])*exchange_num+"", "�һ�ʧȥ");
				
				roleInfo.getBasicInfo().addCopper(-Integer.valueOf(unit[1])*exchange_num);
				
				//ִ��ͳ��
				GameSystemStatisticsService gsss = new GameSystemStatisticsService();
				gsss.addPropNum(6, StatisticsType.MONEY, Integer.valueOf(unit[1])*exchange_num, StatisticsType.USED, StatisticsType.DUIHUAN,Integer.parseInt(pPk));
				
				resultWml.append(MoneyUtil.changeCopperToStr(Integer.valueOf(unit[1])*exchange_num));
			}else if(unit[0].equals("y")){							//��������ϼ�ȥ����
//				PartInfoDao partInfoDao = new PartInfoDao();
//				partInfoDao.updateExperience(Integer.valueOf(pPk),-Integer.valueOf(unit[1])*exchange_num);
				
				//���
				LogService logService = new LogService();
				logService.recordExpLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCurExp(), Integer.parseInt(unit[1])*exchange_num+"", "�һ��۵�");
				
				
				
				roleInfo.getBasicInfo().updateAddCurExp(-Integer.parseInt(unit[1])*exchange_num);
				
				resultWml.append(Integer.valueOf(unit[1])*exchange_num+"����");
			} else if(unit[0].equals("s")) {							//��������ϼ�ȥ����
				Integer cid=Integer.parseInt(unit[1]);
				Integer ncount=Integer.parseInt(unit[2])*exchange_num;
				Integer ppk=Integer.parseInt(pPk);
				CreaditService service=new CreditProce();
				service.subtractCredit(ppk, cid, ncount);
				PlayerCreditVO pcv=service.getPcvDisplay(cid);
				resultWml.append(pcv.getCreditname()).append("��").append(ncount);
			 } else if(unit[0].equals("r")) {						//��������ϼ�ȥ����
				Integer ppk=Integer.parseInt(pPk);
				Integer ncount=Integer.parseInt(unit[1])*exchange_num;
				CreaditService service=new CreditProce();
				service.subtractHonor(ppk,ncount);
				resultWml.append("����").append("��").append(ncount);
			}	
			if(t+1 < articles.length)
				resultWml.append(",");
		}
		resultWml.append(",");
		return resultWml.toString();
	}
}
