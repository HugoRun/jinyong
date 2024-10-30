package com.ls.web.service.pk;

import java.util.List;

import com.ls.ben.dao.pkhite.PKHiteDao;
import com.ls.ben.vo.pkhite.PKHiteVO;

/**
 * ������ҳ���б����
 * @author Thomas.lei
 */
public class PKHiteService
{
	PKHiteDao pd=new PKHiteDao();
	/** *****�鿴�Ƿ������Ѿ��г�޼�¼,������򷵻ؼ�¼******* */
	public PKHiteVO checkHiteInfo(int ppk,int enemyPpk)
	{
		return pd.checkIsHaveHiteRecord(ppk, enemyPpk);
	}
	/** ******��������һ���µĳ�޶���********** */
	public void addEnemy(PKHiteVO pv)
	{
		pd.addEnemy(pv);
	}
	/** ******����Ѿ��г�޶�������³�޵�******** */
	public void updateHitePoint(PKHiteVO pv)
	{
		pd.updateHitePoint(pv);
	}
	/**********��ҳ��ѯ��ҵĳ�ޱ�************/
	public List<PKHiteVO> getEnemys(int ppk,int index,int limit)
	{
		return pd.getEnemys(ppk, index, limit);
	}

	/*************�õ���¼������**************/
	public int getRecordNum(int ppk)
	{
		return pd.getRecordNum(ppk);
	}
	/********���ɾ����ɫ��ʱ��ɾ�����еĳ����Ϣ********/
	public void removeHiteInfo(int ppk)
	{
		pd.removeHiteInfo(ppk);
	}
}
