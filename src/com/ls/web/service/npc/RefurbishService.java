package com.ls.web.service.npc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.cache.dynamic.manual.attack.AttacckCache;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.dao.info.npc.NpcAttackDao;
import com.ls.ben.dao.info.npc.NpcDeadRecordDao;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.npc.NpcDeadRecordVO;
import com.ls.ben.vo.info.npc.NpcFighter;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcrefurbishVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.instance.InstanceService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.room.RoomService;

public class RefurbishService
{
	Logger logger = Logger.getLogger("log.service");

	/**
	 * ���ݵص�sence_idˢ��npc
	 * @param sence_id
	 * @return
	 */
	public List<NpcFighter> refurbishNPC(int p_pk, int scene_id)
	{

		PlayerService playerService = new PlayerService();
		playerService.clearTempData(p_pk,"all");

		List<NpcFighter> npcs = new ArrayList<NpcFighter>();
		
		
		NpcVO npc = null;
		NpcrefurbishVO npcrefurbishVO = null;

		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		List<NpcrefurbishVO> npcrefurbishs = npcrefurbishDao.getBySenceId(scene_id);

		// ����npcˢ�±���npc
		for (int i = 0; i < npcrefurbishs.size(); i++)
		{
			npcrefurbishVO = npcrefurbishs.get(i);
			/*****����С�ֵ�ˢ��ʱ����ע�͵�***/
			//reLoadRefurbishInfo(p_pk, scene_id, npcrefurbishVO);
			npc = NpcCache.getById(npcrefurbishVO.getNpcId());

			if (isRefurbished(p_pk, scene_id, npcrefurbishVO, npc) == true)// �ж��Ƿ�ˢ��
			{
				for (int j = 0; j < npcrefurbishVO.getRandomNum(); j++)
				{
					npcs.add(createNPCByRefurbish(npc, npcrefurbishVO, p_pk));
				}
			}
		}
		
		// �����ڴ滺��
		insertByNPCs(npcs,p_pk);
		return npcs;
	}

	/**
	 * �ж��Ƿ�ˢ��
	 * 
	 * @param p_pk
	 * @param scene_id
	 * @param npcrefurbishVO
	 * @return
	 */
	private boolean isRefurbished(int p_pk, int scene_id,
			NpcrefurbishVO npcrefurbishVO, NpcVO npc)
	{
		if (npcrefurbishVO == null)
		{
			return false;
		}
		boolean result = false;
		
		//�ж�ˢ��ʱ��
		if( !DateUtil.isEffectTime(npcrefurbishVO.getTimeKs(), npcrefurbishVO.getDayTimeJs(), npcrefurbishVO.getTimeKs(), npcrefurbishVO.getTimeJs(),npcrefurbishVO.getWeekStr()))
		{
			return false;
		}
		
		if (npcrefurbishVO.getIsBoss() == 1)// �ж��Ƿ���boss
		{
			InstanceService instanceService = new InstanceService();
			if (instanceService.isHaveArchive(p_pk, scene_id) == true)// ���ݵص��жϸø�����boss�Ƿ�ɱ
			{
				return false;
			}
			else
			{
				SceneVO sceneVO = SceneCache.getById(scene_id+"");
				MapVO mapVO = sceneVO.getMap();
				if(mapVO.getMapType() == MapType.TIANGUAN){
					RoleEntity roleinfo = RoleCache.getByPpk(p_pk);
					if(roleinfo.getBasicInfo().getTianguan_npc().indexOf(npc.getNpcID()+"") != -1){
						if(roleinfo.getBasicInfo().getTianguan_kill_num()>0){
							return false;
						}
					}
					if(npc.getNpcID() == 4143){
						if(roleinfo.getBasicInfo().getTianguan_kill_num()>0){
							return false;
						}
					}
				}
				
			}
		}
			//��������ʱ���ж��Ƿ�ˢ��
			if (isRefurbishByDeadTime(npcrefurbishVO, npc) == true)
			{
				// �ж��Ƿ񰴸���ˢ��
				if (MathUtil.isAppearByPercentage(npcrefurbishVO.getProbability()))
				{
					return true;
				}
			}
		
		return result;
	}

	/**
	 * ��������ʱ���ж��Ƿ�ˢ��
	 * @param npcrefurbishVO
	 * @param npc
	 * @return
	 */
	private boolean isRefurbishByDeadTime(NpcrefurbishVO npcrefurbishVO,
			NpcVO npc)
	{
		boolean result = false;
		if (npc.getNpcRefurbishTime() == 0)// ��ˢ��ʱ�����
		{
			return true;
		}

		if (npcrefurbishVO.getIsDead() == 1)// NPC����
		{
			if (DateUtil.isOverdue(npcrefurbishVO.getDeadTime(), npc
					.getNpcRefurbishTime()
					* DateUtil.MINUTE))
			{
				// ������ȴʱ��
				reviveNPC(npc.getNpcID(), npcrefurbishVO.getSceneId());
				result = true;
			}
		}
		else
		{
			result = true;
		}

		return result;
	}

	/**
	 * ���ߵص㣬����npcˢ�µ���Ϣ
	 */
	private NpcrefurbishVO reLoadRefurbishInfo(int p_pk, int scene_id,
			NpcrefurbishVO npcrefurbishVO)
	{
		if (npcrefurbishVO == null)
		{
			logger.info("NPCˢ�£�npcrefurbishVO����Ϣ��Ч");
		}

		RoomService roomService = new RoomService();
		int map_type = roomService.getMapType(scene_id);

		if (npcrefurbishVO.getIsBoss() != 1 && map_type == MapType.INSTANCE)// ����Boss���Ǹ�������
		{
			GroupService groupService = new GroupService();
			NpcDeadRecordDao npcDeadRecordDao = new NpcDeadRecordDao();

			int caption_pk = groupService.getCaptionPk(p_pk);
			NpcDeadRecordVO npcDeadRecord = npcDeadRecordDao.getRecord(
					caption_pk, scene_id);

			if (npcDeadRecord != null)
			{
				npcrefurbishVO.setIsDead(1);
				npcrefurbishVO.setDeadTime(npcDeadRecord.getNpcDeadTime());
			}
			else
			{
				npcrefurbishVO.setIsDead(0);
			}
		}

		return npcrefurbishVO;
	}

	/**
	 * ����npc��ʱ��
	 */
	private void insertByNPCs(List<NpcFighter> npcs,int p_pk)
	{
		AttacckCache attacckCache = new AttacckCache();
		List<NpcFighter> list1 = attacckCache.getZDAttackNpc(p_pk, AttacckCache.BDATTACKNPC);
		if (list1 != null){
			list1.clear();		
		}
		list1 = null;	
		List<NpcFighter> list = attacckCache.getZDAttackNpc(p_pk, AttacckCache.ZDATTACKNPC);
		if (list != null){
			list.clear();		
		}
		list = null;	
		
		
		attacckCache.clearNpcTempData(p_pk+"");
		
		// ����Ϊ�����ڴ� 
		List<NpcFighter> zdNpcs = new ArrayList<NpcFighter>();
		for ( int i = npcs.size()-1;i>=0;i--) {
			NpcFighter npcAttackVO = npcs.get(i);
			npcAttackVO.setID(i);				// ��npcȷ��һ�����������Ķ���
			if (npcAttackVO.getNAttackswitch() == 1 ) {
				zdNpcs.add(npcAttackVO);
				npcs.remove(i);
			}		
		}		
				
		Object[] attack = new Object[2];
		
		attack[0] = zdNpcs;
		attack[1] = npcs;
				
		attacckCache.setByPPkd(p_pk, attack);
	}	


	/**
	 * ����ˢ�³�����npcʵ��
	 * 
	 * @param npc
	 * @param pPk
	 *            ��ɫid
	 * @return
	 */
	public NpcFighter createNPCByRefurbish(NpcVO npc,
			NpcrefurbishVO npcrefurbishVO, int p_pk)
	{
		NpcFighter temp_npc = new NpcFighter();
		// ����������������
		temp_npc.setNAttackswitch(npcrefurbishVO.getAttackswitch());
		temp_npc.setPPk(p_pk);
		temp_npc.setNpcID(npc.getNpcID());
		temp_npc.setNpcName(npc.getNpcName());
		temp_npc.setCurrentHP(npc.getNpcHP());
		temp_npc.setNpcHP(npc.getNpcHP());
		temp_npc.setDefenceDa(npc.getDefenceDa());
		temp_npc.setDefenceXiao(npc.getDefenceXiao());

		temp_npc.setJinFy(npc.getJinFy());
		temp_npc.setMuFy(npc.getMuFy());
		temp_npc.setShuiFy(npc.getShuiFy());
		temp_npc.setHuoFy(npc.getHuoFy());
		temp_npc.setTuFy(npc.getTuFy());

		temp_npc.setDrop(npc.getDrop());
		temp_npc.setLevel(npc.getLevel());
		temp_npc.setExp(npc.getExp());
		temp_npc.setMoney(npc.getMoney());
		temp_npc.setTake(npc.getTake());
		temp_npc.setNpcType(npc.getNpcType());
		temp_npc.setNpcRefurbishTime(npc.getNpcRefurbishTime());
		temp_npc.setSceneId(npcrefurbishVO.getSceneId());

		return temp_npc;
	}

	/**
	 * 
	 * ���������ַ���condition,������npc
	 * 
	 * @param npc
	 * @param pPk
	 *            ��ɫid
	 * @return
	 */
	public void createNPCByCondition(int p_pk, String condition, int npc_type,
			int mapId)
	{
		if (condition == null)
		{
			logger.info("����npc�����ַ���Ϊ��");
			return;
		}

		NpcVO npc = null;
		NpcAttackDao npcAttackDao = new NpcAttackDao();

		String npcs[] = condition.split("-");
		for (int j = 0; j < npcs.length; j++)
		{
			String temp[] = npcs[j].split(",");
			int npc_id = Integer.parseInt(temp[0]);
			int npc_num = Integer.parseInt(temp[1]);
			
			npc = NpcCache.getById(npc_id);
			for (int i = 0; i < npc_num; i++)
			{
				npcAttackDao.createByNpc(npc, p_pk, 1, npc_type, mapId,i);
			}
		}
	}
	
	/**
	 * ������ȴ��npc
	 */
	public void reviveNPC(int npc_id, int scene_id)
	{
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		npcrefurbishDao.reviveNPC(npc_id, scene_id);
	}

	/**
	 * �����ǰ��ɫˢ�³���npcs
	 * 
	 * @param p_pk
	 * @return
	 *//*
	public int deleteByPpk(int p_pk)
	{
		int result = -1;
		NpcAttackDao npcAttackDao = new NpcAttackDao();
		result = npcAttackDao.deleteByPpk(p_pk);
		logger.info("���" + result + "��NPC");
		return result;
	}
*/
	/**
	 * ��������ʱ��
	 * 
	 * @param npc_id
	 * @param scene_id
	 * @param type
	 *            1Ϊ����,2Ϊ����
	 */
	public void updateTimeOfNPCDead(PartInfoVO player,NpcAttackVO npc,int npc_id,int scene_id,int type )
	{
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
			RoomService roomService = new RoomService();
			if( roomService.getMapType(scene_id) == MapType.INSTANCE )//������ˢ��ʱ���С��
			{
				InstanceService instanceService =  new InstanceService();
				GroupService groupService = new GroupService();
				int caption_pk = groupService.getCaptionPk(player.getPPk());
				instanceService.recordNpcDeadTime(caption_pk, scene_id, npc_id);
				npcrefurbishDao.setDeadState(npc_id, scene_id);
				
				//ͳ����Ҫ
				//new RankService().updateAdd(player.getPPk(), "killnpc", npc.getLevel());
				
				logger.info("����С��������ץ������ϵͳ��Ϣ");
				return;
			}
			else// ����ϵͳ��Ϣ
			{
				if( !npcrefurbishDao.isDead(npc_id, scene_id) )
				{
    				npcrefurbishDao.setDeadState(npc_id, scene_id);
    				NpcService npcServcie = new NpcService();
    				if(type == 1) {
    					npcServcie.sendSysInfoOfNpcDead(player,npc,npc.getNpcRefurbishTime());
    					//ͳ����Ҫ
    					//new RankService().updateAdd(player.getPPk(), "killboss", (npc.getLevel()*2));
    				}else {
    					logger.info("player.getPPk()="+player.getPPk());
    					//5.04ȡ����׽��ʾ��Ϣ
    					//npcServcie.sendSysInfoOfCatch(player.getPPk(),npc);
    				}
				}
			}
	}
	/**
	 * ��ǰ�ص�NCP�Ƿ�BOSS
	 * @param scene_id
	 * @param npc_id
	 * @return
	 */
	public boolean isboss(int scene_id,int npc_id){
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		return npcrefurbishDao.isBoss(scene_id, npc_id);
	}
}
