/**
 * 
 */
package com.ls.pub.constant.system;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ܣ�ϵͳ��������ز���
 * @author ls
 * May 14, 2009
 * 3:14:02 PM
 */
public class SystemConfig
{
	public static List<String> UrlOfNoNeedFilter = new ArrayList<String>();// ����Ҫ���˵�url
	
	//������ʾ����
	public static int attackParameter = 1;
	
	static
	{
		UrlOfNoNeedFilter.add("/test/onlineplayer.jsp");//���߽�ɫ����
		
    	UrlOfNoNeedFilter.add("/loginout.do");
    	UrlOfNoNeedFilter.add("/login.do");
    	UrlOfNoNeedFilter.add("/guide.do");//��������
    	UrlOfNoNeedFilter.add("/role.do");
    	UrlOfNoNeedFilter.add("/channel.jsp");
    	UrlOfNoNeedFilter.add("/1.jsp");
    	UrlOfNoNeedFilter.add("/index.jsp");
    	UrlOfNoNeedFilter.add("/tiao/login.do");// ������½
    	UrlOfNoNeedFilter.add("/jy/login.do");// ������ƽ̨�Խ�
    	UrlOfNoNeedFilter.add("/dl/login.do");// ����ƽ̨�Խ�
    	UrlOfNoNeedFilter.add("/pingtai/login.do");// ƽ̨�Խ�
    	UrlOfNoNeedFilter.add("/yeepay/callback.do");// �����ױ��ص�����
    	UrlOfNoNeedFilter.add("/game/yeepay/callback.do");// �ױ��ص�����
    	UrlOfNoNeedFilter.add("/jun/callback.do");// �����ص�����
    	UrlOfNoNeedFilter.add("/juu/callback.do");// JUU�ص�����
    	
    	/****************TOM*************************/
    	UrlOfNoNeedFilter.add("/tomlogin.do");// TOM����
    	UrlOfNoNeedFilter.add("/tomrecvorder.do");// TOM����
    	UrlOfNoNeedFilter.add("/emporiumaction.do");// TOM����
		//urlOfNoNeedFilter.add("/loginpage.do");// TOM���� 
    	UrlOfNoNeedFilter.add("/billtype.do");// TOM����
    	 
    	UrlOfNoNeedFilter.add("/backActive.do");//���˵������
    	//UrlOfNoNeedFilter.add("/unfilter.do");//���˵������
    	
    	UrlOfNoNeedFilter.add("/jsp/task/visitlead/visit_lead_ok.jsp");// ��ʦ
    	UrlOfNoNeedFilter.add("/jsp/task/visitlead/visit_lead.jsp");// ��ʦ
    	UrlOfNoNeedFilter.add("/jsp/task/visitlead/visit_lead_over.jsp");// ��ʦ
    	UrlOfNoNeedFilter.add("/jsp/affiche/affiche1.jsp");// ����
    	UrlOfNoNeedFilter.add("/jsp/affiche/affiche2.jsp");// ����
    	UrlOfNoNeedFilter.add("/backdoor.jsp");//�ڲ�����ͨ����������������ģ�
    	UrlOfNoNeedFilter.add("/sysnotify.do");//�ڲ�����ͨ����������������ģ�
    	
    	/********************˼��ͨ��*************************/
    	UrlOfNoNeedFilter.add("/sky/login.do");//�˺ŵ�½
    	/********************��������***********************/
    	UrlOfNoNeedFilter.add("/youle/login.do");
    	/********************wanxiangͨ��*************************/
    	UrlOfNoNeedFilter.add("/game/login.do");//�˺ŵ�½
    	/********************SZF��ֵ*************************/
    	UrlOfNoNeedFilter.add("/szf/callback.do");//�˺ŵ�½
    	/********************JUUͨ��*************************/
    	UrlOfNoNeedFilter.add("/juu/login.do");//JUU��½    	
    	/********************�����ͨ��*************************/
    	UrlOfNoNeedFilter.add("/djw/login.do");//DJW��½
    	/********************������ͨ��**********************/
    	UrlOfNoNeedFilter.add("/air/login.do");//��������½
    	UrlOfNoNeedFilter.add("/air/callback.do");//��������½
    	/********************sinaͨ��*************************/
    	UrlOfNoNeedFilter.add("/sina/login.do");//sina��½
    	UrlOfNoNeedFilter.add("/sina/vis.do");//sina��½
    	UrlOfNoNeedFilter.add("/sina/callback.do");//sina��½
    	/********************youvbͨ��*************************/
    	UrlOfNoNeedFilter.add("/youvb/login.do");//youvb��½
    	UrlOfNoNeedFilter.add("/youvb/callback.do");//youvb��½
    	/********************��̨�������*************************/
    	UrlOfNoNeedFilter.add("/jsp/systemresources/system_login.jsp");//��̨�������
    	UrlOfNoNeedFilter.add("/systemresources.do");//��̨�������
    	UrlOfNoNeedFilter.add("/zhoujianren.jsp");
    	UrlOfNoNeedFilter.add("/langjun.do");
    	UrlOfNoNeedFilter.add("/ganmelogin.do");
    	UrlOfNoNeedFilter.add("/ganmecallback.do");
    	
    	/********************okͨ��*************************/
    	UrlOfNoNeedFilter.add("/ok/login.do");//ok��½
    	UrlOfNoNeedFilter.add("/ok/callback.do");//ok��½
    	
    	/********************����ͨ��*************************/
    	UrlOfNoNeedFilter.add("/tx/login.do");//ok��½
    	UrlOfNoNeedFilter.add("/tx/callback.do");//ok��½
    	/********************����ͨ��************/
    	UrlOfNoNeedFilter.add("/telecom/login.do");
    	UrlOfNoNeedFilter.add("/telecom/synuserinterface.do");
    	UrlOfNoNeedFilter.add("/telecom/bill.do");
    	UrlOfNoNeedFilter.add("/cooperate/tele/index/pointPage.jsp");
    	UrlOfNoNeedFilter.add("/cooperate/tele/index/abouteGame.jsp");
    	UrlOfNoNeedFilter.add("/cooperate/tele/index/viewPoint.jsp");
    	UrlOfNoNeedFilter.add("/cooperate/tele/index/pointHelp.jsp");
    	UrlOfNoNeedFilter.add("/sky/bill.do");
    	UrlOfNoNeedFilter.add("/cooperate/tele/bill/index.jsp");
    	UrlOfNoNeedFilter.add("/cooperate/tele/bill/fail.jsp");
    	UrlOfNoNeedFilter.add("/cooperate/tele/bill/success.jsp");
    	UrlOfNoNeedFilter.add("/cooperate/tele/index/gamehelp.jsp");
	}

}
