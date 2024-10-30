package com.ls.model.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ls.ben.dao.mounts.MountsDao;
import com.ls.ben.vo.info.partinfo.PartInfoVO;
import com.ls.ben.vo.mounts.MountsVO;
import com.ls.ben.vo.mounts.UserMountsVO;
import com.ls.model.property.PropertyModel;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.mounts.MountsService;
import com.ls.web.service.player.EconomyService;

/**
 * @author ls
 * 玩家拥有的坐骑集合
 */
public class MountSet extends UserBase
{
	//当前乘坐的坐骑
	private int curMId=-1;
	//当前坐骑的效果
	private PropertyModel propertyModel = new PropertyModel();
	//拥有的坐骑<坐骑id,坐骑信息>
	public Map<Integer,UserMountsVO> mounts = null;
	private MountsDao md=null;
	
	public MountSet(int pPk)
	{
		super(pPk);
		md =new MountsDao();
		mounts = new HashMap<Integer,UserMountsVO>(10);
		List<UserMountsVO> list = md.getUserMountsList(pPk);
		for (UserMountsVO userMountsVO : list)
		{
			if( userMountsVO!=null )
			{
				if( userMountsVO.getMountsState()==1 )
				{
					curMId = userMountsVO.getId();
				}
				mounts.put(userMountsVO.getId(), userMountsVO);
			}
		}
	}

	/**
	 * 为角色增加坐骑
	 * @param uv
	 * @return
	 */
	public UserMountsVO addMounts(UserMountsVO uv)
	{
		if( uv==null )
		{
			return null;
		}
		md.addMounts(uv);
		if( uv.getId()>0 )
		{
			if( uv.getMountsState()==1)
			{
				this.changeCurMount(uv.getId());
			}
			mounts.put(uv.getId(), uv);
			return uv;
		}
		return null;
	}
	
	/**
	 * 查询角色现在拥有的所有坐骑信息
	 * @return
	 */
	public List<UserMountsVO> getUserMountsList()
	{
		return new ArrayList<UserMountsVO>(mounts.values());
	}
	
	/**
	 * 坐骑升级返回1升级成功2晶石不足0升级失败
	 * @param ppk
	 * @param mountsID
	 * @param nextLevelMountsID
	 * @return
	 */
	public int upgrade(int mountsID,int nextLevelMountsID)
	{
		int num=0;
		RoleEntity roleEntity = this.getRoleEntity();
		EconomyService es=new EconomyService();
		MountsService mountsService = new MountsService();
		MountsVO mv=mountsService.getMountsInfo(nextLevelMountsID);
		int needCopper=mv.getUplevelPay();
		long haveCopper= es.getYuanbao(roleEntity.getUPk());
		if(needCopper>haveCopper)
		{
			num=2;
			return num;
		}
		//删除原来的坐骑
		this.deleteMount(mountsID);
		//添加升级后的坐骑
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(this.p_pk);
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		//扣除升级花费的钱
		if(uv!=null)
		{
			es.spendYuanbao(roleEntity.getUPk(), needCopper);
			num = 1;
		}
		return num;
	}
	/**
	 * 坐骑升级 电信消费专用
	 * 坐骑升级返回1升级成功2晶石不足0升级失败
	 */
	public int upgrade(HttpServletRequest request,int mountsID,int nextLevelMountsID)
	{
		int num=0;
		RoleEntity roleEntity = this.getRoleEntity();
		MountsService mountsService = new MountsService();
		MountsVO mv=mountsService.getMountsInfo(nextLevelMountsID);
		MallService ms=new MallService();
		String hint=ms.consumeForTele(request, roleEntity, nextLevelMountsID+"","1");
		if(hint!=null)
		{
			num=2;
			return num;
		}
		//删除原来的坐骑
		this.deleteMount(mountsID);
		//添加升级后的坐骑
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(this.p_pk);
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		return num;
	}

	/**
	 * 购买坐骑返回0购买失败1购买成功2已经有此坐骑3钱不够
	 * @param mountsID
	 * @return
	 */
	public int buyMounts(int mountsID)
	{
		int num=0;
		RoleEntity roleEntity = this.getRoleEntity();
		//判断是否已经有此坐骑
		UserMountsVO u_mount=this.getById(mountsID);
		if(u_mount!=null)
		{
			num=2;
			return num;
		}
		EconomyService es=new EconomyService();
		MountsService mountsService = new MountsService();
		MountsVO mv=mountsService.getMountsInfo(mountsID);
		int needCopper=mv.getSentPrice();
		long haveCopper=es.getYuanbao(roleEntity.getUPk());
		//判断钱是否够
		if(needCopper>haveCopper)
		{
			num=3;
			return num;
		}
		//添加新买的的坐骑
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(roleEntity.getPPk());
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		if(uv!=null)
		{
			es.spendYuanbao(roleEntity.getBasicInfo().getUPk(), needCopper);
			num = 1;
		}
		return num;
	}
	/**
	 * 购买坐骑电信专用 
	 * 返回0购买失败1购买成功2已经有此坐骑3钱不够
	 */
	public int buyMounts(HttpServletRequest request,int mountsID)
	{
		int num=0;
		RoleEntity roleEntity = this.getRoleEntity();
		//判断是否已经有此坐骑
		UserMountsVO u_mount=this.getById(mountsID);
		if(u_mount!=null)
		{
			num=2;
			return num;
		}
		MountsService mountsService = new MountsService();
		MountsVO mv=mountsService.getMountsInfo(mountsID);
		//判断钱是否够
		MallService ms=new MallService();
		String hint=ms.consumeForTele(request, roleEntity, mountsID+"", "1");
		if(hint!=null)
		{
			num=3;
			return num;
		}
		//添加新买的的坐骑
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(roleEntity.getPPk());
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		return num;
	}
	
	/*************取消乘骑****************/
	public void cancelCurMount()
	{
		if( this.curMId==-1 )
		{
			return;
		}
		UserMountsVO curMount = this.getById(this.curMId);
		if( curMount!=null )
		{
			curMount.setMountsState(0);
		}
		this.curMId=-1;
		propertyModel.init();
		md.updateMountState1(this.p_pk);
	}
	/**
	 * 通过坐骑id得到坐骑信息
	 * @return
	 */
	private UserMountsVO getById( int mId )
	{
		return this.mounts.get(mId);
	}
	
	/**
	 * 玩家点击换乘
	 * @param mountID
	 */
	public void changeCurMount(int mountID)
	{
		UserMountsVO u_mount = this.getById(mountID);
		if( u_mount!=null )
		{
			if( this.curMId!=-1 )
			{
				cancelCurMount();
			}
			this.curMId = mountID;
			u_mount.setMountsState(1);
			propertyModel.appendPropertyByAttriStr(u_mount.getMountInfo().getBuff(), 1);
			md.updateMountState2(this.p_pk, mounts.get(mountID).getMountsID());
		}
	}
	
	/**
	 * 加载坐骑效果
	 */
	public void loadPropertys(PartInfoVO player)
	{
		if( this.getCurMount()!=null)
		{
			UserMountsVO u_mount=this.getCurMount();
			propertyModel.init();
			propertyModel.appendPropertyByAttriStr(u_mount.getMountInfo().getBuff(), 1);
			propertyModel.loadPropertys(player);
		}
	}
	/**
	 * 得到坐骑的hash
	 */
	public UserMountsVO getMounts(int uMontsId)
	{
		return this.mounts.get(uMontsId);
	}
	
	/**
	 * 玩家点击遗弃坐骑
	 * @param mountsID
	 */
	public void deleteMount(int mountsID)
	{
		if( mountsID==this.curMId)
		{
			this.curMId=-1;
		}
		md.deleteUserMounts(this.p_pk, mounts.get(mountsID).getMountsID());
		this.mounts.remove(mountsID);
	}
	
	/**
	 * 根据PPK得到玩家当前在乘骑状态的坐骑
	 * @param ppk
	 * @return
	 */
	public UserMountsVO getCurMount()
	{
		return this.getById(this.curMId);
	}

	public  int getCurMId()
	{
		return curMId;
	}
}