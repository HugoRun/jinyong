package com.ls.model.user;

/**
 * @author ls
 * ��ɫ��ؼ�����(���ֲ���Ҫ�������ݿ�ļ�����)
 */
public class RoleCounter
{
	private int killNpcNum = 0;//ɱ�����ٱ��Լ��ȼ������ͨnpc������
	
	/**
	 * ����ɱ��npc������
	 * @param addNum
	 */
	public void addKillNpcNum()
	{
		killNpcNum++;
	}

	public int getKillNpcNum()
	{
		return killNpcNum;
	}
	
}
