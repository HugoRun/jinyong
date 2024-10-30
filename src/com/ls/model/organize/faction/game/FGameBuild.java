package com.ls.model.organize.faction.game;

import java.util.ArrayList;
import java.util.List;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.faction.FBuildService;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * ���ɽ���
 */
public class FGameBuild
{
	private int id;
	private int grade;//�ȼ�
	private String name;//����
	private String pic;//ͼƬ
	
	private String des;//��Ҫ��������
	private int prestige;//�޽���Ҫ�İ�������
	private int mId;//�޽���Ҫ�Ĳ���id
	private int mNum;//�޽���Ҫ�Ĳ�������
	
	private int contribute;//��ȡ���ĵİ��ɹ���
	private String buffIdStr;//�����ֽڣ�buffid�ַ���,1-2
	private List<BuffVO> buff_list = null;
	
	private int nextGradeId;//��һ��id������һ����0
	
	public List<BuffVO> getBuffList()
	{
		if( buff_list==null )
		{
			buff_list = new ArrayList<BuffVO>(3);
			if( buffIdStr!=null )
			{
				String[] buff_id_str_list = buffIdStr.split("-");
				BuffDao buffDao = new BuffDao();
				for(String buff_id_str:buff_id_str_list)
				{
					BuffVO buff = buffDao.getBuff(Integer.parseInt(buff_id_str));
					if( buff!=null )
					{
						buff_list.add(buff);
					}
				}
			}
		}
		return buff_list;
	}
	
	/**
	 * �õ����������Ϣ
	 * @return
	 */
	public PropVO getMaterial()
	{
		return PropCache.getPropById(mId);
	}
	
	/**
	 * ��������Ϣ
	 * @return
	 */
	public String getSimpleDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(").append(getBuffName()).append(",").append(des).append(")");
		return sb.toString();
	}
	
	/**
	 * �õ���һ�ȼ�����ϸ��Ϣ
	 * @return
	 */
	public FGameBuild getNextGradeBuild()
	{
		return FBuildService.getGameBuildById(nextGradeId);
	}
	
	/**
	 * �õ�buffЧ������
	 * @return
	 */
	public String getBuffName()
	{
		try
		{
			return getBuffList().get(0).getBuffName();
		}
		catch (Exception e)
		{
			DataErrorLog.debugData("FGameBuild.getBuffName��ͼ��buff���ݴ���id="+id+";buffIdStr="+buffIdStr);
			return "";
		}
		
	}
	/**
	 * �õ�buffЧ����ϸ����
	 * @return
	 */
	public String getBuffDisplay()
	{
		try
		{
			return getBuffList().get(0).getBuffDisplay();
		}
		catch (Exception e)
		{
			return "";
		}
	}
	
	
	/**
	 * ��������
	 */
	public String getDisplay()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.name).append("<br/>");
		if( pic!=null && !pic.equals(""))
		{
			sb.append(ExchangeUtil.getPicDisplay(pic)).append("<br/>");
		}
		sb.append(this.des).append("<br/>");
		sb.append("�ȼ�:").append(this.grade).append("��").append("<br/>");
		sb.append("ף��Ч��:").append("<br/>");
		
		FGameBuild next_grade_build = this;
		do
		{
			sb.append(next_grade_build.getGrade()).append("��,").append(next_grade_build.getBuffDisplay()).append(",��").append(next_grade_build.getDes()).append("<br/>");
		}
		while( (next_grade_build = next_grade_build.getNextGradeBuild())!=null );
		return sb.toString();
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPic()
	{
		return pic;
	}
	public void setPic(String pic)
	{
		this.pic = pic;
	}
	public int getContribute()
	{
		return contribute;
	}
	public void setContribute(int contribute)
	{
		this.contribute = contribute;
	}
	public int getNextGradeId()
	{
		return nextGradeId;
	}
	public void setNextGradeId(int nextGradeId)
	{
		this.nextGradeId = nextGradeId;
	}
	public int getPrestige()
	{
		return prestige;
	}
	public void setPrestige(int prestige)
	{
		this.prestige = prestige;
	}

	public int getMId()
	{
		return mId;
	}

	public void setMId(int id)
	{
		mId = id;
	}

	public int getMNum()
	{
		return mNum;
	}

	public void setMNum(int num)
	{
		mNum = num;
	}

	public String getDes()
	{
		return des;
	}

	public void setDes(String des)
	{
		this.des = des;
	}

	public String getBuffIdStr()
	{
		return buffIdStr;
	}

	public void setBuffIdStr(String buffIdStr)
	{
		this.buffIdStr = buffIdStr;
	}
}
