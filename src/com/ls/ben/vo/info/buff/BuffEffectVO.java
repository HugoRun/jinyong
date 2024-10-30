package com.ls.ben.vo.info.buff;

import java.util.Date;

import org.apache.log4j.Logger;


/**
 * 功能:u_buffeffect_info
 * @author buff效果 
 * 11:11:09 AM
 */
public class BuffEffectVO extends BuffVO {
	
	Logger logger = Logger.getLogger(BuffEffectVO.class);
	
	 /**id*/
	 private int bfPk; 
	 
	 
	 /**剩下的使用回合数*/
	 private int spareBout;
	 /**使用buff的时间*/
	 private Date useTime;
	 
	 
	 /**buff效果作用对象*/
	 private int  effectObject;
	 /**buff效果作用对象,1表示玩家，2表示npc*/
	 private int effectObjectType;
	 
	public int getBfPk() {
		return bfPk;
	}
	public void setBfPk(int bfPk) {
		this.bfPk = bfPk;
	}
	
	public int getEffectObject() {
		return effectObject;
	}
	public void setEffectObject(int effectObject) {
		this.effectObject = effectObject;
	}
	public int getEffectObjectType() {
		return effectObjectType;
	}
	public void setEffectObjectType(int effectObjectType) {
		this.effectObjectType = effectObjectType;
	}
	public int getSpareBout() {
		return spareBout;
	}
	public void setSpareBout(int spareBout) {
		this.spareBout = spareBout;
	}
	
	public Date getUseTime() {
		return useTime;
	}
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	
	
	@Override
	public void log()
	{
		super.log();
		logger.info("effectObject:"+effectObject);
		logger.info("effectObjectType:"+effectObjectType);
	}
}
