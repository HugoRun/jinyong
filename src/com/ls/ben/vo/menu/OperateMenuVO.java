package com.ls.ben.vo.menu;

/**
 * ����:
 * @author ��˧ 
 *  12:46:49 PM
 */
public class OperateMenuVO implements Cloneable{
	/** id */
	private int id;
	/** �˵����� */
	private String menuName;
	/** �˵����� */
	private int menuType;
	/** �˵�ͼƬ */
	private String menuImg;
	/** �˵��ص�id */
	private int menuMap;
	
	/** �����ֽ�1 */
	private String menuOperate1;
	/** �����ֽ�2 */
	private String menuOperate2;
	/** �����ֽ�3 */
	private String menuOperate3;
	/**�����ֽ�4*/
	private int menuOperate4;
	/** �������� */
	private int menuCamp;
	/** �Ի� �������ֵ�һ�仰 */
	private String menuDialog;
	
	/** ˢ��ʱ��1 ��ĳ��ʱ�䵽��һ��ʱ��֮����� ��ʼʱ�� ������ʽ�� 2008-8-9 16:22:00 */
	private String menuTimeBegin;
	/** ˢ��ʱ��1 ��ĳ��ʱ�䵽��һ��ʱ��֮����� ����ʱ�� */
	private String menuTimeEnd;
	/** ˢ��ʱ��2 ÿ���ĳ��ʱ�䵽��һ��ʱ����� ������ʽ�� 16:00:9 */
	private String menuDayBegin;
	/** ˢ��ʱ��2 ÿ���ĳ��ʱ�䵽��һ��ʱ����� */
	/**
	 * ˢ��ʱ��(���ڿ���)
	 */
	private String weekStr;
	private String menuDayEnd;
	/**���˵�id��������˵�idΪ0�����ʾ�Ƕ����˵�*/
	private int menuFatherId;
	/**ͬһ���˵���С����������ʾ*/
	private int menuOrder;
	/**�˵�npc��ӵ���û�������������ַ���,��ʽ��:2,4,5*/
	private String menuTasksId;
	
	/**��ʶ�Ƿ�������˵�,0��ʾ��ͨ�˵���1��ʾ����˵�*/
	private int menuTaskFlag;
	
	/**�Ƿ����м��ֵ*/
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
