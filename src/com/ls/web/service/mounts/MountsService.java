package com.ls.web.service.mounts;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.npc.NpcDao;
import com.ls.ben.dao.info.npc.NpcdropDao;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.dao.mounts.MountsDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.attack.DropGoodsVO;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.info.npc.NpcrefurbishVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.model.equip.GameEquip;
import com.ls.model.user.MountSet;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.StatisticsType;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.web.service.control.TimeControlService;
import com.ls.web.service.log.LogService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.npc.NpcService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;
import com.pm.service.chuansong.SuiBianChuanService;
import com.pm.vo.chuansong.SuiBianChuanVO;

/**
 * 处理坐骑service类
 * @author Thomas.lei
 */
public class MountsService
{
	MountsDao md=new MountsDao();
	
	/**********得到可供购买的坐骑********** */
	public List<MountsVO> getCanSentMounts()
	{
		return md.getCanSentMounts();
	}
	/** **********根据坐骑ID查询坐骑的详细信息**************** */
	public MountsVO getMountsInfo(int mountID)
	{
		return md.getMountsInfo(mountID);
	}
	/*******系统送给玩家的免费坐骑*********/
	public MountsVO getMountsInfoBySystem(int mountsType)
	{
		return md.getMountsInfoBySystem(mountsType);
	}
	/*********得到适合当前玩家练级的练级地********/
	public List<SuiBianChuanVO> getCurLianji(int prace,int pGrade)
	{
		SuiBianChuanService scs=new SuiBianChuanService();
		List<SuiBianChuanVO> list= scs.getSuiBianByTypeId(prace,2);
		List<SuiBianChuanVO> isList=new ArrayList<SuiBianChuanVO>();
		for (int i = 0; i < list.size(); i++)
		{
			int diGrade=0,gaoGrade=0;
			SuiBianChuanVO scv=list.get(i);
			String partGrade=scv.getPartGrade();
			String[] grades= partGrade.split(",");
			diGrade=Integer.parseInt(grades[0]);
			gaoGrade=Integer.parseInt(grades[1]);
			if(pGrade>=diGrade&&pGrade<=gaoGrade)
			{
				isList.add(scv);
			}
		}
		return isList;
	}
	/*****得到练级信息的字符串******/
	public String getStrForLianji(List<SuiBianChuanVO> list,int pGrade,String mountID)
	{
		NpcrefurbishDao nfd=new NpcrefurbishDao();
		StringBuffer sb=new StringBuffer();
		sb.append("您目前的等级为"+pGrade+"级，适合在");
		for (int i = 0; i <list.size(); i++)
		{
			SuiBianChuanVO scv=list.get(i);
			sb.append(scv.getSceneName());
			if(i!=list.size()-1)
			{
				sb.append(",");
			}
			else
			{
				sb.append("区域练级！");
			}
		}
		sb.append("<br/>");
		for (int i = 0; i < list.size(); i++)
		{
			SuiBianChuanVO scv=list.get(i);
			sb.append(scv.getSceneName());
			sb.append(" 刷新 ");
			List<NpcrefurbishVO> nvList=nfd.getNPCsBySenceId(scv.getSceneId());
			int temp=nvList.size();
			String tempStr="";
			String tempBossId="";
			for (int j = 0; j <temp; j++)
			{
				NpcrefurbishVO nv=nvList.get(j);
				if(nv.getIsBoss()==0)
				{
					sb.append("<anchor>");
					sb.append(NpcCache.getNpcNameById(nv.getNpcId()));
					sb.append("(");
					sb.append(NpcCache.getNpcGradeById(nv.getNpcId()));
					sb.append("级)");
					sb.append("<go href=");
					sb.append(GameConfig.getContextPath());
					sb.append("\"/mounts.do?cmd=n20&amp;npcId=");
					sb.append(nv.getNpcId()+"\"");
					sb.append(" method=\"get\"></go></anchor>");
					
				}
				else
				{
					tempStr=NpcCache.getNpcNameById(nv.getNpcId())+"("+NpcCache.getNpcGradeById(nv.getNpcId())+")";
					tempBossId=nv.getNpcId()+"";
				}
				if(j!=temp-1)
				{
					sb.append(",");
				}
				else
				{
					sb.append("以及BOSS");
					if(!tempStr.equals(""))
					{
						sb.append("<anchor>");
						sb.append(tempStr);
						sb.append("<go href=");
						sb.append(GameConfig.getContextPath());
						sb.append("\"/mounts.do?cmd=n20&amp;npcId=");
						sb.append(tempBossId+"\"");
						sb.append(" method=\"get\"></go></anchor>");
					}
					else
					{
						sb.append(" 无");
					}
					sb.append("!");
					sb.append("<br/>");
				}
			}
			sb.append("<anchor>");
			sb.append("前往");
			sb.append("<go href=");
			sb.append(GameConfig.getContextPath());
			sb.append("\"/mounts.do?cmd=n8&amp;scenceID=");
			sb.append(scv.getSceneId());
			sb.append("&amp;mountsID=");
			sb.append(mountID);
			sb.append("&amp;carryGrade=2\"");
			sb.append(" method=\"get\"></go></anchor>");
			sb.append("<br/>");
		}
		return sb.toString();
	}
	/*****得到NPC信息的字符串******/
	public String getStrForNpc(int npcId,int npcPic)
	{
		
		NpcdropDao nd=new NpcdropDao();
		StringBuffer sb=new StringBuffer();
		StringBuffer buff=new StringBuffer();
		if(npcPic==1)
		{
			buff.append("<img src=\"");
			buff.append(GameConfig.getContextPath());
			buff.append("/image/npc/"+NpcCache.getById(npcId).getPic()+".png\" alt=\"huli\"");
			buff.append("></img><br/>");
		}
		else
		{
			buff.append("");
		}
		sb.append(NpcCache.getNpcNameById(npcId));
		sb.append(",");
		sb.append(NpcCache.getNpcGradeById(npcId));
		sb.append("级<br/>");
		sb.append(buff.toString());
		sb.append("可掉落：");
		List<NpcdropVO> list=nd.getNpcDropByNpcId(npcId);
		if(list!=null&&list.size()!=0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				NpcdropVO nv=list.get(i);
				sb.append("<anchor>");
				sb.append(nv.getGoodsName());
				sb.append("<go href=");
				sb.append(GameConfig.getContextPath());
				sb.append("\"/mounts.do?cmd=n21&amp;goodId=");
				sb.append(nv.getGoodsId());
				sb.append("&amp;goodType=");
				sb.append(nv.getGoodsType());
				sb.append("\"");
				sb.append(" method=\"get\"></go></anchor>");
				if(i!=list.size()-1)
				{
					sb.append(",");
				}
			}
		}
		
		
		
		return sb.toString();
	}
	/*****得到NPC掉落物品信息的字符串******/
	public String getStrForNpcDrop(int goodId,int goodType)
	{
		StringBuffer sb=new StringBuffer();
		if(goodType==1)
		{
			GameEquip gequip= EquipCache.getById(goodId);
			sb.append("〖");
			sb.append(gequip.getName());
			sb.append("〗");
			sb.append("<br/>");
			sb.append("等级：");
			sb.append(gequip.getGrade());
			sb.append("<br/>");
			sb.append("价钱：");
			sb.append(gequip.getPrice());
			sb.append("<br/>");
			sb.append(gequip.getDes());
			
		}
		else if(goodType==4)
		{
			PropVO  pv=PropCache.getPropById(goodId);
			sb.append(pv.getPropName());
			sb.append("<br/>");
			sb.append("使用等级：");
			sb.append(pv.getPropReLevel());
			sb.append("<br/>");
			sb.append("卖出价钱：");
			sb.append(pv.getPropSell());
			sb.append("<br/>");
			sb.append(pv.getPropDisplay());
		}
		else
		{
			sb.append("没有该物品信息");
		}
		return sb.toString();
	}
	/*********其他方式的坐骑使用例如任务传送******/
	public String useMountsByTask(RoleEntity roleInfo,String scene_id)
	{
		String hint="";
		TimeControlService tcs=new TimeControlService();
		MountSet mSet=roleInfo.getMountSet();
		if(mSet==null)
		{
			mSet=new MountSet(roleInfo.getPPk());
		}
		UserMountsVO umv=mSet.getCurMount();
		MountsVO mv=null;
		if(umv==null)
		{
			hint="请点击坐骑进行乘骑";
			return hint;
		}
		mv=umv.getMountInfo();
		if(mv!=null)
		{
			/*********红名传送要附加处理********/
			int needCopper=roleInfo.isRedname()?roleInfo.getBasicInfo().getGrade()/2*2:roleInfo.getBasicInfo().getGrade()/2;
			int carryNum=roleInfo.isRedname()?mv.getCarryNum1()/2:mv.getCarryNum1();
			if(mv.getLevel()==1)
			{
				boolean isUse=tcs.isUseable(roleInfo.getBasicInfo().getPPk(), mv.getId(), mv.getLevel(), carryNum);
				if(!isUse)
				{
					long haveCopper=roleInfo.getBasicInfo().getCopper();
					if(needCopper>haveCopper)
					{
						hint="今日传送次数已满"+carryNum+"次，您灵石不足不能传送";
						return hint;
					}
					else
					{
						hint="您的坐骑今日免费传送次数已满"+carryNum+"次扣除"+needCopper+"灵石";
						//监控
						LogService logService = new LogService();
						logService.recordMoneyLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), roleInfo.getBasicInfo().getCopper()+"", "-"+needCopper, "传送");
						roleInfo.getBasicInfo().addCopper(-needCopper);
						//执行统计
						GameSystemStatisticsService gsss = new GameSystemStatisticsService();
						gsss.addPropNum(6, StatisticsType.MONEY, roleInfo.getBasicInfo().getCopper(), StatisticsType.USED, StatisticsType.CHUANSONG,roleInfo.getBasicInfo().getPPk());
					}
				}
				tcs.updateControlInfo(roleInfo.getBasicInfo().getPPk(),mv.getId(), mv.getLevel());
			}
		}
		roleInfo.getBasicInfo().updateSceneId(scene_id); 
		return hint;
	}
	/*********得到坐骑的描述信息*********/
	public String getMountsDisplay(int mountsID)
	{
		MountsVO mv=getMountsInfo(mountsID);
		String type="";
		if(mv.getType()==1)
		{
			type="走兽";
		}
		else if(mv.getType()==2)
		{
			type="飞禽";
		}
		else
		{
			type="鳞甲";
		}
		StringBuffer sb=new StringBuffer();
		sb.append("【");
		sb.append(mv.getName());
		sb.append("】");
		sb.append("(");
		sb.append(type);
		sb.append(")");
		sb.append("<br/>");
		sb.append("<img src=\""+GameConfig.getContextPath()+"/image/item/mounts/"+mv.getImage()+".png\" alt=\"\"/><br/>");
		sb.append("<br/>");
		sb.append(mv.getDisplay());
		sb.append("<br/>");
		sb.append("等级："+mv.getLevel()+"级");
		sb.append("<br/>");
		return sb.toString();
	}
	/************删除玩家所有的坐骑*******/
	public void removeMountsInfo(int ppk)
	{
		md.removeMountsInfo(ppk);
	}
	/*****电信渠道专用传送扣费0扣费成功1扣费失败****/
	public boolean mountCarryForTele(HttpServletRequest request,RoleEntity role,String mountsId,String mountLevel)
	{
		boolean can=true;
		MallService ms=new MallService();
		String consumeCode=mountsId+mountLevel+mountLevel+mountLevel;
		String hint= ms.consumeForTele(request, role, consumeCode, "1");
		/**扣费失败**/
		if(hint!=null)
		{
			can=false;
		}
		return can;
	}
}
