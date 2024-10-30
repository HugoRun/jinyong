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
 * ��Ϸ��־������
 */
public class GameLogManager
{
	public static int NO_LOG = 0;//����Ҫ��¼
	
	//************�����Ʒ;��(G_��ʾ�õ�gain)
	public static int G_NPD_DROP = 1;//npc����
	public static int G_SHOP = 2;//�̵깺��
	public static int G_AUCTION = 3;//����������
	public static int G_TRADE = 4;//�ӽ����еõ�
	public static int G_EMAIL = 5;//���ʼ��еõ�
	public static int G_SYSTEM = 6;//ϵͳ����
	public static int G_STORAGE = 7;//�Ӳֿ�ȡ��
	public static int G_EXCHANGE = 8;//�һ��õ�
	public static int G_UPGRADE = 9;//�����õ�
	public static int G_TASK = 10;//�����еõ�
	public static int G_BOX_DROP = 11;//�������
	public static int G_MALL = 12;//�̳��л��
	
	//************�Ƴ���Ʒ;��(R_��ʾremove)
	public static int R_STORAGE = 101;//����ֿ�
	public static int R_USE = 102;//ʹ��
	public static int R_DROP = 103;//����
	public static int R_DEAD = 104;//��������
	public static int R_AUCTION = 105;//������������
	public static int R_SHOP = 106;//�����̵�
	public static int R_TRADE = 107;//�ӽ������Ƴ�
	public static int R_EXCHANGE = 108;//�һ�����
	public static int R_MATERIAL_CONSUME = 109;//��������
	public static int R_TASK = 110;//����������
	
	public static String PROP_TYPE = null;//��Ҫ��صĵ�������
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
	

	//*******************sql����
	private static String EQUIP_SQL = "insert into equip_log values ";
	private static String PROP_SQL = "insert into prop_log values ";
	
	//*******************��־�ļ�
	private static String LOG_FILE_NAME="game.log";//��־�ļ���
	public static long SLEEP_TIME = 1000;//ִ����־�ļ���ʱ����,1��
	private static int BUFF_SIZE = 2;//��������С����ʾÿ500���������ݣ�
	
	//*************************
	private static GameLogManager gameLogManager = new GameLogManager();
	
	private BufferedWriter writer = null;//�ļ����
	private int fileIndex = 0;//��ǰ�ļ�����
	private int cur_buff_size = 0;//��ǰ��������
	
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
	 * ���߲�����־
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
	 * װ��������־
	 */
	public void equipLog(int p_pk,PlayerEquipVO equip,int operate_type)
	{
		if( operate_type==NO_LOG || p_pk<=0 || equip==null )
		{
			return;
		}
		
		//����װ�������
		if( equip.getGameEquip().getEndure()==9999 )
		{
			return;
		}
		
		/**
		 * 30�����ϵ���װ
			��װ����ϵͳ���õİ����ԣ�
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
	 * ������־sql
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
				DataErrorLog.debugLogic("GameLogManager.equipLog��־�ļ��������");
				//��������ļ�������ֱ�Ӽ�¼��־
				GameLogDao.getInstance().incertBySql(sql.toString());
			}
		}
	}
	
	/**
	 * ���»�������С
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
				
				//�첽����֮ǰ���ļ�
				String old_file_name = LOG_FILE_NAME+(fileIndex-1);//�ϵ��ļ���
				GameLogThread game_log_thread = new GameLogThread(old_file_name);
				game_log_thread.start();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				DataErrorLog.debugLogic("�����ļ�����,�ļ�����"+file_name);
			}
		}
	}
	
	/**
	 * ���浽��־�ļ�
	 * @throws IOException 
	 */
	public synchronized void saveLogFile(String sql) throws IOException
	{
		BufferedWriter writer = this.getWriter();
		writer.write(sql);
		writer.newLine();
		writer.flush();
		addCurBuffSize();//���ӵ�ǰ����������
	}
}
