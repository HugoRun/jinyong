package com.ls.web.service.player;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.ben.dao.deletepart.DeletePartDAO;
import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.vo.honour.TitleVO;
import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.ben.cache.staticcache.honour.TitleCache;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.ben.dao.info.partinfo.PlayerEquipDao;
import com.ls.ben.dao.info.partinfo.UGrowDao;
import com.ls.ben.dao.storage.WareHouseDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.ShortcutVO;
import com.ls.ben.vo.info.partinfo.UGrowVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.organize.faction.Faction;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.Shortcut;
import com.ls.pub.constant.Wrap;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.login.LoginService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.pk.PKHiteService;
import com.ls.web.service.rank.RankService;
import com.lw.service.laborage.LaborageService;
import com.pm.dao.setting.SettingDao;
import com.web.service.friend.FriendService;
import com.web.service.task.TaskVisitLeadService;

/**
 * ���ܣ���ɫ����
 */
public class RoleService
{

	/**
	 * ������ɫ
	 * @param uPk
	 * @param role_name
	 * @param sex
	 * @param race
	 * @return ����null��ʾ����ʧ��
	 */
	public RoleEntity createRole(String uPk, String role_name, String sex,
			String race)
	{
		//��ʼ����ɫ������ݱ�
		int p_pk = this.initDataTable(uPk, role_name, sex, Integer.parseInt(race));

		if (p_pk == -1)
		{
			return null;
		}

		RoleEntity roleInfo = RoleCache.getByPpk(p_pk);
		if( roleInfo==null )
		{
			return null;
		}

		//��ʼ����ɫ�߼���ز���
		//initRoleLogic(roleInfo);
		
		return roleInfo;
	}
	
	/**
	 * ɾ����ɫ����
	 */
	public boolean delRole( String uPk,String pPk )
	{
		RoleEntity role_info = getRoleInfoById(pPk);
		if( role_info==null || StringUtils.isEmpty(uPk)==true || role_info.getUPk()!=Integer.parseInt(uPk) )
		{
			//��ɫ��ɾ��
			return false;
		}
		
		//************��ɫɾ������
		//������ߣ��������ߴ���
		if( role_info.isOnline()==true )
		{
			LoginService loginService = new LoginService();
			loginService.loginoutRole(pPk);
		}
		
		//�˳����ɴ���
		Faction faction = role_info.getBasicInfo().getFaction();
		if( faction!=null )
		{
			faction.delMember(role_info);
		}
		//ɾ������
		FriendService fs=new FriendService();
		fs.removeFriendInfo(Integer.parseInt(pPk));
		//ɾ�����
		PKHiteService ps=new PKHiteService();
		ps.removeHiteInfo(Integer.parseInt(pPk));
		//ɾ�����а�
		RankService rs=new RankService();
		rs.removeRandInfo(Integer.parseInt(pPk));
		//ɾ��װ��
		PlayerEquipDao ped=new PlayerEquipDao();
		ped.clear(Integer.parseInt(pPk));
		//ɾ������
		MountsService ms=new MountsService();
		ms.removeMountsInfo(Integer.parseInt(pPk));
		//��ʶ����
		DeletePartDAO deletePartDAO = new DeletePartDAO();
		int result = deletePartDAO.delete(pPk,uPk,role_info.getIsRookie());
		if( result!=1 )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * ���ý�ɫΪɾ��״̬,��ɾ�������1
	 * @param p_pk
	 */
	public void setRoleDeleteState(int p_pk)
	{
		// ���ý�ɫΪɾ��״̬
		PlayerService playerService = new PlayerService();
		playerService.updateDeleteFlag(p_pk, 1);
	}

	/**
	 * �ָ���ɫ,��ɾ���������
	 * @param p_pk
	 */
	public void resumeRole(int p_pk)
	{
		// �ָ���ɫ
		PlayerService playerService = new PlayerService();
		playerService.updateDeleteFlag(p_pk, 0);
	}

	/**
	 * ͨ��session�õ���ɫ��Ϣ
	 */
	public RoleEntity getRoleInfoBySession(HttpSession session)
	{
		String pPk = (String) session.getAttribute("pPk");
		if( StringUtils.isEmpty(pPk) )
		{
			return null;
		}
		return getRoleInfoById(pPk);
	}

	/**
	 * ͨ��p_pk�õ���ɫ
	 */
	public static RoleEntity getRoleInfoById(String pPk)
	{
		return RoleCache.getByPpk(pPk);
	}
	/**
	 * ͨ��p_pk�õ���ɫ
	 */
	public static RoleEntity getRoleInfoById(int pPk)
	{
		return RoleCache.getByPpk(pPk);
	}
	
	/**
	 * ͨ����ɫ��ɫ���ֵĵ���ɫ��Ϣ
	 */
	public RoleEntity getRoleInfoByName(String role_name)
	{
		int p_pk = getByName( role_name);
		return getRoleInfoById(p_pk+"");
	}

	/**
	 * ���ֵ�½ʱ����ҳ�ʼ���Ĵ���
	 */
	public void initRoleLogic(RoleEntity roleInfo)
	{
		if( roleInfo==null )
		{
			return;
		}
		
		TaskVisitLeadService taskVisitLeadService = new TaskVisitLeadService();
		GoodsService goodsService = new GoodsService();
		
		//ѧϰһ������
		int skill_id = 45;//����id
		PlayerSkillVO skill = roleInfo.getRoleSkillInfo().study(skill_id);
		
		// ����ҷ��ų�ʼ����
		int prop_id = 390;//����id
		goodsService.putPropToWrap(roleInfo.getPPk(), prop_id, 5,GameLogManager.G_SYSTEM);
		prop_id = 391;//����id
		goodsService.putPropToWrap(roleInfo.getPPk(), prop_id, 5,GameLogManager.G_SYSTEM);
		prop_id = 148;//����id,������
		goodsService.putPropToWrap(roleInfo.getPPk(), prop_id, 20,GameLogManager.G_SYSTEM);
		
		//���ÿ�ݼ�
		ShortcutService shortcutService = new ShortcutService();
		List<ShortcutVO> shortcut_list = roleInfo.getRoleShortCutInfo().getShortList();
		//���õ�1����ݼ�Ϊ����
		shortcutService.updateShortcut(shortcut_list.get(0).getScPk(), Shortcut.ATTACK,0, roleInfo.getPPk()+"");
		//���õ�2����ݼ�ΪҩƷ
		shortcutService.updateShortcut(shortcut_list.get(1).getScPk(), Shortcut.CURE,prop_id, roleInfo.getPPk()+"");
		//���õ�3����ݼ�Ϊ����
		shortcutService.updateShortcut(shortcut_list.get(2).getScPk(), Shortcut.SKILL,skill.getSPk(), roleInfo.getPPk()+"");
		
		//��װ��
		for( int equip_id=127;equip_id<=134;equip_id++)
		{
			//��װ������������
			goodsService.giveEquipOnBody(roleInfo, equip_id, Equip.Q_ORANGE);
		}
		
		
		//��������ӳ�ʼ�ƺź�����
		String t_zu = null;//������
		int race = roleInfo.getBasicInfo().getPRace();
		TitleVO race_title = null;//����ƺ�
		switch(race)
		{
			case 1://��
				race_title = TitleCache.getById(10);
				t_zu = "ty_juezhanbuzhoushan01";// ������
				break;
			case 2://��
				race_title = TitleCache.getById(18);
				t_zu = "ty_juezhanbuzhoushan02";// ������
				break;
		}
		// ��ʼ��һ�������������
		taskVisitLeadService.acceptTask(roleInfo, t_zu);
		//���һ���ƺ�
		roleInfo.getTitleSet().gainTitle(race_title);
		
		//��ʼ��Ѫ���ͷ���
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByRoleInfo(roleInfo);
		PropertyService propertyService = new PropertyService();
		propertyService.updateHpProperty(player.getPPk(), player.getPUpHp());
		propertyService.updateMpProperty(player.getPPk(), player.getPUpMp());
	}


	/**
	 * ��ʼ����ɫ��ر�
	 * @param uPk
	 * @param role_name
	 * @param sex
	 * @param race
	 * @return
	 */
	private int initDataTable(String uPk, String role_name, String sex,int race)
	{
		UGrowDao u_grow_dao = new UGrowDao();
		UGrowVO init_info = u_grow_dao.getMaxGradeInfo(race);//�õ���ɫ��ʼ����Ϣ
		
		if( init_info==null )
		{
			return -1;
		}
		
		/** ����ֵ���� */
		String pUpHp = init_info.getGHP()+"";
		/** ����ֵ���� */
		String pUpMp = init_info.getGMP()+"";
		/** ���� */
		String pGj = init_info.getGGj()+"";
		/** ���� */
		String pFy = init_info.getGFy()+"";
		/** ������ */
		String pDropMultiple = init_info.getGDropMultiple()+"";
		
		/** ��ʼ��Ǯ */
		String pCopper = "1000";
		/** ����1��2�� */
		String pPks = "1";
		/** �������� */
		String pWrapContent = "50";

		/** ���������� */
		String pMap = "1";
		switch(race)
		{
			case 1://��
				pMap = "328";
				break;
			case 2://��
				pMap = "329";
				break;
		}
		
		//��ʼ����ɫ������Ϣ��
		PartInfoDao partInfoDAO = new PartInfoDao();
		int p_pk = partInfoDAO.add(uPk, role_name, sex, pUpHp, pUpMp, pGj, pFy,
				 pCopper, pPks,pDropMultiple,pMap, pWrapContent,race);

		if( p_pk==-1 )
		{
			return p_pk;
		}
		
		//��ʼ����ݼ�
		PartInfoDAO dao = new PartInfoDAO();
		dao.initShortcut(p_pk+"");
		//��ʼ���ֿ�����
		WareHouseDao wareHouseDao = new WareHouseDao();
		wareHouseDao.insertWareHouse(Integer.parseInt(uPk), p_pk, Wrap.COPPER);
		//��ʼ����������
		SettingDao settingDao = new SettingDao();
		settingDao.createSysSetting(p_pk);
		//��ʼ����ɫ���ʱ�
		LaborageService ls = new LaborageService();
		ls.insertPlayLaborageMessage(p_pk);
		return p_pk;
	}
	

	public String[] getName(String p_pk)
	{
		return new PartInfoDAO().getName(p_pk);
	}

	public int getByName(String pName)
	{
		return new PartInfoDao().getByName(pName);
	}

	/*******************ϵͳ������͸���ɫ��ѵ�һ������**********************/
	public void addMountsForPart( RoleEntity roleInfo)
	{
		int[] types=new int[]{1,2,3};
		Random random=new Random();
		int randomNum=random.nextInt(3);
		int mountsType=types[randomNum];
		MountsService ms=new MountsService();
		MountsVO mv=ms.getMountsInfoBySystem(mountsType);
		UserMountsVO umv=new UserMountsVO();
		if(mv==null)
		{
			return;
		}
		umv.setPpk(roleInfo.getPPk());
		umv.setMountsID(mv.getId());
		umv.setMountsState(1);
		roleInfo.getMountSet().addMounts(umv);
	}
	
	/**
	 * // ����ս����ʱר�÷���.
	 * 
	 * @param p_pk
	 * @param parseInt
	 *//*
	private void tongSiegeInit(int p_pk, int uPk)
	{
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>(3);
		list.add("mingjiao");
		list.add("gaibang");
		list.add("shaolin");

		PartInfoDAO dao = new PartInfoDAO();
		Random random = new Random();
		int menpai = random.nextInt(list.size());

		List<String> menpailist = new ArrayList<String>(16);
		// �����0�ǣ�
		if (menpai == 0)
		{
			menpailist.add("mingjiao");
			menpailist.add("����");
			menpailist.add("wuxingqijingrui");
			menpailist.add("�����쾫��");
			dao.getMenPaiInfo(menpailist, 0);
		}
		else
			if (menpai == 1)
			{
				menpailist.add("gaibang");
				menpailist.add("ؤ��");
				menpailist.add("sandaidizi");
				menpailist.add("��������");
				dao.getMenPaiInfo(menpailist, 1);
			}
			else
				if (menpai == 2)
				{
					menpailist.add("shaolin");
					menpailist.add("����");
					menpailist.add("wuseng");
					menpailist.add("��ɮ");
					dao.getMenPaiInfo(menpailist, 2);
				}

		dao.updatePartInfo(menpailist, p_pk);
		dao.addSkillInfo(p_pk, menpai);
	}*/
	
	
}
