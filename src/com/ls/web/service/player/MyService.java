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
	 * �����������ܶ�
	 * @param p_pk 
	 * @param relation��ϵ 1Ϊ���壬2Ϊ���
	 */
	public void addDear(int p_pk,String name);
	
	/**
	 * ���������Ч�� 
	 * @param P_PK
	 * @param relation  1Ϊ���壬2Ϊ���
	 * @return �������Ч��
	 */
//	public int addTeamEffect(int P_PK);
	
	
	/**
	 * �����þ���
	 * @param p_pk
	 * @param fd_pk
	 * @param relation
	 * @return
	 */
	public int shareExp(int p_pk,int level,int exp);
	
	
	public void addBloodMax(PartInfoVO player);
	
	
	/**
	 * �鿴�Լ���Ϊͽ��ʱ,ʦ���Ƿ�Ҳ����
	 */
	public boolean isTeaOnline(int p_pk);
	
	/**
	 * �鿴�Լ��Ƿ�ӵ��ʦͽ��ϵ(�Լ���ͽ��)
	 * @param p_pk
	 * @return
	 */
	public boolean isShitu(int p_pk);
	
	
	/**
	 * ͽ��ÿ��5����ʦ���ɻ�������;���Ƚ���
	 * ��þ��齱����ʽ��ʦ����ǰ�ȼ���������/20*(ͽ�ܵ�ǰ�ȼ�/ʦ����ǰ�ȼ�)
     * �������������ʽ��5*ͽ�ܵȼ�
	 * ͽ�ܵ�һ�μ������ɺͰ�ᣬʦ���ɻ�á�Ԫ������500
	 * ͽ�ܵȼ��ﵽʦ���ȼ����ɫ�ȼ��ﵽ40�������ɳ�ʦ������á���ʦ���������1
	 * ͽ�ܳ�ʦʦ����ý�������100������ʦ���������1�;��飨ʦ����ǰ���������10%��
	 */
	
	public void levelUp(int p_pk);
}
