package com.ben.jms;

import java.util.HashMap;
import java.util.Map;

import com.ls.model.user.RoleEntity;
import com.ls.pub.config.GameConfig;
import com.ls.web.service.player.RoleService;

public class JmsUtil
{
	public static Map<QSQ, QudaoDetail> QYDAODETAIL_MAP = new HashMap<QSQ, QudaoDetail>();
	// private static String SUPER_QUDAO = GameConfig.getChannelId() + "";
	private static String FENQU = GameConfig.getAreaId();

	public static void addPeo(String super_qudao, String qudao)
	{
		if (GameConfig.jmsIsOn()&&super_qudao!=null&&!"null".equalsIgnoreCase(super_qudao.trim())&&qudao!=null&&!"null".equalsIgnoreCase(qudao.trim()))
		{
			QSQ qsq = new QSQ(super_qudao, qudao);
			synchronized (QYDAODETAIL_MAP)
			{
				QudaoDetail qd = QYDAODETAIL_MAP.get(qsq);
				if (qd != null)
				{
					qd.addPeo();
				}
				else
				{
					QYDAODETAIL_MAP.put(qsq, new QudaoDetail());
				}
			}
		}

	}

	public static void delPeo(String super_qudao, String qudao)
	{
		if (GameConfig.jmsIsOn()&&super_qudao!=null&&!"null".equalsIgnoreCase(super_qudao.trim())&&qudao!=null&&!"null".equalsIgnoreCase(qudao.trim()))
		{
			QSQ qsq = new QSQ(super_qudao, qudao);
			synchronized (QYDAODETAIL_MAP)
			{
				QudaoDetail qd = QYDAODETAIL_MAP.get(qsq);
				if (qd != null)
				{
					qd.delPeo();
					QYDAODETAIL_MAP.put(qsq, qd);
				}
			}
		}
	}

	public static void sendJmsRole(String super_qudao, String qudao,
			String fenqu, String userid, String name, int level)
	{
		if (GameConfig.jmsIsOn()&&super_qudao!=null&&!"null".equalsIgnoreCase(super_qudao.trim())&&qudao!=null&&!"null".equalsIgnoreCase(qudao.trim()))
		{

			RoleJms rj = new RoleJms();
			rj.setFenqu(fenqu);
			rj.setLevel(level);
			rj.setName(name);
			rj.setQudao(qudao);
			rj.setSuper_qudao(super_qudao);
			rj.setUserid(userid);
			System.out.println("����ע����Ϣ ���� �� " + super_qudao + " ������ �� " + qudao
					+ " ���� �� " + fenqu + " �û�ID �� " + userid + " �ǳ� �� " + name);
			JmsSendQueue1.log(rj);
		}
	}

	public static void sendJmsRole(String super_qudao, String qudao,
			String userid, String name, int level)
	{
		if (GameConfig.jmsIsOn()&&super_qudao!=null&&!"null".equalsIgnoreCase(super_qudao.trim())&&qudao!=null&&!"null".equalsIgnoreCase(qudao.trim()))
		{
			RoleJms rj = new RoleJms();
			rj.setFenqu(FENQU);
			rj.setQudao(qudao);
			rj.setSuper_qudao(super_qudao);
			rj.setUserid(userid);
			rj.setName(name);
			rj.setLevel(level);
			System.out.println("����ע����Ϣ ���� �� " + super_qudao + " ������ �� " + qudao
					+ " ���� �� " + FENQU + " �û�ID �� " + userid + " �ǳ� �� " + name);
			JmsSendQueue1.log(rj);
		}
	}

	public static void updateLevel(String super_qudao, String qudao,
			String userid, String name, int level)
	{
		if (GameConfig.jmsIsOn()&&super_qudao!=null&&!"null".equalsIgnoreCase(super_qudao.trim())&&qudao!=null&&!"null".equalsIgnoreCase(qudao.trim()))
		{
			RoleJms rj = new RoleJms();
			rj.setFenqu(FENQU);
			rj.setQudao(qudao);
			rj.setSuper_qudao(super_qudao);
			rj.setUserid(userid);
			rj.setName(name);
			rj.setLevel(level);
			rj.setCaozuo(1);
			System.out.println("����������Ϣ ���� �� " + super_qudao + " ������ �� " + qudao
					+ " ���� �� " + FENQU + " �û�ID �� " + userid + " �ǳ� �� " + name
					+ " �ȼ� �� " + level);
			JmsSendQueue1.log(rj);
		}
	}

	public static void updateLast_loginin_time(String super_qudao,
			String qudao, String userid, String name)
	{
		if (GameConfig.jmsIsOn()&&super_qudao!=null&&!"null".equalsIgnoreCase(super_qudao.trim())&&qudao!=null&&!"null".equalsIgnoreCase(qudao.trim()))
		{
			addPeo(super_qudao, qudao);
			RoleJms rj = new RoleJms();
			rj.setFenqu(FENQU);
			rj.setQudao(qudao);
			rj.setSuper_qudao(super_qudao);
			rj.setUserid(userid);
			rj.setName(name);
			rj.setCaozuo(2);
			System.out.println("�������ʱ����Ϣ ���� �� " + super_qudao + " ������ �� "
					+ qudao + " ���� �� " + FENQU + " �û�ID �� " + userid + " �ǳ� �� "
					+ rj.getName());
			JmsSendQueue1.log(rj);
		}
	}

	public static void sendQudaoMessage(QudaoMessage qm)
	{
		if (GameConfig.jmsIsOn())
		{
			System.out.println("����ϵͳͳ�� ���� �� " + qm.getSuper_qudao() + " ������ �� "
					+ qm.getQudao() + " ���� �� " + qm.getFenqu() + " ƽ������ �� "
					+ qm.getNow_peo() + " ������� �� " + qm.getMax_peo());
			JmsSendQueue2.log(qm);
		}
	}

	public static void chongzhi(int ppk, int money, String come)
	{
		if (GameConfig.jmsIsOn())
		{
			RoleEntity re = new RoleService().getRoleInfoById(ppk + "");
			if (re != null&&re.getStateInfo().getSuper_qudao()!=null&&!"null".equalsIgnoreCase(re.getStateInfo().getSuper_qudao().trim())&&re.getStateInfo().getQudao()!=null&&!"null".equalsIgnoreCase(re.getStateInfo().getQudao().trim()))
			{
				ChongzhiMessage cm = new ChongzhiMessage();
				if (come != null)
				{
					if (come.trim().equals("SNDACARD"))
					{
						cm.setZheng(money);
					}
					else
						if (come.trim().equals("JUNNET"))
						{
							cm.setYi(money);
						}
						else
						{
							cm.setShen(money);
						}
				}
				cm.setFenqu(FENQU);
				cm.setMoney(money);
				cm.setQudao(re.getStateInfo().getQudao());
				cm.setSuper_qudao(re.getStateInfo().getSuper_qudao());
				cm.setUserid(re.getStateInfo().getUserid());
				System.out.println("���ͳ�ֵͳ�� ���� �� " + cm.getSuper_qudao()
						+ " ������ �� " + cm.getQudao() + " ���� �� " + cm.getFenqu()
						+ " �û�ID �� " + cm.getUserid() + " ��� �� "
						+ cm.getMoney());
				JmsSendQueue3.log(cm);
			}
		}
	}

	 public static void main(String[] args)
	 {
     while(true){
	 ChongzhiMessage cm = new ChongzhiMessage();
	 cm.setFenqu(FENQU);
	 cm.setMoney(100);
	 cm.setQudao("1");
	 cm.setSuper_qudao("1");
	 cm.setUserid("jy0003");
	 cm.setShen(100);
	 cm.setYi(100);
	 cm.setZheng(100);
	 System.out.println("���ͳ�ֵͳ�� ���� �� "+cm.getSuper_qudao()+" ������ �� "+cm.getQudao() +" ���� �� "+cm.getFenqu()+" �û�ID �� "+cm.getUserid()+" ��� ��	 "+cm.getMoney());
	 JmsSendQueue3.log(cm);
     }
	 }

}