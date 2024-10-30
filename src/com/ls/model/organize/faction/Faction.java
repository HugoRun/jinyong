package com.ls.model.organize.faction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ls.ben.dao.faction.FNoticeDao;
import com.ls.ben.dao.faction.FactionDao;
import com.ls.ben.dao.faction.game.FUpgradeMaterialDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.organize.Organize;
import com.ls.model.organize.faction.game.FUpgradeMaterial;
import com.ls.model.user.RoleEntity;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.player.RoleService;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.mail.MailInfoVO;

/**
 * @author ls
 * ����
 */
public class Faction extends Organize
{
	/*********ְλȨ��(�峤�����ϡ�����������)******/
	public final static int ZUZHANG = 3;
	public final static int ZHANGLAO = 2;
	public final static int HUFA = 1;
	public final static int ZUZHONG = 0;

	private FactionDao factionDao = new FactionDao();
	
	/***********���ɻ�����Ϣ*************/
	private int id;
	private String name;
	private int memberNum=1;//��ǰ��Ա����
	private int race;
	private int grade=1;
	private int prestige;//����
	private int citangGrade=1;//���õȼ�:0��ʾ��û����������0��ʾ���õȼ�
	private boolean isDisband=false;//�Ƿ��ڽ�ɢ�����ڣ��������賿3ʱ��������ɢ
	private Date createTime=null;//����ʱ��
	private int mGradeTotal;//��Ա�ȼ��ܺ�
	private Date changeZZHTime=null;//�´�ת���峤��ʱ��
	
	private FStorage fStorage;//���ɲֿ�
	
	//*******�ǳ־û���Ϣ
	private FNotice lastedNotice;//���µ�һ������
	
	/**
	 * ��Ա���������Ӱ��ɳ�Ա�ȼ�����
	 */
	public void memUpgrade()
	{
		factionDao.updateMGradeTotal(this.id, 1);
	}
	
	/**
	 * �Ƿ��ǻ�Ծ����
	 */
	public boolean isActived()
	{
		//�жϴ����Ƿ񳬹�10��
		if( DateUtil.getDifferDays(createTime, new Date())>10 && this.isFull()==false)
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * �õ����õ�ǰ�ȼ���Ϣ
	 * @return
	 */
	public FUpgradeMaterial getCitangInfo()
	{
		FUpgradeMaterialDao  fUpgradeMaterialDao = new FUpgradeMaterialDao();
		return fUpgradeMaterialDao.getOneByType(FUpgradeMaterial.C_UPGRADE, this.citangGrade);
	}
	
	/**
	 * �õ���������
	 * @param upgradeType  ��������
	 * @return
	 */
	public FUpgradeMaterial getUpgradeMaterial(int upgradeType)
	{
		int upgrade_grade = 0;//�����ȼ�
		if( upgradeType==FUpgradeMaterial.F_UPGRADE )
		{
			upgrade_grade = this.grade+1;
		}
		else if( upgradeType==FUpgradeMaterial.C_UPGRADE )
		{
			upgrade_grade = this.citangGrade+1;
		}
		FUpgradeMaterialDao  fUpgradeMaterialDao = new FUpgradeMaterialDao();
		return fUpgradeMaterialDao.getOneByType(upgradeType, upgrade_grade);
	}
	
	/**
	 * ��ʾ
	 */
	public String getDisplay()
	{
		StringBuffer sb = new StringBuffer();
		RoleEntity role=this.getLeader();
		String leaderName="";
		if(role!=null&&this.leaderId!=-1)
		{
			leaderName=role.getName();
		}
		else
		{
			leaderName="����(���Ͽɽӹܸ�����)";
		}
		sb.append(this.getFullName()).append("<br/>");
		sb.append("����ȼ�:").append(this.grade).append("��<br/>");
		sb.append("�峤:").append(leaderName).append("<br/>");
		sb.append("������Ա:").append(this.memberNum).append("��<br/>");
		sb.append("��������:").append(this.prestige).append("��<br/>");
		sb.append("���õȼ�:").append(this.citangGrade).append("��<br/>");
		return sb.toString();
	}
	
	/**
	 * ���¹���
	 */
	public FNotice getLastedNotice()
	{
		if( lastedNotice==null )
		{
			FNoticeDao fNoticeDao = new FNoticeDao();
			lastedNotice = fNoticeDao.getLastedNotice(this.id);
			if( lastedNotice==null )
			{
				lastedNotice = new FNotice();
			}
		}
		return lastedNotice;
	}
	/**
	 * ɾ������
	 */
	public void delNotice(int id)
	{
		if( lastedNotice!=null && lastedNotice.getId()==id )
		{
			lastedNotice=null;
		}
		FNoticeDao fNoticDao = new FNoticeDao();
		fNoticDao.delById(id);
	}
	
	/**
	 * ��������
	 */
	public void publishNotice(FNotice notice)
	{
		FNoticeDao fNoticDao = new FNoticeDao();
		fNoticDao.add(notice);
		this.lastedNotice = notice;
	}
	
	/**
	 * �õ���������
	 */
	public String getFullName()
	{
		return this.name+"("+ExchangeUtil.getRaceName(race)+")";
	}
	
	/**
	 * ��������
	 * @param update_prestige
	 * @return
	 */
	public boolean updatePrestige(int update_prestige)
	{
		if( update_prestige<0 && this.prestige<-update_prestige )//��������
		{
			return false;
		}
		this.prestige+=update_prestige;
		this.save();
		return true;
	}
	
	/**
	 * ����
	 */
	public String upgrade()
	{
		this.grade++;
		this.save();
		return null;
	}
	/**
	 * ����
	 */
	public String upgradeCitang()
	{
		this.citangGrade++;
		this.save();
		return null;
	}
	
	/**
	 * �ж��Ƿ���Լ�����ɳ�Ա
	 * @param operater                ִ����
	 * @param member			      ����ĳ�Ա
	 * @return
	 */
	public String isAddMember( RoleEntity member)
	{
		if( member==null )
		{
			return "�޸ý�ɫ";
		}
		if(member.getIsRookie())
		{
			return member.getName()+"�������������׶β������������ɣ�";
		}
		if( member!=null && member.getBasicInfo().getFaction()!=null )
		{
			return member.getName()+"�Ѽ�����������";
		}
		
		if( member.getBasicInfo().getPRace()!=this.race)
		{
			return "���岻��";
		}
		
		if( this.isFull() )
		{
			return "������������";
		}
		return null;
	}
	
	/**
	 * ��Ӱ��ɳ�Ա
	 * @param member		 ����ĳ�Ա
	 * @return
	 */
	public String addMember(RoleEntity member)
	{
		String hint = this.isAddMember( member);
		if( hint!=null )
		{
			return hint;
		}
		
		member.getBasicInfo().jionFaction(this.id, ZUZHONG);
		memberNum++;
		this.mGradeTotal = member.getBasicInfo().getGrade();
		this.save();
		return null;
	}

	
	@Override
	public String changeLeader(RoleEntity newLeader)
	{
		if( newLeader==null )
		{
			return "����Ҳ�����,����ת���峤";
		}
		
		//�ж��Ƿ�ת�õ���ȴʱ�䣬ʱ��Ϊ24Сʱ
		String hint = DateUtil.returnTimeStr(this.changeZZHTime);
		if( hint!=null )
		{
			return "���ڲ���ת���峤,���´�ת���峤����"+hint;
		}
		
		//ԭ�峤��Ϊ����
		this.getLeader().getBasicInfo().changeFJob(Faction.ZUZHONG);
		//�����µ��峤
		newLeader.getBasicInfo().changeFJob(Faction.ZUZHANG);
		this.leaderId = newLeader.getPPk();
		FactionDao factionDao = new FactionDao();
		
		Calendar cur_time = Calendar.getInstance();
		cur_time.add(Calendar.HOUR, 24);//�õ��´ο���ת�������ʱ��
		
		this.changeZZHTime = cur_time.getTime();
		factionDao.updateChangeZZHTime(this.id);
		return null;
	}

	@Override
	public String create(RoleEntity leader,String faction_name)
	{
		if( leader==null )
		{
			return "��������";
		}
		
		if( leader.getBasicInfo().getFaction()!=null )
		{
			return "���Ѽ�����������";
		}
		
		int leader_id = leader.getBasicInfo().getPPk();
		
		this.leaderId = leader_id;
		this.name = faction_name;
		this.race = leader.getBasicInfo().getPRace();
		this.mGradeTotal = leader.getBasicInfo().getGrade();
		
		factionDao.add(this);
		if( this.id==0 )
		{
			return "����ʧ��";
		}

		leader.getBasicInfo().jionFaction(id,ZUZHANG);
		
		member_list.put(leader_id, leader);
		return null;
	}

	@Override
	public String delMember(RoleEntity member)
	{
		if( member!=null )
		{
			if( member.getBasicInfo().getFJob()==Faction.ZUZHANG)
			{
				//������峤�뿪
				PartInfoDao partInfoDao = new PartInfoDao();
				List<Integer> list = partInfoDao.getUpZhanglaoListByFId(getId());
				MailInfoService mailInfoService = new MailInfoService();
				MailInfoVO mail = new MailInfoVO();
				mail.setMailType(MailInfoService.F_DISBAND_MAIL);
				mail.setSendPk(getId());
				mail.setTitle(getName()+"������ɢ!");
				mail.setContent("���������������ɢ����,��24Сʱ��,����δ�ı�����,���������彫���׽�ɢ!");
				for(Integer p_pk:list )
				{
					/**����ҷ���һ��������ɵ��ʼ�  */
					mail.setReceivePk(p_pk);
					mailInfoService.sendMail(mail);
				}
				this.leaderId=-1;
			}
			member.getBasicInfo().leaveFaction();
			memberNum--;
			this.mGradeTotal-=member.getBasicInfo().getGrade();
			this.isDisband=true;
			this.save();
		}
		return null;
	}
	/**
	 * �ӹܰ���
	 */
	public void assume(RoleEntity operater)
	{
		//����ǳ��Ͻӹ�
		if( operater.getBasicInfo().getFaction().getId()==this.id && operater.getBasicInfo().getFJob()==Faction.ZHANGLAO )
		{
			RoleEntity role= this.getLeader();
			if(role!=null)
			{
				role.getBasicInfo().leaveFaction();
				this.memberNum--;
				this.mGradeTotal-=role.getBasicInfo().getGrade();
			}
		}
		operater.getBasicInfo().changeFJob(Faction.ZUZHANG);
		this.leaderId=operater.getPPk();
		this.isDisband = false;
		this.save();
	}
	
	/**
	 * ��ɢ
	 */
	public void disband()
	{
		this.isDisband = true;
		this.save();
	}

	@Override
	public RoleEntity getLeader()
	{
		if( leaderId==-1 )
		{
			PartInfoDao partInfoDao = new PartInfoDao();
			leaderId = partInfoDao.getFactinLeader(this.id);
		}
		if(leaderId==-1)
		{
			return null;
		}
		return RoleService.getRoleInfoById(leaderId+"");
	}

	@Override
	public List<RoleEntity> getMemberList()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * �Ƿ������������ϵ�����
	 */
	public String isChangeJob(int job)
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		int cur_job_num = partInfoDao.getCurFJobNum(this.id, job);//��ǰְҵ������
		int max_job_num = this.getMaxJobNum(job);
		if( cur_job_num>=max_job_num )
		{
			return ExchangeUtil.getFJobName(job)+"����������";
		}
		return null;
	}

	/**
	 * �õ�ְ����������
	 * @return
	 */
	public int getMaxJobNum(int job)
	{
		switch(job)
		{
			case ZUZHANG:return 1;
			case ZHANGLAO:
				if( this.grade==1)
				{
					return 2;
				}
				else if( this.grade==2)
				{
					return 3;
				}
				break;
			case HUFA:
				if( this.grade==1)
				{
					return 5;
				}
				else if( this.grade==2)
				{
					return 10;
				}
				break;
			case ZUZHONG:return this.getMaxMemberNum();
		}
		return 0;
	}
	
	/**
	 * �õ���ǰ���ɵ��������
	 */
	public int getMaxMemberNum()
	{
		switch(this.grade)
		{
			case 1:return 20;
			case 2:return 30;
		}
		return 0;
	}
	
	/**
	 * �õ���ǰ��Ա����
	 */
	@Override
	public int getMemberNum()
	{
		return memberNum;
	}

	/**
	 * �жϰ����Ƿ�����
	 */
	public boolean isFull()
	{
		if( getMemberNum()>= getMaxMemberNum() )
		{
			return true;
		}
		return false;
	}

	
	
	@Override
	public boolean isJoin(RoleEntity member)
	{
		return false;
	}

	
	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public int getGrade()
	{
		return grade;
	}

	public int getPrestige()
	{
		return prestige;
	}

	public int getCitangGrade()
	{
		return citangGrade;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setGrade(int grade)
	{
		this.grade = grade;
	}

	public void setPrestige(int prestige)
	{
		this.prestige = prestige;
	}

	public void setCitangGrade(int citangGrade)
	{
		this.citangGrade = citangGrade;
	}

	public int getRace()
	{
		return race;
	}

	public void setRace(int race)
	{
		this.race = race;
	}

	public void save()
	{
		factionDao.save(this);
	}

	public void setMemberNum(int memberNum)
	{
		this.memberNum = memberNum;
	}
	public boolean getIsDisband()
	{
		return isDisband;
	}
	public void setIsDisband(boolean isDisband)
	{
		this.isDisband = isDisband;
	}
	public FStorage getFStorage()
	{
		if( fStorage==null )
		{
			FactionDao factionDao = new FactionDao();
			String material_str = factionDao.getStorageStr(id);
			fStorage = new FStorage(id,material_str);
		}
		return fStorage;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}


	public int getMGradeTotal()
	{
		return mGradeTotal;
	}

	public void setMGradeTotal(int gradeTotal)
	{
		if( gradeTotal<0 )
		{
			gradeTotal = 0;
		}
		mGradeTotal = gradeTotal;
	}


	public void setChangeZZHTime(Date changeZZHTime)
	{
		this.changeZZHTime = changeZZHTime;
	}
}
