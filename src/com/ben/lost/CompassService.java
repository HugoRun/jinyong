package com.ben.lost;

import com.ben.shitu.model.DateUtil;
import com.ls.ben.dao.menu.OperateMenuDao;
import com.ls.ben.vo.map.SceneVO;
import com.ls.ben.vo.menu.OperateMenuVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.MapType;
import com.ls.pub.constant.PropType;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.pub.util.MoneyUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.player.RoleService;
import com.ls.web.service.rank.RankService;
import com.ls.web.service.system.UMsgService;
import com.pm.service.systemInfo.SystemInfoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class CompassService {
    private final CompassDao compassDao = new CompassDao();

    public static OperateMenuVO getCurrentMenu(OperateMenuVO omv, int ppk) {
        if (LostConstant.USE_SHEARE.contains(ppk)) {
            return null;
        } else {
            if (LostConstant.currentMenuId == 0) {
                return omv;
            } else {
                OperateMenuVO currM = LostConstant.SHEARE_MENU.get(LostConstant.currentMenuId);
                if (currM != null && currM.getMenuOperate4() != 0) {
                    return LostConstant.SHEARE_MENU.get(currM.getMenuOperate4());
                } else {
                    return null;
                }
            }
        }
    }

    // 该菜单是否可以使用
    public static boolean useSheareMenu(int menu_id, int ppk) {
        if (menu_id <= LostConstant.currentMenuId) {
            return false;
        }
        LostConstant.currentMenuId = menu_id;
        LostConstant.USE_SHEARE.add(ppk);
        return true;
    }

    // 是否在本迷宫领取过奖励
    private static boolean isHuodeJiangli(int scene_id, int ppk) {
        if (!LostConstant.JIANGLI.containsKey(scene_id)) {
            List<Integer> lis = new ArrayList<Integer>();
            lis.add(ppk);
            LostConstant.JIANGLI.put(scene_id, lis);
            return false;
        } else {
            List<Integer> list = LostConstant.JIANGLI.get(scene_id);
            if (!list.contains(ppk)) {
                list.add(ppk);
                return false;
            } else {
                return true;
            }
        }
    }

    // 通过迷宫获得奖励
    public static void pass(BasicInfo bi) {
        if (bi != null) {
            SceneVO scene_info = bi.getSceneInfo();
            if (scene_info.getMap().getMapType() == MapType.COMPASS && scene_info.getSceneSkill() != 0 && !isHuodeJiangli(scene_info.getSceneID(), bi.getPPk())) {
                // 到达迷宫的出口
                // 经验公式:玩家等级*50*(1-(30-玩家等级)/30)*层数+(u_grow_info表的当前g_grade中的g_next_exp - g_exp)/200

//				银两公式:玩家等级*5文*(1-(30-玩家等级)/30)* 层数
                int grade = bi.getGrade();
                double addExp = grade * 50 * (1 - (30 - grade) / (double) 30) * scene_info.getSceneSkill() + (Integer.valueOf(bi.getNextGradeExp().trim()) - Integer.valueOf(bi.getCurExp().trim())) / 200;
                double addmoney = grade * 5 * (1 - (30 - grade) / (double) 30) * scene_info.getSceneSkill();

                bi.addCopper((int) addmoney);
                String message = "恭喜您通过了这层迷宫，您获得了银两" + MoneyUtil.changeCopperToStr((int) addmoney);
                if (bi.getCurExp() == bi.getNextGradeExp()) {
                    bi.updateAddCurExp((int) addExp);
                    message += "," + (int) addExp + "经验.";
                }
                UMessageInfoVO uif = new UMessageInfoVO();
                uif.setCreateTime(new Date());
                uif.setMsgPriority(PopUpMsgType.COMMON_FIRST);
                uif.setMsgType(PopUpMsgType.COMMON);
                uif.setPPk(bi.getPPk());
                uif.setResult(message);
                new UMsgService().sendPopUpMsg(uif);
                new RankService().updatea(bi.getPPk(), "lost", scene_info.getSceneSkill());
            }
        }
    }

    // 是否可以使用破旧箱子
    public static boolean useOld_Xiang(OperateMenuVO omv, int ppk) {
        synchronized (LostConstant.USE_OLD_XIANG) {

            if (omv.getMenuOperate4() <= 0) {
                return false;
            }
            if (LostConstant.USE_OLD_XIANG.containsKey(omv.getId())) {
                List<Integer> list = LostConstant.USE_OLD_XIANG.get(omv.getId());
                if (list.contains(ppk)) {
                    return false;
                } else {
                    if (list.size() >= omv.getMenuOperate4()) {
                        return false;
                    } else {
                        list.add(ppk);
                        return true;
                    }
                }
            } else {
                List<Integer> list = new ArrayList<Integer>();
                list.add(ppk);
                LostConstant.USE_OLD_XIANG.put(omv.getId(), list);
                return true;
            }
        }
    }

    // 删除秘境地图
    public static void removeMiJing(int ppk, int goodsType) {
        RoleEntity re = RoleService.getRoleInfoById(ppk + "");
        if (re != null && re.getBasicInfo().getSceneInfo().getMap().getMapType() == MapType.COMPASS) {
            // 在迷宫中
            if (goodsType == PropType.GOBACKCITY || goodsType == PropType.MARKUP || goodsType == PropType.SUIBIANCHUAN || goodsType == PropType.GROUPCHUAN || goodsType == PropType.FRIENDCHUAN || goodsType == PropType.XINYINDU) {
                new GoodsService().removeMiJing(ppk);
            }

        }
    }

    public List<Compass> findAll() {
        return this.compassDao.findAll();
    }

    public Compass findById(int scene_id) {
        Compass c = LostConstant.COMPASS_MAP.get(scene_id);
        if (c != null) {
            return c;
        }
        return this.compassDao.findById(scene_id);
    }

    //传入结束时间
    public void startLost(Date date, Date date1) {
        LostConstant.LOST_END_TIME = date;
        new SystemInfoService().insertSystemInfoBySystem("神秘迷宫的大门已经打开！大家可以进入迷宫探险啦！");
        if (LostConstant.END_LOST_TIMER != null) {
            LostConstant.END_LOST_TIMER.cancel();
            LostConstant.END_LOST_TIMER = null;
        }
        if (LostConstant.LAST_FIVE_TIMER != null) {
            LostConstant.LAST_FIVE_TIMER.cancel();
            LostConstant.LAST_FIVE_TIMER = null;
        }
        LostConstant.END_LOST_TIMER = new Timer();
        LostConstant.END_LOST_TIMER.schedule(new EndLostTimer(), LostConstant.LOST_END_TIME);
        LostConstant.LAST_FIVE_TIMER = new Timer();
        System.out.println(DateUtil.getDate(LostConstant.LOST_END_TIME));
        date1.setMinutes(date1.getMinutes() - LostConstant.LAST_MIN);
        System.out.println(DateUtil.getDate(date1));
        System.out.println(DateUtil.getDate(LostConstant.LOST_END_TIME));
        LostConstant.LAST_FIVE_TIMER.schedule(new LastFiveTimer(), date1);
    }

    public void loadToMemory() {
        List<Compass> list = findAll();
        if (list != null) {
            for (Compass compass : list) {
                LostConstant.COMPASS_MAP.put(compass.getScene_id(), compass);
            }
        }
        List<OperateMenuVO> menu_list = new OperateMenuDao().findAll_Sheare_menu();
        if (menu_list != null) {
            for (OperateMenuVO om : menu_list) {
                LostConstant.SHEARE_MENU.put(om.getId(), om);
            }
        }
    }

}
