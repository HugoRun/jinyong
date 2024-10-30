package com.web.service.petservice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.dao.pet.PetDAO;
import com.ben.dao.petinfo.PetInfoDAO;
import com.ben.vo.pet.pet.PetShapeVO;
import com.ben.vo.pet.pet.PetVO;
import com.ben.vo.petinfo.PetInfoVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.vo.info.npc.NpcAttackVO;
import com.ls.model.property.RolePetInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.PetNameBean;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.player.RoleService;
import com.lw.service.skill.PetSkillLevelUpService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pub.operation.Operation;

/**
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class PetService
{
	Logger logger = Logger.getLogger("log.service");
	DecimalFormat df = new DecimalFormat("0.00");
	DecimalFormat dfs = new DecimalFormat("0");

	/**
	 * 功能:宠物捕捉 数据初始化
	
	public boolean PetSinewService11(PetNameBean petNameBean,NpcAttackVO npc, String p_pk)
	{
		RoleService roleService = new RoleService();
		RoleEntity role_info = roleService.getRoleInfoById(p_pk);
		try
		{
			PetDAO petDAO = new PetDAO();
			PetVO PetVO = petDAO.getPetView(npc.getNpcID());
			// logger.info("npcId="+npc.getNpcID()+" ,petVO="+PetVO.getPetId()+"
			// ,petvo==null="+(PetVO != null));
			if (PetVO != null)
			{
				/** ************玩家消耗*************** *
				PartInfoDAO partInfoDAO = new PartInfoDAO();
				int cur_MP = role_info.getBasicInfo().getMp();
				int pp = npc.getLevel() + 10;
				// //System.out.print("rr="+rr+" ,pp="+pp);
				if (cur_MP < pp)
				{
					return false;
				}
				int p_mp = cur_MP - pp;
				role_info.getBasicInfo().updateMp(p_mp);//更新MP
				logger.info("玩家发力消耗:" + p_mp);
				/** ***************宠物技能附着****************** *
				/** 技能1 可学习的技能id *
				String petSkillOne = "0";
				/** 技能2 可学习的技能id *
				String petSkillTwo = "0";
				/** 技能3 可学习的技能id *
				String petSkillThree = "0";
				/** 技能4 可学习的技能id *
				String petSkillFour = "0";
				/** 技能5 可学习的技能id *
				String petSkillFive = "0";

				// 11.26宠物技能附着改正, 原来为附着的技能严格控制为pet表中所填的对应的技能,
				// 现在修改为宠物技能如果附着成功, 所得到的技能是此pet所可能具有的任何一个技能.
				/**
				 * if(PetVO.getPetSkillOne()!=0){
				 * if(mathUtil.isAppearByPercentage(30,100)){ petSkillOne =
				 * PetVO.getPetSkillOne()+""; //30%
				 * logger.info("第1种技能30%可能已经产生附着"); }
				 * }if(PetVO.getPetSkillTwo()!=0){
				 * if(mathUtil.isAppearByPercentage(3,100)){ petSkillTwo =
				 * PetVO.getPetSkillTwo()+""; //3%
				 * logger.info("第2种技能3%可能已经产生附着"); }
				 * }if(PetVO.getPetSkillThree()!=0){
				 * if(mathUtil.PercentageRandomByParamdouble(0.3,100)){
				 * petSkillThree = PetVO.getPetSkillThree()+"";//%
				 * logger.info("第3种技能0.3%可能已经产生附着"); }
				 * }if(PetVO.getPetSkillFour()!=0){
				 * if(mathUtil.PercentageRandomByParamdouble(0.03,100)){
				 * petSkillFour = PetVO.getPetSkillFour()+"";//0.03%
				 * logger.info("第4种技能0.03%可能已经产生附着"); }
				 * }if(PetVO.getPetSkillFive()!=0){
				 * if(mathUtil.PercentageRandomByParamdouble(0.003,100)){
				 * petSkillFive = PetVO.getPetSkillFive()+"";//0.003%
				 * logger.info("第5种技能0.003%可能已经产生附着"); } }
				 *

				int i = 0;
				int skill_num = 0;
				List<Integer> list = new ArrayList<Integer>();
				if (PetVO.getPetSkillOne() != 0)
					list.add(PetVO.getPetSkillOne());
				if (PetVO.getPetSkillTwo() != 0)
					list.add(PetVO.getPetSkillTwo());
				if (PetVO.getPetSkillThree() != 0)
					list.add(PetVO.getPetSkillThree());
				if (PetVO.getPetSkillFour() != 0)
					list.add(PetVO.getPetSkillFour());
				if (PetVO.getPetSkillFive() != 0)
					list.add(PetVO.getPetSkillFive());

				Random random = new Random();
				
				if (list.size() != 0)
				{
					
					if (MathUtil.isAppearByPercentage(30, 100))
					{
						i = random.nextInt(list.size());
						petSkillOne = list.get(i) + "";
						skill_num++;
						logger.info("第1种技能30%可能已经产生附着");
					}
					if (MathUtil.isAppearByPercentage(3, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.

							do
							{
								i = random.nextInt(list.size());
								petSkillTwo = list.get(i) + "";
								skill_num++;
							} while (petSkillTwo == petSkillOne);
						}

						logger.info("第2种技能3%可能已经产生附着");
					}
					if (MathUtil.PercentageRandomByParamdouble(0.3, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							do
							{
								i = random.nextInt(list.size());
								petSkillThree = list.get(i) + "";
								logger.info("第3种技能0.3%可能已经产生附着");
								skill_num++;
							} while (petSkillThree == petSkillOne
									|| petSkillThree == petSkillTwo);
						}

					}
					if (MathUtil.PercentageRandomByParamdouble(0.03, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							do
							{
								i = random.nextInt(list.size());
								petSkillFour = list.get(i) + "";
								logger.info("第4种技能0.03%可能已经产生附着");
								skill_num++;
							} while (petSkillFour == petSkillOne
								|| petSkillFour == petSkillTwo
								|| petSkillFour == petSkillThree);
						}
					}
					if (MathUtil.PercentageRandomByParamdouble(0.003, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							do
							{
								i = random.nextInt(list.size());
								petSkillFive = list.get(i) + "";
								logger.info("第5种技能0.003%可能已经产生附着");
							} while (petSkillFive == petSkillOne
									|| petSkillFive == petSkillTwo
									|| petSkillFive == petSkillThree
									|| petSkillFive == petSkillFour);
						}
					}
				}

				/** ***************以下是取出宠物信息然后换算成捕捉后的宠物信息****************** *
				/** 宠物成长率 *
				double petDropDa = PetVO.getPetDropDa();
				/** 宠物成长率 *
				double petDropXiao = PetVO.getPetDropXiao();
				double dd = MathUtil.getRandomDoubleXY(petDropXiao, petDropDa);

				/** 宠物成长率” *
				String petGrow = df.format(dd);
				/** 角色id *
				String pPk = p_pk;
				/** 对应pet表里的id *
				String petId = PetVO.getPetId() + "";
				
				/** 宠物名称 *
				String petName = PetVO.getPetName();
				/** 宠物昵称 *
				String petNickname = PetVO.getPetName();
				String xing = "*";
				if(!petSkillOne.equals("0")||!petSkillTwo.equals("0")||!petSkillThree.equals("0")||!petSkillFour.equals("0")||!petSkillFive.equals("0")){
					petNickname = petNickname+xing;
				} 
				petNameBean.setPetName(petNickname);
				/** 宠物图片 *
				String pet_img = PetVO.getPetImg();
				/** 等级 ,如果是异兽就将其等级置为零 *
				String petGrade = null;
				if (PetVO.getPetType() == 2)
				{
					petGrade = "1";
				}
				else
				{
					petGrade = npc.getLevel() + "";
				}
				// 通过宠物等级和宠物ID和宠物类型 去找宠物成长信息
				PetShapeVO petShapeVO = petDAO.getPetShapeView(
						petDAO.npcType(npc.getNpcID()), Integer
								.valueOf(petGrade));
				if (petShapeVO == null)
				{
					logger.info("petShapeVO == null");
					return false;
				}
				// 攻击力常数:宠物的成长率 * 等级/3
				double cc = Double.parseDouble(petGrow) * npc.getLevel() / 3;
				// (10+等级*5)+4*成长率
				double gongji = 0.0;
				// double gongjix=0.0;
				if (npc.getLevel() < 9)
				{
					gongji = (10 + Integer.valueOf(petGrade) * 5) + 4
							* Double.parseDouble(petGrow);
				}
				else
				{
					gongji = Math.pow(Integer.valueOf(petGrade), 0.5) * 20
							+ Double.parseDouble(petGrow)
							* (5 * npc.getLevel() - npc.getLevel()) / 2;
				}
				/** 经验 *
				double petExps = Double.parseDouble(petShapeVO
						.getShapeBenExperience())
						* Double.parseDouble(petGrow);
				String petExp = dfs.format(petExps);
				/** 下级经验达到下一级需要的经验 *
				// double s =
				// (Math.pow(npc.getLevel(),3)/90+5)*(Math.pow(npc.getLevel(),2)/3+10)*0.36*Double.parseDouble(petGrow);
				double petXiaExps = Double.parseDouble(petShapeVO
						.getShapeXiaExperience())
						* Double.parseDouble(petGrow);
				String petXiaExp = dfs.format(petXiaExps);
				/** 最小攻击 *
				int petGjXiao = Integer.parseInt(dfs.format(gongji))
						- Integer.parseInt(dfs.format(cc));
				/** 最大攻击 *
				int petGjDa = Integer.parseInt(dfs.format(gongji))
						+ Integer.parseInt(dfs.format(cc));

				/**异兽的攻击初始化*
				if(PetVO.getPetType() == 2){
					petGjXiao = (int) (15 + 4 * Double.parseDouble(petGrow));
					petGjDa = petGjXiao;
				}else{
					petGjXiao = Integer.parseInt(dfs.format(gongji))
							- Integer.parseInt(dfs.format(cc));
					petGjDa = Integer.parseInt(dfs.format(gongji))
							+ Integer.parseInt(dfs.format(cc));
				}
				
				/** 卖出价格 *
				String petSale = petShapeVO.getShapeSale() + "";
				/** 五行属性金=1，木=2，水=3，火=4，土=5 *
				String petWx = PetVO.getPetWx();
				/** 五行属性值 *
				String petWxValue = PetVO.getPetWxValue();
				/** 寿命* *

				int m = 2;
				String ll = "";
				int n = random.nextInt(m);
				if (n == 0)
				{
					ll = "*1.1";
				}
				else
					if (n == 1)
					{
						ll = "*0.9";
					}
				int p = 10;
				int o = random.nextInt(p);
				Operation operation = new Operation();
				String u = PetVO.getPetLonge() + ll;
				String petLife = operation.getResult(u) + "";
				////System.out.println("******************  " + petLife);
				/** 升级 是否可自然升级 *
				String petType = PetVO.getPetType() + "";
				/** 是否在身上:1表示在战斗状态，0表示否 *
				int petIsBring = 0;
				/** 疲劳度0-100,出战状态下增加疲劳度，一个小时加10点 *
				String petFatigue = PetVO.getPetFatigue() + "";
				/** 宠物寿命 *
				String petLonge = operation.getResult(u) + "";
				////System.out.println("**-----------****  " + petLonge);
				/** 增加寿命道具使用次数 *
				String longeNumber = PetVO.getLongeNumber() + "";
				/** 寿命道具已经使用次数 *
				int longeNumberOk = 0;
				/** 这个宠物最多可以学习多少个技能 *
				String skillControl = PetVO.getSkillControl() + "";
				PetInfoDAO petInfoDAO = new PetInfoDAO();
				petInfoDAO.getPetInfoAdd(pPk, petId, petName, petNickname,
						petGrade, petExp, petXiaExp, petGjXiao, petGjDa,
						petSale, petGrow, petWx, petWxValue, petSkillOne,
						petSkillTwo, petSkillThree, petSkillFour, petSkillFive,
						petLife, petType, petIsBring, petFatigue, petLonge,
						longeNumber, longeNumberOk, skillControl, PetVO
								.getPetType(), pet_img, PetVO
								.getPetViolenceDorp());
				// //System.out.println("给角色种附带宠物");
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return true;
	} */

	/**
	 * 宠物升级
	 */
	public String getPetGrandir(int pPk, int pGrade, int DropExp)
	{
		try
		{
			String pet_display = null;
			PetInfoDAO petInfoDAO = new PetInfoDAO();
			PetInfoVO petInfoVO = petInfoDAO.getPetInfo(pPk + "");
			if (petInfoVO == null)
			{
				pet_display = null;
				return pet_display;
			}
			if (petInfoVO.getPetGrade() == GameConfig.getGradeUpperLimit())
			{
				pet_display = "您的宠物已达到满级!";
				return pet_display;
			}
			else
				if (petInfoVO.getPetGrade() > (pGrade + 10))
				{
					pet_display = "您的宠物等级已高出您的等级10级不能再获取经验!";
					return pet_display;
				}
				else
				{
					int petExp = petInfoVO.getPetExp();// 本级经验
					int petBenExp = petInfoVO.getPetBenExp();// 当前经验
					int petXiaExp = petInfoVO.getPetXiaExp();// 下级经验

					double DropExps = DropExp * 1.0; // 宠物获得经验,可调整系数.
					// logger.info("NPC掉落经验与宠物成长相乘:
					// "+Integer.parseInt(dfs.format(DropExps)));
					int nonce = Integer.parseInt(dfs.format(DropExps))
							+ petBenExp;
					// logger.info("掉落经验与本级经验相加:
					// "+Integer.parseInt(dfs.format(DropExps)));
					if (nonce > petXiaExp)// 当前经验已到升级经验要求
					{
						uppet(petInfoVO, dfs.format(DropExps));// 玩家升级
						PetSkillLevelUpService pl = new PetSkillLevelUpService();
						String pet_skill_level_up = pl
								.petSkillLevelUp(petInfoVO.getPetPk());
						pet_display = "您的宠物等级升到了" + petInfoVO.getPetGrade()
								+ "级" + "  " + pet_skill_level_up;
						
						RoleCache roleCache = new RoleCache();
						RoleEntity roleEntity = roleCache.getByPpk(pPk+"");		
						RolePetInfo userPet = roleEntity.getRolePetInfo();
						userPet.initPet(petInfoVO.getPetPk(), pPk);
						return pet_display;
					}
					else
					{
						petInfoDAO.getPetInfoBjExp(dfs.format((petInfoVO
								.getPetBenExp() + DropExps)), petInfoVO
								.getPetPk()
								+ "");
						pet_display = "您的宠物获得了:经验+" + dfs.format(DropExps)
								+ "点";
						return pet_display;
					}
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 宠物升级，根据成长表
	 * 
	 * @param character
	 * @return
	 */
	public void uppet(PetInfoVO petInfoVO, String DropExps)
	{
		if (petInfoVO == null)
		{
			logger.info("参数为空");
		}
		petInfoVO.setPetGrade(petInfoVO.getPetGrade() + 1);
		// 找出宠物成长 通过宠物ID 和宠物等级
		PetDAO petDAO = new PetDAO();
		PetShapeVO petShapeVO = petDAO.getPetShapeView(petInfoVO.getPetType(), petInfoVO.getPetGrade());
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		petInfoDAO.getPetInfoUpdate(petInfoVO, petShapeVO, DropExps);
	}

	/**
	 * 递减宠物体力
	 * 
	 * @param character
	 * @return
	 */
	public void petFatigue(int pPk)
	{
		PetDAO petDAO = new PetDAO();
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		PetInfoVO vo = petInfoDAO.getPetInfo(pPk + "");
		if (vo != null)
		{
			/*// 当小于100的时候给发系统消息
			if (vo.getPetFatigue() <= 100 && vo.getPetFatigue() >= 90)
			{
				SystemInfoService systemInfoService = new SystemInfoService();
				String hint = "您的宠物体力已经小于100，请尽快给宠物增加体力！";
				systemInfoService.insertSystemInfoBySystem(pPk, StringUtil
						.gbToISO(hint));
			}*/
			if (vo.getPetFatigue() == 20)
			{
				int petIsBring = 0;
				petInfoDAO.petIsBring(pPk, vo.getPetPk(), petIsBring);
				SystemInfoService systemInfoService = new SystemInfoService();
				String hint = "宠物体力过小,请尽快给宠物增加体力！";
				systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
				logger.info("---------------宠物体力小于10点,将宠物状态改为未出战状态");
			}
			if (vo.getPetFatigue() == 10)
			{
				int petIsBring = 0;
				petInfoDAO.petIsBring(pPk, vo.getPetPk(), petIsBring);
				SystemInfoService systemInfoService = new SystemInfoService();
				String hint = "宠物体力过小,请尽快给宠物增加体力！已将宠物状态改为未出战状态";
				systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
				logger.info("---------------宠物体力小于10点,将宠物状态改为未出战状态");
			}
			else
			{
				petDAO.petFatigue(pPk);
				logger.info("宠物体力减了1点");
			}
		}
	}

	/**
	 * 返回是否携带宠物
	 * 
	 * @param character
	 * @return
	 */
	public int pet(int pPk)
	{
		int s = 0;
		PetInfoDAO dao = new PetInfoDAO();
		if (dao.pet_isBring(pPk))
		{
			s = 1;
			return s;
		}
		else
		{
			return s;
		}
	}

	/**
	 * 一次攻击改变宠物本身状态如寿命等
	 * 
	 * @param pet
	 
	public void attackUpdateStat1(int pPk)
	{
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		int pet_longe = petInfoDAO.pet_longeBring(pPk);
		if (pet_longe == 100)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "您的宠物寿命已经小于100，请尽快给宠物增加寿命！";
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil
					.gbToISO(hint));
		} 
		if (pet_longe == 50)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "您的宠物寿命已经小于50，请尽快给宠物增加寿命！";
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil
					.gbToISO(hint));
		}
		if (pet_longe == 10)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "您的宠物寿命已经小于10，请尽快给宠物增加寿命！";
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil
					.gbToISO(hint));
		}
		if (pet_longe > 0)
		{
			petInfoDAO.pet_life(pPk);
			logger.debug("减少一次宠物寿命");
		}
		else
		{
			int petPk = petInfoDAO.getPetPk(pPk);
			int petIsBring = 0;
			petInfoDAO.petIsBring(pPk, petPk, petIsBring);
			logger.debug("宠物寿命已经达到零 不能在减了 并且吧宠物改为不出战状态");
		}
	}*/

	/**
	 * *功能:遗弃宠物
	 * @param petPk
	 * @param pk
	 */
	public void getPetInfoDelete(String petPk, String pk)
	{
		PetInfoDAO dao = new PetInfoDAO();
		dao.getPetInfoDelte(petPk);
		
	}
}
