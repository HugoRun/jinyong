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
 * 帮派仓库
 */
public class FStorage
{
	private int fId;
	private Map<Integer,Item> material_list = new HashMap<Integer,Item>(10);

	/**
	 * 初始化
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
						DataErrorLog.debugData("FStorage初始化帮派仓库错误，storageStr="+storageStr);
					}
				}
			}
		}
	}
	
	/**
	 * 帮派仓库物品列表
	 */
	public List<Item> getList()
	{
		return new ArrayList<Item>(material_list.values());
	}
	
	/**
	 * 把道具组全部贡献（往帮派仓库放入材料）
	 * @param prop
	 * @param num
	 * @return
	 */
	public String contributeAll(RoleEntity roleEntity,PlayerPropGroupVO material)
	{
		if( material==null )
		{
			return "参数错误";
		}
		return this.contribute(roleEntity, material, material.getPropNum());
	}
	
	/**
	 * 贡献（往帮派仓库放入材料）
	 * @param prop
	 * @param num
	 * @return
	 */
	public String contribute(RoleEntity roleEntity,PlayerPropGroupVO material,int num)
	{
		if( roleEntity==null || material==null )
		{
			return "参数错误";
		}
		
		Faction faction = roleEntity.getBasicInfo().getFaction();
		if( faction==null )
		{
			return "氏族已解散";
		}
		
		if( material.getPropNum()<num )
		{
			return material.getPropName()+"数量不足";
		}
		String contribute_point = material.getPropInfo().getPropOperate1();
		
		//增加贡献度
		roleEntity.getBasicInfo().addFContribute(Integer.parseInt(contribute_point)*num);
		//增加帮派声望
		faction.updatePrestige(num);
		//扣除道具
		GoodsService goodsService = new GoodsService();
		goodsService.removeProps(material, num);
		//放入材料仓库
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
	 * 判断是否有足够的帮派材料
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
				return "材料不足";
			}
		}
		return null;
	}
	/**
	 * 消耗帮派材料
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
	 * 判断是否有该物品
	 * @param new_item   如果有则返回当前的物品，如果没有返回null
	 * @return
	 */
	private Item isHave(int propId)
	{
		return material_list.get(propId);
	}
	
	/**
	 * 保存数据
	 * @return
	 */
	private void save()
	{
		StringBuffer sb = new StringBuffer();
		Set<Integer> key_list = material_list.keySet();
		int material_total = 0 ;//材料总数
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
