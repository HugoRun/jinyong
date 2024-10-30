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
 * 功能:副本管理程序
 * Feb 3, 2009
 */
public class InstanceService
{
	private Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 判断副本是否完成
	 */
	public boolean isFinshed( int p_pk,InstanceInfoVO instanceInfo )
	{
		if( instanceInfo==null )
		{
			logger.info("instanceInfo为空");
			return false;
		}
			
		int boss_scene_num = 0;
		int dead_scene_num = 0;
		
		InstanceArchiveVO instanceArchive = getArchiveByMapId(p_pk, instanceInfo.getMapId());
		
		if( instanceArchive==null )//如果没有存档
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
	 * 判断副本是否重置
	 * @param instanceInfo    副本信息
	 * @return	
	 */
	public boolean isReset( InstanceInfoVO instanceInfo )
	{
		boolean result = false;
		
		if( instanceInfo==null )
		{
			logger.info("参数instanceInfo空");
			return false;
		}
		
		
		if( DateUtil.getDifferDaysToToday(instanceInfo.getPreResetTime())>=instanceInfo.getResetTimeDistance() )
		{
			result = true;
		}
		return result;
	}
	/**
	 * 副本重置
	 * @param p_pk            角色id
	 * @param map_id     副本id(map_id)
	 */
	public void reset( int p_pk,int map_id )
	{
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		InstanceInfoDao  instanceInfoDao = new InstanceInfoDao();
		
		instanceInfoDao.updateResetTime(map_id);//更新重置时间
		instanceArchiveDao.clearAllArchive(map_id);//初始化所有玩家该副本进度
	}
	
	/**
	 * 判断角色是否可以进入副本
	 * @param p_pk
	 * @param instance_id          副本id
	 * @return     返回null表示可以
	 */
	public String isEntered( int p_pk,int level,int instance_id )
	{
		String hint = null;
		
		InstanceInfoVO instanceInfo = null;
		int player_level = 0;//玩家等级
		int group_member_num = 0;//玩家所在队伍人数
		
		GroupService  groupService = new GroupService();
		
		player_level =level;
		
		group_member_num = groupService.getGroupNumByMember(p_pk);
		
		instanceInfo = getInfoById(instance_id);
		
		if( instanceInfo==null )
		{
			hint = "无效instance_id:"+instance_id;
			logger.info(hint);
			return hint;
		}
		
		if( group_member_num<instanceInfo.getGroupLimit() )
		{
			hint = "队伍人数不够,至少需要"+instanceInfo.getGroupLimit()+"人才能进入副本";
			logger.info(hint);
			return hint;
		}
		
		if( player_level<instanceInfo.getLevelLimit() )
		{
			hint = "等级不够,至少需要"+instanceInfo.getLevelLimit()+"级才可以进入副本";
			logger.info(hint);
			return hint;
		}
		
		
		return hint;
	}
	
	/**
	 * 记录boss死亡
	 * @param p_pk
	 * @param scene_id                死亡boss所在地
	 */
	public void archive( int p_pk,int scene_id )
	{
		int map_id = -1;
		
		RoomService roomService = new RoomService();
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		int map_type = roomService.getMapType(scene_id);
		
		if( map_type==MapType.INSTANCE )//判断是否是在副本区域
		{
    		//**判断该地点是否有boss
    		if( roomService.isHaveBoss(scene_id)==true )
    		{
    			map_id = roomService.getMapId(scene_id);
    			instanceArchiveDao.updateArchive(p_pk, map_id, scene_id);
    			InstanceInfoVO vo = this.getInfoByMapId(map_id);
    			if(this.isFinshed(p_pk, vo)){
    				//统计需要
    				new RankService().updateAdd(p_pk, "meng", vo.getLevelLimit());
        		}
    		}
		}
	}
	
	/**
	 * 创建副本存档
	 */
	public void createArchive( int p_pk,int map_id )
	{
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		instanceArchiveDao.insertArchive(p_pk, map_id);
	}
	
	/**
	 * 读取副本进度
	 */
	public InstanceArchiveVO getArchiveBySceneId( int p_pk ,int scene_id )
	{
		int map_id = -1;
		
		RoomService roomService = new RoomService();
		
		map_id = roomService.getMapId(scene_id);
		
		return getArchiveByMapId(p_pk,map_id);
	}
	/**
	 * 读取副本进度
	 */
	public InstanceArchiveVO getArchiveByMapId( int p_pk ,int map_id )
	{
		InstanceArchiveVO instanceArchive = null;
		
		InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
		
		if( instanceArchiveDao.isHaveArchive(p_pk, map_id)==false )//如果没有当前副本进度则创建新的进度
		{
			createArchive(p_pk, map_id);
		}
		instanceArchive = instanceArchiveDao.getArchive(p_pk, map_id);
		
		return instanceArchive;
	}

	/**
	 *  判断玩家是否以有副本进度存档
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
	 * 根据instance_id得到副本信息，如:进入副本的地点，死亡复活的地点，进入组队人数的限制，进入副本的等级限制
	 */
	public InstanceInfoVO getInfoById( int instance_id )
	{
		InstanceInfoDao instanceInfoDao = new InstanceInfoDao();
		return instanceInfoDao.getByID(instance_id);
	}
	/**
	 * 根据map_id得到副本信息，如:进入副本的地点，死亡复活的地点，进入组队人数的限制，进入副本的等级限制
	 */
	public InstanceInfoVO getInfoByMapId( int map_id )
	{
		InstanceInfoDao instanceInfoDao = new InstanceInfoDao();
		return instanceInfoDao.getByMapID(map_id);
	}
	/**
	 * 根据scene_id得到副本信息，如:进入副本的地点，死亡复活的地点，进入组队人数的限制，进入副本的等级限制
	 */
	public InstanceInfoVO getInfoBySceneId( int scene_id )
	{
		RoomService roomService = new RoomService();
		int map_id = -1;
		map_id = roomService.getMapId(scene_id);
		return getInfoByMapId(map_id);
	}
	
	
	/**
	 * 记录副本小怪的死亡时间
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
	 * 清除副本小怪的死亡记录
	 */
	public void clearDeadRecord( int caption_pk,int map_id )
	{
		NpcDeadRecordDao npcDeadRecordDao = new NpcDeadRecordDao();
		
		npcDeadRecordDao.clearByMapID(caption_pk, map_id);
	}
	/**
	 * 清除队伍的副本小怪的死亡所有记录
	 */
	public void clearAllDeadRecord( int caption_pk)
	{
		NpcDeadRecordDao npcDeadRecordDao = new NpcDeadRecordDao();
		
		npcDeadRecordDao.clear(caption_pk);
	}
	
	/********玩家杀怪数量和时间的显示********/
	public String getTianGuanDisplay(RoleEntity roleInfo){
		String scene_id = roleInfo.getBasicInfo().getSceneId();
		SceneVO sceneVO = SceneCache.getById(scene_id);
		MapVO mapVO = sceneVO.getMap();
		if(mapVO.getMapType() == MapType.TIANGUAN){
			if(roleInfo.getBasicInfo().getTianguan_time() != 0){
				String[] condition = mapVO.getMapSkill().split(",");
				//条件  第一位 是 该MAP的类型 第二个为 怪物NPCID  第三个为数量  第四个为该地图的时间
				int num = 0;
				num = Integer.parseInt(condition[2]) - roleInfo.getBasicInfo().getTianguan_kill_num();
				if(num < 0){
					num = 0;
				}
				int time = Integer.parseInt(condition[3]);
				long now = new Date().getTime();
				long time_now = (now - roleInfo.getBasicInfo().getTianguan_time())/1000;//秒
				long time_out = time*60 - time_now;
				if(time_out == 0){
					return "剩余挑战时间:60分钟!<br/>剩余杀怪数量:"+num+"<br/>";
				}else{
					if(time_out < 1){
						return "剩余挑战时间:"+time_now+"秒!<br/>剩余杀怪数量:"+num+"<br/>";
					}else{
						return "剩余挑战时间:"+time_out/60+"分钟!<br/>剩余杀怪数量:"+num+"<br/>";
					}
				}
			}else{
				return "";	
			}
		}else{
			return "";
		}
	}
	
	//返回FALSE 说明玩家不用跳转  返回TRUE 说明玩家要返回
	public boolean ifPlayerOut(RoleEntity roleInfo){
		if(roleInfo != null){
			String scene_id = roleInfo.getBasicInfo().getSceneId();
			if(scene_id != null && scene_id.equals("") && scene_id.equals("null")){
				SceneVO sceneVO = SceneCache.getById(scene_id);
				MapVO mapVO = sceneVO.getMap();
				if(mapVO.getMapType() == MapType.TIANGUAN){
					if(roleInfo.getBasicInfo().getTianguan_time() != 0){
						String[] condition = mapVO.getMapSkill().split(",");
						//条件  第一位 是 该MAP的类型 第二个为 怪物NPCID  第三个为数量  第四个为该地图的时间
						int time = Integer.parseInt(condition[3]);
						long now = new Date().getTime();
						long time_now = (now - roleInfo.getBasicInfo().getTianguan_time())/1000;//秒
						long time_out = time*60 - time_now;
						if(time_out !=0 && time_out < 1){
							RoomService rs = new RoomService();
							int resurrection_point = rs.getResurrectionPoint(roleInfo);
							roleInfo.getBasicInfo().updateSceneId(resurrection_point + "");
							SystemInfoService infoService = new SystemInfoService();
							infoService.insertSystemInfoBySystem(roleInfo.getBasicInfo().getPPk(),"以你的实力而言,能到这里已经是奇迹了,回去好好磨练下再来吧!");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
