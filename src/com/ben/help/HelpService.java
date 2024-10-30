package com.ben.help;

import java.util.List;

public class HelpService
{
	private HelpDao helpDao = new HelpDao();

	public List<Help> findBySuperId(Object super_id,int begin,int limit)
	{
		return helpDao.findBySuperId(super_id,begin,limit);
	}
	
	public int findBySuperId(Object super_id){
		return helpDao.findBySuperId(super_id);
	}
	
	public Help findById(Object id){
		return helpDao.findById(id);
	}
	
	public List<Guai> findByDiaoluo(Object goods_id,Object type){
		List<Guai> list = helpDao.findByDiaoluo(goods_id, type);
		if(list!=null){
			for(Guai guai : list){
				String scene_name = helpDao.findScene(guai.getGuai_id());
				guai.setScene_name(scene_name==null?"":scene_name.indexOf("(")==-1?scene_name:scene_name.substring(0,scene_name.indexOf("(")));
			}
		}
		return list;
	}
	
	public List<Guai> findByDiaoluo1(Object goods_id,Object type)
	{
		return helpDao.findByDiaoluo1(goods_id, type);
		
	}
}
