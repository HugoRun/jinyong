package com.ls.web.service.menu.buff;

import java.util.List;

import org.apache.log4j.Logger;

import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.dao.info.buff.BuffDao;
import com.ls.ben.dao.info.buff.BuffEffectDao;
import com.ls.ben.vo.info.buff.BuffEffectVO;
import com.ls.ben.vo.info.buff.BuffVO;
import com.ls.pub.constant.buff.BuffSystem;
import com.ls.pub.constant.buff.BuffType;
import com.ls.web.service.buff.BuffEffectService;
import com.pm.dao.systemInfo.SysInfoDao;

public class BuffMenuService
{

	Logger logger =  Logger.getLogger("log.service");
	/**
	 * 给玩家加上buff效果
	 * @param pk
	 * @param menuOperate1 就是buff_id
	 */
	public void setBuffStatus(int pPk, int buff_id)
	{
		//BuffEffectService bfeService = new BuffEffectService();
		//bfeService.createBuffEffect(pPk, BuffSystem.PLAYER, buff_id);
		BuffDao buffDao = new BuffDao();
		BuffEffectService bfeService = new BuffEffectService();
		
		int buffType = buffDao.getBuffType(buff_id+"");
		
		List<BuffEffectVO> buffEffectslist = bfeService.getBuffList(pPk);
		bfeService.listenBuffEffectStat(buffEffectslist);
		
		BuffEffectDao buffEffectDao = new BuffEffectDao();
		BuffEffectVO buffEffect = buffEffectDao.hasAlreadyBuff(pPk,BuffSystem.PLAYER, buffType+"");
		logger.info("hasSameBuff="+buffEffect == null);
		//如果有一个同类型的buff在,就要查看其是否可以被累加, 如果可以就累加,如果不行就删除前面的buff，再添加这个buff
		if( buffEffect != null  ) {
			logger.info("hasSameBuff="+buffEffect.getBfPk());
			if(buffEffect.getBuffId() == buff_id) { 
				BuffVO buffvo = buffDao.getBuff(buff_id);
				if(buffEffect.getBuffTimeOverlap() == 1) {
					dealWithUpdateBuffEffect(buffEffect);
					buffEffectDao.updateBuffEffect(pPk, buff_id,buffvo.getBuffTime());
					bfeService.dealWithHPBuff(pPk, BuffSystem.PLAYER, buff_id);
					
				} else if(buffEffect.getBuffBoutOverlap() == 1) {
					buffEffectDao.updateBuffBoutEffect(pPk, buff_id,buffvo.getBuffTime());
					bfeService.dealWithHPBuff(pPk, BuffSystem.PLAYER, buff_id);
					
				} else {
					buffEffectDao.deleteAlreadyBuff(Integer.valueOf(pPk),BuffSystem.PLAYER, buffType);
					bfeService.createBuffEffect(pPk, BuffSystem.PLAYER, buff_id);
					
				}
			} else {
				buffEffectDao.deleteAlreadyBuff(pPk,BuffSystem.PLAYER, buffType);
				bfeService.createBuffEffect(pPk, BuffSystem.PLAYER, buff_id);				
			}
		//如果没有类似的buff,就给其一个加一个状态buff
		} else {
			bfeService.createBuffEffect(pPk, BuffSystem.PLAYER, buff_id);
			
		}		
	}
	
	
	/**
	 * 给玩家加上buff效果,vbuff有多个的情况
	 * @param pk
	 * @param menuOperate1 就是buff_ids
	 */
	public String setBuffStatus(int pPk, String buff_ids)
	{
		if ( buff_ids == null || buff_ids.equals("")) {
			logger.error("buffMenu发buff时buffId为空");
			return null;
		}
		
		StringBuffer sBuffer = new StringBuffer();
		BuffDao buffDao = new BuffDao();
		sBuffer.append("您获得了:");
		String[] buffidStrings = buff_ids.split(",");
		for ( int i=buffidStrings.length-1;i>=0;i--) {
			setBuffStatus(pPk,Integer.parseInt(buffidStrings[i]));		
			BuffVO buffVO = buffDao.getBuff(Integer.parseInt(buffidStrings[i]));
			sBuffer.append(buffVO.getBuffDisplay());			
		}		
		return sBuffer.toString();
	}
	
	/**
	 * 处理在 时间可有效叠加时 如果叠加使用buff时可能要处理的
	 * @param buffEffect
	 */
	private void dealWithUpdateBuffEffect(BuffEffectVO buffEffect)
	{
		switch (buffEffect.getBuffId()){
			
			case BuffType.REDUCEPKVALUE :
			{
				PropDao propdao = new PropDao();
				String propName = propdao.getPropNameByType(buffEffect.getBuffId()+"");
				
				SysInfoDao sysInfoDao = new SysInfoDao();
				sysInfoDao.updateTimeReducePkValue(buffEffect.getEffectObject(),buffEffect.getBuffEffectValue(),propName);
				
				break;
			}
		}	
	}

}
