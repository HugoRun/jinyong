/**
 * 
 */
package com.ben.vo.intimatehint;

/**
 * @author ��ƾ�
 * С��ʿ
 * 10:10:13 AM
 */
public class IntimateHintVO
{
	/** ��Һ���id */
	private int hPk;
	/** С��ʿ�ı��� */
	private String hHint;
	/** С��ʿ������ */
	private String hContent;

	public int getHPk()
	{
		return hPk;
	}

	public void setHPk(int pk)
	{
		hPk = pk;
	}

	public String getHHint()
	{
		return hHint;
	}

	public void setHHint(String hint)
	{
		hHint = hint;
	}

	public String getHContent()
	{
		return hContent;
	}

	public void setHContent(String content)
	{
		hContent = content;
	}
}
