package com.ls.ben.vo.info.quiz;

import com.ls.iface.function.Probability;

/**
 * ����:quiz_respobily ���
 * @author ��ƾ�
 * 5:43:30 PM
 */
public class QuizVO implements Probability {
	
	/**id*/
	private int id;
	/**�������*/
	private int probability;
	
	/**��Ŀ����*/
	private String     quizContent;
	/**��Ŀ��ѡ��,�洢��ʽ��:1.��һ,2.�𰸶�,3.����    ���ж��*/
	private String      quizAnswers;
	/**��Ŀ��ȷ��,�洢��Ӧ�������ţ�����ȷ��Ϊ2.�𰸶�����洢:2*/
	private int     quziRightAnswer;
	
	/**�����ľ���*/
	private long awardExperience;
	 /**�����Ľ�Ǯ*/
	private long      awardMoney;
	/**����������*/
	private String      awardPrestige;
	/**��������Ʒ���洢��ʽ��:��Ʒ����,��Ʒid,��Ʒ����- */
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
