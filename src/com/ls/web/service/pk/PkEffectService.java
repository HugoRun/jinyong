package com.ls.web.service.pk;

import com.ls.ben.vo.info.partinfo.Fighter;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.organize.faction.Faction;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.web.service.player.FightService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.room.RoomService;

public class PkEffectService
{
	/**
	 * �������� ������ pkֵ�Լ� ����ֵ�� ������Ĵ���
	 * @param winer ʤ����
	 * @param loser	ʧ����
	 *//*
	public void dealPkEffect(Fighter winer, Fighter loser,String tong)
	{
		FightService fightService = new FightService();
		
		RoleEntity winer_info = RoleService.getRoleInfoById(winer.getPPk());
		RoleEntity loser_info = RoleService.getRoleInfoById(loser.getPPk());
		
		SceneVO cur_scene = winer_info.getBasicInfo().getSceneInfo();
		
		if( cur_scene.getMap().getMapType()==MapType.DANGER )//�����Σ������
		{
			if( winer.getPRace()!=loser.getPRace() )//�������һ������(����PK)
			{
				Faction faction = winer_info.getBasicInfo().getFaction();
				if( faction!=null )
				{
					//***********����������
					winer_info.getBasicInfo().addFContribute(1);//���Ӹ��˰��ɹ���
					faction.updatePrestige(1);//���Ӱ�������
				}
			}
			else//�����һ������(ǿ��PK)
			{
				
				
			}
		}
		//��������
		{
			//�PK
		}
		
		
		
		
		//tong 0 ����ͨPK 1�ǰ�ս 2����Ӫ 
		if(tong.equals("0")){
			// ������a�����������ߣ� ��ô�ͻ��������ֵ 
			if( pKLogService.isZDAttacker(winer.getPPk())) {
			
    			//�������A�����ֵ�����map��������������ֵ,�ȿ�������.
    			RoomService roomService = new RoomService();
    			if(!roomService.isLimitedByAttribute(Integer.valueOf(winer.getPMap()), RoomService.PK_PUNISH)) {
    				int add_pk_value = fightService.addPkValue(winer,loser);
    				fightService.setPkValueElimiTime(winer_info,add_pk_value);
    			}	
			}
		} else if(tong.equals("1")) {//��սPK״̬ ����ɱ������������ֵ
		//ɱ����Һ���������������ɱ������
			winer_info.getBasicInfo().addFContribute(1);
		} else if(tong.equals("2")) {
			// ��ӪPK״̬ ��������ֵ
			// �������ս���У��������������ս��ɱ�������������ɱ����Һ���������������ɱ��������
			winer_info.getBasicInfo().addFContribute(1);
		} else if ( tong.equals("5")) { // ������� ����ս����, 
			//ɱ����Һ���������������ɱ������
			// ��������� ��������,���ڴ˰����������
			winer_info.getBasicInfo().addFContribute(1);
		} 
		
	}*/

}
