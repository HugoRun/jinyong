package com.ls.web.service.pk;

import java.util.List;

import com.ls.ben.dao.pkhite.PKHiteDao;
import com.ls.ben.vo.pkhite.PKHiteVO;

/**
 * 处理玩家仇恨列表的类
 * @author Thomas.lei
 */
public class PKHiteService
{
	PKHiteDao pd=new PKHiteDao();
	/** *****查看是否此玩家已经有仇恨记录,如果有则返回记录******* */
	public PKHiteVO checkHiteInfo(int ppk,int enemyPpk)
	{
		return pd.checkIsHaveHiteRecord(ppk, enemyPpk);
	}
	/** ******给玩家添加一个新的仇恨对象********** */
	public void addEnemy(PKHiteVO pv)
	{
		pd.addEnemy(pv);
	}
	/** ******玩家已经有仇恨对象则更新仇恨点******** */
	public void updateHitePoint(PKHiteVO pv)
	{
		pd.updateHitePoint(pv);
	}
	/**********分页查询玩家的仇恨表************/
	public List<PKHiteVO> getEnemys(int ppk,int index,int limit)
	{
		return pd.getEnemys(ppk, index, limit);
	}

	/*************得到记录总条数**************/
	public int getRecordNum(int ppk)
	{
		return pd.getRecordNum(ppk);
	}
	/********玩家删除角色的时候删除所有的仇恨信息********/
	public void removeHiteInfo(int ppk)
	{
		pd.removeHiteInfo(ppk);
	}
}
