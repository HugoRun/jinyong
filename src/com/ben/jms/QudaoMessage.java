package com.ben.jms;

import java.io.Serializable;
import java.util.Date;

import com.ben.shitu.model.DateUtil;

public class QudaoMessage implements Serializable
{
	private static final long serialVersionUID = 1111112L;
	private String super_qudao;
	private String qudao;
	private String fenqu;
	private String record_time;
	private int now_peo = 0;
	private int max_peo = 0;
    public QudaoMessage(){
    	super();
    	this.record_time = DateUtil.getDate(new Date());
    }
	public String getSuper_qudao()
	{
		return super_qudao;
	}

	public void setSuper_qudao(String super_qudao)
	{
		this.super_qudao = super_qudao;
	}

	public String getQudao()
	{
		return qudao;
	}

	public void setQudao(String qudao)
	{
		this.qudao = qudao;
	}

	public String getFenqu()
	{
		return fenqu;
	}

	public void setFenqu(String fenqu)
	{
		this.fenqu = fenqu;
	}

	public String getRecord_time()
	{
		return record_time;
	}

	public void setRecord_time(String record_time)
	{
		this.record_time = record_time;
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
}
