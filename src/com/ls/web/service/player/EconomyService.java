package com.ls.web.service.player;

import org.apache.log4j.Logger;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ls.ben.dao.economy.EconomyDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.StatisticsType;
import com.ls.web.service.rank.RankService;
import com.lw.service.gamesystemstatistics.GameSystemStatisticsService;

/**
 * @author ls
 * ����:��Ҿ��ù���
 * Feb 20, 2009
 */
public class EconomyService
{
	Logger logger = Logger.getLogger("log.service");
	
	
	/**
	 * ���������Ԫ������
	 * @param u_pk                           ���id
	 * @param yb_num                         �õ�Ԫ������
	 * @param channel						 ���Ԫ����;��
	 */
	public void addYuanbao(int p_Pk, int u_pk,int yb_num,String channel)
	{
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
//		StoreProce storeProce = new StoreProce();
//		storeProce.fullYuanBao(u_pk,yb_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateYuanbao(u_pk, yb_num);
		//ͳ����Ҫ
		new RankService().updateAdd(p_Pk, "yuanbao", yb_num);
		//ִ��ͳ��
		gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, channel,u_pk);//��ֵ��ʽ
	}
	
	/**
	 * ���������Ԫ������
	 * @param u_pk                           ���id
	 * @param yb_num                         �õ�Ԫ������
	 * @param channel						 ���Ԫ����;��
	 */
	public void addYuanbao( int u_pk,long yb_num)
	{
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
//		StoreProce storeProce = new StoreProce();
//		
//		storeProce.fullYuanBao(u_pk,yb_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateYuanbao(u_pk, yb_num);
		
		//ִ��ͳ��
		//gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, channel,u_pk);//��ֵ��ʽ
	}
	
	/**
	 * ��������ӻ�������
	 * @param u_pk                           ���id
	 * @param yb_num                         �õ�Ԫ������
	 * @param channel						 ���Ԫ����;��
	 */
	public void addJifen( int u_pk,int jf_num)
	{
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
//		StoreProce storeProce = new StoreProce();
//		
//		storeProce.addPlayerJf(u_pk,jf_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateJifen(u_pk, jf_num);
		
		//ִ��ͳ��
		//gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, channel,u_pk);//��ֵ��ʽ
	}
	
	/**
	 * �û�����Ԫ��
	 * @param u_pk
	 * @param yb_num
	 */
	public void spendYuanbao( int u_pk,int yb_num )
	{
//		StoreProce storeProce = new StoreProce();
//		
//		storeProce.subPlayerYue(u_pk,1,yb_num );
		
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateYuanbao(u_pk, -yb_num);
		
		// ִ��ͳ��Ԫ������
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,
				StatisticsType.USED, StatisticsType.BUY, u_pk);
		
	}
	
	/**
	 * �û����ѻ���
	 * @param u_pk
	 * @param yb_num
	 */
	public void spendJifen( int u_pk,int jf_num )
	{
//		StoreProce storeProce = new StoreProce();
//		
//		storeProce.subPlayerYue(u_pk,2,yb_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateJifen(u_pk, -jf_num);
	}
	
	/**
	 * �õ�����˺ŵ�Ԫ������
	 */
	public long getYuanbao(int u_pk)
	{
		EconomyDao economyDao = new EconomyDao();
		
		return economyDao.getYuanbao(u_pk);
	}
	
	
	
	/**
	 * �õ�����˺ŵĻ�����
	 */
	public int getJifen(int u_pk)
	{
		EconomyDao economyDao = new EconomyDao();
		
		return economyDao.getJifen(u_pk);
	}
	
	
	
	
	
	/**
	 * �����Ǯ
	 * 
	 * @param player
	 * @param add_money
	 */
	public void addMoney(PartInfoVO player, int add_money)
	{
		addMoney(player.getPPk(), add_money);
		player.setPCopper(""
				+ (Long.parseLong(player.getPCopper()) + add_money));
	}

	public void addMoney(int p_pk, int add_money)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(p_pk+"");
		
		roleInfo.getBasicInfo().addCopper(add_money);
	}
	
	
	public void addMoney1(int p_pk, int add_money)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(p_pk+"");
		if(roleInfo!=null){
		roleInfo.getBasicInfo().addCopper(add_money);
		}else{
			new PartInfoDAO().updateMoney(p_pk, add_money);
		}
	}
	
	
	/**
	 * ��Ǯ
	 */
	public void spendMoney(int p_pk, int money)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(p_pk+"");
		
		roleInfo.getBasicInfo().addCopper(-money);
	}
}
