package com.web.service.popupmsg;

import java.text.MessageFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.cache.staticcache.prop.PropCache;
import com.ls.ben.dao.goods.prop.PropDao;
import com.ls.ben.vo.goods.prop.PropVO;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.ben.vo.system.UMessageInfoVO;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.system.PopUpMsgType;
import com.ls.web.service.mall.MallService;
import com.ls.web.service.system.UMsgService;

/**
 * @author HHJ ϵͳ�������͵ĵ���ʽ��Ϣ
 */
public class PopUpMsgService
{
	/**
	 * ���ӵ���ʽ��Ϣ
	 * @param p_pk
	 * @param gread ��ҵȼ�
	 * @param digit ��������λ��
	 * @param msgtype ��Ϣ����
	 */
	public void addSysSpecialMsg(int p_pk,int gread,int digit, int msgtype)
	{
		UMsgService uMsgService = new UMsgService();
		UMessageInfoVO msgInfo = new UMessageInfoVO();
		msgInfo.setPPk(p_pk);
		msgInfo.setMsgType(PopUpMsgType.SYS_TESHU_MSG);
		msgInfo.setMsgPriority(PopUpMsgType.SYS_TESHU_MSG_FIRST);
		switch (msgtype)
		{
			case PopUpMsgType.GO_UP_GRADE:// ##�ȼ����
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.GO_UP_GRADE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.WRAP_LOWER_LIMIT:// ##�������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.WRAP_LOWER_LIMIT + "");
				break;
			case PopUpMsgType.PET_FATIGUE:// ##�������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.PET_FATIGUE + "");
				break;
			case PopUpMsgType.ATTAIN_PROP_TYPE:// ##�������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.ATTAIN_PROP_TYPE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.TASK_INSTANCE://##�����������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.TASK_INSTANCE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.TASK_30TONG://30�����ɽ����������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.TASK_30TONG + "");
				break;
			case PopUpMsgType.TASK_30PK://30��PK�����������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.TASK_30PK + "");
				break;
			case PopUpMsgType.MENU_INSTANCE://�����˵�
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.MENU_INSTANCE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.MENU_SIEGE://���ǲ˵����
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.MENU_SIEGE + "");
				break;
			case PopUpMsgType.PROP_GRADE://�������������ȼ������
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.PROP_GRADE + "");
				break;
			case PopUpMsgType.VIP_ENDTIME://VIP��Ա�����Ժ�
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.VIP_ENDTIME + "");
				break;
			case PopUpMsgType.PK_SWITCH://PK���ش�
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.PK_SWITCH + "");
				break;
			case PopUpMsgType.NEW_ROLE://��ע���˺�
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.NEW_ROLE + "");
				break;
			/*case PopUpMsgType.CNN_TODAY://�̳ǽ��տ�Ѷ
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.CNN_TODAY + "");
				break;*/
			case PopUpMsgType.USE_PROP://ʹ�õ���
				// ��������뵯��ʽ��Ϣ����
				msgInfo.setMsgOperate1(PopUpMsgType.USE_PROP + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
		}
		
		uMsgService.sendPopUpMsg(msgInfo);
	}

	/**
	 * �ȼ�����
	 * @return
	 */
	public String SysSpecialGrade(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//ȡ�õȼ��ĵȼ����ڵ�λ��
		String[] go_up_mallprop = GameConfig.getPropertiesObjectArray("go_up_mallprop");//ȡ��Ҫ��ĵ���ID
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(go_up_mallprop[Integer.parseInt(msgOperate2[1])]);
		if(commodityVO != null){
			String go_up_hint = GameConfig.getPropertiesObject("go_up_hint");
			go_up_hint = MessageFormat.format(go_up_hint, msgOperate2[0],commodityVO.getSellNum());
			hint.append(go_up_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("go_up_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("�ȼ����ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	/**
	 * ��������
	 * @return
	 */
	public String SysSpecialWrapLowerLimit(HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String wrap_hint = GameConfig.getPropertiesObject("wrap_hint");
		hint.append(wrap_hint+"<br/><anchor> "); 
		hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n4")+"\"></go>");
		hint.append("ȷ�Ϲ���");
		hint.append("</anchor>");
		hint.append("<br/><anchor> "); 
		hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
		hint.append("�´ΰ�");
		hint.append("</anchor>");
		return hint.toString();
	}
	/**
	 * �������
	 * @param resources
	 * @param response
	 * @return
	 */
	public String SysSpecialPetFatigue(HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String pet_mallprop = GameConfig.getPropertiesObject("pet_mallprop");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(pet_mallprop);
		if(commodityVO != null){
			String pet_hint = GameConfig.getPropertiesObject("pet_hint");
			pet_hint = MessageFormat.format(pet_hint, commodityVO.getSellNum());
			hint.append(pet_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("pet_fatigue_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("�������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	/**
	 * ��õ������ 
	 * @param response
	 * @return
	 */
	public String SysSpecialAttainProp(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//ȡ�õȼ��ĵȼ����ڵ�λ��
		PropVO prop = PropCache.getPropById(Integer.parseInt(msgOperate2[0]));
		String[] attainprop_mallprop = GameConfig.getPropertiesObjectArray("attainprop_mallprop");//ȡ��Ҫ��ĵ���ID
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(attainprop_mallprop[Integer.parseInt(msgOperate2[1])]);
		if(commodityVO != null){
			String attainprop_hint = GameConfig.getPropertiesObject("attainprop_hint");
			attainprop_hint = MessageFormat.format(attainprop_hint, prop.getPropName(),commodityVO.getPropName(),commodityVO.getPropName(),commodityVO.getSellNum());
			hint.append(attainprop_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("attainprop_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("�������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * �����������
	 * @return
	 */
	public String SysSpecialTaskInstance(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//ȡ�õȼ��ĵȼ����ڵ�λ��
		String[] task_instance_mallprop = GameConfig.getPropertiesObjectArray("task_instance_mallprop");//ȡ��Ҫ��ĵ���ID
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(task_instance_mallprop[Integer.parseInt(msgOperate2[1])]);
		if(commodityVO != null){
			String task_instance_hint = GameConfig.getPropertiesObject("task_instance_hint");
			task_instance_hint = MessageFormat.format(task_instance_hint, msgOperate2[0],msgOperate2[0],commodityVO.getSellNum());
			hint.append(task_instance_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("task_instance_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("�������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * //30�����ɽ����������
	 * @return
	 */
	public String SysSpecialTask30Tong(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String task_30tong_mallprop = GameConfig.getPropertiesObject("task_30tong_mallprop");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(task_30tong_mallprop);
		if(commodityVO != null){
			String task_30tong_hint = GameConfig.getPropertiesObject("task_30tong_hint");
			task_30tong_hint = MessageFormat.format(task_30tong_hint, commodityVO.getSellNum());
			hint.append(task_30tong_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("task_30tong_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("�������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	/**
	 * //30��PK�����������
	 * @return
	 */
	public String SysSpecialTask30Pk(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String task_30pk_mallprop = GameConfig.getPropertiesObject("task_30pk_mallprop");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(task_30pk_mallprop);
		if(commodityVO != null){
			String task_30pk_hint = GameConfig.getPropertiesObject("task_30pk_hint");
			task_30pk_hint = MessageFormat.format(task_30pk_hint, commodityVO.getSellNum());
			hint.append(task_30pk_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("task_30pk_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("pk���ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * �˵����븱�����
	 * @return
	 */
	public String SysSpecialMenuInstance(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//ȡ�õȼ��ĵȼ����ڵ�λ��
		int digit  = GameConfig.getMenuInstance(msgOperate2[0], "menu_instance_grade");
		if(digit != -1){
			String[] menu_instance_mallprop = GameConfig.getPropertiesObjectArray("menu_instance_mallprop");//ȡ��Ҫ��ĵ���ID
			//�õ��̳���Ʒ��Ϣ
			MallService mallService = new MallService();
			CommodityVO commodityVO = mallService.getPropCommodityInfo(menu_instance_mallprop[digit]);
			if(commodityVO != null){
				String menu_instance_hint = GameConfig.getPropertiesObject("menu_instance_hint");
				menu_instance_hint = MessageFormat.format(menu_instance_hint, msgOperate2[0],commodityVO.getSellNum());
				hint.append(menu_instance_hint+"<br/><anchor> "); 
				hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
				hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
				hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("menu_instance_discount") + "\" /> ");
				hint.append("</go>");
				hint.append("ȷ�Ϲ���");
				hint.append("</anchor>");
				hint.append("<br/><anchor> "); 
				hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
				hint.append("�´ΰ�");
				hint.append("</anchor>");
			}else{
				hint.append("�˵����ݴ���!����ϵ����Ա!");
			}
		}else{
			hint.append("�˵����ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * ���ǲ˵����
	 * @return
	 */
	public String SysSpecialMenuSiege(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String menu_siege_mallprop = GameConfig.getPropertiesObject("menu_siege_mallprop");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(menu_siege_mallprop);
		if(commodityVO != null){
			String menu_siege_hint = GameConfig.getPropertiesObject("menu_siege_hint");
			menu_siege_hint = MessageFormat.format(menu_siege_hint, commodityVO.getSellNum());
			hint.append(menu_siege_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("menu_siege_mallprop") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("�������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * �������������ȼ������
	 * @return
	 */
	public String SysSpecialPropGrade(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String prop_grade_mallprop = GameConfig.getPropertiesObject("prop_grade_mallprop");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(prop_grade_mallprop);
		if(commodityVO != null){
			String prop_grade_hint = GameConfig.getPropertiesObject("prop_grade_hint");
			prop_grade_hint = MessageFormat.format(prop_grade_hint, commodityVO.getPropName(),commodityVO.getSellNum());
			hint.append(prop_grade_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("prop_grade_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("����������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * VIP����
	 * @return
	 */
	public String SysSpecialVipEndtime(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String vip_endtime_proptype = GameConfig.getPropertiesObject("vip_endtime_proptype");
		//���ȵõ����л�Ա���͵ĵ���
		PropDao propDao = new PropDao();
		List list = propDao.getListByType(Integer.parseInt(vip_endtime_proptype));
	    if(list != null && list.size() > 0){
	    	String vip_endtime_hint = GameConfig.getPropertiesObject("vip_endtime_hint");
	    	int vipnumber = 6;
	    	vip_endtime_hint = MessageFormat.format(vip_endtime_hint, vipnumber);
	    	hint.append(vip_endtime_hint+"<br/>");
	    	for(int i = 0 ; i < list.size() ; i++){
	    		PropVO vo = (PropVO)list.get(i);
	    		hint.append(vo.getPropName()+" ");
	    		hint.append("<anchor>");
	    		hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n5")+"\">");
	    		hint.append("<postfield name=\"prop_id\" value=\""+vo.getPropID()+"\" />");
	    		hint.append("<postfield name=\"discount\" value=\""+GameConfig.getPropertiesObject("vip_endtime_discount")+"\" />");
	    		hint.append("</go>");
	    		hint.append("����");
	    		hint.append("</anchor><br/>");
	    	}
	    	hint.append("<anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
	    }else{
	    	hint.append("vip�������ݴ���!����ϵ����Ա!");
	    } 
		return hint.toString();
	}
	
	/**
	 * PK���ش򿪵�ʱ��
	 * @return
	 */
	public String SysSpecialPkSwitch(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String pk_switch_mallprop = GameConfig.getPropertiesObject("pk_switch_mallprop");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(pk_switch_mallprop);
		if(commodityVO != null){
			String pk_switch_hint = GameConfig.getPropertiesObject("pk_switch_hint");
			pk_switch_hint = MessageFormat.format(pk_switch_hint, commodityVO.getPropName(),commodityVO.getSellNum());
			hint.append(pk_switch_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("pk_switch_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("pk�������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	
	/**
	 * �½�ɫ������Ϸ��ʾ
	 * @return
	 */
	public String SysSpecialNewRole(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String new_role_prop_id = GameConfig.getPropertiesObject("new_role_prop_id");
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(new_role_prop_id);
		if(commodityVO != null){
			String new_role_hint = GameConfig.getPropertiesObject("new_role_hint");
			//new_role_hint = MessageFormat.format(new_role_hint, commodityVO.getPropName(),commodityVO.getSellNum());
			hint.append(new_role_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n6")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("new_role_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("��ɫ������Ϸ���ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	/**
	 * �̳ǽ��տ�Ѷ
	 * @return
	 *//*
	public String loginHotDisplay(HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getRandomHotCommodity();
		hint.append("�����տ�Ѷ��<br/>");
		if(commodityVO != null){
			hint.append(commodityVO.getHotDisplay()+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + commodityVO.getDiscount() + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("����û��������Ʒ");
		}
		return hint.toString();
	}*/
	
	/**
	 * ʹ�õ������ 
	 * @param response
	 * @return
	 */
	public String SysSpecialUseProp(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//ȡ�õȼ��ĵȼ����ڵ�λ��
		String[] use_prop_mallprop = GameConfig.getPropertiesObjectArray("use_prop_mallprop");//ȡ��Ҫ��ĵ���ID
		//�õ��̳���Ʒ��Ϣ
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getPropCommodityInfo(use_prop_mallprop[Integer.parseInt(msgOperate2[1])]);
		if(commodityVO != null){
			String use_prop_hint = GameConfig.getPropertiesObject("use_prop_hint");
			use_prop_hint = MessageFormat.format(use_prop_hint, commodityVO.getPropName(),commodityVO.getPropName());
			hint.append(use_prop_hint+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + GameConfig.getPropertiesObject("use_prop_discount") + "\" /> ");
			hint.append("</go>");
			hint.append("ȷ�Ϲ���");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("�´ΰ�");
			hint.append("</anchor>");
		}else{
			hint.append("ʹ�õ������ݴ���!����ϵ����Ա!");
		}
		return hint.toString();
	}
	public static void main(String[] args)
	{ 
	}
}
