package com.ben.pk.active;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.model.user.RoleEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：PK活动Service
 *
 * @author thomas.lei 27/04/10 PM
 */
public class PKActiveService {
    PKActiveDao pd = new PKActiveDao();

    // PK玩家报名
    public int pkActiveRegist(PKActiveRegist role) {
        return pd.pkActiveRegist(role);
    }

    // 查询玩家是否有历史报名记录
    public PKActiveRegist checkRoleRegist(int roleID) {
        return pd.checkRoleRegist(roleID);
    }

    // 如果玩家已经有历史报名记录则更新报名记录
    public int refreshRegist(PKActiveRegist role) {
        return pd.refreshRegist(role);
    }

    //创建对阵信息  每月1，2，3号不创建  其余每天12:00由定时器自动创建-------------------------------------------------
    public void createVSInfo() {
        pd.deleteVsInfo();// 删除原来的对阵信息
        Map vsInfo = pd.getRoleInfo();
        Map map = getVSList(pd.getRoleIDs());
        for (Object id : map.keySet()) {
            PKVs vs = new PKVs();
            Integer roleAId = (Integer) id;
            Integer roleBId = (Integer) map.get(id);
            String roleAName = (String) vsInfo.get(roleAId);
            String roleBName = (String) vsInfo.get(roleBId);
            if (roleBId == -1)// 如果轮空直接进入下一轮
            {
                pd.updateIsWin(roleAId, 0);
                continue;
            }
            vs.setRoleAID(roleAId);
            vs.setRoleBID(roleBId);
            vs.setRoleAName(roleAName);
            vs.setRoleBName(roleBName);
            vs.setWinRoleID(0);
            pd.addVsInfo(vs);
        }
        pd.updateEnterState();
    }

    // 查询对阵信息 查询比赛结果
    public List<PKVs> getVsInfo(int index, int limit) {
        return pd.getVsInfo(index, limit);
    }

    // PK开始后更新PK结果 胜利或者失败
    public void updatePkInfo(int roleId, int isWin) {
        pd.updateIsWin(roleId, isWin);
    }

    //更新对阵表获胜ID
    public void updateWinID(int winRoleId) {
        pd.updateWinRoleID(winRoleId);
    }

    // 得到对阵信息的总条数
    public int getTotalNum() {
        return pd.getTotalNum();
    }

    // 得到对手的ppk
    public int getPpk(int ppk) {
        int p = pd.getAppk(ppk);
        if (p == 0) {
            p = pd.getBppk(ppk);
        }
        return p;
    }

    // 本轮中已经失败玩家不可以进入比赛
    public boolean checkIsFail(int roleId) {
        return pd.checkIsFail(roleId);
    }


    // 更新玩家进入场景的状态
    public int updateEnterState(int roleId, int state) {
        return pd.updateEnterState(roleId, state);
    }

    // 到时间没有进入比赛场地的玩家的更新 更新其为败 比赛开始5分钟以后不进入赛场的玩家判负 每月123号不执行  其余13:05执行一次
    public int updateOutofTime() {
        List<Integer> ids = pd.getOutofEnterIDs();
        if (ids != null && !ids.isEmpty()) {
            for (int i = 0; i < ids.size(); i++) {
                int aid = Integer.parseInt(ids.get(i).toString());
                int bid = this.getPpk(aid);
                if (bid == 0 || ids.contains(bid)) {
                    continue;
                } else {
                    this.updatePriceState(bid, 1);
                    this.updateWinID(bid);
                }
            }
        }
        return pd.updateOutofTime();
    }

    // 得到没有结果的对阵信息
    public Map<Integer, Integer> getOutOfVs() {
        return pd.getNoresultVs();

    }

    // 处理PK超过30分钟的对阵信息  每月123不执行 其余每天5:30执行一次
    public void outOfTime() {
        RoleCache roleCache = new RoleCache();
        Map<Integer, Integer> map = this.getOutOfVs();
        // 去除双放都没有参加比赛的 剩下的就是正在PK超时的玩家
        List temp = new ArrayList();
        for (Integer id : map.keySet()) {
            RoleEntity roleEntity = RoleCache.getByPpk(id);
            if ((roleEntity == null) || (roleEntity.getBasicInfo().getSceneId() != PKActiveContent.SCENEID_PK)) {
                temp.add(id);
            }
        }
        for (int i = 0; i < temp.size(); i++) {
            map.remove(temp.get(i));
        }
        // 比较玩家的等级和气血来判断谁胜利
        for (Integer id : map.keySet()) {
            RoleEntity roleAEntity = RoleCache.getByPpk(id);// 玩家A
            RoleEntity roleBEntity = RoleCache.getByPpk(map.get(id));// 玩家B
            if (roleAEntity.getBasicInfo().getGrade() < roleBEntity.getBasicInfo().getGrade())// 等级小的取胜
            {
                this.updatePkInfo(map.get(id), 1);
                this.updatePriceState(id, 1);
                this.updateWinID(id);
            } else if (roleAEntity.getBasicInfo().getGrade() == roleBEntity.getBasicInfo().getGrade()) {
                if (roleAEntity.getBasicInfo().getHp() > roleBEntity.getBasicInfo().getHp()) {
                    this.updatePkInfo(map.get(id), 1);
                    this.updatePriceState(id, 1);
                    this.updateWinID(id);
                } else {
                    this.updatePkInfo(id, 1);
                    this.updatePriceState(map.get(id), 1);
                    this.updateWinID(map.get(id));
                }
            } else {
                this.updatePkInfo(id, 1);
                this.updatePriceState(map.get(id), 1);
                this.updateWinID(map.get(id));
            }
            roleAEntity.getBasicInfo().updateSceneId(PKActiveContent.NPCSCENEID);// /////////////////////////////////////////////senceID
            roleBEntity.getBasicInfo().updateSceneId(PKActiveContent.NPCSCENEID);// /////////////////////////////////////////////senceID
        }
    }

    // 查询对方的名称
    public String getOherName(int roleId) {
        int ppk = this.getPpk(roleId);
        return pd.getOherName(ppk);
    }

    //玩家是否可以领取奖品
    public boolean isGetPrice(int roleID) {
        return pd.isGetPrice(roleID);
    }

    //修改领取奖品的状态
    public void updatePriceState(int roleID, int isPrice) {
        pd.updatePriceState(roleID, isPrice);
    }

    //判断参赛人数是否进了八强 四强 或者半决赛
    public int getPlayerNum() {
        int num = pd.getPlayerNum();
        if (num > PKActiveContent.BAQIANG) {
            return PKActiveContent.PPRICEID;
        }
        if (num == PKActiveContent.BAQIANG) {
            return PKActiveContent.BPRICEID;
        }
        if (num == PKActiveContent.SIQIANG) {
            return PKActiveContent.SPRICEID;
        }
        if (num == PKActiveContent.BANJUESAI) {
            return PKActiveContent.BJPRICEID;
        }
        if (num == PKActiveContent.GUANJUN) {
            return PKActiveContent.GPRICEID;
        } else {
            return PKActiveContent.PPRICEID;
        }
    }

    //查询所有的报名玩家信息
    public List getAllRole() {
        return pd.getAllRole();
    }

    /**
     * 根据玩家的等级自动产生配对映射信息
     *
     * @param rolesID
     * @return map 参赛玩家ID配对映射信息
     */
    public Map<Integer, Integer> getVSList(int[] IDs) {
        Map map = new HashMap<Integer, Integer>();
        for (int i = 0; i < IDs.length; i++) {
            if (i % 2 == 0) {
                if (i != IDs.length - 1) {
                    map.put(IDs[i], IDs[i + 1]);
                } else {
                    map.put(IDs[i], -1);
                }
            }
        }
        return map;
    }

}
