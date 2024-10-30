package com.ls.model.equip;

import com.ls.ben.dao.goods.equip.EquipAppendAttrDao;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Equip;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;

/**
 * @author ls
 * 游戏装备数据
 */
public class GameEquip
{
	//基础属性
	private int id;
	private String name;
	private String des;
	private String pic;
	private int type;//装备类型:1武器，2头盔，3衣服，4要带，5鞋子，6饰品
	private int price;//卖出价钱
	private int endure;//最大耐久度
	
	//使用限制属性
	private int grade;//装备等级，即使用等级
	private int sex;
	private int job;//种族
	private int isMarried;
	private int isBind;//是否绑定
	private int isProtected;//是否是受保护的
	private int isReconfirm;//是否需要二次确认

	//战斗属性
	private int minAtt;
	private int maxAtt;
	private int minDef;
	private int maxDef;
	
	private String otherAttriStr="";//最高品质的装备的固定，属性字符串,如：jinAtt,2-maxHp,100
	
	private String appendAttriStr="";//等级升到最高级时，附加属性字符串,如：jinAtt,2-maxHp,100
	
	//其他属性
	private int suitId;//套装id
	private int qualityRate=1;//品质掉落系数
	private int dropNum=0;//该装备，掉落数量(该字段不保存数据库)
	
	/**
	 * 当装备升到最高等级时随机得到一个装备属性
	 */
	public EquipAppendAttri getAppendAttri()
	{
		if( appendAttriStr==null )
		{
			return null;
		}
		String[] attri_list = appendAttriStr.split(",");
		if( attri_list.length<=0 )
		{
			return null;
		}
		int random_index = MathUtil.getRandomBetweenXY(0, attri_list.length-1);
		EquipAppendAttrDao equipAppendAttrDao = new EquipAppendAttrDao();
		return equipAppendAttrDao.getById(attri_list[random_index]);
	}
	
	/**
	 * 得到打孔数
	 * @return   
	 */
	public int getHoleNum( int quality )
	{
		int min_hole_num = 0;//最小孔数
		int max_hole_num = 0;//最大孔数
		switch(quality)
		{
			case 1:
				min_hole_num = 1;
				max_hole_num = 1;
				break;
			case 2:
				min_hole_num = 2;
				max_hole_num = 2;
				break;
			case 3:
				min_hole_num = 3;
				max_hole_num = 4;
				break;
			case 4:
				min_hole_num = 3;
				max_hole_num = 5;
				break;
			default:
				return 0;
		}
		return MathUtil.getRandomBetweenXY(min_hole_num, max_hole_num);
	}
	
	/**
	 * 价钱描述
	 */
	public String getPriceDes()
	{
		return ExchangeUtil.getMoneyDes(this.price);
	}
	
	/**
	 * 得到图片显示完整路径
	 * @return
	 */
	public String getPicDisplay()
	{
		if( pic!=null && !pic.equals(""))
		{
			return "<img src=\""+GameConfig.getContextPath()+"/image/item/equip/"+pic+".png\" alt=\"\"/><br/>";
		}
		return "";
	}
	
	/**
	 * 得到掉落品质
	 * 掉落装备品质装备公式调整为按照掉落数量进行计算，即设一系数，
	 * 宝器（优秀）掉率为1×系数；
	 * 后天灵器（精良）掉率为10×系数；
	 * 先天灵宝（极品）掉率为100×系数。
	 * 这就要求将掉落表中控制属性掉落几率的字段改为系数控制字段
	 */
	public int getDropQuality()
	{
		dropNum++;
		if( dropNum%(100*qualityRate) == 0  )
		{
			return Equip.Q_JIPIN;
		}
		if( dropNum%(10*qualityRate) == 0  )
		{
			return Equip.Q_LIANGHAO;
		}
		if( dropNum%(1*qualityRate) == 0  )
		{
			return Equip.Q_YOUXIU;
		}
		
		return Equip.Q_PUTONG;
	}
	
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getDes()
	{
		return des;
	}
	public String getPic()
	{
		return pic;
	}
	public int getType()
	{
		return type;
	}
	public int getPrice()
	{
		return price;
	}
	public int getEndure()
	{
		return endure;
	}
	public int getSex()
	{
		return sex;
	}
	public int getJob()
	{
		return job;
	}
	public int getMinAtt()
	{
		return minAtt;
	}
	public int getMaxAtt()
	{
		return maxAtt;
	}
	public int getMinDef()
	{
		return minDef;
	}
	public int getMaxDef()
	{
		return maxDef;
	}
	public String getOtherAttriStr()
	{
		return otherAttriStr;
	}
	public String getAppendAttriStr()
	{
		return appendAttriStr;
	}
	public int getSuitId()
	{
		return suitId;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setDes(String des)
	{
		this.des = des;
	}
	public void setPic(String pic)
	{
		this.pic = pic;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
	public void setEndure(int endure)
	{
		this.endure = endure;
	}
	public void setSex(int sex)
	{
		this.sex = sex;
	}
	public void setJob(int job)
	{
		this.job = job;
	}
	public void setMinAtt(int minAtt)
	{
		this.minAtt = minAtt;
	}
	public void setMaxAtt(int maxAtt)
	{
		this.maxAtt = maxAtt;
	}
	public void setMinDef(int minDef)
	{
		this.minDef = minDef;
	}
	public void setMaxDef(int maxDef)
	{
		this.maxDef = maxDef;
	}
	public void setOtherAttriStr(String otherAttriStr)
	{
		this.otherAttriStr = otherAttriStr;
	}
	public void setAppendAttriStr(String appendAttriStr)
	{
		this.appendAttriStr = appendAttriStr;
	}
	public void setSuitId(int suitId)
	{
		this.suitId = suitId;
	}

	public int getGrade()
	{
		return grade;
	}


	public void setGrade(int grade)
	{
		this.grade = grade;
	}
	public int getQualityRate()
	{
		return qualityRate;
	}
	public void setQualityRate(int qualityRate)
	{
		this.qualityRate = qualityRate;
	}

	public int getIsMarried()
	{
		return isMarried;
	}

	public int getIsBind()
	{
		return isBind;
	}

	public int getIsProtected()
	{
		return isProtected;
	}

	public int getIsReconfirm()
	{
		return isReconfirm;
	}

	public void setIsMarried(int isMarried)
	{
		this.isMarried = isMarried;
	}

	public void setIsBind(int isBind)
	{
		this.isBind = isBind;
	}

	public void setIsProtected(int isProtected)
	{
		this.isProtected = isProtected;
	}

	public void setIsReconfirm(int isReconfirm)
	{
		this.isReconfirm = isReconfirm;
	}
}
