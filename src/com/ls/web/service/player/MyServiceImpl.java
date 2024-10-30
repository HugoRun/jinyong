package com.ls.web.service.player;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ben.shitu.model.Shitu;
import com.ben.shitu.model.ShituConstant;
import com.ben.vo.friend.FriendVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.rank.RankService;
import com.web.jieyi.util.Constant;

public class MyServiceImpl implements MyService
{

	/**
	 * 增加亲密度
	 */
	public void addDear(int p_pk,String name)
	{
		List<RoleEntity> list = groupService.getMembersList(p_pk);
		if (list != null)
		{
			for (RoleEntity giv : list)
			{
				
				friendService.addDear(p_pk + "", giv.getBasicInfo().getPPk() + "",name,giv.getBasicInfo().getName(), "+1");
			}
		}
	}
	
	/**
	 * 获取组队效果
	 */
	public int addTeamEffect(int P_PK)
	{
		int addTeamEffert = 0;
		List<RoleEntity> list = groupService.getMembersList(P_PK);
		if (list != null)
		{
			StringBuffer sb = new StringBuffer();
			for (RoleEntity giv : list)
			{
				sb.append(giv.getBasicInfo().getPPk() + ",");
			}
			String ssb = sb.toString();
			if (ssb.length() > 1)
			{
				addTeamEffert = friendService.jieyiCount(P_PK + "", ssb
						.substring(0, ssb.length() - 1), 1);
			}
		}
		return addTeamEffert * 10;
	}

	/**
	 * 获得打怪经验
	 * 
	 * @param p_pk
	 * @param fd_pk
	 * @param relation
	 * @return
	 */
	public int shareExp(int p_pk,int level, int exp)
	{
		if (exp != 0)
		{
			List<RoleEntity> list = groupService.getMembersList(p_pk);
			StringBuffer sb = new StringBuffer();
			if (list != null)
			{
				for (RoleEntity giv : list)
				{
					sb.append(giv.getBasicInfo().getPPk()).append(",");
				}
			}
			List<FriendVO> friendList = friendService.findFriends(p_pk + "", sb
					.toString());
			if (friendList != null)
			{
				for (FriendVO fv : friendList)
				{
					RoleEntity roleEntity = roleService.getRoleInfoById(fv.getFdPk()+"");
					float bilv = 1;
					if(roleEntity!=null&&roleEntity.getBasicInfo().getGrade()<level){
						bilv = (float)((float)roleEntity.getBasicInfo().getGrade()/(float)level);
					}
					if (fv.getRelation() == 2)
					{
						friendService.setExpShare(fv.getFdPk(), fv.getPPk(),
								Math.round(((float)(((float)exp)*bilv) * 2)));
					}
					else
						if (fv.getRelation() == 1)
						{
							int huodeExp = 0;
							for (Integer i : Constant.EXP_SHARE.keySet())
							{
								if (fv.getDear() >= i)
								{
									huodeExp = Math.round((float)(((float)exp)*bilv)) * Constant.EXP_SHARE.get(i) / 100;
									break;
								}
							}
							friendService.setExpShare(fv.getFdPk(),
									fv.getPPk(), huodeExp);
						}
				}
			}
			return friendList != null ? friendList.size() : 0;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * 夫妻组队获得5%气血上限
	 */
	public void addBloodMax(PartInfoVO player)
	{
		if (player != null)
		{
			List<RoleEntity> list = groupService.getMembersList(player.getPPk());
			StringBuffer sb = new StringBuffer();
			if (list != null)
			{
				for (RoleEntity giv : list)
				{
					sb.append(giv.getBasicInfo().getPPk()).append(",");
				}
			}
			int i = friendService.isFuQi(player.getPPk(), sb.toString(), 2);
			if (i > 0)
			{
				player.updatePUpHp(Constant.FUQI_TEAM_ADD_PUPHP);
			}
		}
	}

	public boolean isTeaOnline(int p_pk)
	{
		List<Shitu> teacher = shituService.findByStudent(p_pk);
		if(teacher==null||teacher.size()<=0){
			return false;
		}
		Shitu shitu = teacher.get(0);
		int tea_id = shitu.getTe_id();
		RoleEntity roleEntity = roleService.getRoleInfoById(tea_id+"");
		if(roleEntity==null){
			return false;
		}
		return true;
	}
	
	public boolean isShitu(int p_pk){
		List<Shitu> teacher = shituService.findByStudent(p_pk);
		if(teacher==null||teacher.size()<=0){
			return false;
		}
		return true;
	}

	/**
	 * 徒弟每升5级，师傅可获得银两和经验等奖励
	 * 获得经验奖励公式：师傅当前等级升级经验/20*(徒弟当前等级/师傅当前等级)
     * 获得银两奖励公式：500*徒弟等级
	 * 徒弟第一次加入门派，师傅可获得【元宝卷】×500
	 * 徒弟等级达到师傅等级或角色等级达到40级，即可出师，并获得【出师大礼包】×1
	 * 徒弟出师师傅获得江湖声望100，【出师大礼包】×1和经验（师傅当前升级经验的10%）
	 */
	public void levelUp(int p_pk)
	{
		List<Shitu> teacher = shituService.findByStudent(p_pk);
		if(teacher!=null&&teacher.size()>0){
			Shitu shitu = teacher.get(0);
			int tea_id = shitu.getTe_id();
			BasicInfo bi = roleService.getRoleInfoById(p_pk+"").getBasicInfo();
			com.ben.vo.info.partinfo.PartInfoVO pv = (com.ben.vo.info.partinfo.PartInfoVO) partInfoDAO.getPartView(tea_id+"");
			if(bi!=null&&pv!=null){
				if((bi.getGrade()-shitu.getStu_level())%5==0){
//					获得经验奖励公式：师傅当前等级升级经验/20*(徒弟当前等级/师傅当前等级)
					long teaGetExp = ShituConstant.getTeaExpGet(bi.getGrade(), pv);
					if(((pv.getPGrade()==59||pv.getPGrade()==69||pv.getPGrade()==79||pv.getPGrade()==GameConfig.getGradeUpperLimit()))&&((teaGetExp+Long.parseLong(pv.getPExperience().trim()))>Long.parseLong(pv.getPXiaExperience().trim()))){
						teaGetExp =  Long.parseLong(pv.getPXiaExperience().trim())-Long.parseLong(pv.getPExperience().trim());
					}
					propertyService.updateAddExpProperty(pv.getPPk(), teaGetExp);
//					获得银两奖励公式：5*徒弟等级
					int teaGetMoney = ShituConstant.getTeaMoneyGet(bi.getGrade());
					economyService.addMoney1(pv.getPPk(), teaGetMoney);
					String message = "您的徒弟"+bi.getName()+"升了5级，您获得了"+(teaGetExp==0?"":("经验："+teaGetExp+"点，"))+"银子:"+MoneyUtil.changeCopperToStr(teaGetMoney);
					mailInfoService.sendMailBySystem(pv.getPPk(), "您的徒弟"+bi.getName()+"升了5级", message);
				}
//				第一次加入门派,师傅可获得【元宝卷】×500
				if(bi.getGrade()==10){
					String get = "";
//					if(pv.getPWrapSpare()>0){
//					goodsService.putPropToWrap(pv.getPPk(), ShituConstant.YUANBAOQUAN_ID, ShituConstant.YUANBAOQUAN_COUNT);
					get = "你获得奖励：【"+GameConfig.getYuanbaoQuanName()+"】×"+ShituConstant.YUANBAOQUAN_COUNT+".<br/>";
//					}
					String help = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn1\" />"
						+ "</go>领取</anchor><br/>";
					String message = "你的徒弟"+bi.getName()+"加入了"+bi.getFactionName()+".";
					int mailId = mailInfoService.insertMailReturnId(pv.getPPk(), message, "aa");
					String help1 = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn1\" />"
						+ "<postfield name=\"mailId\" value=\""+mailId+"\" />"
						+ " </go>"
						+ "领取</anchor><br/>";
					mailInfoService.updateMail(mailId, message+get+help1);
				}
//				徒弟出师师傅获得江湖声望100，【出师大礼包】×1和经验（师傅当前升级经验的10%）
				if(bi.getGrade()>=40||bi.getGrade()>=pv.getPGrade()){
					creditProce.addPlayerCredit(pv.getPPk(),ShituConstant.CHUSHI_CREDIT_ID,ShituConstant.CHUSHI_CREDIT_COUNT);
					//统计需要
					new RankService().updateAdd(pv.getPPk(), "credit", ShituConstant.CHUSHI_CREDIT_COUNT);
//					String libao = "";
//					if(pv.getPWrapSpare()>0){
//					goodsService.putPropToWrap(bi.getPPk(), ShituConstant.STU_DALIBAO, 1);
//					libao = "【出师大礼包】×1，";
//					}
//					if(bi.getWrapSpare()>0){
//					goodsService.putPropToWrap(pv.getPPk(), ShituConstant.TEA_DALIBAO, 1);
//					}
					shituService.delbyId(shitu.getId());
					partInfoDAO.updateTe_level(pv.getPPk());
					String getExp = "<br/>";
					if(pv.getPGrade()!=GameConfig.getGradeUpperLimit()){
					long chushiExp = ShituConstant.getChushiExp(pv);
					if(chushiExp>0){
					if((pv.getPGrade()==59||pv.getPGrade()==69||pv.getPGrade()==79||pv.getPGrade()==GameConfig.getGradeUpperLimit())&&((chushiExp+Long.parseLong(pv.getPExperience().trim()))>Long.parseLong(pv.getPXiaExperience().trim()))){
						chushiExp =  Long.parseLong(pv.getPXiaExperience().trim())-Long.parseLong(pv.getPExperience().trim());
					}
					propertyService.updateAddExpProperty(pv.getPPk(), chushiExp);
					getExp = ",经验"+chushiExp+"<br/>";
					}
					}
					String message = "您的徒弟"+bi.getName()+"已经顺利出师了.";
					int mailId = mailInfoService.insertMailReturnId(pv.getPPk(), message, "aa");
					String help1 = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn2\" />"
						+ "<postfield name=\"mailId\" value=\""+mailId+"\" />"
						+ " </go>"
						+ "领取【出师大礼包】</anchor><br/>";
					mailInfoService.updateMail(mailId, message+"您获得奖励：【出师大礼包】×1,江湖声望×"+ShituConstant.CHUSHI_CREDIT_COUNT+getExp+help1);
					
					int mailId1 = mailInfoService.insertMailReturnId(bi.getPPk(), "您已经顺利出师了", "aa");
					String help2 = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn3\" />"
						+ "<postfield name=\"mailId\" value=\""+mailId1+"\" />"
						+ " </go>"
						+ "领取【出师大礼包】</anchor><br/>";
					mailInfoService.updateMail(mailId1, "您已经顺利出师了,获得奖励：【出师大礼包】×1.请查看邮件领取.<br/>"+help2);
					
					UMessageInfoVO uif = new UMessageInfoVO();
					uif.setCreateTime(new Date());
					uif.setMsgPriority(PopUpMsgType.CHUSHI_FIRST);
					uif.setMsgType(PopUpMsgType.CHUSHI);
					uif.setPPk(bi.getPPk());
					uif.setResult("出师");
					uif.setMsgOperate1(1+"");
					uif.setMsgOperate2(bi.getPPk()+"");
					uMsgService.sendPopUpMsg(uif);
				}
			}
		}
	}

}
