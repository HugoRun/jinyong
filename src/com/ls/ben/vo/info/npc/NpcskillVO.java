/**
 * 
 */
package com.ls.ben.vo.info.npc;

import com.ls.iface.function.Probability;

/**
 * 功能:对应npcskill表
 * @author 刘帅
 *
 * 7:18:18 PM
 */
public class NpcskillVO implements Probability {
	 
	/**npc技能id*/
	private int id;
	
	/**技能名称*/
	private String npcskiName;
	
	/**类型 1为basic攻击，2为power攻击，3为supper攻击*/
	private int npcskiType;
	
	/**npc的id*/
	private int npcId;
	
	/**技能五行:金=1，木=2，水=3，火=4，土=5。*/
	private int npcskiWx;
	
	/**五行伤害*/
	private int npcskiWxInjure;
	
	/**实际技能伤害，在最低和最高伤害之间取值*/
	private int npcskiInjure;
	
	/**最低伤害*/
	private int npcskiInjureXiao;
	
	/**最高伤害*/
	private int npcskiInjureDa;
	
	/**出招几率*/
	private int probability;

	
	
	
	public int getNpcskiInjure()
	{
		return this.npcskiInjure;
	}
	
	/**
	 * @param skiInjure    在最低和最高伤害之间取值
	 * @param supperInjureMultiple   暴击倍数
	 */
	public void setNpcskiInjure( int skiInjure,double supperInjureMultiple )
	{
		this.npcskiInjure = (int) (skiInjure*supperInjureMultiple);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int npcskiId) {
		this.id = npcskiId;
	}




	

	public String getNpcskiName() {
		return npcskiName;
	}

	public void setNpcskiName(String npcskiName) {
		this.npcskiName = npcskiName;
	}

	public int getNpcskiType() {
		return npcskiType;
	}

	public void setNpcskiType(int npcskiType) {
		this.npcskiType = npcskiType;
	}

	public int getNpcId() {
		return npcId;
	}

	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}

	public int getNpcskiWx() {
		return npcskiWx;
	}

	public void setNpcskiWx(int npcskiWx) {
		this.npcskiWx = npcskiWx;
	}

	public int getNpcskiWxInjure() {
		return npcskiWxInjure;
	}

	public void setNpcskiWxInjure(int npcskiWxInjure) {
		this.npcskiWxInjure = npcskiWxInjure;
	}

	public int getNpcskiInjureXiao() {
		return npcskiInjureXiao;
	}

	public void setNpcskiInjureXiao(int npcskiInjureXiao) {
		this.npcskiInjureXiao = npcskiInjureXiao;
	}

	public int getNpcskiInjureDa() {
		return npcskiInjureDa;
	}

	public void setNpcskiInjureDa(int npcskiInjureDa) {
		this.npcskiInjureDa = npcskiInjureDa;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int npcskiProbability) {
		this.probability = npcskiProbability;
	}

}
