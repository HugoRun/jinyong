package com.ls.ben.vo.info.buff;

import org.apache.log4j.Logger;

import com.ls.pub.util.StringUtil;

/**
 * 功能:buff
 * @author 刘帅
 * 10:14:53 AM
 */
public class BuffVO {
	
	Logger logger = Logger.getLogger(BuffVO.class);
	
	 /**id*/
	private int buffId;	
	/**buff类型*/
    private int buffType;  
    /**名称*/
	private String buffName;
	/** buff描述*/
	private String buffDisplay;
	/**持续时间，单位为秒*/
	private int buffTime;
	/**持续回合*/
	private int buffBout;
	/**buff效果值*/
	private int buffEffectValue;
	/**是否回合叠加,0表示不能，1表示能*/
	private int buffBoutOverlap;
	/**是否时间叠加,0表示不能，1表示能*/
	private int buffTimeOverlap;
	/**使用方式，1表示增益，2表示减益*/
	private int buffUseMode;  
	
	
	public int getBuffId() {
		return buffId;
	}
	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}
	public int getBuffType() {
		return buffType;
	}
	public void setBuffType(int buffType) {
		this.buffType = buffType;
	}
	public String getBuffName() {
		return buffName;
	}
	public void setBuffName(String buffName) {
		this.buffName = buffName;
	}
	public int getBuffTime() {
		return buffTime;
	}
	public void setBuffTime(int buffTime) {
		this.buffTime = buffTime;
	}
	public int getBuffBout() {
		return buffBout;
	}
	public void setBuffBout(int buffBout) {
		this.buffBout = buffBout;
	}
	public int getBuffEffectValue() {
		return buffEffectValue;
	}
	public void setBuffEffectValue(int buffEffectValue) {
		this.buffEffectValue = buffEffectValue;
	}
	public int getBuffBoutOverlap() {
		return buffBoutOverlap;
	}
	public void setBuffBoutOverlap(int buffBoutOverlap) {
		this.buffBoutOverlap = buffBoutOverlap;
	}
	public int getBuffTimeOverlap() {
		return buffTimeOverlap;
	}
	public void setBuffTimeOverlap(int buffTimeOverlap) {
		this.buffTimeOverlap = buffTimeOverlap;
	}
	public int getBuffUseMode() {
		return buffUseMode;
	}
	public void setBuffUseMode(int buffUseMode) {
		this.buffUseMode = buffUseMode;
	}
	public String getBuffDisplay() {
		return buffDisplay;
	}
	public void setBuffDisplay(String buffDisplay) {
		this.buffDisplay = buffDisplay;
	}
	
	public void log()
	{
		logger.info("buffId:"+buffId);
		logger.info("buffName:"+StringUtil.isoToGBK(buffName));
		logger.info("buffDisplay:"+StringUtil.isoToGBK(buffDisplay));
		logger.info("buffType:"+buffType);
		logger.info("value:"+buffEffectValue);
		
		logger.info("useMode:"+buffUseMode);
		logger.info("buffBoutOverlap:"+buffBoutOverlap);
		logger.info("buffTimeOverlap:"+buffTimeOverlap);
		logger.info("buffBout:"+buffBout);
		logger.info("buffTime:"+buffTime);
	}
}
