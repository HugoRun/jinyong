/**
 * 
 */
package com.pm.constant;

import com.ls.iface.function.Probability;

/**
 * @author Administrator
 *
 */
public class NpcGaiLv implements Probability  {

	private int id = 0;
	private int propbability = 0;
	public int getId()
	{
		// TODO Auto-generated method stub
		return id;
	}
	public int getProbability()
	{
		// TODO Auto-generated method stub
		return propbability;
	}
	public void setId(int id)
	{
		this.id = id;
		
	}
	public void setProbability(int probability)
	{
		this.propbability = probability;
		
	}
	
	
}
