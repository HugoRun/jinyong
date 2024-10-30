package com.ls.web.service.instance;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.npc.NpcDeadRecordDao;
import com.ls.ben.dao.instance.InstanceArchiveDao;
import com.ls.ben.dao.instance.InstanceInfoDao;
import com.ls.ben.vo.info.npc.NpcDeadRecordVO;
import com.ls.ben.vo.instance.InstanceArchiveVO;
import com.ls.ben.vo.instance.InstanceInfoVO;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.util.DateUtil;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.room.RoomService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * @author ls
 * ����:�����������
 * Feb 3, 2009
 */
public class InstanceService
{
	private Logger logger = Logger.getLogger("log.service");
	
	/**
	 * �жϸ����Ƿ����
	 */
	public boolean isFinshed( int p_pk,InstanceInfoVO instanceInfo )
	{
		if( instanceInfo==null )
		{
			logger.info("instanceInfoΪ��");
			return false;
		}
			
		int boss_scene_num = 0;
		int dead_scene_num = 0;
		
		InstanceArchiveVO instanceArchive = getArchiveByMapId(p_pk, instanceInfo.getMapId());
		
		if( instanceArchive==null )//���û�д浵
		{
			return false;
		}
		
		boss_scene_num = instanceInfo.getBossSceneNum();
		
		String boss_record_record = instanceArchive.getDeadBossRecord();
		
		String boss_scenes[] = boss_record_record.split(",");
		
		dead_scene_num = boss_scenes.length-1;
		
		if( dead_scene_num<boss_scene_num )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * �жϸ����Ƿ�����
	 * @param instanceInfo    ������Ϣ
	 * @return	
	 */
	public boolean isReset( InstanceInfoVO instanceInfo )
	{
		boolean result = false;
		
		if( instanceInfo==null )
		{
			logger.info("����instanceInfo��");
			return false;
		}
		
		
		if( DateUtil.getDifferDaysToToday(instanceInfo.getPreResetTime())>=instanceInfo.getResetTimeDistance() )
		{
			result = true;
		}
		return result;
	}
	/**
	 * ��������
	 * @param p_pk            ��ɫid
	 * @param map_id     ����id(map_id)
	 */
	public void reset( int p_pk,int map_id )
	{
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		InstanceInfoDao  instanceInfoDao = new InstanceInfoDao();
		
		instanceInfoDao.updateResetTime(map_id);//��������ʱ��
		instanceArchiveDao.clearAllArchive(map_id);//��ʼ��������Ҹø�������
	}
	
	/**
	 * �жϽ�ɫ�Ƿ���Խ��븱��
	 * @param p_pk
	 * @param instance_id          ����id
	 * @return     ����null��ʾ����
	 */
	public String isEntered( int p_pk,int level,int instance_id )
	{
		String hint = null;
		
		InstanceInfoVO instanceInfo = null;
		int player_level = 0;//��ҵȼ�
		int group_member_num = 0;//������ڶ�������
		
		GroupService  groupService = new GroupService();
		
		player_level =level;
		
		group_member_num = groupService.getGroupNumByMember(p_pk);
		
		instanceInfo = getInfoById(instance_id);
		
		if( instanceInfo==null )
		{
			hint = "��Чinstance_id:"+instance_id;
			logger.info(hint);
			return hint;
		}
		
		if( group_member_num<instanceInfo.getGroupLimit() )
		{
			hint = "������������,������Ҫ"+instanceInfo.getGroupLimit()+"�˲��ܽ��븱��";
			logger.info(hint);
			return hint;
		}
		
		if( player_level<instanceInfo.getLevelLimit() )
		{
			hint = "�ȼ�����,������Ҫ"+instanceInfo.getLevelLimit()+"���ſ��Խ��븱��";
			logger.info(hint);
			return hint;
		}
		
		
		return hint;
	}
	
	/**
	 * ��¼boss����
	 * @param p_pk
	 * @param scene_id                ����boss���ڵ�
	 */
	public void archive( int p_pk,int scene_id )
	{
		int map_id = -1;
		
		RoomService roomService = new RoomService();
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		int map_type = roomService.getMapType(scene_id);
		
		if( map_type==MapType.INSTANCE )//�ж��Ƿ����ڸ�������
		{
    		//**�жϸõص��Ƿ���boss
    		if( roomService.isHaveBoss(scene_id)==true )
    		{
    			map_id = roomService.getMapId(scene_id);
    			instanceArchiveDao.updateArchive(p_pk, map_id, scene_id);
    			InstanceInfoVO vo = this.getInfoByMapId(map_id);
    			if(this.isFinshed(p_pk, vo)){
    				//ͳ����Ҫ
    				new RankService().updateAdd(p_pk, "meng", vo.getLevelLimit());
        		}
    		}
		}
	}
	
	/**
	 * ���������浵
	 */
	public void createArchive( int p_pk,int map_id )
	{
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		instanceArchiveDao.insertArchive(p_pk, map_id);
	}
	
	/**
	 * ��ȡ��������
	 */
	public InstanceArchiveVO getArchiveBySceneId( int p_pk ,int scene_id )
	{
		int map_id = -1;
		
		RoomService roomService = new RoomService();
		
		map_id = roomService.getMapId(scene_id);
		
		return getArchiveByMapId(p_pk,map_id);
	}
	/**
	 * ��ȡ��������
	 */
	public InstanceArchiveVO getArchiveByMapId( int p_pk ,int map_id )
	{
		InstanceArchiveVO instanceArchive = null;
		
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		
		if( instanceArchiveDao.isHaveArchive(p_pk, map_id)==false )//���û�е�ǰ���������򴴽��µĽ���
		{
			createArchive(p_pk, map_id);
		}
		instanceArchive = instanceArchiveDao.getArchive(p_pk, map_id);
		
		return instanceArchive;
	}

	/**
	 *  �ж�����Ƿ����и������ȴ浵
	 */
	public boolean isHaveArchive( int p_pk,int scene_id )
	{
		boolean result = false;
		
		InstanceArchiveVO instanceArchive =  getArchiveBySceneId(  p_pk , scene_id );
		String dead_record = instanceArchive.getDeadBossRecord();
		if( dead_record.indexOf(""+scene_id) != -1 )
		{
			result = true;
		}
		return result;
	}
	
	/**
	 * ����instance_id�õ�������Ϣ����:���븱���ĵص㣬��������ĵص㣬����������������ƣ����븱���ĵȼ�����
	 */
	public InstanceInfoVO getInfoById( int instance_id )
	{
		InstanceInfoDao instanceInfoDao = new InstanceInfoDao();
		return instanceInfoDao.getByID(instance_id);
	}
	/**
	 * ����map_id�õ�������Ϣ����:���븱���ĵص㣬��������ĵص㣬����������������ƣ����븱���ĵȼ�����
	 */
	public InstanceInfoVO getInfoByMapId( int map_id )
	{
		InstanceInfoDao instanceInfoDao = new InstanceInfoDao();
		return instanceInfoDao.getByMapID(map_id);
	}
	/**
	 * ����scene_id�õ�������Ϣ����:���븱���ĵص㣬��������ĵص㣬����������������ƣ����븱���ĵȼ�����
	 */
	public InstanceInfoVO getInfoBySceneId( int scene_id )
	{
		RoomService roomService = new RoomService();
		int map_id = -1;
		map_id = roomService.getMapId(scene_id);
		return getInfoByMapId(map_id);
	}
	
	
	/**
	 * ��¼����С�ֵ�����ʱ��
	 */
	public void recordNpcDeadTime(int caption_pk,int scene_id,int npc_id)
	{
		if( caption_pk==-1 )
		{
			return;
		}
		NpcDeadRecordDao npcDeadRecordDao = new NpcDeadRecordDao();
		NpcDeadRecordVO npcDeadRecord = npcDeadRecordDao.getRecord(caption_pk, scene_id);
		if( npcDeadRecord==null )
		{
			RoomService roomService = new RoomService();
			int map_id = roomService.getMapId(scene_id);
			npcDeadRecord = new NpcDeadRecordVO();
			npcDeadRecord.setPPk(caption_pk);
			npcDeadRecord.setNpcId(npc_id);
			npcDeadRecord.setSceneId(scene_id);
			npcDeadRecord.setMapId(map_id);
			npcDeadRecordDao.incert(npcDeadRecord);
		}
		else
		{
			npcDeadRecordDao.updateDeadTime(caption_pk, npc_id, scene_id);
		}
	}
	
	/**
	 * �������С�ֵ�������¼
	 */
	public void clearDeadRecord( int caption_pk,int map_id )
	{
		NpcDeadRecordDao npcDeadRecordDao = new NpcDeadRecordDao();
		
		npcDeadRecordDao.clearByMapID(caption_pk, map_id);
	}
	/**
	 * �������ĸ���С�ֵ��������м�¼
	 */
	public void clearAllDeadRecord( int caption_pk)
	{
		NpcDeadRecordDao npcDeadRecordDao = new NpcDeadRecordDao();
		
		npcDeadRecordDao.clear(caption_pk);
	}
	
	/********���ɱ��������ʱ�����ʾ********/
	public String getTianGuanDisplay(RoleEntity roleInfo){
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		SceneVO sceneVO = SceneCache.getById(scene_id);
		MapVO mapVO = sceneVO.getMap();
		if(mapVO.getMapType() == MapType.TIANGUAN){
			if(roleInfo.getBasicInfo().getTianguan_time() != 0){
				String[] condition = mapVO.getMapSkill().split(",");
				//����  ��һλ �� ��MAP������ �ڶ���Ϊ ����NPCID  ������Ϊ����  ���ĸ�Ϊ�õ�ͼ��ʱ��
				int num = 0;
				num = Integer.parseInt(condition[2]) - roleInfo.getBasicInfo().getTianguan_kill_num();
				if(num < 0){
					num = 0;
				}
				int time = Integer.parseInt(condition[3]);
				long now = new Date().getTime();
				long time_now = (now - roleInfo.getBasicInfo().getTianguan_time())/1000;//��
				long time_out = time*60 - time_now;
				if(time_out == 0){
					return "ʣ����սʱ��:60����!<br/>ʣ��ɱ������:"+num+"<br/>";
				}else{
					if(time_out < 1){
						return "ʣ����սʱ��:"+time_now+"��!<br/>ʣ��ɱ������:"+num+"<br/>";
					}else{
						return "ʣ����սʱ��:"+time_out/60+"����!<br/>ʣ��ɱ������:"+num+"<br/>";
					}
				}
			}else{
				return "";	
			}
		}else{
			return "";
		}
	}
	
	//����FALSE ˵����Ҳ�����ת  ����TRUE ˵�����Ҫ����
	public boolean ifPlayerOut(RoleEntity roleInfo){
		if(roleInfo != null){
			String scene_id = roleInfo.getBasicInfo().getSceneId();
			if(scene_id != null && scene_id.equals("") && scene_id.equals("null")){
				SceneVO sceneVO = SceneCache.getById(scene_id);
				MapVO mapVO = sceneVO.getMap();
				if(mapVO.getMapType() == MapType.TIANGUAN){
					if(roleInfo.getBasicInfo().getTianguan_time() != 0){
						String[] condition = mapVO.getMapSkill().split(",");
						//����  ��һλ �� ��MAP������ �ڶ���Ϊ ����NPCID  ������Ϊ����  ���ĸ�Ϊ�õ�ͼ��ʱ��
						int time = Integer.parseInt(condition[3]);
						long now = new Date().getTime();
						long time_now = (now - roleInfo.getBasicInfo().getTianguan_time())/1000;//��
						long time_out = time*60 - time_now;
						if(time_out !=0 && time_out < 1){
							RoomService rs = new RoomService();
							int resurrection_point = rs.getResurrectionPoint(roleInfo);
							roleInfo.getBasicInfo().updateSceneId(resurrection_point + "");
							SystemInfoService infoService = new SystemInfoService();
							infoService.insertSystemInfoBySystem(roleInfo.getBasicInfo().getPPk(),"�����ʵ������,�ܵ������Ѿ����漣��,��ȥ�ú�ĥ����������!");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
