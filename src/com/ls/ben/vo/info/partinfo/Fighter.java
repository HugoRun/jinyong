package com.ls.ben.vo.info.partinfo;

import com.ls.ben.vo.info.pet.PetInfoVO;
import com.ls.ben.vo.info.skill.PlayerSkillVO;

/**
 * 功能:战斗实体
 *
 * @author 刘帅
 * 1:18:55 AM
 */
public class Fighter extends PartInfoVO {

    // 玩家伤害
    private int playerInjure;
    // 宠物伤害
    private int petInjure;
    // 伤害描述
    private String injureDisplay;
    private boolean isDead;
    private int expendMP;
    /**
     * 物品掉落说明
     */
    private String dropDisplay;

    /**
     * 技能描述:当技能发动时，为技能名字，不能用时为技能不能使用的提示
     */
    private String skillDisplay = "";

    private String skillNoUseDisplay = "";

    private long dropExp;

    /**
     * 是否有免死道具
     */
    private int deadProp;

    /**
     * 是否在不掉经验有效时间内
     */
    private boolean notDropExp;


    /**
     * 宠物技能描述
     */
    private String petSkillDisplay;

    /**
     * 死亡描述
     */
    private final StringBuffer killDisplay = new StringBuffer();

    private String task_display = null;//任务提示

    /**
     * 玩家当前使用的技能
     */
    private PlayerSkillVO skill = null;

    /**
     * 战斗宠物
     */
    private PetInfoVO pet = null;

    /**
     * 附加属性描述
     */
    private String appendAttributeDescribe = null;

    /**
     * 宠物技能伤害显示描述
     */
    private String petInjureOut;
    /**
     * 绝学判断
     */
    private String juexuedisplay;
    /**
     * 绝学伤害
     */
    private String juexueInjure;

    public String getPetInjureOut() {
        return petInjureOut;
    }

    public void setPetInjureOut(String petInjureOut) {
        this.petInjureOut = petInjureOut;
    }

    public String getTask_display() {
        return task_display;
    }

    public void setTask_display(String task_display) {
        this.task_display = task_display;
    }

    public String getSkillDisplay() {
        return skillDisplay;
    }

    public void setSkillDisplay(String skillDisplay) {
        this.skillDisplay = skillDisplay;
    }

    public PlayerSkillVO getSkill() {
        return skill;
    }

    public void setSkill(PlayerSkillVO skill) {
        this.skill = skill;
    }

    public long getDropExp() {
        return dropExp;
    }

    public void setDropExp(long dropExp) {
        this.dropExp = dropExp;
    }

    public int getPlayerInjure() {
        return playerInjure;
    }

    public void setPlayerInjure(int playerInjure) {

        int currentHP = getPHp() - playerInjure;
        if (currentHP <= 0) {
            setPHp(0);
            isDead = true;
        } else {
            setPHp(currentHP);
        }

        this.playerInjure = playerInjure;
    }

    public int getExpendMP() {
        return expendMP;
    }

    public void setExpendMP(int expendMP) {
        int currentMP = getPMp() - expendMP;
        if (currentMP <= 0) {
            setPMp(0);
        } else {
            setPMp(currentMP);
        }

        this.expendMP = expendMP;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public int getPetInjure() {
        return petInjure;
    }

    public void setPetInjure(int petInjure) {

        int currentHP = getPHp() - petInjure;
        if (currentHP <= 0) {
            setPHp(0);
            isDead = true;
        } else {
            setPHp(currentHP);
        }


        this.petInjure = petInjure;
    }

    public PetInfoVO getPet() {
        return pet;
    }

    public void setPet(PetInfoVO pet) {
        this.pet = pet;
    }

    public String getPetSkillDisplay() {
        return petSkillDisplay;
    }

    public void setPetSkillDisplay(String petSkillDisplay) {
        this.petSkillDisplay = petSkillDisplay;
    }

    public String getSkillNoUseDisplay() {
        return skillNoUseDisplay;
    }

    public void setSkillNoUseDisplay(String skillNoUseDisplay) {
        this.skillNoUseDisplay = skillNoUseDisplay;
    }

    public String getInjureDisplay() {
        return injureDisplay;
    }

    public void setInjureDisplay(String injureDisplay) {
        this.injureDisplay = injureDisplay;
    }

    public String getKillDisplay() {
        return killDisplay.toString();
    }

    /**
     * 添加死亡描述
     *
     * @param killDisplay
     */
    public StringBuffer appendKillDisplay(String killDisplay) {
        return this.killDisplay.append(killDisplay);
    }

    public String getAppendAttributeDescribe() {
        return appendAttributeDescribe;
    }

    public void setAppendAttributeDescribe(String appendAttributeDescribe) {
        this.appendAttributeDescribe = appendAttributeDescribe;
    }

    public String getDropDisplay() {
        return dropDisplay;
    }

    public void setDropDisplay(String dropDisplay) {
        this.dropDisplay = dropDisplay;
    }

    public int getDeadProp() {
        return deadProp;
    }

    public void setDeadProp(int deadProp) {
        this.deadProp = deadProp;
    }

    public boolean isNotDropExp() {
        return notDropExp;
    }

    public void setNotDropExp(boolean notDropExp) {
        this.notDropExp = notDropExp;
    }

    public String getJuexuedisplay() {
        return juexuedisplay;
    }

    public void setJuexuedisplay(String juexuedisplay) {
        this.juexuedisplay = juexuedisplay;
    }

    public String getJuexueInjure() {
        return juexueInjure;
    }

    public void setJuexueInjure(String juexueInjure) {
        this.juexueInjure = juexueInjure;
    }

}
