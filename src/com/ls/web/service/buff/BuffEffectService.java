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
 * ����:buffЧ������
 * @author ��˧ 3:11:25 PM
 */
public class BuffEffectService
{

	/**
	 * ��������һ��buff Ч��
	 * @param object_id
	 * @param buff_id
	 */
	public void createRoleBuffEffect(int p_pk, int buff_id)
	{
		this.createBuffEffect(p_pk, BuffSystem.PLAYER, buff_id);
	}
	
	/**
	 * ����һ��buffЧ��
	 * @param object_id   ���ö���id
	 * @param object_type ���ö�������
	 * @param buff_id
	 */
	public void createBuffEffect(int object_id,int object_type, int buff_id)
	{
		BuffDao buffDao = new BuffDao();
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		BuffVO buff = buffDao.getBuff(buff_id);
		if (buff == null)
		{
			DataErrorLog.debugData("BuffEffectService.createBuffEffect:��ЧbuffId="+buff_id);
		}
		
		BuffEffectVO buffEffect = (BuffEffectVO) buff;
		buffEffect.setBuffUseMode(buff.getBuffUseMode());
		buffEffect.setEffectObject(object_id);// ���ö���
		buffEffect.setEffectObjectType(object_type);// ���ö�������

		buffEffect.setBuffTime(buff.getBuffTime());
		buffEffect.setSpareBout(buff.getBuffBout());
		
		dealWithBuffEffect (buffEffect);

		buffEffectDao.add(buffEffect);// ���buffЧ��
	}
	
	/**
	 * ����һ��buffЧ��
	 * @param object_id   ���ö���id
	 * @param object_type ���ö�������
	 * @param buff_id
	 * @param time ʱ��,�Է���Ϊ��λ
	 */
	public void createBuffEffect(int object_id,int object_type, int buff_id,int time)
	{
		BuffDao buffDao = new BuffDao();
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		BuffVO buff = buffDao.getBuff(buff_id);
		if (buff == null)
		{
			DataErrorLog.debugData("BuffEffectService.createBuffEffect:��ЧbuffId="+buff_id);
		}
		
		BuffEffectVO buffEffect = (BuffEffectVO) buff;
		buffEffect.setBuffUseMode(buff.getBuffUseMode());
		buffEffect.setEffectObject(object_id);// ���ö���
		buffEffect.setEffectObjectType(object_type);// ���ö�������

		buffEffect.setBuffTime(time);
		buffEffect.setSpareBout(buff.getBuffBout());
		
		dealWithBuffEffect (buffEffect);

		buffEffectDao.add(buffEffect);// ���buffЧ��
	}

	/**
	 * �ڴ���ʱ���buff������п���Ҫ����ͬ�Ĵ���
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
	 * �ڴ���ʱ�������pkֵ��buff
	 * @param buffEffect
	 */
	private void dealWithReducePkValue(BuffEffectVO buffEffect)
	{
		PropDao propdao = new PropDao();
		String propName = propdao.getPropNameByType(buffEffect.getBuffId()+"");
		
		int buffTime = buffEffect.getBuffTime();
		String info = "��"+propName+"��Ч���Ѿ�������!";
		SystemInfoService sysInfoSerice = new SystemInfoService();
		sysInfoSerice.insertSystemInfoBySystem(buffEffect.getEffectObject(),info, buffTime*60);
	}

	/**
	 * ��HP��BUFF��Ϣ�����,�����Ч������ʱ�Լ�����ǰ�����ӷ�,
	 * ��ɾ��֮ǰ�������Ϣ
	 * @param buffEffect
	 */
	private void dealWithHPBuff(BuffEffectVO buffEffect)
	{
		this.dealWithHPBuff(buffEffect.getEffectObject(), buffEffect.getEffectObjectType(), buffEffect.getBuffId());
	}	
	
	/**
	 * ��HP��BUFF��Ϣ�����,�����Ч������ʱ�Լ�����ǰ�����ӷ�,
	 * ��ɾ��֮ǰ�������Ϣ
	 * @param buffEffect
	 */
	public void dealWithHPBuff (int object_id,int object_type, int buff_id)
	{
		BuffDao buffDao = new BuffDao();
		BuffVO buff = buffDao.getBuff(buff_id);
		if (buff == null)
		{
			DataErrorLog.debugData("BuffEffectService.dealWithHPBuff:��ЧbuffId="+buff_id);
		}
		
		BuffEffectVO buffEffect = (BuffEffectVO) buff;
		buffEffect.setBuffUseMode(buff.getBuffUseMode());
		buffEffect.setEffectObject(object_id);// ���ö���
		buffEffect.setEffectObjectType(object_type);// ���ö�������

		buffEffect.setBuffTime(buff.getBuffTime());
		buffEffect.setSpareBout(buff.getBuffBout());
		
		
		PropDao propdao = new PropDao();
		String propName = propdao.getPropNameByType(buffEffect.getBuffId()+"");
		String info1 = "����"+propName+"Ч�����ж����Ӿͽ�����!";
		String info2 = "����"+propName+"Ч��������!";
		 
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
	 * ���ĳһʵ�������buff
	 */
	public void clearBuffEffect( int object_id,int object_type )
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		buffEffectDao.clearBuffEffect(object_id, object_type);
	}
	
	
	
	/**
	 * ����buff״̬��ȥ����Ч��buff
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
	 * buff�Ƿ���Ч
	 * @param buffEffects
	 * @return
	 */
	public boolean isEffected(BuffEffectVO buffEffect)
	{
		if( buffEffect==null )
		{
			return false;
		}
		if( buffEffect.getBuffBout()!=0 )//�غϿ���buff
		{
			return validateBoutBuff(buffEffect);
		}
		else if( buffEffect.getBuffTime()!=0 )//ʱ�����buff
		{
			return validateTimeBuff(buffEffect);
		}
		return true;
	}
	
	/**BUFF��ʧ�����ʼ������*/
	
	/*private void sendBuffMail(BuffEffectVO buffEffect){
		if(buffEffect.getEffectObjectType()==11){
			MailInfoService mailInfoService = new MailInfoService();
			String content = buffEffect.getBuffName()+"Ч����ʧ��!";
			String title = buffEffect.getBuffName()+"Ч��";
			mailInfoService.sendMailBySystem(buffEffect.getEffectObject(), title, content); 
		}
	}*/
	
	
	/**
	 * ��֤ʱ���Ƶ�buff �Ƿ�ʧЧ
	 * @param buffEffect
	 * @return true��ʾ��Ч
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
			// buffЧ������
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
	 * ��֤�غ��Ƶ�buff �Ƿ�ʧЧ
	 * @param buffEffect
	 * @return true��ʾ��Ч
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
	 * buffЧ��ʧЧ
	 */
	public void invalidateForPlayer(BuffEffectVO buffEffect)
	{
		if( buffEffect==null )
		{
			return;
		}
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		buffEffectDao.deleteByID(buffEffect.getBfPk());
		
		//�ж��Ƿ���ֵ�������Ϣ
		boolean pop_msg = getIsUsedByTime(buffEffect.getEffectObject(), buffEffect.getBuffId());
		if(pop_msg == false){
			SysInfoDao dao = new SysInfoDao();
			SystemInfoService systemInfoService = new SystemInfoService();
			SystemControlInfoVO vo = dao.getSystemInfoByTypeAndID("9", buffEffect.getBuffId()+"");
			systemInfoService.insertPopMsggse(buffEffect.getEffectObject(), vo.getSendContent());
		}
		//��buff����ʱ������Ҫ����һЩ����
		switch (buffEffect.getBuffType()) {
			case BuffType.HP_UP :
				return;
			/*case BuffType.VIPBUFF ://�����VIP��BUFF ��ôִ�����·���
				VipService vipService = new VipService();
				vipService.deleteRoleVip(buffEffect.getEffectObject());
				return;*/
		}
	}
	
	
	/**
	 *  ����ʣ��Ļغ���
	 */
	public void updateSpareBout(int p_pk)
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		buffEffectDao.updateSpareBout(p_pk);
	}
	
	
	/**
	 * ����Ҽ���ָ�����͵�buffЧ��
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
	 * ����Ҽ�������buffЧ��
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
	 * ��npc��������buffЧ��
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
	 * ����buffЧ��������Ҷ�Ӧ������ֵ
	 * @param player
	 * @param buffEffect          
	 * @return              ����
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
					effect_describe = "�ж��˺�"+change_value+"��";
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
    		//*********************���й�����buff
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
    		//************************************���з�����buff
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
    		
    		case BuffType.ADD_CATCH_PROBABILITY://���Ӳ������
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendCatchProbability(player.getAppendCatchProbability()+change_value);
    			break;
    		}
    		case BuffType.ADD_DROP_PROBABILITY://���ӵ������
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendDropProbability(player.getAppendDropProbability()+change_value);
    			break;
    		}
    		case BuffType.ADD_EXP://����ӳ�
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendExp(player.getAppendExp()+change_value);
    			break;
    		}
    		case BuffType.ADD_NONSUCH_PROBABILITY://���Ӽ�Ʒװ���������
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendNonsuchProbability(player.getAppendNonsuchProbability()+change_value);
    			break;
    		}
    		
    		case BuffType.IMMUNITY_POISON://**�Ƿ������ж�
    		{
    			if( buffEffect.getBuffEffectValue()==1 )
    			{
    				player.setImmunityPoison(true);
    			}
    			break;
    		}
    		case BuffType.IMMUNITY__DIZZY://**�Ƿ����߻���
    		{
    			if( buffEffect.getBuffEffectValue()==1 )
    			{
    				player.setImmunityDizzy(true);
    			}
    			break;
    		}
    		case BuffType.PET_EXP://**���ӳ���õ��ľ���
    		{
    			int change_value = (buffEffect.getBuffEffectValue());
    			player.setAppendPetExp(player.getAppendPetExp()+change_value);
    			break;
    		}
    		case BuffType.CHANGER_BJ://������
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
	 * ����buffЧ������NPC��Ӧ������ֵ
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
			
			//*********************���й�����buff
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
			//************************************�������з�����buff
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
	 * �õ����buff�б�
	 * @param p_pk
	 * @return
	 */
	public List<BuffEffectVO> getBuffList( int p_pk )
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		return buffEffectDao.getBuffEffects(p_pk, BuffSystem.PLAYER);
	}
	
	
	/**
	 * �õ����buff�б�����
	 * @param p_pk
	 * @return
	 */
	public String getBuffListDescribe( int p_pk )
	{
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		return buffEffectDao.getBuffListDescribe(p_pk, BuffSystem.PLAYER);
	}
	
	/**
	 * �õ����buff�б�����
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
			wml = StringUtil.gbToISO("��");
		}
		return wml;
	}

	/**
	 * ��ȡ��ʣ�����ʱ����ַ���
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
			result = "ʱ���ѵ�";
		}else {
			long nohave = (nowTimes - useTimes)/(1000*60);
			int has = (int)(buffTime - nohave);
			result = "����"+DateUtil.returnTimeStr(has)+"";
		}
		return StringUtil.gbToISO(result);
	}

	/**
	 * ���������Ƿ��е�����������ͬ��buff
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
		prop = PropCache.getPropById(propGroup.getPropId());// �õ�������ϸ��Ϣ
		
		PropUseEffect propUseEffect = new PropUseEffect();
		// ************�����Ƿ����ʹ���ж� **************//
		// ����ʹ�û����ж�
		PropUseService propUseService = new PropUseService();
		String resutl = propUseService.isPropUseByBasicCondition(role_info, prop, 1);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return null;
		}
		// ************�����Ƿ����ʹ���жϽ���**************//

		// �õ����߹��ܿ����ֶ�
		String buff_id_ss = prop.getPropOperate1();
		
		String[] buff_id = buff_id_ss.split(",");
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		
		for (int i = 0; i < buff_id.length; i++) {
    		BuffDao buffDao = new BuffDao();
    		int buffType = buffDao.getBuffType(buff_id[i]);
    		// �������ֵ����-1,�����Ѿ�������buff,��������buff��ID
    		///int result = buffEffectDao.hasAlreadyBuff(role_info.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
    		BuffEffectVO buffEffectVO = buffEffectDao.getBuffEffectByBuffType(role_info.getBasicInfo().getPPk(),BuffSystem.PLAYER, buffType);
    		if( buffEffectVO != null && buffEffectVO.getBuffId() != Integer.parseInt(buff_id[i])) {
    				return buffEffectVO;
    		}
		}		
		return null;
	}
	
	/**
	 * ��������Ѿ�����ص�buff״̬�͸�����-1.
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
		prop = propDao.getById(propGroup.getPropId());// �õ�������ϸ��Ϣ
		
		PropUseEffect propUseEffect = new PropUseEffect();
		// ************�����Ƿ����ʹ���ж� **************
		// ����ʹ�û����ж�
		PropUseService propUseService = new PropUseService();
		String resutl = propUseService.isPropUseByBasicCondition(role_info, prop, 1);
		if (resutl != null)
		{
			propUseEffect.setIsEffected(false);
			propUseEffect.setNoUseDisplay(resutl);
			return 0;
		}
		// ************�����Ƿ����ʹ���жϽ���**************

		// �õ����߹��ܿ����ֶ�
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
	 * ɾ��ͬ���͵�buff
	 * @param pk
	 * @param player
	 * @param parseInt
	 */
	public void deleteSameBuff(PartInfoVO player, PlayerPropGroupVO propGroup)
	{
		PropVO prop = PropCache.getPropById(propGroup.getPropId());// �õ�������ϸ��Ϣ
		
		// �õ����߹��ܿ����ֶ�
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
	 * ȷ���ܷ���д���,����true��ʾ���ܴ���,false��ʾ���Դ���
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
	
	//�жϸ�BUFF��Ұ��Ƿ�ʹ�ù�
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
