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
 * 功能：处理排名相关逻辑
 * 
 * @author ls Apr 7, 2009 2:03:47 PM
 */
public class RankService
{
	private RankDao rankDao = new RankDao();

	/**
	 * 得到角色江湖排名信息
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
			result = "您现在的江湖排名第" + (gradePaiMing + samePaiMIng + 1)
					+ "名,与上一名相差" + (preWanJia - myfenshu) + "积分;";
		}
		else
		{
			result = "您现在的江湖排名第1名;";
		}

		// String result = null;
		// UntangLeDao untangdao = new UntangLeDao();
		// // gradePaiMing,一是自己排名，2是自己分数
		// int[] gradePaiMing = untangdao.getGradePaiMimgQuan(pPk);
		// // 前一个玩家的分数
		// int preWanJia = untangdao.getJiangHuFenShu(gradePaiMing[0]);
		// if(gradePaiMing[0] != 0)
		// {
		// result = "您现在的江湖排名第"+(gradePaiMing[0]+1)+"名,与上一名相差"+(preWanJia
		// -gradePaiMing[1])+"积分;";
		// }
		// else
		// {
		// result = "您现在的江湖排名第1名;";
		// }
		//				
		return result;
	}

	/**
	 * 得到角色财富排名信息
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
		 * "财富排名第"+(moneyPaiMing+sameMoneyPaiMIng+1)+"名,与上一名相差"+MoneyUtil.changeCopperToStr(bigmoneyPerson[1]-mymoney)+";"; }
		 * else { result = "财富排名第1名;"; }
		 */

		String result = null;
		UntangLeDao untangdao = new UntangLeDao();
		int[] gradePaiMing = untangdao.getMoneyPaiMimgQuan(pPk);
		int[] bigPerson = untangdao.getBigMoneyPerson(gradePaiMing[0]);
		if (gradePaiMing[0] != 0)
		{
			result = "财富排名第"
					+ (gradePaiMing[0] + 1)
					+ "名,与上一名相差"
					+ MoneyUtil.changeCopperToStr(bigPerson[1]
							- gradePaiMing[1]) + ";";
		}
		else
		{
			result = "财富排名第1名;";
		}

		return result;
	}

	// 在原有的基础上增加或者减少
	public int updateAdd(Object p_pk, String field, Object value)
	{
		return rankDao.updateAdd(p_pk, field, value);
	}

	// 当玩家消除某些数据的时候
	public int updatea(Object p_pk, String field, Object value)
	{
		return rankDao.update(p_pk, field, value);
	}

	// 创建角色时增加记录
	public int insert(Object p_pk, String name)
	{
		return rankDao.insert(p_pk, name);
	}
	
	// 增加用户
	public int insert(Object p_pk, String name,int grade)
	{
		return rankDao.insert(p_pk, name, grade);
	}
	
	public int isExist(Object p_pk){
		return rankDao.isExist(p_pk);
	}

	// 更改门派
	public int updateMenPai(Object p_pk, String menpai)
	{
		return rankDao.updateMenPai(p_pk, menpai);
	}

	// 根据字段查询
	public List<com.ben.rank.model.RankVo> findByField(String field, int i)
	{
		field = StringUtils.replace(field, "r_", "",1);
		return rankDao.findByField(field, i);
	}
	
	// 查看自己的排名
	public int findOwnByField(Object p_pk, String field, int i)
	{
		field = StringUtils.replace(field, "r_", "",1);
		return rankDao.findOwnByField(p_pk, field, i);
	}
	
	// 更改兄弟义气
	public int updateYiqi(Object p_pk, int yiqi, String with_who)
	{
		return rankDao.updateYiqi(p_pk, yiqi, with_who);
	}
	
	// 更改爱情甜蜜
	public int updateDear(Object p_pk, int dear, String with_who)
	{
		return rankDao.updateDear(p_pk, dear, with_who);
	}
	
	// 根据字段清0
	public int clear(String field)
	{
		return rankDao.clear(field);
	}
	
	//更改VIP
	public int updateVIP(Object p_pk,int vip_type,int vip_time){
		return rankDao.updateVIP(p_pk, vip_type, vip_time);
	}
	
//	查看自己的VIP排名
	public int findOwnVIP(Object p_pk){
		return rankDao.findOwnVIP(p_pk);
	}
	
	public List<RankVo> findVip()
	{
		return rankDao.findVip();
	}
	
	// 查询爱情甜蜜
	public List<RankVo> findDear()
	{
		return rankDao.findDear();
	}
	
	// 查询义气
	public List<RankVo> findYi()
	{
		return rankDao.findYi();
	}
	
	// 删除兄弟义气
	public int updateYiqiToZero(Object p_pk)
	{
		return rankDao.updateYiqiToZero(p_pk);
	}
	
	// 删除爱情甜蜜
	public int updateDearToZero(Object p_pk)
	{
		return rankDao.updateDearToZero(p_pk);
	}
	
	//将已经统计过经验的打上标志
	public int updatePpk(int id){
		return rankDao.updatePpk(id);
	}
	
	//查看经验排名前十的宠物
	public List<PetInfoVO> fintbyExp(){
		return new PetInfoDao().fintbyExp();
	}
	
	//查看攻击排名前十的宠物
	public List<PetInfoVO> fintbyGONGji(){
		return new PetInfoDao().fintbyGONGji();
	}
	
	//查看自己的宠物经验排名
	public int findOwnExp(Object p_pk){
		return new PetInfoDao().findOwnExp(p_pk);
	}
	
	//查看自己的宠物攻击排名
	public int findGjExp(Object p_pk){
		return new PetInfoDao().findGjExp(p_pk);
	}
	
	//删除角色
	public int remove(Object p_pk){
		return rankDao.remove(p_pk);
	}
	
	//江湖圣榜
	public List<RankVo> findSheng(){
		return rankDao.findSheng();
	}
	
	//江湖圣榜
	public List<Rank> findSheng1(){
		return rankDao.findSheng1();
	}
	
	//查看自己的江湖圣榜排名
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
	/**********玩家删除角色的时候删除排行榜的相关信息***********/
	public void removeRandInfo(int ppk)
	{
		rankDao.removeRandInfo(ppk);
	}
}
