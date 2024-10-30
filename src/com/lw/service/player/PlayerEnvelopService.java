package com.lw.service.player;

import java.util.Date;

import com.lw.dao.player.PlayerEnvelopPpkDao;

public class PlayerEnvelopService
{
	/**
	 * ��ѯ����Ƿ��ڷ��״̬ ����з���ʱ�� ���û�� ����null
	 * 
	 * @param p_pk
	 *            ���ID
	 * @return time nullΪû�� ���� ʱ���STRING����
	 */
	public String getPlayerEnvelop(int p_pk)
	{
		String time = null;
		Date date = new Date();
		PlayerEnvelopPpkDao dao = new PlayerEnvelopPpkDao();
		if (dao.getPlayerFromEnvelop(p_pk) == true)
		{
			Date t1 = dao.getPlayerEnvelop(p_pk);
			long i = (t1.getTime() - date.getTime()) / 60000;
			if (i < 0)
			{
				dao.updatePlayerEnvelop(p_pk);
				return time;
			}
			else
			{
				if (i >= 1)
				{
					time = String.valueOf(i);
					return time;
				}
				else
				{
					time = "1";
					return time;
				}
			}
		}
		else
		{
			return time;
		}
	}

	/**
	 * �õ�����Ƿ����÷�� ����true Ϊ���÷�� falseΪû��
	 */
	public boolean getPlayerEnvelopForever(int p_pk)
	{
		PlayerEnvelopPpkDao dao = new PlayerEnvelopPpkDao();
		boolean x = dao.getPlayerEnvelopForever(p_pk);
		return x;
	}
}
