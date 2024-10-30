package com.ls.web.service.goods.equip;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.dao.goods.equip.EquipAppendAttributeDao;
import com.ls.ben.dao.goods.equip.EquipRelelaDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.equip.EquipAppendAttributeVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.player.EconomyService;
import com.ls.web.service.player.RoleService;
import com.pm.constant.SystemInfoType;
import com.pm.service.mail.MailInfoService;
import com.pm.service.systemInfo.SystemInfoService;
import com.web.service.popupmsg.PopUpMsgService;


/**
 * ����:װ����ز���
 * 
 * @author ��˧ 10:03:12 PM
 */
public class EquipService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * ����װ����ӵ����
	 * @param new_owner		����֮���ӵ����
	 * @param pwPk			װ��id
	 * @return
	 */
	public String updateOwner( RoleEntity new_owner,int pwPk,int gain_type )
	{
		GoodsService goodsService = new GoodsService();
		PlayerEquipVO equip = goodsService.getEquipByID(pwPk);
		RoleEntity old_owner = RoleService.getRoleInfoById(equip.getPPk());
		return this.updateOwner(old_owner, new_owner, equip,gain_type);
	}
	
	/**
	 * ����װ����ӵ����
	 * @param old_owner 		����֮ǰ��ӵ����
	 * @param new_owner			����֮���ӵ����
	 * @param equip				���ĵ�װ��
	 * @return
	 */
	public String updateOwner( RoleEntity old_owner,RoleEntity new_owner,PlayerEquipVO equip,int gain_type )
	{
		String hint = null;
		
		if( new_owner==null || equip==null )
		{
			return "���ݴ���";
		}
		
		if( old_owner!=null )//�����װ����ӵ����
		{
			hint = equip.isOwnByPPk(old_owner.getBasicInfo().getPPk());
			if( hint !=null )
			{
				return hint;
			}
		}
		
		hint = new_owner.getBasicInfo().isEnoughWrapSpace(1);
		if( hint !=null )
		{
			return hint;
		}
		
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updateOwner(equip.getPwPk(), new_owner.getBasicInfo().getPPk());
		addWrapSpare(new_owner.getBasicInfo().getPPk(), -1);
		if( old_owner!=null )
		{
			addWrapSpare(old_owner.getBasicInfo().getPPk(), 1);
		}
		GameLogManager.getInstance().equipLog(new_owner.getPPk(), equip, gain_type);
		return hint;
	}
	
	/**
	 * ����װ��
	 */
	public String protectEquip( RoleEntity roleInfo,PlayerEquipVO equip,String pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		String hint = equip.isOwnByPPk(roleInfo.getBasicInfo().getPPk());
		if( hint!=null )
		{
			return hint;
		}
		
		goodsService.removeProps(propGroup, 1);
		String protect_time_str = propGroup.getPropInfo().getPropOperate1();//������ʱ��
		int protect_time = 0;
		try{
			protect_time = Integer.parseInt(protect_time_str);
		}
		catch (Exception e) {
			DataErrorLog.prop(propGroup.getPropId(),"operate1����"+protect_time_str);
		}
		
		equip.protectEquip(protect_time);
		
		return "��ϲ��,�ѽ�"+equip.getFullName()+"װ���ɹ���,�ü�װ��XXXʱ����Ϊ���ɵ���״̬��";
	}
	
	
	/**
	 * ����װ��Ʒ��
	 */
	public String upgradeQuality( RoleEntity roleInfo,PlayerEquipVO equip,String pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		if( propGroup!=null && propGroup.getPropNum()>0&&propGroup.getPropInfo().getPropClass()!=PropType.EQUIP_UPGRADE_QUALITY )
		{
			return "����ȷʹ������װ��Ʒ�ʵĵ���";
		}
		
		String hint =  equip.isOwnByPPk(roleInfo.getBasicInfo().getPPk());
		if( hint!=null )
		{
			return hint;
		}
		
		if( equip.getWZbGrade()>0 || equip.getEffectHoleNum()>0)
		{
			return "����,���׻���Ƕ����װ����������Ʒ��";
		}
		
		if( equip.getWQuality()>=Equip.Q_ORANGE )
		{
			return "װ�����ǳ�װ,װ����������Ʒ��!";
		}
		PlayerEquipVO new_equip = this.createEquipByQuality(p_pk, equip.getEquipId(), equip.getWQuality()+1,GameLogManager.G_UPGRADE);
		goodsService.removeProps(propGroup, 1);
		goodsService.removeEquipById(p_pk, equip.getPwPk(),GameLogManager.R_MATERIAL_CONSUME);
		
		return "װ�������ɹ�,�������"+new_equip.getFullName();
	}
	
	/**
	 * ���װ���İ�״̬
	 */
	public String unbind( RoleEntity roleInfo,PlayerEquipVO equip,String pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		String hint = equip.isOwnByPPk(roleInfo.getBasicInfo().getPPk());
		if( hint!=null )
		{
			return hint;
		}
		
		goodsService.removeProps(propGroup, 1);
		equip.unbind();
		return "���Ѿ���"+equip.getFullName()+"�����״̬��";
	}
	
	/**
	 * �޸��𻵵�װ��
	 */
	public String maintainBad( RoleEntity roleInfo,PlayerEquipVO equip,String pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		String hint = equip.isOwnByPPk(roleInfo.getBasicInfo().getPPk());
		if( hint!=null )
		{
			return hint;
		}
		
		goodsService.removeProps(propGroup, 1);
		equip.maintainBad();
		return "�ɹ��޸���"+equip.getFullName();
	}
	
	/**
	 * ��������װ����ʾ
	 */
	public String propMaintainHint( RoleEntity roleInfo,String pg_pk)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		int total_endure = Integer.parseInt(propGroup.getPropInfo().getPropOperate1());
		int total_consume_endure = roleInfo.getEquipOnBody().getConsumeEndureTotal();
		if( total_consume_endure==0 )//����Ҫ����
		{
			return null;
		}
		String hint = propGroup.getPropInfo().getPropName()+"���޸�"+total_endure+"��װ���;ã�Ŀǰȫ��װ����ʧ�;�"+total_consume_endure+"�㡣��ȷ��Ҫ�޸���";
		return hint;
	}


	/**
	 * ��ɫ��װ����ָ��λ��
	 */
	public String puton(RoleEntity roleInfo, PlayerEquipVO equip,int position)
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		
		String hint = null;
		hint = this.isPuton(roleInfo, equip);//�ж�װ���Ƿ���Դ�
		if (hint != null)
		{
			return hint;
		}
		
		PlayerEquipVO takeoff_equip = null;//���µ�װ��
		//��װ��
		if( position==-1)//��ָ������ʲô�ط�
		{
			takeoff_equip =	roleInfo.getEquipOnBody().puton(equip);
		}
		else//ָ������ĳ��λ
		{
			takeoff_equip =	roleInfo.getEquipOnBody().puton(equip,position);
		}
		
		playerEquipDao.updatePosition(equip.getPwPk(), equip.getWType());
		
		
		if (takeoff_equip!=null)//������滻װ��
		{
			playerEquipDao.updatePosition(takeoff_equip.getPwPk(), 0);
		}
		else//���û���滻������ͬһ����λ��װ��
		{
			roleInfo.getBasicInfo().addWrapSpare(1);
		}
		return hint;
	}
	/**
	 * ��ɫ��װ��
	 */
	public String puton(RoleEntity roleInfo, PlayerEquipVO equip)
	{
		return this.puton(roleInfo, equip, -1);
	}

	/**
	 * �ж��Ƿ���Դ�װ��
	 */
	public String isPuton(RoleEntity roleInfo, PlayerEquipVO equip)
	{
		BasicInfo basicInfo = roleInfo.getBasicInfo();
		if ( equip!= null)
		{
			String hint =  equip.isOwnByPPk(roleInfo.getBasicInfo().getPPk());
			if( hint!=null )
			{
				return hint;
			}
			GameEquip gameEquip = equip.getGameEquip();
			if( equip.isEffected()==false )
			{
				return "װ������";
			}
			if ( gameEquip.getSex()!= 0 && basicInfo.getSex() != gameEquip.getSex() )
			{
				return "�Ա𲻷�";
			}
			if (basicInfo.getGrade() < gameEquip.getGrade())
			{
				return "�ȼ�����";
			}
			if( gameEquip.getIsMarried()!= 0 && basicInfo.getMarried()==0 )
			{
				return "��鲻��";
			}
			if ( gameEquip.getJob()!=0 && basicInfo.getPRace()!=gameEquip.getJob())
			{
				return "���岻��";
			}
		}
		else
		{
			logger.info("װ����ָ��");
		}
		return null;
	}


	/**
	 * ��ɫ��װ��
	 */
	public String takeoff(RoleEntity roleInfo, int pw_pk,int position)
	{
		if( roleInfo==null || pw_pk<0 )
		{
			return "���ݴ���";
		}
		String hint = null;
		
		int spare = roleInfo.getBasicInfo().getWrapSpare();
		if (spare == 0)
		{
			hint = "�����񲻹���!";
			return hint;
		}
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updatePosition(pw_pk, 0);
		addWrapSpare(roleInfo.getBasicInfo().getPPk(), -1);
		roleInfo.getEquipOnBody().takeoff(position);
		return hint;
	}

	/**
	 * ���Ӱ���ʣ��ռ�����
	 * 
	 */
	public int addWrapSpare(int p_pk, int add_wrap_spare)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk+"");
		
		int remain = roleInfo.getBasicInfo().getWrapSpare();
		int result = 1;
		roleInfo.getBasicInfo().addWrapSpare(add_wrap_spare);

		// ���ԭ����������ո� �Ҽ�����������ʱ���ͷ��ʼ�֪ͨ
		if (remain > 5 && (remain + add_wrap_spare) <= 5)
		{
			if(roleInfo.getBasicInfo().getWrapContent() != 100){
				new PopUpMsgService().addSysSpecialMsg(p_pk,roleInfo.getBasicInfo().getGrade(),0, PopUpMsgType.WRAP_LOWER_LIMIT);
			}
			MailInfoService mailInfoService = new MailInfoService();
			mailInfoService.sendWrapSpareMail(p_pk);
		}

		return result;
	}
	
	/**
	 * ��ѯ����ɫ�������д���װ����ִ�г־�����װ������
	 */
	public String useEquip(int pPk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(pPk+"");
		String hint = null;
		List<PlayerEquipVO> list = role_info.getEquipOnBody().getEquipList();
		if (list != null && list.size() != 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				PlayerEquipVO vo = list.get(i);
				if (vo.getEquipType()==Equip.WEAPON )//��������
				{
					continue;
				}
				int pwPk = vo.getPwPk();
				int wDuraConsume = vo.getCurEndure() - 1;
				if (wDuraConsume == 209)
				{
					MailInfoService ms = new MailInfoService();
					String title = "װ���;�С��20";
					String hintss = StringUtil.isoToGB(vo.getWName())
							+ "���;�С��20�������޸�װ����";
					ms.sendMailBySystem(pPk, title, hintss);
				}
				if (wDuraConsume == 59)
				{
					MailInfoService ms = new MailInfoService();
					String title = "װ���;�С��5";
					String hintss = StringUtil.isoToGB(vo.getWName())
							+ "���;�С��5�������޸�װ����";
					ms.sendMailBySystem(pPk, title, hintss);
				}
				if (wDuraConsume <= 0)
				{
					hint = "װ���־��Ѿ�Ϊ0���������";
					return hint;
				}
				consumeEndure(pwPk, wDuraConsume);
			}
			
			if( role_info!=null )
			{
				role_info.getEquipOnBody().useEquip();
			}
		}
		return null;
	}

	/**
	 * ��ѯ����ɫ�������д���װ����ִ�г־�������������
	 */
	public String useWeapon(int pPk)
	{
		RoleEntity role_info = RoleService.getRoleInfoById(pPk+"");
		String hint = null;
		PlayerEquipVO weapon = role_info.getEquipOnBody().getByPositin(Equip.WEAPON);
		if (weapon != null)
		{
			int pwPk = weapon.getPwPk();
			int wDuraConsume = weapon.getCurEndure() - 1;
			if (wDuraConsume == 209)
			{
				MailInfoService ms = new MailInfoService();
				String title = "װ���;�С��20";
				String hintss = StringUtil.isoToGB(weapon.getWName())
						+ "���;�С��20�������޸�װ����";
				ms.sendMailBySystem(pPk, title, hintss);
			}
			if (wDuraConsume == 59)
			{
				MailInfoService ms = new MailInfoService();
				String title = "װ���;�С��5";
				String hintss = StringUtil.isoToGB(weapon.getWName())
						+ "���;�С��5�������޸�װ����";
				ms.sendMailBySystem(pPk, title, hintss);
			}
			if (wDuraConsume <= 0)
			{
				hint = "װ���־��Ѿ�Ϊ0���������";
				return hint;
			}
			consumeEndure(pwPk, wDuraConsume);
			if( role_info!=null )
			{
				role_info.getEquipOnBody().useArm();
			}
		}
		return null;
	}

	/**
	 * �����;�
	 */
	private void consumeEndure(int pw_pk, int cur_endure)
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updateCurEndure(pw_pk, cur_endure);
	}
	
	/**
	 * �õ���������Ʒ�ʵ�װ����ҳ�б�
	 */
	public QueryPage getPageQualityList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageQualityList(p_pk, page_no);
	}
	/**
	 * �õ����Ա�����װ����ҳ�б�
	 */
	public QueryPage getPageProtectList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageProtectList(p_pk, page_no);
	}
	/**
	 * �õ����Խ���󶨵�װ����ҳ�б�
	 */
	public QueryPage getPageBindList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageBindList(p_pk, page_no);
	}
	/**
	 * �õ���Ҫ�޸�������װ����ҳ�б�
	 */
	public QueryPage getPageBadList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageBadList(p_pk, page_no);
	}
	/**
	 * �õ�����Ƕ��װ����ҳ�б�
	 */
	public QueryPage getPageInlayList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageInlayList(p_pk, page_no);
	}
	/**
	 * �õ��ɴ�׵�װ����ҳ�б�
	 */
	public QueryPage getPagePunchList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPagePunchList(p_pk, page_no);
	}
	
	/**
	 * �õ���������װ����ҳ�б�
	 */
	public QueryPage getPageUpgradeEquip(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageUpgradeList(p_pk, page_no);
	}
	
	/**
	 * �õ���ת�����еĵ�װ����ҳ�б�
	 */
	public QueryPage getPageChangeWXEquip(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageChangeWXList(p_pk, page_no);
	}
	
	
	/**
	 * ����Ʒ�ʹ���װ��
	 * @param p_pk
	 * @param equip_id
	 * @param equip_quality			װ��Ʒ��			
	 * @param gain_type				���װ����������	
	 * @return
	 */
	public PlayerEquipVO createEquipByQuality(int p_pk, int equip_id,int equip_quality,int gain_type)
	{
		GameEquip gameEquip = EquipCache.getById(equip_id);
		
		PlayerEquipVO equip = new PlayerEquipVO(); 
		
		equip.setPPk(p_pk);
		equip.setEquipId(equip_id);
		equip.setWQuality(equip_quality); 
		
		equip.setEquipType(gameEquip.getType());
		equip.setWName(StringUtil.gbToISO(gameEquip.getName()));
		equip.setCurEndure(gameEquip.getEndure()*10);
		equip.setMaxEndure(equip.getCurEndure());
		
		equip.setWGjXiao(gameEquip.getMinAtt());
		equip.setWGjDa(gameEquip.getMaxAtt());
		equip.setWFyXiao(gameEquip.getMinDef());
		equip.setWFyDa(gameEquip.getMaxDef());
		
		equip.setWLevel(gameEquip.getGrade());
		equip.setHoleNum(gameEquip.getHoleNum(equip_quality));
		
		//Ʒ���Ǻ�����������װ�����ϵ�װ�����ǰ�װ��
		if( equip_quality>Equip.Q_YOUXIU)
		{
			equip.setWBonding(1);
		}
		else
		{
			equip.setWBonding(gameEquip.getIsBind());
		}
		
		//����Ʒ������װ������
		if( equip_quality==Equip.Q_ORANGE )
		{
			//����ǳ�װ,�õ��̶�����
			equip.appendAttriByAttrisStr(gameEquip.getOtherAttriStr());
		}
		else
		{
			// ����Ʒ�ʸ�װ��׷�Ӹ�������
			appendBasicAttri(equip);//���ӻ�������
			appendOtherAttri(equip);//������������
		}
		
		PlayerEquipDao equipDao = new PlayerEquipDao();
		equipDao.add(equip);
		
		if( equip.getPwPk()>0 )
		{
			GameLogManager.getInstance().equipLog(p_pk, equip, gain_type);
			return equip;
		}
		else
		{
			return null;
		}
		
	}
	
	
	
	/**
	 * װ���������Եĸ���ֵ��������������
	 * @param max_value    ���ֵ
	 * @param min_value  	��Сֵ
	 * @param min_rate		��С���Ӹ���
	 * @param max_rate		��󸽼Ӹ���
	 * @return
	 */
	private int getAppendValue(int max_value, int min_value, int min_rate, int max_rate,int quality)
	{
		if( max_value==0 && min_value==0 )
		{
			return 0;
		}
		int average_value = (max_value + min_value)/2;//ƽ��ֵ
		double random_rate = MathUtil.getRandomBetweenXY(min_rate, max_rate);//�õ��������
		double appendValue = random_rate*average_value/100;
		return Math.round((float)appendValue)+quality;
	}

	/**
	 * ����Ʒ�ʣ������������ԣ����У�hp,mp��
	 */
	private void appendOtherAttri( PlayerEquipVO equip )
	{
		if( equip!=null )
		{
			int quality = equip.getWQuality();//װ��Ʒ��
			int append_wx_attri_num = 1;//�����������Ե�����
			switch (quality)
			{
				case Equip.Q_YOUXIU:
					append_wx_attri_num = 1;
					break;
				case Equip.Q_LIANGHAO:
					append_wx_attri_num = 1;
					break;
				case Equip.Q_JIPIN:
					if( MathUtil.isAppearByPercentage(10) )//�ٷ�֮10����3����������
					{
						append_wx_attri_num = 3;
					}
					else
					{
						append_wx_attri_num = 2;
					}
					break;
				default: return;//��������
			}
			
			EquipAppendAttributeDao equipAppendAttributeDao = new EquipAppendAttributeDao();
			List<EquipAppendAttributeVO> appendAttributes = equipAppendAttributeDao.getRandomAttributes(equip.getEquipType(),equip.getWLevel(),quality , append_wx_attri_num);
			
			List<EquipAppendAttributeVO> appendAttributesHpMp = equipAppendAttributeDao.getRandomAttributesByHpMp(equip.getEquipType(),equip.getWLevel(),quality );
			
			//����Ʒװ������hp��mp����
			if( appendAttributesHpMp!=null )
			{						
				for( EquipAppendAttributeVO appendAttributeHpMpVO:appendAttributesHpMp )
				{
					equip.appendAttriByAttriType( appendAttributeHpMpVO.getAttributeType(), appendAttributeHpMpVO.getValue());
				}
			}
			
			
			if( appendAttributes!=null )
			{						
				for( EquipAppendAttributeVO appendAttribute:appendAttributes )
				{
					equip.appendAttriByAttriType(appendAttribute.getAttributeType(), appendAttribute.getValue());
				}
			}
		}
	}

	/**
	 * �����������͸�װ����������
	 * @param equip			 Ҫ���ӵ�װ��
	 * @param attri_type      ��������
	 * @param attri_value    ����ֵ
	 *//*
	public void appendAttriByAttriType( PlayerEquipVO equip,int attri_type,int attri_value)
	{
		switch(attri_type)
		{						
			case Equip.MIN_DEF:
				equip.setWFyXiao(equip.getWFyXiao()+attri_value);
				break;
			case Equip.MAX_DEF:
				equip.setWFyDa(equip.getWFyDa()+attri_value);
				break;
			case Equip.MIN_ATT:
				equip.setWGjXiao(equip.getWGjXiao()+attri_value);
				break;
			case Equip.MAX_ATT:
				equip.setWGjDa(equip.getWGjDa()+attri_value);
				break;
			case Equip.HP_UPPER:
				equip.setWHp(equip.getWHp()+attri_value);
				break;
			case Equip.MP_UPPER:
				equip.setWMp(equip.getWMp()+attri_value);
				break;
			case Equip.JIN_FY:
				equip.setWJinFy(equip.getWJinFy()+attri_value);
				break;
			case Equip.MU_FY:
				equip.setWMuFy(equip.getWMuFy()+attri_value);
				break;
			case Equip.SHUI_FY:
				equip.setWShuiFy(equip.getWShuiFy()+attri_value);
				break;
			case Equip.HUO_FY:
				equip.setWHuoFy(equip.getWHuoFy()+attri_value);
				break;
			case Equip.TU_FY:
				equip.setWTuFy(equip.getWTuFy()+attri_value);
				break;
			case Equip.JIN_GJ:
				equip.setWJinGj(equip.getWJinGj()+attri_value);
				break;
			case Equip.MU_GJ:
				equip.setWMuGj(equip.getWMuGj()+attri_value);
				break;
			case Equip.SHUI_GJ:
				equip.setWShuiGj(equip.getWShuiGj()+attri_value);
				break;
			case Equip.HUO_GJ:
				equip.setWHuoGj(equip.getWHuoGj()+attri_value);
				break;
			case Equip.TU_GJ:
				equip.setWTuGj(equip.getWTuGj()+attri_value);
				break;
		}
	}*/
	
	/**
	 * ����Ʒ�ʣ���װ�����ӻ��������ԣ�������������
	 */
	private void appendBasicAttri( PlayerEquipVO equip )
	{
		if( equip!=null )
		{
			int quality = equip.getWQuality();//װ��Ʒ��
			int min_rate = 0;
			int max_rate = 0;
			switch (quality)
			{
				case Equip.Q_YOUXIU:
					min_rate = 3;
					max_rate = 5;
					break;
				case Equip.Q_LIANGHAO:
					min_rate = 8;
					max_rate = 10;
					break;
				case Equip.Q_JIPIN:
					min_rate = 15;
					max_rate = 18;
					break;
				default: return;
			}
			
			// ��߻�������.
			int fyvalue = getAppendValue(equip.getWFyDa(),equip.getWFyXiao(),min_rate,max_rate,quality);
			int gjvalue = getAppendValue(equip.getWGjDa(),equip.getWGjXiao(),min_rate,max_rate,quality);
			
			equip.setWFyXiao((equip.getWFyXiao()+fyvalue));
			equip.setWFyDa((equip.getWFyDa()+fyvalue));
			
			equip.setWGjXiao((equip.getWGjXiao()+gjvalue));
			equip.setWGjDa((equip.getWGjDa()+gjvalue));
		}
	}
	

	/**�õ���Ҽӳ�����*//*
	//�����ӳ�
	public int getPlayerEquipAttributeGj(int p_pk){
		PlayerEquipDao dao = new PlayerEquipDao();
		List list = dao.getEquipAttribute(p_pk);
		if(list.size() != 0){
		int count = Integer.parseInt(list.get(0).toString());
		int type = Integer.parseInt(list.get(1).toString());
		if(count == 8){
			if(type == 1){
				return 10;
			}
		  }
		if(count == 7){
			int x = dao.getEquipAttributeByAllContent(p_pk);
			if(x > 0){
			if(type == 1){
				return 10;
			}
		  }
		}
		}return 0;
	}
	//HP�ӳ�
	public int getPlayerEquipAttributeHp(int p_pk){
		PlayerEquipDao dao = new PlayerEquipDao();
		List list = dao.getEquipAttribute(p_pk);
		if(list.size() != 0){
		int count = Integer.parseInt(list.get(0).toString());
		int type = Integer.parseInt(list.get(1).toString());
		if(count == 8){
			 if(type == 2){
				return 25;
			}
		  }
		if(count == 7){
			int x = dao.getEquipAttributeByAllContent(p_pk);
			if(x > 0){
			 if(type == 2){
				return 25;
			}
		  }
		}
		}return 0;
	}
	//MP�ӳ�
	public int getPlayerEquipAttributeMp(int p_pk){
		PlayerEquipDao dao = new PlayerEquipDao();
		List list = dao.getEquipAttribute(p_pk);
		if(list.size() != 0){
		int count = Integer.parseInt(list.get(0).toString());
		int type = Integer.parseInt(list.get(1).toString());
		if(count == 8){
			if(type == 3){
				return 10;
			}
		  }
		if(count == 7){
			int x = dao.getEquipAttributeByAllContent(p_pk);
			if(x > 0){
			if(type == 3){
				return 10;
			}
		  }
		}
		}return 0;
	}
	//�����ʼӳ�
	public int getPlayerEquipAttributeBj(int p_pk){
		PlayerEquipDao dao = new PlayerEquipDao();
		List list = dao.getEquipAttribute(p_pk);
		if(list.size() != 0){
		int count = Integer.parseInt(list.get(0).toString());
		int type = Integer.parseInt(list.get(1).toString());
		if(count == 8){
			if(type == 4){
				return 8;
			}
		  }
		if(count == 7){
			int x = dao.getEquipAttributeByAllContent(p_pk);
			if(x > 0){
			if(type == 4){
				return 8;
			}
		  }
		}
		}return 0;
	}
	//�������ӳ�
	public int getPlayerEquipAttributeFy(int p_pk){
		PlayerEquipDao dao = new PlayerEquipDao();
		List list = dao.getEquipAttribute(p_pk);
		if(list.size() != 0){
		int count = Integer.parseInt(list.get(0).toString());
		int type = Integer.parseInt(list.get(1).toString());
		if(count == 8){
			if(type == 5){
				return 20;
			}
		  }
		if(count == 7){
			int x = dao.getEquipAttributeByAllContent(p_pk);
			if(x > 0){
			if(type == 5){
				return 20;
			}
		  }
		}
		}return 0;
	}

	//װ�����װ�����Լӳ�
	public void loadWXProperty(PartInfoVO player){
		PlayerEquipDao dao = new PlayerEquipDao();
		int p_pk = player.getPPk();
		List list = dao.getEquipAttribute(p_pk);
		if(list.size() != 0){
		int count = Integer.parseInt(list.get(0).toString());
		int type = Integer.parseInt(list.get(1).toString());
		if(count == 8){
			if(type == 1){
				int gj = (int)(((player.getPZbgjXiao() + player.getPZbgjDa())*1.0/2*getPlayerEquipAttributeGj(p_pk))/100);
				player.setPZbgjXiao(player.getPZbgjXiao()+gj);
				player.setPZbgjDa(player.getPZbgjDa()+gj);
			}else if(type == 2){
				int hp = (player.getPMaxHp()*getPlayerEquipAttributeHp(p_pk))/ 100;
				//player.setPHp(player.getPHp()+hp);
				player.setPUpHp(player.getPUpHp()+hp);
			}else if(type == 3){
				int mp = (player.getPMaxMp()*getPlayerEquipAttributeMp(p_pk))/ 100;
				//player.setPMp(player.getPMp()+mp);
				player.setPUpMp(player.getPUpMp()+mp);
			}else if(type == 4){
				double bj = player.getDropMultiple()+getPlayerEquipAttributeBj(p_pk);
				player.setDropMultiple(bj);
			}else if(type == 5){
				int fy =  (int)(((player.getPZbfyXiao() + player.getPZbfyDa())*1.0/2*getPlayerEquipAttributeFy(p_pk))/100);
				player.setPZbfyXiao(player.getPZbfyXiao()+fy);
				player.setPZbfyDa(player.getPZbfyDa()+fy);
			}
		}
		if(count == 7){
			int x = dao.getEquipAttributeByAllContent(p_pk);
			if(x > 0){
				if(type == 1){
					int gj = (int)(((player.getPZbgjXiao() + player.getPZbgjDa())*1.0/2*getPlayerEquipAttributeGj(p_pk))/100);
					player.setPZbgjXiao(player.getPZbgjXiao()+gj);
					player.setPZbgjDa(player.getPZbgjDa()+gj);
				}else if(type == 2){
					int hp = (player.getPMaxHp()*getPlayerEquipAttributeHp(p_pk))/ 100;
					//player.setPHp(player.getPHp()+hp);
					player.setPUpHp(player.getPUpHp()+hp);
				}else if(type == 3){
					int mp = (player.getPMaxMp()*getPlayerEquipAttributeMp(p_pk))/ 100;
					//player.setPMp(player.getPMp()+mp);
					player.setPUpMp(player.getPUpMp()+mp);
				}else if(type == 4){
					double bj = player.getDropMultiple()+getPlayerEquipAttributeBj(p_pk);
					player.setDropMultiple(bj);
				}else if(type == 5){
					int fy =  (int)(((player.getPZbfyXiao() + player.getPZbfyDa())*1.0/2*getPlayerEquipAttributeFy(p_pk))/100);
					player.setPZbfyXiao(player.getPZbfyXiao()+fy);
					player.setPZbfyDa(player.getPZbfyDa()+fy);
				}
			}
		}
		}
	}*/

	/**
	 * չʾװ��
	 * @param roleEntity
	 * @param equipVO
	 */
	public String relelaEquip(RoleEntity roleEntity, PlayerEquipVO equipVO,HttpServletResponse response,HttpServletRequest request)
	{
		EquipDisplayService equipDisplayService = new EquipDisplayService();
		String displayString = equipDisplayService.getEquipDisplay(roleEntity,equipVO,false);
		
		EquipRelelaDao equipRelelaDao = new EquipRelelaDao();
		equipRelelaDao.insertEquipRelela(equipVO.getPwPk(),displayString);
		
		
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("���").append(roleEntity.getName()).append("չʾ������װ��");
		sBuffer.append("<anchor>");
		sBuffer.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/equiprelela.do")+"\">");
		sBuffer.append("<postfield name=\"cmd\" value=\"n2\" /> ");
		sBuffer.append("<postfield name=\"pwpk\" value=\"" + equipVO.getPwPk() + "\" /> ");
		sBuffer.append("</go>");
		sBuffer.append(equipVO.getWName());
		sBuffer.append("</anchor> ");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������
		
		SystemInfoService systemInfoService = new SystemInfoService();
		systemInfoService.insertSystemInfoByBackTong(sBuffer.toString(),time,SystemInfoType.EQUIPRELELA);
				
		// ����50��Ԫ��
		EconomyService economyService = new EconomyService();
		economyService.spendYuanbao(roleEntity.getBasicInfo().getUPk(), 50);

		StringBuffer hint = new StringBuffer();
		hint.append("���Ѿ�����"+GameConfig.getYuanbaoName()+"��50չʾ�����װ��").append(equipVO.getWName());
		return hint.toString();
		
	}
	
	
	/**
	 * չʾװ��
	 * @param roleEntity
	 * @param equipVO
	 */
	public String getEelelaEquipInfo(RoleEntity roleEntity,String pwpk)
	{
		if ( pwpk == null || pwpk.equals("") || pwpk.equals("null")) {
			return "��װ���Ѿ�������";
		}
		
		EquipRelelaDao equipRelelaDao = new EquipRelelaDao();
		String displayInfo = equipRelelaDao.getEquipRelelaInfo(pwpk);

		return displayInfo;
	}

}
