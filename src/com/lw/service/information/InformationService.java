package com.lw.service.information;

import com.lw.dao.information.InformationDAO;

public class InformationService
{
	/** �õ���ҵ��ʸ� ������Ƿ��л�ô�������ʸ� */
	public boolean getInformationBy50(int u_pk)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByUpk(u_pk, "50��") == null)
		{
			return true;
		}
		return false;
	}

	/** �õ���ҵ��ʸ� ������Ƿ��л�ô�������ʸ� */
	public boolean getInformationBy60(int u_pk)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByUpk(u_pk, "60��") == null)
		{
			return true;
		}
		return false;
	}

	/** �鿴��Ұ���Ƿ�μӹ�� */
	public boolean getInformationByTong(int u_pk, String tongid)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByUpk(u_pk, tongid) == null)
		{
			return true;
		}
		return false;
	}

	/** �õ���Ұ��������ʸ� ������Ƿ��л�ô�������ʸ� */
	public boolean getInformationByTong(int p_pk)
	{
		InformationDAO dao = new InformationDAO();
		if (dao.getInfotmationByTong(p_pk) != 0)
		{
			return true;
		}
		return false;
	}
}
