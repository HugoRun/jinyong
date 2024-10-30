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
	 * 用来处理 死亡后 pk值以及 荣誉值等 死亡后的处理
	 * @param winer 胜利者
	 * @param loser	失败者
	 *//*
	public void dealPkEffect(Fighter winer, Fighter loser,String tong)
	{
		FightService fightService = new FightService();
		
		RoleEntity winer_info = RoleService.getRoleInfoById(winer.getPPk());
		RoleEntity loser_info = RoleService.getRoleInfoById(loser.getPPk());
		
		SceneVO cur_scene = winer_info.getBasicInfo().getSceneInfo();
		
		if( cur_scene.getMap().getMapType()==MapType.DANGER )//如果在危险区域
		{
			if( winer.getPRace()!=loser.getPRace() )//如果不是一个种族(随意PK)
			{
				Faction faction = winer_info.getBasicInfo().getFaction();
				if( faction!=null )
				{
					//***********如果加入帮派
					winer_info.getBasicInfo().addFContribute(1);//增加个人帮派贡献
					faction.updatePrestige(1);//增加帮派声望
				}
			}
			else//如果是一个种族(强制PK)
			{
				
				
			}
		}
		//其他区域
		{
			//活动PK
		}
		
		
		
		
		//tong 0 是普通PK 1是帮战 2是阵营 
		if(tong.equals("0")){
			// 如果玩家a是主动攻击者， 那么就会增加罪恶值 
			if( pKLogService.isZDAttacker(winer.getPPk())) {
			
    			//增加玩家A的罪恶值，如果map区域可以增加罪恶值,踩可以增加.
    			RoomService roomService = new RoomService();
    			if(!roomService.isLimitedByAttribute(Integer.valueOf(winer.getPMap()), RoomService.PK_PUNISH)) {
    				int add_pk_value = fightService.addPkValue(winer,loser);
    				fightService.setPkValueElimiTime(winer_info,add_pk_value);
    			}	
			}
		} else if(tong.equals("1")) {//帮战PK状态 增加杀人数量和荣誉值
		//杀死玩家后在荣誉表中增加杀人数量
			winer_info.getBasicInfo().addFContribute(1);
		} else if(tong.equals("2")) {
			// 阵营PK状态 增加荣誉值
			// 如果是在战场中，增加玩家荣誉和战场杀人数。如否则在杀死玩家后在荣誉表中增加杀人数量。
			winer_info.getBasicInfo().addFContribute(1);
		} else if ( tong.equals("5")) { // 如果是在 攻城战场上, 
			//杀死玩家后在荣誉表中增加杀人数量
			// 给玩家增加 帮派荣誉,即在此帮派里的荣誉
			winer_info.getBasicInfo().addFContribute(1);
		} 
		
	}*/

}
