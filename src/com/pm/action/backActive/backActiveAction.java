package com.pm.action.backActive;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.encrypt.MD5Util;

public class backActiveAction extends DispatchAction
{

	Logger logger = Logger.getLogger("log.action");

	// ������һ��˺��ٴ��ύ���������
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String previourFile = (String) request.getSession().getAttribute("PreviourFile");
		
		String preiour = "";

		try
		{
			if ( previourFile == null || previourFile.equals(""))
			{
				String uPk = (String) request.getSession().getAttribute("uPk");
				
				if (uPk == null || uPk.equals("") || Integer.parseInt(uPk) == 0)
				{
					int channel_id = GameConfig.getChannelId();
					String outtime_hint = GameConfig.getTimeoutHint();
					if(Channel.JUU == channel_id){
						String key = "3IOJ3934KJ3493KJ94K";
						String username = (String)request.getSession().getAttribute("ssid");
						String time = Long.toString(new Date().getTime()/1000);
						String sign = MD5Util.md5Hex("51"+username+time+key);
						preiour = outtime_hint+"<br/>";
						preiour = preiour+"<anchor><go href=\"http://interface.juu.cn/new/no_pws_login.php?gameid=51&amp;username="+username+"&amp;time="+time+"&amp;sign="+sign+"\" method=\"get\"></go>����ר��</anchor><br/>";
					
					}else{
						if(GameConfig.getChannelId() == Channel.AIR){
							preiour = "���ӳ�ʱ,�����µ�½<br/>";
							preiour = preiour+"<anchor><go method='get' href=\""+response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")+"\"/>���µ�½</anchor>";
						}else{
							String login_platform_url = GameConfig.getUrlOfLoginPlatform();
							preiour = outtime_hint+"<br/>";
							preiour = preiour+"<a href=\""+login_platform_url+"\">����ר��</a>";	
						}
					}
				}
				else
				{ 
					preiour = "��Ч����!<br/>";
					preiour = preiour+"<anchor><go method='get' href=\""+response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")+"\"/>������ҳ</anchor>";
				}
			}
			else
			{
				String preiour1 = previourFile.split("<p>")[1];
				preiour = preiour1.split("<\\/p>")[0]; 
			}
		}
		catch (Exception e)
		{
			if(GameConfig.getChannelId() == Channel.AIR){
				try
				{
					request.getRequestDispatcher("login.do").forward(request, response); 
				}
				catch (Exception ex)
				{
					preiour = "���ӳ�ʱ,�����µ�½<br/>";
					preiour = preiour+"<anchor><go method='get' href=\""+response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3")+"\"/>���µ�½</anchor>";
				}
			}else{
				String outtime_hint = GameConfig.getTimeoutHint();
				String login_platform_url = GameConfig.getUrlOfLoginPlatform();
				preiour = outtime_hint+"<br/>";
				preiour = preiour+"<a href=\""+login_platform_url+"\">����ר��</a>";	
			}
			request.setAttribute("PreviourFile", preiour);
			return mapping.findForward("back");
		}
		request.setAttribute("PreviourFile", preiour);

		return mapping.findForward("back");
	}

}