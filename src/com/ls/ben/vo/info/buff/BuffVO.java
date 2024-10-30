package com.ls.ben.vo.info.buff;

import org.apache.log4j.Logger;

import com.ls.pub.util.StringUtil;

/**
 * ����:buff
 * @author ��˧
 * 10:14:53 AM
 */
public class BuffVO {
	
	Logger logger = Logger.getLogger(BuffVO.class);
	
	 /**id*/
	private int buffId;	
	/**buff����*/
    private int buffType;  
    /**����*/
	private String buffName;
	/** buff����*/
	private String buffDisplay;
	/**����ʱ�䣬��λΪ��*/
	private int buffTime;
	/**�����غ�*/
	private int buffBout;
	/**buffЧ��ֵ*/
	private int buffEffectValue;
	/**�Ƿ�غϵ���,0��ʾ���ܣ�1��ʾ��*/
	private int buffBoutOverlap;
	/**�Ƿ�ʱ�����,0��ʾ���ܣ�1��ʾ��*/
	private int buffTimeOverlap;
	/**ʹ�÷�ʽ��1��ʾ���棬2��ʾ����*/
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
