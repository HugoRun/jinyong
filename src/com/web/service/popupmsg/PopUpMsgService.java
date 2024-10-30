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
 * @author HHJ 系统特殊类型的弹出式消息
 */
public class PopUpMsgService
{
	/**
	 * 增加弹出式消息
	 * @param p_pk
	 * @param gread 玩家等级
	 * @param digit 道具数组位置
	 * @param msgtype 消息类型
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
			case PopUpMsgType.GO_UP_GRADE:// ##等级情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.GO_UP_GRADE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.WRAP_LOWER_LIMIT:// ##包裹情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.WRAP_LOWER_LIMIT + "");
				break;
			case PopUpMsgType.PET_FATIGUE:// ##宠物情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.PET_FATIGUE + "");
				break;
			case PopUpMsgType.ATTAIN_PROP_TYPE:// ##道具情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.ATTAIN_PROP_TYPE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.TASK_INSTANCE://##副本任务情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.TASK_INSTANCE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.TASK_30TONG://30级帮派结束任务情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.TASK_30TONG + "");
				break;
			case PopUpMsgType.TASK_30PK://30级PK结束任务情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.TASK_30PK + "");
				break;
			case PopUpMsgType.MENU_INSTANCE://副本菜单
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.MENU_INSTANCE + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
			case PopUpMsgType.MENU_SIEGE://攻城菜单情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.MENU_SIEGE + "");
				break;
			case PopUpMsgType.PROP_GRADE://特殊道具在特殊等级的情况
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.PROP_GRADE + "");
				break;
			case PopUpMsgType.VIP_ENDTIME://VIP会员到期以后
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.VIP_ENDTIME + "");
				break;
			case PopUpMsgType.PK_SWITCH://PK开关打开
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.PK_SWITCH + "");
				break;
			case PopUpMsgType.NEW_ROLE://新注册账号
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.NEW_ROLE + "");
				break;
			/*case PopUpMsgType.CNN_TODAY://商城今日快讯
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.CNN_TODAY + "");
				break;*/
			case PopUpMsgType.USE_PROP://使用道具
				// 在这里插入弹出式消息内容
				msgInfo.setMsgOperate1(PopUpMsgType.USE_PROP + "");
				msgInfo.setMsgOperate2(gread+","+digit);
				break;
		}
		
		uMsgService.sendPopUpMsg(msgInfo);
	}

	/**
	 * 等级类型
	 * @return
	 */
	public String SysSpecialGrade(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//取得等级的等级所在的位置
		String[] go_up_mallprop = GameConfig.getPropertiesObjectArray("go_up_mallprop");//取得要买的道具ID
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("等级数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	/**
	 * 包裹类型
	 * @return
	 */
	public String SysSpecialWrapLowerLimit(HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String wrap_hint = GameConfig.getPropertiesObject("wrap_hint");
		hint.append(wrap_hint+"<br/><anchor> "); 
		hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n4")+"\"></go>");
		hint.append("确认购买");
		hint.append("</anchor>");
		hint.append("<br/><anchor> "); 
		hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
		hint.append("下次吧");
		hint.append("</anchor>");
		return hint.toString();
	}
	/**
	 * 宠物情况
	 * @param resources
	 * @param response
	 * @return
	 */
	public String SysSpecialPetFatigue(HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String pet_mallprop = GameConfig.getPropertiesObject("pet_mallprop");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("宠物数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	/**
	 * 获得道具情况 
	 * @param response
	 * @return
	 */
	public String SysSpecialAttainProp(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//取得等级的等级所在的位置
		PropVO prop = PropCache.getPropById(Integer.parseInt(msgOperate2[0]));
		String[] attainprop_mallprop = GameConfig.getPropertiesObjectArray("attainprop_mallprop");//取得要买的道具ID
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("道具数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * 副本任务情况
	 * @return
	 */
	public String SysSpecialTaskInstance(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//取得等级的等级所在的位置
		String[] task_instance_mallprop = GameConfig.getPropertiesObjectArray("task_instance_mallprop");//取得要买的道具ID
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("副本数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * //30级帮派结束任务情况
	 * @return
	 */
	public String SysSpecialTask30Tong(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String task_30tong_mallprop = GameConfig.getPropertiesObject("task_30tong_mallprop");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("帮派数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	/**
	 * //30级PK结束任务情况
	 * @return
	 */
	public String SysSpecialTask30Pk(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String task_30pk_mallprop = GameConfig.getPropertiesObject("task_30pk_mallprop");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("pk数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * 菜单进入副本情况
	 * @return
	 */
	public String SysSpecialMenuInstance(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//取得等级的等级所在的位置
		int digit  = GameConfig.getMenuInstance(msgOperate2[0], "menu_instance_grade");
		if(digit != -1){
			String[] menu_instance_mallprop = GameConfig.getPropertiesObjectArray("menu_instance_mallprop");//取得要买的道具ID
			//得到商城商品信息
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
				hint.append("确认购买");
				hint.append("</anchor>");
				hint.append("<br/><anchor> "); 
				hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
				hint.append("下次吧");
				hint.append("</anchor>");
			}else{
				hint.append("菜单数据错误!请联系管理员!");
			}
		}else{
			hint.append("菜单数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * 攻城菜单情况
	 * @return
	 */
	public String SysSpecialMenuSiege(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String menu_siege_mallprop = GameConfig.getPropertiesObject("menu_siege_mallprop");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("攻城数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * 特殊道具在特殊等级的情况
	 * @return
	 */
	public String SysSpecialPropGrade(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String prop_grade_mallprop = GameConfig.getPropertiesObject("prop_grade_mallprop");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("特殊道具数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * VIP到期
	 * @return
	 */
	public String SysSpecialVipEndtime(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String vip_endtime_proptype = GameConfig.getPropertiesObject("vip_endtime_proptype");
		//首先得到所有会员类型的道具
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
	    		hint.append("购买");
	    		hint.append("</anchor><br/>");
	    	}
	    	hint.append("<anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
	    }else{
	    	hint.append("vip到期数据错误!请联系管理员!");
	    } 
		return hint.toString();
	}
	
	/**
	 * PK开关打开的时候
	 * @return
	 */
	public String SysSpecialPkSwitch(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String pk_switch_mallprop = GameConfig.getPropertiesObject("pk_switch_mallprop");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("pk开关数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	
	/**
	 * 新角色进入游戏提示
	 * @return
	 */
	public String SysSpecialNewRole(HttpServletResponse response,HttpServletRequest request)
	{
		StringBuffer hint = new StringBuffer();
		String new_role_prop_id = GameConfig.getPropertiesObject("new_role_prop_id");
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("角色进入游戏数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	/**
	 * 商城今日快讯
	 * @return
	 *//*
	public String loginHotDisplay(HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		MallService mallService = new MallService();
		CommodityVO commodityVO = mallService.getRandomHotCommodity();
		hint.append("【今日快讯】<br/>");
		if(commodityVO != null){
			hint.append(commodityVO.getHotDisplay()+"<br/><anchor> "); 
			hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/popupmsg.do?cmd=n3")+"\">");
			hint.append("<postfield name=\"c_id\" value=\"" + commodityVO.getId() + "\" /> ");
			hint.append("<postfield name=\"discount\" value=\"" + commodityVO.getDiscount() + "\" /> ");
			hint.append("</go>");
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("今日没有热销商品");
		}
		return hint.toString();
	}*/
	
	/**
	 * 使用道具情况 
	 * @param response
	 * @return
	 */
	public String SysSpecialUseProp(UMessageInfoVO msg,HttpServletResponse response,HttpServletRequest request){
		StringBuffer hint = new StringBuffer();
		String[] msgOperate2 = msg.getMsgOperate2().split(",");//取得等级的等级所在的位置
		String[] use_prop_mallprop = GameConfig.getPropertiesObjectArray("use_prop_mallprop");//取得要买的道具ID
		//得到商城商品信息
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
			hint.append("确认购买");
			hint.append("</anchor>");
			hint.append("<br/><anchor> "); 
			hint.append("<go method=\"get\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")+"\"></go>");
			hint.append("下次吧");
			hint.append("</anchor>");
		}else{
			hint.append("使用道具数据错误!请联系管理员!");
		}
		return hint.toString();
	}
	public static void main(String[] args)
	{ 
	}
}
