package com.pm.service.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ls.ben.cache.dynamic.manual.user.RoleCache;
import com.ls.pub.config.GameConfig;
import com.ls.pub.constant.Channel;
import com.ls.pub.util.encrypt.MD5Util;
import com.ls.pub.yeepay.HttpUtils;
import com.ls.web.service.login.LoginService;

public class JUUGameOnlinePlayerJob implements Job
{
	public void execute(JobExecutionContext context)
	{
		if(GameConfig.getChannelId()!=Channel.JUU)
		{
			return;
		}
		String gameid = "51";
		String serverid = "74";
		int total = LoginService.getOnlineNum();
		String date = Long.toString(new Date().getTime() / 1000);
		String time = Long.toString(new Date().getTime() / 1000);
		String key = "3IOJ3934KJ3493KJ94K";
		String sign = MD5Util.md5Hex(gameid + serverid + total + date + time
				+ key);
		List result = null;
		for (int i = 0; i < i + 1; i++)
		{
			Map reqParams = new HashMap();
			reqParams.put("gameid", gameid);
			reqParams.put("serverid", serverid);
			reqParams.put("total", total);
			reqParams.put("date", date);
			reqParams.put("time", time);
			reqParams.put("sign", sign);

			try
			{
				// ·¢ÆðÇëÇó
				result = HttpUtils.URLGet("http://interface.juu.cn/new/online_total.php",reqParams);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e.getMessage());
			}
			if (result.size() == 0)
			{
				throw new RuntimeException("no response.");
			}
			for (int t = 0; t < result.size(); t++)
			{
				String currentResult = (String) result.get(t);
				if (currentResult == null || currentResult.equals(""))
				{
					continue;
				}
				String callback = (String) result.get(t);
				if (callback.equals("success"))
				{
					break;
				}
			}
			break;
		}

	}
}