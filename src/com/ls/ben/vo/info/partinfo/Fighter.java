package com.ls.ben.vo.info.partinfo;

import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;

/**
 * ����:ս��ʵ��
 * @author ��˧
 * 1:18:55 AM
 */
public class Fighter extends PartInfoVO {
	
	private int playerInjure;
	private int petInjure;
	private String injureDisplay;
	private boolean isDead;
	private int expendMP;
	/** ��Ʒ����˵�� */
	private String dropDisplay;
	
	/**
	 * ��������:�����ܷ���ʱ��Ϊ�������֣�������ʱΪ���ܲ���ʹ�õ���ʾ
	 */
	private String skillDisplay="";
	
	private String skillNoUseDisplay="";
	
	private long dropExp;
	
	/** �Ƿ�����������  */
	private int deadProp;
	
	/** �Ƿ��ڲ���������Чʱ����  */
	private boolean notDropExp;

	
	/**���＼������*/
	private String petSkillDisplay;
	
	/**
	 * ��������
	 */
	private StringBuffer killDisplay=new StringBuffer();

	private String task_display = null;//������ʾ
	
	/**��ҵ�ǰʹ�õļ���*/
	private PlayerSkillVO skill = null;
	
	/**ս������*/
	private PetInfoVO pet = null;
	
	/**������������*/
	private String appendAttributeDescribe = null; 
	
	/**���＼���˺���ʾ����*/
	private String petInjureOut;
	/**��ѧ�ж�*/
	private String juexuedisplay;
	/**��ѧ�˺�*/
	private String juexueInjure;
	
	public String getPetInjureOut()
	{
		return petInjureOut;
	}
	public void setPetInjureOut(String petInjureOut)
	{
		this.petInjureOut = petInjureOut;
	}
	public String getTask_display()
	{
		return task_display;
	}
	public void setTask_display(String task_display)
	{
		this.task_display = task_display;
	}
	public String getSkillDisplay() {
		return skillDisplay;
	}
	public void setSkillDisplay(String skillDisplay) {
		this.skillDisplay = skillDisplay;
	}
	
	public PlayerSkillVO getSkill() {
		return skill;
	}
	public void setSkill(PlayerSkillVO skill) {
		this.skill = skill;
	}
	public long getDropExp() {
		return dropExp;
	}
	public void setDropExp(long dropExp) {
		this.dropExp = dropExp;
	}

	public int getPlayerInjure() {
		return playerInjure;
	}
	public void setPlayerInjure(int playerInjure) {
		
		int currentHP=getPHp()-playerInjure; 
		if( currentHP<= 0 )
		{
			setPHp(0);
			isDead = true;
		}
		else
		{
			setPHp(currentHP);
		}
		
		this.playerInjure = playerInjure;
	}
	public int getExpendMP() {
		return expendMP;
	}
	public void setExpendMP(int expendMP) {
		int currentMP = getPMp()-expendMP;
		if( currentMP<=0 )
		{
			setPMp(0);
		}
		else
		{
			setPMp(currentMP);
		}
		
		this.expendMP = expendMP;
	}
	public boolean isDead() {
		return isDead;
	}
	public int getPetInjure() {
		return petInjure;
	}
	public void setPetInjure(int petInjure) {
		
		int currentHP=getPHp()-petInjure; 
		if( currentHP<= 0 )
		{
			setPHp(0);
			isDead = true;
		}
		else
		{
			setPHp(currentHP);
		}
		
		
		this.petInjure = petInjure;
	}
	public PetInfoVO getPet() {
		return pet;
	}
	public void setPet(PetInfoVO pet) {
		this.pet = pet;
	}
	public String getPetSkillDisplay() {
		return petSkillDisplay;
	}
	public void setPetSkillDisplay(String petSkillDisplay) {
		this.petSkillDisplay = petSkillDisplay;
	}
	public String getSkillNoUseDisplay() {
		return skillNoUseDisplay;
	}
	public void setSkillNoUseDisplay(String skillNoUseDisplay) {
		this.skillNoUseDisplay = skillNoUseDisplay;
	}
	public String getInjureDisplay() {
		return injureDisplay;
	}
	public void setInjureDisplay(String injureDisplay) {
		this.injureDisplay = injureDisplay;
	}
	public String getKillDisplay() {
		return killDisplay.toString();
	}
	/**
	 * �����������
	 * @param killDisplay
	 */
	public StringBuffer appendKillDisplay(String killDisplay) {
		return this.killDisplay.append(killDisplay);
	}
	public String getAppendAttributeDescribe()
	{
		return appendAttributeDescribe;
	}
	public void setAppendAttributeDescribe(String appendAttributeDescribe)
	{
		this.appendAttributeDescribe = appendAttributeDescribe;
	}
	public String getDropDisplay()
	{
		return dropDisplay;
	}
	public void setDropDisplay(String dropDisplay)
	{
		this.dropDisplay = dropDisplay;
	}
	public int getDeadProp()
	{
		return deadProp;
	}
	public void setDeadProp(int deadProp)
	{
		this.deadProp = deadProp;
	}
	public void setDead(boolean isDead)
	{
		this.isDead = isDead;
	}
	
	public void setNotDropExp(boolean notDropExp)
	{
		this.notDropExp = notDropExp;
	}
	public boolean isNotDropExp()
	{
		return notDropExp;
	}
	public String getJuexuedisplay()
	{
		return juexuedisplay;
	}
	public void setJuexuedisplay(String juexuedisplay)
	{
		this.juexuedisplay = juexuedisplay;
	}
	public String getJuexueInjure()
	{
		return juexueInjure;
	}
	public void setJuexueInjure(String juexueInjure)
	{
		this.juexueInjure = juexueInjure;
	}

}
