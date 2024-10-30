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
	 * ������������뵽���ݿ���
	 * @param pk
	 * @param secondPass
	 */
	public void insertSecondPass(int u_pk, String secondPass)
	{
		// ��ǰ����Ϊ�ڷ��ʼ�ʱֱ�ӽ����˳�ʼ������������ֻ��Ҫupdate���ɣ�����û�г�ʼ���ˣ�ֻ���ڴ˽���insert����
		MD5 md5 = MD5.getInstance();
		String pass_md5 = md5.getMD5ofStr(secondPass);
		SecondPassDao seconddao = new SecondPassDao();
		//seconddao.insertSecondPass(u_pk,pass_md5);
		seconddao.updateSendFlag(u_pk+"", pass_md5,1);
	}

	/**
	 * �޸ĵ�¼����
	 * @param pk
	 * @param newPass
	 */
	public void updateLoginPaw(int u_pk, String newPass)
	{
		LoginInfoDAO loginInfoDAO = new LoginInfoDAO(); 
		loginInfoDAO.updatePassWord(u_pk,newPass);
	}
	
	/**
	 * ��õ�¼����
	 * @param pk
	 * @param newPass
	 */
	public String getUserLoginPawByUPk(int u_pk)
	{
		LoginInfoDAO loginInfoDAO = new LoginInfoDAO(); 
		 return loginInfoDAO.getUserLoginPawByUPk(u_pk);
	}
	
	
	/**
	 * ��������������һ��
	 * @param u_pk
	 */
	public void insertErrorSecondPsw(int u_pk) {
		SecondPassDao seconddao = new SecondPassDao();
		SecondPassVO secondPassVO = seconddao.getSecondPassTime(u_pk);
		seconddao.updateSecondPassSelect(u_pk,secondPassVO.getPassSecondTime(),secondPassVO.getPassThirdTime());
	}	

	/**
	 * �����˺��Ƿ���Ժ˶Զ�������
	 * ����false,�ø���Ҽ����˶�,����true,����������˶�
	 * @param pk
	 * @param passWrongFlag
	 * @return
	 */
	public boolean checkSeconePass(int u_pk, SecondPassVO secondPassVO)
	{
		if(secondPassVO.getPassWrongFlag() < 3) {	//�������������С��3, �϶����ø���Ҽ����˶�����
			return false;
		}
		SecondPassDao seconddao = new SecondPassDao();
		int onedayTime = 1000*60*60*24;
		//����������޸�ʱ���������Ѿ���24��Сʱ,������������������.
		Date thirdtime = DateUtil.strToDate(secondPassVO.getPassThirdTime()) ;
		if(DateUtil.isOverdue(thirdtime, onedayTime)) {
			seconddao.updateSecondPass(u_pk, 0);
			return false;
		}	
		//����ڶ����޸�ʱ���������Ѿ���24��Сʱ,�����������������.
		Date secondtime = DateUtil.strToDate(secondPassVO.getPassSecondTime()) ;
		if(DateUtil.isOverdue(secondtime, onedayTime)) {
			seconddao.updateSecondPass(u_pk, 1);
			return false;
		}
		//�����һ���޸�ʱ���������Ѿ���24��Сʱ,��������������һ��.
		Date firsttime = DateUtil.strToDate(secondPassVO.getPassFirstTime());
		if(DateUtil.isOverdue(firsttime, onedayTime)) {
			seconddao.updateSecondPass(u_pk, 2);
			return false;
		}
		
		return true;
	}

	/**
	 * ȷ���Ƿ��Ѿ������������
	 * @param uPk
	 * @return
	 */
	public String getHasSetSecondPassword(String uPk)
	{
		
		SecondPassDao seconddao = new SecondPassDao();
		
		boolean flag = seconddao.hasAlreadySecondPass(uPk);
		String hint = "";
		if (flag ) {
			hint = " ���Ѿ����ù�����������!";
		} else {
			hint = " ����û�����ö���������! ";
		}
		return hint;
	}
	
	
}
