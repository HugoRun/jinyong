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
			setMessage(request, "�Բ���ֻ��" + ShituConstant.BAISHI_LEVEL_LIMIT
					+ "�����µ���Ҳſɰ�ʦ��");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "���ÿ�����ֻ�ܰ�һ��ʦ����");
			return mapping.findForward(ERROR);
		}
		Object[] args = new Object[] { bi.getPPk() };
		int count = shituService.findCount(ShituConstant.BAISHISQL1, args);
		if (count != 0)
		{
			setMessage(request,
					"�Բ������Ѿ�����һλʦ����������Ͷ��ʦ������Ϸ��ϵͳ�����ġ�ʦͽ�����н��ʦͽ��ϵ���ٴΰ�ʦ��");
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
			setMessage(request, "�����ˣ������²���");
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

	// ��ʦ����
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String te_id = (String) request.getParameter("te_id");
		String id = (String) request.getParameter("id");
		if (te_id == null || "".equals(te_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(te_id.trim());
		if(vo==null){
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
         List<Shitu> list = shituService.findByTeacher(vo.getPPk());
		
		if (list != null)
		{
			if (list.size() >= ShituConstant.getCANRECCOUNT(vo.getTe_level()))
			{
				setMessage(request, "�Բ��𣬶Է���ͽ����������������Ͷ��ʦ�ɣ�");
				return mapping.findForward(ERROR);
			}
		}
		if (bi.getGrade() >= ShituConstant.BAISHI_LEVEL_LIMIT)
		{
			setMessage(request, "�Բ������ĵȼ�����"+ShituConstant.BAISHI_LEVEL_LIMIT+",���ܰ�ʦ!");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "���ÿ�����ֻ�ܰ�һ��ʦ����");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(vo.getLast_shoutu_time())){
			setMessage(request, "�Է�Ŀǰ������ͽ��");
			return mapping.findForward(ERROR);
		}
		FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
		if (fv != null && fv.getRelation() != 0)
		{
			setMessage(request, "�Բ������ͶԷ���"
					+ (fv.getRelation() == 1 ? "����" : "���") + "��ϵ�����ܰݶԷ�Ϊʦ");
			return mapping.findForward(ERROR);
		}
		String message = bi.getName()
				+ "�����Ϊʦ.<br/>"
				+ "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/baishi.do")+"\">"
				+ "<postfield name=\"cmd\" value=\"n5\" />"
				+ "<postfield name=\"id\" value=\""+id+"\" />"
				+ " <postfield name=\"stu_id\" value=\""
				+ bi.getPPk()
				+ "\" />"
				+ "<postfield name=\"caozuo\" value=\"0\" />"
				+ " </go>"
				+ "ͬ��</anchor><br/>"
				+ "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/baishi.do")+"\">"
				+ "<postfield name=\"cmd\" value=\"n5\" />"
				+ "<postfield name=\"id\" value=\""+id+"\" />"
				+ "<postfield name=\"stu_id\" value=\"" + bi.getPPk() + "\" />"
				+ "<postfield name=\"caozuo\" value=\"1\" />" + " </go>"
				+ "�ܾ�</anchor><br/>";
		mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "�����Ϊʦ",
				message);
		setMessage(request, "����������" + vo.getPName() + "��ʦ");
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
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(stu_id.trim());
		if(vo==null){
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "���ÿ�����ֻ����һ��ͽ�ܣ�");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(vo.getLast_shoutu_time())){
			setMessage(request, "�Է�Ŀǰ���ܰ�ʦ��");
			return mapping.findForward(ERROR);
		}
		if(Integer.parseInt(caozuo.trim())==1){
			String message1 = "���ܾ���"+vo.getPName()+"Ϊͽ��";
			setMessage(request, message1);
			mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "�ܾ�����Ϊͽ",
					 bi.getName() + "�ܾ�����Ϊͽ");
			return mapping.findForward(ERROR);
		}
		
		List<Shitu> list = shituService.findByTeacher(bi.getPPk());
		
		if (list != null)
		{
			if (list.size() >= ShituConstant.getCANRECCOUNT(bi.getTe_level()))
			{
				setMessage(request, "�Բ�������ͽ����������������"+vo.getPName()+"Ϊͽ��");
				return mapping.findForward(ERROR);
			}
		}
		if (vo.getPGrade() >= ShituConstant.BAISHI_LEVEL_LIMIT)
		{
			setMessage(request, "�Բ��𣬶Է��ȼ���"+ShituConstant.BAISHI_LEVEL_LIMIT+"���ϣ���������Ϊͽ!");
			return mapping.findForward(ERROR);
		}
		FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
		if (fv != null && fv.getRelation() != 0)
		{
			setMessage(request, "�Բ������ͶԷ���"
					+ (fv.getRelation() == 1 ? "����" : "���") + "��ϵ��������ͽ");
			return mapping.findForward(ERROR);
		}
		List<Shitu> shitu = shituService.findByStudent(stu_id.trim());
		if (shitu != null && shitu.size() > 0)
		{
			setMessage(request, "�Բ��𣬶Է��Ѿ���ʦ��������ѡ��ͽ�ɣ�");
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
		String message = "��ϲ�㣬���Ѿ������" + bi.getName() + "�ĵ����ˣ�����Ի�����ºô���<br/>"
				+ "1.	�ɽ���ʦ��ÿ��ֱ�Ӵ���һ��<br/>" + "2.	ʦͽͬʱ���ߣ��ɶ����ô�־����20%�ľ���<br/>"
				+ "3.	��ɸ�������ʱ�ɻ��1.5����������<br/>"
				+ "4.	����ϵͳ���ġ�ʦͽ�����ڲ鿴ʦͽ�����ϵ<br/>";
		
		String message1 = vo.getPName() + "����Ϊʦ���Ժ�һ��һ����ź��㡣";
		setMessage(request, message1);
		mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "ͬ������Ϊͽ",
				message);
		// systemInfoService.insertSystemInfoBySystem(vo.getPPk(),message1);

		// �Ӱ�ʦ�б���ɾ���Լ�
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
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		if(bi.getWrapSpare()<1){
			setMessage(request, "���İ������������������ȡ��");
		}else{
			goodsService.putPropToWrap(bi.getPPk(), ShituConstant.YUANBAOQUAN_ID, ShituConstant.YUANBAOQUAN_COUNT,GameLogManager.G_SYSTEM);
			setMessage(request, "���ɹ���ȡ�ˡ�"+GameConfig.getYuanbaoQuanName()+"����"+ShituConstant.YUANBAOQUAN_COUNT);
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
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		if(bi.getWrapSpare()<1){
			setMessage(request, "���İ������������������ȡ��");
		}else{
			if(mailInfoService.getPersonMailView(mailId)!=null){
			goodsService.putPropToWrap(bi.getPPk(), ShituConstant.STU_DALIBAO, 1,GameLogManager.G_SYSTEM);
			setMessage(request, "���ɹ���ȡ�ˡ���ʦ���������1");
			mailInfoService.deleteMailByid(mailId, bi.getPPk());
			}else{
				setMessage(request, "���Ѿ���ȡ������ʦ�������!");
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
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		if(bi.getWrapSpare()<1){
			setMessage(request, "���İ������������������ȡ��");
		}else{
			goodsService.putPropToWrap(bi.getPPk(), ShituConstant.TEA_DALIBAO, 1,GameLogManager.G_SYSTEM);
			setMessage(request, "���ɹ���ȡ�ˡ���ʦ���������1");
			mailInfoService.deleteMailByid(mailId, bi.getPPk());
		}
		return mapping.findForward(ERROR);
	}
}
