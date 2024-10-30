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
	 * �򿪻ƽ���
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
			reuslWmlString = "����Ʒ���ռ䲻��,�뱣���㹻�Ŀռ��ٳ��Կ�������!";
			return reuslWmlString;
		}
		
		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10�����²��ܴ򿪻ƽ���!";
			return reuslWmlString;
		}
	 	 // ���ȼ��������������ǲ�����Ķ���������ϡ�
		 if ( checkHaveThisProp(pg_pk,Integer.parseInt(gold_box_pgpk),roleInfo)) {
			 TimeControlService timeControlService = new TimeControlService();
			// �鿴���Ƿ��� 100�εķ�Χ֮��
				if ( timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, 
								TimeControlService.ANOTHERPROP, 100)) {
				
    			 	// ��ʼ������Ʒ
    			 	PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(gold_box_pgpk));
    			 	PropVO propVO = PropCache.getPropById(groupVO.getPropId());
    			 	//String npcId = getDuiYingGrade(roleInfo,propVO);
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// ���ԭ���ĵ�����
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// �ƽ������˸���Ʒ
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// ɾ��������������
    			 	PlayerPropGroupVO groupVO2 = dao.getByPgPk(Integer.parseInt(pg_pk));
    			 	goodsService.removeProps(groupVO2, 1);
    			 	goodsService.removeProps(groupVO, 1);
    			 	
    			 	// ��¼�±������Ϣ
    			 	String recordString = roleInfo.getBasicInfo().getName()+"���۷�һ������";
    				insertRecordInfo(roleInfo.getBasicInfo().getPPk(),2,recordString);
    				
				} else {
					 reuslWmlString = "���������߿����Ѿ�������������!";
					
				}
		 } else {
			 reuslWmlString = "���������䳬��100����!";
		 }
		return reuslWmlString;
	}
	
	
	/**
	 * ����������(�ǻƽ���)
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
			reuslWmlString = "����Ʒ���ռ䲻��,�뱣���㹻�Ŀռ��ٳ��Կ�������!";
			return reuslWmlString;
		}
		
		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10�����²��ܴ򿪴˱���!";
			return reuslWmlString;
		}
	 	 // ���ȼ��������������ǲ�����Ķ���������ϡ�
		 if ( checkHaveThisProp(pg_pk,Integer.parseInt(gold_box_pgpk),roleInfo)) {
			 
    			 	// ��ʼ������Ʒ
    			 	PlayerPropGroupVO groupVO = dao.getByPgPk(Integer.parseInt(gold_box_pgpk));
    			 	PropVO propVO = PropCache.getPropById(groupVO.getPropId());
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// ���ԭ���ĵ�����
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// �ƽ������˸���Ʒ
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// ɾ��������������
    			 	PlayerPropGroupVO groupVO2 = dao.getByPgPk(Integer.parseInt(pg_pk));
    			 	goodsService.removeProps(groupVO2, 1);
    			 	goodsService.removeProps(groupVO, 1);
    			 	
    			 	
			 } else {
				 reuslWmlString = "���������߿����Ѿ�������������!";				
    		 }
		return reuslWmlString;
	}

	/**
	 * �����ƽ�����Ϣ
	 */
	private String openGoldBoxInfo(RoleEntity roleInfo)
	{
		TimeControlService timeControlService = new TimeControlService();
		int already_num = timeControlService.alreadyUseNumber(roleInfo.getBasicInfo().getPPk(), 
				PropType.GOLD_BOX, TimeControlService.ANOTHERPROP) ;
		String info = "";
		if (already_num % 10 == 0 && already_num != 0) {
			SystemInfoService infoService = new SystemInfoService();
			info = "�ۡ�"+roleInfo.getBasicInfo().getName()+"ʵ����̫�����ˣ��������������Ѿ�����"+
					already_num+"�������ɲأ������ϵͳ���͵ġ��ɲ������һ����";
			infoService.insertSystemInfoBySystem(info) ;
			GoodsService goodsService = new GoodsService();
			PropDao propDao = new PropDao();
			int prop_id = propDao.getPropIdByType(PropType.GOLD_KEY);
			goodsService.putGoodsToWrap(roleInfo.getBasicInfo().getPPk(), prop_id, GoodsType.PROP, 1);
		}
			
		
		return info;
	}

	/**
	 * �ѱ������Ϣ��¼����
	 * @param pk
	 * @param info_type
	 * @param content
	 */
	private void insertRecordInfo(int pPk, int info_type, String content)
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		playerPropGroupDao.sendRecordGoldInfo(pPk,info_type,content);
			if(info_type == 1){
				//ͳ����Ҫ
				new RankService().updateAdd(pPk, "open", 1);
			}	
	}


	/**
	 * �õ���Ӧ
	 * @param roleInfo
	 * @param propVO 
	 * @return
	 */
	public int getDuiYingGrade(RoleEntity roleInfo, PropVO propVO)
	{
		// ��һ�����ֶ��� npc�ֶ�
		String propOperate1 = propVO.getPropOperate1();
		// ���������ֶ��� �ȼ��ֶ�
		String gradeFenBu = propVO.getPropOperate3();
		String[] npcIdStrings = propOperate1.split(";");
		
		// �õ���ǰ�ȼ��ε�λ��
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
	 * �õ��ȼ����ַ����е�λ��
	 * @param gradeFenBu ����40,50,60 �������ַ���
	 * @param grade	��ҵȼ�
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
	 * �鿴����Ҫ����Ʒ�Ƿ����������
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
	 * �鿴���Ƿ��н�Կ��
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
	 * ��ûƽ��������Ʒ
	 * @param roleInfo
	 * @param key_id ��Կ��ID
	 */
	public String  getGoldBoxGoods(RoleEntity roleInfo,String key_id,PropVO keyVO,PropVO boxVO,HttpServletResponse response,HttpServletRequest request)
	{
		TimeControlService timeControlService = new TimeControlService();
		NpcService npcService = new NpcService();
		
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		int result = 0;
		String resultWmlString = "";
		
		
		if (dropgoods!= null && dropgoods.size() != 0) {
			// �鿴���Ƿ�ӵ�н�Կ��
			if ( checkHaveGoldYueShi(roleInfo,key_id)) {
				// �鿴���Ƿ��� 101�εķ�Χ֮��
				if ( timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, 
								TimeControlService.ANOTHERPROP, 101)) {
				
    			Random random = new Random();
    			int num = random.nextInt(dropgoods.size()) ;
    			DropGoodsVO dropGoods = dropgoods.get(num);
    			
    			
    			// �����Ӧ�û�õ���Ʒ��Ϣ��¼����
    			String hintString = roleInfo.getBasicInfo().getName()+"���˽�Կ��,Ӧ�û��"+dropGoods.getGoodsName()+"*"+dropGoods.getDropNum();
    			insertRecordInfo(roleInfo.getBasicInfo().getPPk(),2,hintString);
    			
    			GoodsService goodsService = new GoodsService();
    			result = goodsService.pickupGoods(roleInfo.getBasicInfo().getPPk(), dropGoods.getDPk(),0,response,request);
    			
    			if ( result == -1) {
    				resultWmlString = "���İ����ռ䲻��!";
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    			} else {
    				this.getGoldBoxInfo(dropGoods,roleInfo,timeControlService);
    				goodsService.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(key_id), 1,GameLogManager.R_USE);
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    				
    				insertRecordInfo(roleInfo.getBasicInfo().getPPk(),1,hintString);
    				// ��������������1    			 	
    			 	timeControlService.updateControlInfo(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, TimeControlService.ANOTHERPROP);
    			 	//��ҵĿ������ܴ�������
    			 	playerOpenGoldBox(roleInfo.getBasicInfo().getUPk(), roleInfo.getBasicInfo().getPPk());
    			 	
    			 	// ����ı�����Ϣ
    				String reuslWmlString = openGoldBoxInfo(roleInfo);
    			 	
    			 	resultWmlString = "��ʹ����һ��"+keyVO.getPropName()+"����"+boxVO.getPropName()+"��һ������������鱦������ǰ!<br/> �������"+dropGoods.getGoodsName()+"��"+dropGoods.getDropNum()
					+"<br/>"+reuslWmlString;
    			}
				} else {
					// �����ƽ��䳬��100��
					resultWmlString = "���������䳬��100����!";
					npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
					
				}
			} else {
				// ���û�н�Կ�׾Ϳ�������ʧ��
				resultWmlString = "��û��"+keyVO.getPropName()+",����ʧ��!";
				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			}
		}else {
			resultWmlString = "���Ѿ�����������!";			
		}
		return resultWmlString;
	}
	
	/**
	 * �����������(�ǻƽ���)�����Ʒ
	 * @param roleInfo
	 * @param key_id ��Կ��ID
	 */
	public String  getGoldBoxGoodsByOtherBox(RoleEntity roleInfo,String key_id,PropVO keyVO,PropVO boxVO,HttpServletResponse response,HttpServletRequest request)
	{
		//TimeControlService timeControlService = new TimeControlService();
		NpcService npcService = new NpcService();
		
		List<DropGoodsVO> dropgoods = roleInfo.getDropSet().getList();
		int result = 0;
		String resultWmlString = "";
				
		if (dropgoods!= null && dropgoods.size() != 0) {
			// �鿴���Ƿ�ӵ�н�Կ��
			if ( checkHaveGoldYueShi(roleInfo,key_id)) {
				
    			Random random = new Random();
    			int num = random.nextInt(dropgoods.size()) ;
    			DropGoodsVO dropGoods = dropgoods.get(num);
    			GoodsService goodsService = new GoodsService();
    			result = goodsService.pickupGoods(roleInfo.getBasicInfo().getPPk(), dropGoods.getDPk(),0,response,request);
    			
    			if ( result == -1) {
    				resultWmlString = "���İ����ռ䲻��!";
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    			} else {
    				goodsService.removeProps(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(key_id), 1,GameLogManager.R_USE);
    				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
    				resultWmlString = "��ʹ����һ��"+keyVO.getPropName()+"����"+boxVO.getPropName()+"��һ����������ı�������������ǰ!<br/> �������"+dropGoods.getGoodsName()+"��"+dropGoods.getDropNum();	
    			}
				
			} else {
				// ���û�н�Կ�׾Ϳ�������ʧ��
				resultWmlString = "��û��"+keyVO.getPropName()+",����ʧ��!";
				npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
			}
		}else {
			resultWmlString = "���Ѿ�����������!";			
		}
		return resultWmlString;
	}
	
	
	
	
	

	/**
	 * ��ȡ��������Ʒʱ����Ϣ
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
    		info = "��ϲ"+roleInfo.getBasicInfo().getName()+"������"+already_num+"���ƽ���ʱ�����"+dropGoods.getGoodsName()+"!";
    		infoService.insertSystemInfoBySystem(info) ;
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		playerPropGroupDao.sendGoldInfo(roleInfo.getBasicInfo().getPPk(),roleInfo.getBasicInfo().getGrade(),
							dropGoods.getGoodsId(),dropGoods.getGoodsName(),dropGoods.getGoodsQuality());
		
	}

	/**
	 * �����ƽ��������Ʒ
	 * @param roleInfo
	 * @return
	 */
	public String dropGoldBoxGoods(RoleEntity roleInfo,PropVO boxVO)
	{
		
		NpcService npcService = new NpcService();
		npcService.deleteByPpk(roleInfo.getBasicInfo().getPPk());
		
		String hint = "��������"+boxVO.getPropName()+"�����Ʒ!";
		return hint;
	}
	
	/**
	 * �ý�Կ�״���������(�ǻƽ���)
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
		
		// ��ûƽ����id
		int prop_id = Integer.parseInt(box_id);
		
		if (roleInfo.getBasicInfo().getWrapSpare() < 1) {
			reuslWmlString = "����Ʒ���ռ䲻��,�뱣���㹻�Ŀռ��ٳ��Կ�������!";
			return reuslWmlString;
		}

		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10�����²��ܴ򿪴˱���!";
			return reuslWmlString;
		}
		// ��ûƽ����pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo.getBasicInfo().getPPk(),prop_id);
		
		PlayerPropGroupVO keyGroupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	 // ���ȼ��������������ǲ�����Ķ���������ϡ�
		 if ( keyGroupVO != null) {
			 
					// ��ʼ������Ʒ
    			 	PropVO propVO = PropCache.getPropById(prop_id);
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// ���ԭ���ĵ�����
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// �ƽ������˸���Ʒ
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// ɾ�����ƽ���
    			 	goodsService.removeProps(boxGroupVO, 1);
    			 	
    			 	
			} else {
			 reuslWmlString = "�ƽ�������Ѿ�������������!";
			}
		return reuslWmlString;
	}
	
	
	/**
	 * �ж��Ƿ���Դ򿪻ƽ���
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
		//�ж��Ƿ�����õ���Կ��
		if (roleInfo.getBasicInfo().getWrapSpare() < 2) {
			reuslWmlString = "����Ʒ���ռ䲻��,�뱣���㹻�Ŀռ��ٳ��Կ�������!";
			return reuslWmlString;
		}

		if (roleInfo.getBasicInfo().getGrade() < 10) {
			reuslWmlString = "10�����²��ܴ򿪻ƽ���!";
			return reuslWmlString;
		}
		// ��ûƽ����pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo.getBasicInfo().getPPk(),prop_id);
		
		PlayerPropGroupVO keyGroupVO = dao.getByPgPk(Integer.parseInt(pg_pk));
	 	 // ���ȼ��������������ǲ�����Ķ���������ϡ�
		 if ( keyGroupVO != null) {
			 TimeControlService timeControlService = new TimeControlService();
			// �鿴���Ƿ��� 100�εķ�Χ֮��
				if ( timeControlService.isUseable(roleInfo.getBasicInfo().getPPk(), PropType.GOLD_BOX, 
								TimeControlService.ANOTHERPROP, 100)) {
					
					// ��ʼ������Ʒ
    			 	PropVO propVO = PropCache.getPropById(prop_id);
    			 	NpcService npcService = new NpcService();
    			 	
    			 	// ���ԭ���ĵ�����
    			 	roleInfo.getDropSet().clearDropItem();
    			 	// �ƽ������˸���Ʒ
    			 	PlayerService playerService = new PlayerService();
    				PartInfoVO player = playerService.getPlayerByPpk(roleInfo.getBasicInfo().getPPk());
    			 	npcService.dropGoodsByRareBoxByGOLD(roleInfo, player,propVO);
    			 	
    			 	// ɾ�����ƽ���
    			 	goodsService.removeProps(boxGroupVO, 1);
    			 	
				} else {
					 reuslWmlString = "��ÿ�����ֻ�ܿ���100���ƽ���!";
				}
		 } else {
			 reuslWmlString = "�ƽ�������Ѿ�������������!";
		 }
		return reuslWmlString;
	}
	
	//��ҳ
	public QueryPage getGoldBoxList(int p_pk, String  prop_id,int page_no)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		return dao.getGoldBoxList(p_pk, prop_id,page_no);
	}
	
	//�ж�����Ƿ��лƽ����¼
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
