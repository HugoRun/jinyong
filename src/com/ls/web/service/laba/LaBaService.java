package com.ls.web.service.laba;

import java.util.ArrayList;
import java.util.Random;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.ben.vo.laba.labaVO;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.npc.NpcService;
import com.ls.web.service.player.PlayerService;
import com.ls.web.service.rank.RankService;

public class LaBaService
{
	Random rd = new Random();

	/**
	 * һ�����ʣ�������һ����Ʒ����Ϊ������Ľ���
	 * 
	 * @return
	 */
	public labaVO getGood(String prop_id, int p_pk, RoleEntity roleInfo,
			int other)
	{
		// ����Ӧ���Ǵ����ݿ��еõ�NPCid��Ȼ���ո��ʴ�NPC��id��ȡһ��
		PropVO pv = PropCache.getPropById(Integer.parseInt(prop_id));
		String s_NPC[] = pv.getPropOperate1().split(",");
		// Ȼ����ݵõ���NPC_id�õ�һ����Ʒ������
		PlayerService playerService = new PlayerService();
		PartInfoVO player = playerService.getPlayerByPpk(roleInfo
				.getBasicInfo().getPPk());
		NpcService npcService = new NpcService();
		int npc_id = 0;
		int num = 0;
		if (other == 0)// ������¿�����
		{
			num = rd.nextInt(100);// ��ô�͵õ�һ�������
		}
		else
		// �����¿�����
		{
			num = other;// ��num��ֵ
		}
		String s_NPC_id[] = new String[2];
		int daNum = 0;// �󽱵Ľ���ֵ
		int zhongNum = 0;// �н��Ľ���ֵ
		daNum = Integer.parseInt(s_NPC[0].split("-")[1]);
		zhongNum = Integer.parseInt(s_NPC[1].split("-")[1]);
		// ��num��ֵ��Ȼ����ݼ��ʷ�Χȷ���õ����Ǵ󽱡��н����ǹ�����
		if (num >= 0 && num <= daNum)
		{
			s_NPC_id = s_NPC[0].split("-");
			npc_id = Integer.parseInt(s_NPC_id[0]);// �õ���npc
		}
		else
			if (num >= (daNum + 1) && num <= zhongNum)
			{
				s_NPC_id = s_NPC[1].split("-");
				npc_id = Integer.parseInt(s_NPC_id[0]);// �õ��н�npc
			}
			else
			{
				s_NPC_id = s_NPC[2].split("-");
				npc_id = Integer.parseInt(s_NPC_id[0]);// �õ�������npc
			}
		NpcdropVO npcVo = npcService
				.dropGoodsByLabaBoxTwo(npc_id, player, p_pk);// Ȼ��ӵ������õ�һ����Ʒ
		// npcVo.setGoodsName(npcVo.getGoodsName() + "-" + num);//
		// ��num��������������������ʾ
		labaVO lv = new labaVO();
		lv.setNvo(npcVo);
		lv.setSNum(num);
		return lv;// ���ػ����Ʒ
	}

	/**
	 * ���ѿ����ı�����Ʒ���³�ȡһ���󽱡�С�����߹���
	 * 
	 * @return
	 */
	public ArrayList<String> getGoodTwo(String da_id, String zhong_id,
			String xiao_id, int daNum, int zhongNum, String NPC_DA,
			String NPC_ZHONG, String NPC_XIAO, String daName, String zhongName,
			String xiaoName, String daType, String zhongType, String xiaoType)
	{
		ArrayList<String> idAndName = new ArrayList<String>();
		// ����Ӧ���Ǵ����ݿ��еõ�NPCid��Ȼ���ո��ʴ�NPC��id��ȡһ��
		String name = "";
		int num = rd.nextInt(100);
		// ��num��ֵ��Ȼ����ݼ��ʷ�Χȷ���õ����Ǵ󽱡��н����ǹ�����
		if (num >= 0 && num <= daNum)
		{
			name = "�󽱣�" + daName;// �õ���npc
			idAndName.add(name);
			idAndName.add(da_id);
			idAndName.add(NPC_DA);
			idAndName.add(daType);
		}
		else
			if (num >= (daNum + 1) && num <= zhongNum)
			{
				name = "С����" + zhongName;// �õ��н�npc
				idAndName.add(name);
				idAndName.add(zhong_id);
				idAndName.add(NPC_ZHONG);
				idAndName.add(zhongType);
			}
			else
			{
				name = "��������" + xiaoName;// �õ�������npc
				idAndName.add(name);
				idAndName.add(xiao_id);
				idAndName.add(NPC_XIAO);
				idAndName.add(xiaoType);
			}
		return idAndName;// ���ػ����Ʒ
	}

	/**
	 * ���ݱ���ID�õ���������
	 * 
	 * @return
	 */
	public PropVO getGoodByID(String prop_id)
	{
		return PropCache.getPropById(Integer.parseInt(prop_id));// ���ػ�õ�����
	}

	/**
	 * �鿴����Ҫ����Ʒ�Ƿ����������
	 * 
	 * @param pg_pk
	 * @param gold_box_pgpk
	 * @param roleInfo
	 * @return
	 */
	public boolean checkHaveThisProp(String prop_id, RoleEntity roleInfo)
	{
		boolean boo = false;
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		boo = dao.getUserHasProp(roleInfo.getBasicInfo().getPPk(), Integer
				.parseInt(prop_id));
		return boo;
	}

	/**
	 * ����Ʒ��ӵ���������Ƴ���һ������
	 * 
	 * @param goodId
	 * @param roleInfo
	 * @param prop_id
	 * @param p_pk
	 * @return
	 */
	public String getGoodAndRemoveABox(String goodId, int prop_id, int p_pk,
			int good_type)
	{
		GoodsService goodsService = new GoodsService();
		String name = "";
		// ����Ʒ������ұ�����
		int a = goodsService.putGoodsToWrap(p_pk, Integer.parseInt(goodId),
				good_type, 1);
		if (a == -1)
		{
			name = "��ı�������";
		}
		else
		{
			name = "��Ʒ�ѷ������ı�������ע��鿴!";
		}
		return name;
	}

	/**
	 * �ڵ��ʹ�õ�ʱ����Ƴ���һ������
	 * 
	 * @param goodId
	 * @param roleInfo
	 * @param prop_id
	 * @param p_pk
	 * @return
	 */
	public void RemoveBox(RoleEntity roleInfo, int prop_id, int p_pk)
	{
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		GoodsService goodsService = new GoodsService();
		// ��ñ����pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo
				.getBasicInfo().getPPk(), prop_id);
		// ɾ����һ������
		goodsService.removeProps(boxGroupVO, 1);
		// ��¼�±������Ϣ
		String recordString = roleInfo.getBasicInfo().getName() + "һ�����Ա���";
		insertRecordInfo(roleInfo.getBasicInfo().getPPk(), 2, recordString);
	}

	/**
	 * �Ƴ�ˢ�±������õĵ���
	 * 
	 * @param goodId
	 * @param roleInfo
	 * @param prop_id
	 * @param p_pk
	 * @return
	 */
	public boolean RemovePropOfBox(RoleEntity roleInfo, int prop_id)
	{
		boolean boo = false;
		PlayerPropGroupDao dao = new PlayerPropGroupDao();
		GoodsService goodsService = new GoodsService();
		// ��ñ����pg_pk
		PlayerPropGroupVO boxGroupVO = dao.getPropGroupByTime(roleInfo
				.getBasicInfo().getPPk(), prop_id);
		// ɾ����һ������
		goodsService.removeProps(boxGroupVO, 1);
		boo = true;
		return boo;
	}

	/**
	 * �����ʾ��
	 * 
	 * @return
	 */
	public String getWords()
	{
		String word = "";
		String words[] = { "��������", "��ϲ�lؔ", "�帣�R�T" };
		word = words[rd.nextInt(3)];
		return word;
	}

	/**
	 * ������ʾ��һ��
	 * 
	 * @param strs
	 * @param num
	 * @param goodid
	 * @param count
	 * @return
	 */
	public ArrayList<String> getListThree(String strs[], String num, int count)
	{
		ArrayList<String> al = new ArrayList<String>();
		String sWord = "";
		if (count == 0)
		{// ����ǵ�һ�ε�
			if (num.equals("1")) // �������ĵ�һ��
			{
				strs[0] = getWords();
			}
			if (num.equals("2")) // �������ĵڶ���
			{
				strs[1] = getWords();
			}
			if (num.equals("3")) // �������ĵ�����
			{
				strs[2] = getWords();
			}
		}
		else
			if (count == 1 || count == 2)// �ڶ��ε��
			{
				for (int i = 0; i < strs.length; i++)
				{
					if (!strs[i].equals("-1"))
					{
						sWord = strs[i];
						break;
					}
				}
				if (num.equals("1")) // �������ĵ�һ��
				{
					strs[0] = sWord;
				}
				if (num.equals("2")) // �������ĵڶ���
				{
					strs[1] = sWord;
				}
				if (num.equals("3")) // �������ĵ�����
				{
					strs[2] = sWord;
				}
			}
		al.add(strs[0]);
		al.add(strs[1]);
		al.add(strs[2]);
		return al;
	}

	/**
	 * ������ʾ��һ��
	 * 
	 * @param strs
	 * @param num
	 * @param goodid
	 * @param count
	 * @return
	 */
	public ArrayList<String> getListTwo(String strs[], String num, int count)
	{
		ArrayList<String> al = new ArrayList<String>();
		String ss[] = new String[2];
		Random rd = new Random();
		if (count == 0)
		{// ����ǵ�һ�ε�
			if (num.equals("1")) // �������ĵ�һ��
			{
				strs[0] = getWords();
			}
			if (num.equals("2")) // �������ĵڶ���
			{
				strs[1] = getWords();
			}
			if (num.equals("3")) // �������ĵ�����
			{
				strs[2] = getWords();
			}
		}
		else
			if (count == 1)// �ڶ��ε��
			{
				if (num.equals("1")) // �������ĵ�һ��
				{
					strs[0] = getWords();
				}
				if (num.equals("2")) // �������ĵڶ���
				{
					strs[1] = getWords();
				}
				if (num.equals("3")) // �������ĵ�����
				{
					strs[2] = getWords();
				}
			}
			else
				if (count == 2)
				{
					if (num.equals("1")) // �������ĵ�һ��
					{
						if (strs[1].equals(strs[2]))
						{
							do
							{
								strs[0] = getWords();
							} while (strs[0].equals(strs[1]));
						}
						else
						{
							ss[0] = strs[1];
							ss[1] = strs[2];
							strs[0] = ss[rd.nextInt(2)];
						}
					}
					if (num.equals("2")) // �������ĵڶ���
					{
						if (strs[0].equals(strs[2]))
						{
							do
							{
								strs[1] = getWords();
							} while (strs[1].equals(strs[0]));
						}
						else
						{
							ss[0] = strs[0];
							ss[1] = strs[2];
							strs[1] = ss[rd.nextInt(2)];
						}
					}
					if (num.equals("3")) // �������ĵ�����
					{
						if (strs[0].equals(strs[1]))
						{
							do
							{
								strs[2] = getWords();
							} while (strs[2].equals(strs[0]));
						}
						else
						{
							ss[0] = strs[0];
							ss[1] = strs[1];
							strs[2] = ss[rd.nextInt(2)];
						}
					}
				}
		al.add(strs[0]);
		al.add(strs[1]);
		al.add(strs[2]);
		return al;
	}

	/**
	 * ������ʾ�ֶ���һ��
	 * 
	 * @param strs
	 * @param num
	 * @param goodid
	 * @param count
	 * @return
	 */
	public ArrayList<String> getList(String strs[], String num, int count)
	{
		ArrayList<String> al = new ArrayList<String>();
		if (count == 0)
		{// ����ǵ�һ�ε�
			if (num.equals("1")) // �������ĵ�һ��
			{
				strs[0] = getWords();
			}
			if (num.equals("2")) // �������ĵڶ���
			{
				strs[1] = getWords();
			}
			if (num.equals("3")) // �������ĵ�����
			{
				strs[2] = getWords();
			}
		}
		else
			if (count == 1)// �ڶ��ε��
			{
				String sWord = "";
				for (int i = 0; i < strs.length; i++)
				{
					if (!strs[i].equals("-1"))
					{
						sWord = strs[i];
						break;
					}
				}
				if (num.equals("1")) // �������ĵ�һ��
				{
					do
					{
						strs[0] = getWords();
					} while (strs[0].equals(sWord));
				}
				if (num.equals("2")) // �������ĵڶ���
				{
					do
					{
						strs[1] = getWords();
					} while (strs[1].equals(sWord));
				}
				if (num.equals("3")) // �������ĵ�����
				{
					do
					{
						strs[2] = getWords();
					} while (strs[2].equals(sWord));
				}
			}
			else
				if (count == 2)
				{
					if (num.equals("1")) // �������ĵ�һ��
					{
						do
						{
							strs[0] = getWords();
						} while (strs[0].equals(strs[1])
								|| strs[0].equals(strs[2]));
					}
					if (num.equals("2")) // �������ĵڶ���
					{
						do
						{
							strs[1] = getWords();
						} while (strs[1].equals(strs[0])
								|| strs[1].equals(strs[2]));
					}
					if (num.equals("3")) // �������ĵ�����
					{
						do
						{
							strs[2] = getWords();
						} while (strs[2].equals(strs[0])
								|| strs[2].equals(strs[1]));
					}
				}
		al.add(strs[0]);
		al.add(strs[1]);
		al.add(strs[2]);
		return al;
	}

	/**
	 * �ѱ������Ϣ��¼����
	 * 
	 * @param pk
	 * @param info_type
	 * @param content
	 */
	private void insertRecordInfo(int pPk, int info_type, String content)
	{
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		playerPropGroupDao.sendRecordGoldInfo(pPk, info_type, content);
		if (info_type == 1)
		{
			// ͳ����Ҫ
			new RankService().updateAdd(pPk, "open", 1);
		}
	}
}
