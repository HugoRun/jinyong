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
import com.ben.shitu.service.ShituService;
import com.ben.vo.friend.FriendVO;
import com.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.dao.info.partinfo.PartInfoDao;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.action.menu.BaseAction;
import com.ls.web.service.rank.RankService;

public class ShituAction extends BaseAction
{

	public ActionForward n1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		if (bi.getGrade() < ShituConstant.ZHAOTU_LEVEL_LIMIT)
		{
			setMessage(request, "�Բ���ֻ��" + ShituConstant.ZHAOTU_LEVEL_LIMIT
					+ "�����ϵ���Ҳſ�����ͽ�ܣ�");
			return mapping.findForward(ERROR);
		}
		Object[] args = new Object[] { bi.getPPk() };
		Shitu shitu1 = shituService.findOne(ShituConstant.SHOUTUSQL1, args);
		if (shitu1 != null)
		{
			if (shitu1.getTe_id() != 0)
			{
				setMessage(request, "�Բ������Ǳ��˵�ͽ�ܣ�");
				return mapping.findForward(ERROR);
			}
			else
			{
				shituService.doit(ShituConstant.SHOUTUSQL2, args);
				shituService.removeFromStudent(bi.getPPk());
			}
		}
		int count = shituService.findCount(ShituConstant.SHOUTUSQL
				+ " and s.stu_id!=0", args);
		if (count >= ShituConstant.getCANRECCOUNT(bi.getTe_level()))
		{
			setMessage(request, "�Բ�������ͽ����������");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "���ÿ���������ֻ����һ��ͽ�ܣ�");
			return mapping.findForward(ERROR);
		}
		
		int count1 = shituService.findCount(ShituConstant.SHOUTUSQL
				+ " and s.stu_id=0", args);
		if (count1 <= 0)
		{
			Shitu shitu = new Shitu(0, bi.getPPk(), 0, bi.getName(), null, bi
					.getGrade(), 0, null, null);
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
		List<Shitu> list = shituService.findStudent(nowPage);
		int stuCount = shituService.studentCount();
		departList(request, list, stuCount, nowPage);
		return mapping.findForward(INDEX);
	}

	public ActionForward n3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String id = (String) request.getParameter("id");
		if (stu_id == null || "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}

		RoleEntity roleInfo = roleService.getRoleInfoById(stu_id.trim());
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(stu_id.trim());
		String playerPic = picService.getPlayerPicStr(roleInfo, stu_id.trim());
		setAttribute(request, "roleInfo", roleInfo);
		setAttribute(request, "vo", vo);
		setAttribute(request, "playerPic", playerPic);
		setAttribute(request, "id", id);
		setAttribute(request, "stu_id", stu_id);
		return mapping.findForward("studetail");
	}

	// ��ͽ����
	public ActionForward n4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String id = (String) request.getParameter("id");
		if (stu_id == null || "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(stu_id.trim());
		if(vo!=null&&vo.getPGrade()>=ShituConstant.BAISHI_LEVEL_LIMIT){
			setMessage(request, "�Բ��𣬶Է��ѳ�����ʦ�ȼ���");
			// �Ӱ�ʦ�б���ɾ���Լ�
			new ShituService().delShitu(vo.getPPk());
			return mapping.findForward(ERROR);
		}
		List<Shitu> list = shituService.findByTeacher(bi.getPPk());
		if (list != null)
		{
			if (list.size() >= ShituConstant.getCANRECCOUNT(bi.getTe_level()))
			{
				setMessage(request, "�Բ��������յ�ͽ������������");
				return mapping.findForward(ERROR);
			}
		}
		if (bi.getGrade() < ShituConstant.ZHAOTU_LEVEL_LIMIT)
		{
			setMessage(request, "�Բ���ֻ��" + ShituConstant.ZHAOTU_LEVEL_LIMIT
					+ "�����ϵ���Ҳſ���ͽ��");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "���ÿ���������ֻ����һ��ͽ�ܣ�");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(vo.getLast_shoutu_time())){
			setMessage(request, "�Է�Ŀǰ���ܰ�ʦ");
			return mapping.findForward(ERROR);
		}
		
		List<Shitu> shitu = shituService.findByStudent(stu_id.trim());
		if (shitu != null && shitu.size() > 0)
		{
			setMessage(request, "�Բ��𣬶Է��Ѿ���ʦ��������ѡ��ͽ�ɣ�");
			return n2(mapping, form, request, response);
		}
		FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
		if (fv != null && fv.getRelation() != 0)
		{
			setMessage(request, "�Բ������ͶԷ���"
					+ (fv.getRelation() == 1 ? "����" : "���") + "��ϵ��������ͽ");
			return n2(mapping, form, request, response);
		}
		String message = bi.getName() + "������Ϊͽ.<br/>"
				+ "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/shitu.do")+"\">"
				+ "<postfield name=\"cmd\" value=\"nn4\" />"
				+ "<postfield name=\"id\" value=\"" + id + "\" />"
				+ " <postfield name=\"te_id\" value=\"" + bi.getPPk() + "\" />"
				+ "<postfield name=\"caozuo\" value=\"0\" />" + " </go>"
				+ "ͬ��</anchor><br/>"
				+ "<anchor><go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/shitu.do")+"\">"
				+ "<postfield name=\"cmd\" value=\"nn4\" />"
				+ "<postfield name=\"id\" value=\"" + id + "\" />"
				+ "<postfield name=\"te_id\" value=\"" + bi.getPPk() + "\" />"
				+ "<postfield name=\"caozuo\" value=\"1\" />" + " </go>"
				+ "�ܾ�</anchor><br/>";
		mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "������Ϊͽ",
				message);
		setMessage(request, "����������" + vo.getPName() + "Ϊͽ");
		return mapping.findForward(ERROR);
	}

	public ActionForward nn4(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String te_id = (String) request.getParameter("te_id");
		String id = (String) request.getParameter("id");
		String caozuo = (String) request.getParameter("caozuo");
		if (te_id == null || "".equals(te_id.trim()) || id == null
				|| "".equals(id.trim()) || caozuo == null
				|| "".equals(caozuo.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		BasicInfo bi = getBasicInfo(request);
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(te_id.trim());
		if (vo == null)
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(bi.getLast_shoutu_time())){
			setMessage(request, "���ÿ���������ֻ�ܰ�һ��ʦ����");
			return mapping.findForward(ERROR);
		}
		if(!DateUtil.check(vo.getLast_shoutu_time())){
			setMessage(request, "�Է�Ŀǰ������ͽ");
			return mapping.findForward(ERROR);
		}
		if(Integer.parseInt(caozuo.trim())==1){
			String message1 = "���ܾ���"+vo.getPName()+"Ϊʦ��";
			setMessage(request, message1);
			mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "�ܾ�����Ϊʦ",
					 bi.getName() + "�ܾ�����Ϊʦ");
			return mapping.findForward(ERROR);
		}
		List<Shitu> list = shituService.findByTeacher(vo.getPPk());
		if (list != null)
		{
			if (list.size() >= ShituConstant.getCANRECCOUNT(vo.getTe_level()))
			{
				setMessage(request, "�Բ��𣬶Է����յ�ͽ������������������Ͷ��ʦ�ɣ�");
				return mapping.findForward(ERROR);
			}
		}
		if (vo.getPGrade() < ShituConstant.ZHAOTU_LEVEL_LIMIT)
		{
			setMessage(request, "�Բ��𣬶Է��ȼ�δ��" + ShituConstant.ZHAOTU_LEVEL_LIMIT
					+ "��");
			return mapping.findForward(ERROR);
		}
		List<Shitu> shitu = shituService.findByStudent(bi.getPPk() + "");
		if (shitu != null && shitu.size() > 0)
		{
			setMessage(request, "�Բ������Ѿ���ʦ���ˣ�");
			return mapping.findForward(ERROR);
		}
		FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
		if (fv != null && fv.getRelation() != 0)
		{
			setMessage(request, "�Բ������ͶԷ���"
					+ (fv.getRelation() == 1 ? "����" : "���") + "��ϵ�����ܰ�ʦ");
			return mapping.findForward(ERROR);
		}
		Object[] args = new Object[] { vo.getPPk(), vo.getPName(),
				vo.getPGrade(), id };
		shituService.doit(ShituConstant.SHOUTUSQL3, args);
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

		String message = vo.getPName() + "����Ϊͽ����Ҫһ��һ���̷�����";
		String message1 = "��ϲ�㣬����ʽ��" + bi.getName() + "Ϊͽ������Ի�����ºô���<br/>" +

		"1.         ʦ����ÿ���ֱ�Ӹ�ͽ�ܴ���һ��<br/>" +

		"2.         ͽ��ÿ��5����ʦ���ɻ�������;���Ƚ���<br/>" +

		"3.         ͽ�ܳ�ʦ��ʦ���ɻ����������<br/>" +

		"4.         ͽ�ܵ�һ�μ������ɺͰ�ᣬʦ���ɻ�á�"+GameConfig.getYuanbaoQuanName()+"����500<br/>" +

		"5.         ����ϵͳ���ġ�ʦͽ�����ڲ鿴ʦͽ�����ϵ<br/>";
		setMessage(request, message);
		mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "����Ϊʦ",
				message1);
		// �Ӱ�ʦ�б���ɾ���Լ�
		shituService.doit(ShituConstant.SHOUTUSQL4,
				new Object[] { vo.getPPk() });
		shituService.remove(bi.getName(), vo.getPName());
		return mapping.findForward(ERROR);
	}

	// ʦͽ��ϵ
	public ActionForward n5(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		BasicInfo bi = getBasicInfo(request);
		if (bi == null)
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		List<Shitu> teacher = shituService.findByStudent(bi.getPPk());
		List<Shitu> student = shituService.findByTeacher(bi.getPPk());
		// �Լ���ʦ�����ж��ͽ��
		if (student != null && student.size() > 0)
		{
			if (teacher != null && teacher.size() > 0)
			{
				shituService.delAsStudent(bi.getPPk());
			}
			setAttribute(request, "list", student);
			return mapping.findForward("asteacher");
		}

		// �Լ���ͽ�ܣ�ֻ��һ��ʦ��
		if (teacher != null && teacher.size() > 0)
		{
			if (teacher.size() > 1)
			{
				shituService.delAsStudent(bi.getPPk());
				setMessage(
						request,
						"���ʦͽ��ϵ���������²�����<br/>"
								+ "<anchor><go href=\""+GameConfig.getContextPath()+"/jsp/function/function.jsp\" method=\"get\"></go>����</anchor>");
				return mapping.findForward(ERROR);
			}
			else
			{
				Shitu shitu = teacher.get(0);
				if (shitu == null)
				{
					setMessage(
							request,
							"�Բ����㲻����ʦͽ��ϵ�����ɲ����������ݣ�<br/>"
									+ "<anchor><go href=\""+GameConfig.getContextPath()+"/jsp/function/function.jsp\" method=\"get\"/>����</anchor>");
					return mapping.findForward(ERROR);
				}
				RoleEntity roleInfo = roleService.getRoleInfoById(shitu
						.getTe_id()
						+ "");
				PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(shitu
						.getTe_id()
						+ "");
				String playerPic = picService.getPlayerPicStr(roleInfo, shitu
						.getTe_id()
						+ "");
				setAttribute(request, "roleInfo", roleInfo);
				setAttribute(request, "vo", vo);
				setAttribute(request, "playerPic", playerPic);
				setAttribute(request, "id", shitu.getId());
				setAttribute(request, "te_id", shitu.getTe_id());
				return mapping.findForward("asstudent");
			}
		}
		setMessage(
				request,
				"�Բ����㲻����ʦͽ��ϵ�����ɲ����������ݣ�<br/>"
						+ "<anchor><go href=\""+GameConfig.getContextPath()+"/jsp/function/function.jsp\" method=\"get\"/>����</anchor>");
		return mapping.findForward(ERROR);
	}

	public ActionForward n6(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String id = (String) request.getParameter("id");
		if (stu_id == null || "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}

		RoleEntity roleInfo = roleService.getRoleInfoById(stu_id.trim());
		PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(stu_id.trim());
		String playerPic = picService.getPlayerPicStr(roleInfo, stu_id.trim());
		setAttribute(request, "roleInfo", roleInfo);
		setAttribute(request, "vo", vo);
		setAttribute(request, "playerPic", playerPic);
		setAttribute(request, "id", id);
		setAttribute(request, "stu_id", stu_id);
		return mapping.findForward("studetaill");
	}

	// ���ʦͽ��ϵ��ת
	public ActionForward n7(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String stu_name = (String) request.getParameter("stu_name");
		String id = (String) request.getParameter("id");
		if (stu_name == null || "".equals(stu_name.trim()) || stu_id == null
				|| "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		else
		{
			setAttribute(request, "stu_name", stu_name);
			setAttribute(request, "stu_id", stu_id);
			setAttribute(request, "id", id);
			return mapping.findForward("jiechu");
		}
	}

	// ���ʦͽ��ϵ����
	public ActionForward n8(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String id = (String) request.getParameter("id");
		if (stu_id == null || "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		else
		{
			Shitu shitu = shituService.findById(id);
			if (shitu != null)
			{
				BasicInfo bi = getBasicInfo(request);
				bi.addEvilValue(ShituConstant.TEA_ZUIE);
				shituService.delbyId(id);
				String message = "���" + bi.getName() + "��������ʦͽ��ϵ��";
				request.setAttribute("message1", "���ʦͽ��ϵ�ɹ�");
				mailInfoService.sendMailBySystem(Integer
						.parseInt(stu_id.trim()), message, message);
			}
			return n5(mapping, form, request, response);
		}
	}

	public ActionForward n9(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String te_id = (String) request.getParameter("te_id");
		String te_name = (String) request.getParameter("te_name");
		String id = (String) request.getParameter("id");
		if (te_name == null || "".equals(te_name.trim()) || te_id == null
				|| "".equals(te_id.trim()) || id == null
				|| "".equals(id.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		else
		{
			setAttribute(request, "te_name", te_name);
			setAttribute(request, "te_id", te_id);
			setAttribute(request, "id", id);
			return mapping.findForward("stujiechu");
		}
	}

	public ActionForward n10(ActionMapping mapping, ActionForm form,
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
		else
		{
			Shitu shitu = shituService.findById(id);
			if (shitu != null)
			{
				BasicInfo bi = getBasicInfo(request);
				bi.addEvilValue(ShituConstant.STU_ZUIE);
				shituService.delbyId(id);
				String message = "���" + bi.getName() + "��������ʦͽ��ϵ��";
				request.setAttribute("message1", "���ʦͽ��ϵ�ɹ�");
				mailInfoService.sendMailBySystem(
						Integer.parseInt(te_id.trim()), message, message);
			}
			return n5(mapping, form, request, response);
		}
	}

	// ������ת
	public ActionForward n11(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String stu_name = (String) request.getParameter("stu_name");
		String id = (String) request.getParameter("id");
		String stu_level = (String) request.getParameter("stu_level");
		if (stu_name == null || "".equals(stu_name.trim()) || stu_id == null
				|| "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()) || stu_level == null
				|| "".equals(stu_level.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		else
		{
			BasicInfo bi = getBasicInfo(request);
			int teaEXP = ShituConstant.getTeaEXP(bi, Integer.parseInt(stu_level
					.trim()));
			setAttribute(request, "teaEXP", teaEXP);
			setAttribute(request, "stu_id", stu_id);
			setAttribute(request, "stu_name", stu_name);
			setAttribute(request, "id", id);
			return mapping.findForward("chuangong");
		}
	}

	// ��������
	public ActionForward n12(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String stu_id = (String) request.getParameter("stu_id");
		String id = (String) request.getParameter("id");
		String stu_name = (String) request.getParameter("stu_name");
		if (stu_id == null || "".equals(stu_id.trim()) || id == null
				|| "".equals(id.trim()) || stu_name == null
				|| "".equals(stu_name.trim()))
		{
			setMessage(request, "�����ˣ������²���");
			return mapping.findForward(ERROR);
		}
		else
		{
			Shitu shitu = shituService.findById(id);
			if (shitu == null)
			{
				setMessage(request, "�����ˣ������²���");
				return mapping.findForward(ERROR);
			}
			BasicInfo bi = getBasicInfo(request);
			if (!DateUtil.checkTime(bi.getChuangong()))
			{
				// һ��ֻ�ܴ���һ��
				setMessage(request, "�Բ�����ÿ��ֻ�ܸ�һ��ͽ�ܴ���һ�Σ�");
			}
			else
			{
				PartInfoVO vo = (PartInfoVO) partInfoDAO.getPartView(stu_id
						.trim());
				if (vo.getPGrade() == 19 || vo.getPGrade() == 39)
				{
					setMessage(request, "�Բ������ͽ��" + vo.getPName()
							+ "����תְ״̬�����ܴ�����");
				}
				else
				{
					int teaEXP = (bi.getGrade()==ShituConstant.MAX_LEVEL?ShituConstant.getTeaExpMax(vo.getPGrade()):ShituConstant.getTeaEXP(bi, vo.getPGrade()));
					int curExp = ((bi.getCurExp() == null || "".equals(bi
							.getCurExp())) ? 0 : Integer.parseInt(bi
							.getCurExp().trim()));
					if (curExp < teaEXP)
					{
						setMessage(request, "�㱾�����鲻���Դ��������ͽ��" + stu_name + "��");
					}
					else
					{
						bi.updateChuangong();
						bi.updateAddCurExp(-teaEXP);
						long stuExp = ShituConstant.getStuExp(teaEXP);
						if((vo.getPGrade()==19||vo.getPGrade()==39)&&((stuExp+Long.parseLong(vo.getPExperience().trim()))>Long.parseLong(vo.getPXiaExperience().trim()))){
							stuExp = Long.parseLong(vo.getPXiaExperience().trim())-Long.parseLong(vo.getPExperience().trim());
						}
						propertyService.updateAddExpProperty(vo.getPPk(),
								stuExp);
						creditProce.addPlayerCredit(bi.getPPk(),
								ShituConstant.CREDIT_ID,
								ShituConstant.CREDIT_COUNT);
						//ͳ����Ҫ
						new RankService().updateAdd(bi.getPPk(), "credit", ShituConstant.CREDIT_COUNT);
						
						String message = "�����ʦ" + bi.getName() + "���㴫�������þ���"
								+ stuExp + "��";
						mailInfoService.sendMailBySystem(vo.getPPk(), "�����ʦ"
								+ bi.getName() + "���㴫��", message);
						setMessage(request, "�㽫����" + teaEXP + "���鴫����ͽ��"
								+ vo.getPName() + "��ͽ��" + vo.getPName()
								+ "��þ���" + stuExp + "��<br/>���ý���������������"
								+ ShituConstant.CREDIT_COUNT);
					}
				}
			}
			return mapping.findForward("chuangongok");
		}
	}
}
