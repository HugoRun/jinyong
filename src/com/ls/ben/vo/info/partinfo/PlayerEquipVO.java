package com.ls.ben.vo.info.partinfo;

import java.util.Date;
import java.util.Map;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.model.equip.EquipAppendAttri;
import com.ls.model.equip.GameEquip;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.WuXing;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.goods.GoodsService;
import com.pm.vo.constant.AuctionType;

/**
 * @author 刘帅
 * 4:11:29 PM
 */
public class PlayerEquipVO
{
	private static int equip_punch_prop_id = 5;//打孔道具id
	
	private int pwPk;//id
	private int pPk;//角色id
	private int equipId;//装备ID
	private int equipType;//装备类型
	private String wName;//装备名字
	
	private int curEndure;//剩余的耐久
	private int maxEndure;//耐久上限
	private int wBonding;//是否绑定
	
	private int wFyXiao;//最小防御
	private int wFyDa;//最大防御
	private int wGjXiao;//最小攻击
	private int wGjDa;//最大攻击
	
	private int wHp;//增加的血量上线
	private int wMp;//增加的法力上线
	
	private PlayerWXVO wuxing;//装备五行属性
	private String wxAttrisStr;//五行属性字符串
	private String inlayPropStr="";//镶嵌道具字符串
	
	private int wType;//装备所在的位置
	private int wQuality;//品质
	private int wZbGrade;//装备级别, 即玩家将其升了几级, 默认为零级.
	private String createTime;//创建时间
	private int wLevel;//使用等级
	private int holeNum = -1;//总的孔的数量
	private int effectHoleNum;//有效孔数(开过的孔数)
	private int leftEffectHoleNum;//剩余有效孔数(可以镶嵌宝石的孔数)
	private int rankKey;// 武器排名规则关键字
	private Date protectEndTime=null;//装备受保护的结束时间(为空时不受保护)
	
	private String appendAttriDes;//装备升到最高级时，附加属性的描述
	
	/**
	 * 是否在身上
	 */
	public boolean isOnBody()
	{
		if( this.wType>0 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * 装备掉落
	 */
	public void drop()
	{
		GameLogManager.getInstance().equipLog(pPk, this, GameLogManager.R_DEAD);
		this.pPk=-1;
		this.wType=0;
		new PlayerEquipDao().updateOwner(this.pwPk, pPk);
	}
	
	/**
	 * 得到拍卖场分类
	 * @return
	 */
	public int  getAuctionType()
	{
		if( this.equipType==Equip.WEAPON)
		{
			return AuctionType.ARM;
		}
		else if(  this.equipType==Equip.JEWELRY)
		{
			return AuctionType.JEWELRY;
		}
		else
		{
			return AuctionType.ACCOUTE;
		}
	}
	
	/**
	 * 消耗装备耐久上线
	 * @param consume_ponit    消耗点数
	 */
	public void consumeMaxEndure( int consume_ponit )
	{
		if( this.maxEndure<=0 )
		{
			return;
		}
		this.maxEndure = this.maxEndure-consume_ponit*10;
		if( this.maxEndure<0 )
		{
			this.maxEndure = 0;
		}
		
		if( this.maxEndure<this.curEndure)
		{
			this.curEndure = this.maxEndure;
		}
		this.save();
	}
	
	/**
	 * 该装备是否属于某人
	 */
	public String isOwnByPPk( int p_pk)
	{
		if( this.pPk==p_pk )
		{
			return null;
		}
		else
		{
			return "该装备不属于你";
		}
	}
	/**
	 * 判读丢弃是否需要二次确认
	 */
	public boolean isReconfirm()
	{
		if( this.wQuality>=Equip.Q_LIANGHAO || this.getGameEquip().getIsReconfirm()==1 )
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 判读是否可以交易
	 */
	public boolean isTraded()
	{
		if( this.wType==0 && this.getGameEquip().getIsBind()==0 && this.getCurEndure()>=10 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 得到装备受保护的描述(该装备已被保护,剩余**分钟)
	 * @return
	 */
	public String getProtectDes()
	{
		if(this.isProtectOnPK()==false)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(该装备已被保护,剩余").append(DateUtil.getDifferTimes(new Date(), protectEndTime)).append("分钟)");
		
		return sb.toString();
	}
	
	/**
	 * 保护装备
	 * @param protect_time 受保护的时间（分钟）
	 */
	public void protectEquip( int protect_time )
	{
		if( protect_time<=0 )
		{
			return;
		}
		Date now = new Date();
		if( protectEndTime!=null && now.before(protectEndTime))//没有受保护，且没有超时
		{
			return;
		}
		protectEndTime = new Date(now.getTime()+protect_time*DateUtil.MINUTE*1000);
		this.save();
	}
	
	/**
	 * PK时该装备是否是否受保护
	 * @return
	 */
	public boolean isProtectOnPK()
	{
		if( protectEndTime==null )
		{
			return false;
		}
		Date now = new Date();
		if(  now.after(protectEndTime) )
		{
			//超时设置为空
			//发送消息告诉玩家超时
			//todo:
			protectEndTime = null;
			this.save();
		}
		return true;
	}
	
	public String getRoleName()
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		return partInfoDao.getNameByPpk(this.pPk);
	}
	
	/**
	 * 武器排行榜，排行规则
	 * 一级条件，武器攻击力（攻击力+属性攻击力*10）
	 * 二级条件，可以开孔数
	 * 三级条件，达到条件时间
	 * @return
	 */
	private void buildRankKey()
	{
		this.rankKey = this.getMaxAtt()+this.getMaxDef()+this.getWuxing().getRankKey();
	}
	
	/**
	 * 解除装备的绑定状态
	 * @return
	 */
	public void unbind()
	{
		if( this.wBonding==0 || this.wQuality==Equip.Q_ORANGE)//橙色装备不能解绑
		{
			return;
		}
		this.wBonding = 0;
		this.save();
	}
	
	/**
	 * 修复损坏的装备
	 * @return
	 */
	public void maintainBad()
	{
		this.maxEndure = this.getGameEquip().getEndure()*10;
		this.curEndure = this.maxEndure;
		this.save();
	}
	
	/**
	 * 得到消耗耐久的点数
	 */
	public int getConsumeEndure()
	{
		return (this.maxEndure/10-this.curEndure/10);
	}
	
	/**
	 * 得到装备的修理费用
	 */
	public int getMaintainFee()
	{
		if( this.isEffected()==false )//损坏的装备不能修理
		{
			return 0;
		}
		if( this.equipType==Equip.WEAPON)
		{
			return (this.maxEndure-this.curEndure)*this.getGameEquip().getGrade() * (this.wQuality + 1) / 100;
		}
		else
		{
			return (this.maxEndure-this.curEndure)*this.getGameEquip().getGrade()*(this.wQuality + 1) / 50;
		}
	}
	
	/**
	 * 根据装备耐久，判断该装备是否有效
	 */
	public boolean isEffected()
	{
		if (getCurEndure() < 10)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 开孔状态描述
	 */
	public String getHoleDes()
	{
		if( this.holeNum<=0 )
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("宝石槽:").append(getEffectHoleNum()).append("/").append(this.holeNum).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * 镶嵌描述
	 * @return
	 */
	public String getInlayDes()
	{
		if( getEffectHoleNum()<=0 )
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("镶嵌附加:");
		if( this.inlayPropStr!=null && !this.inlayPropStr.equals("") )
		{
			sb.append(this.inlayPropStr);
		}
		else
		{
			sb.append("无");
		}
		sb.append("<br/>");
		sb.append("镶嵌状态:").append(this.getInlayStoneNum()).append("/").append(getEffectHoleNum()).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * 有效的孔数
	 */
	public int getEffectHoleNum()
	{
		return effectHoleNum;
	}
	/**
	 * 得到镶嵌的宝石数
	 */
	private int getInlayStoneNum()
	{
		if( this.inlayPropStr==null || this.inlayPropStr.equals(""))
		{
			return 0;
		}
		String[] temp = this.inlayPropStr.split(",");
		return temp.length;
	}
	
	/**
	 * 装备打孔
	 */
	public String punch( RoleEntity roleEntity)
	{
		String hint = null;
		
		if( roleEntity==null )
		{
			return "数据错误";
		}
		
		hint = this.isOwnByPPk(roleEntity.getBasicInfo().getPPk());
		if(hint!=null)
		{
			return hint;
		}
		
		if( this.getEffectHoleNum()>=this.getHoleNum() )
		{
			return "该装备已不能打孔了";
		}
		
		GoodsService goodsService = new GoodsService();
		int consume_num = goodsService.removeProps(roleEntity.getBasicInfo().getPPk(), equip_punch_prop_id, 1,GameLogManager.R_MATERIAL_CONSUME);
		if( consume_num==-1)
		{
			return PropCache.getPropById(equip_punch_prop_id).getPropName()+"数量不足";
		}
		
		if( isPunchSuccess()==false )
		{
			hint = "太不幸了，您开孔失败，请继续努力！";
		}
		else
		{
			this.effectHoleNum++;
			this.leftEffectHoleNum++;
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			playerEquipDao.addEffectHoleNum(this.pwPk);
			hint = "恭喜您，此孔已成功打开，您现在可去镶嵌宝石了！";
		}
		return hint;
	}
	/**
	 * 打孔是否成功
	 */
	private boolean isPunchSuccess()
	{
		int rate = 0;
		switch(this.wQuality)
		{
			case Equip.Q_YOUXIU:rate=100;break;
			case Equip.Q_LIANGHAO:rate=80;break;
			case Equip.Q_JIPIN:rate=50;break;
			case Equip.Q_ORANGE:rate=20;break;
		}
		return MathUtil.isAppearByPercentage(rate);
	}
	
	/**
	 * 装备等级与基础属性的公式:武器基础属性×[1+(等级×5)%]
	 */
	private int getTotalValue(int basic_value)
	{
		return (int) Math.round(basic_value * (1+1.0*(this.wZbGrade*5)/100));
	}
	
	/**
	 * 总的攻击小
	 * @return
	 */
	public int getMinAtt()
	{
		return this.getTotalValue(this.wGjXiao);
	}
	/**
	 * 总的攻击大
	 * @return
	 */
	public int getMaxAtt()
	{
		return this.getTotalValue(this.wGjDa);
	}
	
	/**
	 * 总的防御小
	 * @return
	 */
	public int getMinDef()
	{
		return this.getTotalValue(this.wFyXiao);
	}
	/**
	 * 总的防御大
	 * @return
	 */
	public int getMaxDef()
	{
		return this.getTotalValue(this.wFyDa);
	}
	
	/**
	 * 更改装备属性
	 */
	public void save()
	{
		buildRankKey();//每次保存时，重新生成排名关键字
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updateEquipAttri(this);
	}
	
	
	/**
	 * 根据属性类型给装备附加属性
	 * @param sttriStr   属性字符串
	 */
	public void appendAttriByAttrisStr( String sttrisStr )
	{
		if( sttrisStr!=null && !sttrisStr.equals("") )
		{
			String[] attrisStr = sttrisStr.split("-");
			for( String attriStr:attrisStr)
			{
				String[] temp = attriStr.split(",");
				if( temp.length==2)
				{
					this.appendAttriByAttriType(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
				}
			}
		}
	}
	
	/**
	 * 根据属性类型给装备附加属性
	 * @param attri_type      属性类型
	 * @param attri_value    属性值
	 */
	public void appendAttriByAttriType( int attri_type,int attri_value)
	{
		switch(attri_type)
		{						
			case Equip.DEF:
				setWFyXiao(getWFyXiao()+attri_value);
				setWFyDa(getWFyDa()+attri_value);
				break;
			case Equip.ATT:
				setWGjXiao(getWGjXiao()+attri_value);
				setWGjDa(getWGjDa()+attri_value);
				break;
			case Equip.HP_UPPER:
				setWHp(getWHp()+attri_value);
				break;
			case Equip.MP_UPPER:
				setWMp(getWMp()+attri_value);
				break;
			default:
				getWuxing().appendAttriByAttriType(attri_type, attri_value);
		}
	}
	
	
	/**
	 * 得到装备全名
	 */
	public String getFullName()
	{
		StringBuffer full_name = new StringBuffer();
		if( this.wZbGrade== 13){
			full_name.append("〖神〗");
		}else if(this.wZbGrade>0){
			full_name.append("+").append(this.wZbGrade);
		}
		full_name.append(this.wName);
		full_name.append(ExchangeUtil.getQualityName(this.wQuality));
		return full_name.toString();
	}
	
	/**
	 * 得到品质全名
	 * @return
	 */
	public String getQualityFullName()
	{
		if( this.wQuality>0)
		{
			return ExchangeUtil.getQualityFullName(this.wQuality)+"<br/>";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * 转换五行类型
	 */
	public String changeWXType()
	{
		String hint = "恭喜您，转换属性成功，"+this.getFullName()+getWuxing().changeWXType();
		this.save();
		return hint;
	}
	

	/**
		 * 得到可以给该装备镶嵌的道具类型字符串
		 * @return
		 */
		public String getInlayPropTypeStr()
		{
			StringBuffer prop_type_str = new StringBuffer();
			
			Map<Integer,String> wu_type = this.getWuxing().getWxType();
			if( wu_type.get(WuXing.JIN)!=null)
			{
				prop_type_str.append(PropType.EQUIP_INLAY_STONE_JIN).append(",");
			}
			if( wu_type.get(WuXing.MU)!=null)
			{
				prop_type_str.append(PropType.EQUIP_INLAY_STONE_MU).append(",");
			}
			if( wu_type.get(WuXing.SHUI)!=null)
			{
				prop_type_str.append(PropType.EQUIP_INLAY_STONE_SHUI).append(",");
			}
			if( wu_type.get(WuXing.HUO)!=null)
			{
				prop_type_str.append(PropType.EQUIP_INLAY_STONE_HUO).append(",");
			}
			if( wu_type.get(WuXing.TU)!=null)
			{
				prop_type_str.append(PropType.EQUIP_INLAY_STONE_TU).append(",");
			}
			
			if( prop_type_str.length()==0 )
			{
				return "0";
			}
			
			return prop_type_str.toString().substring(0,prop_type_str.length()-1);
		}
	/**
	 * 升级
	 */
	public void upgrade()
	{
		if( this.wZbGrade>=13)
			return;
		this.wZbGrade++;//等级增加
		if( this.wZbGrade==13 )//升到最高级时，给装备附加属性
		{
			EquipAppendAttri equip_append_attri = this.getGameEquip().getAppendAttri();
			if( equip_append_attri!=null )
			{
				this.appendAttriByAttrisStr(equip_append_attri.getAttriStr());
				this.appendAttriDes = equip_append_attri.getAttriDes();
			}
		}
		save();//保存到数据库
	}
		
	/**
	 * 镶嵌
	 * @return
	 */
	public String inlay( PlayerPropGroupVO stone)
	{
		if( stone==null || stone.getPropNum()<=0 )
		{
			return "请选择要镶嵌的宝石";
		}
		if( this.leftEffectHoleNum==0 )
		{
			return "装备已经没有可以镶嵌的孔了";
		}
		
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(this.pPk, stone.getPropId(), 1,GameLogManager.R_MATERIAL_CONSUME);
		this.appendAttriByAttrisStr(stone.getPropInfo().getPropOperate1());
		if( this.inlayPropStr.equals(""))
		{
			this.inlayPropStr=stone.getPropInfo().getPropOperate3();
		}
		else
		{
			this.inlayPropStr+=","+stone.getPropInfo().getPropOperate3();
		}
		this.leftEffectHoleNum--;
		this.save();
		return "恭喜您，宝石已镶嵌成功！";
	}
	
	/**
	 * 得到游戏装备基础信息
	 * @return
	 */
	public GameEquip getGameEquip()
	{
		return EquipCache.getById(equipId);
	}
	

	public int getWLevel()
	{
		return wLevel;
	}

	public void setWLevel(int level)
	{
		wLevel = level;
	}

	public int getWZbGrade()
	{
		return wZbGrade;
	}

	public void setWZbGrade(int zbGrade)
	{
		wZbGrade = zbGrade;
	}

	public int getSuitId()
	{
		return getGameEquip().getSuitId();
	}

	
	public int getPwPk()
	{
		return pwPk;
	}

	public void setPwPk(int pwPk)
	{
		this.pwPk = pwPk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public String getWName()
	{
		return wName;
	}

	public void setWName(String name)
	{
		wName = name;
	}

	public int getWFyXiao()
	{
		return wFyXiao;
	}

	public void setWFyXiao(int fyXiao)
	{
		wFyXiao = fyXiao;
	}

	public int getWFyDa()
	{
		return wFyDa;
	}

	public void setWFyDa(int fyDa)
	{
		wFyDa = fyDa;
	}

	public int getWGjXiao()
	{
		return wGjXiao;
	}

	public void setWGjXiao(int gjXiao)
	{
		wGjXiao = gjXiao;
	}

	public int getWGjDa()
	{
		return wGjDa;
	}

	public void setWGjDa(int gjDa)
	{
		wGjDa = gjDa;
	}

	public int getWHp()
	{
		return wHp;
	}

	public void setWHp(int hp)
	{
		wHp = hp;
	}

	public int getWMp()
	{
		return wMp;
	}

	public void setWMp(int mp)
	{
		wMp = mp;
	}


	public int getWType()
	{
		return wType;
	}

	public void setWType(int type)
	{
		wType = type;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getWQuality()
	{
		return wQuality;
	}

	public void setWQuality(int quality)
	{
		wQuality = quality;
	}

	public int getWPrice()
	{
		return getGameEquip().getPrice();
	}


	public int getEquipId()
	{
		return equipId;
	}

	public void setEquipId(int equipId)
	{
		this.equipId = equipId;
	}

	public int getEquipType()
	{
		return equipType;
	}

	public void setEquipType(int equipType)
	{
		this.equipType = equipType;
	}

	public int getWBonding()
	{
		return this.wBonding;
	}

	public int getWProtect()
	{
		return getGameEquip().getIsProtected();
	}

	public int getWIsreconfirm()
	{
		return getGameEquip().getIsReconfirm();
	}


	public int getHoleNum()
	{
		return holeNum;
	}

	public void setHoleNum(int holeNum)
	{
		this.holeNum = holeNum;
	}

	public PlayerWXVO getWuxing()
	{
		if( wuxing==null )
		{
			wuxing = new PlayerWXVO(this.wxAttrisStr);
		}
		return wuxing;
	}

	public String getInlayPropStr()
	{
		return inlayPropStr;
	}

	public void setInlayPropStr(String inlayPropStr)
	{
		this.inlayPropStr = inlayPropStr;
	}

	public String getWxAttrisStr()
	{
		return getWuxing().toString();
	}

	public void setWxAttrisStr(String wxAttrisStr)
	{
		this.wxAttrisStr = wxAttrisStr;
	}

	public void setWBonding(int bonding)
	{
		wBonding = bonding;
	}

	public int getLeftEffectHoleNum()
	{
		return leftEffectHoleNum;
	}

	public void setLeftEffectHoleNum(int leftEffectHoleNum)
	{
		this.leftEffectHoleNum = leftEffectHoleNum;
	}

	public void setEffectHoleNum(int effectHoleNum)
	{
		this.effectHoleNum = effectHoleNum;
	}

	public int getRankKey()
	{
		this.buildRankKey();
		return rankKey;
	}

	public void setRankKey(int rankKey)
	{
		this.rankKey = rankKey;
	}

	public Date getProtectEndTime()
	{
		return protectEndTime;
	}

	public void setProtectEndTime(Date protectEndTime)
	{
		this.protectEndTime = protectEndTime;
	}

	public int getMaxEndure()
	{
		return maxEndure;
	}

	public void setMaxEndure(int maxEndure)
	{
		if( maxEndure<=0)
		{
			maxEndure = 0 ;
		}
		this.maxEndure = maxEndure;
	}

	public int getCurEndure()
	{
		return curEndure;
	}

	public void setCurEndure(int curEndure)
	{
		if( curEndure<0 )
		{
			curEndure = 0;
		}
		this.curEndure = curEndure;
	}

	public String getAppendAttriDes()
	{
		return appendAttriDes;
	}

	public void setAppendAttriDes(String appendAttriDes)
	{
		this.appendAttriDes = appendAttriDes;
	}

}