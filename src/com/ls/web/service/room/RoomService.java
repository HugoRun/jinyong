package com.ls.web.service.room;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.vo.map.MapVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;

/**
 * ����:�����������ĸ��
 * @author ��˧
 * Sep 17, 2008 5:09:13 PM
 */
public class RoomService
{
	Logger logger = Logger.getLogger("log.service");
    //**********************���봫�����Ƴ���****************************
	/**���Դ���*/
	public static final String CARRY_IN="1";     
	/**�����Դ���*/
	public static final String NOT_CARRY_IN="2";  
	/**���Դ���*/
	public static final String CARRY_OUT="3";       
	/**�����Դ���*/
	public static final String NOT_CARRY_OUT="4";   
	
	//************************������������****************************0��ʾ��1��ʾ����
	/**�����Ƿ������Ʒ*/
	public static final int DROP_GOODS = 0;         
	/**�����Ƿ�ץ*/
	public static final int REDNAME_SEIZURED = 1;           
	/**���Ƿ���侭��*/
	public static final int DROP_EXP = 2;           
	/**����Ƿ���Ч*/
	public static final int GROUP_EFFECT = 3;       
	/**�Ƿ���PK�ȼ�����*/
	public static final int PK_GRADE_PROTECT = 4;  
	/**�Ƿ��������ֵ*/
	public static final int PK_PUNISH = 5;          
	
	
	/**
	 * ͨ��scene_id�õ�������Ϣ
	 * @param scene_id
	 * @return
	 */
	public SceneVO getById( String scene_id )
	{
		return SceneCache.getById(scene_id);
	}
	
	/**
	 * �õ�scene����map����Ϣ
	 * @param scene_id
	 * @return
	 */
	public MapVO getMapInfoBySceneId( String scene_id )
	{
		SceneVO scene = SceneCache.getById(scene_id);
		return scene.getMap();
	}
	
	/**
	 * �������Կ���
	 * @param scene_id     
	 * @param attribute_type        ������������   
	 * @return                  true��ʾ�����ƣ�false��ʾû������
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
		//�ж������ַ�����ʽ�Ƿ���ȷ
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
	 * �жϴ˷����Ƿ��������Ҵ���ȥ
	 * @param scene_id
	 * @return  ���ؿձ�ʾ��������ȥ
	 */
	public String isCarryedOut(int scene_id)
	{
		String hint = null;
		
		SceneVO scene_info = getById(scene_id+"");
		String carryLimits = scene_info.getSceneLimit();
		
		String carryLimit[] = carryLimits.split(",");
		if( carryLimit[1].equals(NOT_CARRY_OUT) )
		{
			//hint =  getName(scene_id)+ "���ɴ���";
			hint =  getName(scene_id)+ "�ص㲻��ʹ�ô˵���";
		}
		return hint;
	}
	
	/**
	 * �жϴ˷����Ƿ��������Ҵ�����
	 * @param scene_id
	 * @return  ���ؿձ�ʾ����������
	 */
	public String isCarryedIn(int scene_id)
	{
		String hint = null;
		SceneVO scene_info = getById(scene_id+"");
		String carryLimits = scene_info.getSceneLimit();
		
		String carryLimit[] = carryLimits.split(",");
		if( carryLimit[0].equals(NOT_CARRY_IN) )
		{
			hint = getName(scene_id)+ "�ص㲻��ʹ�ô˵���";
		}
		return hint;
	}
	
	/**
	 * �жϴ�A���Ƿ���Դ��͵�B��
	 * @param sceneA_id    ������
	 * @param sceneB_id    Ŀ�ĵ�
	 * @return ���ؿձ�ʾ����������
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
		//���ɴ����ɴ���
		else if( carryout_hint!=null && carryin_hint==null )
		{
			hint = carryout_hint;
		}
		//�ɴ������ɴ���
		else if( carryin_hint!=null && carryout_hint==null )
		{
			hint = carryin_hint;
		}
		//�Ȳ��ɴ���Ҳ���ɴ���
		else
		{
			hint = carryout_hint + "," + carryin_hint;
		}
		return hint;
	}
	/**
	 * �õ�scene����
	 */
	public String getName( int scene_id )
	{
		SceneVO scene = getById(scene_id+"");
		return scene.getSceneName();
	}
	/**
	 * �õ�����map_id
	 */
	public int getMapId( int scene_id )
	{
		SceneVO scene = getById(scene_id+"");
		return Integer.parseInt(scene.getSceneMapqy());
	}
	
	/**
	 * �õ�����map_id������
	 */
	public int getMapType( int scene_id )
	{
		SceneVO scene = getById(scene_id+""); 
		return scene.getMap().getMapType();
	}
	
	/**
	 * �ж�scene_id���ڵĳ����Ƿ���ָ����map����
	 * �õ�����map_id������
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
	 * �õ�����scene_id��barea_type
	 */
	public int getBareaType( int scene_id )
	{
		SceneVO scene = getById(scene_id+""); 
		return scene.getMap().getBarea().getBareaType();
	}
	/**
	 * �õ�scene_id��������ĸ����
	 * @param scene_id
	 * @return
	 */
	public int getResurrectionPoint( RoleEntity roleInfo )
	{
		int resurrection_point = -1;
		
		int map_type = -1;
		
		SceneVO scene = roleInfo.getBasicInfo().getSceneInfo();
		
		map_type =scene.getMap().getMapType();
		
		
		if( map_type==MapType.FIELD )//����ս
		{
			
		} else if ( map_type == MapType.TONGBATTLE)//���ɹ���ս��
		{
		}
		else//�������ĵ�
		{
			resurrection_point = scene.getMap().getBarea().getBareaPoint();
		}
		
		return resurrection_point;
	}
	
	/**
	 * �жϸõص��Ƿ���boss
	 */
	public boolean isHaveBoss( int scene_id )
	{
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		return npcrefurbishDao.isHaveBoss(scene_id);
	}
	
	
	/**
	 * �õ�scene_id��ָ�������sceneid
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
