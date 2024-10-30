/**
 * 
 */
package com.ls.ben.vo.info.npc;

import com.ls.iface.function.Probability;

/**
 * ����:��Ӧnpcskill��
 * @author ��˧
 *
 * 7:18:18 PM
 */
public class NpcskillVO implements Probability {
	 
	/**npc����id*/
	private int id;
	
	/**��������*/
	private String npcskiName;
	
	/**���� 1Ϊbasic������2Ϊpower������3Ϊsupper����*/
	private int npcskiType;
	
	/**npc��id*/
	private int npcId;
	
	/**��������:��=1��ľ=2��ˮ=3����=4����=5��*/
	private int npcskiWx;
	
	/**�����˺�*/
	private int npcskiWxInjure;
	
	/**ʵ�ʼ����˺�������ͺ�����˺�֮��ȡֵ*/
	private int npcskiInjure;
	
	/**����˺�*/
	private int npcskiInjureXiao;
	
	/**����˺�*/
	private int npcskiInjureDa;
	
	/**���м���*/
	private int probability;

	
	
	
	public int getNpcskiInjure()
	{
		return this.npcskiInjure;
	}
	
	/**
	 * @param skiInjure    ����ͺ�����˺�֮��ȡֵ
	 * @param supperInjureMultiple   ��������
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
