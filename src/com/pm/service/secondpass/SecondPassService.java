package com.pm.service.secondpass;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ben.dao.logininfo.LoginInfoDAO;
import com.ls.pub.util.DateUtil;
import com.pm.dao.secondpass.SecondPassDao;
import com.pm.vo.passsecond.SecondPassVO;
import com.pub.MD5;


public class SecondPassService
{

	Logger logger = Logger.getLogger("log.service");

	/**
	 * 将二级密码插入到数据库中
	 * @param pk
	 * @param secondPass
	 */
	public void insertSecondPass(int u_pk, String secondPass)
	{
		// 以前是因为在发邮件时直接进行了初始化，所有现在只需要update即可，现在没有初始化了，只能在此进行insert操作
		MD5 md5 = MD5.getInstance();
		String pass_md5 = md5.getMD5ofStr(secondPass);
		SecondPassDao seconddao = new SecondPassDao();
		//seconddao.insertSecondPass(u_pk,pass_md5);
		seconddao.updateSendFlag(u_pk+"", pass_md5,1);
	}

	/**
	 * 修改登录密码
	 * @param pk
	 * @param newPass
	 */
	public void updateLoginPaw(int u_pk, String newPass)
	{
		LoginInfoDAO loginInfoDAO = new LoginInfoDAO(); 
		loginInfoDAO.updatePassWord(u_pk,newPass);
	}
	
	/**
	 * 获得登录密码
	 * @param pk
	 * @param newPass
	 */
	public String getUserLoginPawByUPk(int u_pk)
	{
		LoginInfoDAO loginInfoDAO = new LoginInfoDAO(); 
		 return loginInfoDAO.getUserLoginPawByUPk(u_pk);
	}
	
	
	/**
	 * 输入二级密码错误一次
	 * @param u_pk
	 */
	public void insertErrorSecondPsw(int u_pk) {
		SecondPassDao seconddao = new SecondPassDao();
		SecondPassVO secondPassVO = seconddao.getSecondPassTime(u_pk);
		seconddao.updateSecondPassSelect(u_pk,secondPassVO.getPassSecondTime(),secondPassVO.getPassThirdTime());
	}	

	/**
	 * 检测此账号是否可以核对二级密码
	 * 返回false,让该玩家继续核对,返回true,则不允许继续核对
	 * @param pk
	 * @param passWrongFlag
	 * @return
	 */
	public boolean checkSeconePass(int u_pk, SecondPassVO secondPassVO)
	{
		if(secondPassVO.getPassWrongFlag() < 3) {	//如果输入错误次数小于3, 肯定能让该玩家继续核对密码
			return false;
		}
		SecondPassDao seconddao = new SecondPassDao();
		int onedayTime = 1000*60*60*24;
		//如果第三次修改时间离现在已经有24个小时,可以让其再输入三次.
		Date thirdtime = DateUtil.strToDate(secondPassVO.getPassThirdTime()) ;
		if(DateUtil.isOverdue(thirdtime, onedayTime)) {
			seconddao.updateSecondPass(u_pk, 0);
			return false;
		}	
		//如果第二次修改时间离现在已经有24个小时,可以让其再输入二次.
		Date secondtime = DateUtil.strToDate(secondPassVO.getPassSecondTime()) ;
		if(DateUtil.isOverdue(secondtime, onedayTime)) {
			seconddao.updateSecondPass(u_pk, 1);
			return false;
		}
		//如果第一次修改时间离现在已经有24个小时,可以让其再输入一次.
		Date firsttime = DateUtil.strToDate(secondPassVO.getPassFirstTime());
		if(DateUtil.isOverdue(firsttime, onedayTime)) {
			seconddao.updateSecondPass(u_pk, 2);
			return false;
		}
		
		return true;
	}

	/**
	 * 确定是否已经设过二级密码
	 * @param uPk
	 * @return
	 */
	public String getHasSetSecondPassword(String uPk)
	{
		
		SecondPassDao seconddao = new SecondPassDao();
		
		boolean flag = seconddao.hasAlreadySecondPass(uPk);
		String hint = "";
		if (flag ) {
			hint = " 您已经设置过二级密码了!";
		} else {
			hint = " 您还没有设置二级密码了! ";
		}
		return hint;
	}
	
	
}
