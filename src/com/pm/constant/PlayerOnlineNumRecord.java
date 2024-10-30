package com.pm.constant;

import com.pm.dao.statistics.PlayerOnlineNumRecordDao;
import com.pub.db.mysql.SqlData;

/**
 * 用户在线人数统计
 * @author Administrator
 *
 */
public class PlayerOnlineNumRecord
{
	SqlData con;
	public PlayerOnlineNumRecord(){}
	
	
	/**
	 * 增加在线人数数量
	 */
	public synchronized void addOnlineNum(){
		/*try{
//    		File file=new File(PlayerOnlineNumRecord.class.getResource("/aaa.txt").getFile());
//    		BufferedReader reader=new BufferedReader(new FileReader(file));
//    		playerOnlineNum=Integer.parseInt(reader.readLine());
//    		playerOnlineNum++;
//    		//System.out.println("当前在线人数为:"+playerOnlineNum+"人");
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
	 * 增加在线人数数量
	 * 此方法应在且只在创建角色时和在loginpage的n2方法中被调用。
	 */
	public void addOnlineNumElse(){
		PlayerOnlineNumRecordDao playerOnlineNumRecordDao = new PlayerOnlineNumRecordDao();
		playerOnlineNumRecordDao.addOnlineNumElse();
	}
	
	/**
	 * 减少当前在线人数一
	 */
	public  void reduceOnlineNum(){
		
		PlayerOnlineNumRecordDao playerOnlineNumRecordDao = new PlayerOnlineNumRecordDao();
		
		int i = getPlayerOnlineNum();
		if(i > 0) {
			playerOnlineNumRecordDao.reduceOnlineNum();
		}
		
	}
	/**
	 * 获得当前在线人数
	 * @return
	 */
	public int getPlayerOnlineNum(){
		PlayerOnlineNumRecordDao playerOnlineNumRecordDao = new PlayerOnlineNumRecordDao();
		return playerOnlineNumRecordDao.getPlayerOnlineNum();
		
	}
	
	
	/** 当服务器启动时, 将当前在线人数置零. */
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
