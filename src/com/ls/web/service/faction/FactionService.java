package com.ls.web.service.faction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.faction.FApplyInfoDao;
import com.ls.ben.dao.faction.FNoticeDao;
import com.ls.ben.dao.faction.FactionDao;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.log.GameLogManager;
import com.ls.model.organize.faction.FApplyInfo;
import com.ls.model.organize.faction.FNotice;
import com.ls.model.organize.faction.Faction;
import com.ls.model.organize.faction.FactionRecruit;
import com.ls.model.organize.faction.game.FUpgradeMaterial;
import com.ls.model.user.RoleEntity;
import com.ls.pub.bean.QueryPage;
import com.ls.pub.util.ExchangeUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.goods.prop.PropService;
import com.ls.web.service.log.DataErrorLog;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.validate.ValidateService;
import com.pm.service.mail.MailInfoService;
import com.pm.vo.mail.MailInfoVO;

/**
 * @author ls
 * ��������߼�
 */
public class FactionService
{
	//������Ϣ����
	private static Map<Integer,Faction> faction_cache = new HashMap<Integer,Faction>(50);
	
	public static int MAX_GRADE = 2;//���ɵ����ȼ�
	public static int MAX_CITANG_GRADE = 5;//�����������ȼ�
	
	private static int CREATE_NEED_MONEY = 1000;//������Ҫ��Ǯ
	private static int CREATE_NEED_GRADE = 30;//��Ҫ�ĵȼ�
	private static int CREATE_NEED_PROP = 233;//������id
	
	private static int RECRUIT_NEED_PROP = 379;//��ļ��id
	private static int RECRUIT_NEED_MONEY = 100;//��ļ��Ҫ��Ǯ

	/**
	 * ������������
	 */
	public String upgradeCitang( RoleEntity operater )
	{
		Faction faction = operater.getBasicInfo().getFaction();
		
		if( faction.getGrade()<2 )
		{
			return "����ȼ�Ϊ1��,������������,������������";
		}
		
		if( faction.getCitangGrade()>=FactionService.MAX_CITANG_GRADE)
		{
			return "�����Ѵ���ߵȼ�,��������";
		}
		
		String hint = this.upgrade(operater, FUpgradeMaterial.C_UPGRADE);
		if( hint==null )
		{
			//������������
			faction.upgradeCitang();
			hint = "��ϲ��,��������������"+faction.getCitangGrade()+"��,"+faction.getCitangInfo().getEffectDes()+"!";
		}
		
		return hint;
	}
	/**
	 * ��������
	 */
	public String upgradeFaction( RoleEntity operater )
	{
		Faction faction = operater.getBasicInfo().getFaction();
		
		if( faction.getGrade()>=MAX_GRADE)
		{
			return "��������Ѵ���ߵȼ�";
		}
		
		String hint = this.upgrade(operater, FUpgradeMaterial.F_UPGRADE);
		if( hint==null )
		{
			//��������
			faction.upgrade();
			hint = "��ϲ��,���������Ѿ�����Ϊ"+faction.getGrade()+"������!";
		}
		
		return hint;
	}
	
	/**
	 * ����
	 */
	private String upgrade(RoleEntity operater,int upgradeType)
	{
		String hint = this.isOperate(operater, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = operater.getBasicInfo().getFaction();
		
		FUpgradeMaterial fMaterial = faction.getUpgradeMaterial(upgradeType);//������Ҫ�Ĳ���
		if( fMaterial==null )
		{
			DataErrorLog.debugData("�ް���������������,���ɵȼ�="+faction.getGrade());
			return "��������������";
		}
		
		//�жϰ��������Ƿ��㹻
		if( faction.getPrestige()<fMaterial.getPrestige() )
		{
			return "������������";
		}
		
		//�жϰ��ɲ����Ƿ��㹻
		hint = faction.getFStorage().isEnoughMaterial(fMaterial.getFMStr());
		if( hint!=null )
		{
			return hint;
		}
		
		//�жϸ��˲����Ƿ��㹻
		if( operater.getBasicInfo().isEnoughMoney(fMaterial.getMoney())==false )
		{
			return "��Ľ�Ǯ����";
		}
		
		GoodsService goodsService = new GoodsService();
		int cur_m_num = goodsService.getPropNum(operater.getPPk(), fMaterial.getMId());//�õ���ǰ��������
		if( cur_m_num<fMaterial.getMNum() )
		{
			return "���ϲ���";
		}
		
		
		//���ĸ��˲��ϺͰ��ɲ���
		operater.getBasicInfo().addCopper(-fMaterial.getMoney());
		faction.updatePrestige(-fMaterial.getPrestige());
		goodsService.removeProps(operater.getPPk(), fMaterial.getMId(), fMaterial.getMNum(),GameLogManager.R_MATERIAL_CONSUME);
		faction.getFStorage().consumeMaterial(fMaterial.getFMStr());
		
		return null;
	}
	
	/**
	 * �ӹܰ���
	 */
	public String assume(RoleEntity operater)
	{
		if( operater==null )
		{
			return "��������";
		}
		Faction faction = operater.getBasicInfo().getFaction();
		if( faction==null )
		{
			return "��������ѽ�ɢ";
		}
		
		if( operater.getBasicInfo().isOperateByFJob(Faction.ZHANGLAO)==false )
		{
			return "���Ȩ�޲���";
		}
		
		if( faction.getIsDisband()==false )
		{
			return "�����ѱ����˽ӹ�";
		}
		
		faction.assume(operater);
		return "��ϲ�������ѳɹ��ӹ������壬��������壡";
	}
	
	/**
	 * ��ɢ����
	 * @param operater
	 * @return
	 */
	public String disband(RoleEntity operater,HttpServletResponse response)
	{
		String hint = this.isOperate(operater, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		Faction faction = operater.getBasicInfo().getFaction();
		faction.disband();
		
		PartInfoDao partInfoDao = new PartInfoDao();
		List<Integer> list = partInfoDao.getUpZhanglaoListByFId(faction.getId());
		
		MailInfoService mailInfoService = new MailInfoService();
		MailInfoVO mail = new MailInfoVO();
		mail.setMailType(MailInfoService.F_DISBAND_MAIL);
		mail.setSendPk(faction.getId());
		mail.setTitle(faction.getName()+"������ɢ!");
		mail.setContent("���������������ɢ����,��24Сʱ��,����Ϊ�ı�����,���������彫���׽�ɢ!");
		for(Integer p_pk:list )
		{
			/**����ҷ���һ��������ɵ��ʼ�  */
			mail.setReceivePk(p_pk);
			mailInfoService.sendMail(mail);
		}
		return mail.getContent();
	}
	
	/**
	 * ת���峤
	 */
	public String changeZuzhang(RoleEntity operater,RoleEntity new_zuzhang)
	{
		String hint = this.isOperate(operater, new_zuzhang, Faction.ZUZHANG);
		if( hint!=null )
		{
			return hint;
		}
		if( new_zuzhang.getBasicInfo().getFJob()!=Faction.ZHANGLAO)
		{
			return new_zuzhang.getName()+"��ְλ�����峤������ת���峤ְλ";
		}
		
		Faction faction = new_zuzhang.getBasicInfo().getFaction();
		
		hint = faction.changeLeader(new_zuzhang);
		if( hint!=null )
		{
			return hint;
		}
		
		return "���Ѿ��ɹ����峤һְת�ø���"+new_zuzhang.getName()+"�������ڵ�ְλΪ���ڣ�";
	}
	
	/**
	 * ������ļ��Ϣ
	 */
	public String recruit(RoleEntity operater,String rInfo)
	{
		String hint = this.isOperate(operater, Faction.HUFA);
		if( hint!=null )
		{
			return hint;
		}
		
		hint = ValidateService.validateBasicInput(rInfo, 25);
		if( hint!=null )
		{
			return hint;
		}
		//�жϽ�Ǯ�Ƿ��㹻
		if( operater.getBasicInfo().isEnoughMoney(RECRUIT_NEED_MONEY)==false)
		{
			return "��Ǯ����";
		}
		//�ж��Ƿ�����ļ��
		PropService propService = new PropService();
		if( propService.isEnoughProp(operater, RECRUIT_NEED_PROP, 1)==false )
		{
			return "û��"+PropCache.getPropNameById(RECRUIT_NEED_PROP);
		}
		
		//�۳���ļ��Ҫ�Ĳ���
		GoodsService goodsService = new GoodsService();
		operater.getBasicInfo().addCopper(-RECRUIT_NEED_MONEY);
		goodsService.removeProps(operater.getPPk(), CREATE_NEED_PROP, 1,GameLogManager.R_USE);
		//todo:������ļϵͳ��Ϣ
		FactionRecruit.getInstance().recruit(operater.getBasicInfo().getFaction().getId(), rInfo);
		
		return null;
	}
	/**
	 * ���ĳ�Ա��ְλ
	 */
	public String changeJob(RoleEntity operater,RoleEntity member,int new_job)
	{
		String hint = this.isOperate(operater, member, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		
		if( new_job<Faction.ZUZHONG || new_job>=Faction.ZUZHANG)
		{
			return "�Ƿ�ְλ";
		}
		
		//����ǳ��ϸ���������Աְλ�����ܸ��ĸð������������ϵ�ְλ
		if( operater.getBasicInfo().getFJob()==Faction.ZHANGLAO && member.getBasicInfo().getFJob()>=Faction.ZHANGLAO )
		{
			return "���Ȩ�޲���";
		}
		
		if( member.getBasicInfo().getFJob()==new_job)
		{
			return member.getName()+"��ְλ����"+ExchangeUtil.getFJobName(new_job)+",������!";
		}
		
		hint = operater.getBasicInfo().getFaction().isChangeJob(new_job);
		if(  hint!=null )
		{
			return hint;
		}
		
		member.getBasicInfo().changeFJob(new_job);
		
		return "��ϲ����"+member.getName()+"��ְλ�ѱ��"+ExchangeUtil.getFJobName(new_job)+"��";
	}
	
	/**
	 * ����
	 * @param roleEntity
	 * @param invited_role_name    �������ߵ�����
	 * @return
	 */
	public String invite( RoleEntity roleEntity,String invited_role_name,HttpServletResponse response )
	{
		String hint = this.isOperate(roleEntity, Faction.HUFA);
		if( hint!=null )
		{
			return hint;
		}
		
		hint = ValidateService.validateBasicInput(invited_role_name, 10);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		RoleService roleService = new RoleService();
		RoleEntity inviteder = roleService.getRoleInfoByName(invited_role_name);//�õ��������ߵ���Ϣ
		hint = faction.isAddMember( inviteder);
		if( hint!=null )
		{
			return hint;
		}
		
		//���������߷����ʼ�
		MailInfoService mailInfoService = new MailInfoService();
		/**����ҷ���һ��������ɵ��ʼ�  */
		String mailTitle = faction.getName()+"�����������ļ���!";
		MailInfoVO mail = new MailInfoVO();
		mail.setSendPk(faction.getId());
		mail.setReceivePk(inviteder.getPPk());
		mail.setMailType(MailInfoService.F_INVITE_MAIL);
		mail.setTitle(mailTitle);
		mail.setContent(mailTitle);
		mailInfoService.sendMail(mail);
		
		return "���������Ѿ��������������������";
	}
	
	/**
	 * ͨ��idɾ��������Ϣ
	 */
	public String delApply( RoleEntity roleEntity,int aId )
	{
		String hint = this.isOperate(roleEntity, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		FApplyInfo fApplyInfo =  fApplyInfoDao.getById(aId);
		
		if( roleEntity.getBasicInfo().getFaction().getId()!=fApplyInfo.getFId() )
		{
			return "�Ƿ�����";
		}
		fApplyInfoDao.delById(aId);
		return null;
	}
	
	/**
	 *ͨ��id�õ�������Ϣ
	 */
	public String agreeApply( RoleEntity roleEntity,int aId )
	{
		String hint = this.isOperate(roleEntity, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		FApplyInfo fApplyInfo =  fApplyInfoDao.getById(aId);
		
		if( fApplyInfo==null )
		{
			return "�Է���ȡ������";
		}
		
		if( faction.getId()!=fApplyInfo.getFId() )
		{
			return "�Ƿ�����";
		}
		RoleEntity applyer = fApplyInfo.getRoleEntity();//������
		
		if( applyer.getBasicInfo().getFaction()!=null )
		{
			return applyer.getName()+"�Ѽ�����������";
		}
		
		hint = faction.addMember(applyer);
		if( hint!=null )
		{
			return hint;
		}
		
		fApplyInfoDao.delByPPk(fApplyInfo.getPPk());
		
		MailInfoService mailInfoService = new MailInfoService();
		mailInfoService.sendMailAndSystemInfo(fApplyInfo.getPPk(), "�����ʼ�", "���ѳɹ���������"+faction.getName());
		return "��ϲ��"+applyer.getName()+"�Ѿ����뵽����������֮�У�";
	}
	
	/**
	 * ɾ����Ա
	 */
	public String delMember(RoleEntity operater,RoleEntity member )
	{
		String hint = this.isOperate(operater, member, Faction.ZHANGLAO);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction operater_faction = operater.getBasicInfo().getFaction();
		Faction member_faction = member.getBasicInfo().getFaction();
		
		if( member_faction==null )
		{
			return member_faction.getName()+"���뿪����";
		}
		
		//����������ǳ��ϣ����������峤����������
		if( operater.getBasicInfo().getFJob()==Faction.ZHANGLAO &&  member.getBasicInfo().getFJob()>=Faction.ZHANGLAO)
		{
			return "���Ȩ�޲���";
		}
		
		
		if( member_faction.getId()!=operater_faction.getId() )
		{
			return "���ǲ���";
		}
		
		operater_faction.delMember(member);
		
		return "���ѳɹ���"+member.getName()+"�������!";
	}
	
	/**
	 * �����б�
	 */
	public QueryPage getApplyListPageList( int f_id,int page_no )
	{
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		return fApplyInfoDao.getPageList(f_id,page_no);
	}
	
	/**
	 * ���ɳ�Ա�б�
	 */
	public QueryPage getMemListPageList( int f_id,int page_no )
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		return partInfoDao.getPageListByFId(f_id, page_no);
	}
	/**
	 * ���ɳ����б�
	 */
	public QueryPage getZhanglaoListPageList( int f_id,int page_no )
	{
		PartInfoDao partInfoDao = new PartInfoDao();
		return partInfoDao.getPageZhanglaoListByFId(f_id, page_no);
	}
	
	
	/**
	 * ����������
	 */
	public String apply( RoleEntity roleEntity,Faction faction )
	{
		if( roleEntity==null )
		{
			return "�޸ý�ɫ";
		}
		
		if(  roleEntity.getBasicInfo().getFaction()!=null )
		{
			return "��������,���Ѽ�����������";
		}
		
		if( faction==null )
		{
			return "�ð����ѽ�ɢ";	
		}
		
		if( roleEntity.getBasicInfo().getPRace()!=faction.getRace())
		{
			return "���岻��";
		}
		
		FApplyInfoDao fApplyInfoDao = new FApplyInfoDao();
		
		if( fApplyInfoDao.isHave(roleEntity.getPPk(), faction.getId())==true )
		{
			return "�����ύ������";
		}
		
		fApplyInfoDao.add(roleEntity.getPPk(), faction.getId());
		return "�����������ύ�����������������������ǻ���ϵͳ�ʼ��ķ�ʽ֪ͨ����";
	}
	
	/**
	 * �õ������б�
	 */
	public QueryPage getPageList( int page_no )
	{
		FactionDao factionDao = new FactionDao();
		return factionDao.getPageList(page_no);
	}
	
	/**
	 * �ж��Ƿ����㴴�����ɵ�����
	 * @param roleEntity
	 * @return
	 */
	public String isCreated( RoleEntity roleEntity )
	{
		String hint = null;
		
		if( roleEntity==null )
		{
			return "��������";
		}
		
		if( roleEntity.getBasicInfo().getGrade()<CREATE_NEED_GRADE )
		{
			return "�ȼ�������";
		}
		
		if( roleEntity.getBasicInfo().isEnoughMoney(CREATE_NEED_MONEY)==false )
		{
			return "��Ǯ����";
		}
		
		PropService propService = new PropService();
		if( propService.isEnoughProp(roleEntity, CREATE_NEED_PROP, 1)==false )
		{
			return "û��"+PropCache.getPropNameById(CREATE_NEED_PROP);
		}
		
		return hint;
	}
	
	/**
	 * ��������
	 */
	public String create( RoleEntity roleEntity,String faction_name )
	{
		if( roleEntity==null )
		{
			return "���ݴ���";
		}
		
		if( this.isCreated(roleEntity)!=null )
		{
			return "�Բ������ĵȼ�����������ʯ���㣬��û����������ɴ������壡";
		}
		
		String hint = this.validateFName(faction_name);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = new Faction();
		hint = faction.create(roleEntity,faction_name);//��������
		if( hint!=null )
		{
			return hint;
		}
		
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(roleEntity.getPPk(), CREATE_NEED_PROP, 1,GameLogManager.R_MATERIAL_CONSUME);
		roleEntity.getBasicInfo().addCopper(-CREATE_NEED_MONEY);
		return null;
	}
	
	/**
	 * ��֤���ɵ������Ƿ�Ϸ�
	 */
	private String validateFName(String faction_name )
	{
		String hint = ValidateService.validateBasicInput(faction_name, 5);
		if( hint!=null )
		{
			return hint;
		}
		
		FactionDao factionDao = new FactionDao();
		if( factionDao.isHaveName(faction_name))
		{
			return "�����ѱ�ռ��";
		}
		
		return null;
	}
	
	/**
	 * ������ļ��Ϣ
	 */
	public String publishRecruitInfo()
	{
		return null;
	}
	
	/**
	 * ��������
	 */
	public String publishNotice( RoleEntity roleEntity,String content )
	{
		if( roleEntity==null )
		{
			return "��������";
		}
		
		if( roleEntity.getBasicInfo().getIsPublishNotice()==false )
		{
			return "�Բ���ֻ���峤�����Ϻͻ����ž��з��������Ȩ���������д���Ȩ����";
		}
		
		String hint = ValidateService.validateBasicInput(content, 30);
		if( hint!=null )
		{
			return hint;
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		FNotice notice = new FNotice(faction.getId(),content);
		faction.publishNotice(notice);
		return null;
	}
	/**
	 * ɾ������
	 * @param roleEntity
	 * @param id		����id
	 * @return
	 */
	public String delNotice(RoleEntity roleEntity,int id)
	{
		if( roleEntity==null )
		{
			return "��������";
		}
		
		if( roleEntity.getBasicInfo().getIsDelNotice()==false )
		{
			return "�Բ���ֻ���峤���ϲ���Ȩ��ɾ�����棬�����д���Ȩ����";
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		
		FNoticeDao fNoticDao = new FNoticeDao();
		FNotice notice = fNoticDao.getById(id);
		
		if( faction.getId()!=notice.getFId())
		{
			return "�Ƿ�����";
		}
		
		faction.delNotice(id);
		
		return null;
	}
	
	/**
	 * �õ�����ķ�ҳ�б�
	 * @return
	 */
	public QueryPage getNoticePageList(int f_id,int page_no)
	{
		FNoticeDao fNoticDao = new FNoticeDao();
		return fNoticDao.getPageList(f_id, page_no);
	}
	
	/**
	 * ͨ��id�õ�������Ϣ
	 */
	public static Faction getById( int fId )
	{
		Faction faction = faction_cache.get(fId);
		if( faction==null )
		{
			FactionDao factionDao = new FactionDao();
			faction = factionDao.getById(fId);
			if( faction!=null )
			{
				faction_cache.put(faction.getId(), faction);
			}
		}
		return faction_cache.get(fId);
	}
	
	/**
	 * ɾ������
	 * @param faction
	 */
	public static void del( int id )
	{
		if( id>0 )
		{
			faction_cache.remove(id);
			FactionDao factionDao = new FactionDao();
			factionDao.delById(id);
		}
	}

	/**
	 * ��Ҷ԰�������(���������Ա�Ĳ���)�Ĳ������������ж�
	 * @param operater				ִ�в��������
	 * @param job 					ִ�в��������ְ��
	 * @return
	 */
	private String isOperate(RoleEntity operater,RoleEntity member,int job)
	{
		String hint = this.isOperate(operater, job);
		if( hint!=null )
		{
			return hint;
		}
		if( member==null )
		{
			return "�ó�Ա�Ѳ�����";
		}
		
		if( operater.getPPk()==member.getPPk() )
		{
			return "�Ƿ�����";
		}
		
		//�ж��Ƿ���ͬһ����
		if( operater.getBasicInfo().getFaction().getId()!=member.getBasicInfo().getFaction().getId())
		{
			return "�Ƿ�����";
		}
		
		return null;
	}
	
	/**
	 * ��Ҷ԰�������Ĳ������������ж�
	 * @param operater				ִ�в��������
	 * @param job 					ִ�в��������ְ��
	 * @return
	 */
	String isOperate(RoleEntity operater,int job)
	{
		if( operater==null )
		{
			return "��������";
		}
		Faction faction = operater.getBasicInfo().getFaction();
		if( faction==null )
		{
			return "��������ѽ�ɢ";
		}
		
		if( faction.getIsDisband()==true )
		{
			return "���屻��ɢ��...";
		}
		
		if( operater.getBasicInfo().isOperateByFJob(job)==false )
		{
			return "���Ȩ�޲���";
		}
		
		return null;
	}
	
	/**
	 * ��ʱ�������ɽ�ɢ
	 */
	public static void checkDisband()
	{
		try
		{
			FactionDao factionDao = new FactionDao();
			List<Integer> list = factionDao.getDisbandList();
			for( Integer id:list )
			{
				del(id);
			}
		}
		catch(Exception e)
		{
			DataErrorLog.debugData("��ʱ�������ɽ�ɢ,����"+e.getMessage());
		}
	}
	
	/**
	 * ��ʱ������Ƿ��ǻ�Ծ���ɣ��Ƿ���10�����ˣ�
	 */
	public static void checkIsActived()
	{
		try
		{
			Set<Integer> f_id_list = faction_cache.keySet();
			for( Integer f_id:f_id_list )
			{
				Faction faction = faction_cache.get(f_id);
				if( faction==null )
				{
					del(f_id);
				}
				else
				{
					if( faction.isActived()==false )
					{
						del(f_id);
					}
				}
			}
		}
		catch(Exception e)
		{
			DataErrorLog.debugData("��ʱ������Ƿ��ǻ�Ծ����,����"+e.getMessage());
		}
	}
}
