package com.ben.shitu.action;

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
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShituAction extends BaseAction {

    public ActionForward n1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BasicInfo bi = getBasicInfo(request);
        if (bi.getGrade() < ShituConstant.ZHAOTU_LEVEL_LIMIT) {
            setMessage(request, "对不起，只有" + ShituConstant.ZHAOTU_LEVEL_LIMIT + "级以上的玩家才可招收徒弟！");
            return mapping.findForward(ERROR);
        }
        Object[] args = new Object[]{bi.getPPk()};
        Shitu shitu1 = shituService.findOne(ShituConstant.SHOUTUSQL1, args);
        if (shitu1 != null) {
            if (shitu1.getTe_id() != 0) {
                setMessage(request, "对不起，您是别人的徒弟！");
                return mapping.findForward(ERROR);
            } else {
                shituService.doit(ShituConstant.SHOUTUSQL2, args);
                shituService.removeFromStudent(bi.getPPk());
            }
        }
        int count = shituService.findCount(ShituConstant.SHOUTUSQL + " AND s.stu_id!=0", args);
        if (count >= ShituConstant.getCANRECCOUNT(bi.getTe_level())) {
            setMessage(request, "对不起，你收徒名额已满！");
            return mapping.findForward(ERROR);
        }
        if (!DateUtil.check(bi.getLast_shoutu_time())) {
            setMessage(request, "玩家每七天中最多只能收一次徒弟！");
            return mapping.findForward(ERROR);
        }

        int count1 = shituService.findCount(ShituConstant.SHOUTUSQL + " AND s.stu_id=0", args);
        if (count1 <= 0) {
            Shitu shitu = new Shitu(0, bi.getPPk(), 0, bi.getName(), null, bi.getGrade(), 0, null, null);
            shituService.addShitu(shitu);
        }
        return n2(mapping, form, request, response);
    }

    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        int nowPage = Integer.parseInt(request.getParameter("nowPage") == null ? "1" : request.getParameter("nowPage").trim());
        List<Shitu> list = shituService.findStudent(nowPage);
        int stuCount = shituService.studentCount();
        departList(request, list, stuCount, nowPage);
        return mapping.findForward(INDEX);
    }

    public ActionForward n3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String id = request.getParameter("id");
        if (stu_id == null || stu_id.trim().isEmpty() || id == null || id.trim().isEmpty()) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        }

        RoleEntity roleInfo = RoleService.getRoleInfoById(stu_id.trim());
        PartInfoVO vo = partInfoDAO.getPartView(stu_id.trim());
        String playerPic = picService.getPlayerPicStr(roleInfo, stu_id.trim());
        setAttribute(request, "roleInfo", roleInfo);
        setAttribute(request, "vo", vo);
        setAttribute(request, "playerPic", playerPic);
        setAttribute(request, "id", id);
        setAttribute(request, "stu_id", stu_id);
        return mapping.findForward("studetail");
    }

    // 招徒操作
    public ActionForward n4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String id = request.getParameter("id");
        if (stu_id == null || "".equals(stu_id.trim()) || id == null || "".equals(id.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        }
        BasicInfo bi = getBasicInfo(request);
        PartInfoVO vo = partInfoDAO.getPartView(stu_id.trim());
        if (vo != null && vo.getPGrade() >= ShituConstant.BAISHI_LEVEL_LIMIT) {
            setMessage(request, "对不起，对方已超过拜师等级！");
            // 从拜师列表中删除自己
            new ShituService().delShitu(vo.getPPk());
            return mapping.findForward(ERROR);
        }
        List<Shitu> list = shituService.findByTeacher(bi.getPPk());
        if (list != null) {
            if (list.size() >= ShituConstant.getCANRECCOUNT(bi.getTe_level())) {
                setMessage(request, "对不起，您招收的徒弟名额已满！");
                return mapping.findForward(ERROR);
            }
        }
        if (bi.getGrade() < ShituConstant.ZHAOTU_LEVEL_LIMIT) {
            setMessage(request, "对不起，只有" + ShituConstant.ZHAOTU_LEVEL_LIMIT + "级以上的玩家才可招徒！");
            return mapping.findForward(ERROR);
        }
        if (!DateUtil.check(bi.getLast_shoutu_time())) {
            setMessage(request, "玩家每七天中最多只能收一次徒弟！");
            return mapping.findForward(ERROR);
        }
        if (!DateUtil.check(vo.getLast_shoutu_time())) {
            setMessage(request, "对方目前不能拜师");
            return mapping.findForward(ERROR);
        }

        List<Shitu> shitu = shituService.findByStudent(stu_id.trim());
        if (shitu != null && shitu.size() > 0) {
            setMessage(request, "对不起，对方已经拜师，请你另选贤徒吧！");
            return n2(mapping, form, request, response);
        }
        FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
        if (fv != null && fv.getRelation() != 0) {
            setMessage(request, "对不起，您和对方是" + (fv.getRelation() == 1 ? "结义" : "结婚") + "关系，不能收徒");
            return n2(mapping, form, request, response);
        }
        String message = bi.getName() + "想招你为徒.<br/>" + "<anchor><go method=\"post\" href=\"" + response.encodeURL(GameConfig.getContextPath() + "/shitu.do") + "\">" + "<postfield name=\"cmd\" value=\"nn4\" />" + "<postfield name=\"id\" value=\"" + id + "\" />" + " <postfield name=\"te_id\" value=\"" + bi.getPPk() + "\" />" + "<postfield name=\"caozuo\" value=\"0\" />" + " </go>" + "同意</anchor><br/>" + "<anchor><go method=\"post\" href=\"" + response.encodeURL(GameConfig.getContextPath() + "/shitu.do") + "\">" + "<postfield name=\"cmd\" value=\"nn4\" />" + "<postfield name=\"id\" value=\"" + id + "\" />" + "<postfield name=\"te_id\" value=\"" + bi.getPPk() + "\" />" + "<postfield name=\"caozuo\" value=\"1\" />" + " </go>" + "拒绝</anchor><br/>";
        mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "想招你为徒", message);
        setMessage(request, "您已申请招" + vo.getPName() + "为徒");
        return mapping.findForward(ERROR);
    }

    public ActionForward nn4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

        String te_id = request.getParameter("te_id");
        String id = request.getParameter("id");
        String caozuo = request.getParameter("caozuo");
        if (te_id == null || "".equals(te_id.trim()) || id == null || "".equals(id.trim()) || caozuo == null || "".equals(caozuo.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        }
        BasicInfo bi = getBasicInfo(request);
        PartInfoVO vo = partInfoDAO.getPartView(te_id.trim());
        if (vo == null) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        }
        if (!DateUtil.check(bi.getLast_shoutu_time())) {
            setMessage(request, "玩家每七天中最多只能拜一次师傅！");
            return mapping.findForward(ERROR);
        }
        if (!DateUtil.check(vo.getLast_shoutu_time())) {
            setMessage(request, "对方目前不能收徒");
            return mapping.findForward(ERROR);
        }
        if (Integer.parseInt(caozuo.trim()) == 1) {
            String message1 = "您拒绝拜" + vo.getPName() + "为师！";
            setMessage(request, message1);
            mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "拒绝拜您为师", bi.getName() + "拒绝拜您为师");
            return mapping.findForward(ERROR);
        }
        List<Shitu> list = shituService.findByTeacher(vo.getPPk());
        if (list != null) {
            if (list.size() >= ShituConstant.getCANRECCOUNT(vo.getTe_level())) {
                setMessage(request, "对不起，对方招收的徒弟名额已满，请你另投名师吧！");
                return mapping.findForward(ERROR);
            }
        }
        if (vo.getPGrade() < ShituConstant.ZHAOTU_LEVEL_LIMIT) {
            setMessage(request, "对不起，对方等级未满" + ShituConstant.ZHAOTU_LEVEL_LIMIT + "！");
            return mapping.findForward(ERROR);
        }
        List<Shitu> shitu = shituService.findByStudent(bi.getPPk() + "");
        if (shitu != null && shitu.size() > 0) {
            setMessage(request, "对不起，您已经有师傅了！");
            return mapping.findForward(ERROR);
        }
        FriendVO fv = friendService.viewfriend(bi.getPPk(), vo.getPPk() + "");
        if (fv != null && fv.getRelation() != 0) {
            setMessage(request, "对不起，您和对方是" + (fv.getRelation() == 1 ? "结义" : "结婚") + "关系，不能拜师");
            return mapping.findForward(ERROR);
        }
        Object[] args = new Object[]{vo.getPPk(), vo.getPName(), vo.getPGrade(), id};
        shituService.doit(ShituConstant.SHOUTUSQL3, args);
        PartInfoDao pid = new PartInfoDao();
        bi.setLast_shoutu_time();
        pid.updateLastTime(vo.getPPk());
        pid.updateLastTime(bi.getPPk());
        RoleEntity re = RoleService.getRoleInfoById(vo.getPPk() + "");
        if (re != null) {
            re.getBasicInfo().setLast_shoutu_time();
        }
        if (fv == null) {
            friendService.addfriend(bi.getPPk(), vo.getPPk() + "", vo.getPName(), getTime());
        }
        FriendVO fv1 = friendService.viewfriend(vo.getPPk(), bi.getPPk() + "");
        if (fv1 == null) {
            friendService.addfriend(vo.getPPk(), bi.getPPk() + "", bi.getName(), getTime());
        }

        String message = vo.getPName() + "收你为徒，你要一心一意侍奉他。";
        String message1 = "恭喜你，你正式收" + bi.getName() + "为徒！你可以获得以下好处：<br/>" +

                "1.         师傅可每天可直接给徒弟传功一次<br/>" +

                "2.         徒弟每升5级，师傅可获得银两和经验等奖励<br/>" +

                "3.         徒弟出师后，师傅可获得其他奖励<br/>" +

                "4.         徒弟第一次加入门派和帮会，师傅可获得【" + GameConfig.getYuanbaoQuanName() + "】×500<br/>" +

                "5.         可在系统栏的“师徒管理”内查看师徒各项关系<br/>";
        setMessage(request, message);
        mailInfoService.sendMailBySystem(vo.getPPk(), bi.getName() + "拜您为师", message1);
        // 从拜师列表中删除自己
        shituService.doit(ShituConstant.SHOUTUSQL4, new Object[]{vo.getPPk()});
        shituService.remove(bi.getName(), vo.getPName());
        return mapping.findForward(ERROR);
    }

    // 师徒关系
    public ActionForward n5(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        BasicInfo bi = getBasicInfo(request);
        if (bi == null) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        }
        List<Shitu> teacher = shituService.findByStudent(bi.getPPk());
        List<Shitu> student = shituService.findByTeacher(bi.getPPk());
        // 自己是师父，有多个徒弟
        if (student != null && student.size() > 0) {
            if (teacher != null && teacher.size() > 0) {
                shituService.delAsStudent(bi.getPPk());
            }
            setAttribute(request, "list", student);
            return mapping.findForward("asteacher");
        }

        // 自己是徒弟，只有一个师父
        if (teacher != null && teacher.size() > 0) {
            if (teacher.size() > 1) {
                shituService.delAsStudent(bi.getPPk());
                setMessage(request, "你的师徒关系出错，请重新操作！<br/>" + "<anchor><go href=\"" + GameConfig.getContextPath() + "/jsp/function/function.jsp\" method=\"get\"></go>返回</anchor>");
                return mapping.findForward(ERROR);
            } else {
                Shitu shitu = teacher.get(0);
                if (shitu == null) {
                    setMessage(request, "对不起，你不存在师徒关系，不可操作此项内容！<br/>" + "<anchor><go href=\"" + GameConfig.getContextPath() + "/jsp/function/function.jsp\" method=\"get\"/>返回</anchor>");
                    return mapping.findForward(ERROR);
                }
                RoleEntity roleInfo = RoleService.getRoleInfoById(shitu.getTe_id() + "");
                PartInfoVO vo = partInfoDAO.getPartView(shitu.getTe_id() + "");
                String playerPic = picService.getPlayerPicStr(roleInfo, shitu.getTe_id() + "");
                setAttribute(request, "roleInfo", roleInfo);
                setAttribute(request, "vo", vo);
                setAttribute(request, "playerPic", playerPic);
                setAttribute(request, "id", shitu.getId());
                setAttribute(request, "te_id", shitu.getTe_id());
                return mapping.findForward("asstudent");
            }
        }
        setMessage(request, "对不起，你不存在师徒关系，不可操作此项内容！<br/>" + "<anchor><go href=\"" + GameConfig.getContextPath() + "/jsp/function/function.jsp\" method=\"get\"/>返回</anchor>");
        return mapping.findForward(ERROR);
    }

    public ActionForward n6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String id = request.getParameter("id");
        if (stu_id == null || "".equals(stu_id.trim()) || id == null || "".equals(id.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        }

        RoleEntity roleInfo = RoleService.getRoleInfoById(stu_id.trim());
        PartInfoVO vo = partInfoDAO.getPartView(stu_id.trim());
        String playerPic = picService.getPlayerPicStr(roleInfo, stu_id.trim());
        setAttribute(request, "roleInfo", roleInfo);
        setAttribute(request, "vo", vo);
        setAttribute(request, "playerPic", playerPic);
        setAttribute(request, "id", id);
        setAttribute(request, "stu_id", stu_id);
        return mapping.findForward("studetaill");
    }

    // 解除师徒关系跳转
    public ActionForward n7(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String stu_name = request.getParameter("stu_name");
        String id = request.getParameter("id");
        if (stu_name == null || "".equals(stu_name.trim()) || stu_id == null || "".equals(stu_id.trim()) || id == null || "".equals(id.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        } else {
            setAttribute(request, "stu_name", stu_name);
            setAttribute(request, "stu_id", stu_id);
            setAttribute(request, "id", id);
            return mapping.findForward("jiechu");
        }
    }

    // 解除师徒关系操作
    public ActionForward n8(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String id = request.getParameter("id");
        if (stu_id == null || "".equals(stu_id.trim()) || id == null || "".equals(id.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        } else {
            Shitu shitu = shituService.findById(id);
            if (shitu != null) {
                BasicInfo bi = getBasicInfo(request);
                bi.addEvilValue(ShituConstant.TEA_ZUIE);
                shituService.delbyId(id);
                String message = "玩家" + bi.getName() + "与你解除了师徒关系！";
                request.setAttribute("message1", "解除师徒关系成功");
                mailInfoService.sendMailBySystem(Integer.parseInt(stu_id.trim()), message, message);
            }
            return n5(mapping, form, request, response);
        }
    }

    public ActionForward n9(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String te_id = request.getParameter("te_id");
        String te_name = request.getParameter("te_name");
        String id = request.getParameter("id");
        if (te_name == null || "".equals(te_name.trim()) || te_id == null || "".equals(te_id.trim()) || id == null || "".equals(id.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        } else {
            setAttribute(request, "te_name", te_name);
            setAttribute(request, "te_id", te_id);
            setAttribute(request, "id", id);
            return mapping.findForward("stujiechu");
        }
    }

    public ActionForward n10(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String te_id = request.getParameter("te_id");
        String id = request.getParameter("id");
        if (te_id == null || "".equals(te_id.trim()) || id == null || "".equals(id.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        } else {
            Shitu shitu = shituService.findById(id);
            if (shitu != null) {
                BasicInfo bi = getBasicInfo(request);
                bi.addEvilValue(ShituConstant.STU_ZUIE);
                shituService.delbyId(id);
                String message = "玩家" + bi.getName() + "与你解除了师徒关系！";
                request.setAttribute("message1", "解除师徒关系成功");
                mailInfoService.sendMailBySystem(Integer.parseInt(te_id.trim()), message, message);
            }
            return n5(mapping, form, request, response);
        }
    }

    // 传功跳转
    public ActionForward n11(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String stu_name = request.getParameter("stu_name");
        String id = request.getParameter("id");
        String stu_level = request.getParameter("stu_level");
        if (stu_name == null || "".equals(stu_name.trim()) || stu_id == null || "".equals(stu_id.trim()) || id == null || "".equals(id.trim()) || stu_level == null || "".equals(stu_level.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        } else {
            BasicInfo bi = getBasicInfo(request);
            int teaEXP = ShituConstant.getTeaEXP(bi, Integer.parseInt(stu_level.trim()));
            setAttribute(request, "teaEXP", teaEXP);
            setAttribute(request, "stu_id", stu_id);
            setAttribute(request, "stu_name", stu_name);
            setAttribute(request, "id", id);
            return mapping.findForward("chuangong");
        }
    }

    // 传功操作
    public ActionForward n12(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        String stu_id = request.getParameter("stu_id");
        String id = request.getParameter("id");
        String stu_name = request.getParameter("stu_name");
        if (stu_id == null || "".equals(stu_id.trim()) || id == null || "".equals(id.trim()) || stu_name == null || "".equals(stu_name.trim())) {
            setMessage(request, "出错了，请重新操作");
            return mapping.findForward(ERROR);
        } else {
            Shitu shitu = shituService.findById(id);
            if (shitu == null) {
                setMessage(request, "出错了，请重新操作");
                return mapping.findForward(ERROR);
            }
            BasicInfo bi = getBasicInfo(request);
            if (!DateUtil.checkTime(bi.getChuangong())) {
                // 一天只能传功一次
                setMessage(request, "对不起，你每天只能给一个徒弟传功一次！");
            } else {
                PartInfoVO vo = partInfoDAO.getPartView(stu_id.trim());
                if (vo.getPGrade() == 19 || vo.getPGrade() == 39) {
                    setMessage(request, "对不起，你的徒弟" + vo.getPName() + "处于转职状态，不能传功！");
                } else {
                    int teaEXP = (bi.getGrade() == ShituConstant.MAX_LEVEL ? ShituConstant.getTeaExpMax(vo.getPGrade()) : ShituConstant.getTeaEXP(bi, vo.getPGrade()));
                    int curExp = ((bi.getCurExp() == null || "".equals(bi.getCurExp())) ? 0 : Integer.parseInt(bi.getCurExp().trim()));
                    if (curExp < teaEXP) {
                        setMessage(request, "你本级经验不足以传功给你的徒弟" + stu_name + "！");
                    } else {
                        bi.updateChuangong();
                        bi.updateAddCurExp(-teaEXP);
                        long stuExp = ShituConstant.getStuExp(teaEXP);
                        if ((vo.getPGrade() == 19 || vo.getPGrade() == 39) && ((stuExp + Long.parseLong(vo.getPExperience().trim())) > Long.parseLong(vo.getPXiaExperience().trim()))) {
                            stuExp = Long.parseLong(vo.getPXiaExperience().trim()) - Long.parseLong(vo.getPExperience().trim());
                        }
                        propertyService.updateAddExpProperty(vo.getPPk(), stuExp);
                        creditProce.addPlayerCredit(bi.getPPk(), ShituConstant.CREDIT_ID, ShituConstant.CREDIT_COUNT);
                        //统计需要
                        new RankService().updateAdd(bi.getPPk(), "credit", ShituConstant.CREDIT_COUNT);

                        String message = "你的老师" + bi.getName() + "给你传功，你获得经验" + stuExp + "！";
                        mailInfoService.sendMailBySystem(vo.getPPk(), "你的老师" + bi.getName() + "给你传功", message);
                        setMessage(request, "你将本级" + teaEXP + "经验传功给徒弟" + vo.getPName() + "。徒弟" + vo.getPName() + "获得经验" + stuExp + "！<br/>你获得奖励：江湖声望×" + ShituConstant.CREDIT_COUNT);
                    }
                }
            }
            return mapping.findForward("chuangongok");
        }
    }
}
