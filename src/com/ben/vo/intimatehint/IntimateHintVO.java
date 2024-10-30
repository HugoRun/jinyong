/**
 * 
 */
package com.ben.vo.intimatehint;

/**
 * @author 侯浩军
 * 小贴士
 * 10:10:13 AM
 */
public class IntimateHintVO
{
	/** 玩家好友id */
	private int hPk;
	/** 小贴士的标题 */
	private String hHint;
	/** 小贴士的内容 */
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
