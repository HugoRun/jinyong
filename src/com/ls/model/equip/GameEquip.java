package com.ls.model.equip;

import com.ls.ben.dao.goods.equip.EquipAppendAttrDao;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Equip;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;

/**
 * @author ls
 * ��Ϸװ������
 */
public class GameEquip
{
	//��������
	private int id;
	private String name;
	private String des;
	private String pic;
	private int type;//װ������:1������2ͷ����3�·���4Ҫ����5Ь�ӣ�6��Ʒ
	private int price;//������Ǯ
	private int endure;//����;ö�
	
	//ʹ����������
	private int grade;//װ���ȼ�����ʹ�õȼ�
	private int sex;
	private int job;//����
	private int isMarried;
	private int isBind;//�Ƿ��
	private int isProtected;//�Ƿ����ܱ�����
	private int isReconfirm;//�Ƿ���Ҫ����ȷ��

	//ս������
	private int minAtt;
	private int maxAtt;
	private int minDef;
	private int maxDef;
	
	private String otherAttriStr="";//���Ʒ�ʵ�װ���Ĺ̶��������ַ���,�磺jinAtt,2-maxHp,100
	
	private String appendAttriStr="";//�ȼ�������߼�ʱ�����������ַ���,�磺jinAtt,2-maxHp,100
	
	//��������
	private int suitId;//��װid
	private int qualityRate=1;//Ʒ�ʵ���ϵ��
	private int dropNum=0;//��װ������������(���ֶβ��������ݿ�)
	
	/**
	 * ��װ��������ߵȼ�ʱ����õ�һ��װ������
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
	 * �õ������
	 * @return   
	 */
	public int getHoleNum( int quality )
	{
		int min_hole_num = 0;//��С����
		int max_hole_num = 0;//������
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
	 * ��Ǯ����
	 */
	public String getPriceDes()
	{
		return ExchangeUtil.getMoneyDes(this.price);
	}
	
	/**
	 * �õ�ͼƬ��ʾ����·��
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
	 * �õ�����Ʒ��
	 * ����װ��Ʒ��װ����ʽ����Ϊ���յ����������м��㣬����һϵ����
	 * ���������㣩����Ϊ1��ϵ����
	 * ��������������������Ϊ10��ϵ����
	 * �����鱦����Ʒ������Ϊ100��ϵ����
	 * ���Ҫ�󽫵�����п������Ե��伸�ʵ��ֶθ�Ϊϵ�������ֶ�
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
