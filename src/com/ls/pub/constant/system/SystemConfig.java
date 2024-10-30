/**
 * 
 */
package com.ls.pub.constant.system;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：系统的配置相关参数
 * @author ls
 * May 14, 2009
 * 3:14:02 PM
 */
public class SystemConfig
{
	public static List<String> UrlOfNoNeedFilter = new ArrayList<String>();// 不需要过滤的url
	
	//攻击显示参数
	public static int attackParameter = 1;
	
	static
	{
		UrlOfNoNeedFilter.add("/test/onlineplayer.jsp");//在线角色数量
		
    	UrlOfNoNeedFilter.add("/loginout.do");
    	UrlOfNoNeedFilter.add("/login.do");
    	UrlOfNoNeedFilter.add("/guide.do");//新手引导
    	UrlOfNoNeedFilter.add("/role.do");
    	UrlOfNoNeedFilter.add("/channel.jsp");
    	UrlOfNoNeedFilter.add("/1.jsp");
    	UrlOfNoNeedFilter.add("/index.jsp");
    	UrlOfNoNeedFilter.add("/tiao/login.do");// 跳网登陆
    	UrlOfNoNeedFilter.add("/jy/login.do");// 金银岛平台对接
    	UrlOfNoNeedFilter.add("/dl/login.do");// 当乐平台对接
    	UrlOfNoNeedFilter.add("/pingtai/login.do");// 平台对接
    	UrlOfNoNeedFilter.add("/yeepay/callback.do");// 当乐易宝回调请求
    	UrlOfNoNeedFilter.add("/game/yeepay/callback.do");// 易宝回调请求
    	UrlOfNoNeedFilter.add("/jun/callback.do");// 骏网回调请求
    	UrlOfNoNeedFilter.add("/juu/callback.do");// JUU回调请求
    	
    	/****************TOM*************************/
    	UrlOfNoNeedFilter.add("/tomlogin.do");// TOM请求
    	UrlOfNoNeedFilter.add("/tomrecvorder.do");// TOM请求
    	UrlOfNoNeedFilter.add("/emporiumaction.do");// TOM请求
		//urlOfNoNeedFilter.add("/loginpage.do");// TOM请求 
    	UrlOfNoNeedFilter.add("/billtype.do");// TOM请求
    	 
    	UrlOfNoNeedFilter.add("/backActive.do");//过滤点击过快
    	//UrlOfNoNeedFilter.add("/unfilter.do");//过滤点击过快
    	
    	UrlOfNoNeedFilter.add("/jsp/task/visitlead/visit_lead_ok.jsp");// 拜师
    	UrlOfNoNeedFilter.add("/jsp/task/visitlead/visit_lead.jsp");// 拜师
    	UrlOfNoNeedFilter.add("/jsp/task/visitlead/visit_lead_over.jsp");// 拜师
    	UrlOfNoNeedFilter.add("/jsp/affiche/affiche1.jsp");// 公告
    	UrlOfNoNeedFilter.add("/jsp/affiche/affiche2.jsp");// 公告
    	UrlOfNoNeedFilter.add("/backdoor.jsp");//内部测试通道（可用万能密码的）
    	UrlOfNoNeedFilter.add("/sysnotify.do");//内部测试通道（可用万能密码的）
    	
    	/********************思凯通道*************************/
    	UrlOfNoNeedFilter.add("/sky/login.do");//账号登陆
    	/********************悠乐渠道***********************/
    	UrlOfNoNeedFilter.add("/youle/login.do");
    	/********************wanxiang通道*************************/
    	UrlOfNoNeedFilter.add("/game/login.do");//账号登陆
    	/********************SZF充值*************************/
    	UrlOfNoNeedFilter.add("/szf/callback.do");//账号登陆
    	/********************JUU通道*************************/
    	UrlOfNoNeedFilter.add("/juu/login.do");//JUU登陆    	
    	/********************大家网通道*************************/
    	UrlOfNoNeedFilter.add("/djw/login.do");//DJW登陆
    	/********************空中网通道**********************/
    	UrlOfNoNeedFilter.add("/air/login.do");//空中网登陆
    	UrlOfNoNeedFilter.add("/air/callback.do");//空中网登陆
    	/********************sina通道*************************/
    	UrlOfNoNeedFilter.add("/sina/login.do");//sina登陆
    	UrlOfNoNeedFilter.add("/sina/vis.do");//sina登陆
    	UrlOfNoNeedFilter.add("/sina/callback.do");//sina登陆
    	/********************youvb通道*************************/
    	UrlOfNoNeedFilter.add("/youvb/login.do");//youvb登陆
    	UrlOfNoNeedFilter.add("/youvb/callback.do");//youvb登陆
    	/********************后台管理过滤*************************/
    	UrlOfNoNeedFilter.add("/jsp/systemresources/system_login.jsp");//后台管理过滤
    	UrlOfNoNeedFilter.add("/systemresources.do");//后台管理过滤
    	UrlOfNoNeedFilter.add("/zhoujianren.jsp");
    	UrlOfNoNeedFilter.add("/langjun.do");
    	UrlOfNoNeedFilter.add("/ganmelogin.do");
    	UrlOfNoNeedFilter.add("/ganmecallback.do");
    	
    	/********************ok通道*************************/
    	UrlOfNoNeedFilter.add("/ok/login.do");//ok登陆
    	UrlOfNoNeedFilter.add("/ok/callback.do");//ok登陆
    	
    	/********************天下通道*************************/
    	UrlOfNoNeedFilter.add("/tx/login.do");//ok登陆
    	UrlOfNoNeedFilter.add("/tx/callback.do");//ok登陆
    	/********************电信通道************/
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
