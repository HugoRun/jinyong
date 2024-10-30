package com.ls.web.service.player;

import com.ben.dao.info.partinfo.PartInfoDAO;
import com.ben.shitu.service.ShituService;
import com.dp.dao.credit.CreditProce;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.group.GroupService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.mail.MailInfoService;
import com.web.service.friend.FriendService;

public interface MyService
{
	GroupService groupService = new GroupService();
	RoleService roleService = new RoleService();
	FriendService friendService = new FriendService();
	ShituService shituService = new ShituService();
	PartInfoDAO partInfoDAO = new PartInfoDAO();
	PropertyService propertyService = new PropertyService();
	GoodsService goodsService = new GoodsService();
	MailInfoService mailInfoService = new MailInfoService();
	CreditProce creditProce = new CreditProce();
	EconomyService economyService = new EconomyService();
	UMsgService uMsgService = new UMsgService();
	/**
	 * 结义增加亲密度
	 * @param p_pk 
	 * @param relation关系 1为结义，2为结婚
	 */
	public void addDear(int p_pk,String name);
	
	/**
	 * 结义获得组队效果 
	 * @param P_PK
	 * @param relation  1为结义，2为结婚
	 * @return 返回组队效果
	 */
//	public int addTeamEffect(int P_PK);
	
	
	/**
	 * 结义获得经验
	 * @param p_pk
	 * @param fd_pk
	 * @param relation
	 * @return
	 */
	public int shareExp(int p_pk,int level,int exp);
	
	
	public void addBloodMax(PartInfoVO player);
	
	
	/**
	 * 查看自己作为徒弟时,师父是否也在线
	 */
	public boolean isTeaOnline(int p_pk);
	
	/**
	 * 查看自己是否拥有师徒关系(自己是徒弟)
	 * @param p_pk
	 * @return
	 */
	public boolean isShitu(int p_pk);
	
	
	/**
	 * 徒弟每升5级，师傅可获得银两和经验等奖励
	 * 获得经验奖励公式：师傅当前等级升级经验/20*(徒弟当前等级/师傅当前等级)
     * 获得银两奖励公式：5*徒弟等级
	 * 徒弟第一次加入门派和帮会，师傅可获得【元宝卷】×500
	 * 徒弟等级达到师傅等级或角色等级达到40级，即可出师，并获得【出师大礼包】×1
	 * 徒弟出师师傅获得江湖声望100，【出师大礼包】×1和经验（师傅当前升级经验的10%）
	 */
	
	public void levelUp(int p_pk);
}
