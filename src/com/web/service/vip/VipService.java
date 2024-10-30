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
	 * �ж�����Ƿ��Ѿ���VIP�� ����ǵĻ�����
	 * @param p_pk
	 * @param v_id
	 * @return
	 *//*
	public String isVip(int p_pk, String v_id, int prop_id, int buff_id,HttpServletRequest request, HttpServletResponse response)
	{
		// ��ȡ��ǰʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		StringBuffer hint = new StringBuffer();
		// �����жϸ�����Ƿ��Ѿ���VIP��ĳƺ���
		HonourService honourService = new HonourService();
		if (honourService.isRoleHonourType(p_pk, TitleVO.VIP))
		{// ������ƺ�
			VipCach vipCach = new VipCach();
			VipVO vo = vipCach.getById(v_id);// ��ȡVIP�����Ϣ
			if (vo != null)
			{
				TitleVO titleVO = TitleCache.getById(vo.getHoId() + "");// �õ��ƺ������Ϣ
				RoleEntity roleEntity = RoleService.getRoleInfoById(p_pk+"");
				RoleVipVO roleVipVO = roleEntity.getRoleVip().getRoleVipView();
				if (roleVipVO != null)
				{
					int time = DateUtil.getDifferTimes(Time, roleVipVO
							.getRvEndTime());
					hint.append("�������Ѿ���" + roleVipVO.getVName() + "��"
							+ DateUtil.returnDay(time) + "Сʱ�������ʹ�øõ��ߺ��㽫���"
							+ DateUtil.returnDay(vo.getUseTime() * 60) + "�ġ�"
							+ titleVO.getName() + "��������ƺţ�����"
							+ roleVipVO.getVName()
							+ "���ĳƺż����������Խ���ʧ����ȷ��Ҫʹ�øõ�����<br/>");
					hint.append("<anchor> ");
					hint.append("<go method=\"post\" href=\""+response.encodeURL(GameConfig.getContextPath()+"/vip.do?cmd=n1")+"\">");
					hint.append("<postfield name=\"v_id\" value=\"" + v_id
							+ "\" /> ");
					hint.append("<postfield name=\"prop_id\" value=\""
							+ prop_id + "\" /> ");
					hint.append("<postfield name=\"buff_id\" value=\""
							+ buff_id + "\" /> ");
					hint.append("</go>");
					hint.append("ȷ��");
					hint.append("</anchor>");
				}
			}
		}
		return hint.toString();
	}

	*//**
	 * �������һ��VIP
	 * @param p_pk
	 * @param v_id
	 * @param honourVO
	 * @return
	 *//*
	public String addVip(RoleEntity role_info, String v_id, int buff_id)
	{
		// ��ȡ��ǰʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		// ���Ȼ�ȡVIP��̨���Ӧ������
		VipCach vipCach = new VipCach();
		VipVO vipVO = vipCach.getById(v_id);// ��ȡVIP�����Ϣ

		// ������Ӧ�ĳƺ�
		TitleVO titleVO = TitleCache.getById(vipVO.getHoId() + "");// �õ��ƺ������Ϣ

		// ���������һ���ƺ�
		HonourService honourService = new HonourService();
		honourService.addRoleHonour(role_info.getBasicInfo().getPPk(), titleVO.getId(), titleVO.getType());// ���������һ���ƺ�

		// ���������һ��VIP�˺�
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
		role_info.getRoleVip().addRoleVip(roleVipVO);// ���������һ��VIP����

		
		SystemInfoService systemInfoService = new SystemInfoService();
		
		String info = "���"+ role_info.getName()+ "��Ϊ��������" + vipVO.getVName() + "";
		
		systemInfoService.insertSystemInfoBySystem(info);
		
		return vipVO.getVipHint();
	}

	// �޸�һ�����VIP
	public String updateVip(RoleEntity roleInfo, String v_id, int buff_id)
	{
		// ��ȡ��ǰʱ��
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ʱ����и�ʽ��
		String Time = formatter.format(new Date());// ��ҳ��õ���ǰʱ��,���Ҹ���һ������

		// ���Ȼ�ȡVIP��̨���Ӧ������
		VipCach vipCach = new VipCach();
		VipVO vipVO = vipCach.getById(v_id);// ��ȡVIP�����Ϣ

		// ������Ӧ�ĳƺ�
		TitleVO titleVO = TitleCache.getById(vipVO.getHoId() + "");// �õ��ƺ������Ϣ

		// ���������һ���ƺ�
		HonourService honourService = new HonourService();
		honourService.addRoleHonour(roleInfo.getBasicInfo().getPPk(), titleVO.getId(), titleVO.getType());// ���������һ���ƺ�

		// ���������һ��VIP�˺�
		// RoleVip roleVip = new RoleVip(p_pk);//�õ���ҵ�VIP����
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
		// "��ϲ���Ϊ��Ϸ��"+vipVO.getVName()+"����Ա��Чʱ��Ϊ"+DateUtil.returnDay(vipVO.getUseTime()*60)+"���ڴ��ڼ�"+vipVO.getVName()+"�ʸ��Ϊ��������ºô���<br/>1.���Խ����̳��Ļ�Աר�������Աר����Ʒ<br/>2.������ͨ�̳���Ʒ����"+vipVO.getVipDiscount()+"�Ż�<br/>3.ӵ�С�"+vipVO.getVName()+"��������ƺ�<br/>4.��ʾ��"+vipVO.getVName()+"����������׿Խ����";
		SystemInfoService systemInfoService = new SystemInfoService();
		PartInfoDao partInfoDao = new PartInfoDao();
		String info = "���"
				+ partInfoDao.getNameByPpk(roleInfo.getBasicInfo().getPPk())
				+ "��Ϊ��������" + vipVO.getVName() + "��Ա";
		systemInfoService.insertSystemInfoBySystem(info);
		return vipVO.getVipHint();
	}



	*//**
	 * vip���ڣ�����ҷ��͹���vip����Ϣ
	 * @param p_pk
	 * @param vipName
	 *//*
	public void sendVipEndMail(int p_pk, String vipName)
	{
		// �����ʼ�������ҵ�����
		StringBuffer sb = new StringBuffer(800);
		MailInfoService mailInfoService = new MailInfoService();
		String title = "ϵͳ�ʼ�";
		sb.append("���").append(vipName).append("�ʸ��Ѿ�����,���������ٴι����Ա�ʸ������8���Ż�!<br/>");
		// ���ȵõ����л�Ա���͵ĵ���
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
			sb.append("����");
			sb.append("</anchor><br/>");
		}
		mailInfoService.sendMailBySystem(p_pk, title, sb.toString());
	}*/

	/***************************************************************************
	 * 
	 * �����ݞ�LW�� ���ܣ�VIP�Iȡ���Y������
	 * 
	 **************************************************************************/
	// �õ�ԓ��ҵ�VIP���I���Ľ��X ����-1�鲻���Iȡ
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

	/** �IȡVIP���Y������ */
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
				Vip vip = roleInfo.getTitleSet().getVIP();// �õ���ҵ�VIP����
				roleInfo.getBasicInfo().addCopper(money);
				// ���´Δ�
				timeControlService.updateControlInfo(roleInfo.getBasicInfo()
						.getPPk(), 1, TimeControlService.VIPLABORAGE);
				return "��������" + vip.getName() + ",��ȡ��"
						+ money / 100 + "��!";
			}
			else
			{
				return null;
			}
		}
	}

	/***************************************************************************
	 * ��ȡ����ǰ�Ĺ�����ʾ
	 * ����null ��ʾû������
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
				sb.append(vo.getName()).append("ÿ��").append(vo.getSalary() / 100).append("��!<br/>");
			}
			return sb.toString();
		}
		else
		{
			return null;
		}
	}

	/** *�㹤��ʱ����VIP��ƴ��** */
	public String getPlayerGetVipMoneyByBuyVip(HttpServletResponse response,
			HttpServletRequest request)
	{
		CommodityDao commodityDao = new CommodityDao();
		
		
		List<CommodityVO> list = commodityDao.getListByType(1);//�õ���Ա����Ʒ�б�
		
		
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
				sb.append("����").append(vo.getPropName());
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
