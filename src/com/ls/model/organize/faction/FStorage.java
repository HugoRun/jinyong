package com.ls.model.organize.faction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ls.ben.dao.faction.FactionDao;
import com.ls.ben.vo.info.partinfo.PlayerPropGroupVO;
import com.ls.model.item.Item;
import com.ls.model.item.ItemBuilder;
import com.ls.model.item.impl.Prop;
import com.ls.model.user.RoleEntity;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * ���ɲֿ�
 */
public class FStorage
{
	private int fId;
	private Map<Integer,Item> material_list = new HashMap<Integer,Item>(10);

	/**
	 * ��ʼ��
	 * @param fId
	 * @param storageStr
	 */
	public FStorage(int fId,String storageStr)
	{
		this.fId = fId;
		if( storageStr!=null )
		{
			String[] material_str_list = storageStr.split(",");
			if( material_str_list!=null && material_str_list.length>0 )
			{
				for(String material_str:material_str_list )
				{
					try
					{
						Prop prop = (Prop) ItemBuilder.build(material_str);
						material_list.put(prop.getId(),prop);
					}
					catch (Exception e)
					{
						DataErrorLog.debugData("FStorage��ʼ�����ɲֿ����storageStr="+storageStr);
					}
				}
			}
		}
	}
	
	/**
	 * ���ɲֿ���Ʒ�б�
	 */
	public List<Item> getList()
	{
		return new ArrayList<Item>(material_list.values());
	}
	
	/**
	 * �ѵ�����ȫ�����ף������ɲֿ������ϣ�
	 * @param prop
	 * @param num
	 * @return
	 */
	public String contributeAll(RoleEntity roleEntity,PlayerPropGroupVO material)
	{
		if( material==null )
		{
			return "��������";
		}
		return this.contribute(roleEntity, material, material.getPropNum());
	}
	
	/**
	 * ���ף������ɲֿ������ϣ�
	 * @param prop
	 * @param num
	 * @return
	 */
	public String contribute(RoleEntity roleEntity,PlayerPropGroupVO material,int num)
	{
		if( roleEntity==null || material==null )
		{
			return "��������";
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return "�����ѽ�ɢ";
		}
		
		if( material.getPropNum()<num )
		{
			return material.getPropName()+"��������";
		}
		String contribute_point = material.getPropInfo().getPropOperate1();
		
		//���ӹ��׶�
		roleEntity.getBasicInfo().addFContribute(Integer.parseInt(contribute_point)*num);
		//���Ӱ�������
		faction.updatePrestige(num);
		//�۳�����
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(material, num);
		//������ϲֿ�
		this.put(material.getPropId(), num);
		return null;
	}
	
	private void put(int propId,int num)
	{
		Item old_item = this.isHave(propId);
		if( old_item!=null )
		{
			old_item.update(num);
		}
		else
		{
			material_list.put(propId,new Prop(propId,num));
		}
		this.save();
	}
	
	/**
	 * �ж��Ƿ����㹻�İ��ɲ���
	 */
	public String isEnoughMaterial( String needMaterialsStr)
	{
		if( needMaterialsStr==null )
		{
			return null;
		}
		String[] need_material_str_list = needMaterialsStr.split(",");
		for( String need_material_str:need_material_str_list )
		{
			String[] temp = need_material_str.split("-");
			Item item = this.isHave(Integer.parseInt(temp[0]));
			if( item==null || item.isEnough(Integer.parseInt(temp[1]))==false )
			{
				return "���ϲ���";
			}
		}
		return null;
	}
	/**
	 * ���İ��ɲ���
	 */
	public String consumeMaterial( String needMaterialsStr)
	{
		if( needMaterialsStr==null )
		{
			return null;
		}
		String[] need_material_str_list = needMaterialsStr.split(",");
		for( String need_material_str:need_material_str_list )
		{
			String[] temp = need_material_str.split("-");
			Item item = this.isHave(Integer.parseInt(temp[0]));
			int need_num = Integer.parseInt(temp[1]);
			if( item!=null && item.isEnough(need_num)==true )
			{
				item.update(-need_num);
			}
		}
		this.save();
		return null;
	}
	
	/**
	 * �ж��Ƿ��и���Ʒ
	 * @param new_item   ������򷵻ص�ǰ����Ʒ�����û�з���null
	 * @return
	 */
	private Item isHave(int propId)
	{
		return material_list.get(propId);
	}
	
	/**
	 * ��������
	 * @return
	 */
	private void save()
	{
		StringBuffer sb = new StringBuffer();
		Set<Integer> key_list = material_list.keySet();
		int material_total = 0 ;//��������
		for(Integer key:key_list)
		{
			Item material = material_list.get(key);
			material_total+=material.getNum();
			sb.append(material.toString()).append(",");
		}
		FactionDao factionDao = new FactionDao();
		factionDao.saveStorageStr(fId, sb.toString().substring(0,sb.length()-1),material_total);
	}
}
