package com.ls.ben.vo.menu;

/**
 * 功能:
 * @author 刘帅 
 *  12:46:49 PM
 */
public class OperateMenuVO implements Cloneable{
	/** id */
	private int id;
	/** 菜单名称 */
	private String menuName;
	/** 菜单类型 */
	private int menuType;
	/** 菜单图片 */
	private String menuImg;
	/** 菜单地点id */
	private int menuMap;
	
	/** 特殊字节1 */
	private String menuOperate1;
	/** 特殊字节2 */
	private String menuOperate2;
	/** 特殊字节3 */
	private String menuOperate3;
	/**特殊字节4*/
	private int menuOperate4;
	/** 种族限制 */
	private int menuCamp;
	/** 对话 点击后出现的一句话 */
	private String menuDialog;
	
	/** 刷新时间1 从某个时间到另一个时间之间出现 开始时间 表现形式是 2008-8-9 16:22:00 */
	private String menuTimeBegin;
	/** 刷新时间1 从某个时间到另一个时间之间出现 结束时间 */
	private String menuTimeEnd;
	/** 刷新时间2 每天的某个时间到另一个时间出现 表现形式是 16:00:9 */
	private String menuDayBegin;
	/** 刷新时间2 每天的某个时间到另一个时间出现 */
	/**
	 * 刷新时间(星期控制)
	 */
	private String weekStr;
	private String menuDayEnd;
	/**父菜单id，如果父菜单id为0，则表示是顶级菜单*/
	private int menuFatherId;
	/**同一级菜单由小到大排序显示*/
	private int menuOrder;
	/**菜单npc所拥有用户可以领的任务字符串,形式如:2,4,5*/
	private String menuTasksId;
	
	/**标识是否是任务菜单,0表示普通菜单，1表示任务菜单*/
	private int menuTaskFlag;
	
	/**是否是中间点值*/
	private int taskPoint = 0;
	
	
	public OperateMenuVO clone()
	{ 
		OperateMenuVO new_menu = null;
		try
		{ 
			new_menu = (OperateMenuVO)super.clone(); 
		}catch(CloneNotSupportedException e)
		{ 
			e.printStackTrace(); 
		} 
		return new_menu; 
	} 

	
	public int getTaskPoint()
	{
		return taskPoint;
	}
	public void setTaskPoint(int taskPoint)
	{
		this.taskPoint = taskPoint;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public int getMenuType()
	{
		return menuType;
	}
	public void setMenuType(int menuType)
	{
		this.menuType = menuType;
	}
	public String getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	public int getMenuMap() {
		return menuMap;
	}
	public void setMenuMap(int menuMap) {
		this.menuMap = menuMap;
	}
	public String getMenuOperate1() {
		if( menuOperate1==null )
		{
			return "";
		}
		return menuOperate1;
	}
	public void setMenuOperate1(String menuOperate1) {
		this.menuOperate1 = menuOperate1;
	}
	public String getMenuOperate2() {
		if( menuOperate2==null )
		{
			return "";
		}
		return menuOperate2;
	}
	public void setMenuOperate2(String menuOperate2) {
		this.menuOperate2 = menuOperate2;
	}
	public String getMenuOperate3() {
		if( menuOperate3==null )
		{
			return "";
		}
		return menuOperate3;
	}
	public void setMenuOperate3(String menuOperate3) {
		this.menuOperate3 = menuOperate3;
	}
	public int getMenuCamp() {
		return menuCamp;
	}
	public void setMenuCamp(int menuCamp) {
		this.menuCamp = menuCamp;
	}
	public String getMenuDialog() {
		return menuDialog;
	}
	public void setMenuDialog(String menuDialog) {
		this.menuDialog = menuDialog;
	}
	public String getMenuTimeBegin() {
		if( menuTimeBegin==null )
		{
			menuTimeBegin = "";
		}
		return menuTimeBegin;
	}
	public void setMenuTimeBegin(String menuTimeBegin) {
		this.menuTimeBegin = menuTimeBegin;
	}
	public String getMenuTimeEnd() {
		if( menuTimeEnd==null )
		{
			menuTimeEnd = "";
		}
		return menuTimeEnd;
	}
	public void setMenuTimeEnd(String menuTimeEnd) {
		this.menuTimeEnd = menuTimeEnd;
	}
	public String getMenuDayBegin() {
		if( menuDayBegin==null )
		{
			menuDayBegin = "";
		}
		return menuDayBegin;
	}
	public void setMenuDayBegin(String menuDayBegin) {
		this.menuDayBegin = menuDayBegin;
	}
	public String getMenuDayEnd() {
		if( menuDayEnd==null )
		{
			menuDayEnd = "";
		}
		return menuDayEnd;
	}
	public void setMenuDayEnd(String menuDayEnd) {
		this.menuDayEnd = menuDayEnd;
	}
	public int getMenuFatherId() {
		return menuFatherId;
	}
	public void setMenuFatherId(int menuFatherId) {
		this.menuFatherId = menuFatherId;
	}
	public String getMenuTasksId()
	{
		if( menuTasksId==null )
		{
			menuTasksId = "";
		}
		return menuTasksId;
	}
	public void setMenuTasksId(String menuTasksId)
	{
		this.menuTasksId = menuTasksId;
	}
	public int getMenuTaskFlag()
	{
		return menuTaskFlag;
	}
	public void setMenuTaskFlag(int menuTaskFlag)
	{
		this.menuTaskFlag = menuTaskFlag;
	}


	public int getMenuOrder()
	{
		return menuOrder;
	}


	public void setMenuOrder(int menuOrder)
	{
		this.menuOrder = menuOrder;
	}


	public int getMenuOperate4()
	{
		return menuOperate4;
	}


	public void setMenuOperate4(int menuOperate4)
	{
		this.menuOperate4 = menuOperate4;
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
