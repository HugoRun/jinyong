package com.lw.service.player;

import java.util.List;

import com.ls.pub.util.DateUtil;
import com.lw.dao.player.QudaoStaDao;
import com.lw.vo.player.QudaoVO;

public class QudaoService
{
	public void insertQudao()
	{
		QudaoStaDao dao = new QudaoStaDao();
		List<QudaoVO> list = dao.selectnum(DateUtil.getTodayStr());
		for (int i = 0; i < list.size(); i++)
		{
			QudaoVO vo = list.get(i);
			dao.insertNum(vo.getQudao(), vo.getNum());
		}
	}
}
