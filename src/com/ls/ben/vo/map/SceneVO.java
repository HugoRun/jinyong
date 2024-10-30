package com.ls.ben.vo.map;

import java.util.HashMap;
import java.util.List;

import com.ls.ben.cache.staticcache.map.SceneCache;
import com.ls.ben.dao.info.npc.NpcrefurbishDao;
import com.ls.ben.vo.info.npc.NpcrefurbishVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ��ƾ�
 * 11:57:17 AM
 */
public class SceneVO {
	/** �ص� */
	private int sceneID;
	/** �������� */
	private String sceneName;
	/** �������� */
	private String sceneCoordinate;
	/** �������� */
	private String sceneLimit;
	/** ��ͼ��ת */
	private int sceneJumpterm;
	/** ��ͼ����(�����Ǽ���) */
	private String sceneAttribute;
	/** ��ͼ����ֵ1��2�� */
	private String sceneAttributeValue;
	/** PK���� */
	private int sceneSwitch;
	/** ��Ұ��� */
	private String sceneKen;
	/** �����Թ������ã���ʾ���Թ��ĵڼ��� */
	private int sceneSkill;
	/** ����ͼƬ */
	private String scenePhoto;
	/** ����˵�� */
	private String sceneDisplay;
	/** ˢ��ʱ�� */
	private int sceneRenovatetime;
	/** MAP���� */
	private String sceneMapqy;
	/** ���������� 0������ 1������ */
	private int sceneShang;
	/** ��������С 0������ 1������ */
	private int sceneXia;
	/** ���������� 0������ 1������ */
	private int sceneZuo;
	/** ���������� 0������ 1������ */
	private int sceneYou;
	
	/**��������map*/
	private MapVO map = null;
	/**��ת�ĳ�����Ϣ*/
	private SceneVO jumpScene;
	
	/**
	 * ��ǰ�����µĸ��˵�
	 */
	private HashMap<String,OperateMenuVO> fatherMenuList = new HashMap<String,OperateMenuVO>(10);
	
	/**
	 * �õ�������ת�ĳ�����Ϣ
	 * @return
	 */
	public SceneVO getJumpSceneInfo()
	{
		if( sceneJumpterm>0 )
		{
			jumpScene = SceneCache.getById(sceneJumpterm+"");
			if( jumpScene==null )
			{
				DataErrorLog.debugData("�ó�������ת����id����scene_id="+this.sceneID+";sceneJumpterm="+sceneJumpterm);
			}
		}
		return jumpScene;
	}
	
	/**
	 * �Ƿ���Խ���ó���
	 * @return
	 */
	public String isEntered( RoleEntity roleInfo )
	{
		if( roleInfo==null )
		{
			return "��������";
		}
		int targetType= this.getMap().getBarea().getBareaType();
		int selfType=roleInfo.getBasicInfo().getPRace();
		if(targetType!=selfType&&targetType!=0)
		{
			return "���ܽ�����������";
		}
		return null;
	}

	/**
	 * �Ƿ�����PK
	 * @return
	 */
	public boolean isAllowPK()
	{
		if( this.getSceneSwitch()==1 )
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * �õ�����������
	 * @return
	 */
	public String getFullName()
	{
		StringBuffer result = new StringBuffer();
		
		result.append("[");
		result.append(this.getSceneName());
		result.append("]");
		if( this.getSceneSwitch()==1 )
		{
			result.append("��");
		}
		else
		{
			result.append("Σ");
		}
		
		return result.toString();
	}
	
	/**
	 * �õ���ǰ������boss�֣���ˢ��ʱ����Ƶ�npc����ˢ����ʾ
	 * @return
	 */
	public String getBossRefHint()
	{
		StringBuffer result = new StringBuffer();;
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		//�õ���ǰ�ص�npc����������npc��Ϣ
		List<NpcrefurbishVO> refurbish_list = npcrefurbishDao.getDeadBySenceId(this.sceneID);
		for (NpcrefurbishVO refurbish : refurbish_list)
		{
			result.append(refurbish.getBossRefHint());
		}
		return result.toString();
	}
	
	public HashMap<String, OperateMenuVO> getFatherMenuList()
	{
		return fatherMenuList;
	}
	
	public String getSceneMapqy()
	{
		return sceneMapqy;
	}

	public void setSceneMapqy(String sceneMapqy)
	{
		this.sceneMapqy = sceneMapqy;
	}

	public int getSceneShang()
	{
		return sceneShang;
	}

	public void setSceneShang(int sceneShang)
	{
		this.sceneShang = sceneShang;
	}

	public int getSceneXia()
	{
		return sceneXia;
	}

	public void setSceneXia(int sceneXia)
	{
		this.sceneXia = sceneXia;
	}

	public int getSceneZuo()
	{
		return sceneZuo;
	}

	public void setSceneZuo(int sceneZuo)
	{
		this.sceneZuo = sceneZuo;
	}

	public int getSceneYou()
	{
		return sceneYou;
	}

	public void setSceneYou(int sceneYou)
	{
		this.sceneYou = sceneYou;
	}

	public String getSceneAttributeValue() {
		return sceneAttributeValue;
	}

	public void setSceneAttributeValue(String sceneAttributeValue) {
		this.sceneAttributeValue = sceneAttributeValue;
	}

	public int getSceneID() {
		return sceneID;
	}

	public void setSceneID(int sceneID) {
		this.sceneID = sceneID;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public String getSceneCoordinate() {
		return sceneCoordinate;
	}

	public void setSceneCoordinate(String sceneCoordinate) {
		this.sceneCoordinate = sceneCoordinate;
	}

	public String getSceneLimit() {
		return sceneLimit;
	}

	public void setSceneLimit(String sceneLimit) {
		this.sceneLimit = sceneLimit;
	}

	public int getSceneJumpterm() {
		return sceneJumpterm;
	}

	public void setSceneJumpterm(int sceneJumpterm) {
		this.sceneJumpterm = sceneJumpterm;
	}

	public String getSceneAttribute() {
		return sceneAttribute;
	}

	public void setSceneAttribute(String sceneAttribute) {
		this.sceneAttribute = sceneAttribute;
	}

	public int getSceneSwitch() {
		return sceneSwitch;
	}

	public void setSceneSwitch(int sceneSwitch) {
		this.sceneSwitch = sceneSwitch;
	}

	public String getSceneKen() {
		return sceneKen;
	}

	public void setSceneKen(String sceneKen) {
		this.sceneKen = sceneKen;
	}

	public int getSceneSkill() {
		return sceneSkill;
	}

	public void setSceneSkill(int sceneSkill) {
		this.sceneSkill = sceneSkill;
	}

	public String getScenePhoto() {
		return scenePhoto;
	}

	public void setScenePhoto(String scenePhoto) {
		this.scenePhoto = scenePhoto;
	}

	public String getSceneDisplay() {
		return sceneDisplay;
	}

	public void setSceneDisplay(String sceneDisplay) {
		this.sceneDisplay = sceneDisplay;
	}

	public int getSceneRenovatetime() {
		return sceneRenovatetime;
	}

	public void setSceneRenovatetime(int sceneRenovatetime) {
		this.sceneRenovatetime = sceneRenovatetime;
	}

	public MapVO getMap()
	{
		return map;
	}

	public void setMap(MapVO map)
	{
		this.map = map;
	}

}
