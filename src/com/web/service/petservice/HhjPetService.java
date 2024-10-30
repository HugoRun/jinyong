package com.web.service.petservice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

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
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.MathUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.player.RoleService;
import com.lw.service.skill.PetSkillLevelUpService;
import com.pm.service.mail.MailInfoService;
import com.pm.service.systemInfo.SystemInfoService;
import com.pub.operation.Operation;
import com.web.service.popupmsg.PopUpMsgService;

/**
 * 
 * @author 侯浩军 11:13:44 AM
 */
public class HhjPetService
{
	Logger logger = Logger.getLogger("log.service");
	DecimalFormat df = new DecimalFormat("0.00");
	DecimalFormat dfs = new DecimalFormat("0");

	/**
	 * 功能:宠物捕捉 数据初始化
	 */
	public boolean PetSinewService(PetNameBean petNameBean, NpcAttackVO npc,
			String p_pk)
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
				/** ************玩家消耗*************** */
				int cur_mp = role_info.getBasicInfo().getMp();// 角色当前打MP
				int pp = npc.getLevel() + 10;
				// //System.out.print("rr="+rr+" ,pp="+pp);
				if (cur_mp < pp)
				{
					return false;
				}
				int p_mp = cur_mp - pp;

				role_info.getBasicInfo().updateMp(p_mp);// 更新MP

				logger.info("玩家发力消耗:" + p_mp);
				/** ***************宠物技能附着****************** */
				/** 技能1 可学习的技能id */
				String petSkillOne = "0";
				/** 技能2 可学习的技能id */
				String petSkillTwo = "0";
				/** 技能3 可学习的技能id */
				String petSkillThree = "0";
				/** 技能4 可学习的技能id */
				String petSkillFour = "0";
				/** 技能5 可学习的技能id */
				String petSkillFive = "0";

				// 11.26宠物技能附着改正, 原来为附着的技能严格控制为pet表中所填的对应的技能,
				// 现在修改为宠物技能如果附着成功, 所得到的技能是此pet所可能具有的任何一个技能.

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
						list.remove(i);
					}
					if (MathUtil.isAppearByPercentage(3, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							if (list.size() != 0)
							{
								do
								{

									i = random.nextInt(list.size());
									petSkillTwo = list.get(i) + "";
									skill_num++;
									list.remove(i);

								} while (petSkillTwo == petSkillOne);
							}
						}
						logger.info("第2种技能3%可能已经产生附着");
					}
					if (MathUtil.PercentageRandomByParamdouble(0.3, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							if (list.size() != 0)
							{
								do
								{
									i = random.nextInt(list.size());
									petSkillThree = list.get(i) + "";
									logger.info("第3种技能0.3%可能已经产生附着");
									skill_num++;
									list.remove(i);
								} while (petSkillThree == petSkillOne
										|| petSkillThree == petSkillTwo);
							}
						}

					}
					if (MathUtil.PercentageRandomByParamdouble(0.03, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							if (list.size() != 0)
							{
								do
								{
									i = random.nextInt(list.size());
									petSkillFour = list.get(i) + "";
									logger.info("第4种技能0.03%可能已经产生附着");
									skill_num++;
									list.remove(i);
								} while (petSkillFour == petSkillOne
										|| petSkillFour == petSkillTwo
										|| petSkillFour == petSkillThree);
							}
						}
					}
					if (MathUtil.PercentageRandomByParamdouble(0.003, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // 在已有的技能数小于宠物所应有的技能情况下,才能继续.
							if (list.size() != 0)
							{
								do
								{
									i = random.nextInt(list.size());
									petSkillFive = list.get(i) + "";
									list.remove(i);
									logger.info("第5种技能0.003%可能已经产生附着");
								} while (petSkillFive == petSkillOne
										|| petSkillFive == petSkillTwo
										|| petSkillFive == petSkillThree
										|| petSkillFive == petSkillFour);
							}
						}
					}
				}

				/** ***************以下是取出宠物信息然后换算成捕捉后的宠物信息****************** */
				/** 宠物成长率 */
				double petDropDa = PetVO.getPetDropDa();
				/** 宠物成长率 */
				double petDropXiao = PetVO.getPetDropXiao();
				double dd = MathUtil.getRandomDoubleXY(petDropXiao, petDropDa);

				/** 宠物成长率” */
				String petGrow = df.format(dd);
				/** 角色id */
				String pPk = p_pk;
				/** 对应pet表里的id */
				String petId = PetVO.getPetId() + "";

				/** 宠物名称 */
				String petName = PetVO.getPetName();
				/** 宠物昵称 */
				String petNickname = PetVO.getPetName();
				String xing = "*";
				if (!petSkillOne.equals("0") || !petSkillTwo.equals("0")
						|| !petSkillThree.equals("0")
						|| !petSkillFour.equals("0")
						|| !petSkillFive.equals("0"))
				{
					petNickname = petNickname + xing;
					petName = petName + xing;
				}
				petNameBean.setPetName(petNickname);
				/** 宠物图片 */
				String pet_img = PetVO.getPetImg();
				/** 等级 ,如果是异兽就将其等级置为零 */
				String petGrade = null;
				if (PetVO.getPetType() == 2 || PetVO.getPetType() == 3)
				{
					petGrade = "1";
				}
				else
				{
					petGrade = getInitPetGrade(npc.getLevel()) + "";
					// petGrade = npc.getLevel() + "";
				}
				// 通过宠物等级和宠物ID和宠物类型 去找宠物成长信息
				int type = petDAO.npcType(npc.getNpcID());
				if(npc.getNpcType() == 8){
					type = 3;
				}
				
				PetShapeVO petShapeVO = petDAO.getPetShapeView(type, Integer.valueOf(petGrade));
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
							* (3 * npc.getLevel() - npc.getLevel()) / 2;
				}
				/** 经验 */
				double petExps = Double.parseDouble(petShapeVO
						.getShapeBenExperience())
						* Double.parseDouble(petGrow);
				String petExp = dfs.format(petExps);
				/** 下级经验达到下一级需要的经验 */
				double petXiaExps = Double.parseDouble(petShapeVO
						.getShapeXiaExperience())
						* Double.parseDouble(petGrow);
				String petXiaExp = dfs.format(petXiaExps);
				// 06.29修改, 初始化的宠物攻击力由原来的公式改为由宠物表中获取
				/** 最小攻击 */
//				int petGjXiao = Integer.parseInt(dfs.format(gongji))
//						- Integer.parseInt(dfs.format(cc));
				int petGjXiao = Integer.parseInt(petShapeVO.getShapeAttackXiao());
				/** 最大攻击 */
//				int petGjDa = Integer.parseInt(dfs.format(gongji))
//						+ Integer.parseInt(dfs.format(cc));
				int petGjDa = Integer.parseInt(petShapeVO.getShapeAttackDa());
				
				/** 异兽的攻击初始化 */
				if (PetVO.getPetType() == 2)
				{
					petGjXiao = (int) (15 + 4 * Double.parseDouble(petGrow));
					petGjDa = petGjXiao;
				}

				/** 卖出价格 */
				String petSale = petShapeVO.getShapeSale() + "";
				/** 五行属性金=1，木=2，水=3，火=4，土=5 */
				String petWx = PetVO.getPetWx();
				/** 五行属性值 */
				String petWxValue = PetVO.getPetWxValue();
				/** 寿命* */

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
				// //System.out.println("****************** " + petLife);
				/** 升级 是否可自然升级 */
				String petType = PetVO.getPetType() + "";
				/** 是否在身上:1表示在战斗状态，0表示否 */
				int petIsBring = 0;
				/** 疲劳度0-100,出战状态下增加疲劳度，一个小时加10点 */
				String petFatigue = PetVO.getPetFatigue() + "";
				/** 宠物寿命 */
				String petLonge = operation.getResult(u) + "";
				// //System.out.println("**-----------**** " + petLonge);
				/** 增加寿命道具使用次数 */
				String longeNumber = PetVO.getLongeNumber() + "";
				/** 寿命道具已经使用次数 */
				int longeNumberOk = 0;
				/** 这个宠物最多可以学习多少个技能 */
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
	}

	/**
	 * 获得宠物被捕捉后的等级,如果等级能被2除，那么为其一半,如果等级为奇数,那么为 (等级+1)/2
	 * 
	 * @param level
	 *            宠物被捕捉时的自身等级
	 * @return
	 */
	private int getInitPetGrade(int level)
	{
		if (level % 2 == 0)
		{
			return level / 2;
		}
		else
		{
			return (level + 1) / 2;
		}
	}

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
				if (petInfoVO.getPetGrade() >= (pGrade + 10))
				{
					pet_display = "您的宠物等级已高出您的等级10级不能再获取经验!";
					return pet_display;
				}
				else
				{
					// int petExp = petInfoVO.getPetExp();// 本级经验
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

						// 重新载入出战宠物信息
						RoleCache roleCache = new RoleCache();
						RoleEntity roleEntity = roleCache.getByPpk(pPk + "");
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
	 * 宠物升级
	 */
	public String getPetGrandirProp(int pet_pk, int pGrade, int DropExp)
	{
		try
		{
			String pet_display = null;
			PetInfoDAO petInfoDAO = new PetInfoDAO();
			PetInfoVO petInfoVO = petInfoDAO.getPetInfoView(pet_pk + "");
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
			/*
			 * else if (petInfoVO.getPetGrade() > (pGrade + 10)) { pet_display =
			 * "您的宠物等级已高出您的等级10级将不再出战"; //return pet_display; } else {
			 */
			int petExp = petInfoVO.getPetExp();// 本级经验
			int petBenExp = petInfoVO.getPetBenExp();// 当前经验
			int petXiaExp = petInfoVO.getPetXiaExp();// 下级经验

			double DropExps = DropExp * 1.0; // 宠物获得经验,可调整系数.
			// logger.info("NPC掉落经验与宠物成长相乘:
			// "+Integer.parseInt(dfs.format(DropExps)));
			int nonce = 0;
			nonce = Integer.parseInt(dfs.format(DropExps)) + petBenExp;
			// logger.info("掉落经验与本级经验相加:
			// "+Integer.parseInt(dfs.format(DropExps)));
			if (nonce > petXiaExp)// 当前经验已到升级经验要求
			{

				int s = nonce / petXiaExp;

				for (int i = 0; i < s; i++)
				{
					uppet(petInfoVO, dfs.format(DropExps));// 玩家升级

					PetInfoVO vo = petInfoDAO.getPetInfoView(pet_pk + "");
					s = (Integer.parseInt(dfs.format(DropExps)) + vo
							.getPetBenExp())
							/ vo.getPetXiaExp();
					petInfoVO.setPetGrow(vo.getPetGrow());
					petInfoVO.setPetGrade(vo.getPetGrade());
					petInfoVO.setPetPk(vo.getPetPk());
					petInfoVO.setPetType(vo.getPetType());

					PetSkillLevelUpService pl = new PetSkillLevelUpService();
					String pet_skill_level_up = pl.petSkillLevelUp(petInfoVO
							.getPetPk());
					pet_display = "您的宠物等级升到了" + petInfoVO.getPetGrade() + "级"
							+ "  " + pet_skill_level_up;
				}

				RoleCache roleCache = new RoleCache();

				RoleEntity roleEntity = roleCache.getByPpk(petInfoVO.getPPk()
						+ "");
				RolePetInfo userPet = roleEntity.getRolePetInfo();
				userPet.initPet(petInfoVO.getPetPk(), petInfoVO.getPPk());

				// 如果宠物和玩家等级差距超过10级,那就将其卸下
				if (petInfoVO.getPetGrade() > (pGrade + 10))
				{
					pet_display += "您的宠物等级已高出您的等级10级将不再出战";
					userPet.unBringpetAll();
					return pet_display;
				}

				return pet_display;

			}
			else
			{
				petInfoDAO.getPetInfoBjExp(dfs
						.format((petInfoVO.getPetBenExp() + DropExps)),
						petInfoVO.getPetPk() + "");
				pet_display = "您的宠物获得了:经验+" + dfs.format(DropExps) + "点";
				return pet_display;
			}
			/* } */
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
		PetShapeVO petShapeVO = petDAO.getPetShapeView(petInfoVO.getPetType(),
				petInfoVO.getPetGrade());
		PetInfoDAO petInfoDAO = new PetInfoDAO();
		petInfoDAO.getPetInfoUpdate(petInfoVO, petShapeVO, DropExps);

		/*
		 * RoleCache roleCache = new RoleCache(); RoleEntity roleEntity =
		 * roleCache.getByPpk(petInfoVO.getPPk()+""); RolePetInfo userPet =
		 * roleEntity.getRolePetInfo();
		 * 
		 * userPet.initPet(petInfoVO.getPetPk(), petInfoVO.getPPk());
		 */

		logger.info("升级后的宠物等级是=" + petInfoVO.getPetGrade() + 1);
	}

	/**
	 * 递减宠物体力
	 * 
	 * @param character
	 * @return
	 */
	public void petFatigue(RoleEntity roleEntity)
	{
		int pPk = roleEntity.getBasicInfo().getPPk();
		com.ls.web.service.pet.PetService petService = new com.ls.web.service.pet.PetService();

		com.ls.ben.vo.info.pet.PetInfoVO vo = roleEntity.getRolePetInfo()
				.getBringPet();
		if (vo != null)
		{
			if (vo.getPetFatigue() == 20 || vo.getPetFatigue() == 10)
			{
				roleEntity.getRolePetInfo().updatePetFatigue(pPk);
				int petIsBring = 0;
				petService.petIsBring(pPk, vo.getPetPk(), petIsBring);
				SystemInfoService systemInfoService = new SystemInfoService();
				String hint = "宠物体力过小,请尽快给宠物增加体力！已将宠物状态改为未出战状态";
				systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
				logger.info("---------------宠物体力小于10点,将宠物状态改为未出战状态");
				String pet_fatigue = GameConfig.getPropertiesObject("pet_fatigue");
				if(vo.getPetFatigue() == Integer.parseInt(pet_fatigue)){
					new PopUpMsgService().addSysSpecialMsg(pPk,roleEntity.getBasicInfo().getPPk(),0, PopUpMsgType.PET_FATIGUE);
				}
				/*
				 * } if (vo.getPetFatigue() == 10) {
				 * roleEntity.getRolePetInfo().updatePetFatigue(pPk); int
				 * petIsBring = 0; petService.petIsBring(pPk, vo.getPetPk(),
				 * petIsBring); SystemInfoService systemInfoService = new
				 * SystemInfoService(); String hint =
				 * "宠物体力过小,请尽快给宠物增加体力！已将宠物状态改为未出战状态";
				 * systemInfoService.insertSystemInfoBySystem(pPk,
				 * StringUtil.gbToISO(hint));
				 * logger.info("---------------宠物体力小于10点,将宠物状态改为未出战状态");
				 */
			}
			else
			{
				roleEntity.getRolePetInfo().updatePetFatigue(pPk);
				logger.debug("宠物体力减了1点");
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
		// PetInfoDAO dao = new PetInfoDAO();
		// if (dao.pet_isBring(pPk))
		// {
		// s = 1;
		// return s;
		// }
		// else
		// {
		// return s;
		// }
		RoleCache roleCache = new RoleCache();
		RoleEntity roleEntity = roleCache.getByPpk(pPk);

		if (roleEntity.getRolePetInfo().getBringPet() != null)
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
	 */
	public void attackUpdateStat(int pPk)
	{
		RoleService roleService = new RoleService();
		
		RoleEntity roleEntity = roleService.getRoleInfoById(pPk + "");
		RolePetInfo rolePetInfo = roleEntity.getRolePetInfo();
		
	
		if( rolePetInfo == null ){
			return;
		}
		
		rolePetInfo.updatePetLong(pPk);//减少一次宠物寿命
		
		int pet_longe = rolePetInfo.getPetLong();
		
		if( pet_longe==-1 )
		{
			return;
		}
		
		MailInfoService mailInfoService = new MailInfoService(); 
		
		if (pet_longe == 50)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "您的宠物寿命已经小于50，请使用豆斋果增加寿命！";
			
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
			mailInfoService.sendMail(pPk,-1,2, "系统邮件", hint);
			
		}else  if (pet_longe == 10)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "您的宠物寿命已经小于10，请使用豆斋果增加寿命！";
			
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
			mailInfoService.sendMail(pPk,-1,2, "系统邮件", hint);
		}else if(pet_longe == 0){ 
			rolePetInfo.unBringpetAll();//把身上的宠物至于未出战状态
			String hint = "您的宠物寿命为0，已经死亡了";
			mailInfoService.sendMail(pPk,-1,2, "系统邮件", hint);
			logger.debug("宠物寿命已经达到零 不能在减了 并且吧宠物改为不出战状态");
		}
	}
}
