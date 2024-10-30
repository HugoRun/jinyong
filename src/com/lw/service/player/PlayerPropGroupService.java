package com.lw.service.player;

import javax.servlet.http.HttpServletRequest;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.log.LogService;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.player.EconomyService;

public class PlayerPropGroupService
{
	// �жϰ���
	public String getPlayerPropGroupInfo(RoleEntity roleInfo)
	{
		int num = roleInfo.getBasicInfo().getWrapContent();
		String display = null;
		if (num == 50)
		{
			display = "��һ�ι�������10����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����100!";
			return display;
		}
		else
			if (num == 60)
			{
				display = "�ڶ��ι�������10����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����300!";
				return display;
			}
			else
				if (num == 70)
				{
					display = "�����ι�������10����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����500!";
					return display;
				}
				else
					if (num == 80)
					{
						display = "���Ĵι�������10����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����700!";
						return display;
					}
					else
						if (num == 90)
						{
							display = "����ι�������10����λ��Ҫ���ѡ�"+GameConfig.getYuanbaoName()+"����1000!";
							return display;
						}
						else
						{
							display = "";
							return null;
						}
	}

	// �������
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
				display = "�Բ�������"+GameConfig.getYuanbaoName()+"���㣬�����<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>��ֵ</anchor><br/>";
				return display;
			}
			else
			{
				roleInfo.getBasicInfo().addWrapContent(10);// ����10����������
				roleInfo.getBasicInfo().addWrapSpare(10);// ����10����������
				
				logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "�������");
				economyService.spendYuanbao(u_pk, useyuanbao);
				
				display = "����ɹ�,���İ�������������10������,���ѡ�"+GameConfig.getYuanbaoName()+"����100!<br/>";
				return display;
			}
		}
		else
			if (num == 60)
			{
				useyuanbao = 300;
				if (useyuanbao > yuanbao)
				{
					display = "�Բ�������"+GameConfig.getYuanbaoName()+"���㣬�����<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>��ֵ</anchor><br/>";
					return display;
				}
				else
				{
					roleInfo.getBasicInfo().addWrapContent(10);// ����10����������
					roleInfo.getBasicInfo().addWrapSpare(10);// ����10����������
					
					logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "�������");
					economyService.spendYuanbao(u_pk, useyuanbao);
					
					display = "����ɹ�,���İ�������������10������,���ѡ�"+GameConfig.getYuanbaoName()+"����300!<br/>";
					return display;
				}
			}
			else
				if (num == 70)
				{
					useyuanbao = 500;
					if (useyuanbao > yuanbao)
					{
						display = "�Բ�������"+GameConfig.getYuanbaoName()+"���㣬�����<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>��ֵ</anchor><br/>";
						return display;
					}
					else
					{
						roleInfo.getBasicInfo().addWrapContent(10);// ����10����������
						roleInfo.getBasicInfo().addWrapSpare(10);// ����10����������
						
						logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "�������");
						economyService.spendYuanbao(u_pk, useyuanbao);
						
						display = "����ɹ�,���İ�������������10������,���ѡ�"+GameConfig.getYuanbaoName()+"����500!<br/>";
						return display;
					}
				}
		if (num == 80)
		{
			useyuanbao = 700;
			if (useyuanbao > yuanbao)
			{
				display = "�Բ�������"+GameConfig.getYuanbaoName()+"���㣬�����<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>��ֵ</anchor><br/>";
				return display;
			}
			else
			{
				roleInfo.getBasicInfo().addWrapContent(10);// ����10����������
				roleInfo.getBasicInfo().addWrapSpare(10);// ����10����������
				
				logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "�������");
				economyService.spendYuanbao(u_pk, useyuanbao);
				
				display = "����ɹ�,���İ�������������10������,���ѡ�"+GameConfig.getYuanbaoName()+"����700!<br/>";
				return display;
			}
		}
		else
			if (num == 90)
			{
				useyuanbao = 1000;
				if (useyuanbao > yuanbao)
				{
					display = "�Բ�������"+GameConfig.getYuanbaoName()+"���㣬�����<anchor><go method=\"get\" href=\""+GameConfig.getContextPath()+"/sky/bill.do?cmd=n0"+"\" ></go>��ֵ</anchor><br/>";
					return display;
				}
				else
				{
					roleInfo.getBasicInfo().addWrapContent(10);// ����10����������
					roleInfo.getBasicInfo().addWrapSpare(10);// ����10����������
					
					logService.recordYBLog(roleInfo.getBasicInfo().getPPk(), roleInfo.getBasicInfo().getName(), economyService.getYuanbao(roleInfo.getBasicInfo().getUPk())+"", useyuanbao+"", "�������");

					economyService.spendYuanbao(u_pk, useyuanbao);
					display = "����ɹ�,���İ�������������10������,���ѡ�"+GameConfig.getYuanbaoName()+"����1000!<br/>";
					return display;
				}
			}
			else
			{
				display = "�����������������¹���<br/>";
			}
		return display;
	}
	/**
	 * Ϊ�˳���������ԶԵ���ר��дһ������
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
			display="����ɹ������İ���������10�����ӣ�";
		}
		return display;
}
}