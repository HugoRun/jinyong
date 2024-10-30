
package com.ls.ben.vo.goods.prop;

import com.ls.pub.config.GameConfig;


public class PropVO {
	/** ���߱� */
	private int propID;
	/** ���� */
	private String propName;

	/** ���Ͳ�ͬ��Ʒ����ʹ���в�ͬ�Ĺ��ܣ���������ա���ӹȺ������Ʒ�� */
	private int propClass;

	/** ʹ�õȼ� ʹ�ø���ƷҪ���ɫ�ȼ� */
	private String propReLevel;

	
	
	/** �� 0�ǲ��󶨣�1��ʰȡ�󶨣�2��װ���� */
	private int propBonding;
	/** �ɷ�ѵ� 1��ʾ�����ص���0��ʾ�����ص����ص��������20 */
	private int propAccumulate;
	/** ˵�� ��Ʒ���� */
	private String propDisplay;
	/** ������ ��Ʒ����ϵͳ�ļ۸� */
	private int propSell;
	/** ְҵ��������ͨ�õģ� ְҵid��ֻ�и�ְҵ�Ľ�ɫ�ſ�ʹ�ø���Ʒ���ɶ��ְҵ */
	private String propJob;
	/** ʹ�ú��Ƿ�ɾ�� */
	private String propDrop;
	/** ÿ��ʹ�ô��� ��ɫÿ��ʹ�ø���Ʒ�Ĵ������� */
	private int propUsedegree;
	/** �Ա� ʹ�ø���Ʒ���Ա����ƣ���Ϊ1��ŮΪ2����Ҫ��Ϊ0 */
	private int propSex;
	/** ���� 0�ǲ�������1�Ǳ������������Դ����� */
	private int propProtect;
	/** ͼƬ ��дͼƬcode */
	private String propPic;
	/*���Ҫ��,1Ϊ�ǣ�2Ϊ��Ҫ��*/
	private int marriage;
	
	/**�����ֽ�1*/
	private String propOperate1;

	/**�����ֽ�2*/
	private String propOperate2;
	
	/**�����ֽ�3*/
	private String propOperate3;
	
	/**�洢λ��,*/
	private int propPosition;
	
	/**�洢λ��,���������Ĵ洢λ��*/
	private int propAuctionPosition;
	
	/**����ȷ�ϱ�־��0��ʾ���ö���ȷ�ϣ�1��ʾ��Ҫ����ȷ��*/
	private int propReconfirm;
	
	 /**���Ƶ����Ƿ���ã�0��ʾ�������ƣ�1��ʾս��ʱ������*/
	private int propUseControl;

	/**
	 * �õ�num��������Ҫ�İ����ռ�
	 * @return
	 */
	public int getNeedSpace(int num)
	{
		if( num<=0 )
		{
			return 0;
		}
		int need_space = (num+propAccumulate-1)/propAccumulate;
		return need_space;
	}
	
	/**
	 * ״̬����
	 * @return
	 */
	public String getStatusDisplay()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("��Ʒ״̬:");
		if( this.getPropBonding()==1)
		{
			sb.append("���ɽ���");
		}
		else
		{
			sb.append("���Խ���");
		}
		
		sb.append(",");
		
		if( this.getPropProtect()==1 )
		{
			sb.append("���ɶ���");
		}
		else
		{
			sb.append("���Զ���");
		}
		sb.append("<br/>");
		return sb.toString();
	}
	
	/**
	 * �õ�ͼƬ��ʾ����·��
	 * @return
	 */
	public String getPicDisplay()
	{
		if( propPic!=null && !propPic.equals(""))
		{
			return "<img src=\""+GameConfig.getContextPath()+"/image/item/prop/"+propPic+".png\" alt=\"\"/><br/>";
		}
		return "";
	}
	
	
	public int getPropID() {
		return propID;
	}

	public void setPropID(int propID) {
		this.propID = propID;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}
	
	
	
	public int getPropClass() {
		return propClass;
	}

	public void setPropClass(int propClass) {
		this.propClass = propClass;
	}
	
	
	
	public String getPropReLevel() {
		return propReLevel;
	}

	public void setPropReLevel(String propReLevel) {
		this.propReLevel = propReLevel;
	}


	public int getMarriage() {
		return marriage;
	}

	public void setMarriage(int marriage) {
		this.marriage = marriage;
	}

	public int getPropBonding() {
		return propBonding;
	}

	public void setPropBonding(int propBonding) {
		this.propBonding = propBonding;
	}

	public int getPropAccumulate() {
		return propAccumulate;
	}

	public void setPropAccumulate(int propAccumulate) {
		this.propAccumulate = propAccumulate;
	}

	public String getPropDisplay() {
		return propDisplay;
	}

	public void setPropDisplay(String propDisplay) {
		this.propDisplay = propDisplay;
	}

	public int getPropSell() {
		return propSell;
	}

	public void setPropSell(int propSell) {
		this.propSell = propSell;
	}

	public String getPropJob() {
		return propJob;
	}

	public void setPropJob(String propJob) {
		this.propJob = propJob;
	}

	public String getPropDrop() {
		return propDrop;
	}

	public void setPropDrop(String propDrop) {
		this.propDrop = propDrop;
	}

	public int getPropUsedegree() {
		return propUsedegree;
	}

	public void setPropUsedegree(int propUsedegree) {
		this.propUsedegree = propUsedegree;
	}

	public int getPropSex() {
		return propSex;
	}

	public void setPropSex(int propSex) {
		this.propSex = propSex;
	}

	public int getPropProtect() {
		return propProtect;
	}

	public void setPropProtect(int propProtect) {
		this.propProtect = propProtect;
	}

	public String getPropPic() {
		return propPic;
	}

	public void setPropPic(String propPic) {
		this.propPic = propPic;
	}

	public String getPropOperate1()
	{
		return propOperate1;
	}

	public void setPropOperate1(String propOperate1)
	{
		this.propOperate1 = propOperate1;
	}

	public String getPropOperate2()
	{
		return propOperate2;
	}

	public void setPropOperate2(String propOperate2)
	{
		this.propOperate2 = propOperate2;
	}

	public String getPropOperate3()
	{
		return propOperate3;
	}

	public void setPropOperate3(String propOperate3)
	{
		this.propOperate3 = propOperate3;
	}

	public int getPropPosition()
	{
		return propPosition;
	}

	public void setPropPosition(int propPosition)
	{
		this.propPosition = propPosition;
	}

	public int getPropReconfirm()
	{
		return propReconfirm;
	}

	public void setPropReconfirm(int propReconfirm)
	{
		this.propReconfirm = propReconfirm;
	}

	public int getPropAuctionPosition()
	{
		return propAuctionPosition;
	}

	public void setPropAuctionPosition(int propAuctionPosition)
	{
		this.propAuctionPosition = propAuctionPosition;
	}

	public int getPropUseControl()
	{
		return propUseControl;
	}

	public void setPropUseControl(int propUseControl)
	{
		this.propUseControl = propUseControl;
	}

}
