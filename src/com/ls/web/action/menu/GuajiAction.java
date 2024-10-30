package com.ls.web.action.menu;

import com.ben.guaji.vo.GetGoodVo;
import com.ben.guaji.vo.GoodVo;
import com.ben.guaji.vo.GuaJiConstant;
import com.ben.guaji.vo.GuajiVo;
import com.ben.shitu.model.DateUtil;
import com.ben.shitu.model.ShituConstant;
import com.ls.ben.cache.staticcache.npc.NpcCache;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.user.BasicInfo;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.GoodsType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.room.RoomService;
import com.web.jieyi.util.Constant;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

public class GuajiAction extends BaseAction {

    public ActionForward n1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            SceneVO sceneVo = guajiService.findByOwnLevel(bi.getGrade());

            setAttribute(request, "scenevo", sceneVo);
            return mapping.findForward("n1");
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    public ActionForward n2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String scene_id = request.getParameter("scene_id");
            if (scene_id == null || "".equals(scene_id.trim())) {
                return n1(mapping, form, request, response);
            }
            BasicInfo bi = getBasicInfo(request);
            SceneVO curScene = roomService.getById(bi.getSceneId());
            if (curScene != null && curScene.getSceneLimit() != null && curScene.getSceneLimit().indexOf(RoomService.NOT_CARRY_OUT) != -1) {
                setMessage(request, "对不起，目前该地点不允许传出！");
                return mapping.findForward(ERROR);
            }
            SceneVO toScene = roomService.getById(scene_id);
            if (toScene != null && toScene.getSceneLimit() != null && toScene.getSceneLimit().indexOf(RoomService.NOT_CARRY_IN) != -1) {
                setMessage(request, "对不起，" + toScene.getSceneName() + "地点不允许传入！");
                return mapping.findForward(ERROR);
            }
            bi.updateSceneId(scene_id);
            return mapping.findForward("no_refurbish_scene");
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 挂机
    public ActionForward n3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            if (bi.getGrade() == 19 || bi.getGrade() == 39 || bi.getGrade() == 59 || bi.getGrade() == 69 || bi.getGrade() == 79) {
                setMessage(request, "对不起，您正在转职状态，不能挂机！");
                return mapping.findForward(ERROR);
            }
            SceneVO curScene = roomService.getById(bi.getSceneId());
            if (curScene == null) {
                setMessage(request, "对不起，请重新操作！");
                return mapping.findForward(ERROR);
            }
            if (curScene.getMap().getMapType() != 2) {
                setMessage(request, "对不起，该地点不允许挂机。请前往冒险区域离线挂机！");
                return mapping.findForward(ERROR);
            }
            if (!in(curScene)) {
                setMessage(request, "对不起，该地点不允许挂机");
                return mapping.findForward(ERROR);
            }
            NpcVO npc = guajiService.findSceneOgreBySceneId(curScene.getSceneID(), bi.getGrade());
            if (npc == null) {
                setMessage(request, "对不起,该地点没有怪物,或者您等级太低不能挂机.请前往其他区域离线挂机！");
                return mapping.findForward(ERROR);
            }

            if (npc.getLevel() > bi.getGrade()) {
                setMessage(request, "你等级太低，不能在此挂机！你只能在刷新怪物等级不高于角色等级的地点进行离线挂机！");
                return mapping.findForward(ERROR);
            }
            GuajiVo guajiVo = new GuajiVo(bi.getPPk(), npc.getNpcID());
            GuaJiConstant.GUAJIVO.put(bi.getPPk(), guajiVo);
            return nn3(mapping, form, request, response);
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    private Boolean in(SceneVO curScene) {
        if (curScene == null || curScene.getMap() == null) {
            return false;
        }
        if (curScene.getMap().getMapID() == 5 || curScene.getMap().getMapID() == 6 || curScene.getMap().getMapID() == 7) {
            return false;
        }
        return !GuaJiConstant.CAN_NOT_GUAJI.contains(curScene.getSceneID() + "");
    }

    public ActionForward nn3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        return mapping.findForward("chose");
    }

    private boolean isFree(Date time) {
        String guaji_control = GameConfig.getPropertiesObject(GuaJiConstant.GUAJI_CONTROL);
        String guaji_week = GameConfig.getPropertiesObject(GuaJiConstant.GUAJI_WEEK);
        String guaji_begin_time = GameConfig.getPropertiesObject(GuaJiConstant.GUAJI_BEGIN_TIME);
        String guaji_end_time = GameConfig.getPropertiesObject(GuaJiConstant.GUAJI_END_TIME);

        if (guaji_control == null || "".equals(guaji_control.trim()) || guaji_week == null || "".equals(guaji_week.trim()) || guaji_begin_time == null || "".equals(guaji_begin_time.trim()) || guaji_end_time == null || "".equals(guaji_end_time.trim())) {
            return false;
        }
        if (Integer.parseInt(guaji_control.trim()) == 1) {
            return false;
        }
        int guaji_weekI = Integer.parseInt(guaji_week.trim());
        int guaji_begin_timeI = Integer.parseInt(guaji_begin_time.trim());
        int guaji_end_timeI = Integer.parseInt(guaji_end_time.trim());
        return DateUtil.isFreeGuaji(guaji_weekI, guaji_begin_timeI, guaji_end_timeI, time);
    }

    // 挂机类型跳转
    public ActionForward n4(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = request.getParameter("type");
            if (type == null || "".equals(type.trim())) {
                type = "0";
            }
            int needMoney = findCostByType(Integer.parseInt(type.trim()));

            setAttribute(request, "sb", "你选择了" + findByType(Integer.parseInt(type.trim())));
            BasicInfo bi = getBasicInfo(request);
            if (isFree(new Date())) {
                //免费挂机
                GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
                guajiVo.setGuaji_type(Integer.parseInt(type.trim()));
                guajiVo.setTime(360);
                setAttribute(request, "message", "你可以免费挂机6小时。（挂机开始前请预留出尽量多的物品栏空位以放置挂机所得物品）");
            } else {
                long yuanbao = economyService.getYuanbao(bi.getUPk());
                int can_GuaJi_time = (int) (yuanbao / needMoney > GuaJiConstant.MAX_GUAJI_TIME ? GuaJiConstant.MAX_GUAJI_TIME : yuanbao / needMoney);
                if (can_GuaJi_time < 1) {
                    String message = "对不起，你的" + GameConfig.getYuanbaoName() + "数量不足以支付挂机消耗！<br/><anchor><go href=\"" + response.encodeURL(GameConfig.getContextPath() + "/sky/bill.do?cmd=n1\"") + " method=\"get\"></go>我要充值</anchor>";
                    setMessage(request, message);
                    return mapping.findForward(ERROR);
                }
                GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
                if (guajiVo == null) {
                    setMessage(request, "请重新操作");
                    return mapping.findForward(ERROR);
                }
                guajiVo.setGuaji_type(Integer.parseInt(type.trim()));
                guajiVo.setTime(can_GuaJi_time);
                String time = can_GuaJi_time > GuaJiConstant.MAX_GUAJI_TIME ? "6小时" : minToString(can_GuaJi_time);
                setAttribute(request, "message", "你现有" + GameConfig.getYuanbaoName() + "×" + yuanbao + "，最长可挂机" + time + "。（挂机开始前请预留出尽量多的物品栏空位以放置挂机所得物品）");
            }
            setAttribute(request, "type", type.trim());
            return mapping.findForward("chosegood");
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    private String minToString(int min) {
        if (min <= 0) {
            return "0分钟";
        } else {
            return ((min / 60 > 0 ? min / 60 + "小时" : "") + (min % 60 > 0 ? min % 60 + "分钟" : ""));
        }
    }

    // 挂机类型跳转
    public ActionForward n5(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
            if (guajiVo == null) {
                setMessage(request, "请重新操作");
                return mapping.findForward(ERROR);
            }
            int nowPage = Integer.parseInt(request.getParameter("nowPage") == null ? "1" : request.getParameter("nowPage").trim());
            List<NpcdropVO> list = guajiService.getNpcdropsByNpcID(guajiVo.getNpc_id(), (nowPage - 1) * Constant.EVERY_PAGE_COUNT, Constant.EVERY_PAGE_COUNT);
            departList(request, list, guajiService.getNpcdropsCountByNpcID(guajiVo.getNpc_id()), nowPage);
            return mapping.findForward("goodlist");
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 拾取掉落物品处理
    public ActionForward n6(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            String good_id = request.getParameter("good_id");
            String good_name = request.getParameter("good_name");
            String good_type = request.getParameter("good_type");
            if (good_id != null && !"".equals(good_id.trim()) && good_name != null && !"".equals(good_name.trim()) && good_type != null && !"".equals(good_type.trim())) {
                GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
                if (guajiVo == null) {
                    setMessage(request, "请重新操作");
                    return mapping.findForward(ERROR);
                }
                Set<GoodVo> set = guajiVo.getGood();
                // for (GoodVo it : set)
                // {
                // System.out.println("拾取的物品id ： " + it.getGood_id()
                // + " 拾取物品名称 " + it.getGood_name() + " 拾取物品类型 "
                // + it.getGood_type());
                // }
                GoodVo goodVo1 = new GoodVo(Integer.parseInt(good_id.trim()), good_name, Integer.parseInt(good_type.trim()));
                set.add(goodVo1);
                setMessage(request, "您选择了拾取" + good_name.trim());
            }
            return n5(mapping, form, request, response);
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 拾取掉落物品品质处理
    public ActionForward n7(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            int pinzhi = 0;
            GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
            if (guajiVo == null) {
                setMessage(request, "请重新操作");
                return mapping.findForward(ERROR);
            }
            Set<GoodVo> set = guajiVo.getGood();
            for (GoodVo it : set) {
                if (it.getGood_type() == GoodsType.EQUIP) {
                    pinzhi++;
                    break;
                }
            }
            if (pinzhi == 0) {
                request.setAttribute("set", set);
                request.setAttribute("pinzhi1", guajiVo.getLevel() + "");
                return mapping.findForward("enter");
            } else {
                return mapping.findForward("pinzhi");
            }
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 拾取掉落物品品质处理
    public ActionForward n8(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = request.getParameter("type");
            if (type == null || "".equals(type.trim())) {
                type = "1";
            }
            BasicInfo bi = getBasicInfo(request);
            GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
            if (guajiVo == null) {
                setMessage(request, "请重新操作");
                return mapping.findForward(ERROR);
            }
            guajiVo.setLevel(Integer.parseInt(type.trim()));
            Set<GoodVo> set = guajiVo.getGood();
            request.setAttribute("set", set);
            request.setAttribute("pinzhi1", type.trim());
            String mess = "";
            switch (Integer.parseInt(type.trim())) {
                case GuaJiConstant.YOU:
                    mess = "仅拾取“优”以上";
                    break;
                case GuaJiConstant.JING:
                    mess = "仅拾取“精”以上";
                    break;
                case GuaJiConstant.JI:
                    mess = "仅拾取“极”以上";
                    break;
                case GuaJiConstant.ALL:
                    mess = "拾取全部";
                    break;
                default:
                    mess = "拾取全部";
                    break;
            }
            request.setAttribute("mess", mess);
            return mapping.findForward("enter");
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 拾取掉落物品品质处理
    public ActionForward n9(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
            if (guajiVo == null) {
                setMessage(request, "请重新操作");
                return mapping.findForward(ERROR);
            }
            guajiVo.setGood(new TreeSet<GoodVo>());
            return n5(mapping, form, request, response);
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 开始挂机
    public ActionForward n10(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            GuajiVo guajiVo = GuaJiConstant.GUAJIVO.get(bi.getPPk());
            if (guajiVo == null) {
                setMessage(request, "请重新操作");
                return mapping.findForward(ERROR);
            }
            try {
                guajiService.addAuto(guajiVo);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                setAttribute(request, "time", minToString(guajiVo.getTime()));
                return mapping.findForward("start");
            }
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    // 开始上线处理
    public ActionForward n11(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            BasicInfo bi = getBasicInfo(request);
            GuajiVo gv = null;
            try {
                gv = guajiService.findByPpk(bi.getPPk());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (gv == null) {
                return mapping.findForward("refurbish_scene");
            } else {
                guajiService.updateEndTime(gv.getId());
                int gua_time = 0;
                StringBuffer sb = new StringBuffer();
                if (isFree(DateUtil.getTimeFormat(gv.getStart_time()))) {
                    gua_time = lookTime(gv, 0, GuaJiConstant.DUIYING.get(gv.getGuaji_type()), true);
                    sb.append("你获得免费挂机" + minToString(gua_time) + "挂机效果。");
//					如果上一次是免费挂机
                } else {
                    // 得到挂机的时间
                    long yuanbao = economyService.getYuanbao(bi.getUPk());
                    gua_time = lookTime(gv, yuanbao, GuaJiConstant.DUIYING.get(gv.getGuaji_type()), false);
                    if (gua_time != 0) {
                        economyService.spendYuanbao(bi.getUPk(), gua_time * GuaJiConstant.DUIYING.get(gv.getGuaji_type()));
                    }
                    sb.append(yuanbao < GuaJiConstant.DUIYING.get(gv.getGuaji_type()) ? "你帐号内没有" + GameConfig.getYuanbaoName() + "支付此次挂机，挂机效果消失！" : ("支付" + gua_time * GuaJiConstant.DUIYING.get(gv.getGuaji_type()) + GameConfig.getYuanbaoName() + ",获得" + minToString(gua_time) + "挂机效果。"));
                }
                // 该npc所有物品掉率总和
                NpcVO npc = NpcCache.getById(gv.getNpc_id());
                String mess = "";
                if (npc == null) {
                    setMessage(request, "挂机时，怪物消失，请联系客服");
                    return mapping.findForward(ERROR);
                }
                int guaji_type = gv.getGuaji_type();
                int getExp = npc.getExp() * gua_time * 2;
                if (npc.getLevel() < bi.getGrade())// npc的等级小于玩家的等级时，有经验衰减，否则就是npc的原始经验
                {
                    getExp = getExp * (10 - (bi.getGrade() - npc.getLevel())) / 10;
                    if (getExp < 0) {
                        getExp = 0;
                    }
                }
                int getMoney = getMoney(npc.getMoney()) * gua_time * 2;
                if (guaji_type == GuaJiConstant.DOUBLE_G) {
                    getExp *= 2;
                } else if (guaji_type == GuaJiConstant.FIVE_G) {
                    getExp *= 5;
                } else if (guaji_type == GuaJiConstant.EIGHT_G) {
                    getExp *= 8;
                } else if (guaji_type == GuaJiConstant.TEN_G) {
                    getExp *= 10;
                }
                if (bi.getGrade() != ShituConstant.MAX_LEVEL && getExp > 0) {
                    bi.updateAddCurExp(getExp);
                    sb.append("获得经验" + getExp + ".");
                }
                if (getMoney > 0) {
                    bi.addCopper(getMoney);
                    sb.append("获得银两" + MoneyUtil.changeCopperToStr(getMoney) + ".");
                }
                List<GetGoodVo> getGood = new ArrayList<GetGoodVo>();

                Set<GoodVo> set = gv.getGood();
                if (set != null && set.size() > 0) {
                    String goods_id = "";
                    for (GoodVo goodVo : set) {
                        goods_id += goodVo.getGood_id() + ",";
                    }
                    if (!"".equals(goods_id.trim())) {
                        if (goods_id.lastIndexOf(",") + 1 == goods_id.length()) {
                            goods_id = goods_id.substring(0, goods_id.length() - 1);
                        }
                        // 获得角色想要的物品
                        List<NpcdropVO> want = guajiService.getNpcdropsByNpcIDAndGood_id(gv.getNpc_id(), goods_id);
                        if (want != null && want.size() > 0) {
                            int gailv = 0;
                            try {
                                gailv = guajiService.GetNpcJilv(gv.getNpc_id());
                            } catch (SQLException e) {
                                log.error("查询npc掉落物品总概率出错");
                            }
                            //幸运gailv
                            if (guaji_type == GuaJiConstant.DOUBLE_G) {
                                gailv *= 2;
                            } else if (guaji_type == GuaJiConstant.FIVE_G) {
                                gailv *= 5;
                            } else if (guaji_type == GuaJiConstant.EIGHT_G) {
                                gailv *= 8;
                            } else if (guaji_type == GuaJiConstant.TEN_G) {
                                gailv *= 10;
                            }
                            NpcdropVO model = want.get(0);
                            List<NpcdropVO> list = guajiService.getNpcdropsByNpcID(gv.getNpc_id(), 0, 0);
                            // 该npc所有物品大暴后掉率总和
                            int dabao = 0;
                            if (list != null) {
                                for (NpcdropVO ndv : list) {
                                    dabao += (ndv.getNpcdropProbability() * 5 > GuaJiConstant.BILV ? GuaJiConstant.BILV : ndv.getNpcdropProbability() * 5);
                                }
                            }
                            // 物品获得数量计算公式：挂机时间分钟*2*（该npc所有物品掉率总和/1000000*（100-大暴几率）/100+该npc所有物品大暴后掉率总和/1000000*大暴几率/100）

                            int count = Math.round((float) gua_time * 2 * ((float) gailv / (float) GuaJiConstant.BILV * (100 - ((float) model.getNpcdropLuck())) / 100 + (float) dabao / (float) GuaJiConstant.BILV * ((float) model.getNpcdropLuck()) / 100));

                            int shengyu = bi.getWrapSpare();// 剩余包裹格数
                            int huodeCount = 0;

                            for (NpcdropVO ndv : want) {
                                huodeCount += ((float) count * ((float) ndv.getNpcdropProbability() / (float) GuaJiConstant.BILV));
                            }
                            int jishu = (huodeCount > shengyu ? shengyu : huodeCount);
                            for (NpcdropVO ndv : want) {
                                int getHuodeCount = Math.round(((float) count * ((float) ndv.getNpcdropProbability() / (float) GuaJiConstant.BILV)));
                                if (getHuodeCount != 0) {
                                    int attri_bilv = (ndv.getGoodsType() == GoodsType.PROP ? 0 : 1);//规则有改变
                                    if (guaji_type == GuaJiConstant.DOUBLE_G) {
                                        attri_bilv *= 2;
                                    } else if (guaji_type == GuaJiConstant.FIVE_G) {
                                        attri_bilv *= 5;
                                    } else if (guaji_type == GuaJiConstant.EIGHT_G) {
                                        attri_bilv *= 8;
                                    } else if (guaji_type == GuaJiConstant.TEN_G) {
                                        attri_bilv *= 10;
                                    }

                                    // 属性装备数量
                                    int attriCount = getHuodeCount * attri_bilv / 100;
                                    int youCount = 0;
                                    int jingCount = 0;
                                    int jiCount = 0;
                                    for (int j = 0; j < attriCount; j++) {
                                        switch (getPingzhi()) {
                                            case Equip.Q_JIPIN:
                                                jiCount++;
                                                break;
                                            case Equip.Q_LIANGHAO:
                                                jingCount++;
                                                break;
                                            case Equip.Q_YOUXIU:
                                                youCount++;
                                                break;
                                            default:
                                                youCount++;
                                                break;
                                        }
                                    }
                                    GetGoodVo ggv = new GetGoodVo();
                                    ggv.setCom(getHuodeCount - attriCount);
                                    ggv.setGood_id(ndv.getGoodsId());
                                    ggv.setGood_name(ndv.getGoodsName());
                                    ggv.setGood_type(ndv.getGoodsType());
                                    ggv.setJi(jiCount);
                                    ggv.setJing(jingCount);
                                    ggv.setYou(youCount);
                                    ggv.setNpcDropId(ndv.getNpcdropID());
                                    getGood.add(ggv);
                                }
                            }
                            int del = 1;
                            for (GetGoodVo ndv : getGood) {

                                switch (gv.getLevel()) {
                                    case GuaJiConstant.YOU:
                                        del += ndv.getJing() + ndv.getJi() + ndv.getYou();
                                        break;
                                    case GuaJiConstant.JING:
                                        del += ndv.getJing() + ndv.getJi();
                                        break;
                                    case GuaJiConstant.JI:
                                        del += ndv.getJi();
                                        break;
                                    default:
                                        del += ndv.getCom() + ndv.getJing() + ndv.getCom() + ndv.getYou();
                                        break;
                                }
                            }
                            List<GetGoodVo> newGetGood = new ArrayList<GetGoodVo>();
                            for (GetGoodVo ndv : getGood) {
                                ndv.setCom(Math.round((float) ndv.getCom() * (shengyu < jishu ? (jishu > del ? ((float) jishu / (float) del) : 1) : 1)));
                                ndv.setYou(Math.round((float) ndv.getYou() * (shengyu < jishu ? (jishu > del ? ((float) jishu / (float) del) : 1) : 1)));
                                ndv.setJing(Math.round((float) ndv.getJing() * (shengyu < jishu ? (jishu > del ? ((float) jishu / (float) del) : 1) : 1)));
                                ndv.setJi(Math.round((float) ndv.getJi() * (shengyu < jishu ? (jishu > del ? ((float) jishu / (float) del) : 1) : 1)));
                                if (ndv.getGood_type() == 4) {
                                    if (ndv.getCom() > 100) {
                                        ndv.setCom(100);
                                    }
                                }
                                newGetGood.add(ndv);
                            }
                            mess = guajiService.giveGood(getRoleEntity(request), bi, newGetGood, gv.getLevel());
                        }
                    }
                }

                setMessage(request, sb + (mess.equals("") ? "" : "获得物品:" + mess));
                return mapping.findForward(ERROR);

            }
        } catch (Exception e) {
            log.info(e.getStackTrace());
            setMessage(request, "出错了");
            return mapping.findForward(ERROR);
        }
    }

    public int getPingzhi() {
        Random rd = new Random();
        int i = rd.nextInt(GuaJiConstant.BILV);
        if (i <= GuaJiConstant.JI_BILV) {
            return Equip.Q_JIPIN;
        } else if (i <= GuaJiConstant.JING_BILV) {
            return Equip.Q_LIANGHAO;
        } else {
            return Equip.Q_YOUXIU;
        }
    }

    //
    // public static void main(String[] args)
    // {
    // int gailv = 100000;
    // int a = 30;
    // int count =
    // Math.round((float)25*2*((float)gailv/(float)GuaJiConstant.BILV*(100-((float)a))/100+(float)gailv/(float)GuaJiConstant.BILV*((float)a)/100));
    // System.out.println(count);
    // }

    private int getMoney(String money) {
        int mm = 0;
        if (money == null || "".equals(money.trim())) {
            return mm;
        }
        String[] s = money.split(",");
        if (s != null) {
            for (String ss : s) {
                mm += Integer.parseInt(ss.trim());
            }
            mm /= s.length;
        }
        Random rd = new Random();
        int i = rd.nextInt(200) - 100;

        return Math.abs(mm * (1000 - i) / 1000);
    }


    // 查看挂机了多长时间(分钟),超过6小时按6小时计算
    private int lookTime(GuajiVo gv, long yuanbao, int every_min_spend, boolean isFree) {
        int time1 = 0;
        if (DateUtil.check(gv.getStart_time(), gv.getTime())) {
            time1 = gv.getTime();
        } else {
            int time = DateUtil.delTime(gv.getStart_time());
            time1 = time > 0 ? time : 0;
        }
        if (isFree) {
            return time1;
        } else {
            if ((long) time1 * every_min_spend <= yuanbao) {
                return time1;
            } else {
                return (int) yuanbao / every_min_spend;
            }
        }
    }

    private String findByType(int type) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case GuaJiConstant.COMMON:
                sb.append("普通离线挂机（最基本的离线挂机，没有任何特殊效果，每分钟消耗" + GuaJiConstant.COMMON_YUANBAO + GameConfig.getYuanbaoName() + "。）");
                break;
            case GuaJiConstant.DOUBLE_G:
                sb.append("双倍经验,幸运,掉率离线挂机（享受打怪时获得2倍经验的效果加成,2倍数量的物品的效果加成,2倍数量的属性装备的效果加成,每分钟消耗" + GuaJiConstant.DOUBLE_G_YUANBAO + GameConfig.getYuanbaoName() + "。）");
                break;
            case GuaJiConstant.FIVE_G:
                sb.append("五倍经验,幸运,掉率离线挂机（享受打怪时获得5倍经验的效果加成,5倍数量的物品的效果加成,5倍数量的属性装备的效果加成,每分钟消耗" + GuaJiConstant.FIVE_G_YUANBAO + GameConfig.getYuanbaoName() + "。）");
                break;
            case GuaJiConstant.EIGHT_G:
                sb.append("八倍经验,幸运,掉率离线挂机（享受打怪时获得8倍经验的效果加成,8倍数量的物品的效果加成,8倍数量的属性装备的效果加成,每分钟消耗" + GuaJiConstant.EIGHT_G_YUANBAO + GameConfig.getYuanbaoName() + "。）");
                break;
            case GuaJiConstant.TEN_G:
                sb.append("十倍经验,幸运,掉率离线挂机（享受打怪时获得10倍经验的效果加成,10倍数量的物品的效果加成,10倍数量的属性装备的效果加成,每分钟消耗" + GuaJiConstant.TEN_G_YUANBAO + GameConfig.getYuanbaoName() + "。）");
                break;
            default:
                sb.append("普通离线挂机（最基本的离线挂机，没有任何特殊效果，每分钟消耗" + GuaJiConstant.COMMON_YUANBAO + GameConfig.getYuanbaoName() + "。）");
                break;
        }
        return sb.toString();
    }

    private int findCostByType(int type) {
        int cost = 1;
        switch (type) {
            case GuaJiConstant.COMMON:
                cost = GuaJiConstant.COMMON_YUANBAO;
                break;
            case GuaJiConstant.DOUBLE_G:
                cost = GuaJiConstant.DOUBLE_G_YUANBAO;
                break;
            case GuaJiConstant.FIVE_G:
                cost = GuaJiConstant.FIVE_G_YUANBAO;
                break;
            case GuaJiConstant.EIGHT_G:
                cost = GuaJiConstant.EIGHT_G_YUANBAO;
                break;
            case GuaJiConstant.TEN_G:
                cost = GuaJiConstant.TEN_G_YUANBAO;
                break;
            default:
                cost = GuaJiConstant.COMMON_YUANBAO;
                break;
        }
        return cost;
    }
}
