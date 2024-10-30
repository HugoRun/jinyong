package com.ls.ben.vo.info.npc;

import java.util.Date;

import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.pub.util.DateUtil;
import com.ls.pub.util.MathUtil;

/**
 * ����:
 * @author ��˧
 * 12:29:11 AM
 */
public class NpcrefurbishVO {
	/**
	 * id
	 */
	private int id;
	/**����id*/
	private int npcId;
	/**�ص�id*/
	private int sceneId;
	
	/** ˢ�������ַ�����Χ��:2,4*/
	private String number;
	
	/**����õ�ˢ������*/
	private int randomNum;
	
	
	/**������������	0��ʾ�˵�ˢ�³���npc����������ң�1��ʾ�˵�ˢ�³���npc�����������*/
	private int attackswitch;
	/**ˢ�¸���	�˵�ÿ������ˢ�³��ֵļ��ʣ���10000Ϊ��ĸ������ֵ��ʾ����*/
	private int probability;
	
	/**ˢ��ʱ��1	��ĳ��ʱ�䵽��һ��ʱ��֮�����  ��ʼʱ��*/
	private String timeKs="";
	 /**ˢ��ʱ��1	��ĳ��ʱ�䵽��һ��ʱ��֮�����  ����ʱ��*/
	private String timeJs="";
	/**ˢ��ʱ��2	ÿ���ĳ��ʱ�䵽��һ��ʱ�����*/
	private String dayTimeKs="";
	/**ˢ��ʱ��2	ÿ���ĳ��ʱ�䵽��һ��ʱ�����*/
	private String dayTimeJs="";
	/**
	 * ˢ��ʱ��(���ڿ���)
	 */
	private String weekStr;
	
	
	 /**���һ������ʱ��*/
	private Date deadTime;
	
	/**�Ƿ�������1��ʾ�ǣ�0��ʾû��*/
	private int isDead;
	
	/**��ʾnpc�Ƿ���boss��0��ʾ���ǣ�1��ʾ��*/
	private int isBoss;
	
	/**
	 * �õ��´�ˢ�¸�boss�֣���ˢ��ʱ����Ƶ�npc����ˢ����ʾ
	 * @return
	 */
	public String getBossRefHint()
	{
		StringBuffer result = new StringBuffer();;
		int refurbish_time = 0;
		NpcVO npc = getNpc();
		refurbish_time = npc.getNpcRefurbishTime();//ˢ��ʱ����
		//��ˢ��ʱ����ʱ�仹û��
		if( this.getIsBoss()==1&&refurbish_time>0 && !DateUtil.isOverdue(this.getDeadTime(),refurbish_time * 60))
		{
			int timeDifference = DateUtil.getTimeDifference(this.getDeadTime(), (long) refurbish_time * 60000);
			result.append("��").append(npc.getNpcName()).append("���ֻ�ʣ").append(timeDifference).append("����<br/>");
		}
		return result.toString();
	}
	
	/**
	 * �õ�npc��Ϣ
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
