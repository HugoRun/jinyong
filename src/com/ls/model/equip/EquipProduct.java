package com.ls.model.equip;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.info.partinfo.PlayerPropGroupDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.info.partinfo.PlayerEquipVO;
import com.ls.model.log.GameLogManager;
import com.ls.model.user.UserBase;
import com.ls.pub.constant.Equip;
import com.ls.pub.constant.PropType;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.MathUtil;
import com.ls.web.service.goods.GoodsService;

/**
 * @author ls
 * װ���ӹ�������
 */
abstract public class EquipProduct extends UserBase
{
	//�ӹ�����
	public static final int upgrade=1;
	public static final int change_wx=2;
	
	private String actionName;//��������
	
	//Ҫ�ӹ���װ��
	protected PlayerEquipVO equip = null;
	
	//�������
	private int[] material_id_list;//��Ҫ�Ĳ����б�
	private int[] need_materials_num;//��Ҫ�Ĳ�������
	private int[] cur_materials_num;//��ǰӵ�еģ�ʹ�õģ���������
	
	//�ɹ��ʱ�ʯ
	private  int rate_stone_id;//�����ɹ��ʲ���id
	private  int rate_stone_num;//�����ɹ��ʲ�������
	private  int cur_rate_stone_num;//ӵ�еĳɹ��ʲ�������
	private int stone_rate=0;//��ǰ��ʯ�ɹ���
	
	//����ʯ�����ã��ӹ�ʧ�ܲ�������
	public static int protect_stone_id = 4;//������ʯid
	private boolean isCanUseProtectedStone = false;//�Ƿ����ʹ�ñ���ʯ
	protected int cur_protect_stone_num = 0;//��ǰӵ�б���ʯ������
	
	abstract public String selectEquip(PlayerEquipVO select_equip);//ѡ��Ҫ�ӹ���װ��
	abstract public int getSuccessRate();//�õ���ǰ�ɹ���
	abstract public int getNeedMoney();//�õ���Ҫ��Ǯ
	abstract public String isProcessed();//�Ƿ���Լӹ�����Ļ�������
	abstract protected String processSuccess();//����ɹ�
	protected abstract String processFail();//����ʧ��
	protected abstract void cleare();//������
	
	public EquipProduct(int pPk,String actionName,boolean isNeedProtectedStone )
	{
		super(pPk);
		this.actionName = actionName;
		this.isCanUseProtectedStone = isNeedProtectedStone;
	}
	
	/**
	 * �ɷ�ʹ�ñ��ױ�ʯ
	 */
	public boolean isCanUseProtectedStone()
	{
		return isCanUseProtectedStone;
	}
	
	/**
	 * �ӹ�����
	 */
	public String process()
	{
		String hint = null;
		//�ж��Ƿ�������
		if( this.equip==null )
		{
			return "��ѡ��Ҫ"+this.actionName+"װ��";
		}
		hint =  equip.isOwnByPPk(this.p_pk);
		if( hint!=null )
		{
			return hint;
		}
		if( equip.isEffected()==false )
		{
			return "��װ������";
		}
		if(equip.getWType()!=Equip.ON_WRAP)
		{
			return "��װ�����ڰ�����";
		}
		
		//�Ƿ���Լӹ�����Ļ�������
		hint = this.isProcessed();
		if( hint!=null )
		{
			return hint;
		}
		
		//�ж����������Ƿ�
		hint = isEnoughMaterials();
		if( hint!=null )
		{
			return hint;
		}
		
		//������Ʒ
		consumeMaterials();
		
		//װ������
		if( this.isSucessed() )//�����ɹ�
		{
			hint = processSuccess();
		}
		else//����ʧ��
		{
			hint = processFail();
		}
		
		this.cleare();
		return hint;
	}
	
	
	/**
	 * �����������
	 * ����1��[0/0]
	 */
	public String getNeedMaterialsDes()
	{
		if( material_id_list==null )
		{
			return "";
		}
		StringBuffer des = new StringBuffer();
		
		for( int i=0;i< material_id_list.length;i++)
		{
			PropVO prop = PropCache.getPropById(material_id_list[i]);
			if( prop!=null )
			{
				des.append(prop.getPropName()).append("��[").append(cur_materials_num[i]);
				des.append("/").append(this.need_materials_num[i]).append("]").append("<br/>");
			}
		}
		
		return des.toString();
	}
	
	/**
	 * ������Ҫ�Ĳ���
	 */
	protected void setNeedMaterials(int[] need_material_id_list,int[] cur_need_materials_num)
	{
		if( need_material_id_list==null || need_material_id_list.length==0 || cur_need_materials_num==null || cur_need_materials_num.length==0 || need_material_id_list.length!=cur_need_materials_num.length )
		{
			return ;
		}
		
		this.material_id_list = need_material_id_list;
		this.need_materials_num = cur_need_materials_num;
		cur_materials_num = new int[need_material_id_list.length];
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		for( int i=0;i< material_id_list.length;i++)
		{
			int cur_material_num = playerPropGroupDao.getPropNumByByPropID(p_pk,material_id_list[i]);//���в��ϵ�����
			if( cur_material_num>need_materials_num[i])
			{
				cur_material_num = need_materials_num[i];
			}
			cur_materials_num[i]=cur_material_num;
		}
	}
	
	/**
	 * �жϲ����Ƿ��㹻
	 */
	protected String isEnoughMaterials()
	{
		//�ж��Ƿ����㹻��Ǯ
		if( this.getRoleEntity().getBasicInfo().getCopper()<this.getNeedMoney() )
		{
			return "�����������";
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		if( material_id_list!=null && material_id_list.length>0 )
		{
			for( int i=0;i< material_id_list.length;i++)
			{
				int cur_material_num = playerPropGroupDao.getPropNumByByPropID(p_pk,material_id_list[i]);//���в��ϵ�����
				if( cur_material_num<need_materials_num[i])
				{
					return "���ϲ���";
				}
			}
		}
		
		if( isUseProtectedStone() )//ʹ�ñ���ʯ
		{
			int cur_protected_stone_num = playerPropGroupDao.getPropNumByByPropID(p_pk,this.protect_stone_id);//���б���ʯ������
			if( cur_protected_stone_num<1 )
			{
				return "���ϲ���";
			}
		}
		
		return null;
	}
	
	
	/**
	 * �Ƿ�ʹ�ñ���ʯ
	 * @return
	 */
	public boolean isUseProtectedStone()
	{
		if( isCanUseProtectedStone )//����ʹ�ñ���ʯ
		{
			if( cur_protect_stone_num>0 )//ʹ�ñ���ʯ
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ���Ĳ��ϣ�������ϣ��������ʱ�ʯ����Ǯ��
	 */
	protected void consumeMaterials()
	{
		GoodsService goodsService = new GoodsService();
		//���ı������
		if( material_id_list!=null && material_id_list.length>0 )
		{
			for( int i=0;i< material_id_list.length;i++)
			{
				goodsService.removeProps(p_pk,material_id_list[i], need_materials_num[i],GameLogManager.R_MATERIAL_CONSUME);
			}
		}
		//�����������ʱ�ʯ
		if( this.rate_stone_num>0)
		{
			goodsService.removeProps(p_pk, this.rate_stone_id, this.rate_stone_num,GameLogManager.R_MATERIAL_CONSUME);
		}
		
		if( isUseProtectedStone() )//ʹ�ñ���ʯ
		{
			//���ı���ʯ
			goodsService.removeProps(p_pk, this.protect_stone_id, 1,GameLogManager.R_MATERIAL_CONSUME);
		}
		//���Ľ�Ǯ
		this.getRoleEntity().getBasicInfo().addCopper(-this.getNeedMoney());
	}
	
	/**
	 * ���ݸ����ж��Ƿ�ɹ�
	 */
	protected boolean isSucessed()
	{
		return MathUtil.isAppearByPercentage(getSuccessRate());
	}
	
	/**
	 * �õ���Ҫ��Ǯ����
	 * @return
	 */
	public String getNeedMoneyDes()
	{
		return ExchangeUtil.getMoneyDes(this.getNeedMoney());
	}
	
	/**
	 * ѡ�񱣵ױ�ʯ
	 */
	public String selectProtectStone( int protect_stone_id )
	{
		String hint = null;
		
		if( this.protect_stone_id!=protect_stone_id)
		{
			return "��ʹ�ñ��ױ�ʯ";
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int have_num = playerPropGroupDao.getPropNumByByPropID(this.p_pk, this.protect_stone_id);
		
		if( have_num<1 )
		{
			return "��û���㹻�ı�ʯ";
		}
		
		this.cur_protect_stone_num = have_num;
		
		return hint;
	}
	
	/**
	 * ѡ��ɹ��ʱ�ʯ
	 */
	public String selectRateStone( int rate_stone_id,int rate_stone_num )
	{
		String hint = null;
		
		PropVO rate_stone = PropCache.getPropById(rate_stone_id);
		if( rate_stone.getPropClass()!=PropType.UPGRADEHELPPROP)
		{
			return "��ʹ����ȷ�ı�ʯ";
		}
		
		if( rate_stone_num>5 )
		{
			return "���ֻ����ʹ��5����ʯ";
		}
		
		PlayerPropGroupDao playerPropGroupDao = new PlayerPropGroupDao();
		int have_num = playerPropGroupDao.getPropNumByByPropID(this.p_pk, rate_stone_id);
		
		if( have_num<rate_stone_num )
		{
			return "��û���㹻�ı�ʯ";
		}
		
		this.rate_stone_id = rate_stone_id;
		this.rate_stone_num = rate_stone_num;
		this.cur_rate_stone_num = have_num;
		
		this.stone_rate = Integer.parseInt(rate_stone.getPropOperate1())*rate_stone_num;
		
		return hint;
	}
	
	/**
	 * �õ��ɹ��ʵ�������
	 * @return
	 */
	public String getRateStoneName()
	{
		if( rate_stone_id==0 )
		{
			return "���ӳɹ��ʵĲ���";
		}
		PropVO rate_stone = PropCache.getPropById(rate_stone_id);
		return rate_stone.getPropName();
	}
	/**
	 * �õ�ӵ�гɹ��ʵ��ߵ�����
	 * @return
	 */
	public int getRateStoneNum()
	{
		return this.rate_stone_num;
	}
	
	/**
	 * �õ���ʯ�ɹ���
	 * @return
	 */
	protected int getStoneRate()
	{
		return stone_rate;
	}
	
	/**
	 * �õ�װ������
	 * @return
	 */
	public String getEquipName()
	{
		if( this.equip==null )
		{
			return "װ��";
		}
		return this.equip.getFullName();
	}
	
	/**
	 * �õ����׵�������
	 * @return
	 */
	public String getProtectStoneName()
	{
		if( this.cur_protect_stone_num==0 )
		{
			return "���ײ���";
		}
		PropVO protect_stone = PropCache.getPropById(protect_stone_id);
		return protect_stone.getPropName();
	}
	
	/**
	 * �õ����׵�������
	 * @return
	 */
	public int getProtectStoneNum()
	{
		return cur_protect_stone_num;
	}
	
	/**
	 * ��ʼ��
	 */
	protected void init()
	{
		this.equip = null;
		this.stone_rate = 0;
		
		this.rate_stone_id = 0;
		this.rate_stone_num = 0;
		
		material_id_list = null;
		need_materials_num = null;
		cur_materials_num = null;
		
		this.cur_protect_stone_num = 0;
		cur_rate_stone_num = 0;
	}
	public String getActionName()
	{
		return actionName;
	}
	public int getCurRateStoneNum()
	{
		return cur_rate_stone_num;
	}
	public PlayerEquipVO getEquip()
	{
		return this.equip;
	}
}
