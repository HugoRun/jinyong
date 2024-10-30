/**
 * 
 */
package com.ls.web.service.rank;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ben.dao.friend.FriendDAO;
import com.ben.rank.dao.RankDao;
import com.ben.rank.model.Rank;
import com.ben.rank.model.RankVo;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.dao.info.pet.PetInfoDao;
import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.pub.util.MoneyUtil;
import com.pm.dao.untangle.UntangLeDao;

/**
 * ���ܣ�������������߼�
 * 
 * @author ls Apr 7, 2009 2:03:47 PM
 */
public class RankService
{
	private RankDao rankDao = new RankDao();

	/**
	 * �õ���ɫ����������Ϣ
	 * 
	 * @return
	 */
	public String getJianghuRank(String pPk)
	{
		String result = "";
		UntangLeDao untangdao = new UntangLeDao();
		Date dt = new Date();
		int gradePaiMing = untangdao.getGradePaiMimg(pPk);
		int myfenshu = untangdao.getFenShuByPPk(pPk);
		// int[] bigPerson = untangdao.getBigPerson(gradePaiMing);
		int preWanJia = untangdao.getJiangHuFenShu(gradePaiMing);
		int samePaiMIng = untangdao.getSamePaiMing(pPk, myfenshu);

		Date dt2 = new Date();
		long ddd = dt2.getTime() - dt.getTime();
		if ((gradePaiMing + samePaiMIng) != 0)
		{
			result = "�����ڵĽ���������" + (gradePaiMing + samePaiMIng + 1)
					+ "��,����һ�����" + (preWanJia - myfenshu) + "����;";
		}
		else
		{
			result = "�����ڵĽ���������1��;";
		}

		// String result = null;
		// UntangLeDao untangdao = new UntangLeDao();
		// // gradePaiMing,һ���Լ�������2���Լ�����
		// int[] gradePaiMing = untangdao.getGradePaiMimgQuan(pPk);
		// // ǰһ����ҵķ���
		// int preWanJia = untangdao.getJiangHuFenShu(gradePaiMing[0]);
		// if(gradePaiMing[0] != 0)
		// {
		// result = "�����ڵĽ���������"+(gradePaiMing[0]+1)+"��,����һ�����"+(preWanJia
		// -gradePaiMing[1])+"����;";
		// }
		// else
		// {
		// result = "�����ڵĽ���������1��;";
		// }
		//				
		return result;
	}

	/**
	 * �õ���ɫ�Ƹ�������Ϣ
	 * 
	 * @return
	 */
	public String getRichRank(String pPk)
	{
		/*
		 * String result = null; UntangLeDao untangdao = new UntangLeDao(); int
		 * moneyPaiMing = untangdao.getMoneyPaiMing(pPk); int[] bigmoneyPerson =
		 * untangdao.getBigMoneyPerson(moneyPaiMing); int mymoney =
		 * untangdao.getMoneyByPPk(pPk); int sameMoneyPaiMIng =
		 * untangdao.getMoneySamePaiMing(pPk,mymoney); if(moneyPaiMing != 0) {
		 * result =
		 * "�Ƹ�������"+(moneyPaiMing+sameMoneyPaiMIng+1)+"��,����һ�����"+MoneyUtil.changeCopperToStr(bigmoneyPerson[1]-mymoney)+";"; }
		 * else { result = "�Ƹ�������1��;"; }
		 */

		String result = null;
		UntangLeDao untangdao = new UntangLeDao();
		int[] gradePaiMing = untangdao.getMoneyPaiMimgQuan(pPk);
		int[] bigPerson = untangdao.getBigMoneyPerson(gradePaiMing[0]);
		if (gradePaiMing[0] != 0)
		{
			result = "�Ƹ�������"
					+ (gradePaiMing[0] + 1)
					+ "��,����һ�����"
					+ MoneyUtil.changeCopperToStr(bigPerson[1]
							- gradePaiMing[1]) + ";";
		}
		else
		{
			result = "�Ƹ�������1��;";
		}

		return result;
	}

	// ��ԭ�еĻ��������ӻ��߼���
	public int updateAdd(Object p_pk, String field, Object value)
	{
		return rankDao.updateAdd(p_pk, field, value);
	}

	// ���������ĳЩ���ݵ�ʱ��
	public int updatea(Object p_pk, String field, Object value)
	{
		return rankDao.update(p_pk, field, value);
	}

	// ������ɫʱ���Ӽ�¼
	public int insert(Object p_pk, String name)
	{
		return rankDao.insert(p_pk, name);
	}
	
	// �����û�
	public int insert(Object p_pk, String name,int grade)
	{
		return rankDao.insert(p_pk, name, grade);
	}
	
	public int isExist(Object p_pk){
		return rankDao.isExist(p_pk);
	}

	// ��������
	public int updateMenPai(Object p_pk, String menpai)
	{
		return rankDao.updateMenPai(p_pk, menpai);
	}

	// �����ֶβ�ѯ
	public List<com.ben.rank.model.RankVo> findByField(String field, int i)
	{
		field = StringUtils.replace(field, "r_", "",1);
		return rankDao.findByField(field, i);
	}
	
	// �鿴�Լ�������
	public int findOwnByField(Object p_pk, String field, int i)
	{
		field = StringUtils.replace(field, "r_", "",1);
		return rankDao.findOwnByField(p_pk, field, i);
	}
	
	// �����ֵ�����
	public int updateYiqi(Object p_pk, int yiqi, String with_who)
	{
		return rankDao.updateYiqi(p_pk, yiqi, with_who);
	}
	
	// ���İ�������
	public int updateDear(Object p_pk, int dear, String with_who)
	{
		return rankDao.updateDear(p_pk, dear, with_who);
	}
	
	// �����ֶ���0
	public int clear(String field)
	{
		return rankDao.clear(field);
	}
	
	//����VIP
	public int updateVIP(Object p_pk,int vip_type,int vip_time){
		return rankDao.updateVIP(p_pk, vip_type, vip_time);
	}
	
//	�鿴�Լ���VIP����
	public int findOwnVIP(Object p_pk){
		return rankDao.findOwnVIP(p_pk);
	}
	
	public List<RankVo> findVip()
	{
		return rankDao.findVip();
	}
	
	// ��ѯ��������
	public List<RankVo> findDear()
	{
		return rankDao.findDear();
	}
	
	// ��ѯ����
	public List<RankVo> findYi()
	{
		return rankDao.findYi();
	}
	
	// ɾ���ֵ�����
	public int updateYiqiToZero(Object p_pk)
	{
		return rankDao.updateYiqiToZero(p_pk);
	}
	
	// ɾ����������
	public int updateDearToZero(Object p_pk)
	{
		return rankDao.updateDearToZero(p_pk);
	}
	
	//���Ѿ�ͳ�ƹ�����Ĵ��ϱ�־
	public int updatePpk(int id){
		return rankDao.updatePpk(id);
	}
	
	//�鿴��������ǰʮ�ĳ���
	public List<PetInfoVO> fintbyExp(){
		return new PetInfoDao().fintbyExp();
	}
	
	//�鿴��������ǰʮ�ĳ���
	public List<PetInfoVO> fintbyGONGji(){
		return new PetInfoDao().fintbyGONGji();
	}
	
	//�鿴�Լ��ĳ��ﾭ������
	public int findOwnExp(Object p_pk){
		return new PetInfoDao().findOwnExp(p_pk);
	}
	
	//�鿴�Լ��ĳ��﹥������
	public int findGjExp(Object p_pk){
		return new PetInfoDao().findGjExp(p_pk);
	}
	
	//ɾ����ɫ
	public int remove(Object p_pk){
		return rankDao.remove(p_pk);
	}
	
	//����ʥ��
	public List<RankVo> findSheng(){
		return rankDao.findSheng();
	}
	
	//����ʥ��
	public List<Rank> findSheng1(){
		return rankDao.findSheng1();
	}
	
	//�鿴�Լ��Ľ���ʥ������
	public int findOwnSheng(Object p_pk){
		return rankDao.findOwnSheng(p_pk);
	}
	
	public List<Rank> findByFieldaLL(String field, int i)
	{
		return rankDao.findByFieldaLL(field, i);
	}
	
	public List<FriendVO> paiFriend(int relation){
		return new FriendDAO().paihang(relation);
	}
	
	public int getOwn(int p_pk, int relation)
	{
		return new FriendDAO().getOwn(p_pk, relation);
	}
	/**********���ɾ����ɫ��ʱ��ɾ�����а�������Ϣ***********/
	public void removeRandInfo(int ppk)
	{
		rankDao.removeRandInfo(ppk);
	}
}
