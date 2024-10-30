package com.lw.service.instance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ls.ben.cache.staticcache.map.MapCache;
import com.ls.ben.dao.instance.InstanceArchiveDao;
import com.ls.ben.dao.instance.InstanceInfoDao;
import com.ls.ben.vo.instance.InstanceInfoVO;
import com.ls.ben.vo.map.MapVO;
import com.lw.dao.instance.InstanceOutDao;
import com.lw.vo.instance.InstanceOutVO;

public class InstanceOutService
{
	/** ���㸱����ʱ�� */
	private String instanceReTime(int day, Date datetime, int map_id)
	{
		String time = null;
		Date date = new Date();
		long i = (date.getTime() - datetime.getTime()) / 60000;
		int totime = day * 60 * 24 - (int) i;
		if (totime < 0)
		{
			InstanceArchiveDao instanceArchiveDao = new InstanceArchiveDao();
			InstanceInfoDao instanceInfoDao = new InstanceInfoDao();

			instanceInfoDao.updateResetTime(map_id);// ��������ʱ��
			instanceArchiveDao.clearAllArchive(map_id);// ��ʼ��������Ҹø�������
			return "����������";
		}
		else
		{
			int x = totime / (60 * 24);
			if (x < 0 || x == 0)
			{
				int hours = totime / 60;
				if (hours == 0)
				{
					time = "������ʱ��:" + totime + "����";
				}
				else
				{
					totime = totime - 60 * hours;
					time = "������ʱ��:" + hours + "Сʱ" + totime + "����";
				}
				return time;
			}
			else
			{
				int timenum = totime - 60 * 24 * x;
				int hours = timenum / 60;
				if (hours == 0)
				{
					time = "������ʱ��:" + x + "��" + timenum + "����";
				}
				else
				{
					timenum = timenum - 60 * hours;;
					time = "������ʱ��:" + x + "��" + hours + "Сʱ" + timenum + "����";
				}
				return time;
			}
		}
	}

	/** �õ���������ʾ */
	public List<InstanceOutVO> getInstanceOut()
	{
		MapCache mapCache = new MapCache();
		List<InstanceOutVO> outlist = new ArrayList<InstanceOutVO>();
		InstanceOutVO vo = null;
		InstanceOutDao dao = new InstanceOutDao();
		List<InstanceInfoVO> list = dao.getInstanceInfo();
		for (int i = 0; i < list.size(); i++)
		{
			vo = new InstanceOutVO();
			InstanceInfoVO infovo = list.get(i);
			MapVO mapvo = mapCache.getById(infovo.getMapId() + "");
			vo.setInstanceName(mapvo.getMapName());
			vo.setInstanceLv(infovo.getLevelLimit() + "");
			vo.setInstanceTime(instanceReTime(infovo.getResetTimeDistance(),
					infovo.getPreResetTime(), infovo.getMapId()));
			outlist.add(vo);
		}
		return outlist;
	}

	/** �õ�������ʱ�� */
	public String getInstanceTime(InstanceInfoVO instanceInfoVO)
	{
		String time = null;
		if (instanceInfoVO != null)
		{
			time = instanceReTime(instanceInfoVO.getResetTimeDistance(),
					instanceInfoVO.getPreResetTime(), instanceInfoVO.getMapId());
		}
		else
		{
			time = "����������";
		}
		return time;
	}
}
