package com.ls.ben.vo.info.title;


/**
 * ����:�ȼ��ƺ�
 * @author ��˧
 * 4:33:12 PM
 */
public class GradeTitleVO  {
	/**��νid����:saodiseng*/
	private String titleId;
	 /**��ν����*/
	private String  titleName;
	/**����id*/
	private String  schoolId;
	/**��������*/
	private String  schoolName;
	/**Ҫ��ĵȼ�����*/
	private int  titleLevelDown;
	 /**Ҫ��ĵȼ�����*/
	private int  titleLevelUp;
	
	
	
	public String getTitleId() {
		return titleId;
	}
	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public int getTitleLevelDown() {
		return titleLevelDown;
	}
	public void setTitleLevelDown(int titleLevelDown) {
		this.titleLevelDown = titleLevelDown;
	}
	public int getTitleLevelUp() {
		return titleLevelUp;
	}
	public void setTitleLevelUp(int titleLevelUp) {
		this.titleLevelUp = titleLevelUp;
	}  
}
