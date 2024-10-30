package com.lw.service.player;

import javax.servlet.http.HttpServletRequest;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.log.LogService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;

public class PlayerPropGroupService
{
	// 判断包裹
	public String getPlayerPropGroupInfo(RoleEntity roleInfo)
	{
		int num = roleInfo.getBasicInfo().getWrapContent();
		String display = null;
		if (num == 50)
		{
			display = "第一次购买增加10个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×100!";
			return display;
		}
		else
			if (num == 60)
			{
				display = "第二次购买增加10个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×300!";
				return display;
			}
			else
				if (num == 70)
				{
					display = "第三次购买增加10个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×500!";
					return display;
				}
				else
					if (num == 80)
					{
						display = "第四次购买增加10个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×700!";
						return display;
					}
					else
						if (num == 90)
						{
							display = "第五次购买增加10个栏位需要花费【"+GameConfig.getYuanbaoName()+"】×1000!";
							return display;
						}
						else
						{
							display = "";
							return null;
						}
	}

	// 购买包裹
	public String buyPropGroup(RoleEntity roleInfo)
	{
		LogService logService = new LogService();
		EconomyService economyService = new EconomyService();
		int u_pk = roleInfo.getBasicInfo().getUPk();
		long yuanbao = economyService.getYuanbao(u_pk);
		int num = roleInfo.getBasicInfo().getWrapContent();
		int useyuanbao = 0;
		String display = null;
		if (num == 50)
		{
			useyuanbao = 100;
			if (useyuanbao > yuanbao)
			{
				display = "对不起，您的"+GameConfig.getYuanbaoName()+"不足，请进行<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>充值</anchor><br/>";
				return display;
			}
			else
			{
				roleInfo.getBasicInfo().addWrapContent(10);// 增加10个包裹格子
				roleInfo.getBasicInfo().addWrapSpare(10);// 增加10个包裹格子
				
				logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "购买包裹");
				economyService.spendYuanbao(u_pk, useyuanbao);
				
				display = "购买成功,您的包裹格数增加了10个格子,消费【"+GameConfig.getYuanbaoName()+"】×100!<br/>";
				return display;
			}
		}
		else
			if (num == 60)
			{
				useyuanbao = 300;
				if (useyuanbao > yuanbao)
				{
					display = "对不起，您的"+GameConfig.getYuanbaoName()+"不足，请进行<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>充值</anchor><br/>";
					return display;
				}
				else
				{
					roleInfo.getBasicInfo().addWrapContent(10);// 增加10个包裹格子
					roleInfo.getBasicInfo().addWrapSpare(10);// 增加10个包裹格子
					
					logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "购买包裹");
					economyService.spendYuanbao(u_pk, useyuanbao);
					
					display = "购买成功,您的包裹格数增加了10个格子,消费【"+GameConfig.getYuanbaoName()+"】×300!<br/>";
					return display;
				}
			}
			else
				if (num == 70)
				{
					useyuanbao = 500;
					if (useyuanbao > yuanbao)
					{
						display = "对不起，您的"+GameConfig.getYuanbaoName()+"不足，请进行<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>充值</anchor><br/>";
						return display;
					}
					else
					{
						roleInfo.getBasicInfo().addWrapContent(10);// 增加10个包裹格子
						roleInfo.getBasicInfo().addWrapSpare(10);// 增加10个包裹格子
						
						logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "购买包裹");
						economyService.spendYuanbao(u_pk, useyuanbao);
						
						display = "购买成功,您的包裹格数增加了10个格子,消费【"+GameConfig.getYuanbaoName()+"】×500!<br/>";
						return display;
					}
				}
		if (num == 80)
		{
			useyuanbao = 700;
			if (useyuanbao > yuanbao)
			{
				display = "对不起，您的"+GameConfig.getYuanbaoName()+"不足，请进行<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>充值</anchor><br/>";
				return display;
			}
			else
			{
				roleInfo.getBasicInfo().addWrapContent(10);// 增加10个包裹格子
				roleInfo.getBasicInfo().addWrapSpare(10);// 增加10个包裹格子
				
				logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "购买包裹");
				economyService.spendYuanbao(u_pk, useyuanbao);
				
				display = "购买成功,您的包裹格数增加了10个格子,消费【"+GameConfig.getYuanbaoName()+"】×700!<br/>";
				return display;
			}
		}
		else
			if (num == 90)
			{
				useyuanbao = 1000;
				if (useyuanbao > yuanbao)
				{
					display = "对不起，您的"+GameConfig.getYuanbaoName()+"不足，请进行<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>充值</anchor><br/>";
					return display;
				}
				else
				{
					roleInfo.getBasicInfo().addWrapContent(10);// 增加10个包裹格子
					roleInfo.getBasicInfo().addWrapSpare(10);// 增加10个包裹格子
					
					logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "购买包裹");

					economyService.spendYuanbao(u_pk, useyuanbao);
					display = "购买成功,您的包裹格数增加了10个格子,消费【"+GameConfig.getYuanbaoName()+"】×1000!<br/>";
					return display;
				}
			}
			else
			{
				display = "包裹格数错误请重新购买<br/>";
			}
		return display;
	}
	/**
	 * 为了程序的完整性对电信专门写一个方法
	 */
	public String buyPropGroup(HttpServletRequest request,RoleEntity roleInfo)
	{
		int num = roleInfo.getBasicInfo().getWrapContent();
		String consumCode="";
		if(num==50)
		{
			consumCode="wrap1";
		}
		else if(num==60)
		{
			consumCode="wrap2";
		}
		else if(num==70)
		{
			consumCode="wrap3";
		}
		else if(num==80)
		{
			consumCode="wrap4";
		}
		else if(num==90)
		{
			consumCode="wrap5";
		}
		String display = null;
		MallService ms=new MallService();
		String hint=ms.consumeForTele(request, roleInfo, consumCode, "1");
		if(hint!=null)
		{
			display=hint;
		}
		else
		{
			display="购买成功！您的包裹增加了10个格子！";
		}
		return display;
}
}