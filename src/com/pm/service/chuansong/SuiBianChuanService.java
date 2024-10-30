/**
 * 
 */
package com.pm.service.chuansong;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ben.lost.CompassService;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.group.GroupInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.group.GroupModel;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.PropUseService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.room.RoomService;
import com.pm.dao.chuansong.SuiBianDao;
import com.pm.vo.chuansong.SuiBianChuanVO;

/**
 * ��㴫�͵���
 * @author zhangjj
 *
 */
public class SuiBianChuanService
{

	Logger logger = Logger.getLogger("log.service");
	/**
	 * 
	 * @param pg_pk
	 * @param goods_id
	 * @param goods_type
	 * @return
	 */
	public List<SuiBianChuanVO> getChuanSongType(RoleEntity roleEntity,String pg_pk, String goods_id,
			String goods_type)
	{
		
		PropVO prop = null;
		PlayerPropGroupVO propGroup = null;

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		//PropDao propDAO = new PropDao();
		PropCache propCache = new PropCache();

		logger.info("pg_pk=" + pg_pk);
		propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		logger.info("propId=" + propGroup.getPropId());
		prop = propCache.getPropById(propGroup.getPropId());// �õ�������ϸ��Ϣ
		
		
		SuiBianDao suiBianDao = new SuiBianDao();
		List<SuiBianChuanVO> list = suiBianDao.getChuanSongType();
		
		
		return list;
	}
	
	
	
	
	
	
	/**
	 * ���˵����ܷ����ʹ��
	 * @param pg_pk
	 * @param goods_id
	 * @param goods_type
	 * @return
	 */
	public String checkIsUse(RoleEntity roleInfo, String pg_pk)
	{
		PropVO prop = null;
		PlayerPropGroupVO propGroup = null;

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		//PropDao propDAO = new PropDao();
		PropCache propCache = new PropCache();

		logger.info("pg_pk=" + pg_pk);
		propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		logger.info("propId=" + propGroup.getPropId());
		prop = propCache.getPropById(propGroup.getPropId());// �õ�������ϸ��Ϣ
		
		// �����Ƿ����ʹ���ж�
		// ����ʹ�û����ж�
		PropUseService propUseService = new PropUseService();
		String resutl = propUseService.isPropUseByBasicCondition(roleInfo, prop, 1);
		
		
		return resutl;
	}






	/**
	 * ��������ID�õ��ص���Ϣ
	 * @param roleInfo
	 * @param pg_pk
	 */
	public List<SuiBianChuanVO> getSuiBianByTypeId(int prace,int carryType)
	{
		SuiBianDao suiBianDao = new SuiBianDao();
		List<SuiBianChuanVO> list = new ArrayList<SuiBianChuanVO>();
		list = suiBianDao.getChuanSongBy(prace,carryType);
		return list;
	}





	/**
	 * ʹ����㴫
	 * @param roleInfo
	 * @param pg_pk
	 */
	public String useSuiBianChuan(RoleEntity roleInfo, String pg_pk,
			String carryId)
	{
		String result = "";
		if ( carryId == null || carryId.equals("")) {
			return "�ص����";			
		}
		
		
		PlayerPropGroupVO propGroup = null;

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		
		logger.info("pg_pk=" + pg_pk);
		propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		SuiBianDao suiBianDao = new SuiBianDao();
		SuiBianChuanVO suiBianChuanVO = suiBianDao.getChuanSongById(carryId);
		
		if (suiBianChuanVO.getCarryGrade() > roleInfo.getBasicInfo().getGrade()) {
			return "���ĵȼ�����!";
		}
		
		
		RoomService roomService = new RoomService();
		String carry_hint = roomService.isCarryedIn(suiBianChuanVO.getSceneId());
		if ( carry_hint != null && !carry_hint.equals("")) {			
			return carry_hint;
		}
		
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(propGroup, 1);
		
		//result = "��ʹ����"+propGroup.getPropName()+",ƽ��������"+suiBianChuanVO.getSceneName();
		roleInfo.getBasicInfo().updateSceneId(suiBianChuanVO.getSceneId()+"");
		CompassService.removeMiJing(roleInfo.getBasicInfo().getPPk(), propGroup.getPropType());//ɾ���ؾ���ͼ
		return result;
	}



	/**
	 * ʹ�ö�Ա���ͷ�
	 * @param roleInfo      �����Ϣ
	 * @param pg_pk         ������Ʒid
	 * @param member_pk     ����id
	 * @return
	 */
	public String useGroupChuan(RoleEntity roleInfo, String pg_pk,
			String member_pk)
	{
		String result = "";
		if ( member_pk == null || member_pk.equals("")) {
			return "��Ա����";			
		}
		
		PlayerPropGroupVO propGroup = null;
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		
		GoodsService goodsService = new GoodsService();
		RoomService roomService = new RoomService();
		
		logger.info("pg_pk=" + pg_pk);
		propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		GroupModel group_info = roleInfo.getStateInfo().getGroupInfo();
		
		RoleEntity member_info = group_info.getMemberInfo(Integer.parseInt(member_pk));
		
		int go_map = 0;
		
		if ( member_info==null ) 
		{
			result = "��Ա����2!";
			return result;
		}
		else
		{
			go_map = Integer.parseInt(member_info.getBasicInfo().getSceneId());
		}
		
		String carry_hint = roomService.isCarryedIn(go_map);
		if ( carry_hint != null && !carry_hint.equals("")) {			
			return carry_hint;
		}
		
		goodsService.removeProps(propGroup, 1);
				
		roleInfo.getBasicInfo().updateSceneId(go_map+"");
		CompassService.removeMiJing(roleInfo.getBasicInfo().getPPk(), propGroup.getPropType());//ɾ���ؾ���ͼ
		return result;
	}
	

}
