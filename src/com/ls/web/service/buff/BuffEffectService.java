package com.ls.web.service.buff;

import java.util.Date;
import java.util.List;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.ben.vo.info.effect.PropUseEffect;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.buff.BuffType;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.goods.prop.PropUseService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.player.RoleService;
import com.pm.dao.systemInfo.SysInfoDao;
import com.pm.service.systemInfo.SystemInfoService;
import com.pm.vo.sysInfo.SystemControlInfoVO;

/**
 * 功能:buff效果管理
 * @author 刘帅 3:11:25 PM
 */
public class BuffEffectService
{

	/**
	 * 给玩家添加一个buff 效果
	 * @param object_id
	 * @param buff_id
	 */
	public void createRoleBuffEffect(int p_pk, int buff_id)
	{
		this.createBuffEffect(p_pk, BuffSystem.PLAYER, buff_id);
	}
	
	/**
	 * 创建一个buff效果
	 * @param object_id   作用对象id
	 * @param object_type 作用对象类型
	 * @param buff_id
	 */
	public void createBuffEffect(int object_id,int object_type, int buff_id)
	{
		BuffDao buffDao = new BuffDao();
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		BuffVO buff = buffDao.getBuff(buff_id);
		if (buff == null)
		{
			DataErrorLog.debugData("BuffEffectService.createBuffEffect:无效buffId="+buff_id);
		}
		
		BuffEffectVO buffEffect = (BuffEffectVO) buff;
		buffEffect.setBuffUseMode(buff.getBuffUseMode());
		buffEffect.setEffectObject(object_id);// 作用对象
		buffEffect.setEffectObjectType(object_type);// 作用对象类型

		buffEffect.setBuffTime(buff.getBuffTime());
		buffEffect.setSpareBout(buff.getBuffBout());
		
		dealWithBuffEffect (buffEffect);

		buffEffectDao.add(buffEffect);// 添加buff效果
	}
	
	/**
	 * 创建一个buff效果
	 * @param object_id   作用对象id
	 * @param object_type 作用对象类型
	 * @param buff_id
	 * @param time 时间,以分钟为单位
	 */
	public void createBuffEffect(int object_id,int object_type, int buff_id,int time)
	{
		BuffDao buffDao = new BuffDao();
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		BuffVO buff = buffDao.getBuff(buff_id);
		if (buff == null)
		{
			DataErrorLog.debugData("BuffEffectService.createBuffEffect:无效buffId="+buff_id);
		}
		
		BuffEffectVO buffEffect = (BuffEffectVO) buff;
		buffEffect.setBuffUseMode(buff.getBuffUseMode());
		buffEffect.setEffectObject(object_id);// 作用对象
		buffEffect.setEffectObjectType(object_type);// 作用对象类型

		buffEffect.setBuffTime(time);
		buffEffect.setSpareBout(buff.getBuffBout());
		
		dealWithBuffEffect (buffEffect);

		buffEffectDao.add(buffEffect);// 添加buff效果
	}

	/**
	 * 在创建时针对buff的情况有可能要作不同的处理
	 * @param buffEffect
	 */
	private void dealWithBuffEffect(BuffEffectVO buffEffect)
	{
		switch (buffEffect.getBuffType()) {
			case BuffType.HP_UP :
			{
				int change_value =	buffEffect.getBuffEffectValue();
				
				RoleEntity roleInfo = RoleService.getRoleInfoById(buffEffect.getEffectObject()+"");
				roleInfo.getBasicInfo().updateHp(roleInfo.getBasicInfo().getHp()+change_value);
				
				dealWithHPBuff(buffEffect);
				break;
			}
			case BuffType.REDUCEPKVALUE :
			{
				dealWithReducePkValue(buffEffect);
				break;
			}
		}
	}

	/**
	 * 在创建时处理减少pk值的buff
	 * @param buffEffect
	 */
	private void dealWithReducePkValue(BuffEffectVO buffEffect)
	{
		PropDao propdao = new PropDao();
		String propName = propdao.getPropNameByType(buffEffect.getBuffId()+"");
		
		int buffTime = buffEffect.getBuffTime();
		String info = "您"+propName+"的效果已经结束了!";
		SystemInfoService sysInfoSerice = new SystemInfoService();
		sysInfoSerice.insertSystemInfoBySystem(buffEffect.getEffectObject(),info, buffTime*60);
	}

	/**
	 * 发HP的BUFF信息给玩家,在玩家效果结束时以及结束前两分钟发,
	 * 并删除之前的相关信息
	 * @param buffEffect
	 */
	private void dealWithHPBuff(BuffEffectVO buffEffect)
	{
		this.dealWithHPBuff(buffEffect.getEffectObject(), buffEffect.getEffectObjectType(), buffEffect.getBuffId());
	}	
	
	/**
	 * 发HP的BUFF信息给玩家,在玩家效果结束时以及结束前两分钟发,
	 * 并删除之前的相关信息
	 * @param buffEffect
	 */
	public void dealWithHPBuff (int object_id,int object_type, int buff_id)
	{
		BuffDao buffDao = new BuffDao();
		BuffVO buff = buffDao.getBuff(buff_id);
		if (buff == null)
		{
			DataErrorLog.debugData("BuffEffectService.dealWithHPBuff:无效buffId="+buff_id);
		}
		
		BuffEffectVO buffEffect = (BuffEffectVO) buff;
		buffEffect.setBuffUseMode(buff.getBuffUseMode());
		buffEffect.setEffectObject(object_id);// 作用对象
		buffEffect.setEffectObjectType(object_type);// 作用对象类型

		buffEffect.setBuffTime(buff.getBuffTime());
		buffEffect.setSpareBout(buff.getBuffBout());
		
		
		PropDao propdao = new PropDao();
		String propName = propdao.getPropNameByType(buffEffect.getBuffId()+"");
		String info1 = "您的"+propName+"效果还有二分钟就结束了!";
		String info2 = "您的"+propName+"效果结束了!";
		 
		int buffTime = buffEffect.getBuffTime();
		
		SystemInfoService sysInfoSerice = new SystemInfoService();
		if (sysInfoSerice.selectByPPkInfo(buffEffect.getEffectObject(), propName)) {
			sysInfoSerice.updateByPPkInfo(buffEffect.getEffectObject(),propName,buffTime);
		} else {
			sysInfoSerice.deleteByPPkInfo(buffEffect.getEffectObject(),propName);
			sysInfoSerice.insertSystemInfoBySystem(buffEffect.getEffectObject(),info2, buffTime*60);
			sysInfoSerice.insertSystemInfoBySystem(buffEffect.getEffectObject(),info1, (buffTime-2)*60);
			
		}	
	} 
	
	
	/**
	 * 清楚某一实体的所有buff
	 */
	public void clearBuffEffect( int object_id,int object_type )
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		buffEffectDao.clearBuffEffect(object_id, object_type);
	}
	
	
	
	/**
	 * 监听buff状态，去掉无效的buff
	 */
	public void listenBuffEffectStat( List<BuffEffectVO> buffEffects)
	{
		if( buffEffects!=null )
		{
			BuffEffectVO buffEffect = null;
			for (int i = 0; i < buffEffects.size(); i++)
			{
				buffEffect = buffEffects.get(i);
				if( !isEffected(buffEffect) )
				{
					buffEffects.remove(i);
				}
			}
		}
	}
	/**
	 * buff是否有效
	 * @param buffEffects
	 * @return
	 */
	public boolean isEffected(BuffEffectVO buffEffect)
	{
		if( buffEffect==null )
		{
			return false;
		}
		if( buffEffect.getBuffBout()!=0 )//回合控制buff
		{
			return validateBoutBuff(buffEffect);
		}
		else if( buffEffect.getBuffTime()!=0 )//时间控制buff
		{
			return validateTimeBuff(buffEffect);
		}
		return true;
	}
	
	/**BUFF消失发送邮件给玩家*/
	
	/*private void sendBuffMail(BuffEffectVO buffEffect){
		if(buffEffect.getEffectObjectType()==11){
			MailInfoService mailInfoService = new MailInfoService();
			String content = buffEffect.getBuffName()+"效果消失了!";
			String title = buffEffect.getBuffName()+"效果";
			mailInfoService.sendMailBySystem(buffEffect.getEffectObject(), title, content); 
		}
	}*/
	
	
	/**
	 * 验证时间制的buff 是否失效
	 * @param buffEffect
	 * @return true表示有效
	 */
	private boolean validateTimeBuff(BuffEffectVO buffEffect)
	{
		boolean result = false;
		if( buffEffect==null )
		{
			return  true;
		}
		if( DateUtil.isOverdue(buffEffect.getUseTime(), buffEffect.getBuffTime()*60) ) 
		{
			// buff效果过期
			invalidateForPlayer(buffEffect);
			result = false;
		}
		else
		{
			 result = true;
		}
		return  result;
	}

	/**
	 * 验证回合制的buff 是否失效
	 * @param buffEffect
	 * @return true表示有效
	 */
	private boolean validateBoutBuff(BuffEffectVO buffEffect)
	{
		boolean result = true;
		if( buffEffect==null )
		{
			return  true;
		}
		if( buffEffect.getSpareBout()>=1 )
		{
			result = true;
		}
		else
		{
			invalidateForPlayer(buffEffect);
			result = false;
		}
		return  result;
	}
	
	/**
	 * buff效果失效
	 */
	public void invalidateForPlayer(BuffEffectVO buffEffect)
	{
		if( buffEffect==null )
		{
			return;
		}
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		buffEffectDao.deleteByID(buffEffect.getBfPk());
		
		//判断是否出现弹出是消息
		boolean pop_msg = getIsUsedByTime(buffEffect.getEffectObject(), buffEffect.getBuffId());
		if(pop_msg == false){
			SysInfoDao dao = new SysInfoDao();
			SystemInfoService systemInfoService = new SystemInfoService();
			SystemControlInfoVO vo = dao.getSystemInfoByTypeAndID("9", buffEffect.getBuffId()+"");
			systemInfoService.insertPopMsggse(buffEffect.getEffectObject(), vo.getSendContent());
		}
		//在buff销毁时，可能要做的一些处理
		switch (buffEffect.getBuffType()) {
			case BuffType.HP_UP :
				return;
			/*case BuffType.VIPBUFF ://如果是VIP的BUFF 那么执行以下方法
				VipService vipService = new VipService();
				vipService.deleteRoleVip(buffEffect.getEffectObject());
				return;*/
		}
	}
	
	
	/**
	 *  更新剩余的回合数
	 */
	public void updateSpareBout(int p_pk)
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		buffEffectDao.updateSpareBout(p_pk);
	}
	
	
	/**
	 * 给玩家加载指定类型的buff效果
	 */
	public void loadBuffEffectOfPlayer( PartInfoVO player ,int buff_type)
	{
		if( player==null )
		{
			return;
		}
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		BuffEffectVO buffEffect = buffEffectDao.getBuffEffectByBuffType(player.getPPk(), BuffSystem.PLAYER,buff_type);
		updatePropertyOfPlayer(player,buffEffect);
	}
	
	
	/**
	 * 给玩家加载所有buff效果
	 */
	public String  loadBuffEffectOfPlayer( PartInfoVO player)
	{
		if( player==null )
		{
			return null;
		}
		StringBuffer effect_describe = new StringBuffer(); 
		
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		List<BuffEffectVO> buffEffects = buffEffectDao.getBuffEffects(player.getPPk(),BuffSystem.PLAYER);
		listenBuffEffectStat(buffEffects);
		if( buffEffects!=null )
		{
			for ( BuffEffectVO buffEffect:buffEffects)
			{
				effect_describe.append(updatePropertyOfPlayer(player,buffEffect));
			}
		}
		return effect_describe.toString();
	}
	
	/**
	 * 给npc加载所有buff效果
	 */
	public void loadBuffEffectOfNPC( NpcAttackVO npc)
	{
		if( npc==null )
		{
			return;
		}
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		List<BuffEffectVO> buffEffects = buffEffectDao.getBuffEffects(npc.getID(),BuffSystem.NPC);
		listenBuffEffectStat(buffEffects);
		if( buffEffects!=null )
		{
			for (BuffEffectVO buffEffect:buffEffects)
			{
				updatePropertyOfNPC(npc,buffEffect);
			}
		}
	}
	
	
	/**
	 * 根据buff效果更新玩家对应的属性值
	 * @param player
	 * @param buffEffect          
	 * @return              返回
	 */
	public String updatePropertyOfPlayer( PartInfoVO player,BuffEffectVO buffEffect)
	{
		if( player==null || buffEffect==null )
		{
			return null;
		}
		
		String effect_describe = null;
		
		if( buffEffect.getBuffUseMode()==2)
		{
			//debuff
			buffEffect.setBuffEffectValue(-buffEffect.getBuffEffectValue());
		}
		switch (buffEffect.getBuffType())
		{
			case BuffType.CHANGER_HP:
			{
				int change_value = buffEffect.getBuffEffectValue();
				player.setPHp(player.getPHp()+change_value);
				if( buffEffect.getBuffUseMode()==BuffSystem.DEBUFF )
				{
					effect_describe = "中毒伤害"+change_value+"点";
				}
				break;
			}
			case BuffType.CHANGER_MP:
			{
				int change_value = buffEffect.getBuffEffectValue();
				player.setPMp(player.getPMp()+change_value);
				break;
			}
			case BuffType.ATTACK:
			{
				int change_value =buffEffect.getBuffEffectValue();
				player.setPGj(player.getPGj()+change_value);
				break;
			}
			case BuffType.DEFENCE:
			{
				int change_value =buffEffect.getBuffEffectValue();
				player.setPFy(player.getPFy()+change_value);
				break;
			}
    		case BuffType.HP_UP:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.setPUpHp(player.getPUpHp()+change_value);
    			break;
    		}
    		case BuffType.MP_UP:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.setPUpMp(player.getPUpMp()+change_value);
    			break;
    		}
    		//*********************五行攻击的buff
    		case BuffType.JIN_ATTACK:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setJinGj(player.getWx().getJinGj()+change_value);
    			break;
    		}
    		case BuffType.MU_ATTACK:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setMuGj(player.getWx().getMuGj()+change_value);
    			break;
    		}
    		case BuffType.SHUI_ATTACK:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setShuiGj(player.getWx().getShuiGj()+change_value);
    			break;
    		}
    		case BuffType.HUO_ATTACK:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setHuoGj(player.getWx().getHuoGj()+change_value);
    			break;
    		}
    		case BuffType.TU_ATTACK:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setTuGj(player.getWx().getTuGj()+change_value);
    			break;
    		}
    		//************************************五行防御的buff
    		case BuffType.JIN_DEFENCE:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setJinFy(player.getWx().getJinFy()+change_value);
    			break;
    		}
    		case BuffType.MU_DEFENCE:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setMuFy(player.getWx().getMuFy()+change_value);
    			break;
    		}
    		case BuffType.SHUI_DEFENCE:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setShuiFy(player.getWx().getShuiFy()+change_value);
    			break;
    		}
    		case BuffType.HUO_DEFENCE:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setHuoFy(player.getWx().getHuoFy()+change_value);
    			break;
    		}
    		case BuffType.TU_DEFENCE:
    		{
    			int change_value = buffEffect.getBuffEffectValue();
    			player.getWx().setTuFy(player.getWx().getTuFy()+change_value);
    			break;
    		}
    		
    		case BuffType.ADD_CATCH_PROBABILITY://增加捕获概率
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendCatchProbability(player.getAppendCatchProbability()+change_value);
    			break;
    		}
    		case BuffType.ADD_DROP_PROBABILITY://增加掉落概率
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendDropProbability(player.getAppendDropProbability()+change_value);
    			break;
    		}
    		case BuffType.ADD_EXP://经验加成
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendExp(player.getAppendExp()+change_value);
    			break;
    		}
    		case BuffType.ADD_NONSUCH_PROBABILITY://增加极品装备掉落概率
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendNonsuchProbability(player.getAppendNonsuchProbability()+change_value);
    			break;
    		}
    		
    		case BuffType.IMMUNITY_POISON://**是否免疫中毒
    		{
    			if( buffEffect.getBuffEffectValue()==1 )
    			{
    				player.setImmunityPoison(true);
    			}
    			break;
    		}
    		case BuffType.IMMUNITY__DIZZY://**是否免疫击晕
    		{
    			if( buffEffect.getBuffEffectValue()==1 )
    			{
    				player.setImmunityDizzy(true);
    			}
    			break;
    		}
    		case BuffType.PET_EXP://**增加宠物得到的经验
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendPetExp(player.getAppendPetExp()+change_value);
    			break;
    		}
    		case BuffType.CHANGER_BJ://暴击率
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setDropMultiple(player.getDropMultiple()*change_value/100);
    			if(player.getDropMultiple()>100){
    				player.setDropMultiple(100);
    			}
    			break;
    		}
		}
		return effect_describe;
	}
	
	
	/**
	 * 根据buff效果更新NPC对应的属性值
	 * @param buffEffect
	 */
	public void updatePropertyOfNPC( NpcAttackVO npc,BuffEffectVO buffEffect)
	{
		if( npc==null || buffEffect==null )
		{
			return;
		}
		
		if( buffEffect.getBuffUseMode()==2)
		{
			buffEffect.setBuffEffectValue(-buffEffect.getBuffEffectValue());
		}
		
		switch (buffEffect.getBuffType())
		{
			case BuffType.CHANGER_HP:
			{
				int change_value = buffEffect.getBuffEffectValue();
				npc.setCurrentHP(npc.getCurrentHP()+change_value);
				break;
			}
			case BuffType.ATTACK:
			{
				int change_value =buffEffect.getBuffEffectValue();
				npc.setAppendGj(change_value);
				break;
			}
			case BuffType.DEFENCE:
			{
				int change_value =buffEffect.getBuffEffectValue();
				npc.setDefenceXiao(npc.getDefenceXiao()+change_value);
				npc.setDefenceDa(npc.getDefenceDa()+change_value);
				break;
			}
			
			//*********************五行攻击的buff
			case BuffType.JIN_ATTACK:
			{
				/*int change_value = buffEffect.getBuffEffectValue();
				npc.getWx().setJinGj(npc.getWx().getJinGj()+change_value);
				break;*/
			}
			case BuffType.MU_ATTACK:
			{
				/*int change_value = buffEffect.getBuffEffectValue();
				npc.getWx().setMuGj(npc.getWx().getMuGj()+change_value);
				break;*/
			}
			case BuffType.SHUI_ATTACK:
			{
				/*int change_value = buffEffect.getBuffEffectValue();
				npc.getWx().setShuiGj(npc.getWx().getShuiGj()+change_value);
				break;*/
			}
			case BuffType.HUO_ATTACK:
			{
				/*int change_value = buffEffect.getBuffEffectValue();
				npc.getWx().setHuoGj(npc.getWx().getHuoGj()+change_value);
				break;*/
			}
			case BuffType.TU_ATTACK:
			{
				/*int change_value = buffEffect.getBuffEffectValue();
				npc.getWx().setTuGj(npc.getWx().getTuGj()+change_value);
				break;*/
			}
			//************************************增加五行防御的buff
			case BuffType.JIN_DEFENCE:
			{
				int change_value = buffEffect.getBuffEffectValue();
				npc.setJinFy(npc.getJinFy()+change_value);
				break;
			}
			case BuffType.MU_DEFENCE:
			{
				int change_value = buffEffect.getBuffEffectValue();
				npc.setMuFy(npc.getMuFy()+change_value);
				break;
			}
			case BuffType.SHUI_DEFENCE:
			{
				int change_value = buffEffect.getBuffEffectValue();
				npc.setShuiFy(npc.getShuiFy()+change_value);
				break;
			}
			case BuffType.HUO_DEFENCE:
			{
				int change_value = buffEffect.getBuffEffectValue();
				npc.setHuoFy(npc.getHuoFy()+change_value);
				break;
			}
			case BuffType.TU_DEFENCE:
			{
				int change_value = buffEffect.getBuffEffectValue();
				npc.setTuFy(npc.getTuFy()+change_value);
				break;
			}
		}
	}
	
	/**
	 * 得到玩家buff列表
	 * @param p_pk
	 * @return
	 */
	public List<BuffEffectVO> getBuffList( int p_pk )
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		return buffEffectDao.getBuffEffects(p_pk, BuffSystem.PLAYER);
	}
	
	
	/**
	 * 得到玩家buff列表描述
	 * @param p_pk
	 * @return
	 */
	public String getBuffListDescribe( int p_pk )
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		return buffEffectDao.getBuffListDescribe(p_pk, BuffSystem.PLAYER);
	}
	
	/**
	 * 得到玩家buff列表描述
	 * @param p_pk
	 * @return
	 */
	public String getBuffListWml( int p_pk )
	{
		String wml = null;
		StringBuffer result = new StringBuffer();
		List<BuffEffectVO> buff_list =  getBuffList( p_pk );
		
		for(BuffEffectVO buff:buff_list)
		{
			result.append(buff.getBuffName()+":");
			result.append(getLeftTime(buff.getBuffTime(),buff.getUseTime()));
			result.append("<br/>");
		}
		wml = result.toString();
		if( wml.equals(""))
		{
			wml = StringUtil.gbToISO("无");
		}
		return wml;
	}

	/**
	 * 获取还剩余多少时间的字符串
	 * @param buffTime
	 * @param useTime
	 * @return
	 */
	private String getLeftTime(long buffTime, Date useTime)
	{
		if(buffTime == 0) {
		}
		if(useTime == null ){
		}
		
		String result = "";
		long useTimes = useTime.getTime();
		long nowTimes = (new Date()).getTime();
		if(nowTimes > useTimes + (buffTime * 60 * 1000)) {
			result = "时间已到";
		}else {
			long nohave = (nowTimes - useTimes)/(1000*60);
			int has = (int)(buffTime - nohave);
			result = "还有"+DateUtil.returnTimeStr(has)+"";
		}
		return StringUtil.gbToISO(result);
	}

	/**
	 * 返回身上是否有道具上所带的同样buff
	 * @param pk
	 * @param parseInt
	 * @return
	 */
	public BuffEffectVO checkHasSameBuffType(int p_pk, int pg_pk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(p_pk+"");
		
		PropVO prop = null;
		PlayerPropGroupVO propGroup = null;

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		
		propGroup = propGroupDao.getByPgPk(pg_pk);
		prop = PropCache.getPropById(propGroup.getPropId());// 得到道具详细信息
		
		PropUseEffect propUseEffect = new PropUseEffect();
		// ************道具是否可以使用判断 **************//
		// 道具使用基本判断
		PropUseService propUseService = new PropUseService();
		String resutl = propUseService.isPropUseByBasicCondition(role_info, prop, 1);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return null;
		}
		// ************道具是否可以使用判断结束**************//

		// 得到道具功能控制字段
		String buff_id_ss = prop.getPropOperate1();
		
		String[] buff_id = buff_id_ss.split(",");
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		
		for (int i = 0; i < buff_id.length; i++) {
    		BuffDao buffDao = new BuffDao();
    		int buffType = buffDao.getBuffType(buff_id[i]);
    		// 如果返回值大于-1,代表已经有这种buff,返回这种buff的ID
    		///int result = buffEffectDao.hasAlreadyBuff(role_info.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
    		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(role_info.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
    		if( buffEffectVO != null && buffEffectVO.getBuffId() != Integer.parseInt(buff_id[i])) {
    				return buffEffectVO;
    		}
		}		
		return null;
	}
	
	/**
	 * 如果身上已经有相关的buff状态就给返回-1.
	 * @param pk
	 * @param parseInt
	 * @return
	 
	public int useManyBuffProp1(int p_pk, int pg_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoById(p_pk+"");
		
		PropVO prop = null;
		PlayerPropGroupVO propGroup = null;

		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PropDao propDao = new PropDao();
		
		logger.info("pg_pk="+pg_pk);
		propGroup = propGroupDao.getByPgPk(pg_pk);
		logger.info("propId="+propGroup.getPropId());
		prop = propDao.getById(propGroup.getPropId());// 得到道具详细信息
		
		PropUseEffect propUseEffect = new PropUseEffect();
		// ************道具是否可以使用判断 **************
		// 道具使用基本判断
		PropUseService propUseService = new PropUseService();
		String resutl = propUseService.isPropUseByBasicCondition(role_info, prop, 1);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return 0;
		}
		// ************道具是否可以使用判断结束**************

		// 得到道具功能控制字段
		String buff_id_ss = prop.getPropOperate1();
		
		String[] buff_id = buff_id_ss.split(",");
		
		for (int i = 0; i < buff_id.length; i++) {
    		BuffDao buffDao = new BuffDao();
    		int buffType = buffDao.getBuffType(buff_id[i]);
    		BuffEffectDao buffEffectDao = new BuffEffectDao();
    		
    		//int result = buffEffectDao.hasAlreadyBuff(role_info.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
    		//if( result == -1) 
    		//	return result;
		}
		
		return 0;
	}*/

	/**
	 * 删除同类型的buff
	 * @param pk
	 * @param player
	 * @param parseInt
	 */
	public void deleteSameBuff(PartInfoVO player, PlayerPropGroupVO propGroup)
	{
		PropVO prop = PropCache.getPropById(propGroup.getPropId());// 得到道具详细信息
		
		// 得到道具功能控制字段
		String buff_id_s = prop.getPropOperate1();
		String[] buff_id = null;
		if(buff_id_s != null && !buff_id_s.equals("")) {
			buff_id = buff_id_s.split(",");
			for(int i=0;i<buff_id.length;i++) {
				BuffDao buffDao = new BuffDao();
				int buffType = buffDao.getBuffType(buff_id[i]);
				BuffEffectDao buffEffectDao = new BuffEffectDao();
		
				buffEffectDao.deleteAlreadyBuff(player.getPPk(),BuffSystem.PLAYER, buffType);
			}
		}
	}

	/**
	 * 确定能否进行传送,返回true表示不能传送,false表示可以传送
	 * @param roleEntity
	 * @return
	 */
	public boolean checkCanCarry(RoleEntity roleEntity)
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		
		List<BuffEffectVO> list = buffEffectDao.getBuffEffects(roleEntity.getBasicInfo().getPPk(),BuffSystem.PLAYER);
		BuffEffectVO vo = null;
		if ( list != null && list.size() != 0 ) {
			listenBuffEffectStat(list);
			for ( int i = 0;i < list.size();i++) {
				vo = list.get(i);
				if ( vo.getBuffType() == BuffType.NOPERMISSCHUANSONG) {
					return true;
				}
			}
		}
		return false;
	}
	
	//判断该BUFF玩家爱是否使用过
	private boolean getIsUsedByTime(int p_pk, int buff_id)
	{
		TimeControlService timeControlService = new TimeControlService();
		boolean skill_used = timeControlService.isUseableByAll(p_pk, buff_id,
				TimeControlService.BUFFPOPMSG, 1);
		if (skill_used == true)
		{
			return true;
		}
		else
		{
			timeControlService.updateControlInfoByAll(p_pk,buff_id,TimeControlService.BUFFPOPMSG);
			return false;
		}
	}
}
