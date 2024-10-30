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
 * ���ӵ�е����Ｏ��
 */
public class MountSet extends UserBase
{
	//��ǰ����������
	private int curMId=-1;
	//��ǰ�����Ч��
	private PropertyModel propertyModel = new PropertyModel();
	//ӵ�е�����<����id,������Ϣ>
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
	 * Ϊ��ɫ��������
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
	 * ��ѯ��ɫ����ӵ�е�����������Ϣ
	 * @return
	 */
	public List<UserMountsVO> getUserMountsList()
	{
		return new ArrayList<UserMountsVO>(mounts.values());
	}
	
	/**
	 * ������������1�����ɹ�2��ʯ����0����ʧ��
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
		//ɾ��ԭ��������
		this.deleteMount(mountsID);
		//��������������
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(this.p_pk);
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		//�۳��������ѵ�Ǯ
		if(uv!=null)
		{
			es.spendYuanbao(roleEntity.getUPk(), needCopper);
			num = 1;
		}
		return num;
	}
	/**
	 * �������� ��������ר��
	 * ������������1�����ɹ�2��ʯ����0����ʧ��
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
		//ɾ��ԭ��������
		this.deleteMount(mountsID);
		//��������������
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(this.p_pk);
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		return num;
	}

	/**
	 * �������ﷵ��0����ʧ��1����ɹ�2�Ѿ��д�����3Ǯ����
	 * @param mountsID
	 * @return
	 */
	public int buyMounts(int mountsID)
	{
		int num=0;
		RoleEntity roleEntity = this.getRoleEntity();
		//�ж��Ƿ��Ѿ��д�����
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
		//�ж�Ǯ�Ƿ�
		if(needCopper>haveCopper)
		{
			num=3;
			return num;
		}
		//�������ĵ�����
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
	 * �����������ר�� 
	 * ����0����ʧ��1����ɹ�2�Ѿ��д�����3Ǯ����
	 */
	public int buyMounts(HttpServletRequest request,int mountsID)
	{
		int num=0;
		RoleEntity roleEntity = this.getRoleEntity();
		//�ж��Ƿ��Ѿ��д�����
		UserMountsVO u_mount=this.getById(mountsID);
		if(u_mount!=null)
		{
			num=2;
			return num;
		}
		MountsService mountsService = new MountsService();
		MountsVO mv=mountsService.getMountsInfo(mountsID);
		//�ж�Ǯ�Ƿ�
		MallService ms=new MallService();
		String hint=ms.consumeForTele(request, roleEntity, mountsID+"", "1");
		if(hint!=null)
		{
			num=3;
			return num;
		}
		//�������ĵ�����
		UserMountsVO uv=new UserMountsVO();
		uv.setPpk(roleEntity.getPPk());
		uv.setMountsID(mv.getId());
		uv=addMounts(uv);
		return num;
	}
	
	/*************ȡ������****************/
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
	 * ͨ������id�õ�������Ϣ
	 * @return
	 */
	private UserMountsVO getById( int mId )
	{
		return this.mounts.get(mId);
	}
	
	/**
	 * ��ҵ������
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
	 * ��������Ч��
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
	 * �õ������hash
	 */
	public UserMountsVO getMounts(int uMontsId)
	{
		return this.mounts.get(uMontsId);
	}
	
	/**
	 * ��ҵ����������
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
	 * ����PPK�õ���ҵ�ǰ�ڳ���״̬������
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