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
 * 帮派建筑
 */
public class FGameBuild
{
	private int id;
	private int grade;//等级
	private String name;//名字
	private String pic;//图片
	
	private String des;//需要材料描述
	private int prestige;//修建需要的帮派声望
	private int mId;//修建需要的材料id
	private int mNum;//修建需要的材料数量
	
	private int contribute;//领取消耗的帮派贡献
	private String buffIdStr;//功能字节：buffid字符串,1-2
	private List<BuffVO> buff_list = null;
	
	private int nextGradeId;//下一级id，无下一级添0
	
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
	 * 得到所需材料信息
	 * @return
	 */
	public PropVO getMaterial()
	{
		return PropCache.getPropById(mId);
	}
	
	/**
	 * 简单描述信息
	 * @return
	 */
	public String getSimpleDes()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(").append(getBuffName()).append(",").append(des).append(")");
		return sb.toString();
	}
	
	/**
	 * 得到下一等级的详细信息
	 * @return
	 */
	public FGameBuild getNextGradeBuild()
	{
		return FBuildService.getGameBuildById(nextGradeId);
	}
	
	/**
	 * 得到buff效果描述
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
			DataErrorLog.debugData("FGameBuild.getBuffName，图腾buff数据错误：id="+id+";buffIdStr="+buffIdStr);
			return "";
		}
		
	}
	/**
	 * 得到buff效果详细描述
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
	 * 详情描述
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
		sb.append("等级:").append(this.grade).append("级").append("<br/>");
		sb.append("祝福效果:").append("<br/>");
		
		FGameBuild next_grade_build = this;
		do
		{
			sb.append(next_grade_build.getGrade()).append("级,").append(next_grade_build.getBuffDisplay()).append(",需").append(next_grade_build.getDes()).append("<br/>");
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
