package com.ben.vo.friend;

import java.util.Date;

/**
 * ����:����
 * 
 * @author ��ƾ� 9:23:19 AM
 */
public class FriendVO
{
	/** ��Һ���id */
	private int fPk;
	/** ���id */
	private int pPk;
	/** ����ID */
	private int fdPk;
	/** �������� */
	private String fdName;
	/** ����ʱ�� */
	private String createTime;
	/** �����Ƿ����� */
	private int fOnline;
	/** �������ڵ�ͼ */
	private int pMap;

	/** �������ڵ�ͼ�� */
	private String pMapName;
	private Date tim;
	/***�¼��˺����Ƿ����ߵ��ֽ�***/
	private int login_state;
	
	//���ܶ�
	private int dear;
	
	//˫����ϵ
	private int relation;
	
	//�������ֵ
	private int expShare;
	
	//��������ֵ
	private int love_dear;
	
	

	public int getLove_dear()
	{
		return love_dear;
	}

	public void setLove_dear(int love_dear)
	{
		this.love_dear = love_dear;
	}

	public int getExpShare()
	{
		return expShare;
	}

	public void setExpShare(int expShare)
	{
		this.expShare = expShare;
	}

	public int getRelation()
	{
		return relation;
	}

	public void setRelation(int relation)
	{
		this.relation = relation;
	}

	public int getDear()
	{
		return dear;
	}

	public void setDear(int dear)
	{
		this.dear = dear;
	}

	public String getPMapName()
	{
		return pMapName;
	}

	public void setPMapName(String mapName)
	{
		pMapName = mapName;
	}

	public int getPMap()
	{
		return pMap;
	}

	public void setPMap(int map)
	{
		pMap = map;
	}

	public int getFPk()
	{
		return fPk;
	}

	public void setFPk(int pk)
	{
		fPk = pk;
	}

	public int getPPk()
	{
		return pPk;
	}

	public void setPPk(int pk)
	{
		pPk = pk;
	}

	public int getFdPk()
	{
		return fdPk;
	}

	public void setFdPk(int fdPk)
	{
		this.fdPk = fdPk;
	}

	public String getFdName()
	{
		return fdName;
	}

	public void setFdName(String fdName)
	{
		this.fdName = fdName;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public int getFOnline()
	{
		return fOnline;
	}

	public void setFOnline(int online)
	{
		fOnline = online;
	}

	public Date getTim()
	{
		return tim;
	}

	public void setTim(Date tim)
	{
		this.tim = tim;
	}

	public int getLogin_state()
	{
		return login_state;
	}

	public void setLogin_state(int login_state)
	{
		this.login_state = login_state;
	}

}
