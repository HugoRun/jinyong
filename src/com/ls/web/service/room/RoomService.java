package com.ls.web.service.room;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;

/**
 * 功能:区域管理（房间的概念）
 * @author 刘帅
 * Sep 17, 2008 5:09:13 PM
 */
public class RoomService
{
	Logger logger = Logger.getLogger("log.service");
    //**********************传入传出限制常量****************************
	/**可以传入*/
	public static final String CARRY_IN="1";     
	/**不可以传入*/
	public static final String NOT_CARRY_IN="2";  
	/**可以传出*/
	public static final String CARRY_OUT="3";       
	/**不可以传出*/
	public static final String NOT_CARRY_OUT="4";   
	
	//************************区域属性类型****************************0表示否，1表示可以
	/**死亡是否掉落物品*/
	public static final int DROP_GOODS = 0;         
	/**红名是否被抓*/
	public static final int REDNAME_SEIZURED = 1;           
	/**亡是否掉落经验*/
	public static final int DROP_EXP = 2;           
	/**组队是否有效*/
	public static final int GROUP_EFFECT = 3;       
	/**是否有PK等级保护*/
	public static final int PK_GRADE_PROTECT = 4;  
	/**是否增加罪恶值*/
	public static final int PK_PUNISH = 5;          
	
	
	/**
	 * 通过scene_id得到场景信息
	 * @param scene_id
	 * @return
	 */
	public SceneVO getById( String scene_id )
	{
		return SceneCache.getById(scene_id);
	}
	
	/**
	 * 得到scene所在map的信息
	 * @param scene_id
	 * @return
	 */
	public MapVO getMapInfoBySceneId( String scene_id )
	{
		SceneVO scene = SceneCache.getById(scene_id);
		return scene.getMap();
	}
	
	/**
	 * 区域属性控制
	 * @param scene_id     
	 * @param attribute_type        区域属性类型   
	 * @return                  true表示有限制，false表示没有限制
	 */
	public boolean isLimitedByAttribute( int scene_id,int attribute_type )
	{
		boolean result = false;
		
		SceneVO scene_info = getById(scene_id+"");
		String attributes = scene_info.getSceneAttribute();
		
		if( attributes==null )
		{
			return false;
		}
		//判断属性字符串格式是否正确
		if( attributes.equals("") || attributes.length() != 11 )
		{
			return false;
		}
		
		String attribute[] = attributes.split(",");
		if( attribute[attribute_type].equals("0") )
		{
			result = true;
		}
		return result;
	}
	
	/**
	 * 判断此房间是否可以让玩家传出去
	 * @param scene_id
	 * @return  返回空表示可正常出去
	 */
	public String isCarryedOut(int scene_id)
	{
		String hint = null;
		
		SceneVO scene_info = getById(scene_id+"");
		String carryLimits = scene_info.getSceneLimit();
		
		String carryLimit[] = carryLimits.split(",");
		if( carryLimit[1].equals(NOT_CARRY_OUT) )
		{
			//hint =  getName(scene_id)+ "不可传出";
			hint =  getName(scene_id)+ "地点不可使用此道具";
		}
		return hint;
	}
	
	/**
	 * 判断此房间是否可以让玩家传送入
	 * @param scene_id
	 * @return  返回空表示可正常传入
	 */
	public String isCarryedIn(int scene_id)
	{
		String hint = null;
		SceneVO scene_info = getById(scene_id+"");
		String carryLimits = scene_info.getSceneLimit();
		
		String carryLimit[] = carryLimits.split(",");
		if( carryLimit[0].equals(NOT_CARRY_IN) )
		{
			hint = getName(scene_id)+ "地点不可使用此道具";
		}
		return hint;
	}
	
	/**
	 * 判断从A点是否可以传送到B点
	 * @param sceneA_id    出发点
	 * @param sceneB_id    目的点
	 * @return 返回空表示可正常传送
	 */
	public String isCarryFromSceneAToSceneB( int sceneA_id,int sceneB_id)
	{
		String hint = null;
		String carryout_hint = isCarryedOut(sceneA_id);
		String carryin_hint = isCarryedIn(sceneB_id);
		if( carryout_hint==null && carryin_hint==null )
		{
			return null;
		}
		//不可传出可传入
		else if( carryout_hint!=null && carryin_hint==null )
		{
			hint = carryout_hint;
		}
		//可传出不可传入
		else if( carryin_hint!=null && carryout_hint==null )
		{
			hint = carryin_hint;
		}
		//既不可传出也不可传入
		else
		{
			hint = carryout_hint + "," + carryin_hint;
		}
		return hint;
	}
	/**
	 * 得到scene名字
	 */
	public String getName( int scene_id )
	{
		SceneVO scene = getById(scene_id+"");
		return scene.getSceneName();
	}
	/**
	 * 得到所在map_id
	 */
	public int getMapId( int scene_id )
	{
		SceneVO scene = getById(scene_id+"");
		return Integer.parseInt(scene.getSceneMapqy());
	}
	
	/**
	 * 得到所在map_id的类型
	 */
	public int getMapType( int scene_id )
	{
		SceneVO scene = getById(scene_id+""); 
		return scene.getMap().getMapType();
	}
	
	/**
	 * 判断scene_id所在的场景是否在指定的map类型
	 * 得到所在map_id的类型
	 */
	public boolean isSpecifyMapType( int scene_id,int specify_map_type )
	{
		int map_type = getMapType(scene_id);
		
		if( specify_map_type == map_type )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 得到所在scene_id的barea_type
	 */
	public int getBareaType( int scene_id )
	{
		SceneVO scene = getById(scene_id+""); 
		return scene.getMap().getBarea().getBareaType();
	}
	/**
	 * 得到scene_id所在区域的复活点
	 * @param scene_id
	 * @return
	 */
	public int getResurrectionPoint( RoleEntity roleInfo )
	{
		int resurrection_point = -1;
		
		int map_type = -1;
		
		SceneVO scene = roleInfo.getBasicInfo().getSceneInfo();
		
		map_type =scene.getMap().getMapType();
		
		
		if( map_type==MapType.FIELD )//攻城战
		{
			
		} else if ( map_type == MapType.TONGBATTLE)//帮派攻城战场
		{
		}
		else//城市中心点
		{
			resurrection_point = scene.getMap().getBarea().getBareaPoint();
		}
		
		return resurrection_point;
	}
	
	/**
	 * 判断该地点是否有boss
	 */
	public boolean isHaveBoss( int scene_id )
	{
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		return npcrefurbishDao.isHaveBoss(scene_id);
	}
	
	
	/**
	 * 得到scene_id的指定方向的sceneid
	 * @return
	 */
	public int getSceneByWay( int scene_id,String way)
	{
		if( way==null )
		{
			return scene_id;
		}
		int new_scene_id=scene_id;
		
		SceneVO old_scene =  getById(  scene_id+"" );
		
		
		if( way.equals("1"))
		{
			new_scene_id = old_scene.getSceneShang();
		}
		else if ( way.equals("2") )
		{
			new_scene_id = old_scene.getSceneXia();
		}
		else if ( way.equals("3") )
		{
			new_scene_id = old_scene.getSceneZuo();
		}
		else if ( way.equals("4") )
		{
			new_scene_id = old_scene.getSceneYou();
		}
		else if ( way.equals("5") )
		{
			new_scene_id = old_scene.getSceneJumpterm();
		}
		
		return new_scene_id;
	}
	
}
