package com.ben.pk.active;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.model.user.RoleEntity;

/**
 * ���ܣ�PK�Service
 * 
 * @author thomas.lei 27/04/10 PM
 */
public class PKActiveService
{
	PKActiveDao pd = new PKActiveDao();

	// PK��ұ���
	public int pkActiveRegist(PKActiveRegist role)
	{
		int count = pd.pkActiveRegist(role);
		return count;
	}

	// ��ѯ����Ƿ�����ʷ������¼
	public PKActiveRegist checkRoleRegist(int roleID)
	{
		PKActiveRegist pr = pd.checkRoleRegist(roleID);
		return pr;
	}

	// �������Ѿ�����ʷ������¼����±�����¼
	public int refreshRegist(PKActiveRegist role)
	{
		int count = pd.refreshRegist(role);
		return count;
	}

	//����������Ϣ  ÿ��1��2��3�Ų�����  ����ÿ��12:00�ɶ�ʱ���Զ�����-------------------------------------------------
	public void createVSInfo()
	{
		pd.deleteVsInfo();// ɾ��ԭ���Ķ�����Ϣ
		Map vsInfo = pd.getRoleInfo();
		Map map = getVSList(pd.getRoleIDs());
		for (Object id : map.keySet())
		{
			PKVs vs = new PKVs();
			Integer roleAId = (Integer) id;
			Integer roleBId = (Integer) map.get(id);
			String roleAName = (String) vsInfo.get(roleAId);
			String roleBName = (String) vsInfo.get(roleBId);
			if (roleBId == -1)// ����ֿ�ֱ�ӽ�����һ��
			{
				pd.updateIsWin(roleAId, 0);
				continue;
			}
			vs.setRoleAID(roleAId);
			vs.setRoleBID(roleBId);
			vs.setRoleAName(roleAName);
			vs.setRoleBName(roleBName);
			vs.setWinRoleID(0);
			pd.addVsInfo(vs);
		}
		pd.updateEnterState();
	}

	// ��ѯ������Ϣ ��ѯ�������
	public List<PKVs> getVsInfo(int index, int limit)
	{
		List<PKVs> list = pd.getVsInfo(index, limit);
		return list;
	}

	// PK��ʼ�����PK��� ʤ������ʧ��
	public void updatePkInfo(int roleId, int isWin)
	{
		pd.updateIsWin(roleId, isWin);
	}
	//���¶�����ʤID
	public void updateWinID(int winRoleId)
	{
		pd.updateWinRoleID(winRoleId);
	}
	// �õ�������Ϣ��������
	public int getTotalNum()
	{
		return pd.getTotalNum();
	}

	// �õ����ֵ�ppk
	public int getPpk(int ppk)
	{
		int p = pd.getAppk(ppk);
		if (p == 0)
		{
			p = pd.getBppk(ppk);
		}
		return p;
	}

	// �������Ѿ�ʧ����Ҳ����Խ������
	public boolean checkIsFail(int roleId)
	{
		return pd.checkIsFail(roleId);
	}

	
	// ������ҽ��볡����״̬
	public int updateEnterState(int roleId,int state)
	{
		return pd.updateEnterState(roleId,state);
	}

	// ��ʱ��û�н���������ص���ҵĸ��� ������Ϊ�� ������ʼ5�����Ժ󲻽�������������и� ÿ��123�Ų�ִ��  ����13:05ִ��һ��
	public int updateOutofTime()
	{
		List ids=pd.getOutofEnterIDs();
		if(ids!=null&&ids.size()!=0)
		{
			for (int i = 0; i <ids.size(); i++)
			{
				int aid=Integer.parseInt(ids.get(i).toString());
				int bid=this.getPpk(aid);
				if(bid==0||ids.contains(bid))
				{
					continue;
				}
				else
				{
					this.updatePriceState(bid, 1);
					this.updateWinID(bid);
				}
			}
		}
		int count= pd.updateOutofTime();
		return count;
	}
	// �õ�û�н���Ķ�����Ϣ
	public Map<Integer, Integer> getOutOfVs()
	{
		return pd.getNoresultVs();

	}

	// ����PK����30���ӵĶ�����Ϣ  ÿ��123��ִ�� ����ÿ��5:30ִ��һ��
	public void outOfTime()
	{
		RoleCache roleCache = new RoleCache();
		Map<Integer, Integer> map = this.getOutOfVs();
		// ȥ��˫�Ŷ�û�вμӱ����� ʣ�µľ�������PK��ʱ�����
		List temp = new ArrayList();
		for (Integer id : map.keySet())
		{
			RoleEntity roleEntity = roleCache.getByPpk(id);
			if ((roleEntity == null)
					|| (roleEntity.getBasicInfo().getSceneId() != PKActiveContent.SCENEID_PK))
			{
				temp.add(id);
			}
		}
		for (int i = 0; i < temp.size(); i++)
		{
			map.remove(temp.get(i));
		}
		// �Ƚ���ҵĵȼ�����Ѫ���ж�˭ʤ��
		for (Integer id : map.keySet())
		{
			RoleEntity roleAEntity = roleCache.getByPpk(id);// ���A
			RoleEntity roleBEntity = roleCache.getByPpk(map.get(id));// ���B
			if (roleAEntity.getBasicInfo().getGrade() < roleBEntity
					.getBasicInfo().getGrade())// �ȼ�С��ȡʤ
			{
				this.updatePkInfo(map.get(id),1);
				this.updatePriceState(id, 1);
				this.updateWinID(id);
			}
			else if (roleAEntity.getBasicInfo().getGrade() == roleBEntity
						.getBasicInfo().getGrade())
				{
					if (roleAEntity.getBasicInfo().getHp() > roleBEntity
							.getBasicInfo().getHp())
					{
						this.updatePkInfo(map.get(id),1);
						this.updatePriceState(id, 1);
						this.updateWinID(id);
					}
					else
					{
						this.updatePkInfo(id, 1);
						this.updatePriceState(map.get(id), 1);
						this.updateWinID(map.get(id));
					}
				}
				else
				{
					this.updatePkInfo(id,1);
					this.updatePriceState(map.get(id), 1);
					this.updateWinID(map.get(id));
				}
			roleAEntity.getBasicInfo().updateSceneId(PKActiveContent.NPCSCENEID);// /////////////////////////////////////////////senceID
			roleBEntity.getBasicInfo().updateSceneId(PKActiveContent.NPCSCENEID);// /////////////////////////////////////////////senceID
		}
	}

	// ��ѯ�Է�������
	public String getOherName(int roleId)
	{
		int ppk = this.getPpk(roleId);
		return pd.getOherName(ppk);
	}
	//����Ƿ������ȡ��Ʒ
	public boolean isGetPrice(int roleID)
	{
		return pd.isGetPrice(roleID);
	}
	//�޸���ȡ��Ʒ��״̬
	public void updatePriceState(int roleID,int isPrice)
	{
		pd.updatePriceState(roleID, isPrice);
	}
	//�жϲ��������Ƿ���˰�ǿ ��ǿ ���߰���� 
	public int getPlayerNum()
	{
		int num= pd.getPlayerNum();
		if(num>PKActiveContent.BAQIANG)
		{
			return PKActiveContent.PPRICEID;
		}
		if(num==PKActiveContent.BAQIANG)
		{
			return PKActiveContent.BPRICEID;
		}
		if(num==PKActiveContent.SIQIANG)
		{
			return PKActiveContent.SPRICEID;
		}
		if(num==PKActiveContent.BANJUESAI)
		{
			return PKActiveContent.BJPRICEID;
		}
		if(num==PKActiveContent.GUANJUN)
		{
			return PKActiveContent.GPRICEID;
		}
		else
		{
			return PKActiveContent.PPRICEID;
		}
	}
	//��ѯ���еı��������Ϣ
	public List getAllRole()
	{
		return pd.getAllRole();
	}

	/**
	 * ������ҵĵȼ��Զ��������ӳ����Ϣ
	 * 
	 * @param rolesID
	 * @return map �������ID���ӳ����Ϣ
	 */
	public Map<Integer, Integer> getVSList(int[] IDs)
	{
		Map map = new HashMap<Integer, Integer>();
		for (int i = 0; i < IDs.length; i++)
		{
			if (i % 2 == 0)
			{
				if (i != IDs.length - 1)
				{
					map.put(IDs[i], IDs[i + 1]);
				}
				else
				{
					map.put(IDs[i], -1);
				}
			}
		}
		return map;
	}

}
