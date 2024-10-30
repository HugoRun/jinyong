package com.ls.ben.vo.info.quiz;

import com.ls.iface.function.Probability;

/**
 * 功能:quiz_respobily 题库
 * @author 侯浩军
 * 5:43:30 PM
 */
public class QuizVO implements Probability {
	
	/**id*/
	private int id;
	/**出题概率*/
	private int probability;
	
	/**题目内容*/
	private String     quizContent;
	/**题目备选答案,存储形式如:1.答案一,2.答案二,3.答案三    可有多个*/
	private String      quizAnswers;
	/**题目正确答案,存储对应上面的序号，如正确答案为2.答案二，则存储:2*/
	private int     quziRightAnswer;
	
	/**奖励的经验*/
	private long awardExperience;
	 /**奖励的金钱*/
	private long      awardMoney;
	/**奖励的声望*/
	private String      awardPrestige;
	/**奖励的物品，存储形式如:物品类型,物品id,物品数量- */
	private String      awardGoods;
	
	public String getQuizContent() {
		return quizContent;
	}
	public void setQuizContent(String quizContent) {
		this.quizContent = quizContent;
	}
	public String getQuizAnswers() {
		return quizAnswers;
	}
	public void setQuizAnswers(String quizAnswers) {
		this.quizAnswers = quizAnswers;
	}
	public int getQuziRightAnswer() {
		return quziRightAnswer;
	}
	public void setQuziRightAnswer(int quziRightAnswer) {
		this.quziRightAnswer = quziRightAnswer;
	}
	
	public long getAwardExperience() {
		return awardExperience;
	}
	public void setAwardExperience(long awardExperience) {
		this.awardExperience = awardExperience;
	}
	public long getAwardMoney() {
		return awardMoney;
	}
	public void setAwardMoney(long awardMoney) {
		this.awardMoney = awardMoney;
	}
	public String getAwardPrestige() {
		return awardPrestige;
	}
	public void setAwardPrestige(String awardPrestige) {
		this.awardPrestige = awardPrestige;
	}
	public String getAwardGoods() {
		return awardGoods;
	}
	public void setAwardGoods(String awardGoods) {
		this.awardGoods = awardGoods;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
