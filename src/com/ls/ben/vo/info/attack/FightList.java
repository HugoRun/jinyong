package com.ls.ben.vo.info.attack;

import java.util.List;

/**
 * ����:
 * @author ��˧
 * 2:05:26 PM
 */
public class FightList {
	
	private String growDisplay=null;
	private int exp;
	private int money;
	private List<DropGoodsVO> dropGoods=null;
	private String pet_display = null;//������ʾ
	private String task_display;//������ʾ
	
	public String getTask_display()
	{
		return task_display;
	}
	public void setTask_display(String task_display)
	{
		this.task_display = task_display;
	}
	public String getPet_display() {
		return pet_display;
	}
	public void setPet_display(String pet_display) {
		this.pet_display = pet_display;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public List<DropGoodsVO> getDropGoods() {
		return dropGoods;
	}
	public void setDropGoods(List<DropGoodsVO> dropGoods) {
		this.dropGoods = dropGoods;
	}
	public String getGrowDisplay() {
		return growDisplay;
	}
	public void setGrowDisplay(String growDisplay) {
		this.growDisplay = growDisplay;
	}
	
}
