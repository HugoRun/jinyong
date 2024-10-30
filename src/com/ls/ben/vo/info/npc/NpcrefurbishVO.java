package com.ls.ben.vo.info.npc;

import java.util.Date;

import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;

/**
 * 功能:
 * @author 刘帅
 * 12:29:11 AM
 */
public class NpcrefurbishVO {
	/**
	 * id
	 */
	private int id;
	/**怪物id*/
	private int npcId;
	/**地点id*/
	private int sceneId;
	
	/** 刷新数量字符串范围如:2,4*/
	private String number;
	
	/**随机得到刷新数量*/
	private int randomNum;
	
	
	/**主动攻击开关	0表示此点刷新出的npc被动攻击玩家，1表示此点刷新出的npc主动攻击玩家*/
	private int attackswitch;
	/**刷新概率	此点每个怪物刷新出现的几率，以10000为分母，整数值表示分子*/
	private int probability;
	
	/**刷新时间1	从某个时间到另一个时间之间出现  开始时间*/
	private String timeKs="";
	 /**刷新时间1	从某个时间到另一个时间之间出现  结束时间*/
	private String timeJs="";
	/**刷新时间2	每天的某个时间到另一个时间出现*/
	private String dayTimeKs="";
	/**刷新时间2	每天的某个时间到另一个时间出现*/
	private String dayTimeJs="";
	/**
	 * 刷新时间(星期控制)
	 */
	private String weekStr;
	
	
	 /**最后一次死亡时间*/
	private Date deadTime;
	
	/**是否已死，1表示是，0表示没死*/
	private int isDead;
	
	/**表示npc是否是boss，0表示不是，1表示是*/
	private int isBoss;
	
	/**
	 * 得到下次刷新该boss怪（有刷新时间控制的npc）的刷新提示
	 * @return
	 */
	public String getBossRefHint()
	{
		StringBuffer result = new StringBuffer();;
		int refurbish_time = 0;
		NpcVO npc = getNpc();
		refurbish_time = npc.getNpcRefurbishTime();//刷新时间间隔
		//有刷新时间且时间还没到
		if( this.getIsBoss()==1&&refurbish_time>0 && !DateUtil.isOverdue(this.getDeadTime(),refurbish_time * 60))
		{
			int timeDifference = DateUtil.getTimeDifference(this.getDeadTime(), (long) refurbish_time * 60000);
			result.append("距").append(npc.getNpcName()).append("出现还剩").append(timeDifference).append("分钟<br/>");
		}
		return result.toString();
	}
	
	/**
	 * 得到npc信息
	 * @return
	 */
	public NpcVO getNpc()
	{
		return NpcCache.getById(npcId);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNpcId() {
		return npcId;
	}
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
		randomNum = MathUtil.getValueByStr(number);
	}
	public int getAttackswitch() {
		return attackswitch;
	}
	public void setAttackswitch(int attackswitch) {
		this.attackswitch = attackswitch;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public String getTimeKs() {
		return timeKs;
	}
	public void setTimeKs(String timeKs) {
		if( timeKs==null )
		{
			timeKs = "";
		}
		this.timeKs = timeKs;
	}
	public String getTimeJs() {
		return timeJs;
	}
	public void setTimeJs(String timeJs) {
		if( timeJs==null )
		{
			timeJs = "";
		}
		this.timeJs = timeJs;
	}
	public String getDayTimeKs() {
		return dayTimeKs;
	}
	public void setDayTimeKs(String dayTimeKs) {
		if( dayTimeKs==null )
		{
			dayTimeKs = "";
		}
		this.dayTimeKs = dayTimeKs;
	}
	public String getDayTimeJs() {
		return dayTimeJs;
	}
	public void setDayTimeJs(String dayTimeJs) {
		if( dayTimeJs==null )
		{
			dayTimeJs = "";
		}
		this.dayTimeJs = dayTimeJs;
	}
	public int getRandomNum() {
		return randomNum;
	}
	public Date getDeadTime()
	{
		return deadTime;
	}
	public void setDeadTime(Date deadTime)
	{
		this.deadTime = deadTime;
	}
	public int getIsDead()
	{
		return isDead;
	}
	public void setIsDead(int isDead)
	{
		this.isDead = isDead;
	}
	public int getIsBoss()
	{
		return isBoss;
	}
	public void setIsBoss(int isBoss)
	{
		this.isBoss = isBoss;
	}

	public void setWeekStr(String weekStr)
	{
		this.weekStr = weekStr;
	}

	public String getWeekStr()
	{
		return weekStr;
	}


}
