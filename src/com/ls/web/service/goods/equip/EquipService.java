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
 * 功能:装备相关操作
 * 
 * @author 刘帅 10:03:12 PM
 */
public class EquipService
{
	Logger logger = Logger.getLogger("log.service");
	
	/**
	 * 更改装备的拥有者
	 * @param new_owner		更改之后的拥有者
	 * @param pwPk			装备id
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
	 * 更改装备的拥有者
	 * @param old_owner 		更改之前的拥有者
	 * @param new_owner			更改之后的拥有者
	 * @param equip				更改的装备
	 * @return
	 */
	public String updateOwner( RoleEntity old_owner,RoleEntity new_owner,PlayerEquipVO equip,int gain_type )
	{
		String hint = null;
		
		if( new_owner==null || equip==null )
		{
			return "数据错误";
		}
		
		if( old_owner!=null )//如果该装备有拥有者
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
	 * 保护装备
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
		String protect_time_str = propGroup.getPropInfo().getPropOperate1();//保护的时间
		int protect_time = 0;
		try{
			protect_time = Integer.parseInt(protect_time_str);
		}
		catch (Exception e) {
			DataErrorLog.prop(propGroup.getPropId(),"operate1错误："+protect_time_str);
		}
		
		equip.protectEquip(protect_time);
		
		return "恭喜您,已将"+equip.getFullName()+"装备成功绑定,该件装备XXX时间内为不可掉落状态！";
	}
	
	
	/**
	 * 升级装备品质
	 */
	public String upgradeQuality( RoleEntity roleInfo,PlayerEquipVO equip,String pg_pk)
	{
		GoodsService goodsService = new GoodsService();
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		
		int p_pk = roleInfo.getBasicInfo().getPPk();
		
		if( propGroup!=null && propGroup.getPropNum()>0&&propGroup.getPropInfo().getPropClass()!=PropType.EQUIP_UPGRADE_QUALITY )
		{
			return "请正确使用提升装备品质的道具";
		}
		
		String hint =  equip.isOwnByPPk(roleInfo.getBasicInfo().getPPk());
		if( hint!=null )
		{
			return hint;
		}
		
		if( equip.getWZbGrade()>0 || equip.getEffectHoleNum()>0)
		{
			return "升级,开孔或镶嵌过的装备不可提升品质";
		}
		
		if( equip.getWQuality()>=Equip.Q_ORANGE )
		{
			return "装备已是橙装,装备不能升级品质!";
		}
		PlayerEquipVO new_equip = this.createEquipByQuality(p_pk, equip.getEquipId(), equip.getWQuality()+1,GameLogManager.G_UPGRADE);
		goodsService.removeProps(propGroup, 1);
		goodsService.removeEquipById(p_pk, equip.getPwPk(),GameLogManager.R_MATERIAL_CONSUME);
		
		return "装备升级成功,您获得了"+new_equip.getFullName();
	}
	
	/**
	 * 解除装备的绑定状态
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
		return "您已经将"+equip.getFullName()+"解除绑定状态了";
	}
	
	/**
	 * 修复损坏的装备
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
		return "成功修复了"+equip.getFullName();
	}
	
	/**
	 * 道具修理装备提示
	 */
	public String propMaintainHint( RoleEntity roleInfo,String pg_pk)
	{
		PlayerPropGroupDao propGroupDao = new PlayerPropGroupDao();
		PlayerPropGroupVO propGroup = propGroupDao.getByPgPk(Integer.parseInt(pg_pk));
		int total_endure = Integer.parseInt(propGroup.getPropInfo().getPropOperate1());
		int total_consume_endure = roleInfo.getEquipOnBody().getConsumeEndureTotal();
		if( total_consume_endure==0 )//不需要修理
		{
			return null;
		}
		String hint = propGroup.getPropInfo().getPropName()+"可修复"+total_endure+"点装备耐久，目前全身装备损失耐久"+total_consume_endure+"点。你确定要修复吗？";
		return hint;
	}


	/**
	 * 角色穿装备到指定位置
	 */
	public String puton(RoleEntity roleInfo, PlayerEquipVO equip,int position)
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		
		String hint = null;
		hint = this.isPuton(roleInfo, equip);//判断装备是否可以穿
		if (hint != null)
		{
			return hint;
		}
		
		PlayerEquipVO takeoff_equip = null;//脱下的装备
		//穿装备
		if( position==-1)//不指定穿到什么地方
		{
			takeoff_equip =	roleInfo.getEquipOnBody().puton(equip);
		}
		else//指定穿到某部位
		{
			takeoff_equip =	roleInfo.getEquipOnBody().puton(equip,position);
		}
		
		playerEquipDao.updatePosition(equip.getPwPk(), equip.getWType());
		
		
		if (takeoff_equip!=null)//如果是替换装备
		{
			playerEquipDao.updatePosition(takeoff_equip.getPwPk(), 0);
		}
		else//如果没有替换的身上同一个部位的装备
		{
			roleInfo.getBasicInfo().addWrapSpare(1);
		}
		return hint;
	}
	/**
	 * 角色穿装备
	 */
	public String puton(RoleEntity roleInfo, PlayerEquipVO equip)
	{
		return this.puton(roleInfo, equip, -1);
	}

	/**
	 * 判断是否可以穿装备
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
				return "装备已损坏";
			}
			if ( gameEquip.getSex()!= 0 && basicInfo.getSex() != gameEquip.getSex() )
			{
				return "性别不符";
			}
			if (basicInfo.getGrade() < gameEquip.getGrade())
			{
				return "等级不符";
			}
			if( gameEquip.getIsMarried()!= 0 && basicInfo.getMarried()==0 )
			{
				return "结婚不符";
			}
			if ( gameEquip.getJob()!=0 && basicInfo.getPRace()!=gameEquip.getJob())
			{
				return "种族不符";
			}
		}
		else
		{
			logger.info("装备空指针");
		}
		return null;
	}


	/**
	 * 角色脱装备
	 */
	public String takeoff(RoleEntity roleInfo, int pw_pk,int position)
	{
		if( roleInfo==null || pw_pk<0 )
		{
			return "数据错误";
		}
		String hint = null;
		
		int spare = roleInfo.getBasicInfo().getWrapSpare();
		if (spare == 0)
		{
			hint = "包裹格不够了!";
			return hint;
		}
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updatePosition(pw_pk, 0);
		addWrapSpare(roleInfo.getBasicInfo().getPPk(), -1);
		roleInfo.getEquipOnBody().takeoff(position);
		return hint;
	}

	/**
	 * 增加包裹剩余空间数量
	 * 
	 */
	public int addWrapSpare(int p_pk, int add_wrap_spare)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(p_pk+"");
		
		int remain = roleInfo.getBasicInfo().getWrapSpare();
		int result = 1;
		roleInfo.getBasicInfo().addWrapSpare(add_wrap_spare);

		// 如果原来超过五个空格， 且加入后少于五个时，就发邮件通知
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
	 * 查询出角色身体所有穿戴装备并执行持久消耗装备方法
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
				if (vo.getEquipType()==Equip.WEAPON )//不是武器
				{
					continue;
				}
				int pwPk = vo.getPwPk();
				int wDuraConsume = vo.getCurEndure() - 1;
				if (wDuraConsume == 209)
				{
					MailInfoService ms = new MailInfoService();
					String title = "装备耐久小于20";
					String hintss = StringUtil.isoToGB(vo.getWName())
							+ "的耐久小于20，尽快修复装备！";
					ms.sendMailBySystem(pPk, title, hintss);
				}
				if (wDuraConsume == 59)
				{
					MailInfoService ms = new MailInfoService();
					String title = "装备耐久小于5";
					String hintss = StringUtil.isoToGB(vo.getWName())
							+ "的耐久小于5，尽快修复装备！";
					ms.sendMailBySystem(pPk, title, hintss);
				}
				if (wDuraConsume <= 0)
				{
					hint = "装备持久已经为0不能在相减";
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
	 * 查询出角色身体所有穿戴装备并执行持久消耗武器方法
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
				String title = "装备耐久小于20";
				String hintss = StringUtil.isoToGB(weapon.getWName())
						+ "的耐久小于20，尽快修复装备！";
				ms.sendMailBySystem(pPk, title, hintss);
			}
			if (wDuraConsume == 59)
			{
				MailInfoService ms = new MailInfoService();
				String title = "装备耐久小于5";
				String hintss = StringUtil.isoToGB(weapon.getWName())
						+ "的耐久小于5，尽快修复装备！";
				ms.sendMailBySystem(pPk, title, hintss);
			}
			if (wDuraConsume <= 0)
			{
				hint = "装备持久已经为0不能在相减";
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
	 * 消耗耐久
	 */
	private void consumeEndure(int pw_pk, int cur_endure)
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updateCurEndure(pw_pk, cur_endure);
	}
	
	/**
	 * 得到可以升级品质的装备分页列表
	 */
	public QueryPage getPageQualityList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageQualityList(p_pk, page_no);
	}
	/**
	 * 得到可以保护的装备分页列表
	 */
	public QueryPage getPageProtectList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageProtectList(p_pk, page_no);
	}
	/**
	 * 得到可以解除绑定的装备分页列表
	 */
	public QueryPage getPageBindList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageBindList(p_pk, page_no);
	}
	/**
	 * 得到需要修复的破损装备分页列表
	 */
	public QueryPage getPageBadList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageBadList(p_pk, page_no);
	}
	/**
	 * 得到可镶嵌的装备分页列表
	 */
	public QueryPage getPageInlayList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageInlayList(p_pk, page_no);
	}
	/**
	 * 得到可打孔的装备分页列表
	 */
	public QueryPage getPagePunchList(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPagePunchList(p_pk, page_no);
	}
	
	/**
	 * 得到可升级的装备分页列表
	 */
	public QueryPage getPageUpgradeEquip(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageUpgradeList(p_pk, page_no);
	}
	
	/**
	 * 得到可转换五行的的装备分页列表
	 */
	public QueryPage getPageChangeWXEquip(int p_pk,int page_no )
	{
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		return playerEquipDao.getPageChangeWXList(p_pk, page_no);
	}
	
	
	/**
	 * 根据品质构造装备
	 * @param p_pk
	 * @param equip_id
	 * @param equip_quality			装备品质			
	 * @param gain_type				获得装备操作类型	
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
		
		//品质是后天灵器（绿装）以上的装备都是绑定装备
		if( equip_quality>Equip.Q_YOUXIU)
		{
			equip.setWBonding(1);
		}
		else
		{
			equip.setWBonding(gameEquip.getIsBind());
		}
		
		//根据品质生成装备属性
		if( equip_quality==Equip.Q_ORANGE )
		{
			//如果是橙装,得到固定属性
			equip.appendAttriByAttrisStr(gameEquip.getOtherAttriStr());
		}
		else
		{
			// 根据品质给装备追加附加属性
			appendBasicAttri(equip);//附加基础属性
			appendOtherAttri(equip);//附加其他属性
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
	 * 装备基础属性的附加值（攻击，防御）
	 * @param max_value    最大值
	 * @param min_value  	最小值
	 * @param min_rate		最小附加概率
	 * @param max_rate		最大附加概率
	 * @return
	 */
	private int getAppendValue(int max_value, int min_value, int min_rate, int max_rate,int quality)
	{
		if( max_value==0 && min_value==0 )
		{
			return 0;
		}
		int average_value = (max_value + min_value)/2;//平均值
		double random_rate = MathUtil.getRandomBetweenXY(min_rate, max_rate);//得到随机比率
		double appendValue = random_rate*average_value/100;
		return Math.round((float)appendValue)+quality;
	}

	/**
	 * 根据品质，附加其他属性（五行，hp,mp）
	 */
	private void appendOtherAttri( PlayerEquipVO equip )
	{
		if( equip!=null )
		{
			int quality = equip.getWQuality();//装备品质
			int append_wx_attri_num = 1;//附加五行属性的属性
			switch (quality)
			{
				case Equip.Q_YOUXIU:
					append_wx_attri_num = 1;
					break;
				case Equip.Q_LIANGHAO:
					append_wx_attri_num = 1;
					break;
				case Equip.Q_JIPIN:
					if( MathUtil.isAppearByPercentage(10) )//百分之10生成3个五行属性
					{
						append_wx_attri_num = 3;
					}
					else
					{
						append_wx_attri_num = 2;
					}
					break;
				default: return;//不做处理
			}
			
			EquipAppendAttributeDao equipAppendAttributeDao = new EquipAppendAttributeDao();
			List<EquipAppendAttributeVO> appendAttributes = equipAppendAttributeDao.getRandomAttributes(equip.getEquipType(),equip.getWLevel(),quality , append_wx_attri_num);
			
			List<EquipAppendAttributeVO> appendAttributesHpMp = equipAppendAttributeDao.getRandomAttributesByHpMp(equip.getEquipType(),equip.getWLevel(),quality );
			
			//给极品装备增加hp和mp属性
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
	 * 根据属性类型给装备附加属性
	 * @param equip			 要附加的装备
	 * @param attri_type      属性类型
	 * @param attri_value    属性值
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
	 * 根据品质，给装备附加基础攻属性（攻击，防御）
	 */
	private void appendBasicAttri( PlayerEquipVO equip )
	{
		if( equip!=null )
		{
			int quality = equip.getWQuality();//装备品质
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
			
			// 提高基本属性.
			int fyvalue = getAppendValue(equip.getWFyDa(),equip.getWFyXiao(),min_rate,max_rate,quality);
			int gjvalue = getAppendValue(equip.getWGjDa(),equip.getWGjXiao(),min_rate,max_rate,quality);
			
			equip.setWFyXiao((equip.getWFyXiao()+fyvalue));
			equip.setWFyDa((equip.getWFyDa()+fyvalue));
			
			equip.setWGjXiao((equip.getWGjXiao()+gjvalue));
			equip.setWGjDa((equip.getWGjDa()+gjvalue));
		}
	}
	

	/**得到玩家加成属性*//*
	//攻击加成
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
	//HP加成
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
	//MP加成
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
	//暴击率加成
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
	//防御力加成
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

	//装载玩家装备属性加成
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
	 * 展示装备
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
		sBuffer.append("玩家").append(roleEntity.getName()).append("展示了他的装备");
		sBuffer.append("<anchor>");
		sBuffer.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/equiprelela.do")+"\">");
		sBuffer.append("<postfield name=\"cmd\" value=\"n2\" /> ");
		sBuffer.append("<postfield name=\"pwpk\" value=\"" + equipVO.getPwPk() + "\" /> ");
		sBuffer.append("</go>");
		sBuffer.append(equipVO.getWName());
		sBuffer.append("</anchor> ");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量
		
		SystemInfoService systemInfoService = new SystemInfoService();
		systemInfoService.insertSystemInfoByBackTong(sBuffer.toString(),time,SystemInfoType.EQUIPRELELA);
				
		// 花费50个元宝
		EconomyService economyService = new EconomyService();
		economyService.spendYuanbao(roleEntity.getBasicInfo().getUPk(), 50);

		StringBuffer hint = new StringBuffer();
		hint.append("你已经花费"+GameConfig.getYuanbaoName()+"×50展示了你的装备").append(equipVO.getWName());
		return hint.toString();
		
	}
	
	
	/**
	 * 展示装备
	 * @param roleEntity
	 * @param equipVO
	 */
	public String getEelelaEquipInfo(RoleEntity roleEntity,String pwpk)
	{
		if ( pwpk == null || pwpk.equals("") || pwpk.equals("null")) {
			return "此装备已经不存在";
		}
		
		EquipRelelaDao equipRelelaDao = new EquipRelelaDao();
		String displayInfo = equipRelelaDao.getEquipRelelaInfo(pwpk);

		return displayInfo;
	}

}
