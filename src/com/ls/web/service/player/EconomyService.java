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
 * 功能:玩家经济管理
 * Feb 20, 2009
 */
public class EconomyService
{
	Logger logger = Logger.getLogger("log.service");
	
	
	/**
	 * 给玩家增加元宝数量
	 * @param u_pk                           玩家id
	 * @param yb_num                         得到元宝数量
	 * @param channel						 获得元宝的途径
	 */
	public void addYuanbao(int p_Pk, int u_pk,int yb_num,String channel)
	{
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
//		StoreProce storeProce = new StoreProce();
//		storeProce.fullYuanBao(u_pk,yb_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateYuanbao(u_pk, yb_num);
		//统计需要
		new RankService().updateAdd(p_Pk, "yuanbao", yb_num);
		//执行统计
		gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, channel,u_pk);//充值方式
	}
	
	/**
	 * 给玩家增加元宝数量
	 * @param u_pk                           玩家id
	 * @param yb_num                         得到元宝数量
	 * @param channel						 获得元宝的途径
	 */
	public void addYuanbao( int u_pk,long yb_num)
	{
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
//		StoreProce storeProce = new StoreProce();
//		
//		storeProce.fullYuanBao(u_pk,yb_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateYuanbao(u_pk, yb_num);
		
		//执行统计
		//gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, channel,u_pk);//充值方式
	}
	
	/**
	 * 给玩家增加积分数量
	 * @param u_pk                           玩家id
	 * @param yb_num                         得到元宝数量
	 * @param channel						 获得元宝的途径
	 */
	public void addJifen( int u_pk,int jf_num)
	{
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
//		StoreProce storeProce = new StoreProce();
//		
//		storeProce.addPlayerJf(u_pk,jf_num );
		
		EconomyDao economyDao = new EconomyDao();
		
		economyDao.updateJifen(u_pk, jf_num);
		
		//执行统计
		//gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,StatisticsType.DEDAO, channel,u_pk);//充值方式
	}
	
	/**
	 * 用户花费元宝
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
		
		// 执行统计元宝销售
		GameSystemStatisticsService gsss = new GameSystemStatisticsService();
		gsss.addPropNum(7, StatisticsType.YUANBAO, yb_num,
				StatisticsType.USED, StatisticsType.BUY, u_pk);
		
	}
	
	/**
	 * 用户花费积分
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
	 * 得到玩家账号的元宝数量
	 */
	public long getYuanbao(int u_pk)
	{
		EconomyDao economyDao = new EconomyDao();
		
		return economyDao.getYuanbao(u_pk);
	}
	
	
	
	/**
	 * 得到玩家账号的积分数
	 */
	public int getJifen(int u_pk)
	{
		EconomyDao economyDao = new EconomyDao();
		
		return economyDao.getJifen(u_pk);
	}
	
	
	
	
	
	/**
	 * 玩家挣钱
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
	 * 花钱
	 */
	public void spendMoney(int p_pk, int money)
	{
		RoleService roleService = new RoleService();
		RoleEntity  roleInfo = roleService.getRoleInfoById(p_pk+"");
		
		roleInfo.getBasicInfo().addCopper(-money);
	}
}
