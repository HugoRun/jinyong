package com.ls.web.service.npc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.dao.task.UTaskDAO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ben.vo.task.UTaskVO;
import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.menu.MenuCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.npc.NpcAttackDao;
import com.ls.ben.dao.info.npc.NpcdropDao;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.dao.info.npc.NpcskilDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropExpMoneyVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.info.npc.NpcskillVO;
import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.iface.function.Probability;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.NpcType;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.attack.AttackService;
import com.ls.web.service.buff.BuffEffectService;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.equip.EquipService;
import com.ls.web.service.goods.prop.GoldBoxService;
import com.ls.web.service.menu.MenuService;
import com.ls.web.service.player.FightService;
import com.ls.web.service.player.MyService;
import com.ls.web.service.player.MyServiceImpl;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.room.RoomService;
import com.ls.web.service.task.TaskSubService;
import com.pm.constant.NpcGaiLv;
import com.pm.dao.record.RecordDao;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.jieyi.util.Constant;
import com.web.service.task.TaskPageService;

/**
 * ����:����NPC��ص��߼�
 * 
 * @author ��˧ 7:42:28 PM
 */
public class NpcService
{

	Logger logger = Logger.getLogger("log.service");

	public static final int PLAYERINJURE = 1;
	public static final int PETINJURE = 2;

	// �����ʱ���
	public final static int DROP_MULTIPLE = 1;

	/** 
	 * ���д��ڹ���״̬��npc������ɫ
	 * 
	 * @param npcs
	 * @param player
	 */
	public void attackPlayer(List npcs, Fighter player)
	{
		RoleEntity roleinfo = RoleCache.getByPpk(player.getPPk());
		if (npcs == null || npcs.size() == 0 || player == null)
		{
			logger.debug("��������");
			return;
		}
		FightService fightService = new FightService();

		NpcFighter npc = (NpcFighter) npcs.get(0);
		NpcskillVO npcSkill = null;
		int injure = 0;

		int injure_temp = 0;
		int skillInjure_temp = 0;
		int wxInjure_temp = 0;

		StringBuffer injureDisplay = new StringBuffer();

		for (int i = 0; i < npcs.size(); i++)
		{
			npc = (NpcFighter) npcs.get(i);
			if(npc.getNpcType() == NpcAttackVO.NIANSHOU){
				npcSkill = getNpcSkill(npc);
				
					npc = Constant.PETNPC.get(npc.getNpcID());
					if(npc.getNpccountnum()<10){
						injure = player.getPUpHp()*npc.getNpccountnum()/10;
						injure = injure + MathUtil.getRandomBetweenXY(npcSkill.getNpcskiInjureXiao(), npcSkill.getNpcskiInjureDa());
						injureDisplay.append("-" + injure);
					}else{
						injure = npc.getNpccountnum()*npcSkill.getNpcskiWxInjure()/100;
						injure = injure + MathUtil.getRandomBetweenXY(npcSkill.getNpcskiInjureXiao(), npcSkill.getNpcskiInjureDa());
						injureDisplay.append("-" + injure);	
				
				}
				player.setPlayerInjure(injure);
				if (npcSkill != null) {
					npc.setSkillName(npcSkill.getNpcskiName());
				}
				if (player.isDead()) // ��������˺�Ϊ
				{
					
					player.appendKillDisplay("����"
							+ StringUtil.isoToGBK(npc.getNpcName()) + "ɱ���ˣ�");
					if (i != 0)
					{
						injureDisplay.append("/");
					}
					injureDisplay.append("-" + injure);
					break;
				}
			}else{
				logger.debug("npcs.size=" + npcs.size() + " ,npc.getNpcType()="
						+ npc.getNpcType());
				// ���npc����Ϊ���,�ͻ᲻�������
				if (npc.getNpcType() == NpcAttackVO.MAST || npc.getNpcType() == NpcAttackVO.ZHAOHUN 
						|| npc.getNpcType() == NpcAttackVO.CITYDOOR || npc.getNpcType() == NpcAttackVO.DIAOXIANG)
				{
					npc.setSkillName("������");
					logger.debug("��Ϊ�����,���в��������");
					break;
				}
				logger.debug("npcs.size=" + npcs.size());

				if (npc.getDizzyBoutNum() > 0) // npc������
				{
					injure_temp = 0;
					npc.setSkillName("������");
					// ���ٻ���״̬�Ļغ���
					updateDizzyBoutNumOfNPC(npc.getID(), -1);
				}
				else if (npc.getPoisonBoutNum()>0) {
					injure_temp=0;
					npc.setSkillName("�ж�");
					//�����ж�״̬�Ļغ���
					updatePoisonBoutNumOfNPC(npc.getID(), -1);
				}
				else
				{
					npcSkill = getNpcSkill(npc);
					int npc_gj = 0;
					if (npcSkill == null) {
						npc_gj = npc.getAppendGj();
					} else {
						npc_gj = npcSkill.getNpcskiInjure() + npc.getAppendGj();
					}
					int npc_level = npc.getLevel();
					int p_fy = player.getBasicFy();//��������
					int p_zbfy = player.getZbFy();//װ������
					int p_level = player.getPGrade();
					//���з����ĸ���ֵ ��ҵķ���/100�õ��������ĸ���ֵ
					int wx_fy_join = (p_fy+p_zbfy)/100;
					
					skillInjure_temp = AttackService.npcSkillInjureExpressions(
							npc_gj, p_fy, p_zbfy, p_level, npc_level);
					wxInjure_temp = AttackService.getWxInjureValue(npcSkill, player
							.getWx(), npc.getLevel(), player.getPGrade(),wx_fy_join);
					injure_temp = skillInjure_temp + wxInjure_temp;
					if (injure_temp <= 0)
					{
						injure_temp = 1;
					}
					if (injure_temp > player.getPHp())
					{
						injure_temp = player.getPHp();
					}

					// ���˺����ۼ�
					injure += injure_temp;
					
					if (npcSkill != null) {
						npc.setSkillName(npcSkill.getNpcskiName());
					}
					player.setPlayerInjure(injure_temp);
					if (player.isDead()) // ��������˺�Ϊ
					{
						
						player.appendKillDisplay("����"
								+ StringUtil.isoToGBK(npc.getNpcName()) + "ɱ���ˣ�");
						if (i != 0)
						{
							injureDisplay.append("/");
						}
						injureDisplay.append("-" + injure_temp);
						// player.setKillDisplay("����"+StringUtil.isoToGB(npc.getNpcName())+"����ˣ�");
						if(npc.getNpcType() == NpcType.MENPAINPC){
							roleinfo.getBasicInfo().updateSceneId(210+"");
							roleinfo.getBasicInfo().setShouldScene(210);
							Constant.MENPAINPC.put(1, 0);
						}
						break;
					}
				}
				// ����˺�����
				if (i != 0)
				{
					injureDisplay.append("/");
				}
				injureDisplay.append("-" + injure_temp);
			}
		}

		EquipService equipService = new EquipService();
		// ����PK��ȥװ���־�
		equipService.useEquip(player.getPPk());

		// ���½�ɫ״̬
		player.setInjureDisplay(injureDisplay.toString());
		fightService.statUpdateByPlayerInjure(player, player,npc.getNpcType());
	}

	/**
	 * npc����õ�������ɫ��һ��
	 * @param npc
	 * @return
	 */
	public NpcskillVO getNpcSkill(NpcAttackVO npc)
	{
		NpcskillVO npcSkill = null;

		NpcskilDao npcSkillDao = new NpcskilDao();

		// �õ�npc��ӵ�е����м���
		List npcSkills = npcSkillDao.getSkillByNpcID(npc.getNpcID());

		// ���ݸ��ʵõ�һ��ʹ�ü���, ������100Ϊ����.
		npcSkill = (NpcskillVO) MathUtil.getRandomEntityFromList(npcSkills, 100);

		if (npcSkill != null)
		{
			int skiInjure = MathUtil.getRandomBetweenXY(npcSkill.getNpcskiInjureXiao(), npcSkill.getNpcskiInjureDa());
			double supperInjureMultiple = 1; // ��������
			if (MathUtil.isAppearByPercentage(npc.getDrop()))
			{
				logger.info("npc���ֱ���");
				// 4��7�� ����������2����Ϊ1.2
				supperInjureMultiple = 1.2;
			}
			//4��7�� �˺���INT ���� ����ΪDOUBLE����
			npcSkill.setNpcskiInjure(skiInjure, supperInjureMultiple);
		}

		return npcSkill;
	}


	/**
	 * ����npd_id�͵����õ����е�����Ʒ
	 * 
	 * @param npc_id
	 */
	private List<DropGoodsVO> dropGoods(int npc_id, PartInfoVO player)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		List<DropGoodsVO> drop_goods_list = new ArrayList<DropGoodsVO>();
		if (player == null)
		{
			logger.debug("����Ϊ�գ�");
			return drop_goods_list;
		}
		TaskSubService taskService = new TaskSubService();

		NpcdropDao npcDropDao = new NpcdropDao();
		
		//����ǰ�����������ʱ��������ݣ���ֹ��������Ʒ
		role_info.getDropSet().clearDropItem();

		String drop_task_condition = taskService.getDropTaskConditions(player.getPPk());
		
		List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByNpcID(npc_id,drop_task_condition); // npc����Ҫ������Ʒ

		checkPropIsDrop(npcdrops, player, drop_goods_list);

		return drop_goods_list;
	}
	/**
	 * ����npd_id�͵����õ����е�����Ʒ
	 * �ƽ���ʹ��
	 * @param npc_id
	 */
	private List<DropGoodsVO> dropGoodsByGoldBox(int npc_id, PartInfoVO player)
	{
		List<DropGoodsVO> drop_goods_list = new ArrayList<DropGoodsVO>();
		if (player == null)
		{
			logger.debug("����Ϊ�գ�");
			return drop_goods_list;
		}
		TaskSubService taskService = new TaskSubService();

		NpcdropDao npcDropDao = new NpcdropDao();

		String drop_task_condition = taskService.getDropTaskConditions(player
				.getPPk());
		List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByNpcID(npc_id,
				drop_task_condition); // npc����Ҫ������Ʒ
		
		// ȷ�����ĸ���Ʒ  
		List<Probability> list = new ArrayList<Probability>();
		NpcGaiLv npcGaiLv = null; 
		NpcdropVO npcdropVO = null;
		for ( int i =0;i<npcdrops.size();i++) {
			npcGaiLv =  new NpcGaiLv();
			npcdropVO = npcdrops.get(i);
			//npcGaiLv.setId(npcdropVO.getNpcdropID());
			npcGaiLv.setId(i);
			npcGaiLv.setProbability(npcdropVO.getNpcdropProbability());
			list.add(npcGaiLv);
		}
		
		Probability probability = MathUtil.getRandomEntityFromList(list,MathUtil.DROPDENOMINATOR);
		int npcID = probability.getId();
		npcdropVO = npcdrops.get(npcID);
		
		// ����Ʒ�ӵ��������
		addGoodsToDropTable(npcdropVO, player,drop_goods_list);

		return drop_goods_list;
	}

	/**
	 * ����npd_id�͵����õ����е�����Ʒ ���Ա���ʹ��
	 * 
	 * @param npc_id
	 */
	public NpcdropVO dropGoodsByLabaBox(int npc_id, PartInfoVO player, int p_pk)
	{
		if (player == null)
		{
			logger.debug("����playerΪ�գ�");
			return null;
		}
		TaskSubService taskService = new TaskSubService();

		NpcdropDao npcDropDao = new NpcdropDao();

		String drop_task_condition = taskService.getDropTaskConditions(player
				.getPPk());
		List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsByNpcID(npc_id,
				drop_task_condition); // npc����Ҫ������Ʒ

		// ȷ�����ĸ���Ʒ
		NpcdropVO npcdropVO = npcdrops.get(0);

		// ����Ʒ�ӷŵ���ұ�����
		GoodsService goodsService = new GoodsService();
		goodsService.putPropToWrap(p_pk, npcdropVO.getGoodsId(), 1,GameLogManager.G_BOX_DROP);
		return npcdropVO;// ������Ʒ������
	}

	/**
	 * ����npd_id�͵����õ����е�����Ʒ ���Ա���ʹ��
	 * 
	 * @param npc_id
	 */
	public NpcdropVO dropGoodsByLabaBoxTwo(int npc_id, PartInfoVO player,
			int p_pk)
	{
		if (player == null)
		{
			logger.debug("����playerΪ�գ�");
			return null;
		}
		NpcdropDao npcDropDao = new NpcdropDao();
		List<NpcdropVO> npcdrops = npcDropDao.getNpcdropsForLaBa(String
				.valueOf(npc_id)); // npc����Ҫ������Ʒ
		// ȷ�����ĸ���Ʒ
		NpcdropVO npcdropVO = null;
		npcdropVO = npcdrops.get(0);
		return npcdropVO;// ������Ʒ������
	}

	/**
	 * ����Ʒ�ӵ��������
	 * 
	 * @param npcdrops
	 * @param player
	 * @param drop_goods_list
	 * @param maxSize
	 *            drop_goods_list�����size
	 */
	private void addGoodsToDropTable(NpcdropVO npcdropVO, PartInfoVO player,List<DropGoodsVO> drop_goods_list)
	{
		DropGoodsVO dropGoodsVO = new DropGoodsVO();
		if (npcdropVO != null )
		{
					dropGoodsVO.setPPk(player.getPPk());
					dropGoodsVO.setDropNum(npcdropVO.getNpcDropNum());
					dropGoodsVO.setGoodsId(npcdropVO.getGoodsId());
					dropGoodsVO.setGoodsType(npcdropVO.getGoodsType());
					dropGoodsVO.setGoodsName(npcdropVO.getGoodsName());
					dropGoodsVO.setGoodsImportance(npcdropVO.getNpcDropImprot());
					
					if (dropGoodsVO.getGoodsType() == GoodsType.EQUIP)
					{
						this.loadDropEquipQuality(dropGoodsVO, npcdropVO);//���ص���װ����Ʒ��
					}
					
					if ( dropGoodsVO.getGoodsImportance() == 2) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"����"+NpcCache.getNpcNameById(npcdropVO.getNpcID())+"���"
									+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					} else if (dropGoodsVO.getGoodsImportance() == 3) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"��"+NpcCache.getNpcNameById(npcdropVO.getNpcID())+"���"
									+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					}else if (dropGoodsVO.getGoodsImportance() == 1) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"���"	+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					}
					
					drop_goods_list.add(dropGoodsVO);
			}		
		else
		{
			logger.info("�޵�����");
		}
	}
	/**
	 * ȷ�� npc������ �Ƿ����
	 * 
	 * @param npcdrops
	 * @param player
	 * @param drop_goods_list
	 * @param maxSize
	 *            drop_goods_list�����size
	 */
	private void checkPropIsDrop(List<NpcdropVO> npcdrops, PartInfoVO player,
			List<DropGoodsVO> drop_goods_list)
	{
		// ��ͨ�ֵ��������Ϊ3��
		int maxSize = 3;
		
		DropGoodsVO dropGoodsVO = new DropGoodsVO();
		if (npcdrops != null && npcdrops.size() > 0)
		{
			RefurbishService refurbishService = new RefurbishService();
			if( refurbishService.isboss(Integer.parseInt(player.getPMap()), npcdrops.get(0).getNpcID()))
			{
				//�����boss���������Ϊ7��
				maxSize = 7;
			}
			
			for (NpcdropVO npcdropVO : npcdrops)
			{
				//�ж��Ƿ����
				boolean isDrop = npcdropVO.isDrop(player.getAppendDropProbability());
				if ( (isDrop && drop_goods_list.size() < maxSize) || (npcdropVO.getNpcdropProbability() >= MathUtil.DROPDENOMINATOR) )
				{
					dropGoodsVO = new DropGoodsVO();
					dropGoodsVO.setPPk(player.getPPk());
					dropGoodsVO.setDropNum(npcdropVO.getNpcDropNum());
					dropGoodsVO.setGoodsId(npcdropVO.getGoodsId());
					dropGoodsVO.setGoodsType(npcdropVO.getGoodsType());
					dropGoodsVO.setGoodsName(npcdropVO.getGoodsName());
					dropGoodsVO.setGoodsImportance(npcdropVO.getNpcDropImprot());
					
					if (dropGoodsVO.getGoodsType() == GoodsType.EQUIP)
					{
						this.loadDropEquipQuality(dropGoodsVO, npcdropVO);//���ص���װ����Ʒ��
					}
					//��ֵõ�����Ʒ
					if ( dropGoodsVO.getGoodsImportance() == 2) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"����"+NpcCache.getNpcNameById(npcdropVO.getNpcID())+"���"
									+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					} 
					//����õ�����Ʒ
					else if (dropGoodsVO.getGoodsImportance() == 3) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"��"+NpcCache.getNpcNameById(npcdropVO.getNpcID())+"���"
									+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					}
					//������
					else if (dropGoodsVO.getGoodsImportance() == 1) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"���"	+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					}
					//��ֵõ�����Ʒ(����)
					else if (dropGoodsVO.getGoodsImportance() == 4) {
						GoodsService goodsService = new GoodsService();
						String dropInfo = "��ϲ"+player.getPName()+"ū��"+NpcCache.getNpcNameById(npcdropVO.getNpcID())+"���"
									+goodsService.getGoodsName(npcdropVO.getGoodsId(), npcdropVO.getGoodsType());
						dropGoodsVO.setDropGoodsInfo(dropInfo);
					}
					

					drop_goods_list.add(dropGoodsVO);
				}
				if (npcdropVO.getNpcdropProbability() >= MathUtil.DROPDENOMINATOR)
				{
					// ���Ҫ��Ʒ�� �������Ϊһ����,���ص���Ʒ, �͸���Ʒ����������ֵ��һ
					maxSize++;
				}
			}
		}
		else
		{
			logger.info("�޵�����");
		}
	}

	/**
	 * ɱ��npc������Ʒ
	 * 
	 * @param npc_id
	 * @param player
	 */
	public void dropGoodsByNpc(int npc_id, PartInfoVO player)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		List<DropGoodsVO> drop_goods_list = dropGoods(npc_id, player);
		role_info.getDropSet().addDropItem(drop_goods_list);
	}

	/**
	 * ���������Ʒ
	 * 
	 * @param npc_id
	 * @param player
	 */
	public void dropGoodsByRareBox(int npc_id, PartInfoVO player, String dropNum)
	{
		if ( dropNum == null || Integer.parseInt(dropNum) <= 0) {
			return ;
		}
		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		
		// TODO ��������е�npc_id ��������Ʒ��,��ô��������
		List<DropGoodsVO> drop_goods_list = null;
		// �������ѭ��500�κ���ѭ��,���������ѭ��
		int num = 0;
		do
		{
			drop_goods_list = dropGoods(npc_id, player);
			num++;
			if (num > 400)
			{
				logger.error("�������������� ");
			}
		} while ((drop_goods_list == null && num < 500 )
				|| (drop_goods_list.size() == 0 && num < 500));

		role_info.getDropSet().addDroItem(drop_goods_list,Integer.valueOf(dropNum));
	}
	
	/**
	 * ���������Ʒ
	 * @param npc_id
	 * @param player
	 */
	public void dropGoodsByRareBoxByGOLD(RoleEntity roleInfo, PartInfoVO player, PropVO propVO)
	{	
		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		String[] dropNum = propVO.getPropOperate2().split(",");
		GoldBoxService goldBoxService = new GoldBoxService();
		for (int i = 0; i < Integer.parseInt(dropNum[0]); i++)
		{ 
			int npcId = goldBoxService.getDuiYingGrade(roleInfo,propVO);
			
			List<DropGoodsVO> drop_goods_list = dropGoodsByGoldBox(npcId, player);
			
			role_info.getDropSet().addDropItem(drop_goods_list);
		}
	}
	
	/**
	 * ���������Ʒ
	 * 
	 * @param npc_id
	 * @param player
	 *
	public void dropGoodsByRareBoxByGOLD(int npc_id, PartInfoVO player, String dropNum)
	{
		// TODO ��������е�npc_id ��������Ʒ��,��ô��������
		List<DropGoodsVO> drop_goods_list = null;
		// �������ѭ��500�κ���ѭ��,���������ѭ��
		int num = 0;
		do
		{
			drop_goods_list = dropGoods(npc_id, player);
			num++;
			if (num > 400)
			{
				logger.error("�������������� ");
			}
		} while ((drop_goods_list == null && num < 500  )
				|| (drop_goods_list != null && num < 500 && drop_goods_list.size() < Integer.parseInt(dropNum)));
		
		
		
		for (int i = 0; i < Integer.valueOf(dropNum); i++)
		{
			if (drop_goods_list != null && drop_goods_list.size() > 0)
			{
				//int random_index = (int) Math.random() * drop_goods_list.size();
				//Random random = new Random();
				//int random_index =  random.nextInt(drop_goods_list.size());
				DropGoodsDao dropGoodsDao = new DropGoodsDao();
				DropGoodsVO drop_goods = drop_goods_list.get(i);
				dropGoodsDao.add(drop_goods);
			}
		}
	} */

	/**
	 * �������������Ʒ
	 * 
	 * @param npc_id
	 * @param player
	 */
	public void dropGoodsByJiangRareBox(int npc_id, PartInfoVO player)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		List<DropGoodsVO> drop_goods_list = dropGoods(npc_id, player);
		role_info.getDropSet().addDropItem(drop_goods_list);
	}

	/**
	 * ���ص���װ����Ʒ��
	 */
	private void loadDropEquipQuality(DropGoodsVO dropGoodsVO,NpcdropVO npcdropVO)
	{
		int quality = 0;
		if( npcdropVO.getQuality()==-1 )//����������Ʒ��
		{
			GameEquip gameEquip = EquipCache.getById(dropGoodsVO.getGoodsId());
			quality = gameEquip.getDropQuality();
		}
		else//ָ�����ɵ�Ʒ��
		{
			quality = npcdropVO.getQuality();
		}
		dropGoodsVO.setGoodsQuality(quality);
		
		dropGoodsVO.setGoodsName(dropGoodsVO.getGoodsName()+ ExchangeUtil.getQualityName(quality));
	}


	/**
	 * �����ǰ��ɫˢ�³�һֻnpc����ɱ��һֻnpc
	 * 
	 * @param p_pk
	 * @return
	 *//*
	public int deleteByNpk1(int n_pk)
	{
		int result = -1;
		NpcAttackDao npcAttackDao = new NpcAttackDao();
		result = npcAttackDao.deleteByNpk(n_pk);

		// ���npc��buffЧ��
		BuffEffectService buffService = new BuffEffectService();
		buffService.clearBuffEffect(n_pk, BuffSystem.NPC);
		return result;
	}*/

	/**
	 * ����npc,ͬʱ������Ĳ���ʱ���¼,
	 * 
	 * @param p_pk
	 * @return
	 */
	public int capturePet(NpcAttackVO npc, String mapid)
	{
		int result = -1;
		int p_pk = npc.getPPk();
		
		RoleEntity  roleInfo = RoleService.getRoleInfoById(p_pk+"");
		
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//		logger.info("��������=" + npc.getNAttackswitch());
//		if (npc.getNpcIsAttack() == 1)
//		{
//			result = npcAttackDao.deleteNpcByMapNpcSwitch(p_pk, npc_id, mapid);
//		}
//		else
//		{
//			result = npcAttackDao.deleteNpcByMapNpcPPk(p_pk, npc_id, mapid);
//		}
		
		AttacckCache attacckCache = new AttacckCache();
		List<NpcFighter> list = null;
		
		list = attacckCache.getZDAttackNpc(npc.getPPk(), AttacckCache.ZDATTACKNPC);
		if(list != null && list.size() != 0){
			list.remove(0);
		}
		

		// ���npc��buffЧ��
		BuffEffectService buffService = new BuffEffectService();
		buffService.clearBuffEffect(npc.getID(), BuffSystem.NPC);

		if (npc.getNpcRefurbishTime() != 0)// ���npc��ˢ������ȴʱ�����
		{
			PartInfoVO infovo = new PartInfoVO();
			RefurbishService refurbishService = new RefurbishService();
			infovo.setPName(roleInfo.getBasicInfo().getName());
			infovo.setPPk(p_pk);
			refurbishService.updateTimeOfNPCDead(infovo, npc, npc.getNpcID(),
					Integer.valueOf(mapid), 2);
		}

		return result;
	}

	/**
	 * �õ���ɫ��Ӧ��,���ڹ���״̬������npc
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<NpcFighter> getAttackNPCs(int p_pk, String scene_id)
	{
		List<NpcFighter> list = new ArrayList<NpcFighter>();
//		List<NpcAttackVO> attackvolist = null;
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//		list = npcAttackDao.getAttackNPCs(p_pk,scene_id);
		
		AttacckCache attacckCache = new AttacckCache();
		
		list = attacckCache.getZDAttackNpc(p_pk,AttacckCache.ZDATTACKNPC );
		logger.info("��"+list.size()+"������������");
		
		if (list == null || list.size() == 0 ) {
			NpcFighter npcFighter = (NpcFighter) attacckCache.getNpcWithSceneByPPkd(scene_id);
			if (npcFighter != null) {
				list.add(npcFighter);		
			}
		} 
		
		BuffEffectService buffEffectService = new BuffEffectService(); 
		
		//����npc��buffЧ��
		if( list!=null )
		{
			for( int i= list.size()-1;i>=0;i-- )
			{
				NpcFighter npc = list.get(i);
				if ( npc != null) {
    				npc.setPlayerInjure(0);
    				npc.setPetInjure(0);
    				buffEffectService.loadBuffEffectOfNPC(npc);
				} else {
					list.remove(npc);
				}
			}
		}
		return list;
	}

	/**
	 * ����ʱ�����ж��Ƿ��д��ڹ���״̬��npc
	 * 
	 * @param p_pk
	 * @return
	 */
	public boolean isHaveAttackNPC(RoleEntity roleInfo)
	{
		if( roleInfo==null )
		{
			return false;	
		}
		
		AttacckCache attacckCache = new AttacckCache();
		Object[] attackNpc = attacckCache.getByPPkd(roleInfo.getBasicInfo().getPPk());
		
		
		if (attackNpc == null || ((List)attackNpc[0]).size() == 0 ) {
			return false;
		} else {
			if(shiFouYouBiGuaiBUFF(roleInfo,attackNpc)){
				return false;
			}
			logger.debug("�м���������npc="+((List)attackNpc[0]).size());
			return true;
		}
		
	}

	/**
	 * �õ�һ����ǰս����npc
	 * 
	 * @param p_pk
	 * @return
	 */
	public NpcAttackVO getOneAttackNPCByPpk(int p_pk, int scene_id)
	{
//		NpcAttackDao npcAttackDao = new NpcAttackDao();
//		return npcAttackDao.getOneAttackNPCByPpk(p_pk, scene_id);
		
		List<NpcFighter> npcs = null;
		AttacckCache attacckCache = new AttacckCache();
		npcs = attacckCache.getZDAttackNpc(p_pk, AttacckCache.ZDATTACKNPC);
		if(npcs == null && npcs.size() == 0){
			return null;
		}
		return npcs.get(0);
		
	}

	/**
	 * �õ���ɫ��Ӧ��ˢ������npcs
	 * 
	 * @param p_pk
	 * @return
	 */
	public List<NpcFighter> getCurrentNPCs(int p_pk, int scene_id)
	{
		List<NpcFighter> npcs = null;
		
		AttacckCache attacckCache = new AttacckCache();
		npcs = attacckCache.getZDAttackNpc(p_pk, AttacckCache.BDATTACKNPC);
		
		return npcs;

	}

	/**
	 * �ж��Ƿ�������������npc
	 * 
	 * @param p_pk
	 * @return
	 *//*
	public NpcAttackVO isInitiativeAttackNPC(int p_pk)
	{
		NpcAttackDao npcAttackDao = new NpcAttackDao();
		return npcAttackDao.isInitiativeAttackNPC(p_pk);
	}*/

	/**
	 * npc���侭���Ǯ ʵ�ʵ��侭�� = npc���侭�� * (1 �C (��ҵȼ�-npc�ȼ�)*0.1)��
	 * 
	 * @param npc
	 */
	public void dropExpMoney(NpcAttackVO npc, PartInfoVO player)
	{
		if (npc == null)
		{
			logger.debug("npcΪ��");
			return;
		}

		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk()+"");
		
		DropExpMoneyVO dropExpMoney = new DropExpMoneyVO();

		int npc_exp = npc.getExp();
		int player_grade = player.getPGrade();
		int npc_level = npc.getLevel();
		double drop_exp = npc_exp;
		if( npc_level < player_grade )//npc�ĵȼ�С����ҵĵȼ�ʱ���о���˥�����������npc��ԭʼ����
		{
			drop_exp = npc_exp * (1 - (player_grade - npc_level) * 0.1);
			if (drop_exp < 0)
			{
				drop_exp = 0;
			}
		}
		
		PetInfoDAO dao = new PetInfoDAO();
		PetInfoVO petvo = dao.getPetInfo(player.getPPk() + "");
		double petdropExp = 0;
		if (petvo != null && petvo.getPPk() != 0)
		{
			int petlevel = petvo.getPetGrade();
			petdropExp = npc_exp * (1 - (petlevel - npc_level) * 0.1) * 0.2;

		}
		if (petdropExp < 0)
		{
			petdropExp = 0;
		}

		dropExpMoney.setDropExp((int) drop_exp);
		dropExpMoney.setDropMoney(npc.getDropMoney());
		dropExpMoney.setDropPetExp((int) petdropExp);

		role_info.getDropSet().addExpAndMoney(dropExpMoney);

	}


	/**
	 * npc�õ��Լ�����������
	 */
	public void loadNPCWx(NpcAttackVO npc)
	{
		if (npc == null)
		{
			logger.debug("��Чnpc");
			return;
		}
		
		if ( npc.getNpcType() == NpcAttackVO.CITYDOOR) {
			return ;
		}
		NpcskilDao npcSkillDao = new NpcskilDao();
		npcSkillDao.loadNPCWx(npc);
	}

	/**
	 * ����һ��npc������
	 * @param player
	 * @param npc
	 */
	public void processDeadOfOneNpc(PartInfoVO player, NpcAttackVO npc)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(player.getPPk());
		
		RefurbishService refurbishService = new RefurbishService();
		
		//��npc�Ƿ���boss
		boolean is_boss = refurbishService.isboss(npc.getSceneId(), npc.getNpcID());
		
		
		// ���npc��ˢ������ȴʱ�����
		if (npc.getNpcRefurbishTime() != 0)
		{
			refurbishService.updateTimeOfNPCDead(player, npc, npc.getNpcID(),Integer.parseInt(player.getPMap()), 1);
		}
		// ���侭���Ǯ
		dropExpMoney(npc, player);
		// �Ƿ������Ʒ
		if (getNpcIsDropGoods(player, npc))
		{
			//ֻ��ɱ�����һ����ʱ�ŵ���
			dropGoodsByNpc(npc.getNpcID(), player);
		}

		//�������ֵ����
		if( npc.getLevel()>=role_info.getGrade())
		{
			if( is_boss )
			{
				role_info.getBasicInfo().addEvilValue(-5);
			}
			else
			{
				role_info.getCounter().addKillNpcNum();
				if( role_info.getCounter().getKillNpcNum()%20==0 )
				{
					role_info.getBasicInfo().addEvilValue(-1);
				}
			}
		}
		
		//************************************����ͳ����
		RoomService roomService = new RoomService();
		//�ж�NPC�Ƿ��ڸ�����
		boolean is_in_instance = roomService.getMapType(npc.getSceneId())==MapType.INSTANCE?true:false;
		if( is_in_instance && is_boss)
		{//ɱ��BOSS
			//ͳ����Ҫ
			new RankService().updateAdd(player.getPPk(), "killboss", (npc.getLevel()*2));
		}else if( is_in_instance && is_boss == false)
		{//ɱ�ĸ���С��
			//ͳ����Ҫ
			new RankService().updateAdd(player.getPPk(), "killnpc", (npc.getLevel() * 2));
		}else if( is_in_instance && is_boss)
		{//���ٸ�����ɱ��BOSS
			//ͳ����Ҫ
			new RankService().updateAdd(player.getPPk(), "killboss", npc.getLevel());
		}else
		{ 
			//ͳ����Ҫ
			new RankService().updateAdd(player.getPPk(), "killnpc", npc.getLevel());
		}
		// �ж��Ƿ�������npc,������
		processTaskNPC(npc,player);
	}


	/*// �������npc�ǳ�����, �ʹ��������npc������,����ս����������
	private void processCITYDOOR(NpcAttackVO npc, PartInfoVO player)
	{
		int sceneId = npc.getSceneId();
		RoomService roomService = new RoomService();
		MapVO mapVO = roomService.getMapInfoBySceneId(sceneId+"");
		
		SystemInfoService systemInfoService = new SystemInfoService();
		OperateMenuDao menuDao = new OperateMenuDao();
		OperateMenuVO menuVO = menuDao.getOperateMenuVOBySceneAndType(sceneId, MenuType.CITYDOOR);
		TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
		TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(mapVO.getMapID());
		TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService.getSiegeTongInfo(""+tongSiegeBattleVO.getSiegeId());
		
		// ����Ϊ1�ǳ���,2��Ӣ�۵���
		// ���ű����ƺ�,ս����״̬��Ϊ2
		if (tongSiegeControlVO.getNowPhase() < FinalNumber.THIRD) {
			TongSiegeBattleDao tongSiegeBattleDao = new TongSiegeBattleDao();
			tongSiegeBattleDao.updateTongSiegeNowPhase(Integer.parseInt(menuVO.getMenuOperate1()), FinalNumber.THIRD);
			String infoString = tongSiegeBattleVO.getSiegeName()+"�ĳ��ű�������!";
			systemInfoService.insertSystemInfoByBarea(infoString, mapVO.getMapFrom());
		}
			

	}*/
	
	
/*	// �������npc�ǵ�����, �ʹ��������npc������,����ս����������
	private void processDIAOXIANG(NpcAttackVO npc, PartInfoVO player)
	{
		int sceneId = npc.getSceneId();
		RoomService roomService = new RoomService();
		MapVO mapVO = roomService.getMapInfoBySceneId(sceneId+"");
		
		SystemInfoService systemInfoService = new SystemInfoService();
		OperateMenuDao menuDao = new OperateMenuDao();
		OperateMenuVO menuVO = menuDao.getOperateMenuVOBySceneAndType(sceneId, MenuType.DIAOXIANG);
		TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
		TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(mapVO.getMapID());
		
		// Ӣ�۵��񱻴��ƺ�, ����ս��һ�׶ν���
		tongSiegeBattleService.firstGradeEnd(player.getPPk(),npc.getSceneId(),Integer.parseInt(menuVO.getMenuOperate1()),menuVO.getMenuOperate3());
		 
		String infoString = tongSiegeBattleVO.getSiegeName()+"��Ӣ�۵��񱻴�����!���ǽ���ڶ��׶�";
		systemInfoService.insertSystemInfoByBarea(infoString, mapVO.getMapFrom());
		
	}*/

	/*// �������npc�������, �ʹ������npc��������˲˵�����Ӫ������
	private void processMast(NpcAttackVO npc, PartInfoVO player)
	{
		FieldService fieldService = new FieldService();
		fieldService.dealMast(npc, player.getPCamp());

		SystemInfoService systemInfoService = new SystemInfoService();

		StringBuffer systemInfo = new StringBuffer();

		RoomService roomService = new RoomService();
		String sceneName = roomService.getName(npc.getSceneId());
		
		systemInfo.append("λ��").append(sceneName).append("����˱�");

		if (player.getPCamp() == 1)
		{
			systemInfo.append("����").append("�����ɹ�,ת���Ϊ����!");
		}
		else
		{
			systemInfo.append("а��").append("�����ɹ���,ת���Ϊа��!");
		}

		systemInfoService.insertSystemInfoByBarea(systemInfo.toString(),
				FinalNumber.FIELDBAREA);
		
	}
	*/
	


	/**
	 * �жϴ�npc�Ƿ���Ҫ������Ʒ,�жϴ˵��Ƿ���������ͬ�������������͹�, ����оͲ�������Ʒ.
	 * 
	 * @param player
	 * @param npc
	 * @return
	 */
	private boolean getNpcIsDropGoods(PartInfoVO player, NpcAttackVO npc)
	{
		boolean flag = true;
		
		AttacckCache attacckCache = new AttacckCache();
		List<NpcFighter> list = attacckCache.getZDAttackNpc(player.getPPk(),  AttacckCache.ZDATTACKNPC);
		
		
		if(list != null && list.size() > 1) {
			flag = false;
		}		
		
		if (flag)
		{
			logger.info("��npc������Ʒ");
		}
		else
		{
			logger.info("��npc��������Ʒ");
		}
		return flag;
	}

	/**
	 * ����npcѪ��
	 * 
	 * @param player
	 * @param npc
	 * @param injure
	 * @param murderer_type
	 *            ��������(��ң�����)
	 */
	public void updateNPCCurrentHP(Fighter player, NpcFighter npc, int injure,
			int murderer_type)
	{
		RoleEntity roleinfo = RoleService.getRoleInfoById(player.getPPk()+"");
		if (player == null || npc == null)
		{
			logger.debug("��������");
			return;
		}
		
		// ͳ����ҶԴ�npc���˺�ֵ
		if(npc.getNpcType() == NpcAttackVO.NIANSHOU){
			
		}else{
			recordPlayerInjure(player,npc,injure);
			if (murderer_type == PETINJURE)
			{
				// ȥ������������������˺� 
				npc.setPetInjure(injure);
			}
			else
				if (murderer_type == PLAYERINJURE)
				{
					// ȥ������������������˺� 
					npc.setPlayerInjure(injure);
				}
		}
		
		if (npc.isDead())
		{
			
			// npc��������  
			if (npc.getNpcType() == NpcAttackVO.DEADNPC)
			{
				TaskPageService taskPageService = new TaskPageService();
				player.appendKillDisplay(StringUtil.isoToGBK(npc.getNpcName()) + ":"+npc.getCurrentHP()+"/"+npc.getNpcHP()+"<br/>");
				player.setTask_display(taskPageService.getGeiDJService(npc.getNpcID(), StringUtil.isoToGBK(npc.getNpcName()),player.getPPk()));
			}
			else
				if (npc.getNpcType() == NpcAttackVO.LOSENPC)
				{
					player.appendKillDisplay(player.getKillDisplay() + "�������"
							+ StringUtil.isoToGBK(npc.getNpcName()) + "<br/>");
				}
				else
					if (npc.getNpcType() == NpcAttackVO.MAST)
					{
						player.appendKillDisplay("��������˳ɹ�,���ڴ����˳����Ϊ����Ӫ����!<br/>");

					}
				else
					if (npc.getNpcType() == NpcAttackVO.CITYDOOR)
					{
						player.appendKillDisplay("���������ųɹ�,���������Ѿ�����!<br/>");
					}
				else
					if (npc.getNpcType() == NpcAttackVO.ZHAOHUN)
					{
						player.appendKillDisplay("�������л�ᦳɹ�,���ڴ��л���Ѿ���Ϊ����ɵ���!<br/>");
					}
				else
					if (npc.getNpcType() == NpcAttackVO.NIANSHOU)
					{
						getNpcByNianshouDead(npc, "��"+player.getPName()+"ɱ����!");
					}
				else
					if (npc.getNpcType() == NpcType.MENPAINPC)
					{
						//�������NPC���е�����
						int menuid = roleinfo.getBasicInfo().getMenpainpcstate();
						if(menuid != 0){
							MenuService ms = new MenuService();
							OperateMenuVO vo = new OperateMenuVO();
							vo = ms.getMenuById(menuid);
							vo.setMenuOperate4(roleinfo.getBasicInfo().getMenpainpcid());
							MenuCache menuCache = new MenuCache();
							menuCache.reloadOneMenu(vo);
							vo = ms.getMenuById(menuid);
						}
						Constant.MENPAINPC.put(1, 0);
						roleinfo.getBasicInfo().updateSceneId(210+"");
						roleinfo.getBasicInfo().setShouldScene(210);
					}
			// �����npcˢ��ʱ�䲻Ϊ0����npc���������ϵͳ��Ϣ���ͳ�ȥ
			// if(npc.getNpcRefurbishTime() > 0){
			// sendSystemByNpcDead(player,npc,npc.getNpcRefurbishTime());
			// }

			// ����һ��npc������
			processDeadOfOneNpc(player, npc);
			if(npc!=null&&player!=null){
			if(npc.getLevel()>=player.getPGrade()){
				MyService my = new MyServiceImpl();
				my.addDear(player.getPPk(),player.getPName());
			}
			}
			if(roleinfo.getBasicInfo().getTianguan_npc().indexOf(npc.getNpcID()+"") != -1){
				roleinfo.getBasicInfo().setTianguan_kill_num(roleinfo.getBasicInfo().getTianguan_kill_num()+1);
			}
		}
		else
		// û����������Ѫ��
		{
			getNpcByNianshouAttack(npc, injure,murderer_type);
				if (npc.isDead())
				{
					
					// npc��������  
					if (npc.getNpcType() == NpcAttackVO.DEADNPC)
					{
						TaskPageService taskPageService = new TaskPageService();
						player.appendKillDisplay(player.getKillDisplay() + StringUtil.isoToGBK(npc.getNpcName()) + ":"+npc.getCurrentHP()+"/"+npc.getNpcHP()+"<br/>");
						player.setTask_display(taskPageService.getGeiDJService(npc.getNpcID(), StringUtil.isoToGBK(npc.getNpcName()),player.getPPk()));
					}
					else
						if (npc.getNpcType() == NpcAttackVO.LOSENPC)
						{
							player.appendKillDisplay(player.getKillDisplay() + "�������"
									+ StringUtil.isoToGBK(npc.getNpcName()) + "<br/>");
						}
						else
							if (npc.getNpcType() == NpcAttackVO.MAST)
							{
								player.appendKillDisplay("��������˳ɹ�,���ڴ����˳����Ϊ����Ӫ����!<br/>");

							}
						else
							if (npc.getNpcType() == NpcAttackVO.CITYDOOR)
							{
								player.appendKillDisplay("���������ųɹ�,���������Ѿ�����!<br/>");
							}
						else
							if (npc.getNpcType() == NpcAttackVO.ZHAOHUN)
							{
								player.appendKillDisplay("�������л�ᦳɹ�,���ڴ��л���Ѿ���Ϊ����ɵ���!<br/>");
							}
						else
							if (npc.getNpcType() == NpcAttackVO.NIANSHOU)
							{
								getNpcByNianshouDead(npc, "��"+player.getPName()+"ɱ����!");
							}
						else
							if (npc.getNpcType() == NpcType.MENPAINPC)
							{
								//�������NPC���е�����
								int menuid = roleinfo.getBasicInfo().getMenpainpcstate();
								if(menuid != 0){
									MenuService ms = new MenuService();
									OperateMenuVO vo = new OperateMenuVO();
									vo = ms.getMenuById(menuid);
									vo.setMenuOperate4(roleinfo.getBasicInfo().getMenpainpcid());
									MenuCache menuCache = new MenuCache();
									menuCache.reloadOneMenu(vo);
									vo = ms.getMenuById(menuid);
								}
								roleinfo.getBasicInfo().updateSceneId(210+"");
								roleinfo.getBasicInfo().setShouldScene(210);
								Constant.MENPAINPC.put(1, 0);
							}

					// ����һ��npc������
					processDeadOfOneNpc(player, npc);
					if(npc!=null&&player!=null){
					if(npc.getLevel()>=player.getPGrade()){
						MyService my = new MyServiceImpl();
						my.addDear(player.getPPk(),player.getPName());
					}
					}
			}
		}
	}

	
	/**
	 * ��¼�� ��ҶԴ�npc���˺��� ��ֵ
	 * @param player
	 * @param npc
	 * @param injure
	 */
	private void recordPlayerInjure(Fighter player, NpcFighter npc, int injure)
	{
		RecordDao recordDao = new RecordDao();
		
		int recordValue = injure;
		if ( npc.getCurrentHP() < injure) {
			recordValue = npc.getCurrentHP();
		}
		
		RoleEntity roleEntity = RoleService.getRoleInfoById(player.getPPk()+"");
		
		// ��¼��������
		if ( npc.getNpcType() == NpcAttackVO.DIAOXIANG) {
			// 
			if ( roleEntity.getBasicInfo().getFaction()!=null) {
				recordDao.updateInjure(roleEntity.getBasicInfo().getFaction().getId(),recordValue,npc.getNpcID(),npc.getNpcType());
			}
		}
	}
	
	

	// ��npc���������ϵͳ��Ϣ���ͳ�ȥ
	public void sendSysInfoOfNpcDead(PartInfoVO player, NpcAttackVO npc,
			int npcRefurbishTime)
	{
		SystemInfoService systemInfoService = new SystemInfoService();
		RoomService roomService = new RoomService();
		String sceneName = roomService.getName(npc.getSceneId());
		String sceneStr = StringUtil.gbToISO(sceneName);

		logger.info("bossˢ�µ�=" + npc.getSceneId());
		DateFormat df = new SimpleDateFormat("HH:mm");
		// ����ϵͳ��Ϣ�������, ĳboss�Ѿ������ĳĳɱ��
		StringBuffer sb = new StringBuffer();
		sb.append(df.format(new Date())).append(StringUtil.gbToISO("��,"))
				.append(npc.getNpcName()).append(StringUtil.gbToISO("��"))
				.append(sceneStr).append(StringUtil.gbToISO("��")).append(
						player.getPName()).append(StringUtil.gbToISO("ɱ����"));
		systemInfoService.insertSystemInfoBySystem(sb.toString());
		// ����ϵͳ��֪���, boss��������Ӻ�ˢ��
		StringBuffer sb1 = new StringBuffer();
		sb1.append(npc.getNpcName()).append(StringUtil.gbToISO("��������Ӻ���"))
				.append(sceneStr).append(StringUtil.gbToISO("����"));
		systemInfoService.insertSystemInfoBySystem(sb1.toString(), npc
				.getNpcRefurbishTime() - 5);
		// ����ϵͳ��Ϣ��֪��� ,boss�Ѿ���ʼˢ��
		StringBuffer sb2 = new StringBuffer();
		sb2.append(npc.getNpcName()).append(StringUtil.gbToISO("��")).append(
				sceneStr).append(StringUtil.gbToISO("���ڳ����ˣ�"));
		systemInfoService.insertSystemInfoBySystem(sb2.toString(), npc
				.getNpcRefurbishTime());

	}

	// ��npc����������ϵͳ��Ϣ���ͳ�ȥ
	public void sendSysInfoOfCatch(int pPk, NpcAttackVO npc)
	{
		RoleEntity  roleInfo = RoleService.getRoleInfoById(pPk+"");
		
		SystemInfoService systemInfoService = new SystemInfoService();
		RoomService roomService = new RoomService();
		String sceneName = roomService.getName(npc.getSceneId());
		String sceneStr = StringUtil.gbToISO(sceneName);
		String pName = roleInfo.getBasicInfo().getName();
		// logger.info("bossˢ�µ�="+npc.getSceneId());
		DateFormat df = new SimpleDateFormat("HH:mm");
		// ����ϵͳ��Ϣ�������, ĳboss�Ѿ������ĳĳ����
		StringBuffer sb = new StringBuffer();
		sb.append(df.format(new Date())).append(StringUtil.gbToISO("��,"))
				.append(npc.getNpcName()).append(StringUtil.gbToISO("��"))
				.append(sceneStr).append(StringUtil.gbToISO("��")).append(pName)
				.append(StringUtil.gbToISO("��׽��"));
		systemInfoService.insertSystemInfoBySystem(sb.toString());
		// ����ϵͳ��֪���, boss��������Ӻ�ˢ��
		StringBuffer sb1 = new StringBuffer();
		sb1.append(npc.getNpcName()).append(StringUtil.gbToISO("��������Ӻ���"))
				.append(sceneStr).append(StringUtil.gbToISO("����"));
		systemInfoService.insertSystemInfoBySystem(sb1.toString(), npc
				.getNpcRefurbishTime() - 5);
		// ����ϵͳ��Ϣ��֪��� ,boss�Ѿ���ʼˢ��
		StringBuffer sb2 = new StringBuffer();
		sb2.append(npc.getNpcName()).append(StringUtil.gbToISO("��")).append(
				sceneStr).append(StringUtil.gbToISO("���ڳ����ˣ�"));
		systemInfoService.insertSystemInfoBySystem(sb2.toString(), npc
				.getNpcRefurbishTime());

	}

	/**
	 * �жϵ�ǰ������npc�Ƿ�����npc�����������npc�򰴸��������������
	 */
	private void processTaskNPC(NpcAttackVO npc,PartInfoVO player)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(player.getPPk()+""); 
		
		// ��ȡNPCID
		int npcid = npc.getNpcID();
		String tType = "2";
		UTaskDAO dao = new UTaskDAO();
		List<UTaskVO> list = dao.getUTaskNpcId(npc.getPPk() + "", npcid + "",tType);
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				UTaskVO vo = list.get(i);
				CurTaskInfo curTaskInfo = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getById(vo.getTPk()+"");
				curTaskInfo.updateKillingOk(1);
				logger.info("ע��: ɱ������ж����� ::ɱ������Ϊ:: " + vo.getTKillingNo());
			}
		}
	}

	/**
	 * �жϵ�ǰ�ص��npc�Ƿ�����
	 * 
	 * @param npc_id
	 * @param scene_id
	 */
	public boolean isDead(int npc_id, int scene_id)
	{
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		return npcrefurbishDao.isDead(npc_id, scene_id);
	}

	/**
	 * ���º����ս����npcs����״̬�Ļغ���
	 * 
	 * @param p_pk
	 * @param dizzy_bout_num
	 */
	public void addDizzyBoutNumOfNPCs(int p_pk, int dizzy_bout_num)
	{
		//��ʱû�жԻ���Ч��������
		/*NpcAttackDao npcAttackDao = new NpcAttackDao();
		npcAttackDao.addDizzyBoutNumOfNPCs(p_pk, dizzy_bout_num);*/
	}

	/**
	 * ���»���״̬ʣ��غ���
	 * 
	 * @param p_pk
	 * @param change_bout
	 */
	public void updateDizzyBoutNumOfNPC(int n_pk, int change_bout)
	{
		//��ʱû�жԻ���Ч��������
		/*NpcAttackDao npcAttackDao = new NpcAttackDao();
		npcAttackDao.updateDizzyBoutNumOfNPC(n_pk, change_bout);*/
	}
	
	
	/*
	 * ���º����ս����npcs�ж�״̬�Ļغ���
	 */
	public void addPoisonBoutNumOfNPCs(int p_pk,int poison_bout_num)
	{	
		NpcAttackDao npcAttavkDao=new NpcAttackDao();
		npcAttavkDao.addPoisonBoutNumOfNPCs(p_pk, poison_bout_num);
	}
	
	/*
	 * �����ж�״̬�غ���
	 */
	public void updatePoisonBoutNumOfNPC(int n_pk, int change_bout)
	{
		NpcAttackDao npcAttackDao = new NpcAttackDao();
		npcAttackDao.updatePoisonBoutNumOfNPC(n_pk, change_bout);
	}
	

	/**
	 * �õ�NPC�����Թ�������ʾ
	 */
	public String getWxNum(int npc_id)
	{
		String out = null;
		NpcskilDao dao = new NpcskilDao();
		List<NpcskillVO> list = dao.getWxNum(npc_id);
		if ( list == null || list.size() == 0) {
			out = "�����й���";
			return out;
		}
		NpcskillVO vo1 = list.get(0);
		NpcskillVO vo2 = list.get(list.size() - 1);
		if (vo1.getNpcskiWx() != 0)
		{
			if (vo1.getNpcskiWx() == 1)
			{
				out = "��" + vo1.getNpcskiWxInjure() + "-"
						+ vo2.getNpcskiWxInjure();
				return out;
			}
			if (vo1.getNpcskiWx() == 2)
			{
				out = "ľ" + vo1.getNpcskiWxInjure() + "-"
						+ vo2.getNpcskiWxInjure();
				return out;
			}
			if (vo1.getNpcskiWx() == 3)
			{
				out = "ˮ" + vo1.getNpcskiWxInjure() + "-"
						+ vo2.getNpcskiWxInjure();
				return out;
			}
			if (vo1.getNpcskiWx() == 4)
			{
				out = "��" + vo1.getNpcskiWxInjure() + "-"
						+ vo2.getNpcskiWxInjure();
				return out;
			}
			if (vo1.getNpcskiWx() == 5)
			{
				out = "��" + vo1.getNpcskiWxInjure() + "-"
						+ vo2.getNpcskiWxInjure();
				return out;
			}
			if (vo1.getNpcskiWx() == 6)
			{
				out = "��???";
				return out;
			}
		}
		else
		{
			out = "�����й���";
		}
		return out;
	}
	
	 
	/**
	 * ɾ����npc�������ĸ�������
	 * @param p_pk
	 */
	public void deleteByPpk(int p_pk) {
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		role_info.getDropSet().clearDropItem();
	}

	/**
	 * �鿴�Ƿ���Ҫ���� �����ս����ҳ��
	 * @param roleInfo
	 * @param npcFighter
	 * @return
	 *//*
	public boolean getIsJoinFight(RoleEntity roleInfo, NpcFighter npcFighter)
	{
		// �����npc����,�ǾͲ��ý���ս����
		if ( npcFighter.isDead()) {
			return false;
		}
		if( npcFighter.getNpcType() == NpcAttackVO.CITYDOOR) {
			SceneVO sceneVO = SceneCache.getById(""+roleInfo.getBasicInfo().getSceneId());
			
			MapVO mapVO = MapCache.getById(sceneVO.getSceneMapqy());
	
			// ����mapID��ȷ������ս����ID
			TongSiegeBattleService tongSiegeBattleService = new TongSiegeBattleService();
			TongSiegeBattleVO tongSiegeBattleVO = tongSiegeBattleService.getSiegeByMapId(mapVO.getMapID());
					
			// ����ս��ID��ppk��ȷ����һ�ε�ս�����
			TongSiegeBattleDao tongSiegeBattleDao = new TongSiegeBattleDao();
			int siegeFightNumber = tongSiegeBattleDao.getPreiousFightNumber(tongSiegeBattleVO.getSiegeId()+"");
			
			TongSiegeInfoService tongSiegeInfoService = new TongSiegeInfoService();
			TongSiegeInfoVO tongSiegeInfoVO = tongSiegeInfoService.getPersonInfo(roleInfo.getBasicInfo().getPPk(),
										tongSiegeBattleVO.getSiegeId()+"",(1+siegeFightNumber));
			TongSiegeControlVO tongSiegeControlVO = tongSiegeBattleService.getSiegeTongInfo(tongSiegeBattleVO.getSiegeId()+"");
			
			// ���Ϊ���Ƿ�,����ս��
			if ( tongSiegeInfoVO.getJoinType() == FinalNumber.ATTACKBATTLE) {
				String startTimeString = tongSiegeControlVO.getSiegeStartTime();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startDate = null;
				Date nowDate = new Date();
				try
				{
					startDate = df.parse(startTimeString);
				}
				catch (ParseException e1)
				{
					e1.printStackTrace();
				}
				
				if ( nowDate.getTime() >= startDate.getTime() ) {
					return true;
				}
			}
		}
		
		return false;
	}*/
	/**
	 * �Ƿ��бܹ�BUFF
	 * @param p_pk
	 * @return
	 */
	public boolean shiFouYouBiGuaiBUFF(RoleEntity roleInfo,Object[] attackNpc){
		//���������NPC ���жϱܹ�BUFF�Ƿ���
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		//�õ��ֵܹ��ߵ�BUFF
		BuffEffectVO buffEffect = buffEffectDao.hasAlreadyBuff(roleInfo.getBasicInfo().getPPk(),BuffSystem.PLAYER, BuffType.BIGUAIDAOJUBUFF+"");
		
		//�����ж��Ƿ��ڸ�����ֵܹ��߲�������
		RoomService roomService = new RoomService();
		if(roomService.getMapType(Integer.parseInt(roleInfo.getBasicInfo().getSceneId())) == MapType.INSTANCE){
			return false;
		}
		//�õ���ǰˢ�µ����������Ĺ�
		List<NpcFighter> npcs = (List<NpcFighter>)attackNpc[0] ;
		for(int i = 0 ; i < npcs.size() ; i++){
			NpcAttackVO npc = (NpcAttackVO) npcs.get(i);
			//�����ǰĳ��NPC�Ĺ���ȼ�������ҵĵȼ� ����TRUE ��ˢ�¹���
			if(npc.getLevel() > roleInfo.getBasicInfo().getGrade()){
				return false;
			}
		}
		if(buffEffect != null  ) {
			//�������������
			PlayerService playerService = new PlayerService();
			playerService.clearTempData(roleInfo.getBasicInfo().getPPk(), "zd");
			return true;
		}else{
			return false;
		}
	}
	
	public void getNpcByNianshouAttack(NpcFighter npc,int attack,int p_p){
		if(npc.getNpcType() == NpcAttackVO.NIANSHOU){
			int attack_bak = attack * MathUtil.getRandomBetweenXY(20, 50)/100;
			NpcFighter npc_bak = Constant.PETNPC.get(npc.getNpcID());
			if(p_p == 1){
				npc_bak.setNpccountnum(attack_bak);
				npc.setNpccountnum(attack_bak);
				npc_bak.setPlayerInjure(attack_bak);
				npc.setPetInjure(attack_bak);
				npc_bak.setPetInjure(0);
				npc.setPetInjure(0);
				if(npc_bak.getCurrentHP() < 0){
					npc_bak.setPlayerInjure(attack_bak);
				}
			}
			if(p_p == 2){
				npc_bak.setPetInjure(attack_bak);
				npc.setPetInjure(attack_bak);
				npc_bak.setPetInjureOut("-" + attack_bak);
				npc.setPetInjureOut("-" + attack_bak);
				if(npc_bak.getCurrentHP() < 0){
					npc_bak.setPetInjure(attack_bak);
				}
			}
			npc_bak.setCurrentHP(npc_bak.getCurrentHP() - attack_bak);
			Constant.PETNPC.put(npc.getNpcID(),npc_bak);
		}else{
			npc.setCurrentHP(npc.getCurrentHP() - attack);
		}
	}
	public void getNpcByNianshouBuzhuo(int npc_id,String display){
		NpcFighter npc = Constant.PETNPC.get(npc_id);
		npc.setNpcdeaddisplay(npc.getNpcName()+display);
		SystemInfoService systemInfoService = new SystemInfoService();
		systemInfoService.insertSystemInfoBySystem(npc.getNpcName()+display);
		npc = Constant.PETNPC.put(npc_id,npc);
	}
	public void getNpcByNianshouDead(NpcFighter npc,String display){
		npc = Constant.PETNPC.get(npc.getNpcID());
		npc.setNpcdeaddisplay(npc.getNpcName()+display);
		SystemInfoService systemInfoService = new SystemInfoService();
		systemInfoService.insertSystemInfoBySystem(npc.getNpcName()+display);
		npc = Constant.PETNPC.put(npc.getNpcID(),npc);
	}
	public boolean getCatchPetByNpc(String trem ,int npc_id){
		String[] trem_bak = trem.split(",");
		for(int i = 1;i<trem_bak.length;i++){
			try
			{
				if(Integer.parseInt(trem_bak[i]) == npc_id){
					return true;
				}
			}
			catch (Exception e)
			{
				return false;
			}
		}
		return false;
	}
	
}
