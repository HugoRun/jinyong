package com.ls.model.property;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ls.model.user.RoleEntity;
import com.pm.constant.SystemInfoType;
import com.pm.dao.systemInfo.SysInfoDao;
import com.pm.vo.sysInfo.SystemInfoVO;

/**
 * ϵͳ��Ϣ�Ĵ洢�ͻ��
 * @author Administrator
 *
 */
public class RoleSystemInfo
{
	public List<SystemInfoVO> infoList = null;
	DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public RoleSystemInfo(int p_pk)
	{
		SysInfoDao infoDao = new SysInfoDao();
		infoList =  infoDao.getSystemInfoThree(p_pk);
		SystemInfoVO list_infoVO = null;
		for ( int i =0;i < infoList.size();i++) {
			list_infoVO = infoList.get(i);
			list_infoVO.setAppearTime(dFormat.format(new Date()));
		}
	}
	
	
	
	public List<SystemInfoVO> getInfoList(RoleEntity roleInfo)
	{
		SysInfoDao infoDao = new SysInfoDao();
		List<SystemInfoVO> new_List = infoDao.getSystemInfoThree(roleInfo.getBasicInfo().getPPk());
		SystemInfoVO list_infoVO = null;
		SystemInfoVO new_infoVO = null;
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		checkList(infoList);
		// �����δ���ֵ���Ϣ,�����¼����.
		for ( int a =0;a < new_List.size();a++) {
			new_infoVO = new_List.get(a);
			if ( infoList == null || infoList.size() == 0) {
				new_infoVO.setAppearTime(dFormat.format(new Date()));
				infoList.add(new_infoVO);
			} else {
			
				boolean flag =false;
        		// ��ѭ��,������µ���ϢiD����,�͸�����ʾ����
        		for ( int i =0;i < infoList.size();i++) {
        			list_infoVO = infoList.get(i);	        			
    				if ( new_infoVO.getSysInfoId() == list_infoVO.getSysInfoId() ) {
    					flag = true;
    				}				
        		}	
        		if ( !flag) {
    				new_infoVO.setAppearTime(dFormat.format(new Date()));
    				infoList.add(new_infoVO);
    			}
			}
			// �����Ϣ��¼��������,��
			if (infoList.size() >= 3) {
				break;
			}
		}
		return infoList;
	}

	public void setInfoList(List<SystemInfoVO> infoList)
	{
		this.infoList = infoList;
	}
	
	
	private void checkList(List<SystemInfoVO> list) {
		SystemInfoVO infoVO = null;
		Date dt = new Date();
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ������ʮ��Ĵ�list���߳�
		Date infotimeDate = null;
		Date appearTime = null;
		try
		{
    		for ( int i =0;i < infoList.size();i++) {
    			infoVO = infoList.get(i);
    			infotimeDate = dFormat.parse(infoVO.getCreateTime());
    			// ������ʮ��Ŀ϶�ɾ��
    			if ( infotimeDate.getTime() < (dt.getTime() - 1000*30) ) {
    				infoList.remove(i);
    			}
    		}
    		
    		for ( int i =0;i < infoList.size();i++) {
    			infoVO = infoList.get(i);
    			appearTime = dFormat.parse(infoVO.getAppearTime());
    				
    			// ��ʾʱ�䳬��ʮ���Ҳɾ��,���ǵ�ϵͳ�����ʱ��ʹ��͵�ʱ��,��˷��ɵ�10��,
    			switch (infoVO.getInfoType())
				{
					case SystemInfoType.EQUIPRELELA:
						if ( appearTime.getTime() < (dt.getTime() - 1000*30)) {
		    				infoList.remove(i);
		    			}
						break;
					default:
						if ( appearTime.getTime() < (dt.getTime() - 1000*10)) {
		    				infoList.remove(i);
		    			}
						break;
				}
    			
    			
    			
    			
    		}
    	}	
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
