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
	 * �������ܶ�
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
	 * ��ȡ���Ч��
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
	 * ��ô�־���
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
	 * ������ӻ��5%��Ѫ����
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
	 * ͽ��ÿ��5����ʦ���ɻ�������;���Ƚ���
	 * ��þ��齱����ʽ��ʦ����ǰ�ȼ���������/20*(ͽ�ܵ�ǰ�ȼ�/ʦ����ǰ�ȼ�)
     * �������������ʽ��500*ͽ�ܵȼ�
	 * ͽ�ܵ�һ�μ������ɣ�ʦ���ɻ�á�Ԫ������500
	 * ͽ�ܵȼ��ﵽʦ���ȼ����ɫ�ȼ��ﵽ40�������ɳ�ʦ������á���ʦ���������1
	 * ͽ�ܳ�ʦʦ����ý�������100������ʦ���������1�;��飨ʦ����ǰ���������10%��
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
//					��þ��齱����ʽ��ʦ����ǰ�ȼ���������/20*(ͽ�ܵ�ǰ�ȼ�/ʦ����ǰ�ȼ�)
					long teaGetExp = ShituConstant.getTeaExpGet(bi.getGrade(), pv);
					if(((pv.getPGrade()==59||pv.getPGrade()==69||pv.getPGrade()==79||pv.getPGrade()==GameConfig.getGradeUpperLimit()))&&((teaGetExp+Long.parseLong(pv.getPExperience().trim()))>Long.parseLong(pv.getPXiaExperience().trim()))){
						teaGetExp =  Long.parseLong(pv.getPXiaExperience().trim())-Long.parseLong(pv.getPExperience().trim());
					}
					propertyService.updateAddExpProperty(pv.getPPk(), teaGetExp);
//					�������������ʽ��5*ͽ�ܵȼ�
					int teaGetMoney = ShituConstant.getTeaMoneyGet(bi.getGrade());
					economyService.addMoney1(pv.getPPk(), teaGetMoney);
					String message = "����ͽ��"+bi.getName()+"����5�����������"+(teaGetExp==0?"":("���飺"+teaGetExp+"�㣬"))+"����:"+MoneyUtil.changeCopperToStr(teaGetMoney);
					mailInfoService.sendMailBySystem(pv.getPPk(), "����ͽ��"+bi.getName()+"����5��", message);
				}
//				��һ�μ�������,ʦ���ɻ�á�Ԫ������500
				if(bi.getGrade()==10){
					String get = "";
//					if(pv.getPWrapSpare()>0){
//					goodsService.putPropToWrap(pv.getPPk(), ShituConstant.YUANBAOQUAN_ID, ShituConstant.YUANBAOQUAN_COUNT);
					get = "���ý�������"+GameConfig.getYuanbaoQuanName()+"����"+ShituConstant.YUANBAOQUAN_COUNT+".<br/>";
//					}
					String help = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn1\" />"
						+ "</go>��ȡ</anchor><br/>";
					String message = "���ͽ��"+bi.getName()+"������"+bi.getFactionName()+".";
					int mailId = mailInfoService.insertMailReturnId(pv.getPPk(), message, "aa");
					String help1 = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn1\" />"
						+ "<postfield name=\"mailId\" value=\""+mailId+"\" />"
						+ " </go>"
						+ "��ȡ</anchor><br/>";
					mailInfoService.updateMail(mailId, message+get+help1);
				}
//				ͽ�ܳ�ʦʦ����ý�������100������ʦ���������1�;��飨ʦ����ǰ���������10%��
				if(bi.getGrade()>=40||bi.getGrade()>=pv.getPGrade()){
					creditProce.addPlayerCredit(pv.getPPk(),ShituConstant.CHUSHI_CREDIT_ID,ShituConstant.CHUSHI_CREDIT_COUNT);
					//ͳ����Ҫ
					new RankService().updateAdd(pv.getPPk(), "credit", ShituConstant.CHUSHI_CREDIT_COUNT);
//					String libao = "";
//					if(pv.getPWrapSpare()>0){
//					goodsService.putPropToWrap(bi.getPPk(), ShituConstant.STU_DALIBAO, 1);
//					libao = "����ʦ���������1��";
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
					getExp = ",����"+chushiExp+"<br/>";
					}
					}
					String message = "����ͽ��"+bi.getName()+"�Ѿ�˳����ʦ��.";
					int mailId = mailInfoService.insertMailReturnId(pv.getPPk(), message, "aa");
					String help1 = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn2\" />"
						+ "<postfield name=\"mailId\" value=\""+mailId+"\" />"
						+ " </go>"
						+ "��ȡ����ʦ�������</anchor><br/>";
					mailInfoService.updateMail(mailId, message+"����ý���������ʦ���������1,����������"+ShituConstant.CHUSHI_CREDIT_COUNT+getExp+help1);
					
					int mailId1 = mailInfoService.insertMailReturnId(bi.getPPk(), "���Ѿ�˳����ʦ��", "aa");
					String help2 = "<anchor><go method=\"post\" href=\""+GameConfig.getContextPath()+"/baishi.do"+"\">"
						+ "<postfield name=\"cmd\" value=\"nn3\" />"
						+ "<postfield name=\"mailId\" value=\""+mailId1+"\" />"
						+ " </go>"
						+ "��ȡ����ʦ�������</anchor><br/>";
					mailInfoService.updateMail(mailId1, "���Ѿ�˳����ʦ��,��ý���������ʦ���������1.��鿴�ʼ���ȡ.<br/>"+help2);
					
					UMessageInfoVO uif = new UMessageInfoVO();
					uif.setCreateTime(new Date());
					uif.setMsgPriority(PopUpMsgType.CHUSHI_FIRST);
					uif.setMsgType(PopUpMsgType.CHUSHI);
					uif.setPPk(bi.getPPk());
					uif.setResult("��ʦ");
					uif.setMsgOperate1(1+"");
					uif.setMsgOperate2(bi.getPPk()+"");
					uMsgService.sendPopUpMsg(uif);
				}
			}
		}
	}

}
