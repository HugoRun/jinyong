package com.ben.guaji.service;

import java.sql.SQLException;
import java.util.List;

import com.ben.guaji.dao.GuajiDao;
import com.ben.guaji.vo.GetGoodVo;
import com.ben.guaji.vo.GuaJiConstant;
import com.ben.guaji.vo.GuajiVo;
import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.ben.vo.info.npc.NpcVO;
import com.ls.ben.vo.info.npc.NpcdropVO;
import com.ls.ben.vo.map.SceneVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.BasicInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.GoodsType;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.room.RoomService;

public class GuajiService
{
	private GuajiDao guajiDao = new GuajiDao();
	private RoomService roomService = new RoomService();
	private GoodsService goodsService = new GoodsService();

	// 根据自己的等级得到相应怪物所出现的场景
	public SceneVO findByOwnLevel(int level)
	{
		int scene_id = guajiDao.findNpcByLevel(level);
		return roomService.getById(scene_id + "");
	}

	public NpcVO findSceneOgreBySceneId(Object scene_id,int roleLevel)
	{
		return guajiDao.findSceneOgreBySceneId(scene_id,roleLevel);
	}

	public List<NpcdropVO> getNpcdropsByNpcID(int npc_ID, int start, int count)
	{
		return guajiDao.getNpcdropsByNpcID(npc_ID, start, count);
	}

	public int getNpcdropsCountByNpcID(int npc_ID)
	{
		return guajiDao.getNpcdropsByNpcID(npc_ID, 0, 0).size();
	}

	public void addAuto(GuajiVo guajiVo) throws SQLException
	{
		guajiDao.addAuto(guajiVo);
	}

	public int findIsGuaji(Object p_pk) throws SQLException
	{
		return guajiDao.findIsGuaji(p_pk);
	}

	public GuajiVo findByPpk(Object p_pk) throws SQLException
	{
		return guajiDao.findByPpk(p_pk);
	}

	public int GetNpcJilv(Object npc_id) throws SQLException
	{
		return guajiDao.GetNpcJilv(npc_id);
	}

	public List<NpcdropVO> getNpcdropsByNpcIDAndGood_id(int npc_id,
			String good_id)
	{
		return guajiDao.getNpcdropsByNpcIDAndGood_id(npc_id, good_id);
	}

	public void updateEndTime(long id)
	{
		guajiDao.updateEndTime(id);
	}

	public String giveGood(RoleEntity re,BasicInfo bi, List<GetGoodVo> list, int level)
	{
		StringBuffer sb = new StringBuffer();
		if (bi != null)
		{
			if (list != null)
			{
				for (GetGoodVo ggv : list)
				{

					switch (level)
					{
						case 0:
							if (ggv.getJi() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_JIPIN,
										ggv.getJi());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(极)*"
											+ ggv.getJi() + ".");
								}
							}
							if (ggv.getJing() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(),
										Equip.Q_LIANGHAO, ggv.getJing());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(精)*"
											+ ggv.getJing() + ".");
								}
							}
							if (ggv.getYou() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_YOUXIU,
										ggv.getYou());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(优)*"
											+ ggv.getYou() + ".");
								}
							}
							if (ggv.getCom() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_PUTONG,
										ggv.getCom());
								if (k != -1)
								{
									sb
											.append(ggv.getGood_name()
													+ (ggv.getGood_type() == GoodsType.PROP ? "*"
															: "(普通)*")
													+ ggv.getCom() + ".");
								}
							}
							break;
						case GuaJiConstant.ALL:
							if (ggv.getJi() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_JIPIN,
										ggv.getJi());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(极)*"
											+ ggv.getJi() + ".");
								}
							}
							if (ggv.getJing() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(),
										Equip.Q_LIANGHAO, ggv.getJing());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(精)*"
											+ ggv.getJing() + ".");
								}
							}
							if (ggv.getYou() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_YOUXIU,
										ggv.getYou());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(优)*"
											+ ggv.getYou() + ".");
								}
							}
							if (ggv.getCom() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_PUTONG,
										ggv.getCom());
								if (k != -1)
								{
									sb
											.append(ggv.getGood_name()
													+ (ggv.getGood_type() == GoodsType.PROP ? "*"
															: "(普通)*")
													+ ggv.getCom() + ".");
								}
							}
							break;
						case GuaJiConstant.YOU:
							if (ggv.getJi() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_JIPIN,
										ggv.getJi());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(极)*"
											+ ggv.getJi() + ".");
								}
							}
							if (ggv.getJing() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(),
										Equip.Q_LIANGHAO, ggv.getJing());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(精)*"
											+ ggv.getJing() + ".");
								}
							}
							if (ggv.getYou() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_YOUXIU,
										ggv.getYou());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(优)*"
											+ ggv.getYou() + ".");
								}
							}
							if (ggv.getGood_type() == GoodsType.PROP
									&& ggv.getCom() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_PUTONG,
										ggv.getCom());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "*"
											+ ggv.getCom() + ".");
								}
							}
							break;
						case GuaJiConstant.JING:
							if (ggv.getJi() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_JIPIN,
										ggv.getJi());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(极)*"
											+ ggv.getJi() + ".");
								}
							}
							if (ggv.getJing() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(),
										Equip.Q_LIANGHAO, ggv.getJing());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(精)*"
											+ ggv.getJing() + ".");
								}
							}
							if (ggv.getGood_type() == GoodsType.PROP
									&& ggv.getCom() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_PUTONG,
										ggv.getCom());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "*"
											+ ggv.getCom() + ".");
								}
							}
							break;
						case GuaJiConstant.JI:
							if (ggv.getJi() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_JIPIN,
										ggv.getJi());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(极)*"
											+ ggv.getJi() + ".");
								}
							}
							if (ggv.getGood_type() == GoodsType.PROP
									&& ggv.getCom() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_PUTONG,
										ggv.getCom());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "*"
											+ ggv.getCom() + ".");
								}
							}
							break;
						default:
							if (ggv.getJi() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_JIPIN,
										ggv.getJi());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(极)*"
											+ ggv.getJi() + ".");
								}
							}
							if (ggv.getJing() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(),
										Equip.Q_LIANGHAO, ggv.getJing());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(精)*"
											+ ggv.getJing() + ".");
								}
							}
							if (ggv.getYou() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_YOUXIU,
										ggv.getYou());
								if (k != -1)
								{
									sb.append(ggv.getGood_name() + "(优)*"
											+ ggv.getYou() + ".");
								}
							}
							if (ggv.getCom() != 0)
							{
								int k = give(re,bi,ggv.getNpcDropId(), ggv.getGood_id(), ggv
												.getGood_type(), Equip.Q_PUTONG,
										ggv.getCom());
								if (k != -1)
								{
									sb
											.append(ggv.getGood_name()
													+ (ggv.getGood_type() == GoodsType.PROP ? "*"
															: "(普通)*")
													+ ggv.getCom() + ".");
								}
							}
							break;
					}
				}

			}
		}
		return sb.toString();
	}

	private int give(RoleEntity re,BasicInfo bi,int npcdrop_id, int goods_id, int goods_type, int goods_quality,
			int goods_num)
	{
		if(bi==null){
			return -1;
		}
        int i = 0;
		try
		{
			i = findTask(npcdrop_id);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(i==0){
		return goodsService.putGoodsToWrap(bi.getPPk(), goods_id, goods_type,
				goods_quality, goods_num,GameLogManager.G_SYSTEM);
        }else{
    		TaskVO taskVOCache = TaskCache.getById(i+"");
    		String name = taskVOCache.getTZu();
    		if(re.getTaskInfo().getTaskCompleteInfo().taskCompleteBoo(name)){
    			return goodsService.putGoodsToWrap(bi.getPPk(), goods_id, goods_type,
    					goods_quality, goods_num,GameLogManager.G_SYSTEM);
    		}else{
    			return -1;
    		}
        }
	}

	public int findTask(int id) throws SQLException
	{
		return guajiDao.findTask(id);
	}
}
