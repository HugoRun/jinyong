package com.ben.jms;

public class  QudaoDetail{
	private int now_peo = 0;
	private int max_peo = 0;
	public QudaoDetail(){
		now_peo = max_peo = 1;
	}
	
	public int getNow_peo()
	{
		return now_peo;
	}
	public void setNow_peo(int now_peo)
	{
		this.now_peo = now_peo;
	}
	public int getMax_peo()
	{
		return max_peo;
	}
	public void setMax_peo(int max_peo)
	{
		this.max_peo = max_peo;
	}
	
	public void addPeo(){
		this.now_peo++;
		if(now_peo>max_peo){
			max_peo = now_peo;
		}
		System.out.println("上线一个，现在的人数为 ： "+now_peo+" 最高人数为 : "+max_peo);
	}
	
	public void delPeo(){
		if(now_peo>0){
		this.now_peo--;
		}
		System.out.println("下线一个，现在的人数为 ： "+now_peo+" 最高人数为 : "+max_peo);
	}
}
