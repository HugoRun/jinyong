package com.pm.constant;

import com.pm.dao.statistics.PlayerOnlineNumRecordDao;
import com.pub.db.mysql.SqlData;

/**
 * �û���������ͳ��
 * @author Administrator
 *
 */
public class PlayerOnlineNumRecord
{
	SqlData con;
	public PlayerOnlineNumRecord(){}
	
	
	/**
	 * ����������������
	 */
	public synchronized void addOnlineNum(){
		/*try{
//    		File file=new File(PlayerOnlineNumRecord.class.getResource("/aaa.txt").getFile());
//    		BufferedReader reader=new BufferedReader(new FileReader(file));
//    		playerOnlineNum=Integer.parseInt(reader.readLine());
//    		playerOnlineNum++;
//    		//System.out.println("��ǰ��������Ϊ:"+playerOnlineNum+"��");
//    		reader.close();
//    		BufferedWriter writer=new BufferedWriter(new FileWriter(file));
//    		writer.write(playerOnlineNum+"");
//    		writer.flush();
//    		writer.close();
			con=new SqlData();
			String sql="update t_online set onlinecount=onlinecount+1";
			con.update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			con.close();
		}*/
	}
	
	/**
	 * ����������������
	 * �˷���Ӧ����ֻ�ڴ�����ɫʱ����loginpage��n2�����б����á�
	 */
	public void addOnlineNumElse(){
		PlayerOnlineNumRecordDao playerOnlineNumRecordDao = new PlayerOnlineNumRecordDao();
		playerOnlineNumRecordDao.addOnlineNumElse();
	}
	
	/**
	 * ���ٵ�ǰ��������һ
	 */
	public  void reduceOnlineNum(){
		
		PlayerOnlineNumRecordDao playerOnlineNumRecordDao = new PlayerOnlineNumRecordDao();
		
		int i = getPlayerOnlineNum();
		if(i > 0) {
			playerOnlineNumRecordDao.reduceOnlineNum();
		}
		
	}
	/**
	 * ��õ�ǰ��������
	 * @return
	 */
	public int getPlayerOnlineNum(){
		PlayerOnlineNumRecordDao playerOnlineNumRecordDao = new PlayerOnlineNumRecordDao();
		return playerOnlineNumRecordDao.getPlayerOnlineNum();
		
	}
	
	
	/** ������������ʱ, ����ǰ������������. */
	public void setOnlineNumToZero(){
		try{
			con=new SqlData();
			String sql="update t_online set onlinecount = 0";
			con.update(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			con.close();
		}
	}
}
