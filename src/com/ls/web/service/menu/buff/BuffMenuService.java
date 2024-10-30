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
	 * ����Ҽ���buffЧ��
	 * @param pk
	 * @param menuOperate1 ����buff_id
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
		//�����һ��ͬ���͵�buff��,��Ҫ�鿴���Ƿ���Ա��ۼ�, ������Ծ��ۼ�,������о�ɾ��ǰ���buff����������buff
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
		//���û�����Ƶ�buff,�͸���һ����һ��״̬buff
		} else {
			bfeService.createBuffEffect(pPk, BuffSystem.PLAYER, buff_id);
			
		}		
	}
	
	
	/**
	 * ����Ҽ���buffЧ��,vbuff�ж�������
	 * @param pk
	 * @param menuOperate1 ����buff_ids
	 */
	public String setBuffStatus(int pPk, String buff_ids)
	{
		if ( buff_ids == null || buff_ids.equals("")) {
			logger.error("buffMenu��buffʱbuffIdΪ��");
			return null;
		}
		
		StringBuffer sBuffer = new StringBuffer();
		BuffDao buffDao = new BuffDao();
		sBuffer.append("�������:");
		String[] buffidStrings = buff_ids.split(",");
		for ( int i=buffidStrings.length-1;i>=0;i--) {
			setBuffStatus(pPk,Integer.parseInt(buffidStrings[i]));		
			BuffVO buffVO = buffDao.getBuff(Integer.parseInt(buffidStrings[i]));
			sBuffer.append(buffVO.getBuffDisplay());			
		}		
		return sBuffer.toString();
	}
	
	/**
	 * ������ ʱ�����Ч����ʱ �������ʹ��buffʱ����Ҫ�����
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
