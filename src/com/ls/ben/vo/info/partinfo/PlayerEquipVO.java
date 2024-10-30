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
 * @author ��˧
 * 4:11:29 PM
 */
public class PlayerEquipVO
{
	private static int equip_punch_prop_id = 5;//��׵���id
	
	private int pwPk;//id
	private int pPk;//��ɫid
	private int equipId;//װ��ID
	private int equipType;//װ������
	private String wName;//װ������
	
	private int curEndure;//ʣ����;�
	private int maxEndure;//�;�����
	private int wBonding;//�Ƿ��
	
	private int wFyXiao;//��С����
	private int wFyDa;//������
	private int wGjXiao;//��С����
	private int wGjDa;//��󹥻�
	
	private int wHp;//���ӵ�Ѫ������
	private int wMp;//���ӵķ�������
	
	private PlayerWXVO wuxing;//װ����������
	private String wxAttrisStr;//���������ַ���
	private String inlayPropStr="";//��Ƕ�����ַ���
	
	private int wType;//װ�����ڵ�λ��
	private int wQuality;//Ʒ��
	private int wZbGrade;//װ������, ����ҽ������˼���, Ĭ��Ϊ�㼶.
	private String createTime;//����ʱ��
	private int wLevel;//ʹ�õȼ�
	private int holeNum = -1;//�ܵĿ׵�����
	private int effectHoleNum;//��Ч����(�����Ŀ���)
	private int leftEffectHoleNum;//ʣ����Ч����(������Ƕ��ʯ�Ŀ���)
	private int rankKey;// ������������ؼ���
	private Date protectEndTime=null;//װ���ܱ����Ľ���ʱ��(Ϊ��ʱ���ܱ���)
	
	private String appendAttriDes;//װ��������߼�ʱ���������Ե�����
	
	/**
	 * �Ƿ�������
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
	 * װ������
	 */
	public void drop()
	{
		GameLogManager.getInstance().equipLog(pPk, this, GameLogManager.R_DEAD);
		this.pPk=-1;
		this.wType=0;
		new PlayerEquipDao().updateOwner(this.pwPk, pPk);
	}
	
	/**
	 * �õ�����������
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
	 * ����װ���;�����
	 * @param consume_ponit    ���ĵ���
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
	 * ��װ���Ƿ�����ĳ��
	 */
	public String isOwnByPPk( int p_pk)
	{
		if( this.pPk==p_pk )
		{
			return null;
		}
		else
		{
			return "��װ����������";
		}
	}
	/**
	 * �ж������Ƿ���Ҫ����ȷ��
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
	 * �ж��Ƿ���Խ���
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
	 * �õ�װ���ܱ���������(��װ���ѱ�����,ʣ��**����)
	 * @return
	 */
	public String getProtectDes()
	{
		if(this.isProtectOnPK()==false)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(��װ���ѱ�����,ʣ��").append(DateUtil.getDifferTimes(new Date(), protectEndTime)).append("����)");
		
		return sb.toString();
	}
	
	/**
	 * ����װ��
	 * @param protect_time �ܱ�����ʱ�䣨���ӣ�
	 */
	public void protectEquip( int protect_time )
	{
		if( protect_time<=0 )
		{
			return;
		}
		Date now = new Date();
		if( protectEndTime!=null && now.before(protectEndTime))//û���ܱ�������û�г�ʱ
		{
			return;
		}
		protectEndTime = new Date(now.getTime()+protect_time*DateUtil.MINUTE*1000);
		this.save();
	}
	
	/**
	 * PKʱ��װ���Ƿ��Ƿ��ܱ���
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
			//��ʱ����Ϊ��
			//������Ϣ������ҳ�ʱ
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
	 * �������а����й���
	 * һ��������������������������+���Թ�����*10��
	 * �������������Կ�����
	 * �����������ﵽ����ʱ��
	 * @return
	 */
	private void buildRankKey()
	{
		this.rankKey = this.getMaxAtt()+this.getMaxDef()+this.getWuxing().getRankKey();
	}
	
	/**
	 * ���װ���İ�״̬
	 * @return
	 */
	public void unbind()
	{
		if( this.wBonding==0 || this.wQuality==Equip.Q_ORANGE)//��ɫװ�����ܽ��
		{
			return;
		}
		this.wBonding = 0;
		this.save();
	}
	
	/**
	 * �޸��𻵵�װ��
	 * @return
	 */
	public void maintainBad()
	{
		this.maxEndure = this.getGameEquip().getEndure()*10;
		this.curEndure = this.maxEndure;
		this.save();
	}
	
	/**
	 * �õ������;õĵ���
	 */
	public int getConsumeEndure()
	{
		return (this.maxEndure/10-this.curEndure/10);
	}
	
	/**
	 * �õ�װ�����������
	 */
	public int getMaintainFee()
	{
		if( this.isEffected()==false )//�𻵵�װ����������
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
	 * ����װ���;ã��жϸ�װ���Ƿ���Ч
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
	 * ����״̬����
	 */
	public String getHoleDes()
	{
		if( this.holeNum<=0 )
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("��ʯ��:").append(getEffectHoleNum()).append("/").append(this.holeNum).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * ��Ƕ����
	 * @return
	 */
	public String getInlayDes()
	{
		if( getEffectHoleNum()<=0 )
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("��Ƕ����:");
		if( this.inlayPropStr!=null && !this.inlayPropStr.equals("") )
		{
			sb.append(this.inlayPropStr);
		}
		else
		{
			sb.append("��");
		}
		sb.append("<br/>");
		sb.append("��Ƕ״̬:").append(this.getInlayStoneNum()).append("/").append(getEffectHoleNum()).append("<br/>");
		return sb.toString();
	}
	
	/**
	 * ��Ч�Ŀ���
	 */
	public int getEffectHoleNum()
	{
		return effectHoleNum;
	}
	/**
	 * �õ���Ƕ�ı�ʯ��
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
	 * װ�����
	 */
	public String punch( RoleEntity roleEntity)
	{
		String hint = null;
		
		if( roleEntity==null )
		{
			return "���ݴ���";
		}
		
		hint = this.isOwnByPPk(roleEntity.getBasicInfo().getPPk());
		if(hint!=null)
		{
			return hint;
		}
		
		if( this.getEffectHoleNum()>=this.getHoleNum() )
		{
			return "��װ���Ѳ��ܴ����";
		}
		
		GoodsService goodsService = new GoodsService();
		int consume_num = goodsService.removeProps(roleEntity.getBasicInfo().getPPk(), equip_punch_prop_id, 1,GameLogManager.R_MATERIAL_CONSUME);
		if( consume_num==-1)
		{
			return PropCache.getPropById(equip_punch_prop_id).getPropName()+"��������";
		}
		
		if( isPunchSuccess()==false )
		{
			hint = "̫�����ˣ�������ʧ�ܣ������Ŭ����";
		}
		else
		{
			this.effectHoleNum++;
			this.leftEffectHoleNum++;
			PlayerEquipDao playerEquipDao = new PlayerEquipDao();
			playerEquipDao.addEffectHoleNum(this.pwPk);
			hint = "��ϲ�����˿��ѳɹ��򿪣������ڿ�ȥ��Ƕ��ʯ�ˣ�";
		}
		return hint;
	}
	/**
	 * ����Ƿ�ɹ�
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
	 * װ���ȼ���������ԵĹ�ʽ:�����������ԡ�[1+(�ȼ���5)%]
	 */
	private int getTotalValue(int basic_value)
	{
		return (int) Math.round(basic_value * (1+1.0*(this.wZbGrade*5)/100));
	}
	
	/**
	 * �ܵĹ���С
	 * @return
	 */
	public int getMinAtt()
	{
		return this.getTotalValue(this.wGjXiao);
	}
	/**
	 * �ܵĹ�����
	 * @return
	 */
	public int getMaxAtt()
	{
		return this.getTotalValue(this.wGjDa);
	}
	
	/**
	 * �ܵķ���С
	 * @return
	 */
	public int getMinDef()
	{
		return this.getTotalValue(this.wFyXiao);
	}
	/**
	 * �ܵķ�����
	 * @return
	 */
	public int getMaxDef()
	{
		return this.getTotalValue(this.wFyDa);
	}
	
	/**
	 * ����װ������
	 */
	public void save()
	{
		buildRankKey();//ÿ�α���ʱ���������������ؼ���
		PlayerEquipDao playerEquipDao = new PlayerEquipDao();
		playerEquipDao.updateEquipAttri(this);
	}
	
	
	/**
	 * �����������͸�װ����������
	 * @param sttriStr   �����ַ���
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
	 * �����������͸�װ����������
	 * @param attri_type      ��������
	 * @param attri_value    ����ֵ
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
	 * �õ�װ��ȫ��
	 */
	public String getFullName()
	{
		StringBuffer full_name = new StringBuffer();
		if( this.wZbGrade== 13){
			full_name.append("����");
		}else if(this.wZbGrade>0){
			full_name.append("+").append(this.wZbGrade);
		}
		full_name.append(this.wName);
		full_name.append(ExchangeUtil.getQualityName(this.wQuality));
		return full_name.toString();
	}
	
	/**
	 * �õ�Ʒ��ȫ��
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
	 * ת����������
	 */
	public String changeWXType()
	{
		String hint = "��ϲ����ת�����Գɹ���"+this.getFullName()+getWuxing().changeWXType();
		this.save();
		return hint;
	}
	

	/**
		 * �õ����Ը���װ����Ƕ�ĵ��������ַ���
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
	 * ����
	 */
	public void upgrade()
	{
		if( this.wZbGrade>=13)
			return;
		this.wZbGrade++;//�ȼ�����
		if( this.wZbGrade==13 )//������߼�ʱ����װ����������
		{
			EquipAppendAttri equip_append_attri = this.getGameEquip().getAppendAttri();
			if( equip_append_attri!=null )
			{
				this.appendAttriByAttrisStr(equip_append_attri.getAttriStr());
				this.appendAttriDes = equip_append_attri.getAttriDes();
			}
		}
		save();//���浽���ݿ�
	}
		
	/**
	 * ��Ƕ
	 * @return
	 */
	public String inlay( PlayerPropGroupVO stone)
	{
		if( stone==null || stone.getPropNum()<=0 )
		{
			return "��ѡ��Ҫ��Ƕ�ı�ʯ";
		}
		if( this.leftEffectHoleNum==0 )
		{
			return "װ���Ѿ�û�п�����Ƕ�Ŀ���";
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
		return "��ϲ������ʯ����Ƕ�ɹ���";
	}
	
	/**
	 * �õ���Ϸװ��������Ϣ
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