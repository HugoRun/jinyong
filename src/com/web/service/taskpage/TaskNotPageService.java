package com.web.service.taskpage;

import org.apache.log4j.Logger;

import com.ben.vo.task.TaskVO;
import com.ls.ben.cache.staticcache.equip.EquipCache;
import com.ls.ben.cache.staticcache.task.TaskCache;
import com.ls.model.equip.GameEquip;
import com.ls.model.property.task.CurTaskInfo;
import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.pub.util.ExchangeUtil;
import com.ls.pub.util.StringUtil;
import com.ls.web.service.goods.GoodsService;
import com.ls.web.service.log.DataErrorLog;

/**
 * ����:�˵� 
 * 
 * @author ��ƾ� 11:13:44 AM
 */
public class TaskNotPageService
{
	Logger logger = Logger.getLogger("log.service");

	public String taskNotPageService(String taskId, RoleEntity roleInfo)
	{
		StringBuffer hint = new StringBuffer();
		GoodsService goodsService = new GoodsService();
		//ͨ������ID��cacheȡ����������
		TaskVO vo = TaskCache.getById(taskId);
		CurTaskInfo UTaskVO = (CurTaskInfo)roleInfo.getTaskInfo().getCurTaskList().getByZu(vo.getTZu()); 
		    hint.append(StringUtil.isoToGBK(vo.getTName()) + "<br/> ");  
			String tishi = StringUtil.isoToGBK(vo.getTTishi()); 
			hint.append(tishi + "<br/> "); 
			String ss = StringUtil.isoToGBK(vo.getTDisplay());
			String rr = ss.replaceAll("\\(OWN\\)", "" + roleInfo.getBasicInfo().getName() + "");
			//�滻����˵��
			String task_content = ExchangeUtil.getTitleBySex(rr, roleInfo.getBasicInfo().getSex());
			hint.append(task_content + "<br/> ");
			if (vo.getTMoney() != null && !vo.getTMoney().equals("")
					&& !vo.getTMoney().equals("0"))
			{
				hint.append("��Ǯ:" + vo.getTMoney() +GameConfig.getMoneyUnitName()+ "<br/> ");
			}
			if (vo.getTExp() != null && !vo.getTExp().equals("")
					&& !vo.getTExp().equals("0"))
			{
				hint.append("����:" + vo.getTExp() + "<br/> ");
			}
			if (vo.getTEncouragement() != null && !vo.getTEncouragement().equals("") && !vo.getTEncouragement().equals("0"))
			{
				String[] tGeidjs = vo.getTEncouragement().split(",");
				String[] tGeidjNumbers = vo.getTWncouragementNo().split(",");
				for(int i = 0 ; i < tGeidjs.length ;i++){
					hint.append("��Ʒ:"+goodsService.getGoodsName(Integer.parseInt(tGeidjs[i]),4)+" x"+tGeidjNumbers[i]+"<br/>"); 
				}
				
				//hint.append("��Ʒ:" + goodsService.getGoodsName(Integer.parseInt(vo.getTEncouragement()), 4) + "x" + vo.getTWncouragementNo() + "<br/> ");

			}
			if (vo.getTEncouragementZb() != null && !vo.getTEncouragementZb().equals("") && !vo.getTEncouragementZb().equals("0"))
			{
				String equip_list[] = vo.getTEncouragementZb().split(",");
				for (int i = 0; i < equip_list.length; i++)
				{
					try{
						String equip_id_str = equip_list[i];
						GameEquip equip = EquipCache.getById(Integer.parseInt(equip_id_str.trim()));
						if( equip!=null )
						{
							hint.append("װ��:" + equip.getName() + "x" + vo.getTEncouragementNoZb() + "<br/>");
						}
					}catch(Exception e)
					{
						DataErrorLog.task(vo.getTId(), "������װ�����ݴ���"+vo.getTEncouragementZb() );
					}
				}
			}
			if(UTaskVO != null){
			if (UTaskVO.getTKillingNo() != 0)
			{
				hint.append("<br/> ");
				hint.append("�������:" + UTaskVO.getTKillingOk() + "/" + UTaskVO.getTKillingNo() + "<br/>");
			}
			if (UTaskVO.getTKillingNo() != 0 && UTaskVO.getTKillingOk() != 0 && UTaskVO.getTKillingNo() == UTaskVO.getTKillingOk())
			{
				hint.append("�������<br/>");
			}
			if (UTaskVO.getTGoods() != null && !UTaskVO.getTGoods().equals("") && !UTaskVO.getTGoods().equals("0"))
			{// ȡ�������е���Ʒ
				String[] goods = UTaskVO.getTGoods().split(",");
				String[] goodsnumber = UTaskVO.getTGoodsNo().split(",");
				for (int gd = 0; gd < goods.length; gd++)
				{
					int dd = goodsService.getPropNum(roleInfo.getBasicInfo().getPPk(), Integer.parseInt(goods[gd]));// ȡ�����������Ʒ����
					if (dd <= Integer.parseInt(goodsnumber[gd]))
					{
						hint.append("�������:"+ StringUtil.isoToGBK(vo.getTName())+ "("+ goodsService.getGoodsName(Integer.parseInt(goods[gd]), 4) + ") " + dd + "/" + goodsnumber[gd]+ "<br/>");

					}
					else
						if (dd >= Integer.parseInt(goodsnumber[gd]))
						{
							hint.append("�������");
						}
				}
			}
			}
		return hint.toString();
	}
}
