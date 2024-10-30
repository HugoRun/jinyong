package com.ben.shitu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ben.shitu.model.DateUtil;
import com.ben.shitu.model.Shitu;
import com.ben.shitu.model.ShituConstant;
import com.ben.vo.friend.FriendVO;
import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.action.menu.BaseAction;

public class BaishiAction extends BaseAction

{

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		if (bi.getGrade() >= ShituConstant.BAISHI_LEVEL_LIMIT)
		{
			setMessage(request, "对不起，只有" + ShituConstant.BAISHI_LEVEL_LIMIT
					+ "级以下的玩家才可拜师！");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "玩家每周最多只能拜一次师傅！");
			return mapping.findForward(ERROR);
		}
		Object[] args = new Object[] { bi.getPPk() };
		int count = shituService.findCount(ShituConstant.BAISHISQL1, args);
		if (count != 0)
		{
			setMessage(request,
					"对不起，你已经有了一位师傅！如想另投名师可在游戏“系统”栏的“师徒管理”中解除师徒关系后再次拜师！");
			return mapping.findForward(ERROR);
		}
		int count1 = shituService.findCount(ShituConstant.BAISHISQL2, args);
		if (count1 == 0)
		{
			Shitu shitu = new Shitu(0, 0, bi.getPPk(), null, bi.getName(), 0,
					bi.getGrade(), null, null);
			shituService.addShitu(shitu);
		}
		return n2(mapping, form, request, response);
	}

	public ActionForward n2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		int nowPage = Integer
				.parseInt(request.getParameter("nowPage") == null ? "1"
						: request.getParameter("nowPage").trim());
		List<Shitu> list = shituService.findTeacher(nowPage);
		int stuCount = shituService.teacherCount();
		departList(request, list, stuCount, nowPage);
		return mapping.findForward("teacher");
	}

	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String te_id = (String) request.getParameter("te_id");
		String id = (String) request.getParameter("id");
		if (te_id == null || "".equals(te_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}

		RoleEntity roleInfo = roleService.getRoleInfoById(te_id.trim());
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(te_id.trim());
		String playerPic = picService.getPlayerPicStr(roleInfo, te_id.trim());
		setAttribute(request, "roleInfo", roleInfo);
		setAttribute(request, "vo", vo);
		setAttribute(request, "playerPic", playerPic);
		setAttribute(request, "id", id);
		setAttribute(request, "te_id", te_id.trim());
		return mapping.findForward("teadetail");
	}

	// 拜师操作
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String te_id = (String) request.getParameter("te_id");
		String id = (String) request.getParameter("id");
		if (te_id == null || "".equals(te_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(te_id.trim());
		if(vo==null){
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
         List<Shitu> list = shituService.findByTeacher(vo.getPPk());
		
		if (list != null)
		{
			if (list.size() >= ShituConstant.getCANRECCOUNT(vo.getTe_level()))
			{
				setMessage(request, "对不起，对方招徒名额已满，请你另投名师吧！");
				return mapping.findForward(ERROR);
			}
		}
		if (bi.getGrade() >= ShituConstant.BAISHI_LEVEL_LIMIT)
		{
			setMessage(request, "对不起，您的等级超过"+ShituConstant.BAISHI_LEVEL_LIMIT+",不能拜师!");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "玩家每周最多只能拜一次师傅！");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(vo.getLast_shoutu_time())){
			setMessage(request, "对方目前不能招徒！");
			return mapping.findForward(ERROR);
		}
		FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
		if (fv != null && fv.getRelation() != 0)
		{
			setMessage(request, "对不起，您和对方是"
					+ (fv.getRelation() == 1 ? "结义" : "结婚") + "关系，不能拜对方为师");
			return mapping.findForward(ERROR);
		}
		String message = bi.getName()
				+ "想拜你为师.<br/>"
				+ "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/baishi.do")+"\">"
				+ "<postfield name=\"cmd\" value=\"n5\" />"
				+ "<postfield name=\"id\" value=\""+id+"\" />"
				+ " <postfield name=\"stu_id\" value=\""
				+ bi.getPPk()
				+ "\" />"
				+ "<postfield name=\"caozuo\" value=\"0\" />"
				+ " </go>"
				+ "同意</anchor><br/>"
				+ "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/baishi.do")+"\">"
				+ "<postfield name=\"cmd\" value=\"n5\" />"
				+ "<postfield name=\"id\" value=\""+id+"\" />"
				+ "<postfield name=\"stu_id\" value=\"" + bi.getPPk() + "\" />"
				+ "<postfield name=\"caozuo\" value=\"1\" />" + " </go>"
				+ "拒绝</anchor><br/>";
		mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "想拜你为师",
				message);
		setMessage(request, "您已申请向" + vo.getPName() + "拜师");
		return mapping.findForward(ERROR);

	}

	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String id = (String) request.getParameter("id");
		String caozuo = (String)request.getParameter("caozuo");
		if (stu_id == null || "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim())||caozuo==null||"".equals(caozuo.trim()))
		{
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(stu_id.trim());
		if(vo==null){
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "玩家每周最多只能收一次徒弟！");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(vo.getLast_shoutu_time())){
			setMessage(request, "对方目前不能拜师！");
			return mapping.findForward(ERROR);
		}
		if(Integer.parseInt(caozuo.trim())==1){
			String message1 = "您拒绝收"+vo.getPName()+"为徒！";
			setMessage(request, message1);
			mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "拒绝收您为徒",
					 bi.getName() + "拒绝收您为徒");
			return mapping.findForward(ERROR);
		}
		
		List<Shitu> list = shituService.findByTeacher(bi.getPPk());
		
		if (list != null)
		{
			if (list.size() >= ShituConstant.getCANRECCOUNT(bi.getTe_level()))
			{
				setMessage(request, "对不起，您招徒名额已满，不能收"+vo.getPName()+"为徒！");
				return mapping.findForward(ERROR);
			}
		}
		if (vo.getPGrade() >= ShituConstant.BAISHI_LEVEL_LIMIT)
		{
			setMessage(request, "对不起，对方等级在"+ShituConstant.BAISHI_LEVEL_LIMIT+"以上，不能收他为徒!");
			return mapping.findForward(ERROR);
		}
		FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
		if (fv != null && fv.getRelation() != 0)
		{
			setMessage(request, "对不起，您和对方是"
					+ (fv.getRelation() == 1 ? "结义" : "结婚") + "关系，不能收徒");
			return mapping.findForward(ERROR);
		}
		List<Shitu> shitu = shituService.findByStudent(stu_id.trim());
		if (shitu != null && shitu.size() > 0)
		{
			setMessage(request, "对不起，对方已经拜师，请你另选贤徒吧！");
			return n2(mapping, form, request, response);
		}
		Object[] args = new Object[] { vo.getPPk(), vo.getPName(),
				vo.getPGrade(), id };
		shituService.doit(ShituConstant.BAISHISQL3, args);
		PartInfoDao pid = new PartInfoDao();
		bi.setLast_shoutu_time();
		pid.updateLastTime(vo.getPPk());
		pid.updateLastTime(bi.getPPk());
		RoleEntity re = roleService.getRoleInfoById(vo.getPPk()+"");
		if(re!=null){
			re.getBasicInfo().setLast_shoutu_time();
		}
		if (fv == null)
		{
			friendService.addfriend(bi.getPPk(), vo.getPPk() + "", vo
					.getPName(), getTime());
		}
		FriendVO fv1 = friendService.viewfriend(vo.getPPk(), bi.getPPk() + "");
		if (fv1 == null)
		{
			friendService.addfriend(vo.getPPk(), bi.getPPk() + "",
					bi.getName(), getTime());
		}
		String message = "恭喜你，你已经是玩家" + bi.getName() + "的弟子了，你可以获得以下好处：<br/>"
				+ "1.	可接受师傅每天直接传功一次<br/>" + "2.	师徒同时在线，可额外获得打怪经验的20%的经验<br/>"
				+ "3.	完成各类任务时可获得1.5倍的任务奖励<br/>"
				+ "4.	可在系统栏的“师徒管理”内查看师徒各项关系<br/>";
		
		String message1 = vo.getPName() + "拜你为师，以后一心一意的伺候你。";
		setMessage(request, message1);
		mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "同意收您为徒",
				message);
		// systemInfoService.insertSystemInfoBySystem(vo.getPPk(),message1);

		// 从拜师列表中删除自己
		shituService.doit(ShituConstant.BAISHISQL4,
				new Object[] { vo.getPPk() });
		shituService.remove(vo.getPName(),bi.getName());
		return mapping.findForward(ERROR);
	}
	
	public ActionForward nn1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		String mailId = request.getParameter("mailId");
		if(mailId==null||"".equals(mailId.trim())){
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		if(bi.getWrapSpare()<1){
			setMessage(request, "您的包裹已满，请清理后领取！");
		}else{
			goodsService.putPropToWrap(bi.getPPk(), ShituConstant.YUANBAOQUAN_ID, ShituConstant.YUANBAOQUAN_COUNT,GameLogManager.G_SYSTEM);
			setMessage(request, "您成功领取了【"+GameConfig.getYuanbaoQuanName()+"】×"+ShituConstant.YUANBAOQUAN_COUNT);
			mailInfoService.deleteMailByid(mailId, bi.getPPk());
		}
		return mapping.findForward(ERROR);
	}
	
	public ActionForward nn2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		String mailId = request.getParameter("mailId");
		if(mailId==null||"".equals(mailId.trim())){
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		if(bi.getWrapSpare()<1){
			setMessage(request, "您的包裹已满，请清理后领取！");
		}else{
			if(mailInfoService.getPersonMailView(mailId)!=null){
			goodsService.putPropToWrap(bi.getPPk(), ShituConstant.STU_DALIBAO, 1,GameLogManager.G_SYSTEM);
			setMessage(request, "您成功领取了【出师大礼包】×1");
			mailInfoService.deleteMailByid(mailId, bi.getPPk());
			}else{
				setMessage(request, "您已经领取过【出师大礼包】!");
			}
			
		}
		return mapping.findForward(ERROR);
	}
	
	public ActionForward nn3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		String mailId = request.getParameter("mailId");
		if(mailId==null||"".equals(mailId.trim())){
			setMessage(request, "出错了，请重新操作");
			return mapping.findForward(ERROR);
		}
		if(bi.getWrapSpare()<1){
			setMessage(request, "您的包裹已满，请清理后领取！");
		}else{
			goodsService.putPropToWrap(bi.getPPk(), ShituConstant.TEA_DALIBAO, 1,GameLogManager.G_SYSTEM);
			setMessage(request, "您成功领取了【出师大礼包】×1");
			mailInfoService.deleteMailByid(mailId, bi.getPPk());
		}
		return mapping.findForward(ERROR);
	}
}
