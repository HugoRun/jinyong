package com.ls.model.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import com.ls.ben.dao.log.GameLogDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * 游戏日志工管理
 */
public class GameLogManager
{
	public static int NO_LOG = 0;//不需要记录
	
	//************获得物品途径(G_表示得到gain)
	public static int G_NPD_DROP = 1;//npc掉落
	public static int G_SHOP = 2;//商店购买
	public static int G_AUCTION = 3;//拍卖场购买
	public static int G_TRADE = 4;//从交易中得到
	public static int G_EMAIL = 5;//从邮件中得到
	public static int G_SYSTEM = 6;//系统发放
	public static int G_STORAGE = 7;//从仓库取出
	public static int G_EXCHANGE = 8;//兑换得到
	public static int G_UPGRADE = 9;//升级得到
	public static int G_TASK = 10;//任务中得到
	public static int G_BOX_DROP = 11;//宝箱掉落
	public static int G_MALL = 12;//商城中获得
	
	//************移除物品途径(R_表示remove)
	public static int R_STORAGE = 101;//放入仓库
	public static int R_USE = 102;//使用
	public static int R_DROP = 103;//丢弃
	public static int R_DEAD = 104;//死亡掉落
	public static int R_AUCTION = 105;//从拍卖场卖出
	public static int R_SHOP = 106;//卖给商店
	public static int R_TRADE = 107;//从交易中移除
	public static int R_EXCHANGE = 108;//兑换消耗
	public static int R_MATERIAL_CONSUME = 109;//材料消耗
	public static int R_TASK = 110;//任务中消耗
	
	public static String PROP_TYPE = null;//需要监控的道具类型
	static
	{
		StringBuffer sb = new StringBuffer();
		sb.append(",");
		sb.append(3).append(",").append(6).append(",");
		sb.append(22).append(",").append(23).append(",").append(29).append(",");
		sb.append(32).append(",").append(34).append(",");
		sb.append(36).append(",").append(38).append(",").append(39).append(",");
		sb.append(40).append(",").append(41).append(",").append(44).append(",").append(47).append(",");
		sb.append(50).append(",").append(53).append(",").append(54).append(",").append(57).append(",").append(58).append(",").append(59).append(",");
		sb.append(70).append(",");
		sb.append(80).append(",").append(86).append(",").append(87).append(",").append(88).append(",").append(89).append(",");
		sb.append(91).append(",").append(92).append(",").append(93).append(",").append(94).append(",").append(95).append(",").append(96).append(",").append(97).append(",").append(98).append(",");
		PROP_TYPE = sb.toString();
	}
	

	//*******************sql常量
	private static String EQUIP_SQL = "insert into equip_log values ";
	private static String PROP_SQL = "insert into prop_log values ";
	
	//*******************日志文件
	private static String LOG_FILE_NAME="game.log";//日志文件名
	public static long SLEEP_TIME = 1000;//执行日志文件的时间间隔,1秒
	private static int BUFF_SIZE = 2;//缓存区大小（表示每500条插入数据）
	
	//*************************
	private static GameLogManager gameLogManager = new GameLogManager();
	
	private BufferedWriter writer = null;//文件输出
	private int fileIndex = 0;//当前文件索引
	private int cur_buff_size = 0;//当前缓存数量
	
	private GameLogManager(){};
	
	public static GameLogManager getInstance()
	{
		return gameLogManager;
	}
	
	
	private BufferedWriter getWriter()
	{
		if( writer==null )
		try
		{
			String file_name = LOG_FILE_NAME+fileIndex;
			File file = new File(file_name);
			writer = new BufferedWriter(new FileWriter(file,true));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return writer;
	}
	
	/**
	 * 道具操作日志
	 */
	public void propLog(int p_pk,PropVO prop,int prop_num,int operate_type)
	{
		if( operate_type==NO_LOG || p_pk<=0 || prop==null )
		{
			return;
		}
		if( isLogProp(prop.getPropClass()) )
		{

			StringBuffer sql = new StringBuffer();
			sql.append(PROP_SQL);
			sql.append("(");
			sql.append(System.currentTimeMillis());
			sql.append(",").append(p_pk);
			sql.append(",").append(prop.getPropID());
			sql.append(",").append(prop_num);
			sql.append(",").append(operate_type);
			sql.append(",now())");
			
			this.saveLog(sql.toString());
		}
	}
	
	private boolean isLogProp( int prop_type )
	{
		String prop_type_str = ","+prop_type+",";
		if( PROP_TYPE.indexOf(prop_type_str)!=-1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 装备操作日志
	 */
	public void equipLog(int p_pk,PlayerEquipVO equip,int operate_type)
	{
		if( operate_type==NO_LOG || p_pk<=0 || equip==null )
		{
			return;
		}
		
		//体验装备不监控
		if( equip.getGameEquip().getEndure()==9999 )
		{
			return;
		}
		
		/**
		 * 30级以上的绿装
			绑定装备（系统设置的绑定属性）
		 */
		//if( equip.getGameEquip().getIsBind()==1 || (equip.getWLevel()>=30 && equip.getWQuality()>=Equip.Q_LIANGHAO ) )
		{
			StringBuffer sql = new StringBuffer();
			sql.append(EQUIP_SQL);
			sql.append("(");
			sql.append(System.currentTimeMillis());
			sql.append(",").append(p_pk);
			sql.append(",").append(equip.getEquipId());
			sql.append(",").append(operate_type);
			sql.append(",now())");
			
			this.saveLog(sql.toString());
		}
	}
	
	/**
	 * 保存日志sql
	 * @param sql
	 */
	private void saveLog(String sql )
	{
		if( StringUtils.isEmpty(sql)==false)
		{
			try
			{
				saveLogFile(sql);
			}
			catch (IOException e)
			{
				DataErrorLog.debugLogic("GameLogManager.equipLog日志文件保存错误");
				//如果保存文件出错，则直接记录日志
				GameLogDao.getInstance().incertBySql(sql.toString());
			}
		}
	}
	
	/**
	 * 更新缓存区大小
	 */
	private void addCurBuffSize()
	{
		cur_buff_size++;
		if( cur_buff_size>=BUFF_SIZE) 
		{
			cur_buff_size=0;
			fileIndex++;
			String file_name = LOG_FILE_NAME+fileIndex;
			File file = new File(file_name);
			try
			{
				BufferedWriter new_writer = new BufferedWriter(new FileWriter(file,true));
				writer.close();
				writer = new_writer;
				
				//异步处理之前的文件
				String old_file_name = LOG_FILE_NAME+(fileIndex-1);//老的文件名
				GameLogThread game_log_thread = new GameLogThread(old_file_name);
				game_log_thread.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				DataErrorLog.debugLogic("创建文件错误,文件名："+file_name);
			}
		}
	}
	
	/**
	 * 保存到日志文件
	 * @throws IOException 
	 */
	public synchronized void saveLogFile(String sql) throws IOException
	{
		BufferedWriter writer = this.getWriter();
		writer.write(sql);
		writer.newLine();
		writer.flush();
		addCurBuffSize();//增加当前缓冲区数量
	}
}
