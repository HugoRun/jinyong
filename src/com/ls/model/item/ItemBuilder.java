package com.ls.model.item;

import java.util.HashMap;
import java.util.Map;

import com.ls.web.service.log.DataErrorLog;

/**
 * @author ls
 * ��Ϸ��Ʒ������
 */
public class ItemBuilder
{
	private static Map<String,String> mapping = new HashMap<String,String>(10);
	
	static 
	{
		mapping.put(Item.PROP_PREFIX_STR, "com.ls.model.item.impl.Prop");
		mapping.put(Item.UEQUP_PREFIX_STR, "com.ls.model.item.impl.UEquip");
	}
	/**
	 * �����ַ�������һ����Ʒ
	 * @param itemStr
	 * @return
	 */
	public static Item build(String itemStr)
	{
		Item item = null;
		try
		{
			String[] temp = itemStr.split("-",2);
			String class_name = mapping.get(temp[0]);
			item = (Item)Class.forName(class_name).newInstance();
			item.init(temp[1]);
		}
		catch(Exception e)
		{
			DataErrorLog.debugData("ItemBuilder.build.��������:itemStr="+itemStr);
		}
		return item;
	}
}
