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
 * @author 侯浩军
 * 11:57:17 AM
 */
public class SceneVO {
	/** 地点 */
	private int sceneID;
	/** 场景名称 */
	private String sceneName;
	/** 场景坐标 */
	private String sceneCoordinate;
	/** 传送限制 */
	private String sceneLimit;
	/** 地图跳转 */
	private int sceneJumpterm;
	/** 地图属性(可以是几个) */
	private String sceneAttribute;
	/** 地图属性值1是2否 */
	private String sceneAttributeValue;
	/** PK开关 */
	private int sceneSwitch;
	/** 视野编号 */
	private String sceneKen;
	/** 现做迷宫层数用，表示是迷宫的第几层 */
	private int sceneSkill;
	/** 场景图片 */
	private String scenePhoto;
	/** 场景说明 */
	private String sceneDisplay;
	/** 刷新时间 */
	private int sceneRenovatetime;
	/** MAP区域 */
	private String sceneMapqy;
	/** 场景条件上 0无限制 1有限制 */
	private int sceneShang;
	/** 场景条件小 0无限制 1有限制 */
	private int sceneXia;
	/** 场景条件左 0无限制 1有限制 */
	private int sceneZuo;
	/** 场景条件右 0无限制 1有限制 */
	private int sceneYou;
	
	/**场景所在map*/
	private MapVO map = null;
	/**跳转的场景信息*/
	private SceneVO jumpScene;
	
	/**
	 * 当前场景下的父菜单
	 */
	private HashMap<String,OperateMenuVO> fatherMenuList = new HashMap<String,OperateMenuVO>(10);
	
	/**
	 * 得到可以跳转的场景信息
	 * @return
	 */
	public SceneVO getJumpSceneInfo()
	{
		if( sceneJumpterm>0 )
		{
			jumpScene = SceneCache.getById(sceneJumpterm+"");
			if( jumpScene==null )
			{
				DataErrorLog.debugData("该场景的跳转场景id错误：scene_id = "+this.sceneID+";sceneJumpterm = "+sceneJumpterm);
			}
		}
		return jumpScene;
	}
	
	/**
	 * 是否可以进入该场景
	 * @return
	 */
	public String isEntered( RoleEntity roleInfo )
	{
		if( roleInfo==null )
		{
			return "参数错误";
		}
		int targetType= this.getMap().getBarea().getBareaType();
		int selfType=roleInfo.getBasicInfo().getPRace();
		if(targetType!=selfType&&targetType!=0)
		{
			return "不能进入它族主城";
		}
		return null;
	}

	/**
	 * 是否允许PK
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
	 * 得到场景的描述
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
			result.append("安");
		}
		else
		{
			result.append("危");
		}
		
		return result.toString();
	}
	
	/**
	 * 得到当前场景下boss怪（有刷新时间控制的npc）的刷新提示
	 * @return
	 */
	public String getBossRefHint()
	{
		StringBuffer result = new StringBuffer();;
		NpcrefurbishDao npcrefurbishDao = new NpcrefurbishDao();
		//得到当前地点npc已死的所有npc信息
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
