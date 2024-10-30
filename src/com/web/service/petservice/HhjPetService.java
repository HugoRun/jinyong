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
 * @author ��ƾ� 11:13:44 AM
 */
public class HhjPetService
{
	Logger logger = Logger.getLogger("log.service");
	DecimalFormat df = new DecimalFormat("0.00");
	DecimalFormat dfs = new DecimalFormat("0");

	/**
	 * ����:���ﲶ׽ ���ݳ�ʼ��
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
				/** ************�������*************** */
				int cur_mp = role_info.getBasicInfo().getMp();// ��ɫ��ǰ��MP
				int pp = npc.getLevel() + 10;
				// //System.out.print("rr="+rr+" ,pp="+pp);
				if (cur_mp < pp)
				{
					return false;
				}
				int p_mp = cur_mp - pp;

				role_info.getBasicInfo().updateMp(p_mp);// ����MP

				logger.info("��ҷ�������:" + p_mp);
				/** ***************���＼�ܸ���****************** */
				/** ����1 ��ѧϰ�ļ���id */
				String petSkillOne = "0";
				/** ����2 ��ѧϰ�ļ���id */
				String petSkillTwo = "0";
				/** ����3 ��ѧϰ�ļ���id */
				String petSkillThree = "0";
				/** ����4 ��ѧϰ�ļ���id */
				String petSkillFour = "0";
				/** ����5 ��ѧϰ�ļ���id */
				String petSkillFive = "0";

				// 11.26���＼�ܸ��Ÿ���, ԭ��Ϊ���ŵļ����ϸ����Ϊpet��������Ķ�Ӧ�ļ���,
				// �����޸�Ϊ���＼��������ųɹ�, ���õ��ļ����Ǵ�pet�����ܾ��е��κ�һ������.

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
						logger.info("��1�ּ���30%�����Ѿ���������");
						list.remove(i);
					}
					if (MathUtil.isAppearByPercentage(3, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
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
						logger.info("��2�ּ���3%�����Ѿ���������");
					}
					if (MathUtil.PercentageRandomByParamdouble(0.3, 100))
					{
						if (skill_num < PetVO.getSkillControl())
						{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
							if (list.size() != 0)
							{
								do
								{
									i = random.nextInt(list.size());
									petSkillThree = list.get(i) + "";
									logger.info("��3�ּ���0.3%�����Ѿ���������");
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
						{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
							if (list.size() != 0)
							{
								do
								{
									i = random.nextInt(list.size());
									petSkillFour = list.get(i) + "";
									logger.info("��4�ּ���0.03%�����Ѿ���������");
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
						{ // �����еļ�����С�ڳ�����Ӧ�еļ��������,���ܼ���.
							if (list.size() != 0)
							{
								do
								{
									i = random.nextInt(list.size());
									petSkillFive = list.get(i) + "";
									list.remove(i);
									logger.info("��5�ּ���0.003%�����Ѿ���������");
								} while (petSkillFive == petSkillOne
										|| petSkillFive == petSkillTwo
										|| petSkillFive == petSkillThree
										|| petSkillFive == petSkillFour);
							}
						}
					}
				}

				/** ***************������ȡ��������ϢȻ����ɲ�׽��ĳ�����Ϣ****************** */
				/** ����ɳ��� */
				double petDropDa = PetVO.getPetDropDa();
				/** ����ɳ��� */
				double petDropXiao = PetVO.getPetDropXiao();
				double dd = MathUtil.getRandomDoubleXY(petDropXiao, petDropDa);

				/** ����ɳ��ʡ� */
				String petGrow = df.format(dd);
				/** ��ɫid */
				String pPk = p_pk;
				/** ��Ӧpet�����id */
				String petId = PetVO.getPetId() + "";

				/** �������� */
				String petName = PetVO.getPetName();
				/** �����ǳ� */
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
				/** ����ͼƬ */
				String pet_img = PetVO.getPetImg();
				/** �ȼ� ,��������޾ͽ���ȼ���Ϊ�� */
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
				// ͨ������ȼ��ͳ���ID�ͳ������� ȥ�ҳ���ɳ���Ϣ
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
				// ����������:����ĳɳ��� * �ȼ�/3
				double cc = Double.parseDouble(petGrow) * npc.getLevel() / 3;
				// (10+�ȼ�*5)+4*�ɳ���
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
				/** ���� */
				double petExps = Double.parseDouble(petShapeVO
						.getShapeBenExperience())
						* Double.parseDouble(petGrow);
				String petExp = dfs.format(petExps);
				/** �¼�����ﵽ��һ����Ҫ�ľ��� */
				double petXiaExps = Double.parseDouble(petShapeVO
						.getShapeXiaExperience())
						* Double.parseDouble(petGrow);
				String petXiaExp = dfs.format(petXiaExps);
				// 06.29�޸�, ��ʼ���ĳ��﹥������ԭ���Ĺ�ʽ��Ϊ�ɳ�����л�ȡ
				/** ��С���� */
//				int petGjXiao = Integer.parseInt(dfs.format(gongji))
//						- Integer.parseInt(dfs.format(cc));
				int petGjXiao = Integer.parseInt(petShapeVO.getShapeAttackXiao());
				/** ��󹥻� */
//				int petGjDa = Integer.parseInt(dfs.format(gongji))
//						+ Integer.parseInt(dfs.format(cc));
				int petGjDa = Integer.parseInt(petShapeVO.getShapeAttackDa());
				
				/** ���޵Ĺ�����ʼ�� */
				if (PetVO.getPetType() == 2)
				{
					petGjXiao = (int) (15 + 4 * Double.parseDouble(petGrow));
					petGjDa = petGjXiao;
				}

				/** �����۸� */
				String petSale = petShapeVO.getShapeSale() + "";
				/** �������Խ�=1��ľ=2��ˮ=3����=4����=5 */
				String petWx = PetVO.getPetWx();
				/** ��������ֵ */
				String petWxValue = PetVO.getPetWxValue();
				/** ����* */

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
				/** ���� �Ƿ����Ȼ���� */
				String petType = PetVO.getPetType() + "";
				/** �Ƿ�������:1��ʾ��ս��״̬��0��ʾ�� */
				int petIsBring = 0;
				/** ƣ�Ͷ�0-100,��ս״̬������ƣ�Ͷȣ�һ��Сʱ��10�� */
				String petFatigue = PetVO.getPetFatigue() + "";
				/** �������� */
				String petLonge = operation.getResult(u) + "";
				// //System.out.println("**-----------**** " + petLonge);
				/** ������������ʹ�ô��� */
				String longeNumber = PetVO.getLongeNumber() + "";
				/** ���������Ѿ�ʹ�ô��� */
				int longeNumberOk = 0;
				/** �������������ѧϰ���ٸ����� */
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
				// //System.out.println("����ɫ�ָ�������");
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
	 * ��ó��ﱻ��׽��ĵȼ�,����ȼ��ܱ�2������ôΪ��һ��,����ȼ�Ϊ����,��ôΪ (�ȼ�+1)/2
	 * 
	 * @param level
	 *            ���ﱻ��׽ʱ������ȼ�
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
	 * ��������
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
				pet_display = "���ĳ����Ѵﵽ����!";
				return pet_display;
			}
			else
				if (petInfoVO.getPetGrade() >= (pGrade + 10))
				{
					pet_display = "���ĳ���ȼ��Ѹ߳����ĵȼ�10�������ٻ�ȡ����!";
					return pet_display;
				}
				else
				{
					// int petExp = petInfoVO.getPetExp();// ��������
					int petBenExp = petInfoVO.getPetBenExp();// ��ǰ����
					int petXiaExp = petInfoVO.getPetXiaExp();// �¼�����

					double DropExps = DropExp * 1.0; // �����þ���,�ɵ���ϵ��.
					// logger.info("NPC���侭�������ɳ����:
					// "+Integer.parseInt(dfs.format(DropExps)));
					int nonce = Integer.parseInt(dfs.format(DropExps))
							+ petBenExp;
					// logger.info("���侭���뱾���������:
					// "+Integer.parseInt(dfs.format(DropExps)));
					if (nonce > petXiaExp)// ��ǰ�����ѵ���������Ҫ��
					{
						uppet(petInfoVO, dfs.format(DropExps));// �������
						PetSkillLevelUpService pl = new PetSkillLevelUpService();
						String pet_skill_level_up = pl
								.petSkillLevelUp(petInfoVO.getPetPk());
						pet_display = "���ĳ���ȼ�������" + petInfoVO.getPetGrade()
								+ "��" + "  " + pet_skill_level_up;

						// ���������ս������Ϣ
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
						pet_display = "���ĳ�������:����+" + dfs.format(DropExps)
								+ "��";
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
	 * ��������
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
				pet_display = "���ĳ����Ѵﵽ����!";
				return pet_display;
			}
			/*
			 * else if (petInfoVO.getPetGrade() > (pGrade + 10)) { pet_display =
			 * "���ĳ���ȼ��Ѹ߳����ĵȼ�10�������ٳ�ս"; //return pet_display; } else {
			 */
			int petExp = petInfoVO.getPetExp();// ��������
			int petBenExp = petInfoVO.getPetBenExp();// ��ǰ����
			int petXiaExp = petInfoVO.getPetXiaExp();// �¼�����

			double DropExps = DropExp * 1.0; // �����þ���,�ɵ���ϵ��.
			// logger.info("NPC���侭�������ɳ����:
			// "+Integer.parseInt(dfs.format(DropExps)));
			int nonce = 0;
			nonce = Integer.parseInt(dfs.format(DropExps)) + petBenExp;
			// logger.info("���侭���뱾���������:
			// "+Integer.parseInt(dfs.format(DropExps)));
			if (nonce > petXiaExp)// ��ǰ�����ѵ���������Ҫ��
			{

				int s = nonce / petXiaExp;

				for (int i = 0; i < s; i++)
				{
					uppet(petInfoVO, dfs.format(DropExps));// �������

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
					pet_display = "���ĳ���ȼ�������" + petInfoVO.getPetGrade() + "��"
							+ "  " + pet_skill_level_up;
				}

				RoleCache roleCache = new RoleCache();

				RoleEntity roleEntity = roleCache.getByPpk(petInfoVO.getPPk()
						+ "");
				RolePetInfo userPet = roleEntity.getRolePetInfo();
				userPet.initPet(petInfoVO.getPetPk(), petInfoVO.getPPk());

				// ����������ҵȼ���೬��10��,�Ǿͽ���ж��
				if (petInfoVO.getPetGrade() > (pGrade + 10))
				{
					pet_display += "���ĳ���ȼ��Ѹ߳����ĵȼ�10�������ٳ�ս";
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
				pet_display = "���ĳ�������:����+" + dfs.format(DropExps) + "��";
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
	 * �������������ݳɳ���
	 * 
	 * @param character
	 * @return
	 */
	public void uppet(PetInfoVO petInfoVO, String DropExps)
	{
		if (petInfoVO == null)
		{
			logger.info("����Ϊ��");
		}
		petInfoVO.setPetGrade(petInfoVO.getPetGrade() + 1);
		// �ҳ�����ɳ� ͨ������ID �ͳ���ȼ�
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

		logger.info("������ĳ���ȼ���=" + petInfoVO.getPetGrade() + 1);
	}

	/**
	 * �ݼ���������
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
				String hint = "����������С,�뾡������������������ѽ�����״̬��Ϊδ��ս״̬";
				systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
				logger.info("---------------��������С��10��,������״̬��Ϊδ��ս״̬");
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
				 * "����������С,�뾡������������������ѽ�����״̬��Ϊδ��ս״̬";
				 * systemInfoService.insertSystemInfoBySystem(pPk,
				 * StringUtil.gbToISO(hint));
				 * logger.info("---------------��������С��10��,������״̬��Ϊδ��ս״̬");
				 */
			}
			else
			{
				roleEntity.getRolePetInfo().updatePetFatigue(pPk);
				logger.debug("������������1��");
			}
		}
	}

	/**
	 * �����Ƿ�Я������
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
	 * һ�ι����ı���ﱾ��״̬��������
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
		
		rolePetInfo.updatePetLong(pPk);//����һ�γ�������
		
		int pet_longe = rolePetInfo.getPetLong();
		
		if( pet_longe==-1 )
		{
			return;
		}
		
		MailInfoService mailInfoService = new MailInfoService(); 
		
		if (pet_longe == 50)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "���ĳ��������Ѿ�С��50����ʹ�ö�ի������������";
			
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
			mailInfoService.sendMail(pPk,-1,2, "ϵͳ�ʼ�", hint);
			
		}else  if (pet_longe == 10)
		{
			SystemInfoService systemInfoService = new SystemInfoService();
			String hint = "���ĳ��������Ѿ�С��10����ʹ�ö�ի������������";
			
			systemInfoService.insertSystemInfoBySystem(pPk, StringUtil.gbToISO(hint));
			mailInfoService.sendMail(pPk,-1,2, "ϵͳ�ʼ�", hint);
		}else if(pet_longe == 0){ 
			rolePetInfo.unBringpetAll();//�����ϵĳ�������δ��ս״̬
			String hint = "���ĳ�������Ϊ0���Ѿ�������";
			mailInfoService.sendMail(pPk,-1,2, "ϵͳ�ʼ�", hint);
			logger.debug("���������Ѿ��ﵽ�� �����ڼ��� ���Ұɳ����Ϊ����ս״̬");
		}
	}
}
