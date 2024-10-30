package com.ls.web.service.player;

import org.apache.log4j.Logger;

import com.ls.ben.dao.info.partinfo.UGrowDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.system.SystemConfig;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.log.LogService;
import com.ls.web.service.rank.RankService;
import com.pm.service.systemInfo.SystemInfoService;

/**
 * @author ls ����:��ҳɳ�������߼� Mar 5, 2009
 */
public class GrowService
{

	Logger logger = Logger.getLogger("log.service");

	// ���鱶��
	public final static int EXP_MULTIPLE = 1;
	// npc���侭�鱶��
	public final static int EXP_NPCDROP = 1;

	/**
	 * �õ��ɳ���Ϣ
	 */
	private UGrowVO getGrowInfo(RoleEntity roleEntity)
	{
		if (roleEntity == null)
		{
			logger.info("����Ϊ��");
			return null;
		}
		int grade = roleEntity.getBasicInfo().getGrade();
		int race = roleEntity.getBasicInfo().getPRace();
		UGrowDao growDao = new UGrowDao();
		UGrowVO grow = growDao.getByGradeAndRace(grade, race);
		return grow;
	}

	/**
	 * ��ɫ��ʧ���鴦��ÿ�μ�(next_exp-benji_exp)/20�㾭�飬�����ȥ֮��С�ڱ��������򲻼�����
	 * 
	 * 04.09 ��ҵ��侭���Ϊԭ����һ��,��2.5%
	 * 
	 * @param player
	 * @return
	 */
	public long dropExperience(PartInfoVO player)
	{
		if (player.getPlayer_state_by_new() == 1)
		{
			return 0;
		}

		// pkΪ30�����²�������
		if (player.getPGrade() <= 29)
			return 0;

		// ʵ�ʵ���ľ���
		long dropedExp = 0;
		if (player == null)
		{
			return 0;
		}
		long current_exp = Long.parseLong(player.getPExperience());
		long benji_exp = Long.parseLong(player.getPBjExperience());
		long next_exp = Long.parseLong(player.getPXiaExperience());
		// ��������䱾�������5%
		long shouldDropExp = benji_exp*5/100;
		/* ����ǰ������ڱ��������ʱ�򲻵� */
		if ((current_exp - shouldDropExp) < benji_exp)
		{
			dropedExp = 0;
			current_exp = benji_exp;
		}
		else
		{
			/*  */
			current_exp = current_exp - shouldDropExp;
			dropedExp = shouldDropExp;
		}

		PropertyService propertyService = new PropertyService();
		propertyService.updateExpProperty(player.getPPk(), current_exp);

		logger.debug("��ɫ���侭��:" + dropedExp);
		return dropedExp;
	}

	/**
	 * �������䵱ǰ�ȼ������10%
	 * 
	 * @param player
	 * @return
	 */
	public long dropPlayerExperience(PartInfoVO player)
	{
		// ���Ϊ10�����²�������
		if (player.getPGrade() <= 10)
			return 0;
		long dropedExp = 0;
		if (player == null)
		{
			return 0;
		}
		long current_exp = Long.parseLong(player.getPExperience());
		long benji_exp = Long.parseLong(player.getPBjExperience());
		// ��������䱾�������5%
		long shouldDropExp = benji_exp*5/100;
		/* ����ǰ������ڱ��������ʱ�򲻵� */
		if ((current_exp - shouldDropExp) < benji_exp)
		{
			dropedExp = current_exp-benji_exp;
			current_exp = benji_exp;
		}
		else
		{
			/*  */
			current_exp = current_exp - shouldDropExp;
			dropedExp = shouldDropExp;
		}

		PropertyService propertyService = new PropertyService();
		propertyService.updateExpProperty(player.getPPk(), current_exp);

		logger.debug("��ɫ���侭��:" + dropedExp);
		return dropedExp;
	}

	/**
	 * ����ɫ���Ӿ���(��ɫ�ĳɳ�),�������ɫ�������سɳ�����
	 */
	public String playerGrow(RoleEntity roleEntity, PartInfoVO player,
			long experience)
	{

		String grow_display = null;
		PartInfoVO character = player;
		long current_experience = Long.parseLong(character.getPExperience()
				.trim());// ��ǰ����ֵ
		long xia_experience = Long.parseLong(character.getPXiaExperience()
				.trim());// ��һ�����辭��ֵ

		long last_experience = current_experience + experience;// �������þ����ľ���ֵ

		if (player.getPGrade() == GameConfig.getGradeUpperLimit())// �������߼�������
		{
			if (current_experience == xia_experience)
			{
				// ����߼����еĲ���
				grow_display = maxGradeDoThing(roleEntity, experience, 1);
				return grow_display;
			}
			else
			{
				if (current_experience > xia_experience)
				{
					roleEntity.getBasicInfo().updateCurExp(xia_experience + "");
					grow_display = maxGradeDoThing(roleEntity, experience, 1);
					return grow_display;
				}
				else
				{
					if (last_experience > xia_experience)
					{
						last_experience = xia_experience;
						grow_display = "�������:����+" + experience + "��";
					}
				}
			}
		}
		else
		{
			if (character == null)
			{
				logger.info("��ɫ���Ӿ���ʱ����:��ɫ��Ϊ�ա�");
				return null;
			}

			if (last_experience > xia_experience)// ��ǰ�����ѵ���������Ҫ��
			{

				UGrowDao growDao = new UGrowDao();
				boolean isAutoGrow = growDao.isAutogrow(character.getPGrade(),
						character.getPRace());

				if (!isAutoGrow)// ���ڵ���9��ʱ����������Ȼ����
				{
					// ��ǰ�����ѵ���������Ҫ��,��������Ȼ��������Ҫתְ��������
					last_experience = xia_experience;// ��������ʱ���鲻���ӣ���ǰ��������¼�����
					grow_display = "���������Ѵﵽ,���תְ����󷽿�����";
				}
				else
				// ������Ȼ����
				{
					grow_display = upgrade(character, roleEntity);// �������
				}
			}
			else
			{
				grow_display = "�������:����+" + experience + "��";
			}
		}

		logger.info("����ɫ���Ӿ���:" + experience + "��");

		// ���
		LogService logService = new LogService();
		logService.recordExpLog(roleEntity.getBasicInfo().getPPk(), roleEntity
				.getBasicInfo().getName(), roleEntity.getBasicInfo()
				.getCurExp(), experience + "", "��ֵõ�");

		// ͳ����Ҫ
		new RankService().updateAdd(roleEntity.getBasicInfo().getPPk(),
				"p_exp", experience);
		// ��������Ӿ���
		updateExperience(character, last_experience);

		character.setPExperience(last_experience + "");

		return grow_display;
	}

	/**
	 * 
	 * @param roleEntity
	 * @param experience
	 * @param type
	 *            Ϊ1�����з���,Ϊ0������nullֵ
	 * @return
	 */
	private String maxGradeDoThing(RoleEntity roleEntity, long experience,
			int type)
	{
		String maxGradeDisplay = null;
		if (roleEntity.getBasicInfo().getGrade() == GameConfig
				.getGradeUpperLimit()
				&& roleEntity.getBasicInfo().getCurExp().equals(
						roleEntity.getBasicInfo().getNextGradeExp()))
		{
			int translateExp = Math.round((float) ((float) experience / 1000));
			roleEntity.getBasicInfo().addCopper(translateExp);

			maxGradeDisplay = "�������Ѿ�������߼�,�������þ����ǧ��֮һת��Ϊ��Ǯ!<br/>���"
					+ translateExp+"��ʯ";
		}

		if (type == 1)
		{
			return maxGradeDisplay;
		}
		else
		{
			return null;
		}
	}

	/**
	 * ��������Ӿ���
	 * 
	 * @param character
	 * @param last_experience
	 */
	private void updateExperience(PartInfoVO character, long last_experience)
	{
		RoleEntity roleInfo = RoleService.getRoleInfoById(character.getPPk()
				+ "");
		roleInfo.getBasicInfo().updateCurExp(last_experience + "");
	}

	/**
	 * �������ɫ���Ӿ���(��ɫ�ĳɳ�),�������ɫ�������سɳ�����
	 */
	public String playerGrowTask(RoleEntity roleEntity, PartInfoVO player,
			int experience)
	{
		String grow_display = null;
		PartInfoVO character = player;

		if (character == null)
		{
			logger.info("��ɫ���Ӿ���ʱ����:��ɫ��Ϊ�ա�");
			return null;
		}

		long current_experience = Long.parseLong(character.getPExperience());// ��ǰ����ֵ
		long xia_experience = Long.parseLong(character.getPXiaExperience());// ��һ�����辭��ֵ

		long last_experience = current_experience + experience;// �������þ����ľ���ֵ
		if (player.getPGrade() == GameConfig.getGradeUpperLimit())// �������߼�������
		{

			if (current_experience == xia_experience)
			{
				// ����߼����еĲ���
				grow_display = maxGradeDoThing(roleEntity, experience, 1);
				return grow_display;
			}
			else
			{
				if (current_experience > xia_experience)
				{
					roleEntity.getBasicInfo().updateCurExp(xia_experience + "");
					grow_display = maxGradeDoThing(roleEntity, experience, 1);
					return grow_display;
				}
				else
				{
					if (last_experience > xia_experience)
					{
						last_experience = xia_experience;
					}
				}
			}

		}
		else
		{
			if (last_experience > xia_experience)// ��ǰ�����ѵ���������Ҫ��
			{

				UGrowDao growDao = new UGrowDao();
				boolean isAutoGrow = growDao.isAutogrow(character.getPGrade(),
						character.getPRace());

				if (!isAutoGrow)// ��������Ȼ����
				{
					// ��ǰ�����ѵ���������Ҫ��,��������Ȼ��������Ҫתְ��������
					last_experience = xia_experience;// ��������ʱ���鲻���ӣ���ǰ��������¼�����
					grow_display = "���������Ѵﵽ,���רְ����󷽿�����";
				}
				else
				// ������Ȼ����
				{
					grow_display = upgrade(character, roleEntity);// �������
				}
			}
			else
			{
				logger.info("����ɫ���Ӿ���" + experience + "��");
			}
		}

		// ���
		LogService logService = new LogService();
		logService.recordExpLog(roleEntity.getBasicInfo().getPPk(), roleEntity
				.getBasicInfo().getName(), roleEntity.getBasicInfo()
				.getCurExp(), experience + "", "����õ�");

		// ͳ����Ҫ
		new RankService().updateAdd(roleEntity.getBasicInfo().getPPk(),
				"p_exp", experience);
		character.setPExperience(last_experience + "");

		roleEntity.getBasicInfo().updateCurExp(last_experience + "");

		return grow_display;
	}

	/**
	 * ����ǰ������䵽�����Ǯ����
	 */
	/*
	 * public void dropExpToExpTable(int p_pk, long exp) { RoleEntity roleEntity =
	 * RoleService.getRoleInfoById(p_pk + ""); if(roleEntity != null){
	 * roleEntity.getStateInfo().setDeadDropExp(exp); } }
	 */

	/**
	 * ������������ݳɳ�������u_part_info��
	 * 
	 * @param player
	 * @return ��������
	 */
	public String upgrade(PartInfoVO player, RoleEntity roleEntity)
	{
		String upgrade_display = "";
		if (player == null)
		{
			logger.info("����Ϊ��");
			return upgrade_display;
		}
		if(player.getPGrade()==GameConfig.getGradeUpperHighLimit())
		{
			return upgrade_display;
		}
		SystemInfoService SystemInfoService = new SystemInfoService();

		// ��ҵȼ���һ
		player.setPGrade(player.getPGrade() + 1);
		// ��������
		roleEntity.getBasicInfo().addGrade();

		// ��������ϵͳ��Ϣ 
		SystemInfoService.sendSystemInfoByPGrade(player.getPPk(), player.getPGrade());
		// ������������
		SystemInfoService.setNewPlayerGuideInfoMSG(roleEntity, 10 + "", player
				.getPGrade()
				+ "");
		// ���˵�����
		SystemInfoService.setNewPlayerGuideInfoMSG(roleEntity, 11 + "", player
				.getPGrade()
				+ "");

		// �õ��ɳ���Ϣ
		UGrowVO grow = getGrowInfo(roleEntity);
		upgrade_display = "��ϲ��,������" + roleEntity.getBasicInfo().getGrade()
		+ "��!��Ѫ����" + grow.getGHP() + ",��������" + grow.getGMP() + ",��������"
		+ grow.getGGj() * SystemConfig.attackParameter + ",��������"
		+ grow.getGFy() * SystemConfig.attackParameter + "!";
		return upgrade_display;
	}
}
