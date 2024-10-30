package com.web.service.vip;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ls.ben.dao.mall.CommodityDao;
import com.ls.ben.vo.mall.CommodityVO;
import com.ls.model.user.RoleEntity;
import com.ls.model.vip.Vip;
import com.ls.model.vip.VipManager;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.control.TimeControlService;

public class VipService
{

	/**
	 * 判断玩家是否已经是VIP了 如果是的话返回
	 * @param p_pk
	 * @param v_id
	 * @return
	 *//*
	public String isVip(int p_pk, String v_id, int prop_id, int buff_id,HttpServletRequest request, HttpServletResponse response)
	{
		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		StringBuffer hint = new StringBuffer();
		// 首先判断该玩家是否已经有VIP组的称号了
		HonourService honourService = new HonourService();
		if (honourService.isRoleHonourType(p_pk, TitleVO.VIP))
		{// 有这个称号
			VipCach vipCach = new VipCach();
			VipVO vo = vipCach.getById(v_id);// 获取VIP相关信息
			if (vo != null)
			{
				TitleVO titleVO = TitleCache.getById(vo.getHoId() + "");// 得到称号相关信息
				RoleEntity roleEntity = RoleService.getRoleInfoById(p_pk+"");
				RoleVipVO roleVipVO = roleEntity.getRoleVip().getRoleVipView();
				if (roleVipVO != null)
				{
					int time = DateUtil.getDifferTimes(Time, roleVipVO
							.getRvEndTime());
					hint.append("你现在已经是" + roleVipVO.getVName() + "，"
							+ DateUtil.returnDay(time) + "小时后结束！使用该道具后你将获得"
							+ DateUtil.returnDay(vo.getUseTime() * 60) + "的“"
							+ titleVO.getName() + "”的至尊称号，但“"
							+ roleVipVO.getVName()
							+ "”的称号及其所得属性将消失！你确定要使用该道具吗？<br/>");
					hint.append("<anchor> ");
					hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/vip.do?cmd=n1")+"\">");
					hint.append("<postfield name=\"v_id\" value=\"" + v_id
							+ "\" /> ");
					hint.append("<postfield name=\"prop_id\" value=\""
							+ prop_id + "\" /> ");
					hint.append("<postfield name=\"buff_id\" value=\""
							+ buff_id + "\" /> ");
					hint.append("</go>");
					hint.append("确定");
					hint.append("</anchor>");
				}
			}
		}
		return hint.toString();
	}

	*//**
	 * 玩家增加一个VIP
	 * @param p_pk
	 * @param v_id
	 * @param honourVO
	 * @return
	 *//*
	public String addVip(RoleEntity role_info, String v_id, int buff_id)
	{
		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		// 首先获取VIP后台相对应的数据
		VipCach vipCach = new VipCach();
		VipVO vipVO = vipCach.getById(v_id);// 获取VIP相关信息

		// 获得相对应的称号
		TitleVO titleVO = TitleCache.getById(vipVO.getHoId() + "");// 得到称号相关信息

		// 给玩家增加一个称号
		HonourService honourService = new HonourService();
		honourService.addRoleHonour(role_info.getBasicInfo().getPPk(), titleVO.getId(), titleVO.getType());// 给玩家增加一个称号

		// 给玩家增加一个VIP账号
		RoleVipVO roleVipVO = new RoleVipVO();
		roleVipVO.setPPk(role_info.getBasicInfo().getPPk());
		roleVipVO.setHoId(vipVO.getHoId());
		roleVipVO.setVId(vipVO.getVId());
		roleVipVO.setVName(vipVO.getVName());
		TimeShow timeShow = new TimeShow();
		int time = vipVO.getUseTime() * 60;
		String rv_end_time = timeShow.endTime(time);
		roleVipVO.setRvBeginTime(Time);
		roleVipVO.setRvEndTime(rv_end_time);
		roleVipVO.setIsDieDropExp(vipVO.getIsDieDropExp());
		role_info.getRoleVip().addRoleVip(roleVipVO);// 给玩家增加一个VIP数据

		
		SystemInfoService systemInfoService = new SystemInfoService();
		
		String info = "玩家"+ role_info.getName()+ "成为了荣誉的" + vipVO.getVName() + "";
		
		systemInfoService.insertSystemInfoBySystem(info);
		
		return vipVO.getVipHint();
	}

	// 修改一个玩家VIP
	public String updateVip(RoleEntity roleInfo, String v_id, int buff_id)
	{
		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 对时间进行格式化
		String Time = formatter.format(new Date());// 从页面得到当前时间,并且赋给一个变量

		// 首先获取VIP后台相对应的数据
		VipCach vipCach = new VipCach();
		VipVO vipVO = vipCach.getById(v_id);// 获取VIP相关信息

		// 获得相对应的称号
		TitleVO titleVO = TitleCache.getById(vipVO.getHoId() + "");// 得到称号相关信息

		// 给玩家增加一个称号
		HonourService honourService = new HonourService();
		honourService.addRoleHonour(roleInfo.getBasicInfo().getPPk(), titleVO.getId(), titleVO.getType());// 给玩家增加一个称号

		// 给玩家增加一个VIP账号
		// RoleVip roleVip = new RoleVip(p_pk);//得到玩家的VIP数据
		RoleVipVO roleVipVO = new RoleVipVO();
		roleVipVO.setPPk(roleInfo.getBasicInfo().getPPk());
		roleVipVO.setHoId(vipVO.getHoId());
		roleVipVO.setVId(vipVO.getVId());
		roleVipVO.setVName(vipVO.getVName());
		TimeShow timeShow = new TimeShow();
		int time = vipVO.getUseTime() * 60;
		String rv_end_time = timeShow.endTime(time);
		roleVipVO.setRvBeginTime(Time);
		roleVipVO.setRvEndTime(rv_end_time);
		roleVipVO.setIsDieDropExp(vipVO.getIsDieDropExp());
		roleInfo.getRoleVip().updateRoleVip(roleVipVO);

		// String hint =
		// "恭喜你成为游戏的"+vipVO.getVName()+"！会员有效时间为"+DateUtil.returnDay(vipVO.getUseTime()*60)+"，在此期间"+vipVO.getVName()+"资格可为你带来以下好处：<br/>1.可以进入商场的会员专区购买会员专属用品<br/>2.购买普通商场物品享受"+vipVO.getVipDiscount()+"优惠<br/>3.拥有“"+vipVO.getVName()+"”的至尊称号<br/>4.显示“"+vipVO.getVName()+"”给你增加卓越属性";
		SystemInfoService systemInfoService = new SystemInfoService();
		PartInfoDao partInfoDao = new PartInfoDao();
		String info = "玩家"
				+ partInfoDao.getNameByPpk(roleInfo.getBasicInfo().getPPk())
				+ "成为了荣誉的" + vipVO.getVName() + "会员";
		systemInfoService.insertSystemInfoBySystem(info);
		return vipVO.getVipHint();
	}



	*//**
	 * vip到期，给玩家发送购买vip的信息
	 * @param p_pk
	 * @param vipName
	 *//*
	public void sendVipEndMail(int p_pk, String vipName)
	{
		// 发送邮件告诉玩家到期了
		StringBuffer sb = new StringBuffer(800);
		MailInfoService mailInfoService = new MailInfoService();
		String title = "系统邮件";
		sb.append("你的").append(vipName).append("资格已经到期,现在马上再次购买会员资格可享受8折优惠!<br/>");
		// 首先得到所有会员类型的道具
		PropDAO propDAO = new PropDAO();
		List<PropVO> list = propDAO.getPropType(PropType.VIP);
		for (PropVO vo:list)
		{
			sb.append(vo.getPropName());
			sb.append("<anchor>");
			sb.append("<go method=\"post\" href=\"").append(GameConfig.getContextPath()).append("/vip.do"+"\">");
			sb.append("<postfield name=\"cmd\" value=\"n2\" />");
			sb.append("<postfield name=\"prop_id\" value=\"").append(vo.getPropID()).append("\" />");
			sb.append("</go>");
			sb.append("购买");
			sb.append("</anchor><br/>");
		}
		mailInfoService.sendMailBySystem(p_pk, title, sb.toString());
	}*/

	/***************************************************************************
	 * 
	 * 以下內容為LW寫 功能：VIP領取工資的流程
	 * 
	 **************************************************************************/
	// 得到該玩家的VIP能領導的金錢 返回-1為不能領取
	private int getPlayerVipMoney(RoleEntity roleInfo)
	{
		int result = -1;
		if( roleInfo!=null )
		{
			Vip vip = roleInfo.getTitleSet().getVIP();
			if (vip != null)
			{
				return vip.getSalary();
			}
		}
		return result;
	}

	/** 領取VIP工資的流程 */
	public String playerGetVipMoney(RoleEntity roleInfo)
	{
		int money = getPlayerVipMoney(roleInfo);
		if (money == -1)
		{
			return "nomoney";
		}
		else
		{
			TimeControlService timeControlService = new TimeControlService();
			boolean can_get = timeControlService.isUseable(roleInfo
					.getBasicInfo().getPPk(),1, 
					TimeControlService.VIPLABORAGE, 1);
			if (can_get)
			{
				Vip vip = roleInfo.getTitleSet().getVIP();// 得到玩家的VIP数据
				roleInfo.getBasicInfo().addCopper(money);
				// 更新次數
				timeControlService.updateControlInfo(roleInfo.getBasicInfo()
						.getPPk(), 1, TimeControlService.VIPLABORAGE);
				return "您是尊贵的" + vip.getName() + ",领取了"
						+ money / 100 + "两!";
			}
			else
			{
				return null;
			}
		}
	}

	/***************************************************************************
	 * 领取工资前的工资显示
	 * 返回null 表示没有数据
	 */
	public String getPlayerGetVipMoneyPrint()
	{
		List<Vip> list = VipManager.getVipList();
		if (list != null && list.size() != 0)
		{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++)
			{
				Vip vo = list.get(i);
				sb.append(vo.getName()).append("每天").append(vo.getSalary() / 100).append("两!<br/>");
			}
			return sb.toString();
		}
		else
		{
			return null;
		}
	}

	/** *零工资时候购买VIP的拼接** */
	public String getPlayerGetVipMoneyByBuyVip(HttpServletResponse response,
			HttpServletRequest request)
	{
		CommodityDao commodityDao = new CommodityDao();
		
		
		List<CommodityVO> list = commodityDao.getListByType(1);//得到会员卡商品列表
		
		
		if (list != null && list.size() != 0)
		{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++)
			{
				CommodityVO vo = list.get(i);
				sb.append("<anchor>");
				sb.append("<go method=\"post\" href=\"").append(response.encodeURL(GameConfig.getContextPath()+"/vip.do?cmd=n4")).append("\">");
				sb.append("<postfield name=\"prop_id\" value=\"").append( vo.getPropId()).append("\" />");
				sb.append("</go>");
				sb.append("购买").append(vo.getPropName());
				sb.append("</anchor><br/>");
			}
			return sb.toString();
		}
		else
		{
			return null;
		}
	}
}
