package com.ls.ben.vo.info.title;


/**
 * 功能:等级称号
 * @author 刘帅
 * 4:33:12 PM
 */
public class GradeTitleVO  {
	/**称谓id，如:saodiseng*/
	private String titleId;
	 /**称谓名称*/
	private String  titleName;
	/**门派id*/
	private String  schoolId;
	/**门派名称*/
	private String  schoolName;
	/**要求的等级下线*/
	private int  titleLevelDown;
	 /**要求的等级上线*/
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
